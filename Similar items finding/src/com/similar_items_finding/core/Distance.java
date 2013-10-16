package com.similar_items_finding.core;

import java.util.Vector;

/**
 * 距离度量类
 * 
 * @author pstar
 * 
 */
public class Distance {

	// 计算Shingles的Jaccard距离
	public static double SHINGLES_JACCARD_DISTANCE(Vector<Integer> s1,
			Vector<Integer> s2) {
		int conj = 0;
		for (Integer s : s1) {
			if (s2.contains(s)) {
				conj++;
			}
		}
		int disj = s1.size() + s2.size();
		return ((double) conj) / disj;
	}
	
	// 计算SIMHASH的海明距离
	public static int HAMMING_DISTANCE(int a, int b) {
		int result = 0;
		for (int i = 0; i < 32; i++) {
			int bita = a % 2;
			int bitb = b % 2;
			if (bita != bitb) {
				result++;
			}
			a >>= 1;
			b >>= 1;
		}
		return result;
	}
	
	//计算在布隆过滤器中的shingles的比例
	public static double BLOOMFILTER_DISTANCE(Vector<Integer> s1,BloomFilter bf){
		int in = 0;
		for (Integer s : s1) {
			if (bf.isInBloomFilter(s)) {
				in++;
			}
		}
		return (double)in / s1.size();
	}
}
