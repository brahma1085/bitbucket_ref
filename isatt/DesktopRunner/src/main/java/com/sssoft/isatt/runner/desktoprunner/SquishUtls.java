package com.sssoft.isatt.runner.desktoprunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.apache.log4j.Logger;

import com.sssoft.isatt.data.pojo.input.Param;
import com.sssoft.isatt.data.pojo.output.TestStepResult;

/**
 * The Class SquishUtls.
 */
public class SquishUtls {

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(SquishUtls.class);

	/**
	 * Step rslt.
	 * 
	 * @param stepResult
	 *            the step result
	 * @param relt
	 *            the relt
	 * @param summary
	 *            the summary
	 * @param detail
	 *            the detail
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
			LOG.info(sb.toString());
			stepResult.setComment(sb.toString());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("stepRslt(TestStepResult, int, String, String) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Gets the step first obj param value.
	 * 
	 * @param param
	 *            the param
	 * @return the step first obj param value
	 */
	public static String getStepFirstObjParamValue(Param param) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getStepFirstObjParamValue(Param) - start"); //$NON-NLS-1$
		}

		String v = param.getTestParamData().getParamValue();

		if (LOG.isDebugEnabled()) {
			LOG.debug("getStepFirstObjParamValue(Param) - end"); //$NON-NLS-1$
		}
		return v;
	}

	/**
	 * Step fill detail.
	 * 
	 * @param stepResult
	 *            the step result
	 * @param msg
	 *            the msg
	 * @param e
	 *            the e
	 */
	public static void stepFillDetail(TestStepResult stepResult, String msg, Throwable e) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("stepFillDetail(TestStepResult, String, Throwable) - start"); //$NON-NLS-1$
		}

		String s = stepResult.getComment();
		StringBuilder sb = new StringBuilder();
		if (msg == null) {
			msg = "";
		}
		if (s == null) {
			sb.append(msg);
		} else {
			sb.append(s);
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
		stepResult.setComment(sb.toString());

		if (LOG.isDebugEnabled()) {
			LOG.debug("stepFillDetail(TestStepResult, String, Throwable) - end"); //$NON-NLS-1$
		}
	}
}
