package com.sssoft.isatt.ui.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class MyFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		System.out.println(" Exception "+exception.getMessage());
		String contextpath = request.getContextPath();
//		contextpath = contextpath.substring(0, contextpath.length());
		response.sendRedirect(contextpath+"?error=true");
	}
}
