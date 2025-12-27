package com.banking.data.pojo;
// default package



/**
 * PSLnMstId entity. @author MyEclipse Persistence Tools
 */

public class PSLnMstId  implements java.io.Serializable {


    // Fields    

     private String lnAcNo;
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
    public PSLnMstId() {
    }

    
    /** full constructor */
    public PSLnMstId(String lnAcNo, String deUser, String deTml, String deDate, String deTime, String veUser, String veTml, String veDate, String veTime) {
        this.lnAcNo = lnAcNo;
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

    public String getLnAcNo() {
        return this.lnAcNo;
    }
    
    public void setLnAcNo(String lnAcNo) {
        this.lnAcNo = lnAcNo;
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
		 if ( !(other instanceof PSLnMstId) ) return false;
		 PSLnMstId castOther = ( PSLnMstId ) other; 
         
		 return ( (this.getLnAcNo()==castOther.getLnAcNo()) || ( this.getLnAcNo()!=null && castOther.getLnAcNo()!=null && this.getLnAcNo().equals(castOther.getLnAcNo()) ) )
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
         
         result = 37 * result + ( getLnAcNo() == null ? 0 : this.getLnAcNo().hashCode() );
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