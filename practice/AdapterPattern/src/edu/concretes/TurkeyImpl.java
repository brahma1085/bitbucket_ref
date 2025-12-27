package edu.concretes;

import edu.interfaces.Turkey;

public class TurkeyImpl implements Turkey {

	@Override
	public void flyTurkey() {
		System.out.println("i am turkey.....can fly more distance now....");
	}

	@Override
	public void gobble() {
		System.out.println("i am turkey.....gobble..gobble...");
	}

}
