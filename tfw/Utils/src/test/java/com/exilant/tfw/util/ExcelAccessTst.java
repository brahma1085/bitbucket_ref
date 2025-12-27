package com.exilant.tfw.util;

import org.apache.log4j.Logger;

import org.junit.Test;

import com.exilant.tfw.bean.UtlConf;

/**
 * The Class ExcelAccessTst.
 */
public class ExcelAccessTst {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(ExcelAccessTst.class);
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		/*before running this make sure */
		com.exilant.tfw.util.ExcelAccess ee = new com.exilant.tfw.util.ExcelAccess();
		ee.openWorkBook(new java.io.File("/src/main/TestUtf.xls"));
		String ss = ee.getCellValue("createOrder", 8, 0);
		LOG.info(ss + " :" + ((int) ss.charAt(0)) + ";");
		
		ExcelAccessTst a = new ExcelAccessTst();
		a.openWorkBook();
	}

	/**
	 * Open work book.
	 */
	@Test
	public void openWorkBook() {
		com.exilant.tfw.util.ExcelAccess ee = new com.exilant.tfw.util.ExcelAccess();
		ee.openWorkBook(new java.io.File(UtlConf.getProperty("xlsPath.AgentRunnerSets")));
		String ss = ee.getSheetNames(0);
		LOG.info("Sheet first :" + ss);
		org.junit.Assert.assertNotNull("loaded ", ss);

	}

	/**
	 * Count shts.
	 */
	@Test
	public void countShts() {
		com.exilant.tfw.util.ExcelAccess ee = new com.exilant.tfw.util.ExcelAccess();
		ee.openWorkBook(new java.io.File(UtlConf.getProperty("xlsPath.AgentRunnerSets")));
		// String ss = ee.getSheetNames(0);
		int i = ee.getNumberOfSheets();
		org.junit.Assert.assertTrue("has sheets :" + i, i > 0);

	}
}
