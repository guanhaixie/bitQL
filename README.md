# bitQL

#### 介绍
BitQL是一个内存检索引擎，设计理念是数据存储即索引。首先如果是boolean型数据，则直接用一个bit向量，N个数据，在内存中存放，最多浪费空间不超过8个byte(一个long整形的空间)。其他数据类型都是由一系列的相同维度的bit向量组成。例如把long看出由64个bit向量组成。 

#### 软件架构
![架构](https://images.gitee.com/uploads/images/2020/0626/134456_a7d3fa57_5337335.png "屏幕截图.png")

整个架构都构建在bit向量上，借用了传统关系型数据的库表和数据列的概念。最大的改进和创新在意对数据表垂直切分的极限做法，即数据行和数据列对齐后，最小单元就是一个bit向量。需要这样看待数据：一行数据假设
有若干列组成,每列有不同个数的bit位构成，一行共有m个bit位，有n行数据，形成一个n行m列的矩阵，一张表就是由m个n维bit向量组成的，一列数据对应这个向量组的一个子向量组。这样做的一个好处在于，数据按照自然顺序插入后，天然具有二叉排序树的性质，bit向量的底层是long数组,long提供高效的bit位统计函数，所以在排序和深度随机分页方便就会有天然的优势。

不过数据被拆散到了bit位的程度也带来了一个劣势，就是不适合做聚合函数，因为数据聚合时，大多需要先把bit位还原成原始数据，然后再聚合计算。所以当前还没有做聚合函数的计划。本数据检索引擎也不是面向数据分析儿设计的，

#### 安装教程

本工程使用maven编译，都是都是引用的常见第三jar,编译使用即可

环境：jdk8 maven 

git clone https://gitee.com/xiegh517/bit-ql.git

cd bit-sql

cd xuanYue-seach

mvn install

#### 测试

1.  生成测试数据： 在命令行下依次填入   init,e:/data2,100000000,100000  表示初始化数据库，数据目录为e:/data2,随机生成1个亿的测试数据并写入表中，每生成10万条刷新一次提示日志

![测试](https://images.gitee.com/uploads/images/2020/1020/201303_b081a334_5337335.jpeg)

![测试](https://images.gitee.com/uploads/images/2020/1020/202123_8dfe16fd_5337335.jpeg)

1亿条数据随机生成并写入库中，耗时 780秒 

2.测试sql

![测试](https://images.gitee.com/uploads/images/2020/1021/180754_aca8049f_5337335.png)


#### 简单的jvm集成使用说明
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

#### 数据类型

1. PhoneIndex 手机号，并支持8位的固话号码

2. BooleanIndex boolean数据列

3. DateIndex 日期 数据列

4. FLOATIndex

5. IntIndex

6. ListUnumberIndex 无符号整数列表

7. LongIndex 

8. MapBooleanIndex 可以看成是一组BooleanIndex列集合，

9. MapUNumberIndex

10. NumberIndex 有符号整数

11. UIntIndex

12. ULongIndex 

13. UNumberIndex
