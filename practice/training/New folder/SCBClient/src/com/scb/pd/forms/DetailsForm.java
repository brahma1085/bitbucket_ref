package com.scb.pd.forms;

import org.apache.struts.action.ActionForm;

/**
 * Created by IntelliJ IDEA.
 * User: Suraj
 * Date: Nov 14, 2007
 * Time: 2:05:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class DetailsForm extends ActionForm {
    String Details=null;

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }
}
