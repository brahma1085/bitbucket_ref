package com.exilant.tfw.bean;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * This class is used to instantiate the data source using the properties file.
 * Pooled connection creation, closing implementation is provided with this
 * class.
 */
public class UtlProps {

	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(UtlProps.class);

	/** The main props. */
	private Properties mainProps;

	/**
	 * Instantiates a new utl props.
	 */
	public UtlProps() {
		mainProps = new Properties();
	}

	/**
	 * Instantiates a new utl props.
	 * 
	 * @param f
	 *            the f
	 */
	public UtlProps(File f) {
		mainProps = new Properties();
		initialize(f);
	}

	/**
	 * Gets the property.
	 * 
	 * @param name
	 *            the name
	 * @return the property
	 */
	public String getProperty(String name) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getProperty(String) - start"); //$NON-NLS-1$
		}

		String returnString = mainProps.getProperty(name);
		if (LOG.isDebugEnabled()) {
			LOG.debug("getProperty(String) - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	/**
	 * Gets the property.
	 * 
	 * @param name
	 *            the name
	 * @param defalt
	 *            the defalt
	 * @return the property
	 */
	public String getProperty(String name, String defalt) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getProperty(String, String) - start"); //$NON-NLS-1$
		}

		String returnString = mainProps.getProperty(name, defalt);
		if (LOG.isDebugEnabled()) {
			LOG.debug("getProperty(String, String) - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	/**
	 * Sets the main props.
	 * 
	 * @param mainProps
	 *            the new main props
	 */
	public void setMainProps(Properties mainProps) {
		this.mainProps = mainProps;
	}

	/**
	 * Method to initialize the properties and the data source by using the
	 * database properties configured in user defined file.
	 * 
	 * @param filePath
	 *            the file path
	 */
	public final void initialize(File filePath) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("initialize(File) - start"); //$NON-NLS-1$
		}

		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(filePath));
			Properties p = new Properties();
			p.load(is);
			mainProps.clear();// for re init, only discard current if new set
								// was loaded successfully.
			mainProps.putAll(p);
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
			LOG.debug("initialize(File) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Gets the props.
	 * 
	 * @return the props
	 */
	public Properties getProps() {
		return mainProps;
	}
}
