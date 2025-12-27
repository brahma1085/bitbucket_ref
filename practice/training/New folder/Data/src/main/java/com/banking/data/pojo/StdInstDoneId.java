package com.banking.data.pojo;
// default package



/**
 * StdInstDoneId entity. @author MyEclipse Persistence Tools
 */

public class StdInstDoneId  implements java.io.Serializable {


    // Fields    

     private Integer siNo;
     private String trnDate;
     private String dueDat;
     private Double trnAmt;
     private Double prinAmt;
     private Double intAmt;
     private Double penalIntAmt;
     private Double otherAmt;
     private String intDate;
     private String completeInd;
     private String deTml;
     private String deUser;
     private String deDtTime;


    // Constructors

    /** default constructor */
    public StdInstDoneId() {
    }

    
    /** full constructor */
    public StdInstDoneId(Integer siNo, String trnDate, String dueDat, Double trnAmt, Double prinAmt, Double intAmt, Double penalIntAmt, Double otherAmt, String intDate, String completeInd, String deTml, String deUser, String deDtTime) {
        this.siNo = siNo;
        this.trnDate = trnDate;
        this.dueDat = dueDat;
        this.trnAmt = trnAmt;
        this.prinAmt = prinAmt;
        this.intAmt = intAmt;
        this.penalIntAmt = penalIntAmt;
        this.otherAmt = otherAmt;
        this.intDate = intDate;
        this.completeInd = completeInd;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDtTime = deDtTime;
    }

   
    // Property accessors

    public Integer getSiNo() {
        return this.siNo;
    }
    
    public void setSiNo(Integer siNo) {
        this.siNo = siNo;
    }

    public String getTrnDate() {
        return this.trnDate;
    }
    
    public void setTrnDate(String trnDate) {
        this.trnDate = trnDate;
    }

    public String getDueDat() {
        return this.dueDat;
    }
    
    public void setDueDat(String dueDat) {
        this.dueDat = dueDat;
    }

    public Double getTrnAmt() {
        return this.trnAmt;
    }
    
    public void setTrnAmt(Double trnAmt) {
        this.trnAmt = trnAmt;
    }

    public Double getPrinAmt() {
        return this.prinAmt;
    }
    
    public void setPrinAmt(Double prinAmt) {
        this.prinAmt = prinAmt;
    }

    public Double getIntAmt() {
        return this.intAmt;
    }
    
    public void setIntAmt(Double intAmt) {
        this.intAmt = intAmt;
    }

    public Double getPenalIntAmt() {
        return this.penalIntAmt;
    }
    
    public void setPenalIntAmt(Double penalIntAmt) {
        this.penalIntAmt = penalIntAmt;
    }

    public Double getOtherAmt() {
        return this.otherAmt;
    }
    
    public void setOtherAmt(Double otherAmt) {
        this.otherAmt = otherAmt;
    }

    public String getIntDate() {
        return this.intDate;
    }
    
    public void setIntDate(String intDate) {
        this.intDate = intDate;
    }

    public String getCompleteInd() {
        return this.completeInd;
    }
    
    public void setCompleteInd(String completeInd) {
        this.completeInd = completeInd;
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
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof StdInstDoneId) ) return false;
		 StdInstDoneId castOther = ( StdInstDoneId ) other; 
         
		 return ( (this.getSiNo()==castOther.getSiNo()) || ( this.getSiNo()!=null && castOther.getSiNo()!=null && this.getSiNo().equals(castOther.getSiNo()) ) )
 && ( (this.getTrnDate()==castOther.getTrnDate()) || ( this.getTrnDate()!=null && castOther.getTrnDate()!=null && this.getTrnDate().equals(castOther.getTrnDate()) ) )
 && ( (this.getDueDat()==castOther.getDueDat()) || ( this.getDueDat()!=null && castOther.getDueDat()!=null && this.getDueDat().equals(castOther.getDueDat()) ) )
 && ( (this.getTrnAmt()==castOther.getTrnAmt()) || ( this.getTrnAmt()!=null && castOther.getTrnAmt()!=null && this.getTrnAmt().equals(castOther.getTrnAmt()) ) )
 && ( (this.getPrinAmt()==castOther.getPrinAmt()) || ( this.getPrinAmt()!=null && castOther.getPrinAmt()!=null && this.getPrinAmt().equals(castOther.getPrinAmt()) ) )
 && ( (this.getIntAmt()==castOther.getIntAmt()) || ( this.getIntAmt()!=null && castOther.getIntAmt()!=null && this.getIntAmt().equals(castOther.getIntAmt()) ) )
 && ( (this.getPenalIntAmt()==castOther.getPenalIntAmt()) || ( this.getPenalIntAmt()!=null && castOther.getPenalIntAmt()!=null && this.getPenalIntAmt().equals(castOther.getPenalIntAmt()) ) )
 && ( (this.getOtherAmt()==castOther.getOtherAmt()) || ( this.getOtherAmt()!=null && castOther.getOtherAmt()!=null && this.getOtherAmt().equals(castOther.getOtherAmt()) ) )
 && ( (this.getIntDate()==castOther.getIntDate()) || ( this.getIntDate()!=null && castOther.getIntDate()!=null && this.getIntDate().equals(castOther.getIntDate()) ) )
 && ( (this.getCompleteInd()==castOther.getCompleteInd()) || ( this.getCompleteInd()!=null && castOther.getCompleteInd()!=null && this.getCompleteInd().equals(castOther.getCompleteInd()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDtTime()==castOther.getDeDtTime()) || ( this.getDeDtTime()!=null && castOther.getDeDtTime()!=null && this.getDeDtTime().equals(castOther.getDeDtTime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getSiNo() == null ? 0 : this.getSiNo().hashCode() );
         result = 37 * result + ( getTrnDate() == null ? 0 : this.getTrnDate().hashCode() );
         result = 37 * result + ( getDueDat() == null ? 0 : this.getDueDat().hashCode() );
         result = 37 * result + ( getTrnAmt() == null ? 0 : this.getTrnAmt().hashCode() );
         result = 37 * result + ( getPrinAmt() == null ? 0 : this.getPrinAmt().hashCode() );
         result = 37 * result + ( getIntAmt() == null ? 0 : this.getIntAmt().hashCode() );
         result = 37 * result + ( getPenalIntAmt() == null ? 0 : this.getPenalIntAmt().hashCode() );
         result = 37 * result + ( getOtherAmt() == null ? 0 : this.getOtherAmt().hashCode() );
         result = 37 * result + ( getIntDate() == null ? 0 : this.getIntDate().hashCode() );
         result = 37 * result + ( getCompleteInd() == null ? 0 : this.getCompleteInd().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDtTime() == null ? 0 : this.getDeDtTime().hashCode() );
         return result;
   }   





}