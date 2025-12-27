package com.banking.data.pojo;
// default package



/**
 * AccountMasterId entity. @author MyEclipse Persistence Tools
 */

public class AccountMasterId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private Integer acNo;


    // Constructors

    /** default constructor */
    public AccountMasterId() {
    }

    
    /** full constructor */
    public AccountMasterId(String acType, Integer acNo) {
        this.acType = acType;
        this.acNo = acNo;
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
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof AccountMasterId) ) return false;
		 AccountMasterId castOther = ( AccountMasterId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         return result;
   }   





}