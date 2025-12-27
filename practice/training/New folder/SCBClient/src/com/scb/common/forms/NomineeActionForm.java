package com.scb.common.forms;

import org.apache.struts.action.ActionForm;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Dec 7, 2007
 * Time: 11:42:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class NomineeActionForm extends ActionForm {
	
	private String cid, name, dob, age, sex, address, relationship, percentage;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	
    
}
