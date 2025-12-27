package com.tfw.exilant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
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

import com.exilant.tfw.exception.ServiceException;
import com.exilant.tfw.pojo.Actions;
import com.exilant.tfw.pojo.input.CaseStepMapping;
import com.exilant.tfw.pojo.input.ConditionGroup;
import com.exilant.tfw.pojo.input.IdentifierType;
import com.exilant.tfw.pojo.input.ObjectGroup;
import com.exilant.tfw.pojo.input.ObjectType;
import com.exilant.tfw.pojo.input.Objects;
import com.exilant.tfw.pojo.input.Param;
import com.exilant.tfw.pojo.input.ParamGroup;
import com.exilant.tfw.pojo.input.TestParamData;
import com.exilant.tfw.pojo.input.TestStep;
import com.exilant.tfw.service.InputService;
import com.exilant.tfw.service.MainService;
import com.exilant.tfw.utils.DataConstants;
import com.tfw.exilant.utils.ExtractClassParameter;

/**
 * The Class AddTestStepController.
 */
@Controller
public class AddTestStepController {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(AddTestStepController.class);

	/** The jb. */
	static StringBuffer jb = new StringBuffer();

	/** The input service. */
	private InputService inputService;

	/** The main service. */
	private MainService mainService;

	/** The file list. */
	List<String> fileList;

	/** The Constant INPUT_ZIP_FILE. */
	private static final String INPUT_ZIP_FILE = "/var/lib/jenkins/uploadedFiles/";

	/** The Constant OUTPUT_FOLDER. */
	private static final String OUTPUT_FOLDER = "/var/lib/jenkins/uploadedFiles";

	// private static final String INPUT_ZIP_FILE =
	// "/Users/anand.mathur/data/apache-tomcat-6.0.36/SrcDir/";
	// private static final String OUTPUT_FOLDER =
	// "/Users/anand.mathur/data/apache-tomcat-6.0.36/TargetDir";

	/** The class name. */
	private static String className;

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
	 * Gets the main service.
	 * 
	 * @return the main service
	 */
	public MainService getMainService() {
		return mainService;
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

	/**
	 * Upload file.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping(value = "/uploadfile1", method = RequestMethod.POST)
	public @ResponseBody
	String uploadFile(MultipartHttpServletRequest request, HttpServletResponse response) {
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
	 * Adds the test steps.
	 * 
	 * @param junitData
	 *            the junit data
	 */
	@RequestMapping(value = "/addTestSteps1", method = RequestMethod.POST)
	public void addTestSteps(@RequestBody Map<String, String> junitData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addTestSteps(Map<String,String>) - start"); //$NON-NLS-1$
		}
		try {
			JSONObject jsonObject = new JSONObject(junitData);
			int app = (Integer) jsonObject.get("appID");
			int testDataId = Integer.parseInt((String) jsonObject.get("testDataID"));
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
			int testStepId = inputService.insertTestStepGetKey(testStep);
			String testCaseIDs = (String) jsonObject.get("testCaseID");
			for (Map.Entry<String, ?> entry : junitData.entrySet()) {
				if (!("AppParamPopUpsName".equals(entry.getKey()) && "testCaseID".equals(entry.getKey()))) {
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
	}

	/**
	 * Gets the class details.
	 * 
	 * @param javaApiData
	 *            the java api data
	 * @return the class details
	 */
	@RequestMapping(value = "/getClassDetails1", method = RequestMethod.POST)
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

	/**
	 * Adds the test java api steps.
	 * 
	 * @param javaApiData
	 *            the java api data
	 */
	@RequestMapping(value = "/addTestJavaApiSteps1", method = RequestMethod.POST)
	@ResponseBody
	public void addTestJavaApiSteps(@RequestBody Map<String, String> javaApiData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addTestJavaApiSteps(Map<String,String>) - start"); //$NON-NLS-1$
		}
		try {
			JSONObject jsonObject = new JSONObject(javaApiData);
			int testDataId = Integer.parseInt((String) jsonObject.get("TestDataID"));
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
			int testStepId = inputService.insertTestStepGetKey(testStep);
			String testCaseIDs = (String) jsonObject.get("testCaseID");
			int caseId = Integer.parseInt(testCaseIDs);
			CaseStepMapping caseStepMapping = new CaseStepMapping();
			caseStepMapping.setTestCaseID(caseId);
			caseStepMapping.setTestStepID(testStepId);
			caseStepMapping.setCreatedBy(DataConstants.DEFAULT_USER);
			caseStepMapping.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			caseStepMapping.setUpdatedBy(DataConstants.DEFAULT_USER);
			caseStepMapping.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			inputService.insertCaseStepMappingGetKey(caseStepMapping);
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
	}

	/**
	 * Adds the test step control.
	 * 
	 * @param testStepsData
	 *            the test steps data
	 */
	@RequestMapping(value = "/addTestStepsApi", method = RequestMethod.POST)
	public @ResponseBody
	void addTestStepControl(@RequestBody Map<String, String> testStepsData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("addTestStepControl(Map<String,String>) - start"); //$NON-NLS-1$
		}
		try {
			JSONObject jsonObject = new JSONObject(testStepsData);
			String stepNames = (String) jsonObject.get("stepName");
			String description = (String) jsonObject.get("description");
			String expectedResults = (String) jsonObject.get("expectedResult");
			String testStepTypes = (String) jsonObject.get("testStepType");
			String actives = (String) jsonObject.get("active");
			int sortOrders = (Integer) jsonObject.get("sortOrder");
			String preConditionGroupIDs = (String) jsonObject.get("preConditionGroupID");
			int preConditGroupIDs = Integer.parseInt(preConditionGroupIDs);
			String postConditionGroupIDs = (String) jsonObject.get("postConditionGroupID");
			int postConditGroupIDs = Integer.parseInt(postConditionGroupIDs);
			String inputParamGroupIDs = (String) jsonObject.get("inputParamGroupID");
			int inputParamGrIDs = Integer.parseInt(inputParamGroupIDs);
			String outputParamGroupIDs = (String) jsonObject.get("outputParamGroupID");
			int outputParamGrIDs = Integer.parseInt(outputParamGroupIDs);
			String actionIDs = (String) jsonObject.get("actionID");
			int actiIDs = Integer.parseInt(actionIDs);
			TestStep testSteps = new TestStep();
			testSteps.setStepName(stepNames);
			testSteps.setDescription(description);
			testSteps.setExpectedResult(expectedResults);
			testSteps.setTestStepType(testStepTypes);
			testSteps.setActive(actives);
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
			long testStepsId = inputService.insertTestStepGetKey(testSteps);
			CaseStepMapping caseSteps = new CaseStepMapping();
			int testStsId = (int) testStepsId;
			String testCaseIDs = (String) jsonObject.get("testCaseID");
			int CassId = Integer.parseInt(testCaseIDs);
			caseSteps.setTestCaseID(CassId);
			caseSteps.setTestStepID(testStsId);
			caseSteps.setCreatedBy(DataConstants.DEFAULT_USER);
			caseSteps.setCreatedDateTime(DataConstants.DEFAULT_DATE);
			caseSteps.setUpdatedBy(DataConstants.DEFAULT_USER);
			caseSteps.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			inputService.insertCaseStepMappingGetKey(caseSteps);
		} catch (Exception e) {
			LOG.warn("addTestStepControl(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("addTestStepControl(Map<String,String>) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Gets the condition group names by app id.
	 * 
	 * @param appID
	 *            the app id
	 * @return the condition group names by app id
	 * @throws ServiceException
	 *             the service exception
	 */
	@RequestMapping(value = "/getConditionGroupNamesByAppId", method = RequestMethod.POST)
	public @ResponseBody
	List<ConditionGroup> getConditionGroupNamesByAppId(@RequestBody String appID) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getConditionGroupNamesByAppId(String) - start"); //$NON-NLS-1$
		}
		int appId = Integer.parseInt(appID);
		List<ConditionGroup> ConditionName = new ArrayList<ConditionGroup>();
		try {
			ConditionName = inputService.getConditionGroupNamesByAppId(appId);
		} catch (ServiceException se) {
			LOG.warn("getConditionGroupNamesByAppId(String) - exception ignored", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getConditionGroupNamesByAppId(String) - end"); //$NON-NLS-1$
		}
		return ConditionName;
	}

	/**
	 * Gets the param group names by app id.
	 * 
	 * @param appID
	 *            the app id
	 * @return the param group names by app id
	 * @throws ServiceException
	 *             the service exception
	 */
	@RequestMapping(value = "/getParamGroupNamesByAppId", method = RequestMethod.POST)
	public @ResponseBody
	List<ParamGroup> getParamGroupNamesByAppId(@RequestBody String appID) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamGroupNamesByAppId(String) - start"); //$NON-NLS-1$
		}
		int appId = Integer.parseInt(appID);
		List<ParamGroup> ParamName = new ArrayList<ParamGroup>();
		try {
			ParamName = inputService.getParamGroupNamesByAppId(appId);
		} catch (ServiceException se) {
			LOG.warn("getParamGroupNamesByAppId(String) - exception ignored", se); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamGroupNamesByAppId(String) - end"); //$NON-NLS-1$
		}
		return ParamName;
	}

	/**
	 * Gets the actions.
	 * 
	 * @return the actions
	 * @throws ServiceException
	 *             the service exception
	 */
	@RequestMapping(value = "/getActionList", method = RequestMethod.GET)
	public @ResponseBody
	List<Actions> getActions() throws ServiceException {
		return mainService.getActions();
	}

	/**
	 * Edits the test steps control.
	 * 
	 * @param testStep
	 *            the test step
	 * @return the test step
	 */
	@RequestMapping(value = "/editTestSteps", method = RequestMethod.POST)
	public @ResponseBody
	TestStep editTestStepsControl(@RequestBody Map<String, Integer> testStep) {
		JSONObject jsonObject = new JSONObject(testStep);
		int testStepId = (Integer) jsonObject.get("testStepID");
		TestStep tSteps = new TestStep();
		try {
			tSteps = inputService.getTestStepById(testStepId);
		} catch (ServiceException se) {
			LOG.warn("editTestStepsControl(Map<String,Integer>) - exception ignored", se); //$NON-NLS-1$
		}
		return tSteps;
	}

	/**
	 * Update test steps.
	 * 
	 * @param testStepsDatas
	 *            the test steps datas
	 * @return the long
	 */
	@RequestMapping(value = "/updateTestSteps", method = RequestMethod.POST)
	public @ResponseBody
	long updateTestSteps(@RequestBody Map<String, String> testStepsDatas) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestSteps(Map<String,String>) - start"); //$NON-NLS-1$
		}
		long update = 0;
		try {
			JSONObject jsonObject = new JSONObject(testStepsDatas);
			String stepNames = (String) jsonObject.get("stepName");
			String description = (String) jsonObject.get("description");
			String expectedResults = (String) jsonObject.get("expectedResult");
			String testStepTypes = (String) jsonObject.get("testStepType");
			String actives = (String) jsonObject.get("active");
			int sortOrders = (Integer) jsonObject.get("sortOrder");
			int stepsIDs = (Integer) jsonObject.get("testStepID");
			String preConditionGroupIDs = (String) jsonObject.get("preConditionGroupID");
			int preConditGroupIDs = Integer.parseInt(preConditionGroupIDs);
			String postConditionGroupIDs = (String) jsonObject.get("postConditionGroupID");
			int postConditGroupIDs = Integer.parseInt(postConditionGroupIDs);
			String inputParamGroupIDs = (String) jsonObject.get("inputParamGroupID");
			int inputParamGrIDs = Integer.parseInt(inputParamGroupIDs);
			String outputParamGroupIDs = (String) jsonObject.get("outputParamGroupID");
			int outputParamGrIDs = Integer.parseInt(outputParamGroupIDs);
			String actionIDs = (String) jsonObject.get("actionID");
			int actiIDs = Integer.parseInt(actionIDs);
			TestStep testSteps = new TestStep();
			testSteps.setStepName(stepNames);
			testSteps.setDescription(description);
			testSteps.setExpectedResult(expectedResults);
			testSteps.setTestStepType(testStepTypes);
			testSteps.setActive(actives);
			testSteps.setSortOrder(sortOrders);
			testSteps.setTestStepID(stepsIDs);
			testSteps.setPreConditionGroupID(preConditGroupIDs);
			testSteps.setPostConditionGroupID(postConditGroupIDs);
			testSteps.setInputParamGroupID(inputParamGrIDs);
			testSteps.setOutputParamGroupID(outputParamGrIDs);
			testSteps.setActionID(actiIDs);
			testSteps.setUpdatedBy(DataConstants.DEFAULT_USER);
			testSteps.setUpdatedDateTime(DataConstants.DEFAULT_DATE);
			update = inputService.updateTestStep(testSteps);
		} catch (Exception e) {
			LOG.warn("updateTestSteps(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("updateTestSteps(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return update;
	}

	/**
	 * Gets the param group by id.
	 * 
	 * @param paramGroupID
	 *            the param group id
	 * @return the param group by id
	 * @throws ServiceException
	 *             the service exception
	 */
	@RequestMapping(value = "/getParamGroupNamesByParamId", method = RequestMethod.POST)
	public @ResponseBody
	ParamGroup getParamGroupById(@RequestBody String paramGroupID) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamGroupById(String) - start"); //$NON-NLS-1$
		}
		int paramGroupIDs = Integer.parseInt(paramGroupID);
		ParamGroup params = new ParamGroup();
		try {
			params = inputService.getParamGroupById(paramGroupIDs);
		} catch (Exception e) {
			LOG.warn("getParamGroupById(String) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getParamGroupById(String) - end"); //$NON-NLS-1$
		}
		return params;
	}

	/**
	 * Gets the condition details by id.
	 * 
	 * @param conditionId
	 *            the condition id
	 * @return the condition details by id
	 */
	@RequestMapping(value = "/getConditionGroupByConditionId", method = RequestMethod.POST)
	public @ResponseBody
	ConditionGroup getConditionDetailsById(@RequestBody String conditionId) {
		int preConditGroupIDs = Integer.parseInt(conditionId);
		ConditionGroup conditions = new ConditionGroup();
		try {
			conditions = inputService.getConditionGroupById(preConditGroupIDs);
		} catch (Exception e) {
			LOG.warn("getConditionDetailsById(String) - exception ignored", e); //$NON-NLS-1$
		}
		return conditions;
	}

	/**
	 * Gets the actions by id.
	 * 
	 * @param actionsId
	 *            the actions id
	 * @return the actions by id
	 */
	@RequestMapping(value = "/getActionsNameById", method = RequestMethod.POST)
	public @ResponseBody
	Actions getActionsById(@RequestBody String actionsId) {
		int actionIDs = Integer.parseInt(actionsId);
		Actions actions = new Actions();
		try {
			actions = mainService.getActionsById(actionIDs);
		} catch (Exception e) {
			LOG.warn("getActionsById(String) - exception ignored", e); //$NON-NLS-1$
		}
		return actions;
	}
}