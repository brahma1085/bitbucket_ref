package edu.mappings;

/**
 * Coursedemo entity. @author MyEclipse Persistence Tools
 */

public class Coursedemo implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;
	private Long cno;

	public Long getCno() {
		return cno;
	}

	public void setCno(Long cno) {
		this.cno = cno;
	}

	private String cname;

	// Constructors

	/** default constructor */
	public Coursedemo() {
	}

	/** full constructor */
	public Coursedemo(String cname) {
		this.cname = cname;
	}

	// Property accessors

	public String getCname() {
		return this.cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

}