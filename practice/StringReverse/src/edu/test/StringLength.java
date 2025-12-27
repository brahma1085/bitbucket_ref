package edu.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;

public class StringLength {
	public static void main(String[] args) throws Exception {
		BufferedReader fileReader = new BufferedReader(new FileReader(
				"E://anand.txt"));
		String string = null;
		while ((string = fileReader.readLine()) != null) {
			System.out.println(string);
			StringTokenizer tokenizer = new StringTokenizer(string, " ");
			while (tokenizer.hasMoreElements()) {
				String string2 = (String) tokenizer.nextElement();
				System.out.println(string2 + " : " + string2.length());
			}
		}
	}
}
