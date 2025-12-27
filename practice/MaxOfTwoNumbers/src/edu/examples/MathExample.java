package edu.examples;

import java.util.Scanner;

public class MathExample {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		double a = scanner.nextDouble();
		double b = scanner.nextDouble();
		System.out.println("logorithemic Exponential value as base 'e'==>"
				+ Math.E);
		System.out.println("PI value==>" + Math.PI);
		System.out.println("absolute double==>" + Math.abs(a));
		System.out.println("absloute integer==>" + Math.abs(a));
		System.out.println("the large integer for given value==>"
				+ Math.ceil(a));
		System.out.println("the small integer for given value==>"
				+ Math.floor(a));
		System.out.println("logorithemic value 'e' as base ==>" + Math.exp(a));
		System.out.println("square root of given value==>" + Math.sqrt(a));
		System.out.println("round value for given value==>" + Math.round(a));
		System.out.println("rint value for given value==>" + Math.rint(a));
		System.out.println("random values for given value==>" + Math.random());
		System.out.println("logerithem value==>" + Math.log(a));
		System.out.println("next up value for given==>" + Math.nextUp(a));
		System.out.println("power of given value==>" + Math.pow(a, b));
		System.out.println("max of given values==>" + Math.max(a, b));
		System.out.println("min of given==>" + Math.min(a, b));
	}
}
