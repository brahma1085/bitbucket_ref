package edu.pojo;

public class Student extends AbstractStudent {
	private String studentName;
	private StudentXtra studentXtra;

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public StudentXtra getStudentXtra() {
		return studentXtra;
	}

	public void setStudentXtra(StudentXtra studentXtra) {
		this.studentXtra = studentXtra;
	}

}
