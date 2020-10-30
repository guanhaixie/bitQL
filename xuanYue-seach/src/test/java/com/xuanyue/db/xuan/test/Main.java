package com.xuanyue.db.xuan.test;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.alibaba.fastjson.JSONObject;
import com.xuanyue.db.xuan.SeachContext;
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

/**
 * 测试手机号搜索
 * @author 解观海
 * @email guanhaixie@sina.cn
 * @date 2020年10月30日
 *
 */
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
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
//		
		IXyDB db = new XyDB();
		db.init(dbMeta);
		db.load();
		String tableName = "t_ph";
		IXyTable table = db.getTable(tableName);
		Date start = new Date();
		System.out.print("xiegh [随机生成测试数据条数]>");
		int num = Integer.parseInt( sc.nextLine() );
		table.flush(num);
		Map<String,Object> r = new HashMap<>();
		Date end = null;
		System.out.print("xiegh [日志 频率    (条/次)  ]>");
		int av = Integer.parseInt( sc.nextLine() );
		for(int i=0;i<num;i++) {
			table.insertInto(getPh(r));
			if(i%av==av-1) {
				end = new Date();
				System.out.println(String.format("%s from %s to %s [ %s ms ]", i, simpleDateFormat.format(start) ,simpleDateFormat.format(end),(end.getTime()-start.getTime()) )   );
			}
		}
		table.save(String.format("%s/%s/%s",dbMeta.getDataPath(),dbMeta.getName(),  tableName));
		
		end = new Date();
		System.out.println(String.format("%s to %s [ %s ms ]",  simpleDateFormat.format(start) ,simpleDateFormat.format(end),(end.getTime()-start.getTime()) )   );
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
			String sql = null;
			a:while(true) {
				System.out.print("xiegh [sql]>");
				sql = sc.nextLine();
				if(sql==null||sql.length()==0||sql.trim().length()==0) {
					continue a;
				}else {
					break a;
				}
			}
			re.setSql(sql);
			//re.setSql("select phone,price,create_time from T_PH where   Phone_seach(phone,Contains,'999') price>2000f and ismy=true and city>3 order by price  limit 12000000,10;");
//			re.setSql(args[1]);
			QueryResult x = null;
			System.out.println();
			System.out.println(new Date());
			long now = System.currentTimeMillis();
			int times = 0;
			b:while(true) {
				System.out.print("xiegh [times int]>");
				try {
					times = Integer.parseInt( sc.nextLine() );
					break b;
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				}
			}
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
	static void show(Scanner sc) throws Exception{
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
		
		IXyTable t = db.getTable("t_ph");
		while(true) {
			System.out.print("xiegh [datapath]>");
			int rowId = Integer.parseInt(sc.nextLine());
			System.out.println(t.read(rowId));
		}
	}
	public static void main(String[] args) throws Exception {
		System.out.println("v1.1");
		Scanner sc = new Scanner(System.in);
		System.out.print("todo init or test>");
		String op = sc.nextLine() ;
		if("test".equals( op)) {
			test(sc);
		}else if("init".equals( op)){
			init(sc);
		}else if("show".equals( op)){
			show(sc);
		}else if("x".equals( op)) {
			System.out.print("123456");
			Thread.sleep(3000);
			System.out.print("\b \b");
			
			Thread.sleep(7000);
			System.out.println("xxxxxx");
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