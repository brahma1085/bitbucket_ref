package edu.nit;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class XMLParsingSAX {
	private static Document document;
	private static SAXBuilder builder;
	static {
		builder = new SAXBuilder();
		try {
			document = builder.build(XMLParsingSAX.class.getClassLoader()
					.getResourceAsStream("db-details.xml"));
		} catch (JDOMException e) {
			System.out.println("JDOMException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
		}
	}

	public static void main(String[] args) {
		String env = System.getenv("STU.ENV");
		Element element = document.getRootElement().getChild(env).getChild(
				"student");
		System.out.println("element==>" + element);
		List list = element.getAttributes();
		System.out.println("attributes as a list==>" + list);
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			System.out.println("attribuite values after iteration process==>"
					+ iterator.next());
		}
		String key = element.getAttributeValue("details");
		System.out.println("attribute value==>" + key);
		Element element2 = document.getRootElement().getChild(env).getChild(
				"env1").getChild("db-class");
		System.out.println("2nd element==>" + element2);
		String string = element2.getText();
		System.out.println(string);
		Element element3 = document.getRootElement();
		System.out.println("root element==>" + element3);
		String string2 = element3.getValue();
		List values = element3.getChildren();
		System.out.println("2nd division values==>" + string2);
		System.out.println(values);
		Element element4 = document.getRootElement().getChild("dev2");
		System.out.println("2nd division elements==>" + element4);
		Element element5 = document.getRootElement().getChild("dev2").getChild(
				"env1");
		String string3 = element5.getAttributeValue("details");
		System.out.println("2nd division details==>" + string3);
		String string4 = element5.getChildText("db-class");
		System.out.println(string4);
	}
}