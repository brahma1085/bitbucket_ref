package com.banking.data.pojo;
// default package



/**
 * HeadId entity. @author MyEclipse Persistence Tools
 */

public class HeadId  implements java.io.Serializable {


    // Fields    

     private String bankname;
     private String location;
     private Integer bankcode;
     private String bankAbbr;
     private String deTml;
     private String deUser;
     private String deDate;


    // Constructors

    /** default constructor */
    public HeadId() {
    }

    
    /** full constructor */
    public HeadId(String bankname, String location, Integer bankcode, String bankAbbr, String deTml, String deUser, String deDate) {
        this.bankname = bankname;
        this.location = location;
        this.bankcode = bankcode;
        this.bankAbbr = bankAbbr;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
    }

   
    // Property accessors

    public String getBankname() {
        return this.bankname;
    }
    
    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getLocation() {
        return this.location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getBankcode() {
        return this.bankcode;
    }
    
    public void setBankcode(Integer bankcode) {
        this.bankcode = bankcode;
    }

    public String getBankAbbr() {
        return this.bankAbbr;
    }
    
    public void setBankAbbr(String bankAbbr) {
        this.bankAbbr = bankAbbr;
    }

    public String getDeTml() {
        return this.deTml;
    }
    
    public void setDeTml(String deTml) {
        this.deTml = deTml;
    }

    public String getDeUser() {
        return this.deUser;
    }
    
    public void setDeUser(String deUser) {
        this.deUser = deUser;
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
		 if ( !(other instanceof HeadId) ) return false;
		 HeadId castOther = ( HeadId ) other; 
         
		 return ( (this.getBankname()==castOther.getBankname()) || ( this.getBankname()!=null && castOther.getBankname()!=null && this.getBankname().equals(castOther.getBankname()) ) )
 && ( (this.getLocation()==castOther.getLocation()) || ( this.getLocation()!=null && castOther.getLocation()!=null && this.getLocation().equals(castOther.getLocation()) ) )
 && ( (this.getBankcode()==castOther.getBankcode()) || ( this.getBankcode()!=null && castOther.getBankcode()!=null && this.getBankcode().equals(castOther.getBankcode()) ) )
 && ( (this.getBankAbbr()==castOther.getBankAbbr()) || ( this.getBankAbbr()!=null && castOther.getBankAbbr()!=null && this.getBankAbbr().equals(castOther.getBankAbbr()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getBankname() == null ? 0 : this.getBankname().hashCode() );
         result = 37 * result + ( getLocation() == null ? 0 : this.getLocation().hashCode() );
         result = 37 * result + ( getBankcode() == null ? 0 : this.getBankcode().hashCode() );
         result = 37 * result + ( getBankAbbr() == null ? 0 : this.getBankAbbr().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         return result;
   }   





}