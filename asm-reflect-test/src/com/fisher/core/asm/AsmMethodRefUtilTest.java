package com.fisher.core.asm;

import org.junit.Assert;
import org.junit.Test;

import com.fisher.core.reflect.AsmMethodRefUtil;

public class AsmMethodRefUtilTest {
	@Test
	public void testInvokeAction() throws Exception {
		Class<?>[] Types = new Class<?>[3];
		Types[0] = String.class;
 
		Types[2] = String.class;
		Types[1] = String.class;
		String[] args = new String[3];
		args[0] = "this";
		args[1] = "this";
		args[2] = "this";
		AsmMethodRefUtilTest test = new AsmMethodRefUtilTest();
		AsmMethodRefUtil.invokeAsmAction(AsmMethodRefUtilTest.class, "callVoid", null, null);
		AsmMethodRefUtil.invokeAsmAction(AsmMethodRefUtilTest.class, "callVoid", null, null,
				test);
		AsmMethodRefUtil.invokeAsmAction(AsmMethodRefUtilTest.class.getName(), "callVoid",
				null, null, test);
		AsmMethodRefUtil.invokeAsmAction(AsmMethodRefUtilTest.class.getName(), "callVoid",
				null, null, test);

		AsmMethodRefUtil.invokeAsmAction(AsmMethodRefUtilTest.class, "call", args, Types);
		AsmMethodRefUtil.invokeAsmAction(AsmMethodRefUtilTest.class, "call", args, Types,
				test);
		AsmMethodRefUtil.invokeAsmAction(AsmMethodRefUtilTest.class.getName(), "call", args,
				Types, test);
		AsmMethodRefUtil.invokeAsmAction(AsmMethodRefUtilTest.class.getName(), "call", args,
				Types, test);
	}

	@Test
	public void testInvokeFunction() throws Exception {
		Class<?>[] Types = new Class<?>[3];
		Types[0] = String.class;

		Types[2] = String.class;
		Types[1] = String.class;
		String[] args = new String[3];
		args[0] = "this";
		args[1] = "this";
		args[2] = "this";
		AsmMethodRefUtilTest test = new AsmMethodRefUtilTest();
		Assert.assertEquals(test.getInteger(), AsmMethodRefUtil.invokeAsmFunc(
				AsmMethodRefUtilTest.class, "getInteger", null, null));
		Assert.assertEquals(test.getInteger(), AsmMethodRefUtil.invokeAsmFunc(
				AsmMethodRefUtilTest.class, "getInteger", null, null, test));
		Assert.assertEquals(test.getInteger(), AsmMethodRefUtil.invokeAsmFunc(
				AsmMethodRefUtilTest.class.getName(), "getInteger", null, null, test));
		Assert.assertEquals(test.getInteger(), AsmMethodRefUtil.invokeAsmFunc(
				AsmMethodRefUtilTest.class.getName(), "getInteger", null, null, test));

		AsmMethodRefUtil.invokeAsmFunc(AsmMethodRefUtilTest.class, "getBool", null, null);
		AsmMethodRefUtil.invokeAsmFunc(AsmMethodRefUtilTest.class, "getBool", null, null,
				test);
		AsmMethodRefUtil.invokeAsmFunc(AsmMethodRefUtilTest.class.getName(), "getBool",
				null, null, test);
		AsmMethodRefUtil.invokeAsmFunc(AsmMethodRefUtilTest.class.getName(), "getBool",
				null, null, test);

		AsmMethodRefUtil
				.invokeAsmFunc(AsmMethodRefUtilTest.class, "callString", args, Types);
		AsmMethodRefUtil.invokeAsmFunc(AsmMethodRefUtilTest.class, "callString", args,
				Types, test);
		AsmMethodRefUtil.invokeAsmFunc(AsmMethodRefUtilTest.class.getName(), "callString",
				args, Types, test);
		AsmMethodRefUtil.invokeAsmFunc(AsmMethodRefUtilTest.class.getName(), "callString",
				args, Types, test);
	}

	//

	public Integer getInteger() {
		return 1;
	}

	public int getInt() {
		return 1;
	}

	public boolean getBool() {
		return false;
	}

	public Boolean getBoolean() {
		return true;
	}

	public void callVoid() {
		System.out.println("call");
	}

	public String callS() {
		return "test";
	}

	public static void staticCallVoid() {
		System.out.println("call ");
	}

	public static String staticCallS() {
		return "test";
	}

	public Object callString(String value, String test, String v) {
		return value;
	}

	public void call(String value, String test, String test1) {
		System.out.println(value.toString() + test);
	}

	public static Object staticCallString(String value, String test, String v) {
		return 1;
	}

	public static void staticCall(String value, String test, String test1) {
		System.out.println(value.toString() + test);
	}

	public void static1Call(Object[] values) {
		staticCall((String) values[0], (String) values[1], (String) values[1]);
	}
}
