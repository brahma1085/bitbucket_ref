package com.exilant.runner.JavaAPIRunner.Utils;


/**
 * The Class ParameterSize.
 */
public class ParameterSize {
	
	/** The Param type size. */
	String[] ParamTypeSize;
	
	/** The paramtr name. */
	String[] paramtrName;
	
	/** The cls. */
	Class cls;
	
	/** The obj. */
	Object obj;
	
	/**
	 * Sets the param type size.
	 *
	 * @param ParamTypeSize the new param type size
	 */
	public void setParamTypeSize(String[] ParamTypeSize) {
		this.ParamTypeSize = ParamTypeSize;
	}

	/**
	 * Gets the param type size.
	 *
	 * @return the param type size
	 */
	public String[] getParamTypeSize() {
		return ParamTypeSize;
	}
	
	/**
	 * Sets the param name size.
	 *
	 * @param paramtrName the new param name size
	 */
	public void setParamNameSize(String[] paramtrName) {
		this.paramtrName = paramtrName;
	}

	/**
	 * Gets the param name size.
	 *
	 * @return the param name size
	 */
	public String[] getParamNameSize() {
		return paramtrName;
	}
	
	/**
	 * Sets the class name.
	 *
	 * @param cls the new class name
	 */
	public void setClassName(Class cls) {
		this.cls = cls;
	}

	/**
	 * Gets the class name.
	 *
	 * @return the class name
	 */
	public Class getClassName() {
		return cls;
	}
	
	/**
	 * Sets the class object.
	 *
	 * @param obj the new class object
	 */
	public void setClassObject(Object obj) {
		this.obj = obj;
	}

	/**
	 * Gets the class object.
	 *
	 * @return the class object
	 */
	public Object getClassObject() {
		return obj;
	}
}
