package edu.calc;

public class Calc {
	public static void main(String[] args) {
		int a=4,b=4;
		System.out.println("Break Point---f5");
		add(a,b);
		System.out.println("Break Point---f5");
		sub(a,b);
		System.out.println("Break Point---f8");
		System.out.println("SUCCESS");
		System.out.println("Break Point");
		System.out.println("--Watch Expression--");
		
	}

	private static void sub(int a, int b) {
		System.out.println("Break Point---f7");
		int sub=a-b;
		System.out.println("SUB="+sub);
	}

	private static void add(int a, int b) {
	System.out.println("Break Point---f6");
	System.out.println("change variable value");
	int sum=a+b;
	System.out.println("SUM="+sum);
		
	}

}
