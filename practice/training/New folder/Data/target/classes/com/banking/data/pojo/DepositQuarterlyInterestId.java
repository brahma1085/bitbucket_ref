package com.banking.data.pojo;
// default package



/**
 * DepositQuarterlyInterestId entity. @author MyEclipse Persistence Tools
 */

public class DepositQuarterlyInterestId  implements java.io.Serializable {


    // Fields    

     private String trnDate;
     private String acType;
     private Integer acNo;
     private String name;
     private Double depamt;
     private Double matamt;
     private Double intrate;
     private Double lstBalance;
     private String accdupto;
     private Double intamt;
     private String deTml;
     private String deUser;
     private String deDate;


    // Constructors

    /** default constructor */
    public DepositQuarterlyInterestId() {
    }

    
    /** full constructor */
    public DepositQuarterlyInterestId(String trnDate, String acType, Integer acNo, String name, Double depamt, Double matamt, Double intrate, Double lstBalance, String accdupto, Double intamt, String deTml, String deUser, String deDate) {
        this.trnDate = trnDate;
        this.acType = acType;
        this.acNo = acNo;
        this.name = name;
        this.depamt = depamt;
        this.matamt = matamt;
        this.intrate = intrate;
        this.lstBalance = lstBalance;
        this.accdupto = accdupto;
        this.intamt = intamt;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
    }

   
    // Property accessors

    public String getTrnDate() {
        return this.trnDate;
    }
    
    public void setTrnDate(String trnDate) {
        this.trnDate = trnDate;
    }

    public String getAcType() {
        return this.acType;
    }
    
    public void setAcType(String acType) {
        this.acType = acType;
    }

    public Integer getAcNo() {
        return this.acNo;
    }
    
    public void setAcNo(Integer acNo) {
        this.acNo = acNo;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public Double getDepamt() {
        return this.depamt;
    }
    
    public void setDepamt(Double depamt) {
        this.depamt = depamt;
    }

    public Double getMatamt() {
        return this.matamt;
    }
    
    public void setMatamt(Double matamt) {
        this.matamt = matamt;
    }

    public Double getIntrate() {
        return this.intrate;
    }
    
    public void setIntrate(Double intrate) {
        this.intrate = intrate;
    }

    public Double getLstBalance() {
        return this.lstBalance;
    }
    
    public void setLstBalance(Double lstBalance) {
        this.lstBalance = lstBalance;
    }

    public String getAccdupto() {
        return this.accdupto;
    }
    
    public void setAccdupto(String accdupto) {
        this.accdupto = accdupto;
    }

    public Double getIntamt() {
        return this.intamt;
    }
    
    public void setIntamt(Double intamt) {
        this.intamt = intamt;
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
		 if ( !(other instanceof DepositQuarterlyInterestId) ) return false;
		 DepositQuarterlyInterestId castOther = ( DepositQuarterlyInterestId ) other; 
         
		 return ( (this.getTrnDate()==castOther.getTrnDate()) || ( this.getTrnDate()!=null && castOther.getTrnDate()!=null && this.getTrnDate().equals(castOther.getTrnDate()) ) )
 && ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getName()==castOther.getName()) || ( this.getName()!=null && castOther.getName()!=null && this.getName().equals(castOther.getName()) ) )
 && ( (this.getDepamt()==castOther.getDepamt()) || ( this.getDepamt()!=null && castOther.getDepamt()!=null && this.getDepamt().equals(castOther.getDepamt()) ) )
 && ( (this.getMatamt()==castOther.getMatamt()) || ( this.getMatamt()!=null && castOther.getMatamt()!=null && this.getMatamt().equals(castOther.getMatamt()) ) )
 && ( (this.getIntrate()==castOther.getIntrate()) || ( this.getIntrate()!=null && castOther.getIntrate()!=null && this.getIntrate().equals(castOther.getIntrate()) ) )
 && ( (this.getLstBalance()==castOther.getLstBalance()) || ( this.getLstBalance()!=null && castOther.getLstBalance()!=null && this.getLstBalance().equals(castOther.getLstBalance()) ) )
 && ( (this.getAccdupto()==castOther.getAccdupto()) || ( this.getAccdupto()!=null && castOther.getAccdupto()!=null && this.getAccdupto().equals(castOther.getAccdupto()) ) )
 && ( (this.getIntamt()==castOther.getIntamt()) || ( this.getIntamt()!=null && castOther.getIntamt()!=null && this.getIntamt().equals(castOther.getIntamt()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getTrnDate() == null ? 0 : this.getTrnDate().hashCode() );
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getName() == null ? 0 : this.getName().hashCode() );
         result = 37 * result + ( getDepamt() == null ? 0 : this.getDepamt().hashCode() );
         result = 37 * result + ( getMatamt() == null ? 0 : this.getMatamt().hashCode() );
         result = 37 * result + ( getIntrate() == null ? 0 : this.getIntrate().hashCode() );
         result = 37 * result + ( getLstBalance() == null ? 0 : this.getLstBalance().hashCode() );
         result = 37 * result + ( getAccdupto() == null ? 0 : this.getAccdupto().hashCode() );
         result = 37 * result + ( getIntamt() == null ? 0 : this.getIntamt().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         return result;
   }   





}