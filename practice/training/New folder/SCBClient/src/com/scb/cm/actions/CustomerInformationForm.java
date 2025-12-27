package com.scb.cm.actions;



import java.io.Serializable;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;
import com.scb.cm.forms.*;
 
 

public class CustomerInformationForm extends ActionForm implements Serializable{
	private String cid_first,name,midname,lastname,salutation,fathername,mothername,marital,spousename,nation,religion,dob,sex,caste,scst,intro, mailid,faxno,state,city,pin,addrproof,mobile,phnum1,nameproof,passportno,issuedate,expirydate,occupation,pvt_sector,pan,addrestype,addressarea,country,pageId,clear,check_passport;
	String buttonvalues,button,passportdetail,passport_detail,txt_guardate,txt_guarcourtno,txt_gursex,txt_guaraddress,txt_gurtype,txt_guarname,ver_cid;
	private int custid;
	private int categories,cid;
	private FormFile theFile,secfile;
	private String cid_fi,combo_addresstype,addressarea2,clear_val,custtype,value,cid_verified,cid_notver,clear_value,totalclear,forward,instname;
	
	Customerform custform=new Customerform();
	
	SucessForm sucessform=new SucessForm();
	
	
	public SucessForm getSucessform() {
		return sucessform;
	}
	public void setSucessform(SucessForm sucessform) {
		this.sucessform = sucessform;
	}
	public FormFile getTheFile() {
		return theFile;
	}
	public void setTheFile(FormFile theFile) {
		this.theFile = theFile;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	} 
	public String getMidname() {
		return midname;
	}
	public void setMidname(String midname) {
		this.midname = midname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getSalutation() {
		return salutation;
	}
	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}
	
	public int getCategories() {
		return categories;
	}
	public void setCategories(int categories) {
		this.categories = categories;
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
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
	public String getPvt_sector() {
		return pvt_sector;
	}
	public void setPvt_sector(String pvt_sector) {
		this.pvt_sector = pvt_sector;
	}
	public String getAddrestype() {
		return addrestype;
	}
	public void setAddrestype(String addrestype) {
		this.addrestype = addrestype;
	}
	public String getAddrproof() {
		return addrproof;
	}
	public void setAddrproof(String addrproof) {
		this.addrproof = addrproof;
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
	public String getNameproof() {
		return nameproof;
	}
	public void setNameproof(String nameproof) {
		this.nameproof = nameproof;
	}
	public String getMailid() {
		return mailid;
	}
	public void setMailid(String mailid) {
		this.mailid = mailid;
	}
	public String getFaxno() {
		return faxno;
	}
	public void setFaxno(String faxno) {
		this.faxno = faxno;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getPhnum1() {
		return phnum1;
	}
	public void setPhnum1(String phnum1) {
		this.phnum1 = phnum1;
	}

	public String getPassportno() {
		return passportno;
	}
	public void setPassportno(String passportno) {
		this.passportno = passportno;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getAddressarea() {
		return addressarea;
	}
	public void setAddressarea(String addressarea) {
		this.addressarea = addressarea;
	}
	public Customerform getCustform() {
		return custform;
	}
	public void setCustform(Customerform custform) {
		this.custform = custform;
	}
	public String getButtonvalues() {
		return buttonvalues;
	}
	public void setButtonvalues(String buttonvalues) {
		this.buttonvalues = buttonvalues;
	}
	public String getButton() {
		return button;
	}
	public void setButton(String button) {
		this.button = button;
	}
	public String getPassportdetail() {
		return passportdetail;
	}
	public void setPassportdetail(String passportdetail) {
		this.passportdetail = passportdetail;
	}
	public String getClear() {
		return clear;
	}
	public void setClear(String clear) {
		this.clear = clear;
	}
	public String getCheck_passport() {
		return check_passport;
	}
	public void setCheck_passport(String check_passport) {
		this.check_passport = check_passport;
	}
	public String getPassport_detail() {
		return passport_detail;
	}
	public void setPassport_detail(String passport_detail) {
		this.passport_detail = passport_detail;
	}
	public String getTxt_guardate() {
		return txt_guardate;
	}
	public void setTxt_guardate(String txt_guardate) {
		this.txt_guardate = txt_guardate;
	}
	public String getTxt_guarcourtno() {
		return txt_guarcourtno;
	}
	public void setTxt_guarcourtno(String txt_guarcourtno) {
		this.txt_guarcourtno = txt_guarcourtno;
	}
	public String getTxt_gursex() {
		return txt_gursex;
	}
	public void setTxt_gursex(String txt_gursex) {
		this.txt_gursex = txt_gursex;
	}
	public String getTxt_guaraddress() {
		return txt_guaraddress;
	}
	public void setTxt_guaraddress(String txt_guaraddress) {
		this.txt_guaraddress = txt_guaraddress;
	}
	public String getTxt_gurtype() {
		return txt_gurtype;
	}
	public void setTxt_gurtype(String txt_gurtype) {
		this.txt_gurtype = txt_gurtype;
	}
	public String getTxt_guarname() {
		return txt_guarname;
	}
	public void setTxt_guarname(String txt_guarname) {
		this.txt_guarname = txt_guarname;
	}
	public String getVer_cid() {
		return ver_cid;
	}
	public void setVer_cid(String ver_cid) {
		this.ver_cid = ver_cid;
	}
	public String getCombo_addresstype() {
		return combo_addresstype;
	}
	public void setCombo_addresstype(String combo_addresstype) {
		this.combo_addresstype = combo_addresstype;
	}
	public String getAddressarea2() {
		return addressarea2;
	}
	public void setAddressarea2(String addressarea2) {
		this.addressarea2 = addressarea2;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getClear_val() {
		return clear_val;
	}
	public void setClear_val(String clear_val) {
		this.clear_val = clear_val;
	}
	public FormFile getSecfile() {
		return secfile;
	}
	public void setSecfile(FormFile secfile) {
		this.secfile = secfile;
	}
	public String getCid_first() {
		return cid_first;
	}
	public void setCid_first(String cid_first) {
		this.cid_first = cid_first;
	}
	public String getCid_fi() {
		return cid_fi;
	}
	public void setCid_fi(String cid_fi) {
		this.cid_fi = cid_fi;
	}
	public int getCustid() {
		return custid;
	}
	public void setCustid(int custid) {
		this.custid = custid;
	}
	public String getCusttype() {
		return custtype;
	}
	public void setCusttype(String custtype) {
		this.custtype = custtype;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getCid_verified() {
		return cid_verified;
	}
	public void setCid_verified(String cid_verified) {
		this.cid_verified = cid_verified;
	}
	public String getCid_notver() {
		return cid_notver;
	}
	public void setCid_notver(String cid_notver) {
		this.cid_notver = cid_notver;
	}
	public String getClear_value() {
		return clear_value;
	}
	public void setClear_value(String clear_value) {
		this.clear_value = clear_value;
	}
	public String getTotalclear() {
		return totalclear;
	}
	public void setTotalclear(String totalclear) {
		this.totalclear = totalclear;
	}
	public String getForward() {
		return forward;
	}
	public void setForward(String forward) {
		this.forward = forward;
	}
	public String getInstname() {
		return instname;
	}
	public void setInstname(String instname) {
		this.instname = instname;
	}
	
}
