package com.fisher.core.util.common;

import java.util.List;

public class EmptyValue {

	public static final String emptyString = "";
	public static final byte[] emptyBytes = new byte[0];

	/**
	 * 判断字符串为null或者为""或者由空白组成,返回true;否则返回false
	 * 
	 * @param instr
	 * @return
	 */
	public static Boolean isNullOrWhiteSpace(String instr) {
		if (instr == null || instr.trim().length() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断byte数组为null或者为长度为0,返回true;否则返回false
	 * 
	 * @param bts
	 * @return
	 */
	public static boolean isNullOrZeroBytes(byte[] bts) {
		return bts == null || bts.length == 0;
	}

	/**
	 * 判断list是否为空
	 * 
	 * @param bts
	 * @return
	 */
	public static boolean isNullOrEmptyList(List<?> target) {
		return target == null || target.size() == 0;
	}

	/**
	 * 判断list是否为空
	 * 
	 * @param bts
	 * @return
	 */
	public static <T> boolean isNullOrEmptyArray(T[] target) {
		return target == null || target.length == 0;
	}
}
