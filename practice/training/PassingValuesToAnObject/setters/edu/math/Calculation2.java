package edu.math;

public class Calculation2 {
	private int a, b;

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public void sum() {
		int sum = a + b;
		System.out.println("sum=" + sum);
	}

	public void sub() {
		int sub = a - b;
		System.out.println("sub=" + sub);
	}
}
