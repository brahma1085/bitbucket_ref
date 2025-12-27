package com.banking.data.pojo;
// default package



/**
 * ShareTypeId entity. @author MyEclipse Persistence Tools
 */

public class ShareTypeId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private Integer memCat;


    // Constructors

    /** default constructor */
    public ShareTypeId() {
    }

    
    /** full constructor */
    public ShareTypeId(String acType, Integer memCat) {
        this.acType = acType;
        this.memCat = memCat;
    }

   
    // Property accessors

    public String getAcType() {
        return this.acType;
    }
    
    public void setAcType(String acType) {
        this.acType = acType;
    }

    public Integer getMemCat() {
        return this.memCat;
    }
    
    public void setMemCat(Integer memCat) {
        this.memCat = memCat;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ShareTypeId) ) return false;
		 ShareTypeId castOther = ( ShareTypeId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getMemCat()==castOther.getMemCat()) || ( this.getMemCat()!=null && castOther.getMemCat()!=null && this.getMemCat().equals(castOther.getMemCat()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getMemCat() == null ? 0 : this.getMemCat().hashCode() );
         return result;
   }   





}