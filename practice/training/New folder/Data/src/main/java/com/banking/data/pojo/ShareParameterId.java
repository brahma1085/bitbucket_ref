package com.banking.data.pojo;
// default package



/**
 * ShareParameterId entity. @author MyEclipse Persistence Tools
 */

public class ShareParameterId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private Integer memCat;
     private Integer prmCode;


    // Constructors

    /** default constructor */
    public ShareParameterId() {
    }

    
    /** full constructor */
    public ShareParameterId(String acType, Integer memCat, Integer prmCode) {
        this.acType = acType;
        this.memCat = memCat;
        this.prmCode = prmCode;
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

    public Integer getPrmCode() {
        return this.prmCode;
    }
    
    public void setPrmCode(Integer prmCode) {
        this.prmCode = prmCode;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ShareParameterId) ) return false;
		 ShareParameterId castOther = ( ShareParameterId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getMemCat()==castOther.getMemCat()) || ( this.getMemCat()!=null && castOther.getMemCat()!=null && this.getMemCat().equals(castOther.getMemCat()) ) )
 && ( (this.getPrmCode()==castOther.getPrmCode()) || ( this.getPrmCode()!=null && castOther.getPrmCode()!=null && this.getPrmCode().equals(castOther.getPrmCode()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getMemCat() == null ? 0 : this.getMemCat().hashCode() );
         result = 37 * result + ( getPrmCode() == null ? 0 : this.getPrmCode().hashCode() );
         return result;
   }   





}