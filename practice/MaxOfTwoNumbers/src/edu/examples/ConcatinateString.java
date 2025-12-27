package edu.examples;

import java.util.Scanner;

public class ConcatinateString {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int a = scanner.nextInt();
		String result = "";
		for (int i = 0; i < a; i++)
			result = result + i;
		System.out.println(result);
	}
}