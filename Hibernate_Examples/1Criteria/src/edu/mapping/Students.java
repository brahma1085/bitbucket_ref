package edu.mapping;

/**
 * Students entity. @author MyEclipse Persistence Tools
 */

public class Students implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 1L;
	private Integer studentNo;
	private String studentName;

	// Constructors

	/** default constructor */
	public Students() {
	}

	/** full constructor */
	public Students(String studentName) {
		this.studentName = studentName;
	}

	// Property accessors

	public Integer getStudentNo() {
		return this.studentNo;
	}

	public void setStudentNo(Integer studentNo) {
		this.studentNo = studentNo;
	}

	public String getStudentName() {
		return this.studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

}