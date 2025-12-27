package com.banking.data.pojo;
// default package



/**
 * CustomerIdId entity. @author MyEclipse Persistence Tools
 */

public class CustomerIdId  implements java.io.Serializable {


    // Fields    

     private String acTy;
     private Integer acNo;
     private Integer cid;
     private String purpose;
     private Integer nameKey;
     private Integer addrKey;
     private String refAcTy;
     private Integer refAcNo;
     private String backup;
     private String deTml;
     private Integer deUser;
     private String deDate;
     private String deTime;


    // Constructors

    /** default constructor */
    public CustomerIdId() {
    }

    
    /** full constructor */
    public CustomerIdId(String acTy, Integer acNo, Integer cid, String purpose, Integer nameKey, Integer addrKey, String refAcTy, Integer refAcNo, String backup, String deTml, Integer deUser, String deDate, String deTime) {
        this.acTy = acTy;
        this.acNo = acNo;
        this.cid = cid;
        this.purpose = purpose;
        this.nameKey = nameKey;
        this.addrKey = addrKey;
        this.refAcTy = refAcTy;
        this.refAcNo = refAcNo;
        this.backup = backup;
        this.deTml = deTml;
        this.deUser = deUser;
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

    public Integer getAcNo() {
        return this.acNo;
    }
    
    public void setAcNo(Integer acNo) {
        this.acNo = acNo;
    }

    public Integer getCid() {
        return this.cid;
    }
    
    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getPurpose() {
        return this.purpose;
    }
    
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Integer getNameKey() {
        return this.nameKey;
    }
    
    public void setNameKey(Integer nameKey) {
        this.nameKey = nameKey;
    }

    public Integer getAddrKey() {
        return this.addrKey;
    }
    
    public void setAddrKey(Integer addrKey) {
        this.addrKey = addrKey;
    }

    public String getRefAcTy() {
        return this.refAcTy;
    }
    
    public void setRefAcTy(String refAcTy) {
        this.refAcTy = refAcTy;
    }

    public Integer getRefAcNo() {
        return this.refAcNo;
    }
    
    public void setRefAcNo(Integer refAcNo) {
        this.refAcNo = refAcNo;
    }

    public String getBackup() {
        return this.backup;
    }
    
    public void setBackup(String backup) {
        this.backup = backup;
    }

    public String getDeTml() {
        return this.deTml;
    }
    
    public void setDeTml(String deTml) {
        this.deTml = deTml;
    }

    public Integer getDeUser() {
        return this.deUser;
    }
    
    public void setDeUser(Integer deUser) {
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
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof CustomerIdId) ) return false;
		 CustomerIdId castOther = ( CustomerIdId ) other; 
         
		 return ( (this.getAcTy()==castOther.getAcTy()) || ( this.getAcTy()!=null && castOther.getAcTy()!=null && this.getAcTy().equals(castOther.getAcTy()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getCid()==castOther.getCid()) || ( this.getCid()!=null && castOther.getCid()!=null && this.getCid().equals(castOther.getCid()) ) )
 && ( (this.getPurpose()==castOther.getPurpose()) || ( this.getPurpose()!=null && castOther.getPurpose()!=null && this.getPurpose().equals(castOther.getPurpose()) ) )
 && ( (this.getNameKey()==castOther.getNameKey()) || ( this.getNameKey()!=null && castOther.getNameKey()!=null && this.getNameKey().equals(castOther.getNameKey()) ) )
 && ( (this.getAddrKey()==castOther.getAddrKey()) || ( this.getAddrKey()!=null && castOther.getAddrKey()!=null && this.getAddrKey().equals(castOther.getAddrKey()) ) )
 && ( (this.getRefAcTy()==castOther.getRefAcTy()) || ( this.getRefAcTy()!=null && castOther.getRefAcTy()!=null && this.getRefAcTy().equals(castOther.getRefAcTy()) ) )
 && ( (this.getRefAcNo()==castOther.getRefAcNo()) || ( this.getRefAcNo()!=null && castOther.getRefAcNo()!=null && this.getRefAcNo().equals(castOther.getRefAcNo()) ) )
 && ( (this.getBackup()==castOther.getBackup()) || ( this.getBackup()!=null && castOther.getBackup()!=null && this.getBackup().equals(castOther.getBackup()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) )
 && ( (this.getDeTime()==castOther.getDeTime()) || ( this.getDeTime()!=null && castOther.getDeTime()!=null && this.getDeTime().equals(castOther.getDeTime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcTy() == null ? 0 : this.getAcTy().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getCid() == null ? 0 : this.getCid().hashCode() );
         result = 37 * result + ( getPurpose() == null ? 0 : this.getPurpose().hashCode() );
         result = 37 * result + ( getNameKey() == null ? 0 : this.getNameKey().hashCode() );
         result = 37 * result + ( getAddrKey() == null ? 0 : this.getAddrKey().hashCode() );
         result = 37 * result + ( getRefAcTy() == null ? 0 : this.getRefAcTy().hashCode() );
         result = 37 * result + ( getRefAcNo() == null ? 0 : this.getRefAcNo().hashCode() );
         result = 37 * result + ( getBackup() == null ? 0 : this.getBackup().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         result = 37 * result + ( getDeTime() == null ? 0 : this.getDeTime().hashCode() );
         return result;
   }   





}