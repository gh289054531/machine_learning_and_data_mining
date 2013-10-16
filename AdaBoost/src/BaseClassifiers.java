/**
 * 基分类器是坐标轴上的横线与竖线，且值为整数，例如x=1、y=2
 * @author pstar
 *
 */
public class BaseClassifiers {
	char type;	//类型，x代表x=coordinate，y代表y=coordinate
	int coordinate;
	
	public BaseClassifiers(char type,int coordinate){
		this.type=type;
		this.coordinate=coordinate;
	}
	
	//在分类器右方或者上方返回1，否则返回-1
	public int classify(Point p){
		double x=p.x;
		double y=p.y;
		if(this.type=='x'){
			if((x-this.coordinate>=0)){
				return 1;
			}else{
				return -1;
			}
		}else{
			if((y-this.coordinate>=0)){
				return 1;
			}else{
				return -1;
			}
		}
	}
}
