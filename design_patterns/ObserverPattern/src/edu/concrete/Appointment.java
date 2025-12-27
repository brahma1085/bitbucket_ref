package edu.concrete;

public class Appointment {
	private String mtime;

	public Appointment(String mtime) {
		this.mtime = mtime;
	}

	public void changeTime(String mtime) {
		this.mtime = mtime;
	}

	public String toString() {
		return mtime;
	}
}
