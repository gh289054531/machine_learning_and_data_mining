import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Test;

public class PageRankTest {

	@Test
	public void basicPageRankTest() {
		TransitMatrix tm = new TransitMatrix();
		tm.add('A', 'B');
		tm.add('A', 'C');
		tm.add('A', 'D');
		tm.add('B', 'A');
		tm.add('B', 'D');
		tm.add('C', 'A');
		tm.add('D', 'B');
		tm.add('D', 'C');
		PageRank bpr = new PageRank();
		bpr.basicPageRank(tm, 50, 0.01);
		tm.printTransitMatrix();
	}

	@Test
	public void removeTerminalPageRankTest() {
		TransitMatrix tm = new TransitMatrix();
		tm.add('A', 'B');
		tm.add('A', 'C');
		tm.add('A', 'D');
		tm.add('B', 'A');
		tm.add('B', 'D');
		tm.add('C', 'E');
		tm.add('D', 'B');
		tm.add('D', 'C');
		tm.add('E');
		PageRank bpr = new PageRank();
		bpr.removeTerminalPageRank(tm, 50, 0.01);
		tm.printTransitMatrix();
	}

	@Test
	public void teleportPageRankTest() {
		TransitMatrix tm = new TransitMatrix();
		tm.add('A', 'B');
		tm.add('A', 'C');
		tm.add('A', 'D');
		tm.add('B', 'A');
		tm.add('B', 'D');
		tm.add('C', 'A');
		tm.add('D', 'B');
		tm.add('D', 'C');
		HashSet<Object> telModel = new HashSet<Object>();
		telModel.add('B');
		telModel.add('D');
		PageRank bpr = new PageRank();
		bpr.teleportPageRank(tm, 100, 0.001, 0.8, telModel);
		tm.printTransitMatrix();
	}

	@Test
	public void removeTerminalTeleportPageRankTest() {
		TransitMatrix tm = new TransitMatrix();
		tm.add('A', 'B');
		tm.add('A', 'C');
		tm.add('A', 'D');
		tm.add('B', 'A');
		tm.add('B', 'D');
		tm.add('C', 'A');
		tm.add('C', 'E');
		tm.add('D', 'B');
		tm.add('D', 'C');
		tm.add('E');
		HashSet<Object> telModel = new HashSet<Object>();
		telModel.add('B');
		telModel.add('D');
		PageRank bpr = new PageRank();
		bpr.removeTerminalTeleportPageRank(tm, 100, 0.001, 0.8, telModel);
		tm.printTransitMatrix();
	}

	@Test
	public void spamMassTest() {
		TransitMatrix tm = new TransitMatrix();
		tm.add('A', 'B');
		tm.add('A', 'C');
		tm.add('A', 'D');
		tm.add('B', 'A');
		tm.add('B', 'D');
		tm.add('C', 'A');
		tm.add('D', 'B');
		tm.add('D', 'C');
		HashSet<Object> telModel = new HashSet<Object>();
		telModel.add('B');
		telModel.add('D');
		PageRank bpr = new PageRank();
		bpr.spamMass(tm, 100, 0.001, 0.8, telModel);
		for (Point p : tm.points) {
			System.out.println(p.source.toString() + "\t" + p.nextPageRank);
		}
	}
}
