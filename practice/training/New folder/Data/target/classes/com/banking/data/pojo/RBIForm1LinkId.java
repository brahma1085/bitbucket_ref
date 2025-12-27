package com.banking.data.pojo;
// default package

import java.sql.Timestamp;
import java.util.Date;


/**
 * RBIForm1LinkId entity. @author MyEclipse Persistence Tools
 */

public class RBIForm1LinkId  implements java.io.Serializable {


    // Fields    

     private Integer code;
     private String trnSrc;
     private String glType;
     private Integer glCode;
     private Date fromdate;
     private Date todate;
     private Double percentage;
     private String cdInd;
     private Integer mulBy;
     private Integer sequence;
     private String deTml;
     private String deUser;
     private Timestamp deDtTime;


    // Constructors

    /** default constructor */
    public RBIForm1LinkId() {
    }

    
    /** full constructor */
    public RBIForm1LinkId(Integer code, String trnSrc, String glType, Integer glCode, Date fromdate, Date todate, Double percentage, String cdInd, Integer mulBy, Integer sequence, String deTml, String deUser, Timestamp deDtTime) {
        this.code = code;
        this.trnSrc = trnSrc;
        this.glType = glType;
        this.glCode = glCode;
        this.fromdate = fromdate;
        this.todate = todate;
        this.percentage = percentage;
        this.cdInd = cdInd;
        this.mulBy = mulBy;
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

    public Date getFromdate() {
        return this.fromdate;
    }
    
    public void setFromdate(Date fromdate) {
        this.fromdate = fromdate;
    }

    public Date getTodate() {
        return this.todate;
    }
    
    public void setTodate(Date todate) {
        this.todate = todate;
    }

    public Double getPercentage() {
        return this.percentage;
    }
    
    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public String getCdInd() {
        return this.cdInd;
    }
    
    public void setCdInd(String cdInd) {
        this.cdInd = cdInd;
    }

    public Integer getMulBy() {
        return this.mulBy;
    }
    
    public void setMulBy(Integer mulBy) {
        this.mulBy = mulBy;
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
		 if ( !(other instanceof RBIForm1LinkId) ) return false;
		 RBIForm1LinkId castOther = ( RBIForm1LinkId ) other; 
         
		 return ( (this.getCode()==castOther.getCode()) || ( this.getCode()!=null && castOther.getCode()!=null && this.getCode().equals(castOther.getCode()) ) )
 && ( (this.getTrnSrc()==castOther.getTrnSrc()) || ( this.getTrnSrc()!=null && castOther.getTrnSrc()!=null && this.getTrnSrc().equals(castOther.getTrnSrc()) ) )
 && ( (this.getGlType()==castOther.getGlType()) || ( this.getGlType()!=null && castOther.getGlType()!=null && this.getGlType().equals(castOther.getGlType()) ) )
 && ( (this.getGlCode()==castOther.getGlCode()) || ( this.getGlCode()!=null && castOther.getGlCode()!=null && this.getGlCode().equals(castOther.getGlCode()) ) )
 && ( (this.getFromdate()==castOther.getFromdate()) || ( this.getFromdate()!=null && castOther.getFromdate()!=null && this.getFromdate().equals(castOther.getFromdate()) ) )
 && ( (this.getTodate()==castOther.getTodate()) || ( this.getTodate()!=null && castOther.getTodate()!=null && this.getTodate().equals(castOther.getTodate()) ) )
 && ( (this.getPercentage()==castOther.getPercentage()) || ( this.getPercentage()!=null && castOther.getPercentage()!=null && this.getPercentage().equals(castOther.getPercentage()) ) )
 && ( (this.getCdInd()==castOther.getCdInd()) || ( this.getCdInd()!=null && castOther.getCdInd()!=null && this.getCdInd().equals(castOther.getCdInd()) ) )
 && ( (this.getMulBy()==castOther.getMulBy()) || ( this.getMulBy()!=null && castOther.getMulBy()!=null && this.getMulBy().equals(castOther.getMulBy()) ) )
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
         result = 37 * result + ( getFromdate() == null ? 0 : this.getFromdate().hashCode() );
         result = 37 * result + ( getTodate() == null ? 0 : this.getTodate().hashCode() );
         result = 37 * result + ( getPercentage() == null ? 0 : this.getPercentage().hashCode() );
         result = 37 * result + ( getCdInd() == null ? 0 : this.getCdInd().hashCode() );
         result = 37 * result + ( getMulBy() == null ? 0 : this.getMulBy().hashCode() );
         result = 37 * result + ( getSequence() == null ? 0 : this.getSequence().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDtTime() == null ? 0 : this.getDeDtTime().hashCode() );
         return result;
   }   





}