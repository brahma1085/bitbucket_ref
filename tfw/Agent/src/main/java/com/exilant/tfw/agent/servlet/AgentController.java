package com.exilant.tfw.agent.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.exilant.tfw.agent.ControllerThread;
import com.exilant.tfw.agent.lanes.SchedulerLaneProcess;
import com.exilant.tfw.agent.lanes.SchedulerProcess;
import com.exilant.tfw.pojo.Scheduler;
import com.exilant.tfw.pojo.SchedulerLaneDetails;
import com.exilant.tfw.util.Base64;

/**
 * The Class AgentController.
 */
@Controller
public class AgentController {
	
	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(AgentController.class);

	/** The controller thread. */
	private ControllerThread controllerThread;

	/**
	 * Sets the controller thread.
	 *
	 * @param controllerThread the new controller thread
	 */
	public void setControllerThread(ControllerThread controllerThread) {
		this.controllerThread = controllerThread;
	}

	/**
	 * Constructor.
	 */
	public AgentController() {
	}

	/**
	 * Execute scheduler lane details.
	 *
	 * @param request the request
	 * @param response the response
	 */
	@RequestMapping(value = "/iTAPMulti", method = RequestMethod.POST)
	public void executeSchedulerLaneDetails(HttpServletRequest request, HttpServletResponse response) {
		LOG.info("Received the request");
		int options = 0;
		String typ = request.getParameter("typ");
		String objData = request.getParameter("obj1");
		String jvmMode = request.getParameter("jvmMode");
		Object obj;
		try {
			obj = Base64.decodeToObject(objData, options, null);
			SchedulerLaneDetails schedulerLaneDetails = (SchedulerLaneDetails) obj;
			SchedulerLaneProcess schedulerLaneProcess = new SchedulerLaneProcess();
			schedulerLaneProcess.process(schedulerLaneDetails, Boolean.parseBoolean(jvmMode), schedulerLaneProcess.getTaskListener(), controllerThread,
					schedulerLaneDetails.getScheduleID());
			LOG.info("lane executed for the lane type : " + typ);
		} catch (ClassNotFoundException e) {
			LOG.warn("executeSchedulerLaneDetails(HttpServletRequest, HttpServletResponse) - exception ignored", e); //$NON-NLS-1$
		} catch (Exception e) {
			LOG.warn("executeSchedulerLaneDetails(HttpServletRequest, HttpServletResponse) - exception ignored", e); //$NON-NLS-1$
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("executeSchedulerLaneDetails(HttpServletRequest, HttpServletResponse) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Execute scheduler.
	 *
	 * @param request the request
	 * @param response the response
	 */
	@RequestMapping(value = "/iTAP", method = RequestMethod.POST)
	public void executeScheduler(HttpServletRequest request, HttpServletResponse response) {
		LOG.info("Received the request");
		int options = 0;
		String typ = request.getParameter("typ");
		String objData = request.getParameter("obj1");
		Object obj;
		try {
			obj = Base64.decodeToObject(objData, options, null);
			Scheduler scheduler = (Scheduler) obj;
			controllerThread.initScheduler(scheduler.getScheduleID());
			SchedulerProcess schedulerProcess = new SchedulerProcess();
			schedulerProcess.process(scheduler, schedulerProcess.getTaskListener(), controllerThread);
			LOG.info("lane executed for the lane type : " + typ);
		} catch (ClassNotFoundException e) {
			LOG.warn("executeScheduler(HttpServletRequest, HttpServletResponse) - exception ignored", e); //$NON-NLS-1$
		} catch (Exception e) {
			LOG.warn("executeScheduler(HttpServletRequest, HttpServletResponse) - exception ignored", e); //$NON-NLS-1$
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("executeScheduler(HttpServletRequest, HttpServletResponse) - end"); //$NON-NLS-1$
		}
	}

}