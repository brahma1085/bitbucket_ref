package com.scb.loans.forms;

import org.apache.struts.action.ActionForm;

import com.scb.common.forms.PageIdForm;

public class LoansAmmendmentsForm extends ActionForm {
	
	int sanction_res;
	private String acctype,shtype,detailsammend,priority,disbamt,combo_pay_mode,buttonvalue,agentcode,value1,tabPaneHeading,button_value,disburse_submit;
	private int accno,period,sanction,purpose,shno,cid;
	private double amount,holiday,intrate,penalrate,installment,disbleft;
	private boolean weak;
	private PageIdForm  pageidentity= new PageIdForm();
	
	
	
	//used in Sanction form
	private int appln_no,interesttype,interestcalctype,payaccno,dirdetails;
	private double reqamount;
	private String appndate,relativetodir,submit,paymtmode,dirrelations,payactype;
	
	//Used in Signature form
	
	private int acNum;
	private String acType,name,tyop;
	
	
	
	
	
	 
	//Vehicle Form
	 private String maketype,dealername,licenseno,vehicletype,vehiclefor,vehiclearea,vehicleparked,othervehicle,validity,permitno,permitvalid;
	 private double cost;   
	
	//Employment Form
	  String comb_emp_visible;
	  
	//Self-Employed Form
	  private String employmtnature,address,serv_length,emp_nature;
	  int phno;
	  double income,expenditure,netincome;

	 //ServiceForm
	  String employername,designation,department;
	  int phoneno;
	  String empno;
	  boolean confirmation,transferable,certicateenclosed;
	  
	  //BusinessForm
	   private String concernname,businessnature;
	   private double surplus,avg_turnover;
	  
	   
	   //PensionForm
	   
	   String empname,bankname;
	 
	   double pensionamt;
	   
	   //Rent Form
	   private String land_addr;
	   private double totamt,tax_payment;
	   
	   //PropertyForm
	   private String propertyno,addr,ewbyfeets,nsbyfeets,eastby,westby,northby,southby,propertyacquiredby,nature;
	   private double value;
	   private Object tenant[][];
		
	   //Relation From
	   String relName,relDob,relTor,relTos,relTom,relTostatus,indName,infDob,indTos,indTom,indTostatus,depName,depDob,depTos,depTor;
	  String forward;
	
	
	public PageIdForm getPageidentity() {
		return pageidentity;
	}
	public void setPageidentity(PageIdForm pageidentity) {
		this.pageidentity = pageidentity;
	}
	public String getAcctype() {
		return acctype;
	}
	public void setAcctype(String acctype) {
		this.acctype = acctype;
	}
	public String getShtype() {
		return shtype;
	}
	public void setShtype(String shtype) {
		this.shtype = shtype;
	}
	public int getShno() {
		return shno;
	}
	public void setShno(int shno) {
		this.shno = shno;
	}
	public int getPurpose() {
		return purpose;
	}
	public void setPurpose(int purpose) {
		this.purpose = purpose;
	}
	
	
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	public boolean isWeak() {
		return weak;
	}
	public void setWeak(boolean weak) {
		this.weak = weak;
	}
	public String getDisbamt() {
		return disbamt;
	}
	public void setDisbamt(String disbamt) {
		this.disbamt = disbamt;
	}
	public String getCombo_pay_mode() {
		return combo_pay_mode;
	}
	public void setCombo_pay_mode(String combo_pay_mode) {
		this.combo_pay_mode = combo_pay_mode;
	}
	public int getAccno() {
		return accno;
	}
	public void setAccno(int accno) {
		this.accno = accno;
	}
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
	public double getIntrate() {
		return intrate;
	}
	public void setIntrate(double intrate) {
		this.intrate = intrate;
	}
	
	public int getAppln_no() {
		return appln_no;
	}
	public void setAppln_no(int appln_no) {
		this.appln_no = appln_no;
	}
	
	
	public double getReqamount() {
		return reqamount;
	}
	public void setReqamount(double reqamount) {
		this.reqamount = reqamount;
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
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public double getHoliday() {
		return holiday;
	}
	public void setHoliday(double holiday) {
		this.holiday = holiday;
	}
	public double getPenalrate() {
		return penalrate;
	}
	public void setPenalrate(double penalrate) {
		this.penalrate = penalrate;
	}
	public double getInstallment() {
		return installment;
	}
	public void setInstallment(double installment) {
		this.installment = installment;
	}
	public String getButtonvalue() {
		return buttonvalue;
	}
	public void setButtonvalue(String buttonvalue) {
		this.buttonvalue = buttonvalue;
	}
	public int getSanction() {
		return sanction;
	}
	public void setSanction(int sanction) {
		this.sanction = sanction;
	}
	public String getAgentcode() {
		return agentcode;
	}
	public void setAgentcode(String agentcode) {
		this.agentcode = agentcode;
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
	public String getSubmit() {
		return submit;
	}
	public void setSubmit(String submit) {
		this.submit = submit;
	}
	public String getPaymtmode() {
		return paymtmode;
	}
	public void setPaymtmode(String paymtmode) {
		this.paymtmode = paymtmode;
	}
	public String getValue1() {
		return value1;
	}
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	public int getSanction_res() {
		return sanction_res;
	}
	public void setSanction_res(int sanction_res) {
		this.sanction_res = sanction_res;
	}
	public String getDirrelations() {
		return dirrelations;
	}
	public void setDirrelations(String dirrelations) {
		this.dirrelations = dirrelations;
	}
	public int getDirdetails() {
		return dirdetails;
	}
	public void setDirdetails(int dirdetails) {
		this.dirdetails = dirdetails;
	}
	public int getPayaccno() {
		return payaccno;
	}
	public void setPayaccno(int payaccno) {
		this.payaccno = payaccno;
	}
	public String getPayactype() {
		return payactype;
	}
	public void setPayactype(String payactype) {
		this.payactype = payactype;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTyop() {
		return tyop;
	}
	public void setTyop(String tyop) {
		this.tyop = tyop;
	}
	public String getTabPaneHeading() {
		return tabPaneHeading;
	}
	public void setTabPaneHeading(String tabPaneHeading) {
		this.tabPaneHeading = tabPaneHeading;
	}
	public double getDisbleft() {
		return disbleft;
	}
	public void setDisbleft(double disbleft) {
		this.disbleft = disbleft;
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
	public String getServ_length() {
		return serv_length;
	}
	public void setServ_length(String serv_length) {
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
		return employername;
	}
	public void setEmployername(String employername) {
		this.employername = employername;
	}
	public String getDesignation() {
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
		return phoneno;
	}
	public void setPhoneno(int phoneno) {
		this.phoneno = phoneno;
	}
	public String getEmpno() {
		return empno;
	}
	public void setEmpno(String empno) {
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
	public String getForward() {
		return forward;
	}
	public void setForward(String forward) {
		this.forward = forward;
	}
	public String getDetailsammend() {
		return detailsammend;
	}
	public void setDetailsammend(String detailsammend) {
		this.detailsammend = detailsammend;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getButton_value() {
		return button_value;
	}
	public void setButton_value(String button_value) {
		this.button_value = button_value;
	}
	public String getDisburse_submit() {
		return disburse_submit;
	}
	public void setDisburse_submit(String disburse_submit) {
		this.disburse_submit = disburse_submit;
	}


}
