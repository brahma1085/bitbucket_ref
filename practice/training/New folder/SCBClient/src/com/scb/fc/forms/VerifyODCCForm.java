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
    public class VerifyODCCForm extends ActionForm implements Serializable {
       String pageId,acType,ac_no,combo_share_type,txt_sh_type,name,details,hidvar;

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getAcType() {
		return acType;
	}

	public void setAcType(String acType) {
		this.acType = acType;
	}

	public String getAc_no() {
		return ac_no;
	}

	public void setAc_no(String ac_no) {
		this.ac_no = ac_no;
	}

	public String getCombo_share_type() {
		return combo_share_type;
	}

	public void setCombo_share_type(String combo_share_type) {
		this.combo_share_type = combo_share_type;
	}

	public String getTxt_sh_type() {
		return txt_sh_type;
	}

	public void setTxt_sh_type(String txt_sh_type) {
		this.txt_sh_type = txt_sh_type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getHidvar() {
		return hidvar;
	}

	public void setHidvar(String hidvar) {
		this.hidvar = hidvar;
	}
   
}
