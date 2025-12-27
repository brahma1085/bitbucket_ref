package edu.domainobj;

import java.io.Serializable;

public class Account implements Serializable {

	private static final long serialVersionUID = 1L;
	private int accno;
	private String name;
	private float balance;

	public int getAccno() {
		return accno;
	}

	public void setAccno(int accno) {
		this.accno = accno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

}
