package com.sssoft.isatt.ui2.controller;

import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sssoft.isatt.data.service.MainService;
import com.sssoft.isatt.utils.util.CipherUtil;

/**
 * The Class ForgotPasswordController2.
 */
@Controller
public class ForgotPasswordController2 {
	
	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(ForgotPasswordController2.class);

	/** The main service. */
	private MainService mainService;
	public CipherUtil mainCrpt;
	
	/**
	 * Gets the main service.
	 *
	 * @return the main service
	 */
	public MainService getMainService() {
		return mainService;
	}

	/**
	 * Sets the main service.
	 *
	 * @param mainService the new main service
	 */
	public void setMainService(MainService mainService) {
		this.mainService = mainService;
	}

	/**
	 * Gets the all applications.
	 *
	 * @param guestName the guest name
	 * @param request the request
	 * @return the all applications
	 */
	@RequestMapping(value = "/forgotPasswordITAP", method = RequestMethod.POST)
	public @ResponseBody
	String forgotPassword(@RequestBody Map<String, String> forgotPassword) {
		String password = null;
		
		JSONObject jsonObject = new JSONObject(forgotPassword);
		if (LOG.isDebugEnabled()) {
		}
		
		try {
			String username = (String) jsonObject.get("username");
			String emailID = (String) jsonObject.get("emailID");
			password = mainService.getPasswordByUsernameEmailID(username, emailID);
			
			System.out.println("Username"+ username+ "Email ID"+ emailID+  "Password"+ password );
			
			try {
				mainCrpt = CipherUtil.getInstance();
				String decryptedPassword = this.dec(password);
				System.out.println("Decrypted Password is: "+ decryptedPassword );
			} catch (Exception e) {
				e.printStackTrace();
			}
			
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
		         
		         try {
						mainCrpt = CipherUtil.getInstance();
						String decryptedPassword = this.dec(password);
						System.out.println("Decrypted Password is: "+ decryptedPassword );
						
						message.setText("Dear " + username+ ", Your iTAP Password is: "+decryptedPassword +" Best Regards, iTAP Admin");
					} catch (Exception e) {
						e.printStackTrace();
					}
		        

		         // Send message
		         Transport.send(message);
		         System.out.println("message sent successfully....");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (com.sssoft.isatt.data.exception.ServiceException e) {
			LOG.error("getAllApplications(String)", e); //$NON-NLS-1$
		}  catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("forgotPassword(String, String) - end"); //$NON-NLS-1$
		}
		return password;
		

	}

	public String enc(String s) throws Exception {
		return mainCrpt.encrypt(s);

	}

	public String dec(String s) throws Exception {
		return mainCrpt.decrypt(s);

	}
	
}
