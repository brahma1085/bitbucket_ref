package edu.mapping;

/**
 * Student entity. @author MyEclipse Persistence Tools
 */

public class Student implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int studentNo;
	private String studentName;

	// Constructors

	/** default constructor */
	public Student() {
	}

	/** minimal constructor */
	public Student(int studentNo) {
		this.studentNo = studentNo;
	}

	public int getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(int studentNo) {
		this.studentNo = studentNo;
	}

	/** full constructor */
	public Student(int studentNo, String studentName) {
		this.studentNo = studentNo;
		this.studentName = studentName;
	}

	// Property accessors

	public String getStudentName() {
		return this.studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

}