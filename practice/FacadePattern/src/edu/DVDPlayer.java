package edu;

public class DVDPlayer {

	public void on() {
		System.out.println("DVDPlayer is on");
	}

	public void play(String movie) {
		System.out.println("currently playing movie==>" + movie);
	}

	public void stop() {
		System.out.println("stop playing movie");
	}

	public void eject() {
		System.out.println("eject dvd");
	}

	public void off() {
		System.out.println("DVDPlayer is off");
	}
}
