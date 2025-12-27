package com.sssoft.isatt.data.utils;

import org.apache.log4j.Logger;

import java.util.GregorianCalendar;

/**
 * The Class CacheData.
 */
public class CacheData {
	
	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(CacheData.class);

	/** The object. */
	private Object object;
	
	/** The expiry time. */
	private long expiryTime;

	/**
	 * Instantiates a new cache data.
	 *
	 * @param object the object
	 * @param expiryTime the expiry time
	 */
	public CacheData(Object object, long expiryTime) {
		this.object = object;
		this.expiryTime = new GregorianCalendar().getTimeInMillis() + expiryTime;
	}

	/**
	 * Checks if is expired.
	 *
	 * @return true, if is expired
	 */
	public boolean isExpired() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("isExpired() - start"); //$NON-NLS-1$
		}

		boolean returnboolean = this.expiryTime <= (new GregorianCalendar().getTimeInMillis());
		if (LOG.isDebugEnabled()) {
			LOG.debug("isExpired() - end"); //$NON-NLS-1$
		}
		return returnboolean;
	}

	/**
	 * Gets the object.
	 *
	 * @return the object
	 */
	public Object getObject() {
		return this.object;
	}
}
