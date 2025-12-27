package edu.actions;

import org.actions.Action;

import edu.forms.EmployeeForm;

public class EmployeeAction implements Action {

	public String execute(Object actionForm) {
		EmployeeForm employeeForm = (EmployeeForm) actionForm;
		System.out.println(employeeForm.getEmployeeNo());
		System.out.println(employeeForm.getEmployeeName());
		return "forward";
	}
}
