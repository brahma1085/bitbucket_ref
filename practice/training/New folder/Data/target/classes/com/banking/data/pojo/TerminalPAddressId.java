package com.banking.data.pojo;
// default package



/**
 * TerminalPAddressId entity. @author MyEclipse Persistence Tools
 */

public class TerminalPAddressId  implements java.io.Serializable {


    // Fields    

     private String tmlNo;
     private String ipAddress;
     private String deUser;
     private String deTml;
     private String deDate;


    // Constructors

    /** default constructor */
    public TerminalPAddressId() {
    }

    
    /** full constructor */
    public TerminalPAddressId(String tmlNo, String ipAddress, String deUser, String deTml, String deDate) {
        this.tmlNo = tmlNo;
        this.ipAddress = ipAddress;
        this.deUser = deUser;
        this.deTml = deTml;
        this.deDate = deDate;
    }

   
    // Property accessors

    public String getTmlNo() {
        return this.tmlNo;
    }
    
    public void setTmlNo(String tmlNo) {
        this.tmlNo = tmlNo;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }
    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
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
		 if ( !(other instanceof TerminalPAddressId) ) return false;
		 TerminalPAddressId castOther = ( TerminalPAddressId ) other; 
         
		 return ( (this.getTmlNo()==castOther.getTmlNo()) || ( this.getTmlNo()!=null && castOther.getTmlNo()!=null && this.getTmlNo().equals(castOther.getTmlNo()) ) )
 && ( (this.getIpAddress()==castOther.getIpAddress()) || ( this.getIpAddress()!=null && castOther.getIpAddress()!=null && this.getIpAddress().equals(castOther.getIpAddress()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getTmlNo() == null ? 0 : this.getTmlNo().hashCode() );
         result = 37 * result + ( getIpAddress() == null ? 0 : this.getIpAddress().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         return result;
   }   





}