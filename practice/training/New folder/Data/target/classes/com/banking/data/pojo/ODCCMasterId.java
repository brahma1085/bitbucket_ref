package com.banking.data.pojo;
// default package



/**
 * ODCCMasterId entity. @author MyEclipse Persistence Tools
 */

public class ODCCMasterId  implements java.io.Serializable {


    // Fields    

     private Integer acNo;
     private String acType;


    // Constructors

    /** default constructor */
    public ODCCMasterId() {
    }

    
    /** full constructor */
    public ODCCMasterId(Integer acNo, String acType) {
        this.acNo = acNo;
        this.acType = acType;
    }

   
    // Property accessors

    public Integer getAcNo() {
        return this.acNo;
    }
    
    public void setAcNo(Integer acNo) {
        this.acNo = acNo;
    }

    public String getAcType() {
        return this.acType;
    }
    
    public void setAcType(String acType) {
        this.acType = acType;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ODCCMasterId) ) return false;
		 ODCCMasterId castOther = ( ODCCMasterId ) other; 
         
		 return ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         return result;
   }   





}