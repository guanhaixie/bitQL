# bitQL

#### 介绍
BitQL是一个内存检索引擎，设计理念是数据存储即索引。首先如果是boolean型数据，则直接用一个bit向量，N个数据，在内存中存放，最多浪费空间不超过8个byte(一个long整形的空间)。其他数据类型都是由一系列的相同维度的bit向量组成。例如把long看出由64个bit向量组成。 

#### 软件架构
![架构](https://images.gitee.com/uploads/images/2020/0626/134456_a7d3fa57_5337335.png "屏幕截图.png")


#### 安装教程

本工程使用maven编译，都是都是引用的常见第三jar,编译使用即可

环境：jdk8 maven 

git clone https://gitee.com/xiegh517/bit-ql.git

cd bit-sql

cd xuanYue-seach

mvn install


#### 使用说明
1. 创建数据库的元数据和创建表t_ph

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
2.创建库实例，并造一个亿的测试数据

		IXyDB db = new XyDB();
		db.init(dbMeta);
		db.load();
		String tableName = "t_ph";
		IXyTable table = db.getTable(tableName);
		table.flush(100000000);
		Map<String,Object> r = new HashMap<>();
		for(int i=0;i<100000000;i++) {
			table.insertInto(getPh(r));//随机生成一行测试数据并插入表中，getPh方法体在后边给出
			if(i%10000==9999) {
				System.out.println(i);
			}
		}
		table.save(String.format("%s/%s/%s",dbMeta.getDataPath(),dbMeta.getName(),  tableName));
3.查询

		SeachContext.initDB(db);//初始化检索引擎的上下文
        QueryRequest re = new QueryRequest();
        //深度分页，取1200000页
        re.setSql("select phone,price,create_time "+
                          "from T_PH where price>2000f and ismy=true and city>3 "
                           "order by price limit 12000000,10");		

4.执行查询和打印结果

		QueryResult x = null;
		long now = System.currentTimeMillis();
		for(int i=0;i<100;i++) {//执行一百次
			x = SeachContext.query(re);
		}
		System.out.println(x.getFl());
		x.getResult().forEach( e->{
			System.out.println(e);
		});
		System.out.println(x.getCount());
		float xd = System.currentTimeMillis()-now;
		System.out.println(xd/100);

5.测试数据构造方法

		static float getPrice() {//随机生成价格
		    float v = random(1000000);
			return v/100;
		}
		static String getPhStr() {//随机生成手机号码
			long r = random(130,199)*100000000l+random(0,100000000);
			return String.format("%s", r);
		}
		
		static int random(int max) {//生成0到max的随机数
			int min=0;
			return (int) (Math.random()*(max-min)+min);
		}
		static int random(int min,int max) {//生成min到max的随机数
			return (int) (Math.random()*(max-min)+min);
		}
		static Map<String,Object> getPh(Map<String,Object> r){//随机生成一行测试数据
			r.put("phone", getPhStr());
			r.put("operator", random(3));
			r.put("price", getPrice());
			r.put("ismy", random(100)<=30);
			r.put("city", random(1000)+1);
			r.put("create_time", new Date(System.currentTimeMillis()-1000l*random(3600*24*365*10)));
			return r;
		}
		

#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
