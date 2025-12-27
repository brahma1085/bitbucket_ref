package edu.examples;

import java.util.Scanner;

public class FabbinocciDemo {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int a = scanner.nextInt();
		int f1, f2 = 0, f3 = 1;
		for (int i = 0; i <= a; i++) {
			System.out.println(f3);
			f1 = f2;
			f2 = f3;
			f3 = f1 + f2;
		}
	}
}