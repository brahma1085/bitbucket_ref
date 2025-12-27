package com.scb.cm.forms;

import java.io.Serializable;

import org.apache.struts.action.ActionForm;

public class AddressForm extends ActionForm implements Serializable  {
	private String sub,hid,udate,residential,communication,office,city,state,country,mobile,email,pageId;
	private String comcity, comstate, comcountry ,offcity, offstate, offcountry;


	private int cid,pin,phno,phstd,fax,faxstd,compin,offpin,offphno,comphno;
	public String getUdate() {
		return udate;
	}
	public void setUdate(String udate) {
		this.udate = udate;
	}
	public String getResidential() {
		return residential;
	}
	public void setResidential(String residential) {
		this.residential = residential;
	}
	public String getCommunication() {
		return communication;
	}
	public void setCommunication(String communication) {
		this.communication = communication;
	}
	public String getOffice() {
		return office;
	}
	public void setOffice(String office) {
		this.office = office;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public int getPin() {
		return pin;
	}
	public void setPin(int pin) {
		this.pin = pin;
	}
	public int getPhno() {
		return phno;
	}
	public void setPhno(int phno) {
		this.phno = phno;
	}
	public int getPhstd() {
		return phstd;
	}
	public void setPhstd(int phstd) {
		this.phstd = phstd;
	}
	public int getFax() {
		return fax;
	}
	public void setFax(int fax) {
		this.fax = fax;
	}
	public int getFaxstd() {
		return faxstd;
	}
	public void setFaxstd(int faxstd) {
		this.faxstd = faxstd;
	}
	public String getHid() {
		return hid;
	}
	public void setHid(String hid) {
		this.hid = hid;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getComcity() {
		return comcity;
	}
	public void setComcity(String comcity) {
		this.comcity = comcity;
	}
	public String getComstate() {
		return comstate;
	}
	public void setComstate(String comstate) {
		this.comstate = comstate;
	}
	public String getComcountry() {
		return comcountry;
	}
	public void setComcountry(String comcountry) {
		this.comcountry = comcountry;
	}
	public String getOffcity() {
		return offcity;
	}
	public void setOffcity(String offcity) {
		this.offcity = offcity;
	}
	public String getOffstate() {
		return offstate;
	}
	public void setOffstate(String offstate) {
		this.offstate = offstate;
	}
	public String getOffcountry() {
		return offcountry;
	}
	public void setOffcountry(String offcountry) {
		this.offcountry = offcountry;
	}
	public int getCompin() {
		return compin;
	}
	public void setCompin(int compin) {
		this.compin = compin;
	}
	public int getOffpin() {
		return offpin;
	}
	public void setOffpin(int offpin) {
		this.offpin = offpin;
	}
	public int getOffphno() {
		return offphno;
	}
	public void setOffphno(int offphno) {
		this.offphno = offphno;
	}
	public int getComphno() {
		return comphno;
	}
	public void setComphno(int comphno) {
		this.comphno = comphno;
	}



	}