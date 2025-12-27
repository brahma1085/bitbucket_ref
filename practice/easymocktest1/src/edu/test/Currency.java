package edu.test;

import java.io.IOException;

public class Currency implements ExchangeRate{

	private String units;
	private long amount;
	private int cents;

	public Currency(double amount, String units) {
		this.units = units;
		setAmount(amount);
	}

	private void setAmount(double amount) {
		this.amount = new Double(amount).longValue();
		this.cents = (int) ((amount * 100.0) % 100);
	}

	public Currency toEuros(ExchangeRate converter) {
		if ("EUR".equals(units))
			return this;
		else {
			double input = amount + cents / 100;
			double rate;
			try {
				rate = converter.getRate(units, "EUR");
				double output = input * rate;
				return new Currency(output, "EUR");
			} catch (Exception e) {
				return null;
			}
		}
	}

	public boolean equals(Object o) {
		if (o instanceof Currency) {
			Currency other = (Currency) o;
			return this.units.equals(other.units)
					&& this.amount == other.amount && this.cents == other.cents;
		}
		return false;
	}

	public String toString() {
		return amount + "." + Math.abs(cents) + "" + units;
	}

	@Override
	public double getRate(String inputCurrency, String outputCurrency)
			throws IOException {
		return amount;
	}
}
