package com.exilant.tfw.excelreader;

/**
 * The Enum ColumnNames.
 */
public enum ColumnNames{
	
	/** The sl no. */
	SL_NO("Sl No"),
	
	/** The test case name. */
	TEST_CASE_NAME("Test Case name"),
	
	/** The test case classfication tags. */
	TEST_CASE_CLASSFICATION_TAGS ("TEST CASE CLASSIFCATION TAGS"),
	
	/** The test case scenario. */
	TEST_CASE_SCENARIO ("Test Case Scenario"),
	
	/** The test case title. */
	TEST_CASE_TITLE ("Test Case Title"),
	
	/** The postive or negative. */
	POSTIVE_OR_NEGATIVE ("Positive Or Negative"),
	
	/** The runner type. */
	RUNNER_TYPE ("RUNNER TYPE"),
	
	/** The step type. */
	STEP_TYPE("Step Type"),
	
	/** The action. */
	ACTION ("Action"),
	
	/** The execute. */
	EXECUTE ("Execute/Skip TEST CASE"),
	
	/** The dependency. */
	DEPENDENCY ("Dependency"),
	
	/** The user comments. */
	USER_COMMENTS ("Comments"),
	
	/** The param group object. */
	PARAM_GROUP_OBJECT ("Param group Object"),
	
	/** The step param. */
	STEP_PARAM ("Step Param"),
	
	/** The value. */
	VALUE ("value(TestParam data)"),
	
	/** The expected result. */
	EXPECTED_RESULT ("Expected  Result"),
	
	/** The actual result. */
	ACTUAL_RESULT ("Actual Result"),
	
	/** The result. */
	RESULT ("Result"),
	
	/** The runner comments. */
	RUNNER_COMMENTS("Runner Comments"),
	
	/** The date format. */
	DATE_FORMAT("Date Format");
	
	/** The get text value. */
	private String getTextValue;

	/**
	 * Instantiates a new key val.
	 * 
	 * @param text
	 *            the text
	 */
	ColumnNames(String text) {

		this.getTextValue = text;
	}

	/**
	 * Gets the text.
	 * 
	 * @return the text
	 */
	public String getText() {

		return this.getTextValue;
	}
}