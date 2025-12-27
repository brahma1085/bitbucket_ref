package com.tfw.exilant.ITAP;

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

import com.exilant.tfw.service.MainService;
import com.exilant.tfw.util.CipherUtil;

@Controller
public class ResetControllerITAP {
	private static final Logger LOG = Logger.getLogger(ResetControllerITAP.class);

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
	 * @param mainService
	 *            the new main service
	 */
	public void setMainService(MainService mainService) {
		this.mainService = mainService;
	}

	/**
	 * Gets the all applications.
	 * 
	 * @param guestName
	 *            the guest name
	 * @param request
	 *            the request
	 * @return the all applications
	 */
	@RequestMapping(value = "/resetPasswordITAP", method = RequestMethod.POST)
	public @ResponseBody
	void resetPassword(@RequestBody Map<String, String> resetPassword) {
		JSONObject jsonObject = new JSONObject(resetPassword);
		try {
			
			String username1 = (String) jsonObject.get("username");
			String username = username1.replaceAll(" ", "+");
			System.out.println("username "+username);
			//String username = "kowKCwtUeQzDfJsXGmPGag==";
			String newPassword = (String) jsonObject.get("newPassword");
			String confirmNewPassword = (String) jsonObject.get("confirmNewPassword");
			if (newPassword.equals(confirmNewPassword)) {
				mainCrpt = CipherUtil.getInstance();
				String encryptedNewPassword = this.enc(newPassword);
				
				String decryptedUsername = this.dec(username);
				mainService.updatePasswordByUsername(decryptedUsername, encryptedNewPassword);
				String emailID = mainService.getEmailByUsername(decryptedUsername);
				String from = "admin@itap.com";
				String to = emailID;
				String smtpServer = "mail.exilant.com";
				int smtpPort = 25;
				Properties properties = System.getProperties();
				properties.put("mail.smtp.host", smtpServer);
				properties.put("mail.smtp.port", smtpPort);
				Session session = Session.getDefaultInstance(properties);
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(from));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				message.setSubject("Reset iTAP Password");
				message.setText("Dear " + decryptedUsername + ", Your iTAP Password has been successfully Reset to " + newPassword
						+ " Best Regards, iTAP Admin");
				Transport.send(message);
				mainService.updateIncorrectPasswordCount(decryptedUsername, 0);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (com.exilant.tfw.exception.ServiceException e) {
			LOG.error("getAllApplications(String)", e); //$NON-NLS-1$
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("forgotPassword(String, String) - end"); //$NON-NLS-1$
		}
	}

	public String enc(String s) throws Exception {
		return mainCrpt.encrypt(s);

	}

	public String dec(String s) throws Exception {
		return mainCrpt.decrypt(s);

	}
}
