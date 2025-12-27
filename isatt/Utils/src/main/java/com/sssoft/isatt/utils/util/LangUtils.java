package com.sssoft.isatt.utils.util;

import java.net.URLDecoder;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * The Class LangUtils.
 */
public class LangUtils {

	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(LangUtils.class);

	/** The sdf formats. */
	private static Map<String, SimpleDateFormat> sdfFormats = new HashMap<String, SimpleDateFormat>();

	/** The nmbr format. */
	private static NumberFormat nmbrFormat = NumberFormat.getIntegerInstance();

	// private static ThreadLocal<Map<String, SimpleDateFormat>> sdf =
	// null;//new ThreadLocal(new HashMap<String,SimpleDateFormat>());

	/**
	 * Checks if is true.
	 * 
	 * @param s
	 *            the s
	 * @param nullOrEmptyIsTrue
	 *            the null or empty is true
	 * @return true, if is true
	 */
	public static boolean isTrue(String s, boolean nullOrEmptyIsTrue) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("isTrue(String, boolean) - start"); //$NON-NLS-1$
		}

		if (s == null || s.length() == 0) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("isTrue(String, boolean) - end"); //$NON-NLS-1$
			}
			return nullOrEmptyIsTrue;
		}
		char char1 = s.substring(0, 1).toLowerCase().charAt(0);
		boolean returnboolean = 'y' == char1 || '1' == char1 || Boolean.parseBoolean(s);
		if (LOG.isDebugEnabled()) {
			LOG.debug("isTrue(String, boolean) - end"); //$NON-NLS-1$
		}
		return returnboolean;
	}

	/*
	 * DO NOT USE THESE FOR sdf.parse(String) as that function is not thread
	 * safe. Will need a cache of thread local sdf Map for that.
	 */
	/**
	 * Gets the sdf for format.
	 * 
	 * @param pattern
	 *            the pattern
	 * @return the sdf for format
	 */
	public static final SimpleDateFormat getSdfForFormat(String pattern) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSdfForFormat(String) - start"); //$NON-NLS-1$
		}

		SimpleDateFormat sdf = sdfFormats.get(pattern);
		if (sdf == null) {
			sdf = new SimpleDateFormat(pattern);
			sdfFormats.put(pattern, sdf);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getSdfForFormat(String) - end"); //$NON-NLS-1$
		}
		return sdf;
	}

	/**
	 * Gets the sdf for parse.
	 * 
	 * @param pattern
	 *            the pattern
	 * @return the sdf for parse
	 */
	public static final SimpleDateFormat getSdfForParse(String pattern) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSdfForParse(String) - start"); //$NON-NLS-1$
		}

		SimpleDateFormat sdf = sdfFormats.get(pattern);
		if (sdf == null) {
			sdf = new SimpleDateFormat(pattern);
			sdfFormats.put(pattern, sdf);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getSdfForParse(String) - end"); //$NON-NLS-1$
		}
		return sdf;
	}

	/**
	 * Gets the int.
	 * 
	 * @param s
	 *            the s
	 * @param def
	 *            the def
	 * @param msg
	 *            the msg
	 * @return the int
	 */
	public static final int getInt(String s, int def, String msg) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getInt(String, int, String) - start"); //$NON-NLS-1$
		}

		int i = def;
		try {
			Number n = nmbrFormat.parse(s);
			if (n != null) {
				i = n.intValue();
			}
		} catch (ParseException e) {
			LOG.warn("getInt err " + e + ", input :" + s + "; msg :" + msg);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getInt(String, int, String) - end"); //$NON-NLS-1$
		}
		return i;
	}

	/**
	 * Sleep.
	 * 
	 * @param delayMilli
	 *            the delay milli
	 */
	public static void sleep(int delayMilli) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("sleep(int) - start"); //$NON-NLS-1$
		}

		try {
			Thread.sleep(delayMilli);
		} catch (Exception e) {
			LOG.error("Error in LangUtils: " + e, e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("sleep(int) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Default URLDecoder.decode without char-set. Kept in separate function so
	 * can suppress deprecation locally
	 * 
	 * @param in
	 *            the in
	 * @return the string
	 */

	@SuppressWarnings("deprecation")
	public static String urlDecode(String in) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("urlDecode(String) - start"); //$NON-NLS-1$
		}

		String returnString = URLDecoder.decode(in);
		if (LOG.isDebugEnabled()) {
			LOG.debug("urlDecode(String) - end"); //$NON-NLS-1$
		}
		return returnString;
	}
}
