package edu.concrete;

import edu.interfaces.AbstractEmployee;

public class EmployeeImpl1 implements AbstractEmployee {
	private int eno = 9999;
	private String ename = "EMP1";

	@Override
	public String getEmpName() {
		return ename;
	}

	@Override
	public int getEmpNo() {
		return eno;
	}
}
