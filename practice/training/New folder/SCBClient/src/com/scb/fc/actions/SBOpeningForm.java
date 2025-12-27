package com.scb.fc.actions;

import org.apache.struts.action.ActionForm;

/**
 * Created by IntelliJ IDEA.
 * User: ${Sreenivas}
 * Date: Nov 19, 2007
 * Time: 1:23:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class SBOpeningForm extends ActionForm {
    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    private String cid;
    
}
