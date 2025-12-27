package edu.examples;

import java.util.Scanner;

public class MonthsDays {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int a = scanner.nextInt();
		System.out.println("given days==>" + a);
		int b = a % 30;
		int c = a / 30;
		System.out.println(c + "months" + b + "days");
	}
}
