package edu.calc;

public class SumCalculation extends Calculation {
	public void sum() {
		int sum = getA() + getB();
		System.out.println("sum = " + sum);
	}
}
