package com.banking.data.pojo;
// default package



/**
 * GeneralLedgerPostId entity. @author MyEclipse Persistence Tools
 */

public class GeneralLedgerPostId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private String glType;
     private Integer glCode;
     private String trnType;
     private String crDr;
     private Integer multBy;
     private String deTml;
     private String deUser;
     private String deDate;


    // Constructors

    /** default constructor */
    public GeneralLedgerPostId() {
    }

    
    /** full constructor */
    public GeneralLedgerPostId(String acType, String glType, Integer glCode, String trnType, String crDr, Integer multBy, String deTml, String deUser, String deDate) {
        this.acType = acType;
        this.glType = glType;
        this.glCode = glCode;
        this.trnType = trnType;
        this.crDr = crDr;
        this.multBy = multBy;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
    }

   
    // Property accessors

    public String getAcType() {
        return this.acType;
    }
    
    public void setAcType(String acType) {
        this.acType = acType;
    }

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

    public String getTrnType() {
        return this.trnType;
    }
    
    public void setTrnType(String trnType) {
        this.trnType = trnType;
    }

    public String getCrDr() {
        return this.crDr;
    }
    
    public void setCrDr(String crDr) {
        this.crDr = crDr;
    }

    public Integer getMultBy() {
        return this.multBy;
    }
    
    public void setMultBy(Integer multBy) {
        this.multBy = multBy;
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
		 if ( !(other instanceof GeneralLedgerPostId) ) return false;
		 GeneralLedgerPostId castOther = ( GeneralLedgerPostId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getGlType()==castOther.getGlType()) || ( this.getGlType()!=null && castOther.getGlType()!=null && this.getGlType().equals(castOther.getGlType()) ) )
 && ( (this.getGlCode()==castOther.getGlCode()) || ( this.getGlCode()!=null && castOther.getGlCode()!=null && this.getGlCode().equals(castOther.getGlCode()) ) )
 && ( (this.getTrnType()==castOther.getTrnType()) || ( this.getTrnType()!=null && castOther.getTrnType()!=null && this.getTrnType().equals(castOther.getTrnType()) ) )
 && ( (this.getCrDr()==castOther.getCrDr()) || ( this.getCrDr()!=null && castOther.getCrDr()!=null && this.getCrDr().equals(castOther.getCrDr()) ) )
 && ( (this.getMultBy()==castOther.getMultBy()) || ( this.getMultBy()!=null && castOther.getMultBy()!=null && this.getMultBy().equals(castOther.getMultBy()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getGlType() == null ? 0 : this.getGlType().hashCode() );
         result = 37 * result + ( getGlCode() == null ? 0 : this.getGlCode().hashCode() );
         result = 37 * result + ( getTrnType() == null ? 0 : this.getTrnType().hashCode() );
         result = 37 * result + ( getCrDr() == null ? 0 : this.getCrDr().hashCode() );
         result = 37 * result + ( getMultBy() == null ? 0 : this.getMultBy().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         return result;
   }   





}