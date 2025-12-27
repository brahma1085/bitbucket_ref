package com.scb.fc.forms;

import java.io.Serializable;
import javax.servlet.http.*;
import org.apache.struts.action.*;

    /**
     * Created by IntelliJ IDEA.
     * User: Mohsin
     * Date: Dec 17, 2008
     * Time: 10:50:35 AM
     * To change this template use File | Settings | File Templates.
     */
    public class FCPOConsolidatedReportForm extends ActionForm implements Serializable {
       String pageId,tdate,fdate,save;
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

	public String getSave() {
		return save;
	}

	public void setSave(String save) {
		this.save = save;
	}
         
}
