package com.scb.bk.forms;
import org.apache.struts.action.ActionForm;

public class TransactionSummaryForm extends ActionForm {
	
  private String fromdate,todate,accType,category,select;
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

public String getAccType() {return accType;}
public void setAccType(String accType) {this.accType = accType;}

public String getCategory() {return category;}
public void setCategory(String category) {this.category = category;}

public String getSelect() {	return select;}
public void setSelect(String select) {	this.select = select;}

public String getValid() {
	return valid;
}
public void setValid(String valid) {
	this.valid = valid;
}
	
}