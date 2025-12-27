package com.sssoft.isatt.utils.util.mail;

import java.io.IOException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.sssoft.isatt.utils.bean.MailingBean;

/**
 * The Class iTAPSSLMailingUtility.
 */
public class iTAPSSLMailingUtility {
	
	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(iTAPSSLMailingUtility.class);

	/**
	 * Send mail.
	 *
	 * @param mail the mail
	 * @param attachment the attachment
	 */
	public static void sendMail(MailingBean mail, boolean attachment) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("sendMail(MailingBean, boolean) - start"); //$NON-NLS-1$
		}

		String username = MailUtils.getUserName();
		String password = MailUtils.getPassword();
		try {
			Message message = new MimeMessage(MailUtils.getAuthenticatedSession(username, password));
			MailUtils.prepareMessage(message, mail);
			message.setContent(MailUtils.prepareContent(mail, attachment));
			Transport.send(message);
		} catch (IOException e) {
			LOG.error("unable to send mail, due to : " + e.getMessage(), e);
		} catch (MessagingException e) {
			LOG.error("unable to send mail, due to : " + e.getMessage(), e);
		} catch (Exception e) {
			LOG.error("unable to send mail, due to : " + e.getMessage(), e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("sendMail(MailingBean, boolean) - end"); //$NON-NLS-1$
		}
	}

}
