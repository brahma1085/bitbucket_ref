package com.banking.data.pojo;
// default package



/**
 * PayOderLinkId entity. @author MyEclipse Persistence Tools
 */

public class PayOderLinkId  implements java.io.Serializable {


    // Fields    

     private String payordDt;
     private Integer payordNo;
     private Integer refSno;
     private String refType;


    // Constructors

    /** default constructor */
    public PayOderLinkId() {
    }

    
    /** full constructor */
    public PayOderLinkId(String payordDt, Integer payordNo, Integer refSno, String refType) {
        this.payordDt = payordDt;
        this.payordNo = payordNo;
        this.refSno = refSno;
        this.refType = refType;
    }

   
    // Property accessors

    public String getPayordDt() {
        return this.payordDt;
    }
    
    public void setPayordDt(String payordDt) {
        this.payordDt = payordDt;
    }

    public Integer getPayordNo() {
        return this.payordNo;
    }
    
    public void setPayordNo(Integer payordNo) {
        this.payordNo = payordNo;
    }

    public Integer getRefSno() {
        return this.refSno;
    }
    
    public void setRefSno(Integer refSno) {
        this.refSno = refSno;
    }

    public String getRefType() {
        return this.refType;
    }
    
    public void setRefType(String refType) {
        this.refType = refType;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof PayOderLinkId) ) return false;
		 PayOderLinkId castOther = ( PayOderLinkId ) other; 
         
		 return ( (this.getPayordDt()==castOther.getPayordDt()) || ( this.getPayordDt()!=null && castOther.getPayordDt()!=null && this.getPayordDt().equals(castOther.getPayordDt()) ) )
 && ( (this.getPayordNo()==castOther.getPayordNo()) || ( this.getPayordNo()!=null && castOther.getPayordNo()!=null && this.getPayordNo().equals(castOther.getPayordNo()) ) )
 && ( (this.getRefSno()==castOther.getRefSno()) || ( this.getRefSno()!=null && castOther.getRefSno()!=null && this.getRefSno().equals(castOther.getRefSno()) ) )
 && ( (this.getRefType()==castOther.getRefType()) || ( this.getRefType()!=null && castOther.getRefType()!=null && this.getRefType().equals(castOther.getRefType()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getPayordDt() == null ? 0 : this.getPayordDt().hashCode() );
         result = 37 * result + ( getPayordNo() == null ? 0 : this.getPayordNo().hashCode() );
         result = 37 * result + ( getRefSno() == null ? 0 : this.getRefSno().hashCode() );
         result = 37 * result + ( getRefType() == null ? 0 : this.getRefType().hashCode() );
         return result;
   }   





}