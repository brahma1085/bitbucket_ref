package com.sssoft.isatt.utils.util.threads;

import java.util.concurrent.ThreadFactory;

/**
 * The Class PoolOptions.
 */
public class PoolOptions {

	/** The max threads. */
	private int maxThreads = 5;
	
	/** The core threads. */
	private int coreThreads = 2;
	
	/** The factory. */
	private ThreadFactory factory;
	
	/**
	 * Gets the max threads.
	 *
	 * @return the max threads
	 */
	public final int getMaxThreads() {
		return maxThreads;
	}
	
	/**
	 * Sets the max threads.
	 *
	 * @param maxThreads the new max threads
	 */
	public final void setMaxThreads(int maxThreads) {
		this.maxThreads = maxThreads;
	}
	
	/**
	 * Gets the core threads.
	 *
	 * @return the core threads
	 */
	public final int getCoreThreads() {
		return coreThreads;
	}
	
	/**
	 * Sets the core threads.
	 *
	 * @param coreThreads the new core threads
	 */
	public final void setCoreThreads(int coreThreads) {
		this.coreThreads = coreThreads;
	}
	
	/**
	 * Gets the factory.
	 *
	 * @return the factory
	 */
	public final ThreadFactory getFactory() {
		return factory;
	}
	
	/**
	 * Sets the factory.
	 *
	 * @param factory the new factory
	 */
	public final void setFactory(ThreadFactory factory) {
		this.factory = factory;
	}
	
	
}
