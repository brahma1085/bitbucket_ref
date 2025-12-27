package com.banking.data.pojo;
// default package



/**
 * CashCodeId entity. @author MyEclipse Persistence Tools
 */

public class CashCodeId  implements java.io.Serializable {


    // Fields    

     private String moduleCode;
     private String moduleAbbr;
     private Integer acNo;
     private String mainAbbr;


    // Constructors

    /** default constructor */
    public CashCodeId() {
    }

    
    /** full constructor */
    public CashCodeId(String moduleCode, String moduleAbbr, Integer acNo, String mainAbbr) {
        this.moduleCode = moduleCode;
        this.moduleAbbr = moduleAbbr;
        this.acNo = acNo;
        this.mainAbbr = mainAbbr;
    }

   
    // Property accessors

    public String getModuleCode() {
        return this.moduleCode;
    }
    
    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleAbbr() {
        return this.moduleAbbr;
    }
    
    public void setModuleAbbr(String moduleAbbr) {
        this.moduleAbbr = moduleAbbr;
    }

    public Integer getAcNo() {
        return this.acNo;
    }
    
    public void setAcNo(Integer acNo) {
        this.acNo = acNo;
    }

    public String getMainAbbr() {
        return this.mainAbbr;
    }
    
    public void setMainAbbr(String mainAbbr) {
        this.mainAbbr = mainAbbr;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof CashCodeId) ) return false;
		 CashCodeId castOther = ( CashCodeId ) other; 
         
		 return ( (this.getModuleCode()==castOther.getModuleCode()) || ( this.getModuleCode()!=null && castOther.getModuleCode()!=null && this.getModuleCode().equals(castOther.getModuleCode()) ) )
 && ( (this.getModuleAbbr()==castOther.getModuleAbbr()) || ( this.getModuleAbbr()!=null && castOther.getModuleAbbr()!=null && this.getModuleAbbr().equals(castOther.getModuleAbbr()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getMainAbbr()==castOther.getMainAbbr()) || ( this.getMainAbbr()!=null && castOther.getMainAbbr()!=null && this.getMainAbbr().equals(castOther.getMainAbbr()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getModuleCode() == null ? 0 : this.getModuleCode().hashCode() );
         result = 37 * result + ( getModuleAbbr() == null ? 0 : this.getModuleAbbr().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getMainAbbr() == null ? 0 : this.getMainAbbr().hashCode() );
         return result;
   }   





}