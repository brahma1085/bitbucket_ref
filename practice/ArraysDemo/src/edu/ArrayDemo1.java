package edu;

public class ArrayDemo1 {
	public static void main(String[] args) {
		int a[];
		int[] b;
		int[] c;
		a = new int[0];
		b = new int[5];
		c = new int[5];
		System.out.println(c.getClass().getName());
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		System.out.println(a.length);
		System.out.println(b.length);
		System.out.println(c.length);
		// normal for loop
		for (int i = 0; i < c.length; i++) {
			System.out.println(i);
		}
		// enhanced for loop
		for (int i : b) {
			System.out.println(i);
		}
		double d[];
		d = new double[4];
		d[0] = 3.4;
		d[3] = 5.8;
		for (double x : d)
			System.out.println(x);
	}
}
