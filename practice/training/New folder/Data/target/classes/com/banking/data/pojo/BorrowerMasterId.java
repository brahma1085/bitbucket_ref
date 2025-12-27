package com.banking.data.pojo;
// default package



/**
 * BorrowerMasterId entity. @author MyEclipse Persistence Tools
 */

public class BorrowerMasterId  implements java.io.Serializable {


    // Fields    

     private String lnAcType;
     private Integer lnAcNo;
     private String type;
     private String acType;
     private Integer acNo;
     private String brCode;


    // Constructors

    /** default constructor */
    public BorrowerMasterId() {
    }

    
    /** full constructor */
    public BorrowerMasterId(String lnAcType, Integer lnAcNo, String type, String acType, Integer acNo, String brCode) {
        this.lnAcType = lnAcType;
        this.lnAcNo = lnAcNo;
        this.type = type;
        this.acType = acType;
        this.acNo = acNo;
        this.brCode = brCode;
    }

   
    // Property accessors

    public String getLnAcType() {
        return this.lnAcType;
    }
    
    public void setLnAcType(String lnAcType) {
        this.lnAcType = lnAcType;
    }

    public Integer getLnAcNo() {
        return this.lnAcNo;
    }
    
    public void setLnAcNo(Integer lnAcNo) {
        this.lnAcNo = lnAcNo;
    }

    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

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

    public String getBrCode() {
        return this.brCode;
    }
    
    public void setBrCode(String brCode) {
        this.brCode = brCode;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof BorrowerMasterId) ) return false;
		 BorrowerMasterId castOther = ( BorrowerMasterId ) other; 
         
		 return ( (this.getLnAcType()==castOther.getLnAcType()) || ( this.getLnAcType()!=null && castOther.getLnAcType()!=null && this.getLnAcType().equals(castOther.getLnAcType()) ) )
 && ( (this.getLnAcNo()==castOther.getLnAcNo()) || ( this.getLnAcNo()!=null && castOther.getLnAcNo()!=null && this.getLnAcNo().equals(castOther.getLnAcNo()) ) )
 && ( (this.getType()==castOther.getType()) || ( this.getType()!=null && castOther.getType()!=null && this.getType().equals(castOther.getType()) ) )
 && ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getBrCode()==castOther.getBrCode()) || ( this.getBrCode()!=null && castOther.getBrCode()!=null && this.getBrCode().equals(castOther.getBrCode()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getLnAcType() == null ? 0 : this.getLnAcType().hashCode() );
         result = 37 * result + ( getLnAcNo() == null ? 0 : this.getLnAcNo().hashCode() );
         result = 37 * result + ( getType() == null ? 0 : this.getType().hashCode() );
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getBrCode() == null ? 0 : this.getBrCode().hashCode() );
         return result;
   }   





}