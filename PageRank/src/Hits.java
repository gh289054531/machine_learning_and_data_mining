public class Hits {
	// L代表链出，PageRank值放的是h导航度；LT代表链入，PageRank值放的是a权威度
	public void hits(TransitMatrix L, TransitMatrix LT, int iter) {
		for (Point p : L.points) {
			p.pageRank = 1.0;
		}
		//迭代计算
		for (int it = 0; it < iter; it++) {
			//计算权威度，存放到LT中
			for (Point p : L.points) {
				for (Object t : p.target) {
					Point target = LT.getPoint(t);
					target.pageRank += p.pageRank;
				}
			}
			//找出最大值的权威值
			double max = -1.0;
			for (Point p : LT.points) {
				if (p.pageRank > max) {
					max = p.pageRank;
				}
			}
			//归一化
			for (Point p : LT.points) {
				p.pageRank /= max;
			}
			//计算导航度，存放到L中
			for (Point p : LT.points) {
				for (Object t : p.target) {
					Point target = L.getPoint(t);
					target.pageRank += p.pageRank;
				}
			}
			//找出最大值的导航值
			double max2 = -1.0;
			for (Point p : L.points) {
				if (p.pageRank > max2) {
					max2 = p.pageRank;
				}
			}
			//归一化
			for (Point p : L.points) {
				p.pageRank /= max2;
			}
		}
	}
}
