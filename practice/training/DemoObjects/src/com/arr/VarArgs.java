package com.arr;

public class VarArgs {

	static void fun(int... a) {
		System.out.println("fun");
	}

	public static void main(String[] args) {
		fun();
		fun(6);
		fun(1, 5);
	}

}
