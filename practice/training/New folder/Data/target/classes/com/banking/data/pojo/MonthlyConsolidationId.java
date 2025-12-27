package com.banking.data.pojo;
// default package

import java.sql.Timestamp;


/**
 * MonthlyConsolidationId entity. @author MyEclipse Persistence Tools
 */

public class MonthlyConsolidationId  implements java.io.Serializable {


    // Fields    

     private Integer brCode;
     private Integer yrMth;
     private String recordType;
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
     private String veTml;
     private String veUser;
     private Timestamp veDateTime;


    // Constructors

    /** default constructor */
    public MonthlyConsolidationId() {
    }

    
    /** full constructor */
    public MonthlyConsolidationId(Integer brCode, Integer yrMth, String recordType, String glType, Integer glCode, Double cashDr, Double cgDr, Double trfDr, Double cashCr, Double cgCr, Double trfCr, String deTml, String deUser, Timestamp deDateTime, String veTml, String veUser, Timestamp veDateTime) {
        this.brCode = brCode;
        this.yrMth = yrMth;
        this.recordType = recordType;
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
        this.veTml = veTml;
        this.veUser = veUser;
        this.veDateTime = veDateTime;
    }

   
    // Property accessors

    public Integer getBrCode() {
        return this.brCode;
    }
    
    public void setBrCode(Integer brCode) {
        this.brCode = brCode;
    }

    public Integer getYrMth() {
        return this.yrMth;
    }
    
    public void setYrMth(Integer yrMth) {
        this.yrMth = yrMth;
    }

    public String getRecordType() {
        return this.recordType;
    }
    
    public void setRecordType(String recordType) {
        this.recordType = recordType;
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

    public String getVeTml() {
        return this.veTml;
    }
    
    public void setVeTml(String veTml) {
        this.veTml = veTml;
    }

    public String getVeUser() {
        return this.veUser;
    }
    
    public void setVeUser(String veUser) {
        this.veUser = veUser;
    }

    public Timestamp getVeDateTime() {
        return this.veDateTime;
    }
    
    public void setVeDateTime(Timestamp veDateTime) {
        this.veDateTime = veDateTime;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof MonthlyConsolidationId) ) return false;
		 MonthlyConsolidationId castOther = ( MonthlyConsolidationId ) other; 
         
		 return ( (this.getBrCode()==castOther.getBrCode()) || ( this.getBrCode()!=null && castOther.getBrCode()!=null && this.getBrCode().equals(castOther.getBrCode()) ) )
 && ( (this.getYrMth()==castOther.getYrMth()) || ( this.getYrMth()!=null && castOther.getYrMth()!=null && this.getYrMth().equals(castOther.getYrMth()) ) )
 && ( (this.getRecordType()==castOther.getRecordType()) || ( this.getRecordType()!=null && castOther.getRecordType()!=null && this.getRecordType().equals(castOther.getRecordType()) ) )
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
 && ( (this.getDeDateTime()==castOther.getDeDateTime()) || ( this.getDeDateTime()!=null && castOther.getDeDateTime()!=null && this.getDeDateTime().equals(castOther.getDeDateTime()) ) )
 && ( (this.getVeTml()==castOther.getVeTml()) || ( this.getVeTml()!=null && castOther.getVeTml()!=null && this.getVeTml().equals(castOther.getVeTml()) ) )
 && ( (this.getVeUser()==castOther.getVeUser()) || ( this.getVeUser()!=null && castOther.getVeUser()!=null && this.getVeUser().equals(castOther.getVeUser()) ) )
 && ( (this.getVeDateTime()==castOther.getVeDateTime()) || ( this.getVeDateTime()!=null && castOther.getVeDateTime()!=null && this.getVeDateTime().equals(castOther.getVeDateTime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getBrCode() == null ? 0 : this.getBrCode().hashCode() );
         result = 37 * result + ( getYrMth() == null ? 0 : this.getYrMth().hashCode() );
         result = 37 * result + ( getRecordType() == null ? 0 : this.getRecordType().hashCode() );
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
         result = 37 * result + ( getVeTml() == null ? 0 : this.getVeTml().hashCode() );
         result = 37 * result + ( getVeUser() == null ? 0 : this.getVeUser().hashCode() );
         result = 37 * result + ( getVeDateTime() == null ? 0 : this.getVeDateTime().hashCode() );
         return result;
   }   





}