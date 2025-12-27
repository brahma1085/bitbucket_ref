package com.exilant.tfw.runner.mobile;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import com.gorillalogic.monkeytalk.java.MonkeyTalkDriver;
import com.gorillalogic.monkeytalk.java.api.Application;

/**
 * The Class Sample.
 */
public class Sample {
	
	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(Sample.class);

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("main(String[]) - start"); //$NON-NLS-1$
		}

		MonkeyTalkDriver driver = new MonkeyTalkDriver(new File("."), "iOS");
		Application app = driver.app();
		String component = "button";
		String action = "touchDown";
		Method[] methods = Application.class.getDeclaredMethods();
		String[] methodNames = new String[methods.length];
		int i = 0;
		for (Method method : methods) {
			methodNames[i] = method.getName();
			i++;
		}
		if (ArrayUtils.contains(methodNames, component)) {
			Method actionMethd = app.getClass().getMethod(component, String.class);
			Object object = actionMethd.invoke(app, component);
			Method[] methods1 = object.getClass().getDeclaredMethods();
			String[] methodNames1 = new String[methods1.length];
			int i1 = 0;
			for (Method method : methods1) {
				methodNames1[i1] = method.getName();
				i1++;
			}
			if (ArrayUtils.contains(methodNames1, action)) {
				if (null != args[0]) {
					Method actionMethd1 = object.getClass().getMethod(action, Map.class);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					Map<String, String> map = new HashMap<String, String>();
					Object object2 = actionMethd1.invoke(object, map);
				} else {
					Method actionMethd1 = object.getClass().getMethod(action);
					LOG.info("actionMethd1 : " + actionMethd1.getName());
					Object object2 = actionMethd1.invoke(object, null);
				}
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("main(String[]) - end"); //$NON-NLS-1$
		}
	}
}
