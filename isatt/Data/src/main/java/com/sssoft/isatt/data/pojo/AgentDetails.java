package com.sssoft.isatt.data.pojo;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.sssoft.isatt.data.pojo.output.LaneResults;

/**
 * The Class AgentDetails.
 *
 * @author mohammedfirdos
 */
public class AgentDetails implements Serializable {

	/** AgentDetails Entity. */
	private static final long serialVersionUID = 1L;

	/** The agent id. */
	private int agentID;
	
	/** The agent name. */
	private String agentName;
	
	/** The ip. */
	private String ip;
	
	/** The port. */
	private int port;
	
	/** The machine details. */
	private String machineDetails;
	
	/** The protocol. */
	private String protocol;
	
	/** The created by. */
	private String createdBy;
	
	/** The created date time. */
	private Date createdDateTime;
	
	/** The updated by. */
	private String updatedBy;
	
	/** The updated date time. */
	private Date updatedDateTime;
	
	/** The lane results. */
	private List<LaneResults> laneResults = new ArrayList<LaneResults>(0);
	
	/** The status. */
	private boolean status;

	/**
	 * Checks if is status.
	 *
	 * @return true, if is status
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * Gets the agent id.
	 *
	 * @return the agentID
	 */
	public int getAgentID() {
		return agentID;
	}

	/**
	 * Sets the agent id.
	 *
	 * @param agentID the agentID to set
	 */
	public void setAgentID(int agentID) {
		this.agentID = agentID;
	}

	/**
	 * Gets the agent name.
	 *
	 * @return the agentName
	 */
	public String getAgentName() {
		return agentName;
	}

	/**
	 * Sets the agent name.
	 *
	 * @param agentName the agentName to set
	 */
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	/**
	 * Gets the ip.
	 *
	 * @return the iP
	 */
	public String getIP() {
		return ip;
	}

	/**
	 * Sets the ip.
	 *
	 * @param iP the iP to set
	 */
	public void setIP(String iP) {
		this.ip = iP;
	}

	/**
	 * Gets the port.
	 *
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Sets the port.
	 *
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * Gets the machine details.
	 *
	 * @return the machineDetails
	 */
	public String getMachineDetails() {
		return machineDetails;
	}

	/**
	 * Sets the machine details.
	 *
	 * @param machineDetails the machineDetails to set
	 */
	public void setMachineDetails(String machineDetails) {
		this.machineDetails = machineDetails;
	}

	/**
	 * Gets the protocol.
	 * 
	 * @return the protocol
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * Sets the protocol.
	 *
	 * @param protocol the new protocol
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
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
	 * Gets the lane results.
	 *
	 * @return the laneResults
	 */
	public List<LaneResults> getLaneResults() {
		return laneResults;
	}

	/**
	 * Sets the lane results.
	 *
	 * @param laneResults the laneResults to set
	 */
	public void setLaneResults(List<LaneResults> laneResults) {
		this.laneResults = laneResults;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AgentDetails [AgentID=" + agentID + ", AgentName=" + agentName + ", IP=" + ip + ", Port=" + port + ", MachineDetails=" + machineDetails
				+ ", protocol=" + protocol + ", CreatedBy=" + createdBy + ", CreatedDateTime=" + createdDateTime + ", UpdatedBy=" + updatedBy
				+ ", UpdatedDateTime=" + updatedDateTime + ", status=" + status + "]";
	}

}
