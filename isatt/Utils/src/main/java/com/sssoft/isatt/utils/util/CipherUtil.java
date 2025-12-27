package com.sssoft.isatt.utils.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

import com.sssoft.isatt.utils.bean.UtlConf;

/**
 * The Class CipherUtil.
 */
public class CipherUtil {

	/** The Constant LOG. */
	private final static Logger LOG = Logger.getLogger(CipherUtil.class);

	/** The encryption algorithm */
	private static final String ALGORITHM = "AES/ECB/PKCS5PADDING";

	/** The secret key. */
	private static SecretKeySpec secretKey;
	private static byte[] key;
	private static String decryptedString;
	private static String encryptedString;

	/** The cipher. */
	private static Cipher cipher;

	static {
		MessageDigest sha = null;
		try {
			key = UtlConf.getProperty("appEncKey").getBytes();
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16); // use only first 128 bit
			secretKey = new SecretKeySpec(key, "AES");
		} catch (NoSuchAlgorithmException e) {
			if (LOG.isDebugEnabled()) {
				LOG.error("Error in setting the key due to : " + e.getMessage());
			}
		}
	}

	/**
	 * param key1 shouldbe string of len 32.
	 * 
	 * @param key1
	 *            the key1
	 * @throws Exception
	 *             the exception
	 */
	private CipherUtil() {
	}

	public static CipherUtil getInstance() throws CloneNotSupportedException {
		try {
			cipher = Cipher.getInstance(CipherUtil.ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			if (LOG.isDebugEnabled()) {
				LOG.error("Error in setting the key due to : " + e.getMessage());
			}
		} catch (NoSuchPaddingException e) {
			if (LOG.isDebugEnabled()) {
				LOG.error("Error in setting the key due to : " + e.getMessage());
			}
		}
		return new CipherUtil();
	}
	/**
	 * Encrypt.
	 * 
	 * @param strToEncrypt
	 *            the str to encrypt
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String encrypt(String strToEncrypt) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("encrypt(String) - start");
		}
		try {
			synchronized(this) {
				cipher.init(Cipher.ENCRYPT_MODE, secretKey);
				setEncryptedString(Base64.encodeBytes(cipher.doFinal(strToEncrypt.getBytes())));
			}
			return getEncryptedString();
		} catch (Exception e) {
			LOG.error("Error while encrypting :" + e + "] strToEncrypt :" + strToEncrypt, e);
			throw e;
		}
	}

	/**
	 * Decrypt.
	 * 
	 * @param strToDecrypt
	 *            the str to decrypt
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String decrypt(String strToDecrypt) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("decrypt(String) - start"); //$NON-NLS-1$
		}
		try {
			synchronized(this) {
				cipher.init(Cipher.DECRYPT_MODE, secretKey);
				setDecryptedString(new String(cipher.doFinal(Base64.decode(strToDecrypt))));
			}
		} catch (Exception e) {
			LOG.error("Error while decrypting :" + e + "] strToDecrypt : " + strToDecrypt, e);
			throw e;
		}
		return getDecryptedString();
	}

	/**
	 * @return the version
	 */

	public static final String getVersion() {
		return "1.0";
	}

	/**
	 * gets the decrypted string
	 * 
	 * @return
	 */
	public static String getDecryptedString() {
		return decryptedString;
	}

	/**
	 * sets the decrypted string
	 * 
	 * @param decryptedString
	 */
	private static void setDecryptedString(String decryptedString) {
		CipherUtil.decryptedString = decryptedString;
	}

	/**
	 * gets the encrypted string
	 * 
	 * @return
	 */
	public static String getEncryptedString() {
		return encryptedString;
	}

	/**
	 * sets the encrypted string
	 * 
	 * @param encryptedString
	 */
	private static void setEncryptedString(String encryptedString) {
		CipherUtil.encryptedString = encryptedString;
	}

}
