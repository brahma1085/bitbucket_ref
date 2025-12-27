package edu.utility;

import java.util.BitSet;

public class BitSetDemo {
	public void getBitsetDetails() {
		BitSet bitSet = new BitSet(20);
		BitSet bitSet2 = new BitSet(25);
		for (int i = 0; i < 30; i++) {
			if (i % 2 == 0)
				bitSet.set(i);
			if (i % 5 != 0)
				bitSet2.set(i);
		}
		System.out.println("initial pattern in bitset==>" + bitSet);
		System.out.println("initial pattern in bitset2==>" + bitSet2);
		bitSet.and(bitSet2);
		System.out.println("and operation on bitSet==>" + bitSet);
		bitSet2.andNot(bitSet);
		System.out.println("andNot operation on bitSet2==>" + bitSet2);
		System.out.println("no: of bitsets are set to true==>"
				+ bitSet.cardinality());
		System.out.println(bitSet.get(2, 20));
		System.out.println(bitSet.length());
		bitSet.xor(bitSet2);
		System.out.println(bitSet);
	}
}
