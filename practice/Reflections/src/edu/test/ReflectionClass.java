package edu.test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.Scanner;

public class ReflectionClass {
	private static Properties properties = new Properties();;
	static {
		try {
			properties.load(ReflectionClass.class.getClassLoader()
					.getResourceAsStream("reflection.properties"));
			System.out.println("properties file loaded");
			System.out.println(properties);
		} catch (IOException e) {
			throw new ExceptionInInitializerError("error in loading file");
		}
	}

	public static void main(String[] args) {

		String className = properties.getProperty("class");
		String method1 = properties.getProperty("method.one");
		String method2 = properties.getProperty("method.two");
		try {
			Class class1 = Class.forName(className);
			Object object = class1.newInstance();
			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter the integer values==>");
			int a = scanner.nextInt();
			int b = scanner.nextInt();
			Integer[] integers = { a, b };
			Class[] parameters = new Class[2];
			parameters[0] = Integer.TYPE;
			parameters[1] = Integer.TYPE;
			Method method = class1.getMethod(method1, parameters);
			Method method5 = class1.getMethod(method2, parameters);
			Integer integer = (Integer) method.invoke(object, integers);
			Integer integer2 = (Integer) method5.invoke(object, integers);
			System.out.println("return type of the method==>"
					+ method.getReturnType() + " " + method.getName() + "()");
			System.out.println("return type of the method==>"
					+ method5.getReturnType() + " " + method5.getName() + "()");
			System.out.println("output from reflection class for addition==>"
					+ integer.intValue());
			System.out
					.println("output from reflection class for substration==>"
							+ integer2.intValue());
		} catch (Exception e) {
			System.out.println("Exception==>" + e.getClass().getName() + "==>"
					+ e.getMessage());
		}
	}
}
