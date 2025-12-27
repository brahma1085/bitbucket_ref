package com.banking.data.pojo;
// default package



/**
 * LockerCategoryRateId entity. @author MyEclipse Persistence Tools
 */

public class LockerCategoryRateId  implements java.io.Serializable {


    // Fields    

     private String lockerType;
     private Integer category;
     private String dateFr;
     private String dateTo;
     private Integer daysFr;
     private Integer daysTo;
     private Double extraLockerRate;
     private String deUser;
     private String deTml;
     private String deDate;


    // Constructors

    /** default constructor */
    public LockerCategoryRateId() {
    }

    
    /** full constructor */
    public LockerCategoryRateId(String lockerType, Integer category, String dateFr, String dateTo, Integer daysFr, Integer daysTo, Double extraLockerRate, String deUser, String deTml, String deDate) {
        this.lockerType = lockerType;
        this.category = category;
        this.dateFr = dateFr;
        this.dateTo = dateTo;
        this.daysFr = daysFr;
        this.daysTo = daysTo;
        this.extraLockerRate = extraLockerRate;
        this.deUser = deUser;
        this.deTml = deTml;
        this.deDate = deDate;
    }

   
    // Property accessors

    public String getLockerType() {
        return this.lockerType;
    }
    
    public void setLockerType(String lockerType) {
        this.lockerType = lockerType;
    }

    public Integer getCategory() {
        return this.category;
    }
    
    public void setCategory(Integer category) {
        this.category = category;
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

    public Double getExtraLockerRate() {
        return this.extraLockerRate;
    }
    
    public void setExtraLockerRate(Double extraLockerRate) {
        this.extraLockerRate = extraLockerRate;
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
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof LockerCategoryRateId) ) return false;
		 LockerCategoryRateId castOther = ( LockerCategoryRateId ) other; 
         
		 return ( (this.getLockerType()==castOther.getLockerType()) || ( this.getLockerType()!=null && castOther.getLockerType()!=null && this.getLockerType().equals(castOther.getLockerType()) ) )
 && ( (this.getCategory()==castOther.getCategory()) || ( this.getCategory()!=null && castOther.getCategory()!=null && this.getCategory().equals(castOther.getCategory()) ) )
 && ( (this.getDateFr()==castOther.getDateFr()) || ( this.getDateFr()!=null && castOther.getDateFr()!=null && this.getDateFr().equals(castOther.getDateFr()) ) )
 && ( (this.getDateTo()==castOther.getDateTo()) || ( this.getDateTo()!=null && castOther.getDateTo()!=null && this.getDateTo().equals(castOther.getDateTo()) ) )
 && ( (this.getDaysFr()==castOther.getDaysFr()) || ( this.getDaysFr()!=null && castOther.getDaysFr()!=null && this.getDaysFr().equals(castOther.getDaysFr()) ) )
 && ( (this.getDaysTo()==castOther.getDaysTo()) || ( this.getDaysTo()!=null && castOther.getDaysTo()!=null && this.getDaysTo().equals(castOther.getDaysTo()) ) )
 && ( (this.getExtraLockerRate()==castOther.getExtraLockerRate()) || ( this.getExtraLockerRate()!=null && castOther.getExtraLockerRate()!=null && this.getExtraLockerRate().equals(castOther.getExtraLockerRate()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getLockerType() == null ? 0 : this.getLockerType().hashCode() );
         result = 37 * result + ( getCategory() == null ? 0 : this.getCategory().hashCode() );
         result = 37 * result + ( getDateFr() == null ? 0 : this.getDateFr().hashCode() );
         result = 37 * result + ( getDateTo() == null ? 0 : this.getDateTo().hashCode() );
         result = 37 * result + ( getDaysFr() == null ? 0 : this.getDaysFr().hashCode() );
         result = 37 * result + ( getDaysTo() == null ? 0 : this.getDaysTo().hashCode() );
         result = 37 * result + ( getExtraLockerRate() == null ? 0 : this.getExtraLockerRate().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         return result;
   }   





}