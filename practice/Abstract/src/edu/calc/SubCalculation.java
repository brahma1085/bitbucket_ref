package edu.calc;

public class SubCalculation extends Calculation {
	public void sub() {
		int sub = getA() - getB();
		System.out.println("sub= " + sub);
	}
}
