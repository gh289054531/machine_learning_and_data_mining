package com.similar_items_finding.core;

import java.util.Vector;

/**
 * Simhash算法，计算shingles的simhash指纹
 * @author pstar
 *
 */

public class Simhash {
	public static int SIMHASH(Vector<Integer> shingles) {
		int[] temp = new int[32];
		for (Integer shingle : shingles) {
			for (int i = 0; i < 32; i++) {
				int bit = shingle % 2;
				if (bit == 0) {
					temp[31 - i] -= 1;
				} else if (bit == 1) {
					temp[31 - i] += 1;
				}
				shingle >>= 1;
			}
		}
		for (int i = 0; i < 32; i++) {
			if (temp[i] > 0) {
				temp[i] = 1;
			} else {
				temp[i] = 0;
			}
		}
		int v = 0;
		for (int i = 0; i < 32; i++) {
			v = v * 2 + temp[i];
		}
		return v;
	}
}
