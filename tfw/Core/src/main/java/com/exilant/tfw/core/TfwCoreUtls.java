package com.exilant.tfw.core;

import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.exilant.tfw.pojo.input.TestStep;
import com.exilant.tfw.pojo.output.TestStepResult;

/**
 * The Class TfwCoreUtls.
 */
public class TfwCoreUtls {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(TfwCoreUtls.class);

	/**
	 * Step rslt.
	 *
	 * @param stepResult the step result
	 * @param relt the relt
	 * @param summary the summary
	 * @param detail the detail
	 */
	public static void stepRslt(TestStepResult stepResult, int relt, String summary, String detail) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("stepRslt(TestStepResult, int, String, String) - start"); //$NON-NLS-1$
		}

		stepResult.setResult(relt);
		stepResult.setComment(summary);

		if (detail != null) {
			StringBuilder sb = new StringBuilder();
			String gg = stepResult.getComment();
			if (gg != null) {
				sb.append(gg).append("\n");
			}
			sb.append(detail);
			stepResult.setComment(sb.toString());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("stepRslt(TestStepResult, int, String, String) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Gets the step first obj param value.
	 *
	 * @param testStep the test step
	 * @return the step first obj param value
	 */
	public static String getStepFirstObjParamValue(TestStep testStep) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getStepFirstObjParamValue(TestStep) - start"); //$NON-NLS-1$
		}

		String v = testStep.getParamGroup().get(0).getParam().get(1).getParamName();

		if (LOG.isDebugEnabled()) {
			LOG.debug("getStepFirstObjParamValue(TestStep) - end"); //$NON-NLS-1$
		}
		return v;
	}

	/**
	 * Step fill detail.
	 *
	 * @param stepResult the step result
	 * @param comment the comment
	 * @param msg the msg
	 * @param e the e
	 */
	public static void stepFillDetail(TestStepResult stepResult,String comment, String msg, Throwable e) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("stepFillDetail(TestStepResult, String, String, Throwable) - start"); //$NON-NLS-1$
		}
		
		stepResult.setComment(comment);
		StringBuilder sb = new StringBuilder();
		if (msg == null) {
			msg = "";
		}
		if (comment == null) {
			sb.append(msg);
		} else {
			sb.append(comment);
			sb.append("\n");
			sb.append(msg);
		}
		if (e != null) {
			java.io.ByteArrayOutputStream bis = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(bis);
			LOG.warn(ps);
			String sq = new String(bis.toByteArray());
			sb.append("\nTrace:\n");
			sb.append(sq);
		}
		stepResult.setException(sb.toString());

		if (LOG.isDebugEnabled()) {
			LOG.debug("stepFillDetail(TestStepResult, String, String, Throwable) - end"); //$NON-NLS-1$
		}
	}
}
