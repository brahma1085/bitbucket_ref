package com.scb.td.forms;

import org.apache.struts.action.ActionForm;

public class ReceiptUpdation extends ActionForm {
	
	
	private String pageId,testing,forward;
	private int from_ac_no,to_ac_no,rct_no,cid;
	private int curr_recipt_no,new_rct_no;
	private String ac_type,pay_mode,pay_ac_type,details,but_clear,but_update,butt_print,butt_reprint,butt_view,butt_selectAll,refreshpage;
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	
	public int getFrom_ac_no() {
		return from_ac_no;
	}
	public void setFrom_ac_no(int from_ac_no) {
		this.from_ac_no = from_ac_no;
	}
	public int getTo_ac_no() {
		return to_ac_no;
	}
	public void setTo_ac_no(int to_ac_no) {
		this.to_ac_no = to_ac_no;
	}
	public int getRct_no() {
		return rct_no;
	}
	public void setRct_no(int rct_no) {
		this.rct_no = rct_no;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getAc_type() {
		return ac_type;
	}
	public void setAc_type(String ac_type) {
		this.ac_type = ac_type;
	}
	public String getPay_mode() {
		return pay_mode;
	}
	public void setPay_mode(String pay_mode) {
		this.pay_mode = pay_mode;
	}
	public String getPay_ac_type() {
		return pay_ac_type;
	}
	public void setPay_ac_type(String pay_ac_type) {
		this.pay_ac_type = pay_ac_type;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getBut_clear() {
		return but_clear;
	}
	public void setBut_clear(String but_clear) {
		this.but_clear = but_clear;
	}
	public String getBut_update() {
		return but_update;
	}
	public void setBut_update(String but_update) {
		this.but_update = but_update;
	}
	public int getCurr_recipt_no() {
		return curr_recipt_no;
	}
	public void setCurr_recipt_no(int curr_recipt_no) {
		this.curr_recipt_no = curr_recipt_no;
	}
	public int getNew_rct_no() {
		return new_rct_no;
	}
	public void setNew_rct_no(int new_rct_no) {
		this.new_rct_no = new_rct_no;
	}
	public String getButt_print() {
		return butt_print;
	}
	public void setButt_print(String butt_print) {
		this.butt_print = butt_print;
	}
	public String getButt_reprint() {
		return butt_reprint;
	}
	public void setButt_reprint(String butt_reprint) {
		this.butt_reprint = butt_reprint;
	}
	public String getButt_view() {
		return butt_view;
	}
	public void setButt_view(String butt_view) {
		this.butt_view = butt_view;
	}
	public String getButt_selectAll() {
		return butt_selectAll;
	}
	
	public void setButt_selectAll(String butt_selectAll) {
		this.butt_selectAll = butt_selectAll;
	}
	public String getTesting() {
		return testing;
	}
	public void setTesting(String testing) {
		this.testing = testing;
	}
	public String getForward() {
		return forward;
	}
	public void setForward(String forward) {
		this.forward = forward;
	}
	public String getRefreshpage() {
		return refreshpage;
	}
	public void setRefreshpage(String refreshpage) {
		this.refreshpage = refreshpage;
	}

}
