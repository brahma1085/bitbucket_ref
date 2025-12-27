package com.sssoft.isatt.adapters.verifiers.parsers.test;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * The Class XmlParserTest.
 */
public class XmlParserTest {
	
	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(XmlParserTest.class);

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("main(String[]) - start"); //$NON-NLS-1$
		}

		try {
			String path = XmlParserTest.class.getClassLoader().getResource("employees.xml").getPath();
			FileInputStream file = new FileInputStream(new File(path));
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document xmlDocument = builder.parse(file);
			XPath xPath = XPathFactory.newInstance().newXPath();
			String expression = "/Employees/Employee[@emplid='3333']/email";
			LOG.info(expression);
			String email = xPath.compile(expression).evaluate(xmlDocument);
			LOG.info(email);
			expression = "/Employees/Employee/firstname";
			LOG.info(expression);
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
			for (int i = 0; i < nodeList.getLength(); i++) {
				LOG.info(nodeList.item(i).getFirstChild().getNodeValue());
			}
			expression = "/Employees/Employee[@type='admin']/firstname";
			LOG.info(expression);
			nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
			for (int i = 0; i < nodeList.getLength(); i++) {
				LOG.info(nodeList.item(i).getFirstChild().getNodeValue());
			}
			expression = "/Employees/Employee[@emplid='2222']";
			LOG.info(expression);
			Node node = (Node) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODE);
			if (null != node) {
				nodeList = node.getChildNodes();
				for (int i = 0; null != nodeList && i < nodeList.getLength(); i++) {
					Node nod = nodeList.item(i);
					if (nod.getNodeType() == Node.ELEMENT_NODE)
						LOG.info(nodeList.item(i).getNodeName() + " : " + nod.getFirstChild().getNodeValue());
				}
			}
			expression = "/Employees/Employee[age>40]/firstname";
			nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
			LOG.info(expression);
			for (int i = 0; i < nodeList.getLength(); i++) {
				LOG.info(nodeList.item(i).getFirstChild().getNodeValue());
			}
			expression = "/Employees/Employee[1]/firstname";
			LOG.info(expression);
			nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
			for (int i = 0; i < nodeList.getLength(); i++) {
				LOG.info(nodeList.item(i).getFirstChild().getNodeValue());
			}
			expression = "/Employees/Employee[position() <= 2]/firstname";
			LOG.info(expression);
			nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
			for (int i = 0; i < nodeList.getLength(); i++) {
				LOG.info(nodeList.item(i).getFirstChild().getNodeValue());
			}
			expression = "/Employees/Employee[last()]/firstname";
			LOG.info(expression);
			nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
			for (int i = 0; i < nodeList.getLength(); i++) {
				LOG.info(nodeList.item(i).getFirstChild().getNodeValue());
			}
		} catch (FileNotFoundException e) {
			LOG.warn("main(String[]) - exception ignored", e); //$NON-NLS-1$
		} catch (SAXException e) {
			LOG.warn("main(String[]) - exception ignored", e); //$NON-NLS-1$
		} catch (IOException e) {
			LOG.warn("main(String[]) - exception ignored", e); //$NON-NLS-1$
		} catch (ParserConfigurationException e) {
			LOG.warn("main(String[]) - exception ignored", e); //$NON-NLS-1$
		} catch (XPathExpressionException e) {
			LOG.warn("main(String[]) - exception ignored", e); //$NON-NLS-1$
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("main(String[]) - end"); //$NON-NLS-1$
		}
	}
}
