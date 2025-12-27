package com.sssoft.isatt.ui1.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sssoft.isatt.data.exception.ServiceException;
import com.sssoft.isatt.data.pojo.Application;
import com.sssoft.isatt.data.pojo.ExcelImport;
import com.sssoft.isatt.data.service.MainService;
import com.sssoft.isatt.ui.exception.ControllerException;

/**
 * The Class ImportExcelController.
 */
@Controller
public class ImportExcelController {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(ImportExcelController.class);

	/** The main service. */
	private MainService mainService;

	/**
	 * Sets the main service.
	 * 
	 * @param mainService
	 *            the new main service
	 */
	public void setMainService(MainService mainService) {
		this.mainService = mainService;
	}

	/** The new file. */
	File newFile = null;

	/**
	 * Upload excel util.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 * @throws ServiceException
	 *             the service exception
	 */
	@RequestMapping(value = "/uploadExcelUtil", method = RequestMethod.POST)
	public @ResponseBody
	String uploadExcelUtil(MultipartHttpServletRequest request, HttpServletResponse response) throws ServiceException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("uploadExcelUtil(MultipartHttpServletRequest, HttpServletResponse) - start"); //$NON-NLS-1$
		}
		InputStream inputStream = null;
		OutputStream outputStream = null;
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf = request.getFile(itr.next());
		String fileName = mpf.getOriginalFilename();
		newFile = null;
		try {
			inputStream = mpf.getInputStream();
			newFile = new File(fileName);
			if (!newFile.exists()) {
				newFile.createNewFile();
			}
			outputStream = new FileOutputStream(newFile);
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		} catch (Exception e) {
			LOG.warn("uploadExcelUtil(MultipartHttpServletRequest, HttpServletResponse) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("uploadExcelUtil(MultipartHttpServletRequest, HttpServletResponse) - end"); //$NON-NLS-1$
		}
		return fileName;
	}

	/**
	 * Upload excel sheet data.
	 * 
	 * @param importData
	 *            the import data
	 * @return the string
	 */
	@RequestMapping(value = "/uploadExcelSheetData", method = RequestMethod.POST)
	public @ResponseBody
	String uploadExcelSheetData(@RequestBody Map<String, String> importData) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("uploadExcelSheetData(Map<String,String>) - start"); //$NON-NLS-1$
		}
		ExcelImport excelImport = new ExcelImport();
		JSONObject jsonObj = new JSONObject(importData);
		try {
			if (newFile != null) {
				excelImport.setApplicationName((String) jsonObj.get("applicationName"));
				excelImport.setTestPlanName((String) jsonObj.get("testPlanName"));
				excelImport.setTestSuiteName((String) jsonObj.getString("testSuiteName"));
				excelImport.setTestScenarioName((String) jsonObj.get("testScenarioName"));
				excelImport.setFunctionName((String) jsonObj.get("functionName"));
				excelImport.setFeatureName((String) jsonObj.get("featureName"));
				excelImport.setDataSetDescription((String) jsonObj.get("testPlanName") + "_dataset");
				excelImport.setExcelFile(newFile);
				mainService.insertReadPlanData(excelImport);
			}
			newFile = null;
		} catch (Exception e) {
			LOG.warn("uploadExcelSheetData(Map<String,String>) - exception ignored", e); //$NON-NLS-1$
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("uploadExcelSheetData(Map<String,String>) - end"); //$NON-NLS-1$
		}
		return "Succesfully persisted";
	}

	/**
	 * Import excel sheet data.
	 * 
	 * @param importData
	 *            the import data
	 * @return the string
	 * @throws ControllerException
	 *             the controller exception
	 */
	@RequestMapping(value = "/importExcelSheetData", method = RequestMethod.POST)
	public @ResponseBody
	String importExcelSheetData(@RequestBody Map<String, String> importData) throws ControllerException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("importExcelSheetData(Map<String,String>) - start"); //$NON-NLS-1$
		}
		ExcelImport excelImport = new ExcelImport();
		JSONObject jsonObj = new JSONObject(importData);
		try {
			if (newFile != null) {
				int id = (Integer) jsonObj.get("appID");
				Application app = mainService.getApplicationById(id);
				if (app != null) {
					excelImport.setApplicationName(app.getAppName());
					excelImport.setTestPlanName((String) jsonObj.get("testPlanName"));
					excelImport.setTestSuiteName((String) jsonObj.getString("testSuiteName"));
					excelImport.setTestScenarioName((String) jsonObj.get("testScenarioName"));
					excelImport.setFunctionName((String) jsonObj.get("functionName"));
					excelImport.setFeatureName((String) jsonObj.get("featureName"));
					excelImport.setDataSetDescription((String) jsonObj.get("testPlanName") + "_dataset");
					excelImport.setExcelFile(newFile);
					mainService.insertReadPlanData(excelImport);
				}
			}
			newFile = null;
			if (LOG.isDebugEnabled()) {
				LOG.debug("importExcelSheetData(Map<String,String>) - end"); //$NON-NLS-1$
			}
			return "Successfully imported";
		} catch (Exception e) {
			LOG.error("importExcelSheetData(Map<String,String>)", e); //$NON-NLS-1$
			String returnString = e.getMessage();
			if (LOG.isDebugEnabled()) {
				LOG.debug("importExcelSheetData(Map<String,String>) - end"); //$NON-NLS-1$
			}
			return returnString;
		}
	}

}