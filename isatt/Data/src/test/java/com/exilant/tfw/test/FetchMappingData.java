package com.exilant.tfw.test;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sssoft.isatt.data.pojo.input.TestSuite;
import com.sssoft.isatt.data.service.InputService;

public class FetchMappingData {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(FetchMappingData.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("main(String[]) - start"); //$NON-NLS-1$
		}

		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml", "dataAccessContext-local.xml" });

		InputService inputService = (InputService) context.getBean("inputService");
		List<TestSuite> suites = inputService.getAllTestSuites();
		LOG.info(suites);
	}

}
