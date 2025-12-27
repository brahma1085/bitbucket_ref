package edu.examples;

import java.util.Scanner;

public class MaxOfTwoNumbers {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int a = scanner.nextInt();
		int b = scanner.nextInt();
		int res = (a > b) ? a : b;
		System.out.println(res + " is the maximum value");
		int result = (a < b) ? a : b;
		System.out.println(result + " is the minimum value");
	}
}
