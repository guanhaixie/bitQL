package com.xuanyue.db.xuan;

import java.util.Collections;
import java.util.Map;

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
import com.xuanyue.db.xuan.core.db.DBMeta;
import com.xuanyue.db.xuan.core.db.IXyDB;
import com.xuanyue.db.xuan.core.db.XyDB;
import com.xuanyue.db.xuan.core.table.IXyTable;
import com.xuanyue.db.xuan.core.tools.LruCache;

public class SeachContext {

	private static IXyDB db;
	
	private static Map<String,ParseTree> treeCache = Collections.synchronizedMap(new LruCache<String,ParseTree>(100));
	private static ParseTree get(String sql) {
		ParseTree tree = treeCache.get(sql);
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
		return tree;
	}
	
	public static QueryResult query(QueryRequest req) {
		ParseTree tree = get(req.getSql());
		ParseTreeWalker walker = new ParseTreeWalker();
		BitMaxSourceListenerImpl evalByListener = new BitMaxSourceListenerImpl();
		walker.walk(evalByListener, tree);
		BitQListenerImpl l2 = new BitQListenerImpl(evalByListener.getMaxSource());
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
}
