package com.arr;

public class SindledimArr {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int a[][] = new int[3][];
		a[0] = new int[] { 1, 2 };
		a[1] = new int[] { 3 };
		a[2] = new int[] { 1, 2, 3, 4, 5 };
		for (int b[] : a) {
			System.out.println();
			for (int c : b)
				System.out.print(c);
		}
	}

}
