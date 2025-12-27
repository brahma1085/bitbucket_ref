package edu.utility;

import java.util.StringTokenizer;

public class StringTokenizerDemo {
	public void getStringDetails() {
		String string = "name = anand : learner;" + "edu = engg. : from JNTU;"
				+ "loc = hyd;" + "branch = CSE;";
		StringTokenizer tokenizer = new StringTokenizer(string, "=;");
		while (tokenizer.hasMoreTokens()) {
			String key = tokenizer.nextToken();
			String value = tokenizer.nextToken();
			System.out.println(key + "\t" + value);
		}
		System.out.println("number of tokens in this string==>"
				+ tokenizer.countTokens());
		System.out.println("next delim by giving delim==>"
				+ tokenizer.nextToken(":"));
	}
}