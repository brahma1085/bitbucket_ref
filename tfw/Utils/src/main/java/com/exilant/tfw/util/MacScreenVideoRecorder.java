package com.exilant.tfw.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.exilant.tfw.bean.UtlConf;

public class MacScreenVideoRecorder {
	private static Logger logger = Logger.getLogger(MacScreenVideoRecorder.class);
	public static final double VIDEO_SCREEN_CAPTURE_OS_SUPPORT_VER = 10.7;
	public static final String STR_SUCCESS_VIDEO_SERVER_RESPONSE = "OK,";
	private ProcessRunner recorderApp;
	private static String strBaseDir;
//	private TestBase testBase;
	

//	public MacScreenVideoRecorder(TestBase testBase) {
//		this.testBase = testBase;
//	}


	public boolean startScreenRecorder() {

		if (!isScreenRecorderEnabledInThisOS()) {
			logger.info("Failed to launch the screen recorder as this is not supported in the current OS:" + AutomationUtilities.getOSVersion());
			return false;
		}

		String strScreenRecorderHandler = getScreenRecorderHandler();
		if (StringUtils.isEmpty(strScreenRecorderHandler)) {
			logger.info("Failed to get the Screen Video Recorder handler");
			return false;
		}

		String strScreenRecorderApp = getScreenRecorderApplication();
		if (StringUtils.isEmpty(strScreenRecorderApp)) {
			logger.info("Failed to get the Screen Video Recorder Application");
			return false;
		}

//		if (StringUtils.isEmpty(testBase.getProperty("MAX_VIDEO_LENGTH_PER_TESTCASE_IN_SECONDS"))) {
//			System.out.println("Failed to get max. video length from configuration.");
//			return false;
//		}
//
//		if (StringUtils.isEmpty(testBase.getProperty("VIDEO_CAPTURE_SERVER_PORT"))) {
//			System.out.println("Failed to video recorder server port from configuration.");
//			return false;
//		}
//		int MAX_VIDEO_LENGTH_PER_TESTCASE_IN_SECONDS = ;
//		int VIDEO_CAPTURE_SERVER_PORT = 7000;
		List<String> cmds = new ArrayList<String>();
		cmds.add("java");
		cmds.add("-jar");
		cmds.add(strScreenRecorderHandler);
		cmds.add("-b");
		cmds.add(".");
		cmds.add("-r");
		cmds.add(strScreenRecorderApp);
		cmds.add("-m");
		cmds.add(UtlConf.getProperty("MAX_VIDEO_LENGTH_PER_TESTCASE_IN_SECONDS"));
		cmds.add("-p");
		
		cmds.add(UtlConf.getProperty("VIDEO_CAPTURE_SERVER_PORT"));

		recorderApp = new ProcessRunner(cmds);
		ProcessOutputListener listner = new ProcessOutputListener() {

			public void outStream(String aLog) {
				// TODO Auto-generated method stub
				logger.info(aLog);
			}

			public void errorStream(String aErr) {
				// TODO Auto-generated method stub
				logger.error(aErr);
			}
		};
		recorderApp.registerListener(listner);

		int result = recorderApp.runProcessAndDontWait();

		if (result == 0) {
			logger.info("Success in launching the video screen recorder");
			return true;
		}

		logger.error("Failure in launching the video screen recorder application.");
		return false;
	}

	public boolean startTestcaseRecording(String aTestcaseName) {

		if (StringUtils.isEmpty(aTestcaseName)) {
			aTestcaseName = "ScreenVideoRecording_";
		}
		if (!StringUtils.endsWith(aTestcaseName, "_")) {
			aTestcaseName += "_";
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		aTestcaseName += dateFormat.format(Calendar.getInstance().getTime());
		aTestcaseName += ".mov";

		String strVideoFile = FilenameUtils.normalize(UtlConf.getProperty("VIDEO_FOLDER")+aTestcaseName);
		logger.info("Storing the video to file:" + strVideoFile);

		return sendStartRecordCommand(strVideoFile);
	}

	private boolean sendStartRecordCommand(String aFile) {
		boolean status = false;

		try {

			String url = "http://localhost:" + UtlConf.getProperty("VIDEO_CAPTURE_SERVER_PORT") + "/?cmd=startrecord&videofile=" + aFile;
			url += "&videoquality=" +UtlConf.getProperty("VIDEO_CAPTURE_QUALITY");
			logger.info("Sending screen video record request to:" + url);

			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet method = new HttpGet(url);

			HttpResponse response = client.execute(method);
			String strServerResponse = EntityUtils.toString(response.getEntity());

			logger.info("Screen Video record start response found as:" + strServerResponse);
			if (StringUtils.startsWith(strServerResponse, STR_SUCCESS_VIDEO_SERVER_RESPONSE)) {
				status = true;
				logger.info("Successfully started the screen video recording");
			} else {
				logger.error("Failed to start the screen video recording...");
			}

		} catch (Exception e) {
			logger.error("Exception occurred while sending the start screen video record command. Error:" + e.getMessage());
		}

		return status;
	}

	public boolean stopTestcaseRecording() {
		boolean status = false;

		try {
			Thread.sleep(2000);
			String url = "http://localhost:" +UtlConf.getProperty("VIDEO_CAPTURE_SERVER_PORT") + "/?cmd=stoprecord";
			logger.info("Sending stop screen video record request to:" + url);

			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet method = new HttpGet(url);
			HttpResponse response = client.execute(method);
			String strServerResponse = EntityUtils.toString(response.getEntity());

			logger.info("Screen Video record stop response found as:" + strServerResponse);
			if (StringUtils.startsWith(strServerResponse, STR_SUCCESS_VIDEO_SERVER_RESPONSE)) {
				status = true;
				logger.info("Successfully stopped the screen video recording");
			} else {
				logger.error("Failed to stop the screen video recording...");
			}

		} catch (Exception e) {
			logger.error("Exception occurred while sending the stop screen video record command. Error:" + e.getMessage());
		}

		return status;
	}

	public boolean stopScreenRecorder() {
		if (recorderApp == null) {
			logger.error("Recorder application is found to be null");
			return false;
		}

		if (recorderApp.killProcess() != 0) {
			logger.error("Failed to close the recorder application");
			return false;
		}
		logger.info("Successfully closed the Recorder application");
		return true;
	}

	private String getScreenRecorderHandler() {
		String handlerPath = UtlConf.getProperty("VIDEO_CAPTURE_HANDLER");
		File f = new File(handlerPath);

		if (f.exists()) {
			return f.getAbsolutePath();
		}
		strBaseDir = System.getenv("TEST_HOME");
//		dataShare = new StaticDataSharingContext(this);

		strBaseDir = FilenameUtils.normalize(strBaseDir);
		handlerPath = strBaseDir + File.separator + "conf" + File.separator + "framework" + File.separator + handlerPath;
		f = new File(handlerPath);
		if (f.exists()) {
			return f.getAbsolutePath();
		}
		return null;
	}

	private String getScreenRecorderApplication() {
		String appPath = UtlConf.getProperty("VIDEO_CAPTURE_APP_PATH");
		File f = new File(appPath);

		if (f.exists()) {
			return f.getAbsolutePath();
		}
		appPath = strBaseDir + File.separator + "conf" + File.separator + "framework" + File.separator + appPath;
		f = new File(appPath);
		if (f.exists()) {
			return f.getAbsolutePath();
		}
		return null;
	}

	private boolean isScreenRecorderEnabledInThisOS() {
		try {
			String strOSVersion = System.getProperty("os.version");
			// strOSVersion = "10.7.4";
			logger.info("Current OS Version is:" + strOSVersion);
			int index = StringUtils.indexOf(strOSVersion, '.');
			if (index != -1) {
				index = StringUtils.indexOf(strOSVersion, '.', index + 1);
				strOSVersion = StringUtils.substring(strOSVersion, 0, index);
				if (StringUtils.endsWith(strOSVersion, ".")) {
					strOSVersion = StringUtils.substring(strOSVersion, 0, strOSVersion.length() - 1);
				}
			}

			Double f = Double.parseDouble(strOSVersion);
			logger.info("After update, the OS Version is:" + f);

			if (f >= VIDEO_SCREEN_CAPTURE_OS_SUPPORT_VER) {
				logger.info("Video Screen Capture is supported in this OS version");
				return true;
			}
			logger.info("Video Capture Screen is supported in OS version greater than:" + VIDEO_SCREEN_CAPTURE_OS_SUPPORT_VER);

		} catch (Exception e) {
			logger.info("Exception while identifying the OS version for Video Screen Capture Support. Exception:" + e);
		}
		return false;

	}
}
