package edu.utility;

import java.util.Date;

public class DateAndTimeDemo {
	@SuppressWarnings("deprecation")
	public void getDateFormats() {
		Date date = new Date();
		String string[] = { "jan", "feb", "mar", "apr", "may", "jun", "jul",
				"aug", "sep", "oct", "nov", "dec" };
		System.out.println("current date==>" + date);
		System.out.println("getting date manually==>" + date.getDate() + ":"
				+ date.getMonth() + ":" + date.getYear() + "  "
				+ date.getHours() + ":" + date.getMinutes() + ":"
				+ date.getSeconds());
		date.setHours(23);
		date.setMinutes(45);
		date.setSeconds(34);
		System.out.println("updated time==>" + date.getHours() + ":"
				+ date.getMinutes() + ":" + date.getSeconds());
		System.out.println("default time format==>" + date);
		System.out.println("converted to GMT==>" + date.toGMTString());
		System.out.println("converted to Local==>" + date.toLocaleString());
		System.out.println("to string format==>" + date.toString());
		Date date1 = new Date(98375934579398l);
		System.out.println("difference b/w 2 dates==>" + date.compareTo(date1));
		System.out.println("time offset value in minutes==>"
				+ date.getTimezoneOffset());
	}
}