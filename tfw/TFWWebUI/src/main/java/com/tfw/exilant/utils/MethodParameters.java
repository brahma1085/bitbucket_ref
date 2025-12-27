package com.tfw.exilant.utils;

import java.util.List;

/**
 * The Class MethodParameters.
 */
public class MethodParameters {
	
	/** The param name. */
	String[] paramName;
	
	/** The param types. */
	String[] paramTypes;
	
	/** The return param name. */
	String returnParamName;
	
	/** The return param type. */
	String returnParamType;
	
	/** The method name. */
	String methodName;
	
	/** The method params as object. */
	List<MethodParamsAsObject> methodParamsAsObject;

	/**
	 * Gets the method name.
	 *
	 * @return the method name
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * Gets the method params as object.
	 *
	 * @return the method params as object
	 */
	public List<MethodParamsAsObject> getMethodParamsAsObject() {
		return methodParamsAsObject;
	}

	/**
	 * Sets the method params as object.
	 *
	 * @param methodParamsAsObject the new method params as object
	 */
	public void setMethodParamsAsObject(
			List<MethodParamsAsObject> methodParamsAsObject) {
		this.methodParamsAsObject = methodParamsAsObject;
	}

	/**
	 * Sets the method name.
	 *
	 * @param methodName the new method name
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * Gets the param name.
	 *
	 * @return the param name
	 */
	public String[] getParamName() {
		return paramName;
	}

	/**
	 * Sets the param name.
	 *
	 * @param paramName the new param name
	 */
	public void setParamName(String[] paramName) {
		this.paramName = paramName;
	}

	/**
	 * Gets the param types.
	 *
	 * @return the param types
	 */
	public String[] getParamTypes() {
		return paramTypes;
	}

	/**
	 * Sets the param types.
	 *
	 * @param paramTypes the new param types
	 */
	public void setParamTypes(String[] paramTypes) {
		this.paramTypes = paramTypes;
	}

	/**
	 * Gets the return param name.
	 *
	 * @return the return param name
	 */
	public String getReturnParamName() {
		return returnParamName;
	}

	/**
	 * Sets the return param name.
	 *
	 * @param returnParamName the new return param name
	 */
	public void setReturnParamName(String returnParamName) {
		this.returnParamName = returnParamName;
	}

	/**
	 * Gets the return param type.
	 *
	 * @return the return param type
	 */
	public String getReturnParamType() {
		return returnParamType;
	}

	/**
	 * Sets the return param type.
	 *
	 * @param returnParamType the new return param type
	 */
	public void setReturnParamType(String returnParamType) {
		this.returnParamType = returnParamType;
	}

}
