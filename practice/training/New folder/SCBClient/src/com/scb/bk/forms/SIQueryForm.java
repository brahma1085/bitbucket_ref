package com.scb.bk.forms;
import org.apache.struts.action.ActionForm;

public class SIQueryForm extends ActionForm {
	
private String forward;
private String acctype,accno,pageId,accholdername,valid;


public String getPageId() {	return pageId;}
public void setPageId(String pageId) {	this.pageId = pageId;}

public String getAcctype() {return acctype;}
public void setAcctype(String acctype) {this.acctype = acctype;}

public String getAccno() {return accno;}
public void setAccno(String accno) {this.accno = accno;}

public String getForward() {return forward;}
public void setForward(String forward) {this.forward = forward;}

public String getAccholdername() {return accholdername;}
public void setAccholdername(String accholdername) {this.accholdername = accholdername;}
public String getValid() {
	return valid;
}
public void setValid(String valid) {
	this.valid = valid;
}



}