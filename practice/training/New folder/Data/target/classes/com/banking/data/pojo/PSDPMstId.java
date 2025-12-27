package com.banking.data.pojo;
// default package



/**
 * PSDPMstId entity. @author MyEclipse Persistence Tools
 */

public class PSDPMstId  implements java.io.Serializable {


    // Fields    

     private String dpAcNo;
     private String intrAcNo;
     private String intrAcTy;
     private String acCatgry;
     private String dpType;
     private String deUser;
     private String deTml;
     private String deDate;
     private String deTime;
     private String veUser;
     private String veTml;
     private String veDate;
     private String veTime;


    // Constructors

    /** default constructor */
    public PSDPMstId() {
    }

    
    /** full constructor */
    public PSDPMstId(String dpAcNo, String intrAcNo, String intrAcTy, String acCatgry, String dpType, String deUser, String deTml, String deDate, String deTime, String veUser, String veTml, String veDate, String veTime) {
        this.dpAcNo = dpAcNo;
        this.intrAcNo = intrAcNo;
        this.intrAcTy = intrAcTy;
        this.acCatgry = acCatgry;
        this.dpType = dpType;
        this.deUser = deUser;
        this.deTml = deTml;
        this.deDate = deDate;
        this.deTime = deTime;
        this.veUser = veUser;
        this.veTml = veTml;
        this.veDate = veDate;
        this.veTime = veTime;
    }

   
    // Property accessors

    public String getDpAcNo() {
        return this.dpAcNo;
    }
    
    public void setDpAcNo(String dpAcNo) {
        this.dpAcNo = dpAcNo;
    }

    public String getIntrAcNo() {
        return this.intrAcNo;
    }
    
    public void setIntrAcNo(String intrAcNo) {
        this.intrAcNo = intrAcNo;
    }

    public String getIntrAcTy() {
        return this.intrAcTy;
    }
    
    public void setIntrAcTy(String intrAcTy) {
        this.intrAcTy = intrAcTy;
    }

    public String getAcCatgry() {
        return this.acCatgry;
    }
    
    public void setAcCatgry(String acCatgry) {
        this.acCatgry = acCatgry;
    }

    public String getDpType() {
        return this.dpType;
    }
    
    public void setDpType(String dpType) {
        this.dpType = dpType;
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

    public String getDeTime() {
        return this.deTime;
    }
    
    public void setDeTime(String deTime) {
        this.deTime = deTime;
    }

    public String getVeUser() {
        return this.veUser;
    }
    
    public void setVeUser(String veUser) {
        this.veUser = veUser;
    }

    public String getVeTml() {
        return this.veTml;
    }
    
    public void setVeTml(String veTml) {
        this.veTml = veTml;
    }

    public String getVeDate() {
        return this.veDate;
    }
    
    public void setVeDate(String veDate) {
        this.veDate = veDate;
    }

    public String getVeTime() {
        return this.veTime;
    }
    
    public void setVeTime(String veTime) {
        this.veTime = veTime;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof PSDPMstId) ) return false;
		 PSDPMstId castOther = ( PSDPMstId ) other; 
         
		 return ( (this.getDpAcNo()==castOther.getDpAcNo()) || ( this.getDpAcNo()!=null && castOther.getDpAcNo()!=null && this.getDpAcNo().equals(castOther.getDpAcNo()) ) )
 && ( (this.getIntrAcNo()==castOther.getIntrAcNo()) || ( this.getIntrAcNo()!=null && castOther.getIntrAcNo()!=null && this.getIntrAcNo().equals(castOther.getIntrAcNo()) ) )
 && ( (this.getIntrAcTy()==castOther.getIntrAcTy()) || ( this.getIntrAcTy()!=null && castOther.getIntrAcTy()!=null && this.getIntrAcTy().equals(castOther.getIntrAcTy()) ) )
 && ( (this.getAcCatgry()==castOther.getAcCatgry()) || ( this.getAcCatgry()!=null && castOther.getAcCatgry()!=null && this.getAcCatgry().equals(castOther.getAcCatgry()) ) )
 && ( (this.getDpType()==castOther.getDpType()) || ( this.getDpType()!=null && castOther.getDpType()!=null && this.getDpType().equals(castOther.getDpType()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) )
 && ( (this.getDeTime()==castOther.getDeTime()) || ( this.getDeTime()!=null && castOther.getDeTime()!=null && this.getDeTime().equals(castOther.getDeTime()) ) )
 && ( (this.getVeUser()==castOther.getVeUser()) || ( this.getVeUser()!=null && castOther.getVeUser()!=null && this.getVeUser().equals(castOther.getVeUser()) ) )
 && ( (this.getVeTml()==castOther.getVeTml()) || ( this.getVeTml()!=null && castOther.getVeTml()!=null && this.getVeTml().equals(castOther.getVeTml()) ) )
 && ( (this.getVeDate()==castOther.getVeDate()) || ( this.getVeDate()!=null && castOther.getVeDate()!=null && this.getVeDate().equals(castOther.getVeDate()) ) )
 && ( (this.getVeTime()==castOther.getVeTime()) || ( this.getVeTime()!=null && castOther.getVeTime()!=null && this.getVeTime().equals(castOther.getVeTime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getDpAcNo() == null ? 0 : this.getDpAcNo().hashCode() );
         result = 37 * result + ( getIntrAcNo() == null ? 0 : this.getIntrAcNo().hashCode() );
         result = 37 * result + ( getIntrAcTy() == null ? 0 : this.getIntrAcTy().hashCode() );
         result = 37 * result + ( getAcCatgry() == null ? 0 : this.getAcCatgry().hashCode() );
         result = 37 * result + ( getDpType() == null ? 0 : this.getDpType().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         result = 37 * result + ( getDeTime() == null ? 0 : this.getDeTime().hashCode() );
         result = 37 * result + ( getVeUser() == null ? 0 : this.getVeUser().hashCode() );
         result = 37 * result + ( getVeTml() == null ? 0 : this.getVeTml().hashCode() );
         result = 37 * result + ( getVeDate() == null ? 0 : this.getVeDate().hashCode() );
         result = 37 * result + ( getVeTime() == null ? 0 : this.getVeTime().hashCode() );
         return result;
   }   





}