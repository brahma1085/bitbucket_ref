package edu.test;

import edu.designs.BuisnessDeligate1;
import edu.exceptions.BusinessDeligateException;

public class Client {

	public static void main(String[] args) {
		BuisnessDeligate1 deligate1;
		try {
			deligate1 = new BuisnessDeligate1();
			int c = deligate1.add(34, 87);
			System.out.println(c);
		} catch (BusinessDeligateException e) {
			System.out.println("exception in business exception");
		}
	}

}
