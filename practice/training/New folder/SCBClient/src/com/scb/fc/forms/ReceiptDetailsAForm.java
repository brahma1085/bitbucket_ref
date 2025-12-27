package com.scb.fc.forms;

import org.apache.struts.action.ActionForm;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Dec 7, 2007
 * Time: 1:36:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReceiptDetailsAForm extends ActionForm {
    private String scrollNum;
    private String date;
    private String name;

    public String getScrollNum() {
        return scrollNum;
    }

    public void setScrollNum(String scrollNum) {
        this.scrollNum = scrollNum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    private String amount;
}
