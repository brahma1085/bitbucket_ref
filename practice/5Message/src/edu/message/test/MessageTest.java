package edu.message.test;

import java.io.IOException;
import java.util.Properties;

import edu.message.Message;

public class MessageTest {
	private static Properties properties = new Properties();
	static {
		try {
			properties.load(MessageTest.class.getClassLoader()
					.getResourceAsStream("message.properties"));
		} catch (IOException e) {
			throw new ExceptionInInitializerError("error in loading file");
		}
	}

	public static void main(String[] args) {
		String className = properties.getProperty("message.hello");
		// String className = properties.getProperty("message.hai");
		try {
			Message message = (Message) Class.forName(className).newInstance();
			message.printMessage();
		} catch (Exception e) {
			System.out.println("Exception==>" + e.getClass().getName() + "==>"
					+ e.getMessage());
		}
	}
}
