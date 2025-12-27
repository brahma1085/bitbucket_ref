package com.scb.pd.actions;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Suraj
 * Date: Nov 20, 2007
 * Time: 3:50:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class PersonalDetails implements Serializable {
    String cust_id;
     String dob=null;
        String sex=null;
        String age=null;

    public String getCust_id() {
        return cust_id;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
