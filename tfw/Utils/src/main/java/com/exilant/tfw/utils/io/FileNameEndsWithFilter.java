package com.exilant.tfw.utils.io;

import org.apache.log4j.Logger;

import java.io.File;

/**
 * The Class FileNameEndsWithFilter.
 */
public class FileNameEndsWithFilter implements java.io.FileFilter {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(FileNameEndsWithFilter.class);

	/** The filtr. */
	private String filtr;

	/** The folder also. */
	private boolean folderAlso;

	/**
	 * Gets the sub string.
	 * 
	 * @param sample
	 *            the sample
	 * @param myDir
	 *            the my dir
	 * @return the sub string
	 */
	public String getSubString(String sample, String myDir) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSubString(String, String) - start"); //$NON-NLS-1$
		}

		String path = sample.substring(myDir.length()); // "r/2013aug05run1_DFJE/index.html";

		String name = path.replace("/index.html", "");

		name = name.replace("r/", ""); // name = "2013_aug_05run1_DFJE";

		if (LOG.isDebugEnabled()) {
			LOG.debug("getSubString(String, String) - end"); //$NON-NLS-1$
		}
		return name;
	}

	/**
	 * Instantiates a new file name ends with filter.
	 * 
	 * @param filte
	 *            the filte
	 * @param folderAlso
	 *            the folder also
	 */
	public FileNameEndsWithFilter(String filte, boolean folderAlso) {
		this.filtr = filte;
		this.folderAlso = folderAlso;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.FileFilter#accept(java.io.File)
	 */
	public boolean accept(File pathname) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("accept(File) - start"); //$NON-NLS-1$
		}

		if (pathname.isDirectory() && folderAlso) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("accept(File) - end"); //$NON-NLS-1$
			}
			return true;
		}
		if (pathname.getName().endsWith(filtr)) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("accept(File) - end"); //$NON-NLS-1$
			}
			return true;
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("accept(File) - end"); //$NON-NLS-1$
		}
		return false;
	}

}
