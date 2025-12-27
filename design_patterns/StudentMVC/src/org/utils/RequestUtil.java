package org.utils;

public class RequestUtil {
	public static Object createObject(String className) throws Exception {
		return Class.forName(className).newInstance();
	}
}
