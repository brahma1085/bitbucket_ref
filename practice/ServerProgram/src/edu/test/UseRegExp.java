package edu.test;

import java.util.Locale;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.RegularExpression;

public class UseRegExp {
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		RegularExpression expression = new RegularExpression(
				"anand is bad boy", "i");
		System.out.println(expression.getNumberOfGroups());
		System.out.println(expression.matches("anand is bad boy"));
		System.out.println(expression.toString());
		Match match = new Match();
		if (expression.matches("anand is bad boy", match)) {
			System.out.println("expression matched with the given text");
		}
		Locale locale = new Locale("it");
		System.out.println(locale.getDisplayLanguage());
		System.out.println(locale.getISO3Language());
		Locale[] locales = locale.getAvailableLocales();
		System.out.println(locales.length);
		for (Locale s : locales) {
			System.out.println(s);
		}
	}
}
