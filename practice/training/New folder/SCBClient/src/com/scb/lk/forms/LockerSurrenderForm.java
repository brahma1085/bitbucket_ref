package com.scb.lk.forms;

import org.apache.struts.action.ActionForm;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: Nov 29, 2007
 * Time: 6:55:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class LockerSurrenderForm extends ActionForm {
		String txt_acType;
        String txt_acNo;
        String txt_lockType;
        String txt_lockNo;
        String txt_allotDate;
        String txt_expiryDate;
        String txt_rentUpto;
        String txt_totRent;
        String txt_rentAmnt;
        String select_opInstr;
        String select_details;
        String submit;
        String forward;
        String tabPaneHeading;
        String defaultTab;
        private String val,clearButton;
        
  
        
        
        
        //alert indicators
        String acntFound,acntVeri,acntClosed,acntFreez,disableBut,validateFlag;

        
        
    public String getDisableBut() {
			return disableBut;
		}

		public void setDisableBut(String disableBut) {
			this.disableBut = disableBut;
		}

	public String getAcntFound() {
			return acntFound;
		}

		public void setAcntFound(String acntFound) {
			this.acntFound = acntFound;
		}

		public String getAcntVeri() {
			return acntVeri;
		}

		public void setAcntVeri(String acntVeri) {
			this.acntVeri = acntVeri;
		}

		public String getAcntClosed() {
			return acntClosed;
		}

		public void setAcntClosed(String acntClosed) {
			this.acntClosed = acntClosed;
		}

		public String getAcntFreez() {
			return acntFreez;
		}

		public void setAcntFreez(String acntFreez) {
			this.acntFreez = acntFreez;
		}

	public String getVal() {
			return val;
		}

		public void setVal(String val) {
			this.val = val;
		}

	public String getForward() {
			return forward;
		}

		public void setForward(String forward) {
			this.forward = forward;
		}

	public String getSubmit() {
			return submit;
		}

		public void setSubmit(String submit) {
			this.submit = submit;
		}

	public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    String  pageId;


    public String getTxt_acType() {
        return txt_acType;
    }

    public void setTxt_acType(String txt_acType) {
        this.txt_acType = txt_acType;
    }

    public String getTxt_acNo() {
        return txt_acNo;
    }

    public void setTxt_acNo(String txt_acNo) {
        this.txt_acNo = txt_acNo;
    }

    public String getTxt_lockType() {
        return txt_lockType;
    }

    public void setTxt_lockType(String txt_lockType) {
        this.txt_lockType = txt_lockType;
    }

    public String getTxt_lockNo() {
        return txt_lockNo;
    }

    public void setTxt_lockNo(String txt_lockNo) {
        this.txt_lockNo = txt_lockNo;
    }

    public String getTxt_allotDate() {
        return txt_allotDate;
    }

    public void setTxt_allotDate(String txt_allotDate) {
        this.txt_allotDate = txt_allotDate;
    }

    public String getTxt_expiryDate() {
        return txt_expiryDate;
    }

    public void setTxt_expiryDate(String txt_expiryDate) {
        this.txt_expiryDate = txt_expiryDate;
    }

    public String getTxt_rentUpto() {
        return txt_rentUpto;
    }

    public void setTxt_rentUpto(String txt_rentUpto) {
        this.txt_rentUpto = txt_rentUpto;
    }

    public String getTxt_totRent() {
        return txt_totRent;
    }

    public void setTxt_totRent(String txt_totRent) {
        this.txt_totRent = txt_totRent;
    }

    public String getTxt_rentAmnt() {
        return txt_rentAmnt;
    }

    public void setTxt_rentAmnt(String txt_rentAmnt) {
        this.txt_rentAmnt = txt_rentAmnt;
    }

    public String getSelect_opInstr() {
        return select_opInstr;
    }

    public void setSelect_opInstr(String select_opInstr) {
        this.select_opInstr = select_opInstr;
    }

    public String getSelect_details() {
        return select_details;
    }

    public void setSelect_details(String select_details) {
        this.select_details = select_details;
    }

	public String getValidateFlag() {
		return validateFlag;
	}

	public void setValidateFlag(String validateFlag) {
		this.validateFlag = validateFlag;
	}

	public String getClearButton() {
		return clearButton;
	}

	public void setClearButton(String clearButton) {
		this.clearButton = clearButton;
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


}
