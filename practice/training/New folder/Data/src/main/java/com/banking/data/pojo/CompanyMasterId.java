package com.banking.data.pojo;
// default package



/**
 * CompanyMasterId entity. @author MyEclipse Persistence Tools
 */

public class CompanyMasterId  implements java.io.Serializable {


    // Fields    

     private Integer compCode;
     private String compName;
     private String acType;
     private Integer acNo;
     private String deTml;
     private String deUser;
     private String deDtTime;
     private String veTml;
     private String veUser;
     private String veDtTime;


    // Constructors

    /** default constructor */
    public CompanyMasterId() {
    }

    
    /** full constructor */
    public CompanyMasterId(Integer compCode, String compName, String acType, Integer acNo, String deTml, String deUser, String deDtTime, String veTml, String veUser, String veDtTime) {
        this.compCode = compCode;
        this.compName = compName;
        this.acType = acType;
        this.acNo = acNo;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDtTime = deDtTime;
        this.veTml = veTml;
        this.veUser = veUser;
        this.veDtTime = veDtTime;
    }

   
    // Property accessors

    public Integer getCompCode() {
        return this.compCode;
    }
    
    public void setCompCode(Integer compCode) {
        this.compCode = compCode;
    }

    public String getCompName() {
        return this.compName;
    }
    
    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getAcType() {
        return this.acType;
    }
    
    public void setAcType(String acType) {
        this.acType = acType;
    }

    public Integer getAcNo() {
        return this.acNo;
    }
    
    public void setAcNo(Integer acNo) {
        this.acNo = acNo;
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

    public String getDeDtTime() {
        return this.deDtTime;
    }
    
    public void setDeDtTime(String deDtTime) {
        this.deDtTime = deDtTime;
    }

    public String getVeTml() {
        return this.veTml;
    }
    
    public void setVeTml(String veTml) {
        this.veTml = veTml;
    }

    public String getVeUser() {
        return this.veUser;
    }
    
    public void setVeUser(String veUser) {
        this.veUser = veUser;
    }

    public String getVeDtTime() {
        return this.veDtTime;
    }
    
    public void setVeDtTime(String veDtTime) {
        this.veDtTime = veDtTime;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof CompanyMasterId) ) return false;
		 CompanyMasterId castOther = ( CompanyMasterId ) other; 
         
		 return ( (this.getCompCode()==castOther.getCompCode()) || ( this.getCompCode()!=null && castOther.getCompCode()!=null && this.getCompCode().equals(castOther.getCompCode()) ) )
 && ( (this.getCompName()==castOther.getCompName()) || ( this.getCompName()!=null && castOther.getCompName()!=null && this.getCompName().equals(castOther.getCompName()) ) )
 && ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDtTime()==castOther.getDeDtTime()) || ( this.getDeDtTime()!=null && castOther.getDeDtTime()!=null && this.getDeDtTime().equals(castOther.getDeDtTime()) ) )
 && ( (this.getVeTml()==castOther.getVeTml()) || ( this.getVeTml()!=null && castOther.getVeTml()!=null && this.getVeTml().equals(castOther.getVeTml()) ) )
 && ( (this.getVeUser()==castOther.getVeUser()) || ( this.getVeUser()!=null && castOther.getVeUser()!=null && this.getVeUser().equals(castOther.getVeUser()) ) )
 && ( (this.getVeDtTime()==castOther.getVeDtTime()) || ( this.getVeDtTime()!=null && castOther.getVeDtTime()!=null && this.getVeDtTime().equals(castOther.getVeDtTime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getCompCode() == null ? 0 : this.getCompCode().hashCode() );
         result = 37 * result + ( getCompName() == null ? 0 : this.getCompName().hashCode() );
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDtTime() == null ? 0 : this.getDeDtTime().hashCode() );
         result = 37 * result + ( getVeTml() == null ? 0 : this.getVeTml().hashCode() );
         result = 37 * result + ( getVeUser() == null ? 0 : this.getVeUser().hashCode() );
         result = 37 * result + ( getVeDtTime() == null ? 0 : this.getVeDtTime().hashCode() );
         return result;
   }   





}