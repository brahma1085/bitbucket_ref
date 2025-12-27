package com.scb.pd.forms;

import org.apache.struts.action.ActionForm;

/**
 * Created by IntelliJ IDEA.
 * User: Suraj
 * Date: Nov 14, 2007
 * Time: 1:26:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class SelfActypeForm extends ActionForm {
            String SelfActype=null;

    public String getSelfActype() {
        return SelfActype;
    }

    public void setSelfActype(String selfActype) {
        SelfActype = selfActype;
    }
}
