package com.banking.data.pojo;
// default package



/**
 * PSACInstId entity. @author MyEclipse Persistence Tools
 */

public class PSACInstId  implements java.io.Serializable {


    // Fields    

     private String acTy;
     private String acNo;
     private String instrns;
     private String deUser;
     private String deTml;
     private String deDate;
     private String deTime;


    // Constructors

    /** default constructor */
    public PSACInstId() {
    }

    
    /** full constructor */
    public PSACInstId(String acTy, String acNo, String instrns, String deUser, String deTml, String deDate, String deTime) {
        this.acTy = acTy;
        this.acNo = acNo;
        this.instrns = instrns;
        this.deUser = deUser;
        this.deTml = deTml;
        this.deDate = deDate;
        this.deTime = deTime;
    }

   
    // Property accessors

    public String getAcTy() {
        return this.acTy;
    }
    
    public void setAcTy(String acTy) {
        this.acTy = acTy;
    }

    public String getAcNo() {
        return this.acNo;
    }
    
    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getInstrns() {
        return this.instrns;
    }
    
    public void setInstrns(String instrns) {
        this.instrns = instrns;
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
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof PSACInstId) ) return false;
		 PSACInstId castOther = ( PSACInstId ) other; 
         
		 return ( (this.getAcTy()==castOther.getAcTy()) || ( this.getAcTy()!=null && castOther.getAcTy()!=null && this.getAcTy().equals(castOther.getAcTy()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getInstrns()==castOther.getInstrns()) || ( this.getInstrns()!=null && castOther.getInstrns()!=null && this.getInstrns().equals(castOther.getInstrns()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) )
 && ( (this.getDeTime()==castOther.getDeTime()) || ( this.getDeTime()!=null && castOther.getDeTime()!=null && this.getDeTime().equals(castOther.getDeTime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcTy() == null ? 0 : this.getAcTy().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getInstrns() == null ? 0 : this.getInstrns().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         result = 37 * result + ( getDeTime() == null ? 0 : this.getDeTime().hashCode() );
         return result;
   }   





}