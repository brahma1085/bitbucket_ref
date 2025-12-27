package com.exilant.tfw.util.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;

/**
 * The Class ThrdsStop.
 */
public class ThrdsStop {
	
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(ThrdsStop.class);

	/**
	 * Stop that match.
	 *
	 * @param thrdsName the thrds name
	 * @param tpe the tpe
	 * @return the list
	 */
	@SuppressWarnings("deprecation")
	public static List<String> stopThatMatch(String thrdsName, ThreadPoolExecutor tpe) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("stopThatMatch(String, ThreadPoolExecutor) - start"); //$NON-NLS-1$
		}

		List<String> result = new ArrayList<String>();
		ThreadFactory tfm = tpe.getThreadFactory();
		if (tfm instanceof TfwThreadFactory) {
			TfwThreadFactory tf = (TfwThreadFactory) tfm;
			ThreadGroup tg = tf.getGroup();
			String name = tf.getNamePrefix();
			int cnt = tg.activeCount();
			Thread[] thrds = new Thread[cnt + 50];
			int cnt2 = tg.enumerate(thrds);
			for (int i = 0; i < cnt2; i++) {
				String thNm = thrds[i].getName();
				if (thNm.startsWith(name)) {
					StringBuilder msg = new StringBuilder().append(thNm);
					try {
						thrds[i].interrupt();
						msg.append(" interrupt okay;");
					} catch (Exception e) {
						msg.append(" interrupt : " + e + ";");
						LOG.warn("thrd stop " + name + ", interrupt " + e, e);
					}
					try {
						thrds[i].stop(new RuntimeException("Pool Stop"));
						msg.append(" stop okay ");
					} catch (Exception e) {
						msg.append(" stop : " + e + ";");
						LOG.warn("thrd stop " + name + ",  stop " + e, e);
					}
					result.add(thNm.toString());
				}
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("stopThatMatch(String, ThreadPoolExecutor) - end"); //$NON-NLS-1$
		}
		return result;
	}
}
