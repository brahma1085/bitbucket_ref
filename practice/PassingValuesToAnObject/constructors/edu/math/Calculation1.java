package edu.math;

public class Calculation1 {
	private int a, b;

	public Calculation1(int a, int b) {
		this.a = a;
		this.b = b;
	}

	public void sum() {
		int sum = a + b;
		System.out.println("sum = " + sum);
	}

	public void sub() {
		int sub = a - b;
		System.out.println("sub = " + sub);
	}
}
