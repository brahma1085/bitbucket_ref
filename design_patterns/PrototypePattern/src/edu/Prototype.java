package edu;

public class Prototype implements Cloneable {
	private String sno = "some name";

	public Object clone() {
		try {
			return super.clone();// shallow type cloning
		} catch (CloneNotSupportedException e) {
			System.out.println("CloneNotSupportedException==>"
					+ e.getClass().getName() + "==>" + e.getMessage());
		}
		return null;
	}

	public String getString() {
		return sno;
	}
}