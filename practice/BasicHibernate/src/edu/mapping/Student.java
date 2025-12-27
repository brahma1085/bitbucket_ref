package edu.mapping;

/**
 * Student entity. @author MyEclipse Persistence Tools
 */

public class Student implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	
	public void aa(){
		try{
			
		}catch(NumberFormatException e){
			
		}
	}
	private static final long serialVersionUID = 1L;
	private Integer studentNo;
	private String studentName;

	// Constructors

	/** default constructor */
	public Student() {
	}

	/** minimal constructor */
	public Student(Integer studentNo) {
		this.studentNo = studentNo;
	}

	/** full constructor */
	public Student(Integer studentNo, String studentName) {
		this.studentNo = studentNo;
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