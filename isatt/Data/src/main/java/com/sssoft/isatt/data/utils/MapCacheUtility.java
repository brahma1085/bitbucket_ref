package com.sssoft.isatt.data.utils;

import org.apache.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;

import com.sssoft.isatt.data.exception.CacheException;

/**
 * The Class MapCacheUtility.
 */
public class MapCacheUtility {
	
	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(MapCacheUtility.class);

	/** The cache map. */
	private ConcurrentHashMap<String, CacheData> cacheMap;

	/**
	 * Instantiates a new map cache utility.
	 */
	public MapCacheUtility() {
		cacheMap = new ConcurrentHashMap<String, CacheData>();
	}

	/**
	 * Instantiates a new map cache utility.
	 *
	 * @param initialCapacity the initial capacity
	 * @param loadFactor the load factor
	 */
	public MapCacheUtility(int initialCapacity, float loadFactor) {
		cacheMap = new ConcurrentHashMap<String, CacheData>(initialCapacity, loadFactor);
	}

	/**
	 * Adds the.
	 *
	 * @param key the key
	 * @param value the value
	 * @throws CacheException the cache exception
	 */
	public synchronized void add(String key, Object value) throws CacheException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("add(String, Object) - start"); //$NON-NLS-1$
		}

		long timeToLive = System.currentTimeMillis() + (300 * 1000);
		CacheData cacheData = new CacheData(value, timeToLive);
		cacheMap.put(key, cacheData);

		if (LOG.isDebugEnabled()) {
			LOG.debug("add(String, Object) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Adds the.
	 *
	 * @param key the key
	 * @param value the value
	 * @param timeToLiveInSeconds the time to live in seconds
	 * @throws Exception the exception
	 */
	public synchronized void add(String key, Object value, long timeToLiveInSeconds) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("add(String, Object, long) - start"); //$NON-NLS-1$
		}

		long timeToLive = System.currentTimeMillis() + (timeToLiveInSeconds * 1000);
		CacheData cacheData = new CacheData(value, timeToLive);
		cacheMap.put(key, cacheData);

		if (LOG.isDebugEnabled()) {
			LOG.debug("add(String, Object, long) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Gets the.
	 *
	 * @param key the key
	 * @return the object
	 * @throws CacheException the cache exception
	 */
	public Object get(String key) throws CacheException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("get(String) - start"); //$NON-NLS-1$
		}

		if (cacheMap.isEmpty()) {
			throw new CacheException("No data available in cache at this time");
		} else {
			CacheData cacheData = cacheMap.get(key);
			if (cacheData != null && !cacheData.isExpired()) {
				Object returnObject = cacheData.getObject();
				if (LOG.isDebugEnabled()) {
					LOG.debug("get(String) - end"); //$NON-NLS-1$
				}
				return returnObject;
			} else {
				throw new CacheException("Object does not exist/expired");
			}
		}
	}

	/**
	 * Delete.
	 *
	 * @param key the key
	 * @throws CacheException the cache exception
	 */
	public synchronized void delete(String key) throws CacheException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("delete(String) - start"); //$NON-NLS-1$
		}

		if (cacheMap.isEmpty()) {
			throw new CacheException("No data available in cache at this time");
		} else {
			cacheMap.remove(key);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("delete(String) - end"); //$NON-NLS-1$
		}
	}
}
