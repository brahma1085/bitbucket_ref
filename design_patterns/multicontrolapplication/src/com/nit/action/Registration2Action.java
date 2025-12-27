package com.nit.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.nit.form.RegistrationForm;
import com.nit.model.Registration;
import com.nit.service.RegistrationService;

public class Registration2Action extends Action {
	RegistrationService service;
	private static final String SUCCESS = "success";
	private static final String FAILURE = "failure";

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String responsekey = FAILURE;
		RegistrationForm registrationForm = (RegistrationForm) form;
		Registration registration = new Registration();
		registration.setName(registrationForm.getName());
		registration.setPassword(registrationForm.getPassword());
		registration.setProfession(registrationForm.getProfession());
		registration.setCell(registrationForm.getCell());
		registration.setGender(registrationForm.getGender());
		registration.setAgree(registrationForm.isAgree());
		boolean flag = service.doRegistration(registration);
		if (flag)
			responsekey = SUCCESS;
		return mapping.findForward(responsekey);
	}

	public Registration2Action() {
		service = new RegistrationService();
	}
}
