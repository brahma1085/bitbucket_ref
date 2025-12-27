package SRC.COM.SUNRISE.UTILITY.VALUEOBJECT;

public class AddressObject {

	String address,city,state,country,pin,phno,fax,mobile,email,phstd,faxstd;
	int flag;
	boolean changed;

	public boolean isChanged(){return changed;}
	public void setChanged(boolean f){changed=f;}


	public String getAddress(){return address;}
	public String getCity(){return city;}
	public String getState(){return state;}
	public String getCountry(){return country;}
	public String getPin(){return pin;}
	public String getPhno(){return phno;}
	public String getPhStd(){return phstd;}
	public int getFlag(){return flag;}

	public String getFax(){return fax;}
	public String getFaxStd(){return faxstd;}
	public String getMobile(){return mobile;}
	public String getEmail(){return email;}

	public void setAddress(String address){this.address=address;}
	public void setCity(String city){this.city=city;}
	public void setState(String state){this.state=state;}
	public void setCountry(String country){this.country=country;}
	public void setPin(String pin){this.pin=pin;}
	public void setPhno(String phno){this.phno=phno;}
	public void setPhStd(String phno){this.phstd=phno;}
	public void setFlag(int flag){this.flag=flag;}


	public void setFax(String fax){this.fax=fax;}
	public void setFaxStd(String fax){this.faxstd=fax;}
	public void setMobile(String mobile){this.mobile=mobile;}
	public void setEmail(String email){this.email=email;}

	
}
