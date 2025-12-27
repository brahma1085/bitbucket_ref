package com.scb.cm.actions;

import org.apache.struts.action.ActionForm;

import com.scb.cm.forms.Customerform;

/**
 * Created by IntelliJ IDEA.
 * User: user 
 * Date: Nov 16, 2007 
 * Time: 1:31:31 AM 
 * To change this template use File | Settings | File Templates.
 */
public class CustomerInstituteForm extends ActionForm {
 private String instname,contactpersonname,designation,introid,introid2,regno,pangir;
 private String pageId,addressarea;
 private String issuedate,expirydate,occupation,sector,pan,addrestype,country,state;
 private String intro,passportno,salutation;
 private String pvt_sector,buttonvalues,testing;
 String city,pin,mobile,phnum,phnum1,mailid,faxno,photo,sign,addrproof,Nameproof,submit,forward;
 String button;
 Customerform customer =new Customerform();
 
 
   
 public Customerform getCustomer() {
	return customer;
}

public void setCustomer(Customerform customer) {
	this.customer = customer;
}

public String getPageId() { 
        return pageId;
    }  

    public void setPageId(String pageId) {
        this.pageId = pageId;   
    }

    public String getInstname() {
        return instname;
    }

    public void setInstname(String instname) {
        this.instname = instname;
    }

    public String getContactpersonname() {
        return contactpersonname;
    }

    public void setContactpersonname(String contactpersonname) {
        this.contactpersonname = contactpersonname;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getIntroid() {
        return introid;
    }

    public void setIntroid(String introid) {
        this.introid = introid;
    }

    public String getIntroid2() {
        return introid2;
    }

    public void setIntroid2(String introid2) {
        this.introid2 = introid2;
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
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

    public String getPangir() {
        return pangir;
    }

    public void setPangir(String pangir) {
        this.pangir = pangir;
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

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhnum() {
        return phnum;
    }

    public void setPhnum(String phnum) {
        this.phnum = phnum;
    }

    public String getPhnum1() {
        return phnum1;
    }

    public void setPhnum1(String phnum1) {
        this.phnum1 = phnum1;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
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
    
	public String getPassportno() {
		return passportno;
	}

	public void setPassportno(String passportno) {
		this.passportno = passportno;
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

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getPvt_sector() {
		return pvt_sector;
	}

	public void setPvt_sector(String pvt_sector) {
		this.pvt_sector = pvt_sector;
	}

	public String getAddressarea() {
		return addressarea;
	}

	public void setAddressarea(String addressarea) {
		this.addressarea = addressarea;
	}

	public String getSubmit() {
		return submit;
	}

	public void setSubmit(String submit) {
		this.submit = submit;
	}

	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public String getButtonvalues() {
		return buttonvalues;
	}

	public void setButtonvalues(String buttonvalues) {
		this.buttonvalues = buttonvalues;
	}

	public String getForward() {
		System.out.println("in getting");
		return forward;
	}

	public void setForward(String forward) {
		System.out.println("in getting" +forward);
		this.forward = forward;
	}

	public String getButton() {
		return button;
	}

	public void setButton(String button) {
		System.out.println("button=="+button);
		this.button = button;
	}

	public String getTesting() {
		return testing;
	}

	public void setTesting(String testing) {
		this.testing = testing;
	}

	/*public String getForward() {
		System.out.println("inside get"); 
		return forward;
	}

	public void setForward(String forward) {
		System.out.println("forward in set"+forward);
		this.forward = forward;
	}*/

}
