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
    public class VerifyPOConsolidationForm extends ActionForm implements Serializable {
       String pageId;
       String payorder,chequeno,payeename,hidval;
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getPayorder() {
		return payorder;
	}
	public void setPayorder(String payorder) {
		this.payorder = payorder;
	}
	public String getChequeno() {
		return chequeno;
	}
	public void setChequeno(String chequeno) {
		this.chequeno = chequeno;
	}
	public String getPayeename() {
		return payeename;
	}
	public void setPayeename(String payeename) {
		this.payeename = payeename;
	}
	public String getHidval() {
		return hidval;
	}
	public void setHidval(String hidval) {
		this.hidval = hidval;
	}
  
}
