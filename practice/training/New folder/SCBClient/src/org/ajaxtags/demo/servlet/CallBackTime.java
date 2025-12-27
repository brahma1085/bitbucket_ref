package org.ajaxtags.demo.servlet;

import javax.servlet.http.HttpServletRequest;

import org.ajaxtags.demo.TimeDeamon;
import org.ajaxtags.server.BaseAjaxObserver;

/**
 * still working at
 * 
 * @author jens
 * 
 */
public class CallBackTime extends BaseAjaxObserver {

	public void catchClient(HttpServletRequest arg0)
			throws InterruptedException {
		TimeDeamon.get().addAndWait(this);
	}

	 

}
