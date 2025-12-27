package edu.test;

import edu.concrete.Alex;
import edu.concrete.Anny;
import edu.concrete.Appointment;
import edu.concrete.Envy;
import edu.concrete.Gwen;
import edu.observers.Observable;
import edu.observers.ObservableImpl;

public class ObserverTest {
	public static void main(String[] args) {
		Appointment appointment = new Appointment("7:00 AM");
		Alex alex = new Alex(appointment);
		Anny anny = new Anny(appointment);
		Envy envy = new Envy(appointment);
		Gwen gwen = new Gwen(appointment);
		Observable observable = new ObservableImpl();
		observable.addObserver(alex);
		observable.addObserver(anny);
		observable.addObserver(envy);
		observable.addObserver(gwen);
		observable.notifyAllObservers();
		appointment.changeTime("8:00 AM");
		System.out.println("due to rain match changed to");
		observable.notifyAllObservers();
	}
}
