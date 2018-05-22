

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
   <value>2</value>
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
	

	（*）一定配置免密码登录
	
四、全分布模式
	特点：正在的分布式环境，用于生产





<!--配置NameNode地址,9000是RPC通信端口-->
<property>
   <name>fs.defaultFS</name>
   <value>hdfs://bigData12:9000</value>
</property>	

<!--HDFS数据保存在Linux的哪个目录，默认值是Linux的tmp目录-->
<property>
   <name>hadoop.tmp.dir</name>
   <value>/root/training/hadoop-2.7.3/tmp</value>
</property>


<!--配置NameNode地址,9000是RPC通信端口-->
<property>
   <name>fs.defaultFS</name>
   <value>hdfs://bigData13:9000</value>
</property>	

<!--HDFS数据保存在Linux的哪个目录，默认值是Linux的tmp目录-->
<property>
   <name>hadoop.tmp.dir</name>
   <value>/root/training/hadoop-2.7.3/tmp</value>
</property>	



<!--配置NameNode地址,9000是RPC通信端口-->
<property>
   <name>fs.defaultFS</name>
   <value>hdfs://bigData14:9000</value>
</property>	

<!--HDFS数据保存在Linux的哪个目录，默认值是Linux的tmp目录-->
<property>
   <name>hadoop.tmp.dir</name>
   <value>/root/training/hadoop-2.7.3/tmp</value>
</property>











