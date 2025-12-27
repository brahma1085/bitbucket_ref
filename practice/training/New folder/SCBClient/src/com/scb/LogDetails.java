package com.scb;

import java.io.FileOutputStream;

import org.apache.log4j.HTMLLayout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.WriterAppender;

public class LogDetails {
	private WriterAppender appender = null;

	private static LogDetails instance;

	public static LogDetails getInstance() {
		if (instance == null)
			instance = new LogDetails();

		return instance;
	}

	private LogDetails() {
		System.out.println("Hiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
		HTMLLayout layout = new HTMLLayout();

		try {
			FileOutputStream output = new FileOutputStream(
					"C:\\LogDetails.html");
			appender = new WriterAppender(layout, output);
		} catch (Exception e) {

		}

	}

	public Logger getLoggerObject(String name) {
		final Logger logger = Logger.getLogger(name + ".class");
		logger.addAppender(appender);
		logger.setLevel((Level) Level.DEBUG);
		logger.debug("here is some debug");
		logger.error("here is some error");

		return logger;
	}

}