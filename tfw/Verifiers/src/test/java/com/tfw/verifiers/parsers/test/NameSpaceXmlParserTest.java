package com.tfw.verifiers.parsers.test;

import org.apache.log4j.Logger;

import java.io.StringReader;
import java.util.Iterator;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * The Class NameSpaceXmlParserTest.
 */
public class NameSpaceXmlParserTest {
	
	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(NameSpaceXmlParserTest.class);

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("main(String[]) - start"); //$NON-NLS-1$
		}

		String xml = "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
				+ " xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
				+ "xmlns:ser=\"http://services.web.post.list.com\"><soapenv:Header><authInfo xsi:type=\"soap:authentication\" "
				+ "xmlns:soap=\"http://list.com/services/SoapRequestProcessor\"><!--You may enter the following 2 items in any order-->"
				+ "<username xsi:type=\"xsd:string\">dfasf@google.com</username><password xsi:type=\"xsd:string\">PfasdfRem91</password></authInfo>"
				+ "</soapenv:Header></soapenv:Envelope>";
		LOG.info(xml);
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true);
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		Document doc = builder.parse(new InputSource(new StringReader(xml)));
		XPath xpath = XPathFactory.newInstance().newXPath();
		xpath.setNamespaceContext(new NamespaceContext() {

			@SuppressWarnings("rawtypes")
			@Override
			public Iterator getPrefixes(String arg0) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("$NamespaceContext.getPrefixes(String) - start"); //$NON-NLS-1$
				}

				if (LOG.isDebugEnabled()) {
					LOG.debug("$NamespaceContext.getPrefixes(String) - end"); //$NON-NLS-1$
				}
				return null;
			}

			@Override
			public String getPrefix(String arg0) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("$NamespaceContext.getPrefix(String) - start"); //$NON-NLS-1$
				}

				if (LOG.isDebugEnabled()) {
					LOG.debug("$NamespaceContext.getPrefix(String) - end"); //$NON-NLS-1$
				}
				return null;
			}

			@Override
			public String getNamespaceURI(String arg0) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("$NamespaceContext.getNamespaceURI(String) - start"); //$NON-NLS-1$
				}

				if ("soapenv".equals(arg0)) {
					if (LOG.isDebugEnabled()) {
						LOG.debug("$NamespaceContext.getNamespaceURI(String) - end"); //$NON-NLS-1$
					}
					return "http://schemas.xmlsoap.org/soap/envelope/";
				}

				if (LOG.isDebugEnabled()) {
					LOG.debug("$NamespaceContext.getNamespaceURI(String) - end"); //$NON-NLS-1$
				}
				return null;
			}
		});
		// XPath Query for showing all nodes value

		try {
			XPathExpression expr = xpath.compile("/soapenv:Envelope/soapenv:Header/authInfo/password");
			Object result = expr.evaluate(doc, XPathConstants.NODESET);
			NodeList nodes = (NodeList) result;
			LOG.info("Got " + nodes.getLength() + " nodes");
		} catch (Exception e) {
			LOG.error(e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("main(String[]) - end"); //$NON-NLS-1$
		}
	}
}
