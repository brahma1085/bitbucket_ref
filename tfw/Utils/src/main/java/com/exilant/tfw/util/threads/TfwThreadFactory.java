package com.exilant.tfw.util.threads;

import org.apache.log4j.Logger;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A factory for creating TfwThread objects.
 */
public class TfwThreadFactory implements ThreadFactory {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(TfwThreadFactory.class);
	
	/** The Constant poolNumber. */
	private static final AtomicInteger poolNumber = new AtomicInteger(1);
	
	/** The group. */
	private final ThreadGroup group;
	
	/** The thread number. */
	private final AtomicInteger threadNumber = new AtomicInteger(1);
	
	/** The name prefix. */
	private final String namePrefix;

	/**
	 * Instantiates a new tfw thread factory.
	 */
	public TfwThreadFactory() {
		SecurityManager s = System.getSecurityManager();
		group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
		namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";
	}

	/**
	 * Instantiates a new tfw thread factory.
	 *
	 * @param poolName the pool name
	 */
	public TfwThreadFactory(String poolName) {
		SecurityManager s = System.getSecurityManager();
		group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
		namePrefix = "pool-" + poolName + "=" + poolNumber.getAndIncrement() + "-thread-";
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.ThreadFactory#newThread(java.lang.Runnable)
	 */
	public Thread newThread(Runnable r) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("newThread(Runnable) - start"); //$NON-NLS-1$
		}

		Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
		if (t.isDaemon()) {
			t.setDaemon(false);
		}
		if (t.getPriority() != Thread.NORM_PRIORITY) {
			t.setPriority(Thread.NORM_PRIORITY);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("newThread(Runnable) - end"); //$NON-NLS-1$
		}
		return t;
	}

	/**
	 * Gets the group.
	 *
	 * @return the group
	 */
	public ThreadGroup getGroup() {
		return group;
	}

	/**
	 * Gets the name prefix.
	 *
	 * @return the name prefix
	 */
	public String getNamePrefix() {
		return namePrefix;
	}

}
