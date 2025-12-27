package com.banking.data.pojo;
// default package



/**
 * PayOrderCommissionId entity. @author MyEclipse Persistence Tools
 */

public class PayOrderCommissionId  implements java.io.Serializable {


    // Fields    

     private String poType;
     private Integer custType;
     private Integer custSubType;
     private String frDate;
     private String toDate;
     private Double frAmt;
     private Double toAmt;
     private Double commRate;
     private String commType;
     private Double minCommAmt;
     private Double extraRate;
     private String deTml;
     private String deUser;
     private String deDate;


    // Constructors

    /** default constructor */
    public PayOrderCommissionId() {
    }

    
    /** full constructor */
    public PayOrderCommissionId(String poType, Integer custType, Integer custSubType, String frDate, String toDate, Double frAmt, Double toAmt, Double commRate, String commType, Double minCommAmt, Double extraRate, String deTml, String deUser, String deDate) {
        this.poType = poType;
        this.custType = custType;
        this.custSubType = custSubType;
        this.frDate = frDate;
        this.toDate = toDate;
        this.frAmt = frAmt;
        this.toAmt = toAmt;
        this.commRate = commRate;
        this.commType = commType;
        this.minCommAmt = minCommAmt;
        this.extraRate = extraRate;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
    }

   
    // Property accessors

    public String getPoType() {
        return this.poType;
    }
    
    public void setPoType(String poType) {
        this.poType = poType;
    }

    public Integer getCustType() {
        return this.custType;
    }
    
    public void setCustType(Integer custType) {
        this.custType = custType;
    }

    public Integer getCustSubType() {
        return this.custSubType;
    }
    
    public void setCustSubType(Integer custSubType) {
        this.custSubType = custSubType;
    }

    public String getFrDate() {
        return this.frDate;
    }
    
    public void setFrDate(String frDate) {
        this.frDate = frDate;
    }

    public String getToDate() {
        return this.toDate;
    }
    
    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public Double getFrAmt() {
        return this.frAmt;
    }
    
    public void setFrAmt(Double frAmt) {
        this.frAmt = frAmt;
    }

    public Double getToAmt() {
        return this.toAmt;
    }
    
    public void setToAmt(Double toAmt) {
        this.toAmt = toAmt;
    }

    public Double getCommRate() {
        return this.commRate;
    }
    
    public void setCommRate(Double commRate) {
        this.commRate = commRate;
    }

    public String getCommType() {
        return this.commType;
    }
    
    public void setCommType(String commType) {
        this.commType = commType;
    }

    public Double getMinCommAmt() {
        return this.minCommAmt;
    }
    
    public void setMinCommAmt(Double minCommAmt) {
        this.minCommAmt = minCommAmt;
    }

    public Double getExtraRate() {
        return this.extraRate;
    }
    
    public void setExtraRate(Double extraRate) {
        this.extraRate = extraRate;
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
		 if ( !(other instanceof PayOrderCommissionId) ) return false;
		 PayOrderCommissionId castOther = ( PayOrderCommissionId ) other; 
         
		 return ( (this.getPoType()==castOther.getPoType()) || ( this.getPoType()!=null && castOther.getPoType()!=null && this.getPoType().equals(castOther.getPoType()) ) )
 && ( (this.getCustType()==castOther.getCustType()) || ( this.getCustType()!=null && castOther.getCustType()!=null && this.getCustType().equals(castOther.getCustType()) ) )
 && ( (this.getCustSubType()==castOther.getCustSubType()) || ( this.getCustSubType()!=null && castOther.getCustSubType()!=null && this.getCustSubType().equals(castOther.getCustSubType()) ) )
 && ( (this.getFrDate()==castOther.getFrDate()) || ( this.getFrDate()!=null && castOther.getFrDate()!=null && this.getFrDate().equals(castOther.getFrDate()) ) )
 && ( (this.getToDate()==castOther.getToDate()) || ( this.getToDate()!=null && castOther.getToDate()!=null && this.getToDate().equals(castOther.getToDate()) ) )
 && ( (this.getFrAmt()==castOther.getFrAmt()) || ( this.getFrAmt()!=null && castOther.getFrAmt()!=null && this.getFrAmt().equals(castOther.getFrAmt()) ) )
 && ( (this.getToAmt()==castOther.getToAmt()) || ( this.getToAmt()!=null && castOther.getToAmt()!=null && this.getToAmt().equals(castOther.getToAmt()) ) )
 && ( (this.getCommRate()==castOther.getCommRate()) || ( this.getCommRate()!=null && castOther.getCommRate()!=null && this.getCommRate().equals(castOther.getCommRate()) ) )
 && ( (this.getCommType()==castOther.getCommType()) || ( this.getCommType()!=null && castOther.getCommType()!=null && this.getCommType().equals(castOther.getCommType()) ) )
 && ( (this.getMinCommAmt()==castOther.getMinCommAmt()) || ( this.getMinCommAmt()!=null && castOther.getMinCommAmt()!=null && this.getMinCommAmt().equals(castOther.getMinCommAmt()) ) )
 && ( (this.getExtraRate()==castOther.getExtraRate()) || ( this.getExtraRate()!=null && castOther.getExtraRate()!=null && this.getExtraRate().equals(castOther.getExtraRate()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getPoType() == null ? 0 : this.getPoType().hashCode() );
         result = 37 * result + ( getCustType() == null ? 0 : this.getCustType().hashCode() );
         result = 37 * result + ( getCustSubType() == null ? 0 : this.getCustSubType().hashCode() );
         result = 37 * result + ( getFrDate() == null ? 0 : this.getFrDate().hashCode() );
         result = 37 * result + ( getToDate() == null ? 0 : this.getToDate().hashCode() );
         result = 37 * result + ( getFrAmt() == null ? 0 : this.getFrAmt().hashCode() );
         result = 37 * result + ( getToAmt() == null ? 0 : this.getToAmt().hashCode() );
         result = 37 * result + ( getCommRate() == null ? 0 : this.getCommRate().hashCode() );
         result = 37 * result + ( getCommType() == null ? 0 : this.getCommType().hashCode() );
         result = 37 * result + ( getMinCommAmt() == null ? 0 : this.getMinCommAmt().hashCode() );
         result = 37 * result + ( getExtraRate() == null ? 0 : this.getExtraRate().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         return result;
   }   





}