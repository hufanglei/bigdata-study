
# Linux基础 
  ## 1. 安装linux注意事项
  ## 2. 配置主机模式
  ## 3. linux文件权限的介绍
  ## 3. vi编辑器的使用
  ## 4. 几个常用的软件winscp（ftp传输软件），putty 主机操作虚拟机的软件
  ## 5. java和tree的安装 
   备注   
      ①关于第一台虚拟机，修改的配置文件的地方有，配置文件 vi /etc/hosts
       新增: 192.168.157.111 bigdata111
      ②在安装过程中选择主机模式
         对应一个主机虚拟的网卡：vmnet1  ----> 对应的IP：192.168.157.1
         
  上课时间：
1、3、5的晚上：8点 ~  10:40分

课程资料：链接：https://pan.baidu.com/s/1c2haSrm 密码：c140

环境：
1、Redhat 7.4 64位
2、JDK 1.8 64位
3、VMware 12：版本不能太低
4、Putty 远程登录工具（命令行）


学习大数据：学习的路线和课程概述
1、基础： Java SE
          Linux基础
		  
2、Hadoop的生态系统
	（1）数据存储：HDFS
	（2）数据计算：MapReduce（Java程序）----> 运行在Yarn平台
	               处理离线（历史）数据
	（3）其他：HBase 是NoSQL数据库
	           Hive、Pig 是数据分析引擎
			   Flume、Sqoop 是数据采集引擎
			   ZooKeeper、HUE等等

3、Storm：实时计算框架（处理流式数据）
	（1）内存NoSQL数据库：Redis
	（2）Apache Storm
	
4、Spark 基于内存，处理大数据
	（1）Scala编程语言
	（2）Spark Core：内核（最重要的内容）-----> 相当于MapReduce
	（3）Spark SQL：相当于Hive、Pig 是数据分析引擎
	                 支持SQL
	（4）Spark Streaming：相当于Storm：实时计算框架（处理流式数据）

5、项目实训

第一章、Linux基础
==========================================================
一、Linux的实验环境
	1、安装Linux
		重点：	      （1）选择网卡的类型
					选择“仅主机模式”：注意一个问题：设置虚拟机的IP地址的时候
						对应一个主机虚拟的网卡：vmnet1  ----> 对应的IP：192.168.157.1
					
					
				（2）选择：Development Tools ----> 自带了gcc编译器 ----> 安装Redis
				           Server with GUI 图形界面
						   
				（3）设置虚拟机的网卡
					
					
		介质：rhel-server-7.4-x86_64-dvd.iso
		安装RedHatLinux7.docx
	
	2、总共需要5台Linux
		  192.168.157.111    bigdata111 
		  192.168.157.112    bigdata112 
		  192.168.157.113    bigdata113 
		  192.168.157.114    bigdata114 
		  192.168.157.115    bigdata115 

二、配置Linux和Linux的目录结构

	清屏：ctrl+L

	1、Linux的体系结构（操作系统体系结构）
	2、Linux和UNIX
	3、关闭防火墙
		systemctl status firewalld.service
		systemctl stop firewalld.service     ---> 如果重启，会重启防火墙
		systemctl disable firewalld.service  ---> 永久关闭防火墙
	
	4、配置主机名和IP的对应关系
		编辑配置文件 vi /etc/hosts
			192.168.157.111 bigdata111

三、vi编辑器：相当于linux的记事本
	1、命令格式  vi 文件名  
		如果文件存在，打开
		如果不存在，创建
		
	2、三种模式
		（1）编辑模式（默认）：等待命令的输入
		                       按i ---> 进入到了：插入模式
							   按：  进入了命令模式
							   按/   进入搜索模式
		
		（2）插入模式：输入文本
		     按esc键，回到编辑模式
		
		（3）命令模式
			w 保存
			q 退出
			wq 保存退出
			! 强制***
			set number 打开行号
			set nonumber 关闭行号
			set wrap 换行显示
			set nowrap 不换行显示

四、文件目录操作命令（Linux的重点，这些命令跟HDFS的命令非常像）
		（1）ls 显示文件和目录列表 
			 -l 列出文件的详细信息，等同于 ll命令
			 
			 -a 列出当前目录所有文件，包含隐藏文件和隐藏目录
			     带.的 就是隐藏
				 
				 隐藏文件：/root/.bash_profile 设置环境变量
				 隐藏目录: /root/.ssh  保存公钥和私钥，用于免密码登录（hadoop、spark需要配置免密码登录）
		
			查看HDFS：hdfs dfs -ls /
		
		（2）mkdir 创建目录
				-p 父目录不存在情况下先生成父目录
				
				约定： mkdir /root/training   安装目录
				       mkdir /root/tools      介质
					   
			在HDFS上创建目录：hdfs dfs -mkdir /data
		
		（3）cd 切换目录
			 注意： ~ 代表当前用户的家目录 /root
		
		（4）touch 生成一个空文件
			 echo 生成一个带内容文件；更常用的方式是用它查看环境变量
			       echo $JAVA_HOME
		
		（5）cat、tac 显示文本文件内容
			 cat是从第一行开始写；tac是从最后一行开始写
			 more 命令：分页显示
			 
			 more data.txt
			 
			 查看HDFS目录下文件的内容  hdfs dfs -cat /data/data.txt
		
		（6）cp 复制文件或目录
				基本拷贝：cp data.txt a.txt
				
			想在HDFS上拷贝文件：hdfs dfs -cp /data/data.txt /data/bbb.txt
				
			 scp 在网络上拷贝命令（安装Hadoop、Spark、Storm全分布的需要用到）
		
		（7）rm 删除文件
				-r 同时删除该目录下的所有文件
				-f 强制删除文件或目录
				
			例子： rm -rf a.txt
				如果想在HDFS上删除: hdfs dfs -rmr /data 
				
		（8）tar命令：打包压缩命令
		              一般安装介质的后缀:  .tar.gz格式
								hadoop-2.7.3.tar.gz
								jdk-8u144-linux-x64.tar.gz
		（9）kill命令，最常用的参数 -9  PID
		                 kill -9 PID
						 
					   另外一个参数：kill -3 PID 对于Java来说，打印出Java的Thread Dump信息（死锁、性能瓶颈）

		（10）find 在文件系统中查找指定的文件   -name  文件名
		       举例: find . -name *.txt
			       . 表示当前目录和子目录
				   
		（11）man 命令帮助信息查询
		       举例  man ls

		（12）Linux 通道：命令1 | 命令2
				把命令1的输出 作为 命令2 的输入
				
				cat data.txt | more
				ps -ef ----> 查看所有的进程
				ps -ef | grep java  ----> 把所有的进程进行过滤 查看java的进程

五、Linux的权限管理（原因：HDFS也有权限的管理，类似Linux的权限管理）
	1、权限的类型：3种
	     r  读
		 w  写
		 x  执行
		 
	2、举例

六、安装常用软件
	使用一个ftp的工具: winscp 免费的FTP

	1、JDK: 版本： 1.8 64位
		（1）使用tar命令解压
				tar -zxvf jdk-8u144-linux-x64.tar.gz -C ~/training/
				
		（2）设置环境变量：vi ~/.bash_profile
				JAVA_HOME=/root/training/jdk1.8.0_144
				export JAVA_HOME

				PATH=$JAVA_HOME/bin:$PATH
				export PATH
				
		（3）生效环境变量  source ~/.bash_profile
	
	2、tree命令: 安装包： .rpm
		命令： rpm -ivh tree-1.6.0-10.el7.x86_64.rpm
			tree -d -L 2
			
			-d：只显示目录
			-L：表示目录的层数
			[root@bigdata111 ~]# tree -d -L 2
			.
			├── Desktop
			├── Documents
			├── Downloads
			├── Music
			├── Pictures
			├── Public
			├── Templates
			├── tools
			├── training
			│   └── jdk1.8.0_144
			└── Videos
			
七、案例：Java的死锁分析 
	预习：DeadLock.java

       
         
