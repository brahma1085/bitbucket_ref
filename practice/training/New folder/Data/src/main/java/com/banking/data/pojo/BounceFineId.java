package com.banking.data.pojo;
// default package



/**
 * BounceFineId entity. @author MyEclipse Persistence Tools
 */

public class BounceFineId  implements java.io.Serializable {


    // Fields    

     private Integer BCode;
     private String accType;
     private Double fine;
     private Double returnFine;
     private Double mailChg;
     private Double smsChg;
     private Double emailChg;
     private String deTml;
     private String deUser;
     private String deDate;


    // Constructors

    /** default constructor */
    public BounceFineId() {
    }

    
    /** full constructor */
    public BounceFineId(Integer BCode, String accType, Double fine, Double returnFine, Double mailChg, Double smsChg, Double emailChg, String deTml, String deUser, String deDate) {
        this.BCode = BCode;
        this.accType = accType;
        this.fine = fine;
        this.returnFine = returnFine;
        this.mailChg = mailChg;
        this.smsChg = smsChg;
        this.emailChg = emailChg;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
    }

   
    // Property accessors

    public Integer getBCode() {
        return this.BCode;
    }
    
    public void setBCode(Integer BCode) {
        this.BCode = BCode;
    }

    public String getAccType() {
        return this.accType;
    }
    
    public void setAccType(String accType) {
        this.accType = accType;
    }

    public Double getFine() {
        return this.fine;
    }
    
    public void setFine(Double fine) {
        this.fine = fine;
    }

    public Double getReturnFine() {
        return this.returnFine;
    }
    
    public void setReturnFine(Double returnFine) {
        this.returnFine = returnFine;
    }

    public Double getMailChg() {
        return this.mailChg;
    }
    
    public void setMailChg(Double mailChg) {
        this.mailChg = mailChg;
    }

    public Double getSmsChg() {
        return this.smsChg;
    }
    
    public void setSmsChg(Double smsChg) {
        this.smsChg = smsChg;
    }

    public Double getEmailChg() {
        return this.emailChg;
    }
    
    public void setEmailChg(Double emailChg) {
        this.emailChg = emailChg;
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
		 if ( !(other instanceof BounceFineId) ) return false;
		 BounceFineId castOther = ( BounceFineId ) other; 
         
		 return ( (this.getBCode()==castOther.getBCode()) || ( this.getBCode()!=null && castOther.getBCode()!=null && this.getBCode().equals(castOther.getBCode()) ) )
 && ( (this.getAccType()==castOther.getAccType()) || ( this.getAccType()!=null && castOther.getAccType()!=null && this.getAccType().equals(castOther.getAccType()) ) )
 && ( (this.getFine()==castOther.getFine()) || ( this.getFine()!=null && castOther.getFine()!=null && this.getFine().equals(castOther.getFine()) ) )
 && ( (this.getReturnFine()==castOther.getReturnFine()) || ( this.getReturnFine()!=null && castOther.getReturnFine()!=null && this.getReturnFine().equals(castOther.getReturnFine()) ) )
 && ( (this.getMailChg()==castOther.getMailChg()) || ( this.getMailChg()!=null && castOther.getMailChg()!=null && this.getMailChg().equals(castOther.getMailChg()) ) )
 && ( (this.getSmsChg()==castOther.getSmsChg()) || ( this.getSmsChg()!=null && castOther.getSmsChg()!=null && this.getSmsChg().equals(castOther.getSmsChg()) ) )
 && ( (this.getEmailChg()==castOther.getEmailChg()) || ( this.getEmailChg()!=null && castOther.getEmailChg()!=null && this.getEmailChg().equals(castOther.getEmailChg()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getBCode() == null ? 0 : this.getBCode().hashCode() );
         result = 37 * result + ( getAccType() == null ? 0 : this.getAccType().hashCode() );
         result = 37 * result + ( getFine() == null ? 0 : this.getFine().hashCode() );
         result = 37 * result + ( getReturnFine() == null ? 0 : this.getReturnFine().hashCode() );
         result = 37 * result + ( getMailChg() == null ? 0 : this.getMailChg().hashCode() );
         result = 37 * result + ( getSmsChg() == null ? 0 : this.getSmsChg().hashCode() );
         result = 37 * result + ( getEmailChg() == null ? 0 : this.getEmailChg().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         return result;
   }   





}