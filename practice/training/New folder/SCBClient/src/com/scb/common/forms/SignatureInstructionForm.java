package com.scb.common.forms;

import org.apache.struts.action.ActionForm;

import com.scb.fc.forms.SBOpeningActionForm;
import com.scb.pd.forms.AgentOpeningForm;
import com.scb.sh.forms.AllotmentForm;
/**
 * Created by IntelliJ IDEA.
 * User: sangeetha
 * Date: Dec 10, 2007
 * Time: 11:11:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class SignatureInstructionForm extends ActionForm {
    String cid;
    String acType;
    String acNum;
    String name;
    String sign;
    String tyop;
    AgentOpeningForm agentForm=new AgentOpeningForm();
    String signPageID;
    String signCombo;
    String signInfo;
    SBOpeningActionForm sbForm=new SBOpeningActionForm();
    String add;
    String delete;
    AllotmentForm shareForm=new AllotmentForm();

	public String getAdd() {
		return add;
	}

	public void setAdd(String add) {
		this.add = add;
	}

	public String getDelete() {
		return delete;
	}

	public void setDelete(String delete) {
		this.delete = delete;
	}

	public String getSignPageID() {
		return signPageID;
	}

	public void setSignPageID(String signPageID) {
		this.signPageID = signPageID;
		System.out.println("sp id");
	}

	public String getSignInfo() {
		return signInfo;
	}

	public void setSignInfo(String signInfo) {
		this.signInfo = signInfo;
		System.out.println("sign");
	}

	

	public AgentOpeningForm getAgentForm() {
		return agentForm;
	}

	public void setAgentForm(AgentOpeningForm agentForm) {
		this.agentForm = agentForm;
		System.out.println("agentf");
	}

	public String getCid() {
        return cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

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

    public String getTyop() {
        return tyop;
    }

    public void setTyop(String tyop) {
        this.tyop = tyop;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

	public String getSignCombo() {
		return signCombo;
	}

	public void setSignCombo(String signCombo) {
		this.signCombo = signCombo;
		System.out.println("signc");
	}

	public SBOpeningActionForm getSbForm() {
		return sbForm;
	}

	public void setSbForm(SBOpeningActionForm sbForm) {
		this.sbForm = sbForm;
	}

	public AllotmentForm getShareForm() {
		return shareForm;
	}

	public void setShareForm(AllotmentForm shareForm) {
		this.shareForm = shareForm;
	}


}
