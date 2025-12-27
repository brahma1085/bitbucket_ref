package com.exilant.grx.iTAP.plugin;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import com.exilant.tfw.bean.UtlProps;

/**
 * Generate random device ids for GRX. Since at times we can have server set to
 * accept certain device ids (when talking to SAP) this class can be configured
 * to give from a fixed set or generate a random one
 * */

public class DeviceIdGenerator {

	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(DeviceIdGenerator.class);

	/** The post device ids. */
	public static ArrayList<String> POST_DEVICE_IDS;

	/** The srtl. */
	private static ThreadLocal<SecureRandom> srtl = new ThreadLocal<SecureRandom>();

	/** The nxt fixed. */
	private static ThreadLocal<Integer> nxtFixed = new ThreadLocal<Integer>();

	/** The fixed ids. */
	private static List<String> fixedIds = new ArrayList<String>();

	/** The props. */
	private static Properties props;

	/** The connection. */
	private java.sql.Connection connection;

	/** The stm. */
	private java.sql.Statement stm;

	/** The rs. */
	private ResultSet rs;

	/** The date format. */
	private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");

	/** The date. */
	private Date date = new Date();

	/** The prop. */
	public UtlProps prop;

	/**
	 * Instantiates a new device id generator.
	 */
	public DeviceIdGenerator() {
		props = new Properties();

	}

	/**
	 * Instantiates a new device id generator.
	 * 
	 * @param propsp
	 *            the propsp
	 */
	public DeviceIdGenerator(Properties propsp) {
		props = propsp;
		initFixed();
	}

	/**
	 * Sets the props.
	 * 
	 * @param propsp
	 *            the new props
	 */
	public void setProps(Properties propsp) {
		props = propsp;
		initFixed();
	}

	/**
	 * Test generate.
	 */
	public void testGenerate() {
		int count = 100000;
		if (LOG.isDebugEnabled()) {
			LOG.debug("testGenerate() - end" + count);
		}
	}

	/**
	 * Inits the post device ids.
	 */
	public void initPostDeviceIds() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("initPostDeviceIds() - start"); //$NON-NLS-1$
		}
		POST_DEVICE_IDS = new ArrayList<String>();
		String pDIds = props.getProperty("postDeviceIds");
		if (pDIds == null) {
			POST_DEVICE_IDS.add(new String("DTTP"));
			POST_DEVICE_IDS.add(new String("F8GH"));
		} else {
			for (String str : pDIds.split(",")) {
				POST_DEVICE_IDS.add(str);
			}
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("initPostDeviceIds() - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Generate.
	 * 
	 * @param index
	 *            the index
	 * @return the string
	 */
	public String generate(int index) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("generate(int) - start"); //$NON-NLS-1$
		}

		int count = 1;
		List<String> ids = new ArrayList<String>();
		if (POST_DEVICE_IDS.size() <= 0) {
			initPostDeviceIds();
		}
		// int index = 0;
		int oldSize = -1, infiniteLoopDetector = 10;
		String timeSeed = props.getProperty("timeSeed");
		while (true) {
			String rand;
			if (timeSeed == null || "nano".equalsIgnoreCase(timeSeed)) {
				rand = new Long(System.nanoTime()).toString();
			} else {
				rand = new Long(System.currentTimeMillis()).toString();
			}
			// rand = rand.substring(rand.length() - 8);
			rand = rand.substring(rand.length() - 7);

			String id = rand + POST_DEVICE_IDS.get(index++);
			ids.add(id);
			if (index >= POST_DEVICE_IDS.size()) {
				index = 0;
			}

			if (ids.size() == count) {
				break;
			}
			if (ids.size() == oldSize) {
				if (0 >= infiniteLoopDetector--) {
					throw new RuntimeException("can't generate " + count + " unique ids. Try a smaller number");
				}

			} else {
				oldSize = ids.size();
				infiniteLoopDetector = 10;
			}
		}
		StringBuilder s1 = new StringBuilder(ids.get(0));
		// s1.replace(4, 5, "X");
		s1.insert(4, 'X');
		// DevSaver(s1.toString());
		String returnString = s1.toString();
		if (LOG.isDebugEnabled()) {
			LOG.debug("generate(int) - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	/*
	 * Simple File saver for Device ID and Date as req by QA team
	 */
	/**
	 * Dev saver.
	 * 
	 * @param s
	 *            the s
	 */
	public static void DevSaver(String s) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("DevSaver(String) - start"); //$NON-NLS-1$
		}

		Date dt = new Date();
		try {
			FileWriter fw = new FileWriter(new File("/data/tfw/DevIdStorage.txt"), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("Date&Time :" + dt.toString() + "  Generated Device Id:" + s + "\n");
			bw.close();
		} catch (Exception ex) {
			LOG.error("DevSaver(String)", ex); //$NON-NLS-1$

			LOG.info("Exception caught while writing data to DevFile");
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("DevSaver(String) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws SQLException
	 *             the sQL exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws InvalidFormatException
	 *             the invalid format exception
	 */
	public static void main(String[] args) throws SQLException, IOException, InvalidFormatException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("main(String[]) - start"); //$NON-NLS-1$
		}

		// genBulk();
		log4Default();
		if (args != null && args.length == 0) {
			final String s = "deviceId_gen.properties";
			args = new String[1];
			args[0] = s;
		}
		Properties pro = new Properties();
		if (args != null && args.length > 0) {
			File f = new File(args[0]);
			InputStream is = null;

			try {
				is = new BufferedInputStream(new FileInputStream(f));
				pro.load(is);
			} catch (Exception e) {
				LOG.warn("Err " + e, e);

			} finally {
				try {
					is.close();
				} catch (IOException e) {
					LOG.warn("Err close is " + e, e);
				}
			}

		}
		DeviceIdGenerator dig = new DeviceIdGenerator(pro);

		// List<Integer> indxs = new ArrayList<Integer>();
		// int idx1 = 1;

		// for (int i = 0; i < 20; i++) {
		// if (i < args.length) {
		// try {
		// idx1 = Integer.parseInt(args[i]);
		// } catch (Exception e) {
		// idx1 = dig.getRand(0, POST_DEVICE_IDS.length);
		// }
		// } else {
		// idx1 = dig.getRand(0, POST_DEVICE_IDS.length);
		// }
		//
		// String s = dig.getOne();
		// LOG.trace(i + ". " + POST_DEVICE_IDS[idx1] + " : " + s);
		// }

		for (int i = 1; i <= 20; i++) {
			LOG.trace(i + " " + dig.getOne());
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("main(String[]) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Gets the one.
	 * 
	 * @return the one
	 */
	public String getOne() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getOne() - start"); //$NON-NLS-1$
		}

		String type = props.getProperty("generateType", "random");
		initPostDeviceIds();
		String s = null;// DNPL1093DTTP
		if ("random".equals(type)) {
			int i = getRand(0, POST_DEVICE_IDS.size());
			s = generate(i);
		} else if ("dbFixed".equalsIgnoreCase(type)) {
			s = getDbFixed();
		} else {
			s = getNextFixed();
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getOne() - end"); //$NON-NLS-1$
		}
		return s;
	}

	/**
	 * Connect to db.
	 * 
	 * @return the java.sql. connection
	 */
	public java.sql.Connection connectToDB() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("connectToDB() - start"); //$NON-NLS-1$
		}

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			LOG.error("MySQL JDBC Class Not found", e);
		}
		connection = null;
		try {
			connection = DriverManager.getConnection(prop.getProperty("dB_connectionURL"), prop.getProperty("dB_username"),
					prop.getProperty("dB_password"));
		} catch (SQLException e) {
			LOG.error("Connection Failed! Check output console", e);
		}
		if (connection != null) {
			LOG.info("connected to DB.....");
		} else {
			LOG.info("Failed to make connection!");
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("connectToDB() - end"); //$NON-NLS-1$
		}
		return connection;
	}

	/**
	 * Gets the random suffix.
	 * 
	 * @return the random suffix
	 * @throws SQLException
	 *             the sQL exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String getRandomSuffix() throws SQLException, IOException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getRandomSuffix() - start"); //$NON-NLS-1$
		}

		String suffix = null;
		int count = 0;
		stm = connection.createStatement();
		rs = stm.executeQuery("select Suffix,Count from GRX_DeviceID_Suffix WHERE Active ='1' ORDER BY RAND() LIMIT 1");
		while (rs.next()) {
			suffix = rs.getString("Suffix");
			count = rs.getInt("Count");
			while ("Y".equals(prop.getProperty("Deactivate_suffix")) && prop.getProperty("limit").equals(String.valueOf(count))) {
				stm.executeUpdate("Update GRX_DeviceID_Suffix SET Active='0' where Suffix=" + "'" + suffix + "'");
				rs = stm.executeQuery("select Suffix,Count from GRX_DeviceID_Suffix WHERE Active ='1' ORDER BY RAND() LIMIT 1");
				while (rs.next()) {
					suffix = rs.getString("Suffix");
					count = rs.getInt("Count");
				}
			}
		}
		count++;
		stm.executeUpdate("Update GRX_DeviceID_Suffix SET Count=" + "'" + count + "'" + " where Suffix=" + "'" + suffix + "'");

		if (LOG.isDebugEnabled()) {
			LOG.debug("getRandomSuffix() - end"); //$NON-NLS-1$
		}
		return suffix;
	}

	/**
	 * Gen bulk device id.
	 * 
	 * @param filePath
	 *            the file path
	 * @return the list
	 * @throws InvalidFormatException
	 *             the invalid format exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws SQLException
	 *             the sQL exception
	 */
	public List<String[]> genBulkDeviceID(String filePath) throws InvalidFormatException, IOException, SQLException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("genBulkDeviceID(String) - start");
		}
		List<String> devIdList = getDeviceIDList();
		List<String[]> csvData = new ArrayList<String[]>();
		try {
			InputStream fi = DeviceIdGenerator.class.getClassLoader().getResourceAsStream("bulkupload.csv");
			CSVReader reader = new CSVReader(new InputStreamReader(fi));
			for (int j = 0; j < 18; j++) {
				String[] row = reader.readNext();
				for (int k = 0; k < row.length; k++) {
					if ("AppleCare Sales Date:".equalsIgnoreCase(row[k])) {
						row[k + 1] = dateFormat.format(date);
					}
				}
				csvData.add(row);
			}
			for (int i = 0; i < devIdList.size(); i++) {
				String[] vals = { String.valueOf(i + 1), devIdList.get(i), "", dateFormat.format(date), "Y" };
				csvData.add(vals);
			}
			CSVWriter writer = new CSVWriter(new FileWriter(filePath), ',');
			writer.writeAll(csvData);
			writer.close();
		} catch (Exception e) {
			LOG.error("Could not generate Device ids to csv file" + e, e);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("genBulkDeviceID(String) - end");
		}
		return csvData;
	}

	/**
	 * Gen bulk.
	 * 
	 * @param filePath
	 *            the file path
	 * @return the list
	 * @throws InvalidFormatException
	 *             the invalid format exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws SQLException
	 *             the sQL exception
	 */
	public List<String> genBulk(String filePath) throws InvalidFormatException, IOException, SQLException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("genBulk(String) - start");
		}
		List<String> devIdList = getDeviceIDList();
		int k = 0;
		int l = 0;
		InputStream fi = DeviceIdGenerator.class.getClassLoader().getResourceAsStream("bulkupload.xls");
		HSSFWorkbook wb = new HSSFWorkbook(fi);
		HSSFSheet sh = wb.getSheetAt(0);
		int rowcount = sh.getLastRowNum();
		for (int i = 0; i < rowcount + 1; i++) {
			Row r = sh.getRow(i);
			while (r == null) {
				i++;
				r = sh.getRow(i);
			}
			int colcount = r.getLastCellNum();
			for (int j = 0; j < colcount; j++) {
				if ("Item #".equalsIgnoreCase(sh.getRow(i).getCell(j).toString())) {
					k = i + 1;
					break;
				}
			}
		}
		fi.close();
		FileOutputStream fos = new FileOutputStream("/Users/manjunathh/Downloads/bulkupload.xls");
		HSSFRow row;
		HSSFCell cell0;
		HSSFCell cell1;
		HSSFCell cell2;
		HSSFCell cell3;
		HSSFCell cell4;
		for (int i = k; i < k + devIdList.size(); i++) {
			row = sh.createRow((short) i);
			cell0 = row.createCell(0);
			cell1 = row.createCell(1);
			cell2 = row.createCell(2);
			cell3 = row.createCell(3);
			cell4 = row.createCell(4);
			cell0.setCellValue(String.valueOf((l + 1)));
			cell1.setCellValue(devIdList.get(l).toString());
			cell2.setCellValue("");
			cell3.setCellValue(dateFormat.format(date));
			cell4.setCellValue("Y");
			l = l + 1;
		}
		LOG.info("Done...Device ids are generated into the csv file..");
		wb.write(fos);
		fos.close();
		if (LOG.isDebugEnabled()) {
			LOG.debug("genBulk(String) - end");
		}
		return devIdList;
	}

	/**
	 * Read property file.
	 * 
	 * @param filePath
	 *            the file path
	 * @return the utl props
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public UtlProps readPropertyFile(String filePath) throws IOException {
		prop = new com.exilant.tfw.bean.UtlProps(new File(filePath));
		if (LOG.isDebugEnabled()) {
			LOG.debug("readPropertyFile(String) - end");
		}
		return prop;
	}

	/**
	 * Gets the one device id.
	 * 
	 * @return the one device id
	 * @throws SQLException
	 *             the sQL exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String getOneDeviceID() throws SQLException, IOException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getOneDeviceID() - start");
		}
		String allAlphabets = prop.getProperty("remainingDigits");
		String firstDigit = prop.getProperty("firstDigit");
		String fourthDigit = prop.getProperty("fourthDigit");
		String fifthDigit = prop.getProperty("fifthDigit");
		String suffix_str1 = prop.getProperty("suffix");
		String suffix_str2 = suffix_str1.replace("[", "");
		String suffix_str3 = suffix_str2.replace("]", "");
		String[] suffix = suffix_str3.split(",");
		StringBuilder result = new StringBuilder();
		Random r = new Random();
		result.append(firstDigit.charAt(r.nextInt(firstDigit.length())));
		result.append(allAlphabets.charAt(r.nextInt(allAlphabets.length())));
		result.append(allAlphabets.charAt(r.nextInt(allAlphabets.length())));
		result.append(fourthDigit.charAt(r.nextInt(fourthDigit.length())));
		result.append(fifthDigit.charAt(r.nextInt(fifthDigit.length())));
		result.append(allAlphabets.charAt(r.nextInt(allAlphabets.length())));
		result.append(allAlphabets.charAt(r.nextInt(allAlphabets.length())));
		result.append(allAlphabets.charAt(r.nextInt(allAlphabets.length())));
		result.append(suffix[r.nextInt(suffix.length)]);
		String returnString = result.toString();
		if (LOG.isDebugEnabled()) {
			LOG.debug("getOneDeviceID() - end"); //$NON-NLS-1$
		}
		return returnString;
	}

	/**
	 * Gets the device id list.
	 * 
	 * @return the device id list
	 * @throws SQLException
	 *             the sQL exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public List<String> getDeviceIDList() throws SQLException, IOException {
		List<String> deviceIdList = new ArrayList<String>();
		long range = Long.parseLong(prop.getProperty("NoOfDeviceID_ForBulk"));
		for (int i = 0; i < range; i++) {
			String devID = getOneDeviceID();
			deviceIdList.add(devID);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getDeviceIDList() - end");
		}
		return deviceIdList;
	}

	/**
	 * Gets the device i d1.
	 * 
	 * @param range
	 *            the range
	 * @return the device i d1
	 * @throws SQLException
	 *             the sQL exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public List<String> getDeviceID1(long range) throws SQLException, IOException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getDeviceID1(long) - start"); //$NON-NLS-1$
		}

		long range_size = 0;
		long absolute_min_value = 0;
		long absolute_max_value = 0;
		int x_position;

		long start_range;
		long end_range;
		long min = 0;
		long max = 0;
		String suffix;
		String sysDate = dateFormat.format(date);
		List<Long> listOfPrefixes = new ArrayList<Long>();
		List<String> deviceIdList = new ArrayList<String>();

		File file = new File("src/main/resources/deviceId_gen.properties");
		FileInputStream fileInput = new FileInputStream(file);
		Properties properties = new Properties();
		properties.load(fileInput);
		fileInput.close();
		range_size = range;
		absolute_min_value = Long.parseLong(properties.getProperty("absolute_min_value"));
		absolute_max_value = Long.parseLong(properties.getProperty("absolute_max_value"));
		x_position = Integer.parseInt(properties.getProperty("x_position"));
		start_range = absolute_min_value;
		end_range = start_range + range_size;

		connection = connectToDB();
		stm = connection.createStatement();
		stm.executeUpdate("DELETE from Runners_DeviceID_GEN where Type='DIDG'");
		do {
			suffix = getRandomSuffix();
			if (suffix == null) {
				LOG.info("All suffixes are Inacive Please add suffixes before proceeding further");
				break;
			} else {
				rs = stm.executeQuery("select StartRange,EndRange from Runners_DeviceID_GEN where Type='DIDG' AND ExtraID = " + "'" + "KPRL" + "'");
				if (rs.next()) {
					min = rs.getLong("StartRange");
					max = rs.getLong("EndRange");
					if ((range_size + max) < absolute_max_value) {
						listOfPrefixes = getRand(min, max, range_size);
						stm.executeUpdate("update Runners_DeviceID_GEN SET StartRange=" + "'" + (max + 1) + "'" + "," + "EndRange=" + "'"
								+ (max + 1 + range_size) + "'" + "where ExtraID=" + "'" + suffix + "'");
					} else {
						stm.executeUpdate("update GRX_DeviceID_Suffix SET Active='0' where Suffix=" + "'" + suffix + "'");
					}
				} else {
					stm.executeUpdate("INSERT INTO Runners_DeviceID_GEN (Type,StartRange,EndRange,DateAdded,ExtraID,Info) VALUES('DIDG'," + start_range
							+ "," + end_range + "," + "'" + sysDate + "'" + "," + "'" + suffix + "'" + ",null" + ")");
					min = start_range;
					max = end_range;
					listOfPrefixes = getRand(min, max, range_size);
				}
			}
		} while (listOfPrefixes.size() == 0);
		StringBuilder devIdBuild = new StringBuilder();
		for (int i = 0; i < listOfPrefixes.size(); i++) {
			devIdBuild.append(listOfPrefixes.get(i));
			devIdBuild.append(suffix);
			devIdBuild.insert(x_position, "X");
			String devID = devIdBuild.substring(0, devIdBuild.length());
			devIdBuild.delete(0, devIdBuild.length());
			deviceIdList.add(devID);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getDeviceID1(long) - end"); //$NON-NLS-1$
		}
		return deviceIdList;
	}

	/**
	 * Gets the rand.
	 * 
	 * @param min
	 *            the min
	 * @param max
	 *            the max
	 * @param range_size
	 *            the range_size
	 * @return the rand
	 */
	private List<Long> getRand(long min, long max, long range_size) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getRand(long, long, long) - start"); //$NON-NLS-1$
		}

		Set<Long> randomValuesSet = new HashSet<Long>();
		List<Long> listOfRandomValues = new ArrayList<Long>();
		Iterator<Long> itr = randomValuesSet.iterator();
		SecureRandom sr = srtl.get();
		if (sr == null) {
			sr = new SecureRandom();
			srtl.set(sr);
		}
		for (int i = 0; i < range_size; i++) {
			long randomNumber = min + i;
			listOfRandomValues.add(randomNumber);
		}
		/*
		 * while(randomValuesSet.size()!=range_size){ long randomNumber = min+
		 * (long)(sr.nextDouble()*(max-min)); randomValuesSet.add(randomNumber);
		 * } listOfRandomValues.addAll(randomValuesSet);
		 */
		Collections.shuffle(listOfRandomValues);

		if (LOG.isDebugEnabled()) {
			LOG.debug("getRand(long, long, long) - end"); //$NON-NLS-1$
		}
		return listOfRandomValues;
	}

	/**
	 * Gets the db fixed.
	 * 
	 * @return the db fixed
	 */
	private String getDbFixed() {
		// String dId = TestDao.selectDeviceId();
		// if (dId != null) {
		// TestDao.updateDeviceIdStatus(dId);
		// return dId;
		// }else {
		// return new String("NoDeviceId");
		// }
		return "not done";
	}

	/**
	 * Gets the rand.
	 * 
	 * @param mi
	 *            the mi
	 * @param mx
	 *            the mx
	 * @return the rand
	 */
	private int getRand(int mi, int mx) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getRand(int, int) - start"); //$NON-NLS-1$
		}

		SecureRandom sr = srtl.get();
		if (sr == null) {
			sr = new SecureRandom();
			srtl.set(sr);
		}
		int r = sr.nextInt(mx) + mi;

		if (LOG.isDebugEnabled()) {
			LOG.debug("getRand(int, int) - end"); //$NON-NLS-1$
		}
		return r;

	}

	/**
	 * Gets the next fixed.
	 * 
	 * @return the next fixed
	 */
	private String getNextFixed() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getNextFixed() - start"); //$NON-NLS-1$
		}

		String sRtnId = "NOT-SET";
		if (fixedIds.size() > 0) {
			Integer ii = nxtFixed.get();
			int i = 0;
			if (ii == null) {
				ii = 0;
			} else {
				ii = ii + 1;
			}

			i = ii;
			if (i >= fixedIds.size()) {
				// rotate
				i = 0;
				ii = i;
			}
			nxtFixed.set(ii);
			sRtnId = fixedIds.get(i);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getNextFixed() - end"); //$NON-NLS-1$
		}
		return sRtnId;
	}

	/**
	 * Inits the fixed.
	 */
	private void initFixed() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("initFixed() - start"); //$NON-NLS-1$
		}

		int i = 1;
		String key = "fixed." + i;
		String val = props.getProperty(key);
		while (val != null) {
			fixedIds.add(val);
			i++;
			key = "fixed." + i;
			val = props.getProperty(key);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("initFixed() - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Log4 default.
	 */
	private static void log4Default() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("log4Default() - start"); //$NON-NLS-1$
		}

		boolean noLog = true;
		org.apache.log4j.Logger rootLogger = org.apache.log4j.Logger.getRootLogger();
		Enumeration appenders = rootLogger.getAllAppenders();
		if (!appenders.hasMoreElements()) {
			LOG.info("LOG4J config file is missing");
		} else {
			LOG.info("appender found " + ((Appender) appenders.nextElement()).getName());
			noLog = false;

		}

		if (noLog) {
			LOG.info("no log4j");
			Layout layout = new PatternLayout(" %-5p %t %d [%t][%F:%L] : %m%n");
			Appender ap = new ConsoleAppender(layout, ConsoleAppender.SYSTEM_OUT);
			Logger.getRootLogger().setLevel(Level.ALL);
			// Logger.getRootLogger().addAppender(new ConsoleAppender(layout,
			// ConsoleAppender.SYSTEM_ERR));
			Logger.getRootLogger().addAppender(ap);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("log4Default() - end"); //$NON-NLS-1$
		}
	}
}
