package com.scb.common.forms;

import org.apache.struts.action.ActionForm;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: Nov 15, 2007
 * Time: 12:17:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class PersonnelForm extends ActionForm {


    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getMailaddress() {
        return mailaddress;
    }

    public void setMailaddress(String mailaddress) {
        this.mailaddress = mailaddress;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getScst() {
        return scst;
    }

    public void setScst(String scst) {
        this.scst = scst;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    private String category;
    private String subcategory;
    private String mailaddress;
    private String scst;
    private String dob;
    private String sex;
    private String age,dep_amt,mat_amt,intRate,depacTyp,depacNum;
    private String occupation,acc_no,dep_date,mat_date,period_in_days,ir;

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    private String Address;


    public int getCustomer_id() {
        return Customer_id;
    }

    public void setCustomer_id(int customer_id) {
        Customer_id = customer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int Customer_id ;
    private String name;

	public String getAcc_no() {
		return acc_no;
	}

	public void setAcc_no(String acc_no) {
		this.acc_no = acc_no;
	}

	public String getDep_amt() {
		return dep_amt;
	}

	public void setDep_amt(String dep_amt) {
		this.dep_amt = dep_amt;
	}

	public String getMat_amt() {
		return mat_amt;
	}

	public void setMat_amt(String mat_amt) {
		this.mat_amt = mat_amt;
	}

	public String getIntRate() {
		return intRate;
	}

	public void setIntRate(String intRate) {
		this.intRate = intRate;
	}

	public String getDep_date() {
		return dep_date;
	}

	public void setDep_date(String dep_date) {
		this.dep_date = dep_date;
	}

	public String getMat_date() {
		return mat_date;
	}

	public void setMat_date(String mat_date) {
		this.mat_date = mat_date;
	}

	public String getPeriod_in_days() {
		return period_in_days;
	}

	public void setPeriod_in_days(String period_in_days) {
		this.period_in_days = period_in_days;
	}

	public String getIr() {
		return ir;
	}

	public void setIr(String ir) {
		this.ir = ir;
	}

	public String getDepacTyp() {
		return depacTyp;
	}

	public void setDepacTyp(String depacTyp) {
		this.depacTyp = depacTyp;
	}

	public String getDepacNum() {
		return depacNum;
	}

	public void setDepacNum(String depacNum) {
		this.depacNum = depacNum;
	}

}
