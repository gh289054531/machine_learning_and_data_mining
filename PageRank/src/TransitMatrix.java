import java.util.HashSet;

/**
 * 紧凑表示的转移矩阵,见大数据书中P127,优点：数据量越大越省空间，缺点：表示矩阵中关系非常麻烦，稍不谨慎就会出错
 * 
 * @author pstar
 * 
 */
public class TransitMatrix implements Cloneable {
	public HashSet<Point> points = new HashSet<Point>();

	// 如果输入c不是java自带的数据类型，需要重定义equals方法，因为add实际上通过equals方法判断两个元素是否相同
	public void add(Object s, Object t) {
		for (Point p : points) {
			if (p != null && p.source.equals(s)) {
				p.add(t);
				return;
			}
		}
		Point point = new Point(s);
		point.add(t);
		points.add(point);
	}

	public void add(Object s) {
		for (Point p : points) {
			if (p != null && p.source.equals(s)) {
				return;
			}
		}
		Point point = new Point(s);
		points.add(point);
	}

	// 如果输入c不是java自带的数据类型，需要重定义equals方法，因为add实际上通过equals方法判断两个元素是否相同
	public void remove(Object s, Object t) {
		for (Point p : points) {
			if (p != null && p.source.equals(s)) {
				p.remove(t);
				return;
			}
		}
	}

	// 删除一整行,并且连带删除指向它的入边
	public void remove(Point s) {
		Object toRemove=s.source;
		points.remove(s);
		for (Point p : points) {
			for (Object o : p.target) {
				if (o.equals(toRemove)) {
					p.remove(o);
					break;
				}
			}
		}
	}

	// 返回矩阵中的行数，即源网页个数
	public int length() {
		return points.size();
	}

	// 返回s对应的那一行
	public Point getPoint(Object s) {
		for (Point p : points) {
			if (p != null && p.source.equals(s)) {
				return p;
			}
		}
		return null;
	}

	// 获取源网页s的出度,若返回-1则代表s不在转移矩阵中
	public int getOutDegree(Object s) {
		for (Point p : points) {
			if (p != null && p.source.equals(s)) {
				return p.outDegree;
			}
		}
		return -1;
	}

	// 获取源网页s的所有目标网页
	public HashSet<Object> getTarget(Object s) {
		for (Point p : points) {
			if (p != null && p.source.equals(s)) {
				return p.target;
			}
		}
		return null;
	}

	// 初始化PageRank值，为1.0/点的总数,此函数在数据输入完成后调用一次
	public void initialPageRankValue() {
		for (Point p : points) {
			p.pageRank = 1.0 / points.size();
			p.nextPageRank = 0.0;
		}
	}

	// 每一轮迭代后，更新PageRank值
	public void updatePageRank() {
		for (Point p : points) {
			p.pageRank = p.nextPageRank;
			p.nextPageRank = 0.0;
		}
	}

	// 检查迭代后pageRank值的变化情况，若变化值小于delt则可以结束，返回true
	public boolean canQuit(double delt) {
		double temp = 0.0;
		for (Point p : points) {
			temp += Math.abs(p.pageRank - p.nextPageRank);
		}
		if (temp < delt) {
			return true;
		}
		return false;
	}

	// 打印pageRank值
	public void printPageRank() {
		for (Point p : points) {
			System.out.println(p.source.toString() + "\t" + p.pageRank);
		}
	}

	// 打印转移矩阵
	public void printTransitMatrix() {
		for (Point p : points) {
			System.out.println(p.source.toString() + "\t" + p.pageRank + "\t"
					+ p.outDegree + "\t" + p.targetToString());
		}
	}

	public Object clone() {
		TransitMatrix c = new TransitMatrix();
		for (Point p : points) {
			Point point = new Point(p.source);
			point.outDegree = p.outDegree;
			point.pageRank = p.pageRank;
			point.nextPageRank = p.nextPageRank;
			point.target = new HashSet<Object>();
			for (Object o : p.target) {
				point.target.add(o);
			}
			c.points.add(point);
		}
		return c;
	}

}

/**
 * 紧凑表示的转移矩阵中的一行
 * 
 * @author root
 * 
 */
class Point {
	Object source;
	int outDegree = 0;
	HashSet<Object> target = new HashSet<Object>();
	double pageRank=0.0;
	double nextPageRank=0.0;// 计算下一轮PageRank值时临时存放中间结果

	public Point(Object s) {
		source = s;
	}

	// 如果输入c不是java自带的数据类型，需要重定义equals方法，因为add实际上通过equals方法判断两个元素是否相同
	public boolean add(Object c) {
		boolean flag = target.add(c);
		if (flag == true) {
			outDegree++;
		}
		return flag;
	}

	// 如果输入c不是java自带的数据类型，需要重定义equals方法，因为add实际上通过equals方法判断两个元素是否相同
	public boolean remove(Object c) {
		boolean flag = target.remove(c);
		if (flag == true) {
			outDegree--;
		}
		return flag;
	}

	// 把Target转换成字符串便于显示
	public String targetToString() {
		StringBuilder sb = new StringBuilder();
		for (Object p : target) {
			sb.append(p + " ");
		}
		return sb.toString();
	}
}