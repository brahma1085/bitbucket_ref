package com.banking.data.pojo;
// default package



/**
 * InterestTransferVoucherId entity. @author MyEclipse Persistence Tools
 */

public class InterestTransferVoucherId  implements java.io.Serializable {


    // Fields    

     private Integer vchNo;
     private String vchDate;
     private Double vchAmount;
     private String acType;
     private Integer acNo;
     private String vchPrtInd;
     private String vchPayInd;
     private String ddPurInd;
     private String vchPayDate;
     private String payMode;
     private String payAcType;
     private Integer payAcNo;
     private String deTml;
     private String deUser;
     private String deDate;


    // Constructors

    /** default constructor */
    public InterestTransferVoucherId() {
    }

    
    /** full constructor */
    public InterestTransferVoucherId(Integer vchNo, String vchDate, Double vchAmount, String acType, Integer acNo, String vchPrtInd, String vchPayInd, String ddPurInd, String vchPayDate, String payMode, String payAcType, Integer payAcNo, String deTml, String deUser, String deDate) {
        this.vchNo = vchNo;
        this.vchDate = vchDate;
        this.vchAmount = vchAmount;
        this.acType = acType;
        this.acNo = acNo;
        this.vchPrtInd = vchPrtInd;
        this.vchPayInd = vchPayInd;
        this.ddPurInd = ddPurInd;
        this.vchPayDate = vchPayDate;
        this.payMode = payMode;
        this.payAcType = payAcType;
        this.payAcNo = payAcNo;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
    }

   
    // Property accessors

    public Integer getVchNo() {
        return this.vchNo;
    }
    
    public void setVchNo(Integer vchNo) {
        this.vchNo = vchNo;
    }

    public String getVchDate() {
        return this.vchDate;
    }
    
    public void setVchDate(String vchDate) {
        this.vchDate = vchDate;
    }

    public Double getVchAmount() {
        return this.vchAmount;
    }
    
    public void setVchAmount(Double vchAmount) {
        this.vchAmount = vchAmount;
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

    public String getVchPrtInd() {
        return this.vchPrtInd;
    }
    
    public void setVchPrtInd(String vchPrtInd) {
        this.vchPrtInd = vchPrtInd;
    }

    public String getVchPayInd() {
        return this.vchPayInd;
    }
    
    public void setVchPayInd(String vchPayInd) {
        this.vchPayInd = vchPayInd;
    }

    public String getDdPurInd() {
        return this.ddPurInd;
    }
    
    public void setDdPurInd(String ddPurInd) {
        this.ddPurInd = ddPurInd;
    }

    public String getVchPayDate() {
        return this.vchPayDate;
    }
    
    public void setVchPayDate(String vchPayDate) {
        this.vchPayDate = vchPayDate;
    }

    public String getPayMode() {
        return this.payMode;
    }
    
    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public String getPayAcType() {
        return this.payAcType;
    }
    
    public void setPayAcType(String payAcType) {
        this.payAcType = payAcType;
    }

    public Integer getPayAcNo() {
        return this.payAcNo;
    }
    
    public void setPayAcNo(Integer payAcNo) {
        this.payAcNo = payAcNo;
    }

    public String getDeTml() {
        return this.deTml;
    }
    
    public void setDeTml(String deTml) {
        this.deTml = deTml;
    }

    public String getDeUser() {
        return this.deUser;
    }
    
    public void setDeUser(String deUser) {
        this.deUser = deUser;
    }

    public String getDeDate() {
        return this.deDate;
    }
    
    public void setDeDate(String deDate) {
        this.deDate = deDate;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof InterestTransferVoucherId) ) return false;
		 InterestTransferVoucherId castOther = ( InterestTransferVoucherId ) other; 
         
		 return ( (this.getVchNo()==castOther.getVchNo()) || ( this.getVchNo()!=null && castOther.getVchNo()!=null && this.getVchNo().equals(castOther.getVchNo()) ) )
 && ( (this.getVchDate()==castOther.getVchDate()) || ( this.getVchDate()!=null && castOther.getVchDate()!=null && this.getVchDate().equals(castOther.getVchDate()) ) )
 && ( (this.getVchAmount()==castOther.getVchAmount()) || ( this.getVchAmount()!=null && castOther.getVchAmount()!=null && this.getVchAmount().equals(castOther.getVchAmount()) ) )
 && ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getVchPrtInd()==castOther.getVchPrtInd()) || ( this.getVchPrtInd()!=null && castOther.getVchPrtInd()!=null && this.getVchPrtInd().equals(castOther.getVchPrtInd()) ) )
 && ( (this.getVchPayInd()==castOther.getVchPayInd()) || ( this.getVchPayInd()!=null && castOther.getVchPayInd()!=null && this.getVchPayInd().equals(castOther.getVchPayInd()) ) )
 && ( (this.getDdPurInd()==castOther.getDdPurInd()) || ( this.getDdPurInd()!=null && castOther.getDdPurInd()!=null && this.getDdPurInd().equals(castOther.getDdPurInd()) ) )
 && ( (this.getVchPayDate()==castOther.getVchPayDate()) || ( this.getVchPayDate()!=null && castOther.getVchPayDate()!=null && this.getVchPayDate().equals(castOther.getVchPayDate()) ) )
 && ( (this.getPayMode()==castOther.getPayMode()) || ( this.getPayMode()!=null && castOther.getPayMode()!=null && this.getPayMode().equals(castOther.getPayMode()) ) )
 && ( (this.getPayAcType()==castOther.getPayAcType()) || ( this.getPayAcType()!=null && castOther.getPayAcType()!=null && this.getPayAcType().equals(castOther.getPayAcType()) ) )
 && ( (this.getPayAcNo()==castOther.getPayAcNo()) || ( this.getPayAcNo()!=null && castOther.getPayAcNo()!=null && this.getPayAcNo().equals(castOther.getPayAcNo()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getVchNo() == null ? 0 : this.getVchNo().hashCode() );
         result = 37 * result + ( getVchDate() == null ? 0 : this.getVchDate().hashCode() );
         result = 37 * result + ( getVchAmount() == null ? 0 : this.getVchAmount().hashCode() );
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getVchPrtInd() == null ? 0 : this.getVchPrtInd().hashCode() );
         result = 37 * result + ( getVchPayInd() == null ? 0 : this.getVchPayInd().hashCode() );
         result = 37 * result + ( getDdPurInd() == null ? 0 : this.getDdPurInd().hashCode() );
         result = 37 * result + ( getVchPayDate() == null ? 0 : this.getVchPayDate().hashCode() );
         result = 37 * result + ( getPayMode() == null ? 0 : this.getPayMode().hashCode() );
         result = 37 * result + ( getPayAcType() == null ? 0 : this.getPayAcType().hashCode() );
         result = 37 * result + ( getPayAcNo() == null ? 0 : this.getPayAcNo().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         return result;
   }   





}