package com.xuanyue.db.xuan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import com.xuanyue.db.xuan.antlr.BitQLexer;
import com.xuanyue.db.xuan.antlr.BitQParser;
import com.xuanyue.db.xuan.antlr.impl.BitMaxSourceListenerImpl;
import com.xuanyue.db.xuan.antlr.impl.BitQListenerImpl;
import com.xuanyue.db.xuan.antlr.impl.QueryRequest;
import com.xuanyue.db.xuan.antlr.impl.QueryResult;
import com.xuanyue.db.xuan.core.db.IXyDB;
import com.xuanyue.db.xuan.core.exception.IndexException;
import com.xuanyue.db.xuan.core.index.AcceleraterBitIndex;
import com.xuanyue.db.xuan.core.table.IBitIndex;
import com.xuanyue.db.xuan.core.table.IXyTable;
import com.xuanyue.db.xuan.core.task.X2yThreadPoolExecutor;
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
	private static ArrayBlockingQueue<X2yThreadPoolExecutor> queryQ = new ArrayBlockingQueue<X2yThreadPoolExecutor>(10);
	
	private static Map<String,ParseTree> treeCache = Collections.synchronizedMap(new LruCache<String,ParseTree>(100));
	private static String treeLock = "treeLock";
	
	static {
		queryQ.add(new X2yThreadPoolExecutor(5, 16, 20, TimeUnit.SECONDS, 30 ));
	}
	
	private static ParseTree get(String sql) {
		ParseTree tree = treeCache.get(sql);
		if(tree == null) {
			synchronized (treeLock) {
				tree = treeCache.get(sql);
				if(tree == null) {
					CodePointCharStream cpcs = CharStreams.fromString( sql );
			        //用 cpcs 构造词法分析器 lexer，词法分析的作用是产生记号
			        BitQLexer lexer = new BitQLexer(cpcs);
			        //用词法分析器 lexer 构造一个记号流 tokens
			        CommonTokenStream tokens = new CommonTokenStream(lexer);
			        //再使用 tokens 构造语法分析器 parser,至此已经完成词法分析和语法分析的准备工作
			        BitQParser parser = new BitQParser(tokens);
			        //最终调用语法分析器的规则 prog，完成对表达式的验证
			        tree = parser.query();
			        treeCache.put(sql, tree);
				}
			}
		}
		return tree;
	}
	
	public static QueryResult query(QueryRequest req,boolean accelerate) {
		ParseTree tree = get(req.getSql());
		ParseTreeWalker walker = new ParseTreeWalker();
		BitMaxSourceListenerImpl evalByListener = new BitMaxSourceListenerImpl();
		walker.walk(evalByListener, tree);
		BitQListenerImpl l2 = new BitQListenerImpl(evalByListener.getMaxSource());
		l2.isAccelerate(accelerate);
		walker.walk(l2, tree);
		
		QueryResult r = new QueryResult();
		
		r.setCount(l2.getCount());
		r.setFl( l2.getFieldNames() );
		r.setTypes( l2.getTypes() );
		r.setResult( l2.getData() );
		
		
		return r;
		
	}
	
	public static void initDB(IXyDB db) throws Exception{
		SeachContext.db = db;
	}
	public static IXyTable getTable(String name) {
		return db.getTable(name);
	}
	public static X2yThreadPoolExecutor getAccelerate() throws InterruptedException {
		return queryQ.take();
	}
	public static void returnAccelerate(X2yThreadPoolExecutor acc) throws InterruptedException {
		queryQ.put(acc);
	}
	public static List<IBitIndex> toAccelerateEncapsulation(List<IBitIndex> workers,X2yThreadPoolExecutor accelerate){
		List<IBitIndex> r = new ArrayList<>();
		for(IBitIndex worker:workers) {
//			System.out.println("accelerate num:"+accelerate.getActiveCount());
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
}
