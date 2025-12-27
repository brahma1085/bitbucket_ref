package com.sssoft.isatt.ui.utils;

import java.util.List;

/**
 * The Class ClassParameters.
 */
public class ClassParameters {
	
	/** The class name. */
	String className;
	
	/** The method params. */
	List<MethodParameters> methodParams;

	/**
	 * Gets the method params.
	 *
	 * @return the method params
	 */
	public List<MethodParameters> getMethodParams() {
		return methodParams;
	}

	/**
	 * Sets the method params.
	 *
	 * @param methodParams the new method params
	 */
	public void setMethodParams(List<MethodParameters> methodParams) {
		this.methodParams = methodParams;
	}

	/**
	 * Gets the class name.
	 *
	 * @return the class name
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * Sets the class name.
	 *
	 * @param className the new class name
	 */
	public void setClassName(String className) {
		this.className = className;
	}

}