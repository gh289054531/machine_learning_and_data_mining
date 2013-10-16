import java.util.HashSet;
import java.util.LinkedList;

/**
 * PageRank算法集，包括各种变种算法
 * 
 * @author pstar
 * 
 */
public class PageRank {
	// 最基础的PageRank，迭代更新模型。
	public void basicPageRank(TransitMatrix tm, int iter, double delt) {
		tm.initialPageRankValue();
		for (int it = 0; it < iter; it++) {
			for (Point p : tm.points) {
				double passValue = p.pageRank / p.outDegree;
				for (Object t : p.target) {
					Point target = tm.getPoint(t);
					target.nextPageRank += passValue;
				}
			}

			if (tm.canQuit(delt)) {
				tm.updatePageRank();
				break;
			}
			tm.updatePageRank();
		}
	}

	// basicPageRank之上去除终止点。  如果不去除终止点，则PageRank值会随着迭代运算被逐渐抽出，最终无法得到有意义的结果
	public void removeTerminalPageRank(TransitMatrix tm, int iter, double delt) {
		LinkedList<Object> removeList = new LinkedList<Object>();
		TransitMatrix tmBackup = (TransitMatrix) tm.clone();
		boolean flag = true;
		while (flag) {
			flag = false;
			for (Point p : tmBackup.points) {
				if (p.outDegree == 0) {
					removeList.add(p.source);
					tmBackup.remove(p);
					flag = true;
					break;
				}
			}
		}
		tmBackup.initialPageRankValue();
		basicPageRank(tmBackup, iter, delt);//实际上还是调用了最基础的basicPageRank
		for (Point pBackup : tmBackup.points) {
			for (Point p : tm.points) {
				if (pBackup.source.equals(p.source)) {
					p.pageRank = pBackup.pageRank;
				}
			}
		}
		while (removeList.size() > 0) {
			Object removedObject = removeList.getLast();
			Point removedPoint = tm.getPoint(removedObject);
			for (Point p : tm.points) {
				if (p.target != null) {
					for (Object o : p.target) {
						if (o.equals(removedObject)) {
							removedPoint.pageRank += p.pageRank / p.outDegree;
						}
					}
				}
			}
			removeList.removeLast();
		}
	}

	//basicPageRank之上加入跳转模型，即抽税机制，跳转概率为beta，一般设为0.85（此程序没有处理终止点）
	//跳转模型telModel的不同代表不同思想，例如有偏随机跳转、面向主题的跳转、面向信任页面的跳转TrustRank
	public void teleportPageRank(TransitMatrix tm, int iter, double delt,double beta,HashSet<Object> telModel){
		tm.initialPageRankValue();
		for (int it = 0; it < iter; it++) {
			for (Point p : tm.points) {
				double passValue = p.pageRank / p.outDegree;
				for (Object t : p.target) {
					Point target = tm.getPoint(t);
					target.nextPageRank += passValue;
				}
			}
			for(Point p:tm.points){
				if(telModel.contains(p.source)){
					p.nextPageRank=p.nextPageRank*beta+(1-beta)/telModel.size();
				}else{
					p.nextPageRank=p.nextPageRank*beta;
				}
			}
			if (tm.canQuit(delt)) {
				tm.updatePageRank();
				break;
			}
			tm.updatePageRank();
		}
	}
	
	// teleportPageRank之上处理了终止点
	public void removeTerminalTeleportPageRank(TransitMatrix tm, int iter, double delt,double beta,HashSet<Object> telModel) {
		LinkedList<Object> removeList = new LinkedList<Object>();
		TransitMatrix tmBackup = (TransitMatrix) tm.clone();
		tmBackup.printTransitMatrix();
		boolean flag = true;
		while (flag) {
			flag = false;
			for (Point p : tmBackup.points) {
				if (p.outDegree == 0) {
					removeList.add(p.source);
					tmBackup.remove(p);
					flag = true;
					break;
				}
			}
		}
		tmBackup.initialPageRankValue();
		teleportPageRank(tmBackup, iter, delt,beta,telModel);
		for (Point pBackup : tmBackup.points) {
			for (Point p : tm.points) {
				if (pBackup.source.equals(p.source)) {
					p.pageRank = pBackup.pageRank;
				}
			}
		}
		while (removeList.size() > 0) {
			Object removedObject = removeList.getLast();
			Point removedPoint = tm.getPoint(removedObject);
			for (Point p : tm.points) {
				if (p.target != null) {
					for (Object o : p.target) {
						if (o.equals(removedObject)) {
							removedPoint.pageRank += p.pageRank / p.outDegree;
						}
					}
				}
			}
			removeList.removeLast();
		}
	}
	
	//垃圾质量(spam mass),假设页面PageRank为r，TrustRank为t，则垃圾质量spam mass=(r-t)/r
	//此程序借用Point类中nextPageRank字段代表垃圾质量，因为此字段在计算出PageRank值后没有任何作用了，正好拿来用
	public void spamMass(TransitMatrix tm, int iter, double delt,double beta,HashSet<Object> telModel){
		TransitMatrix tm2=(TransitMatrix) tm.clone();
		removeTerminalPageRank(tm, iter, delt);//计算基础PageRank
		removeTerminalTeleportPageRank(tm2, iter, delt, beta, telModel);//计算TrustRank
		for(Point p:tm.points){
			for(Point p2:tm2.points){
				if(p.source.equals(p2.source)){
					p.nextPageRank=(p.pageRank-p2.pageRank)/p.pageRank;
				}
			}
		}
	}
}
