package com.sssoft.isatt.adapters.verifiers.bean;

import java.io.Serializable;

/**
 * The Class VerifyResult.
 */
public class VerifyResult implements Serializable {

	/** Default Serial version id. */
	private static final long serialVersionUID = 1L;
	
	/** The comment. */
	private String comment;
	
	/** The result. */
	private boolean result;
	
	/** The detail msgs. */
	private String detailMsgs;
	
	/** The array count. */
	private int arrayCount;
	
	/** The array instance count. */
	private int arrayInstanceCount;
	
	/** The is format. */
	private boolean isFormat;
	
	/** The is key present. */
	private boolean isKeyPresent;
	
	/** The required result. */
	private String requiredResult;

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
	 * @param comment the new comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
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
	 * @param result the new result
	 */
	public void setResult(boolean result) {
		this.result = result;
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
	 * @param detailMsgs the new detail msgs
	 */
	public void setDetailMsgs(String detailMsgs) {
		this.detailMsgs = detailMsgs;
	}

	/**
	 * Gets the array count.
	 *
	 * @return the array count
	 */
	public int getArrayCount() {
		return arrayCount;
	}

	/**
	 * Sets the array count.
	 *
	 * @param arrayCount the new array count
	 */
	public void setArrayCount(int arrayCount) {
		this.arrayCount = arrayCount;
	}

	/**
	 * Checks if is format.
	 *
	 * @return true, if is format
	 */
	public boolean isFormat() {
		return isFormat;
	}

	/**
	 * Sets the format.
	 *
	 * @param isFormat the new format
	 */
	public void setFormat(boolean isFormat) {
		this.isFormat = isFormat;
	}

	/**
	 * Checks if is key present.
	 *
	 * @return true, if is key present
	 */
	public boolean isKeyPresent() {
		return isKeyPresent;
	}

	/**
	 * Sets the key present.
	 *
	 * @param isKeyPresent the new key present
	 */
	public void setKeyPresent(boolean isKeyPresent) {
		this.isKeyPresent = isKeyPresent;
	}

	/**
	 * Gets the required result.
	 *
	 * @return the required result
	 */
	public String getRequiredResult() {
		return requiredResult;
	}

	/**
	 * Sets the required result.
	 *
	 * @param requiredResult the new required result
	 */
	public void setRequiredResult(String requiredResult) {
		this.requiredResult = requiredResult;
	}

	/**
	 * Gets the array instance count.
	 *
	 * @return the array instance count
	 */
	public int getArrayInstanceCount() {
		return arrayInstanceCount;
	}

	/**
	 * Sets the array instance count.
	 *
	 * @param arrayInstanceCount the new array instance count
	 */
	public void setArrayInstanceCount(int arrayInstanceCount) {
		this.arrayInstanceCount = arrayInstanceCount;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "VerifyResult [detailMsgs=" + detailMsgs + ", isFormat=" + isFormat + ", requiredResult=" + requiredResult + "]";
	}

}
