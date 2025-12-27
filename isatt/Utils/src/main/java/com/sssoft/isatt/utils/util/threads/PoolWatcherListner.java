package com.sssoft.isatt.utils.util.threads;

/**
 * The Interface PoolWatcherListner.
 */
public interface PoolWatcherListner {

	/**
	 * Called when x scans shows that pool is at zero
	 * where x is configured when adding the watcher.
	 */
	void poolAtZero();
	
	/**
	 * Pool stoped within timeout.
	 */
	void poolStopedWithinTimeout();
	
	/**
	 * Pool force stopped.
	 */
	void poolForceStopped();
}
