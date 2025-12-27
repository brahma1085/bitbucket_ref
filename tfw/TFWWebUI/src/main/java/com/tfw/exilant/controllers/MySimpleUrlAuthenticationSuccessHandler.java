package com.tfw.exilant.controllers;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.exilant.tfw.util.CipherUtil;

/**
 * The Class MySimpleUrlAuthenticationSuccessHandler.
 * 
 * @author mohammedfirdos
 */
public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(MySimpleUrlAuthenticationSuccessHandler.class);

	/** The session. */
	HttpSession session;

	/** The redirect strategy. */
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	/** The custom authentication. */
	private CustomAuthentication customAuthentication;

	/**
	 * Sets the custom authentication.
	 * 
	 * @param customAuthentication
	 *            the new custom authentication
	 */
	public void setCustomAuthentication(CustomAuthentication customAuthentication) {
		this.customAuthentication = customAuthentication;
	}

	/**
	 * Instantiates a new my simple url authentication success handler.
	 */
	protected MySimpleUrlAuthenticationSuccessHandler() {
		super();
	}

	// API

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.web.authentication.AuthenticationSuccessHandler
	 * #onAuthenticationSuccess(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse,
	 * org.springframework.security.core.Authentication)
	 */
	@Override
	public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication)
			throws IOException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("onAuthenticationSuccess(HttpServletRequest, HttpServletResponse, Authentication) - start"); //$NON-NLS-1$
		}

		Authentication authenticated = customAuthentication.authenticate(authentication);
		if (authenticated != null) {
			handle(request, response, authentication);
			clearAuthenticationAttributes(request);
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

	// IMPL

	/**
	 * Handle.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param authentication
	 *            the authentication
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	protected void handle(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("handle(HttpServletRequest, HttpServletResponse, Authentication) - start"); //$NON-NLS-1$
		}

		final String targetUrl = determineTargetUrl(authentication, request);

		if (response.isCommitted()) {
			LOG.debug("Response has already been committed. Unable to redirect to " + targetUrl);
			return;
		}
		redirectStrategy.sendRedirect(request, response, targetUrl);

		if (LOG.isDebugEnabled()) {
			LOG.debug("handle(HttpServletRequest, HttpServletResponse, Authentication) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Determine target url.
	 * 
	 * @param authentication
	 *            the authentication
	 * @param request
	 *            the request
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	protected String determineTargetUrl(final Authentication authentication, final HttpServletRequest request) throws IOException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("determineTargetUrl(Authentication, HttpServletRequest) - start"); //$NON-NLS-1$
		}

		boolean isManager = false;
		boolean isLead = false;
		boolean isEngineer = false;
		boolean isAdmin = false;
		final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		String username = authentication.getName();
		session = request.getSession(true);
		for (final GrantedAuthority grantedAuthority : authorities) {
			if ("ROLE_MANAGER".equals(grantedAuthority.getAuthority())) {
				session.setAttribute("currentRoleUser", grantedAuthority);
				session.setAttribute("filterUserName", username);
				isManager = true;
				break;
			} else if ("ROLE_LEAD".equals(grantedAuthority.getAuthority())) {
				session.setAttribute("currentRoleUser", grantedAuthority);
				session.setAttribute("filterUserName", username);
				isLead = true;
				break;
			} else if ("ROLE_ENGINEER".equals(grantedAuthority.getAuthority())) {
				session.setAttribute("currentRoleUser", grantedAuthority);
				session.setAttribute("filterUserName", username);
				isEngineer = true;
				break;
			} else if ("ROLE_ADMIN".equals(grantedAuthority.getAuthority())) {
				session.setAttribute("currentRoleUser", grantedAuthority);
				session.setAttribute("filterUserName", username);
				isAdmin = true;
				break;
			}
		}
		String encryptedRole = null;
		if (isAdmin || isManager || isLead || isEngineer) {
			String queryValue = session.getAttribute("currentRoleUser").toString();
			String queryUser = session.getAttribute("filterUserName").toString();
			try {
				encryptedRole = CipherUtil.getInstance().encrypt(queryValue);
			} catch (Exception e) {
				LOG.warn("determineTargetUrl(Authentication, HttpServletRequest) - exception ignored", e); //$NON-NLS-1$
			}
			// return
			// "http://localhost:8280/TFWWebAppl%2D1%2E0%2E0%2DBUILD%2DSNAPSHOT/?page="
			// + encryptedRole + "&loggedInUser=" + queryUser;
			String returnString = "http://localhost:9082/TFWWebUI/?page=" + encryptedRole + "&loggedInUser=" + queryUser;
			if (LOG.isDebugEnabled()) {
				LOG.debug("determineTargetUrl(Authentication, HttpServletRequest) - end"); //$NON-NLS-1$
			}
			return returnString;
		} else {
			throw new IllegalStateException();
		}
	}

	/**
	 * Removes temporary authentication-related data which may have been stored
	 * in the session during the authentication process.
	 * 
	 * @param request
	 *            the request
	 */
	protected final void clearAuthenticationAttributes(final HttpServletRequest request) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("clearAuthenticationAttributes(HttpServletRequest) - start"); //$NON-NLS-1$
		}

		final HttpSession session = request.getSession(false);

		if (session == null) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("clearAuthenticationAttributes(HttpServletRequest) - end"); //$NON-NLS-1$
			}
			return;
		}

		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

		if (LOG.isDebugEnabled()) {
			LOG.debug("clearAuthenticationAttributes(HttpServletRequest) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Sets the redirect strategy.
	 * 
	 * @param redirectStrategy
	 *            the new redirect strategy
	 */
	public void setRedirectStrategy(final RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	/**
	 * Gets the redirect strategy.
	 * 
	 * @return the redirect strategy
	 */
	protected RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}

}