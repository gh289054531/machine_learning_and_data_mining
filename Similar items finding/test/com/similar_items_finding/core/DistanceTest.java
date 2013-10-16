package com.similar_items_finding.core;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Vector;

import org.junit.Test;

public class DistanceTest {

	@Test
	public void SHINGLES_JACCARD_DISTANCE_test() throws IOException {
		String path1 = "dataset/林书豪24+6哈登29分 火箭胜爵士巩固西部第七- 中国日报网.htm";
		Shingling wp1 = new Shingling(path1);
		Vector<Integer> shingles1 = wp1.Preprocess();

		String path2 = "dataset/7成准星轰24+6送致命3连击 书豪摧毁小莫破围剿- Micro Reading.htm";
		Shingling wp2 = new Shingling(path2);
		Vector<Integer> shingles2 = wp2.Preprocess();

		String path3 = "dataset/全场最佳！林书豪高效24+6 10载NBA老兵被打急了_篮球-NBA_新浪竞技风暴_新浪网.htm";
		Shingling wp3 = new Shingling(path3);
		Vector<Integer> shingles3 = wp3.Preprocess();

		String path4 = "dataset/麦帅：林书豪拯救了火箭 爵士主帅：我们还活着_扬子晚报网.htm";
		Shingling wp4 = new Shingling(path4);
		Vector<Integer> shingles4 = wp4.Preprocess();

		String path5 = "dataset/麦帅：林书豪拯救了火箭 爵士主帅：我们还活着-东方体育-东方网.htm";
		Shingling wp5 = new Shingling(path5);
		Vector<Integer> shingles5 = wp5.Preprocess();

		double jaccardDis1 = Distance.SHINGLES_JACCARD_DISTANCE(shingles1,
				shingles2);
		double jaccardDis2 = Distance.SHINGLES_JACCARD_DISTANCE(shingles1,
				shingles3);
		double jaccardDis3 = Distance.SHINGLES_JACCARD_DISTANCE(shingles2,
				shingles3);
		double jaccardDis4 = Distance.SHINGLES_JACCARD_DISTANCE(shingles4,
				shingles5);
		System.out.println("Shingles的jaccard距离分别是：" + jaccardDis1 + "\t"
				+ jaccardDis2 + "\t" + jaccardDis3 + "\t" + jaccardDis4 + "\t");
	}

	@Test
	public void SIMHASH_HAMMING_DISTANCE_test() throws IOException {
		String path1 = "dataset/林书豪24+6哈登29分 火箭胜爵士巩固西部第七- 中国日报网.htm";
		Shingling wp1 = new Shingling(path1);
		Vector<Integer> shingles1 = wp1.Preprocess();
		int value1 = Simhash.SIMHASH(shingles1);

		String path2 = "dataset/7成准星轰24+6送致命3连击 书豪摧毁小莫破围剿- Micro Reading.htm";
		Shingling wp2 = new Shingling(path2);
		Vector<Integer> shingles2 = wp2.Preprocess();
		int value2 = Simhash.SIMHASH(shingles2);

		String path3 = "dataset/全场最佳！林书豪高效24+6 10载NBA老兵被打急了_篮球-NBA_新浪竞技风暴_新浪网.htm";
		Shingling wp3 = new Shingling(path3);
		Vector<Integer> shingles3 = wp3.Preprocess();
		int value3 = Simhash.SIMHASH(shingles3);

		String path4 = "dataset/麦帅：林书豪拯救了火箭 爵士主帅：我们还活着_扬子晚报网.htm";
		Shingling wp4 = new Shingling(path4);
		Vector<Integer> shingles4 = wp4.Preprocess();
		int value4 = Simhash.SIMHASH(shingles4);

		String path5 = "dataset/麦帅：林书豪拯救了火箭 爵士主帅：我们还活着-东方体育-东方网.htm";
		Shingling wp5 = new Shingling(path5);
		Vector<Integer> shingles5 = wp5.Preprocess();
		int value5 = Simhash.SIMHASH(shingles5);

		double HammingDis1 = Distance.HAMMING_DISTANCE(value1, value2);
		double HammingDis2 = Distance.HAMMING_DISTANCE(value1, value3);
		double HammingDis3 = Distance.HAMMING_DISTANCE(value2, value3);
		double HammingDis4 = Distance.HAMMING_DISTANCE(value4, value5);
		System.out.println("Shingles的Hamming距离分别是：" + HammingDis1 + "\t"
				+ HammingDis2 + "\t" + HammingDis3 + "\t" + HammingDis4);
	}

	@Test
	public void BLOOM_DISTANCE_test() throws IOException {
		BloomFilter bf = new BloomFilter(0.01, 16000);

		String path1 = "dataset/林书豪24+6哈登29分 火箭胜爵士巩固西部第七- 中国日报网.htm";
		Shingling wp1 = new Shingling(path1);
		Vector<Integer> shingles1 = wp1.Preprocess();
		for (Integer shingle : shingles1) {
			bf.add(shingle);
		}

		String path2 = "dataset/7成准星轰24+6送致命3连击 书豪摧毁小莫破围剿- Micro Reading.htm";
		Shingling wp2 = new Shingling(path2);
		Vector<Integer> shingles2 = wp2.Preprocess();
		for (Integer shingle : shingles2) {
			bf.add(shingle);
		}

		String path3 = "dataset/全场最佳！林书豪高效24+6 10载NBA老兵被打急了_篮球-NBA_新浪竞技风暴_新浪网.htm";
		Shingling wp3 = new Shingling(path3);
		Vector<Integer> shingles3 = wp3.Preprocess();
		for (Integer shingle : shingles3) {
			bf.add(shingle);
		}

		String path4 = "dataset/麦帅：林书豪拯救了火箭 爵士主帅：我们还活着_扬子晚报网.htm";
		Shingling wp4 = new Shingling(path4);
		Vector<Integer> shingles4 = wp4.Preprocess();

		String path5 = "dataset/麦帅：林书豪拯救了火箭 爵士主帅：我们还活着-东方体育-东方网.htm";
		Shingling wp5 = new Shingling(path5);
		Vector<Integer> shingles5 = wp5.Preprocess();

		double bloomDistance1 = Distance.BLOOMFILTER_DISTANCE(shingles4, bf);
		double bloomDistance2 = Distance.BLOOMFILTER_DISTANCE(shingles5, bf);
		DecimalFormat df = new DecimalFormat("0.000000");
		String b1 = df.format(bloomDistance1);
		String b2 = df.format(bloomDistance2);
		System.out.println("Shingles的HAMMING距离分别是：" + b1 + "\t" + b2);
	}
}
