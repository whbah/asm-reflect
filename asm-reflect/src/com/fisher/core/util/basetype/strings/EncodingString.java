package com.fisher.core.util.basetype.strings;

import java.nio.charset.Charset;

/**
 * 常用编字符集名称
 */
public class EncodingString {

	/**
	 * UTF-8名称
	 */
	public static final String mUTF8Name = "UTF-8";
	/**
	 * GB2312名称
	 */
	public static final String mGB2312Name = "GB2312";
	/**
	 * ASCII名称
	 */
	public static final String mASCIIName = "ASCII";

	public static final Charset UTF8Set = Charset.forName(mUTF8Name);
	public static final Charset GB2312Set = Charset.forName(mGB2312Name);
	public static final Charset ASCIISet = Charset.forName(mASCIIName);
}
