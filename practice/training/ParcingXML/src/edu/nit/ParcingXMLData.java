package edu.nit;

import java.io.IOException;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import edu.model.Memo;

public class ParcingXMLData {
	public static void main(String[] args) {
		try {
			Digester digester = new Digester();
			digester.addObjectCreate("memo", Memo.class);
			digester.addBeanPropertySetter("memo/to", "to");
			digester.addBeanPropertySetter("memo/from", "from");
			digester.addBeanPropertySetter("memo/subject", "subject");
			digester.addBeanPropertySetter("memo/body", "body");
			digester.addBeanPropertySetter("memo/cc", "cc");
			Memo memo = (Memo) digester.parse(ParcingXMLData.class
					.getClassLoader().getResourceAsStream("memo.xml"));
			System.out.println(memo.getBody());
			System.out.println(memo.getCc());
			System.out.println(memo.getFrom());
			System.out.println(memo.getSubject());
			System.out.println(memo.getTo());
		} catch (IOException e) {
			System.out.println("IOException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
		} catch (SAXException e) {
			System.out.println("SAXException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
		}
	}
}
