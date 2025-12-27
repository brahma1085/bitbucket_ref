package com.sssoft.isatt.ui2.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.lingala.zip4j.core.ZipFile;
import net.minidev.json.JSONObject;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sssoft.isatt.data.exception.DataAccessException;
import com.sssoft.isatt.data.exception.ServiceException;
import com.sssoft.isatt.data.pojo.Actions;
import com.sssoft.isatt.data.pojo.input.CaseStepMapping;
import com.sssoft.isatt.data.pojo.input.IdentifierType;
import com.sssoft.isatt.data.pojo.input.ObjectGroup;
import com.sssoft.isatt.data.pojo.input.ObjectType;
import com.sssoft.isatt.data.pojo.input.Objects;
import com.sssoft.isatt.data.pojo.input.Param;
import com.sssoft.isatt.data.pojo.input.ParamGroup;
import com.sssoft.isatt.data.pojo.input.TestParamData;
import com.sssoft.isatt.data.pojo.input.TestStep;
import com.sssoft.isatt.data.service.InputService;
import com.sssoft.isatt.data.service.MainService;
import com.sssoft.isatt.data.utils.DataConstants;
import com.sssoft.isatt.ui.utils.ExtractClassParameter;

/**
 * The Class AddTestStepsController2.
 */
@Controller
public class AddTestStepsController2 {

	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(AddTestStepsController2.class);

	/** The input service. */
	private InputService inputService;

	/** The main service. */
	private MainService mainService;
	
	List<String> fileList;
	
	private static String className;
	
	
	/**
	 * Gets the class details.
	 * 
	 * @param javaApiData
	 *            the java api data
	 * @return the class details
	 */
	@RequestMapping(value = "/getClassDetails", method = RequestMethod.POST)
	@ResponseBody
	public List<?> getClassDetails(@RequestBody Map<?, ?> javaApiData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getClassDetails(Map<?,?>) - start"); //$NON-NLS-1$
		}
		List<?> methodParams = null;
		try {
			String src = "/Users/anand.mathur/data/tfw/w/WorkSpace/testJavaAPI/bin/";
			className = "Calci";
			for (Map.Entry<?, ?> entry : javaApiData.entrySet()) {
				if ("srcPath".equals(entry.getKey())) {
					src = (String) entry.getValue() + "/bin/";
				}
				if ("classFileName".equals(entry.getKey())) {
					className = (String) entry.getValue();
				}
			}
			methodParams = ExtractClassParameter.generateJavaAPI(src, className);
		} catch (Exception e) {
			LOG.warn("getClassDetails(Map<?,?>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getClassDetails(Map<?,?>) - end"); //$NON-NLS-1$
		}
		return methodParams;
	}

	/** The Constant INPUT_ZIP_FILE. */
	private static final String INPUT_ZIP_FILE = "/var/lib/jenkins/uploadedFiles/";

	/** The Constant OUTPUT_FOLDER. */
	private static final String OUTPUT_FOLDER = "/var/lib/jenkins/uploadedFiles";
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
	 * Sets the main service.
	 * 
	 * @param mainService
	 *            the new main service
	 */
	public void setMainService(MainService mainService) {
		this.mainService = mainService;
	}
	
	File newFile = null;

	/**
	 * Upload excel util.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 * @throws ServiceException
	 *             the service exception
	 */
	@RequestMapping(value = "/uploadfileJunit", method = RequestMethod.POST)
	public @ResponseBody
	String uploadfileJunit(MultipartHttpServletRequest request, HttpServletResponse response) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("uploadFile(MultipartHttpServletRequest, HttpServletResponse) - start"); //$NON-NLS-1$
		}
		String path = null;
		String unzipfileName = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf = request.getFile(itr.next());
		String fileName = mpf.getOriginalFilename();
		try {
			inputStream = mpf.getInputStream();
			File newFile = new File(INPUT_ZIP_FILE + fileName);
			if (!newFile.exists()) {
				newFile.createNewFile();
			}
			path = newFile.getAbsolutePath();
			outputStream = new FileOutputStream(newFile);
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			ZipFile zipFile = new ZipFile(path);
			zipFile.extractAll(OUTPUT_FOLDER);
			unzipfileName = OUTPUT_FOLDER + File.separator + FilenameUtils.removeExtension(fileName);
		} catch (Exception e) {
			LOG.warn("uploadFile(MultipartHttpServletRequest, HttpServletResponse) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("uploadFile(MultipartHttpServletRequest, HttpServletResponse) - end"); //$NON-NLS-1$
		}
		return unzipfileName;
	}

	/**
	 * Adds the test java api steps.
	 * 
	 * @param javaApiData
	 *            the java api data
	 */
	@RequestMapping(value = "/addTestJavaApiSteps", method = RequestMethod.POST)
	public @ResponseBody long addTestJavaApiSteps(@RequestBody Map<String, String> javaApiData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addTestJavaApiSteps(Map<String,String>) - start"); //$NON-NLS-1$
		}
		int testStepId =0;
		try {
			JSONObject jsonObject = new JSONObject(javaApiData);
			int testDataId = (Integer) jsonObject.get("TestDataID");
			int app = (Integer) jsonObject.get("AppParamPopUpsName");
			Actions action = new Actions();
			action.setActionName("sample");
			action.setDescription("Desc-1");
			action.setCreatedBy(DataConstants.DEFAULT_USER);
			action.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			action.setUpdatedBy(DataConstants.DEFAULT_USER);
			action.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			int actionID = mainService.insertActionsGetKey(action);
			IdentifierType identifierType = new IdentifierType();
			identifierType.setIdentifierTypeName("JavaAPI Identifier");
			identifierType.setDescription("Desc-1");
			identifierType.setAppID(app);
			identifierType.setCreatedBy(DataConstants.DEFAULT_USER);
			identifierType.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			identifierType.setUpdatedBy(DataConstants.DEFAULT_USER);
			identifierType.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			int identifireTypeId = inputService.insertIdentifierTypeGetKey(identifierType);
			ObjectType objectType = new ObjectType();
			objectType.setObjectTypeName("JavaAPI Object");
			objectType.setDescription("Desc-2");
			objectType.setActionID(actionID);
			objectType.setCreatedBy(DataConstants.DEFAULT_USER);
			objectType.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			objectType.setUpdatedBy(DataConstants.DEFAULT_USER);
			objectType.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			int objectTypeId = inputService.insertObjectTypeGetKey(objectType);
			ObjectGroup objectGroup = new ObjectGroup();
			objectGroup.setObjectGroupName("JavaAPI object group");
			objectGroup.setDescription("Desc-1");
			objectGroup.setAppID(app);
			objectGroup.setScreenID(1093);
			objectGroup.setCreatedBy(DataConstants.DEFAULT_USER);
			objectGroup.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			objectGroup.setUpdatedBy(DataConstants.DEFAULT_USER);
			objectGroup.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			int objectGroupId = inputService.insertObjectGroupGetKey(objectGroup);
			Objects obj = new Objects();
			obj.setObjectName("JavaAPI object");
			obj.setDescription("desc");
			obj.setObjectGroupID(objectGroupId);
			obj.setObjectTypeID(objectTypeId);
			obj.setIdentifierTypeID(identifireTypeId);
			obj.setAppID(app);
			obj.setIdentifier("identifire");
			obj.setCreatedBy(DataConstants.DEFAULT_USER);
			obj.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			obj.setUpdatedBy(DataConstants.DEFAULT_USER);
			obj.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			inputService.insertObjectsGetKey(obj);
			ParamGroup paramGroup = new ParamGroup();
			paramGroup.setParamGroupName("ParamGroupName-JavaAPI");
			paramGroup.setDescription("Desc-1");
			paramGroup.setTag("tag-1");
			paramGroup.setAppID(app);
			paramGroup.setObjectGroupID(objectGroupId);
			paramGroup.setCreatedBy(DataConstants.DEFAULT_USER);
			paramGroup.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			paramGroup.setUpdatedBy(DataConstants.DEFAULT_USER);
			paramGroup.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			int paramGroupId = inputService.insertParamGroupGetKey(paramGroup);
			TestStep testStep = new TestStep();
			testStep.setStepName("JavaAPI");
			testStep.setDescription("desc");
			testStep.setTestStepType("junitRunner");
			testStep.setInputParamGroupID(paramGroupId);
			testStep.setCreatedBy(DataConstants.DEFAULT_USER);
			testStep.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			testStep.setActionID(actionID);
			testStep.setActive("Y");
			testStep.setUpdatedBy(DataConstants.DEFAULT_USER);
			testStep.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			testStepId = inputService.insertTestStepGetKey(testStep);
			for (Map.Entry<String, ?> entry : javaApiData.entrySet()) {
				if (!("AppParamPopUpsName".equals(entry.getKey()) && "testCaseID".equals(entry.getKey()))) {
					if ("METHODDATA".equals(entry.getKey())) {
						Param param = new Param();
						param.setParamName(entry.getKey());
						param.setDescription("Source path");
						param.setSortOrder(130);
						param.setParamGroupID(paramGroupId);
						param.setCreatedBy(DataConstants.DEFAULT_USER);
						param.setCreatedDateTime(DataConstants.DEFAULT_DATE);
						param.setUpdatedBy(DataConstants.DEFAULT_USER);
						param.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
						int paramId = inputService.insertParamGetKey(param);
						@SuppressWarnings("unchecked")
						JSONObject jsonMethodData = new JSONObject((Map<String, ?>) entry.getValue());
						TestParamData testParamData = new TestParamData();
						testParamData.setParamGroupID(paramGroupId);
						testParamData.setParamID(paramId);
						testParamData.setParamValue("");
						testParamData.setValueBig(jsonMethodData.toString());
						testParamData.setTestDataID(testDataId);
						testParamData.setCreatedBy(DataConstants.DEFAULT_USER);
						testParamData.setCreatedDateTime(DataConstants.DEFAULT_DATE);
						testParamData.setUpdatedBy(DataConstants.DEFAULT_USER);
						testParamData.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
						inputService.insertTestParamDataGetKey(testParamData);
					} else {
						Param param = new Param();
						param.setParamName(entry.getKey());
						param.setDescription("Source path");
						param.setSortOrder(130);
						param.setParamGroupID(paramGroupId);
						param.setCreatedBy(DataConstants.DEFAULT_USER);
						param.setCreatedDateTime(DataConstants.DEFAULT_DATE);
						param.setUpdatedBy(DataConstants.DEFAULT_USER);
						param.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
						int paramId = inputService.insertParamGetKey(param);
						TestParamData testParamData = new TestParamData();
						testParamData.setParamGroupID(paramGroupId);
						testParamData.setParamID(paramId);
						testParamData.setParamValue((String) entry.getValue());
						testParamData.setValueBig(jsonObject.toString());
						testParamData.setTestDataID(testDataId);
						testParamData.setCreatedBy(DataConstants.DEFAULT_USER);
						testParamData.setCreatedDateTime(DataConstants.DEFAULT_DATE);
						testParamData.setUpdatedBy(DataConstants.DEFAULT_USER);
						testParamData.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
						inputService.insertTestParamDataGetKey(testParamData);
					}
				}
			}
			
		} catch (Exception e) {
			LOG.warn("addTestJavaApiSteps(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("addTestJavaApiSteps(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return testStepId;
	}
	
	
	/**
	 * Adds the test steps.
	 * 
	 * @param junitData
	 *            the junit data
	 */
	
	@RequestMapping(value = "/addTestStepsJunit", method = RequestMethod.POST)
	public  @ResponseBody long addTestStepsJunit(@RequestBody Map<String, String>junitData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addTestSteps(Map<String,String>) - start"); //$NON-NLS-1$
		}
		int testStepId= 0;
		try {
			JSONObject jsonObject = new JSONObject(junitData);
			int app = (Integer) jsonObject.get("appId");
			int testDataId = (Integer) jsonObject.get("testDataID");
			Actions action = new Actions();
			action.setActionName("junitRun");
			action.setDescription("Desc-1");
			action.setCreatedBy(DataConstants.DEFAULT_USER);
			action.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			action.setUpdatedBy(DataConstants.DEFAULT_USER);
			action.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			int actionID = mainService.insertActionsGetKey(action);
			IdentifierType identifierType = new IdentifierType();
			identifierType.setIdentifierTypeName("Junit Identifier");
			identifierType.setDescription("Desc-1");
			identifierType.setAppID(app);
			identifierType.setCreatedBy(DataConstants.DEFAULT_USER);
			identifierType.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			identifierType.setUpdatedBy(DataConstants.DEFAULT_USER);
			identifierType.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			int identifireTypeId = inputService.insertIdentifierTypeGetKey(identifierType);
			ObjectType objectType = new ObjectType();
			objectType.setObjectTypeName("Junit Object");
			objectType.setDescription("Desc-2");
			objectType.setActionID(actionID);
			objectType.setCreatedBy(DataConstants.DEFAULT_USER);
			objectType.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			objectType.setUpdatedBy(DataConstants.DEFAULT_USER);
			objectType.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			int objectTypeId = inputService.insertObjectTypeGetKey(objectType);
			ObjectGroup objectGroup = new ObjectGroup();
			objectGroup.setObjectGroupName("junit object group");
			objectGroup.setDescription("Desc-1");
			objectGroup.setAppID(app);
			objectGroup.setScreenID(1093);
			objectGroup.setCreatedBy(DataConstants.DEFAULT_USER);
			objectGroup.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			objectGroup.setUpdatedBy(DataConstants.DEFAULT_USER);
			objectGroup.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			int objectGroupId = inputService.insertObjectGroupGetKey(objectGroup);
			Objects obj = new Objects();
			obj.setObjectName("junit object");
			obj.setDescription("desc");
			obj.setObjectGroupID(objectGroupId);
			obj.setObjectTypeID(objectTypeId);
			obj.setIdentifierTypeID(identifireTypeId);
			obj.setAppID(app);
			obj.setIdentifier("identifire");
			obj.setCreatedBy(DataConstants.DEFAULT_USER);
			obj.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			obj.setUpdatedBy(DataConstants.DEFAULT_USER);
			obj.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			int objectsId = inputService.insertObjectsGetKey(obj);
			ParamGroup paramGroup = new ParamGroup();
			paramGroup.setParamGroupName("ParamGroupName-1");
			paramGroup.setDescription("Desc-1");
			paramGroup.setTag("tag-1");
			paramGroup.setAppID(app);
			paramGroup.setObjectGroupID(objectGroupId);
			paramGroup.setCreatedBy(DataConstants.DEFAULT_USER);
			paramGroup.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			paramGroup.setUpdatedBy(DataConstants.DEFAULT_USER);
			paramGroup.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			int paramGroupId = inputService.insertParamGroupGetKey(paramGroup);
			TestStep testStep = new TestStep();
			testStep.setStepName("JunitSteps");
			testStep.setDescription("desc");
			testStep.setTestStepType("junitRunner");
			testStep.setInputParamGroupID(paramGroupId);
			testStep.setCreatedBy(DataConstants.DEFAULT_USER);
			testStep.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			testStep.setActionID(actionID);
			testStep.setActive("Y");
			testStep.setUpdatedBy(DataConstants.DEFAULT_USER);
			testStep.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			testStepId = inputService.insertTestStepGetKey(testStep);
			for (Map.Entry<String, ?> entry : junitData.entrySet()) {
				if (!("appId".equals(entry.getKey()) || "testCaseID".equals(entry.getKey()))) {
					Param param = new Param();
					param.setParamName(entry.getKey());
					param.setDescription("Source path");
					param.setSortOrder(1);
					param.setParamGroupID(paramGroupId);
					param.setObjectID(objectsId);
					param.setCreatedBy(DataConstants.DEFAULT_USER);
					param.setCreatedDateTime(DataConstants.DEFAULT_DATE);
					param.setUpdatedBy(DataConstants.DEFAULT_USER);
					int paramId = inputService.insertParamGetKey(param);
					TestParamData testParamData = new TestParamData();
					testParamData.setParamGroupID(paramGroupId);
					testParamData.setParamID(paramId);
					testParamData.setParamValue((String) entry.getValue());
					testParamData.setTestDataID(testDataId);
					testParamData.setCreatedBy(DataConstants.DEFAULT_USER);
					testParamData.setCreatedDateTime(DataConstants.DEFAULT_DATE);
					testParamData.setUpdatedBy(DataConstants.DEFAULT_USER);
					testParamData.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
					inputService.insertTestParamDataGetKey(testParamData);
				}
			}
		} catch (Exception e) {
			LOG.warn("addTestSteps(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("addTestSteps(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return testStepId;
	}
	
	
	/**
	 * Adds the test step http control.
	 * 
	 * @param testStepsData
	 *            the test steps data
	 * @return the long
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addTestStepsHttp", method = RequestMethod.POST)
	public @ResponseBody
	long addTestStepHTTPControl(@RequestBody Map<String, String> testStepsData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addTestStepHTTPControl(Map<String,String>) - start");
		}
		LOG.info("Entering addTestCases controller !!!");
		long testStepsId = 0;
		try {
			LOG.info("Printing data recieved from client TestStepsPopup :: " + testStepsData);
			JSONObject jsonObject = new JSONObject(testStepsData);
			String stepNames = (String) jsonObject.get("stepName");
			String description = (String) jsonObject.get("description");
			String expectedResults = (String) jsonObject.get("expectedResult");
			LinkedHashMap<Integer, String> positives = (LinkedHashMap<Integer, String>) jsonObject.get("testStepType");
			List<String> possitive = new ArrayList<String>(positives.values());
			String testStepTypes = String.valueOf(possitive.get(0));
			LinkedHashMap<Integer, String> actives = (LinkedHashMap<Integer, String>) jsonObject.get("active");
			List<String> activ = new ArrayList<String>(actives.values());
			String act = String.valueOf(activ.get(0));
			int sortOrders = (Integer) jsonObject.get("sortOrder");
			int preConditGroupIDs = (Integer) jsonObject.get("preConditionGroupID");
			int postConditGroupIDs = (Integer) jsonObject.get("postConditionGroupID");
			int inputParamGrIDs = (Integer) jsonObject.get("inputParamGroupID");
			int outputParamGrIDs = (Integer) jsonObject.get("outputParamGroupID");
			int actiIDs = (Integer) jsonObject.get("actionID");
			TestStep testSteps = new TestStep();
			testSteps.setStepName(stepNames);
			testSteps.setDescription(description);
			testSteps.setExpectedResult(expectedResults);
			testSteps.setTestStepType(testStepTypes);
			testSteps.setActive(act);
			testSteps.setSortOrder(sortOrders);
			testSteps.setPreConditionGroupID(preConditGroupIDs);
			testSteps.setPostConditionGroupID(postConditGroupIDs);
			testSteps.setInputParamGroupID(inputParamGrIDs);
			testSteps.setOutputParamGroupID(outputParamGrIDs);
			testSteps.setActionID(actiIDs);
			testSteps.setCreatedBy(DataConstants.DEFAULT_USER);
			testSteps.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			testSteps.setUpdatedBy(DataConstants.DEFAULT_USER);
			testSteps.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			LOG.info("testSteps Pojo String ::" + testSteps.toString());
			testStepsId = inputService.insertTestStepGetKey(testSteps);
			LOG.info("testStep ID ::" + testStepsId);
		} catch (Exception e) {
			LOG.error("addTestStepHTTPControl(Map<String,String>)", e);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("addTestStepHTTPControl(Map<String,String>) - end");
		}
		return testStepsId;
	}

	/**
	 * Gets the actions itap.
	 * 
	 * @return the actions itap
	 */
	@RequestMapping(value = "/getActionListITAP", method = RequestMethod.GET)
	public @ResponseBody
	List<Actions> getActionsITAP()  {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getActionsITAP() - start"); //$NON-NLS-1$
		}
		List<Actions> actions = new ArrayList<Actions>();
		try {
			actions = mainService.getActions();
		} catch (Exception e) {
			LOG.error("getActionsITAP()", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getActionsITAP() - end"); //$NON-NLS-1$
		}
		return actions;
	}

	/**
	 * Gets the all steps list by steps id.
	 * 
	 * @param stepID
	 *            the step id
	 * @return the all steps list by steps id
	 */
	@RequestMapping(value = "/getAllStepsListByStepsID", method = RequestMethod.POST)
	public @ResponseBody
	List<TestStep> getAllStepsListByStepsID(@RequestBody String stepID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllStepsListByStepsID(String) - start"); //$NON-NLS-1$
		}
		int stepsId = Integer.parseInt(stepID);
		List<TestStep> stepsList = new ArrayList<TestStep>();
		try {
			stepsList = inputService.getTestStepByStepsId(stepsId);
		} catch (ServiceException se) {
			LOG.error("getAllStepsListByStepsID(String)", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getAllStepsListByStepsID(String) - end"); //$NON-NLS-1$
		}
		return stepsList;
	}

}
