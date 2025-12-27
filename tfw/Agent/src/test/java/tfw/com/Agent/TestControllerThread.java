package tfw.com.Agent;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.exilant.tfw.agent.ControllerThread;
import com.exilant.tfw.agent.ControllerThreadImpl;

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
