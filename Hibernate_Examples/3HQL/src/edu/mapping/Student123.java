package edu.mapping;

/**
 * Student123 entity. @author MyEclipse Persistence Tools
 */

public class Student123 implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 1L;
	private Integer studentNo;
	private String studentName;

	// Constructors

	/** default constructor */
	public Student123() {
	}

	/** full constructor */
	public Student123(String studentName) {
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