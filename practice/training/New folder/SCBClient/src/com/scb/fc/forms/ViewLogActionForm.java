package com.scb.fc.forms;

import java.io.Serializable;

import org.apache.struts.action.ActionForm;

    /**
     * Created by IntelliJ IDEA.
     * User: user
     * Date: Dec 3, 2007
     * Time: 10:50:35 AM
     * To change this template use File | Settings | File Templates.
     */
    public class ViewLogActionForm extends ActionForm implements Serializable {
       String pageId,defaultSignIndex;
       String acType,acNum;
       
       public String getAcType() {
            return acType;
       }

       public void setAcType(String acType) {
    	   this.acType = acType;
       }

       public String getAcNum() {
    	   return acNum;
       }

       public void setAcNum(String acNum) {
    	   this.acNum = acNum;
       }

	

	public String getDefaultSignIndex() {
		return defaultSignIndex;
	}

	public void setDefaultSignIndex(String defaultSignIndex) {
		System.out.println("defaultSignIndex=="+defaultSignIndex);
		this.defaultSignIndex = defaultSignIndex;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

      
}
