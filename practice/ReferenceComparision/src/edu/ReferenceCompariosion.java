package edu;

class Point {
	char x;
	String y;
}

public class ReferenceCompariosion {
	public static void main(String[] args) {
		Point p1, p2;
		p1 = p2 = new Point();
		System.out.println(p1);
		System.out.println(p2);
		System.out.println(p1.x + "," + p1.y);
		System.out.println(p2.x + "," + p2.y);
	}
}
