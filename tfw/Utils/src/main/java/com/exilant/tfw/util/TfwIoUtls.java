package com.exilant.tfw.util;

import java.io.File;

import org.apache.log4j.Logger;


/**
 * The Class TfwIoUtls.
 */
public class TfwIoUtls {
	
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(TfwIoUtls.class);
	
	/**
	 * Prints the current folder name.
	 */
	public static void printCurrentFolderName() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("printCurrentFolderName() - start"); //$NON-NLS-1$
		}

		try {
			java.io.File f = new File("./");
			f = f.getCanonicalFile();
		} catch (Exception e) {
			LOG.error("printCurrentFolderName()", e); //$NON-NLS-1$

		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("printCurrentFolderName() - end"); //$NON-NLS-1$
		}
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("main(String[]) - start"); //$NON-NLS-1$
		}

		 printCurrentFolderName();

		if (LOG.isDebugEnabled()) {
			LOG.debug("main(String[]) - end"); //$NON-NLS-1$
		}
	}

}
