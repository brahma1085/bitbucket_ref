package com.scb.pd.forms;

import org.apache.struts.action.ActionForm;

/**
 * Created by IntelliJ IDEA.
 * User: Suraj
 * Date: Nov 19, 2007
 * Time: 12:06:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class PersonalForm extends ActionForm {
        String custid=null;
        String category=null;
        String subcat=null;
        String mailid=null;
        String scst=null;
        String address=null;
        String dob=null;
        String sex=null;
        String age=null;
        String occupation=null;

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getScst() {
        return scst;
    }

    public void setScst(String scst) {
        this.scst = scst;
    }

    public String getMailid() {
        return mailid;
    }

    public void setMailid(String mailid) {
        this.mailid = mailid;
    }

    public String getSubcat() {
        return subcat;
    }

    public void setSubcat(String subcat) {
        this.subcat = subcat;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }
}
