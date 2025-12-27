package com.banking.data.pojo;
// default package



/**
 * DepositInterestRateId entity. @author MyEclipse Persistence Tools
 */

public class DepositInterestRateId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private String dateFr;
     private String dateTo;
     private Integer daysFr;
     private Integer daysTo;
     private Double intRate;
     private String deTml;
     private String deUser;
     private String deDate;


    // Constructors

    /** default constructor */
    public DepositInterestRateId() {
    }

    
    /** full constructor */
    public DepositInterestRateId(String acType, String dateFr, String dateTo, Integer daysFr, Integer daysTo, Double intRate, String deTml, String deUser, String deDate) {
        this.acType = acType;
        this.dateFr = dateFr;
        this.dateTo = dateTo;
        this.daysFr = daysFr;
        this.daysTo = daysTo;
        this.intRate = intRate;
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

    public Integer getDaysFr() {
        return this.daysFr;
    }
    
    public void setDaysFr(Integer daysFr) {
        this.daysFr = daysFr;
    }

    public Integer getDaysTo() {
        return this.daysTo;
    }
    
    public void setDaysTo(Integer daysTo) {
        this.daysTo = daysTo;
    }

    public Double getIntRate() {
        return this.intRate;
    }
    
    public void setIntRate(Double intRate) {
        this.intRate = intRate;
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
		 if ( !(other instanceof DepositInterestRateId) ) return false;
		 DepositInterestRateId castOther = ( DepositInterestRateId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getDateFr()==castOther.getDateFr()) || ( this.getDateFr()!=null && castOther.getDateFr()!=null && this.getDateFr().equals(castOther.getDateFr()) ) )
 && ( (this.getDateTo()==castOther.getDateTo()) || ( this.getDateTo()!=null && castOther.getDateTo()!=null && this.getDateTo().equals(castOther.getDateTo()) ) )
 && ( (this.getDaysFr()==castOther.getDaysFr()) || ( this.getDaysFr()!=null && castOther.getDaysFr()!=null && this.getDaysFr().equals(castOther.getDaysFr()) ) )
 && ( (this.getDaysTo()==castOther.getDaysTo()) || ( this.getDaysTo()!=null && castOther.getDaysTo()!=null && this.getDaysTo().equals(castOther.getDaysTo()) ) )
 && ( (this.getIntRate()==castOther.getIntRate()) || ( this.getIntRate()!=null && castOther.getIntRate()!=null && this.getIntRate().equals(castOther.getIntRate()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getDateFr() == null ? 0 : this.getDateFr().hashCode() );
         result = 37 * result + ( getDateTo() == null ? 0 : this.getDateTo().hashCode() );
         result = 37 * result + ( getDaysFr() == null ? 0 : this.getDaysFr().hashCode() );
         result = 37 * result + ( getDaysTo() == null ? 0 : this.getDaysTo().hashCode() );
         result = 37 * result + ( getIntRate() == null ? 0 : this.getIntRate().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         return result;
   }   





}