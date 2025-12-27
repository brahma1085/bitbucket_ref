package edu.mappings;

import java.util.List;
import java.util.Set;

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
	private Set studentdemos;

	// Constructors

	/** default constructor */
	public Coursedemo() {
	}

	public Set getStudentdemos() {
		return studentdemos;
	}

	public void setStudentdemos(Set studentdemos) {
		this.studentdemos = studentdemos;
	}

	/** full constructor */

	// Property accessors

	public Long getCno() {
		return this.cno;
	}

	public void setCno(Long cno) {
		this.cno = cno;
	}

	public String getCname() {
		return this.cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

}