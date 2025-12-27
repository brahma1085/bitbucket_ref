package com.sssoft.isatt.data.pojo.input;

import java.io.Serializable;
import java.sql.Date;

/**
 * The Class TestParamData.
 *
 * @author mohammedfirdos
 */
public class TestParamData implements Serializable {

	/** TestParamData Entity. */
	private static final long serialVersionUID = 1L;

	/** The test param data id. */
	private int testParamDataID;
	
	/** The test data id. */
	private int testDataID;
	
	/** The param id. */
	private int paramID;
	
	/** The param value. */
	private String paramValue;
	
	/** The value big. */
	private String valueBig;
	
	/** The created by. */
	private String createdBy;
	
	/** The created date time. */
	private Date createdDateTime;
	
	/** The updated by. */
	private String updatedBy;
	
	/** The updated date time. */
	private Date updatedDateTime;
	
	/** The test data. */
	private TestData testData;
	
	/** The param group id. */
	private int paramGroupID;

	/** The param. */
	private Param param;

	/**
	 * Gets the param.
	 *
	 * @return the param
	 */
	public Param getParam() {
		return param;
	}

	/**
	 * Sets the param.
	 *
	 * @param param the new param
	 */
	public void setParam(Param param) {
		this.param = param;
	}

	/**
	 * Gets the test param data id.
	 *
	 * @return the testParamDataID
	 */
	public int getTestParamDataID() {
		return testParamDataID;
	}

	/**
	 * Sets the test param data id.
	 *
	 * @param testParamDataID the testParamDataID to set
	 */
	public void setTestParamDataID(int testParamDataID) {
		this.testParamDataID = testParamDataID;
	}

	/**
	 * Gets the test data id.
	 *
	 * @return the testDataID
	 */
	public int getTestDataID() {
		return testDataID;
	}

	/**
	 * Sets the test data id.
	 *
	 * @param testDataID the testDataID to set
	 */
	public void setTestDataID(int testDataID) {
		this.testDataID = testDataID;
	}

	/**
	 * Gets the param id.
	 *
	 * @return the paramID
	 */
	public int getParamID() {
		return paramID;
	}

	/**
	 * Sets the param id.
	 *
	 * @param paramID the paramID to set
	 */
	public void setParamID(int paramID) {
		this.paramID = paramID;
	}

	/**
	 * Gets the param value.
	 *
	 * @return the paramValue
	 */
	public String getParamValue() {
		return paramValue;
	}

	/**
	 * Sets the param value.
	 *
	 * @param paramValue the paramValue to set
	 */
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	/**
	 * Gets the value big.
	 *
	 * @return the valueBig
	 */
	public String getValueBig() {
		return valueBig;
	}

	/**
	 * Sets the value big.
	 *
	 * @param valueBig the valueBig to set
	 */
	public void setValueBig(String valueBig) {
		this.valueBig = valueBig;
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
	 * Gets the param group id.
	 *
	 * @return the paramGroupID
	 */
	public int getParamGroupID() {
		return paramGroupID;
	}

	/**
	 * Sets the param group id.
	 *
	 * @param paramGroupID the paramGroupID to set
	 */
	public void setParamGroupID(int paramGroupID) {
		this.paramGroupID = paramGroupID;
	}

	/**
	 * Gets the test data.
	 *
	 * @return the test data
	 */
	public TestData getTestData() {
		return testData;
	}

	/**
	 * Sets the test data.
	 *
	 * @param testData the new test data
	 */
	public void setTestData(TestData testData) {
		this.testData = testData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TestParamData [TestParamDataID=" + testParamDataID + ", TestDataID=" + testDataID + ", ParamID=" + paramID + ", ParamValue=" + paramValue
				+ ", ValueBig=" + valueBig + ", CreatedBy=" + createdBy + ", CreatedDateTime=" + createdDateTime + ", UpdatedBy=" + updatedBy
				+ ", UpdatedDateTime=" + updatedDateTime + ", testData=" + testData + ", ParamGroupID=" + paramGroupID + ", param=" + param + "]";
	}

}
