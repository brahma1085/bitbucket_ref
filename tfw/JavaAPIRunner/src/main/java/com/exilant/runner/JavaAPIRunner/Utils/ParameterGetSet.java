package com.exilant.runner.JavaAPIRunner.Utils;


/**
 * The Class ParameterGetSet.
 */
public class ParameterGetSet {
	
	/** The para type. */
	Class[] paraType;
	
	/** The Arg val. */
	Object[] ArgVal;
	
	/** The Values. */
	Object[] Values;
	
	
	

	/**
	 * Gets the values.
	 *
	 * @return the values
	 */
	public Object[] getValues() {
		return Values;
	}

	/**
	 * Sets the values.
	 *
	 * @param values the new values
	 */
	public void setValues(Object[] values) {
		Values = values;
	}

	/**
	 * Sets the param type.
	 *
	 * @param paraType the new param type
	 */
	public void setParamType(Class[] paraType) {
		this.paraType = paraType;
	}

	/**
	 * Gets the param type.
	 *
	 * @return the param type
	 */
	public Class[] getParamType() {
		return paraType;
	}
	
	/**
	 * Sets the param arg val.
	 *
	 * @param ArgVal the new param arg val
	 */
	public void setParamArgVal(Object[] ArgVal) {
		this.ArgVal = ArgVal;
	}

	/**
	 * Gets the param arg val.
	 *
	 * @return the param arg val
	 */
	public Object[] getParamArgVal() {
		return ArgVal;
	}
	
	
	
}
