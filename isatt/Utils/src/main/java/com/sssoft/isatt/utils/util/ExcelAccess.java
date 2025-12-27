package com.sssoft.isatt.utils.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * The Class ExcelAccess.
 */
public class ExcelAccess {
	
	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(ExcelAccess.class);

	/** The wb. */
	private static Workbook wb;

	/**
	 * This method is to open a workbook excel.
	 * 
	 * @param workbookPath
	 *            the workbook path
	 * @return the workbook
	 */

	public void openWorkBook(File workbookPath) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("openWorkBook(File) - start"); //$NON-NLS-1$
		}

		closeIfOpen();
		InputStream inputStream = null;
		LOG.info("Opening workbook");
		try {
			inputStream = new BufferedInputStream(new FileInputStream(workbookPath));
			wb = WorkbookFactory.create(inputStream);

		} catch (Exception e) {
			LOG.error("openWorkBook(File)", e); //$NON-NLS-1$

			LOG.log(Level.ERROR, "openWorkBook opening " + workbookPath + ", " + e, e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();

				} catch (Exception e) {
					LOG.error("openWorkBook(File)", e); //$NON-NLS-1$

					LOG.log(Level.ERROR, "openWorkBook Closing" + workbookPath + ", " + e, e);

				}
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("openWorkBook(File) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * Close if open.
	 */
	private void closeIfOpen() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("closeIfOpen() - start"); //$NON-NLS-1$
		}

		try {
			if (wb != null) {
				// WorkbookFactory.

			}
		} catch (Exception e) {
			LOG.error("closeIfOpen()", e); //$NON-NLS-1$

			LOG.log(Level.ERROR, "closeIfOpen " + e, e);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("closeIfOpen() - end"); //$NON-NLS-1$
		}
	}

	/**
	 * This method is used to get the cell value for string format.
	 *
	 * @param sheetName the sheet name
	 * @param iRow the row no
	 * @param iCol the column no
	 * @return the string
	 */

	public String getCellValue(String sheetName, int iRow, int iCol) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getCellValue(String, int, int) - start"); //$NON-NLS-1$
		}

		Cell oCell = null;
		String sValue = null;
		try {
			oCell = getCell(wb, sheetName, iRow, iCol);

			if (oCell == null) {
				sValue = "";
			} else {
				if(Cell.CELL_TYPE_NUMERIC == oCell.getCellType()) {
					sValue = String.valueOf(oCell.getNumericCellValue());
				} else if(Cell.CELL_TYPE_STRING == oCell.getCellType()) {
					sValue = oCell.getStringCellValue();
				} else if(Cell.CELL_TYPE_BLANK == oCell.getCellType()) {
					sValue = "";
				} else if(Cell.CELL_TYPE_BOOLEAN == oCell.getCellType()) {
					sValue = String.valueOf(oCell.getBooleanCellValue());
				} else if(Cell.CELL_TYPE_ERROR == oCell.getCellType()) {
					sValue = String.valueOf(oCell.getErrorCellValue());
				} else if(Cell.CELL_TYPE_FORMULA == oCell.getCellType()) {
					sValue = oCell.getCellFormula();
				} else {
					sValue = String.valueOf(oCell.getDateCellValue());
				}
			}
		} catch (java.lang.IllegalStateException e) {
			LOG.error("getCellValue(String, int, int)", e); //$NON-NLS-1$

			sValue = String.valueOf((int) oCell.getNumericCellValue());
		} catch (java.lang.Exception e) {
			LOG.error("getCellValue(String, int, int)", e); //$NON-NLS-1$
			sValue = "";
		}
		if (sValue != null) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("getCellValue(String, int, int) - end"); //$NON-NLS-1$
			}
			return sValue;
		} else {
			if (LOG.isDebugEnabled()) {
				LOG.debug("getCellValue(String, int, int) - end"); //$NON-NLS-1$
			}
			return "-EOF-";
		}
	}

	/**
	 * This method is used to get the cell value for numeric format.
	 *
	 * @param sheetName the sheet name
	 * @param iRow the row no
	 * @param iCol the column no
	 * @return the double
	 */

	public double getCellNumericValue(String sheetName, int iRow, int iCol) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getCellNumericValue(String, int, int) - start"); //$NON-NLS-1$
		}

		Cell oCell;
		double sValue = 0;
		try {
			oCell = getCell(wb, sheetName, iRow, iCol);
			if (oCell == null) {
				sValue = 0;
			} else {
				sValue = oCell.getNumericCellValue();
			}
		} catch (NullPointerException e) {
			LOG.error("getCellNumericValue(String, int, int)", e); //$NON-NLS-1$

			sValue = 0;
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("getCellNumericValue(String, int, int) - end"); //$NON-NLS-1$
		}
		return sValue;
	}

	/**
	 * This method is used to get the total row count in the sheet.
	 *
	 * @param sheetName the sheet name
	 * @return the int
	 */

	public int getTotalRowCount(String sheetName) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTotalRowCount(String) - start"); //$NON-NLS-1$
		}

		Sheet oSheet;
		oSheet = wb.getSheet(sheetName);
		int returnint = oSheet.getLastRowNum();
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTotalRowCount(String) - end"); //$NON-NLS-1$
		}
		return returnint;
	}

	/**
	 * This method is used to get the total column count in the sheet.
	 *
	 * @param sheetName the sheet name
	 * @return the int
	 */
	public int getTotalColumnCount(String sheetName) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTotalColumnCount(String) - start"); //$NON-NLS-1$
		}

		int columncount = 0;
		Sheet oSheet;
		oSheet = wb.getSheet(sheetName);
		for (int colcnt = 0; colcnt <= oSheet.getLastRowNum(); colcnt++) {
			columncount = columncount + 1;
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("getTotalColumnCount(String) - end"); //$NON-NLS-1$
		}
		return columncount;
	}

	/**
	 * Gets the number of sheets.
	 * 
	 * @return the number of sheets
	 */
	public int getNumberOfSheets() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getNumberOfSheets() - start"); //$NON-NLS-1$
		}

		int returnint = wb.getNumberOfSheets();
		if (LOG.isDebugEnabled()) {
			LOG.debug("getNumberOfSheets() - end"); //$NON-NLS-1$
		}
		return returnint;
	}

	/**
	 * isSheetExists.
	 *
	 * @param sheetName the sheet name
	 * @return true, if is sheet exists
	 * @returns whether sheet exists
	 */
	public boolean isSheetExists(String sheetName) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("isSheetExists(String) - start"); //$NON-NLS-1$
		}

		Sheet oSheet = null;
		if (sheetName != null) {

			oSheet = wb.getSheet(sheetName);
		}
		if (oSheet != null) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("isSheetExists(String) - end"); //$NON-NLS-1$
			}
			return true;
		} else {
			if (LOG.isDebugEnabled()) {
				LOG.debug("isSheetExists(String) - end"); //$NON-NLS-1$
			}
			return false;
		}
	}

	/**
	 * Gets the sheet names.
	 * 
	 * @param index
	 *            the index
	 * @return the sheet names
	 */
	public String getSheetNames(int index) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSheetNames(int) - start"); //$NON-NLS-1$
		}

		String sheetName = null;
		sheetName = wb.getSheetName(index);

		if (LOG.isDebugEnabled()) {
			LOG.debug("getSheetNames(int) - end"); //$NON-NLS-1$
		}
		return sheetName;
	}

	/**
	 * Gets the sheet count.
	 *
	 * @return the sheet count
	 */
	public int getSheetCount() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getSheetCount() - start"); //$NON-NLS-1$
		}

		int sheetCount = 0;
		sheetCount = wb.getNumberOfSheets();

		if (LOG.isDebugEnabled()) {
			LOG.debug("getSheetCount() - end"); //$NON-NLS-1$
		}
		return sheetCount;

	}

	/**
	 * This method is used to get the cell value in the sheet.
	 *
	 * @param wb the workbook excel
	 * @param sheetName the sheet name
	 * @param iRow the row no
	 * @param iCol the column no
	 * @return the cell
	 */

	public Cell getCell(Workbook wb, String sheetName, int iRow, int iCol) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("getCell(Workbook, String, int, int) - start"); //$NON-NLS-1$
		}

		Sheet oSheet;
		Row oRow;
		Cell oCell;

		oSheet = wb.getSheet(sheetName);
		oRow = oSheet.getRow(iRow);
		oCell = oRow.getCell(iCol);

		if (LOG.isDebugEnabled()) {
			LOG.debug("getCell(Workbook, String, int, int) - end"); //$NON-NLS-1$
		}
		return oCell;
	}

	/**
	 * Write to excel.
	 *
	 * @param fileName the file name
	 * @param sheetNum the sheet num
	 * @param irow the irow
	 * @param icol the icol
	 * @param value the value
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void writeToExcel(String fileName, String sheetNum, int irow, int icol, String value) throws IOException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("writeToExcel(String, String, int, int, String) - start"); //$NON-NLS-1$
		}

		Sheet sheet = wb.getSheet(sheetNum);
		Row row = sheet.getRow(irow);
		Cell cell = row.getCell(icol);

		HSSFCellStyle style = (HSSFCellStyle) wb.createCellStyle();
		if (cell == null) {
			cell = row.createCell(icol);
		}
		HSSFFont font = (HSSFFont) wb.createFont();
		font.setFontHeightInPoints((short) 13);
		font.setFontName("Arial");
		if ("FAIL".equalsIgnoreCase(value)) {
			font.setColor(HSSFColor.RED.index);
		} else if ("PASS".equalsIgnoreCase(value)) {
			font.setColor(HSSFColor.GREEN.index);
		}
		style.setFont(font);
		cell.setCellStyle(style);
		cell.setCellValue(value);
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(fileName);
			wb.write(fout);

		} finally {
			fout.close();
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("writeToExcel(String, String, int, int, String) - end"); //$NON-NLS-1$
		}
	}
}
