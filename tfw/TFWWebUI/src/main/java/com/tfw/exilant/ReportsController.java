package com.tfw.exilant;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exilant.tfw.exception.ServiceException;
import com.exilant.tfw.pojo.SchedulerRunDetails;
import com.exilant.tfw.service.OutputService;

/**
 * The Class ReportsController.
 */
@Controller
public class ReportsController {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(ReportsController.class);

	/** The output service. */
	private OutputService outputService;

	/**
	 * Sets the output service.
	 * 
	 * @param outputService
	 *            the new output service
	 */
	public void setOutputService(OutputService outputService) {
		this.outputService = outputService;
	}

	/**
	 * Gets the scheduler results.
	 * 
	 * @param runId
	 *            the run id
	 * @param type
	 *            the type
	 * @return the scheduler results
	 */
	@RequestMapping(value = "/getSchedulerRunDetails", method = RequestMethod.GET)
	public @ResponseBody
	List<SchedulerRunDetails> getSchedulerResults(@RequestParam int runId, @RequestParam String type) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSchedulerResults(int, String) - start"); //$NON-NLS-1$
		}
		try {
			return outputService.getSchedulerRunDetails(runId, type);
		} catch (ServiceException e) {
			LOG.error("getSchedulerResults(int, String)", e); //$NON-NLS-1$
		}
		return null;
	}
}