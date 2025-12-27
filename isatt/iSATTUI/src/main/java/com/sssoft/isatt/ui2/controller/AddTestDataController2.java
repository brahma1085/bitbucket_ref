package com.sssoft.isatt.ui2.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sssoft.isatt.data.exception.ServiceException;
import com.sssoft.isatt.data.pojo.input.TestData;
import com.sssoft.isatt.data.pojo.input.TestParamData;
import com.sssoft.isatt.data.service.InputService;
import com.sssoft.isatt.data.utils.DataConstants;

/**
 * The Class AddTestDataController2.
 */
@Controller
public class AddTestDataController2 {

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(AddTestDataController2.class);

	/** The input service. */
	private InputService inputService;

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
	 * Gets the test data definition list.
	 * 
	 * @param appID
	 *            the app id
	 * @return the test data definition list
	 */
	@RequestMapping(value = "/getTestDataDefinitionList", method = RequestMethod.POST)
	public @ResponseBody
	List<TestData> getTestDataDefinitionList(@RequestBody String appID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestDataDefinitionList(String) - start"); //$NON-NLS-1$
		}
		LOG.info("Entering getTestDataDefinitionList Controller !!!");
		int appId = Integer.parseInt(appID);
		LOG.info("appID to retrive :: " + appId);
		List<TestData> getData = new ArrayList<TestData>();
		try {
			getData = inputService.getTestDatabyAppId(appId);
			LOG.info("TestData list ::" + getData.toString());
		} catch (ServiceException se) {
			LOG.error("getTestDataDefinitionList(String)", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestDataDefinitionList(String) - end"); //$NON-NLS-1$
		}
		return getData;
	}

	/**
	 * Gets the test data definition by active status.
	 * 
	 * @param appID
	 *            the app id
	 * @return the test data definition by active status
	 */
	@RequestMapping(value = "/getTestDataDefinitionByActiveStatus", method = RequestMethod.POST)
	public @ResponseBody
	List<TestData> getTestDataDefinitionByActiveStatus(@RequestBody String appID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestDataDefinitionByActiveStatus(String) - start"); //$NON-NLS-1$
		}
		LOG.info("Entering getTestDataDefinitionByActiveStatus Controller !!!");
		int appId = Integer.parseInt(appID);
		LOG.info("appID to retrive :: " + appId);
		List<TestData> getDataByActive = new ArrayList<TestData>();
		List<TestData> getData = new ArrayList<TestData>();
		try {
			getData = inputService.getTestDatabyAppId(appId);
			LOG.info("TestData list ::" + getData.toString());
			for (TestData data : getData) {
				if ("Y".equals(data.getStatus())) {
					getDataByActive.add(data);
				}
			}
			LOG.info("TestDataByActive list ::" + getDataByActive.toString());
		} catch (ServiceException se) {
			LOG.error("getTestDataDefinitionByActiveStatus(String)", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestDataDefinitionByActiveStatus(String) - end"); //$NON-NLS-1$
		}
		return getDataByActive;
	}

	/**
	 * Gets the test data definition by in active status.
	 * 
	 * @param appID
	 *            the app id
	 * @return the test data definition by in active status
	 */
	@RequestMapping(value = "/getTestDataDefinitionByINActiveStatus", method = RequestMethod.POST)
	public @ResponseBody
	List<TestData> getTestDataDefinitionByINActiveStatus(@RequestBody String appID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestDataDefinitionByINActiveStatus(String) - start"); //$NON-NLS-1$
		}
		int appId = Integer.parseInt(appID);
		LOG.info("appID to retrive :: " + appId);
		List<TestData> getDataByINActive = new ArrayList<TestData>();
		List<TestData> getData = new ArrayList<TestData>();
		try {
			getData = inputService.getTestDatabyAppId(appId);
			LOG.info("TestData list ::" + getData.toString());
			for (TestData data : getData) {
				if ("N".equals(data.getStatus())) {
					getDataByINActive.add(data);
				}
			}
			LOG.info("TestDataByINActive list ::" + getDataByINActive.toString());
		} catch (ServiceException se) {
			LOG.error("getTestDataDefinitionByINActiveStatus(String)", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTestDataDefinitionByINActiveStatus(String) - end"); //$NON-NLS-1$
		}
		return getDataByINActive;
	}

	/**
	 * Adds the test data defn.
	 * 
	 * @param TestData
	 *            the test data
	 * @return the int
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addTestDataDefITAP", method = RequestMethod.POST)
	public @ResponseBody
	int addTestDataDefn(@RequestBody Map<String, String> TestData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addTestDataDefn(Map<String,String>) - start"); //$NON-NLS-1$
		}
		int testdatakey = 0;
		long testParamDataID = 0;
		LOG.info("Entering addTestDataDefn controller !!!");
		try {
			JSONObject jsonObj = new JSONObject(TestData);
			try {
				String testDataDesc = (String) jsonObj.get("testDataDescription");
				String status = (String) jsonObj.get("status");
				int appId = (Integer) jsonObj.get("appID");
				TestData testdata = new TestData();
				testdata.setTestDataDescription(testDataDesc);
				if ("Active".equals(status)) {
					testdata.setStatus("Y");
				} else {
					testdata.setStatus("N");
				}
				testdata.setAppID(appId);
				testdata.setCreatedBy(DataConstants.DEFAULT_USER);
				testdata.setCreatedDateTime(DataConstants.DEFAULT_DATE);
				testdata.setUpdatedBy(DataConstants.DEFAULT_USER);
				testdata.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
				testdatakey = inputService.insertTestDataGetKey(testdata);
				LOG.info("addTestDataDefITAP Pojo String ::" + testdata.toString());
				TestParamData testPramData = new TestParamData();
				ArrayList<ArrayList<String>> testparamdata = (ArrayList<ArrayList<String>>) jsonObj.get("paramDataArr");
				LOG.info("testparamdata  : " + testparamdata);
				for (ArrayList<String> testparamdatas : testparamdata) {
					if (testparamdatas != null && testparamdatas.size() > 0) {
						LOG.info(testparamdatas);
						int paramID = Integer.parseInt(testparamdatas.get(1));
						testPramData.setParamID(paramID);
						testPramData.setTestDataID(testdatakey);
						testPramData.setParamValue(testparamdatas.get(2));
						testPramData.setValueBig(testparamdatas.get(3));
						testPramData.setCreatedBy(DataConstants.DEFAULT_USER);
						testPramData.setCreatedDateTime(DataConstants.DEFAULT_DATE);
						testPramData.setUpdatedBy(DataConstants.DEFAULT_USER);
						testPramData.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
						testParamDataID = inputService.insertTestParamData(testPramData);
						LOG.info(" test param data id .....: " + testParamDataID);
					}
				}
			} catch (JSONException e) {
				LOG.error("addTestDataDefn(Map<String,String>)", e); //$NON-NLS-1$
			}
		} catch (Exception e) {
			LOG.error("addTestDataDefn(Map<String,String>)", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("addTestDataDefn(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return testdatakey;
	}

	/**
	 * Edits the test data desc itap.
	 * 
	 * @param testDataDesc
	 *            the test data desc
	 * @return the test data
	 */
	@RequestMapping(value = "/editTestDataDescITAP", method = RequestMethod.POST)
	public @ResponseBody
	TestData editTestDataDescITAP(@RequestBody Map<String, Integer> testDataDesc) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("editTestDataDescITAP(Map<String,Integer>) - start"); //$NON-NLS-1$
		}
		TestData testDatas = new TestData();
		LOG.info("Inside editTestDataDescITAP method");
		LOG.info("Printing data recieved from client :: " + testDataDesc);
		try {
			JSONObject jsonObject = new JSONObject(testDataDesc);
			int testDataIDs = (Integer) jsonObject.get("testDataID");
			LOG.info("testDataID to edit :: " + testDataIDs);
			testDatas = inputService.getTestDataById(testDataIDs);
			LOG.info("TestData Name:: " + testDatas.getTestDataDescription());
		} catch (ServiceException se) {
			LOG.error("editTestDataDescITAP(Map<String,Integer>)", se); //$NON-NLS-1$
		} catch (JSONException e) {
			LOG.error("editTestDataDescITAP(Map<String,Integer>)", e);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("editTestDataDescITAP(Map<String,Integer>) - end"); //$NON-NLS-1$
		}
		return testDatas;
	}

	/**
	 * Update test data defn itap.
	 * 
	 * @param testDataDefn
	 *            the test data defn
	 * @return the long
	 */
	@RequestMapping(value = "/updateTestDataDefnITAP", method = RequestMethod.POST)
	public @ResponseBody
	long updateTestDataDefnITAP(@RequestBody Map<String, String> testDataDefn) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestDataDefnITAP(Map<String,String>) - start"); //$NON-NLS-1$
		}
		long update = 0;
		try {
			JSONObject jsonObj = new JSONObject(testDataDefn);
			String testDataDes = (String) jsonObj.get("testDataDescription");
			String status = (String) jsonObj.get("status");
			int testDataIDs = (Integer) jsonObj.get("testDataID");
			TestData testDatas = new TestData();
			testDatas.setTestDataDescription(testDataDes);
			if ("Active".equals(status)) {
				testDatas.setStatus("Y");
			} else {
				testDatas.setStatus("N");
			}
			testDatas.setTestDataID(testDataIDs);
			testDatas.setUpdatedBy(DataConstants.DEFAULT_USER);
			testDatas.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			update = inputService.updateTestData(testDatas);
			TestParamData testParamData = new TestParamData();
			TestParamData testParamDatas = new TestParamData();
			@SuppressWarnings("unchecked")
			ArrayList<ArrayList<String>> multiobjectdata = (ArrayList<ArrayList<String>>) jsonObj.get("updateTestDataArr");
			for (int i = 1; i < multiobjectdata.size(); i++) {
				ArrayList<String> objData = (ArrayList<String>) multiobjectdata.get(i);
				int ObjArraySize = objData.size();
				if (ObjArraySize == 5) {
					String parts = objData.toString();
					String[] objDetails = parts.split(",");
					int paramGrpID = Integer.parseInt(objDetails[2].trim());
					int paramID = Integer.parseInt(objDetails[3].trim());
					int testParamDataID = Integer.parseInt(objDetails[4].replace("]", "").trim());
					testParamData.setTestParamDataID(testParamDataID);
					testParamData.setParamValue(objDetails[0].replace("[", "").trim());
					testParamData.setValueBig(objDetails[1].trim());
					testParamData.setParamGroupID(paramGrpID);
					testParamData.setParamID(paramID);
					testParamData.setUpdatedBy(DataConstants.DEFAULT_USER);
					testParamData.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
					inputService.updateTestParamData(testParamData);
					LOG.info("update value of testParamData::" + testParamData.toString());
				} else {
					String parts = objData.toString();
					String[] objDetails = parts.split(",");
					testParamDatas.setParamGroupID(Integer.parseInt(objDetails[2].trim()));
					testParamDatas.setParamID(Integer.parseInt(objDetails[3].trim()));
					testParamDatas.setParamValue(objDetails[4].trim());
					testParamDatas.setValueBig(objDetails[5].replace("]", "").trim());
					testParamDatas.setTestDataID(testDataIDs);
					testParamDatas.setCreatedBy(DataConstants.DEFAULT_USER);
					testParamDatas.setCreatedDateTime(DataConstants.DEFAULT_DATE);
					testParamDatas.setUpdatedBy(DataConstants.DEFAULT_USER);
					testParamDatas.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
					inputService.insertTestParamDataGetKey(testParamDatas);
					LOG.info("insert value of testParamDatas::" + testParamDatas.toString());
				}
			}
		} catch (Exception e) {
			LOG.error("updateTestDataDefnITAP(Map<String,String>)", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestDataDefnITAP(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return update;
	}

	/**
	 * Gets the parameter for test data itap.
	 * 
	 * @param testDataID
	 *            the test data id
	 * @return the parameter for test data itap
	 */
	@RequestMapping(value = "/getParameterForTestDataITAP", method = RequestMethod.POST)
	public @ResponseBody
	List<TestParamData> getParameterForTestDataITAP(@RequestBody String testDataID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParameterForTestDataITAP(String) - start"); //$NON-NLS-1$
		}
		LOG.info("Entering getParameterForDataTable Controller !!!");
		int testDataId = Integer.parseInt(testDataID);
		LOG.info("testDataId" + testDataId);
		List<TestParamData> testdata = null;
		try {
			testdata = inputService.getTestParamDataByTestDataId(testDataId);
		} catch (Exception e) {
			LOG.error("getParameterForTestDataITAP(String)", e);
		}
		LOG.info(testdata.toString());
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParameterForTestDataITAP(String) - end"); //$NON-NLS-1$
		}
		return testdata;
	}

}