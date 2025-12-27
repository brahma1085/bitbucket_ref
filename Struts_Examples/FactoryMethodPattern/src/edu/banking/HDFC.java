package edu.banking;

import edu.money.*;

public class HDFC extends ReserveBank {

	@Override
	protected Bank createMoney(int moneyType) {
		if (moneyType == 100)
			return new HDFC100();
		else if (moneyType == 500)
			return new HDFC500();
		else if (moneyType == 1000)
			return new HDFC1000();
		else
			return null;
	}
}