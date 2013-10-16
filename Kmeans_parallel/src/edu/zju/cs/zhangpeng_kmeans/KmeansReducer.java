package edu.zju.cs.zhangpeng_kmeans;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import static edu.zju.cs.zhangpeng_kmeans.KmeansUtils.*;


/**输入key为中心下标，输入value为同一key下所有点
 * 输出key为中心下标，输出value为新中心坐标
 */
public class KmeansReducer extends Reducer<IntWritable, Text, IntWritable, Text> {
	
	@Override
	protected void reduce(IntWritable key, Iterable<Text> values,Context context) throws IOException, InterruptedException {
		int k=context.getConfiguration().getInt("K_value", 1);
		String temp_path=context.getConfiguration().get("TEMP_PATH_value");
		Movie[] c=new Movie[k];
		c=KmeansUtils.LoadCenters(temp_path+"/centers_tmp",k);
		double sumx=0.0,sumy=0.0;
		int num=0;
		Movie newCenter;
		context.write(key, new Text("Movies belog to center:"+c[key.get()].toString()));
		for (Text value : values) {
			num++;
			Movie movie=ChangeToMovie(value.toString(),-3);
			context.write(key, new Text(movie.toString()));
			sumx+=movie.getx();
			sumy+=movie.gety();
		}
		newCenter=new Movie(sumx/num,sumy/num);
		FileSystem fs = FileSystem.get(URI.create(temp_path+"/centers_tmp"), context.getConfiguration());
		FSDataOutputStream out = fs.append(new Path(temp_path+"/centers_tmpnewcenter"));
		out.write(String.valueOf(key.get()).getBytes());
		out.write(",".getBytes());
		out.write(newCenter.toString().getBytes());
		out.write('\n');
		out.close();
		//CENTERS_NEW[key.get()]=newCenter;
		context.write(key, new Text("Sum to:"+num+";New Center:"+newCenter.toString()));
	}

}