import java.text.DecimalFormat;
import java.util.Vector;

/**
 * AdaBoost算法，算法描述见书Foundations of Machine Learning P122
 * 
 * @author pstar
 * 
 */
public class AdaBoost {
	int[][] matrix = new int[8][8];// 矩阵，行代表基分类器，列代表测试点
	Point[] point;
	BaseClassifiers[] bc;

	Vector<Integer> h = new Vector<Integer>();
	Vector<Double> a = new Vector<Double>();

	//预测点的label
	public int judge(Point p) {
		double result = 0.0;
		for (int i = 0; i < h.size(); i++) {
			double temp = a.get(i) * bc[h.get(i)].classify(p);
			result += temp;
		}
		return result > 0 ? 1 : -1;
	}

	// 这个函数在对象生成后就应该被调用，但是参数由用户输入，所以没有把它放到生成函数中
	public void adaboost(int T, double delt) {
		int len = this.point.length;
		double[] D = new double[len];// 每个点的 权重
		// 初始时每个点权重应满足均匀分布
		for (int i = 0; i < len; i++) {
			D[i] = 1.0 / len;
		}
		for (int t = 1; t < T; t++) {
			double error = chooseHypothese(D);
			if (Math.abs(error - 0.5) < delt) {
				break;
			}
			double at = 0.5 * Math.log((1 - error) / error);
			a.add(at);
			double z = 2 * Math.sqrt(error * (1 - error));
			for (int i = 0; i < len; i++) {
				D[i] = D[i]
						* Math.exp(-1 * at * point[i].label
								* matrix[h.get(h.size() - 1)][i]) / z;
			}
		}

		DecimalFormat df = new DecimalFormat("0.00");
		for (Integer i : h) {
			System.out.print(i + "\t\t");
		}
		System.out.println();
		for (Double d : a) {
			String s = df.format(d);
			System.out.print(s + "\t\t");
		}
	}

	// 选择最小错误的分类器，返回错误值
	public double chooseHypothese(double[] D) {
		int min = Integer.MAX_VALUE;
		double error = 1.1;
		for (int i = 0; i < bc.length; i++) {
			double temp = 0.0;
			for (int j = 0; j < point.length; j++) {
				if (bc[i].classify(point[j]) != point[j].label) {
					temp += D[j];
				}
			}
			if (temp < error) {
				error = temp;
				min = i;
			}
		}
		h.add(min);
		return error;
	}

	public AdaBoost(Point[] point, BaseClassifiers[] bc) {
		this.point = point;
		this.bc = bc;
		// 计算矩阵，行代表基分类器，列代表测试点，值为1则表示分类器对测试点分类为1，-1类似
		for (int i = 0; i < bc.length; i++) {// 遍历分类器
			for (int j = 0; j < point.length; j++) {// 遍历测试点
				matrix[i][j] = bc[i].classify(point[j]);
			}
		}
	}
}
