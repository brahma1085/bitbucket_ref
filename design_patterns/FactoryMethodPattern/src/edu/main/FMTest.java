package edu.main;

import edu.banking.*;
import edu.money.*;

public class FMTest {

	public static void main(String[] args) {
		ReserveBank bank = new SBI();
		ReserveBank bank1 = new HDFC();
		Bank bank2 = bank.getMoney(500);
		bank2 = bank1.getMoney(1000);
	}
}
