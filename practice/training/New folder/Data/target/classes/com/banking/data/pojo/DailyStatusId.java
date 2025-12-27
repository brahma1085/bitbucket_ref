package com.banking.data.pojo;
// default package

import java.sql.Timestamp;
import java.util.Date;


/**
 * DailyStatusId entity. @author MyEclipse Persistence Tools
 */

public class DailyStatusId  implements java.io.Serializable {


    // Fields    

     private Date trnDt;
     private String postInd;
     private String cashClose;
     private String dayClose;
     private String deTml;
     private String deUser;
     private Timestamp deDateTime;


    // Constructors

    /** default constructor */
    public DailyStatusId() {
    }

    
    /** full constructor */
    public DailyStatusId(Date trnDt, String postInd, String cashClose, String dayClose, String deTml, String deUser, Timestamp deDateTime) {
        this.trnDt = trnDt;
        this.postInd = postInd;
        this.cashClose = cashClose;
        this.dayClose = dayClose;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDateTime = deDateTime;
    }

   
    // Property accessors

    public Date getTrnDt() {
        return this.trnDt;
    }
    
    public void setTrnDt(Date trnDt) {
        this.trnDt = trnDt;
    }

    public String getPostInd() {
        return this.postInd;
    }
    
    public void setPostInd(String postInd) {
        this.postInd = postInd;
    }

    public String getCashClose() {
        return this.cashClose;
    }
    
    public void setCashClose(String cashClose) {
        this.cashClose = cashClose;
    }

    public String getDayClose() {
        return this.dayClose;
    }
    
    public void setDayClose(String dayClose) {
        this.dayClose = dayClose;
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

    public Timestamp getDeDateTime() {
        return this.deDateTime;
    }
    
    public void setDeDateTime(Timestamp deDateTime) {
        this.deDateTime = deDateTime;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof DailyStatusId) ) return false;
		 DailyStatusId castOther = ( DailyStatusId ) other; 
         
		 return ( (this.getTrnDt()==castOther.getTrnDt()) || ( this.getTrnDt()!=null && castOther.getTrnDt()!=null && this.getTrnDt().equals(castOther.getTrnDt()) ) )
 && ( (this.getPostInd()==castOther.getPostInd()) || ( this.getPostInd()!=null && castOther.getPostInd()!=null && this.getPostInd().equals(castOther.getPostInd()) ) )
 && ( (this.getCashClose()==castOther.getCashClose()) || ( this.getCashClose()!=null && castOther.getCashClose()!=null && this.getCashClose().equals(castOther.getCashClose()) ) )
 && ( (this.getDayClose()==castOther.getDayClose()) || ( this.getDayClose()!=null && castOther.getDayClose()!=null && this.getDayClose().equals(castOther.getDayClose()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDateTime()==castOther.getDeDateTime()) || ( this.getDeDateTime()!=null && castOther.getDeDateTime()!=null && this.getDeDateTime().equals(castOther.getDeDateTime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getTrnDt() == null ? 0 : this.getTrnDt().hashCode() );
         result = 37 * result + ( getPostInd() == null ? 0 : this.getPostInd().hashCode() );
         result = 37 * result + ( getCashClose() == null ? 0 : this.getCashClose().hashCode() );
         result = 37 * result + ( getDayClose() == null ? 0 : this.getDayClose().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDateTime() == null ? 0 : this.getDeDateTime().hashCode() );
         return result;
   }   





}