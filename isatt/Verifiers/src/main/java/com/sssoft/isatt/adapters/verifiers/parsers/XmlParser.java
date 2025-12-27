package com.sssoft.isatt.adapters.verifiers.parsers;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sssoft.isatt.adapters.verifiers.bean.VerifyResult;

/**
 * The Class XmlParser.
 *
 * @author brahma
 * 
 * This class is useful to parse the WSDL/XML response strings and
 * returns the required results by parsing through the given XML String
 */
public class XmlParser {
	
	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(XmlParser.class);

	static {
		BasicConfigurator.configure();
	}

	/**
	 * This method takes the XML String to be parsed, the xPath which specifies
	 * the path to required element and returns the VerifyResult with data.
	 *
	 * @param xmlResponse the String
	 * @param xPathExp the String
	 * @param verifyResult the VerifyResult
	 * @return verifyResult
	 * @throws XPathExpressionException the x path expression exception
	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws SAXException the sAX exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static VerifyResult getNodeValue(String xmlResponse, String xPathExp, VerifyResult verifyResult) throws XPathExpressionException,
			ParserConfigurationException, SAXException, IOException {
		LOG.info("received XML String : " + xmlResponse);
		LOG.info("received XML Xpath expression : " + xPathExp);
		String resultXml = null;
		NodeList nodeList = null;
		nodeList = (NodeList) prepareXmlDocument(xmlResponse, xPathExp);
		resultXml = nodeList.item(0).getFirstChild().getNodeValue();
		verifyResult.setRequiredResult(resultXml);

		if (LOG.isDebugEnabled()) {
			LOG.debug("getNodeValue(String, String, VerifyResult) - end"); //$NON-NLS-1$
		}
		return verifyResult;
	}

	/**
	 * This method prepares the XML Document and returns the result nodes, by
	 * taking the XML response string as source and xPath Expression of the
	 * required value.
	 *
	 * @param xmlSource the String
	 * @param xPathExp the String
	 * @return list of nodes the Object
	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws SAXException the sAX exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws XPathExpressionException the x path expression exception
	 */
	private static Object prepareXmlDocument(String xmlSource, String xPathExp) throws ParserConfigurationException, SAXException, IOException,
			XPathExpressionException {
		LOG.info("preparing the XML Document");
		Object resultObj = null;
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document xmlDocument = builder.parse(new InputSource(new ByteArrayInputStream(xmlSource.getBytes("utf-8"))));
		XPath xPath = XPathFactory.newInstance().newXPath();
		XPathExpression xPathExpression = xPath.compile(xPathExp);
		resultObj = xPathExpression.evaluate(xmlDocument, XPathConstants.NODESET);

		if (LOG.isDebugEnabled()) {
			LOG.debug("prepareXmlDocument(String, String) - end"); //$NON-NLS-1$
		}
		return resultObj;
	}

}