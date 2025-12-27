package edu.poly.test;

import java.io.IOException;
import java.util.Properties;

//overriding==>run time polymorphism
//polymorphism==>resolve the class names from properties or XML
abstract class A2 {
	public void x() {
		System.out.println("A==>x()");
		y();
	}

	public abstract void y();

	public void z() {
		System.out.println("A==>z()");
	}
}

class B2 extends A2 {
	public void y() {
		System.out.println("B==>y()");
		z();
	}
}

class C2 extends A2 {
	public void y() {
		System.out.println("C==>y()");
		z();
	}
}

public class PolyTest {
	private static Properties properties = new Properties();
	static {
		try {
			properties.load(PolyTest.class.getClassLoader()
					.getResourceAsStream("polymorphism.properties"));
		} catch (IOException e) {
			System.out.println("IOException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
			throw new ExceptionInInitializerError("error in loading file");
		}
	}

	public static void main(String[] args) {
		String className = properties.getProperty("class.B2");
		// String className = properties.getProperty("class.C2");
		try {
			A2 a = (A2) Class.forName(className).newInstance();
			a.x();
			System.out
					.println("object created for==>" + a.getClass().getName());
			System.out.println("reference type of the object==>"
					+ a.getClass().getGenericSuperclass());
		} catch (Exception e) {
			System.out.println("Exception==>" + e.getClass().getName() + "==>"
					+ e.getMessage());
		}
	}
}
