package edu.sample.spiral;

import org.apache.log4j.Logger;

/**
 * The Class AntiSpiral.
 */
public class AntiSpiral {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(AntiSpiral.class);

	/** The max. */
	private static int max;

	/**
	 * Gets the max.
	 * 
	 * @return the max
	 */
	public static int getMax() {
		return max;
	}

	/**
	 * Sets the max.
	 * 
	 * @param max
	 *            the new max
	 */
	public static void setMax(int max) {
		AntiSpiral.max = max;
	}

	/**
	 * Generates the AntiSpiral as two dimentional array.
	 * 
	 * @param max
	 *            the max
	 * @return the int[][]
	 */
	public static int[][] spiral(int max) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("spiral(int=" + max + ") - start");
		}

		AntiSpiral.setMax(max);
		int use = (int) Math.ceil(Math.sqrt(max)); // taking the ceil value
		if (use % 2 == 0) {
			use = use + 1; // getting the odd number for two dimentional array
		}
		int[][] spiral = new int[use][use];

		int d, counter, i, j, l, m, k;
		d = use - 1;
		counter = use * use + 1; // highest counter value
		for (i = 0; i <= (use + 1) / 2; i++) {
			// generating left to right array
			for (j = i; j <= d; j++) {
				spiral[i][j] = --counter;
			}
			// generating up to down array
			for (l = i + 1; l <= d; l++) {
				spiral[l][d] = --counter;
			}
			// generating right to left array
			for (k = d - 1; k >= i; k--) {
				spiral[d][k] = --counter;
			}

			// generating down to up array
			for (m = d - 1; m > i; m--) {
				spiral[m][i] = --counter;
			}
			d--;
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("spiral(int) - end");
		}
		return spiral;
	}

}