package com.exilant.tfw.bean;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

/**
 * This class is used to instantiate the data source using the properties file.
 * Pooled connection creation, closing implementation is provided with this
 * class.
 */
public class UtlConf {
	
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(UtlConf.class);
	
	/** The Constant MAIN_PROPS. */
	private static final Properties MAIN_PROPS = new Properties();
	
	/** The app data root. */
	private static String appDataRoot;
	
	/** The app verifier home dir. */
	private static String appVerifierHomeDir;
	
	/** The tfw home dir. */
	private static File tfwHomeDir;

	static {
		initialize();
		// java.lang.reflect.Method m;
		// m.setAccessible(arg0)
	}

	/**
	 * Method to initialize the properties and the data source by using the
	 * database properties configured in user defined file.
	 */
	public static void initialize() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("initialize() - start"); //$NON-NLS-1$
		}

		String filePath = getConfigFilePath();
		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(filePath));
			Properties p = new Properties();
			p.load(is);
			MAIN_PROPS.clear();// for re init, only discard current if new set
								// was loaded successfully.
			// MAIN_PROPS.putAll(p);
			MAIN_PROPS.putAll(updateProp(p, MAIN_PROPS.getProperty("tfwHomeDir", "/data/tfw")));
			String stfwHomeDir = MAIN_PROPS.getProperty("tfwHomeDir", "/data/tfw");
			setTfwHomeDir(new File(stfwHomeDir));
			appVerifierHomeDir = MAIN_PROPS.getProperty("verifiers.home", appVerifierHomeDir);// {r}
		} catch (Exception e) {
			LOG.warn("UtlConf Init " + e, e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e2) {
					LOG.warn("UtlConf Init close is :" + e2, e2);
				}
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("initialize() - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Gets the configuration file path.
	 *
	 * @return String
	 */
	public static String getConfigFilePath() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getConfigFilePath() - start"); //$NON-NLS-1$
		}

		appDataRoot = System.getenv("iTAP_CONFIGURATION_FILE_PATH");
		if (appDataRoot != null) {
			appVerifierHomeDir = appDataRoot;
			appDataRoot = appDataRoot + "/main.properties";
			File configFile = new File(appDataRoot);
			if (!configFile.exists()) {
				LOG.error("No iTAP Configuration file found");
			}
		} else {
			InputStream inputStream = UtlConf.class.getClassLoader().getResourceAsStream("main.properties");
			OutputStream outputStream = null;
			File newFile = new File("main.properties");
			try {
				outputStream = new FileOutputStream(newFile);
				int read = 0;
				byte[] bytes = new byte[1024];
				while ((read = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}
			} catch (IOException e) {
				LOG.error("No iTAP Configuration file found. " + e.getMessage(), e);
			} catch (Exception e) {
				LOG.error("No iTAP Configuration file found. " + e.getMessage(), e);
			} finally {
				IOUtils.closeQuietly(inputStream);
				IOUtils.closeQuietly(outputStream);
			}
			appDataRoot = newFile.getAbsolutePath();
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getConfigFilePath() - end"); //$NON-NLS-1$
		}
		return appDataRoot;
	}

	/**
	 * Replaces the <,>,'," entities with HTML encoded string.
	 *
	 * @param in the in
	 * @return the string
	 */
	public static String replaceHtmlEnt(String in) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("replaceHtmlEnt(String) - start"); //$NON-NLS-1$
		}

		String returnString = in.replace("\"", "&quot;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>");
		if (LOG.isDebugEnabled()) {
			LOG.debug("replaceHtmlEnt(String) - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	/**
	 * Get property from main property file of TFW.
	 *
	 * @param name non null property name
	 * @return property value as string or null if no such property defined
	 */
	public static String getProperty(String name) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getProperty(String) - start"); //$NON-NLS-1$
		}

		String returnString = MAIN_PROPS.getProperty(name);
		if (LOG.isDebugEnabled()) {
			LOG.debug("getProperty(String) - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	/**
	 * Gets the property.
	 *
	 * @param name - name of property non null.
	 * @param defalt - can be null
	 * @return the property
	 */
	public static String getProperty(String name, String defalt) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getProperty(String, String) - start"); //$NON-NLS-1$
		}

		String returnString = MAIN_PROPS.getProperty(name, defalt);
		if (LOG.isDebugEnabled()) {
			LOG.debug("getProperty(String, String) - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	/**
	 * replaces the {conf} entry of the properties with tfwHome data.
	 *
	 * @param in the in
	 * @param replacementString the replacement string
	 * @return the properties
	 */
	private static Properties updateProp(Properties in, String replacementString) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateProp(Properties, String) - start"); //$NON-NLS-1$
		}

		@SuppressWarnings("rawtypes")
		Enumeration e = in.propertyNames();
		Properties repProp = new Properties();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = in.getProperty(key);
			if (value.contains("{conf}")) {
				value = value.replace("{conf}", replacementString);
			}
			repProp.put(key, value);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("updateProp(Properties, String) - end"); //$NON-NLS-1$
		}
		return repProp;
	}

	/*
	 * Random number generator for DB Id, when threads run parallely
	 */
	/**
	 * Random num.
	 *
	 * @return the string
	 */
	public static String randomNum() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("randomNum() - start"); //$NON-NLS-1$
		}

		String rand = new Long(System.nanoTime()).toString();
		String returnString = rand.substring(rand.length() - 7);
		if (LOG.isDebugEnabled()) {
			LOG.debug("randomNum() - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	/**
	 * Gets the root.
	 *
	 * @return the root
	 */
	public static String getRoot() {
		return appDataRoot;
	}

	/**
	 * Gets the api configs root.
	 *
	 * @param project the project
	 * @return the api configs root
	 */
	public static String getApiConfigsRoot(String project) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getApiConfigsRoot(String) - start"); //$NON-NLS-1$
		}

		String returnString = MAIN_PROPS.getProperty("apiConf." + project, appVerifierHomeDir + "/apiPro");
		if (LOG.isDebugEnabled()) {
			LOG.debug("getApiConfigsRoot(String) - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	/**
	 * Gets the tfw home dir.
	 *
	 * @return the tfw home dir
	 */
	public static File getTfwHomeDir() {
		return tfwHomeDir;
	}

	/**
	 * Sets the tfw home dir.
	 *
	 * @param tfwHomeDir the new tfw home dir
	 */
	public static void setTfwHomeDir(File tfwHomeDir) {
		UtlConf.tfwHomeDir = tfwHomeDir;
	}
}
