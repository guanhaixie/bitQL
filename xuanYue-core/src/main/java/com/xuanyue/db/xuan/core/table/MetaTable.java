package com.xuanyue.db.xuan.core.table;

import java.util.HashMap;
import java.util.Map;

import com.xuanyue.db.xuan.core.index.BooleanIndex;
import com.xuanyue.db.xuan.core.index.DateIndex;
import com.xuanyue.db.xuan.core.index.FLOATIndex;
import com.xuanyue.db.xuan.core.index.ListUnumberIndex;
import com.xuanyue.db.xuan.core.index.MapBooleanIndex;
import com.xuanyue.db.xuan.core.index.PhoneIndex;
import com.xuanyue.db.xuan.core.index.UNumberIndex;
import com.xuanyue.db.xuan.core.table.groupby.IAggrElement;
import com.xuanyue.db.xuan.core.tools.SafeManager;

public class MetaTable {
	public static SafeManager<IAggrElement> managerShort = new SafeManager<>(new AggrElementFactory(4),10000);// = new SafeManager<AggrEmement>();
	public static SafeManager<IAggrElement> managerFloat = new SafeManager<>(new AggrElementFactory(2),10000);// = new SafeManager<AggrEmement>();
	
	private static Map<String,IXyTable> tables = new HashMap<>();
	private static String path;
	
	public static void setBasePath(String path) {
		MetaTable.path = path;
	}
	public static IXyTable getTable(String name) throws Exception {
		IXyTable table = tables.get(name);
		if(table==null) {
			synchronized (MetaTable.class) {
				table = tables.get(name);
				if(table==null) {
					if("phone".equals(name.toLowerCase())) {
						 table = addPhoneTable(); 
					}else if("ends8_dir".equals(name.toLowerCase())) {
						table = new XyTable(10,"ends8_dir");
						table.addColumn("key", new UNumberIndex(32));//号码
					}else if("middle9_dir".equals(name.toLowerCase())) {
						table = new XyTable(10,"middle9_dir");
						table.addColumn("key", new UNumberIndex(36));//号码
					}else {
						throw new Exception("table "+name+" is not exists");
					}
					table.load(String.format("%s/%s", path,name));
					tables.put(name, table);
				}
				return table;
			}
		}
		return table;
	}
	
	public static XyTable addPhoneTable() {
		XyTable table = new XyTable("phone");
		table.addColumn("C_Number", new PhoneIndex());//号码
		table.addColumn("C_NumberType", new BooleanIndex());//号码类型，1表示手机号，0：座机号
		table.addColumn("C_Operator", new UNumberIndex(2));//运营商  2为移动，3为联通，4为电信 0其他
		table.addColumn("C_Utiid", new UNumberIndex(20));//会员ID
		table.addColumn("C_MarketPrice",new FLOATIndex(24,100,0));//市场价
		table.addColumn("C_FloorPrice",new FLOATIndex(24,100,0));//阶梯价
		table.addColumn("C_BillPrice",new FLOATIndex(24,100,0));//话费
		table.addColumn("C_MinConsumptionPrice",new FLOATIndex(24,100,0));//月租
		table.addColumn("C_proId", new UNumberIndex(6));//省份ID
		table.addColumn("C_cityId", new UNumberIndex(14));//城市ID
		table.addColumn("C_Setmeals", new ListUnumberIndex(10,20));//所属套餐
		//table.addColumn("C_CardGmxz", new UNumberIndex(10));//购买须知
		table.addColumn("C_IsMy", new BooleanIndex());//是否自售
		table.addColumn("C_IsEnable", new BooleanIndex());//是否启用，1为启用，0为未启用
		table.addColumn("C_IsHome", new BooleanIndex());//是否置顶，1为置顶，0为不置顶
		table.addColumn("C_IsNominate", new BooleanIndex());//是否推荐，1为推荐，0为不推荐
		table.addColumn("C_IsSpecial", new BooleanIndex());//是否特价，等于1为特价靓号
		table.addColumn("C_IsShelves", new UNumberIndex(2));//是否上下价，1为上架，2为已预订，0为下架
		table.addColumn("C_IsSuperGood", new BooleanIndex());//是否是超级靓号，等于1为超级靓号
		table.addColumn("C_IsCostEffective", new BooleanIndex());//是否是高性价比，等于1为高性价比
		
		
		Map<String,String> name2Id = new HashMap<>();
		name2Id.put("ABC**ABC","1"    );
		name2Id.put("AABBAABB","2"    );
		name2Id.put("ABC","3"         );
		name2Id.put("AABBCCDD","4"    );
		name2Id.put("ABABAB","5"      );
		name2Id.put("AABBCC*","6"     );
		name2Id.put("ABBABB","7"      );
		name2Id.put("AAABAAAB","8"    );
		name2Id.put("AABBB","9"       );
		name2Id.put("ABCDABCD***","10");
		name2Id.put("ABABCCDD","11"   );
		name2Id.put("ABAB","12"       );
		name2Id.put("AAABBB","13"     );
		name2Id.put("ABCD","14"       );
		name2Id.put("AAAABB","15"     );
		name2Id.put("AABBCC","16"     );
		name2Id.put("AAAA","17"       );
		name2Id.put("AAAB","18"       );
		name2Id.put("AA","19"         );
		name2Id.put("ABABAB*","20"    );
		name2Id.put("AAA","21"        );
		name2Id.put("ABCDCBA","22"    );
		name2Id.put("AAA*BBB*","23"   );
		name2Id.put("ABCABC","24"     );
		name2Id.put("AABCC","25"      );
		name2Id.put("AAAAB","26"      );
		name2Id.put("ABABCDCD","27"   );
		name2Id.put("ABCDE","28"      );
		name2Id.put("ABCBA","29"      );
		name2Id.put("AAAAA","30"      );
		name2Id.put("*ABC*ABC","31"   );
		name2Id.put("AAAAAB","32"     );
		name2Id.put("ABCDABCD","33"   );
		name2Id.put("AAAAAA","34"     );
		name2Id.put("AABB","35"       );
		name2Id.put("ABBA","36"       );
		name2Id.put("AAAABBBB","37"   );
		name2Id.put("AAABB","38"      );
		name2Id.put("ABBB","39"       );
		table.addColumn("C_hmgz", new MapBooleanIndex(name2Id));//号码规律
		
		Map<String,String> suzi2Id = new HashMap<>();
		suzi2Id.put("0", "0");
		suzi2Id.put("1", "1");
		suzi2Id.put("2", "2");
		suzi2Id.put("3", "3");
		suzi2Id.put("4", "4");
		suzi2Id.put("5", "5");
		suzi2Id.put("6", "6");
		suzi2Id.put("7", "7");
		suzi2Id.put("8", "8");
		suzi2Id.put("9", "9");
		table.addColumn("C_suzi", new MapBooleanIndex(suzi2Id));//含有数字多
		
		Map<String,String> yy2Id = new HashMap<>();
		for(int i=0;i<100;i++) {
			yy2Id.put("yy"+i, ""+i);
		}
		table.addColumn("C_yuyi", new MapBooleanIndex(yy2Id));//寓意
		
		table.addColumn("C_sr8", new BooleanIndex());//后8位日期号码  yyyyMMdd
		table.addColumn("C_sr6", new BooleanIndex());//后6位日期号码        yyMMdd
		
		Map<String,String> g2Id = new HashMap<>();
		g2Id.put("0", "0");
		g2Id.put("1", "1");
		g2Id.put("2", "2");
		g2Id.put("3", "3");
		g2Id.put("4", "4");
		g2Id.put("5", "5");
		table.addColumn("C_isSeckill",new BooleanIndex());//秒杀
		table.addColumn("C_mHmgz", new MapBooleanIndex(name2Id));//号码中间规律
		table.addColumn("C_group", new MapBooleanIndex(g2Id));//分组
		table.addColumn("C_hit", new UNumberIndex(20));//点击率
		table.addColumn("C_update_date",new DateIndex());//修改时间
		table.addColumn("C_create_date",new DateIndex());//创建时间
		return table;
		
	}
	
	
	
	
	static int getRandomInt(int min,int max) {
		return (int)(Math.random()*(max-min)+min);
	}
	
	public static void main(String[] args) {
		Map<Long,Integer> dir = new HashMap<>();
		for(int i=0;i<15000000;i++) {
			dir.put((long)i, i);
		}
		System.out.println("*****************");
		long n = System.currentTimeMillis();
		for(int i=0;i<1000;i++) {
			dir.get(14000000l);
		}
		System.out.println(System.currentTimeMillis()-n);
	}
}
