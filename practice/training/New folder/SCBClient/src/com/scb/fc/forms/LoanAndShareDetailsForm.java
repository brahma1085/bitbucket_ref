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
    public class LoanAndShareDetailsForm extends ActionForm implements Serializable {
       String pageId,defaultSignIndex;
       String maxloan,noshare,totalvalue;
        
       
       
      

	

	
	public String getMaxloan() {
		return maxloan;
	}

	public void setMaxloan(String maxloan) {
		this.maxloan = maxloan;
	}

	public String getNoshare() {
		return noshare;
	}

	public void setNoshare(String noshare) {
		this.noshare = noshare;
	}

	public String getTotalvalue() {
		return totalvalue;
	}

	public void setTotalvalue(String totalvalue) {
		this.totalvalue = totalvalue;
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

	
}
