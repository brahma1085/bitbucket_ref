package com.scb.pd.forms;

import java.io.Serializable;

import org.apache.struts.action.ActionForm;
import com.scb.common.forms.SignatureInstruction;

/**
 * Created by IntelliJ IDEA.
 * User: Suraj
 * Date: Nov 12, 2007
 * Time: 12:08:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class AgentOpeningForm  extends ActionForm implements Serializable{
		String validation=null;
         String agentcode=null;
        String agentnum=null;
        String agentname=null;
        String selfactypeno=null;
        String selfAcno=null;
        String selfname=null;
        String date=null;
        String jointactypeno=null;
        String jointAcno=null;
        String jointname=null;
        String introduceractypeno=null;
        String introducerAcno=null;
        String details=null;
        String pageid,defSIndex;
        String forward,value;
        String cid;
        String tabPaneHeading;
        String defaultTab,signIndex,defaultSignIndex;
        String sysdate;
        
       // SignatureInstructionForm siForm=new SignatureInstructionForm();
        
        
     public String getSignIndex() {
			return signIndex;
		}

		public void setSignIndex(String signIndex) {
			this.signIndex = signIndex;
		}
		
		

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

	public String getTabPaneHeading() {
			return tabPaneHeading;
		}

		public void setTabPaneHeading(String tabPaneHeading) {
			this.tabPaneHeading = tabPaneHeading;
		}

		public String getDefaultTab() {
			return defaultTab;
		}

		public void setDefaultTab(String defaultTab) {
			this.defaultTab = defaultTab;
		}

	public String getCid() {
			return cid;
		}

		public void setCid(String cid) {
			System.out.println("cid==="+cid);
			this.cid = cid;
		}

	public String getForward() {
			return forward;
		}

		public void setForward(String forward) {
			this.forward = forward;
		}

	public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    String flag;

    
    public String getPageid() {
		return pageid;
	}

	public void setPageid(String pageid) {
		this.pageid = pageid;
	}

	public String getAgentcode() {
        return agentcode;
    }

    public void setAgentcode(String agentcode) {
        this.agentcode = agentcode;
    }

    public String getAgentnum() {
        return agentnum;
    }

    public void setAgentnum(String agentnum) {
        this.agentnum = agentnum;
    }

    public String getAgentname() {
        return agentname;
    }

    public void setAgentname(String agentname) {
        this.agentname = agentname;
    }

    public String getSelfactypeno() {
        return selfactypeno;
    }

    public void setSelfactypeno(String selfactypeno) {
        this.selfactypeno = selfactypeno;
    }

    public String getSelfAcno() {
        return selfAcno;
    }

    public void setSelfAcno(String selfAcno) {
        this.selfAcno = selfAcno;
    }

    public String getSelfname() {
        return selfname;
    }

    public void setSelfname(String selfname) {
        this.selfname = selfname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getJointactypeno() {
        return jointactypeno;
    }

    public void setJointactypeno(String jointactypeno) {
        this.jointactypeno = jointactypeno;
    }

    public String getJointAcno() {
        return jointAcno;
    }

    public void setJointAcno(String jointAcno) {
        this.jointAcno = jointAcno;
    }

    public String getJointname() {
        return jointname;
    }

    public void setJointname(String jointname) {
        this.jointname = jointname;
    }

    public String getIntroduceractypeno() {
        return introduceractypeno;
    }

    public void setIntroduceractypeno(String introduceractypeno) {
        this.introduceractypeno = introduceractypeno;
    }

    public String getIntroducerAcno() {
        return introducerAcno;
    }

    public void setIntroducerAcno(String introducerAcno) {
        this.introducerAcno = introducerAcno;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

	public String getDefSIndex() {
		return defSIndex;
	}

	public void setDefSIndex(String defSIndex) {
		this.defSIndex = defSIndex;
	}

	public String getValidation() {
		return validation;
	}

	public void setValidation(String validation) {
		this.validation = validation;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/*public SignatureInstructionForm getSiForm() {
		return siForm;
	}

	public void setSiForm(SignatureInstructionForm siForm) {
		this.siForm = siForm;
	}*/
}

