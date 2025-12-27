package com.sssoft.isatt.ui.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sssoft.isatt.data.pojo.Users;

/**
 * The Class LoginController.
 * 
 * @author mohammedfirdos
 */

@Controller
public class LoginController {

	/**
	 * Prints the welcome.
	 * 
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "/iTAPUI", method = RequestMethod.GET)
	public @ResponseBody
	String printWelcome() {
		Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (object instanceof Users) {
			return "hello";
		}
		return ".";
	}

	/**
	 * Login.
	 * 
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public @ResponseBody
	String login() {
		Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (object instanceof Users) {
			return "login";
		}
		return "login";
	}

	/**
	 * Loginerror.
	 * 
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
	public @ResponseBody
	String loginerror(ModelMap model) {
		model.addAttribute("error", "true");
		return "login";
	}

	/**
	 * Logout.
	 * 
	 * @param model
	 *            the model
	 * @return the string
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public @ResponseBody
	String logout(HttpServletRequest request) {
		HttpSession session = null;
		if (request != null) {
			session = request.getSession(false);
		}
		if (session != null) {
			session.invalidate();
		}
		return "login";
	}
}