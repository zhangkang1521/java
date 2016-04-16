package org.zhangkang.commons;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 显示类信息
 */
public class ShowClass {

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

}
