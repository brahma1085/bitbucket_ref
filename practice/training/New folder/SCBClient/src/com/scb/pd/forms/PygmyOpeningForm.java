package com.scb.pd.forms;

import org.apache.struts.action.ActionForm;


/**
 * Created by IntelliJ IDEA.
 * User: sangeetha
 * Date: Nov 29, 2007
 * Time: 4:56:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class PygmyOpeningForm extends ActionForm {
	  String validations,Error;
      String pgactypeno;
      String pgno;
      String custid;
      String inagenttypeno;
      String intagno;
      String agname;
      String opdate;
      String paymode;
      String pageid;
      String tabPaneHeading;
      String defaultTab ;
      String forward,ClearButton,flag,delButton,value;
      String sysdate;
     NomineeDetailsForm nomform=new NomineeDetailsForm();
     private String  nompageid,hidval,cidis;
     private String acno,actype,nomname,dob,gender,address,rel_ship,percentage,nomforward,issuedate,has_ac,cid,nomvalidations;

    
	public String getSysdate() {
		return sysdate;
	}

	public void setSysdate(String sysdate) {
		this.sysdate = sysdate;
	}

	public String getNompageid() {
		return nompageid;
	}

	public void setNompageid(String nompageid) {
		this.nompageid = nompageid;
	}

	public String getHidval() {
		return hidval;
	}

	public void setHidval(String hidval) {
		this.hidval = hidval;
	}

	public String getCidis() {
		return cidis;
	}

	public void setCidis(String cidis) {
		this.cidis = cidis;
	}

	public String getAcno() {
		return acno;
	}

	public void setAcno(String acno) {
		this.acno = acno;
	}

	public String getActype() {
		return actype;
	}

	public void setActype(String actype) {
		this.actype = actype;
	}

	public String getNomname() {
		return nomname;
	}

	public void setNomname(String nomname) {
		this.nomname = nomname;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRel_ship() {
		return rel_ship;
	}

	public void setRel_ship(String relShip) {
		rel_ship = relShip;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public String getNomforward() {
		return nomforward;
	}

	public void setNomforward(String nomforward) {
		this.nomforward = nomforward;
	}

	public String getIssuedate() {
		return issuedate;
	}

	public void setIssuedate(String issuedate) {
		this.issuedate = issuedate;
	}

	public String getHas_ac() {
		return has_ac;
	}

	public void setHas_ac(String hasAc) {
		has_ac = hasAc;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getNomvalidations() {
		return nomvalidations;
	}

	public void setNomvalidations(String nomvalidations) {
		this.nomvalidations = nomvalidations;
	}

	public NomineeDetailsForm getNomform() {
		return nomform;
	}

	public void setNomform(NomineeDetailsForm nomform) {
		this.nomform = nomform;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
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

	public String getPayno() {
        return payno;
    }

    public void setPayno(String payno) {
        this.payno = payno;
    }

    String payactypeno;
      String payno;
      String name;
      String pygdetails;
     

    public String getPgno() {
        return pgno;
    }

    public void setPgno(String pgno) {
        this.pgno = pgno;
    }

    public String getIntagno() {
        return intagno;
    }

    public void setIntagno(String intagno) {
        this.intagno = intagno;
    }


    public String getPgactypeno() {
        return pgactypeno;
    }

    public void setPgactypeno(String pgactypeno) {
        this.pgactypeno = pgactypeno;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public String getInagenttypeno() {
        return inagenttypeno;
    }

    public void setInagenttypeno(String inagenttypeno) {
        this.inagenttypeno = inagenttypeno;
    }

    public String getAgname() {
        return agname;
    }

    public void setAgname(String agname) {
        this.agname = agname;
    }

    public String getOpdate() {
        return opdate;
    }

    public void setOpdate(String opdate) {
        this.opdate = opdate;
    }

    public String getPaymode() {
        return paymode;
    }

    public void setPaymode(String paymode) {
        this.paymode = paymode;
    }

    public String getPayactypeno() {
        return payactypeno;
    }

    public void setPayactypeno(String payactypeno) {
        this.payactypeno = payactypeno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPygdetails() {
        return pygdetails;
    }

    public void setPygdetails(String pygdetails) {
        this.pygdetails = pygdetails;
    }


	public String getPageid() {
		return pageid;
	}

	public void setPageid(String pageid) {
		this.pageid = pageid;
	}

	public String getClearButton() {
		return ClearButton;
	}

	public void setClearButton(String clearButton) {
		ClearButton = clearButton;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getValidations() {
		return validations;
	}

	public void setValidations(String validations) {
		this.validations = validations;
	}

	public String getDelButton() {
		return delButton;
	}

	public void setDelButton(String delButton) {
		this.delButton = delButton;
	}

	public String getError() {
		return Error;
	}

	public void setError(String error) {
		Error = error;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	

	
}
