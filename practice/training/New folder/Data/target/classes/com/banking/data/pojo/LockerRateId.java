package com.banking.data.pojo;
// default package



/**
 * LockerRateId entity. @author MyEclipse Persistence Tools
 */

public class LockerRateId  implements java.io.Serializable {


    // Fields    

     private String lockerType;
     private String dateFr;
     private String dateTo;
     private Integer daysFr;
     private Integer daysTo;
     private Double lockerRate;
     private String deUser;
     private String deTml;
     private String deDate;
     private Double securityDeposit;


    // Constructors

    /** default constructor */
    public LockerRateId() {
    }

    
    /** full constructor */
    public LockerRateId(String lockerType, String dateFr, String dateTo, Integer daysFr, Integer daysTo, Double lockerRate, String deUser, String deTml, String deDate, Double securityDeposit) {
        this.lockerType = lockerType;
        this.dateFr = dateFr;
        this.dateTo = dateTo;
        this.daysFr = daysFr;
        this.daysTo = daysTo;
        this.lockerRate = lockerRate;
        this.deUser = deUser;
        this.deTml = deTml;
        this.deDate = deDate;
        this.securityDeposit = securityDeposit;
    }

   
    // Property accessors

    public String getLockerType() {
        return this.lockerType;
    }
    
    public void setLockerType(String lockerType) {
        this.lockerType = lockerType;
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

    public Double getLockerRate() {
        return this.lockerRate;
    }
    
    public void setLockerRate(Double lockerRate) {
        this.lockerRate = lockerRate;
    }

    public String getDeUser() {
        return this.deUser;
    }
    
    public void setDeUser(String deUser) {
        this.deUser = deUser;
    }

    public String getDeTml() {
        return this.deTml;
    }
    
    public void setDeTml(String deTml) {
        this.deTml = deTml;
    }

    public String getDeDate() {
        return this.deDate;
    }
    
    public void setDeDate(String deDate) {
        this.deDate = deDate;
    }

    public Double getSecurityDeposit() {
        return this.securityDeposit;
    }
    
    public void setSecurityDeposit(Double securityDeposit) {
        this.securityDeposit = securityDeposit;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof LockerRateId) ) return false;
		 LockerRateId castOther = ( LockerRateId ) other; 
         
		 return ( (this.getLockerType()==castOther.getLockerType()) || ( this.getLockerType()!=null && castOther.getLockerType()!=null && this.getLockerType().equals(castOther.getLockerType()) ) )
 && ( (this.getDateFr()==castOther.getDateFr()) || ( this.getDateFr()!=null && castOther.getDateFr()!=null && this.getDateFr().equals(castOther.getDateFr()) ) )
 && ( (this.getDateTo()==castOther.getDateTo()) || ( this.getDateTo()!=null && castOther.getDateTo()!=null && this.getDateTo().equals(castOther.getDateTo()) ) )
 && ( (this.getDaysFr()==castOther.getDaysFr()) || ( this.getDaysFr()!=null && castOther.getDaysFr()!=null && this.getDaysFr().equals(castOther.getDaysFr()) ) )
 && ( (this.getDaysTo()==castOther.getDaysTo()) || ( this.getDaysTo()!=null && castOther.getDaysTo()!=null && this.getDaysTo().equals(castOther.getDaysTo()) ) )
 && ( (this.getLockerRate()==castOther.getLockerRate()) || ( this.getLockerRate()!=null && castOther.getLockerRate()!=null && this.getLockerRate().equals(castOther.getLockerRate()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) )
 && ( (this.getSecurityDeposit()==castOther.getSecurityDeposit()) || ( this.getSecurityDeposit()!=null && castOther.getSecurityDeposit()!=null && this.getSecurityDeposit().equals(castOther.getSecurityDeposit()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getLockerType() == null ? 0 : this.getLockerType().hashCode() );
         result = 37 * result + ( getDateFr() == null ? 0 : this.getDateFr().hashCode() );
         result = 37 * result + ( getDateTo() == null ? 0 : this.getDateTo().hashCode() );
         result = 37 * result + ( getDaysFr() == null ? 0 : this.getDaysFr().hashCode() );
         result = 37 * result + ( getDaysTo() == null ? 0 : this.getDaysTo().hashCode() );
         result = 37 * result + ( getLockerRate() == null ? 0 : this.getLockerRate().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         result = 37 * result + ( getSecurityDeposit() == null ? 0 : this.getSecurityDeposit().hashCode() );
         return result;
   }   





}