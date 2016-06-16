package com.fisher.core.util.basetype.strings;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.fisher.core.util.common.EmptyValue;

public class StringUtil {
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

	// 字符串拼接

	/**
	 * 将数组元素之间加上拼接符号转成字符串
	 * 
	 * @param list
	 * @param separator
	 * @return
	 */
	public static <T> String join(List<T> list, String separator) {
		StringBuilder sb = new StringBuilder();
		if (list == null || list.size() == 0) {
			return sb.toString();
		}
		int len = list.size();
		if (separator == null || separator.length() == 0) {
			for (int i = 0; i < len; i++) {
				sb.append(list.get(i));
			}
		} else {
			for (int i = 0; i < len; i++) {
				sb.append(list.get(i));
				if ((len - 1) != i) {
					sb.append(separator);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 将数组元素之间加上拼接符号转成字符串
	 * 
	 * @param list
	 * @param separator
	 * @return
	 */
	public static String join(String[] list, String separator) {
		StringBuilder sb = new StringBuilder();
		if (list == null || list.length == 0) {
			return sb.toString();
		}
		int len = list.length;
		if (separator == null || separator.length() == 0) {
			for (int i = 0; i < len; i++) {
				sb.append(list[i]);
			}
		} else {
			for (int i = 0; i < len; i++) {
				sb.append(list[i]);
				if ((len - 1) != i) {
					sb.append(separator);
				}
			}
		}
		return sb.toString();
	}

	// 字符串拼接
	// string byte转换
	/**
	 * String to Byte[],并进行编码
	 * 
	 * @param str
	 * @param charset
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] toBytes(String str, String charset)
			throws UnsupportedEncodingException {
		byte[] bts = null;
		if (charset.toUpperCase().equals(EncodingString.mUTF8Name)) {
			bts = toBytes(str);
		} else {
			bts = str.getBytes(charset);
		}
		return bts;
	}

	/**
	 * String to Byte[],并进行编码
	 * 
	 * @param str
	 * @param charset
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] toBytes(String str, Charset charset)
			throws UnsupportedEncodingException {
		byte[] bts = null;
		if (charset.name().equals(EncodingString.mUTF8Name)) {
			bts = toBytes(str);
		} else {
			bts = str.getBytes(charset);
		}
		return bts;
	}

	/**
	 * String to Byte[],并进行编码,utf-8格式
	 * 
	 * @param str
	 *            String数据
	 * @return byte[]数组
	 */
	public static byte[] toBytes(String str) {
		byte[] bts = toUTF8ByteArray(str);
		return bts;
	}

	/**
	 * byte[]转化为String数据
	 * 
	 * @param bts
	 *            byte[]数据
	 * @param charset
	 *            编码格式
	 * @return String数据
	 */
	public static String fromBytes(byte[] bts, Charset charset) {
		String str = EmptyValue.emptyString;
		if (charset.name().equals(EncodingString.mUTF8Name)) {
			str = fromBytes(bts);
		} else {
			str = new String(bts, charset);
		}
		return str;
	}

	/**
	 * byte[]转化为String数据,默认utf-8字符格式
	 * 
	 * @param bts
	 *            byte[]数据
	 * @return String数据
	 */
	public static String fromBytes(byte[] bts) {
		String str = fromUTF8ByteArray(bts);
		return str;
	}

	/**
	 * 获取utf8字符串
	 * 
	 * @param bytes
	 * @return
	 */
	public static String fromUTF8ByteArray(byte[] bytes) {
		int i = 0;
		int length = 0;
		while (i < bytes.length) {
			length++;
			if ((bytes[i] & 0xf0) == 0xf0) {
				// surrogate pair
				length++;
				i += 4;
			} else if ((bytes[i] & 0xe0) == 0xe0) {
				i += 3;
			} else if ((bytes[i] & 0xc0) == 0xc0) {
				i += 2;
			} else {
				i += 1;
			}
		}
		char[] cs = new char[length];
		i = 0;
		length = 0;
		while (i < bytes.length) {
			char ch;
			if ((bytes[i] & 0xf0) == 0xf0) {
				int codePoint = ((bytes[i] & 0x03) << 18)
						| ((bytes[i + 1] & 0x3F) << 12)
						| ((bytes[i + 2] & 0x3F) << 6) | (bytes[i + 3] & 0x3F);
				int U = codePoint - 0x10000;
				char W1 = (char) (0xD800 | (U >> 10));
				char W2 = (char) (0xDC00 | (U & 0x3FF));
				cs[length++] = W1;
				ch = W2;
				i += 4;
			} else if ((bytes[i] & 0xe0) == 0xe0) {
				ch = (char) (((bytes[i] & 0x0f) << 12)
						| ((bytes[i + 1] & 0x3f) << 6) | (bytes[i + 2] & 0x3f));
				i += 3;
			} else if ((bytes[i] & 0xd0) == 0xd0) {
				ch = (char) (((bytes[i] & 0x1f) << 6) | (bytes[i + 1] & 0x3f));
				i += 2;
			} else if ((bytes[i] & 0xc0) == 0xc0) {
				ch = (char) (((bytes[i] & 0x1f) << 6) | (bytes[i + 1] & 0x3f));
				i += 2;
			} else {
				ch = (char) (bytes[i] & 0xff);
				i += 1;
			}
			cs[length++] = ch;
		}
		return new String(cs);
	}

	/**
	 * 转换为utf8编码
	 * 
	 * @param string
	 * @return
	 */
	public static byte[] toUTF8ByteArray(String string) {
		return toUTF8ByteArray(string.toCharArray());
	}

	public static byte[] toUTF8ByteArray(char[] string) {
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		char[] c = string;
		int i = 0;
		while (i < c.length) {
			char ch = c[i];
			if (ch < 0x0080) {
				bOut.write(ch);
			} else if (ch < 0x0800) {
				bOut.write(0xc0 | (ch >> 6));
				bOut.write(0x80 | (ch & 0x3f));
			}
			// surrogate pair
			else if (ch >= 0xD800 && ch <= 0xDFFF) {
				// in error - can only happen, if the Java String class has a
				// bug.
				if (i + 1 >= c.length) {
					throw new IllegalStateException("invalid UTF-16 codepoint");
				}
				char W1 = ch;
				ch = c[++i];
				char W2 = ch;
				// in error - can only happen, if the Java String class has a
				// bug.
				if (W1 > 0xDBFF) {
					throw new IllegalStateException("invalid UTF-16 codepoint");
				}
				int codePoint = (((W1 & 0x03FF) << 10) | (W2 & 0x03FF)) + 0x10000;
				bOut.write(0xf0 | (codePoint >> 18));
				bOut.write(0x80 | ((codePoint >> 12) & 0x3F));
				bOut.write(0x80 | ((codePoint >> 6) & 0x3F));
				bOut.write(0x80 | (codePoint & 0x3F));
			} else {
				bOut.write(0xe0 | (ch >> 12));
				bOut.write(0x80 | ((ch >> 6) & 0x3F));
				bOut.write(0x80 | (ch & 0x3F));
			}
			i++;
		}
		return bOut.toByteArray();
	}

	public static byte[] toByteArray(char[] chars) {
		byte[] bytes = new byte[chars.length];
		for (int i = 0; i != bytes.length; i++) {
			bytes[i] = (byte) chars[i];
		}
		return bytes;
	}

	public static byte[] toByteArray(String string) {
		byte[] bytes = new byte[string.length()];
		for (int i = 0; i != bytes.length; i++) {
			char ch = string.charAt(i);
			bytes[i] = (byte) ch;
		}
		return bytes;
	}

	// string byte转换
	// 大小写转换
	/**
	 * A locale independent version of toUpperCase.
	 * 
	 * @param string
	 *            input to be converted
	 * @return a US Ascii uppercase version
	 */
	public static String toUpperCase(String string) {
		boolean changed = false;
		char[] chars = string.toCharArray();
		for (int i = 0; i != chars.length; i++) {
			char ch = chars[i];
			if ('a' <= ch && 'z' >= ch) {
				changed = true;
				chars[i] = (char) (ch - 'a' + 'A');
			}
		}
		if (changed) {
			return new String(chars);
		}
		return string;
	}

	/**
	 * A locale independent version of toLowerCase.
	 * 
	 * @param string
	 *            input to be converted
	 * @return a US ASCII lowercase version
	 */
	public static String toLowerCase(String string) {
		boolean changed = false;
		char[] chars = string.toCharArray();
		for (int i = 0; i != chars.length; i++) {
			char ch = chars[i];
			if ('A' <= ch && 'Z' >= ch) {
				changed = true;
				chars[i] = (char) (ch - 'A' + 'a');
			}
		}
		if (changed) {
			return new String(chars);
		}
		return string;
	}

	// 大小写转换
	// 字符串分割
	/**
	 * 根据delimiter进行数据截取，ignoreEmpty=true 过滤结果集中的前后空字符串, ignoreEmpty=false,等同于调用
	 * {@link split(String input, char delimiter)}
	 * 
	 * @param input
	 * @param delimiter
	 * @param isIgnoreEmpty
	 * @return
	 */
	public static String[] split(String input, char delimiter,
			boolean ignoreEmpty) {
		if (input == null) {
			return new String[0];
		}
		Vector<String> v = new Vector<String>();
		boolean moreTokens = true;
		String subString;
		while (moreTokens) {
			int tokenLocation = input.indexOf(delimiter);
			if (tokenLocation == 0) {
				subString = input.substring(0, tokenLocation);
				if (!ignoreEmpty) {
					v.addElement(subString);
				}
				input = input.substring(tokenLocation + 1);
			} else if (tokenLocation > 0) {
				subString = input.substring(0, tokenLocation);
				v.addElement(subString);
				input = input.substring(tokenLocation + 1);
			} else {
				moreTokens = false;
				if (!ignoreEmpty || input.length() > 0) {
					v.addElement(input);
				}
			}
		}
		String[] res = new String[v.size()];
		for (int i = 0; i != res.length; i++) {
			res[i] = (String) v.elementAt(i);
		}
		return res;
	}

	/**
	 * 根据delimiter进行数据截取,如果input=""，则返回String[]{""},如果输入为null,则返回String[0]
	 * 
	 * @param input
	 * @param delimiter
	 * @return
	 */
	public static String[] split(String input, char delimiter) {
		return split(input, delimiter, false);
	}

	// 字符串分割
	// 字符串替换
	/**
	 * 进行字符串替换，性能比String自带的高
	 * 
	 * @param line
	 * @param oldString
	 * @param newString
	 * @return
	 */
	public static String replace(String line, String oldString, String newString) {
		if (line == null) {
			return null;
		}
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	/**
	 * 进行字符串替换，性能比String自带的高 忽略大小写
	 * 
	 * @param line
	 * @param oldString
	 * @param newString
	 * @return
	 */
	public static final String replaceIgnoreCase(String line, String oldString,
			String newString) {
		if (line == null) {
			return null;
		}
		String lcLine = line.toLowerCase();
		String lcOldString = oldString.toLowerCase();
		return replace(lcLine, lcOldString, newString);
	}

	/**
	 * 将string转为integer数组
	 * 
	 * @param str
	 * @return
	 */
	public static Integer[] parseToIntegerArray(String str) {
		if (EmptyValue.isNullOrWhiteSpace(str)) {
			return null;
		}
		String[] tmp = StringUtil.split(str, ',', true);
		Integer[] result = new Integer[tmp.length];
		for (int i = 0; i < tmp.length; i++) {
			result[i] = Integer.parseInt(tmp[i]);
		}
		return result;
	}

	/**
	 * 将string转为integer集合
	 * 
	 * @param str
	 * @return
	 */
	public static List<Integer> parseToIntegerList(String str) {
		if (EmptyValue.isNullOrWhiteSpace(str)) {
			return null;
		}
		String[] tmp = StringUtil.split(str, ',', true);
		List<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < tmp.length; i++) {
			result.add(Integer.parseInt(tmp[i]));
		}
		return result;
	}
}
