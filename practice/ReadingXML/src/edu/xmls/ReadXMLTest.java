package edu.xmls;

import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class ReadXMLTest {
	private static Document document;
	private static SAXBuilder builder;
	static {
		builder = new SAXBuilder();
		try {
			document = builder.build(ReadXMLTest.class.getClassLoader()
					.getResourceAsStream("db-details.xml"));
		} catch (JDOMException e) {
			throw new ExceptionInInitializerError(
					"error in loading configuration file");
		} catch (IOException e) {
			throw new ExceptionInInitializerError(
					" error in loading configuration file");
		}
	}

	public static void main(String[] args) {
		String env = System.getenv("STU.ENV");
		Element element = document.getRootElement().getChild(env).getChild(
				"student1");
		Element element2 = document.getRootElement().getChild(env).getChild(
				"env").getChild("db-class");
		String string23 = element2.getText();
		System.out.println(string23);
		String details = element.getAttributeValue("details");
		String value = element.getName();
		String string = element.getText();
		System.out.println(".details :" + details);
		System.out.println("value==>" + value);
		System.out.println(string);
	}
}
