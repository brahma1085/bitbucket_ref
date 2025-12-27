package edu.model;

public class Student {
	private Long sno;
	private String sname;

	public Student(String sname) {
		super();
		this.sname = sname;
	}

	public Long getSno() {
		return sno;
	}

	public String getSname() {
		return sname;
	}
}
