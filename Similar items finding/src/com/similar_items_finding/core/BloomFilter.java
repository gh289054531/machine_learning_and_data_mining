package com.similar_items_finding.core;

/**
 * 可计数的布隆过滤器
 * 
 * @author pstar
 * 
 */
public class BloomFilter {
	int M; // bloom filter分配的位数
	int K; // 最佳hash函数个数
	int[] SEEDS = { 3, 7, 11, 13, 31, 37, 61, 73, 89, 97 };// 种子数可尽量多选几
	int[] FILTER;

	// delt是错误率,N是输入全集中独立元素数量
	public BloomFilter(double delt, int N) {
		M = (int) (N * Math.log(1 / delt) / Math.log(2) * 1.44 + 1);
		K = (int) (0.7 * M / N);
		FILTER = new int[M];
		System.out.println("BloomFilter参数配置");
		System.out.println("要求错误率：" + delt);
		System.out.println("预计全集独立元素数量：" + N);
		System.out.println("\t过滤器位数:" + M);
		System.out.println("\t最佳Hash函数个数:" + K);
	}

	// 使用第n个SEED产生hash值
	public int getHashValue(int shingle, int n) {
		StringBuilder str = new StringBuilder(Integer.toBinaryString(shingle));
		str = str.reverse();
		for (int i = str.length(); i < 32; i++) {
			str.append("0");
		}
		str = str.reverse();
		int result = 0;
		for (int i = 0; i < str.length(); i++) {
			result = SEEDS[n] * result + str.charAt(i) - '0';
			result %= M;
		}
		return result;
	}
	
	// 向布隆过滤器新加一个值
	public void add(int shingle) {
		for (int i = 0; i < K; i++) {
			int hashvalue = this.getHashValue(shingle, i);
			FILTER[hashvalue]++;
		}
	}

	// 从布隆过滤器中删除一个值
	public void delete(int shingle) {
		for (int i = 0; i < K; i++) {
			int hashvalue = this.getHashValue(shingle, i);
			FILTER[hashvalue]--;
		}
	}
	// 判断是否在过滤器中
	public boolean isInBloomFilter(int shingle) {
		for (int i = 0; i < K; i++) {
			int hashvalue = this.getHashValue(shingle, i);
			if (FILTER[hashvalue] == 0) {
				return false;
			}
		}
		return true;
	}
}
