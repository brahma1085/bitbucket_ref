package com.banking.data.pojo;
// default package



/**
 * GeneralLedgerTransactionTypeId entity. @author MyEclipse Persistence Tools
 */

public class GeneralLedgerTransactionTypeId  implements java.io.Serializable {


    // Fields    

     private Integer acType;
     private String glType;
     private Integer glCode;
     private String trnType;
     private String trnDesc;
     private String deTml;
     private String deUser;
     private String deDate;


    // Constructors

    /** default constructor */
    public GeneralLedgerTransactionTypeId() {
    }

    
    /** full constructor */
    public GeneralLedgerTransactionTypeId(Integer acType, String glType, Integer glCode, String trnType, String trnDesc, String deTml, String deUser, String deDate) {
        this.acType = acType;
        this.glType = glType;
        this.glCode = glCode;
        this.trnType = trnType;
        this.trnDesc = trnDesc;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
    }

   
    // Property accessors

    public Integer getAcType() {
        return this.acType;
    }
    
    public void setAcType(Integer acType) {
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

    public String getTrnDesc() {
        return this.trnDesc;
    }
    
    public void setTrnDesc(String trnDesc) {
        this.trnDesc = trnDesc;
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
		 if ( !(other instanceof GeneralLedgerTransactionTypeId) ) return false;
		 GeneralLedgerTransactionTypeId castOther = ( GeneralLedgerTransactionTypeId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getGlType()==castOther.getGlType()) || ( this.getGlType()!=null && castOther.getGlType()!=null && this.getGlType().equals(castOther.getGlType()) ) )
 && ( (this.getGlCode()==castOther.getGlCode()) || ( this.getGlCode()!=null && castOther.getGlCode()!=null && this.getGlCode().equals(castOther.getGlCode()) ) )
 && ( (this.getTrnType()==castOther.getTrnType()) || ( this.getTrnType()!=null && castOther.getTrnType()!=null && this.getTrnType().equals(castOther.getTrnType()) ) )
 && ( (this.getTrnDesc()==castOther.getTrnDesc()) || ( this.getTrnDesc()!=null && castOther.getTrnDesc()!=null && this.getTrnDesc().equals(castOther.getTrnDesc()) ) )
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
         result = 37 * result + ( getTrnDesc() == null ? 0 : this.getTrnDesc().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         return result;
   }   





}