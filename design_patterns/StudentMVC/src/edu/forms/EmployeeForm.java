package edu.forms;

import org.forms.ActionForm;

public class EmployeeForm extends ActionForm {
	private String employeeNo;
	private String employeeName;

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public boolean validate() {
		boolean flag = false;
		if (getEmployeeNo() == null || "".equalsIgnoreCase(getEmployeeNo()))
			return true;
		return flag;
	}
}
