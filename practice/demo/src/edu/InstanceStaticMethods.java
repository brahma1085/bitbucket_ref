package edu;

class Sample1 {
	private Sample1() {
		fun3();
	}

	void fun1() {
		System.out.println("non static fun1");
	}

	static Sample1 fun2() {
		System.out.println("static fun2");
		Sample1 sample1 = new Sample1();
		return sample1;
	}

	void fun3() {
		fun1();
		System.out.println("non static fun3");
	}
}

public class InstanceStaticMethods {
	public static void main(String[] args) {
		Sample1.fun2();
	}
}
