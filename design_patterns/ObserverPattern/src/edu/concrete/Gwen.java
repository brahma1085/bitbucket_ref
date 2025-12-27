package edu.concrete;

import edu.observers.Observer;

public class Gwen implements Observer {
	Appointment appointment;

	public Gwen(Appointment appointment) {
		this.appointment = appointment;
	}

	@Override
	public void handleEvent() {
		System.out.println("Gwen==>time==>" + appointment);
	}

}
