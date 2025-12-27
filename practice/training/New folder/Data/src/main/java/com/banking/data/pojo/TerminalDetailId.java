package com.banking.data.pojo;
// default package



/**
 * TerminalDetailId entity. @author MyEclipse Persistence Tools
 */

public class TerminalDetailId  implements java.io.Serializable {


    // Fields    

     private String tmlCode;
     private String tmlDesc;
     private Double maxTrnAmt;
     private String ipAddress;
     private Integer status;
     private String deTml;
     private String deUser;
     private String deDate;
     private String tmlType;


    // Constructors

    /** default constructor */
    public TerminalDetailId() {
    }

    
    /** full constructor */
    public TerminalDetailId(String tmlCode, String tmlDesc, Double maxTrnAmt, String ipAddress, Integer status, String deTml, String deUser, String deDate, String tmlType) {
        this.tmlCode = tmlCode;
        this.tmlDesc = tmlDesc;
        this.maxTrnAmt = maxTrnAmt;
        this.ipAddress = ipAddress;
        this.status = status;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
        this.tmlType = tmlType;
    }

   
    // Property accessors

    public String getTmlCode() {
        return this.tmlCode;
    }
    
    public void setTmlCode(String tmlCode) {
        this.tmlCode = tmlCode;
    }

    public String getTmlDesc() {
        return this.tmlDesc;
    }
    
    public void setTmlDesc(String tmlDesc) {
        this.tmlDesc = tmlDesc;
    }

    public Double getMaxTrnAmt() {
        return this.maxTrnAmt;
    }
    
    public void setMaxTrnAmt(Double maxTrnAmt) {
        this.maxTrnAmt = maxTrnAmt;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }
    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getStatus() {
        return this.status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
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

    public String getTmlType() {
        return this.tmlType;
    }
    
    public void setTmlType(String tmlType) {
        this.tmlType = tmlType;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof TerminalDetailId) ) return false;
		 TerminalDetailId castOther = ( TerminalDetailId ) other; 
         
		 return ( (this.getTmlCode()==castOther.getTmlCode()) || ( this.getTmlCode()!=null && castOther.getTmlCode()!=null && this.getTmlCode().equals(castOther.getTmlCode()) ) )
 && ( (this.getTmlDesc()==castOther.getTmlDesc()) || ( this.getTmlDesc()!=null && castOther.getTmlDesc()!=null && this.getTmlDesc().equals(castOther.getTmlDesc()) ) )
 && ( (this.getMaxTrnAmt()==castOther.getMaxTrnAmt()) || ( this.getMaxTrnAmt()!=null && castOther.getMaxTrnAmt()!=null && this.getMaxTrnAmt().equals(castOther.getMaxTrnAmt()) ) )
 && ( (this.getIpAddress()==castOther.getIpAddress()) || ( this.getIpAddress()!=null && castOther.getIpAddress()!=null && this.getIpAddress().equals(castOther.getIpAddress()) ) )
 && ( (this.getStatus()==castOther.getStatus()) || ( this.getStatus()!=null && castOther.getStatus()!=null && this.getStatus().equals(castOther.getStatus()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) )
 && ( (this.getTmlType()==castOther.getTmlType()) || ( this.getTmlType()!=null && castOther.getTmlType()!=null && this.getTmlType().equals(castOther.getTmlType()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getTmlCode() == null ? 0 : this.getTmlCode().hashCode() );
         result = 37 * result + ( getTmlDesc() == null ? 0 : this.getTmlDesc().hashCode() );
         result = 37 * result + ( getMaxTrnAmt() == null ? 0 : this.getMaxTrnAmt().hashCode() );
         result = 37 * result + ( getIpAddress() == null ? 0 : this.getIpAddress().hashCode() );
         result = 37 * result + ( getStatus() == null ? 0 : this.getStatus().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         result = 37 * result + ( getTmlType() == null ? 0 : this.getTmlType().hashCode() );
         return result;
   }   





}