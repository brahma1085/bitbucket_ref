package com.exilant.tfw.quartz.test;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * The Class QuartzJobTest.
 */
public class QuartzJobTest {
	
	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(QuartzJobTest.class);

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("main(String[]) - start"); //$NON-NLS-1$
		}

		new ClassPathXmlApplicationContext("applicationContext-Quartz.xml");
		LOG.info("execution done");
	}
}
