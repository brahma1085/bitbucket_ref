package com.exilant.tfw.util;

import org.apache.log4j.Logger;

/**
 * The Class CypherEncryptionTest.
 */
public class CypherEncryptionTest {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(CypherEncryptionTest.class);

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws Exception
	 *             the exception
	 */
	public static void main(String[] args) throws Exception {
		CipherUtil cipherUtil = CipherUtil.getInstance();
		String encryptedFormPassword = cipherUtil.encrypt("Apr@2013");
		LOG.info(encryptedFormPassword);
		String decryptedString = cipherUtil.decrypt(encryptedFormPassword);
		LOG.info(decryptedString);
	}

}
