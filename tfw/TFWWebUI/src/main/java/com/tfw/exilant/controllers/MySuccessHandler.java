package com.tfw.exilant.controllers;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * The Class MySuccessHandler.
 */
public class MySuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(MySuccessHandler.class);

	/** The custom authentication. */
	private CustomAuthentication customAuthentication;

	/**
	 * Sets the custom authentication.
	 *
	 * @param customAuthentication the new custom authentication
	 */
	public void setCustomAuthentication(CustomAuthentication customAuthentication) {
		this.customAuthentication = customAuthentication;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException,
			ServletException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("onAuthenticationSuccess(HttpServletRequest, HttpServletResponse, Authentication) - start"); //$NON-NLS-1$
		}

		Authentication authenticated = customAuthentication.authenticate(authentication);
		if (authenticated != null) {
			super.onAuthenticationSuccess(request, response, authenticated);
			String username = authenticated.getName();
			request.getSession().setAttribute("username", username);
			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			request.getSession().setAttribute("authorities", authorities);
		} else {
			if (LOG.isDebugEnabled()) {
				LOG.debug("onAuthenticationSuccess(HttpServletRequest, HttpServletResponse, Authentication) - end"); //$NON-NLS-1$
			}
			return;
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("onAuthenticationSuccess(HttpServletRequest, HttpServletResponse, Authentication) - end"); //$NON-NLS-1$
		}
	}

}
