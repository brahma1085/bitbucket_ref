package com.techno.ehrp.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrTokenizer;

/**
 * The Class Utils.
 * 
 * @author brahma
 */
public class Utils {

	/** The init val. */
	private static int initVal;

	/** The sheet. */
	private static Sheet sheet = null;

	/** The Constant MAIN_PROPS. */
	private static final Properties MAIN_PROPS = new Properties();

	/**
	 * Instantiates a new utils.
	 */
	private Utils() {
	}

	static {
		initialize();
	}

	/**
	 * Sets the inits the val.
	 *
	 * @param initVal
	 *            the new inits the val
	 */
	public static void setInitVal(int initVal) {
		Utils.initVal = initVal;
	}

	/**
	 * Gets the inits the val.
	 *
	 * @return the inits the val
	 */
	public static int getInitVal() {
		return initVal;
	}

	/**
	 * Initialize.
	 */
	public static void initialize() {
		Properties alphaProps = null;
		try {
			alphaProps = new Properties();
			alphaProps.load(Utils.class.getClassLoader().getResourceAsStream(Constants.ALPHA_PROPERTIES));
			Utils.MAIN_PROPS.clear();
			Utils.MAIN_PROPS.putAll(alphaProps);
			if (sheet == null) {
				Workbook w = Workbook.getWorkbook(Utils.class.getClassLoader().getResourceAsStream(Constants.HPROB_XLS));
				sheet = w.getSheet(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the property.
	 *
	 * @param name
	 *            the name
	 * @return the property
	 */
	public static String getProperty(String name) {
		return Utils.MAIN_PROPS.getProperty(name);
	}

	/**
	 * Capitalize.
	 *
	 * @param name
	 *            the name
	 * @return the string
	 */
	public static String capitalize(String name) {
		if (StringUtils.isNotBlank(name)) {
			name = name.toUpperCase();
		}
		return name;
	}

	/**
	 * Checks if is vowel.
	 *
	 * @param ch
	 *            the ch
	 * @return true, if is vowel
	 */
	private static boolean isVowel(char ch) {
		ch = Character.toLowerCase(ch);
		return (ch == 'a') || (ch == 'e') || (ch == 'i') || (ch == 'o') || (ch == 'u');
	}

	/**
	 * Checks if is char vowel.
	 *
	 * @param name
	 *            the name
	 * @return true, if is char vowel
	 */
	private static boolean isCharVowel(String name) {
		boolean flag = false;
		if ((name.length() > 1) && (isVowel(name.charAt(1)))) {
			char ch = Character.toLowerCase(name.charAt(0));
			flag = (ch == 'c') || (ch == 'o') || (ch == 'r') || (ch == 's');
		}
		return flag;
	}

	/**
	 * Gets the total.
	 *
	 * @param name
	 *            the name
	 * @return the total
	 */
	public static int getTotal(String name) {
		int value = 0;
		int ex = 0;
		if (StringUtils.isNotBlank(name)) {
			name = name.trim();
			name = capitalize(name);
			if (isCharVowel(name)) {
				String val = getProperty(String.valueOf(name.charAt(0)));
				if (StringUtils.isNotBlank(val)) {
					ex = Integer.parseInt(String.valueOf(val.charAt(2)));
				}
				name = name.substring(1, name.length());
				setInitVal(ex);
			}
			value = ex + getSum(name);
		}
		value = getDigitSum(value);
		return value;
	}

	/**
	 * Gets the digit sum.
	 *
	 * @param value
	 *            the value
	 * @return the digit sum
	 */
	public static int getDigitSum(int value) {
		String val = String.valueOf(value);
		int temp = 0;
		int incr = 0;
		if (val.length() == 1) {
			return value;
		}
		for (int i = 0; i < val.length(); i++) {
			incr = Integer.parseInt(String.valueOf(val.charAt(i)));
			temp += incr;
		}
		return getDigitSum(temp);
	}

	/**
	 * Gets the sum.
	 *
	 * @param name
	 *            the name
	 * @return the sum
	 */
	public static int getSum(String name) {
		name = name.trim();
		int temp = 0;
		int incr = 0;
		for (int i = 0; i < name.length(); i++) {
			String propVal = getProperty(String.valueOf(name.charAt(i)));
			if ((i == 0) && (getInitVal() == 0)) {
				if (propVal.length() > 0) {
					propVal = String.valueOf(propVal.charAt(0));
				}
				setInitVal(Integer.parseInt(propVal));
			}
			if (StringUtils.isNotBlank(propVal)) {
				incr = Integer.parseInt(String.valueOf(propVal.charAt(0)));
				temp += incr;
			}
		}
		return temp;
	}

	/**
	 * Gets the headers.
	 *
	 * @return the headers
	 */
	public static final List<String> getHeaders() {
		List<String> headers = new ArrayList<>();
		Cell[] cells = sheet.getRow(0);
		for (Cell cell : cells) {
			String cellVal = cell.getContents();
			if (StringUtils.isNotBlank(cellVal)) {
				cellVal = cellVal.trim();
				headers.add(cellVal);
			}
		}
		Collections.sort(headers);
		Collections.unmodifiableList(headers);
		return headers;
	}

	/**
	 * Gets the data.
	 *
	 * @param row
	 *            the row
	 * @param col
	 *            the col
	 * @return the data
	 */
	public static String getData(int row, int col) {
		String data = null;
		col = getDesiredColumn(col);
		Cell cell = sheet.getCell(col, row);
		data = cell.getContents();
		return data;
	}

	/**
	 * Gets the desired column.
	 *
	 * @param col
	 *            the col
	 * @return the desired column
	 */
	private static int getDesiredColumn(int col) {
		int column = 0;
		for (String header : getHeaders()) {
			if (header.contains(String.valueOf(col))) {
				return column;
			}
			column++;
		}
		return col;
	}

	/**
	 * Prints the.
	 *
	 * @param data
	 *            the data
	 * @param results
	 *            the results
	 * @param rowNum
	 *            the row num
	 * @param total
	 *            the total
	 * @return the string
	 */
	public static String print(String data, Object[][] results, int rowNum, int total) {
		StringBuilder builder = new StringBuilder();
		int chalMidVal = getMidValue(String.valueOf(total), String.valueOf(rowNum), getChaldeanOrder());
		int weekMidVal = getMidValue(String.valueOf(total), String.valueOf(rowNum), getWeekdayOrder());
		StrTokenizer tokenizer = StrTokenizer.getCSVInstance(data);
		String[] tokens = tokenizer.getTokenArray();
		for (int i = 0; i < tokens.length; i++) {
			String value = tokens[i];
			if (null != value && !Constants.UNIT_FORCE.equalsIgnoreCase(value)) {
				value = value.replaceAll("-", "                 ");
			}
			if (i % 2 == 0) {
				builder.append(value).append(Constants.NEW_LINE_CHAR);
			} else {
				builder.deleteCharAt(builder.length() - 1);
				builder.append(Constants.TAB_CHAR).append(value).append(Constants.NEW_LINE_CHAR);
			}
		}
		builder.deleteCharAt(builder.length() - 1);
		String result = builder.toString();
		if ((results != null) && (!Constants.UNIT_FORCE.equalsIgnoreCase(result))) {
			StringTokenizer str = new StringTokenizer(result, Constants.NEW_LINE_CHAR);
			results[rowNum][4] = str.nextToken().replaceAll(Constants.TAB_CHAR, Constants.NEW_LINE_CHAR + "              " + chalMidVal + Constants.NEW_LINE_CHAR);
			results[rowNum][5] = str.nextToken().replaceAll(Constants.TAB_CHAR, Constants.NEW_LINE_CHAR + "              " + weekMidVal + Constants.NEW_LINE_CHAR);
		} else if ((results != null) && (Constants.UNIT_FORCE.equalsIgnoreCase(result))) {
			results[rowNum][4] = result;
		}
		return result;
	}

	/**
	 * Gets the weekday order.
	 *
	 * @return the weekday order
	 */
	private static final List<String> getWeekdayOrder() {
		List<String> weekOrder = new ArrayList<>();
		weekOrder.add(0, Constants.VALUE_1_4);
		weekOrder.add(1, Constants.VALUE_2_7);
		weekOrder.add(2, Constants.VALUE_9);
		weekOrder.add(3, Constants.VALUE_5);
		weekOrder.add(4, Constants.VALUE_3);
		weekOrder.add(5, Constants.VALUE_6);
		weekOrder.add(6, Constants.VALUE_8);
		return weekOrder;
	}

	/**
	 * Gets the chaldean order.
	 *
	 * @return the chaldean order
	 */
	private static final List<String> getChaldeanOrder() {
		List<String> chalOrder = new ArrayList<>();
		chalOrder.add(0, Constants.VALUE_1_4);
		chalOrder.add(1, Constants.VALUE_6);
		chalOrder.add(2, Constants.VALUE_5);
		chalOrder.add(3, Constants.VALUE_2_7);
		chalOrder.add(4, Constants.VALUE_8);
		chalOrder.add(5, Constants.VALUE_3);
		chalOrder.add(6, Constants.VALUE_9);
		return chalOrder;
	}

	/**
	 * Gets the mid value.
	 *
	 * @param total
	 *            the total
	 * @param row
	 *            the row
	 * @param indexOrder
	 *            the index order
	 * @return the mid value
	 */
	private static int getMidValue(String total, String row, List<String> indexOrder) {
		int result = 0;
		if (Constants.VALUE_1.equals(total) || Constants.VALUE_4.equals(total)) {
			total = Constants.VALUE_1_4;
		} else if (Constants.VALUE_2.equals(total) || Constants.VALUE_7.equals(total)) {
			total = Constants.VALUE_2_7;
		}
		if (Constants.VALUE_1.equals(row) || Constants.VALUE_4.equals(row)) {
			row = Constants.VALUE_1_4;
		} else if (Constants.VALUE_2.equals(row) || Constants.VALUE_7.equals(row)) {
			row = Constants.VALUE_2_7;
		}
		int value = indexOrder.indexOf(String.valueOf(total)) - indexOrder.indexOf(String.valueOf(row));
		if (value < 0) {
			result = 0 - value;
		} else {
			result = 8 - value;
		}
		return result;
	}

	/**
	 * Gets the time.
	 *
	 * @return the time
	 */
	public static String getTime() {
		DateFormat dateFormat = new SimpleDateFormat(Constants.TIMESTAMP_FORMAT);
		Date date = new Date();
		return dateFormat.format(date);
	}
}