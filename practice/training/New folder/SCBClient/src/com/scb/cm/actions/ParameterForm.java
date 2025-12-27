package com.scb.cm.actions;

import org.apache.struts.action.ActionForm;

/*
 @ author SWETHA
 Project:Core Banking Project
  Date: Nov 6, 2007
  Time: 6:01:22 AM
 */
public class ParameterForm extends ActionForm {
  private String Addresstype;

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    private String pageId;

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    private String txt;

    private String comm;

    public String getComm() {
        return comm;
    }

    public void setComm(String comm) {
        this.comm = comm;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getResd() {
        return resd;
    }

    public void setResd(String resd) {
        this.resd = resd;
    }

    private String office;
    private String resd;

    public String getAddresstype() {
        return Addresstype;
    }

    public void setAddresstype(String addresstype) {
        Addresstype = addresstype;
    }

      }





