package edu;

import java.util.Scanner;

public class SwitchcaseDemo {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter your marks & have a  greet==>");
		int a = Integer.parseInt(scanner.nextLine());
		switch (a / 6) {
		case 6:
			System.out.println("excellent");
			break;
		case 5:
			System.out.println("nice work");
			break;
		case 4:
			System.out.println("good");
			break;
		case 3:
			System.out.println("work hard");
			break;
		case 2:
			System.out.println("little poor");
			break;
		case 1:
			System.out.println("very poor");
			break;
		default:
			System.out.println("invalid number entered");
		}
	}
}
