package edu.examples;

public class GivenNumber {
	static double a = 234.33d;

	public static void main(String[] args) {
		System.out.println("small integer is greater than the given number==>"
				+ Math.ceil(a));
		System.out.println("given number==>" + a);
		System.out.println("large integer is less than the given number==>"
				+ Math.floor(a));
		System.out.println("absolute value==>" + Math.abs(a));
	}
}