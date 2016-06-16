package com.fisher.core.asm;

/**
 * 通过ASM调用无返回值非静态函数接口 函数传入参数类型为Object
 * 
 */
public interface ASMActionInvokerImpl {

	/**
	 * 传入参数类型为Object[]
	 * 
	 * @param args
	 */
	public void invokeAction(Object[] args);
}