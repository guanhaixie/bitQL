package com.xuanyue.db.xuan;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.xuanyue.db.xuan.antlr.impl.QueryRequest;
import com.xuanyue.db.xuan.antlr.impl.QueryResult;
import com.xuanyue.db.xuan.core.db.DBMeta;
import com.xuanyue.db.xuan.core.db.IXyDB;
import com.xuanyue.db.xuan.core.db.TableMeta;
import com.xuanyue.db.xuan.core.db.XyDB;
import com.xuanyue.db.xuan.core.index.DateIndex;
import com.xuanyue.db.xuan.core.index.ListUnumberIndex;
import com.xuanyue.db.xuan.core.index.UNumberIndex;
import com.xuanyue.db.xuan.core.table.IXyTable;

public class Hello {

	
	static void init(int num,String path) throws Exception{
		DBMeta dbMeta = new DBMeta();
		dbMeta.setName("zwy");
//		System.out.print("zwy [datapath]>");
		dbMeta.setDataPath(path);
		
		
		String tableName = "t_biz";
		
		TableMeta phs = new TableMeta();
		phs.setName(tableName);
		phs.setSource(80);
		dbMeta.add(tableName, phs);
		
		phs.addColumn(UNumberIndex.class, "source_no",35  );//源发号码  范围： 0-34,359,738,368
		phs.addColumn(ListUnumberIndex.class, "business_labels" ,10,12 );//营销标签 10个 整数  范围：  0-4095之间的数
		phs.addColumn(UNumberIndex.class, "province" ,6 );//省  范围：  0-63 直接
		phs.addColumn(UNumberIndex.class, "city" ,14 );//城市   范围：   0-16383之间
		phs.addColumn(UNumberIndex.class, "operator" ,4 );//运营商    范围：  0-16之间
		phs.addColumn(ListUnumberIndex.class, "game_labels" ,10,16 );//游戏标签    范围： 0-65535
		phs.addColumn(DateIndex.class, "create_time" );//创建时间
		
		IXyDB db = new XyDB();
		db.init(dbMeta);
		db.load();
		
		IXyTable table = db.getTable(tableName);
		//table.flush(10000000);
		table.toBatchLoadMode(String.format("%s/%s/%s",dbMeta.getDataPath(),dbMeta.getName(),  tableName));
		
		//模拟数据
		Map<String,Object> record = new HashMap<>();
		record.put("source_no", 13521192609l);
		
		List<Long> bl = new ArrayList<>();
		bl.add(1l);
		bl.add(2l);
		bl.add(3l);
		bl.add(4l);
		bl.add(6l);
		bl.add(31l);
		bl.add(33l);
		record.put("business_labels", bl);
		
		record.put("province", 1l);
		record.put("city", 112l);
		record.put("operator", 1l);
		List<Long> gl = new ArrayList<>();
		gl.add(1l);
		gl.add(2l);
		gl.add(3l);
		gl.add(4l);
		gl.add(6l);
		gl.add(31l);
		gl.add(33l);
		record.put("game_labels", gl);
		record.put("create_time", new Date());
		
		long s = System.currentTimeMillis();
		for(int i=0;i<num;i++) {//插入num次
			record.put("source_no", (long)record.get( "source_no" )+1l );//每次 source_no加1
			table.insertInto(record);
			if(i%100000==99999) {
				System.out.println(i);
			}
		}
		//table.insertInto(record);//插入
		table.save(String.format("%s/%s/%s",dbMeta.getDataPath(),dbMeta.getName(),  tableName));
		System.out.println(System.currentTimeMillis()-s);
		System.out.println(table.read(2));
	}
	static void test(String path)throws Exception {
		DBMeta dbMeta = new DBMeta();
		dbMeta.setName("zwy");
//		System.out.print("zwy [datapath]>");
		dbMeta.setDataPath(path);
		
		
		String tableName = "t_biz";
		
		TableMeta phs = new TableMeta();
		phs.setName(tableName);
		phs.setSource(80);
		dbMeta.add(tableName, phs);
		
		phs.addColumn(UNumberIndex.class, "source_no" ,44 );
		phs.addColumn(ListUnumberIndex.class, "business_labels" ,10,12 );
		phs.addColumn(UNumberIndex.class, "province" ,6 );
		phs.addColumn(UNumberIndex.class, "city" ,14 );
		phs.addColumn(UNumberIndex.class, "operator" ,4 );
		phs.addColumn(ListUnumberIndex.class, "game_labels" ,10,16 );
		phs.addColumn(DateIndex.class, "create_time" );//35
		
		IXyDB db = new XyDB();
		db.init(dbMeta);
		db.load();
		SeachContext.initDB(db);
		
		QueryRequest re = new QueryRequest();
		Scanner sc = new Scanner(System.in);
		String sql = null;
		while(true) {
			System.out.print("xiegh [sql]>");
			sql = sc.nextLine();
			if(sql==null||sql.length()==0||sql.trim().length()==0) {
				continue;
			}else {
				break;
			}
		}
		sc.close();
		//re.setSql("select source_no,province,business_labels from t_biz where (business_labels Contains 1 or business_labels contains 2) and city = 112 and source_no < 13521193609l");
		re.setSql(sql);
		System.out.println(sql);
		QueryResult x = SeachContext.query(re,false);
		System.out.println();
		System.out.println(x.getCount());
		System.out.println(x.getFl());
		x.getResult().forEach( e->{
			System.out.println(e);
		});
	}
	public static void main(String[] args) throws Exception {
		//test();
		long s = System.currentTimeMillis();
		//init(100000000, "d:/data2");
		test("d:/data");
		long e = System.currentTimeMillis();
		System.out.println(e-s);
	}

}
