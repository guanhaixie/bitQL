# bitQL

#### 介绍
BitQL是一个内存检索引擎，设计理念是数据存储即索引。首先如果是boolean型数据，则直接用一个bit向量，N个数据，在内存中存放，最多浪费空间不超过8个byte(一个long整形的空间)。其他数据类型都是由一系列的相同维度的bit向量组成。例如把long看出由64个bit向量组成。 

#### 软件架构
![架构](https://images.gitee.com/uploads/images/2020/0626/134456_a7d3fa57_5337335.png "屏幕截图.png")


#### 安装教程

本工程使用maven编译，都是都是引用的常见第三jar,编译使用即可

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
			table.insertInto(getPh(r));
			if(i%10000==9999) {
				System.out.println(i);
			}
		}
		table.save(String.format("%s/%s/%s",dbMeta.getDataPath(),dbMeta.getName(),  tableName));
		



#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
