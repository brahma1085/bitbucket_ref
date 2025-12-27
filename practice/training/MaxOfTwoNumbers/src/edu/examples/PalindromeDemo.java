package edu.examples;

import java.util.Scanner;

public class PalindromeDemo {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int a = scanner.nextInt();
		int b = a;
		int reverse = 0, remainder;
		while (b > 0) {
			remainder = b % 10;
			reverse = reverse * 10 + remainder;
			b = b / 10;
		}
		if (reverse == a)
			System.out.println("its a palindrome");
		else
			System.out.println("its not a palindrome");
	}

}
