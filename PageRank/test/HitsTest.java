import static org.junit.Assert.*;

import org.junit.Test;


public class HitsTest {

	@Test
	public void hitsTest() {
		TransitMatrix tm = new TransitMatrix();//用于h=La，相当于L
		tm.add('A', 'B');
		tm.add('A', 'C');
		tm.add('A', 'D');
		tm.add('B', 'A');
		tm.add('B', 'D');
		tm.add('C', 'E');
		tm.add('D', 'B');
		tm.add('D', 'C');
		tm.add('E');
		
		TransitMatrix tm2 = new TransitMatrix();//用于a=LTh，相当于LT
		tm2.add('B', 'A');
		tm2.add('C', 'A');
		tm2.add('D', 'A');
		tm2.add('A', 'B');
		tm2.add('D', 'B');
		tm2.add('E', 'C');
		tm2.add('B', 'D');
		tm2.add('C', 'D');
		tm2.add('E');
		
		Hits hits=new Hits();
		hits.hits(tm, tm2, 100);
		tm.printPageRank();//输出导航度
		tm2.printPageRank();//输出权威度
		
	}

}
