package edu.examples;

import java.util.Scanner;

public class SwitchBreakDemo {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int a = scanner.nextInt();
		int reverse = 0, remainder;
		while (a > 0) {
			remainder = a % 10;
			reverse = reverse * 10 + remainder;
			a = a / 10;
		}
		String result = "";
		while (reverse > 0) {
			remainder = reverse % 10;
			reverse = reverse / 10;
			switch (remainder) {
			case 0:
				result = result + "zero";
				break;
			case 1:
				result = result + "one";
				break;
			case 2:
				result = result + "two";
				break;
			case 3:
				result = result + "three";
				break;
			case 4:
				result = result + "four";
				break;
			case 5:
				result = result + "five";
				break;
			case 6:
				result = result + "six";
				break;
			case 7:
				result = result + "seven";
				break;
			case 8:
				result = result + "eight";
				break;
			case 9:
				result = result + "nine";
				break;
			case 10:
				result = result + "ten";
				break;
			default:
				result = "";
			}
		}
		System.out.print(" "+result);
	}
}