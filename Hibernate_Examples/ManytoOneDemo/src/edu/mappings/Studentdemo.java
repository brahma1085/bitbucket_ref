package edu.mappings;

/**
 * Studentdemo entity. @author MyEclipse Persistence Tools
 */

public class Studentdemo implements java.io.Serializable {

	// Fields

	public Long getSno() {
		return sno;
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Long sno;

	public void setSno(Long sno) {
		this.sno = sno;
	}

	private Coursedemo coursedemo;
	private String sname;

	// Constructors

	/** default constructor */
	public Studentdemo() {
	}

	/** full constructor */
	public Studentdemo(Coursedemo coursedemo, String sname) {
		this.coursedemo = coursedemo;
		this.sname = sname;
	}

	// Property accessors

	public Coursedemo getCoursedemo() {
		return this.coursedemo;
	}

	public void setCoursedemo(Coursedemo coursedemo) {
		this.coursedemo = coursedemo;
	}

	public String getSname() {
		return this.sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

}