package com.fisher.core.util.basetype.bytes;

/**
 * KMP算法类
 */
public class KMPMatcher {

	/**
	 * 部分匹配值表
	 */
	private int[] partialMatch;
	private int patternLength;
	// 要比较的byte数组
	private byte[] pattern;

	public KMPMatcher(byte[] target) throws Exception {
		if (target == null || target.length == 0) {
			throw new Exception("target is null");
		}
		patternLength = target.length;
		pattern = target;
		partialMatch = computePartialMatch();
		computeReversePartialMatch();
	}

	/**
	 * 获取target在text中的索引
	 * 
	 * @param text
	 *            of type byte[]
	 * @param startIndex
	 *            of type int
	 * @return int
	 */
	public int indexOf(byte[] text, int startIndex) {
		int matchPoint = -1;
		int j = 0;
		if (text.length == 0 || startIndex > text.length)
			return matchPoint;
		for (int i = startIndex; i < text.length; i++) {
			while (j > 0 && pattern[j] != text[i]) {
				j = partialMatch[j - 1];
			}
			if (pattern[j] == text[i]) {
				j++;
			}
			if (j == pattern.length) {
				matchPoint = i - pattern.length + 1;
				return matchPoint;
			}
		}
		return matchPoint;
	}

	/**
	 * 查询数组所在的最后一个位置，如果数组不存在，则性能较低
	 * 
	 * @param source
	 * @param startIndex
	 * @return
	 */
	public int lastIndexOf(byte[] source, int startIndex) {
		int length = source.length;
		int matchPoint = -1;
		if (source.length == 0 || startIndex > source.length) {
			return matchPoint;
		}
		// 如果source的长度较长，则从后往前匹配
		boolean isReverse = length / patternLength > 1;
		if (isReverse) {
			// 反序查询
			int j = 0;
			for (int i = startIndex; i < length; i++) {
				int reverseJIndex = patternLength - j - 1;
				int reverseIIndex = length - i - 1;
				while (j > 0 && pattern[reverseJIndex] != source[reverseIIndex]) {
					j = partialMatch[j - 1];
				}
				if (pattern[reverseJIndex] == source[reverseIIndex]) {
					j++;
				}
				if (j == patternLength) {
					matchPoint = reverseIIndex;
					return matchPoint;
				}
			}
		} else {
			// 正序
			int j = 0;
			for (int i = startIndex; i < length; i++) {
				while (j > 0 && pattern[j] != source[i]) {
					j = partialMatch[j - 1];
				}
				if (pattern[j] == source[i]) {
					j++;
				}
				if (j == pattern.length) {
					matchPoint = i - pattern.length + 1;
					if ((length - i) > pattern.length) {
						j = 0;
						continue;
					}
					return matchPoint;
				}
			}
		}
		return matchPoint;
	}

	/**
	 * 计算部分匹配值
	 */
	private int[] computePartialMatch() {
		int j = 0;
		int len = pattern.length;
		int[] failure = new int[len];
		for (int i = 1; i < len; i++) {
			while (j > 0 && pattern[j] != pattern[i]) {
				j = failure[j - 1];
			}
			if (pattern[j] == pattern[i]) {
				j++;
			}
			failure[i] = j;
		}
		return failure;
	}

	/**
	 * 倒序计算部分匹配值
	 */
	private int[] computeReversePartialMatch() {
		int j = 0;
		int[] failure = new int[patternLength];
		for (int i = 1; i < patternLength; i++) {
			int reverseJIndex = patternLength - j - 1;
			int reverseIIndex = patternLength - i - 1;
			while (j > 0 && pattern[reverseJIndex] != pattern[reverseIIndex]) {
				j = failure[j - 1];
			}
			if (pattern[reverseJIndex] == pattern[reverseIIndex]) {
				j++;
			}
			failure[i] = j;
		}
		return failure;
	}
}