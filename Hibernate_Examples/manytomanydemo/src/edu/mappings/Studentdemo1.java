package edu.mappings;


/**
 * Studentdemo1 entity. @author MyEclipse Persistence Tools
 */

public class Studentdemo1 implements java.io.Serializable {

	// Fields

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Integer sno;
	private String sname;

	// Constructors

	/** default constructor */
	public Studentdemo1() {
	}

	/** full constructor */
	public Studentdemo1(String sname) {
		this.sname = sname;
	}

	// Property accessors

	public Integer getSno() {
		return this.sno;
	}

	public void setSno(Integer sno) {
		this.sno = sno;
	}

	public String getSname() {
		return this.sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}
}