package edu;

import junit.framework.TestCase;

public class CalculationJunitTest extends TestCase {
	private CalculationJunit calculationJunit;

	protected void setUp() throws Exception {
		super.setUp();
		calculationJunit = new CalculationJunit();
	}

	protected void tearDown() throws Exception {
		calculationJunit = null;
		super.tearDown();
	}

	public void testDivision() {
		int actualResult = calculationJunit.division(2, 2);
		assertEquals(1, actualResult);
	}

	public void testMul() {
		int actualResult = calculationJunit.mul(2, 2);
		assertEquals(4, actualResult);
	}
}