package com.tfw.exilant;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exilant.tfw.pojo.input.TestData;
import com.exilant.tfw.pojo.input.TestParamData;
import com.exilant.tfw.service.InputService;

/**
 * The Class AddDataDefinitionController.
 */
@Controller
public class AddDataDefinitionController {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(AddDataDefinitionController.class);

	/** The input service. */
	private InputService inputService;

	/** The date. */
	java.util.Date date = new java.util.Date();

	/** The sql today. */
	java.sql.Date sqlToday = new java.sql.Date(date.getTime());

	/**
	 * Gets the input service.
	 * 
	 * @return the input service
	 */
	public InputService getInputService() {
		return inputService;
	}

	/**
	 * Sets the input service.
	 * 
	 * @param inputService
	 *            the new input service
	 */
	public void setInputService(InputService inputService) {
		this.inputService = inputService;
	}

	/**
	 * Gets the data definition.
	 * 
	 * @param AppId
	 *            the app id
	 * @return the data definition
	 */
	@RequestMapping(value = "/getDataDefinition", method = RequestMethod.POST)
	public @ResponseBody
	List<TestData> getDataDefinition(@RequestBody String AppId) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getDataDefinition(String) - start"); //$NON-NLS-1$
		}
		List<TestData> returnList = null;
		try {
			int AppID = Integer.parseInt(AppId);
			returnList = inputService.getTestDatabyAppId(AppID);
		} catch (Exception e) {
			LOG.error("Error Occured", e);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getDataDefinition(String) - end"); //$NON-NLS-1$
		}
		return returnList;
	}

	/**
	 * Adds the test data definition.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 */
	@RequestMapping(value = "/addTestDataDefinition", method = RequestMethod.POST)
	public @ResponseBody
	void addTestDataDefinition(HttpServletRequest request, HttpServletResponse response) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addTestDataDefinition(HttpServletRequest, HttpServletResponse) - start"); //$NON-NLS-1$
		}
		TestData testData = null;
		String line = null;
		StringBuffer JsonData = new StringBuffer();
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				JsonData.append(line);
			}
			testData = convertJsonFormDataToPojo(JsonData);
			testData.setCreatedBy("Jagadish");
			testData.setCreatedDateTime(sqlToday);
			testData.setUpdatedBy("Jagadish");
			testData.setUpdatedDateTime(sqlToday);
			inputService.insertTestDataGetKey(testData);
		} catch (Exception e) {
			LOG.warn("addTestDataDefinition(HttpServletRequest, HttpServletResponse) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("addTestDataDefinition(HttpServletRequest, HttpServletResponse) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * This method will convert json form data values to pojo.
	 * 
	 * @param JsonData
	 *            the json data
	 * @return converted form data values from json to pojo
	 * @throws EOFException
	 *             the eOF exception
	 */
	public TestData convertJsonFormDataToPojo(StringBuffer JsonData) throws EOFException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("convertJsonFormDataToPojo(StringBuffer) - start"); //$NON-NLS-1$
		}

		TestData testDefData = new TestData();
		ObjectMapper mapper = new ObjectMapper();
		try {
			testDefData = mapper.readValue(JsonData.toString(), TestData.class);
		} catch (IOException e) {
			LOG.warn("convertJsonFormDataToPojo(StringBuffer) - exception ignored", e); //$NON-NLS-1$
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("convertJsonFormDataToPojo(StringBuffer) - end"); //$NON-NLS-1$
		}
		return testDefData;
	}

	/**
	 * Gets the editing data definition.
	 * 
	 * @param dataId
	 *            the data id
	 * @return the editing data definition
	 */
	@RequestMapping(value = "/getEditingDataDefinition", method = RequestMethod.POST)
	public @ResponseBody
	TestData getEditingDataDefinition(@RequestBody String dataId) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getEditingDataDefinition(String) - start"); //$NON-NLS-1$
		}
		TestData returnTestData = null;
		try {
			int dataID = Integer.parseInt(dataId);
			returnTestData = inputService.getTestDataById(dataID);
		} catch (Exception e) {
			LOG.error("", e);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getEditingDataDefinition(String) - end"); //$NON-NLS-1$
		}
		return returnTestData;
	}

	/**
	 * Gets the test param group details.
	 * 
	 * @param AppId
	 *            the app id
	 * @return the test param group details
	 */
	@RequestMapping(value = "/getTestParamGroupDetails", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> getTestParamGroupDetails(@RequestBody String AppId) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestParamGroupDetails(String) - start"); //$NON-NLS-1$
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int AppID = Integer.parseInt(AppId);
			Object paramGroup = inputService.getParamGroupByAppId(AppID);
			map.put("paramGroup", paramGroup);
		} catch (Exception e) {
			LOG.error("Error Occured", e);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestParamGroupDetails(String) - end"); //$NON-NLS-1$
		}
		return map;
	}

	/**
	 * Gets the test param based on group details.
	 * 
	 * @param paramGroupId
	 *            the param group id
	 * @return the test param based on group details
	 */
	@RequestMapping(value = "/getTestParamBasedOnGroupDetails", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> getTestParamBasedOnGroupDetails(@RequestBody String paramGroupId) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestParamBasedOnGroupDetails(String) - start"); //$NON-NLS-1$
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Object param = null;
		try {
			int paramGroupID = Integer.parseInt(paramGroupId);
			param = inputService.getParamByParamGroupId(paramGroupID);
		} catch (Exception e) {
			LOG.error("Error Occured", e);
		}
		map.put("param", param);
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestParamBasedOnGroupDetails(String) - end"); //$NON-NLS-1$
		}
		return map;
	}

	/**
	 * Adds the test parameter data.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 */
	@RequestMapping(value = "/addTestParameterData", method = RequestMethod.POST)
	public @ResponseBody
	void addTestParameterData(HttpServletRequest request, HttpServletResponse response) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addTestParameterData(HttpServletRequest, HttpServletResponse) - start"); //$NON-NLS-1$
		}
		TestParamData testParamData = null;
		String line = null;
		StringBuffer JsonData = new StringBuffer();
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				JsonData.append(line);
			}
			testParamData = convertJsonFormDataToTestParamPojo(JsonData);
			testParamData.setCreatedBy("Jagadish");
			testParamData.setCreatedDateTime(sqlToday);
			testParamData.setUpdatedBy("Jagadish");
			testParamData.setUpdatedDateTime(sqlToday);
			inputService.insertTestParamData(testParamData);
		} catch (Exception e) {
			LOG.warn("addTestParameterData(HttpServletRequest, HttpServletResponse) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("addTestParameterData(HttpServletRequest, HttpServletResponse) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Convert json form data to test param pojo.
	 * 
	 * @param JsonData
	 *            the json data
	 * @return the test param data
	 * @throws EOFException
	 *             the eOF exception
	 */
	public TestParamData convertJsonFormDataToTestParamPojo(StringBuffer JsonData) throws EOFException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("convertJsonFormDataToTestParamPojo(StringBuffer) - start"); //$NON-NLS-1$
		}

		TestParamData testParam = new TestParamData();
		ObjectMapper mapper = new ObjectMapper();
		try {
			testParam = mapper.readValue(JsonData.toString(), TestParamData.class);
		} catch (IOException e) {
			LOG.warn("convertJsonFormDataToTestParamPojo(StringBuffer) - exception ignored", e); //$NON-NLS-1$
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("convertJsonFormDataToTestParamPojo(StringBuffer) - end"); //$NON-NLS-1$
		}
		return testParam;
	}

	/**
	 * Update edit data definition.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 */
	@RequestMapping(value = "/updateEditDataDefinition", method = RequestMethod.POST)
	public @ResponseBody
	void updateEditDataDefinition(HttpServletRequest request, HttpServletResponse response) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateEditDataDefinition(HttpServletRequest, HttpServletResponse) - start"); //$NON-NLS-1$
		}
		TestData testData = null;
		String line = null;
		StringBuffer JsonData = new StringBuffer();
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				JsonData.append(line);
			}
			testData = convertJsonFormDataToPojo(JsonData);
			testData.setAppID(1018);
			testData.setCreatedBy("Jagadish");
			testData.setCreatedDateTime(sqlToday);
			testData.setUpdatedBy("Jagadish");
			testData.setUpdatedDateTime(sqlToday);
			inputService.updateTestData(testData);
		} catch (Exception e) {
			LOG.warn("updateEditDataDefinition(HttpServletRequest, HttpServletResponse) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateEditDataDefinition(HttpServletRequest, HttpServletResponse) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Gets the parameter for data table.
	 * 
	 * @param testDataID
	 *            the test data id
	 * @return the parameter for data table
	 */
	@RequestMapping(value = "/getParameterForDataTable", method = RequestMethod.POST)
	public @ResponseBody
	List<TestParamData> getParameterForDataTable(@RequestBody String testDataID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParameterForDataTable(String) - start"); //$NON-NLS-1$
		}
		List<TestParamData> testdata = null;
		try {
			int testDataId = Integer.parseInt(testDataID);
			testdata = inputService.getTestParamDataByTestDataId(testDataId);
		} catch (Exception e) {
			LOG.error("Error Occured", e);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParameterForDataTable(String) - end"); //$NON-NLS-1$
		}
		return testdata;
	}

	/**
	 * Gets the parameter data for edit.
	 * 
	 * @param testParamDataID
	 *            the test param data id
	 * @return the parameter data for edit
	 */
	@RequestMapping(value = "/getParameterDataForEdit", method = RequestMethod.POST)
	public @ResponseBody
	TestParamData getParameterDataForEdit(@RequestBody String testParamDataID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParameterDataForEdit(String) - start"); //$NON-NLS-1$
		}
		TestParamData testParamData = null;
		try {
			int testParamDataId = Integer.parseInt(testParamDataID);
			testParamData = inputService.getTestParamDataById(testParamDataId);
		} catch (Exception e) {
			LOG.error("Error Occured", e);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParameterDataForEdit(String) - end"); //$NON-NLS-1$
		}
		return testParamData;
	}

	/**
	 * Update parameter data.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 */
	@RequestMapping(value = "/updateParameterData", method = RequestMethod.POST)
	public @ResponseBody
	void updateParameterData(HttpServletRequest request, HttpServletResponse response) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateParameterData(HttpServletRequest, HttpServletResponse) - start"); //$NON-NLS-1$
		}
		TestParamData testParamData = null;
		String line = null;
		StringBuffer JsonData = new StringBuffer();
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				JsonData.append(line);
			}
			testParamData = convertJsonFormDataToTestParamPojo(JsonData);
			testParamData.setUpdatedBy("Jagadish");
			testParamData.setUpdatedDateTime(sqlToday);
			inputService.updateTestParamData(testParamData);
		} catch (Exception e) {
			LOG.warn("updateParameterData(HttpServletRequest, HttpServletResponse) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateParameterData(HttpServletRequest, HttpServletResponse) - end"); //$NON-NLS-1$
		}
	}

}