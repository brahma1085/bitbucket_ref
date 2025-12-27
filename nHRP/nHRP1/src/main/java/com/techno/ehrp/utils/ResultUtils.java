package com.techno.ehrp.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrTokenizer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.techno.ehrp.exception.HPException;

/**
 * The Class ResultUtils.
 * 
 * @author brahma
 */
public class ResultUtils {

	/**
	 * Instantiates a new result utils.
	 */
	private ResultUtils() {
	}

	/**
	 * Gets the result.
	 *
	 * @param input
	 *            the input
	 * @return the result
	 * @throws Exception
	 *             the exception
	 */
	public static String getResult(String input) throws HPException {
		StringBuilder builder = new StringBuilder();
		StringBuilder initBuild = new StringBuilder();
		StrTokenizer tokenizer = StrTokenizer.getCSVInstance(input);
		String[] names = tokenizer.getTokenArray();
		for (int i = 0; i < names.length; i++) {
			String name = names[i];
			if (StringUtils.isBlank(name)) {
				throw new HPException("The name '" + name + "' at position '" + i + 1 + "' should be valid. It can not be empty.");
			}
			initBuild.append(name.charAt(0));
			int row = i + 1;
			row = Utils.getDigitSum(row);
			builder.append(getProbability(name, row, null, 0));
			builder.append("\n");
		}
		String initials = initBuild.toString();
		builder.append("All Initial Characters and sum : ").append(initials).append(" - ");
		initials = Utils.capitalize(initials);
		int value = Utils.getSum(initials);
		value = Utils.getDigitSum(value);
		builder.append(value);
		return writeToFile(builder.toString());
	}

	/**
	 * Gets the xls result.
	 *
	 * @param input
	 *            the input
	 * @return the xls result
	 * @throws Exception
	 *             the exception
	 */
	public static String getXlsResult(String input) throws HPException {
		StringBuilder builder = new StringBuilder();
		StringBuilder initBuild = new StringBuilder();
		StrTokenizer tokenizer = StrTokenizer.getCSVInstance(input);
		String[] names = tokenizer.getTokenArray();
		Object[][] results = new Object[names.length + 1][8];
		results[0][0] = Utils.getProperty(Constants.S_NO);
		results[0][1] = Utils.getProperty(Constants.HORSE_NAME);
		results[0][2] = Utils.getProperty(Constants.HORSE_NAME_TOTAL);
		results[0][3] = Utils.getProperty(Constants.OTHERS);
		results[0][4] = Utils.getProperty(Constants.PROBS_1);
		results[0][5] = Utils.getProperty(Constants.PROBS_2);
		results[0][6] = Utils.getProperty(Constants.INIT_CHARS);
		results[0][7] = Utils.getProperty(Constants.INIT_CHARS_TOTAL);
		int temp = 0;
		for (int i = 0; i < names.length; i++) {
			results[(i + 1)][0] = Integer.valueOf(i + 1);
			String name = names[i];
			if (StringUtils.isBlank(name)) {
				throw new HPException("The name '" + name + "' at position '" + i + 1 + "' should be valid. It can not be empty.");
			}
			initBuild.append(name.charAt(0));
			int row = i + 1;
			row = Utils.getDigitSum(row);
			builder.append(getProbability(name, row, results, i + 1));
			builder.append("\n");
			temp = i + 1;
		}
		String initials = initBuild.toString();
		builder.append("All Initial Characters and sum : ").append(initials).append(" - ");
		results[temp][6] = initials;
		initials = Utils.capitalize(initials);
		int value = Utils.getSum(initials);
		value = Utils.getDigitSum(value);
		builder.append(value);
		results[temp][7] = Integer.valueOf(value);
		return generateExcel(results);
	}

	/**
	 * Generate excel.
	 *
	 * @param results
	 *            the results
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	private static String generateExcel(Object[][] results) throws HPException {
		File newFile = new File(System.getProperty("user.home") + "\\nHRP_" + Utils.getTime() + ".xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(Constants.HORSES_PROBABILITY);
		int rowNum = 0;
		Object[][] arrayOfObject = results;
		int j = results.length;
		for (int i = 0; i < j; i++) {
			Object[] datatype = arrayOfObject[i];
			Row row = sheet.createRow(rowNum++);
			int colNum = 0;
			CellStyle style = workbook.createCellStyle();
			XSSFFont font = workbook.createFont();
			font.setBold(true);
			font.setColor(IndexedColors.DARK_BLUE.getIndex());
			style.setFont(font);
			for (Object field : datatype) {
				int tempCol = colNum++;
				Cell cell = row.createCell(tempCol);
				if ((field instanceof String)) {
					cell.setCellValue(new XSSFRichTextString((String) field));
					style.setWrapText(true);
					cell.setCellStyle(style);
					if (row.getRowNum() == 1) {
						style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
						cell.setCellStyle(style);
					}
				} else if ((field instanceof Integer)) {
					cell.setCellValue(((Integer) field).intValue());
					cell.setCellStyle(style);
					if (row.getRowNum() == 1) {
						style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
						cell.setCellStyle(style);
					}
				}
				sheet.autoSizeColumn(tempCol);
			}
		}
		try {
			FileOutputStream outputStream = new FileOutputStream(newFile);
			workbook.write(outputStream);
			workbook.close();
		} catch (Exception e) {
			throw new HPException("Not able to create the result file due to permission denied");
		}
		return newFile.getAbsolutePath();
	}

	/**
	 * Gets the probability.
	 *
	 * @param name
	 *            the name
	 * @param row
	 *            the row
	 * @param results
	 *            the results
	 * @param rowNum
	 *            the row num
	 * @return the probability
	 * @throws Exception
	 *             the exception
	 */
	private static String getProbability(String name, int row, Object[][] results, int rowNum) throws HPException {
		StringBuilder builder = new StringBuilder();
		if (StringUtils.isAlphaSpace(name.trim())) {
			int col = Utils.getTotal(name);
			String data = Utils.getData(row, col);
			if (results != null) {
				results[rowNum][1] = name;
				results[rowNum][2] = Integer.valueOf(col);
			}
			builder.append("name with character sum : " + name + " - " + col).append("\n").append(Utils.print(data, results, rowNum, col));
			return builder.toString();
		}
		throw new HPException("Please enter a valid name. The name '" + name + "' at position '" + row + "' is not valid.");
	}

	/**
	 * Write to file.
	 *
	 * @param data
	 *            the data
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	private static String writeToFile(String data) throws HPException {
		FileWriter writer = null;
		File newFile = null;
		try {
			String root = System.getProperty("user.home");
			newFile = new File(root + "\\nHRP_" + Utils.getTime() + ".doc");
			writer = new FileWriter(newFile);
			writer.write(data);
			writer.flush();
			return newFile.getAbsolutePath();
		} catch (IOException e) {
			throw new HPException(e.getMessage() + ": Error occured while writing into the file");
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}

}