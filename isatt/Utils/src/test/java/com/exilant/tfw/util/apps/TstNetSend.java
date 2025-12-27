package com.exilant.tfw.util.apps;

import java.util.Map;

import org.apache.log4j.Logger;

import com.sssoft.isatt.utils.util.http.HttpData;
import com.sssoft.isatt.utils.util.http.NetSend;

/**
 * The Class TstNetSend.
 */
public class TstNetSend {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(TstNetSend.class);

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		int compreOptions = 0;
		String url = "http://sel2in.com/up6.php";
		Map<String, Object> params = null;
		HttpData hDat = new HttpData(url, compreOptions, params);
		String dat = "k=a";
		try {
			NetSend.send(hDat, dat);
		} catch (Exception e) {
			LOG.error("", e);
		}
	}
}
