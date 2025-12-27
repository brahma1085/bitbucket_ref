package edu;

import java.util.Scanner;

public class Factorial {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		double num = Double.parseDouble(scanner.nextLine());
		double result = 1;
		while (num > 0) {
			result = result * num;
			num--;
		}
		System.out.println("factorial of given number is==>" + result);
	}
}