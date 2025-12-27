package com.scb.sh.forms;

import org.apache.struts.action.ActionForm;

public class ShareDivrateForm extends ActionForm{
private String pageId;
private String select,from_dt,to_dt,div_rate,drf,cal_done,cal_opted;
private String dir_code,cid,dir_fr_dt,dir_to_dt;
private String rel_code,rel_type;
private String forward,submit,insert,delete,clear;

public String getDir_code() {
	return dir_code;
}

public void setDir_code(String dir_code) {
	this.dir_code = dir_code;
}

public String getCid() {
	return cid;
}

public void setCid(String cid) {
	this.cid = cid;
}

public String getDir_fr_dt() {
	return dir_fr_dt;
}

public void setDir_fr_dt(String dir_fr_dt) {
	this.dir_fr_dt = dir_fr_dt;
}

public String getDir_to_dt() {
	return dir_to_dt;
}

public void setDir_to_dt(String dir_to_dt) {
	this.dir_to_dt = dir_to_dt;
}

public String getRel_code() {
	return rel_code;
}

public void setRel_code(String rel_code) {
	this.rel_code = rel_code;
}

public String getRel_type() {
	return rel_type;
}

public void setRel_type(String rel_type) {
	this.rel_type = rel_type;
}

public String getFrom_dt() {
	return from_dt;
}

public void setFrom_dt(String from_dt) {
	this.from_dt = from_dt;
}

public String getTo_dt() {
	return to_dt;
}

public void setTo_dt(String to_dt) {
	this.to_dt = to_dt;
}

public String getDiv_rate() {
	return div_rate;
}

public void setDiv_rate(String div_rate) {
	this.div_rate = div_rate;
}

public String getDrf() {
	return drf;
}

public void setDrf(String drf) {
	this.drf = drf;
}

public String getCal_done() {
	return cal_done;
}

public void setCal_done(String cal_done) {
	this.cal_done = cal_done;
}

public String getCal_opted() {
	return cal_opted;
}

public void setCal_opted(String cal_opted) {
	this.cal_opted = cal_opted;
}

public String getSelect() {
	return select;
}

public void setSelect(String select) {
	this.select = select;
}

public String getPageId() {
	return pageId;
}

public void setPageId(String pageId) {
	this.pageId = pageId;
}

public String getForward() {
	return forward;
}

public void setForward(String forward) {
	this.forward = forward;
}

public String getSubmit() {
	return submit;
}

public void setSubmit(String submit) {
	this.submit = submit;
}

public String getInsert() {
	return insert;
}

public void setInsert(String insert) {
	this.insert = insert;
}

public String getDelete() {
	return delete;
}

public void setDelete(String delete) {
	this.delete = delete;
}

public String getClear() {
	return clear;
}

public void setClear(String clear) {
	this.clear = clear;
}
}
