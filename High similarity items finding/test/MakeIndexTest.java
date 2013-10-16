import static org.junit.Assert.*;

import org.junit.Test;


public class MakeIndexTest {

	@Test
	public void InsertSortTest() {
		String test="fshaghgfmyper";
		String test1="pstar";
		String result=MakeIndex.InsertSort(test);
		String result1=MakeIndex.InsertSort(test1);
		
		
		assertEquals("aeffgghhmprsy",result);
		assertEquals("aprst",result1);
		
	}
	@Test
	public void AddToIndexTest(){
		String test=MakeIndex.InsertSort("acdefghijk");
		String test1=MakeIndex.InsertSort("acdefghijk");
		MakeIndex.AddToIndex(test);
		MakeIndex.AddToIndex(test1);
	}

}
