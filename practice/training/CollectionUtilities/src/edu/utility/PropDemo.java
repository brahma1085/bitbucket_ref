package edu.utility;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class PropDemo {
	private Properties properties, properties1, properties2, properties3;
	String name, number;
	DataInputStream dataInputStream;
	boolean flag = false;

	public void getPropDetails() {
		properties = new Properties();
		System.out.println("pro size==>" + properties.size());
		System.out.println("default value in pro==>"
				+ properties.getProperty("anand", "not available"));
		properties.setProperty("anand", "nice fellow");
		properties.put("ramesh", "nice boy");
		System.out.println("pro size==>" + properties.size());
		System.out.println(properties.clone());
		Enumeration enumeration = properties.keys();
		while (enumeration.hasMoreElements()) {
			String object = (String) enumeration.nextElement();
			System.out.println("keys==>" + object);
		}
		Enumeration enumeration2 = properties.elements();
		while (enumeration2.hasMoreElements()) {
			String object = (String) enumeration2.nextElement();
			System.out.println("values==>" + object);
		}
		Enumeration enumeration3 = properties.propertyNames();
		while (enumeration3.hasMoreElements()) {
			String object = (String) enumeration3.nextElement();
			System.out.println("keys using property names==>" + object);
		}
		System.out.println("is this in pro?==>"
				+ properties.getProperty("umesh"));
		properties1 = new Properties(properties);
		System.out.println("size pro1==>" + properties1.size());
		properties1.setProperty("nagesh", "from Nellore");
		properties1.setProperty("thripura", "from kerala");
		Enumeration enumeration4 = properties1.elements();
		while (enumeration4.hasMoreElements()) {
			System.out.println("pro1 values==>" + enumeration4.nextElement());
		}
		System.out.println("value using get()==>" + properties1.get("nagesh"));
		System.out.println("is it there in pro1==>"
				+ properties1.getProperty("anand"));
	}

	public void loadMethod() throws Exception {
		properties2 = new Properties();
		dataInputStream = new DataInputStream(System.in);
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream("E:/file.txt");
			if (fileInputStream != null) {
				properties2.load(fileInputStream);
				fileInputStream.close();
			}
			System.out.println("prop2 size==>" + properties2.size());
			Set set = properties2.entrySet();
			Iterator iterator = set.iterator();
			while (iterator.hasNext()) {
				System.out.println("person & ph no:==>" + iterator.next());
			}
		} catch (FileNotFoundException e) {
			System.out.println("file is not there");
		} catch (IOException e) {
			System.out.println("file reading error");
		}
	}

	public void saveMethod() throws Exception {
		System.out.println("type quit to exit from here");
		do {
			System.out.println("enter name==>");
			name = dataInputStream.readLine();
			if (name.equals("quit"))
				continue;
			System.out.println("enter number==>");
			number = dataInputStream.readLine();
			properties2.setProperty(name, number);
			flag = true;
		} while (!name.equals("quit"));
		if (flag) {
			PrintStream printStream = new PrintStream(new FileOutputStream(
					"E:/file.txt"));
			properties2.save(printStream, "phone book");
			printStream.close();
		}
		System.out
				.println("search for the number by entering name & quit to exit");
		do {
			System.out.println("enter name to find==>");
			name = dataInputStream.readLine();
			if (name.equals("quit"))
				continue;
			number = (String) properties2.getProperty(name);
			System.out.println("his number is==>" + number);
		} while (!name.equals("quit"));
	}
}
