package com.banking.data.pojo;
// default package



/**
 * TempODCCPenalId entity. @author MyEclipse Persistence Tools
 */

public class TempODCCPenalId  implements java.io.Serializable {


    // Fields    

     private Integer acType;
     private Integer acNo;
     private String fromDate;
     private String toDate;
     private Integer trnSeq;
     private Double bal;
     private Integer period;
     private Double interest;
     private Double prevPenal;


    // Constructors

    /** default constructor */
    public TempODCCPenalId() {
    }

    
    /** full constructor */
    public TempODCCPenalId(Integer acType, Integer acNo, String fromDate, String toDate, Integer trnSeq, Double bal, Integer period, Double interest, Double prevPenal) {
        this.acType = acType;
        this.acNo = acNo;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.trnSeq = trnSeq;
        this.bal = bal;
        this.period = period;
        this.interest = interest;
        this.prevPenal = prevPenal;
    }

   
    // Property accessors

    public Integer getAcType() {
        return this.acType;
    }
    
    public void setAcType(Integer acType) {
        this.acType = acType;
    }

    public Integer getAcNo() {
        return this.acNo;
    }
    
    public void setAcNo(Integer acNo) {
        this.acNo = acNo;
    }

    public String getFromDate() {
        return this.fromDate;
    }
    
    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return this.toDate;
    }
    
    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public Integer getTrnSeq() {
        return this.trnSeq;
    }
    
    public void setTrnSeq(Integer trnSeq) {
        this.trnSeq = trnSeq;
    }

    public Double getBal() {
        return this.bal;
    }
    
    public void setBal(Double bal) {
        this.bal = bal;
    }

    public Integer getPeriod() {
        return this.period;
    }
    
    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Double getInterest() {
        return this.interest;
    }
    
    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public Double getPrevPenal() {
        return this.prevPenal;
    }
    
    public void setPrevPenal(Double prevPenal) {
        this.prevPenal = prevPenal;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof TempODCCPenalId) ) return false;
		 TempODCCPenalId castOther = ( TempODCCPenalId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getFromDate()==castOther.getFromDate()) || ( this.getFromDate()!=null && castOther.getFromDate()!=null && this.getFromDate().equals(castOther.getFromDate()) ) )
 && ( (this.getToDate()==castOther.getToDate()) || ( this.getToDate()!=null && castOther.getToDate()!=null && this.getToDate().equals(castOther.getToDate()) ) )
 && ( (this.getTrnSeq()==castOther.getTrnSeq()) || ( this.getTrnSeq()!=null && castOther.getTrnSeq()!=null && this.getTrnSeq().equals(castOther.getTrnSeq()) ) )
 && ( (this.getBal()==castOther.getBal()) || ( this.getBal()!=null && castOther.getBal()!=null && this.getBal().equals(castOther.getBal()) ) )
 && ( (this.getPeriod()==castOther.getPeriod()) || ( this.getPeriod()!=null && castOther.getPeriod()!=null && this.getPeriod().equals(castOther.getPeriod()) ) )
 && ( (this.getInterest()==castOther.getInterest()) || ( this.getInterest()!=null && castOther.getInterest()!=null && this.getInterest().equals(castOther.getInterest()) ) )
 && ( (this.getPrevPenal()==castOther.getPrevPenal()) || ( this.getPrevPenal()!=null && castOther.getPrevPenal()!=null && this.getPrevPenal().equals(castOther.getPrevPenal()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getFromDate() == null ? 0 : this.getFromDate().hashCode() );
         result = 37 * result + ( getToDate() == null ? 0 : this.getToDate().hashCode() );
         result = 37 * result + ( getTrnSeq() == null ? 0 : this.getTrnSeq().hashCode() );
         result = 37 * result + ( getBal() == null ? 0 : this.getBal().hashCode() );
         result = 37 * result + ( getPeriod() == null ? 0 : this.getPeriod().hashCode() );
         result = 37 * result + ( getInterest() == null ? 0 : this.getInterest().hashCode() );
         result = 37 * result + ( getPrevPenal() == null ? 0 : this.getPrevPenal().hashCode() );
         return result;
   }   





}