package edu;

import java.util.Scanner;

public class Sum_Product_ofDigit {
	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		int num = Integer.parseInt(scanner.nextLine());
		int temp = num;
		int result = 0;
		while (num > 0) {
			result = result + num;
			num--;
			System.out.println(num);
		}
		System.out.println("sum result is==>" + result);
		Thread.sleep(1000);
		System.out.println("product is==>" + product(temp));
	}

	public static int product(int number) {
		int result = 1;
		while (number > 0) {
			result = result * number;
			--number;
			System.out.println(number);
		}
		return result;
	}
}