package com.tfw.verifiers.parsers;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.xml.sax.SAXException;

import com.tfw.verifiers.bean.CaptureProps;
import com.tfw.verifiers.bean.VerifyResult;

/**
 * The Class CommonParser.
 *
 * @author brahma
 * 
 * This class is useful to invoke the respective parsers, based on the
 * response type given by the user and fills up the data into
 * VerifyResult bean and returns that bean
 */
public class CommonParser {
	
	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(CommonParser.class);

	/**
	 * This method is useful to invoke the respective parsers, based on the
	 * response type given by the user and fills up the data into VerifyResult
	 * bean and returns that bean.
	 *
	 * @param response the String
	 * @param captureProps the CaptureProps
	 * @return the VerifyResult
	 */
	public static VerifyResult fetchTagValue(String response, CaptureProps captureProps) {
		LOG.info("response to be compared : " + response);
		String actualKey = captureProps.getActionName();
		String responseType = captureProps.getResponseType();
		VerifyResult verifyResult = new VerifyResult();
		StringBuffer stringBuffer = new StringBuffer();
		try {
			if (responseType != null) {
				String reqXpath = captureProps.getReqXpath();
				String xPath = captureProps.getxPath();
				String compReq = captureProps.getCompRequest();
				if ("json1".equalsIgnoreCase(responseType.trim())) {
					verifyResult = JSONParser.prepareJson(response, captureProps.getxPath(), actualKey, captureProps.getArrayInsCount(),
							captureProps.getArrayGoto(), verifyResult);
				} else if ("nsxml".equalsIgnoreCase(responseType.trim())) {
					if (reqXpath != null && reqXpath.length() != 0) {
						if (compReq != null && compReq.length() != 0) {
							verifyResult = NSXmlParser.getNodeValue(compReq, reqXpath.trim(), verifyResult);
						} else {
							verifyResult = NSXmlParser.getNodeValue(response, xPath, verifyResult);
						}
					} else if (xPath != null && xPath.length() != 0) {
						verifyResult = NSXmlParser.getNodeValue(response, xPath.trim(), verifyResult);
					} else {
						stringBuffer.append("\n" + "need proper Namespace XML xPath. ");
					}
				} else if ("xml".equalsIgnoreCase(responseType.trim())) {
					verifyResult = XmlParser.getNodeValue(response, captureProps.getxPath(), verifyResult);
				} else if ("json2".equalsIgnoreCase(responseType.trim())) {
					try {
						if (reqXpath != null && reqXpath.length() != 0) {
							if (compReq != null && compReq.length() != 0) {
								verifyResult = JSONPathParser.parseJson(compReq, reqXpath.trim(), verifyResult);
							} else {
								verifyResult = JSONPathParser.parseJson(response, xPath, verifyResult);
							}
						} else if (xPath != null && xPath.length() != 0) {
							verifyResult = JSONPathParser.parseJson(response, xPath.trim(), verifyResult);
						} else {
							stringBuffer.append("\n" + "need proper JSON xPath. ");
						}
					} catch (Exception e) {
						stringBuffer.append("\n" + "need proper JSON xPath. ");
						LOG.error("error occured due to : " + e, e);
					}
				} else {
					verifyResult.setRequiredResult(response);
				}
			} else {
				verifyResult.setResult(false);
				stringBuffer.append("\n" + " Please mention response format in properties file. ");
				LOG.error(" Please mention response format in properties file ");
			}
		} catch (JSONException e) {
			LOG.error(actualKey + " is not present at given xPATH " + e, e);
			if (actualKey != null) {
				stringBuffer.append("\n" + actualKey + " is not present at given xPATH. ");
			} else {
				stringBuffer.append("\n" + "required value is not present at given xPATH. ");
			}
		} catch (XPathExpressionException e) {
			LOG.error(" Please provide valid XPath " + e, e);
			stringBuffer.append("\n" + " Please provide valid XPath. ");
		} catch (ParserConfigurationException e) {
			LOG.error(" wrong xml document configuration to parse " + e, e);
			stringBuffer.append("\n" + " wrong xml document configuration to parse. ");
		} catch (SAXException e) {
			LOG.error(" malformed xml document configuration to parse " + e, e);
			stringBuffer.append("\n" + " malformed xml document configuration to parse. ");
		} catch (FileNotFoundException e) {
			LOG.error("namespace file not found" + e, e);
			stringBuffer.append("\n" + "namespace file not found. ");
		} catch (IOException e) {
			LOG.error(" I/O error " + e, e);
			stringBuffer.append("\n" + " I/O error. ");
		} catch (Exception e) {
			LOG.error("error occured due to : " + e, e);
		}
		verifyResult.setComment(stringBuffer.toString());

		if (LOG.isDebugEnabled()) {
			LOG.debug("fetchTagValue(String, CaptureProps) - end"); //$NON-NLS-1$
		}
		return verifyResult;
	}
}
