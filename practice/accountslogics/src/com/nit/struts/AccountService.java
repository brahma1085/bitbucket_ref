package com.nit.struts;

import java.util.ArrayList;
import java.util.List;

public class AccountService {
	public List<Account> getAccounts(float balance) {
		List<Account> accounts = null;
		if (balance > 5000) {
			accounts = new ArrayList<Account>(3);
			Account account1 = new Account();
			account1.setAccno("12332");
			account1.setBalance("2342334");
			account1.setName("SWARNA");
			Account account2 = new Account();
			account2.setAccno("23423");
			account2.setBalance("2342345");
			account2.setName("RAMESH");
			Account account3 = new Account();
			account3.setAccno("21323");
			account3.setBalance("34534547");
			account3.setName("RAJESH");
		}
		return accounts;
	}
}
