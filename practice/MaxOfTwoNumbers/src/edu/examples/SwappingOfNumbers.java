package edu.examples;

import java.util.Scanner;

public class SwappingOfNumbers {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int a = scanner.nextInt();
		int b = scanner.nextInt();
		System.out.println("before swapping");
		System.out.println(a);
		System.out.println(b);
		a = a + b;
		b = a - b;
		a = a - b;
		System.out.println("after swapping");
		System.out.println(a);
		System.out.println(b);
	}

}
