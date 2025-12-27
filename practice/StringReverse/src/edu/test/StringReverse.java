package edu.test;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class StringReverse {

	public static void main(String[] args) throws Exception {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(System.in));
		System.out.println("enter file name ==>");
		String string = bufferedReader.readLine();
		FileOutputStream fileOutputStream = new FileOutputStream("E://"
				+ string + ".txt");
		System.out.println("enter your file content==>");
		StringBuilder builder = new StringBuilder();
		String string2 = null;
		while (!(string2 = bufferedReader.readLine()).equalsIgnoreCase("null")) {
			System.out.println(string2.length());
			builder.append(string2);
			builder.append("\n");
		}
		fileOutputStream.write(builder.toString().getBytes());
		fileOutputStream.flush();
		fileOutputStream.close();
		System.out.println("reverse of the file content==>"
				+ builder.reverse().toString());
		FileOutputStream fileOutputStream2 = new FileOutputStream("E://"
				+ string + 1 + ".txt");
		fileOutputStream2.write(builder.toString().getBytes());
		fileOutputStream2.flush();
		fileOutputStream2.close();
	}
}