package com.similar_items_finding.core;

import java.io.IOException;
import java.util.Vector;

import org.junit.Test;

/**
 * 测试通过
 * @author root
 *
 */
public class WebPageTest {
	String path="dataset/百度一下，你就知道.htm";

	@Test
	public void test() throws IOException {
		Shingling wp=new Shingling(path);
		Vector<Integer> shingles=wp.Preprocess();
		for(Integer s:shingles){
			System.out.print(s+"\t");
		}
	}

}
