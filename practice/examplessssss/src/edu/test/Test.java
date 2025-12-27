package edu.test;

import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Test {
	public static void main(String[] args) {
		try {
			Scanner scanner = new Scanner(System.in);
			System.out.println("enter file location==>");
			File file = new File(scanner.nextLine());
			FileReader fileReader = new FileReader(file);
			int ch = 0;
			while ((ch = fileReader.read()) != -1) {
				String string = Character.toString((char) ch);
				StringBuilder builder = new StringBuilder(string);
				System.out.print(builder.toString());
			}
		} catch (Exception e) {
			System.out.println("Exception==>" + e.getClass().getName() + "==>"
					+ e.getMessage());
		}
	}
}