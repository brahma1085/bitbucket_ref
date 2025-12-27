package edu.examples;

import java.util.Scanner;

public class MultiplicationTable {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int a = scanner.nextInt();
		for (int i = 1; i <= a; i++) {
			for (int j = 1; j <= 5; j++) {
				System.out.println(i + " " + " *" + " " + j + "=" + i * j);
			}
			System.out.print("\n");
		}
	}
}