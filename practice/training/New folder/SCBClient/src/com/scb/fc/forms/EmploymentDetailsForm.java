package com.scb.fc.forms;

import java.io.Serializable;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;

    /**
     * Created by IntelliJ IDEA.
     * User: Mohsin
     * Date: Dec 17, 2008
     * Time: 10:50:35 AM
     * To change this template use File | Settings | File Templates.
     */
    public class EmploymentDetailsForm extends ActionForm implements Serializable {
       String pageId,defaultSignIndex;
       String businessAddr,empName, empAddr, phNo, empNo, empDesign, empDept, empService, srvTrf, servicelength, salCertificate, income, expend, empIncome;
       
       
      

	

		

	public String getServicelength() {
		return servicelength;
	}

	public void setServicelength(String servicelength) {
		this.servicelength = servicelength;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpAddr() {
		return empAddr;
	}

	public void setEmpAddr(String empAddr) {
		this.empAddr = empAddr;
	}

	public String getPhNo() {
		return phNo;
	}

	public void setPhNo(String phNo) {
		this.phNo = phNo;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getEmpDesign() {
		return empDesign;
	}

	public void setEmpDesign(String empDesign) {
		this.empDesign = empDesign;
	}

	public String getEmpDept() {
		return empDept;
	}

	public void setEmpDept(String empDept) {
		this.empDept = empDept;
	}

	public String getEmpService() {
		return empService;
	}

	public void setEmpService(String empService) {
		this.empService = empService;
	}

	public String getSrvTrf() {
		return srvTrf;
	}

	public void setSrvTrf(String srvTrf) {
		this.srvTrf = srvTrf;
	}

	public String getSalCertificate() {
		return salCertificate;
	}

	public void setSalCertificate(String salCertificate) {
		this.salCertificate = salCertificate;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public String getExpend() {
		return expend;
	}

	public void setExpend(String expend) {
		this.expend = expend;
	}

	public String getEmpIncome() {
		return empIncome;
	}

	public void setEmpIncome(String empIncome) {
		this.empIncome = empIncome;
	}

	public String getDefaultSignIndex() {
		return defaultSignIndex;
	}

	public void setDefaultSignIndex(String defaultSignIndex) {
		System.out.println("defaultSignIndex=="+defaultSignIndex);
		this.defaultSignIndex = defaultSignIndex;
	}

	public String getPageId() {
		System.out.println("At 46 inside AccountCloseActionForm"+pageId);
		return pageId;
	}

	public void setPageId(String pageId) {
		System.out.println("At 51 setting pageId"+pageId);
		this.pageId = pageId;
	}

	public String getBusinessAddr() {
		return businessAddr;
	}

	public void setBusinessAddr(String businessAddr) {
		this.businessAddr = businessAddr;
	}

	
}
