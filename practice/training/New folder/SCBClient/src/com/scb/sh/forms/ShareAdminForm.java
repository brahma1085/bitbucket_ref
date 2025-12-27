package com.scb.sh.forms;

import java.util.List;

import org.apache.struts.action.ActionForm;

public class ShareAdminForm extends ActionForm{
private String pageId;
private String actype,mem_cat,cat_name,min,max,sh_val,vote;
private String forward;
private Object[][] table_data;   
private List array_list; 



public String getForward() {
	return forward;
}

public void setForward(String forward) {
	this.forward = forward;
}

public String getActype() {
	return actype;
}

public void setActype(String actype) {
	this.actype = actype;
}

public String getMem_cat() {
	return mem_cat;
}

public void setMem_cat(String mem_cat) {
	this.mem_cat = mem_cat;
}

public String getCat_name() {
	return cat_name;
}

public void setCat_name(String cat_name) {
	this.cat_name = cat_name;
}

public String getMin() {
	return min;
}

public void setMin(String min) {
	this.min = min;
}

public String getMax() {
	return max;
}

public void setMax(String max) {
	this.max = max;
}

public String getSh_val() {
	return sh_val;
}

public void setSh_val(String sh_val) {
	this.sh_val = sh_val;
}

public String getVote() {
	return vote;
}

public void setVote(String vote) {
	this.vote = vote;
}

public String getPageId() {
	return pageId;
}

public void setPageId(String pageId) {
	this.pageId = pageId;
}
public Object[][] getTable_data() {
	return table_data;
}

public void setTable_data(Object[][] table_data) {
	this.table_data = table_data;
	System.out.println("table_data >> "+table_data);
	System.out.println(table_data.length);
	/*if(table_data!=null){
		for(int i=0;i<table_data.length;i++)
			System.out.println(table_data[i][0]);
	}*/
}

public List getArray_list() {
	return array_list;
}

public void setArray_list(List array_list) {
	System.out.println("yes i am here ");
	this.array_list = array_list;
}
}
