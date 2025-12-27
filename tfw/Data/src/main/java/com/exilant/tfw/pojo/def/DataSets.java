package com.exilant.tfw.pojo.def;

import java.io.Serializable;
import java.sql.Date;

public class DataSets implements Serializable {

	/**
	 * Default Serial version ID
	 */
	private static final long serialVersionUID = 1L;
	private int planId;
	private int dataId;
	private int schedulerId;
	private int agentId;
	private String schedulerName;
	private String planName;
	private String dataDescription;
	private String frequency;
	private String notification;
	private Boolean multiLanes;
	private String createdBy;
	private Date createdDate;
	
	
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	
	public String getFrequency() {
		return frequency;
	}
	
	public String getSchedulerName() {
		return schedulerName;
	}

	public void setSchedulerName(String schedulerName) {
		this.schedulerName = schedulerName;
	}
	
	public int getSchedulerId() {
		return schedulerId;
	}

	public void setSchedulerId(int schedulerId) {
		this.schedulerId = schedulerId;
	}
	
	public int getAgentId() {
		return agentId;
	}

	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}
	
	public int getPlanId() {
		return planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
	}

	public int getDataId() {
		return dataId;
	}

	public void setDataId(int dataId) {
		this.dataId = dataId;
	}
	
	public String getNotification() {
		return notification;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}
	
	public Boolean getMultiLanes() {
		return multiLanes;
	}

	public void setMultiLanes(Boolean multiLanes) {
		this.multiLanes = multiLanes;
	}



	/**
	 * @return the planName
	 */
	public String getPlanName() {
		return planName;
	}

	/**
	 * @param planName
	 *            the planName to set
	 */
	public void setPlanName(String planName) {
		this.planName = planName;
	}

	/**
	 * @return the dataDescription
	 */
	public String getDataDescription() {
		return dataDescription;
	}

	/**
	 * @param dataDescription
	 *            the dataDescription to set
	 */
	public void setDataDescription(String dataDescription) {
		this.dataDescription = dataDescription;
	}
	
	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}
