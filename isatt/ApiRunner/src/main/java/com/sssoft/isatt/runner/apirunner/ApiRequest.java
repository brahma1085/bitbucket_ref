package com.sssoft.isatt.runner.apirunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlOperation;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.WsdlRequest;
import com.eviware.soapui.impl.wsdl.WsdlSubmit;
import com.eviware.soapui.impl.wsdl.WsdlSubmitContext;
import com.eviware.soapui.impl.wsdl.support.wsdl.WsdlImporter;
import com.sssoft.isatt.utils.bean.ActionCommand;

/**
 * The Class ApiRequest.
 */
public class ApiRequest {

	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(ApiRequest.class);

	/** The hm. */
	private Map<String, String> hm = new HashMap<String, String>();

	/** The wsdl. */
	private String wsdl;

	/** The param. */
	private String param;

	/** The fieldname. */
	private String fieldname;

	/** The oper. */
	private String oper;

	/** The responsemsg. */
	private String responsemsg;

	/** The requestvalues. */
	private String requestvalues;

	/** The fieldlocation. */
	private String fieldlocation;

	/** The out param. */
	private String outParam;

	/** The tagname. */
	private String tagname;

	/** The addprevresponse. */
	boolean addprevresponse = false;

	/** The count. */
	private int count;

	/** The savingresponse. */
	private String savingresponse;

	/**
	 * Gets the sava response.
	 * 
	 * @return the sava response
	 */
	public String getSavaResponse() {
		return savingresponse;
	}

	/**
	 * Sets the sava response.
	 * 
	 * @param savaResponse
	 *            the new sava response
	 */
	public void setSavaResponse(String savaResponse) {
		this.savingresponse = savaResponse;
	}

	/**
	 * Split request.
	 * 
	 * @param ss
	 *            the ss
	 * @return the string buffer
	 */
	public StringBuffer splitRequest(String ss) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("splitRequest(String) - start"); //$NON-NLS-1$
		}

		StringBuffer sf = new StringBuffer();

		String[] split = ss.split("\\?");

		for (String s : split) {
			sf.append(s);
			for (Entry<String, String> iterate : hm.entrySet()) {
				if (s.contains(iterate.getKey())) {
					sf.append(iterate.getValue());
				}
			}

		}
		hm.clear();

		if (LOG.isDebugEnabled()) {
			LOG.debug("splitRequest(String) - end"); //$NON-NLS-1$
		}
		return sf;
	}

	/**
	 * Save response.
	 */
	public void saveResponse() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("saveResponse() - start"); //$NON-NLS-1$
		}

		if (responsemsg.contains(param)) {
			String[] split = responsemsg.split(param);

			for (String s : split) {

				String[] split1 = s.split("</");
				if (s.contains(param.substring(1))) {
					for (String s1 : split1) {
						LOG.info("Saved Response " + s1);
						setSavaResponse(s1);
						break;
					}

				}
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("saveResponse() - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Gets the response.
	 * 
	 * @return the response
	 */
	public void getResponse() {
		LOG.info("Inside getResponse method");
		String[] split = requestvalues.split(fieldlocation);
		StringBuffer re = new StringBuffer();
		int secondLetter = tagname.indexOf("<") + 1;
		String endtag = new StringBuilder(tagname).insert(secondLetter, "/").toString();
		String startHeader = fieldlocation.replace("/>", ">");
		String closeHeader = fieldlocation.replace("/>", ">");
		String closeHeader1 = closeHeader.replace("<", "</");
		count = 0;
		for (String s : split) {
			re.append(s);
			if (count == 0) {
				re.append(startHeader);
				re.append(tagname);
				re.append(getSavaResponse());
				re.append(endtag);
				re.append(closeHeader1);
				count++;
			}
		}
		requestvalues = re.toString();

		if (LOG.isDebugEnabled()) {
			LOG.debug("getResponse() - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Pass value.
	 * 
	 * @param actionCommand
	 *            the action command
	 * @param paramValue
	 *            the param value
	 * @throws Exception
	 *             the exception
	 */
	public void passValue(ActionCommand actionCommand, String paramValue) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("passValue(ActionCommand, String) - start"); //$NON-NLS-1$
		}

		if ("WSDL".equalsIgnoreCase(actionCommand.getAction())) {
			LOG.info("Calling WSDL action");
			wsdl = paramValue;
			if (wsdl != null) {
				actionCommand.setComment("WSDL is passed");
				actionCommand.setResult(true);
				actionCommand.setDetailMsgs("wsdl: " + wsdl);
			} else {
				actionCommand.setComment("WSDL is not passed");
				actionCommand.setResult(false);
				actionCommand.setDetailMsgs("WSDL is not provided");
			}
		} else if ("Operation".equalsIgnoreCase(actionCommand.getAction())) {
			LOG.info("Calling Operation action");
			oper = paramValue;
			if (oper != null) {
				actionCommand.setComment("Operation name is passed");
				actionCommand.setResult(true);
				actionCommand.setDetailMsgs("Operation " + oper);
			} else {
				actionCommand.setComment("Operation name is not passed");
				actionCommand.setResult(false);
				actionCommand.setDetailMsgs("Operation name is not passed");
			}
		} else if ("Parameter".equalsIgnoreCase(actionCommand.getAction())) {
			LOG.info("Calling Parameter action");
			param = paramValue;
			LOG.info("Parameter : " + param);
			fieldname = actionCommand.getObjectid1();
			hm.put(fieldname, param);
			if ((fieldname != null) && (param != null)) {
				actionCommand.setComment("Parameter is passed");
				actionCommand.setResult(true);
				actionCommand.setDetailMsgs(param + " parameter  is passed to " + fieldname + " object");
			} else {
				actionCommand.setComment("Parameter is not  passed");
				actionCommand.setResult(false);
				actionCommand.setDetailMsgs("Problem withh parameter,fieldname");
			}
		} else if ("Submit".equalsIgnoreCase(actionCommand.getAction())) {
			callingWsdl(actionCommand);
		} else if ("saveResponse".equalsIgnoreCase(actionCommand.getAction())) {
			LOG.info("Calling Save response action");
			param = paramValue;
			if (param != null) {
				saveResponse();
				actionCommand.setComment("Response is saved");
				actionCommand.setResult(true);
				actionCommand.setDetailMsgs("Response is saved");
			} else {
				actionCommand.setComment("Response not saved");
				actionCommand.setResult(false);
				actionCommand.setDetailMsgs("Response not saved");
			}
		} else if ("getResponse".equalsIgnoreCase(actionCommand.getAction())) {
			LOG.info("Calling Get response action");
			addprevresponse = true;
			LOG.info("Calling Get response action");
			fieldlocation = actionCommand.getObjectid1();
			tagname = paramValue;
			String savaResponse2 = getSavaResponse();
			if (savaResponse2 != null) {
				actionCommand.setComment("get Response");
				actionCommand.setResult(true);
				actionCommand.setDetailMsgs("Fetching saved reponse value :" + savaResponse2);
			} else {
				actionCommand.setComment("No Response");
				actionCommand.setResult(false);
				actionCommand.setDetailMsgs("Unable to fetch savedreponse value ");
			}
		} else if ("outputParam".equalsIgnoreCase(actionCommand.getAction())) {
			outParam = paramValue;
			if (responsemsg.contains(outParam)) {
				LOG.info(" Response matched ");
				actionCommand.setComment("Response matched");
				actionCommand.setResult(true);
				actionCommand.setDetailMsgs("Output parameter " + outParam + "and response " + responsemsg + "matched");
			} else {
				actionCommand.setComment("Response mismatched");
				actionCommand.setResult(false);
				actionCommand.setDetailMsgs("Output parameter " + outParam + "and response " + responsemsg + "mismatched");
				LOG.info("Response mismatched");
			}
		} else {
			actionCommand.setComment("Invalid action");
			actionCommand.setResult(false);
			actionCommand.setDetailMsgs("Invalid action.Provide valid action");
			LOG.info("Invalid Action");
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("passValue(ActionCommand, String) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Calling wsdl.
	 * 
	 * @param actionCommand
	 *            the action command
	 */
	@SuppressWarnings("rawtypes")
	public void callingWsdl(ActionCommand actionCommand) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("callingWsdl(ActionCommand) - start"); //$NON-NLS-1$
		}

		String emptyrequest = null;

		try {
			LOG.info("Inside calling Wsdl");
			WsdlProject project = new WsdlProject();
			project.isOpen();

			WsdlInterface[] wsdls = WsdlImporter.importWsdl(project, wsdl);
			WsdlInterface wsdl = wsdls[0];
			WsdlOperation op = null;
			LOG.info("Getting list of operations");
			for (com.eviware.soapui.model.iface.Operation operation1 : wsdl.getOperationList()) {
				LOG.info("Inside opertaiton list");
				op = (WsdlOperation) operation1;

				if (op.getName().equalsIgnoreCase(oper)) {
					LOG.info("Checkng the opration name ");
					emptyrequest = op.createRequest(true);
					break;
				}
			}
			LOG.info("inside request before spliting");
			StringBuffer str = splitRequest(emptyrequest);
			requestvalues = str.toString();
			LOG.info("Inside request after spliting");
			if (addprevresponse) {
				getResponse();
				addprevresponse = false;
			}
			LOG.info("WS Req :" + requestvalues);
			LOG.info("REQUEST  : " + requestvalues);
			WsdlRequest request = op.addNewRequest("AuthRequest1");
			request.setRequestContent(requestvalues);
			actionCommand.setRequest(requestvalues);
			WsdlSubmit submit = (WsdlSubmit) request.submit(new WsdlSubmitContext(request), false);

			com.eviware.soapui.model.iface.Response response = submit.getResponse();

			responsemsg = response.getContentAsString();
			LOG.info(responsemsg);
			actionCommand.setComment("Response generated");
			actionCommand.setResult(true);
			actionCommand.setDetailMsgs("SOAP Response " + responsemsg);
			project.release();
			actionCommand.setResponse(responsemsg);
			LOG.info("Resceived SOAP response as : " + responsemsg + " for SOAP request : " + requestvalues);

		} catch (java.lang.Exception e) {
			actionCommand.setComment("No response");
			actionCommand.setResult(false);
			actionCommand.setDetailMsgs("Response has not generated");
			LOG.error("Soap ui not started", e);
			LOG.error("Exception " + e.getMessage(), e);

		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("callingWsdl(ActionCommand) - end"); //$NON-NLS-1$
		}
	}

}
