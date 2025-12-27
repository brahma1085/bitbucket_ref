package com.exilant.tfw.core;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.exilant.tfw.bean.UtlConf;
import com.exilant.tfw.pojo.AgentDetails;
import com.exilant.tfw.util.LangUtils;

/**
 * List of agents controlled by this Core.
 *
 * @author tusharkapila
 */
public class Agents {
	
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(Agents.class);

	/** The agents. */
	private static Map<String, AgentDetails> agents = new HashMap<String, AgentDetails>();

	// AgentDetails agents1;

	/**
	 * Gets the agent.
	 *
	 * @param n the n
	 * @return the agent
	 */
	public static AgentDetails getAgent(String n) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAgent(String) - start"); //$NON-NLS-1$
		}

		AgentDetails returnAgentDetails = agents.get(n);
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAgent(String) - end"); //$NON-NLS-1$
		}
		return returnAgentDetails;
	}

	/**
	 * put by name.
	 *
	 * @param n the n
	 * @param a the a
	 */
	public static void putAgent(String n, AgentDetails a) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("putAgent(String, AgentDetails) - start"); //$NON-NLS-1$
		}

		agents.put(n, a);

		if (LOG.isDebugEnabled()) {
			LOG.debug("putAgent(String, AgentDetails) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Gets the agents.
	 *
	 * @return the agents
	 */
	public static Map<String, AgentDetails> getAgents() {
		return agents;
	}

	/**
	 * Sets the agents.
	 *
	 * @param agents the agents
	 */
	public static void setAgents(Map<String, AgentDetails> agents) {
		Agents.agents = agents;
	}

	/**
	 * assigns the agent details from properties configuration to agents HashMap.
	 */
	public static void init() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("init() - start"); //$NON-NLS-1$
		}

		AgentDetails aDetails;
		int i = 1;
		LOG.debug("Inside Agents Init");
		while (UtlConf.getProperty("agent" + i + "name") != null && UtlConf.getProperty("agent" + i + "name").length() != 0) {
			aDetails = new AgentDetails();

			final String nm = UtlConf.getProperty("agent" + i + "name");
			aDetails.setAgentName(nm);
			aDetails.setPort(Integer.parseInt(UtlConf.getProperty("agent" + i + "port")));
			aDetails.setIP(UtlConf.getProperty("agent" + i + "ip"));

			String sActive = UtlConf.getProperty("agent" + i + "active");
			aDetails.setStatus(LangUtils.isTrue(sActive, true));
			LOG.warn("agent " + nm + ", status " + sActive);
			agents.put(aDetails.getAgentName(), aDetails);
			i++;
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("init() - end"); //$NON-NLS-1$
		}
	}

}
