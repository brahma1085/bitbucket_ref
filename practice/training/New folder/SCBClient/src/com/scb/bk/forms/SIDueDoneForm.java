package com.scb.bk.forms;
import org.apache.struts.action.ActionForm;

public class SIDueDoneForm extends ActionForm {
	
  private String fromdate,todate,inst_type;
  private String forward,pageId,valid;
  String sysdate;


  public String getSysdate() {
  		return sysdate;
  	}


  	public void setSysdate(String sysdate) {
  		this.sysdate = sysdate;
  	}



  
public String getFromdate() {return fromdate;}
public void setFromdate(String fromdate) {this.fromdate = fromdate;}

public String getTodate() {	return todate;}
public void setTodate(String todate) {this.todate = todate;}

public String getForward() {return forward;}
public void setForward(String forward) {this.forward = forward;}

public String getPageId() {	return pageId;}
public void setPageId(String pageId) {this.pageId = pageId;}

public String getInst_type() {return inst_type;}
public void setInst_type(String inst_type) {this.inst_type = inst_type;}
public String getValid() {
	return valid;
}
public void setValid(String valid) {
	this.valid = valid;
}

	
}