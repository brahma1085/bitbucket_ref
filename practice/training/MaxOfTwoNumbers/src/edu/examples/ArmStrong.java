package edu.examples;

import java.util.Scanner;

public class ArmStrong {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int a = scanner.nextInt();
		int b = a;
		int check = 0, remainder;
		while (b > 0) {
			remainder = b % 10;
			check = check + (int) Math.pow(remainder, 3);
			b = b / 10;
		}
		if (check == a) {
			System.out.println("its armstrong");
		} else {
			System.out.println("its not armstrog");
		}
	}
}
