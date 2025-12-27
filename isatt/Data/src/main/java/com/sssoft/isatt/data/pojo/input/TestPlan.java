package com.sssoft.isatt.data.pojo.input;

import java.io.File;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.sssoft.isatt.data.pojo.AppFunctionality;
import com.sssoft.isatt.data.pojo.Application;
import com.sssoft.isatt.data.pojo.Scheduler;
import com.sssoft.isatt.data.pojo.SchedulerRunDetails;
import com.sssoft.isatt.data.pojo.output.TestPlanResult;

/**
 * The Class TestPlan.
 *
 * @author mohammedfirdos
 */
public class TestPlan implements Serializable {

	/** TestPlan Entity. */
	private static final long serialVersionUID = 1L;

	/** The test plan id. */
	private int testPlanID;
	
	/** The plan name. */
	private String planName;
	
	/** The description. */
	private String description;
	
	/** The app id. */
	private int appID;
	
	/** The pre condition group id. */
	private int preConditionGroupID;
	
	/** The post condition group id. */
	private int postConditionGroupID;
	
	/** The created by. */
	private String createdBy;
	
	/** The created date time. */
	private Date createdDateTime;
	
	/** The updated by. */
	private String updatedBy;
	
	/** The updated date time. */
	private Date updatedDateTime;
	
	/** The scheduler. */
	private List<Scheduler> scheduler = new ArrayList<Scheduler>(0);
	
	/** The scheduler run details. */
	private List<SchedulerRunDetails> schedulerRunDetails = new ArrayList<SchedulerRunDetails>(0);
	
	/** The test plan result. */
	private List<TestPlanResult> testPlanResult = new ArrayList<TestPlanResult>(0);
	
	/** The test suite. */
	private List<TestSuite> testSuite = new ArrayList<TestSuite>(0);
	
	/** The test debug info file. */
	private File testDebugInfoFile;
	
	/** The plan suite mappings. */
	private List<PlanSuiteMapping> planSuiteMappings = new ArrayList<PlanSuiteMapping>(0);

	/**
	 * Gets the plan suite mappings.
	 *
	 * @return the plan suite mappings
	 */
	public List<PlanSuiteMapping> getPlanSuiteMappings() {
		return planSuiteMappings;
	}

	/**
	 * Sets the plan suite mappings.
	 *
	 * @param planSuiteMappings the new plan suite mappings
	 */
	public void setPlanSuiteMappings(List<PlanSuiteMapping> planSuiteMappings) {
		this.planSuiteMappings = planSuiteMappings;
	}

	/** The functional list. */
	private List<AppFunctionality> functionalList = new ArrayList<AppFunctionality>(0);;

	/**
	 * Gets the functional list.
	 *
	 * @return the functional list
	 */
	public List<AppFunctionality> getFunctionalList() {
		return functionalList;
	}

	/**
	 * Sets the functional list.
	 *
	 * @param functionalList the new functional list
	 */
	public void setFunctionalList(List<AppFunctionality> functionalList) {
		this.functionalList = functionalList;
	}

	/**
	 * Gets the test debug info file.
	 *
	 * @return the test debug info file
	 */
	public File getTestDebugInfoFile() {
		return testDebugInfoFile;
	}

	/**
	 * Sets the test debug info file.
	 *
	 * @param testDebugInfoFile the new test debug info file
	 */
	public void setTestDebugInfoFile(File testDebugInfoFile) {
		this.testDebugInfoFile = testDebugInfoFile;
	}

	/** The application. */
	private Application application = new Application();

	/**
	 * Gets the application.
	 *
	 * @return the application
	 */
	public Application getApplication() {
		return application;
	}

	/**
	 * Sets the application.
	 *
	 * @param application the new application
	 */
	public void setApplication(Application application) {
		this.application = application;
	}

	/**
	 * Gets the test plan id.
	 *
	 * @return the testPlanID
	 */
	public int getTestPlanID() {
		return testPlanID;
	}

	/**
	 * Sets the test plan id.
	 *
	 * @param testPlanID the testPlanID to set
	 */
	public void setTestPlanID(int testPlanID) {
		this.testPlanID = testPlanID;
	}

	/**
	 * Gets the plan name.
	 *
	 * @return the planName
	 */
	public String getPlanName() {
		return planName;
	}

	/**
	 * Sets the plan name.
	 *
	 * @param planName the planName to set
	 */
	public void setPlanName(String planName) {
		this.planName = planName;
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
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the app id.
	 *
	 * @return the appID
	 */
	public int getAppID() {
		return appID;
	}

	/**
	 * Sets the app id.
	 *
	 * @param appID the appID to set
	 */
	public void setAppID(int appID) {
		this.appID = appID;
	}

	/**
	 * Gets the pre condition group id.
	 *
	 * @return the preConditionGroupID
	 */
	public int getPreConditionGroupID() {
		return preConditionGroupID;
	}

	/**
	 * Sets the pre condition group id.
	 *
	 * @param preConditionGroupID the preConditionGroupID to set
	 */
	public void setPreConditionGroupID(int preConditionGroupID) {
		this.preConditionGroupID = preConditionGroupID;
	}

	/**
	 * Gets the post condition group id.
	 *
	 * @return the postConditionGroupID
	 */
	public int getPostConditionGroupID() {
		return postConditionGroupID;
	}

	/**
	 * Sets the post condition group id.
	 *
	 * @param postConditionGroupID the postConditionGroupID to set
	 */
	public void setPostConditionGroupID(int postConditionGroupID) {
		this.postConditionGroupID = postConditionGroupID;
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
	 * @param createdBy the createdBy to set
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
	 * @param createdDateTime the createdDateTime to set
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
	 * @param updatedBy the updatedBy to set
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
	 * @param updatedDateTime the updatedDateTime to set
	 */
	public void setUpdatedDateTime(Date updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	/**
	 * Gets the scheduler.
	 *
	 * @return the scheduler
	 */
	public List<Scheduler> getScheduler() {
		return scheduler;
	}

	/**
	 * Sets the scheduler.
	 *
	 * @param scheduler the scheduler to set
	 */
	public void setScheduler(List<Scheduler> scheduler) {
		this.scheduler = scheduler;
	}

	/**
	 * Gets the scheduler run details.
	 *
	 * @return the schedulerRunDetails
	 */
	public List<SchedulerRunDetails> getSchedulerRunDetails() {
		return schedulerRunDetails;
	}

	/**
	 * Sets the scheduler run details.
	 *
	 * @param schedulerRunDetails the schedulerRunDetails to set
	 */
	public void setSchedulerRunDetails(List<SchedulerRunDetails> schedulerRunDetails) {
		this.schedulerRunDetails = schedulerRunDetails;
	}

	/**
	 * Gets the test plan result.
	 *
	 * @return the testPlanResult
	 */
	public List<TestPlanResult> getTestPlanResult() {
		return testPlanResult;
	}

	/**
	 * Sets the test plan result.
	 *
	 * @param testPlanResult the testPlanResult to set
	 */
	public void setTestPlanResult(List<TestPlanResult> testPlanResult) {
		this.testPlanResult = testPlanResult;
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
		return "TestPlan [TestPlanID=" + testPlanID + ", PlanName=" + planName + ", Description=" + description + ", AppID=" + appID
				+ ", PreConditionGroupID=" + preConditionGroupID + ", PostConditionGroupID=" + postConditionGroupID + "]";
	}

}