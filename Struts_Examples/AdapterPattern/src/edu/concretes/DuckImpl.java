package edu.concretes;

import edu.interfaces.Duck;

public class DuckImpl implements Duck {

	@Override
	public void flyDuck() {
		System.out.println("i am duck..... flying");
	}

	@Override
	public void quack() {
		System.out.println("i am duck.....quack..quack...");
	}

}