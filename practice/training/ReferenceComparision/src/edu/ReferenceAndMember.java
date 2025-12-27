package edu;

class Point3 {
	int x;
	int y;
}

public class ReferenceAndMember {
	public static void main(String[] args) {
		Point3 p1, p2;
		p1 = new Point3();
		p2 = new Point3();
		p1.x = 5;
		p1.y = 6;
		p2.x = 5;
		p2.y = 6;
		System.out.println(p1 == p2);
		System.out.println(p1.x == p2.x && p1.y == p2.y);
	}
}
