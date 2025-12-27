package edu;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CalculationTest {
	private Calculation calculation;

	@Before
	public void setUp() throws Exception {
		calculation = new Calculation();
	}

	@After
	public void tearDown() throws Exception {
		calculation = null;
	}

	@Test
	public void testSum() {
		int actual = calculation.sum(3, 4);
		assertEquals(7, actual);
	}

	@Test
	public void testSub() {
		int actual = calculation.sub(4, 2);
		assertEquals(2, actual);
	}

}
