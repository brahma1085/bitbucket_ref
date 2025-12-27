package edu;

import java.util.Scanner;

public class Reverse_Given_Number {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int num = Integer.parseInt(scanner.nextLine());
		int remainder, result = 0;
		while (num > 0) {
			remainder = num % 10;
			result = result * 10 + remainder;
			num = num / 10;
		}
		System.out.println("reverse of the given number==>" + result);
	}
}