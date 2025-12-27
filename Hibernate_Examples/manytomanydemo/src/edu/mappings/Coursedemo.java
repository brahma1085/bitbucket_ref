package edu.mappings;

import java.util.List;

/**
 * Coursedemo entity. @author MyEclipse Persistence Tools
 */

public class Coursedemo implements java.io.Serializable {

	// Fields

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Long cno;
	private String cname;
	private List<Studentdemo1> studentdemo1s;

	public Long getCno() {
		return cno;
	}

	public void setCno(Long cno) {
		this.cno = cno;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public List<Studentdemo1> getStudentdemo1s() {
		return studentdemo1s;
	}

	public void setStudentdemo1s(List<Studentdemo1> studentdemo1s) {
		this.studentdemo1s = studentdemo1s;
	}

}