package edu.concrete;

import edu.observers.Observer;

public class Envy implements Observer {
	Appointment appointment;

	public Envy(Appointment appointment) {
		this.appointment = appointment;
	}

	@Override
	public void handleEvent() {
		System.out.println("Envy==>time==>" + appointment);
	}

}
