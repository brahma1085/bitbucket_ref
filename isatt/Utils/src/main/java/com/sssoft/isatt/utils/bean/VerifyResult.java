package com.sssoft.isatt.utils.bean;

/**
 * The Class VerifyResult.
 */
public class VerifyResult {
	
	/** The comment. */
	private String comment;
	
	/** The result. */
	private boolean result;
	
	/** The detail msgs. */
	private String detailMsgs;

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

}
