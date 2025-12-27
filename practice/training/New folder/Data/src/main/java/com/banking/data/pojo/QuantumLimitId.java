package com.banking.data.pojo;
// default package



/**
 * QuantumLimitId entity. @author MyEclipse Persistence Tools
 */

public class QuantumLimitId  implements java.io.Serializable {


    // Fields    

     private Integer modTy;
     private Integer srlNo;
     private String lmtHdg;
     private Integer frLmt;
     private Integer toLmt;
     private String deTml;
     private String deUser;
     private String deDate;
     private String deTime;
     private String backup;


    // Constructors

    /** default constructor */
    public QuantumLimitId() {
    }

    
    /** full constructor */
    public QuantumLimitId(Integer modTy, Integer srlNo, String lmtHdg, Integer frLmt, Integer toLmt, String deTml, String deUser, String deDate, String deTime, String backup) {
        this.modTy = modTy;
        this.srlNo = srlNo;
        this.lmtHdg = lmtHdg;
        this.frLmt = frLmt;
        this.toLmt = toLmt;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
        this.deTime = deTime;
        this.backup = backup;
    }

   
    // Property accessors

    public Integer getModTy() {
        return this.modTy;
    }
    
    public void setModTy(Integer modTy) {
        this.modTy = modTy;
    }

    public Integer getSrlNo() {
        return this.srlNo;
    }
    
    public void setSrlNo(Integer srlNo) {
        this.srlNo = srlNo;
    }

    public String getLmtHdg() {
        return this.lmtHdg;
    }
    
    public void setLmtHdg(String lmtHdg) {
        this.lmtHdg = lmtHdg;
    }

    public Integer getFrLmt() {
        return this.frLmt;
    }
    
    public void setFrLmt(Integer frLmt) {
        this.frLmt = frLmt;
    }

    public Integer getToLmt() {
        return this.toLmt;
    }
    
    public void setToLmt(Integer toLmt) {
        this.toLmt = toLmt;
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

    public String getBackup() {
        return this.backup;
    }
    
    public void setBackup(String backup) {
        this.backup = backup;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof QuantumLimitId) ) return false;
		 QuantumLimitId castOther = ( QuantumLimitId ) other; 
         
		 return ( (this.getModTy()==castOther.getModTy()) || ( this.getModTy()!=null && castOther.getModTy()!=null && this.getModTy().equals(castOther.getModTy()) ) )
 && ( (this.getSrlNo()==castOther.getSrlNo()) || ( this.getSrlNo()!=null && castOther.getSrlNo()!=null && this.getSrlNo().equals(castOther.getSrlNo()) ) )
 && ( (this.getLmtHdg()==castOther.getLmtHdg()) || ( this.getLmtHdg()!=null && castOther.getLmtHdg()!=null && this.getLmtHdg().equals(castOther.getLmtHdg()) ) )
 && ( (this.getFrLmt()==castOther.getFrLmt()) || ( this.getFrLmt()!=null && castOther.getFrLmt()!=null && this.getFrLmt().equals(castOther.getFrLmt()) ) )
 && ( (this.getToLmt()==castOther.getToLmt()) || ( this.getToLmt()!=null && castOther.getToLmt()!=null && this.getToLmt().equals(castOther.getToLmt()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) )
 && ( (this.getDeTime()==castOther.getDeTime()) || ( this.getDeTime()!=null && castOther.getDeTime()!=null && this.getDeTime().equals(castOther.getDeTime()) ) )
 && ( (this.getBackup()==castOther.getBackup()) || ( this.getBackup()!=null && castOther.getBackup()!=null && this.getBackup().equals(castOther.getBackup()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getModTy() == null ? 0 : this.getModTy().hashCode() );
         result = 37 * result + ( getSrlNo() == null ? 0 : this.getSrlNo().hashCode() );
         result = 37 * result + ( getLmtHdg() == null ? 0 : this.getLmtHdg().hashCode() );
         result = 37 * result + ( getFrLmt() == null ? 0 : this.getFrLmt().hashCode() );
         result = 37 * result + ( getToLmt() == null ? 0 : this.getToLmt().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         result = 37 * result + ( getDeTime() == null ? 0 : this.getDeTime().hashCode() );
         result = 37 * result + ( getBackup() == null ? 0 : this.getBackup().hashCode() );
         return result;
   }   





}