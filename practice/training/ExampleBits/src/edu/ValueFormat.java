package edu;

public class ValueFormat {

	public static void main(String[] args) {
		double i = 876.67878887d;
		System.out.println("small integer is  greater than given number==>"
				+ Math.ceil(i));
		System.out.println("given number==>" + i);
		System.out.println("large integer is smaller than given number==>"
				+ Math.floor(i));
	}
}