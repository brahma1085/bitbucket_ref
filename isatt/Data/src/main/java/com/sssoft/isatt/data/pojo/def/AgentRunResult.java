package com.sssoft.isatt.data.pojo.def;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Date;

import com.sssoft.isatt.data.pojo.input.TestPlan;
import com.sssoft.isatt.data.pojo.output.TestPlanResult;

/**
 * AgentRunResult entity. This object needs to be filled in the following
 * format: AgentDetails1 (1 or 2... where TestPlan is run) TestPlanResult - 1
 * object per TestPlanRun TestAgentRun (1 per Agent that ran) TestPlanResult
 * (have reference to TestPlan) TestScenarioResult (have reference to
 * TestScenario) TestSuiteResult (have reference to TestSuite) TestCaseResult
 * (have reference to TestCase) TestStepResult (have reference to TestStep)
 */
public class AgentRunResult implements Serializable, Cloneable {

	/**
	 * Default serial version id
	 */
	private static final long serialVersionUID = 1L;

	private TestPlanResult planResult;
	private File reportFilePath;
	private URL reportURL;
	private String reportUrlPath;
	private BigDecimal agentId;
	private TestPlan testPlan;
	private BigDecimal testPlanId;
	private String agentName;
	private String agentOS;
	private String ip;
	private int port;
	private String instance;
	private String runBy;
	private Date planRunStartDateTime;
	private Date planRunEndDateTime;

	/** Default constructor */
	public AgentRunResult() {
		super();
	}

	/**
	 * toString will return String object representing the state of this
	 * valueObject. This is useful during application development, and possibly
	 * when application is writing object states in textlog.
	 */
	@Override
	public String toString() {
		return "AgentRunResult [planResult=" + planResult + ", reportFilePath=" + reportFilePath + ", reportURL=" + reportURL + ", reportUrlPath="
				+ reportUrlPath + ", agentId=" + agentId + "]";
	}

	public TestPlanResult getPlanResult() {
		return planResult;
	}

	public void setPlanResult(TestPlanResult planResult) {
		this.planResult = planResult;
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

	public BigDecimal getAgentId() {
		return agentId;
	}

	public void setAgentId(BigDecimal agentId) {
		this.agentId = agentId;
	}

	public TestPlan getTestPlan() {
		return testPlan;
	}

	public void setTestPlan(TestPlan testPlan) {
		this.testPlan = testPlan;
	}

	public BigDecimal getTestPlanId() {
		return testPlanId;
	}

	public void setTestPlanId(BigDecimal testPlanId) {
		this.testPlanId = testPlanId;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAgentOS() {
		return agentOS;
	}

	public void setAgentOS(String agentOS) {
		this.agentOS = agentOS;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public String getRunBy() {
		return runBy;
	}

	public void setRunBy(String runBy) {
		this.runBy = runBy;
	}

	public Date getPlanRunStartDateTime() {
		return planRunStartDateTime;
	}

	public void setPlanRunStartDateTime(Date planRunStartDateTime) {
		this.planRunStartDateTime = planRunStartDateTime;
	}

	public Date getPlanRunEndDateTime() {
		return planRunEndDateTime;
	}

	public void setPlanRunEndDateTime(Date planRunEndDateTime) {
		this.planRunEndDateTime = planRunEndDateTime;
	}

}
