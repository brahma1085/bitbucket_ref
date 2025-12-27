package com.banking.data.pojo;
// default package



/**
 * DiscountChargesId entity. @author MyEclipse Persistence Tools
 */

public class DiscountChargesId  implements java.io.Serializable {


    // Fields    

     private Double frAmt;
     private Double toAmt;
     private String intType;
     private Double intAmt;
     private Double intRate;
     private String deTml;
     private String deUser;
     private String deDate;
     private String acType;


    // Constructors

    /** default constructor */
    public DiscountChargesId() {
    }

    
    /** full constructor */
    public DiscountChargesId(Double frAmt, Double toAmt, String intType, Double intAmt, Double intRate, String deTml, String deUser, String deDate, String acType) {
        this.frAmt = frAmt;
        this.toAmt = toAmt;
        this.intType = intType;
        this.intAmt = intAmt;
        this.intRate = intRate;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
        this.acType = acType;
    }

   
    // Property accessors

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

    public String getIntType() {
        return this.intType;
    }
    
    public void setIntType(String intType) {
        this.intType = intType;
    }

    public Double getIntAmt() {
        return this.intAmt;
    }
    
    public void setIntAmt(Double intAmt) {
        this.intAmt = intAmt;
    }

    public Double getIntRate() {
        return this.intRate;
    }
    
    public void setIntRate(Double intRate) {
        this.intRate = intRate;
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

    public String getAcType() {
        return this.acType;
    }
    
    public void setAcType(String acType) {
        this.acType = acType;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof DiscountChargesId) ) return false;
		 DiscountChargesId castOther = ( DiscountChargesId ) other; 
         
		 return ( (this.getFrAmt()==castOther.getFrAmt()) || ( this.getFrAmt()!=null && castOther.getFrAmt()!=null && this.getFrAmt().equals(castOther.getFrAmt()) ) )
 && ( (this.getToAmt()==castOther.getToAmt()) || ( this.getToAmt()!=null && castOther.getToAmt()!=null && this.getToAmt().equals(castOther.getToAmt()) ) )
 && ( (this.getIntType()==castOther.getIntType()) || ( this.getIntType()!=null && castOther.getIntType()!=null && this.getIntType().equals(castOther.getIntType()) ) )
 && ( (this.getIntAmt()==castOther.getIntAmt()) || ( this.getIntAmt()!=null && castOther.getIntAmt()!=null && this.getIntAmt().equals(castOther.getIntAmt()) ) )
 && ( (this.getIntRate()==castOther.getIntRate()) || ( this.getIntRate()!=null && castOther.getIntRate()!=null && this.getIntRate().equals(castOther.getIntRate()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) )
 && ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getFrAmt() == null ? 0 : this.getFrAmt().hashCode() );
         result = 37 * result + ( getToAmt() == null ? 0 : this.getToAmt().hashCode() );
         result = 37 * result + ( getIntType() == null ? 0 : this.getIntType().hashCode() );
         result = 37 * result + ( getIntAmt() == null ? 0 : this.getIntAmt().hashCode() );
         result = 37 * result + ( getIntRate() == null ? 0 : this.getIntRate().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         return result;
   }   





}