package edu.poly.test;

//is-a relationship is important in polymorphism
abstract class A1 {
	public void x() {
		System.out.println("A==>x()");
		y();
	}

	public abstract void y();

	public void z() {
		System.out.println("A==>z()");
	}
}

// is-a
class B1 extends A1 {
	public void y() {
		System.out.println("B==>y()");
		z();
	}
}

public class Poly2Test {

	public static void main(String[] args) {
		A1 a = new B1();
		a.x();// ==>first checks in B(sub--if not there) then A(super)
		// a.y();//==>first checks in B(sub--if not there) then A(super)
		// a.z();//==>first checks in B(sub--if not there) then A(super)
		// B1 b = new B1();
		// b.x();//==>first checks in B(sub--if not there) then A(super)
		// b.y();//==>first checks in B(sub--if not there) then A(super)
		// b.z();//==>first checks in B(sub--if not there) then A(super)
	}
}
