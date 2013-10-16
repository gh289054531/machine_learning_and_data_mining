package edu.zju.cs.zhangpeng_kmeans;

public class Movie{
	private double x;
	private double y;
	
	public Movie(double x,double y){
		this.x=x;
		this.y=y;
	}
	public void setx(double x){
		this.x=x;
	}
	public void sety(double y){
		this.y=y;
	}
	public double getx(){
		return x;
	}
	public double gety(){
		return y;
	}
	public void print(){
		System.out.print(x+",");
		System.out.println(y);
	}
	public String toString(){
		return String.valueOf(x)+","+ String.valueOf(y);
	}
}