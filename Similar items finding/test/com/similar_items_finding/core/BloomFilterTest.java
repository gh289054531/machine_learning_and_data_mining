package com.similar_items_finding.core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BloomFilterTest {

	@Test
	public void testBloomFilter() {
		BloomFilter bf=new BloomFilter(0.01, 10);
		bf.add(100);
		bf.add(180);
		bf.add(3000);
		bf.add(6422);
		bf.add(45645);
		bf.add(55412);
		bf.add(30300);
		bf.add(64222);
		bf.add(456345);
		bf.add(554412);
		boolean b2=bf.isInBloomFilter(456345);//这是个伪正例
		boolean b1=bf.isInBloomFilter(45);//这是个伪正例
		boolean b3=bf.isInBloomFilter(455545);//这是个伪正例
		
		assertEquals(b2,true);
		assertEquals(b1,true);
		assertEquals(b3,false);
	}
}
