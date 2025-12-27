package com.obj;

class Krishna {
	void fun1() {
		System.out.println("no arg fun");
	}

	void fun1(int a) {
		System.out.println("one arg fun");
	}

	void fun1(int a, int b) {
		System.out.println("two arg fun");
	}

}

public class OverLoading1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Krishna krishna = new Krishna();
		krishna.fun1();
		krishna.fun1(12);
		krishna.fun1(1, 4);
	}

}
