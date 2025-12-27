package com.banking.data.pojo;
// default package



/**
 * ODInterestDetailsId entity. @author MyEclipse Persistence Tools
 */

public class ODInterestDetailsId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private Integer acNo;
     private String secAcType;
     private Integer secAcNo;
     private Double lnIntRate;
     private Integer intType;
     private Double depAmt;
     private Double eligibleAmt;
     private Double intAmt;
     private String fromDate;
     private String toDate;


    // Constructors

    /** default constructor */
    public ODInterestDetailsId() {
    }

    
    /** full constructor */
    public ODInterestDetailsId(String acType, Integer acNo, String secAcType, Integer secAcNo, Double lnIntRate, Integer intType, Double depAmt, Double eligibleAmt, Double intAmt, String fromDate, String toDate) {
        this.acType = acType;
        this.acNo = acNo;
        this.secAcType = secAcType;
        this.secAcNo = secAcNo;
        this.lnIntRate = lnIntRate;
        this.intType = intType;
        this.depAmt = depAmt;
        this.eligibleAmt = eligibleAmt;
        this.intAmt = intAmt;
        this.fromDate = fromDate;
        this.toDate = toDate;
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

    public String getSecAcType() {
        return this.secAcType;
    }
    
    public void setSecAcType(String secAcType) {
        this.secAcType = secAcType;
    }

    public Integer getSecAcNo() {
        return this.secAcNo;
    }
    
    public void setSecAcNo(Integer secAcNo) {
        this.secAcNo = secAcNo;
    }

    public Double getLnIntRate() {
        return this.lnIntRate;
    }
    
    public void setLnIntRate(Double lnIntRate) {
        this.lnIntRate = lnIntRate;
    }

    public Integer getIntType() {
        return this.intType;
    }
    
    public void setIntType(Integer intType) {
        this.intType = intType;
    }

    public Double getDepAmt() {
        return this.depAmt;
    }
    
    public void setDepAmt(Double depAmt) {
        this.depAmt = depAmt;
    }

    public Double getEligibleAmt() {
        return this.eligibleAmt;
    }
    
    public void setEligibleAmt(Double eligibleAmt) {
        this.eligibleAmt = eligibleAmt;
    }

    public Double getIntAmt() {
        return this.intAmt;
    }
    
    public void setIntAmt(Double intAmt) {
        this.intAmt = intAmt;
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
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ODInterestDetailsId) ) return false;
		 ODInterestDetailsId castOther = ( ODInterestDetailsId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getSecAcType()==castOther.getSecAcType()) || ( this.getSecAcType()!=null && castOther.getSecAcType()!=null && this.getSecAcType().equals(castOther.getSecAcType()) ) )
 && ( (this.getSecAcNo()==castOther.getSecAcNo()) || ( this.getSecAcNo()!=null && castOther.getSecAcNo()!=null && this.getSecAcNo().equals(castOther.getSecAcNo()) ) )
 && ( (this.getLnIntRate()==castOther.getLnIntRate()) || ( this.getLnIntRate()!=null && castOther.getLnIntRate()!=null && this.getLnIntRate().equals(castOther.getLnIntRate()) ) )
 && ( (this.getIntType()==castOther.getIntType()) || ( this.getIntType()!=null && castOther.getIntType()!=null && this.getIntType().equals(castOther.getIntType()) ) )
 && ( (this.getDepAmt()==castOther.getDepAmt()) || ( this.getDepAmt()!=null && castOther.getDepAmt()!=null && this.getDepAmt().equals(castOther.getDepAmt()) ) )
 && ( (this.getEligibleAmt()==castOther.getEligibleAmt()) || ( this.getEligibleAmt()!=null && castOther.getEligibleAmt()!=null && this.getEligibleAmt().equals(castOther.getEligibleAmt()) ) )
 && ( (this.getIntAmt()==castOther.getIntAmt()) || ( this.getIntAmt()!=null && castOther.getIntAmt()!=null && this.getIntAmt().equals(castOther.getIntAmt()) ) )
 && ( (this.getFromDate()==castOther.getFromDate()) || ( this.getFromDate()!=null && castOther.getFromDate()!=null && this.getFromDate().equals(castOther.getFromDate()) ) )
 && ( (this.getToDate()==castOther.getToDate()) || ( this.getToDate()!=null && castOther.getToDate()!=null && this.getToDate().equals(castOther.getToDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getSecAcType() == null ? 0 : this.getSecAcType().hashCode() );
         result = 37 * result + ( getSecAcNo() == null ? 0 : this.getSecAcNo().hashCode() );
         result = 37 * result + ( getLnIntRate() == null ? 0 : this.getLnIntRate().hashCode() );
         result = 37 * result + ( getIntType() == null ? 0 : this.getIntType().hashCode() );
         result = 37 * result + ( getDepAmt() == null ? 0 : this.getDepAmt().hashCode() );
         result = 37 * result + ( getEligibleAmt() == null ? 0 : this.getEligibleAmt().hashCode() );
         result = 37 * result + ( getIntAmt() == null ? 0 : this.getIntAmt().hashCode() );
         result = 37 * result + ( getFromDate() == null ? 0 : this.getFromDate().hashCode() );
         result = 37 * result + ( getToDate() == null ? 0 : this.getToDate().hashCode() );
         return result;
   }   





}