package edu;

public class SumOfDigits {
	public static void main(String[] args) {
		int result = 0;
		for (int i = 100; i <= 200; i++) {
			if (i % 10 == 0) {
				result += i;
				System.out.println(result);
				continue;
			}
		}
	}
}