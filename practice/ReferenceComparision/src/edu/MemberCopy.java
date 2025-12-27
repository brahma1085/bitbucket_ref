package edu;

class Point2 {
	int x;
	int y;
}

public class MemberCopy {
	public static void main(String[] args) {
		Point2 p1, p2;
		p1 = new Point2();
		p2 = new Point2();
		System.out.println(p1 + "," + p2);
		p1.x = 5;
		p1.y = 6;
		p2.x = 7;
		p2.y = 8;
		// member by member copying
		p1.x = p2.x;
		p1.y = p2.y;
		System.out.println(p1 + "," + p2);
		System.out.println(p1.x + "," + p1.y);
		System.out.println(p2.x + "," + p2.y);
		p1.x = 1;
		p1.y = 2;
		p2.x = 3;
		p2.y = 4;
		System.out.println(p1.x + "," + p1.y);
		System.out.println(p2.x + "," + p2.y);
	}
}
