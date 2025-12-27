package edu.model;

public class MBAStream extends Student {
	private String mbastream;

	public MBAStream(String mbastream, String sname) {
		super(sname);
		this.mbastream = mbastream;
	}

	public String getMbastream() {
		return mbastream;
	}

}
