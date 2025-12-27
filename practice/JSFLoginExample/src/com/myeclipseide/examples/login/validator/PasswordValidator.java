package com.myeclipseide.examples.login.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class PasswordValidator implements Validator {
	public void validate(FacesContext context, UIComponent component,
			Object value) {

		if (value == null)
			return;
		String password = value.toString();

		if (!valueCheck(password)) {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Please enter MyEclipse as the password.", null);
			throw new ValidatorException(message);
		}
	}

	private static boolean valueCheck(String password) {
		if (password.equalsIgnoreCase("myeclipse")) {
			return true;
		}
		return false;
	}
}
