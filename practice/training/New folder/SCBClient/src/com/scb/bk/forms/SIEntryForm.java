package com.scb.bk.forms;
import org.apache.struts.action.ActionForm;

import com.scb.common.forms.PageIdForm;


/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: Nov 11, 2007
 * Time: 12:09:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class SIEntryForm extends ActionForm {

    private String instruction_num;
    private int priority_num;
    private String from_ac_no;
    private String to_ac_no;
    private String period_mon;
    private String days;
    private String no_of_times_exec;
    private String expiry_days;
    private String from_ac_ty,to_ac_ty,loan_option;
    private String amount;
    private String due_date,last_exec_date;
    private String pageid,pageId;
    private String loan_option_name=null;
    private String submit;
    private String clear;
    private String forward;
    private int instnum;
    private String insvalue;
    private String accnovalid,invalid_ins; 
    String tabPaneHeading,defaultTab;
    String verifyfun,submit1;
  
    
    PageIdForm pageidform= new PageIdForm();

    

    public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getLoan_option_name() {
        return loan_option_name;
    }

    public void setLoan_option_name(String loan_option_name) {
        this.loan_option_name = loan_option_name;
    }





    public String getLoan_option() {
        return loan_option;
    }

    public void setLoan_option(String loan_option) {
        this.loan_option = loan_option;
    }



    public String getTo_ac_ty() {
        return to_ac_ty;
    }

    public void setTo_ac_ty(String to_ac_ty) {
        this.to_ac_ty = to_ac_ty;
    }



    public String getFrom_ac_ty() {
        return from_ac_ty;
    }

    public void setFrom_ac_ty(String from_ac_ty) {
        this.from_ac_ty = from_ac_ty;
    }


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getTo_ac_no() {
        return to_ac_no;
    }

    public void setTo_ac_no(String to_ac_no) {
        this.to_ac_no = to_ac_no;
    }

   
    public int getPriority_num() {
		return priority_num;
	}

	public void setPriority_num(int priority_num) {
		this.priority_num = priority_num;
	}

	public String getPeriod_mon() {
        return period_mon;
    }

    public void setPeriod_mon(String period_mon) {
        this.period_mon = period_mon;
    }

    public String getExpiry_days() {
        return expiry_days;
    }

    public void setExpiry_days(String expiry_days) {
        this.expiry_days = expiry_days;
    }

    public String getFrom_ac_no() {
        return from_ac_no;
    }

    public void setFrom_ac_no(String from_ac_no) {
        this.from_ac_no = from_ac_no;
    }

    public String getInstruction_num() {
        return instruction_num;
    }

    public void setInstruction_num(String instruction_num) {
        this.instruction_num = instruction_num;
    }

    public String getLast_exec_date() {
        return last_exec_date;
    }

    public void setLast_exec_date(String last_exec_date) {
        this.last_exec_date = last_exec_date;
    }

    public String getNo_of_times_exec() {
        return no_of_times_exec;
    }

    public void setNo_of_times_exec(String no_of_times_exec) {
        this.no_of_times_exec = no_of_times_exec;
    }

	

	public String getPageid() {
		return pageid;
	}

	public void setPageid(String pageid) {
		this.pageid = pageid;
	}

	public PageIdForm getPageidform() {
		return pageidform;
	}

	public void setPageidform(PageIdForm pageidform) {
		this.pageidform = pageidform;
	}

	public String getSubmit() {
		return submit;
	}

	public void setSubmit(String submit) {
		this.submit = submit;
	}

	public String getClear() {
		return clear;
	}

	public void setClear(String clear) {
		this.clear = clear;
	}

	public String getForward() {
		System.out.println("getting");
		return forward;
	}

	public void setForward(String forward) {
		System.out.println("setting====="+forward);
		this.forward = forward;
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

	public int getInstnum() {
		return instnum;
	}

	public void setInstnum(int instnum) {
		this.instnum = instnum;
	}

	public String getInsvalue() {
		return insvalue;
	}

	public void setInsvalue(String insvalue) {
		this.insvalue = insvalue;
	}

	

	
	public String getAccnovalid() {
		return accnovalid;
	}

	public void setAccnovalid(String accnovalid) {
		this.accnovalid = accnovalid;
	}

	public String getVerifyfun() {
		return verifyfun;
	}

	public void setVerifyfun(String verifyfun) {
		this.verifyfun = verifyfun;
	}

	public String getInvalid_ins() {
		return invalid_ins;
	}

	public void setInvalid_ins(String invalid_ins) {
		this.invalid_ins = invalid_ins;
	}

	public String getSubmit1() {
		return submit1;
	}

	public void setSubmit1(String submit1) {
		this.submit1 = submit1;
	}

	
}