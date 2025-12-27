package com.scb.fc.forms;

import java.io.Serializable;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;

       public class PODataEntryForm extends ActionForm implements Serializable 
    {
    	String defaultSignIndex,defaultTab,defSIndex,pageId,srlno,acno,favour,amount,comission;
String acType,cat,purName,glcode,glname,gltype,to,gltyp,poto,selectedval,potype,gl;
String hidval,balance,new1,po,subval,verval,srno,bankpurName;
		public String getHidval() {
	return hidval;
}

public void setHidval(String hidval) {
	this.hidval = hidval;
}

		public String getAcType() {
	return acType;
}

public void setAcType(String acType) {
	this.acType = acType;
}

		public String getDefaultSignIndex() {
			return defaultSignIndex;
		}

		public void setDefaultSignIndex(String defaultSignIndex) {
			this.defaultSignIndex = defaultSignIndex;
		}

		public String getDefaultTab() {
			return defaultTab;
		}

		public void setDefaultTab(String defaultTab) {
			this.defaultTab = defaultTab;
		}

		public String getDefSIndex() {
			return defSIndex;
		}

		public void setDefSIndex(String defSIndex) {
			this.defSIndex = defSIndex;
		}

		public String getPageId() {
			return pageId;
		}

		public void setPageId(String pageId) {
			this.pageId = pageId;
		}

		public String getSrlno() {
			return srlno;
		}

		public void setSrlno(String srlno) {
			this.srlno = srlno;
		}

		public String getAcno() {
			return acno;
		}

		public void setAcno(String acno) {
			this.acno = acno;
		}

		public String getFavour() {
			return favour;
		}

		public void setFavour(String favour) {
			this.favour = favour;
		}

		public String getAmount() {
			return amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		public String getComission() {
			return comission;
		}

		public void setComission(String comission) {
			this.comission = comission;
		}

		public String getCat() {
			return cat;
		}

		public void setCat(String cat) {
			this.cat = cat;
		}

		public String getPurName() {
			return purName;
		}

		public void setPurName(String purName) {
			this.purName = purName;
		}

		public String getGlcode() {
			return glcode;
		}

		public void setGlcode(String glcode) {
			this.glcode = glcode;
		}

		public String getGlname() {
			return glname;
		}

		public void setGlname(String glname) {
			this.glname = glname;
		}

		public String getGltype() {
			return gltype;
		}

		public void setGltype(String gltype) {
			this.gltype = gltype;
		}

		public String getTo() {
			return to;
		}

		public void setTo(String to) {
			this.to = to;
		}

		public String getGltyp() {
			return gltyp;
		}

		public void setGltyp(String gltyp) {
			this.gltyp = gltyp;
		}

		public String getPoto() {
			return poto;
		}

		public void setPoto(String poto) {
			this.poto = poto;
		}

		public String getSelectedval() {
			return selectedval;
		}

		public void setSelectedval(String selectedval) {
			this.selectedval = selectedval;
		}

		public String getPotype() {
			return potype;
		}

		public void setPotype(String potype) {
			this.potype = potype;
		}

		public String getGl() {
			return gl;
		}

		public void setGl(String gl) {
			this.gl = gl;
		}

		public String getBalance() {
			return balance;
		}

		public void setBalance(String balance) {
			this.balance = balance;
		}

		public String getNew1() {
			return new1;
		}

		public void setNew1(String new1) {
			this.new1 = new1;
		}

		public String getPo() {
			return po;
		}

		public void setPo(String po) {
			this.po = po;
		}

		public String getSubval() {
			return subval;
		}

		public void setSubval(String subval) {
			this.subval = subval;
		}

		public String getVerval() {
			return verval;
		}

		public void setVerval(String verval) {
			this.verval = verval;
		}

		public String getSrno() {
			return srno;
		}

		public void setSrno(String srno) {
			this.srno = srno;
		}

		public String getBankpurName() {
			return bankpurName;
		}

		public void setBankpurName(String bankpurName) {
			this.bankpurName = bankpurName;
		}
    	
    }
    