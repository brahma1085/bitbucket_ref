package org.ajaxtags.demo;

import java.util.Date;

import org.ajaxtags.server.CallbackObservable;
import org.ajaxtags.server.ICallback;
import org.ajaxtags.xml.AjaxXmlBuilder;

/**
 * reset the date and count all liseners
 * @author game
 *
 */
public class TimeDeamon extends Thread {

	private static CallbackObservable call = null;
	private static TimeDeamon deamon = null;
	
	/**
	 * 
	 * @return the updater
	 */
	public static ICallback get() {
		if (deamon == null) {
			call = new CallbackObservable();
			deamon = new TimeDeamon();
			deamon.start();
		}
		return call;
	}

	@Override
	public void run() {

		while (true) {
			AjaxXmlBuilder xml = new AjaxXmlBuilder();
			xml.addItem("date", String.valueOf(new Date()));
			xml.addItem("datum", String.valueOf(new Date()));
			xml.addItem("count",  String.valueOf(  get().countObservers() ));
			get().quickUpdate(xml);
			try {
				sleep(700);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
