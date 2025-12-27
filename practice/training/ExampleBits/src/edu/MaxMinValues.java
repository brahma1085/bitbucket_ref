package edu;

import java.util.Scanner;

public class MaxMinValues {

	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		System.out.println("enter values==>");
		int a = Integer.parseInt(scanner.nextLine());
		int b = Integer.parseInt(scanner.nextLine());
		if (a > b)
			System.out.println(a + " is greater than " + b);
		else
			System.out.println(a + " is less than " + b);
		int result = (a < b) ? a : b;
		System.out.println(result + "  is the minimum value");
		int res = (a > b) ? a : b;
		System.out.println(res + "  is the maximum value");
	}
}