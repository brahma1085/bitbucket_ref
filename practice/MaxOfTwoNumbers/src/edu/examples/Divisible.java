package edu.examples;

public class Divisible {
	public static void main(String[] args) {
		int result = 0;
		for (int i = 1000; i <= 200000; i++) {
			if (i % 1876 == 0) {
				result += i;
			}
		}
		System.out.println(result);
	}
}
