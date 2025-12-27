package com.banking.data.pojo;
// default package



/**
 * LoanInterestRateId entity. @author MyEclipse Persistence Tools
 */

public class LoanInterestRateId  implements java.io.Serializable {


    // Fields    

     private String lnType;
     private Integer ppsCode;
     private String dateFr;
     private String dateTo;
     private Double minBal;
     private Double maxBal;
     private Double rate;
     private String deTml;
     private String deUser;
     private String deDtTime;


    // Constructors

    /** default constructor */
    public LoanInterestRateId() {
    }

    
    /** full constructor */
    public LoanInterestRateId(String lnType, Integer ppsCode, String dateFr, String dateTo, Double minBal, Double maxBal, Double rate, String deTml, String deUser, String deDtTime) {
        this.lnType = lnType;
        this.ppsCode = ppsCode;
        this.dateFr = dateFr;
        this.dateTo = dateTo;
        this.minBal = minBal;
        this.maxBal = maxBal;
        this.rate = rate;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDtTime = deDtTime;
    }

   
    // Property accessors

    public String getLnType() {
        return this.lnType;
    }
    
    public void setLnType(String lnType) {
        this.lnType = lnType;
    }

    public Integer getPpsCode() {
        return this.ppsCode;
    }
    
    public void setPpsCode(Integer ppsCode) {
        this.ppsCode = ppsCode;
    }

    public String getDateFr() {
        return this.dateFr;
    }
    
    public void setDateFr(String dateFr) {
        this.dateFr = dateFr;
    }

    public String getDateTo() {
        return this.dateTo;
    }
    
    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public Double getMinBal() {
        return this.minBal;
    }
    
    public void setMinBal(Double minBal) {
        this.minBal = minBal;
    }

    public Double getMaxBal() {
        return this.maxBal;
    }
    
    public void setMaxBal(Double maxBal) {
        this.maxBal = maxBal;
    }

    public Double getRate() {
        return this.rate;
    }
    
    public void setRate(Double rate) {
        this.rate = rate;
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

    public String getDeDtTime() {
        return this.deDtTime;
    }
    
    public void setDeDtTime(String deDtTime) {
        this.deDtTime = deDtTime;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof LoanInterestRateId) ) return false;
		 LoanInterestRateId castOther = ( LoanInterestRateId ) other; 
         
		 return ( (this.getLnType()==castOther.getLnType()) || ( this.getLnType()!=null && castOther.getLnType()!=null && this.getLnType().equals(castOther.getLnType()) ) )
 && ( (this.getPpsCode()==castOther.getPpsCode()) || ( this.getPpsCode()!=null && castOther.getPpsCode()!=null && this.getPpsCode().equals(castOther.getPpsCode()) ) )
 && ( (this.getDateFr()==castOther.getDateFr()) || ( this.getDateFr()!=null && castOther.getDateFr()!=null && this.getDateFr().equals(castOther.getDateFr()) ) )
 && ( (this.getDateTo()==castOther.getDateTo()) || ( this.getDateTo()!=null && castOther.getDateTo()!=null && this.getDateTo().equals(castOther.getDateTo()) ) )
 && ( (this.getMinBal()==castOther.getMinBal()) || ( this.getMinBal()!=null && castOther.getMinBal()!=null && this.getMinBal().equals(castOther.getMinBal()) ) )
 && ( (this.getMaxBal()==castOther.getMaxBal()) || ( this.getMaxBal()!=null && castOther.getMaxBal()!=null && this.getMaxBal().equals(castOther.getMaxBal()) ) )
 && ( (this.getRate()==castOther.getRate()) || ( this.getRate()!=null && castOther.getRate()!=null && this.getRate().equals(castOther.getRate()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDtTime()==castOther.getDeDtTime()) || ( this.getDeDtTime()!=null && castOther.getDeDtTime()!=null && this.getDeDtTime().equals(castOther.getDeDtTime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getLnType() == null ? 0 : this.getLnType().hashCode() );
         result = 37 * result + ( getPpsCode() == null ? 0 : this.getPpsCode().hashCode() );
         result = 37 * result + ( getDateFr() == null ? 0 : this.getDateFr().hashCode() );
         result = 37 * result + ( getDateTo() == null ? 0 : this.getDateTo().hashCode() );
         result = 37 * result + ( getMinBal() == null ? 0 : this.getMinBal().hashCode() );
         result = 37 * result + ( getMaxBal() == null ? 0 : this.getMaxBal().hashCode() );
         result = 37 * result + ( getRate() == null ? 0 : this.getRate().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDtTime() == null ? 0 : this.getDeDtTime().hashCode() );
         return result;
   }   





}