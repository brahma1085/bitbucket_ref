package edu.sample.spiral.main;

import java.io.InputStreamReader;
import java.util.Scanner;

import org.apache.log4j.Logger;

import edu.sample.spiral.Spiral;

/**
 * The Executable Main Class ClockwiseSpiralExec.
 */
public class SpiralExec {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(SpiralExec.class);

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(new InputStreamReader(System.in));
		LOG.info("Please enter your input: ");
		int number = 0;
		try {
			number = scanner.nextInt();
		} catch (Exception e) {
			LOG.error("Please enter integer value : " + e.getMessage(), e);
		}
		LOG.info("Result is : \n" + Spiral.toString(Spiral.spiral(number)));

		if (LOG.isDebugEnabled()) {
			LOG.debug("main(String[]) - end");
		}
	}
}
