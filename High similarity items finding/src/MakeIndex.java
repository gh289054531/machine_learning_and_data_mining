import java.util.HashMap;
import java.util.HashSet;

/**
 * 为输入建立索引
 * 
 * @author root
 * 
 */
public class MakeIndex {
	public final static double J = 0.799999;

	public static int COUNT = 0; // 为每个输入分配id
	public static HashMap<String, HashSet<Integer>> INDEX = new HashMap<String, HashSet<Integer>>(); // 索引
	public static HashMap<Integer, InputString> INPUTSTRING = new HashMap<Integer, InputString>(); // 输入集

	/*
	 * 直接插入排序，时间复杂度最优O(1)最差O(N^2)，空间复杂度O(1)
	 * 原理：将数组分为无序区和有序区两个区，然后不断将无序区的第一个元素按大小顺序插入到有序区中去，最终将所有无序区元素都移动到有序区完成排序。
	 * 要点：设立哨兵，作为临时存储和判断数组边界之用。
	 */
	public static String InsertSort(String in) {
		char sentry; // 哨兵
		char[] result = in.toCharArray();
		for (int i = 0; i < in.length() - 1; i++) {
			int j = i + 1;
			sentry = result[j];
			while (i >= 0 && sentry < result[i]) {
				result[i + 1] = result[i];
				i--;
			}
			result[i + 1] = sentry;
			i = j - 1;
		}
		return new String(result);
	}

	//为输入串产生索引
	public static void AddToIndex(String in) {
		InputString is = new InputString(in);
		INPUTSTRING.put(is.id, is);
		int p = (int) ((1 - J) * in.length() + 1); // p是前缀长度
		int len = in.length();
		for (int i = 1; i <= p; i++) {
			String ik = new IndexKey(is.value.charAt(i-1), i, len - i).toString();
			if (INDEX.containsKey(ik)) {
				HashSet<Integer> set = INDEX.get(ik);
				set.add(is.id);
			}else{
				HashSet<Integer> set=new HashSet<Integer>();
				set.add(is.id);
				INDEX.put(ik, set);
			}
		}
	}
}

// 输入串数据结构
class InputString {
	int id;
	String value;

	public InputString(String value) {
		this.value = value;
		this.id = MakeIndex.COUNT;
		MakeIndex.COUNT++;
	}
}

// 索引的key的数据结构
class IndexKey {
	char charValue;
	int position;
	int postfixLength;

	public IndexKey(char charValue, int position, int postfixLength) {
		this.charValue = charValue;
		this.position = position;
		this.postfixLength = postfixLength;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("");
		sb.append(charValue).append(position).append(postfixLength);
		return sb.toString();
	}
}
