package com.tfw.verifiers.parsers.test;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.tfw.verifiers.bean.VerifyResult;

/**
 * The Class NSXmlParserTest.
 */
public class NSXmlParserTest {
	
	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(NSXmlParserTest.class);

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

		String path = XmlParserTest.class.getClassLoader().getResource("soapsample.xml").getPath();
		String xmlResponse = FileUtils.readFileToString(new File(path));
		VerifyResult verifyResult = new VerifyResult();
		verifyResult = com.tfw.verifiers.parsers.NSXmlParser.getNodeValue(xmlResponse,
				"/soapenv:Envelope/soapenv:Body/ns2:authenticateResponse/return/accessToken", verifyResult);
		LOG.info(verifyResult);
	}

}