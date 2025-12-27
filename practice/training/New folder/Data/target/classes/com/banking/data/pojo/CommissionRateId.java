package com.banking.data.pojo;
// default package



/**
 * CommissionRateId entity. @author MyEclipse Persistence Tools
 */

public class CommissionRateId  implements java.io.Serializable {


    // Fields    

     private String agtType;
     private String dateFr;
     private String dateTo;
     private Double minAmt;
     private Double maxAmt;
     private Double commRate;
     private Double secDepRate;
     private String deTml;
     private String deUser;
     private String deDate;


    // Constructors

    /** default constructor */
    public CommissionRateId() {
    }

    
    /** full constructor */
    public CommissionRateId(String agtType, String dateFr, String dateTo, Double minAmt, Double maxAmt, Double commRate, Double secDepRate, String deTml, String deUser, String deDate) {
        this.agtType = agtType;
        this.dateFr = dateFr;
        this.dateTo = dateTo;
        this.minAmt = minAmt;
        this.maxAmt = maxAmt;
        this.commRate = commRate;
        this.secDepRate = secDepRate;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
    }

   
    // Property accessors

    public String getAgtType() {
        return this.agtType;
    }
    
    public void setAgtType(String agtType) {
        this.agtType = agtType;
    }

    public String getDateFr() {
        return this.dateFr;
    }
    
    public void setDateFr(String dateFr) {
        this.dateFr = dateFr;
    }

    public String getDateTo() {
        return this.dateTo;
    }
    
    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public Double getMinAmt() {
        return this.minAmt;
    }
    
    public void setMinAmt(Double minAmt) {
        this.minAmt = minAmt;
    }

    public Double getMaxAmt() {
        return this.maxAmt;
    }
    
    public void setMaxAmt(Double maxAmt) {
        this.maxAmt = maxAmt;
    }

    public Double getCommRate() {
        return this.commRate;
    }
    
    public void setCommRate(Double commRate) {
        this.commRate = commRate;
    }

    public Double getSecDepRate() {
        return this.secDepRate;
    }
    
    public void setSecDepRate(Double secDepRate) {
        this.secDepRate = secDepRate;
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
		 if ( !(other instanceof CommissionRateId) ) return false;
		 CommissionRateId castOther = ( CommissionRateId ) other; 
         
		 return ( (this.getAgtType()==castOther.getAgtType()) || ( this.getAgtType()!=null && castOther.getAgtType()!=null && this.getAgtType().equals(castOther.getAgtType()) ) )
 && ( (this.getDateFr()==castOther.getDateFr()) || ( this.getDateFr()!=null && castOther.getDateFr()!=null && this.getDateFr().equals(castOther.getDateFr()) ) )
 && ( (this.getDateTo()==castOther.getDateTo()) || ( this.getDateTo()!=null && castOther.getDateTo()!=null && this.getDateTo().equals(castOther.getDateTo()) ) )
 && ( (this.getMinAmt()==castOther.getMinAmt()) || ( this.getMinAmt()!=null && castOther.getMinAmt()!=null && this.getMinAmt().equals(castOther.getMinAmt()) ) )
 && ( (this.getMaxAmt()==castOther.getMaxAmt()) || ( this.getMaxAmt()!=null && castOther.getMaxAmt()!=null && this.getMaxAmt().equals(castOther.getMaxAmt()) ) )
 && ( (this.getCommRate()==castOther.getCommRate()) || ( this.getCommRate()!=null && castOther.getCommRate()!=null && this.getCommRate().equals(castOther.getCommRate()) ) )
 && ( (this.getSecDepRate()==castOther.getSecDepRate()) || ( this.getSecDepRate()!=null && castOther.getSecDepRate()!=null && this.getSecDepRate().equals(castOther.getSecDepRate()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAgtType() == null ? 0 : this.getAgtType().hashCode() );
         result = 37 * result + ( getDateFr() == null ? 0 : this.getDateFr().hashCode() );
         result = 37 * result + ( getDateTo() == null ? 0 : this.getDateTo().hashCode() );
         result = 37 * result + ( getMinAmt() == null ? 0 : this.getMinAmt().hashCode() );
         result = 37 * result + ( getMaxAmt() == null ? 0 : this.getMaxAmt().hashCode() );
         result = 37 * result + ( getCommRate() == null ? 0 : this.getCommRate().hashCode() );
         result = 37 * result + ( getSecDepRate() == null ? 0 : this.getSecDepRate().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         return result;
   }   





}