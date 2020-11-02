# bitQL

#### 介绍
BitQL是一个内存检索引擎，设计理念是数据存储即索引，BitQ追求的是亿级别数据量的查询排序和深度随机分页的极限解决方，本组件和redis相比较有更强的数据检索功能，但是本组件不会和redis竞争KV的场景，也不是为这个场景设计的。和ES的比较的话，ES本身是回避深度分页和随机分页问题的，这方面本组件有绝对的优势，但是本组件至少目前没有倒排索引的封装，未来会尝试加入倒排索引的功能。我们希望本组件成为您业务的逻辑整合中心，业务的逻辑关联查询查询放到一张表中

#### 软件架构
![架构](https://images.gitee.com/uploads/images/2020/0626/134456_a7d3fa57_5337335.png "屏幕截图.png")

整个架构都构建在bit向量上，借用了传统关系型数据的库表和数据列的概念。最大的改进和创新在意对数据表垂直切分的极限做法，即数据行和数据列对齐后，最小单元就是一个bit向量。需要这样看待数据：一行数据假设
有若干列组成,每列有不同个数的bit位构成，一行共有m个bit位，有n行数据，形成一个n行m列的矩阵，一张表就是由m个n维bit向量组成的，一列数据对应这个向量组的一个子向量组。这样做的一个好处在于，数据按照自然顺序插入后，天然具有二叉排序树的性质，bit向量的底层是long数组,long提供高效的bit位统计函数，所以在排序和深度随机分页方便就会有天然的优势。

不过数据被拆散到了bit位的程度也带来了一个劣势，就是不适合做聚合函数，因为数据聚合时，大多需要先把bit位还原成原始数据，然后再聚合计算。所以当前还没有做聚合函数的计划。本数据检索引擎也不是面向数据分析儿设计的。

#### 特点

声明：在说明bitQL特点时，不能把它说成无所不能的神物，就像雨水，适量了就叫润物细无声，但是太大就成了洪灾，所以最终还需搞技术决策的诸君做出符合自己“国情”的决策。

 **1. 数据表垂直松散性最强**  ：  即一条数据的每个bit位都是相互独立存放的。

1.1. **优点**：

* 表的字段可以自由增删，甚至可以灵活扩展字段大小 

* 字段的截断无性能损耗  （这部分的API目前尚不具备）    

1.2 **坑点**：

* 数据模型的灵活多变可以给开发工作带来便利，但是如果您把这种灵活性当成您业务逻辑的一部分以应对复杂多变的业务，则需要您先考虑其后果。bitQL认为其本身会运行在稳定的数据模型上，它只是拥抱软件开发中的模型变化。

**2. 天然的二叉排序树**     每个bit向量本来就是相互独立的，所以理论上可以任意打散然后组合起来形成二叉排序树。N表示bit向量的个数，计算量为     1到2N次   bit向量的逻辑运算，所以 排序的复杂度 O(N) 

2.1. **优点**：   

* 字段截断，然后组合排序都有数据库索引的效果    

2.2. **坑点**：

* 未知 

**3. 单线程模型** 当前的查询请求都是单线程执行

3.1. **优点**： 

* 简单可靠

3.2. **坑点**：

* 响应时间随查询条件多少成线性关系，无加速手段

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

 **1亿条数据随机生成并写入库中，耗时 780秒**  

2.测试报告

 ** 测试数据量：1亿** 
 
 **注意：本报告统计的时间包含返回分页结果和统计记录总数两个部分的总时间** 

![输入图片说明](https://images.gitee.com/uploads/images/2020/1022/150748_cc515adc_5337335.png "屏幕截图.png")

3.测试sql的命令行

   cd xuanYue-seach/target/classes
   
   java -Djava.ext.dirs=../lib com.xuanyue.db.xuan.Main
   
          控制台输入   test   然后输入数据目录    e:/data2  然后输入sql  然后输入执行次数  

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
