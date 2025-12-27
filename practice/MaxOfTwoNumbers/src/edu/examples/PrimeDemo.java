package edu.examples;

import java.util.Scanner;

public class PrimeDemo {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int a = scanner.nextInt();
		int flag = 0;
		for (int i = 2; i < a; i++) {
			if (a % i == 0) {
				System.out.println("its not  a prime number");
				flag = 1;
				break;
			}
		}
		if (flag == 0)
			System.out.println("its a prime number");
	}

}
