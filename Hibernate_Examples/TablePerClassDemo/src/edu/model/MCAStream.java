package edu.model;

public class MCAStream extends Student {
	private String mcastream;

	public MCAStream(String sname, String mcastream) {
		super(sname);
		this.mcastream = mcastream;
	}

	public String getMcastream() {
		return mcastream;
	}

}
