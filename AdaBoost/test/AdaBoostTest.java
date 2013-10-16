import static org.junit.Assert.*;

import org.junit.Test;

public class AdaBoostTest {

	@Test
	public void test() {

		Point[] point = new Point[8];
		point[0] = new Point(0.5, 0.5, -1);
		point[1] = new Point(1.5, 0.5, -1);
		point[2] = new Point(4.5, 0.5, 1);
		point[3] = new Point(0.5, 1.5, -1);
		point[4] = new Point(2.5, 2.5, -1);
		point[5] = new Point(3.5, 3.5, 1);
		point[6] = new Point(4.5, 3.5, 1);
		point[7] = new Point(2.5, 4.5, 1);

		// 弱分类器为x=1,x=2,x=3,x=4,y=1,y=2,y=3,y=4
		BaseClassifiers[] bc = new BaseClassifiers[8];
		bc[0] = new BaseClassifiers('x', 1);
		bc[1] = new BaseClassifiers('x', 2);
		bc[2] = new BaseClassifiers('x', 3);
		bc[3] = new BaseClassifiers('x', 4);
		bc[4] = new BaseClassifiers('y', 1);
		bc[5] = new BaseClassifiers('y', 2);
		bc[6] = new BaseClassifiers('y', 3);
		bc[7] = new BaseClassifiers('y', 4);

		AdaBoost ada = new AdaBoost(point, bc);
		ada.adaboost(10, 0.01);
		assertEquals(-1, ada.judge(point[0]));
		assertEquals(-1, ada.judge(point[1]));
		assertEquals(1, ada.judge(point[2]));
		assertEquals(-1, ada.judge(point[3]));
		assertEquals(-1, ada.judge(point[4]));
		assertEquals(1, ada.judge(point[5]));
		assertEquals(1, ada.judge(point[6]));
		assertEquals(1, ada.judge(point[7]));

		//测试通过，说明对于这个例子，ADABOOST得到的分类器不会有错误出现
		
		
	}

}
