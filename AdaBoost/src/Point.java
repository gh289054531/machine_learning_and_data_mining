
public class Point {
	double x;
	double y;
	int label;	//标签0或1
	public Point(double x,double y,int value){
		this.x=x;
		this.y=y;
		this.label=value;
	}
	public String toString(){
		return x+" "+y+"  "+label;
	}
}
