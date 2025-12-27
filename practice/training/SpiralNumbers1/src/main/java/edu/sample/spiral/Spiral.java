package edu.sample.spiral;

import org.apache.log4j.Logger;

/**
 * The Class Spiral.
 */
public class Spiral {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(Spiral.class);

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
		Spiral.max = max;
	}

	/**
	 * Generates the Spiral as two dimentional array.
	 * 
	 * @param max
	 *            the max
	 * @return the int[][]
	 */
	public static int[][] spiral(int max) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("spiral(int=" + max + ") - start");
		}

		Spiral.setMax(max);
		int use = (int) Math.ceil(Math.sqrt(max)); // taking the ceil value
		if (use % 2 == 0) {
			use = use + 1; // getting the odd number for two dimentional array
		}
		int[][] spiral = new int[use][use];

		int d, counter, i, j, l, m, k;
		d = use - 1;
		counter = use * use + 1; // highest counter value
		for (i = 0; i <= (use + 1) / 2; i++) {
			// generating right to left array
			for (j = d; j >= i; j--) {
				spiral[i][j] = --counter;
				if (counter <= 0) {
					return spiral;
				}
			}
			
			// generating down to up array
			for (l = i + 1; l <= d; l++) {
				spiral[l][i] = --counter;
				if (counter <= 0) {
					return spiral;
				}
			}
			
			// generating left to right array
			for (k = i + 1; k <= d; k++) {
				spiral[d][k] = --counter;
				if (counter <= 0) {
					return spiral;
				}
			}

			// generating up to down array
			for (m = d - 1; m > i; m--) {
				spiral[m][d] = --counter;
				if (counter <= 0) {
					return spiral;
				}
			}
			
			d--;
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("spiral(int) - end");
		}
		return spiral;
	}

	/**
	 * generates the spiral matrix by taking the two mentional array string.
	 * 
	 * @param sp
	 *            the sp
	 * @return the string
	 */
	public static String toString(int[][] sp) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("toString(int[][]=" + sp + ") - start");
		}

		String spiral = "";
		for (int i = 0; i < sp.length; i++) {
			for (int j = 0; j < sp.length; j++) {
				if (sp[i][j] > getMax()) {
					spiral += ("  " + " " + " ");
					continue;
				} else if (sp[i][j] >= 100) {
					spiral += (sp[i][j] + " ");
				} else if (sp[i][j] >= 10) {
					spiral += (" " + sp[i][j] + " ");
				} else {
					if (sp[i][j] <= 0) {
						spiral += ("  " + " " + " ");
						continue;
					}
					spiral += ("  " + sp[i][j] + " ");
				}
			}
			spiral += "\n";
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("toString(int[][]) - end");
		}
		return spiral;
	}

}