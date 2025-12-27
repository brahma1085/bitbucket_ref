package com.exilant.tfw.pojo.def;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Run Result entity. It needs to be filled with List (or) Map of
 * AgentRunResults
 */
public class RunResult implements Serializable, Cloneable {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(RunResult.class);

	/**
	 * Default serial version id
	 */
	private static final long serialVersionUID = 1L;
	private int runResultId;
	private String runName;
	private File reportFilePath;
	private URL reportURL;
	private String reportUrlPath;
	private String userId;

	private String masterReportName;

	private File masterReportFile;

	private Map<String, AgentRunResult> agentRunResultMap = new HashMap<String, AgentRunResult>(0);
	private List<AgentRunResult> agentRunResultList = new ArrayList<AgentRunResult>(0);

	private Date startDate;
	private Date endDate;

	private boolean summaryReport;
	private boolean detailReport;

	public int getAgentRunResultMapSize() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAgentRunResultMapSize() - start"); //$NON-NLS-1$
		}

		int returnint = agentRunResultMap.size();
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAgentRunResultMapSize() - end"); //$NON-NLS-1$
		}
		return returnint;
	}

	public void addToAgentRunResultList(AgentRunResult agentRunResult) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addToAgentRunResultList(AgentRunResult) - start"); //$NON-NLS-1$
		}

		this.agentRunResultList.add(agentRunResult);

		if (LOG.isDebugEnabled()) {
			LOG.debug("addToAgentRunResultList(AgentRunResult) - end"); //$NON-NLS-1$
		}
	}

	public AgentRunResult getAgentRunResultFromMap(String key) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAgentRunResultFromMap(String) - start"); //$NON-NLS-1$
		}

		AgentRunResult returnAgentRunResult = agentRunResultMap.get(key);
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAgentRunResultFromMap(String) - end"); //$NON-NLS-1$
		}
		return returnAgentRunResult;
	}

	public void putAgentRunResultInMap(String key, AgentRunResult agentRunResult) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("putAgentRunResultInMap(String, AgentRunResult) - start"); //$NON-NLS-1$
		}

		agentRunResultMap.put(key, agentRunResult);

		if (LOG.isDebugEnabled()) {
			LOG.debug("putAgentRunResultInMap(String, AgentRunResult) - end"); //$NON-NLS-1$
		}
	}

	public String getRunName() {
		return runName;
	}

	public void setRunName(String runName) {
		this.runName = runName;
	}

	public File getReportFilePath() {
		return reportFilePath;
	}

	public void setReportFilePath(File reportFilePath) {
		this.reportFilePath = reportFilePath;
	}

	public URL getReportURL() {
		return reportURL;
	}

	public void setReportURL(URL reportURL) {
		this.reportURL = reportURL;
	}

	public String getReportUrlPath() {
		return reportUrlPath;
	}

	public void setReportUrlPath(String reportUrlPath) {
		this.reportUrlPath = reportUrlPath;
	}

	public Map<String, AgentRunResult> getAgentRunResultMap() {
		return agentRunResultMap;
	}

	public void setAgentRunResultMap(Map<String, AgentRunResult> agentRunResultMap) {
		this.agentRunResultMap = agentRunResultMap;
	}

	public List<AgentRunResult> getAgentRunResultList() {
		return agentRunResultList;
	}

	public void setAgentRunResultList(List<AgentRunResult> agentRunResultList) {
		this.agentRunResultList = agentRunResultList;
	}

	public String getMasterReportName() {
		return masterReportName;
	}

	public void setMasterReportName(String masterReportName) {
		this.masterReportName = masterReportName;
	}

	public File getMasterReportFile() {
		return masterReportFile;
	}

	public void setMasterReportFile(File masterReportFile) {
		this.masterReportFile = masterReportFile;
	}

	@Override
	public String toString() {
		return "RunResult [runName=" + runName + ", reportFilePath=" + reportFilePath + ", reportURL=" + reportURL + ", reportUrlPath=" + reportUrlPath
				+ "]";
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date d) {
		startDate = d;
	}

	public boolean getSummaryReport() {
		return summaryReport;
	}

	public void setSummaryReport(boolean summaryReport) {
		this.summaryReport = summaryReport;
	}

	public boolean getDetailReport() {
		return detailReport;
	}

	public void setDetailReport(boolean detailReport) {
		this.detailReport = detailReport;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getRunResultId() {
		return runResultId;
	}

	public void setRunResultId(int runResultId) {
		this.runResultId = runResultId;
	}

}
