package com.banking.data.pojo;
// default package

import java.util.Date;


/**
 * FreezedAccountsId entity. @author MyEclipse Persistence Tools
 */

public class FreezedAccountsId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private Integer acNo;
     private String reason;
     private Date fromDate;
     private Date toDate;
     private String frzInoperInd;


    // Constructors

    /** default constructor */
    public FreezedAccountsId() {
    }

	/** minimal constructor */
    public FreezedAccountsId(String acType, Integer acNo, String reason, Date fromDate, String frzInoperInd) {
        this.acType = acType;
        this.acNo = acNo;
        this.reason = reason;
        this.fromDate = fromDate;
        this.frzInoperInd = frzInoperInd;
    }
    
    /** full constructor */
    public FreezedAccountsId(String acType, Integer acNo, String reason, Date fromDate, Date toDate, String frzInoperInd) {
        this.acType = acType;
        this.acNo = acNo;
        this.reason = reason;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.frzInoperInd = frzInoperInd;
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

    public String getReason() {
        return this.reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
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

    public String getFrzInoperInd() {
        return this.frzInoperInd;
    }
    
    public void setFrzInoperInd(String frzInoperInd) {
        this.frzInoperInd = frzInoperInd;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof FreezedAccountsId) ) return false;
		 FreezedAccountsId castOther = ( FreezedAccountsId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getReason()==castOther.getReason()) || ( this.getReason()!=null && castOther.getReason()!=null && this.getReason().equals(castOther.getReason()) ) )
 && ( (this.getFromDate()==castOther.getFromDate()) || ( this.getFromDate()!=null && castOther.getFromDate()!=null && this.getFromDate().equals(castOther.getFromDate()) ) )
 && ( (this.getToDate()==castOther.getToDate()) || ( this.getToDate()!=null && castOther.getToDate()!=null && this.getToDate().equals(castOther.getToDate()) ) )
 && ( (this.getFrzInoperInd()==castOther.getFrzInoperInd()) || ( this.getFrzInoperInd()!=null && castOther.getFrzInoperInd()!=null && this.getFrzInoperInd().equals(castOther.getFrzInoperInd()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getReason() == null ? 0 : this.getReason().hashCode() );
         result = 37 * result + ( getFromDate() == null ? 0 : this.getFromDate().hashCode() );
         result = 37 * result + ( getToDate() == null ? 0 : this.getToDate().hashCode() );
         result = 37 * result + ( getFrzInoperInd() == null ? 0 : this.getFrzInoperInd().hashCode() );
         return result;
   }   





}