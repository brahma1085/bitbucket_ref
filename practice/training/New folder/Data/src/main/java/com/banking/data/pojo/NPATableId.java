package com.banking.data.pojo;
// default package



/**
 * NPATableId entity. @author MyEclipse Persistence Tools
 */

public class NPATableId  implements java.io.Serializable {


    // Fields    

     private String npaProDate;
     private Integer days;
     private String acType;
     private Integer acNo;
     private Integer assetCode;
     private String npaTowards;
     private String prOdueFrom;
     private Double priOdueAmt;
     private Integer priOduePrd;
     private Double intOdueAmt;
     private String intUptoDate;
     private Integer intOduePrd;
     private Double loanBalance;
     private Double provAmt;
     private String lastTrnDate;


    // Constructors

    /** default constructor */
    public NPATableId() {
    }

    
    /** full constructor */
    public NPATableId(String npaProDate, Integer days, String acType, Integer acNo, Integer assetCode, String npaTowards, String prOdueFrom, Double priOdueAmt, Integer priOduePrd, Double intOdueAmt, String intUptoDate, Integer intOduePrd, Double loanBalance, Double provAmt, String lastTrnDate) {
        this.npaProDate = npaProDate;
        this.days = days;
        this.acType = acType;
        this.acNo = acNo;
        this.assetCode = assetCode;
        this.npaTowards = npaTowards;
        this.prOdueFrom = prOdueFrom;
        this.priOdueAmt = priOdueAmt;
        this.priOduePrd = priOduePrd;
        this.intOdueAmt = intOdueAmt;
        this.intUptoDate = intUptoDate;
        this.intOduePrd = intOduePrd;
        this.loanBalance = loanBalance;
        this.provAmt = provAmt;
        this.lastTrnDate = lastTrnDate;
    }

   
    // Property accessors

    public String getNpaProDate() {
        return this.npaProDate;
    }
    
    public void setNpaProDate(String npaProDate) {
        this.npaProDate = npaProDate;
    }

    public Integer getDays() {
        return this.days;
    }
    
    public void setDays(Integer days) {
        this.days = days;
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

    public Integer getAssetCode() {
        return this.assetCode;
    }
    
    public void setAssetCode(Integer assetCode) {
        this.assetCode = assetCode;
    }

    public String getNpaTowards() {
        return this.npaTowards;
    }
    
    public void setNpaTowards(String npaTowards) {
        this.npaTowards = npaTowards;
    }

    public String getPrOdueFrom() {
        return this.prOdueFrom;
    }
    
    public void setPrOdueFrom(String prOdueFrom) {
        this.prOdueFrom = prOdueFrom;
    }

    public Double getPriOdueAmt() {
        return this.priOdueAmt;
    }
    
    public void setPriOdueAmt(Double priOdueAmt) {
        this.priOdueAmt = priOdueAmt;
    }

    public Integer getPriOduePrd() {
        return this.priOduePrd;
    }
    
    public void setPriOduePrd(Integer priOduePrd) {
        this.priOduePrd = priOduePrd;
    }

    public Double getIntOdueAmt() {
        return this.intOdueAmt;
    }
    
    public void setIntOdueAmt(Double intOdueAmt) {
        this.intOdueAmt = intOdueAmt;
    }

    public String getIntUptoDate() {
        return this.intUptoDate;
    }
    
    public void setIntUptoDate(String intUptoDate) {
        this.intUptoDate = intUptoDate;
    }

    public Integer getIntOduePrd() {
        return this.intOduePrd;
    }
    
    public void setIntOduePrd(Integer intOduePrd) {
        this.intOduePrd = intOduePrd;
    }

    public Double getLoanBalance() {
        return this.loanBalance;
    }
    
    public void setLoanBalance(Double loanBalance) {
        this.loanBalance = loanBalance;
    }

    public Double getProvAmt() {
        return this.provAmt;
    }
    
    public void setProvAmt(Double provAmt) {
        this.provAmt = provAmt;
    }

    public String getLastTrnDate() {
        return this.lastTrnDate;
    }
    
    public void setLastTrnDate(String lastTrnDate) {
        this.lastTrnDate = lastTrnDate;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof NPATableId) ) return false;
		 NPATableId castOther = ( NPATableId ) other; 
         
		 return ( (this.getNpaProDate()==castOther.getNpaProDate()) || ( this.getNpaProDate()!=null && castOther.getNpaProDate()!=null && this.getNpaProDate().equals(castOther.getNpaProDate()) ) )
 && ( (this.getDays()==castOther.getDays()) || ( this.getDays()!=null && castOther.getDays()!=null && this.getDays().equals(castOther.getDays()) ) )
 && ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getAssetCode()==castOther.getAssetCode()) || ( this.getAssetCode()!=null && castOther.getAssetCode()!=null && this.getAssetCode().equals(castOther.getAssetCode()) ) )
 && ( (this.getNpaTowards()==castOther.getNpaTowards()) || ( this.getNpaTowards()!=null && castOther.getNpaTowards()!=null && this.getNpaTowards().equals(castOther.getNpaTowards()) ) )
 && ( (this.getPrOdueFrom()==castOther.getPrOdueFrom()) || ( this.getPrOdueFrom()!=null && castOther.getPrOdueFrom()!=null && this.getPrOdueFrom().equals(castOther.getPrOdueFrom()) ) )
 && ( (this.getPriOdueAmt()==castOther.getPriOdueAmt()) || ( this.getPriOdueAmt()!=null && castOther.getPriOdueAmt()!=null && this.getPriOdueAmt().equals(castOther.getPriOdueAmt()) ) )
 && ( (this.getPriOduePrd()==castOther.getPriOduePrd()) || ( this.getPriOduePrd()!=null && castOther.getPriOduePrd()!=null && this.getPriOduePrd().equals(castOther.getPriOduePrd()) ) )
 && ( (this.getIntOdueAmt()==castOther.getIntOdueAmt()) || ( this.getIntOdueAmt()!=null && castOther.getIntOdueAmt()!=null && this.getIntOdueAmt().equals(castOther.getIntOdueAmt()) ) )
 && ( (this.getIntUptoDate()==castOther.getIntUptoDate()) || ( this.getIntUptoDate()!=null && castOther.getIntUptoDate()!=null && this.getIntUptoDate().equals(castOther.getIntUptoDate()) ) )
 && ( (this.getIntOduePrd()==castOther.getIntOduePrd()) || ( this.getIntOduePrd()!=null && castOther.getIntOduePrd()!=null && this.getIntOduePrd().equals(castOther.getIntOduePrd()) ) )
 && ( (this.getLoanBalance()==castOther.getLoanBalance()) || ( this.getLoanBalance()!=null && castOther.getLoanBalance()!=null && this.getLoanBalance().equals(castOther.getLoanBalance()) ) )
 && ( (this.getProvAmt()==castOther.getProvAmt()) || ( this.getProvAmt()!=null && castOther.getProvAmt()!=null && this.getProvAmt().equals(castOther.getProvAmt()) ) )
 && ( (this.getLastTrnDate()==castOther.getLastTrnDate()) || ( this.getLastTrnDate()!=null && castOther.getLastTrnDate()!=null && this.getLastTrnDate().equals(castOther.getLastTrnDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getNpaProDate() == null ? 0 : this.getNpaProDate().hashCode() );
         result = 37 * result + ( getDays() == null ? 0 : this.getDays().hashCode() );
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getAssetCode() == null ? 0 : this.getAssetCode().hashCode() );
         result = 37 * result + ( getNpaTowards() == null ? 0 : this.getNpaTowards().hashCode() );
         result = 37 * result + ( getPrOdueFrom() == null ? 0 : this.getPrOdueFrom().hashCode() );
         result = 37 * result + ( getPriOdueAmt() == null ? 0 : this.getPriOdueAmt().hashCode() );
         result = 37 * result + ( getPriOduePrd() == null ? 0 : this.getPriOduePrd().hashCode() );
         result = 37 * result + ( getIntOdueAmt() == null ? 0 : this.getIntOdueAmt().hashCode() );
         result = 37 * result + ( getIntUptoDate() == null ? 0 : this.getIntUptoDate().hashCode() );
         result = 37 * result + ( getIntOduePrd() == null ? 0 : this.getIntOduePrd().hashCode() );
         result = 37 * result + ( getLoanBalance() == null ? 0 : this.getLoanBalance().hashCode() );
         result = 37 * result + ( getProvAmt() == null ? 0 : this.getProvAmt().hashCode() );
         result = 37 * result + ( getLastTrnDate() == null ? 0 : this.getLastTrnDate().hashCode() );
         return result;
   }   





}