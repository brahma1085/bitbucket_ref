package com.scb.bk.forms;
import org.apache.struts.action.ActionForm;

public class UnPresentedChequeForm extends ActionForm {
	
private String forward;
private String acctype,accno,pageId,valid,validaccno,printFile,but_print,testing;



public String getTesting() {         
	return testing;
}
public void setTesting(String testing) {
	this.testing = testing;
}
public String getbut_print() {
	return but_print;                       
}
public void setbut_print(String but_print) {
	this.but_print = but_print;
}
public String getPrintFile() {
	return printFile;
}
public void setPrintFile(String printFile) {
	this.printFile = printFile;
}
public String getPageId() {	return pageId;}
public void setPageId(String pageId) {	this.pageId = pageId;}

public String getAcctype() {return acctype;}
public void setAcctype(String acctype) {this.acctype = acctype;}

public String getAccno() {return accno;}
public void setAccno(String accno) {this.accno = accno;}

public String getForward() {return forward;}
public void setForward(String forward) {this.forward = forward;}
public String getValid() {
	return valid;
}
public void setValid(String valid) {
	this.valid = valid;
}
public String getValidaccno() {
	return validaccno;
}
public void setValidaccno(String validaccno) {
	this.validaccno = validaccno;
}



}