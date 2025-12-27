package edu.mappings;

/**
 * Studentdemo entity. @author MyEclipse Persistence Tools
 */

public class Studentdemo implements java.io.Serializable {

	// Fields

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Long sno;
	private String sname;

	// Constructors

	/** default constructor */
	public Studentdemo() {
	}

	/** minimal constructor */

	// Property accessors

	public Long getSno() {
		return this.sno;
	}

	public void setSno(Long sno) {
		this.sno = sno;
	}

	public String getSname() {
		return this.sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

}