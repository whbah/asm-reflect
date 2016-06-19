package com.fisher.core.asm;

/**
 * 通过ASM调用有返回值函数接口 函数传入参数类型为Object
 * 
 */
public interface ASMFunctionInvokerImpl {

	/**
	 * 传入参数类型为Object[],返回参数类型为Object
	 * 
	 * @param args
	 * @return
	 */
	public Object invokeFunction(Object instance, Object[] args);
}