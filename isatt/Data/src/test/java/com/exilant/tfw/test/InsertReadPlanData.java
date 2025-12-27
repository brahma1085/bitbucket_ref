package com.exilant.tfw.test;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sssoft.isatt.data.pojo.ExcelImport;
import com.sssoft.isatt.data.service.MainService;

/**
 * The Class InsertReadPlanData.
 */
public class InsertReadPlanData {

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(InsertReadPlanData.class);

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws Exception
	 *             the exception
	 */
	public static void main(String[] args) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("main(String[]) - start"); //$NON-NLS-1$
		}

		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml", "dataAccessContext-local.xml" });
		MainService mainService = (MainService) context.getBean("mainService");
		if (args.length < 1) {
			args = new String[2];
			args[0] = "/Users/manoj.rb/Downloads/TestCase_Raft.xls";
		}
		mainService.insertReadPlanData(new ExcelImport());
		LOG.info("excel data inserted into the database successfully");
	}

}
