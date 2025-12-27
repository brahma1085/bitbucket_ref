package com.scb.bk.forms;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: Nov 19, 2007
 * Time: 2:35:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class SIEntryComboElements implements Serializable {
    private String loan_option;

    public String getLoan_option() {
        return loan_option;
    }

    public void setLoan_option(String loan_option) {
        this.loan_option = loan_option;
    }
}
