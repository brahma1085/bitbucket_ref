package com.personal.epf.utils;

import java.io.Serializable;

/**
 * @author brahma
 * 
 *         The Enum Constants. The constant values defined in this class can be
 *         used all over code in this project
 */
public enum Constants implements Serializable {
	;

	/** The Constant ZIP. */
	public static final String ZIP = "zip";

	/** The Constant STATE. */
	public static final String STATE = "state";

	/** The Constant SALARY. */
	public static final String SALARY = "salary";

	/** The Constant PWD. */
	public static final String PWD = "pwd";

	/** The Constant PFVALUE. */
	public static final String PFVALUE = "pfvalue";

	/** The Constant MOBILE. */
	public static final String MOBILE = "mobile";

	/** The Constant MIDDLENAME. */
	public static final String MIDDLENAME = "middlename";

	/** The Constant MARITALSTATUS. */
	public static final String MARITALSTATUS = "maritalstatus";

	/** The Constant LASTNAME. */
	public static final String LASTNAME = "lastname";

	/** The Constant GENDER. */
	public static final String GENDER = "gender";

	/** The Constant FIRSTNAME. */
	public static final String FIRSTNAME = "firstname";

	/** The Constant EMAIL. */
	public static final String EMAIL = "email";

	/** The Constant DOB. */
	public static final String DOB = "dob";

	/** The Constant DESIGNATION. */
	public static final String DESIGNATION = "designation";

	/** The Constant DAYPHONE. */
	public static final String DAYPHONE = "dayphone";

	/** The Constant COUNTRY. */
	public static final String COUNTRY = "country";

	/** The Constant CITY. */
	public static final String CITY = "city";

	/** The Constant ADDRESS. */
	public static final String ADDRESS = "address";

	/** The Constant RETRIEVE_ERROR_MSG. */
	public static final String RETRIEVE_ERROR_MSG = "An unknown error occured while fetching the employee details from database";

	/** The Constant INSERT_ERROR_MSG. */
	public static final String INSERT_ERROR_MSG = "An unknown error occured while inserting the employee data into database";

	/** The Constant INTERNAL_ERROR. */
	public static final String INTERNAL_ERROR = "Internal server error..please try later..!";

	/** The Constant DB_COLUMN. */
	public static final String DB_COLUMN = "empid";

	/** The Constant DEFAULT_ID. */
	public static final int DEFAULT_ID = 0;

	/** The Constant DEFAULT_PF. */
	public static final double DEFAULT_PF = 0.0D;

	/** The Constant INSERT_QUERY. */
	public static final String INSERT_QUERY = "insert into employeedetails values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	/** The Constant EMP_ID. */
	public static final String EMP_ID = "select max(empid) as empid from employeedetails";

	/** The Constant SELECT_EMP. */
	public static final String SELECT_EMP = "select * from employeedetails where empid=?";

	/** The Constant PF_QUERY. */
	public static final String PF_QUERY = "select salary,pfvalue from employeedetails where empid=?";

	/** The Constant EMP_KEY. */
	public static final String EMP_KEY = "employeeID";

	/** The Constant CONTENT_TYPE. */
	public static final String CONTENT_TYPE = "text/html";

	/** The Constant EMP_OBJ. */
	public static final String EMP_OBJ = "employeeObj";

	/** The Constant EMP_PF. */
	public static final String EMP_PF = "employeePF";

	/** The Constant VALUE. */
	public static final String VALUE = "value";

	/** The Constant CONTENT. */
	public static final String CONTENT = "/integration";

	/** The Constant EMPLOYEEREGISTRATION_JSP. */
	public static final String EMPLOYEEREGISTRATION_JSP = "/WEB-INF/pages/employeeregistration.jsp";

	/** The Constant EMPLOYEEPFDETAILS_JSP. */
	public static final String EMPLOYEEPFDETAILS_JSP = "/WEB-INF/pages/employeepfdetails.jsp";

	/** The Constant EMPLOYEESUMMARY_JSP. */
	public static final String EMPLOYEESUMMARY_JSP = "/WEB-INF/pages/employeesummary.jsp";

	/** The Constant REQUIRED_FIELDS. */
	public static final String REQUIRED_FIELDS = "* fields are required";

	/** The Constant MESSAGE. */
	public static final String MESSAGE = "message";

	/** The Constant SUCCESS_MSG. */
	public static final String SUCCESS_MSG = "Request fulfilled successfully :)";

	/** The Constant FAILURE_MSG. */
	public static final String FAILURE_MSG = "Request was not fulfilled properly..!";

	/** The epf jndi. */
	final static String EPF_JNDI = "java:comp/env/jdbc/brahmaJNDI";

}
