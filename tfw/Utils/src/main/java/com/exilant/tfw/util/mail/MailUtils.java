package com.exilant.tfw.util.mail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import com.exilant.tfw.bean.MailingBean;

/**
 * The Class MailUtils.
 */
public class MailUtils {
	
	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(MailUtils.class);

	/** The mail properties. */
	private static Properties mailProperties;

	/**
	 * Gets the mail properties.
	 *
	 * @return the mail properties
	 */
	public static Properties getMailProperties() {
		return mailProperties;
	}

	/**
	 * Sets the mail properties.
	 *
	 * @param mailProperties the new mail properties
	 */
	public static void setMailProperties(Properties mailProperties) {
		MailUtils.mailProperties = mailProperties;
	}

	/**
	 * Gets the value.
	 *
	 * @param key the key
	 * @return the value
	 */
	public static String getValue(String key) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getValue(String) - start"); //$NON-NLS-1$
		}

		String returnString = MailUtils.mailProperties.getProperty(key);
		if (LOG.isDebugEnabled()) {
			LOG.debug("getValue(String) - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public static String getUserName() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getUserName() - start"); //$NON-NLS-1$
		}

		initMailConfigurations();
		String returnString = getValue("username");
		if (LOG.isDebugEnabled()) {
			LOG.debug("getUserName() - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public static String getPassword() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getPassword() - start"); //$NON-NLS-1$
		}

		initMailConfigurations();
		String returnString = getValue("password");
		if (LOG.isDebugEnabled()) {
			LOG.debug("getPassword() - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	/**
	 * Inits the mail configurations.
	 */
	public static void initMailConfigurations() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("initMailConfigurations() - start"); //$NON-NLS-1$
		}

		if (mailProperties == null) {
			try {
				InputStream inputStream = MailUtils.class.getClassLoader().getResourceAsStream("mail.properties");
				mailProperties = new Properties();
				mailProperties.load(inputStream);
				MailUtils.setMailProperties(mailProperties);
				LOG.info("mail properties : " + mailProperties.values());
			} catch (IOException e) {
				LOG.error("unable to load mail configurations, due to : " + e.getMessage(), e);
			} catch (Exception e) {
				LOG.error("unable to load mail configurations, due to : " + e.getMessage(), e);
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("initMailConfigurations() - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Gets the authenticated session.
	 *
	 * @param username the username
	 * @param password the password
	 * @return the authenticated session
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Session getAuthenticatedSession(final String username, final String password) throws IOException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAuthenticatedSession(String, String) - start"); //$NON-NLS-1$
		}

		Session session = Session.getInstance(getMailProperties(), new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				if (LOG.isDebugEnabled()) {
					LOG.debug("$javax.mail.Authenticator.getPasswordAuthentication() - start"); //$NON-NLS-1$
				}

				PasswordAuthentication returnPasswordAuthentication = new PasswordAuthentication(username, password);
				if (LOG.isDebugEnabled()) {
					LOG.debug("$javax.mail.Authenticator.getPasswordAuthentication() - end"); //$NON-NLS-1$
				}
				return returnPasswordAuthentication;
			}
		});

		if (LOG.isDebugEnabled()) {
			LOG.debug("getAuthenticatedSession(String, String) - end"); //$NON-NLS-1$
		}
		return session;
	}

	/**
	 * Prepare message.
	 *
	 * @param message the message
	 * @param mail the mail
	 * @throws AddressException the address exception
	 * @throws MessagingException the messaging exception
	 */
	public static void prepareMessage(Message message, MailingBean mail) throws AddressException, MessagingException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("prepareMessage(Message, MailingBean) - start"); //$NON-NLS-1$
		}

		String from = mail.getFromAddress();
		if (from != null) {
			if (from.equals(getUserName())) {
				message.setFrom(new InternetAddress(from));
			} else {
				LOG.error("provided from address is not having any matching authnetication details");
			}
		} else {
			message.setFrom(new InternetAddress(getValue("from")));
		}
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail.getToAddress()));
		String cc = mail.getCcAddress();
		String bcc = mail.getBccAddress();
		if (cc != null) {
			message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
		}
		if (bcc != null) {
			message.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc));
		}
		message.setSubject(mail.getMailSubject());

		if (LOG.isDebugEnabled()) {
			LOG.debug("prepareMessage(Message, MailingBean) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Prepare content.
	 *
	 * @param mail the mail
	 * @param attachment the attachment
	 * @return the multipart
	 * @throws MessagingException the messaging exception
	 */
	public static Multipart prepareContent(MailingBean mail, boolean attachment) throws MessagingException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("prepareContent(MailingBean, boolean) - start"); //$NON-NLS-1$
		}

		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setText(mail.getMailText());
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		if (attachment) {
			multipart.addBodyPart(prepareAttachment(mail));
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("prepareContent(MailingBean, boolean) - end"); //$NON-NLS-1$
		}
		return multipart;
	}

	/**
	 * Prepare attachment.
	 *
	 * @param mail the mail
	 * @return the body part
	 * @throws MessagingException the messaging exception
	 */
	public static BodyPart prepareAttachment(MailingBean mail) throws MessagingException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("prepareAttachment(MailingBean) - start"); //$NON-NLS-1$
		}

		BodyPart mimeBodyPart = new MimeBodyPart();
		File file = mail.getMailAttachment();
		DataSource source = new FileDataSource(file);
		mimeBodyPart.setDataHandler(new DataHandler(source));
		mimeBodyPart.setFileName(file.getName() + new Date(System.currentTimeMillis()));

		if (LOG.isDebugEnabled()) {
			LOG.debug("prepareAttachment(MailingBean) - end"); //$NON-NLS-1$
		}
		return mimeBodyPart;
	}

}
