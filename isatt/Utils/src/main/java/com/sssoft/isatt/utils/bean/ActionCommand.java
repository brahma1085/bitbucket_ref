package com.sssoft.isatt.utils.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class ActionCommand.
 */
public class ActionCommand {

	private String objectid1 = "";

	/** The objectid2. */
	private String objectid2 = "";

	/** The condition. */
	private String condition = "";

	/** The result. */
	private boolean result = false;

	/** The comment. */
	private String comment = "";

	/** The snap shot. */
	private String snapShot = " ";

	/** The date format. */
	private String dateFormat = "";

	/** The action. */
	private String action = "";

	/** The detail msgs. */
	private String detailMsgs = "";

	/** The response. */
	private String response = "";

	/** The request. */
	private String request = "";

	/** The step param. */
	private String stepParam = "";

	/**
	 * Gets the response.
	 * 
	 * @return the response
	 */
	public String getResponse() {
		return response;
	}

	/**
	 * Sets the response.
	 * 
	 * @param response
	 *            the new response
	 */
	public void setResponse(String response) {
		this.response = response;
	}

	/**
	 * Gets the request.
	 * 
	 * @return the request
	 */
	public String getRequest() {
		return request;
	}

	/**
	 * Sets the request.
	 * 
	 * @param request
	 *            the new request
	 */
	public void setRequest(String request) {
		this.request = request;
	}

	/**
	 * Gets the req map.
	 * 
	 * @return the req map
	 */
	public Map<String, String> getReqMap() {
		return reqMap;
	}

	/**
	 * Sets the req map.
	 * 
	 * @param reqMap
	 *            the req map
	 */
	public void setReqMap(Map<String, String> reqMap) {
		this.reqMap = reqMap;
	}

	/**
	 * Gets the save result.
	 * 
	 * @return the save result
	 */
	public Map<String, String> getSaveResult() {
		return saveResult;
	}

	/**
	 * Sets the save result.
	 * 
	 * @param saveResult
	 *            the save result
	 */
	public void setSaveResult(Map<String, String> saveResult) {
		this.saveResult = saveResult;
	}

	/** The req map. */
	private Map<String, String> reqMap = new HashMap<String, String>();

	/** The save result. */
	private Map<String, String> saveResult = new HashMap<String, String>();

	/**
	 * Gets the action.
	 * 
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * Sets the action.
	 * 
	 * @param action
	 *            the new action
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * Gets the date format.
	 * 
	 * @return the date format
	 */
	public String getDateFormat() {
		return dateFormat;
	}

	/**
	 * Sets the date format.
	 * 
	 * @param dateForamt
	 *            the new date format
	 */
	public void setDateFormat(String dateForamt) {
		this.dateFormat = dateForamt;
	}

	/**
	 * Gets the snap shot.
	 * 
	 * @return the snap shot
	 */
	public String getSnapShot() {
		return snapShot;
	}

	/**
	 * Sets the snap shot.
	 * 
	 * @param snapShot
	 *            the new snap shot
	 */
	public void setSnapShot(String snapShot) {
		if (snapShot != null) {
			this.snapShot = snapShot;
		}
	}

	/**
	 * Gets the objectid1.
	 * 
	 * @return the objectid1
	 */
	public String getObjectid1() {
		return objectid1;
	}

	/**
	 * Sets the objectid1.
	 * 
	 * @param objectid1
	 *            the new objectid1
	 */
	public void setObjectid1(String objectid1) {
		if (objectid1 != null) {
			this.objectid1 = objectid1;
		}
	}

	/**
	 * Gets the condition.
	 * 
	 * @return the condition
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * Sets the condition.
	 * 
	 * @param condition
	 *            the new condition
	 */
	public void setCondition(String condition) {
		if (condition != null) {
			this.condition = condition;
		}
	}

	/**
	 * Checks if is result.
	 * 
	 * @return true, if is result
	 */
	public boolean isResult() {
		return result;
	}

	/**
	 * Sets the result.
	 * 
	 * @param result
	 *            the new result
	 */
	public void setResult(boolean result) {
		this.result = result;
	}

	/**
	 * Gets the comment.
	 * 
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Sets the comment.
	 * 
	 * @param comment
	 *            the new comment
	 */
	public void setComment(String comment) {
		if (comment != null) {
			this.comment = comment;
		}
	}

	/**
	 * Gets the detail msgs.
	 * 
	 * @return the detail msgs
	 */
	public String getDetailMsgs() {
		return detailMsgs;
	}

	/**
	 * Sets the detail msgs.
	 * 
	 * @param detailMsgs
	 *            the new detail msgs
	 */
	public void setDetailMsgs(String detailMsgs) {
		this.detailMsgs = detailMsgs;
	}

	/**
	 * Gets the objectid2.
	 * 
	 * @return the objectid2
	 */
	public String getObjectid2() {
		return objectid2;
	}

	/**
	 * Sets the objectid2.
	 * 
	 * @param objectid2
	 *            the new objectid2
	 */
	public void setObjectid2(String objectid2) {
		if (objectid2 != null) {
			this.objectid2 = objectid2;
		}
	}

	/**
	 * Gets the step param.
	 * 
	 * @return the step param
	 */
	public String getStepParam() {
		return stepParam;
	}

	/**
	 * Sets the step param.
	 * 
	 * @param stepParam
	 *            the new step param
	 */
	public void setStepParam(String stepParam) {
		this.stepParam = stepParam;
	}

}
