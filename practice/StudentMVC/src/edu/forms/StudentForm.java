package edu.forms;

import org.forms.ActionForm;

public class StudentForm extends ActionForm {
	private String studentNo;
	private String studentName;

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public boolean validate() {
		boolean flag = false;
		if (getStudentNo() == null || "".equalsIgnoreCase(getStudentNo()))
			return true;
		return flag;
	}
}
