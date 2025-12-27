package edu.banking;

import edu.money.*;

public class SBI extends ReserveBank {

	@Override
	public Bank createMoney(int moneyType) {
		if (moneyType == 100)
			return new SBI100();
		else if (moneyType == 500)
			return new SBI500();
		else if (moneyType == 1000)
			return new SBI1000();
		else
			return null;
	}
}