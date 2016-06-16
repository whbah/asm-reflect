package com.fisher.core.asm;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.fisher.core.util.basetype.strings.StringUtil;

/**
 * 通过Asm做函数的反射调用
 * 
 * @author Administrator
 * 
 */
public class AsmMethodRefUtil {

	/**
	 * 保存动态执行函数的静态Class对象锁
	 */
	// func锁
	private static Lock funcLock = new ReentrantLock();
	private static HashMap<String, ASMFunctionInvokerImpl> funcMap = new HashMap<String, ASMFunctionInvokerImpl>();
	// action锁
	private static Lock actionLock = new ReentrantLock();
	private static HashMap<String, ASMActionInvokerImpl> actionMap = new HashMap<String, ASMActionInvokerImpl>();
	private static final String mSplitStr = "_";

	/**
	 * 动态执行非静态函数,返回函数执行结果
	 * 
	 * @param cls
	 * @param methodName
	 * @param types
	 * @param objs
	 * @return 执行函数后返回对象
	 * @throws Exception
	 */
	public static Object invokeAsmFunc(Class<?> cls, String methodName,
			Object[] args, Class<?>[] argsTypes) throws Exception {
		String targetName = getClassName(cls.getName());
		String key = targetName + mSplitStr + methodName;
		ASMFunctionInvokerImpl impl;
		if (!funcMap.containsKey(key)) {
			funcLock.lock();
			try {
				if (!funcMap.containsKey(key)) {
					impl = new ASMByteBuilder().createASMFunctionInvoker(key,
							cls, methodName, argsTypes);
					funcMap.put(key, impl);
				}
			} finally {
				funcLock.unlock();
			}
		}
		impl = funcMap.get(key);
		return impl.invokeFunction(args);
	}

	/**
	 * 动态执行非静态函数,无返回值
	 * 
	 * @param clsName
	 * @param methodName
	 * @param types
	 * @param objs
	 * @return 执行函数后返回对象
	 * @throws Exception
	 */
	public static void invokeAsmAction(Class<?> cls, String methodName,
			Object[] args, Class<?>[] argsTypes) throws Exception {
		String targetName = getClassName(cls.getName());
		String key = targetName + mSplitStr + methodName;
		ASMActionInvokerImpl impl;
		if (!actionMap.containsKey(key)) {
			actionLock.lock();
			try {
				if (!actionMap.containsKey(key)) {
					impl = new ASMByteBuilder().createASMActionInvokerImpl(key,
							cls, methodName, argsTypes);
					actionMap.put(key, impl);
				}
			} finally {
				actionLock.unlock();
			}
		}
		impl = actionMap.get(key);
		impl.invokeAction(args);
	}

	/**
	 * 获取Class全名
	 * 
	 * @param clsName
	 * @return
	 */
	private static String getClassName(String clsName) {
		String targetName = null;
		if (clsName.contains(".")) {
			targetName = StringUtil.replace(clsName, ".", "");
		} else {
			targetName = StringUtil.replace(clsName, "/", "");
		}
		return targetName;
	}
}
