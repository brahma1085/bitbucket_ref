package com.banking.data.pojo;
// default package

import java.util.Date;


/**
 * NPAClassification_90Id entity. @author MyEclipse Persistence Tools
 */

public class NPAClassification_90Id  implements java.io.Serializable {


    // Fields    

     private Integer loanType;
     private Integer daysLimitFr;
     private Integer daysLimitTo;
     private Integer mthsLimitFr;
     private Integer mthsLimitTo;
     private Integer assetCode;
     private String assetDesc;
     private Double provPerc;
     private Date fromDate;
     private Date toDate;


    // Constructors

    /** default constructor */
    public NPAClassification_90Id() {
    }

    
    /** full constructor */
    public NPAClassification_90Id(Integer loanType, Integer daysLimitFr, Integer daysLimitTo, Integer mthsLimitFr, Integer mthsLimitTo, Integer assetCode, String assetDesc, Double provPerc, Date fromDate, Date toDate) {
        this.loanType = loanType;
        this.daysLimitFr = daysLimitFr;
        this.daysLimitTo = daysLimitTo;
        this.mthsLimitFr = mthsLimitFr;
        this.mthsLimitTo = mthsLimitTo;
        this.assetCode = assetCode;
        this.assetDesc = assetDesc;
        this.provPerc = provPerc;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

   
    // Property accessors

    public Integer getLoanType() {
        return this.loanType;
    }
    
    public void setLoanType(Integer loanType) {
        this.loanType = loanType;
    }

    public Integer getDaysLimitFr() {
        return this.daysLimitFr;
    }
    
    public void setDaysLimitFr(Integer daysLimitFr) {
        this.daysLimitFr = daysLimitFr;
    }

    public Integer getDaysLimitTo() {
        return this.daysLimitTo;
    }
    
    public void setDaysLimitTo(Integer daysLimitTo) {
        this.daysLimitTo = daysLimitTo;
    }

    public Integer getMthsLimitFr() {
        return this.mthsLimitFr;
    }
    
    public void setMthsLimitFr(Integer mthsLimitFr) {
        this.mthsLimitFr = mthsLimitFr;
    }

    public Integer getMthsLimitTo() {
        return this.mthsLimitTo;
    }
    
    public void setMthsLimitTo(Integer mthsLimitTo) {
        this.mthsLimitTo = mthsLimitTo;
    }

    public Integer getAssetCode() {
        return this.assetCode;
    }
    
    public void setAssetCode(Integer assetCode) {
        this.assetCode = assetCode;
    }

    public String getAssetDesc() {
        return this.assetDesc;
    }
    
    public void setAssetDesc(String assetDesc) {
        this.assetDesc = assetDesc;
    }

    public Double getProvPerc() {
        return this.provPerc;
    }
    
    public void setProvPerc(Double provPerc) {
        this.provPerc = provPerc;
    }

    public Date getFromDate() {
        return this.fromDate;
    }
    
    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return this.toDate;
    }
    
    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof NPAClassification_90Id) ) return false;
		 NPAClassification_90Id castOther = ( NPAClassification_90Id ) other; 
         
		 return ( (this.getLoanType()==castOther.getLoanType()) || ( this.getLoanType()!=null && castOther.getLoanType()!=null && this.getLoanType().equals(castOther.getLoanType()) ) )
 && ( (this.getDaysLimitFr()==castOther.getDaysLimitFr()) || ( this.getDaysLimitFr()!=null && castOther.getDaysLimitFr()!=null && this.getDaysLimitFr().equals(castOther.getDaysLimitFr()) ) )
 && ( (this.getDaysLimitTo()==castOther.getDaysLimitTo()) || ( this.getDaysLimitTo()!=null && castOther.getDaysLimitTo()!=null && this.getDaysLimitTo().equals(castOther.getDaysLimitTo()) ) )
 && ( (this.getMthsLimitFr()==castOther.getMthsLimitFr()) || ( this.getMthsLimitFr()!=null && castOther.getMthsLimitFr()!=null && this.getMthsLimitFr().equals(castOther.getMthsLimitFr()) ) )
 && ( (this.getMthsLimitTo()==castOther.getMthsLimitTo()) || ( this.getMthsLimitTo()!=null && castOther.getMthsLimitTo()!=null && this.getMthsLimitTo().equals(castOther.getMthsLimitTo()) ) )
 && ( (this.getAssetCode()==castOther.getAssetCode()) || ( this.getAssetCode()!=null && castOther.getAssetCode()!=null && this.getAssetCode().equals(castOther.getAssetCode()) ) )
 && ( (this.getAssetDesc()==castOther.getAssetDesc()) || ( this.getAssetDesc()!=null && castOther.getAssetDesc()!=null && this.getAssetDesc().equals(castOther.getAssetDesc()) ) )
 && ( (this.getProvPerc()==castOther.getProvPerc()) || ( this.getProvPerc()!=null && castOther.getProvPerc()!=null && this.getProvPerc().equals(castOther.getProvPerc()) ) )
 && ( (this.getFromDate()==castOther.getFromDate()) || ( this.getFromDate()!=null && castOther.getFromDate()!=null && this.getFromDate().equals(castOther.getFromDate()) ) )
 && ( (this.getToDate()==castOther.getToDate()) || ( this.getToDate()!=null && castOther.getToDate()!=null && this.getToDate().equals(castOther.getToDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getLoanType() == null ? 0 : this.getLoanType().hashCode() );
         result = 37 * result + ( getDaysLimitFr() == null ? 0 : this.getDaysLimitFr().hashCode() );
         result = 37 * result + ( getDaysLimitTo() == null ? 0 : this.getDaysLimitTo().hashCode() );
         result = 37 * result + ( getMthsLimitFr() == null ? 0 : this.getMthsLimitFr().hashCode() );
         result = 37 * result + ( getMthsLimitTo() == null ? 0 : this.getMthsLimitTo().hashCode() );
         result = 37 * result + ( getAssetCode() == null ? 0 : this.getAssetCode().hashCode() );
         result = 37 * result + ( getAssetDesc() == null ? 0 : this.getAssetDesc().hashCode() );
         result = 37 * result + ( getProvPerc() == null ? 0 : this.getProvPerc().hashCode() );
         result = 37 * result + ( getFromDate() == null ? 0 : this.getFromDate().hashCode() );
         result = 37 * result + ( getToDate() == null ? 0 : this.getToDate().hashCode() );
         return result;
   }   





}