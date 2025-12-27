package edu.test;

import edu.concretes.Adapter;
import edu.concretes.DuckImpl;
import edu.concretes.TurkeyImpl;
import edu.interfaces.Duck;
import edu.interfaces.Turkey;

public class AdapterTest {
	public static void main(String[] args) {
		Turkey turkey = new TurkeyImpl();
		Duck duck = new Adapter(turkey);
		duckTest();
		duck.flyDuck();
		duck.quack();
	}

	public static void duckTest() {
		Duck duck = new DuckImpl();
		duck.flyDuck();
		duck.quack();
	}
}