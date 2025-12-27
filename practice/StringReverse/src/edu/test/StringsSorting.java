package edu.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class StringsSorting {

	public static void main(String[] args) throws Exception {
		String name[] = new String[10];
		String string = null;
		System.out.println("enter number of strings==>");
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		int one = Integer.parseInt(reader.readLine());
		for (int i = 0; i < one; i++) {
			name[i] = reader.readLine();
		}
		for (int i = 0; i < one; i++) {
			for (int j = i + 1; j < one; j++) {
				if ((name[j].compareTo(name[i])) < 0) {
					string = name[i];
					name[i] = name[j];
					name[j] = string;
				}
			}
		}
		for (int i = 0; i < one; i++)
			System.out.println(name[i]);
		compareto2();
	}

	public static void compareto2() {
		String string = "anand";
		String string2 = "guru";
		System.out.println(string.compareTo(string2));
	}
}