package edu.examples;

import java.util.Scanner;

public class HarmonicSeries {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int a = scanner.nextInt();
		double result = 0.0d;
		while (a > 0) {
			result = result + (double) 1 / a;
			a--;
		}
		System.out.println(result);
	}

}
