package com.banking.data.pojo;
// default package



/**
 * ShareDividendRateId entity. @author MyEclipse Persistence Tools
 */

public class ShareDividendRateId  implements java.io.Serializable {


    // Fields    

     private String frDate;
     private String toDate;
     private Double divRate;
     private Double drfAmt;
     private String calDone;
     private String calOpt;
     private String deTml;
     private String deUser;
     private String deDate;


    // Constructors

    /** default constructor */
    public ShareDividendRateId() {
    }

    
    /** full constructor */
    public ShareDividendRateId(String frDate, String toDate, Double divRate, Double drfAmt, String calDone, String calOpt, String deTml, String deUser, String deDate) {
        this.frDate = frDate;
        this.toDate = toDate;
        this.divRate = divRate;
        this.drfAmt = drfAmt;
        this.calDone = calDone;
        this.calOpt = calOpt;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
    }

   
    // Property accessors

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

    public Double getDivRate() {
        return this.divRate;
    }
    
    public void setDivRate(Double divRate) {
        this.divRate = divRate;
    }

    public Double getDrfAmt() {
        return this.drfAmt;
    }
    
    public void setDrfAmt(Double drfAmt) {
        this.drfAmt = drfAmt;
    }

    public String getCalDone() {
        return this.calDone;
    }
    
    public void setCalDone(String calDone) {
        this.calDone = calDone;
    }

    public String getCalOpt() {
        return this.calOpt;
    }
    
    public void setCalOpt(String calOpt) {
        this.calOpt = calOpt;
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
		 if ( !(other instanceof ShareDividendRateId) ) return false;
		 ShareDividendRateId castOther = ( ShareDividendRateId ) other; 
         
		 return ( (this.getFrDate()==castOther.getFrDate()) || ( this.getFrDate()!=null && castOther.getFrDate()!=null && this.getFrDate().equals(castOther.getFrDate()) ) )
 && ( (this.getToDate()==castOther.getToDate()) || ( this.getToDate()!=null && castOther.getToDate()!=null && this.getToDate().equals(castOther.getToDate()) ) )
 && ( (this.getDivRate()==castOther.getDivRate()) || ( this.getDivRate()!=null && castOther.getDivRate()!=null && this.getDivRate().equals(castOther.getDivRate()) ) )
 && ( (this.getDrfAmt()==castOther.getDrfAmt()) || ( this.getDrfAmt()!=null && castOther.getDrfAmt()!=null && this.getDrfAmt().equals(castOther.getDrfAmt()) ) )
 && ( (this.getCalDone()==castOther.getCalDone()) || ( this.getCalDone()!=null && castOther.getCalDone()!=null && this.getCalDone().equals(castOther.getCalDone()) ) )
 && ( (this.getCalOpt()==castOther.getCalOpt()) || ( this.getCalOpt()!=null && castOther.getCalOpt()!=null && this.getCalOpt().equals(castOther.getCalOpt()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getFrDate() == null ? 0 : this.getFrDate().hashCode() );
         result = 37 * result + ( getToDate() == null ? 0 : this.getToDate().hashCode() );
         result = 37 * result + ( getDivRate() == null ? 0 : this.getDivRate().hashCode() );
         result = 37 * result + ( getDrfAmt() == null ? 0 : this.getDrfAmt().hashCode() );
         result = 37 * result + ( getCalDone() == null ? 0 : this.getCalDone().hashCode() );
         result = 37 * result + ( getCalOpt() == null ? 0 : this.getCalOpt().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         return result;
   }   





}