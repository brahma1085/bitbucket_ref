package edu.poly.test;

//is-a relationship is important in polymorphism
class A {
	public void x() {
		System.out.println("A==>x()");
	}
}

class B extends A {
	public void x() {
		System.out.println("B==>x()");
	}

	public void y() {
		System.out.println("B==>y()");
	}
}

public class Poly1Test {

	public static void main(String[] args) {
		A a = new B();
		a.x();
		// a.y();==>not possible
		B b = new B();
		a.x();
		b.x();
		b.y();
	}
}
