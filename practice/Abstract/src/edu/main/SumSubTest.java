package edu.main;

import edu.calc.SubCalculation;
import edu.calc.SumCalculation;

public class SumSubTest {
	public static void main(String[] args) {
		SumCalculation s1 = new SumCalculation();
		s1.setA(20);
		s1.setB(20);
		s1.sum();
		SubCalculation s2 = new SubCalculation();
		s2.setA(20);
		s2.setB(10);
		s2.sub();
	}
}
