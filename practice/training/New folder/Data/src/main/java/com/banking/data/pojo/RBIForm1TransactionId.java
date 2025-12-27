package com.banking.data.pojo;
// default package

import java.sql.Timestamp;
import java.util.Date;


/**
 * RBIForm1TransactionId entity. @author MyEclipse Persistence Tools
 */

public class RBIForm1TransactionId  implements java.io.Serializable {


    // Fields    

     private Integer code;
     private String trnSrc;
     private String glType;
     private Integer glCode;
     private Date trnDate;
     private Double closingBal;
     private Integer sequence;
     private String deTml;
     private String deUser;
     private Timestamp deDtTime;


    // Constructors

    /** default constructor */
    public RBIForm1TransactionId() {
    }

    
    /** full constructor */
    public RBIForm1TransactionId(Integer code, String trnSrc, String glType, Integer glCode, Date trnDate, Double closingBal, Integer sequence, String deTml, String deUser, Timestamp deDtTime) {
        this.code = code;
        this.trnSrc = trnSrc;
        this.glType = glType;
        this.glCode = glCode;
        this.trnDate = trnDate;
        this.closingBal = closingBal;
        this.sequence = sequence;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDtTime = deDtTime;
    }

   
    // Property accessors

    public Integer getCode() {
        return this.code;
    }
    
    public void setCode(Integer code) {
        this.code = code;
    }

    public String getTrnSrc() {
        return this.trnSrc;
    }
    
    public void setTrnSrc(String trnSrc) {
        this.trnSrc = trnSrc;
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

    public Date getTrnDate() {
        return this.trnDate;
    }
    
    public void setTrnDate(Date trnDate) {
        this.trnDate = trnDate;
    }

    public Double getClosingBal() {
        return this.closingBal;
    }
    
    public void setClosingBal(Double closingBal) {
        this.closingBal = closingBal;
    }

    public Integer getSequence() {
        return this.sequence;
    }
    
    public void setSequence(Integer sequence) {
        this.sequence = sequence;
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
		 if ( !(other instanceof RBIForm1TransactionId) ) return false;
		 RBIForm1TransactionId castOther = ( RBIForm1TransactionId ) other; 
         
		 return ( (this.getCode()==castOther.getCode()) || ( this.getCode()!=null && castOther.getCode()!=null && this.getCode().equals(castOther.getCode()) ) )
 && ( (this.getTrnSrc()==castOther.getTrnSrc()) || ( this.getTrnSrc()!=null && castOther.getTrnSrc()!=null && this.getTrnSrc().equals(castOther.getTrnSrc()) ) )
 && ( (this.getGlType()==castOther.getGlType()) || ( this.getGlType()!=null && castOther.getGlType()!=null && this.getGlType().equals(castOther.getGlType()) ) )
 && ( (this.getGlCode()==castOther.getGlCode()) || ( this.getGlCode()!=null && castOther.getGlCode()!=null && this.getGlCode().equals(castOther.getGlCode()) ) )
 && ( (this.getTrnDate()==castOther.getTrnDate()) || ( this.getTrnDate()!=null && castOther.getTrnDate()!=null && this.getTrnDate().equals(castOther.getTrnDate()) ) )
 && ( (this.getClosingBal()==castOther.getClosingBal()) || ( this.getClosingBal()!=null && castOther.getClosingBal()!=null && this.getClosingBal().equals(castOther.getClosingBal()) ) )
 && ( (this.getSequence()==castOther.getSequence()) || ( this.getSequence()!=null && castOther.getSequence()!=null && this.getSequence().equals(castOther.getSequence()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDtTime()==castOther.getDeDtTime()) || ( this.getDeDtTime()!=null && castOther.getDeDtTime()!=null && this.getDeDtTime().equals(castOther.getDeDtTime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getCode() == null ? 0 : this.getCode().hashCode() );
         result = 37 * result + ( getTrnSrc() == null ? 0 : this.getTrnSrc().hashCode() );
         result = 37 * result + ( getGlType() == null ? 0 : this.getGlType().hashCode() );
         result = 37 * result + ( getGlCode() == null ? 0 : this.getGlCode().hashCode() );
         result = 37 * result + ( getTrnDate() == null ? 0 : this.getTrnDate().hashCode() );
         result = 37 * result + ( getClosingBal() == null ? 0 : this.getClosingBal().hashCode() );
         result = 37 * result + ( getSequence() == null ? 0 : this.getSequence().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDtTime() == null ? 0 : this.getDeDtTime().hashCode() );
         return result;
   }   





}