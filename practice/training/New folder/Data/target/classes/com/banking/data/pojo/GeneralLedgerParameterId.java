package com.banking.data.pojo;
// default package



/**
 * GeneralLedgerParameterId entity. @author MyEclipse Persistence Tools
 */

public class GeneralLedgerParameterId  implements java.io.Serializable {


    // Fields    

     private Integer acType;
     private Integer code;
     private String keyDesc;
     private Integer glCode;
     private String glType;
     private String deTml;
     private String deUser;
     private String deDate;


    // Constructors

    /** default constructor */
    public GeneralLedgerParameterId() {
    }

    
    /** full constructor */
    public GeneralLedgerParameterId(Integer acType, Integer code, String keyDesc, Integer glCode, String glType, String deTml, String deUser, String deDate) {
        this.acType = acType;
        this.code = code;
        this.keyDesc = keyDesc;
        this.glCode = glCode;
        this.glType = glType;
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

    public Integer getCode() {
        return this.code;
    }
    
    public void setCode(Integer code) {
        this.code = code;
    }

    public String getKeyDesc() {
        return this.keyDesc;
    }
    
    public void setKeyDesc(String keyDesc) {
        this.keyDesc = keyDesc;
    }

    public Integer getGlCode() {
        return this.glCode;
    }
    
    public void setGlCode(Integer glCode) {
        this.glCode = glCode;
    }

    public String getGlType() {
        return this.glType;
    }
    
    public void setGlType(String glType) {
        this.glType = glType;
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
		 if ( !(other instanceof GeneralLedgerParameterId) ) return false;
		 GeneralLedgerParameterId castOther = ( GeneralLedgerParameterId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getCode()==castOther.getCode()) || ( this.getCode()!=null && castOther.getCode()!=null && this.getCode().equals(castOther.getCode()) ) )
 && ( (this.getKeyDesc()==castOther.getKeyDesc()) || ( this.getKeyDesc()!=null && castOther.getKeyDesc()!=null && this.getKeyDesc().equals(castOther.getKeyDesc()) ) )
 && ( (this.getGlCode()==castOther.getGlCode()) || ( this.getGlCode()!=null && castOther.getGlCode()!=null && this.getGlCode().equals(castOther.getGlCode()) ) )
 && ( (this.getGlType()==castOther.getGlType()) || ( this.getGlType()!=null && castOther.getGlType()!=null && this.getGlType().equals(castOther.getGlType()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getCode() == null ? 0 : this.getCode().hashCode() );
         result = 37 * result + ( getKeyDesc() == null ? 0 : this.getKeyDesc().hashCode() );
         result = 37 * result + ( getGlCode() == null ? 0 : this.getGlCode().hashCode() );
         result = 37 * result + ( getGlType() == null ? 0 : this.getGlType().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         return result;
   }   





}