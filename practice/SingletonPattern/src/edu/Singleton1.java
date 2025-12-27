package edu;

public class Singleton1 {
	private static Singleton1 singleton1;
	private static int counter;

	private Singleton1() {
		counter = 0;
	}

	public static Singleton1 getInstance() {
		if (singleton1 == null)
			singleton1 = new Singleton1();
		return singleton1;
	}

	public static synchronized int getNext() {
		return ++counter;
	}
}