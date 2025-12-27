package com.banking.data.pojo;
// default package



/**
 * PSBNMstId entity. @author MyEclipse Persistence Tools
 */

public class PSBNMstId  implements java.io.Serializable {


    // Fields    

     private String bnAcNo;
     private String deUser;
     private String deTml;
     private String deDate;
     private String deTime;
     private String veUser;
     private String veTml;
     private String veDate;
     private String veTime;
     private String bnAcTy;


    // Constructors

    /** default constructor */
    public PSBNMstId() {
    }

    
    /** full constructor */
    public PSBNMstId(String bnAcNo, String deUser, String deTml, String deDate, String deTime, String veUser, String veTml, String veDate, String veTime, String bnAcTy) {
        this.bnAcNo = bnAcNo;
        this.deUser = deUser;
        this.deTml = deTml;
        this.deDate = deDate;
        this.deTime = deTime;
        this.veUser = veUser;
        this.veTml = veTml;
        this.veDate = veDate;
        this.veTime = veTime;
        this.bnAcTy = bnAcTy;
    }

   
    // Property accessors

    public String getBnAcNo() {
        return this.bnAcNo;
    }
    
    public void setBnAcNo(String bnAcNo) {
        this.bnAcNo = bnAcNo;
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

    public String getBnAcTy() {
        return this.bnAcTy;
    }
    
    public void setBnAcTy(String bnAcTy) {
        this.bnAcTy = bnAcTy;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof PSBNMstId) ) return false;
		 PSBNMstId castOther = ( PSBNMstId ) other; 
         
		 return ( (this.getBnAcNo()==castOther.getBnAcNo()) || ( this.getBnAcNo()!=null && castOther.getBnAcNo()!=null && this.getBnAcNo().equals(castOther.getBnAcNo()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) )
 && ( (this.getDeTime()==castOther.getDeTime()) || ( this.getDeTime()!=null && castOther.getDeTime()!=null && this.getDeTime().equals(castOther.getDeTime()) ) )
 && ( (this.getVeUser()==castOther.getVeUser()) || ( this.getVeUser()!=null && castOther.getVeUser()!=null && this.getVeUser().equals(castOther.getVeUser()) ) )
 && ( (this.getVeTml()==castOther.getVeTml()) || ( this.getVeTml()!=null && castOther.getVeTml()!=null && this.getVeTml().equals(castOther.getVeTml()) ) )
 && ( (this.getVeDate()==castOther.getVeDate()) || ( this.getVeDate()!=null && castOther.getVeDate()!=null && this.getVeDate().equals(castOther.getVeDate()) ) )
 && ( (this.getVeTime()==castOther.getVeTime()) || ( this.getVeTime()!=null && castOther.getVeTime()!=null && this.getVeTime().equals(castOther.getVeTime()) ) )
 && ( (this.getBnAcTy()==castOther.getBnAcTy()) || ( this.getBnAcTy()!=null && castOther.getBnAcTy()!=null && this.getBnAcTy().equals(castOther.getBnAcTy()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getBnAcNo() == null ? 0 : this.getBnAcNo().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         result = 37 * result + ( getDeTime() == null ? 0 : this.getDeTime().hashCode() );
         result = 37 * result + ( getVeUser() == null ? 0 : this.getVeUser().hashCode() );
         result = 37 * result + ( getVeTml() == null ? 0 : this.getVeTml().hashCode() );
         result = 37 * result + ( getVeDate() == null ? 0 : this.getVeDate().hashCode() );
         result = 37 * result + ( getVeTime() == null ? 0 : this.getVeTime().hashCode() );
         result = 37 * result + ( getBnAcTy() == null ? 0 : this.getBnAcTy().hashCode() );
         return result;
   }   





}