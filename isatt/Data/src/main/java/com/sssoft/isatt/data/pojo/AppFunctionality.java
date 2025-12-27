package com.sssoft.isatt.data.pojo;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.sssoft.isatt.data.pojo.input.TestSuite;

/**
 * The Class AppFunctionality.
 *
 * @author mohammedfirdos
 */
public class AppFunctionality implements Serializable {

	/** AppFunctionality Entity. */
	private static final long serialVersionUID = 1L;

	/** The functional id. */
	private int functionalID;
	
	/** The functional name. */
	private String functionalName;
	
	/** The description. */
	private String description;
	
	/** The app id. */
	private int appID;
	
	/** The created by. */
	private String createdBy;
	
	/** The created date time. */
	private Date createdDateTime;
	
	/** The updated by. */
	private String updatedBy;
	
	/** The updated date time. */
	private Date updatedDateTime;
	
	/** The app feature. */
	private List<AppFeature> appFeature = new ArrayList<AppFeature>(0);
	
	/** The test suite. */
	private List<TestSuite> testSuite = new ArrayList<TestSuite>(0);
	
	/** The total count. */
	private int totalCount;
	
	/** The pass count. */
	private int passCount;
	
	/** The fail count. */
	private int failCount;
	
	/** The skip count. */
	private int skipCount;

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
	 * @param totalCount the new total count
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
	 * @param passCount the new pass count
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
	 * @param failCount the new fail count
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
	 * @param skipCount the new skip count
	 */
	public void setSkipCount(int skipCount) {
		this.skipCount = skipCount;
	}

	/**
	 * Gets the functional id.
	 *
	 * @return the functional id
	 */
	public int getFunctionalID() {
		return functionalID;
	}

	/**
	 * Sets the functional id.
	 *
	 * @param functionalID the new functional id
	 */
	public void setFunctionalID(int functionalID) {
		this.functionalID = functionalID;
	}

	/**
	 * Gets the functional name.
	 *
	 * @return the functional name
	 */
	public String getFunctionalName() {
		return functionalName;
	}

	/**
	 * Sets the functional name.
	 *
	 * @param functionalName the new functional name
	 */
	public void setFunctionalName(String functionalName) {
		this.functionalName = functionalName;
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
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

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
	 * @param appID the new app id
	 */
	public void setAppID(int appID) {
		this.appID = appID;
	}

	/**
	 * Gets the created by.
	 *
	 * @return the created by
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets the created by.
	 *
	 * @param createdBy the new created by
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Gets the created date time.
	 *
	 * @return the created date time
	 */
	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	/**
	 * Sets the created date time.
	 *
	 * @param createdDateTime the new created date time
	 */
	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	/**
	 * Gets the updated by.
	 *
	 * @return the updated by
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * Sets the updated by.
	 *
	 * @param updatedBy the new updated by
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * Gets the updated date time.
	 *
	 * @return the updated date time
	 */
	public Date getUpdatedDateTime() {
		return updatedDateTime;
	}

	/**
	 * Sets the updated date time.
	 *
	 * @param updatedDateTime the new updated date time
	 */
	public void setUpdatedDateTime(Date updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	/**
	 * Gets the app feature.
	 *
	 * @return the appFeature
	 */
	public List<AppFeature> getAppFeature() {
		return appFeature;
	}

	/**
	 * Sets the app feature.
	 *
	 * @param appFeature the appFeature to set
	 */
	public void setAppFeature(List<AppFeature> appFeature) {
		this.appFeature = appFeature;
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
	 * @param testSuite the testSuite to set
	 */
	public void setTestSuite(List<TestSuite> testSuite) {
		this.testSuite = testSuite;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AppFunctionality [FunctionalID=" + functionalID + ", FunctionalName=" + functionalName + ", Description=" + description + ", AppID="
				+ appID + "]";
	}

}
