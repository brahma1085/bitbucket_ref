package edu;

public class Amplifier {
	public void on() {
		System.out.println("Amplifier is on..");
	}

	public void off() {
		System.out.println("Amplifier is sutting down");
	}

	public void setDVD(String dvd) {
		System.out.println(dvd + "DVDPlayer is set");
	}

	public void setSurroundings() {
		System.out.println("surroungs are set");
	}

	public void setSound(int sound) {
		System.out.println("volume is==>" + sound);
	}
}
