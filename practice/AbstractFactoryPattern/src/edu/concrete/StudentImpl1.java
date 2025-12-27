package edu.concrete;

import edu.interfaces.AbstractStudent;

public class StudentImpl1 implements AbstractStudent {
	private int sno = 1111;
	private String sname = "SAMIR";

	@Override
	public String getStudentName() {
		return sname;
	}

	@Override
	public int getStudentNo() {
		return sno;
	}
}