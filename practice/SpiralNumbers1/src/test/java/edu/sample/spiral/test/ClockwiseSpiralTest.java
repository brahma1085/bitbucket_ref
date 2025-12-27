package edu.sample.spiral.test;

import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.Test;

import edu.sample.spiral.AntiSpiral;
import edu.sample.spiral.ClockwiseSpiral;
import edu.sample.spiral.Spiral;

/**
 * The JUnit Test Class ClockwiseSpiralTest.
 */
public class ClockwiseSpiralTest {

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(ClockwiseSpiralTest.class);

	/**
	 * Empty test.
	 */
	@Test
	public void emptyTest() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("emptyTest() - start");
		}

		assertTrue(ClockwiseSpiral.spiral(9) != null);

		if (LOG.isDebugEnabled()) {
			LOG.debug("emptyTest() - end");
		}
	}

	/**
	 * Empty test1.
	 */
	@Test
	public void emptyTest1() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("emptyTest1() - start");
		}

		assertTrue(AntiSpiral.spiral(13) != null);

		if (LOG.isDebugEnabled()) {
			LOG.debug("emptyTest1() - end");
		}
	}

	/**
	 * Empty test2.
	 */
	@Test
	public void emptyTest2() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("emptyTest2() - start");
		}

		assertTrue(Spiral.spiral(13) != null);

		if (LOG.isDebugEnabled()) {
			LOG.debug("emptyTest2() - end");
		}
	}
}
