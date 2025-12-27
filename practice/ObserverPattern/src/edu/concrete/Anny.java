package edu.concrete;

import edu.observers.Observer;

public class Anny implements Observer {
	Appointment appointment;

	public Anny(Appointment appointment) {
		this.appointment = appointment;
	}

	@Override
	public void handleEvent() {
		System.out.println("Anny==>time==>" + appointment);
	}
}
