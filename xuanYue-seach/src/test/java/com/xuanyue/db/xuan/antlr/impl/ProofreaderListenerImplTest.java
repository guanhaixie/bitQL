package com.xuanyue.db.xuan.antlr.impl;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
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

	private void initDB() throws Exception {
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
	}
	
	
	@Test
	public void testSelect() throws Exception {
		initDB();
		String sql = "/*parallel(11)*/selet ax from tx ";
		CodePointCharStream cpcs = CharStreams.fromString( sql);
        BitQLexer lexer = new BitQLexer(cpcs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        BitQParser parser = new BitQParser(tokens);
        ParseTree tree = parser.query();
	}
}
