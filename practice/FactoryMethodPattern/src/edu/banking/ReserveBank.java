package edu.banking;

import edu.money.Bank;

public abstract class ReserveBank {
	Bank bank;

	public Bank getMoney(int rs) {
		bank = createMoney(rs);
		bank.prepare();
		bank.process();
		bank.take();
		return bank;
	}

	protected abstract Bank createMoney(int rs);
}
