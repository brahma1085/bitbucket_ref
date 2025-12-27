package com.sssoft.isatt.utils.bean;

import java.io.File;
import java.io.Serializable;

/**
 * The Class MailingBean.
 */
public class MailingBean implements Serializable {

	/** Default Serial Version ID. */
	private static final long serialVersionUID = 1L;

	/** The from address. */
	private String fromAddress;
	
	/** The to address. */
	private String toAddress;
	
	/** The cc address. */
	private String ccAddress;
	
	/** The bcc address. */
	private String bccAddress;
	
	/** The mail subject. */
	private String mailSubject;
	
	/** The mail text. */
	private String mailText;
	
	/** The mail attachment. */
	private File mailAttachment;

	/**
	 * Gets the from address.
	 *
	 * @return the from address
	 */
	public String getFromAddress() {
		return fromAddress;
	}

	/**
	 * Sets the from address.
	 *
	 * @param fromAddress the new from address
	 */
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	/**
	 * Gets the to address.
	 *
	 * @return the to address
	 */
	public String getToAddress() {
		return toAddress;
	}

	/**
	 * Sets the to address.
	 *
	 * @param toAddress the new to address
	 */
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	/**
	 * Gets the cc address.
	 *
	 * @return the cc address
	 */
	public String getCcAddress() {
		return ccAddress;
	}

	/**
	 * Sets the cc address.
	 *
	 * @param ccAddress the new cc address
	 */
	public void setCcAddress(String ccAddress) {
		this.ccAddress = ccAddress;
	}

	/**
	 * Gets the bcc address.
	 *
	 * @return the bcc address
	 */
	public String getBccAddress() {
		return bccAddress;
	}

	/**
	 * Sets the bcc address.
	 *
	 * @param bccAddress the new bcc address
	 */
	public void setBccAddress(String bccAddress) {
		this.bccAddress = bccAddress;
	}

	/**
	 * Gets the mail subject.
	 *
	 * @return the mail subject
	 */
	public String getMailSubject() {
		return mailSubject;
	}

	/**
	 * Sets the mail subject.
	 *
	 * @param mailSubject the new mail subject
	 */
	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	/**
	 * Gets the mail text.
	 *
	 * @return the mail text
	 */
	public String getMailText() {
		return mailText;
	}

	/**
	 * Sets the mail text.
	 *
	 * @param mailText the new mail text
	 */
	public void setMailText(String mailText) {
		this.mailText = mailText;
	}

	/**
	 * Gets the mail attachment.
	 *
	 * @return the mail attachment
	 */
	public File getMailAttachment() {
		return mailAttachment;
	}

	/**
	 * Sets the mail attachment.
	 *
	 * @param mailAttachment the new mail attachment
	 */
	public void setMailAttachment(File mailAttachment) {
		this.mailAttachment = mailAttachment;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MailingBean [fromAddress=" + fromAddress + ", toAddress=" + toAddress + ", ccAddress=" + ccAddress + ", bccAddress=" + bccAddress
				+ ", mailSubject=" + mailSubject + "]";
	}

}
