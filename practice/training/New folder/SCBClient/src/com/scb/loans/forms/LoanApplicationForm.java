package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

import com.scb.common.forms.PageIdForm;

public class LoanApplicationForm extends ActionForm {
	private static final long serialVersionUID = 4637703352587854690L;

	private String loantype, shtype, details, pageid, tabPaneHeading,
			defaultTab, coshtype;
	private int shno, lnaccno, coshno, purpose, cid, intrate, loan_acc_no,validate,
	acNum;
	public int getValidate() {
		return validate;
	}


	public void setValidate(int validate) {
		this.validate = validate;
	}

	private String button_value, tyop, validateShno, detailStatus, main_submit,clear;
	ApplicationForm applicationform = new ApplicationForm();
	// ServiceActionForm serviceActionForm = new ServiceActionForm();
	SelfEmployedForm selfEmployedForm = new SelfEmployedForm();
	BusinessActionForm businessActionForm = new BusinessActionForm();
	RentActionFrom rentActionFrom = new RentActionFrom();
	PensionActionForm pensionActionForm = new PensionActionForm();

	// SignatureInstruction
	private String defaultSignIndex, signIndex, defSIndex, sign, name, acType;

	// Application Form
	private int appln_no, interesttype, interestcalctype, payaccno;
	private String appndate, dirrelations, relativetodir, submit, buttonvalue,
			paymtmode, dirdetails, payactype;
	private String sysdate;


	
	private double reqamount;

	// Vehicle Form
	private String maketype, dealername, licenseno, vehicletype, vehiclefor,
			vehiclearea, vehicleparked, othervehicle, validity, permitno,
			permitvalid;
	private double cost;
	
	//-----01/01/2011-----
	//Gold Details Form
	int slno,grWeight,netWeight;
	double netRate,rate;
	String desc,appCode;
	
	//-----02/10/2011-----
	String shareno,surityname,brname;
    double covalue;
    int brcode;
    
    String coforward,cosharetype,coshareno;
    
    public String getSysdate() {
		return sysdate;
	}


	public void setSysdate(String sysdate) {
		this.sysdate = sysdate;
	}

	public String getShareno() {
		return shareno;
	}

	public void setShareno(String shareno) {
		this.shareno = shareno;
	}

	public String getSurityname() {
		return surityname;
	}

	public void setSurityname(String surityname) {
		this.surityname = surityname;
	}

	public String getBrname() {
		return brname;
	}

	public void setBrname(String brname) {
		this.brname = brname;
	}

	public double getCovalue() {
		return covalue;
	}

	public void setCovalue(double covalue) {
		this.covalue = covalue;
	}

	public int getBrcode() {
		return brcode;
	}

	public void setBrcode(int brcode) {
		this.brcode = brcode;
	}

	public String getCoforward() {
		return coforward;
	}

	public void setCoforward(String coforward) {
		this.coforward = coforward;
	}

	public String getCosharetype() {
		return cosharetype;
	}

	public void setCosharetype(String cosharetype) {
		this.cosharetype = cosharetype;
	}

	public String getCoshareno() {
		return coshareno;
	}

	public void setCoshareno(String coshareno) {
		this.coshareno = coshareno;
	}

	public int getSlno() {
		return slno;
	}

	public void setSlno(int slno) {
		this.slno = slno;
	}

	public int getGrWeight() {
		return grWeight;
	}

	public void setGrWeight(int grWeight) {
		this.grWeight = grWeight;
	}

	public int getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(int netWeight) {
		this.netWeight = netWeight;
	}

	public void setNetRate(double netRate) {
		this.netRate = netRate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getNetRate() {
		return netRate;
	}

	public void setNetRate(Double netRate) {
		this.netRate = netRate;
	}

	// Employment Form
	String comb_emp_visible;

	//Employment Form Buttons
	private String emp_submit;
	
	/*public String getSel_submit() {
		return sel_submit;
	}

	public void setSel_submit(String selSubmit) {
		sel_submit = selSubmit;
	}

	public String getServ_submit() {
		return serv_submit;
	}

	public void setServ_submit(String servSubmit) {
		serv_submit = servSubmit;
	}

	public String getBus_submit() {
		return bus_submit;
	}

	public void setBus_submit(String busSubmit) {
		bus_submit = busSubmit;
	}

	public String getRent_submit() {
		return rent_submit;
	}

	public void setRent_submit(String rentSubmit) {
		rent_submit = rentSubmit;
	}

	public String getPen_submit() {
		return pen_submit;
	}

	public void setPen_submit(String penSubmit) {
		pen_submit = penSubmit;
	}*/
	// Self-Employed Form
	/*
	 * private String employmtnature,address,emp_nature; int phno,serv_length;
	 * double income,expenditure,netincome;
	 */

	// SelfEmployedForm

	

	public String getEmp_submit() {
		return emp_submit;
	}

	public void setEmp_submit(String empSubmit) {
		emp_submit = empSubmit;
	}

	private String empNature, phoneNum;
	private double netIncome;
	private int loService;

	// ServiceForm
	/*
	 * String employername,designation,department; int phoneno; String empno;
	 * boolean confirmation,transferable,certicateenclosed;
	 */
	String employername, address, designation, department, employmtnature,
			emp_nature;
	double income, expenditure, netincome;
	int phoneno, empno, serv_length, phno;
	boolean confirmation, transferable, certicateenclosed;

	// BusinessForm
	private String concernname, businessnature;
	private double surplus, avg_turnover;

	// PensionForm

	String empname, bankname, acctype;
	int accno;
	double pensionamt;

	// Rent Form
	private String land_addr;
	private double totamt, tax_payment;

	// PropertyForm
	private String propertyno, addr, ewbyfeets, nsbyfeets, eastby, westby,
			northby, southby, propertyacquiredby, nature;
	private double value;
	private Object tenant[][];

	// Relation From
	String relName, relDob, relTor, relTos, relTom, relTostatus, indName,
			infDob, indTos, indTom, indTostatus, depName, depDob, depTos,
			depTor;
	String forward;
	// Getters and setters methods for PageIdentity
	private PageIdForm pageidentity = new PageIdForm();



	public String getSubmit() {
		return submit;
	}

	public void setSubmit(String submit) {
		this.submit = submit;
	}

	public String getButtonvalue() {
		return buttonvalue;
	}

	public void setButtonvalue(String buttonvalue) {
		this.buttonvalue = buttonvalue;
	}

	
	public String getClear() {
		return clear;
	}

	public void setClear(String clear) {
		this.clear = clear;
	}


	public String getLoantype() {
		return loantype;
	}

	public void setLoantype(String loantype) {
		this.loantype = loantype;
	}

	public String getShtype() {
		return shtype;
	}

	public void setShtype(String shtype) {
		this.shtype = shtype;
	}

	public int getPurpose() {
		return purpose;
	}

	public void setPurpose(int purpose) {
		this.purpose = purpose;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public int getShno() {
		return shno;
	}

	public void setShno(int shno) {
		this.shno = shno;
	}

	public int getLnaccno() {
		return lnaccno;
	}

	public void setLnaccno(int lnaccno) {
		this.lnaccno = lnaccno;
	}

	public String getPageid() {
		return pageid;
	}

	public void setPageid(String pageid) {
		this.pageid = pageid;
	}

	public PageIdForm getPageidentity() {
		return pageidentity;
	}

	public void setPageidentity(PageIdForm pageidentity) {
		this.pageidentity = pageidentity;
	}

	public String getTabPaneHeading() {
		return tabPaneHeading;
	}

	public void setTabPaneHeading(String tabPaneHeading) {
		this.tabPaneHeading = tabPaneHeading;
	}

	public String getDefaultTab() {
		return defaultTab;
	}

	public void setDefaultTab(String defaultTab) {
		this.defaultTab = defaultTab;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public int getCoshno() {
		return coshno;
	}

	public void setCoshno(int coshno) {
		this.coshno = coshno;
	}

	public String getCoshtype() {
		return coshtype;
	}

	public void setCoshtype(String coshtype) {
		this.coshtype = coshtype;
	}

	public int getAppln_no() {
		return appln_no;
	}

	public void setAppln_no(int appln_no) {
		this.appln_no = appln_no;
	}

	public int getInteresttype() {
		return interesttype;
	}

	public void setInteresttype(int interesttype) {
		this.interesttype = interesttype;
	}

	public int getInterestcalctype() {
		return interestcalctype;
	}

	public void setInterestcalctype(int interestcalctype) {
		this.interestcalctype = interestcalctype;
	}

	public String getAppndate() {
		return appndate;
	}

	public void setAppndate(String appndate) {
		this.appndate = appndate;
	}

	public String getRelativetodir() {
		return relativetodir;
	}

	public void setRelativetodir(String relativetodir) {
		this.relativetodir = relativetodir;
	}

	public double getReqamount() {
		return reqamount;
	}

	public void setReqamount(double reqamount) {
		this.reqamount = reqamount;
	}

	public String getMaketype() {
		return maketype;
	}

	public void setMaketype(String maketype) {
		this.maketype = maketype;
	}

	public String getDealername() {
		return dealername;
	}

	public void setDealername(String dealername) {
		this.dealername = dealername;
	}

	public String getLicenseno() {
		return licenseno;
	}

	public void setLicenseno(String licenseno) {
		this.licenseno = licenseno;
	}

	public String getVehicletype() {
		return vehicletype;
	}

	public void setVehicletype(String vehicletype) {
		this.vehicletype = vehicletype;
	}

	public String getVehiclefor() {
		return vehiclefor;
	}

	public void setVehiclefor(String vehiclefor) {
		this.vehiclefor = vehiclefor;
	}

	public String getVehiclearea() {
		return vehiclearea;
	}

	public void setVehiclearea(String vehiclearea) {
		this.vehiclearea = vehiclearea;
	}

	public String getVehicleparked() {
		return vehicleparked;
	}

	public void setVehicleparked(String vehicleparked) {
		this.vehicleparked = vehicleparked;
	}

	public String getOthervehicle() {
		return othervehicle;
	}

	public void setOthervehicle(String othervehicle) {
		this.othervehicle = othervehicle;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getComb_emp_visible() {
		return comb_emp_visible;
	}

	public void setComb_emp_visible(String comb_emp_visible) {
		this.comb_emp_visible = comb_emp_visible;
	}

	public String getEmploymtnature() {
		return employmtnature;
	}

	public void setEmploymtnature(String employmtnature) {
		this.employmtnature = employmtnature;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getServ_length() {
		return serv_length;
	}

	public void setServ_length(int serv_length) {
		this.serv_length = serv_length;
	}

	public String getEmp_nature() {
		return emp_nature;
	}

	public void setEmp_nature(String emp_nature) {
		this.emp_nature = emp_nature;
	}

	public int getPhno() {
		return phno;
	}

	public void setPhno(int phno) {
		this.phno = phno;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public double getExpenditure() {
		return expenditure;
	}

	public void setExpenditure(double expenditure) {
		this.expenditure = expenditure;
	}

	public double getNetincome() {
		return netincome;
	}

	public void setNetincome(double netincome) {
		this.netincome = netincome;
	}

	public String getEmployername() {
		System.out.println("employername--->" + employername);
		return employername;
	}

	public void setEmployername(String employername) {
		this.employername = employername;
	}

	public String getDesignation() {
		System.out.println("designation--->" + designation);
		return designation;
	}

	public void setDesignation(String designation) {

		this.designation = designation;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public int getPhoneno() {
		System.out.println("phoneno--->" + phoneno);
		return phoneno;
	}

	public void setPhoneno(int phoneno) {
		this.phoneno = phoneno;
	}

	public int getEmpno() {
		return empno;
	}

	public void setEmpno(int empno) {
		this.empno = empno;
	}

	public boolean isConfirmation() {
		return confirmation;
	}

	public void setConfirmation(boolean confirmation) {
		this.confirmation = confirmation;
	}

	public boolean isTransferable() {
		return transferable;
	}

	public void setTransferable(boolean transferable) {
		this.transferable = transferable;
	}

	public boolean isCerticateenclosed() {
		return certicateenclosed;
	}

	public void setCerticateenclosed(boolean certicateenclosed) {
		this.certicateenclosed = certicateenclosed;
	}

	public String getConcernname() {
		return concernname;
	}

	public void setConcernname(String concernname) {
		this.concernname = concernname;
	}

	public String getBusinessnature() {
		return businessnature;
	}

	public void setBusinessnature(String businessnature) {
		this.businessnature = businessnature;
	}

	public double getSurplus() {
		return surplus;
	}

	public void setSurplus(double surplus) {
		this.surplus = surplus;
	}

	public double getAvg_turnover() {
		return avg_turnover;
	}

	public void setAvg_turnover(double avg_turnover) {
		this.avg_turnover = avg_turnover;
	}

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getAcctype() {
		return acctype;
	}

	public void setAcctype(String acctype) {
		this.acctype = acctype;
	}

	public int getAccno() {
		return accno;
	}

	public void setAccno(int accno) {
		this.accno = accno;
	}

	public double getPensionamt() {
		return pensionamt;
	}

	public void setPensionamt(double pensionamt) {
		this.pensionamt = pensionamt;
	}

	public String getLand_addr() {
		return land_addr;
	}

	public void setLand_addr(String land_addr) {
		this.land_addr = land_addr;
	}

	public double getTotamt() {
		return totamt;
	}

	public void setTotamt(double totamt) {
		this.totamt = totamt;
	}

	public double getTax_payment() {
		return tax_payment;
	}

	public void setTax_payment(double tax_payment) {
		this.tax_payment = tax_payment;
	}

	public String getPropertyno() {
		return propertyno;
	}

	public void setPropertyno(String propertyno) {
		this.propertyno = propertyno;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getEwbyfeets() {
		return ewbyfeets;
	}

	public void setEwbyfeets(String ewbyfeets) {
		this.ewbyfeets = ewbyfeets;
	}

	public String getNsbyfeets() {
		return nsbyfeets;
	}

	public void setNsbyfeets(String nsbyfeets) {
		this.nsbyfeets = nsbyfeets;
	}

	public String getEastby() {
		return eastby;
	}

	public void setEastby(String eastby) {
		this.eastby = eastby;
	}

	public String getWestby() {
		return westby;
	}

	public void setWestby(String westby) {
		this.westby = westby;
	}

	public String getNorthby() {
		return northby;
	}

	public void setNorthby(String northby) {
		this.northby = northby;
	}

	public String getSouthby() {
		return southby;
	}

	public void setSouthby(String southby) {
		this.southby = southby;
	}

	public String getPropertyacquiredby() {
		return propertyacquiredby;
	}

	public void setPropertyacquiredby(String propertyacquiredby) {
		this.propertyacquiredby = propertyacquiredby;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Object[][] getTenant() {
		return tenant;
	}

	public void setTenant(Object[][] tenant) {
		this.tenant = tenant;
	}

	/*
	 * public String getSubmit() { return submit; } public void setSubmit(String
	 * submit) { this.submit = submit; } public String getButtonvalue() { return
	 * buttonvalue; } public void setButtonvalue(String buttonvalue) {
	 * this.buttonvalue = buttonvalue; }
	 */
	public String getDefaultSignIndex() {
		return defaultSignIndex;
	}

	public void setDefaultSignIndex(String defaultSignIndex) {
		this.defaultSignIndex = defaultSignIndex;
	}

	public String getSignIndex() {
		return signIndex;
	}

	public void setSignIndex(String signIndex) {
		this.signIndex = signIndex;
	}

	public String getDefSIndex() {
		return defSIndex;
	}

	public void setDefSIndex(String defSIndex) {
		this.defSIndex = defSIndex;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getIntrate() {
		return intrate;
	}

	public void setIntrate(int intrate) {
		this.intrate = intrate;
	}

	public String getButton_value() {
		return button_value;
	}

	public void setButton_value(String button_value) {
		this.button_value = button_value;
	}

	public ApplicationForm getApplicationform() {
		return applicationform;
	}

	public void setApplicationform(ApplicationForm applicationform) {
		this.applicationform = applicationform;
	}

	public int getLoan_acc_no() {
		return loan_acc_no;
	}

	public void setLoan_acc_no(int loan_acc_no) {
		this.loan_acc_no = loan_acc_no;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public String getPermitno() {
		return permitno;
	}

	public void setPermitno(String permitno) {
		this.permitno = permitno;
	}

	public String getPermitvalid() {
		return permitvalid;
	}

	public void setPermitvalid(String permitvalid) {
		this.permitvalid = permitvalid;
	}

	public String getTyop() {
		return tyop;
	}

	public void setTyop(String tyop) {
		this.tyop = tyop;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAcNum() {
		return acNum;
	}

	public void setAcNum(int acNum) {
		this.acNum = acNum;
	}

	public String getAcType() {
		return acType;
	}

	public void setAcType(String acType) {
		this.acType = acType;
	}

	public String getValidateShno() {
		return validateShno;
	}

	public void setValidateShno(String validateShno) {
		this.validateShno = validateShno;
	}

	public String getDetailStatus() {
		return detailStatus;
	}

	public void setDetailStatus(String detailStatus) {
		this.detailStatus = detailStatus;
	}

	public String getRelName() {
		return relName;
	}

	public void setRelName(String relName) {
		this.relName = relName;
	}

	public String getRelDob() {
		return relDob;
	}

	public void setRelDob(String relDob) {
		this.relDob = relDob;
	}

	public String getRelTor() {
		return relTor;
	}

	public void setRelTor(String relTor) {
		this.relTor = relTor;
	}

	public String getRelTos() {
		return relTos;
	}

	public void setRelTos(String relTos) {
		this.relTos = relTos;
	}

	public String getRelTom() {
		return relTom;
	}

	public void setRelTom(String relTom) {
		this.relTom = relTom;
	}

	public String getRelTostatus() {
		return relTostatus;
	}

	public void setRelTostatus(String relTostatus) {
		this.relTostatus = relTostatus;
	}

	public String getIndName() {
		return indName;
	}

	public void setIndName(String indName) {
		this.indName = indName;
	}

	public String getInfDob() {
		return infDob;
	}

	public void setInfDob(String infDob) {
		this.infDob = infDob;
	}

	public String getIndTos() {
		return indTos;
	}

	public void setIndTos(String indTos) {
		this.indTos = indTos;
	}

	public String getIndTom() {
		return indTom;
	}

	public void setIndTom(String indTom) {
		this.indTom = indTom;
	}

	public String getIndTostatus() {
		return indTostatus;
	}

	public void setIndTostatus(String indTostatus) {
		this.indTostatus = indTostatus;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getDepDob() {
		return depDob;
	}

	public void setDepDob(String depDob) {
		this.depDob = depDob;
	}

	public String getDepTos() {
		return depTos;
	}

	public void setDepTos(String depTos) {
		this.depTos = depTos;
	}

	public String getDepTor() {
		return depTor;
	}

	public void setDepTor(String depTor) {
		this.depTor = depTor;
	}

	public String getPaymtmode() {
		return paymtmode;
	}

	public void setPaymtmode(String paymtmode) {
		this.paymtmode = paymtmode;
	}

	public String getDirdetails() {
		return dirdetails;
	}

	public void setDirdetails(String dirdetails) {
		this.dirdetails = dirdetails;
	}

	public String getPayactype() {
		return payactype;
	}

	public void setPayactype(String payactype) {
		this.payactype = payactype;
	}

	public String getDirrelations() {
		return dirrelations;
	}

	public void setDirrelations(String dirrelations) {
		this.dirrelations = dirrelations;
	}

	public int getPayaccno() {
		return payaccno;
	}

	public void setPayaccno(int payaccno) {
		this.payaccno = payaccno;
	}

	public String getMain_submit() {
		return main_submit;
	}

	public void setMain_submit(String main_submit) {
		this.main_submit = main_submit;
	}

	/*
	 * public ServiceActionForm getServiceActionForm() { return
	 * serviceActionForm; }
	 */
	/*
	 * public void setServiceActionForm(ServiceActionForm serviceActionForm) {
	 * this.serviceActionForm = serviceActionForm; }
	 */

	public SelfEmployedForm getSelfEmployedForm() {
		return selfEmployedForm;
	}

	public void setSelfEmployedForm(SelfEmployedForm selfEmployedForm) {
		this.selfEmployedForm = selfEmployedForm;
	}

	public BusinessActionForm getBusinessActionForm() {
		return businessActionForm;
	}

	public void setBusinessActionForm(BusinessActionForm businessActionForm) {
		this.businessActionForm = businessActionForm;
	}

	public RentActionFrom getRentActionFrom() {
		return rentActionFrom;
	}

	public void setRentActionFrom(RentActionFrom rentActionFrom) {
		this.rentActionFrom = rentActionFrom;
	}

	public PensionActionForm getPensionActionForm() {
		return pensionActionForm;
	}

	public void setPensionActionForm(PensionActionForm pensionActionForm) {
		this.pensionActionForm = pensionActionForm;
	}

	public String getEmpNature() {
		System.out.println("empNature--->" + empNature);

		return empNature;
	}

	public void setEmpNature(String empNature) {
		this.empNature = empNature;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public int getLoService() {
		return loService;
	}

	public void setLoService(int loService) {
		this.loService = loService;
	}

	public double getNetIncome() {
		return netIncome;
	}

	public void setNetIncome(double netIncome) {
		this.netIncome = netIncome;
	}

}
