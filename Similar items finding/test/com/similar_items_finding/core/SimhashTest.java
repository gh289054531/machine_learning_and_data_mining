package com.similar_items_finding.core;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

public class SimhashTest {

	@Test
	public void test() {
		Vector<Integer> shingles=new Vector<Integer>();
		shingles.add(4914351);
		System.out.println(Integer.toBinaryString(4914351));
		shingles.add(3244824);
		System.out.println(" "+Integer.toBinaryString(3244824));
		shingles.add(7055034);
		System.out.println(Integer.toBinaryString(7055034));
		int value=Simhash.SIMHASH(shingles);
		assertEquals(7055034, value);
	}

}
