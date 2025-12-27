package com.sssoft.isatt.agent.test;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sssoft.isatt.agent.ControllerThread;
import com.sssoft.isatt.agent.ControllerThreadImpl;

public class TestControllerThread {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(TestControllerThread.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationContext-Agent.xml", "applicationContext.xml",
				"dataAccessContext-local.xml" });
		ControllerThread controllerThread = (ControllerThread) context.getBean("controllerThread");
		controllerThread.initScheduler(999);
		ControllerThreadImpl.start(controllerThread);
		LOG.info("controllerThread initialized successfully");
	}

}
