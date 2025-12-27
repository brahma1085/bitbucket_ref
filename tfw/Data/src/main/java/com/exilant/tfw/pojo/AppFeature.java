package com.exilant.tfw.pojo;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.exilant.tfw.pojo.input.TestSuite;

/**
 * The Class AppFeature.
 * 
 * @author mohammedfirdos
 */
public class AppFeature implements Serializable {

	/** AppFeature Entity. */
	private static final long serialVersionUID = 1L;

	/** The feature id. */
	private int featureID;

	/** The feature name. */
	private String featureName;

	/** The description. */
	private String description;

	/** The functional id. */
	private int functionalID;

	/** The created by. */
	private String createdBy;

	/** The created date time. */
	private Date createdDateTime;

	/** The updated by. */
	private String updatedBy;

	/** The updated date time. */
	private Date updatedDateTime;

	/** The screen id. */
	private int screenID;

	/** The test suite. */
	private List<TestSuite> testSuite = new ArrayList<TestSuite>(0);

	/** The screen. */
	private List<Screen> screen = new ArrayList<Screen>(0);

	/** The total count. */
	private int totalCount;

	/** The pass count. */
	private int passCount;

	/** The fail count. */
	private int failCount;

	/** The skip count. */
	private int skipCount;

	/** The app functionality. */
	private AppFunctionality appFunctionality;

	/** The app id. */
	private int appID;

	/**
	 * Gets the app id.
	 * 
	 * @return the app id
	 */
	public int getAppID() {
		return appID;
	}

	/**
	 * Sets the app id.
	 * 
	 * @param appID
	 *            the new app id
	 */
	public void setAppID(int appID) {
		this.appID = appID;
	}

	/**
	 * Gets the app functionality.
	 * 
	 * @return the app functionality
	 */
	public AppFunctionality getAppFunctionality() {
		return appFunctionality;
	}

	/**
	 * Sets the app functionality.
	 * 
	 * @param appFunctionality
	 *            the new app functionality
	 */
	public void setAppFunctionality(AppFunctionality appFunctionality) {
		this.appFunctionality = appFunctionality;
	}

	/**
	 * Gets the total count.
	 * 
	 * @return the total count
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * Sets the total count.
	 * 
	 * @param totalCount
	 *            the new total count
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * Gets the pass count.
	 * 
	 * @return the pass count
	 */
	public int getPassCount() {
		return passCount;
	}

	/**
	 * Sets the pass count.
	 * 
	 * @param passCount
	 *            the new pass count
	 */
	public void setPassCount(int passCount) {
		this.passCount = passCount;
	}

	/**
	 * Gets the fail count.
	 * 
	 * @return the fail count
	 */
	public int getFailCount() {
		return failCount;
	}

	/**
	 * Sets the fail count.
	 * 
	 * @param failCount
	 *            the new fail count
	 */
	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}

	/**
	 * Gets the skip count.
	 * 
	 * @return the skip count
	 */
	public int getSkipCount() {
		return skipCount;
	}

	/**
	 * Sets the skip count.
	 * 
	 * @param skipCount
	 *            the new skip count
	 */
	public void setSkipCount(int skipCount) {
		this.skipCount = skipCount;
	}

	/**
	 * Gets the feature id.
	 * 
	 * @return the featureID
	 */
	public int getFeatureID() {
		return featureID;
	}

	/**
	 * Sets the feature id.
	 * 
	 * @param featureID
	 *            the featureID to set
	 */
	public void setFeatureID(int featureID) {
		this.featureID = featureID;
	}

	/**
	 * Gets the feature name.
	 * 
	 * @return the featureName
	 */
	public String getFeatureName() {
		return featureName;
	}

	/**
	 * Sets the feature name.
	 * 
	 * @param featureName
	 *            the featureName to set
	 */
	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}

	/**
	 * Gets the description.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 * 
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the functional id.
	 * 
	 * @return the functionalID
	 */
	public int getFunctionalID() {
		return functionalID;
	}

	/**
	 * Sets the functional id.
	 * 
	 * @param functionalID
	 *            the functionalID to set
	 */
	public void setFunctionalID(int functionalID) {
		this.functionalID = functionalID;
	}

	/**
	 * Gets the created by.
	 * 
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets the created by.
	 * 
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Gets the created date time.
	 * 
	 * @return the createdDateTime
	 */
	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	/**
	 * Sets the created date time.
	 * 
	 * @param createdDateTime
	 *            the createdDateTime to set
	 */
	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	/**
	 * Gets the updated by.
	 * 
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * Sets the updated by.
	 * 
	 * @param updatedBy
	 *            the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * Gets the updated date time.
	 * 
	 * @return the updatedDateTime
	 */
	public Date getUpdatedDateTime() {
		return updatedDateTime;
	}

	/**
	 * Sets the updated date time.
	 * 
	 * @param updatedDateTime
	 *            the updatedDateTime to set
	 */
	public void setUpdatedDateTime(Date updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	/**
	 * Gets the screen id.
	 * 
	 * @return the screenID
	 */
	public int getScreenID() {
		return screenID;
	}

	/**
	 * Sets the screen id.
	 * 
	 * @param screenID
	 *            the screenID to set
	 */
	public void setScreenID(int screenID) {
		this.screenID = screenID;
	}

	/**
	 * Gets the test suite.
	 * 
	 * @return the testSuite
	 */
	public List<TestSuite> getTestSuite() {
		return testSuite;
	}

	/**
	 * Sets the test suite.
	 * 
	 * @param testSuite
	 *            the testSuite to set
	 */
	public void setTestSuite(List<TestSuite> testSuite) {
		this.testSuite = testSuite;
	}

	/**
	 * Gets the screen.
	 * 
	 * @return the screen
	 */
	public List<Screen> getScreen() {
		return screen;
	}

	/**
	 * Sets the screen.
	 * 
	 * @param screen
	 *            the new screen
	 */
	public void setScreen(List<Screen> screen) {
		this.screen = screen;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AppFeature [FeatureID=" + featureID + ", FeatureName=" + featureName + ", Description=" + description + ", FunctionalID=" + functionalID
				+ ", ScreenID=" + screenID + "]";
	}

}
