package com.sssoft.isatt.utils.util.threads;

import org.apache.log4j.Logger;

import com.sssoft.isatt.utils.bean.UtlConf;

/**
 * The Class PoolWatcher.
 */
public class PoolWatcher implements Runnable {

	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(PoolWatcher.class);

	static {
		reinitExtrasPool();
	}

	/**
	 * Reinit extras pool.
	 */
	private static void reinitExtrasPool() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("reinitExtrasPool() - start"); //$NON-NLS-1$
		}

		final String poolName = "background-extras";
		LOG.trace("pool :");
		String smax = UtlConf.getProperty("utl.background-extras.poolMax", "1");
		int max = 1;
		try {
			max = Integer.parseInt(smax);
		} catch (NumberFormatException e) {
			LOG.warn("reinitPool smax parse " + e + " for pool :" + poolName + ", mx :" + max);
		}
		PoolOptions options = new PoolOptions();
		options.setMaxThreads(max);
		options.setCoreThreads((int) ((1 + max) / 2));
		TfwPools.initPool(poolName, options);

		if (LOG.isDebugEnabled()) {
			LOG.debug("reinitExtrasPool() - end"); //$NON-NLS-1$
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

	}
}
