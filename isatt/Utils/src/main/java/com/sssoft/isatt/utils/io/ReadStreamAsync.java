package com.sssoft.isatt.utils.io;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.sssoft.isatt.utils.util.threads.TfwPools;

/**
 * The Class ReadStreamAsync.
 */
public class ReadStreamAsync implements Runnable {
	
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(ReadStreamAsync.class);

	/** The is. */
	private InputStream is;
	
	/** The log level. */
	private int logLevel;
	
	/** The save str. */
	private boolean saveStr;
	
	/** The resp. */
	private String resp;
	
	/** The done. */
	private boolean done;
	
	/** The encoding. */
	private String encoding = null;

	/**
	 * Instantiates a new read stream async.
	 *
	 * @param is the is
	 * @param pool the pool
	 * @param ownThread the own thread
	 * @param logLevel the log level
	 * @param saveStr the save str
	 * @param encoding the encoding
	 */
	public ReadStreamAsync(InputStream is, String pool, boolean ownThread, int logLevel, boolean saveStr, String encoding) {
		if (is instanceof BufferedInputStream) {
			this.is = is;
		} else {
			this.is = new BufferedInputStream(is);
		}
		this.saveStr = saveStr;
		this.logLevel = logLevel;
		this.encoding = encoding;
		TfwPools.offer(pool, this);
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("run() - start"); //$NON-NLS-1$
		}

		try {
			StringWriter writer = new StringWriter();
			IOUtils.copy(is, writer, encoding);
			String theString = writer.toString();
			if (logLevel == 1) {
				LOG.info("Response :" + theString);
			} else {
				LOG.debug("Response :" + theString);
			}
			if (saveStr) {
				resp = theString;
			}
		} catch (Exception e) {
			LOG.warn("run err " + e, e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("run() - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Gets the response.
	 *
	 * @return the response
	 */
	public String getResponse() {
		return resp;
	}

	/**
	 * Checks if is done.
	 *
	 * @return true, if is done
	 */
	public boolean isDone() {
		return done;
	}

}
