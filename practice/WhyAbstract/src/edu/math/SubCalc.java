package edu.math;

public class SubCalc {
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

	public void sub() {
		int sub = a - b;
		System.out.println("sub=" + sub);
	}
}
