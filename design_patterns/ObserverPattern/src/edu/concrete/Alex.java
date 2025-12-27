package edu.concrete;

import edu.observers.Observer;

public class Alex implements Observer {
	Appointment appointment;

	public Alex(Appointment appointment) {
		this.appointment = appointment;
	}

	@Override
	public void handleEvent() {
		System.out.println("alex==>time==>" + appointment);
	}

}
