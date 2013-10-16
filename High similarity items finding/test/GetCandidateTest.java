import static org.junit.Assert.assertEquals;

import java.util.HashSet;

import org.junit.Test;


public class GetCandidateTest {

	@Test
	public void getCandidateTest() {
		
		String test=MakeIndex.InsertSort("acdefghijk");
		String test1=MakeIndex.InsertSort("acdefghijkl");
		String test2=MakeIndex.InsertSort("acdefghijklm");
		String test3=MakeIndex.InsertSort("acdefghijklmn");//不是候选
		String test4=MakeIndex.InsertSort("acdefghij");
		String test5=MakeIndex.InsertSort("acdefghi");
		String test6=MakeIndex.InsertSort("acdefgh"); //不是候选
		String test7=MakeIndex.InsertSort("acdefghijx");
		MakeIndex.AddToIndex(test);
		MakeIndex.AddToIndex(test1);
		MakeIndex.AddToIndex(test2);
		MakeIndex.AddToIndex(test3);
		MakeIndex.AddToIndex(test4);
		MakeIndex.AddToIndex(test5);
		MakeIndex.AddToIndex(test6);
		MakeIndex.AddToIndex(test7);
		
		HashSet<String> result=GetCandidate.getCandidate(test);
		
		boolean b1=result.contains(test3);
		boolean b2=result.contains(test6);
		boolean b3=result.contains(test5);
		
		assertEquals(false,b1);
		assertEquals(false,b2);
		assertEquals(true,b3);
	}

}
