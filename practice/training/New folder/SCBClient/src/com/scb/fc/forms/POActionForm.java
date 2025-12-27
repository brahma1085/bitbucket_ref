package com.scb.fc.forms;

import org.apache.struts.action.ActionForm;

public class POActionForm  extends ActionForm
{
	String pageId,potype,cust_type,Sub_Category,cat,subcat,acType,viewval,addval;
String fdate,tdate,famount,tamount,comrate,comtype,mincomamt,extcomrate,detml,deuser,dedate;
String submiting;
String sysdate;
public String getSysdate() {
	return sysdate;
}
public void setSysdate(String sysdate) {
	this.sysdate = sysdate;
}	

public String getSubmiting() {
	return submiting;
}

public void setSubmiting(String submiting) {
	this.submiting = submiting;
}

	public String getFdate() {
	return fdate;
}

public void setFdate(String fdate) {
	this.fdate = fdate;
}

public String getTdate() {
	return tdate;
}

public void setTdate(String tdate) {
	this.tdate = tdate;
}

public String getFamount() {
	return famount;
}

public void setFamount(String famount) {
	this.famount = famount;
}

public String getTamount() {
	return tamount;
}

public void setTamount(String tamount) {
	this.tamount = tamount;
}

public String getComrate() {
	return comrate;
}

public void setComrate(String comrate) {
	this.comrate = comrate;
}

public String getComtype() {
	return comtype;
}

public void setComtype(String comtype) {
	this.comtype = comtype;
}

public String getMincomamt() {
	return mincomamt;
}

public void setMincomamt(String mincomamt) {
	this.mincomamt = mincomamt;
}

public String getExtcomrate() {
	return extcomrate;
}

public void setExtcomrate(String extcomrate) {
	this.extcomrate = extcomrate;
}

public String getDetml() {
	return detml;
}

public void setDetml(String detml) {
	this.detml = detml;
}

public String getDeuser() {
	return deuser;
}

public void setDeuser(String deuser) {
	this.deuser = deuser;
}

public String getDedate() {
	return dedate;
}

public void setDedate(String dedate) {
	this.dedate = dedate;
}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getPotype() {
		return potype;
	}

	public void setPotype(String potype) {
		this.potype = potype;
	}

	public String getCust_type() {
		return cust_type;
	}

	public void setCust_type(String cust_type) {
		this.cust_type = cust_type;
	}

	public String getSub_Category() {
		return Sub_Category;
	}

	public void setSub_Category(String sub_Category) {
		Sub_Category = sub_Category;
	}

	public String getCat() {
		return cat;
	}

	public void setCat(String cat) {
		this.cat = cat;
	}

	public String getSubcat() {
		return subcat;
	}

	public void setSubcat(String subcat) {
		this.subcat = subcat;
	}

	public String getAcType() {
		return acType;
	}

	public void setAcType(String acType) {
		this.acType = acType;
	}

	public String getViewval() {
		return viewval;
	}

	public void setViewval(String viewval) {
		this.viewval = viewval;
	}

	public String getAddval() {
		return addval;
	}

	public void setAddval(String addval) {
		this.addval = addval;
	}
}
