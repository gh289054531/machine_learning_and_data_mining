package edu.zju.cs.zhangpeng_kmeans;

import java.io.*;
import java.net.URI;
import java.util.Random;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class KmeansUtils{
 
	public static final long TIME = System.currentTimeMillis();
	public static int K;
	public static int ITER; //最大迭代次数
	public static double DELTA; //判断收敛的阈值
	public static Movie[] CENTERS;
	public static Movie[] CENTERS_NEW; //暂时记录一轮job完成后的新中心
	public static String INPUT_PATH=null;
	public static String OUTPUT_PATH=null;
	public static String TEMP_PATH=null;
	public static final String[] ERRORMSG={" input stage"," map stage"," reduce stage"};//ChangeToMovie中的出错码
	
	/**初始化CENTERS_NEW,每次运行只在最初调用一次*/
	public static void InitialCENTERS(){
		CENTERS=new Movie[K]; 
		CENTERS_NEW=new Movie[K];
		for(int i=0;i<K;i++){
			CENTERS_NEW[i]=new Movie(0.0,0.0);
		}
	}
	/** 随机选择K个中心 */
	public static void ChooseCenter(String input){
		int movieNum=countMovieNum(input);
		
		int intRd = 0; //存放随机数
        int count = 0; //记录生成的随机数个数
        boolean flag;//记录是否为重复中心，true代表重复
        int[] Rds=new int[K];//记录被选中点的行数
        for(int i=0;i<K;i++){
        	Rds[i]=-1;
        }
        while(count<K){
        	flag=false;
             Random rdm = new Random();
             intRd = Math.abs(rdm.nextInt())%movieNum;
             for(int i=0;i<=count;i++){
                 if(Rds[i]==intRd){
                	 flag=true;
                     break;
                 }
             }  
             if(flag==false){
            	 Rds[count] = intRd;
            	 count++;
             }
        }
        java.util.Arrays.sort(Rds);
        
        int i=0,j=0;
        InputStream in = null;
		try {
			Configuration conf = new Configuration();
			FileSystem fs = FileSystem.get(URI.create(input), conf);
			in = fs.open(new Path(input));
			BufferedReader reader= new BufferedReader(new InputStreamReader(in));
			String temp;
			while((temp=reader.readLine())!=null){
				if(Rds[j]==i) {
					CENTERS[j]=ChangeToMovie(temp,i);
					j++;
					if(j==K){
						break;
					}
				}
				i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally	{
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**计算点的个数，输入文件1行对应1个点 */
	private static int countMovieNum(String input){
		int num=0;
		InputStream in = null;
		try {
			Configuration conf = new Configuration();
			FileSystem fs = FileSystem.get(URI.create(input), conf);
			in = fs.open(new Path(input));
			BufferedReader reader= new BufferedReader(new InputStreamReader(in));
			while(reader.readLine()!=null){
				num++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally	{
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return num;
	}
	
	/**判断中心是否有相同的元素*/
	public static boolean hasSame(){
		for(int i=0;i<K;i++){
			for(int j=i+1;j<K;j++){
				if(MovieDistance(CENTERS[i],CENTERS[j])==0){
					return true;
				}
			}
		}
		return false;
	}
	
	/**将输入字符串转换为Movie
	 * 参数line代表出错码：
	 * -1 ：输入文件转换时出错
	 * -2 ：map阶段出错
	 * -3 ：reduce阶段出错 
	 */
	public static Movie ChangeToMovie(String temp,int line){
		String[] split=temp.split(",");
		if(split.length!=2){
			System.out.print("ChangeToMovie error at line"+line);
			System.exit(0);
		}
		return new Movie(Double.parseDouble(split[0]),Double.parseDouble(split[1]));
	}
	
	/**循环打印中心点坐标*/
	public static void ShowCenters(){
		System.out.println(K+"个中心点为：");
		for(int i=0;i<K;i++){
			CENTERS[i].print();
		}
	}
	
	/**返回两点距离
	 * @throws IOException */
	public static double MovieDistance(Movie a,Movie b , Configuration conf) throws IOException{
//		FileSystem fs = FileSystem.get(conf);
//		FSDataOutputStream out = fs.create(new Path("debug_path"));
//		out.write(a.toString().getBytes());
//		out.write(b.toString().getBytes());
//		out.close();
		double sum=(a.getx()-b.getx())*(a.getx()-b.getx())+(a.gety()-b.gety())*(a.gety()-b.gety());
		return Math.sqrt(sum);
	}

	public static double MovieDistance(Movie a,Movie b )	{

		double sum=(a.getx()-b.getx())*(a.getx()-b.getx())+(a.gety()-b.gety())*(a.gety()-b.gety());
		return Math.sqrt(sum);
	}
	
	/**判断是否已收敛
	 * 收敛条件是CENTERS_NEW和CENTERS中每个对应元素的距离小于DELTA
	 */
	public static boolean IsConvergence(){
		for(int i=0;i<K;i++){
			double delta=0.0;
			delta=MovieDistance(CENTERS[i],CENTERS_NEW[i]);
			if(delta>DELTA){
				return false;
			}
		}
		return true;
	}
	
	/**将CENTERS_NEW复制到CENTERS*/
	public static void CenterCopy(){
		for(int i=0;i<K;i++){
			CENTERS[i].setx(CENTERS_NEW[i].getx());
			CENTERS[i].sety(CENTERS_NEW[i].gety());
		}
	}
	
	/**将CENTERS存储到hdfs文件中
	 * @throws IOException */
	public static void SaveCenters(Movie[] c,String input) throws IOException{
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(input), conf);
		FSDataOutputStream out = fs.create(new Path(input));
		for(int i=0;i<K;i++){
			out.write(String.valueOf(i).getBytes());
			out.write(",".getBytes());
			out.write(c[i].toString().getBytes());
			out.write('\n');
		}
		out.close();
	}
	
	/**从hdfs文件中读取CENTERS
	 * @throws IOException */
	public static Movie[] LoadCenters(String input,int center_num) throws IOException{
		Movie[] m=new Movie[center_num];
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(input), conf);
		InputStream in = fs.open(new Path(input));
		BufferedReader reader= new BufferedReader(new InputStreamReader(in));
		String line=null;
		while((line=reader.readLine())!=null){
			String[] split=line.split(",");
			int i=Integer.parseInt(split[0]);
			double x=Double.parseDouble(split[1]);
			double y=Double.parseDouble(split[2]);
			m[i]=new Movie(x,y);
		}
		in.close();
		return m;
	}
}
