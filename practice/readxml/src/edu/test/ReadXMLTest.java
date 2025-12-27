package edu.test;

import java.io.IOException;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import edu.model.Student;

public class ReadXMLTest {

	public static void main(String[] args) {
		try {
			Digester digester = new Digester();
			digester.addObjectCreate("student", Student.class);
			digester.addBeanPropertySetter("student/studentNo", "studentNo");
			Student student = (Student) digester.parse(ReadXMLTest.class
					.getClassLoader().getResourceAsStream("student.xml"));
			System.out.println(student.getStudentNo());
		} catch (IOException e) {
			System.out.println("IOException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
		} catch (SAXException e) {
			System.out.println("SAXException==>" + e.getClass().getName()
					+ "==>" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception==>" + e.getClass().getName() + "==>"
					+ e.getMessage());
		}

	}

}
