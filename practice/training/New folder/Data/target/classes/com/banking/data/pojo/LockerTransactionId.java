package com.banking.data.pojo;
// default package



/**
 * LockerTransactionId entity. @author MyEclipse Persistence Tools
 */

public class LockerTransactionId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private Integer acNo;
     private Integer lockerNo;
     private String rentBy;
     private String trfActy;
     private Integer trfAcno;
     private Double rentAmt;
     private String rentUpto;
     private Integer trnSeq;
     private String trnType;
     private String cdInd;
     private String operBy;
     private String opDate;
     private String timeIn;
     private String timeOut;
     private String deTml;
     private String deUser;
     private String deDate;
     private String veTml;
     private String veUser;
     private String veDate;


    // Constructors

    /** default constructor */
    public LockerTransactionId() {
    }

    
    /** full constructor */
    public LockerTransactionId(String acType, Integer acNo, Integer lockerNo, String rentBy, String trfActy, Integer trfAcno, Double rentAmt, String rentUpto, Integer trnSeq, String trnType, String cdInd, String operBy, String opDate, String timeIn, String timeOut, String deTml, String deUser, String deDate, String veTml, String veUser, String veDate) {
        this.acType = acType;
        this.acNo = acNo;
        this.lockerNo = lockerNo;
        this.rentBy = rentBy;
        this.trfActy = trfActy;
        this.trfAcno = trfAcno;
        this.rentAmt = rentAmt;
        this.rentUpto = rentUpto;
        this.trnSeq = trnSeq;
        this.trnType = trnType;
        this.cdInd = cdInd;
        this.operBy = operBy;
        this.opDate = opDate;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
        this.veTml = veTml;
        this.veUser = veUser;
        this.veDate = veDate;
    }

   
    // Property accessors

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

    public Integer getLockerNo() {
        return this.lockerNo;
    }
    
    public void setLockerNo(Integer lockerNo) {
        this.lockerNo = lockerNo;
    }

    public String getRentBy() {
        return this.rentBy;
    }
    
    public void setRentBy(String rentBy) {
        this.rentBy = rentBy;
    }

    public String getTrfActy() {
        return this.trfActy;
    }
    
    public void setTrfActy(String trfActy) {
        this.trfActy = trfActy;
    }

    public Integer getTrfAcno() {
        return this.trfAcno;
    }
    
    public void setTrfAcno(Integer trfAcno) {
        this.trfAcno = trfAcno;
    }

    public Double getRentAmt() {
        return this.rentAmt;
    }
    
    public void setRentAmt(Double rentAmt) {
        this.rentAmt = rentAmt;
    }

    public String getRentUpto() {
        return this.rentUpto;
    }
    
    public void setRentUpto(String rentUpto) {
        this.rentUpto = rentUpto;
    }

    public Integer getTrnSeq() {
        return this.trnSeq;
    }
    
    public void setTrnSeq(Integer trnSeq) {
        this.trnSeq = trnSeq;
    }

    public String getTrnType() {
        return this.trnType;
    }
    
    public void setTrnType(String trnType) {
        this.trnType = trnType;
    }

    public String getCdInd() {
        return this.cdInd;
    }
    
    public void setCdInd(String cdInd) {
        this.cdInd = cdInd;
    }

    public String getOperBy() {
        return this.operBy;
    }
    
    public void setOperBy(String operBy) {
        this.operBy = operBy;
    }

    public String getOpDate() {
        return this.opDate;
    }
    
    public void setOpDate(String opDate) {
        this.opDate = opDate;
    }

    public String getTimeIn() {
        return this.timeIn;
    }
    
    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getTimeOut() {
        return this.timeOut;
    }
    
    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
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
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof LockerTransactionId) ) return false;
		 LockerTransactionId castOther = ( LockerTransactionId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getLockerNo()==castOther.getLockerNo()) || ( this.getLockerNo()!=null && castOther.getLockerNo()!=null && this.getLockerNo().equals(castOther.getLockerNo()) ) )
 && ( (this.getRentBy()==castOther.getRentBy()) || ( this.getRentBy()!=null && castOther.getRentBy()!=null && this.getRentBy().equals(castOther.getRentBy()) ) )
 && ( (this.getTrfActy()==castOther.getTrfActy()) || ( this.getTrfActy()!=null && castOther.getTrfActy()!=null && this.getTrfActy().equals(castOther.getTrfActy()) ) )
 && ( (this.getTrfAcno()==castOther.getTrfAcno()) || ( this.getTrfAcno()!=null && castOther.getTrfAcno()!=null && this.getTrfAcno().equals(castOther.getTrfAcno()) ) )
 && ( (this.getRentAmt()==castOther.getRentAmt()) || ( this.getRentAmt()!=null && castOther.getRentAmt()!=null && this.getRentAmt().equals(castOther.getRentAmt()) ) )
 && ( (this.getRentUpto()==castOther.getRentUpto()) || ( this.getRentUpto()!=null && castOther.getRentUpto()!=null && this.getRentUpto().equals(castOther.getRentUpto()) ) )
 && ( (this.getTrnSeq()==castOther.getTrnSeq()) || ( this.getTrnSeq()!=null && castOther.getTrnSeq()!=null && this.getTrnSeq().equals(castOther.getTrnSeq()) ) )
 && ( (this.getTrnType()==castOther.getTrnType()) || ( this.getTrnType()!=null && castOther.getTrnType()!=null && this.getTrnType().equals(castOther.getTrnType()) ) )
 && ( (this.getCdInd()==castOther.getCdInd()) || ( this.getCdInd()!=null && castOther.getCdInd()!=null && this.getCdInd().equals(castOther.getCdInd()) ) )
 && ( (this.getOperBy()==castOther.getOperBy()) || ( this.getOperBy()!=null && castOther.getOperBy()!=null && this.getOperBy().equals(castOther.getOperBy()) ) )
 && ( (this.getOpDate()==castOther.getOpDate()) || ( this.getOpDate()!=null && castOther.getOpDate()!=null && this.getOpDate().equals(castOther.getOpDate()) ) )
 && ( (this.getTimeIn()==castOther.getTimeIn()) || ( this.getTimeIn()!=null && castOther.getTimeIn()!=null && this.getTimeIn().equals(castOther.getTimeIn()) ) )
 && ( (this.getTimeOut()==castOther.getTimeOut()) || ( this.getTimeOut()!=null && castOther.getTimeOut()!=null && this.getTimeOut().equals(castOther.getTimeOut()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) )
 && ( (this.getVeTml()==castOther.getVeTml()) || ( this.getVeTml()!=null && castOther.getVeTml()!=null && this.getVeTml().equals(castOther.getVeTml()) ) )
 && ( (this.getVeUser()==castOther.getVeUser()) || ( this.getVeUser()!=null && castOther.getVeUser()!=null && this.getVeUser().equals(castOther.getVeUser()) ) )
 && ( (this.getVeDate()==castOther.getVeDate()) || ( this.getVeDate()!=null && castOther.getVeDate()!=null && this.getVeDate().equals(castOther.getVeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getLockerNo() == null ? 0 : this.getLockerNo().hashCode() );
         result = 37 * result + ( getRentBy() == null ? 0 : this.getRentBy().hashCode() );
         result = 37 * result + ( getTrfActy() == null ? 0 : this.getTrfActy().hashCode() );
         result = 37 * result + ( getTrfAcno() == null ? 0 : this.getTrfAcno().hashCode() );
         result = 37 * result + ( getRentAmt() == null ? 0 : this.getRentAmt().hashCode() );
         result = 37 * result + ( getRentUpto() == null ? 0 : this.getRentUpto().hashCode() );
         result = 37 * result + ( getTrnSeq() == null ? 0 : this.getTrnSeq().hashCode() );
         result = 37 * result + ( getTrnType() == null ? 0 : this.getTrnType().hashCode() );
         result = 37 * result + ( getCdInd() == null ? 0 : this.getCdInd().hashCode() );
         result = 37 * result + ( getOperBy() == null ? 0 : this.getOperBy().hashCode() );
         result = 37 * result + ( getOpDate() == null ? 0 : this.getOpDate().hashCode() );
         result = 37 * result + ( getTimeIn() == null ? 0 : this.getTimeIn().hashCode() );
         result = 37 * result + ( getTimeOut() == null ? 0 : this.getTimeOut().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         result = 37 * result + ( getVeTml() == null ? 0 : this.getVeTml().hashCode() );
         result = 37 * result + ( getVeUser() == null ? 0 : this.getVeUser().hashCode() );
         result = 37 * result + ( getVeDate() == null ? 0 : this.getVeDate().hashCode() );
         return result;
   }   





}