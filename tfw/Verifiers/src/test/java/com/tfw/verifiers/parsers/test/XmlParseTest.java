package com.tfw.verifiers.parsers.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.tfw.verifiers.bean.VerifyResult;
import com.tfw.verifiers.parsers.XmlParser;

/**
 * The Class XmlParseTest.
 */
public class XmlParseTest {
	
	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(XmlParseTest.class);

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

		VerifyResult verifyResult = new VerifyResult();
		String path = XmlParserTest.class.getClassLoader().getResource("employees.xml").getPath();
		String xmlResponse = FileUtils.readFileToString(new File(path));
		List<String> strings = getExpressions();
		for (String xPathExp : strings) {
			verifyResult = XmlParser.getNodeValue(xmlResponse, xPathExp, verifyResult);
			LOG.info("XmlParseTest => verifyResult => " + verifyResult);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("main(String[]) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Gets the expressions.
	 *
	 * @return the expressions
	 */
	private static List<String> getExpressions() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getExpressions() - start"); //$NON-NLS-1$
		}

		List<String> expressions = new ArrayList<String>();
		expressions.add("/Employees/Employee[@emplid='3333']/email");
		expressions.add("/Employees/Employee/firstname");
		expressions.add("/Employees/Employee[@type='admin']/firstname");
		expressions.add("/Employees/Employee[@emplid='2222']");
		expressions.add("/Employees/Employee[age>40]/firstname");
		expressions.add("/Employees/Employee[1]/firstname");
		expressions.add("/Employees/Employee[position() <= 2]/firstname");
		expressions.add("/Employees/Employee[last()]/firstname");

		if (LOG.isDebugEnabled()) {
			LOG.debug("getExpressions() - end"); //$NON-NLS-1$
		}
		return expressions;
	}
}
