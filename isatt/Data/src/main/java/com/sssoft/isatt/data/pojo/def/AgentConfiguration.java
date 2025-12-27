package com.sssoft.isatt.data.pojo.def;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Configuration for the app. Where input files (xls), helper to link to
 * resources (images, css etc) and where output goes.
 */
@SuppressWarnings("unused")
public class AgentConfiguration {
	private static Logger LOG = Logger.getLogger(AgentConfiguration.class);
	public static final int DEFAULT_SERVER_TIMEOUT = 25000;
	public static final int DEFAULT_PAGE_LOAD_TIME = 3500;
	public static final String JS_FILE = "/JS/amcharts.js";
	public static final String CSS_FILE = "/CSS/styles.css";
	private boolean showUi = true;
	private String staticFiles = null;
	private String reportStaticFiles;
	private File[] files = new File[10];
	private Map<String, String> objectHashMap = new HashMap<String, String>();
	private File htmlOutDir;
	private File defaultDir;
	private Map<String, String> configInputMap = new HashMap<String, String>();
	private int waitForPageTime;
	private String dateFormat;
	private String reportDateStr;
	private String inRptPathToStaticRes;
	private Date testCaseStartTime;
	private String snapShotRelativeDir;

	private String replaceMulti(String in, String... vargs) {
		for (int i = 0; i < vargs.length; i += 2) {
			in = in.replace(vargs[i], vargs[i + 1]);
		}
		return in;
	}

	public File filePathCheck(File filePath, File def, String sub1) {
		if (filePath.exists() && filePath.canExecute()) {
			return filePath;
		}
		File f = new File(def, sub1);
		return f;
	}

	public int waitForPageLoadTime() {
		return waitForPageTime;
	}

	public String getReportsDateFormat() {
		return dateFormat;
	}

	public String reportsDataDate() {
		return reportDateStr;
	}

	public String dateFormatCheck(String dateFormat) {
		try {
			Date date = new Date();
			SimpleDateFormat simpleDate1 = new SimpleDateFormat("hh:mm:ss");
			String simpleDateTime = simpleDate1.format(date);
			SimpleDateFormat simpleDate = new SimpleDateFormat(dateFormat);
			String simpleDatePattern = simpleDate.format(date);
			simpleDate.applyPattern(simpleDatePattern);
			dateFormat = simpleDatePattern + simpleDateTime;
		} catch (IllegalArgumentException e) {
			LOG.error("" + e.getMessage(), e);
		}
		return dateFormat;
	}
}
