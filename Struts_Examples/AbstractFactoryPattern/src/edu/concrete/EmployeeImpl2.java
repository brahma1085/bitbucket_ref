package edu.concrete;

import edu.interfaces.AbstractEmployee;

public class EmployeeImpl2 implements AbstractEmployee {
	private int eno = 888;
	private String ename = "EMP2";

	@Override
	public String getEmpName() {
		return ename;
	}

	@Override
	public int getEmpNo() {
		return eno;
	}

}
