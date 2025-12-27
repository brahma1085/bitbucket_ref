package com.sssoft.isatt.data.pojo;

import java.io.File;
import java.io.Serializable;

/**
 * The Class ExcelImport.
 *
 * @author anonymous
 * 
 * ExcelImport Entity.
 */
public class ExcelImport implements Serializable {

	/** Default Serial Version Id. */
	private static final long serialVersionUID = 1L;
	
	/** The application name. */
	private String applicationName;
	
	/** The test plan name. */
	private String testPlanName;
	
	/** The test suite name. */
	private String testSuiteName;
	
	/** The test scenario name. */
	private String testScenarioName;
	
	/** The function name. */
	private String functionName;
	
	/** The feature name. */
	private String featureName;
	
	/** The data set description. */
	private String dataSetDescription;
	
	/** The excel file. */
	private File excelFile;

	/**
	 * Gets the application name.
	 *
	 * @return the application name
	 */
	public String getApplicationName() {
		return applicationName;
	}

	/**
	 * Sets the application name.
	 *
	 * @param applicationName the new application name
	 */
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	/**
	 * Gets the test plan name.
	 *
	 * @return the test plan name
	 */
	public String getTestPlanName() {
		return testPlanName;
	}

	/**
	 * Sets the test plan name.
	 *
	 * @param testPlanName the new test plan name
	 */
	public void setTestPlanName(String testPlanName) {
		this.testPlanName = testPlanName;
	}

	/**
	 * Gets the test suite name.
	 *
	 * @return the test suite name
	 */
	public String getTestSuiteName() {
		return testSuiteName;
	}

	/**
	 * Sets the test suite name.
	 *
	 * @param testSuiteName the new test suite name
	 */
	public void setTestSuiteName(String testSuiteName) {
		this.testSuiteName = testSuiteName;
	}

	/**
	 * Gets the test scenario name.
	 *
	 * @return the test scenario name
	 */
	public String getTestScenarioName() {
		return testScenarioName;
	}

	/**
	 * Sets the test scenario name.
	 *
	 * @param testScenarioName the new test scenario name
	 */
	public void setTestScenarioName(String testScenarioName) {
		this.testScenarioName = testScenarioName;
	}

	/**
	 * Gets the function name.
	 *
	 * @return the function name
	 */
	public String getFunctionName() {
		return functionName;
	}

	/**
	 * Sets the function name.
	 *
	 * @param functionName the new function name
	 */
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	/**
	 * Gets the feature name.
	 *
	 * @return the feature name
	 */
	public String getFeatureName() {
		return featureName;
	}

	/**
	 * Sets the feature name.
	 *
	 * @param featureName the new feature name
	 */
	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}

	/**
	 * Gets the data set description.
	 *
	 * @return the data set description
	 */
	public String getDataSetDescription() {
		return dataSetDescription;
	}

	/**
	 * Sets the data set description.
	 *
	 * @param dataSetDescription the new data set description
	 */
	public void setDataSetDescription(String dataSetDescription) {
		this.dataSetDescription = dataSetDescription;
	}

	/**
	 * Gets the excel file.
	 *
	 * @return the excel file
	 */
	public File getExcelFile() {
		return excelFile;
	}

	/**
	 * Sets the excel file.
	 *
	 * @param excelFile the new excel file
	 */
	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ExcelImport [applicationName=" + applicationName + ", testPlanName=" + testPlanName + ", testSuiteName=" + testSuiteName
				+ ", testScenarioName=" + testScenarioName + ", functionName=" + functionName + ", featureName=" + featureName + ", dataSetDescription="
				+ dataSetDescription + ", excelFile=" + excelFile + "]";
	}

}
