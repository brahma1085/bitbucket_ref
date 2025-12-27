
package com.scb.fc.forms;
import org.apache.struts.action.ActionForm;

public class EmpDetailsFB extends ActionForm{
	private String pageId;
	private String validations,flag,acType,acNo,empNature,selfEmpAddress,selfPhno,serviceLen,monthlyIncome,monthlyExpenditure,netMonthlyIncome;
	private String srvNetMonthlyIncome,srvMonthlyExpenditure,srvMonthlyIncome,serviceLength,salCertEnc,serviceTrf,presentService,empDept,empDesig,empNo,servicePhno,empAddress,empName;
	private String rentIncome,rentAmount,rentTax,landAddr;
	private String pensionerName,pensionerAddr,pensionerPhno,pensionAmount,pensionerBankName,pensionerAcType,pensionerAcNo,pensionerMthIncome;
	private String businessName,empType,businessAddr,businessNature,stockValue,goodsType,goodsCondition,turnover,businessPhno,busMonthlyIncome,busMonthlyExpenditure,busNetMonthlyIncome;
	
    public String getEmpType() {
		return empType;
	}
	public void setEmpType(String empType) {
		this.empType = empType;
	}
	public String getPensionerName() {
		return pensionerName;
	}
	public void setPensionerName(String pensionerName) {
		this.pensionerName = pensionerName;
	}
	public String getPensionerAddr() {
		return pensionerAddr;
	}
	public void setPensionerAddr(String pensionerAddr) {
		this.pensionerAddr = pensionerAddr;
	}
	public String getPensionerPhno() {
		return pensionerPhno;
	}
	public void setPensionerPhno(String pensionerPhno) {
		this.pensionerPhno = pensionerPhno;
	}
	public String getPensionAmount() {
		return pensionAmount;
	}
	public void setPensionAmount(String pensionAmount) {
		this.pensionAmount = pensionAmount;
	}
	public String getPensionerBankName() {
		return pensionerBankName;
	}
	public void setPensionerBankName(String pensionerBankName) {
		this.pensionerBankName = pensionerBankName;
	}
	public String getPensionerAcType() {
		return pensionerAcType;
	}
	public void setPensionerAcType(String pensionerAcType) {
		this.pensionerAcType = pensionerAcType;
	}
	public String getPensionerAcNo() {
		return pensionerAcNo;
	}
	public void setPensionerAcNo(String pensionerAcNo) {
		this.pensionerAcNo = pensionerAcNo;
	}
	public String getPensionerMthIncome() {
		return pensionerMthIncome;
	}
	public void setPensionerMthIncome(String pensionerMthIncome) {
		this.pensionerMthIncome = pensionerMthIncome;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getBusinessAddr() {
		return businessAddr;
	}
	public void setBusinessAddr(String businessAddr) {
		this.businessAddr = businessAddr;
	}
	public String getBusinessNature() {
		return businessNature;
	}
	public void setBusinessNature(String businessNature) {
		this.businessNature = businessNature;
	}
	public String getStockValue() {
		return stockValue;
	}
	public void setStockValue(String stockValue) {
		this.stockValue = stockValue;
	}
	public String getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	public String getGoodsCondition() {
		return goodsCondition;
	}
	public void setGoodsCondition(String goodsCondition) {
		this.goodsCondition = goodsCondition;
	}
	public String getTurnover() {
		return turnover;
	}
	public void setTurnover(String turnover) {
		this.turnover = turnover;
	}
	public String getBusinessPhno() {
		return businessPhno;
	}
	public void setBusinessPhno(String businessPhno) {
		this.businessPhno = businessPhno;
	}
	public String getBusMonthlyIncome() {
		return busMonthlyIncome;
	}
	public void setBusMonthlyIncome(String busMonthlyIncome) {
		this.busMonthlyIncome = busMonthlyIncome;
	}
	public String getBusMonthlyExpenditure() {
		return busMonthlyExpenditure;
	}
	public void setBusMonthlyExpenditure(String busMonthlyExpenditure) {
		this.busMonthlyExpenditure = busMonthlyExpenditure;
	}
	public String getBusNetMonthlyIncome() {
		return busNetMonthlyIncome;
	}
	public void setBusNetMonthlyIncome(String busNetMonthlyIncome) {
		this.busNetMonthlyIncome = busNetMonthlyIncome;
	}
	public String getRentIncome() {
		return rentIncome;
	}
	public void setRentIncome(String rentIncome) {
		this.rentIncome = rentIncome;
	}
	public String getRentAmount() {
		return rentAmount;
	}
	public void setRentAmount(String rentAmount) {
		this.rentAmount = rentAmount;
	}
	public String getRentTax() {
		return rentTax;
	}
	public void setRentTax(String rentTax) {
		this.rentTax = rentTax;
	}
	public String getLandAddr() {
		return landAddr;
	}
	public void setLandAddr(String landAddr) {
		this.landAddr = landAddr;
	}
	
	public String getSrvNetMonthlyIncome() {
		return srvNetMonthlyIncome;
	}
	public void setSrvNetMonthlyIncome(String srvNetMonthlyIncome) {
		this.srvNetMonthlyIncome = srvNetMonthlyIncome;
	}
	public String getSrvMonthlyExpenditure() {
		return srvMonthlyExpenditure;
	}
	public void setSrvMonthlyExpenditure(String srvMonthlyExpenditure) {
		this.srvMonthlyExpenditure = srvMonthlyExpenditure;
	}
	public String getSrvMonthlyIncome() {
		return srvMonthlyIncome;
	}
	public void setSrvMonthlyIncome(String srvMonthlyIncome) {
		this.srvMonthlyIncome = srvMonthlyIncome;
	}
	public String getServiceLength() {
		return serviceLength;
	}
	public void setServiceLength(String serviceLength) {
		this.serviceLength = serviceLength;
	}
	public String getSalCertEnc() {
		return salCertEnc;
	}
	public void setSalCertEnc(String salCertEnc) {
		this.salCertEnc = salCertEnc;
	}
	public String getServiceTrf() {
		return serviceTrf;
	}
	public void setServiceTrf(String serviceTrf) {
		this.serviceTrf = serviceTrf;
	}
	public String getPresentService() {
		return presentService;
	}
	public void setPresentService(String presentService) {
		this.presentService = presentService;
	}
	public String getEmpDept() {
		return empDept;
	}
	public void setEmpDept(String empDept) {
		this.empDept = empDept;
	}
	public String getEmpDesig() {
		return empDesig;
	}
	public void setEmpDesig(String empDesig) {
		this.empDesig = empDesig;
	}
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public String getServicePhno() {
		return servicePhno;
	}
	public void setServicePhno(String servicePhno) {
		this.servicePhno = servicePhno;
	}
	public String getEmpAddress() {
		return empAddress;
	}
	public void setEmpAddress(String empAddress) {
		this.empAddress = empAddress;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getValidations() {
		return validations;
	}
	public void setValidations(String validations) {
		this.validations = validations;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getAcType() {
		return acType;
	}
	public void setAcType(String acType) {
		this.acType = acType;
	}
	public String getAcNo() {
		return acNo;
	}
	public void setAcNo(String acNo) {
		this.acNo = acNo;
	}
	public String getEmpNature() {
		return empNature;
	}
	public void setEmpNature(String empNature) {
		this.empNature = empNature;
	}
	public String getSelfEmpAddress() {
		return selfEmpAddress;
	}
	public void setSelfEmpAddress(String selfEmpAddress) {
		this.selfEmpAddress = selfEmpAddress;
	}
	public String getSelfPhno() {
		return selfPhno;
	}
	public void setSelfPhno(String selfPhno) {
		this.selfPhno = selfPhno;
	}
	public String getServiceLen() {
		return serviceLen;
	}
	public void setServiceLen(String serviceLen) {
		this.serviceLen = serviceLen;
	}
	public String getMonthlyIncome() {
		return monthlyIncome;
	}
	public void setMonthlyIncome(String monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}
	public String getMonthlyExpenditure() {
		return monthlyExpenditure;
	}
	public void setMonthlyExpenditure(String monthlyExpenditure) {
		this.monthlyExpenditure = monthlyExpenditure;
	}
	public String getNetMonthlyIncome() {
		return netMonthlyIncome;
	}
	public void setNetMonthlyIncome(String netMonthlyIncome) {
		this.netMonthlyIncome = netMonthlyIncome;
	}
	
	
	
	
	
	
}
