package com.scb.common.help;

import general.Validations;

import java.util.Calendar;

import exceptions.DateFormatException;

/**
 * Created by IntelliJ IDEA.
 * User: sangeetha
 * Date: Dec 7, 2007
 * Time: 1:00:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class Date {

     public static String getSysDate() {
        Calendar c = Calendar.getInstance();
        try {
            return (Validations.checkDate(c.get(Calendar.DATE) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR)));
        } catch (DateFormatException e) {
                e.printStackTrace();
        }
        return null;
    }

    public static String getSysTime() {
            return (new java.util.Date().getHours() + ":" + new java.util.Date().getMinutes() + ":" + new java.util.Date().getSeconds());
    }
}
