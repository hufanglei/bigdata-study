

�Hadoop�Ļ���
======================================
һ��׼������
	1����װLinux��JDK���رշ���ǽ������������
	
	
	��ѹ��tar -zxvf hadoop-2.7.3.tar.gz -C ~/training/
	����Hadoop�Ļ�������:  vi ~/.bash_profile
			HADOOP_HOME=/root/training/hadoop-2.7.3
			export HADOOP_HOME

			PATH=$HADOOP_HOME/bin:$HADOOP_HOME/sbin:$PATH
			export PATH
		��Ч
		source ~/.bash_profile
	
	2��Hadoop��Ŀ¼�ṹ
	
���ְ�װģʽ��

�ο������P13ҳ

��������ģʽ��һ̨
	�ص㣺û��HDFS��ֻ�ܲ���MapReduce����
		  MapReduce������Ǳ���Linux���ļ�����

	vi hadoop-env.sh
	25 export JAVA_HOME=/root/training/jdk1.8.0_144
	
	����MapReduce����
	1������Ŀ¼  mkdir ~/input
	             
	2������
			���ӣ�/root/training/hadoop-2.7.3/share/hadoop/mapreduce/hadoop-mapreduce-examples-2.7.3.jar
			hadoop jar hadoop-mapreduce-examples-2.7.3.jar wordcount ~/input/data.txt ~/output
		  

����α�ֲ�ģʽ��һ̨(bigdata111)
	�ص㣺���ڵ����ϣ�ģ��һ���ֲ�ʽ�Ļ���
	      �߱�Hadoop����Ҫ����
		  
	HDFS: namenode+datanode+secondarynamenode
	Yarn: resourcemanager + nodemanager

	hdfs-site.xml

			ԭ��һ�����ݿ������ȸ����ݽڵ㣨DataNode���ĸ���һ�£���󲻳���3

			<!--��ʾ���ݿ������ȣ�Ĭ�ϣ�3-->
			<property>
			   <name>dfs.replication</name>
			   <value>1</value>
			</property>
				
			�Ȳ�����
			<!--�Ƿ���HDFS��Ȩ�޼�飬Ĭ��true-->
			<property>
			   <name>dfs.permissions</name>
			   <value>false</value>
			</property>
	
	core-site.xml

			<!--����NameNode��ַ,9000��RPCͨ�Ŷ˿�-->
			<property>
			   <name>fs.defaultFS</name>
			   <value>hdfs://bigdata111:9000</value>
			</property>	

			<!--HDFS���ݱ�����Linux���ĸ�Ŀ¼��Ĭ��ֵ��Linux��tmpĿ¼-->
			<property>
			   <name>hadoop.tmp.dir</name>
			   <value>/root/training/hadoop-2.7.3/tmp</value>
			</property>	
			
	mapred-site.xml Ĭ��û�� cp mapred-site.xml.template mapred-site.xml

		<!--MR���еĿ��-->
		<property>
		   <name>mapreduce.framework.name</name>
		   <value>yarn</value>
		</property>	
		
	yarn-site.xml
	
		<!--Yarn�����ڵ�RM��λ��-->
		<property>
		   <name>yarn.resourcemanager.hostname</name>
		   <value>bigdata111</value>
		</property>	

		<!--MapReduce���з�ʽ��shuffleϴ��-->
		<property>
		   <name>yarn.nodemanager.aux-services</name>
		   <value>mapreduce_shuffle</value>
		</property>	
		
	��ʽ����HDFS��NameNode��
	    hdfs namenode -format
		
		��־��
		 Storage directory /root/training/hadoop-2.7.3/tmp/dfs/name has been successfully formatted.

	����ֹͣHadoop�Ļ���
	    start-all.sh
		stop-all.sh
		
	���ʣ�ͨ��Web����
	     HDFS:  http://192.168.157.111:50070
		 Yarn:  http://192.168.157.111:8088
	
	����
			���ӣ�/root/training/hadoop-2.7.3/share/hadoop/mapreduce/hadoop-mapreduce-examples-2.7.3.jar
			hadoop jar hadoop-mapreduce-examples-2.7.3.jar wordcount /input/data.txt /output/0407

	��*��һ�������������¼��ԭ������
	
�ġ�ȫ�ֲ�ģʽ
	�ص㣺���ڵķֲ�ʽ��������������
	���ù滮
	
	1��׼������
		��*���رշ���ǽ
		      systemctl stop firewalld.service
			  systemctl disable firewalld.service
			  
		��*����װJDK
		��*������������  vi /etc/hosts
				192.168.157.112 bigdata112
				192.168.157.113 bigdata113
				192.168.157.114 bigdata114
				
		��*�������������¼������֮����������¼
			(1) ÿ̨���������Լ��Ĺ�Կ��˽Կ
					ssh-keygen -t rsa
					
			(2) ÿ̨�������Լ��Ĺ�Կ������
					ssh-copy-id -i .ssh/id_rsa.pub root@bigdata112
					ssh-copy-id -i .ssh/id_rsa.pub root@bigdata113
					ssh-copy-id -i .ssh/id_rsa.pub root@bigdata114
	
		��*����֤ÿ̨������ʱ��ͬ��
				���ʱ�䲻һ����ִ��MapReduce�����ʱ����ܴ�������
	
	2�������ڵ��ϣ�bigdata112����װ
		(1)��ѹ���û�������
		    tar -zxvf hadoop-2.7.3.tar.gz -C ~/training/
			
			���ã�112 113 114
			HADOOP_HOME=/root/training/hadoop-2.7.3
			export HADOOP_HOME

			PATH=$HADOOP_HOME/bin:$HADOOP_HOME/sbin:$PATH
			export PATH
			
		(2) �޸������ļ�
			hadoop-env.sh	JAVA_HOME	/root/training/jdk1.8.0_144
			hdfs-site.xml
				<!--��ʾ���ݿ������ȣ�Ĭ�ϣ�3-->
				<property>
				   <name>dfs.replication</name>
				   <value>2</value>
				</property>
			
			core-site.xml
				<!--����NameNode��ַ,9000��RPCͨ�Ŷ˿�-->
				<property>
				   <name>fs.defaultFS</name>
				   <value>hdfs://bigdata112:9000</value>
				</property>	

				<!--HDFS���ݱ�����Linux���ĸ�Ŀ¼��Ĭ��ֵ��Linux��tmpĿ¼-->
				<property>
				   <name>hadoop.tmp.dir</name>
				   <value>/root/training/hadoop-2.7.3/tmp</value>
				</property>				
	
			mapred-site.xml Ĭ��û�� cp mapred-site.xml.template mapred-site.xml

				<!--MR���еĿ��-->
				<property>
				   <name>mapreduce.framework.name</name>
				   <value>yarn</value>
				</property>	
	
		yarn-site.xml
	
				<!--Yarn�����ڵ�RM��λ��-->
				<property>
				   <name>yarn.resourcemanager.hostname</name>
				   <value>bigdata112</value>
				</property>	

				<!--MapReduce���з�ʽ��shuffleϴ��-->
				<property>
				   <name>yarn.nodemanager.aux-services</name>
				   <value>mapreduce_shuffle</value>
				</property>	
				
		slaves
			bigdata113
			bigdata114
			
		(3) ��ʽ��NameNode�� hdfs namenode -format
		(4) �����ڵ������úõ�hadoop���Ƶ��ӽڵ���
				scp -r hadoop-2.7.3/ root@bigdata113:/root/training
				scp -r hadoop-2.7.3/ root@bigdata114:/root/training
		(5) �����ڵ�������  start-all.sh





Hadoop����ϵ�ṹ���ǳ���Ҫ�����������ӽṹ
===============================

һ��HDFS����ϵ�ṹ
	1��NameNode�����ڵ�
			http://192.168.157.111:50070
	
		��1��ְ�𣺹���ά��HDFS
		           ���տͻ��˵������ϴ������ء�����Ŀ¼�ȵ�
				   ά���������ǳ���Ҫ���ļ���edits�ļ�  -----> ��¼������־
				                             fsimage�ļ� ----> HDFS��Ԫ��Ϣ
											 
		��2��HDFS������־��edits�ļ�
			(*)λ�ã�find . -name edits*
				  ���µĲ�����־��edits_inprogress****
				  
			(*)���Ƕ�����
			(*)HDFS�ṩһ�����ߣ�edits viewer ��־�鿴�� ----> XML
			(*)Demo:
			    hdfs dfs -mkdir /mydemo
				hdfs oev -i edits_inprogress_0000000000000000106 -o ~/a.xml
				
		��3��HDFS��Ԫ��Ϣ��fsimage�ļ�
			(*) �͸�edits�ļ���һ��
			(*) ��¼�����ݿ��λ�á�������Ϣ
			(*) Ҳ��һ��������
			(*) HDFS�ṩһ�� image viewer ----> �ı�����xml
				hdfs oiv -i fsimage_0000000000000000005 -o ~/b.xml -p XML
				
		��4�����⣺edits�ļ���fsimage�ļ����ĸ��ļ�������HDFS���µ�״̬��
	
	2��DataNode���������ݿ�
		��1��1.x  64M    2.x  128M
		��2��λ�ã�find . -name blk*
		��3��Demo���ϴ�һ������128M���ļ�
				hdfs dfs -put hadoop-2.7.3.tar.gz /tools
				�鿴���ݿ���ļ�
				
		��4��һ��ԭ�����ݿ�������һ������ݽڵ����һ�£����Ҫ����3
		               �����������£������������ݽڵ�
				
	
	3��SecondaryNameNode:�ڶ����ƽڵ�
		��*�����ã���edits�����µ�״̬��Ϣ�ϲ���fsimage�ļ���
		��*���ϲ�����
		��*��Web Console��http://192.168.157.111:50090
		��*�����㣺checkpoint
			����һ��֪ʶ��Oracle���ݿ���Ҳ�м��㣬����������㣬����������ȼ��������ݿ�д���̣�DBWn�����ڴ��е�������д�������ļ��ϣ��־û���
	
����Yarn����ϵ�ṹ
	��־��
	18/04/07 20:24:34 INFO client.RMProxy: Connecting to ResourceManager at bigdata111/192.168.157.111:8032


	1�����ӽṹ��ResourceManager��NodeManager
	
	2������MapReduce�������
	3����Դ����ķ�ʽ��3�֣�

����HBase����ϵ�ṹ
	1�����ڵ㣺HMaster��RegionServer
	2����ͼ

�ġ��������˽⣩���ӽṹ�ĵ�����Ϻͽ������





	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	















