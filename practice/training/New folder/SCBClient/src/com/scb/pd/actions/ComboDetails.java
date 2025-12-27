package com.scb.pd.actions;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Suraj
 * Date: Nov 20, 2007
 * Time: 3:35:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class ComboDetails implements Serializable {
    String Details;

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }
}
