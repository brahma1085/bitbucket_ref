package com.sssoft.isatt.utils.util.threads;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
/**
 * The Class TfwPools.
 */
public class TfwPools {
	
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(TfwPools.class);
	
	/** The Constant DEFAULT_POOL. */
	public static final String DEFAULT_POOL = "_d";
	
	/** The pools. */
	private static Map<String, ThreadPoolExecutor> pools = new HashMap<String, ThreadPoolExecutor>(1);
	//private static java.util.concurrent.ThreadPoolExecutor threadExe;
	static {
		try {
			PoolOptions opt = new PoolOptions();
			TfwThreadFactory threadFac = new TfwThreadFactory(DEFAULT_POOL);
			opt.setFactory(threadFac);
			reinitDefault(opt);
		} catch (Exception e) {
			LOG.warn("init a:" + e, e);
		}
	}
	
	/**
	 * Reinit default.
	 *
	 * @param opt the opt
	 */
	private static void reinitDefault(PoolOptions opt) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("reinitDefault(PoolOptions) - start"); //$NON-NLS-1$
		}
		
		initPool(DEFAULT_POOL, opt);

		if (LOG.isDebugEnabled()) {
			LOG.debug("reinitDefault(PoolOptions) - end"); //$NON-NLS-1$
		}
	}
	
	/**
	 * Inits the pool.
	 *
	 * @param poolName the pool name
	 * @param options the options
	 */
	public static void initPool(String poolName, PoolOptions options) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("initPool(String, PoolOptions) - start"); //$NON-NLS-1$
		}

		ThreadPoolExecutor pool = pools.get(poolName);
		if(pool != null) {
			LOG.info("Reinit " + poolName + ", " + options);
		}
		ThreadFactory threadFactory = new TfwThreadFactory(poolName);
		long keepAliveTime = 5;
		TimeUnit unit = TimeUnit.MINUTES;
		BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
		RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
		pool = new ThreadPoolExecutor(options.getCoreThreads(), options.getMaxThreads(), keepAliveTime, unit, workQueue, threadFactory, handler);
		pools.put(poolName, pool);

		if (LOG.isDebugEnabled()) {
			LOG.debug("initPool(String, PoolOptions) - end"); //$NON-NLS-1$
		}
	}
	
	/**
	 * Offer.
	 *
	 * @param poolName the pool name
	 * @param job the job
	 */
	public static void offer(String poolName, Runnable job) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("offer(String, Runnable) - start"); //$NON-NLS-1$
		}

		ThreadPoolExecutor pool = pools.get(poolName);
		if(pool == null) {
			LOG.warn("Pool " + poolName + ", not found using default pool ");
			pool = pools.get(DEFAULT_POOL);
		}
		pool.execute(job);

		if (LOG.isDebugEnabled()) {
			LOG.debug("offer(String, Runnable) - end"); //$NON-NLS-1$
		}
	}
	
	/**
	 * Gets the pool.
	 *
	 * @param poolName the pool name
	 * @return the pool
	 */
	public static ThreadPoolExecutor getPool(String poolName) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getPool(String) - start"); //$NON-NLS-1$
		}

		ThreadPoolExecutor pool = pools.get(poolName);
		if(pool == null) {
			LOG.warn("Pool " + poolName + ", not found giving default pool ");
			pool = pools.get(DEFAULT_POOL);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getPool(String) - end"); //$NON-NLS-1$
		}
		return pool;
	}
	
	/**
	 * Gets the pool names.
	 *
	 * @return the pool names
	 */
	public static List <String> getPoolNames() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getPoolNames() - start"); //$NON-NLS-1$
		}

		ArrayList <String> a = new ArrayList<String>();
		a.addAll(pools.keySet());

		if (LOG.isDebugEnabled()) {
			LOG.debug("getPoolNames() - end"); //$NON-NLS-1$
		}
		return a;
	}
}
