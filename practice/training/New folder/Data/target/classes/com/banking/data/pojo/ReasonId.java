package com.banking.data.pojo;
// default package



/**
 * ReasonId entity. @author MyEclipse Persistence Tools
 */

public class ReasonId  implements java.io.Serializable {


    // Fields    

     private Integer ctrlNo;
     private Integer reasonCd;
     private String deTml;
     private String deUser;
     private String deDtTime;
     private String veTml;
     private String veUser;
     private String veDtTime;


    // Constructors

    /** default constructor */
    public ReasonId() {
    }

    
    /** full constructor */
    public ReasonId(Integer ctrlNo, Integer reasonCd, String deTml, String deUser, String deDtTime, String veTml, String veUser, String veDtTime) {
        this.ctrlNo = ctrlNo;
        this.reasonCd = reasonCd;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDtTime = deDtTime;
        this.veTml = veTml;
        this.veUser = veUser;
        this.veDtTime = veDtTime;
    }

   
    // Property accessors

    public Integer getCtrlNo() {
        return this.ctrlNo;
    }
    
    public void setCtrlNo(Integer ctrlNo) {
        this.ctrlNo = ctrlNo;
    }

    public Integer getReasonCd() {
        return this.reasonCd;
    }
    
    public void setReasonCd(Integer reasonCd) {
        this.reasonCd = reasonCd;
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
		 if ( !(other instanceof ReasonId) ) return false;
		 ReasonId castOther = ( ReasonId ) other; 
         
		 return ( (this.getCtrlNo()==castOther.getCtrlNo()) || ( this.getCtrlNo()!=null && castOther.getCtrlNo()!=null && this.getCtrlNo().equals(castOther.getCtrlNo()) ) )
 && ( (this.getReasonCd()==castOther.getReasonCd()) || ( this.getReasonCd()!=null && castOther.getReasonCd()!=null && this.getReasonCd().equals(castOther.getReasonCd()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDtTime()==castOther.getDeDtTime()) || ( this.getDeDtTime()!=null && castOther.getDeDtTime()!=null && this.getDeDtTime().equals(castOther.getDeDtTime()) ) )
 && ( (this.getVeTml()==castOther.getVeTml()) || ( this.getVeTml()!=null && castOther.getVeTml()!=null && this.getVeTml().equals(castOther.getVeTml()) ) )
 && ( (this.getVeUser()==castOther.getVeUser()) || ( this.getVeUser()!=null && castOther.getVeUser()!=null && this.getVeUser().equals(castOther.getVeUser()) ) )
 && ( (this.getVeDtTime()==castOther.getVeDtTime()) || ( this.getVeDtTime()!=null && castOther.getVeDtTime()!=null && this.getVeDtTime().equals(castOther.getVeDtTime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getCtrlNo() == null ? 0 : this.getCtrlNo().hashCode() );
         result = 37 * result + ( getReasonCd() == null ? 0 : this.getReasonCd().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDtTime() == null ? 0 : this.getDeDtTime().hashCode() );
         result = 37 * result + ( getVeTml() == null ? 0 : this.getVeTml().hashCode() );
         result = 37 * result + ( getVeUser() == null ? 0 : this.getVeUser().hashCode() );
         result = 37 * result + ( getVeDtTime() == null ? 0 : this.getVeDtTime().hashCode() );
         return result;
   }   





}