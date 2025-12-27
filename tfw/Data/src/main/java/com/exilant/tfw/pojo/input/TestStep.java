package com.exilant.tfw.pojo.input;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.exilant.tfw.pojo.Actions;
import com.exilant.tfw.pojo.Runner;
import com.exilant.tfw.pojo.output.TestStepResult;

/**
 * The Class TestStep.
 *
 * @author mohammedfirdos
 */
public class TestStep implements Serializable {

	/** TestStep Entity. */
	private static final long serialVersionUID = 1L;

	/** The test step id. */
	private int testStepID;
	
	/** The step name. */
	private String stepName;
	
	/** The description. */
	private String description;
	
	/** The test step type. */
	private String testStepType;
	
	/** The action id. */
	private int actionID;
	
	/** The active. */
	private String active;
	
	/** The sort order. */
	private int sortOrder;
	
	/** The pre condition group id. */
	private int preConditionGroupID;
	
	/** The post condition group id. */
	private int postConditionGroupID;
	
	/** The input param group id. */
	private int inputParamGroupID;
	
	/** The output param group id. */
	private int outputParamGroupID;
	
	/** The runner id. */
	private int runnerID;
	
	/** The expected result. */
	private String expectedResult;
	
	/** The created by. */
	private String createdBy;
	
	/** The created date time. */
	private Date createdDateTime;
	
	/** The updated by. */
	private String updatedBy;
	
	/** The updated date time. */
	private Date updatedDateTime;
	
	/** The param group. */
	private List<ParamGroup> paramGroup = new ArrayList<ParamGroup>(0);
	
	/** The test step result. */
	private List<TestStepResult> testStepResult = new ArrayList<TestStepResult>(0);
	
	/** The runner. */
	private Runner runner;
	
	/** The actions. */
	private Actions actions;
	
	/** The objects. */
	private Objects objects;

	/**
	 * Gets the case step mappings.
	 *
	 * @return the case step mappings
	 */
	public List<CaseStepMapping> getCaseStepMappings() {
		return caseStepMappings;
	}

	/**
	 * Sets the case step mappings.
	 *
	 * @param caseStepMappings the new case step mappings
	 */
	public void setCaseStepMappings(List<CaseStepMapping> caseStepMappings) {
		this.caseStepMappings = caseStepMappings;
	}

	/** The case step mappings. */
	private List<CaseStepMapping> caseStepMappings = new ArrayList<CaseStepMapping>(0);

	/** The step param. */
	private String stepParam;

	/**
	 * Gets the step param.
	 *
	 * @return the step param
	 */
	public String getStepParam() {
		return stepParam;
	}

	/**
	 * Sets the step param.
	 *
	 * @param stepParam the new step param
	 */
	public void setStepParam(String stepParam) {
		this.stepParam = stepParam;
	}

	/**
	 * Gets the objects.
	 *
	 * @return the objects
	 */
	public Objects getObjects() {
		return objects;
	}

	/**
	 * Sets the objects.
	 *
	 * @param objects the new objects
	 */
	public void setObjects(Objects objects) {
		this.objects = objects;
	}

	/**
	 * Gets the actions.
	 *
	 * @return the actions
	 */
	public Actions getActions() {
		return actions;
	}

	/**
	 * Sets the actions.
	 *
	 * @param actions the actions to set
	 */
	public void setActions(Actions actions) {
		this.actions = actions;
	}

	/**
	 * Gets the test step id.
	 *
	 * @return the testStepID
	 */
	public int getTestStepID() {
		return testStepID;
	}

	/**
	 * Sets the test step id.
	 *
	 * @param testStepID the testStepID to set
	 */
	public void setTestStepID(int testStepID) {
		this.testStepID = testStepID;
	}

	/**
	 * Gets the step name.
	 *
	 * @return the stepName
	 */
	public String getStepName() {
		return stepName;
	}

	/**
	 * Sets the step name.
	 *
	 * @param stepName the stepName to set
	 */
	public void setStepName(String stepName) {
		this.stepName = stepName;
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
	 * Gets the test step type.
	 *
	 * @return the testStepType
	 */
	public String getTestStepType() {
		return testStepType;
	}

	/**
	 * Sets the test step type.
	 *
	 * @param testStepType the testStepType to set
	 */
	public void setTestStepType(String testStepType) {
		this.testStepType = testStepType;
	}

	/**
	 * Gets the action id.
	 *
	 * @return the actionID
	 */
	public int getActionID() {
		return actionID;
	}

	/**
	 * Sets the action id.
	 *
	 * @param actionID the actionID to set
	 */
	public void setActionID(int actionID) {
		this.actionID = actionID;
	}

	/**
	 * Gets the active.
	 *
	 * @return the active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * Sets the active.
	 *
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * Gets the sort order.
	 *
	 * @return the sortOrder
	 */
	public int getSortOrder() {
		return sortOrder;
	}

	/**
	 * Sets the sort order.
	 *
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
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
	 * Gets the input param group id.
	 *
	 * @return the inputParamGroupID
	 */
	public int getInputParamGroupID() {
		return inputParamGroupID;
	}

	/**
	 * Sets the input param group id.
	 *
	 * @param inputParamGroupID the inputParamGroupID to set
	 */
	public void setInputParamGroupID(int inputParamGroupID) {
		this.inputParamGroupID = inputParamGroupID;
	}

	/**
	 * Gets the output param group id.
	 *
	 * @return the outputParamGroupID
	 */
	public int getOutputParamGroupID() {
		return outputParamGroupID;
	}

	/**
	 * Sets the output param group id.
	 *
	 * @param outputParamGroupID the outputParamGroupID to set
	 */
	public void setOutputParamGroupID(int outputParamGroupID) {
		this.outputParamGroupID = outputParamGroupID;
	}

	/**
	 * Gets the runner id.
	 *
	 * @return the runnerID
	 */
	public int getRunnerID() {
		return runnerID;
	}

	/**
	 * Sets the runner id.
	 *
	 * @param runnerID the runnerID to set
	 */
	public void setRunnerID(int runnerID) {
		this.runnerID = runnerID;
	}

	/**
	 * Gets the expected result.
	 *
	 * @return the expectedResult
	 */
	public String getExpectedResult() {
		return expectedResult;
	}

	/**
	 * Sets the expected result.
	 *
	 * @param expectedResult the expectedResult to set
	 */
	public void setExpectedResult(String expectedResult) {
		this.expectedResult = expectedResult;
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
	 * Gets the param group.
	 *
	 * @return the paramGroup
	 */
	public List<ParamGroup> getParamGroup() {
		return paramGroup;
	}

	/**
	 * Sets the param group.
	 *
	 * @param paramGroup the paramGroup to set
	 */
	public void setParamGroup(List<ParamGroup> paramGroup) {
		this.paramGroup = paramGroup;
	}

	/**
	 * Gets the test step result.
	 *
	 * @return the testStepResult
	 */
	public List<TestStepResult> getTestStepResult() {
		return testStepResult;
	}

	/**
	 * Sets the test step result.
	 *
	 * @param testStepResult the testStepResult to set
	 */
	public void setTestStepResult(List<TestStepResult> testStepResult) {
		this.testStepResult = testStepResult;
	}

	/**
	 * Gets the runner.
	 *
	 * @return the runner
	 */
	public Runner getRunner() {
		return runner;
	}

	/**
	 * Sets the runner.
	 *
	 * @param runner the runner to set
	 */
	public void setRunner(Runner runner) {
		this.runner = runner;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TestStep [TestStepID=" + testStepID + ", StepName=" + stepName + ", Description=" + description + ", TestStepType=" + testStepType
				+ ", ActionID=" + actionID + ", Active=" + active + ", SortOrder=" + sortOrder + ", PreConditionGroupID=" + preConditionGroupID
				+ ", PostConditionGroupID=" + postConditionGroupID + ", InputParamGroupID=" + inputParamGroupID + ", OutputParamGroupID="
				+ outputParamGroupID + ", RunnerID=" + runnerID + ", ExpectedResult=" + expectedResult + "]";
	}

}