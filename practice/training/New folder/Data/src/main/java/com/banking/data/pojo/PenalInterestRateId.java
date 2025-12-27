package com.banking.data.pojo;
// default package



/**
 * PenalInterestRateId entity. @author MyEclipse Persistence Tools
 */

public class PenalInterestRateId  implements java.io.Serializable {


    // Fields    

     private String lnType;
     private String dateFr;
     private String dateTo;
     private Integer category;
     private Double penalrate;
     private String deTml;
     private String deUser;
     private String deDtTime;


    // Constructors

    /** default constructor */
    public PenalInterestRateId() {
    }

    
    /** full constructor */
    public PenalInterestRateId(String lnType, String dateFr, String dateTo, Integer category, Double penalrate, String deTml, String deUser, String deDtTime) {
        this.lnType = lnType;
        this.dateFr = dateFr;
        this.dateTo = dateTo;
        this.category = category;
        this.penalrate = penalrate;
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

    public Integer getCategory() {
        return this.category;
    }
    
    public void setCategory(Integer category) {
        this.category = category;
    }

    public Double getPenalrate() {
        return this.penalrate;
    }
    
    public void setPenalrate(Double penalrate) {
        this.penalrate = penalrate;
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
		 if ( !(other instanceof PenalInterestRateId) ) return false;
		 PenalInterestRateId castOther = ( PenalInterestRateId ) other; 
         
		 return ( (this.getLnType()==castOther.getLnType()) || ( this.getLnType()!=null && castOther.getLnType()!=null && this.getLnType().equals(castOther.getLnType()) ) )
 && ( (this.getDateFr()==castOther.getDateFr()) || ( this.getDateFr()!=null && castOther.getDateFr()!=null && this.getDateFr().equals(castOther.getDateFr()) ) )
 && ( (this.getDateTo()==castOther.getDateTo()) || ( this.getDateTo()!=null && castOther.getDateTo()!=null && this.getDateTo().equals(castOther.getDateTo()) ) )
 && ( (this.getCategory()==castOther.getCategory()) || ( this.getCategory()!=null && castOther.getCategory()!=null && this.getCategory().equals(castOther.getCategory()) ) )
 && ( (this.getPenalrate()==castOther.getPenalrate()) || ( this.getPenalrate()!=null && castOther.getPenalrate()!=null && this.getPenalrate().equals(castOther.getPenalrate()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDtTime()==castOther.getDeDtTime()) || ( this.getDeDtTime()!=null && castOther.getDeDtTime()!=null && this.getDeDtTime().equals(castOther.getDeDtTime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getLnType() == null ? 0 : this.getLnType().hashCode() );
         result = 37 * result + ( getDateFr() == null ? 0 : this.getDateFr().hashCode() );
         result = 37 * result + ( getDateTo() == null ? 0 : this.getDateTo().hashCode() );
         result = 37 * result + ( getCategory() == null ? 0 : this.getCategory().hashCode() );
         result = 37 * result + ( getPenalrate() == null ? 0 : this.getPenalrate().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDtTime() == null ? 0 : this.getDeDtTime().hashCode() );
         return result;
   }   





}