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
    public class ReceiptDetailsForm extends ActionForm implements Serializable {
       String pageId,defaultSignIndex;
       String scrollno, date, name, amount,master;
       
       
      

	

		public String getScrollno() {
		return scrollno;
	}

	public void setScrollno(String scrollno) {
		this.scrollno = scrollno;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
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

	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	
}
