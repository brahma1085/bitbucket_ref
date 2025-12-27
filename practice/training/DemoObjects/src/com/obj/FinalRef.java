package com.obj;

class Point1 {

}

public class FinalRef {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final Point1 p1;
		p1 = new Point1();
		Point1 p2 = new Point1();
		System.out.println(p1 + " " + p2);
		// p1=null;
		p2 = null;
		System.out.println(p1 + " " + p2);
	}
}
