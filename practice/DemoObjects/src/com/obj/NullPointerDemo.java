package com.obj;

class Point {
	void fun(){
		System.out.println(this);
	}
}

public class NullPointerDemo {

	/**
	 * @param args
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Point p1;
		p1 = new Point();
		System.out.println(p1);
		p1.fun();
	}

}
