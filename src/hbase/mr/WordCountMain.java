package hbase.mr;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;

public class WordCountMain {

	public static void main(String[] args) throws Exception {
		//从HBase中读取数据
		Configuration conf = new Configuration();
		conf.set("hbase.zookeeper.quorum", "192.168.157.111");		
		
		//创建任务
		Job job = Job.getInstance(conf);
		job.setJarByClass(WordCountMain.class);
		
		//定义一个扫描器读取要处理的数据
		Scan scan = new Scan();
		//指定扫描器扫描的数据
		scan.addColumn(Bytes.toBytes("content"), Bytes.toBytes("info"));
				
		//指定Map,输入是表  word
		TableMapReduceUtil.initTableMapperJob("word", scan, WordCountMapper.class, 
				                              Text.class, IntWritable.class, job);
		
		//指定Reduce 输出的表  result
		TableMapReduceUtil.initTableReducerJob("result", WordCountReducer.class, job);
		
		job.waitForCompletion(true);
	}

}

















