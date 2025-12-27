package com.scb.cm.forms;

import java.io.Serializable;

import org.apache.struts.action.ActionForm;

import com.scb.cm.actions.CustomerInformationForm;

/**
 * Created by IntelliJ IDEA.
 * User: shwetha 
 * Date: Dec 6, 2007
 * Time: 10:01:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class Customerform extends ActionForm implements Serializable{
    int custid;
    String pageId,custtype,value,cid_verified,cid_notver,clear_value,cid_first,totalclear,testing;
   
    
  	
	public int getCustid() {
        return custid;
    }

    public void setCustid(int custid) {
        this.custid = custid;
    }

    public String getCusttype() {
        return custtype;
    }

    public void setCusttype(String custtype) {
        this.custtype = custtype;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCid_verified() {
		return cid_verified;
	}

	public void setCid_verified(String cid_verified) {
		this.cid_verified = cid_verified;
	}

	public String getCid_notver() {
		return cid_notver;
	}

	public void setCid_notver(String cid_notver) {
		this.cid_notver = cid_notver;
	}

	public String getClear_value() {
		return clear_value;
	}

	public void setClear_value(String clear_value) {
		this.clear_value = clear_value;
	}

	public String getCid_first() {
		return cid_first;
	}

	public void setCid_first(String cid_first) {
		this.cid_first = cid_first;
	}

	public String getTotalclear() {
		return totalclear;
	}

	public void setTotalclear(String totalclear) {
		this.totalclear = totalclear;
	}

	public String getTesting() {
		return testing;
	}

	public void setTesting(String testing) {
		this.testing = testing;
	}

		
}
