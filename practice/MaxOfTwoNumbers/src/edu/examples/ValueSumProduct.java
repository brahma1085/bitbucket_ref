package edu.examples;

import java.util.Scanner;

public class ValueSumProduct {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int i = scanner.nextInt();
		int remainder, result = 0;
		while (i > 0) {
			remainder = i % 10;
			result = result * 10 + remainder;
			i = i / 10;
			System.out.println(result);
		}
		System.out.println(result);
	}
}