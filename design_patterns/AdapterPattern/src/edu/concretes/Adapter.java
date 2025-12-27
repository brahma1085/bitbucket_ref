package edu.concretes;

import edu.interfaces.Duck;
import edu.interfaces.Turkey;

public class Adapter implements Duck {
	Turkey turkey;

	public Adapter(Turkey turkey) {
		this.turkey = turkey;
	}

	@Override
	public void flyDuck() {
		for(int i=0;i<=5;i++)
		turkey.flyTurkey();
	}

	@Override
	public void quack() {
		turkey.gobble();
	}

}