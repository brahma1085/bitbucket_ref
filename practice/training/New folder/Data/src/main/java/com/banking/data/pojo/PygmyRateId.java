package com.banking.data.pojo;
// default package



/**
 * PygmyRateId entity. @author MyEclipse Persistence Tools
 */

public class PygmyRateId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private String frDate;
     private String toDate;
     private Integer prdFr;
     private Integer prdTo;
     private Double intRate;
     private String deTml;
     private String deUser;
     private String deDate;


    // Constructors

    /** default constructor */
    public PygmyRateId() {
    }

    
    /** full constructor */
    public PygmyRateId(String acType, String frDate, String toDate, Integer prdFr, Integer prdTo, Double intRate, String deTml, String deUser, String deDate) {
        this.acType = acType;
        this.frDate = frDate;
        this.toDate = toDate;
        this.prdFr = prdFr;
        this.prdTo = prdTo;
        this.intRate = intRate;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
    }

   
    // Property accessors

    public String getAcType() {
        return this.acType;
    }
    
    public void setAcType(String acType) {
        this.acType = acType;
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

    public Integer getPrdFr() {
        return this.prdFr;
    }
    
    public void setPrdFr(Integer prdFr) {
        this.prdFr = prdFr;
    }

    public Integer getPrdTo() {
        return this.prdTo;
    }
    
    public void setPrdTo(Integer prdTo) {
        this.prdTo = prdTo;
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
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof PygmyRateId) ) return false;
		 PygmyRateId castOther = ( PygmyRateId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getFrDate()==castOther.getFrDate()) || ( this.getFrDate()!=null && castOther.getFrDate()!=null && this.getFrDate().equals(castOther.getFrDate()) ) )
 && ( (this.getToDate()==castOther.getToDate()) || ( this.getToDate()!=null && castOther.getToDate()!=null && this.getToDate().equals(castOther.getToDate()) ) )
 && ( (this.getPrdFr()==castOther.getPrdFr()) || ( this.getPrdFr()!=null && castOther.getPrdFr()!=null && this.getPrdFr().equals(castOther.getPrdFr()) ) )
 && ( (this.getPrdTo()==castOther.getPrdTo()) || ( this.getPrdTo()!=null && castOther.getPrdTo()!=null && this.getPrdTo().equals(castOther.getPrdTo()) ) )
 && ( (this.getIntRate()==castOther.getIntRate()) || ( this.getIntRate()!=null && castOther.getIntRate()!=null && this.getIntRate().equals(castOther.getIntRate()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getFrDate() == null ? 0 : this.getFrDate().hashCode() );
         result = 37 * result + ( getToDate() == null ? 0 : this.getToDate().hashCode() );
         result = 37 * result + ( getPrdFr() == null ? 0 : this.getPrdFr().hashCode() );
         result = 37 * result + ( getPrdTo() == null ? 0 : this.getPrdTo().hashCode() );
         result = 37 * result + ( getIntRate() == null ? 0 : this.getIntRate().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         return result;
   }   





}