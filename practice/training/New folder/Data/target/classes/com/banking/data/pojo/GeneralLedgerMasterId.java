package com.banking.data.pojo;
// default package

import java.util.Date;


/**
 * GeneralLedgerMasterId entity. @author MyEclipse Persistence Tools
 */

public class GeneralLedgerMasterId  implements java.io.Serializable {


    // Fields    

     private String glType;
     private Integer glCode;
     private String glName;
     private String schType;
     private String glStatus;
     private String normalCd;
     private Date fromDate;
     private Date toDate;
     private String deTml;
     private String deUser;
     private String deDate;


    // Constructors

    /** default constructor */
    public GeneralLedgerMasterId() {
    }

	/** minimal constructor */
    public GeneralLedgerMasterId(Integer glCode) {
        this.glCode = glCode;
    }
    
    /** full constructor */
    public GeneralLedgerMasterId(String glType, Integer glCode, String glName, String schType, String glStatus, String normalCd, Date fromDate, Date toDate, String deTml, String deUser, String deDate) {
        this.glType = glType;
        this.glCode = glCode;
        this.glName = glName;
        this.schType = schType;
        this.glStatus = glStatus;
        this.normalCd = normalCd;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
    }

   
    // Property accessors

    public String getGlType() {
        return this.glType;
    }
    
    public void setGlType(String glType) {
        this.glType = glType;
    }

    public Integer getGlCode() {
        return this.glCode;
    }
    
    public void setGlCode(Integer glCode) {
        this.glCode = glCode;
    }

    public String getGlName() {
        return this.glName;
    }
    
    public void setGlName(String glName) {
        this.glName = glName;
    }

    public String getSchType() {
        return this.schType;
    }
    
    public void setSchType(String schType) {
        this.schType = schType;
    }

    public String getGlStatus() {
        return this.glStatus;
    }
    
    public void setGlStatus(String glStatus) {
        this.glStatus = glStatus;
    }

    public String getNormalCd() {
        return this.normalCd;
    }
    
    public void setNormalCd(String normalCd) {
        this.normalCd = normalCd;
    }

    public Date getFromDate() {
        return this.fromDate;
    }
    
    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return this.toDate;
    }
    
    public void setToDate(Date toDate) {
        this.toDate = toDate;
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
		 if ( !(other instanceof GeneralLedgerMasterId) ) return false;
		 GeneralLedgerMasterId castOther = ( GeneralLedgerMasterId ) other; 
         
		 return ( (this.getGlType()==castOther.getGlType()) || ( this.getGlType()!=null && castOther.getGlType()!=null && this.getGlType().equals(castOther.getGlType()) ) )
 && ( (this.getGlCode()==castOther.getGlCode()) || ( this.getGlCode()!=null && castOther.getGlCode()!=null && this.getGlCode().equals(castOther.getGlCode()) ) )
 && ( (this.getGlName()==castOther.getGlName()) || ( this.getGlName()!=null && castOther.getGlName()!=null && this.getGlName().equals(castOther.getGlName()) ) )
 && ( (this.getSchType()==castOther.getSchType()) || ( this.getSchType()!=null && castOther.getSchType()!=null && this.getSchType().equals(castOther.getSchType()) ) )
 && ( (this.getGlStatus()==castOther.getGlStatus()) || ( this.getGlStatus()!=null && castOther.getGlStatus()!=null && this.getGlStatus().equals(castOther.getGlStatus()) ) )
 && ( (this.getNormalCd()==castOther.getNormalCd()) || ( this.getNormalCd()!=null && castOther.getNormalCd()!=null && this.getNormalCd().equals(castOther.getNormalCd()) ) )
 && ( (this.getFromDate()==castOther.getFromDate()) || ( this.getFromDate()!=null && castOther.getFromDate()!=null && this.getFromDate().equals(castOther.getFromDate()) ) )
 && ( (this.getToDate()==castOther.getToDate()) || ( this.getToDate()!=null && castOther.getToDate()!=null && this.getToDate().equals(castOther.getToDate()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getGlType() == null ? 0 : this.getGlType().hashCode() );
         result = 37 * result + ( getGlCode() == null ? 0 : this.getGlCode().hashCode() );
         result = 37 * result + ( getGlName() == null ? 0 : this.getGlName().hashCode() );
         result = 37 * result + ( getSchType() == null ? 0 : this.getSchType().hashCode() );
         result = 37 * result + ( getGlStatus() == null ? 0 : this.getGlStatus().hashCode() );
         result = 37 * result + ( getNormalCd() == null ? 0 : this.getNormalCd().hashCode() );
         result = 37 * result + ( getFromDate() == null ? 0 : this.getFromDate().hashCode() );
         result = 37 * result + ( getToDate() == null ? 0 : this.getToDate().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         return result;
   }   





}