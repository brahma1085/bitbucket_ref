/**
 * 
 */
package com.exilant.tfw.util;

import java.util.Iterator;
import java.util.List;

/**
 * @author saravanan
 * 
 */
public class AutomationUtilities {

	public static final String STR_SHELL_COMMAND = "/bin/bash";
	public static final String FILE_SEPARATOR = System.getProperty("file.separator");
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");

	private static final int MAX_CHAR_COL_DISPLAY_WIDTH = 75;
	private static final int MIN_CHAR_COL_SPACE_ASIDE_WIDTH = 2;

	public static boolean blnNumberParseError = false;

	public static final int WS_STR_MODE = 0;
	public static final int DB_STR_MODE = 1;

//	private TestBase testBase;
//
//	public AutomationUtilities(TestBase testBase) {
//		this.testBase = testBase;
//	}

	public static String getOSVersion() {
		return System.getProperty("os.version");
	}

//	public static boolean isMac() {
//		String strOS = System.getProperty("os.name").toLowerCase();
//		return (strOS.indexOf("mac") >= 0);
//	}
//
//	public static boolean isWindows() {
//		String strOS = System.getProperty("os.name").toLowerCase();
//		return (strOS.indexOf("win") >= 0);
//	}
//
//	public static boolean isUnix() {
//		String strOS = System.getProperty("os.name").toLowerCase();
//		return (strOS.indexOf("nix") >= 0 || strOS.indexOf("nux") >= 0 || strOS.indexOf("aix") > 0);
//	}
//
//	public static boolean isSolaris() {
//		String strOS = System.getProperty("os.name").toLowerCase();
//		return (strOS.indexOf("sunos") >= 0);
//	}
//
//	public static String captureViewScreenAndReturnFileName(String strFolder, String aErrorID) {
//		String strFileName = null;
//		strFileName = strFolder + AutomationUtilities.FILE_SEPARATOR + aErrorID + "_view_screenshot.png";
//
//		List<String> cmds = new ArrayList<String>();
//		cmds.add("screencapture");
//		cmds.add("-C");
//		cmds.add(strFileName);
//
//		ProcessRunner runner = new ProcessRunner(cmds);
//		int exitCode = runner.runProcessAndWaitToComplete();
//
//		TestBase.log("View Capture Screenshot completed with exitCode:" + exitCode);
//		TestBase.log("Output:" + StringUtils.join(runner.getOutput(), TestBase.STR_NEW_LINE));
//		TestBase.error("Error:" + StringUtils.join(runner.getError(), TestBase.STR_NEW_LINE));
//
//		return strFileName;
//	}
//
//	public static String capitalize(String s) {
//		if (s.length() == 0)
//			return s;
//		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
//	}
//
//	public RollingFileAppender getLogFileAppender() {
//
//		RollingFileAppender appender = null;
//		try {
//			PatternLayout appLayout = new PatternLayout();
//			appLayout.setConversionPattern("%d [%t] %p - %m%n"); // set log
//																	// layout
//																	// pattern
//
//			appender = new RollingFileAppender(appLayout, this.testBase.getLogFile());
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		}
//		return appender;
//	}
//
//	public static boolean isNullOrEmpty(String aValue) {
//		boolean status = true;
//		if (aValue == null) {
//			return status;
//		}
//
//		aValue = aValue.trim();
//		if (aValue.length() <= 0) {
//			return status;
//		}
//		return false;
//	}
//
//	public boolean isFileExists(String aFileName) {
//		File f = new File(aFileName);
//		return f.exists();
//	}
//
//	public boolean writeDataIntoFile(String aFileName, String aData) {
//		boolean status = false;
//		try {
//			FileOutputStream out = new FileOutputStream(aFileName);
//			out.write(aData.getBytes());
//			out.close();
//			status = true;
//		} catch (Exception e) {
//			TestBase.error(getExceptionTrace(e));
//		}
//		return status;
//	}
//
//	public boolean appendDataIntoFile(String aFileName, String aData) {
//		boolean status = false;
//		try {
//			FileOutputStream out = new FileOutputStream(aFileName, true);
//
//			// append new line
//			aData += TestBase.STR_NEW_LINE;
//
//			out.write(aData.getBytes());
//			out.close();
//			status = true;
//		} catch (Exception e) {
//			TestBase.error(getExceptionTrace(e));
//		}
//		return status;
//	}
//
//	public static String convertInToString(Properties p, String aColumnsToLookup) {
//		StringBuffer buf = new StringBuffer();
////		if (p == null) {
////			return buf.toString();
////		}
////
////		ArrayList<String> columnLookup = splitRowAsArrayList(aColumnsToLookup, ",");
////
////		Enumeration enumKeys = p.keys();
////		while (enumKeys.hasMoreElements()) {
////			String strKey = (String) enumKeys.nextElement();
////
////			if (ResultsVerifier.isTextFoundInArrayList(strKey, columnLookup)) {
////				String strValue = p.getProperty(strKey);
////
////				if (!strKey.equals("SQL_TO_EXECUTE")) {
////					buf.append(strKey).append("=").append(strValue).append("\t");
////				} else {
////					buf.append(strKey).append("=").append(convertToSingleLineString(strValue)).append("\t");
////				}
////			}
////		}
//
//		return buf.toString();
//	}
//
//	public static void delay(long ms) {
//		try {
//			Thread.sleep(ms);
//		} catch (Exception e) {
//
//		}
//	}
//
//	public static String convertToSingleLineString(String aSQL) {
//		String strConvertedLine = aSQL;
//		if (aSQL == null) {
//			return strConvertedLine;
//		}
//
//		// all new lines are replaced by a space
//		strConvertedLine = aSQL.replaceAll("\n", " ");
//
//		return strConvertedLine;
//	}
//
//	public boolean deleteFile(String aFile) {
//		boolean status = false;
//		try {
//			if (isFileExists(aFile)) {
//				File f = new File(aFile);
//				status = f.delete();
//			} else {
//				TestBase.error("Failed as the file:" + aFile + " doesn't exist");
//			}
//
//			if (status) {
//				TestBase.log("Successfully deleted the file:" + aFile);
//			} else {
//				TestBase.error("Failed to delete the file:" + aFile);
//			}
//		} catch (Exception e) {
//			TestBase.error("Failed to delete the file:" + aFile);
//			TestBase.error(getExceptionTrace(e));
//		}
//		return status;
//	}
//
//	public static String getExceptionTrace(Exception e) {
//		StringBuffer buf = new StringBuffer();
//		StringWriter strWriter = new StringWriter();
//		if (e == null) {
//			buf.append("Failed as the given exception object is null");
//			return buf.toString();
//		}
//
//		try {
//			TestBase.log("Dumping the exception details...:");
//			if (StringUtils.isNotBlank(e.getMessage())) {
//				TestBase.log("Exception message observed as:" + e.getMessage());
//				PrintWriter writer = new PrintWriter(strWriter);
//				e.printStackTrace(writer);
//
//				buf.append(strWriter.toString());
//
//			} else {
//				TestBase.log("Exception message is found as null / empty. Ignoring this.");
//			}
//		} catch (Exception ex) {
//			buf.append("Exception occurred while getting the stacktrace of the exception:" + ex.getMessage());
//		}
//		return buf.toString();
//	}
//
//	public static boolean gunzipFile(String strInputZipFile, String strOutputContentFile) {
//		boolean status = false;
//		int BUFFER_SIZE = 2048;
//		try {
//			FileInputStream fin = new FileInputStream(strInputZipFile);
//			FileOutputStream fout = new FileOutputStream(strOutputContentFile);
//
//			BufferedOutputStream bufOut = new BufferedOutputStream(fout, BUFFER_SIZE);
//			GZIPInputStream gzin = new GZIPInputStream(fin);
//			byte[] buf = new byte[BUFFER_SIZE];
//
//			/*
//			 * for (int c = gzin.read(); c != -1; c = gzin.read()) {
//			 * fout.write(c); }
//			 */
//			int bytesRead = 0;
//			while ((bytesRead = gzin.read(buf, 0, BUFFER_SIZE)) != -1) {
//				bufOut.write(buf, 0, bytesRead);
//			}
//			/*
//			 * int offset = 0;
//			 * 
//			 * int numRead = 0; while (offset < buf.length && (numRead =
//			 * gzin.read(buf, offset, Math.min(buf.length - offset,
//			 * MAX_FILE_READ_BUF_SIZE))) >= 0) { fout.write(buf); offset +=
//			 * numRead; }
//			 */
//			gzin.close();
//			bufOut.close();
//
//			// success;
//			status = true;
//		} catch (IOException e) {
//			TestBase.error("Failed while unziping the file:" + strInputZipFile + " Error:" + e.getMessage());
//		}
//		return status;
//	}
//
//	public static boolean unzipFile(String strInputZipFile, String strOutputFolder, String aZipPassword) {
//		boolean status = false;
//
//		try {
//			// Initiate ZipFile object with the path/name of the zip file.
//			ZipFile zipFile = new ZipFile(strInputZipFile);
//
//			// Check to see if the zip file is password protected
//			if (zipFile.isEncrypted()) {
//				// if yes, then set the password for the zip file
//				zipFile.setPassword(aZipPassword);
//			}
//
//			// Get the list of file headers from the zip file
//			List<FileHeader> fileHeaderList = zipFile.getFileHeaders();
//			// Loop through the file headers
//			for (int i = 0; i < fileHeaderList.size(); i++) {
//				FileHeader fileHeader = (FileHeader) fileHeaderList.get(i);
//				// Extract the file to the specified destination
//				zipFile.extractFile(fileHeader, strOutputFolder);
//			}
//			status = true; // Succees
//		} catch (ZipException e) {
//			TestBase.error("Failed while unzipping the file:" + strInputZipFile + " Error:" + e.getMessage());
//		}
//
//		return status;
//	}
//
//	public boolean isKeyFoundInProperties(String aKey, Properties aProperties) {
//		boolean status = false;
//		if (aProperties == null) {
//			return status;
//		}
//
//		Enumeration enumKeys = aProperties.keys();
//		while (enumKeys.hasMoreElements()) {
//			String strKey = (String) enumKeys.nextElement();
//			if (strKey.equals(aKey)) {
//				status = true;
//				break;
//			}
//		}
//		return status;
//	}
//
//	public ArrayList<String> getColumnValuesFromDBResults(String aColumName, ArrayList<Hashtable<String, Object>> dbResults) {
//		ArrayList<String> list = null;
//		if (isNullOrEmpty(aColumName)) {
//			return list;
//		}
//
//		if (dbResults == null) {
//			return list;
//		}
//
//		list = new ArrayList<String>();
//		for (Hashtable<String, Object> dbItem : dbResults) {
//			String strValue = testBase.getDBValueFromHashtable(aColumName, dbItem);
//			list.add(strValue);
//		}
//
//		return list;
//	}
//
//	public static boolean copyFile(String strSourceFile, String aDestinationFile) {
//		boolean status = false;
//
//		try {
//			FileInputStream in = new FileInputStream(strSourceFile);
//			FileOutputStream out = new FileOutputStream(aDestinationFile);
//
//			int c = -1;
//			while ((c = in.read()) != -1) {
//				out.write(c);
//			}
//			in.close();
//			out.close();
//			status = true;
//		} catch (Exception e) {
//			TestBase.error("Failed while copying the file:" + strSourceFile + " to destination:" + aDestinationFile);
//			TestBase.error(getExceptionTrace(e));
//		}
//
//		return status;
//	}
//
//	public static String getFileDataFromFile(String aFileName) {
//		if (isNullOrEmpty(aFileName)) {
//			deb("Input filename is empty or null");
//			return null;
//		}
//
//		try {
//
//			return FileUtils.readFileToString(new File(aFileName));
//		} catch (Exception e) {
//			deb("Failed while reading the content from the file:" + aFileName);
//			deb(getExceptionTrace(e));
//		}
//		return null;
//	}
//
//	public static ArrayList<String> getFileDataFromFileAsArrayList(String aFileName) {
//		ArrayList<String> buf = new ArrayList<String>();
//		if (isNullOrEmpty(aFileName)) {
//			deb("Input filename is empty or null");
//		}
//		try {
//
//			BufferedReader bufReader = new BufferedReader(new FileReader(new File(aFileName)));
//			String line = null;
//			while ((line = bufReader.readLine()) != null) {
//				buf.add(line);
//			}
//			bufReader.close();
//		} catch (Exception e) {
//			deb("Failed while reading the content from the file:" + aFileName);
//			deb(getExceptionTrace(e));
//		}
//		return buf;
//	}
//
//	public String getBase64EncodedString(String aValue) {
//		String strBase64EncodedString = null;
//		Base64 b64 = new Base64();
//		try {
//
//			strBase64EncodedString = new String(b64.encode(aValue.getBytes("UTF-8")));
//			// strBase64EncodedString = b64.encode(aValue.getBytes());
//		} catch (Exception e) {
//		}
//		return strBase64EncodedString;
//	}
//
//	public String getBase64DecodedString(String aValue) {
//		String strBase64DecodedString = null;
//		Base64 b64 = new Base64();
//		try {
//			byte[] result = b64.decode(aValue.getBytes("UTF-8"));
//			strBase64DecodedString = new String(result);
//		} catch (Exception e) {
//		}
//		return strBase64DecodedString;
//	}
//
//	public String getMD5Digest(String aValue) {
//		String strDigest = null;
//		try {
//			byte[] bytesOfMessage = aValue.getBytes("UTF-8");
//
//			MessageDigest md = MessageDigest.getInstance("MD5");
//			byte[] theDigest = md.digest(bytesOfMessage);
//
//			strDigest = convertToHex(theDigest);
//
//		} catch (Exception e) {
//		}
//
//		return strDigest;
//	}
//
//	public String convertToHex(byte[] data) {
//		StringBuffer buf = new StringBuffer();
//		for (int i = 0; i < data.length; i++) {
//			int halfbyte = (data[i] >>> 4) & 0x0F;
//			int two_halfs = 0;
//			do {
//				if ((0 <= halfbyte) && (halfbyte <= 9))
//					buf.append((char) ('0' + halfbyte));
//				else
//					buf.append((char) ('a' + (halfbyte - 10)));
//				halfbyte = data[i] & 0x0F;
//			} while (two_halfs++ < 1);
//		}
//		return buf.toString();
//	}
//
//	public static String getLogDateTimeInFormat(String aFormat) {
//		SimpleDateFormat formatter = new SimpleDateFormat(aFormat);
//		Date timeStamp = new Date();
//		return formatter.format(timeStamp);
//	}
//
//	public static String getDateTimeInFormat(java.sql.Date aDate, String aFormat) {
//		SimpleDateFormat formatter = new SimpleDateFormat(aFormat);
//		return formatter.format(aDate);
//	}
//
//	public static String getDateTimeInFormat(Date aDate, String aFormat) {
//		SimpleDateFormat formatter = new SimpleDateFormat(aFormat);
//		return formatter.format(aDate);
//	}
//
//	public static Date getDateTimeFromString(String aDate, String aFormat) {
//		SimpleDateFormat formatter = new SimpleDateFormat(aFormat);
//		try {
//			return formatter.parse(aDate);
//		} catch (Exception e) {
//			TestBase.error("Failed while parsing the given string to date. Error:" + e.getMessage());
//		}
//		return null;
//	}
//
//	public static String convertDateToGivenFormatFromString(String aDate, String aFormat) {
//		SimpleDateFormat formatter = new SimpleDateFormat(aFormat);
//		try {
//			Date formattedDate = formatter.parse(aDate);
//			return formatter.format(formattedDate);
//		} catch (Exception e) {
//			TestBase.error("Failed while parsing the given string to date. Error:" + e.getMessage());
//		}
//		return null;
//	}
//
//	public static String getFormattedString(String aString, String aSrcFormat, String aFormat) {
//		try {
//			SimpleDateFormat formatter = new SimpleDateFormat(aSrcFormat);
//			Date d = formatter.parse(aString);
//
//			SimpleDateFormat destFormatter = new SimpleDateFormat(aFormat);
//			return destFormatter.format(d);
//		} catch (Exception e) {
//			TestBase.error("Failed toget the formatted string. Error:" + getExceptionTrace(e));
//		}
//		return null;
//	}
//
//	public static String getNumericFormattedString(String aString) {
//		String strFormattedString = null;
//		try {
//			strFormattedString = aString.replaceAll(",", "");
//		} catch (Exception e) {
//			TestBase.error("Failed to get theget NumericFormatted String. Error:" + getExceptionTrace(e));
//		}
//		return strFormattedString;
//	}
//
//	public static String getNumberFormattedString(String aData, String aFormat) {
//		String strFormattedData = null;
//		if (aData == null) {
//			TestBase.error("Failed as the input string data is null / empty");
//			return strFormattedData;
//		}
//
//		try {
//
//			aData = aData.replaceAll(",", "");
//			long input = Long.parseLong(aData);
//			DecimalFormat doubleFormat = new DecimalFormat(".00");
//			strFormattedData = doubleFormat.format(input);// String.format(aFormat,
//															// input);
//		} catch (Exception e) {
//			TestBase.error("Failed while parsing the number formatted data. Error:" + getExceptionTrace(e));
//		}
//		return strFormattedData;
//	}
//
//	public static int getIntegerFromDecimalString(String aValue) {
//		int value = -1;
//		try {
//			double tmp = Double.parseDouble(aValue);
//			value = (int) tmp;
//		} catch (Exception e) {
//			TestBase.error("Failed to parse to Integer. Error:" + getExceptionTrace(e));
//		}
//		return value;
//	}
//
//	public static int getIntegerFromString(String aValue, int aDefaultValue) {
//		int value = aDefaultValue;
//		try {
//			aValue = aValue.replaceAll(",", ""); // remove if commas are present
//													// in the input
//			value = Integer.parseInt(aValue);
//		} catch (Exception e) {
//			TestBase.error("Exception occurred while parsing for integer:" + aValue + " Returning the default:" + aDefaultValue); // Failed
//																																	// to
//																																	// parse
//																																	// to
//																																	// Integer.
//																																	// Error:"
//																																	// +
//																																	// getExceptionTrace(e));
//			value = aDefaultValue;
//		}
//		return value;
//	}
//
//	public static int getIntegerFromString(String aValue) {
//		int value = -1;
//		try {
//			aValue = aValue.replaceAll(",", ""); // remove if commas are present
//													// in the input
//			value = Integer.parseInt(aValue);
//		} catch (Exception e) {
//			TestBase.error("Exception occurred while parsing for integer:" + aValue + ". Error:" + getExceptionTrace(e));
//		}
//		return value;
//	}
//
//	public static String getFormattedPercentage(String aValue) {
//		aValue = aValue.replace("(", "");
//		aValue = aValue.replace(")", "");
//		aValue = aValue.replace("%", "");
//
//		return aValue;
//	}
//
//	public static long getLongFromString(String aValue) {
//		long value = -1;
//		blnNumberParseError = false;
//		try {
//			aValue = aValue.replaceAll(",", "");
//			value = Long.parseLong(aValue);
//		} catch (Exception e) {
//			TestBase.error("Failed to parse to Long. Error:" + getExceptionTrace(e));
//			blnNumberParseError = true;
//		}
//		return value;
//	}
//
//	public static long getLongFromString(String aValue, long aDefaultValue) {
//		long value = aDefaultValue;
//		blnNumberParseError = false;
//		try {
//			aValue = aValue.replaceAll(",", "");
//			value = Long.parseLong(aValue);
//		} catch (Exception e) {
//			TestBase.error("Failed to parse to Long. Error:" + getExceptionTrace(e));
//			blnNumberParseError = true;
//			value = aDefaultValue;
//		}
//		return value;
//	}
//
//	public static double getDoubleFromString(String aValue) {
//		double value = -1;
//		blnNumberParseError = false;
//
//		try {
//			// Convert the input to double value
//			aValue = aValue.replaceAll(",", "");
//
//			value = Double.parseDouble(aValue);
//
//			/*
//			 * //Convert to 2 place decimal holder DecimalFormat doubleFormat =
//			 * new DecimalFormat(".00"); aValue = doubleFormat.format(value);
//			 * value = Double.parseDouble(aValue);
//			 */
//		} catch (Exception e) {
//			TestBase.error("Failed to parse to double. Error:" + getExceptionTrace(e));
//			blnNumberParseError = true;
//		}
//		return value;
//	}
//
//	public static double getDoubleFromString(String aValue, double aDefaultValue) {
//		double value = aDefaultValue;
//		blnNumberParseError = false;
//
//		try {
//			// Convert the input to double value
//			aValue = aValue.replaceAll(",", "");
//
//			value = Double.parseDouble(aValue);
//
//			/*
//			 * //Convert to 2 place decimal holder DecimalFormat doubleFormat =
//			 * new DecimalFormat(".00"); aValue = doubleFormat.format(value);
//			 * value = Double.parseDouble(aValue);
//			 */
//		} catch (Exception e) {
//			TestBase.error("Failed to parse to double. Error:" + getExceptionTrace(e));
//			blnNumberParseError = true;
//			value = aDefaultValue;
//		}
//		return value;
//	}
//
//	public static String getDecimalFormattedFromDouble(double aValue) {
//		blnNumberParseError = false;
//		try {
//			if (aValue == 0) {
//				return "0.00";
//			}
//			// Convert the input to double value
//			// Convert to 2 place decimal holder
//			DecimalFormat doubleFormat = new DecimalFormat(".00");
//			return doubleFormat.format(aValue);
//		} catch (Exception e) {
//			TestBase.error("Failed to parse to double. Error:" + getExceptionTrace(e));
//			blnNumberParseError = true;
//		}
//		return null;
//	}
//
//	public String get1000CommaFormattedText(String aValue) {
//		String value = null;
//		try {
//			double tmp = Double.parseDouble(aValue);
//			value = String.format("%,.0f", tmp);
//		} catch (Exception e) {
//			TestBase.error("Failed while parsing the number formatted data. Error:" + getExceptionTrace(e));
//		}
//		return value;
//	}
//
//	public static PropertiesConfiguration getFileAsProperties(String aFileName) {
//		PropertiesConfiguration p = null;
//
//		try {
//			p = new PropertiesConfiguration();
//			p.setListDelimiter((char) 10);
//			FileInputStream fin = new FileInputStream(new File(aFileName));
//			p.load(fin);
//			fin.close();
//		} catch (Exception e) {
//			TestBase.error("Failed to load the file:" + aFileName + " as properties.");
//			TestBase.error(getExceptionTrace(e));
//		}
//
//		return p;
//	}
//
//	public static boolean writePropertiesAsFile(PropertiesConfiguration aProperties, String aFileName) {
//		boolean status = false;
//		if (aProperties == null) {
//			TestBase.error("Failed to write the properties into the file:" + aFileName + " as the property object is null");
//			return status;
//		}
//
//		try {
//			aProperties.save(new FileOutputStream(aFileName));
//			status = true;
//		} catch (Exception e) {
//			TestBase.error("Failed to save the file:" + aFileName + " as properties.");
//			TestBase.error(getExceptionTrace(e));
//		}
//
//		return status;
//	}
//
//	public static boolean isStringPresentInArray(String aValueToCheck, String[] data) {
//		boolean status = false;
//		if (aValueToCheck == null) {
//			return status;
//		}
//		for (String strData : data) {
//			if (strData == null)
//				continue;
//			if (strData.equals(aValueToCheck)) {
//				status = true;
//				break;
//			}
//		}
//		return status;
//	}
//
//	public static boolean isStringPresentInArray(String aValueToCheck, String[] data, boolean aCaseSensitive) {
//		boolean status = false;
//		if (aValueToCheck == null) {
//			return status;
//		}
//		for (String strData : data) {
//			if (strData == null)
//				continue;
//			if (aCaseSensitive) {
//				if (strData.equals(aValueToCheck)) {
//					status = true;
//					break;
//				}
//			} else {
//				if (strData.equalsIgnoreCase(aValueToCheck)) {
//					status = true;
//					break;
//				}
//			}
//		}
//		return status;
//	}
//
//	public static boolean isStringContainsInArrayList(String aValueToCheck, ArrayList<String> data, boolean aCaseSensitive) {
//		boolean status = false;
//		if (aValueToCheck == null) {
//			return status;
//		}
//		aValueToCheck = aValueToCheck.trim();
//		for (String strData : data) {
//			if (strData == null)
//				continue;
//			// TestBase.log("Checking list data:" + strData + " with:" +
//			// aValueToCheck);
//			if (aCaseSensitive) {
//				status = StringUtils.contains(strData, aValueToCheck);
//			} else {
//				status = StringUtils.containsIgnoreCase(strData, aValueToCheck);
//			}
//			if (status) {
//				break;
//			}
//		}
//		return status;
//	}
//
//	public void dumpProperties(Properties p) {
//
//		StringBuffer buf = new StringBuffer();
//
//		String strHeader = getFormattedRow("NAME", "VALUE");
//		buf.append(getHyphenFormat(strHeader.length())).append(AutomationUtilities.LINE_SEPARATOR);
//		buf.append(strHeader).append(AutomationUtilities.LINE_SEPARATOR);
//		buf.append(getHyphenFormat(strHeader.length())).append(AutomationUtilities.LINE_SEPARATOR);
//
//		Enumeration keys = p.propertyNames();
//		while (keys.hasMoreElements()) {
//			String strKey = (String) keys.nextElement();
//			// deb(strKey + ", " + p.getProperty(strKey));
//			String strValue = getFormattedRow(strKey, p.getProperty(strKey));
//			buf.append(strValue).append(AutomationUtilities.LINE_SEPARATOR);
//		}
//		buf.append(getHyphenFormat(strHeader.length())).append(AutomationUtilities.LINE_SEPARATOR);
//
//		deb("Property Dump:" + AutomationUtilities.LINE_SEPARATOR + buf.toString());
//	}
//
//	public void dumpProperties(String aCaption, Properties p) {
//
//		StringBuffer buf = new StringBuffer();
//
//		String strHeader = getFormattedRow("NAME", "VALUE");
//		buf.append(getHyphenFormat(strHeader.length())).append(AutomationUtilities.LINE_SEPARATOR);
//		buf.append(strHeader).append(AutomationUtilities.LINE_SEPARATOR);
//		buf.append(getHyphenFormat(strHeader.length())).append(AutomationUtilities.LINE_SEPARATOR);
//
//		Enumeration keys = p.propertyNames();
//		while (keys.hasMoreElements()) {
//			String strKey = (String) keys.nextElement();
//			// deb(strKey + ", " + p.getProperty(strKey));
//			String strValue = getFormattedRow(strKey, p.getProperty(strKey));
//			buf.append(strValue).append(AutomationUtilities.LINE_SEPARATOR);
//		}
//		buf.append(getHyphenFormat(strHeader.length())).append(AutomationUtilities.LINE_SEPARATOR);
//
//		deb(aCaption + AutomationUtilities.LINE_SEPARATOR + AutomationUtilities.LINE_SEPARATOR + buf.toString());
//	}
//
//	public String getFormattedRow(String aName, String aValue) {
//		StringBuffer buf = new StringBuffer();
//
//		buf.append(getFormattedDisplay(aName));
//		buf.append(getFormattedDisplay(aValue));
//		return buf.toString();
//	}
//
//	public void dumpHashtable(Hashtable<String, Object> aItem) {
//
//		StringBuffer buf = new StringBuffer();
//
//		String strHeader = getFormattedRow("NAME", "VALUE");
//		buf.append(getHyphenFormat(strHeader.length())).append(AutomationUtilities.LINE_SEPARATOR);
//		buf.append(strHeader).append(AutomationUtilities.LINE_SEPARATOR);
//		buf.append(getHyphenFormat(strHeader.length())).append(AutomationUtilities.LINE_SEPARATOR);
//
//		Enumeration keys = aItem.keys();
//		while (keys.hasMoreElements()) {
//			String strKey = (String) keys.nextElement();
//			// deb(strKey + ", " + p.getProperty(strKey));
//			String strValue = getFormattedRow(strKey, testBase.getDBValueFromHashtable(strKey, aItem));
//			buf.append(strValue).append(AutomationUtilities.LINE_SEPARATOR);
//		}
//		buf.append(getHyphenFormat(strHeader.length())).append(AutomationUtilities.LINE_SEPARATOR);
//
//		deb("Hashtable Dump:" + AutomationUtilities.LINE_SEPARATOR + buf.toString());
//	}
//
//	/*
//	 * Formatted text response
//	 */
//	public String getFormattedDisplay(String aData) {
//		StringBuffer buf = new StringBuffer();
//		int displayWidth = MAX_CHAR_COL_DISPLAY_WIDTH - aData.length();
//		if (displayWidth > 0) {
//			String strLeftSpace = getIndentSpaces(displayWidth / 2);
//			buf.append(strLeftSpace + aData);
//			buf.append(getIndentSpaces(displayWidth - strLeftSpace.length()) + "|");
//		} else {
//			aData = aData.substring(0, (aData.length() - MIN_CHAR_COL_SPACE_ASIDE_WIDTH - 1));
//			aData += "."; // to show truncation
//			buf.append(getIndentSpaces(MIN_CHAR_COL_SPACE_ASIDE_WIDTH / 2) + aData + getIndentSpaces(MIN_CHAR_COL_SPACE_ASIDE_WIDTH / 2) + "|");
//		}
//		return buf.toString();
//	}
//
//	public String getIndentSpaces(int aLength) {
//		StringBuffer buf = new StringBuffer();
//		for (int i = 0; i < aLength; i++) {
//			buf.append(" ");
//		}
//		return buf.toString();
//	}
//
//	public String getHyphenFormat(int aLength) {
//		StringBuffer buf = new StringBuffer();
//		for (int i = 0; i < aLength; i++) {
//			buf.append("-");
//		}
//		return buf.toString();
//	}
//
//	private static void deb(String aLog) {
//		TestBase.log(aLog);
//	}
//
//	public static void dumpStringArrayAsString(String aCaption, String[] arr) {
//		StringBuilder buf = new StringBuilder();
//		for (String item : arr) {
//			buf.append(item).append(" ");
//		}
//		TestBase.log(aCaption + ":" + buf.toString());
//	}
//
//	public static void dumpStringArray(String[] arr) {
//		for (String item : arr) {
//			TestBase.log("The item is:" + item);
//		}
//	}
//
//	public static void dumpStringArray(String aCaption, String[] arr) {
//		TestBase.log(aCaption);
//		for (String item : arr) {
//			TestBase.log("The item is:" + item);
//		}
//	}
//
//	public static void dumpStringArrayList(ArrayList<String> arr) {
//		for (String item : arr) {
//			TestBase.log("The item is:" + item);
//		}
//	}
//
//	public static void dumpStringArrayList(String caption, ArrayList<String> arr) {
//		TestBase.log(caption);
//		for (String item : arr) {
//			TestBase.log("The item is:" + item);
//		}
//	}
//
//	public void dumpBytes(String aValue) {
//		if (isNullOrEmpty(aValue)) {
//			return;
//		}
//		byte[] bytes = aValue.getBytes(Charset.defaultCharset());
//		String byteDecodedData = "";
//		for (byte b : bytes) {
//			byteDecodedData += "[" + b + "] ";
//		}
//		TestBase.log("Bytes for:" + aValue + " is:" + byteDecodedData);
//	}
//
//	public void dumpBytes(String aValue, String strEncoding) {
//		if (isNullOrEmpty(aValue)) {
//			return;
//		}
//		String byteDecodedData = "";
//		try {
//			byte[] bytes = aValue.getBytes(strEncoding);
//			for (byte b : bytes) {
//				byteDecodedData += "[" + b + "] ";
//			}
//		} catch (Exception e) {
//			byteDecodedData += " Failed to getBytes of string:" + aValue + " Err:" + e.getMessage();
//		}
//		TestBase.log("Bytes for:" + aValue + " is:" + byteDecodedData);
//	}
//
//	public static String getUnicodeString(String aValue, int aOption) {
//		if (aValue == null)
//			return aValue;
//
//		String strUnicodeValue = null;
//		try {
//			if (aOption == DB_STR_MODE) {
//				strUnicodeValue = new String(aValue.getBytes(Charset.forName("ISO-8859-1")));
//			} else if (aOption == WS_STR_MODE) {
//				String strModified = new String(aValue.getBytes(), "UTF8");
//				strUnicodeValue = new String(strModified.getBytes(Charset.forName("ISO-8859-1")));
//			}
//		} catch (Exception e) {
//			TestBase.error("Failed while getting unicode based string. Error:" + e.getMessage());
//			strUnicodeValue = aValue;
//		}
//		return strUnicodeValue;
//	}
//
//	public static void printBytes(byte[] array, String name) {
//		for (int k = 0; k < array.length; k++) {
//			TestBase.log(name + "[" + k + "] = " + "0x" + byteToHex(array[k]));
//		}
//	}
//
//	public static String byteToHex(byte b) {
//		// Returns hex String representation of byte b
//		char hexDigit[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
//		char[] array = { hexDigit[(b >> 4) & 0x0f], hexDigit[b & 0x0f] };
//		return new String(array);
//	}
//
//	public static ArrayList<String> splitRowAsArrayList(String aRow, char aSplitChar) {
//		ArrayList<String> tokens = new ArrayList<String>();
//		if (aRow == null) {
//			return tokens;
//		}
//
//		StringBuffer buf = new StringBuffer();
//		for (char c : aRow.toCharArray()) {
//			if (c == aSplitChar) {
//				tokens.add(buf.toString());
//				buf.delete(0, buf.length());
//			} else {
//				buf.append(c);
//			}
//		}
//		tokens.add(buf.toString());
//		return tokens;
//	}
//
//	public static ArrayList<String> splitRowAsArrayList(String aRow, String aSplitStr) {
//		ArrayList<String> tokens = new ArrayList<String>();
//		if (aRow == null) {
//			return tokens;
//		}
//		boolean blnAddEmptyToken = false;
//		if (aRow.endsWith(aSplitStr)) {
//			// add one empty value
//			blnAddEmptyToken = true;
//		}
//		int index = 0;
//		while (aRow.length() > 0) {
//			index = aRow.indexOf(aSplitStr);
//			if (index == -1) {
//				// add the left over which is the last part
//				tokens.add(aRow);
//				break; // not found. break it now
//			}
//			String strFoundValue = aRow.substring(0, index);
//			tokens.add(strFoundValue);
//
//			// remove the added part from the string and remove the split str
//			// also
//			aRow = aRow.substring(strFoundValue.length() + aSplitStr.length());
//		}
//		/*
//		 * for(char c : aRow.toCharArray()) { if(c == aSplitChar) {
//		 * tokens.add(buf.toString()); buf.delete(0, buf.length()); } else {
//		 * buf.append(c); } }
//		 */
//		if (blnAddEmptyToken) {
//			tokens.add("");
//		}
//		return tokens;
//	}
//
//	public static String getFormattedXML(String aXMLString) {
//		String result = null;
//		try {
//			Source xmlInput = new StreamSource(new StringReader(aXMLString));
//			StringWriter stringWriter = new StringWriter();
//			StreamResult xmlOutput = new StreamResult(stringWriter);
//			TransformerFactory transformerFactory = TransformerFactory.newInstance();
//			// transformerFactory.setAttribute("indent-number", new Integer(2));
//			Transformer transformer = transformerFactory.newTransformer();
//			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
//			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//			transformer.transform(xmlInput, xmlOutput);
//			result = xmlOutput.getWriter().toString();
//
//		} catch (Exception e) {
//			// Exception occurred while formatting
//			TestBase.error("Failed in formatting the xml. Error:" + e.getMessage());
//			result = null;
//		}
//		return result;
//	}
//
//	public void dumpXML(String aHeader, String aXML) {
//		StringBuffer buf = new StringBuffer();
//
//		String strHeader = getFormattedRow("XML", aHeader);
//		buf.append(getHyphenFormat(strHeader.length())).append(AutomationUtilities.LINE_SEPARATOR);
//		buf.append(strHeader).append(AutomationUtilities.LINE_SEPARATOR);
//		buf.append(getHyphenFormat(strHeader.length())).append(AutomationUtilities.LINE_SEPARATOR);
//
//		buf.append(aXML);
//		buf.append(getHyphenFormat(strHeader.length())).append(AutomationUtilities.LINE_SEPARATOR);
//
//		deb(AutomationUtilities.LINE_SEPARATOR + buf.toString());
//	}
//
//	/*
//	 * Returns the index in the arraylist for the given value
//	 */
//	public static int getTextIndexFromArray(String aTextToFind, ArrayList<String> aData) {
//		if (aData == null || aTextToFind == null)
//			return -1;
//
//		/*
//		 * int index = 0; for(String strData : aData) {
//		 * if(isNullOrEmpty(strData)) { index++; continue; }
//		 * if(strData.equals(aTextToFind)) { return index; } index++; } return
//		 * -1;
//		 */
//		return aData.indexOf(aTextToFind);
//	}
//
//	public static String getFormattedBrowserName(String aName) {
//		if (aName.startsWith("*")) {
//			aName = aName.substring(1);
//		}
//		return aName;
//	}
//
//	public static boolean saveRectangleToFile(String aFileName, Rectangle aRect) {
//		boolean status = false;
//		PropertiesConfiguration conf = new PropertiesConfiguration();
//		conf.setProperty("X", (int) aRect.getX());
//		conf.setProperty("Y", (int) aRect.getY());
//		conf.setProperty("WIDTH", (int) aRect.getWidth());
//		conf.setProperty("HEIGHT", (int) aRect.getHeight());
//
//		status = writePropertiesAsFile(conf, aFileName);
//		return status;
//	}
//
//	public static boolean executeAppleScript(String aFileNameToRun, String... args) {
//		boolean status = false;
//		if (StringUtils.isBlank(aFileNameToRun)) {
//			TestBase.error("Failed to run the apple script as the file name to run is found as null / empty");
//			return status;
//		}
//
//		ArrayList<String> paramsList = new ArrayList<String>();
//		paramsList.add("osascript");
//		paramsList.add(aFileNameToRun);
//
//		for (String arg : args) {
//			paramsList.add(arg);
//		}
//
//		ProcessRunner r = new ProcessRunner(paramsList);
//		int exitCode = r.runProcessAndWaitToComplete();
//		String strOutput = StringUtils.join(r.getOutput(), System.getProperty("line.separator"));
//		String strError = StringUtils.join(r.getError(), System.getProperty("line.separator"));
//
//		TestBase.log("Executing Applescript:" + aFileNameToRun + " is completed with exitCode:" + exitCode);
//		TestBase.log("Ouput:" + strOutput + ", Error:" + strError);
//
//		status = true;
//		/*
//		 * String[] runParams = paramsList.toArray(new
//		 * String[paramsList.size()]); try {
//		 * 
//		 * AutomationUtilities.dumpStringArrayAsString("Launching the app:",
//		 * runParams);
//		 * 
//		 * ProcessBuilder pb = new ProcessBuilder(runParams);
//		 * pb.redirectErrorStream(true);
//		 * 
//		 * Process p = pb.start(); int exitCode = p.waitFor();
//		 * 
//		 * if(exitCode != 0) {
//		 * TestBase.error("Failed to launch the application:" + aFileNameToRun);
//		 * } else { AutomationUtilities.delay(3000); status = true; } //making
//		 * sure that the window is in foreground
//		 * 
//		 * } catch(Exception e) {
//		 * 
//		 * }
//		 */
//
//		return status;
//	}
//
//	public static Process launchProcess(String aAppLauncher, String... args) {
//		Process process = null;
//		if (StringUtils.isBlank(aAppLauncher)) {
//			TestBase.error("Failed to launch the process as the app launcher is found as null / empty");
//			return process;
//		}
//
//		ArrayList<String> paramsList = new ArrayList<String>();
//		paramsList.add(aAppLauncher);
//
//		for (String arg : args) {
//			paramsList.add(arg);
//		}
//
//		String[] runParams = paramsList.toArray(new String[paramsList.size()]);
//		try {
//
//			AutomationUtilities.dumpStringArrayAsString("Launching the app:", runParams);
//
//			ProcessBuilder pb = new ProcessBuilder(runParams);
//			pb.redirectErrorStream(true);
//
//			Map<String, String> env = pb.environment();
//			for (String strKey : env.keySet()) {
//				// TestBase.log("Key:" + strKey + " and its value:" +
//				// env.get(strKey));
//			}
//
//			process = pb.start();
//
//			if (process != null) {
//				TestBase.log("Successfully launched the application:" + aAppLauncher);
//			}
//
//		} catch (Exception e) {
//			TestBase.error("Exception occurred while launching the application:" + ", Error:" + e.getMessage());
//		}
//
//		return process;
//	}
//
//	public static SeleniumServer launchSeleniumServer() {
//		SeleniumServer server = null;
//		;
//		try {
//			server = new SeleniumServer();
//			server.start();
//		} catch (Exception e) {
//			TestBase.error("Failed to start the Selenium Server. Error:" + e.getMessage());
//		}
//		return server;
//	}
//
//	public static Process launchProcessByFileContent(String aAppLauncher, String aFileName) {
//		Process process = null;
//		if (StringUtils.isBlank(aFileName) || StringUtils.isBlank(aAppLauncher)) {
//			TestBase.error("Failed to launch the process by file content / launcher as the file name is found as null / empty");
//			return process;
//		}
//
//		File f = new File(aFileName);
//		if (!f.exists()) {
//			TestBase.error("Failed to launch the process by file content as the file name:" + aFileName + " doesn't exist");
//			return process;
//		}
//
//		try {
//
//			String strFileContent = FileUtils.readFileToString(new File(aFileName));
//			if (StringUtils.isBlank(strFileContent)) {
//				TestBase.error("Failed to launch the process by file content as the file name:" + aFileName + " content is null / empty");
//				return process;
//			}
//
//			String[] tokens = strFileContent.split(" ");
//
//			ArrayList<String> paramsList = new ArrayList<String>();
//			paramsList.add(aAppLauncher);
//
//			for (String arg : tokens) {
//				paramsList.add(arg);
//			}
//			String[] runParams = paramsList.toArray(new String[paramsList.size()]);
//
//			AutomationUtilities.dumpStringArrayAsString("Launching the app:", runParams);
//			ProcessBuilder pb = new ProcessBuilder(runParams);
//			pb.redirectErrorStream(true);
//			pb.directory(new File(TestBase.strBaseDir));
//
//			Map<String, String> env = pb.environment();
//			for (String strKey : env.keySet()) {
//				// TestBase.log("Key:" + strKey + " and its value:" +
//				// env.get(strKey));
//			}
//
//			process = pb.start();
//
//			if (process != null) {
//
//				/*
//				 * ProcessReader reader = new ProcessReader(process); Thread th
//				 * = new Thread(reader); th.start();
//				 */
//				TestBase.log("Successfully launched the application");
//			}
//
//		} catch (Exception e) {
//			TestBase.error("Exception occurred while launching the application:" + ", Error:" + e.getMessage());
//		}
//
//		return process;
//	}
//
//	public static boolean killAllProcessByName(String aProcessName) {
//		boolean status = false;
//		if (StringUtils.isBlank(aProcessName)) {
//			TestBase.error("Failed to kill all the process by name as the name of the process is found to be null / empty");
//			return status;
//		}
//		TestBase.log("Killing all the process by name:" + aProcessName);
//		try {
//
//			Process p = launchProcess("killall", "-v", aProcessName);
//			if (p != null) {
//				int exitCode = p.waitFor();
//				TestBase.log("Kill all process by name:" + aProcessName + " exited with code:" + exitCode);
//				status = true;
//			}
//		} catch (Exception e) {
//			TestBase.error("Exception occurred while killing all process by name:" + aProcessName + ", Error:" + e.getMessage());
//		}
//
//		return status;
//	}
//
//	public static boolean killAllProcessWithRegularExpression(String... aProcessSearchPattern) {
//		boolean status = false;
//		if (aProcessSearchPattern == null || aProcessSearchPattern.length <= 0) {
//			TestBase.error("Failed to kill all the process by search pattern as the search pattern for the processes found to be null / empty");
//			return status;
//		}
//		TestBase.log("Killing all the process by pattern:" + AutomationUtilities.convertStringArrayToString(aProcessSearchPattern));
//		try {
//
//			List<String> cmds = new ArrayList<String>(Arrays.asList(aProcessSearchPattern));
//
//			cmds.add(0, STR_SHELL_COMMAND);
//			ProcessRunner r = new ProcessRunner(cmds);
//			int exitCode = r.runProcessAndWaitToComplete();
//			String strOutput = StringUtils.join(r.getOutput(), System.getProperty("line.separator"));
//			String strError = StringUtils.join(r.getError(), System.getProperty("line.separator"));
//
//			TestBase.log("Kill all process by search pattern:" + StringUtils.join(aProcessSearchPattern, " ") + " is completed with exitCode:" + exitCode);
//			TestBase.log("Ouput:" + strOutput + ", Error:" + strError);
//			status = true;
//			/*
//			 * Process p = launchProcess(STR_SHELL_COMMAND,
//			 * aProcessSearchPattern); if(p != null) { TestBase.log(
//			 * "Launched the process.. Waiting for the process to complete...");
//			 * int exitCode = p.waitFor();
//			 * TestBase.log("Kill all process by search pattern:" +
//			 * AutomationUtilities
//			 * .convertStringArrayToString(aProcessSearchPattern) +
//			 * " exited with code:" + exitCode); status = true; } else {
//			 * TestBase.error("The process is not null. Is it Alive:"); }
//			 */
//		} catch (Exception e) {
//			e.printStackTrace();
//			TestBase.error("Exception occurred while killing all process by search pattern:" + aProcessSearchPattern + ", Error:" + e.getMessage());
//		}
//
//		return status;
//	}
//
//	public static File copyFile(File aOldFile, String aNewFilePath) {
//		File f = new File(aNewFilePath);
//		try {
//			FileUtils.copyFile(aOldFile, f, true);
//		} catch (Exception e) {
//			return null;
//		}
//		return f;
//	}
//
//	public static int[] convertIntegers(List<Integer> integers) {
//		int[] ret = new int[integers.size()];
//		Iterator<Integer> iterator = integers.iterator();
//		for (int i = 0; i < ret.length; i++) {
//			ret[i] = iterator.next().intValue();
//		}
//		return ret;
//	}
//
	public static String convertStringArrayToString(List<String> aList) {
		StringBuilder buf = new StringBuilder();
		Iterator<String> iterator = aList.iterator();
		while (iterator.hasNext()) {
			buf.append(iterator.next()).append(System.getProperty("line.separator"));
		}
		return buf.toString();
	}

	public static String convertStringArrayToString(String[] aList) {
		StringBuilder buf = new StringBuilder();

		for (String item : aList) {
			buf.append(item).append(System.getProperty("line.separator"));
		}
		return buf.toString();
	}
//
//	public static String convertStringArrayToStringWithSpaces(List<String> aList) {
//		StringBuilder buf = new StringBuilder();
//		Iterator<String> iterator = aList.iterator();
//		while (iterator.hasNext()) {
//			buf.append(iterator.next()).append(" ");
//		}
//		return buf.toString();
//	}
//
//	public static long convertToSecondsFromMills(long aMS) {
//		return aMS / 1000;
//	}
//
//	public static boolean simualteActionsUsingCliClickUtility(String... args) {
//		boolean status = false;
//		if (args == null || args.length <= 0) {
//			TestBase.error("Failed as the command line argument for CliClick not found");
//			return status;
//		}
//
//		try {
//			String strExecutable = TestBase.strBaseDir + File.separator + TestBase.STR_CLICLICK_CMD_EXECUTABLE_FILE;
//			File f = new File(strExecutable);
//
//			if (!f.exists()) {
//				TestBase.error("Failed to simulate actions using CliClick as the executable:" + strExecutable + " is not found");
//				return status;
//			}
//
//			Process p = launchProcess(strExecutable, args);
//			if (p != null) {
//				int exitCode = p.waitFor();
//				TestBase.log("CliClick utility exited with code:" + exitCode);
//				status = true;
//			}
//		} catch (Exception e) {
//			TestBase.error("Exception occurred while simulating the actions using CliClick. Error:" + e.getMessage());
//		}
//
//		return status;
//	}
//
//	// START - RINS merge
//	public static String get1000CommaFormattedTextWithDecimalValue(String aValue) {
//		String value = null;
//		try {
//			double tmp = Double.parseDouble(aValue);
//			value = String.format("%,.2f", tmp);// will return a value with
//												// comma with 2 decimal places
//		} catch (Exception e) {
//			TestBase.error("Failed while parsing the number formatted data. Error:" + getExceptionTrace(e));
//		}
//		return value;
//	}
//
//	public static String getRandomString(int len) {
//		Random random = new Random();
//		char[] charBuffer = new char[len];
//		for (int i = 0; i < charBuffer.length; ++i)
//			charBuffer[i] = (char) (random.nextInt(26) + 97);
//		return new String(charBuffer);
//	}
//
//	public static int getRandomNumber(int maxValue) {
//		Random random = new Random();
//		int randomInt = random.nextInt(maxValue);
//
//		return randomInt;
//	}
//
//	public void dumpPropertiesAsError(Properties p) {
//
//		StringBuffer buf = new StringBuffer();
//
//		String strHeader = getFormattedRow("NAME", "VALUE");
//		buf.append(getHyphenFormat(strHeader.length())).append(AutomationUtilities.LINE_SEPARATOR);
//		buf.append(strHeader).append(AutomationUtilities.LINE_SEPARATOR);
//		buf.append(getHyphenFormat(strHeader.length())).append(AutomationUtilities.LINE_SEPARATOR);
//
//		Enumeration keys = p.propertyNames();
//		while (keys.hasMoreElements()) {
//			String strKey = (String) keys.nextElement();
//			// deb(strKey + ", " + p.getProperty(strKey));
//			String strValue = getFormattedRow(strKey, p.getProperty(strKey));
//			buf.append(strValue).append(AutomationUtilities.LINE_SEPARATOR);
//		}
//		buf.append(getHyphenFormat(strHeader.length())).append(AutomationUtilities.LINE_SEPARATOR);
//		testBase.reportErrorAndContinue("Property Dump:" + AutomationUtilities.LINE_SEPARATOR + buf.toString());
//	}
//
//	public void dumpIntegerArrayList(ArrayList<Integer> arr) {
//		for (Integer item : arr) {
//			TestBase.log("The item is:" + item);
//		}
//	}
//
//	public static String formattedDecimalValueOneDigit(String getnumber) {
//		TestBase.log("calling formatted decimal value method");
//		String formattednumber = null;
//		if (Double.parseDouble(getnumber) == 0)
//			formattednumber = getnumber;
//		else {
//			if ((getnumber.indexOf(".")) == -1)
//				formattednumber = getnumber + ".0";
//			else if ((getnumber.indexOf(".")) != -1) {
//				if ((getnumber.length()) - (getnumber.indexOf(".")) != 3)
//					formattednumber = getnumber + "0";
//				else
//					formattednumber = getnumber;
//			}
//		}
//
//		DecimalFormat df = new DecimalFormat("#0.0");
//		Double dVal = Double.valueOf(formattednumber);
//		formattednumber = df.format(dVal);
//
//		TestBase.log("formatted number" + formattednumber);
//		return formattednumber;
//	}
//
//	public static String zeroBeforeDecimalValue(String getNumber) {
//		TestBase.log("calling zero before decimal value method");
//		String formattednumber = getNumber;
//		if (getNumber.indexOf('.') >= 1)
//			formattednumber = getNumber;
//		else if (getNumber.indexOf('.') == 0)
//			formattednumber = "0" + getNumber;
//		TestBase.log("formatted number" + formattednumber);
//		return formattednumber;// Double.toString(Double.parseDouble(num));
//	}
//
//	/*
//	 * Returns the 3 digit representation of the given country code (2 digit)
//	 */
//	public static String get3DigitCountryCode(String aCountryCode) {
//		Locale l = new Locale("", aCountryCode);
//		return l.getISO3Country();
//	}

	// END - RINS merge

}
