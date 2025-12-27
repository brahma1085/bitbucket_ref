package edu.utility;

import java.util.Enumeration;

public class Enum implements Enumeration {
	private int count = 0;
	private boolean more = true;

	public boolean hasMoreElements() {
		return more;
	}

	public Object nextElement() {
		count++;
		if (count > 4)
			more = false;
		return new Integer(count);
	}

}
