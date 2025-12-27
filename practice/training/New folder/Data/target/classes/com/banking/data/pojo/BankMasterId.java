package com.banking.data.pojo;
// default package



/**
 * BankMasterId entity. @author MyEclipse Persistence Tools
 */

public class BankMasterId  implements java.io.Serializable {


    // Fields    

     private String bankCode;
     private String bankName;
     private String bankAbbr;
     private String deTml;
     private String deUser;
     private String deDate;


    // Constructors

    /** default constructor */
    public BankMasterId() {
    }

	/** minimal constructor */
    public BankMasterId(String bankCode) {
        this.bankCode = bankCode;
    }
    
    /** full constructor */
    public BankMasterId(String bankCode, String bankName, String bankAbbr, String deTml, String deUser, String deDate) {
        this.bankCode = bankCode;
        this.bankName = bankName;
        this.bankAbbr = bankAbbr;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
    }

   
    // Property accessors

    public String getBankCode() {
        return this.bankCode;
    }
    
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return this.bankName;
    }
    
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAbbr() {
        return this.bankAbbr;
    }
    
    public void setBankAbbr(String bankAbbr) {
        this.bankAbbr = bankAbbr;
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
		 if ( !(other instanceof BankMasterId) ) return false;
		 BankMasterId castOther = ( BankMasterId ) other; 
         
		 return ( (this.getBankCode()==castOther.getBankCode()) || ( this.getBankCode()!=null && castOther.getBankCode()!=null && this.getBankCode().equals(castOther.getBankCode()) ) )
 && ( (this.getBankName()==castOther.getBankName()) || ( this.getBankName()!=null && castOther.getBankName()!=null && this.getBankName().equals(castOther.getBankName()) ) )
 && ( (this.getBankAbbr()==castOther.getBankAbbr()) || ( this.getBankAbbr()!=null && castOther.getBankAbbr()!=null && this.getBankAbbr().equals(castOther.getBankAbbr()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getBankCode() == null ? 0 : this.getBankCode().hashCode() );
         result = 37 * result + ( getBankName() == null ? 0 : this.getBankName().hashCode() );
         result = 37 * result + ( getBankAbbr() == null ? 0 : this.getBankAbbr().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         return result;
   }   





}