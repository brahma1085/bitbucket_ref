package com.banking.data.pojo;
// default package



/**
 * CustomerAddressId entity. @author MyEclipse Persistence Tools
 */

public class CustomerAddressId  implements java.io.Serializable {


    // Fields    

     private Integer cid;
     private Integer addrType;
     private String udate;
     private String address;
     private String city;
     private String state;
     private Integer pin;
     private String country;
     private Integer phno;
     private Integer phstd;
     private Integer fax;
     private Integer faxstd;
     private String mobile;
     private String email;
     private String deUser;
     private String deTml;
     private String deDate;


    // Constructors

    /** default constructor */
    public CustomerAddressId() {
    }

	/** minimal constructor */
    public CustomerAddressId(Integer addrType) {
        this.addrType = addrType;
    }
    
    /** full constructor */
    public CustomerAddressId(Integer cid, Integer addrType, String udate, String address, String city, String state, Integer pin, String country, Integer phno, Integer phstd, Integer fax, Integer faxstd, String mobile, String email, String deUser, String deTml, String deDate) {
        this.cid = cid;
        this.addrType = addrType;
        this.udate = udate;
        this.address = address;
        this.city = city;
        this.state = state;
        this.pin = pin;
        this.country = country;
        this.phno = phno;
        this.phstd = phstd;
        this.fax = fax;
        this.faxstd = faxstd;
        this.mobile = mobile;
        this.email = email;
        this.deUser = deUser;
        this.deTml = deTml;
        this.deDate = deDate;
    }

   
    // Property accessors

    public Integer getCid() {
        return this.cid;
    }
    
    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getAddrType() {
        return this.addrType;
    }
    
    public void setAddrType(Integer addrType) {
        this.addrType = addrType;
    }

    public String getUdate() {
        return this.udate;
    }
    
    public void setUdate(String udate) {
        this.udate = udate;
    }

    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return this.city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return this.state;
    }
    
    public void setState(String state) {
        this.state = state;
    }

    public Integer getPin() {
        return this.pin;
    }
    
    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public String getCountry() {
        return this.country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getPhno() {
        return this.phno;
    }
    
    public void setPhno(Integer phno) {
        this.phno = phno;
    }

    public Integer getPhstd() {
        return this.phstd;
    }
    
    public void setPhstd(Integer phstd) {
        this.phstd = phstd;
    }

    public Integer getFax() {
        return this.fax;
    }
    
    public void setFax(Integer fax) {
        this.fax = fax;
    }

    public Integer getFaxstd() {
        return this.faxstd;
    }
    
    public void setFaxstd(Integer faxstd) {
        this.faxstd = faxstd;
    }

    public String getMobile() {
        return this.mobile;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeUser() {
        return this.deUser;
    }
    
    public void setDeUser(String deUser) {
        this.deUser = deUser;
    }

    public String getDeTml() {
        return this.deTml;
    }
    
    public void setDeTml(String deTml) {
        this.deTml = deTml;
    }

    public String getDeDate() {
        return this.deDate;
    }
    
    public void setDeDate(String deDate) {
        this.deDate = deDate;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof CustomerAddressId) ) return false;
		 CustomerAddressId castOther = ( CustomerAddressId ) other; 
         
		 return ( (this.getCid()==castOther.getCid()) || ( this.getCid()!=null && castOther.getCid()!=null && this.getCid().equals(castOther.getCid()) ) )
 && ( (this.getAddrType()==castOther.getAddrType()) || ( this.getAddrType()!=null && castOther.getAddrType()!=null && this.getAddrType().equals(castOther.getAddrType()) ) )
 && ( (this.getUdate()==castOther.getUdate()) || ( this.getUdate()!=null && castOther.getUdate()!=null && this.getUdate().equals(castOther.getUdate()) ) )
 && ( (this.getAddress()==castOther.getAddress()) || ( this.getAddress()!=null && castOther.getAddress()!=null && this.getAddress().equals(castOther.getAddress()) ) )
 && ( (this.getCity()==castOther.getCity()) || ( this.getCity()!=null && castOther.getCity()!=null && this.getCity().equals(castOther.getCity()) ) )
 && ( (this.getState()==castOther.getState()) || ( this.getState()!=null && castOther.getState()!=null && this.getState().equals(castOther.getState()) ) )
 && ( (this.getPin()==castOther.getPin()) || ( this.getPin()!=null && castOther.getPin()!=null && this.getPin().equals(castOther.getPin()) ) )
 && ( (this.getCountry()==castOther.getCountry()) || ( this.getCountry()!=null && castOther.getCountry()!=null && this.getCountry().equals(castOther.getCountry()) ) )
 && ( (this.getPhno()==castOther.getPhno()) || ( this.getPhno()!=null && castOther.getPhno()!=null && this.getPhno().equals(castOther.getPhno()) ) )
 && ( (this.getPhstd()==castOther.getPhstd()) || ( this.getPhstd()!=null && castOther.getPhstd()!=null && this.getPhstd().equals(castOther.getPhstd()) ) )
 && ( (this.getFax()==castOther.getFax()) || ( this.getFax()!=null && castOther.getFax()!=null && this.getFax().equals(castOther.getFax()) ) )
 && ( (this.getFaxstd()==castOther.getFaxstd()) || ( this.getFaxstd()!=null && castOther.getFaxstd()!=null && this.getFaxstd().equals(castOther.getFaxstd()) ) )
 && ( (this.getMobile()==castOther.getMobile()) || ( this.getMobile()!=null && castOther.getMobile()!=null && this.getMobile().equals(castOther.getMobile()) ) )
 && ( (this.getEmail()==castOther.getEmail()) || ( this.getEmail()!=null && castOther.getEmail()!=null && this.getEmail().equals(castOther.getEmail()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getCid() == null ? 0 : this.getCid().hashCode() );
         result = 37 * result + ( getAddrType() == null ? 0 : this.getAddrType().hashCode() );
         result = 37 * result + ( getUdate() == null ? 0 : this.getUdate().hashCode() );
         result = 37 * result + ( getAddress() == null ? 0 : this.getAddress().hashCode() );
         result = 37 * result + ( getCity() == null ? 0 : this.getCity().hashCode() );
         result = 37 * result + ( getState() == null ? 0 : this.getState().hashCode() );
         result = 37 * result + ( getPin() == null ? 0 : this.getPin().hashCode() );
         result = 37 * result + ( getCountry() == null ? 0 : this.getCountry().hashCode() );
         result = 37 * result + ( getPhno() == null ? 0 : this.getPhno().hashCode() );
         result = 37 * result + ( getPhstd() == null ? 0 : this.getPhstd().hashCode() );
         result = 37 * result + ( getFax() == null ? 0 : this.getFax().hashCode() );
         result = 37 * result + ( getFaxstd() == null ? 0 : this.getFaxstd().hashCode() );
         result = 37 * result + ( getMobile() == null ? 0 : this.getMobile().hashCode() );
         result = 37 * result + ( getEmail() == null ? 0 : this.getEmail().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         return result;
   }   





}