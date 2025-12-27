package test;

import edu.Prototype;

public class PrototypeTest {
	public static void main(String[] args) {
		Prototype prototype = new Prototype();
		Prototype prototype2 = (Prototype) prototype.clone();// shallow type
		System.out.println("clone before object==>" + prototype);
		System.out.println("clone after object==>" + prototype2);
		System.out.println(prototype2.getString());
	}
}