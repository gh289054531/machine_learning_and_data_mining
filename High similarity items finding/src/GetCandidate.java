import java.util.HashSet;

/**
 * 获取候选相似项
 * 
 * @author root
 * 
 */
public class GetCandidate {
	// 输入探测串，返回所有候选项
	public static HashSet<String> getCandidate(String in) {
		HashSet<Integer> ids = new HashSet<Integer>();
		int prefixToConsider = (int) ((1 - MakeIndex.J) * in.length() + 1);
		for (int i = 1; i <= prefixToConsider; i++) {
			int p = in.length() - i;
			// 假定p>=q
			for (int q = p; q >= 1; q--) {
				double temp = (q + 1) / MakeIndex.J - in.length() + 1;
				if (temp < 1) {
					break;
				}
				int maxj = (int) temp;
				for (int j = 1; j <= maxj; j++) {
					IndexKey ik = new IndexKey(in.charAt(i - 1), j, q);
					System.out.println(in.charAt(i - 1) + "\t" + j + "\t" + q);
					HashSet<Integer> idSet = MakeIndex.INDEX.get(ik.toString());
					if (idSet != null) {
						ids.addAll(idSet);
					}
				}
			}
			// 假定p<q
			for (int q = p + 1;; q++) {
				double temp = (in.length() - i + 1) / MakeIndex.J - i - q + 1;
				if (temp < 1) {
					break;
				}
				int maxj = (int) temp;
				for (int j = 1; j <= maxj; j++) {
					IndexKey ik = new IndexKey(in.charAt(i - 1), j, q);
					System.out.println(in.charAt(i - 1) + "\t" + j + "\t" + q);
					HashSet<Integer> idSet = MakeIndex.INDEX.get(ik.toString());
					if (idSet != null) {
						ids.addAll(idSet);
					}
				}
			}
		}
		HashSet<String> result = new HashSet<String>();
		for (Integer id : ids) {
			String s = MakeIndex.INPUTSTRING.get(id).value;
			result.add(s);
		}
		return result;
	}
}
