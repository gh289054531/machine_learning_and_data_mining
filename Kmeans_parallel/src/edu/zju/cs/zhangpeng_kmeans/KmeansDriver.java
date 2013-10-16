package edu.zju.cs.zhangpeng_kmeans;


import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import static edu.zju.cs.zhangpeng_kmeans.KmeansUtils.*;

public class KmeansDriver extends Configured implements Tool 
{
	static int iter=0;//当前迭代次数
	// parallel K-means算法，args[0]：输入路径，args[1]：输出路径，args[2]:中介结果暂存路径，args[3]：中心数,args[4]:最大迭代次数，args[5]：收敛判断函数阈值
	public static void main(String[] args) throws Exception{
		INPUT_PATH=args[0];
		OUTPUT_PATH=args[1];
		TEMP_PATH=args[2];
		K=Integer.parseInt(args[3]);
        ITER=Integer.parseInt(args[4]);
        DELTA=Double.parseDouble(args[5]);
		KmeansUtils.InitialCENTERS();
		KmeansUtils.ChooseCenter(INPUT_PATH+"/input0.txt");
		//KmeansUtils.ShowCenters();
		KmeansUtils.SaveCenters(CENTERS,TEMP_PATH+"/centers_tmp");
		if(KmeansUtils.hasSame()==true){
			System.out.println("选择初始中心失败，结束任务");
			System.exit(-1);
		}
		FileSystem fs = FileSystem.get(URI.create(TEMP_PATH+"/centers_tmp"), new Configuration());
		long starttime=System.currentTimeMillis();
		while(iter<ITER){
			FSDataOutputStream o=fs.create(new Path(TEMP_PATH+"/centers_tmpnewcenter"));
			o.close();
			CENTERS=KmeansUtils.LoadCenters(TEMP_PATH+"/centers_tmp",K);
			//System.out.println("after LoadCenters:");
			//KmeansUtils.ShowCenters();
			if(KmeansUtils.hasSame()==true){
				System.out.println("第"+iter+"次迭代出现重复中心，结束任务");
				System.exit(-1);
			}
			System.out.println("开始第"+iter+"次迭代");
			ToolRunner.run(new KmeansDriver(), args);
			CENTERS_NEW=KmeansUtils.LoadCenters(TEMP_PATH+"/centers_tmpnewcenter",K);
			iter++;
			if(KmeansUtils.IsConvergence()==true){
				break;
			}
			KmeansUtils.SaveCenters(CENTERS_NEW, TEMP_PATH+"/centers_tmp");
			fs.deleteOnExit(new Path(TEMP_PATH+"/centers_tmpnewcenter"));
		};
		fs.deleteOnExit(new Path(TEMP_PATH+"/centers_tmp"));
		System.out.println("Cost time(ms):"+(System.currentTimeMillis()-starttime));
		System.exit(0);
	}
	/**进行一轮MapReduce作业*/
	public int run(String[] args) throws Exception {
		Configuration conf=getConf();
		conf.set("TEMP_PATH_value", TEMP_PATH);
		conf.setInt("K_value", K);
	    conf.set("mapred.job.tracker", "10.15.61.111:9001");
		Job job=new Job(conf,"kmeans:"+iter);
		String[] ars=new String[]{INPUT_PATH,OUTPUT_PATH};
	    String[] otherArgs = new GenericOptionsParser(conf, ars).getRemainingArgs();
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]+"/"+TIME+"_"+iter));
		job.setJarByClass(KmeansDriver.class);
		job.setMapperClass(KmeansMapper.class);
		job.setReducerClass(KmeansReducer.class);
		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(Text.class);
		return job.waitForCompletion(true) ? 0 : 1;
	}
}
