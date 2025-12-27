package edu.test;

import org.apache.log4j.Logger;

public class LogTest {
	private static org.apache.log4j.Logger log = Logger
			.getLogger(LogTest.class);

	public static void main(String[] args) {
		log.debug("Debug");
		log.info("Info");
		log.warn("Warn");
		log.error("Error");
		log.fatal("Fatal");
	}
}