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
    public class FCPOMakeForm extends ActionForm implements Serializable {
       String pageId,fdate,tdate,View,save;

       String sysdate;

       public String getSysdate() {
       		return sysdate;
       	}


       	public void setSysdate(String sysdate) {
       		this.sysdate = sysdate;
       	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
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

	public String getView() {
		return View;
	}

	public void setView(String view) {
		View = view;
	}

	public String getSave() {
		return save;
	}

	public void setSave(String save) {
		this.save = save;
	}
  
}
