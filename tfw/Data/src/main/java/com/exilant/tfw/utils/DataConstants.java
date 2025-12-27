package com.exilant.tfw.utils;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * The Class DataConstants.
 */
public class DataConstants {

	/** The Constant OBJECT_TYPE_SHEET. */
	public static final String OBJECT_TYPE_SHEET = "_objectTypes";

	/** The Constant DEFAULT_USER. */
	public static final String DEFAULT_USER = System.getenv("USERNAME");

	/** The Constant OBJECTS. */
	public static final String OBJECTS = "_objects";

	/** The Constant MASTERPLAN. */
	public static final String MASTERPLAN = "masterPlan";

	/** The Constant USER_SKIPPED. */
	public static final int USER_SKIPPED = 0;

	/** The Constant NOT_EXEC_SKIP. */
	public static final int NOT_EXEC_SKIP = 3;

	/** The Constant PASSED_AFTER_RUN. */
	public static final int PASSED_AFTER_RUN = 1;

	/** The Constant FAILED_AFTER_RUN. */
	public static final int FAILED_AFTER_RUN = 2;

	/** The Constant DEEP_TEST_PLAN. */
	public static final String DEEP_TEST_PLAN = "deeptestplan";

	/** The Constant CREATE_ORDER. */
	public static final String CREATE_ORDER = "createOrder";

	/** The Constant ERROR_SHEET. */
	public static final String ERROR_SHEET = "WorkBook must contain only 2 sheets";

	/** The Constant ERROR_IMPORT. */
	public static final String ERROR_IMPORT = "import error";
	
	/** The Constant SHEET_NOT_EXIST. */
	public static final String SHEET_NOT_EXIST="sheet does not exits for the workbook";
	
	/** The Constant DESCRIPTION. */
	public static final String DESCRIPTION = " description";
	
	/** The Constant OBJECT_MAP_START. */
	public static final String OBJECT_MAP_START = "ObjectMap Start";
	
	/** The Constant OBJECT_MAPPING_ENDS. */
	public static final String OBJECT_MAPPING_ENDS = "Object Mapping Ends";
	
	/** The Constant OBJECT_MAPPING_END_NOT_FOUND. */
	public static final String OBJECT_MAPPING_END_NOT_FOUND ="Object Mapping Ends not found at the end of _objects sheet";

	/** The Constant OBJECT_MAPPING_START_NOT_FOUND. */
	public static final String OBJECT_MAPPING_START_NOT_FOUND = "ObjectMap Start not found in the beginning of _objects sheet";
	
	/** The Constant OBJECT_MAPPING_ENDS_HERE. */
	public static final String OBJECT_MAPPING_ENDS_HERE= "Object Mapping from _objects sheet ends here";
	
	/** The Constant OBJECT_MAP_END. */
	public static final String OBJECT_MAP_END = "ObjectMap End";
												
	/** The Constant TC_EXECUTION_ENDS_HERE. */
	public static final String TC_EXECUTION_ENDS_HERE ="TC_EXECUTION_ENDS_HERE";
	
	/** The Constant TC_START. */
	public static final String TC_START = "TC_START";
	/** The Constant SL_NO. */
	public static final String SL_NO = "Sl no";
	
	/** The Constant TC_EXECUTION_ENDS_HERE_NOT_FOUND. */
	public static final String TC_EXECUTION_ENDS_HERE_NOT_FOUND = "Could not find the string TC Execution Ends Here";
	
	/** The Constant TC_START_NOT_FOUND. */
	public static final String TC_START_NOT_FOUND = "Could not find TC_START and not executing this TC ";
	
	/** The Constant TC_END_NOT_FOUND. */
	public static final String TC_END_NOT_FOUND = "Could not find TC_END and not executing this TC";
	
	/** The Constant POSITIVE. */
	public static final String POSITIVE = "Positive";

	/** The Constant DEFAULT_VAL. */
	public static final int DEFAULT_VAL = 0;

	/** The Constant RUNNERS. */
	public static final String RUNNERS = "runners";

	/** The Constant DEFAULT_DATE. */
	public static final Date DEFAULT_DATE = new Date(System.currentTimeMillis());

	/** The Constant DEFAULT_TIMESTAMP. */
	public static final Timestamp DEFAULT_TIMESTAMP = new Timestamp(
			System.currentTimeMillis());

	/** The Constant TC_END. */
	public static final String TC_END = "TC_END";
}
