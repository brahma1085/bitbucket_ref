package com.obj;

class A {
	int x, y;
}

public class ObjectDAtaCopy {
	public static void main(String[] args) {
		A a = new A();
		a.x = 5;
		a.y = 6;
		System.out.println(a.x + "==>" + a.y);
		A a2 = new A();
		a2.x = 10;
		a2.y = 12;
		System.out.println(a2.x + "==>" + a2.y);
		a.x = a2.x;
		a.y = a2.y;
		System.out.println(a.x + "==>" + a.y + "==>a2 values==>" + a2.x + "==>"
				+ a2.y);
		a.x = 17;
		a.y = 123;
		a2.x = 12312;
		a2.y = 1231223;
		System.out.println(a.x + "==>" + a.y + "a2 values==>" + a2.x + "==>"
				+ a2.y);
	}
}
