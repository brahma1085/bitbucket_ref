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
    public class AccountCloseActionForm extends ActionForm implements Serializable {
       String pageId,defaultSignIndex;
       String acType,acNum,hidvar;
       
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
		System.out.println("At 46 inside AccountCloseActionForm"+pageId);
		return pageId;
	}

	public void setPageId(String pageId) {
		System.out.println("At 51 setting pageId"+pageId);
		this.pageId = pageId;
	}

	public String getHidvar() {
		return hidvar;
	}

	public void setHidvar(String hidvar) {
		this.hidvar = hidvar;
	}

	/*public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
    {
	ActionErrors errors = new ActionErrors();
	System.out.println("Actionform class");

		if(acType.equals("")&&acNum.equals(""))
		{
		System.out.println("no input provided");
errors.add("someName", new ActionMessage("errors.cancel"));
		}
        return errors;
    }*/
}
