package edu.sample.spiral;

import org.apache.log4j.Logger;

/**
 * The Actual Functional Class ClockwiseSpiral.
 */
public class ClockwiseSpiral {

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(ClockwiseSpiral.class);

	/**
	 * Spiral method is used to generate the two dimentional matrix according to
	 * clockwise spiral formation.
	 * 
	 * @param max
	 *            the max
	 * @return the int[][]
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 */
	public static int[][] spiral(int max) throws IllegalArgumentException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("spiral(int=" + max + ") - start");
		}

		int use = (int) Math.sqrt(max);// the size of the array
		if (max % use != 0) {
			use = getValue(max);
		}
		
		int last = ((use * use) - max);

		while (last > use) {
			last = last - use;
		}

		int counter = max;// counter to count down from the max
		int x = use - 1;
		int y = 0;
		int left = use;
		int right = use - 1;
		int up = use - 2;
		int down = use - 1;

		int[][] spiral = new int[use][use];
		while (last != 0) {
			spiral[y][x] = 0;
			last--;
		}

		// x = x - last;

		while (counter > 0) {
			// generating left spiral matrix
			for (int i = left; (i > 0 && x >= 0); i--) {
				if (counter <= 0) {
					break;
				}
				spiral[y][x] = counter;
				counter--;
				x--;
			}
			left -= 2;
			y++;
			x++;

			// generating down spiral matrix
			for (int i = down; (i > 0); i--) {
				if (counter <= 0) {
					break;
				}
				spiral[y][x] = counter;
				counter--;
				y++;
			}
			down -= 2;
			y--;
			x++;

			// generating right spiral matrix
			for (int i = right; (i > 0); i--) {
				if (counter <= 0) {
					break;
				}
				spiral[y][x] = counter;
				counter--;
				x++;
			}
			right -= 2;
			y--;
			x--;

			// generating up spiral matrix
			for (int i = up; (i > 0); i--) {
				if (counter <= 0) {
					break;
				}
				spiral[y][x] = counter;
				counter--;
				if (y == up) {
					x--;
				} else {
					y--;
				}
			}
			up -= 2;
			y++;
			x--;
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("spiral(int) - end");
		}
		return spiral;

	}

	/**
	 * toString function to print the spiral numbers by taking the two
	 * dimentional matrix as input.
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
				if (sp[i][j] >= 100) {
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

	/**
	 * Gets the value.
	 * 
	 * @param max
	 *            the max
	 * @return the value
	 */
	public static int getValue(int max) {
		int value = 0;
		for (int i = 1; i < max; i++) {
			if (max <= i * i) {
				value = i;
				break;
			}
		}
		return value;
	}
}
