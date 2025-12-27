package com.scb.fc.forms;

import java.io.Serializable;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionForm;

       public class POInstructionForm extends ActionForm implements Serializable 
    {
    	String defaultSignIndex,defaultTab,defSIndex,pageId,chno,pon,date,validity;
String poinst,stop,cancel,amount,payee,subval;
		
String sysdate;


		public String getSysdate() {
			return sysdate;
		}
	
		public void setSysdate(String sysdate) {
			this.sysdate = sysdate;
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

		public String getChno() {
			return chno;
		}

		public void setChno(String chno) {
			this.chno = chno;
		}

		public String getPon() {
			return pon;
		}

		public void setPon(String pon) {
			this.pon = pon;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getValidity() {
			return validity;
		}

		public void setValidity(String validity) {
			this.validity = validity;
		}

		public String getPoinst() {
			return poinst;
		}

		public void setPoinst(String poinst) {
			this.poinst = poinst;
		}

		public String getStop() {
			return stop;
		}

		public void setStop(String stop) {
			this.stop = stop;
		}

		public String getCancel() {
			return cancel;
		}

		public void setCancel(String cancel) {
			this.cancel = cancel;
		}

		public String getAmount() {
			return amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		public String getPayee() {
			return payee;
		}

		public void setPayee(String payee) {
			this.payee = payee;
		}

		public String getSubval() {
			return subval;
		}

		public void setSubval(String subval) {
			this.subval = subval;
		}

		
		
    	
}
