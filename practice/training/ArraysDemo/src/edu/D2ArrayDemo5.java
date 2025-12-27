package edu;

public class D2ArrayDemo5 {
	public static void main(String[] args) {
		double a[][] = { { 12.9, 12.4, 12.3, 12.4 }, { 23.5, 23.7, 2.3, 2.3 },
				{ 3.4, 0.34, 3.4, 3.4 }, { 4.5, 45.7, 4.5, 4.5 } };
		for (double[] i : a) {
			System.out.printf("\n");
			for (double x : i) {
				System.out.println(x);
			}
		}
	}
}
