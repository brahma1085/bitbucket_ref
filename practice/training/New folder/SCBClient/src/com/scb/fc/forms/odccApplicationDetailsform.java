package com.scb.fc.forms;

import java.io.Serializable;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;

    /**
     * Created by IntelliJ IDEA.
     * User: Mohsin
     * Date: Dec 17, 2008
     * Time: 10:50:35 AM
     * To change this template use File | Settings | File Templates.
     */
    public class odccApplicationDetailsform extends ActionForm implements Serializable {
       String pageId,defaultSignIndex,srlnonew;
       String purpose,date,srlno,amount,mode,intType,intcalc;
       
       
      

	

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSrlno() {
		return srlno;
	}

	public void setSrlno(String srlno) {
		this.srlno = srlno;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getIntType() {
		return intType;
	}

	public void setIntType(String intType) {
		this.intType = intType;
	}

	public String getIntcalc() {
		return intcalc;
	}

	public void setIntcalc(String intcalc) {
		this.intcalc = intcalc;
	}

	public String getDefaultSignIndex() {
		return defaultSignIndex;
	}

	public void setDefaultSignIndex(String defaultSignIndex) {
		System.out.println("defaultSignIndex=="+defaultSignIndex);
		this.defaultSignIndex = defaultSignIndex;
	}

	public String getPageId() {
		System.out.println("At 46 inside AccountCloseActionForm"+pageId);
		return pageId;
	}

	public void setPageId(String pageId) {
		System.out.println("At 51 setting pageId"+pageId);
		this.pageId = pageId;
	}

	public String getSrlnonew() {
		return srlnonew;
	}

	public void setSrlnonew(String srlnonew) {
		this.srlnonew = srlnonew;
	}

	
}
