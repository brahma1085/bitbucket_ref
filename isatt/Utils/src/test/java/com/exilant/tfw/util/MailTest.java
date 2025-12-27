package com.exilant.tfw.util;

import org.apache.log4j.Logger;

import com.sssoft.isatt.utils.bean.MailingBean;
import com.sssoft.isatt.utils.util.mail.iTAPSSLMailingUtility;

/**
 * The Class MailTest.
 */
public class MailTest {
	
	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(MailTest.class);

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		MailingBean bean = new MailingBean();
		bean.setFromAddress("brahmareddy.k@exilant.com");
		bean.setToAddress("brahmareddy.k@exilant.com");
		bean.setMailSubject("test mail - iTAP");
		bean.setMailText("test mail - iTAP");
		iTAPSSLMailingUtility.sendMail(bean, false);
		LOG.info("mail sent");
	}
}
