package edu.examples;

import java.util.Scanner;

public class RequiredTriangle {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int a = scanner.nextInt();
		int c = 0;
		for (int i = 1; i <= a; i++) {
			for (int j = 1; j <= i; j++) {
				System.out.print((i * j));
			}
			System.out.println();
		}
		for (int i = 0; i <= a; i++) {
			for (int j = 1; j <= i; j++) {
				System.out.print((i + j) % 2);
			}
			System.out.println();
		}
		for (int i = 0; i <= a; i++) {
			for (int j = 0; j <= i; j++) {
				if (c != a) {
					c++;
					System.out.print(c);
				} else
					break;
			}
			System.out.println();
		}
	}

}
