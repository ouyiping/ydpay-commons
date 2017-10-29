package com.ydpay.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.ydpay.utils.MgException;

public class MethodReflect {

	public static Object invokeMethodGernaral(Object owner, String methodName,
			Object[] args) throws MgException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		// a.先获取对象所属的类
		@SuppressWarnings("rawtypes")
		Class ownerClass = owner.getClass();
		Method method = null;
		Object result = null;
		// b.获取需要调用的方法
		for (Method m : ownerClass.getDeclaredMethods()) {
			if (m.getName().equalsIgnoreCase(methodName)) {
				method = m;
				break;
			}
		}

		// c.调用该方法
		try {
			result = method.invoke(owner, args);// 调用方法
		} catch (InvocationTargetException e) {
			if(MgException.class.getName().equals(e.getTargetException().getClass().getName())){
				MgException mgException = (MgException) e.getTargetException();
				throw mgException;
			}
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
