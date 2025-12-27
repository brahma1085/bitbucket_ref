package com.scb.cm.actions;

import java.io.File;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;

/**
 * Created by IntelliJ IDEA.
 * User: swetha
 * Date: Nov 16, 2007
 * Time: 1:56:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class CustomerDetailForm extends ValidatorForm{
	public FormFile theFile;
    private String pageId,name,lastname,midname,salutation,fathername;
   
    
    private String mothername,marital,spousename,nation,religion;
    private String sex,caste,scst,dob,categories,issuedate,expirydate,occupation,sector,pan,addrestype,country,state,city,mailid,addrproof,Nameproof;
    private int intro,passportno,pin,mobile,phnum,phnum1,faxno;
    private String pvt_sector;
    String forward;
    
    
    
	public String getForward() {
		return forward;
	}
	public void setForward(String forward) {
		this.forward = forward;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		System.out.println(this.name);
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getMidname() {
		return midname;
	}
	public void setMidname(String midname) {
		this.midname = midname;
	}
	public String getSalutation() {
		return salutation;
	}
	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}
	public String getFathername() {
		return fathername;
	}
	public void setFathername(String fathername) {
		this.fathername = fathername;
	}
	
	public String getMothername() {
		return mothername;
	}
	public void setMothername(String mothername) {
		this.mothername = mothername;
	}
	public String getMarital() {
		return marital;
	}
	public void setMarital(String marital) {
		this.marital = marital;
	}
	public String getSpousename() {
		return spousename;
	}
	public void setSpousename(String spousename) {
		this.spousename = spousename;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getReligion() {
		return religion;
	}
	public void setReligion(String religion) {
		this.religion = religion;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCaste() {
		return caste;
	}
	public void setCaste(String caste) {
		this.caste = caste;
	}
	public String getScst() {
		return scst;
	}
	public void setScst(String scst) {
		this.scst = scst;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getCategories() {
		return categories;
	}
	public void setCategories(String categories) {
		this.categories = categories;
	}
	public String getIssuedate() {
		return issuedate;
	}
	public void setIssuedate(String issuedate) {
		this.issuedate = issuedate;
	}
	public String getExpirydate() {
		return expirydate;
	}
	public void setExpirydate(String expirydate) {
		this.expirydate = expirydate;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getAddrestype() {
		return addrestype;
	}
	public void setAddrestype(String addrestype) {
		this.addrestype = addrestype;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getMailid() {
		return mailid;
	}
	public void setMailid(String mailid) {
		this.mailid = mailid;
	}
	public String getAddrproof() {
		return addrproof;
	}
	public void setAddrproof(String addrproof) {
		this.addrproof = addrproof;
	}
	public String getNameproof() {
		return Nameproof;
	}
	public void setNameproof(String nameproof) {
		Nameproof = nameproof;
	}
	public int getIntro() {
		return intro;
	}
	public void setIntro(int intro) {
		this.intro = intro;
	}
	public int getPassportno() {
		return passportno;
	}
	public void setPassportno(int passportno) {
		this.passportno = passportno;
	}
	public int getPin() {
		return pin;
	}
	public void setPin(int pin) {
		this.pin = pin;
	}
	public int getMobile() {
		return mobile;
	}
	public void setMobile(int mobile) {
		this.mobile = mobile;
	}
	public int getPhnum() {
		return phnum;
	}
	public void setPhnum(int phnum) {
		this.phnum = phnum;
	}
	public int getPhnum1() {
		return phnum1;
	}
	public void setPhnum1(int phnum1) {
		this.phnum1 = phnum1;
	}
	public int getFaxno() {
		return faxno;
	}
	public void setFaxno(int faxno) {
		this.faxno = faxno;
	}
	public String getPvt_sector() {
		return pvt_sector;
	}
	public void setPvt_sector(String pvt_sector) {
		this.pvt_sector = pvt_sector;
	}
	public FormFile getTheFile() {
		return theFile;
	}
	public void setTheFile(FormFile theFile) {
		this.theFile = theFile;
	}

   }
