package edu;

public class D1ArrayDemo4 {
	static void sum(int a[]) {
		int s = 0;
		for (int x : a)
			s += x;
		System.out.println(s);
	}

	public static void main(String[] args) {
		sum(new int[] { 23, 34, 45, 56, 67, 34, 54, 45, 456, 45, 5, 767, 6, 6,
				786, 78 });
	}
}
