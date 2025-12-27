package com.banking.data.pojo;
// default package



/**
 * GoldDetailsMasterId entity. @author MyEclipse Persistence Tools
 */

public class GoldDetailsMasterId  implements java.io.Serializable {


    // Fields    

     private Integer acNo;
     private String acType;
     private Integer appcode;
     private Integer srlNo;
     private String descr;
     private Double grwgt;
     private Double netwgt;
     private Double rate;
     private String date;
     private String time;


    // Constructors

    /** default constructor */
    public GoldDetailsMasterId() {
    }

    
    /** full constructor */
    public GoldDetailsMasterId(Integer acNo, String acType, Integer appcode, Integer srlNo, String descr, Double grwgt, Double netwgt, Double rate, String date, String time) {
        this.acNo = acNo;
        this.acType = acType;
        this.appcode = appcode;
        this.srlNo = srlNo;
        this.descr = descr;
        this.grwgt = grwgt;
        this.netwgt = netwgt;
        this.rate = rate;
        this.date = date;
        this.time = time;
    }

   
    // Property accessors

    public Integer getAcNo() {
        return this.acNo;
    }
    
    public void setAcNo(Integer acNo) {
        this.acNo = acNo;
    }

    public String getAcType() {
        return this.acType;
    }
    
    public void setAcType(String acType) {
        this.acType = acType;
    }

    public Integer getAppcode() {
        return this.appcode;
    }
    
    public void setAppcode(Integer appcode) {
        this.appcode = appcode;
    }

    public Integer getSrlNo() {
        return this.srlNo;
    }
    
    public void setSrlNo(Integer srlNo) {
        this.srlNo = srlNo;
    }

    public String getDescr() {
        return this.descr;
    }
    
    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Double getGrwgt() {
        return this.grwgt;
    }
    
    public void setGrwgt(Double grwgt) {
        this.grwgt = grwgt;
    }

    public Double getNetwgt() {
        return this.netwgt;
    }
    
    public void setNetwgt(Double netwgt) {
        this.netwgt = netwgt;
    }

    public Double getRate() {
        return this.rate;
    }
    
    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getDate() {
        return this.date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return this.time;
    }
    
    public void setTime(String time) {
        this.time = time;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof GoldDetailsMasterId) ) return false;
		 GoldDetailsMasterId castOther = ( GoldDetailsMasterId ) other; 
         
		 return ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAppcode()==castOther.getAppcode()) || ( this.getAppcode()!=null && castOther.getAppcode()!=null && this.getAppcode().equals(castOther.getAppcode()) ) )
 && ( (this.getSrlNo()==castOther.getSrlNo()) || ( this.getSrlNo()!=null && castOther.getSrlNo()!=null && this.getSrlNo().equals(castOther.getSrlNo()) ) )
 && ( (this.getDescr()==castOther.getDescr()) || ( this.getDescr()!=null && castOther.getDescr()!=null && this.getDescr().equals(castOther.getDescr()) ) )
 && ( (this.getGrwgt()==castOther.getGrwgt()) || ( this.getGrwgt()!=null && castOther.getGrwgt()!=null && this.getGrwgt().equals(castOther.getGrwgt()) ) )
 && ( (this.getNetwgt()==castOther.getNetwgt()) || ( this.getNetwgt()!=null && castOther.getNetwgt()!=null && this.getNetwgt().equals(castOther.getNetwgt()) ) )
 && ( (this.getRate()==castOther.getRate()) || ( this.getRate()!=null && castOther.getRate()!=null && this.getRate().equals(castOther.getRate()) ) )
 && ( (this.getDate()==castOther.getDate()) || ( this.getDate()!=null && castOther.getDate()!=null && this.getDate().equals(castOther.getDate()) ) )
 && ( (this.getTime()==castOther.getTime()) || ( this.getTime()!=null && castOther.getTime()!=null && this.getTime().equals(castOther.getTime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAppcode() == null ? 0 : this.getAppcode().hashCode() );
         result = 37 * result + ( getSrlNo() == null ? 0 : this.getSrlNo().hashCode() );
         result = 37 * result + ( getDescr() == null ? 0 : this.getDescr().hashCode() );
         result = 37 * result + ( getGrwgt() == null ? 0 : this.getGrwgt().hashCode() );
         result = 37 * result + ( getNetwgt() == null ? 0 : this.getNetwgt().hashCode() );
         result = 37 * result + ( getRate() == null ? 0 : this.getRate().hashCode() );
         result = 37 * result + ( getDate() == null ? 0 : this.getDate().hashCode() );
         result = 37 * result + ( getTime() == null ? 0 : this.getTime().hashCode() );
         return result;
   }   





}