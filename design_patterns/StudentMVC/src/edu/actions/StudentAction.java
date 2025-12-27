package edu.actions;

import org.actions.Action;

import edu.forms.StudentForm;

public class StudentAction implements Action {

	public String execute(Object actionForm) {
		StudentForm studentForm = (StudentForm) actionForm;
		System.out.println(studentForm.getStudentNo());
		System.out.println(studentForm.getStudentName());
		return "forward";
	}
}
