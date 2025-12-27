package com.exilant.tfw.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class ProcessRunner {
	private static Logger logger = Logger.getLogger(ProcessRunner.class);
	private final List<String> outputContent = new CopyOnWriteArrayList<String>();
	private final List<String> errorContent = new CopyOnWriteArrayList<String>();
	private final List<ProcessOutputListener> processListeners = new CopyOnWriteArrayList<ProcessOutputListener>();

	private volatile Process currentProcess;
	private List<Thread> outputErrorThreads = new ArrayList<Thread>();
	private List<String> cmds;
	private String strCurrentWorkingDir;

	public ProcessRunner(List<String> cmds) {
		this.cmds = cmds;
	}

	public ProcessRunner(List<String> cmds, String aWorkingDir) {
		this.cmds = cmds;
		strCurrentWorkingDir = aWorkingDir;
	}

	public void registerListener(ProcessOutputListener l) {
		processListeners.add(l);
	}

	public int runProcessAndDontWait() {
		run();
		if (currentProcess != null) {
			return 0; // success;
		}
		return -1; // Fail to launch
	}
	
	public int waitForProcessToComplete() {
		int exitCode = -1;
		if (currentProcess == null) {
			return exitCode; // fail
		}
		try {
			exitCode = currentProcess.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
			return exitCode;
		}

		for (Thread t : outputErrorThreads) {
			try {
				t.join();
			} catch (Exception ex) {
			}
		}

		return exitCode;
	}

	public int killProcess() {
		if(currentProcess == null) {
			return -1;
		}
		
		try {
			currentProcess.destroy();
			
			for(Thread t : outputErrorThreads) {
				t.interrupt();
			}
			
		} catch(Exception e) {
			
		}
		return 0;	//success
	}

	public int runProcessAndWaitToComplete() {
		run();
		return waitForProcessToComplete();
	}

	public List<String> getOutput() {
		return outputContent;
	}
	
	public List<String> getError() {
		return errorContent;
	}
	
	private void run() {
		try {
			logger.info("Launching the app:" + AutomationUtilities.convertStringArrayToString(cmds));
			ProcessBuilder pBuilder = new ProcessBuilder(cmds);
			if (StringUtils.isNotBlank(strCurrentWorkingDir)) {
				pBuilder.directory(new File(strCurrentWorkingDir));
			}
			currentProcess = pBuilder.start();
			Thread.sleep(2000);
			final InputStream output = currentProcess.getInputStream();
			final InputStream error = currentProcess.getErrorStream();

			outputErrorThreads.add(getDataMonitorInThread(output,
					outputContent, true));
			outputErrorThreads.add(getDataMonitorInThread(error, errorContent,
					false));

		} catch (Exception e) {
			logger.error("Exception occurred while launching the process "+ e);
		}
	}

	private Thread getDataMonitorInThread(final InputStream in,
			final List<String> out, final boolean outputErrorFlag) {

		Thread currentThread = new Thread(new Runnable() {
			public void run() {
				BufferedReader reader = null;
				try {
					reader = new BufferedReader(new InputStreamReader(in,
							"UTF-8"));
				} catch (UnsupportedEncodingException ignore) {
				}
				String line;
				try {
					while ((line = reader.readLine()) != null) {
						appendDataToStore(line, outputErrorFlag);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		currentThread.start();
		return currentThread;
	}

	private void appendDataToStore(String line, boolean outputErrorFlag) {
		if (outputErrorFlag) {
			outputContent.add(line);
		} else {
			errorContent.add(line);
		}

		for (ProcessOutputListener l : processListeners) {
			if (outputErrorFlag) {
				l.outStream(line);
			} else {
				l.errorStream(line);
			}
		}
	}
}
