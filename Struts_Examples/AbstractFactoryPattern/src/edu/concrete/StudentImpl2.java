package edu.concrete;

import edu.interfaces.AbstractStudent;

public class StudentImpl2 implements AbstractStudent {
	private int sno = 2222;
	private String sname = "SAKETH";

	@Override
	public String getStudentName() {
		return sname;
	}

	@Override
	public int getStudentNo() {
		return sno;
	}

}
