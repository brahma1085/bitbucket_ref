package com.banking.data.pojo;
// default package



/**
 * CertificateMasterId entity. @author MyEclipse Persistence Tools
 */

public class CertificateMasterId  implements java.io.Serializable {


    // Fields    

     private Integer shDistNo;
     private Integer shAcNo;
     private String shAcType;
     private String allotmentdt;
     private String refunddt;


    // Constructors

    /** default constructor */
    public CertificateMasterId() {
    }

	/** minimal constructor */
    public CertificateMasterId(Integer shDistNo) {
        this.shDistNo = shDistNo;
    }
    
    /** full constructor */
    public CertificateMasterId(Integer shDistNo, Integer shAcNo, String shAcType, String allotmentdt, String refunddt) {
        this.shDistNo = shDistNo;
        this.shAcNo = shAcNo;
        this.shAcType = shAcType;
        this.allotmentdt = allotmentdt;
        this.refunddt = refunddt;
    }

   
    // Property accessors

    public Integer getShDistNo() {
        return this.shDistNo;
    }
    
    public void setShDistNo(Integer shDistNo) {
        this.shDistNo = shDistNo;
    }

    public Integer getShAcNo() {
        return this.shAcNo;
    }
    
    public void setShAcNo(Integer shAcNo) {
        this.shAcNo = shAcNo;
    }

    public String getShAcType() {
        return this.shAcType;
    }
    
    public void setShAcType(String shAcType) {
        this.shAcType = shAcType;
    }

    public String getAllotmentdt() {
        return this.allotmentdt;
    }
    
    public void setAllotmentdt(String allotmentdt) {
        this.allotmentdt = allotmentdt;
    }

    public String getRefunddt() {
        return this.refunddt;
    }
    
    public void setRefunddt(String refunddt) {
        this.refunddt = refunddt;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof CertificateMasterId) ) return false;
		 CertificateMasterId castOther = ( CertificateMasterId ) other; 
         
		 return ( (this.getShDistNo()==castOther.getShDistNo()) || ( this.getShDistNo()!=null && castOther.getShDistNo()!=null && this.getShDistNo().equals(castOther.getShDistNo()) ) )
 && ( (this.getShAcNo()==castOther.getShAcNo()) || ( this.getShAcNo()!=null && castOther.getShAcNo()!=null && this.getShAcNo().equals(castOther.getShAcNo()) ) )
 && ( (this.getShAcType()==castOther.getShAcType()) || ( this.getShAcType()!=null && castOther.getShAcType()!=null && this.getShAcType().equals(castOther.getShAcType()) ) )
 && ( (this.getAllotmentdt()==castOther.getAllotmentdt()) || ( this.getAllotmentdt()!=null && castOther.getAllotmentdt()!=null && this.getAllotmentdt().equals(castOther.getAllotmentdt()) ) )
 && ( (this.getRefunddt()==castOther.getRefunddt()) || ( this.getRefunddt()!=null && castOther.getRefunddt()!=null && this.getRefunddt().equals(castOther.getRefunddt()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getShDistNo() == null ? 0 : this.getShDistNo().hashCode() );
         result = 37 * result + ( getShAcNo() == null ? 0 : this.getShAcNo().hashCode() );
         result = 37 * result + ( getShAcType() == null ? 0 : this.getShAcType().hashCode() );
         result = 37 * result + ( getAllotmentdt() == null ? 0 : this.getAllotmentdt().hashCode() );
         result = 37 * result + ( getRefunddt() == null ? 0 : this.getRefunddt().hashCode() );
         return result;
   }   





}