package com.xuanyue.db.xuan;


 
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.alibaba.fastjson.JSONObject;
import com.xuanyue.db.xuan.antlr.impl.QueryRequest;
import com.xuanyue.db.xuan.antlr.impl.QueryResult;
import com.xuanyue.db.xuan.core.db.DBMeta;
import com.xuanyue.db.xuan.core.db.IXyDB;
import com.xuanyue.db.xuan.core.db.TableMeta;
import com.xuanyue.db.xuan.core.db.XyDB;
import com.xuanyue.db.xuan.core.index.BooleanIndex;
import com.xuanyue.db.xuan.core.index.DateIndex;
import com.xuanyue.db.xuan.core.index.FLOATIndex;
import com.xuanyue.db.xuan.core.index.PhoneIndex;
import com.xuanyue.db.xuan.core.index.UNumberIndex;
import com.xuanyue.db.xuan.core.table.IXyTable;


public class Main {
	static void init(Scanner sc) throws Exception{
		DBMeta dbMeta = new DBMeta();
		dbMeta.setName("xiegh");
		System.out.print("xiegh [datapath]>");
		dbMeta.setDataPath(sc.nextLine());
		
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
		
//		JSONObject json = (JSONObject)JSONObject.toJSON(dbMeta);
//		System.out.println(json.toJSONString());
		
//		
		IXyDB db = new XyDB();
		db.init(dbMeta);
		db.load();
		String tableName = "t_ph";
		IXyTable table = db.getTable(tableName);
		System.out.print("xiegh [init number]>");
		int num = Integer.parseInt( sc.nextLine() );
		table.flush(num);
		Map<String,Object> r = new HashMap<>();
		for(int i=0;i<num;i++) {
			table.insertInto(getPh(r));
			if(i%10000==9999) {
				System.out.println(i);
			}
		}
		table.save(String.format("%s/%s/%s",dbMeta.getDataPath(),dbMeta.getName(),  tableName));
		
		
	}
	static void test(Scanner sc) throws Exception{
		DBMeta dbMeta = new DBMeta();
		dbMeta.setName("xiegh");
		
		System.out.print("xiegh [datapath]>");
		dbMeta.setDataPath(sc.nextLine());
		
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
		
		while(true) {
			QueryRequest re = new QueryRequest();
			System.out.print("xiegh [sql]>");
			re.setSql(sc.nextLine());
			//re.setSql("select phone,price,create_time from T_PH where price>2000f and ismy=true and city>3 order by price  limit 12000000,10");
//			re.setSql(args[1]);
			QueryResult x = null;
			System.out.println();
			System.out.println(new Date());
			long now = System.currentTimeMillis();
			System.out.print("xiegh [times int]>");
			
			int times = Integer.parseInt( sc.nextLine() );
			for(int i=0;i<times;i++) {
				x = SeachContext.query(re);
			}
			System.out.println(x.getFl());
			x.getResult().forEach( e->{
				System.out.println(e);
			});
			System.out.println(x.getCount());
			long xd = System.currentTimeMillis()-now;
			System.out.println(xd/times);
			System.out.println(new Date());
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.print("todo init or test>");
		if("test".equals( sc.nextLine() )) {
			test(sc);
		}else {
			init(sc);
		}
		
//		table.saveRow(String.format("%s/%s/%s",dbMeta.getDataPath(),dbMeta.getName(),  tableName), id);
		
		
	}
	static Map<String,Object> getPh(Map<String,Object> r){
		r.put("phone", getPhStr());
		r.put("operator", random(3));
		r.put("price", getPrice());
		r.put("ismy", random(100)<=30);
		r.put("city", random(1000)+1);
		r.put("create_time", new Date(System.currentTimeMillis()-1000l*random(3600*24*365*10)));
		return r;
	}
	static float getPrice() {
		float v = random(1000000);
		return v/100;
	}
	static String getPhStr() {
		long r = random(130,199)*100000000l+random(0,100000000);
		return String.format("%s", r);
	}
	
	
	static int random(int max) {
		int min=0;
		return (int) (Math.random()*(max-min)+min);
	}
	static int random(int min,int max) {
		return (int) (Math.random()*(max-min)+min);
	}
}
