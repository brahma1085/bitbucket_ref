package com.banking.data.pojo;
// default package

import java.sql.Timestamp;
import java.util.Date;


/**
 * DailyStatusSummaryId entity. @author MyEclipse Persistence Tools
 */

public class DailyStatusSummaryId  implements java.io.Serializable {


    // Fields    

     private Date trnDt;
     private String glType;
     private Integer glCode;
     private Double cashDr;
     private Double cgDr;
     private Double trfDr;
     private Double cashCr;
     private Double cgCr;
     private Double trfCr;
     private String deTml;
     private String deUser;
     private Timestamp deDateTime;


    // Constructors

    /** default constructor */
    public DailyStatusSummaryId() {
    }

    
    /** full constructor */
    public DailyStatusSummaryId(Date trnDt, String glType, Integer glCode, Double cashDr, Double cgDr, Double trfDr, Double cashCr, Double cgCr, Double trfCr, String deTml, String deUser, Timestamp deDateTime) {
        this.trnDt = trnDt;
        this.glType = glType;
        this.glCode = glCode;
        this.cashDr = cashDr;
        this.cgDr = cgDr;
        this.trfDr = trfDr;
        this.cashCr = cashCr;
        this.cgCr = cgCr;
        this.trfCr = trfCr;
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

    public String getGlType() {
        return this.glType;
    }
    
    public void setGlType(String glType) {
        this.glType = glType;
    }

    public Integer getGlCode() {
        return this.glCode;
    }
    
    public void setGlCode(Integer glCode) {
        this.glCode = glCode;
    }

    public Double getCashDr() {
        return this.cashDr;
    }
    
    public void setCashDr(Double cashDr) {
        this.cashDr = cashDr;
    }

    public Double getCgDr() {
        return this.cgDr;
    }
    
    public void setCgDr(Double cgDr) {
        this.cgDr = cgDr;
    }

    public Double getTrfDr() {
        return this.trfDr;
    }
    
    public void setTrfDr(Double trfDr) {
        this.trfDr = trfDr;
    }

    public Double getCashCr() {
        return this.cashCr;
    }
    
    public void setCashCr(Double cashCr) {
        this.cashCr = cashCr;
    }

    public Double getCgCr() {
        return this.cgCr;
    }
    
    public void setCgCr(Double cgCr) {
        this.cgCr = cgCr;
    }

    public Double getTrfCr() {
        return this.trfCr;
    }
    
    public void setTrfCr(Double trfCr) {
        this.trfCr = trfCr;
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
		 if ( !(other instanceof DailyStatusSummaryId) ) return false;
		 DailyStatusSummaryId castOther = ( DailyStatusSummaryId ) other; 
         
		 return ( (this.getTrnDt()==castOther.getTrnDt()) || ( this.getTrnDt()!=null && castOther.getTrnDt()!=null && this.getTrnDt().equals(castOther.getTrnDt()) ) )
 && ( (this.getGlType()==castOther.getGlType()) || ( this.getGlType()!=null && castOther.getGlType()!=null && this.getGlType().equals(castOther.getGlType()) ) )
 && ( (this.getGlCode()==castOther.getGlCode()) || ( this.getGlCode()!=null && castOther.getGlCode()!=null && this.getGlCode().equals(castOther.getGlCode()) ) )
 && ( (this.getCashDr()==castOther.getCashDr()) || ( this.getCashDr()!=null && castOther.getCashDr()!=null && this.getCashDr().equals(castOther.getCashDr()) ) )
 && ( (this.getCgDr()==castOther.getCgDr()) || ( this.getCgDr()!=null && castOther.getCgDr()!=null && this.getCgDr().equals(castOther.getCgDr()) ) )
 && ( (this.getTrfDr()==castOther.getTrfDr()) || ( this.getTrfDr()!=null && castOther.getTrfDr()!=null && this.getTrfDr().equals(castOther.getTrfDr()) ) )
 && ( (this.getCashCr()==castOther.getCashCr()) || ( this.getCashCr()!=null && castOther.getCashCr()!=null && this.getCashCr().equals(castOther.getCashCr()) ) )
 && ( (this.getCgCr()==castOther.getCgCr()) || ( this.getCgCr()!=null && castOther.getCgCr()!=null && this.getCgCr().equals(castOther.getCgCr()) ) )
 && ( (this.getTrfCr()==castOther.getTrfCr()) || ( this.getTrfCr()!=null && castOther.getTrfCr()!=null && this.getTrfCr().equals(castOther.getTrfCr()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDateTime()==castOther.getDeDateTime()) || ( this.getDeDateTime()!=null && castOther.getDeDateTime()!=null && this.getDeDateTime().equals(castOther.getDeDateTime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getTrnDt() == null ? 0 : this.getTrnDt().hashCode() );
         result = 37 * result + ( getGlType() == null ? 0 : this.getGlType().hashCode() );
         result = 37 * result + ( getGlCode() == null ? 0 : this.getGlCode().hashCode() );
         result = 37 * result + ( getCashDr() == null ? 0 : this.getCashDr().hashCode() );
         result = 37 * result + ( getCgDr() == null ? 0 : this.getCgDr().hashCode() );
         result = 37 * result + ( getTrfDr() == null ? 0 : this.getTrfDr().hashCode() );
         result = 37 * result + ( getCashCr() == null ? 0 : this.getCashCr().hashCode() );
         result = 37 * result + ( getCgCr() == null ? 0 : this.getCgCr().hashCode() );
         result = 37 * result + ( getTrfCr() == null ? 0 : this.getTrfCr().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDateTime() == null ? 0 : this.getDeDateTime().hashCode() );
         return result;
   }   





}