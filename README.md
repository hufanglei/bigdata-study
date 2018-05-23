

搭建Hadoop的环境
======================================
一、准备工作
	1、安装Linux、JDK、关闭防火墙、配置主机名
	
	
	解压：tar -zxvf hadoop-2.7.3.tar.gz -C ~/training/
	设置Hadoop的环境变量:  vi ~/.bash_profile
			HADOOP_HOME=/root/training/hadoop-2.7.3
			export HADOOP_HOME

			PATH=$HADOOP_HOME/bin:$HADOOP_HOME/sbin:$PATH
			export PATH
		生效
		source ~/.bash_profile
	
	2、Hadoop的目录结构
	
三种安装模式：

参考讲义的P13页

二、本地模式：一台
	特点：没有HDFS、只能测试MapReduce程序
		  MapReduce处理的是本地Linux的文件数据

	vi hadoop-env.sh
	25 export JAVA_HOME=/root/training/jdk1.8.0_144
	
	测试MapReduce程序：
	1、创建目录  mkdir ~/input
	             
	2、运行
			例子：/root/training/hadoop-2.7.3/share/hadoop/mapreduce/hadoop-mapreduce-examples-2.7.3.jar
			hadoop jar hadoop-mapreduce-examples-2.7.3.jar wordcount ~/input/data.txt ~/output
		  

三、伪分布模式：一台(bigdata111)
	特点：是在单机上，模拟一个分布式的环境
	      具备Hadoop的主要功能
		  
	HDFS: namenode+datanode+secondarynamenode
	Yarn: resourcemanager + nodemanager

	hdfs-site.xml

			原则：一般数据块的冗余度跟数据节点（DataNode）的个数一致；最大不超过3

			<!--表示数据块的冗余度，默认：3-->
			<property>
			   <name>dfs.replication</name>
			   <value>1</value>
			</property>
				
			先不设置
			<!--是否开启HDFS的权限检查，默认true-->
			<property>
			   <name>dfs.permissions</name>
			   <value>false</value>
			</property>
	
	core-site.xml

			<!--配置NameNode地址,9000是RPC通信端口-->
			<property>
			   <name>fs.defaultFS</name>
			   <value>hdfs://bigdata111:9000</value>
			</property>	

			<!--HDFS数据保存在Linux的哪个目录，默认值是Linux的tmp目录-->
			<property>
			   <name>hadoop.tmp.dir</name>
			   <value>/root/training/hadoop-2.7.3/tmp</value>
			</property>	
			
	mapred-site.xml 默认没有 cp mapred-site.xml.template mapred-site.xml

		<!--MR运行的框架-->
		<property>
		   <name>mapreduce.framework.name</name>
		   <value>yarn</value>
		</property>	
		
	yarn-site.xml
	
		<!--Yarn的主节点RM的位置-->
		<property>
		   <name>yarn.resourcemanager.hostname</name>
		   <value>bigdata111</value>
		</property>	

		<!--MapReduce运行方式：shuffle洗牌-->
		<property>
		   <name>yarn.nodemanager.aux-services</name>
		   <value>mapreduce_shuffle</value>
		</property>	
		
	格式化：HDFS（NameNode）
	    hdfs namenode -format
		
		日志：
		 Storage directory /root/training/hadoop-2.7.3/tmp/dfs/name has been successfully formatted.

	启动停止Hadoop的环境
	    start-all.sh
		stop-all.sh
		
	访问：通过Web界面
	     HDFS:  http://192.168.157.111:50070
		 Yarn:  http://192.168.157.111:8088
	
	运行
			例子：/root/training/hadoop-2.7.3/share/hadoop/mapreduce/hadoop-mapreduce-examples-2.7.3.jar
			hadoop jar hadoop-mapreduce-examples-2.7.3.jar wordcount /input/data.txt /output/0407

	（*）一定配置免密码登录：原理、配置
	
四、全分布模式
	特点：正在的分布式环境，用于生产
	做好规划
	
	1、准备工作
		（*）关闭防火墙
		      systemctl stop firewalld.service
			  systemctl disable firewalld.service
			  
		（*）安装JDK
		（*）配置主机名  vi /etc/hosts
				192.168.157.112 bigdata112
				192.168.157.113 bigdata113
				192.168.157.114 bigdata114
				
		（*）配置免密码登录：两两之间的免密码登录
			(1) 每台机器产生自己的公钥和私钥
					ssh-keygen -t rsa
					
			(2) 每台机器把自己的公钥给别人
					ssh-copy-id -i .ssh/id_rsa.pub root@bigdata112
					ssh-copy-id -i .ssh/id_rsa.pub root@bigdata113
					ssh-copy-id -i .ssh/id_rsa.pub root@bigdata114
	
		（*）保证每台机器的时间同步
				如果时间不一样，执行MapReduce程序的时候可能存在问题
	
	2、在主节点上（bigdata112）安装
		(1)解压设置环境变量
		    tar -zxvf hadoop-2.7.3.tar.gz -C ~/training/
			
			设置：112 113 114
			HADOOP_HOME=/root/training/hadoop-2.7.3
			export HADOOP_HOME

			PATH=$HADOOP_HOME/bin:$HADOOP_HOME/sbin:$PATH
			export PATH
			
		(2) 修改配置文件
			hadoop-env.sh	JAVA_HOME	/root/training/jdk1.8.0_144
			hdfs-site.xml
				<!--表示数据块的冗余度，默认：3-->
				<property>
				   <name>dfs.replication</name>
				   <value>2</value>
				</property>
			
			core-site.xml
				<!--配置NameNode地址,9000是RPC通信端口-->
				<property>
				   <name>fs.defaultFS</name>
				   <value>hdfs://bigdata112:9000</value>
				</property>	

				<!--HDFS数据保存在Linux的哪个目录，默认值是Linux的tmp目录-->
				<property>
				   <name>hadoop.tmp.dir</name>
				   <value>/root/training/hadoop-2.7.3/tmp</value>
				</property>				
	
			mapred-site.xml 默认没有 cp mapred-site.xml.template mapred-site.xml

				<!--MR运行的框架-->
				<property>
				   <name>mapreduce.framework.name</name>
				   <value>yarn</value>
				</property>	
	
		yarn-site.xml
	
				<!--Yarn的主节点RM的位置-->
				<property>
				   <name>yarn.resourcemanager.hostname</name>
				   <value>bigdata112</value>
				</property>	

				<!--MapReduce运行方式：shuffle洗牌-->
				<property>
				   <name>yarn.nodemanager.aux-services</name>
				   <value>mapreduce_shuffle</value>
				</property>	
				
		slaves
			bigdata113
			bigdata114
			
		(3) 格式化NameNode： hdfs namenode -format
		(4) 把主节点上配置好的hadoop复制到从节点上
				scp -r hadoop-2.7.3/ root@bigdata113:/root/training
				scp -r hadoop-2.7.3/ root@bigdata114:/root/training
		(5) 在主节点上启动  start-all.sh





Hadoop的体系结构（非常重要）：都是主从结构
===============================

一、HDFS的体系结构
	1、NameNode：主节点
			http://192.168.157.111:50070
	
		（1）职责：管理维护HDFS
		           接收客户端的请求：上传、下载、创建目录等等
				   维护了两个非常重要的文件：edits文件  -----> 记录操作日志
				                             fsimage文件 ----> HDFS的元信息
											 
		（2）HDFS操作日志：edits文件
			(*)位置：find . -name edits*
				  最新的操作日志：edits_inprogress****
				  
			(*)都是二进制
			(*)HDFS提供一个工具：edits viewer 日志查看器 ----> XML
			(*)Demo:
			    hdfs dfs -mkdir /mydemo
				hdfs oev -i edits_inprogress_0000000000000000106 -o ~/a.xml
				
		（3）HDFS的元信息：fsimage文件
			(*) 就跟edits文件在一起
			(*) 记录：数据块的位置、冗余信息
			(*) 也是一个二进制
			(*) HDFS提供一个 image viewer ----> 文本或者xml
				hdfs oiv -i fsimage_0000000000000000005 -o ~/b.xml -p XML
				
		（4）问题：edits文件和fsimage文件，哪个文件提现了HDFS最新的状态？
	
	2、DataNode：保存数据块
		（1）1.x  64M    2.x  128M
		（2）位置：find . -name blk*
		（3）Demo：上传一个大于128M的文件
				hdfs dfs -put hadoop-2.7.3.tar.gz /tools
				查看数据块的文件
				
		（4）一般原则：数据块的冗余度一般跟数据节点个数一致，最大不要超过3
		               在生产环境下，至少两个数据节点
				
	
	3、SecondaryNameNode:第二名称节点
		（*）作用：把edits中最新的状态信息合并到fsimage文件中
		（*）合并过程
		（*）Web Console：http://192.168.157.111:50090
		（*）检查点：checkpoint
			补充一个知识：Oracle数据库中也有检查点，如果发生检查点，会以最高优先级唤醒数据库写进程（DBWn）把内存中的脏数据写到数据文件上（持久化）
	
二、Yarn的体系结构
	日志：
	18/04/07 20:24:34 INFO client.RMProxy: Connecting to ResourceManager at bigdata111/192.168.157.111:8032


	1、主从结构：ResourceManager、NodeManager
	
	2、调度MapReduce任务过程
	3、资源分配的方式（3种）

三、HBase的体系结构
	1、主节点：HMaster、RegionServer
	2、画图

四、（今晚了解）主从结构的单点故障和解决方案





	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	















