package edu;

public class D1ArrayDemo3 {
	static void sum(int[] a) {
		int s = 0;
		for (int x : a)
			s += x;
		System.out.println(s);
		for (int i = 0; i < a.length; i++)
			System.out.println(a[0] + "+" + a[1] + "+" + a[2] + "=" + s);
	}

	public static void main(String[] args) {
		int b[] = { 55, 556, 34 };
		sum(b);
		for (int x : b)
			System.out.println(x);
	}
}
