package com.edu.ss;

import java.util.Scanner;

public class Reflection1 {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Scanner sc=new Scanner(System.in);
		String classname=sc.nextLine();
		Class c=Class.forName(classname);
		String classinfo="";
		String interfaceinfo="";
		String superclassinfo="";
		String modifierinfo="";
		String constructorinfo="";
		String methodinfo="";
		superclassinfo+=c.getClass().getName();
	}
}
