package com.banking.data.pojo;
// default package



/**
 * PSSHMstId entity. @author MyEclipse Persistence Tools
 */

public class PSSHMstId  implements java.io.Serializable {


    // Fields    

     private String intrAcNo;
     private String intrAcTy;
     private String shareStat;
     private String sexCd;
     private String scSt;
     private String lnBrCd;
     private String deTml;
     private String deUser;
     private String deDate;
     private String deTime;
     private String veTml;
     private String veUser;
     private String veDate;
     private String veTime;
     private String shLfNo;


    // Constructors

    /** default constructor */
    public PSSHMstId() {
    }

    
    /** full constructor */
    public PSSHMstId(String intrAcNo, String intrAcTy, String shareStat, String sexCd, String scSt, String lnBrCd, String deTml, String deUser, String deDate, String deTime, String veTml, String veUser, String veDate, String veTime, String shLfNo) {
        this.intrAcNo = intrAcNo;
        this.intrAcTy = intrAcTy;
        this.shareStat = shareStat;
        this.sexCd = sexCd;
        this.scSt = scSt;
        this.lnBrCd = lnBrCd;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
        this.deTime = deTime;
        this.veTml = veTml;
        this.veUser = veUser;
        this.veDate = veDate;
        this.veTime = veTime;
        this.shLfNo = shLfNo;
    }

   
    // Property accessors

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

    public String getShareStat() {
        return this.shareStat;
    }
    
    public void setShareStat(String shareStat) {
        this.shareStat = shareStat;
    }

    public String getSexCd() {
        return this.sexCd;
    }
    
    public void setSexCd(String sexCd) {
        this.sexCd = sexCd;
    }

    public String getScSt() {
        return this.scSt;
    }
    
    public void setScSt(String scSt) {
        this.scSt = scSt;
    }

    public String getLnBrCd() {
        return this.lnBrCd;
    }
    
    public void setLnBrCd(String lnBrCd) {
        this.lnBrCd = lnBrCd;
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

    public String getDeTime() {
        return this.deTime;
    }
    
    public void setDeTime(String deTime) {
        this.deTime = deTime;
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

    public String getShLfNo() {
        return this.shLfNo;
    }
    
    public void setShLfNo(String shLfNo) {
        this.shLfNo = shLfNo;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof PSSHMstId) ) return false;
		 PSSHMstId castOther = ( PSSHMstId ) other; 
         
		 return ( (this.getIntrAcNo()==castOther.getIntrAcNo()) || ( this.getIntrAcNo()!=null && castOther.getIntrAcNo()!=null && this.getIntrAcNo().equals(castOther.getIntrAcNo()) ) )
 && ( (this.getIntrAcTy()==castOther.getIntrAcTy()) || ( this.getIntrAcTy()!=null && castOther.getIntrAcTy()!=null && this.getIntrAcTy().equals(castOther.getIntrAcTy()) ) )
 && ( (this.getShareStat()==castOther.getShareStat()) || ( this.getShareStat()!=null && castOther.getShareStat()!=null && this.getShareStat().equals(castOther.getShareStat()) ) )
 && ( (this.getSexCd()==castOther.getSexCd()) || ( this.getSexCd()!=null && castOther.getSexCd()!=null && this.getSexCd().equals(castOther.getSexCd()) ) )
 && ( (this.getScSt()==castOther.getScSt()) || ( this.getScSt()!=null && castOther.getScSt()!=null && this.getScSt().equals(castOther.getScSt()) ) )
 && ( (this.getLnBrCd()==castOther.getLnBrCd()) || ( this.getLnBrCd()!=null && castOther.getLnBrCd()!=null && this.getLnBrCd().equals(castOther.getLnBrCd()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) )
 && ( (this.getDeTime()==castOther.getDeTime()) || ( this.getDeTime()!=null && castOther.getDeTime()!=null && this.getDeTime().equals(castOther.getDeTime()) ) )
 && ( (this.getVeTml()==castOther.getVeTml()) || ( this.getVeTml()!=null && castOther.getVeTml()!=null && this.getVeTml().equals(castOther.getVeTml()) ) )
 && ( (this.getVeUser()==castOther.getVeUser()) || ( this.getVeUser()!=null && castOther.getVeUser()!=null && this.getVeUser().equals(castOther.getVeUser()) ) )
 && ( (this.getVeDate()==castOther.getVeDate()) || ( this.getVeDate()!=null && castOther.getVeDate()!=null && this.getVeDate().equals(castOther.getVeDate()) ) )
 && ( (this.getVeTime()==castOther.getVeTime()) || ( this.getVeTime()!=null && castOther.getVeTime()!=null && this.getVeTime().equals(castOther.getVeTime()) ) )
 && ( (this.getShLfNo()==castOther.getShLfNo()) || ( this.getShLfNo()!=null && castOther.getShLfNo()!=null && this.getShLfNo().equals(castOther.getShLfNo()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getIntrAcNo() == null ? 0 : this.getIntrAcNo().hashCode() );
         result = 37 * result + ( getIntrAcTy() == null ? 0 : this.getIntrAcTy().hashCode() );
         result = 37 * result + ( getShareStat() == null ? 0 : this.getShareStat().hashCode() );
         result = 37 * result + ( getSexCd() == null ? 0 : this.getSexCd().hashCode() );
         result = 37 * result + ( getScSt() == null ? 0 : this.getScSt().hashCode() );
         result = 37 * result + ( getLnBrCd() == null ? 0 : this.getLnBrCd().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         result = 37 * result + ( getDeTime() == null ? 0 : this.getDeTime().hashCode() );
         result = 37 * result + ( getVeTml() == null ? 0 : this.getVeTml().hashCode() );
         result = 37 * result + ( getVeUser() == null ? 0 : this.getVeUser().hashCode() );
         result = 37 * result + ( getVeDate() == null ? 0 : this.getVeDate().hashCode() );
         result = 37 * result + ( getVeTime() == null ? 0 : this.getVeTime().hashCode() );
         result = 37 * result + ( getShLfNo() == null ? 0 : this.getShLfNo().hashCode() );
         return result;
   }   





}