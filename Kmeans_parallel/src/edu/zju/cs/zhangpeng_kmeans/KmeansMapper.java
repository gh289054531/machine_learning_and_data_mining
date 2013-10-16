package edu.zju.cs.zhangpeng_kmeans;

import java.io.IOException;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

import edu.zju.cs.zhangpeng_kmeans.Movie;
import static edu.zju.cs.zhangpeng_kmeans.KmeansUtils.*;

/**输入key无用，输入value为点坐标，格式： x，y
 * 输出key为中心下标，输出value为属于该中心的点
 */
public class KmeansMapper extends Mapper<LongWritable, Text, IntWritable, Text> {
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		int k=context.getConfiguration().getInt("K_value", 1);
		String temp_path=context.getConfiguration().get("TEMP_PATH_value");
		Movie[] c=new Movie[k];
		c=KmeansUtils.LoadCenters(temp_path+"/centers_tmp",k);
		Movie movie=ChangeToMovie(value.toString(),-2);
		int i=0;
		int index=-1;
		double mindis=Double.MAX_VALUE;
		double temp;
		for(;i<k;i++){
			if((temp=KmeansUtils.MovieDistance(movie,c[i],context.getConfiguration()))<=mindis){
				index=i;
				mindis=temp;
			}
		}
		context.write(new IntWritable(index), new Text(movie.toString()));
	}
}