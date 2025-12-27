package com.sssoft.isatt.utils.util;

import org.apache.log4j.Logger;

public class VideoRecording {
	private static Logger logger = Logger.getLogger(VideoRecording.class);
	private MacScreenVideoRecorder videoScreenRecorder;

	
//public static void main(String[] args) throws InterruptedException {
//	
//	
//	VideoRecording recording = new VideoRecording();
//	recording.startVideoCaptureServer();
//	recording.startVideoCapture();
//	WebDriver webDriver = new FirefoxDriver();
//	webDriver.get("http://only-testing-blog.blogspot.in/2013/11/new-test.html");
//	webDriver.findElement(By.xpath(".//*[@id='post-body-2641311481947341581']/div[1]/form[1]/input[1]")).sendKeys("input");
//	Thread.sleep(5000);
//	recording.stopVideoCapture();
//	recording.stopVideoCaptureServer();
//	// stop the video capture server
//	
//
//}

public boolean startVideoCaptureServer() {

	videoScreenRecorder = new MacScreenVideoRecorder();
	return videoScreenRecorder.startScreenRecorder();
}

public boolean startVideoCapture(String scenarioName) {
	boolean status = false;

	try {

		// if (oldScreenVideoRecorder == null) {
		// error("Screen Video Recorder is found as null. Failed to start the video capture");
		// return status;
		// }
		// oldScreenVideoRecorder.setResultsFolderFile(new
		// File(strRuntimeVideoFolder), strCurrentTestScenarioUnderTest);
		// oldScreenVideoRecorder.start();

		if (videoScreenRecorder == null) {
			logger.error("Screen Video Recorder is found as null. Failed to start the video capture");
			return status;
		}

		status = videoScreenRecorder.startTestcaseRecording(scenarioName);
		if (status) {
			logger.info("Video capturing is started successfully.");
		} else {
			logger.error("Failed to start the video capture for testcase:  strCurrentTestScenarioUnderTest");
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return status;
}

public boolean stopVideoCapture() {
	boolean status = false;

	// if (oldScreenVideoRecorder == null) {
	// return status;
	// }
	//
	// if (!oldScreenVideoRecorder.isScreenCaptureInProgress()) {
	// log("Screen capture is not in-progress. Ignoring it");
	// return status;
	// }
	//
	// try {
	// oldScreenVideoRecorder.stop();
	// status = true;
	// } catch (Exception e) {
	// error("Exception occurred while stopping the video capture/moving the files runtime folder",
	// e);
	// }
	try {
		if (videoScreenRecorder == null) {
			logger.error("Screen Video Recorder is found as null. Failed to stop the video capture");
			return status;
		}

		status = videoScreenRecorder.stopTestcaseRecording();
		if (status) {
			logger.info("Video capturing is stopped successfully.");
		} else {
			logger.error("Failed to stop the video capture for testcase: strCurrentTestScenarioUnderTest");
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return status;
}

public boolean stopVideoCaptureServer() {
	if (videoScreenRecorder != null) {
		return videoScreenRecorder.stopScreenRecorder();
	}
	return false;
}
}
