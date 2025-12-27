package com.banking.data.pojo;
// default package

import java.sql.Timestamp;
import java.util.Date;


/**
 * PrecedingForNightId entity. @author MyEclipse Persistence Tools
 */

public class PrecedingForNightId  implements java.io.Serializable {


    // Fields    

     private String dayOfWeek;
     private Date markingDt;
     private Integer recurringDays;
     private Double ndtlamount;
     private String deTml;
     private String deUser;
     private Timestamp deDtTime;


    // Constructors

    /** default constructor */
    public PrecedingForNightId() {
    }

    
    /** full constructor */
    public PrecedingForNightId(String dayOfWeek, Date markingDt, Integer recurringDays, Double ndtlamount, String deTml, String deUser, Timestamp deDtTime) {
        this.dayOfWeek = dayOfWeek;
        this.markingDt = markingDt;
        this.recurringDays = recurringDays;
        this.ndtlamount = ndtlamount;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDtTime = deDtTime;
    }

   
    // Property accessors

    public String getDayOfWeek() {
        return this.dayOfWeek;
    }
    
    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Date getMarkingDt() {
        return this.markingDt;
    }
    
    public void setMarkingDt(Date markingDt) {
        this.markingDt = markingDt;
    }

    public Integer getRecurringDays() {
        return this.recurringDays;
    }
    
    public void setRecurringDays(Integer recurringDays) {
        this.recurringDays = recurringDays;
    }

    public Double getNdtlamount() {
        return this.ndtlamount;
    }
    
    public void setNdtlamount(Double ndtlamount) {
        this.ndtlamount = ndtlamount;
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

    public Timestamp getDeDtTime() {
        return this.deDtTime;
    }
    
    public void setDeDtTime(Timestamp deDtTime) {
        this.deDtTime = deDtTime;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof PrecedingForNightId) ) return false;
		 PrecedingForNightId castOther = ( PrecedingForNightId ) other; 
         
		 return ( (this.getDayOfWeek()==castOther.getDayOfWeek()) || ( this.getDayOfWeek()!=null && castOther.getDayOfWeek()!=null && this.getDayOfWeek().equals(castOther.getDayOfWeek()) ) )
 && ( (this.getMarkingDt()==castOther.getMarkingDt()) || ( this.getMarkingDt()!=null && castOther.getMarkingDt()!=null && this.getMarkingDt().equals(castOther.getMarkingDt()) ) )
 && ( (this.getRecurringDays()==castOther.getRecurringDays()) || ( this.getRecurringDays()!=null && castOther.getRecurringDays()!=null && this.getRecurringDays().equals(castOther.getRecurringDays()) ) )
 && ( (this.getNdtlamount()==castOther.getNdtlamount()) || ( this.getNdtlamount()!=null && castOther.getNdtlamount()!=null && this.getNdtlamount().equals(castOther.getNdtlamount()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDtTime()==castOther.getDeDtTime()) || ( this.getDeDtTime()!=null && castOther.getDeDtTime()!=null && this.getDeDtTime().equals(castOther.getDeDtTime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getDayOfWeek() == null ? 0 : this.getDayOfWeek().hashCode() );
         result = 37 * result + ( getMarkingDt() == null ? 0 : this.getMarkingDt().hashCode() );
         result = 37 * result + ( getRecurringDays() == null ? 0 : this.getRecurringDays().hashCode() );
         result = 37 * result + ( getNdtlamount() == null ? 0 : this.getNdtlamount().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDtTime() == null ? 0 : this.getDeDtTime().hashCode() );
         return result;
   }   





}