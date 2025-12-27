package com.sssoft.isatt.data.excelreader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sssoft.isatt.data.pojo.def.ReplacementString;
import com.sssoft.isatt.utils.bean.UtlConf;
import com.sssoft.isatt.utils.util.ExcelAccess;

/**
 * The Class ReadReplacementStrings.
 */
public class ReadReplacementStrings {
	
	/** Logger for this class. */
	private static final Logger LOG = Logger.getLogger(ReadReplacementStrings.class);

	/** The xls path. */
	private static String xlsPath;

	/**
	 * Gets the xls path.
	 *
	 * @return the xls path
	 */
	public static String getXlsPath() {
		return xlsPath;
	}

	/**
	 * Sets the xls path.
	 *
	 * @param xlsPath the new xls path
	 */
	public static void setXlsPath(String xlsPath) {
		ReadReplacementStrings.xlsPath = xlsPath;
	}

	/**
	 * Gets the replacement string.
	 *
	 * @return the replacement string
	 */
	public static Map<String, ReplacementString> getReplacementString() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getReplacementString() - start"); //$NON-NLS-1$
		}

		Map<String, ReplacementString> rMap = new HashMap<String, ReplacementString>();
		ReplacementString rString = null;
		ExcelAccess excelAccess = new ExcelAccess();
		excelAccess.openWorkBook(new File(UtlConf.getProperty("xlsPath.replacementString")));
		int sheetCnt = excelAccess.getNumberOfSheets();
		for (int i = 0; i < sheetCnt; i++) {
			String sheetName = excelAccess.getSheetNames(i);
			for (int i1 = 5; i1 < excelAccess.getTotalColumnCount(sheetName); i1++) {
				rString = new ReplacementString();
				rString.setAppName(excelAccess.getCellValue(sheetName, i1, 2));
				rString.setName(excelAccess.getCellValue(sheetName, i1, 5));
				rString.setValue(excelAccess.getCellValue(sheetName, i1, 6));
				rString.setEncrypted((int) excelAccess.getCellNumericValue(sheetName, i1, 7));
				rMap.put(rString.getName(), rString);
			}
		}
		LOG.info("values are filled with excel data : " + rMap);
		return rMap;
	}

}
