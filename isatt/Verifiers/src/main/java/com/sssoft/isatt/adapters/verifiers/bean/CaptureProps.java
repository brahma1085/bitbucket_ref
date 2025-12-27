package com.sssoft.isatt.adapters.verifiers.bean;

import java.io.Serializable;

/**
 * The Class CaptureProps.
 *
 * @author brahmareddykapa
 * 
 * This bean captures all the properties from the verifier property
 * files
 */
public class CaptureProps implements Serializable {

	/** Default serial version id. */
	private static final long serialVersionUID = 1L;

	/** The action name. */
	private String actionName;
	
	/** The action type. */
	private String actionType;
	
	/** The param format. */
	private String paramFormat;
	
	/** The response type. */
	private String responseType;
	
	/** The is array. */
	private int isArray;
	
	/** The array count. */
	private String arrayCount;
	
	/** The ele array count. */
	private int eleArrayCount;
	
	/** The array ins type. */
	private String arrayInsType;
	
	/** The arrat ins param. */
	private String arratInsParam;
	
	/** The array ins count. */
	private int arrayInsCount;
	
	/** The x path. */
	private String xPath;
	
	/** The x path missed. */
	private String xPathMissed;
	
	/** The array goto. */
	private int arrayGoto;
	
	/** The req xpath. */
	private String reqXpath;
	
	/** The comp request. */
	private String compRequest;
	
	/** The comp response. */
	private String compResponse;

	/**
	 * Gets the action name.
	 *
	 * @return the action name
	 */
	public String getActionName() {
		return actionName;
	}

	/**
	 * Sets the action name.
	 *
	 * @param actionName the new action name
	 */
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	/**
	 * Gets the action type.
	 *
	 * @return the action type
	 */
	public String getActionType() {
		return actionType;
	}

	/**
	 * Sets the action type.
	 *
	 * @param actionType the new action type
	 */
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	/**
	 * Gets the param format.
	 *
	 * @return the param format
	 */
	public String getParamFormat() {
		return paramFormat;
	}

	/**
	 * Sets the param format.
	 *
	 * @param paramFormat the new param format
	 */
	public void setParamFormat(String paramFormat) {
		this.paramFormat = paramFormat;
	}

	/**
	 * Gets the response type.
	 *
	 * @return the response type
	 */
	public String getResponseType() {
		return responseType;
	}

	/**
	 * Sets the response type.
	 *
	 * @param responseType the new response type
	 */
	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	/**
	 * Gets the checks if is array.
	 *
	 * @return the checks if is array
	 */
	public int getIsArray() {
		return isArray;
	}

	/**
	 * Sets the checks if is array.
	 *
	 * @param isArray the new checks if is array
	 */
	public void setIsArray(int isArray) {
		this.isArray = isArray;
	}

	/**
	 * Gets the array count.
	 *
	 * @return the array count
	 */
	public String getArrayCount() {
		return arrayCount;
	}

	/**
	 * Sets the array count.
	 *
	 * @param arrayCount the new array count
	 */
	public void setArrayCount(String arrayCount) {
		this.arrayCount = arrayCount;
	}

	/**
	 * Gets the x path.
	 *
	 * @return the x path
	 */
	public String getxPath() {
		return xPath;
	}

	/**
	 * Sets the x path.
	 *
	 * @param xPath the new x path
	 */
	public void setxPath(String xPath) {
		this.xPath = xPath;
	}

	/**
	 * Gets the req xpath.
	 *
	 * @return the req xpath
	 */
	public String getReqXpath() {
		return reqXpath;
	}

	/**
	 * Sets the req xpath.
	 *
	 * @param reqXpath the new req xpath
	 */
	public void setReqXpath(String reqXpath) {
		this.reqXpath = reqXpath;
	}

	/**
	 * Gets the x path missed.
	 *
	 * @return the x path missed
	 */
	public String getxPathMissed() {
		return xPathMissed;
	}

	/**
	 * Sets the x path missed.
	 *
	 * @param xPathMissed the new x path missed
	 */
	public void setxPathMissed(String xPathMissed) {
		this.xPathMissed = xPathMissed;
	}

	/**
	 * Gets the ele array count.
	 *
	 * @return the ele array count
	 */
	public int getEleArrayCount() {
		return eleArrayCount;
	}

	/**
	 * Sets the ele array count.
	 *
	 * @param eleArrayCount the new ele array count
	 */
	public void setEleArrayCount(int eleArrayCount) {
		this.eleArrayCount = eleArrayCount;
	}

	/**
	 * Gets the array ins type.
	 *
	 * @return the array ins type
	 */
	public String getArrayInsType() {
		return arrayInsType;
	}

	/**
	 * Sets the array ins type.
	 *
	 * @param arrayInsType the new array ins type
	 */
	public void setArrayInsType(String arrayInsType) {
		this.arrayInsType = arrayInsType;
	}

	/**
	 * Gets the arrat ins param.
	 *
	 * @return the arrat ins param
	 */
	public String getArratInsParam() {
		return arratInsParam;
	}

	/**
	 * Sets the arrat ins param.
	 *
	 * @param arratInsParam the new arrat ins param
	 */
	public void setArratInsParam(String arratInsParam) {
		this.arratInsParam = arratInsParam;
	}

	/**
	 * Gets the array ins count.
	 *
	 * @return the array ins count
	 */
	public int getArrayInsCount() {
		return arrayInsCount;
	}

	/**
	 * Sets the array ins count.
	 *
	 * @param arrayInsCount the new array ins count
	 */
	public void setArrayInsCount(int arrayInsCount) {
		this.arrayInsCount = arrayInsCount;
	}

	/**
	 * Gets the array goto.
	 *
	 * @return the array goto
	 */
	public int getArrayGoto() {
		return arrayGoto;
	}

	/**
	 * Sets the array goto.
	 *
	 * @param arrayGoto the new array goto
	 */
	public void setArrayGoto(int arrayGoto) {
		this.arrayGoto = arrayGoto;
	}

	/**
	 * Gets the comp request.
	 *
	 * @return the comp request
	 */
	public String getCompRequest() {
		return compRequest;
	}

	/**
	 * Sets the comp request.
	 *
	 * @param compRequest the new comp request
	 */
	public void setCompRequest(String compRequest) {
		this.compRequest = compRequest;
	}

	/**
	 * Gets the comp response.
	 *
	 * @return the comp response
	 */
	public String getCompResponse() {
		return compResponse;
	}

	/**
	 * Sets the comp response.
	 *
	 * @param compResponse the new comp response
	 */
	public void setCompResponse(String compResponse) {
		this.compResponse = compResponse;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CaptureProps [" + actionName + ", " + actionType + ", " + paramFormat + ", " + responseType + ", " + isArray + ", " + arrayCount + ", "
				+ eleArrayCount + ", " + arrayInsType + ", " + arratInsParam + ", " + arrayInsCount + ", " + xPath + ", " + xPathMissed + "]";
	}

}
