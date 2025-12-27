package com.scb.td.forms;

import org.apache.struts.action.ActionForm;

public class SignatureInstForm extends ActionForm {
String operation, pageId,cid,actype,acno,name,operationtype;

public String getOperation() {
	return operation;
}

public void setOperation(String operation) {
	this.operation = operation;
}

public String getPageId() {
	return pageId;
}

public void setPageId(String pageId) {
	this.pageId = pageId;
}

public String getCid() {
	return cid;
}

public void setCid(String cid) {
	this.cid = cid;
}

public String getActype() {
	return actype;
}

public void setActype(String actype) {
	this.actype = actype;
}

public String getAcno() {
	return acno;
}

public void setAcno(String acno) {
	this.acno = acno;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getOperationtype() {
	return operationtype;
}

public void setOperationtype(String operationtype) {
	this.operationtype = operationtype;
}

}
