package com.banking.data.pojo;
// default package



/**
 * LoanRecoveryDetailId entity. @author MyEclipse Persistence Tools
 */

public class LoanRecoveryDetailId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private String acNo;
     private String processingDate;
     private Double intAmt;
     private Double penalAmt;
     private Double otherCharges;
     private Double loanBalance;
     private String deTml;
     private String deUser;
     private String deDate;


    // Constructors

    /** default constructor */
    public LoanRecoveryDetailId() {
    }

    
    /** full constructor */
    public LoanRecoveryDetailId(String acType, String acNo, String processingDate, Double intAmt, Double penalAmt, Double otherCharges, Double loanBalance, String deTml, String deUser, String deDate) {
        this.acType = acType;
        this.acNo = acNo;
        this.processingDate = processingDate;
        this.intAmt = intAmt;
        this.penalAmt = penalAmt;
        this.otherCharges = otherCharges;
        this.loanBalance = loanBalance;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
    }

   
    // Property accessors

    public String getAcType() {
        return this.acType;
    }
    
    public void setAcType(String acType) {
        this.acType = acType;
    }

    public String getAcNo() {
        return this.acNo;
    }
    
    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getProcessingDate() {
        return this.processingDate;
    }
    
    public void setProcessingDate(String processingDate) {
        this.processingDate = processingDate;
    }

    public Double getIntAmt() {
        return this.intAmt;
    }
    
    public void setIntAmt(Double intAmt) {
        this.intAmt = intAmt;
    }

    public Double getPenalAmt() {
        return this.penalAmt;
    }
    
    public void setPenalAmt(Double penalAmt) {
        this.penalAmt = penalAmt;
    }

    public Double getOtherCharges() {
        return this.otherCharges;
    }
    
    public void setOtherCharges(Double otherCharges) {
        this.otherCharges = otherCharges;
    }

    public Double getLoanBalance() {
        return this.loanBalance;
    }
    
    public void setLoanBalance(Double loanBalance) {
        this.loanBalance = loanBalance;
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
		 if ( !(other instanceof LoanRecoveryDetailId) ) return false;
		 LoanRecoveryDetailId castOther = ( LoanRecoveryDetailId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getProcessingDate()==castOther.getProcessingDate()) || ( this.getProcessingDate()!=null && castOther.getProcessingDate()!=null && this.getProcessingDate().equals(castOther.getProcessingDate()) ) )
 && ( (this.getIntAmt()==castOther.getIntAmt()) || ( this.getIntAmt()!=null && castOther.getIntAmt()!=null && this.getIntAmt().equals(castOther.getIntAmt()) ) )
 && ( (this.getPenalAmt()==castOther.getPenalAmt()) || ( this.getPenalAmt()!=null && castOther.getPenalAmt()!=null && this.getPenalAmt().equals(castOther.getPenalAmt()) ) )
 && ( (this.getOtherCharges()==castOther.getOtherCharges()) || ( this.getOtherCharges()!=null && castOther.getOtherCharges()!=null && this.getOtherCharges().equals(castOther.getOtherCharges()) ) )
 && ( (this.getLoanBalance()==castOther.getLoanBalance()) || ( this.getLoanBalance()!=null && castOther.getLoanBalance()!=null && this.getLoanBalance().equals(castOther.getLoanBalance()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getProcessingDate() == null ? 0 : this.getProcessingDate().hashCode() );
         result = 37 * result + ( getIntAmt() == null ? 0 : this.getIntAmt().hashCode() );
         result = 37 * result + ( getPenalAmt() == null ? 0 : this.getPenalAmt().hashCode() );
         result = 37 * result + ( getOtherCharges() == null ? 0 : this.getOtherCharges().hashCode() );
         result = 37 * result + ( getLoanBalance() == null ? 0 : this.getLoanBalance().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         return result;
   }   





}