package com.banking.data.pojo;
// default package



/**
 * LockerRemainderId entity. @author MyEclipse Persistence Tools
 */

public class LockerRemainderId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private Integer acNo;
     private String trnDate;
     private String emailFlag;
     private String smsFlag;


    // Constructors

    /** default constructor */
    public LockerRemainderId() {
    }

    
    /** full constructor */
    public LockerRemainderId(String acType, Integer acNo, String trnDate, String emailFlag, String smsFlag) {
        this.acType = acType;
        this.acNo = acNo;
        this.trnDate = trnDate;
        this.emailFlag = emailFlag;
        this.smsFlag = smsFlag;
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

    public String getTrnDate() {
        return this.trnDate;
    }
    
    public void setTrnDate(String trnDate) {
        this.trnDate = trnDate;
    }

    public String getEmailFlag() {
        return this.emailFlag;
    }
    
    public void setEmailFlag(String emailFlag) {
        this.emailFlag = emailFlag;
    }

    public String getSmsFlag() {
        return this.smsFlag;
    }
    
    public void setSmsFlag(String smsFlag) {
        this.smsFlag = smsFlag;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof LockerRemainderId) ) return false;
		 LockerRemainderId castOther = ( LockerRemainderId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getTrnDate()==castOther.getTrnDate()) || ( this.getTrnDate()!=null && castOther.getTrnDate()!=null && this.getTrnDate().equals(castOther.getTrnDate()) ) )
 && ( (this.getEmailFlag()==castOther.getEmailFlag()) || ( this.getEmailFlag()!=null && castOther.getEmailFlag()!=null && this.getEmailFlag().equals(castOther.getEmailFlag()) ) )
 && ( (this.getSmsFlag()==castOther.getSmsFlag()) || ( this.getSmsFlag()!=null && castOther.getSmsFlag()!=null && this.getSmsFlag().equals(castOther.getSmsFlag()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getTrnDate() == null ? 0 : this.getTrnDate().hashCode() );
         result = 37 * result + ( getEmailFlag() == null ? 0 : this.getEmailFlag().hashCode() );
         result = 37 * result + ( getSmsFlag() == null ? 0 : this.getSmsFlag().hashCode() );
         return result;
   }   





}