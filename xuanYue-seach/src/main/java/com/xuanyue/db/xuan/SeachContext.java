package com.xuanyue.db.xuan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import com.alibaba.fastjson.JSONObject;
import com.xuanyue.db.xuan.antlr.BitQBaseListener;
import com.xuanyue.db.xuan.antlr.BitQLexer;
import com.xuanyue.db.xuan.antlr.BitQParser;
import com.xuanyue.db.xuan.antlr.impl.BitMaxSourceListenerImpl;
import com.xuanyue.db.xuan.antlr.impl.BitQListenerImpl;
import com.xuanyue.db.xuan.antlr.impl.ERRORCheckor;
import com.xuanyue.db.xuan.antlr.impl.ParseTreeE;
import com.xuanyue.db.xuan.antlr.impl.ProofreaderListenerImpl;
import com.xuanyue.db.xuan.core.db.DBMeta;
import com.xuanyue.db.xuan.core.db.IXyDB;
import com.xuanyue.db.xuan.core.db.TableMeta;
import com.xuanyue.db.xuan.core.db.XyDB;
import com.xuanyue.db.xuan.core.exception.SQLException;
import com.xuanyue.db.xuan.core.index.AcceleraterBitIndex;
import com.xuanyue.db.xuan.core.index.BooleanIndex;
import com.xuanyue.db.xuan.core.index.DateIndex;
import com.xuanyue.db.xuan.core.index.FLOATIndex;
import com.xuanyue.db.xuan.core.index.PhoneIndex;
import com.xuanyue.db.xuan.core.index.UNumberIndex;
import com.xuanyue.db.xuan.core.table.IBitIndex;
import com.xuanyue.db.xuan.core.table.IXyTable;
import com.xuanyue.db.xuan.core.task.Accelerater;
import com.xuanyue.db.xuan.core.task.X2YEXTThreadPoolExecutor;
import com.xuanyue.db.xuan.core.task.X2YEXTThreadPoolExecutor.NothingRejectedExecutionHandler;
import com.xuanyue.db.xuan.core.tools.LruCache;

/**
 * 查询引擎的上下文
 *
 * @author 解观海
 * @email guanhaixie@sina.cn
 * @date 2020年10月27日
 */
public class SeachContext {

	private static IXyDB db;
	
	private static X2YEXTThreadPoolExecutor exe;
	//private static ArrayBlockingQueue<X2yThreadPoolExecutor> queryQ = new ArrayBlockingQueue<X2yThreadPoolExecutor>(10);
	ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
	
	private static Map<String,ParseTreeE> treeCache = Collections.synchronizedMap(new LruCache<String,ParseTreeE>(100));
	private static String treeLock = "treeLock";
	
	static {
		exe = new X2YEXTThreadPoolExecutor(40, 80, 20, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10),new NothingRejectedExecutionHandler());
	}
	
	private static ParseTreeE get(ParseTreeWalker walker,String sql) {
		ParseTreeE tree = treeCache.get(sql);
		if(tree == null) {
			synchronized (treeLock) {
				tree = treeCache.get(sql);
				if(tree == null) {
					CodePointCharStream cpcs = CharStreams.fromString( sql );
			        //用 cpcs 构造词法分析器 lexer，词法分析的作用是产生记号
			        BitQLexer lexer = new BitQLexer(cpcs);
			        
			        
			       // CommonTokenStream tokens = new CommonTokenStream(lexer,"comment");
			        
			        //用词法分析器 lexer 构造一个记号流 tokens
			        CommonTokenStream tokens = new CommonTokenStream(lexer);
			        //再使用 tokens 构造语法分析器 parser,至此已经完成词法分析和语法分析的准备工作
			        BitQParser parser = new BitQParser(tokens);
			        
			        
			        parser.removeErrorListeners();
			        ERRORCheckor check = new ERRORCheckor(false);
			        parser.addErrorListener(check);
			        
			        
			        //最终调用语法分析器的规则 prog，完成对表达式的验证
			        tree = new ParseTreeE();
			        tree.setTree(parser.query());
			        if(check.isSuccess()) {
			        	tree.setParallel(lexer.getParallel());
			        	ProofreaderListenerImpl evalByListener = new ProofreaderListenerImpl();
			    		walker.walk(evalByListener, tree.getTree());
			        	tree.setMaxSource(evalByListener.getMaxSource());
					    treeCache.put(sql, tree);
			        }else {
			        	throw new SQLException(check.getMsg());
			        }
				}
			}
		}
		return tree;
	}
	
	public static IResult query(String sql) {
		ParseTreeWalker walker = new ParseTreeWalker();
		ParseTreeE tree = get(walker,sql);
		BitQListenerImpl l2 = new BitQListenerImpl(tree.getMaxSource());
		l2.setParallel(tree.getParallel());
		walker.walk(l2, tree.getTree());
		return l2;
		
	}
	
	public static void initDB(IXyDB db) throws Exception{
		SeachContext.db = db;
	}
	public static IXyTable getTable(String name) {
		return db.getTable(name);
	}
	public static Accelerater getAccelerate() throws InterruptedException {
		return new Accelerater(exe);
	}
	public static List<IBitIndex> toAccelerateEncapsulation(List<IBitIndex> workers,Accelerater accelerate){
		List<IBitIndex> r = new ArrayList<>();
		for(IBitIndex worker:workers) {
			r.add( new AcceleraterBitIndex(accelerate, worker) );
		}
		return r;
	}
	public static List<IBitIndex> unpacke(List<IBitIndex> workers){
		List<IBitIndex> r = new ArrayList<>();
		for(IBitIndex e:workers) {
			r.add( ((AcceleraterBitIndex)e).getWorker() );
		}
		return r;
	}

	
	public static void initer(String dataPath) throws Exception {
		DBMeta dbMeta = new DBMeta();
		dbMeta.setName("xiegh");
		
		dbMeta.setDataPath(dataPath);
		
		TableMeta phs = new TableMeta();
		phs.setName("t_ph");
		phs.setSource(80);
		dbMeta.add("t_ph", phs);
		
		phs.addColumn(PhoneIndex.class, "phone"  );
		phs.addColumn(UNumberIndex.class, "operator" ,2 );
		phs.addColumn(FLOATIndex.class, "price" ,24,100,0 );
		phs.addColumn(BooleanIndex.class, "ismy"  );
		phs.addColumn(UNumberIndex.class, "city" ,14 );
		phs.addColumn(DateIndex.class, "create_time" );//35
		
		JSONObject json = (JSONObject)JSONObject.toJSON(dbMeta);
		System.out.println(json.toJSONString());
		dbMeta = JSONObject.parseObject(json.toJSONString(), DBMeta.class);
		IXyDB db = new XyDB();
		db.init(dbMeta);
		db.load();
		
		SeachContext.initDB(db);
	}
	
	
	
	
	public static void main(String[] args) {
		
		//String sql = "select phone,price,create_time from T_PH where   Phone_seach(phone,Contains,'999') price>2000f and ismy=true and city>3 order by price  limit 12000000,10";
		String sql = "/*parallel(11)*/selet ax from tx ";
		CodePointCharStream cpcs = CharStreams.fromString( sql);
        //用 cpcs 构造词法分析器 lexer，词法分析的作用是产生记号
        BitQLexer lexer = new BitQLexer(cpcs);
        
       // CommonTokenStream tokens = new CommonTokenStream(lexer,"comment");
        
        //用词法分析器 lexer 构造一个记号流 tokens
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        		//Token.HIDDEN_CHANNEL);
        //再使用 tokens 构造语法分析器 parser,至此已经完成词法分析和语法分析的准备工作
        
//        List<Token> ts = tokens.getTokens();
//        
//        for(Token t:ts) {
//        	System.out.println(t.getText());
//        }
        
        BitQParser parser = new BitQParser(tokens);
        //最终调用语法分析器的规则 prog，完成对表达式的验证
        
       
        ParseTree tree = parser.query();
        
        ParseTreeWalker walker = new ParseTreeWalker();
		BitMaxSourceListenerImpl evalByListener = new BitMaxSourceListenerImpl();
		walker.walk(evalByListener, tree);
        
        
        System.out.println(tree.toString());
        System.out.println(lexer.getParallel());
        
	}
	
	
	
}
class LLL extends BitQBaseListener{
	
}