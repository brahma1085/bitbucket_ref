package com.sssoft.isatt.agent;

import com.sssoft.isatt.agent.exception.AgentException;

/**
 * The Interface ControllerThread.
 */
public interface ControllerThread extends Runnable {

	/**
	 * Inits the scheduler.
	 *
	 * @param schedulerId the scheduler id
	 * @throws AgentException the agent exception
	 */
	void initScheduler(int schedulerId) throws AgentException;
}
