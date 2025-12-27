package com.sssoft.isatt.data.excelreader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.collections.map.MultiValueMap;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.sssoft.isatt.data.exception.ExcelUtilityException;
import com.sssoft.isatt.data.exception.ServiceException;
import com.sssoft.isatt.data.pojo.Actions;
import com.sssoft.isatt.data.pojo.AppFeature;
import com.sssoft.isatt.data.pojo.AppFunctionality;
import com.sssoft.isatt.data.pojo.Application;
import com.sssoft.isatt.data.pojo.ExcelImport;
import com.sssoft.isatt.data.pojo.Runner;
import com.sssoft.isatt.data.pojo.Screen;
import com.sssoft.isatt.data.pojo.input.CaseStepMapping;
import com.sssoft.isatt.data.pojo.input.ConditionGroup;
import com.sssoft.isatt.data.pojo.input.Conditions;
import com.sssoft.isatt.data.pojo.input.IdentifierType;
import com.sssoft.isatt.data.pojo.input.ObjectGroup;
import com.sssoft.isatt.data.pojo.input.ObjectType;
import com.sssoft.isatt.data.pojo.input.Objects;
import com.sssoft.isatt.data.pojo.input.Param;
import com.sssoft.isatt.data.pojo.input.ParamGroup;
import com.sssoft.isatt.data.pojo.input.PlanSuiteMapping;
import com.sssoft.isatt.data.pojo.input.ScenarioCaseMapping;
import com.sssoft.isatt.data.pojo.input.SuiteScenarioMapping;
import com.sssoft.isatt.data.pojo.input.TestCase;
import com.sssoft.isatt.data.pojo.input.TestConditionData;
import com.sssoft.isatt.data.pojo.input.TestData;
import com.sssoft.isatt.data.pojo.input.TestParamData;
import com.sssoft.isatt.data.pojo.input.TestPlan;
import com.sssoft.isatt.data.pojo.input.TestScenario;
import com.sssoft.isatt.data.pojo.input.TestStep;
import com.sssoft.isatt.data.pojo.input.TestSuite;
import com.sssoft.isatt.data.utils.DataConstants;
import com.sssoft.isatt.utils.util.ExcelAccess;

/**
 * The Class ReadPlan.
 */
public class ReadPlan {

	/** The Constant logger. */
	private static final Logger LOG = Logger.getLogger(ReadPlan.class);

	/** The excel access. */
	private ExcelAccess excelAccess = new ExcelAccess();

	/** The application. */
	private Application application = new Application();

	/** The tc start row. */
	private int tcStartRow;

	/** The multi value map. */
	private MultiValueMap multiValueMap;

	private int testCaseNameCol;

	private int actionColNo;

	private int stepParamColNo;

	private int executeColNo;

	private int paramGrpColNo;

	private int decColNo;

	private int runnerTypeColNo;

	private int stepTypeColNo;

	private int testClassficColNo;

	private int testCaseTitleColNo;

	private int valueColNo;

	private String msg;

	// Ankur-Squish-block-start-tag
	/** The load prop. */
	Properties loadProp = new Properties();

	/** The is squish. */
	String isSquish;

	/**
	 * Instantiates a new read plan.
	 * 
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public ReadPlan() {
		super();
	}
	
	/**
	 * Read plan obj.
	 * 
	 * @param excelImport
	 *            the excel import
	 * @param tfwRemote
	 *            the tfw remote
	 * @return the application
	 * @throws InvalidFormatException
	 *             the invalid format exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ServiceException
	 *             the service exception
	 * @throws ExcelUtilityException
	 *             the excel utility exception
	 */
	public Application readPlanObj(ExcelImport excelImport)
			throws ExcelUtilityException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("readPlanObj(ExcelImport) - start"); //$NON-NLS-1$
		}

		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("readPlanObj(ExcelImport) - start"); //$NON-NLS-1$
			}

			File file = excelImport.getExcelFile();
			LOG.info("File Path : " + file.toString());
			excelAccess.openWorkBook(file);
			String sheetName = null;
			int sheetsCount = excelAccess.getNumberOfSheets();
			if (sheetsCount == 2) {
				for (int sheetCounter = 0; sheetCounter < sheetsCount; sheetCounter++) {
					if (!(excelAccess.getSheetNames(sheetCounter)
							.equals(DataConstants.OBJECTS))) {
						sheetName = excelAccess.getSheetNames(sheetCounter);
					}
				}
				setTcStartRow(getTCStrtRow(excelAccess, sheetName));
				application = strtReadPlanObject(excelImport, excelAccess,
						sheetName);

			} else {
				LOG.info(DataConstants.ERROR_SHEET);
				application = null;
				throw new ExcelUtilityException(DataConstants.ERROR_SHEET);
			}

			if (LOG.isDebugEnabled()) {
				LOG.debug("readPlanObj(ExcelImport) - end"); //$NON-NLS-1$
			}
			return application;
		} catch (ExcelUtilityException e) {
			LOG.error(DataConstants.ERROR_IMPORT, e); //$NON-NLS-1$
			throw new ExcelUtilityException(e.getMessage());
		}
	}

	/**
	 * Strt read plan object.
	 * 
	 * @param excelImport
	 *            the excel import
	 * @param tfwRemote
	 *            the tfw remote
	 * @param excelAccess
	 *            the excel access
	 * @param sheetName
	 *            the sheet name
	 * @param columnIndexMap
	 *            the column index map
	 * @return the application
	 * @throws InvalidFormatException
	 *             the invalid format exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws ExcelUtilityException
	 *             the excel utility exception
	 */
	private Application strtReadPlanObject(ExcelImport excelImport,
			ExcelAccess excelAccess, String sheetName)
			throws ExcelUtilityException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("strtReadPlanObject(ExcelImport, ExcelAccess, String) - start"); //$NON-NLS-1$
		}

		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("strtReadPlanObject(ExcelImport,ExcelAccess, String, Map<String,Integer>) - start"); //$NON-NLS-1$
			}
			setTestCaseNameCol(1);
			setActionColNo(8);
			setStepParamColNo(13);
			setExecuteColNo(9);
			setParamGrpColNo(12);
			setDecColNo(5);
			setRunnerTypeColNo(6);
			setStepTypeColNo(7);
			setTestClassficColNo(2);
			setTestCaseTitleColNo(4);
			setValueColNo(14);
			List<AppFeature> appFeatureList = new ArrayList<AppFeature>();
			List<AppFunctionality> functionalList = new ArrayList<AppFunctionality>();
			if (excelAccess.isSheetExists(DataConstants.OBJECTS)) {
				multiValueMap = objectsRetrieval(excelAccess,
						DataConstants.OBJECTS);
			} else {
				LOG.info(DataConstants.OBJECTS + DataConstants.SHEET_NOT_EXIST);
				throw new ExcelUtilityException(DataConstants.OBJECTS
						+ DataConstants.SHEET_NOT_EXIST);
			}
			List<Screen> screenList = createScreens(multiValueMap);
			List<IdentifierType> identifierTypeList = createIdentifierTypes(multiValueMap);
			application.setIdentifierType(identifierTypeList);
			AppFeature appFeature = featureCreation(excelImport, screenList);
			appFeatureList.add(appFeature);
			AppFunctionality functional = createFunctional(excelImport,
					appFeatureList);
			functional.setAppFeature(appFeatureList);
			functionalList.add(functional);
			String testApp = excelImport.getApplicationName();
			application.setAppName(testApp);
			application.setDescription(testApp + DataConstants.DESCRIPTION);
			List<TestData> testDataList = new ArrayList<TestData>();
			TestData testData = testDataDescription(excelImport);
			testDataList.add(testData);
			application.setTestData(testDataList);
			application.setAppFunctionality(functionalList);
			List<TestPlan> testPlanList = new ArrayList<TestPlan>();
			TestPlan testPlan = testPlanDescription(excelImport, excelAccess,
					sheetName, appFeature, functional, testData);
			testPlanList.add(testPlan);
			application.setTestPlan(testPlanList);

			if (LOG.isDebugEnabled()) {
				LOG.debug("strtReadPlanObject(ExcelImport, TfwRemoteInterface2, ExcelAccess, String, Map<String,Integer>) - end"); //$NON-NLS-1$
			}
			return application;
		} catch (ExcelUtilityException e) {
			LOG.error(DataConstants.ERROR_IMPORT, e); //$NON-NLS-1$
			throw new ExcelUtilityException(e.getMessage());
		}
	}

	/**
	 * @param excelImport
	 * @param excelAccess
	 * @param sheetName
	 * @param appFeature
	 * @param functional
	 * @param testData
	 * @return
	 * @throws ExcelUtilityException
	 */
	private TestPlan testPlanDescription(ExcelImport excelImport,
			ExcelAccess excelAccess, String sheetName, AppFeature appFeature,
			AppFunctionality functional, TestData testData)
			throws ExcelUtilityException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("testPlanDescription(ExcelImport, ExcelAccess, String, AppFeature, AppFunctionality, TestData) - start"); //$NON-NLS-1$
		}

		TestPlan testPlan = new TestPlan();
		List<PlanSuiteMapping> planSuiteMappingsList = new ArrayList<PlanSuiteMapping>();
		PlanSuiteMapping planSuiteMapping = new PlanSuiteMapping();
		TestSuite testSuite = testSuiteDescription(excelImport, excelAccess,
				sheetName, appFeature, functional, testData);
		planSuiteMapping.setTestSuite(testSuite);
		planSuiteMappingsList.add(planSuiteMapping);
		testPlan.setPlanSuiteMappings(planSuiteMappingsList);
		testPlan.setPlanName(excelImport.getTestPlanName());
		testPlan.setDescription(excelImport.getTestPlanName()
				+ " plan description ");

		if (LOG.isDebugEnabled()) {
			LOG.debug("testPlanDescription(ExcelImport, ExcelAccess, String, AppFeature, AppFunctionality, TestData) - end"); //$NON-NLS-1$
		}
		return testPlan;
	}

	/**
	 * @param excelImport
	 * @param excelAccess
	 * @param sheetName
	 * @param appFeature
	 * @param functional
	 * @param testData
	 * @return
	 * @throws ExcelUtilityException
	 */
	private TestSuite testSuiteDescription(ExcelImport excelImport,
			ExcelAccess excelAccess, String sheetName, AppFeature appFeature,
			AppFunctionality functional, TestData testData)
			throws ExcelUtilityException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("testSuiteDescription(ExcelImport, ExcelAccess, String, AppFeature, AppFunctionality, TestData) - start"); //$NON-NLS-1$
		}

		TestSuite testSuite = suiteCreation(excelImport);
		List<SuiteScenarioMapping> suiteScenarioMappingList = new ArrayList<SuiteScenarioMapping>();
		SuiteScenarioMapping suiteScenarioMappings = new SuiteScenarioMapping();
		TestScenario testscenario = testScenarioDescription(excelImport,
				excelAccess, sheetName, appFeature, functional, testData);
		suiteScenarioMappings.setTestScenario(testscenario);
		suiteScenarioMappingList.add(suiteScenarioMappings);
		testSuite.setSuiteScenarioMappings(suiteScenarioMappingList);

		if (LOG.isDebugEnabled()) {
			LOG.debug("testSuiteDescription(ExcelImport, ExcelAccess, String, AppFeature, AppFunctionality, TestData) - end"); //$NON-NLS-1$
		}
		return testSuite;
	}

	/**
	 * @param excelImport
	 * @param excelAccess
	 * @param sheetName
	 * @param appFeature
	 * @param functional
	 * @param testData
	 * @return
	 * @throws ExcelUtilityException
	 */
	private TestScenario testScenarioDescription(ExcelImport excelImport,
			ExcelAccess excelAccess, String sheetName, AppFeature appFeature,
			AppFunctionality functional, TestData testData)
			throws ExcelUtilityException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("testScenarioDescription(ExcelImport, ExcelAccess, String, AppFeature, AppFunctionality, TestData) - start"); //$NON-NLS-1$
		}

		TestScenario testscenario = new TestScenario();
		testscenario.setScenarioCaseMappings(createCaseList(excelAccess,
				sheetName, testData, functional, appFeature));
		testscenario.setTestScenarioName(excelImport.getTestScenarioName());
		testscenario.setDescription(excelImport.getTestScenarioName()
				+ " scenario description");

		if (LOG.isDebugEnabled()) {
			LOG.debug("testScenarioDescription(ExcelImport, ExcelAccess, String, AppFeature, AppFunctionality, TestData) - end"); //$NON-NLS-1$
		}
		return testscenario;
	}

	/**
	 * @param excelImport
	 * @return
	 */
	private TestData testDataDescription(ExcelImport excelImport) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("testDataDescription(ExcelImport) - start"); //$NON-NLS-1$
		}

		TestData testData = new TestData();
		String testDataDescription = excelImport.getDataSetDescription();
		testData.setTestDataDescription(testDataDescription);
		testData.setStatus("Y");

		if (LOG.isDebugEnabled()) {
			LOG.debug("testDataDescription(ExcelImport) - end"); //$NON-NLS-1$
		}
		return testData;
	}

	/**
	 * Gets the object attributes.
	 * 
	 * @param excelAccess
	 *            the excel access
	 * @param objectSheet
	 *            the object sheet
	 * @return the object attributes
	 * @throws InvalidFormatException
	 *             the invalid format exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public MultiValueMap objectsRetrieval(ExcelAccess excelAccess,
			String objectSheet) throws ExcelUtilityException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("objectsRetrieval(ExcelAccess, String) - start"); //$NON-NLS-1$
		}

		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("getObjectAttributes(ExcelAccess, String) - start"); //$NON-NLS-1$
			}

			MultiValueMap multimap = objectMap(excelAccess, objectSheet);

			if (LOG.isDebugEnabled()) {
				LOG.debug("getObjectAttributes(ExcelAccess, String) - end"); //$NON-NLS-1$
			}
			return multimap;
		} catch (Exception e) {
			LOG.error(DataConstants.ERROR_IMPORT, e); //$NON-NLS-1$
			throw new ExcelUtilityException(getMsg());
		}
	}

	/**
	 * @param excelAccess
	 * @param objectSheet
	 * @param msg
	 * @param objTcStartRow
	 * @param tcEndRow
	 * @param objectMaxRows
	 * @param foundStart
	 * @param foundEndObjects
	 * @param multimap
	 * @return
	 * @throws ExcelUtilityException
	 */
	private MultiValueMap objectMap(ExcelAccess excelAccess, String objectSheet)
			throws ExcelUtilityException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("objectMap(ExcelAccess, String) - start"); //$NON-NLS-1$
		}

		String curValue;
		int objTcStartRow = 1;
		int tcEndRow = 1;
		int objectMaxRows = 0;
		int rowCtr;
		boolean foundStart = false;
		boolean foundEndObjects = false;
		MultiValueMap multimap = new MultiValueMap();

		while (true) {
			for (rowCtr = objTcStartRow; rowCtr <= excelAccess
					.getTotalRowCount(objectSheet); rowCtr++) {
				curValue = excelAccess.getCellValue(objectSheet, rowCtr,
						DataConstants.DEFAULT_VAL);
				if (!foundStart
						&& curValue.equals(DataConstants.OBJECT_MAP_START)) {
					objTcStartRow = rowCtr + 1;
					foundStart = true;
				}
				if (!foundEndObjects
						&& curValue.equals(DataConstants.OBJECT_MAPPING_ENDS)) {
					foundEndObjects = true;
					objectMaxRows = rowCtr + 1;
				}
			}
			if (!foundEndObjects) {
				LOG.error(DataConstants.OBJECT_MAPPING_END_NOT_FOUND);
				setMsg(DataConstants.OBJECT_MAPPING_END_NOT_FOUND);
				break;

			}
			if (!foundStart) {
				LOG.error(DataConstants.OBJECT_MAPPING_START_NOT_FOUND);
				setMsg(DataConstants.OBJECT_MAPPING_START_NOT_FOUND);
				break;
			}
			for (rowCtr = objTcStartRow; rowCtr <= objectMaxRows; rowCtr++) {

				curValue = excelAccess.getCellValue(objectSheet, rowCtr,
						DataConstants.DEFAULT_VAL);
				if (curValue.equals(DataConstants.OBJECT_MAP_END)) {
					tcEndRow = (rowCtr - 1);
					break;
				}
				if (curValue.length() != 0) {
					multimap = objectMultiMap(excelAccess, objectSheet, rowCtr,multimap);
				}
			}
			objTcStartRow = (tcEndRow + 2);
			if (excelAccess.getTotalRowCount(objectSheet) == objTcStartRow) {
				LOG.info(DataConstants.OBJECT_MAPPING_ENDS_HERE);
				break;
			}
			foundStart = false;
		}
		if (getMsg() != null) {
			throw new ExcelUtilityException(getMsg());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("objectMap(ExcelAccess, String) - end"); //$NON-NLS-1$
		}
		return multimap;
	}

	/**
	 * @param excelAccess
	 * @param objectSheet
	 * @param rowCtr
	 * @param multimap
	 */
	private MultiValueMap objectMultiMap(ExcelAccess excelAccess,
			String objectSheet, int rowCtr, MultiValueMap multimap) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("objectMultiMap(ExcelAccess, String, int, MultiValueMap) - start"); //$NON-NLS-1$
		}

		String objectPath;
		String screenName;
		String identifierType;
		String formNameColumn;
		String objectGroup;
		formNameColumn = excelAccess.getCellValue(objectSheet, rowCtr,
				DataConstants.DEFAULT_VAL);
		objectPath = excelAccess.getCellValue(objectSheet, rowCtr,
				DataConstants.DEFAULT_VAL + 1);
		screenName = excelAccess.getCellValue(objectSheet, rowCtr,
				DataConstants.DEFAULT_VAL + 2);
		objectGroup = excelAccess.getCellValue(objectSheet, rowCtr,
				DataConstants.DEFAULT_VAL + 3);

		identifierType = excelAccess.getCellValue(objectSheet, rowCtr,
				DataConstants.DEFAULT_VAL + 4);
		multimap.put(formNameColumn, objectPath);
		multimap.put(formNameColumn, screenName);
		multimap.put(formNameColumn, objectGroup);
		multimap.put(formNameColumn, identifierType);

		if (LOG.isDebugEnabled()) {
			LOG.debug("objectMultiMap(ExcelAccess, String, int, MultiValueMap) - end"); //$NON-NLS-1$
		}
		return multimap;
	}

	/**
	 * Creates the screens.
	 * 
	 * @param multiValueMap2
	 *            the multi value map2
	 * @return the list
	 */
	private List<Screen> createScreens(MultiValueMap multiValueMap2) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("createScreens(MultiValueMap) - start"); //$NON-NLS-1$
		}

		List<Screen> screensList = new ArrayList<Screen>();
		Set keySet = multiValueMap2.keySet();
		List<String> values = null;
		Iterator<String> keyIterator = keySet.iterator();
		while (keyIterator.hasNext()) {
			Screen screen = new Screen();
			String key = (String) keyIterator.next();
			values = (List) multiValueMap2.get(key);
			screen.setScreenName((String) values.get(1));
			screen.setDescription((String) values.get(1)
					+ DataConstants.DESCRIPTION);
			screensList.add(screen);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("createScreens(MultiValueMap) - end"); //$NON-NLS-1$
		}
		return screensList;
	}

	/**
	 * Creates the identifier types.
	 * 
	 * @param multiValueMap2
	 *            the multi value map2
	 * @return the list
	 */
	private List<IdentifierType> createIdentifierTypes(
			MultiValueMap multiValueMap2) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("createIdentifierTypes(MultiValueMap) - start"); //$NON-NLS-1$
		}

		List<IdentifierType> identifierTypeList = new ArrayList<IdentifierType>();
		Set keySet = multiValueMap2.keySet();
		List values = null;
		Iterator keyIterator = keySet.iterator();
		while (keyIterator.hasNext()) {
			IdentifierType identifierType = new IdentifierType();
			String key = (String) keyIterator.next();
			values = (List) multiValueMap2.get(key);
			identifierType.setIdentifierTypeName((String) values.get(3));
			identifierTypeList.add(identifierType);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("createIdentifierTypes(MultiValueMap) - end"); //$NON-NLS-1$
		}
		return identifierTypeList;
	}

	/**
	 * Creates the case list.
	 * 
	 * @param tfwRemote
	 *            the tfw remote
	 * @param excelAccess
	 *            the excel access
	 * @param sheetName
	 *            the sheet name
	 * @param testCaseNameCol
	 *            the test case name col
	 * @param actionColNo
	 *            the action col no
	 * @param stepParamColNo
	 *            the step param col no
	 * @param executeColNo
	 *            the execute col no
	 * @param paramGrpColNo
	 *            the param grp col no
	 * @param decColNo
	 *            the dec col no
	 * @param runnerTypeColNo
	 *            the runner type col no
	 * @param stepTypeColNo
	 *            the step type col no
	 * @param testClassficColNo
	 *            the test classfic col no
	 * @param testCaseScenarioColNo
	 *            the test case scenario col no
	 * @param testCaseTitleColNo
	 *            the test case title col no
	 * @param valueColNo
	 *            the value col no
	 * @param testScenario
	 *            the test scenario
	 * @param testData
	 *            the test data
	 * @param functional
	 *            the functional
	 * @param appFeature
	 *            the app fearure
	 * @return the list
	 * @throws InvalidFormatException
	 *             the invalid format exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private List<ScenarioCaseMapping> createCaseList(ExcelAccess excelAccess,
			String sheetName, TestData testData, AppFunctionality functional,
			AppFeature appFeature) throws ExcelUtilityException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("createCaseList(ExcelAccess, String, TestData, AppFunctionality, AppFeature) - start"); //$NON-NLS-1$
		}

		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("createCaseList(TfwRemoteInterface2, ExcelAccess, String, int, int, int, int, int, int, int, int, int, int, int, int, TestScenario, TestData, AppFunctionality, AppFeature) - start"); //$NON-NLS-1$
			}

			int tcEndRow = 1;
			int rowCtr = 0;
			boolean foundStart = false;
			boolean foundEnd = false;
			boolean foundEndSuite = false;
			List<ScenarioCaseMapping> scenarioCaseMappingList = new ArrayList<ScenarioCaseMapping>();
			int totalRowCount = excelAccess.isSheetExists(sheetName) ? excelAccess
					.getTotalRowCount(sheetName) : DataConstants.DEFAULT_VAL; // Ankur
			int endOfCases = totalRowCount;
			while (true) {
				for (rowCtr = getTcStartRow(); rowCtr <= totalRowCount; rowCtr++) {
					String curValue = excelAccess.getCellValue(sheetName,
							rowCtr, getStepTypeColNo());

					/* Get the value from read */
					if (!foundStart && curValue.equals(DataConstants.TC_START)) {
						setTcStartRow(rowCtr + 1);
						foundStart = true;
					}

					if (!foundEnd && curValue.equals(DataConstants.TC_END)) {

						tcEndRow = (rowCtr - 1);
						foundEnd = true;
					}
					if (!foundEndSuite
							&& curValue
									.equals(DataConstants.TC_EXECUTION_ENDS_HERE)) {
						endOfCases = rowCtr;
						foundEndSuite = true;
					}
				}
				if (!foundEndSuite) {
					LOG.info(DataConstants.TC_EXECUTION_ENDS_HERE_NOT_FOUND);
					break;
				}
				if (!foundStart) {
					LOG.info(DataConstants.TC_START_NOT_FOUND);
					break;
				}
				if (!foundEnd) {
					LOG.info(DataConstants.TC_END_NOT_FOUND);
					break;
				}

				ScenarioCaseMapping scenarioCaseMapping = new ScenarioCaseMapping();
				TestCase testCase = caseDescription(functional, appFeature,
						sheetName);
				TestCase tcase = testCaseCreation(excelAccess, sheetName,
						tcEndRow, testData, testCase);
				scenarioCaseMapping.setTestCase(tcase);
				scenarioCaseMappingList.add(scenarioCaseMapping);
				setTcStartRow(tcEndRow + 2);
				if (tcEndRow + 2 >= endOfCases) {
					LOG.info("Reading plan execution ends here");
					break;
				}
				foundStart = false;
				foundEnd = false;
			}

			if (LOG.isDebugEnabled()) {
				LOG.debug("createCaseList(TfwRemoteInterface2, ExcelAccess, String, int, int, int, int, int, int, int, int, int, int, int, int, TestScenario, TestData, AppFunctionality, AppFeature) - end"); //$NON-NLS-1$
			}
			return scenarioCaseMappingList;
		} catch (Exception e) {
			LOG.error(DataConstants.ERROR_IMPORT, e); //$NON-NLS-1$
			throw new ExcelUtilityException(e.getMessage());
		}
	}

	private TestCase caseDescription(AppFunctionality functional,
			AppFeature appFeature, String sheetName) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("caseDescription(AppFunctionality, AppFeature, String) - start"); //$NON-NLS-1$
		}

		TestCase testCase = new TestCase();
		String testCaseTitle;
		String runner = null;
		String execute;
		String testCaseName;
		String classificationTag;
		String tcType;
		testCaseTitle = excelAccess.getCellValue(sheetName,
				getTcStartRow() - 1, getTestCaseTitleColNo());
		testCaseName = excelAccess.getCellValue(sheetName, getTcStartRow() - 1,
				getTestCaseNameCol());
		execute = excelAccess.getCellValue(sheetName, getTcStartRow() - 1,
				getExecuteColNo());
		classificationTag = excelAccess.getCellValue(sheetName,
				getTcStartRow() - 1, getTestClassficColNo());
		tcType = excelAccess.getCellValue(sheetName, getTcStartRow() - 1,
				getDecColNo());
		runner = excelAccess.getCellValue(sheetName, getTcStartRow() - 1,
				getRunnerTypeColNo());
		testCase.setCaseName(testCaseName);
		if (testCaseName.length() > 20) {
			testCase.setCaseName(testCaseName.substring(0, 19));
		}
		testCase.setDescription(testCaseTitle);
		testCase.setClassificationTag(classificationTag);
		testCase.setActive(execute);
		testCase.setPositive(tcType);
		if (runner != null && runner.length() != 0) {
			testCase.setRunner(setRunner(runner));
		}
		testCase.setAppFeature(appFeature);
		testCase.setAppFunctionality(functional);

		if (LOG.isDebugEnabled()) {
			LOG.debug("caseDescription(AppFunctionality, AppFeature, String) - end"); //$NON-NLS-1$
		}
		return testCase;
	}

	/**
	 * Test case creation.
	 * 
	 * @param tfwRemote
	 *            the tfw remote
	 * @param excelAccess
	 *            the excel access
	 * @param sheetName
	 *            the sheet name
	 * @param actionColNo
	 *            the action col no
	 * @param stepParamColNo
	 *            the step param col no
	 * @param paramGrpColNo
	 *            the param grp col no
	 * @param runnerTypeColNo
	 *            the runner type col no
	 * @param testCaseTitleColNo
	 *            the test case title col no
	 * @param valueColNo
	 *            the value col no
	 * @param tcEndRow
	 *            the tc end row
	 * @param testCaseTitle
	 *            the test case title
	 * @param runner
	 *            the runner
	 * @param run
	 *            the run
	 * @param pos
	 *            the pos
	 * @param testCaseName
	 *            the test case name
	 * @param classificationTag
	 *            the classification tag
	 * @param testData
	 *            the test data
	 * @param functional
	 *            the functional
	 * @param appFeature
	 *            the app feature
	 * @return the test case
	 * @throws InvalidFormatException
	 *             the invalid format exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private TestCase testCaseCreation(ExcelAccess excelAccess,
			String sheetName, int tcEndRow, TestData testData,
			TestCase testCase1) throws ExcelUtilityException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("testCaseCreation(ExcelAccess, String, int, TestData, TestCase) - start"); //$NON-NLS-1$
		}

		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("testCaseCreation(TfwRemoteInterface2, ExcelAccess, String, int, int, int, int, int, int, int, String, String, String, String, String, String, TestData, AppFunctionality, AppFeature) - start"); //$NON-NLS-1$
			}
			ConditionGroup conditionGroup = new ConditionGroup();
			conditionGroup.setConditionGroupName(testCase1.getCaseName()
					+ " condition group");
			conditionGroup.setDescription(testCase1.getCaseName()
					+ " condition group");
			List<Conditions> conditionList = new ArrayList<Conditions>();
			Conditions conditions = new Conditions();
			conditions.setConditionName(testCase1.getCaseName() + " condition");
			conditions.setExpression(" expression ");
			conditions.setDescription(testCase1.getCaseName()
					+ " condition description");
			List<TestConditionData> testConditionDataList = new ArrayList<TestConditionData>();
			TestConditionData testConditionData = new TestConditionData();
			testConditionData.setConditionValue("Condition value Test ");
			testConditionData.setTestData(testData);
			testConditionDataList.add(testConditionData);
			conditions.setTestConditionData(testConditionDataList);
			conditionList.add(conditions);
			conditionGroup.setConditions(conditionList);
			testCase1.setConditionGroup(conditionGroup);
			List<CaseStepMapping> caseStepMappingsList = testStepListCreation(
					excelAccess, sheetName, tcEndRow, testCase1, testData);
			testCase1.setCaseStepMappings(caseStepMappingsList);

			if (LOG.isDebugEnabled()) {
				LOG.debug("testCaseCreation(TfwRemoteInterface2, ExcelAccess, String, int, int, int, int, int, int, int, String, String, String, String, String, String, TestData, AppFunctionality, AppFeature) - end"); //$NON-NLS-1$
			}
			return testCase1;
		} catch (ExcelUtilityException e) {
			LOG.error(DataConstants.ERROR_IMPORT, e); //$NON-NLS-1$
			throw new ExcelUtilityException(e.getMessage());
		}
	}

	/**
	 * Sets the runner.
	 * 
	 * @param runner
	 *            the runner
	 * @return the runner
	 */
	private Runner setRunner(String runner) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("setRunner(String) - start"); //$NON-NLS-1$
		}

		Runner runner1 = new Runner();
		runner1.setRunnerName(runner);
		runner1.setDescription(runner + DataConstants.DESCRIPTION);

		if (LOG.isDebugEnabled()) {
			LOG.debug("setRunner(String) - end"); //$NON-NLS-1$
		}
		return runner1;
	}

	/**
	 * Creates the functional.
	 * 
	 * @param excelImport
	 *            the excel import
	 * @param appFeatureList
	 *            the app feature list
	 * @return the app functionality
	 */
	private AppFunctionality createFunctional(ExcelImport excelImport,
			List<AppFeature> appFeatureList) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("createFunctional(ExcelImport, List<AppFeature>) - start"); //$NON-NLS-1$
		}

		AppFunctionality functional = new AppFunctionality();
		String testFun = excelImport.getFunctionName();
		functional.setFunctionalName(testFun);
		functional.setDescription(testFun + DataConstants.DESCRIPTION);
		functional.setAppFeature(appFeatureList);

		if (LOG.isDebugEnabled()) {
			LOG.debug("createFunctional(ExcelImport, List<AppFeature>) - end"); //$NON-NLS-1$
		}
		return functional;
	}

	/**
	 * Feature creation.
	 * 
	 * @param excelImport
	 *            the excel import
	 * @param screenList
	 *            the screen list
	 * @return the app feature
	 */
	private AppFeature featureCreation(ExcelImport excelImport,
			List<Screen> screenList) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("featureCreation(ExcelImport, List<Screen>) - start"); //$NON-NLS-1$
		}

		AppFeature feature = new AppFeature();
		String testFea = excelImport.getFeatureName();
		feature.setFeatureName(testFea);
		feature.setDescription(testFea + " description");
		feature.setScreen(screenList);

		if (LOG.isDebugEnabled()) {
			LOG.debug("featureCreation(ExcelImport, List<Screen>) - end"); //$NON-NLS-1$
		}
		return feature;
	}

	/**
	 * Suite creation.
	 * 
	 * @param excelImport
	 *            the excel import
	 * @return the test suite
	 */
	private TestSuite suiteCreation(ExcelImport excelImport) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("suiteCreation(ExcelImport) - start"); //$NON-NLS-1$
		}

		TestSuite testSuite = new TestSuite();
		String suiteName = excelImport.getTestSuiteName();
		testSuite.setSuiteName(suiteName);
		testSuite.setDescription(suiteName + DataConstants.DESCRIPTION);

		if (LOG.isDebugEnabled()) {
			LOG.debug("suiteCreation(ExcelImport) - end"); //$NON-NLS-1$
		}
		return testSuite;
	}

	/**
	 * Test step list creation.
	 * 
	 * @param tfwRemote
	 *            the tfw remote
	 * @param excelAccess
	 *            the excel access
	 * @param sheetName
	 *            the sheet name
	 * @param actionColNo
	 *            the action col no
	 * @param stepParamColNo
	 *            the step param col no
	 * @param paramGrpColNo
	 *            the param grp col no
	 * @param runnerTypeColNo
	 *            the runner type col no
	 * @param testCaseTitleColNo
	 *            the test case title col no
	 * @param valueColNo
	 *            the value col no
	 * @param tcEndRow
	 *            the tc end row
	 * @param runner
	 *            the runner
	 * @param testCase1
	 *            the test case1
	 * @param testData
	 *            the test data
	 * @return the list
	 * @throws InvalidFormatException
	 *             the invalid format exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private List<CaseStepMapping> testStepListCreation(ExcelAccess excelAccess,
			String sheetName, int tcEndRow, TestCase testCase, TestData testData)
			throws ExcelUtilityException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("testStepListCreation(ExcelAccess, String, int, TestCase, TestData) - start"); //$NON-NLS-1$
		}

		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("testStepListCreation(TfwRemoteInterface2, ExcelAccess, String, int, int, int, int, int, int, int, String, TestCase, TestData) - start"); //$NON-NLS-1$
			}
			List<CaseStepMapping> caseStepMappingsList = new ArrayList<CaseStepMapping>();
			for (int strtTCs = getTcStartRow(); strtTCs <= tcEndRow; strtTCs++) {
				CaseStepMapping caseStepMapping = new CaseStepMapping();
				TestStep testSteps = testStep(excelAccess, sheetName, strtTCs,
						testData, testCase);
				caseStepMapping.setTestStep(testSteps);
				caseStepMappingsList.add(caseStepMapping);
			}
			if (LOG.isDebugEnabled()) {
				LOG.debug("testStepListCreation(TfwRemoteInterface2, ExcelAccess, String, int, int, int, int, int, int, int, String, TestCase, TestData) - end"); //$NON-NLS-1$
			}
			return caseStepMappingsList;
		} catch (ExcelUtilityException e) {
			LOG.error(DataConstants.ERROR_IMPORT, e); //$NON-NLS-1$
			throw new ExcelUtilityException(e.getMessage());
		}
	}

	/**
	 * Test step.
	 * 
	 * @param excelAccess
	 *            the excel access
	 * @param sheetName
	 *            the sheet name
	 * @param stepParamColNo
	 *            the step param col no
	 * @param testCaseTitleColNo
	 *            the test case title col no
	 * @param runner
	 *            the runner
	 * @param actionType
	 *            the action type
	 * @param actionParam
	 *            the action param
	 * @param actionObject
	 *            the action object
	 * @param runnerTstStep
	 *            the runner tst step
	 * @param strtTCs
	 *            the strt t cs
	 * @param testData
	 *            the test data
	 * @param testCase
	 *            the test case
	 * @return the test step
	 */
	private TestStep testStep(ExcelAccess excelAccess, String sheetName,
			int strtTCs, TestData testData, TestCase testCase)
			throws ExcelUtilityException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("testStep(ExcelAccess, String, int, TestData, TestCase) - start"); //$NON-NLS-1$
		}

		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("testStep(ExcelAccess, String, int, int, String, String, String, String, String, int, TestData, TestCase) - start"); //$NON-NLS-1$
			}
			TestStep testSteps = new TestStep();
			String actionType = excelAccess.getCellValue(sheetName, strtTCs,
					getActionColNo());
			String actionObject = excelAccess.getCellValue(sheetName, strtTCs,
					getParamGrpColNo());
			String actionParam = excelAccess.getCellValue(sheetName, strtTCs,
					getValueColNo());
			Actions actions1 = callAction(actionType);
			String stpPr = excelAccess.getCellValue(sheetName, strtTCs,
					getStepParamColNo());
			testSteps.setActions(actions1);
			testSteps = testStepStatus(testCase, testSteps);
			testSteps.setStepName(actions1.getActionName() + " step Name");
			testSteps.setDescription(actions1.getActionName() + " description");
			List<ParamGroup> paramGroupList = new ArrayList<ParamGroup>();
			ParamGroup paramGroup = paramGroupDescription(actionParam,
					actionObject, testData, stpPr, testSteps);
			paramGroupList.add(paramGroup);
			testSteps.setParamGroup(paramGroupList);

			if (LOG.isDebugEnabled()) {
				LOG.debug("testStep(ExcelAccess, String, int, int, String, String, String, String, String, int, TestData, TestCase) - end"); //$NON-NLS-1$
			}
			return testSteps;
		} catch (Exception e) {
			LOG.error("import error", e);
			throw new ExcelUtilityException(e.getMessage());
		}

	}

	/**
	 * @param testCase
	 * @param testSteps
	 */
	private TestStep testStepStatus(TestCase testCase, TestStep testSteps) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("testStepStatus(TestCase, TestStep) - start"); //$NON-NLS-1$
		}

		if (testCase.getActive() != null
				&& ("Y".equalsIgnoreCase(testCase.getActive()) || "1"
						.equalsIgnoreCase(testCase.getActive()))) {
			testSteps.setActive("1");
		} else {
			testSteps.setActive("0");
		}
		if (testCase.getPositive() != null
				&& (testCase.getPositive()
						.equalsIgnoreCase(DataConstants.POSITIVE))) {
			testSteps.setTestStepType("1");
		} else {
			testSteps.setTestStepType("0");
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("testStepStatus(TestCase, TestStep) - end"); //$NON-NLS-1$
		}
		return testSteps;
	}

	/**
	 * @param actionParam
	 * @param actionObject
	 * @param testData
	 * @param stpPr
	 * @param identifier
	 * @param identifierType
	 * @param objectGroupStr
	 * @param screenStr
	 * @param testSteps1
	 * @return
	 */
	private ParamGroup paramGroupDescription(String actionParam,
			String actionObject, TestData testData, String stpPr,
			TestStep testSteps1) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("paramGroupDescription(String, String, TestData, String, TestStep) - start"); //$NON-NLS-1$
		}

		String identifier = null;
		String identifierType = null;
		String objectGroupStr = null;
		String screenStr = null;
		ParamGroup paramGroup = new ParamGroup();

		paramGroup.setParamGroupName(testSteps1.getActions().getActionName()
				+ " ParamGroup");
		paramGroup.setTag(testSteps1.getActions().getActionName() + "_tag");
		paramGroup.setDescription(testSteps1.getActions().getActionName()
				+ " ParamGroup description");
		List<String> values = (List) multiValueMap.get(actionObject);
		if (values == null) {
			LOG.error(actionObject + " not found in the _objects sheet");
		} else {
			identifier = values.get(0);
			objectGroupStr = values.get(2);
			screenStr = values.get(1);
			identifierType = values.get(3);
		}
		if (actionObject != null && actionObject.length() != 0) {
			ObjectGroup objectGroup = new ObjectGroup();
			objectGroup.setObjectGroupName(testSteps1.getActions()
					.getActionName() + " ObjectGroup");
			objectGroup.setDescription(testSteps1.getActions().getActionName()
					+ " ObjectGroup description");
			List<Objects> objectList = new ArrayList<Objects>();
			Objects objects = objectDescription(actionObject, identifier,
					identifierType, objectGroupStr, screenStr);
			objectList.add(objects);

			objectGroup.setScreenName(screenStr);
			objectGroup.setObjects(objectList);
			objectGroup.setObjectGroupName(objectGroupStr);
			List<Param> paramList = new ArrayList<Param>();
			Param param1 = paramDescription(actionParam, testData, stpPr,
					objects);
			paramList.add(param1);

			paramGroup.setObjectGroup(objectGroup);
			paramGroup.setParam(paramList);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("paramGroupDescription(String, String, TestData, String, TestStep) - end"); //$NON-NLS-1$
		}
		return paramGroup;
	}

	/**
	 * @param actionParam
	 * @param testData
	 * @param stpPr
	 * @param objects
	 * @return
	 */
	private Param paramDescription(String actionParam, TestData testData,
			String stpPr, Objects objects) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("paramDescription(String, TestData, String, Objects) - start"); //$NON-NLS-1$
		}

		Param param1 = new Param();
		param1.setObjects(objects);
		param1.setParamName(actionParam + " type ");
		param1.setDescription(actionParam + DataConstants.DESCRIPTION);
		TestParamData testParamData = new TestParamData();
		testParamData.setTestData(testData);
		testParamData.setParamValue(actionParam);
		if (stpPr != null && stpPr.length() != 0) {
			testParamData.setValueBig(stpPr);
		}
		param1.setTestParamData(testParamData);

		if (LOG.isDebugEnabled()) {
			LOG.debug("paramDescription(String, TestData, String, Objects) - end"); //$NON-NLS-1$
		}
		return param1;
	}

	/**
	 * @param actionObject
	 * @param identifier
	 * @param identifierType
	 * @param objectGroupStr
	 * @param screenStr
	 * @return
	 */
	private Objects objectDescription(String actionObject, String identifier,
			String identifierType, String objectGroupStr, String screenStr) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("objectDescription(String, String, String, String, String) - start"); //$NON-NLS-1$
		}

		Objects objects = new Objects();
		objects.setObjectName(actionObject);
		objects.setDescription(actionObject + DataConstants.DESCRIPTION);
		objects.setIdentifier(identifier);
		objects.setObjectGroupValue(objectGroupStr);
		objects.setScreenValue(screenStr);
		objects.setIdentifierTypeValue(identifierType);
		ObjectType objectType = objectTypeDescription(actionObject);
		objects.setObjectType(objectType);

		if (LOG.isDebugEnabled()) {
			LOG.debug("objectDescription(String, String, String, String, String) - end"); //$NON-NLS-1$
		}
		return objects;
	}

	/**
	 * @param actionObject
	 * @return
	 */
	private ObjectType objectTypeDescription(String actionObject) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("objectTypeDescription(String) - start"); //$NON-NLS-1$
		}

		ObjectType objectType = new ObjectType();
		objectType.setObjectTypeName(actionObject + "_type1 ");
		objectType.setDescription(actionObject + DataConstants.DESCRIPTION);

		if (LOG.isDebugEnabled()) {
			LOG.debug("objectTypeDescription(String) - end"); //$NON-NLS-1$
		}
		return objectType;
	}

	/**
	 * Call action.
	 * 
	 * @param actionType
	 *            the action type
	 * @return the actions
	 */
	private Actions callAction(String actionType) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("callAction(String) - start"); //$NON-NLS-1$
		}

		Actions actions1 = new Actions();
		actions1.setActionName(actionType);
		actions1.setDescription(actionType + " description");

		if (LOG.isDebugEnabled()) {
			LOG.debug("callAction(String) - end"); //$NON-NLS-1$
		}
		return actions1;
	}

	/**
	 * Gets the tC strt row.
	 * 
	 * @param excelAccess
	 *            the excel access
	 * @param sheetName
	 *            the sheet name
	 * @return the tC strt row
	 */
	private int getTCStrtRow(ExcelAccess excelAccess, String sheetName) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTCStrtRow(ExcelAccess, String) - start"); //$NON-NLS-1$
		}

		int strtExe = 0;
		int totalRowInSheet = excelAccess.isSheetExists(sheetName) ? excelAccess
				.getTotalRowCount(sheetName) : DataConstants.DEFAULT_VAL; // Ankur
		String slNoColName = "";
		slNoColName = DataConstants.SL_NO;
		for (strtExe = DataConstants.DEFAULT_VAL; strtExe < totalRowInSheet; strtExe++) {
			if (excelAccess.getCellValue(sheetName, strtExe,
					DataConstants.DEFAULT_VAL).equals(slNoColName)) {
				break;
			}
		}
		int returnint = strtExe + 1;
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTCStrtRow(ExcelAccess, String) - end"); //$NON-NLS-1$
		}
		return returnint;
	}

	/**
	 * Gets the tc start row.
	 * 
	 * @return the tc start row
	 */
	public int getTcStartRow() {
		return tcStartRow;
	}

	/**
	 * Sets the tc start row.
	 * 
	 * @param tcStartRow
	 *            the new tc start row
	 */
	public void setTcStartRow(int tcStartRow) {
		this.tcStartRow = tcStartRow;
	}

	public int getTestCaseNameCol() {
		return testCaseNameCol;
	}

	public void setTestCaseNameCol(int testCaseNameCol) {
		this.testCaseNameCol = testCaseNameCol;
	}

	public int getActionColNo() {
		return actionColNo;
	}

	public void setActionColNo(int actionColNo) {
		this.actionColNo = actionColNo;
	}

	public int getStepParamColNo() {
		return stepParamColNo;
	}

	public void setStepParamColNo(int stepParamColNo) {
		this.stepParamColNo = stepParamColNo;
	}

	public int getExecuteColNo() {
		return executeColNo;
	}

	public void setExecuteColNo(int executeColNo) {
		this.executeColNo = executeColNo;
	}

	public int getParamGrpColNo() {
		return paramGrpColNo;
	}

	public void setParamGrpColNo(int paramGrpColNo) {
		this.paramGrpColNo = paramGrpColNo;
	}

	public int getDecColNo() {
		return decColNo;
	}

	public void setDecColNo(int decColNo) {
		this.decColNo = decColNo;
	}

	public int getRunnerTypeColNo() {
		return runnerTypeColNo;
	}

	public void setRunnerTypeColNo(int runnerTypeColNo) {
		this.runnerTypeColNo = runnerTypeColNo;
	}

	public int getStepTypeColNo() {
		return stepTypeColNo;
	}

	public void setStepTypeColNo(int stepTypeColNo) {
		this.stepTypeColNo = stepTypeColNo;
	}

	public int getTestClassficColNo() {
		return testClassficColNo;
	}

	public void setTestClassficColNo(int testClassficColNo) {
		this.testClassficColNo = testClassficColNo;
	}

	public int getTestCaseTitleColNo() {
		return testCaseTitleColNo;
	}

	public void setTestCaseTitleColNo(int testCaseTitleColNo) {
		this.testCaseTitleColNo = testCaseTitleColNo;
	}

	public int getValueColNo() {
		return valueColNo;
	}

	public void setValueColNo(int valueColNo) {
		this.valueColNo = valueColNo;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}