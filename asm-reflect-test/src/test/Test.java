package test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import org.objectweb.asm.Type;

import com.fisher.core.asm.ASMActionInvokerImpl;
import com.fisher.core.asm.ASMByteBuilder;
import com.fisher.core.asm.ASMFunctionInvokerImpl;

public class Test {
	public Object test(Object instance, String[] args) {
 		return test(args[0], args[1], args[2]);
	}

	public static String test(String a, String b, String c) {
		return null;
	}
}
