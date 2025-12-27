package edu;

class Point1 {
	int x;
	int y;
}

public class ReferenceCopy {
	public static void main(String[] args) {
		Point1 point1, point2;
		point1 = new Point1();
		point2 = new Point1();
		System.out.println(point1 + "," + point2);
		point1.x = 5;
		point1.y = 6;
		point2.x = 7;
		point2.y = 8;
		// reference by reference copying
		point1 = point2;
		System.out.println(point1 + "," + point2);
		System.out.println("point1.x=" + point1.x + ", point1.y=" + point1.y);
		System.out.println("point2.x=" + point2.x + ", point2.y=" + point2.y);
		point1.x = 1;
		point1.y = 2;
		point2.x = 3;
		point2.y = 4;
		System.out.println(point1.x + "," + point1.y);
		System.out.println(point2.x + "," + point2.y);
	}
}
