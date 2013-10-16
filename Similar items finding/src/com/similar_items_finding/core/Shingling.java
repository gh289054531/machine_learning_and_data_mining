package com.similar_items_finding.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Vector;

/**
 * Shingling算法，从网页提取文本，得到shingles，再hash
 * @author pstar
 *
 */
public class Shingling {
	final static int K = 4;// k-shingle设置k为4
	File f = null;

	public Shingling(String filepath) {
		f = new File(filepath);
		if (!f.exists()) {
			System.out.println("File not exist:" + filepath);
			System.exit(0);
		}
	}

	// 预处理，除去web页面标签得到文本内容，再提出所有shingle
	Vector<Integer> Preprocess() throws IOException {
		StringBuilder content = new StringBuilder();
		int temp;
		Reader in = null;
		in = new FileReader(f);
		while ((temp = in.read()) != -1) {
			content.append((char) temp);
		}
		in.close();
		
		//判断编码
		int encodeStart = content.indexOf("charset");
		String encode = content.substring(encodeStart, encodeStart + 20);
		if(encode.contains("GBK") || encode.contains("gbk")){
			in=new InputStreamReader(new FileInputStream(f),"GBK");
			content.delete(0, content.length());
			while ((temp = in.read()) != -1) {
				content.append((char) temp);
			}
			in.close();
		}

		int begin = content.indexOf("<body");
		int end = content.indexOf("</body>") + 7;
		content = new StringBuilder(content.substring(begin, end));// 提取body之间内容
		content = Html2Text.Html2Text(content);

		Vector<Integer> shingles = new Vector<Integer>();
		int shingle;
		for (int i = 0; i <= content.length() - K; i++) {
			shingle = content.substring(i, i + K).hashCode();
			if (!shingles.contains(shingle)) {
				shingles.add(shingle);
			}
		}
		return shingles;
	}
}
