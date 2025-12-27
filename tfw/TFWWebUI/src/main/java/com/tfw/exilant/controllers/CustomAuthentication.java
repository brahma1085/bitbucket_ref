package com.tfw.exilant.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import com.exilant.tfw.pojo.Users;
import com.exilant.tfw.service.MainService;
import com.exilant.tfw.util.CipherUtil;

/**
 * The Class CustomAuthentication.
 */
public class CustomAuthentication implements AuthenticationProvider {

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(CustomAuthentication.class);

	/** The main service. */
	private MainService mainService;

	/**
	 * Sets the main service.
	 * 
	 * @param mainService
	 *            the mainService to set
	 */
	public void setMainService(MainService mainService) {
		this.mainService = mainService;
	}

	public CipherUtil mainCrpt;
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.authentication.AuthenticationProvider#
	 * authenticate(org.springframework.security.core.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("authenticate(Authentication) - start"); //$NON-NLS-1$
		}

		String username = authentication.getName();
		String password = (String) authentication.getCredentials();
		String errMsg = null;

		try {
			if (username.length() != 0) {
				CipherUtil appCiper = CipherUtil.getInstance();
				Users users = mainService.getUsersByName(username);
				if (users != null) {
					int userId = users.getUserID();
					String pwd = users.getPassword();
					if (pwd != null) {
						String decryptedFormPassword = appCiper.decrypt(pwd);
						if (password.length() != 0) {
							if (password != null && password.equals(decryptedFormPassword)) {
								if (LOG.isDebugEnabled()) {
									LOG.debug("printWelcome(ModelMap) - end"); //$NON-NLS-1$
								}
								List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
								List<String> authority = this.mainService.getUserRoleById(userId);
								for (String auth : authority) {
									authorities.add(new GrantedAuthorityImpl(auth));
								}
								
								Authentication returnAuthentication = new UsernamePasswordAuthenticationToken(username, password, authorities);
								if (LOG.isDebugEnabled()) {
									LOG.debug("authenticate(Authentication) - end"); //$NON-NLS-1$
								}
								return returnAuthentication;
							} else {
								
								System.out.println("You have entered Incorrect Username/Password");
								int incorrectPasswordCount = mainService.getIncorrectPasswordCount(username) ;
								System.out.println("IncorrectPasswordCount" + incorrectPasswordCount);
								
								long updatedIncorrectPasswordCount = mainService.updateIncorrectPasswordCount(username, ++incorrectPasswordCount);
								System.out.println("Updated IncorrectPasswordCount" + updatedIncorrectPasswordCount);
								if(incorrectPasswordCount > 3) {
									System.out.println("Exceeded incorrect credentials 3 times...");
									String emailID = mainService.getEmailByUsername(username);
									System.out.println("Email ID"+ emailID);
									String from = "admin@itap.com";
									String to = emailID;//change accordingly
								    String smtpServer = "mail.exilant.com";
								    int smtpPort = 25;
								   // String host = "mail.smtp.host";//or IP address
									
								  //Get the session object
								      Properties properties = System.getProperties();
								    //  properties.setProperty("mail.exilant.com", host);
								      properties.put("mail.smtp.host", smtpServer);
								      properties.put("mail.smtp.port", smtpPort);
								      
								      Session session = Session.getDefaultInstance(properties);
								    
								    //compose the message
								      	
								         MimeMessage message = new MimeMessage(session);
								         message.setFrom(new InternetAddress(from));
										
								         message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
								         message.setSubject("iTAP Recovery Password");
								         mainCrpt = CipherUtil.getInstance();
										 String encryptedUsername = this.enc(username);
								         message.setText("Dear "+username + " You have made 3 unsuccessful attempt(s). The maximum retry attempts allowed for login are 3. Password is case-sensitive. To generate new password "+" Please click on the below link" + " Reset Password: http://localhost:9082/TFWWebUI/reset_password.jsp?username="+encryptedUsername );
								         
								       
								         System.out.println("message "+message);
								         // Send message
								         Transport.send(message);
								         System.out.println("message sent successfully....");
									
									throw new AuthenticationCredentialsNotFoundException("Your Account has been Locked!!!");
								} else {
									LOG.error("The Username or Password entered is incorrect");
									errMsg = "The Username or Password entered is incorrect";
									throw new AuthenticationCredentialsNotFoundException("The Username or Password entered is incorrect");
								}
							}
						} else {
							LOG.error(" Please provide the password on the below password textbox");
							errMsg = " Please provide the password on the below password textbox";
							throw new AuthenticationCredentialsNotFoundException(" Please provide the password on the below password textbox");
						}
					} else {
						LOG.error("The Username or Password entered is incorrect");
						errMsg = "The Username or Password entered is incorrect";
						throw new AuthenticationCredentialsNotFoundException("The Username or Password entered is incorrect");
					}
				} else {
					LOG.error("The Username or Password entered is incorrect");
					errMsg = "The Username or Password entered is incorrect";
					throw new AuthenticationCredentialsNotFoundException("The Username or Password entered is incorrect");
				}
			} else {
				LOG.error("Please provide the username on the below Username textbox");
				errMsg = "Please provide the username on the below Username textbox.";
				throw new AuthenticationCredentialsNotFoundException("Please provide the username on the below Username textbox.");
			}
		} catch (Exception e) {
			LOG.error("authenticate(Authentication)", e); //$NON-NLS-1$

			throw new AuthenticationCredentialsNotFoundException(errMsg);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.authentication.AuthenticationProvider#supports
	 * (java.lang.Class)
	 */
	@Override
	public boolean supports(Class<? extends Object> authentication) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("supports(Class<? extends Object>) - start"); //$NON-NLS-1$
		}

		boolean returnboolean = authentication.equals(UsernamePasswordAuthenticationToken.class);
		if (LOG.isDebugEnabled()) {
			LOG.debug("supports(Class<? extends Object>) - end"); //$NON-NLS-1$
		}
		return returnboolean;
	}
	
	public String enc(String s) throws Exception {
		return mainCrpt.encrypt(s);

	}

	public String dec(String s) throws Exception {
		return mainCrpt.decrypt(s);

	}
	
}