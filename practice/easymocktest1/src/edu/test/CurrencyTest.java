package edu.test;

import java.io.IOException;

import org.easymock.EasyMock;

import junit.framework.TestCase;

public class CurrencyTest extends TestCase {

	public void testToEuros() throws IOException {
		Currency currencyTest = new Currency(2.50, "USD");
		Currency currencyexp = new Currency(3.00, "EUR");
		ExchangeRate mock = EasyMock.createMock(ExchangeRate.class);
		EasyMock.expect(mock.getRate("USD", "EUR")).andReturn(1.5);
		EasyMock.replay(mock);
		Currency actual = currencyTest.toEuros(mock);
		assertEquals(currencyexp, actual);
	}
}
