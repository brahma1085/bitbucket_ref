package edu.examples;

import java.util.Scanner;

public class GivenTriangle {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int a = scanner.nextInt();
		while (a > 0) {
			for (int i = 1; i <= a; i++) {
				System.out.print(a);
			}
			System.out.println();
			a--;
		}
	}
}