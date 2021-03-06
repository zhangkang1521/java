package org.zhangkang.commons.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 显示类信息
 */
public class ClassUtils {

	/**实现的接口集合*/
	private static Set<Class> interfaceSet = new HashSet<Class>();

	/**
	 * 显示父类信息
	 * @param c
     */
	private static void showSuperClass(Class<?> c){
		Class<?> superClass = c.getSuperclass();
		if(superClass!=null){
			//加入父类实现的接口
			Class<?>[] interfaces = c.getInterfaces();
			if(interfaces!=null){
				interfaceSet.addAll(Arrays.asList(interfaces));
			}
			System.out.println("\t"+superClass);
			showSuperClass(superClass);
		}
	}

	/**
	 * 显示实现的接口
	 * @param c
     */
	private static void showInterfaces(Class<?> c){
		Class<?>[] interfaces = c.getInterfaces();
		interfaceSet.addAll(Arrays.asList(interfaces));
		for(Class<?> inter: interfaceSet){
			System.out.println("\t"+inter);
		}
	}

	/**
	 * 显示类的继承关系
	 * @param c
     */
	public static void showInherit(Class<?> c){
		interfaceSet.clear();
		System.out.println(c+" inherit from:");
		showSuperClass(c);
		System.out.println("implement interfaces:");
		showInterfaces(c);
	}

	/**
	 * 打印类中的方法
	 * @param c
     */
	public static void showMethods(Class<?> c){
		Method[] methods = c.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			System.out.println(methods[i]);
		}
	}

	/**
	 * 打印类中的构造方法
	 * @param c
     */
	public static void showConstructs(Class<?> c){
		Constructor[] constructors = c.getConstructors();
		if(constructors == null || constructors.length == 0){
			System.out.println("没有找到构造方法");
		} else {
			for (Constructor con : constructors) {
				System.out.println(con);
			}
		}
	}

}
