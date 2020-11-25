package com.xuanyue.db.xuan.antlr.impl;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.xuanyue.db.xuan.SeachContext;
import com.xuanyue.db.xuan.antlr.BitQLexer;
import com.xuanyue.db.xuan.antlr.BitQParser;
import com.xuanyue.db.xuan.core.db.DBMeta;
import com.xuanyue.db.xuan.core.db.IXyDB;
import com.xuanyue.db.xuan.core.db.TableMeta;
import com.xuanyue.db.xuan.core.db.XyDB;
import com.xuanyue.db.xuan.core.index.BooleanIndex;
import com.xuanyue.db.xuan.core.index.DateIndex;
import com.xuanyue.db.xuan.core.index.FLOATIndex;
import com.xuanyue.db.xuan.core.index.PhoneIndex;
import com.xuanyue.db.xuan.core.index.UNumberIndex;

public class ProofreaderListenerImplTest {

	static {
		try {
			DBMeta dbMeta = new DBMeta();
			dbMeta.setName("xiegh");
			
			dbMeta.setDataPath("e:/data");
			
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
			SeachContext.initDB(db);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 检查 关键字 select
	 * @throws Exception
	 */
	//@Test
	public void testSelect() throws Exception {
		String sql = "/*parallel(11)*/selet ax from tx ";
		CodePointCharStream cpcs = CharStreams.fromString( sql);
        BitQLexer lexer = new BitQLexer(cpcs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        BitQParser parser = new BitQParser(tokens);
        
        parser.removeErrorListeners();
        ERRORCheckor check = new ERRORCheckor(true);
        parser.addErrorListener(check);
        
        try {
			parser.query();
			
			if(!check.isSuccess()) {
				System.out.println("success>> 关键字select 错误时可以检查到");
				return;
        	}
		} catch (Exception e) {
			
		}
        throw new Exception("语法合法性问题，select 关键 不合法却没有抛出异常 ");
	}
	@Test
	public void testNoColumn() throws Exception{
		ProofreaderListenerImpl tar = new ProofreaderListenerImpl();
		
		String sql = "select ax from t_ph";
		CodePointCharStream cpcs = CharStreams.fromString( sql);
        BitQLexer lexer = new BitQLexer(cpcs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        BitQParser parser = new BitQParser(tokens);
        parser.removeErrorListeners();
        ERRORCheckor check = new ERRORCheckor(true);
        parser.addErrorListener(check);
        try {
        	ParseTree tree = parser.query();
        	if(!check.isSuccess()) {
        		throw new Exception("语法错误");
        	}
        	ParseTreeWalker walker = new ParseTreeWalker();
        	walker.walk(tar, tree);
		} catch (Exception e) {
			if(e.getMessage().equals("column ax is not exists")) {
				System.out.println("success>>查询结果字段不存在时  可以检查到异常");
				return;
			}
		}
        throw new Exception("语法合法性问题，查询结果字段不存在却没有抛出异常 ");
	}
	/**
	 * 表不存在
	 * @throws Exception
	 */
	//@Test
	public void testNoTable() throws Exception{
		ProofreaderListenerImpl tar = new ProofreaderListenerImpl();
		
		String sql = "select ax from tx ";
		CodePointCharStream cpcs = CharStreams.fromString( sql);
        BitQLexer lexer = new BitQLexer(cpcs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        BitQParser parser = new BitQParser(tokens);
        parser.removeErrorListeners();
        ERRORCheckor check = new ERRORCheckor(true);
        parser.addErrorListener(check);
        try {
        	ParseTree tree = parser.query();
        	if(!check.isSuccess()) {
        		throw new Exception("语法错误");
        	}
        	ParseTreeWalker walker = new ParseTreeWalker();
        	walker.walk(tar, tree);
		} catch (Exception e) {
			if(e.getMessage().equals("table tx is not exists")) {
				System.out.println("success>>表不存在时 可以检查到异常");
				return;
			}
		}
        throw new Exception("语法合法性问题，表不存在却没有抛出异常 ");
	}
	/**
	 * 检查where
	 * @throws Exception
	 */
	@Test
	public void maxSourceWhere() throws Exception{
		ProofreaderListenerImpl tar = new ProofreaderListenerImpl();
		
		String sql = "select phone,price,create_time from T_PH where   Phone_seach(phone,Contains,'999') price>2000f and ismy=true and city>3 order by price  limit 12000000,10";
		CodePointCharStream cpcs = CharStreams.fromString( sql);
        BitQLexer lexer = new BitQLexer(cpcs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        BitQParser parser = new BitQParser(tokens);
        parser.removeErrorListeners();
        ERRORCheckor check = new ERRORCheckor(true);
        parser.addErrorListener(check);
        try {
        	ParseTree tree = parser.query();
        	if(!check.isSuccess()) {
        		throw new Exception("语法合法性问题 ");
        	}
        	ParseTreeWalker walker = new ParseTreeWalker();
        	walker.walk(tar, tree);
        	System.out.println(tar.getMaxSource());
		} catch (Exception e) {
			if(e.getMessage().equals("ERROR at where : not has expr")) {
				System.out.println("success>>查询结果字段不存在时  可以检查到异常");
				return;
			}
			e.printStackTrace();
		}
	}
	
	public void testWhere() throws Exception{
		ProofreaderListenerImpl tar = new ProofreaderListenerImpl();
		
		String sql = "select  phone from t_ph where ";
		CodePointCharStream cpcs = CharStreams.fromString( sql);
        BitQLexer lexer = new BitQLexer(cpcs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        BitQParser parser = new BitQParser(tokens);
        parser.removeErrorListeners();
        ERRORCheckor check = new ERRORCheckor(true);
        parser.addErrorListener(check);
        try {
        	ParseTree tree = parser.query();
        	if(!check.isSuccess()) {
        		return;
        	}
        	ParseTreeWalker walker = new ParseTreeWalker();
        	walker.walk(tar, tree);
        	
		} catch (Exception e) {
			if(e.getMessage().equals("ERROR at where : not has expr")) {
				System.out.println("success>>查询结果字段不存在时  可以检查到异常");
				return;
			}
			e.printStackTrace();
		}
        throw new Exception("语法合法性问题，where 没有查询条件却没有抛出异常 ");
	}
	
}
