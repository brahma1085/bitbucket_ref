package edu.money;

public abstract class Bank {
	public void prepare() {
		System.out.println("preparing");
	}

	public void process() {
		System.out.println("processing");
	}

	public void take() {
		System.out.println("please take the requested amount.");
		System.out.println("---------------------------------------------------------");
	}
}
