package com.banking.data.pojo;
// default package



/**
 * DepositCategoryRateId entity. @author MyEclipse Persistence Tools
 */

public class DepositCategoryRateId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private Integer category;
     private String dateFr;
     private String dateTo;
     private Integer daysFr;
     private Integer daysTo;
     private Double extraIntRate;
     private Double extraLnintRate;
     private String deTml;
     private String deUser;
     private String deDate;


    // Constructors

    /** default constructor */
    public DepositCategoryRateId() {
    }

    
    /** full constructor */
    public DepositCategoryRateId(String acType, Integer category, String dateFr, String dateTo, Integer daysFr, Integer daysTo, Double extraIntRate, Double extraLnintRate, String deTml, String deUser, String deDate) {
        this.acType = acType;
        this.category = category;
        this.dateFr = dateFr;
        this.dateTo = dateTo;
        this.daysFr = daysFr;
        this.daysTo = daysTo;
        this.extraIntRate = extraIntRate;
        this.extraLnintRate = extraLnintRate;
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

    public Double getExtraIntRate() {
        return this.extraIntRate;
    }
    
    public void setExtraIntRate(Double extraIntRate) {
        this.extraIntRate = extraIntRate;
    }

    public Double getExtraLnintRate() {
        return this.extraLnintRate;
    }
    
    public void setExtraLnintRate(Double extraLnintRate) {
        this.extraLnintRate = extraLnintRate;
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
		 if ( !(other instanceof DepositCategoryRateId) ) return false;
		 DepositCategoryRateId castOther = ( DepositCategoryRateId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getCategory()==castOther.getCategory()) || ( this.getCategory()!=null && castOther.getCategory()!=null && this.getCategory().equals(castOther.getCategory()) ) )
 && ( (this.getDateFr()==castOther.getDateFr()) || ( this.getDateFr()!=null && castOther.getDateFr()!=null && this.getDateFr().equals(castOther.getDateFr()) ) )
 && ( (this.getDateTo()==castOther.getDateTo()) || ( this.getDateTo()!=null && castOther.getDateTo()!=null && this.getDateTo().equals(castOther.getDateTo()) ) )
 && ( (this.getDaysFr()==castOther.getDaysFr()) || ( this.getDaysFr()!=null && castOther.getDaysFr()!=null && this.getDaysFr().equals(castOther.getDaysFr()) ) )
 && ( (this.getDaysTo()==castOther.getDaysTo()) || ( this.getDaysTo()!=null && castOther.getDaysTo()!=null && this.getDaysTo().equals(castOther.getDaysTo()) ) )
 && ( (this.getExtraIntRate()==castOther.getExtraIntRate()) || ( this.getExtraIntRate()!=null && castOther.getExtraIntRate()!=null && this.getExtraIntRate().equals(castOther.getExtraIntRate()) ) )
 && ( (this.getExtraLnintRate()==castOther.getExtraLnintRate()) || ( this.getExtraLnintRate()!=null && castOther.getExtraLnintRate()!=null && this.getExtraLnintRate().equals(castOther.getExtraLnintRate()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getCategory() == null ? 0 : this.getCategory().hashCode() );
         result = 37 * result + ( getDateFr() == null ? 0 : this.getDateFr().hashCode() );
         result = 37 * result + ( getDateTo() == null ? 0 : this.getDateTo().hashCode() );
         result = 37 * result + ( getDaysFr() == null ? 0 : this.getDaysFr().hashCode() );
         result = 37 * result + ( getDaysTo() == null ? 0 : this.getDaysTo().hashCode() );
         result = 37 * result + ( getExtraIntRate() == null ? 0 : this.getExtraIntRate().hashCode() );
         result = 37 * result + ( getExtraLnintRate() == null ? 0 : this.getExtraLnintRate().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         return result;
   }   





}