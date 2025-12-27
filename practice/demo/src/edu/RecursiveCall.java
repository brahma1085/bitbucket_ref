package edu;

public class RecursiveCall {
	private int a;
	private int b;

	public RecursiveCall() {
		this(23, 23);
	}

	public RecursiveCall(int a, int b) {
		this.a = a;
		this.b = b;
	}

	public static void main(String[] args) {
		RecursiveCall call=new RecursiveCall();
		System.out.println(call.a+" , "+call.b);
		RecursiveCall call2 = new RecursiveCall(23, 345);
		System.out.println(call2.a + " , " + call2.b);
	}
}
