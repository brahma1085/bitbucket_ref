package com.banking.data.pojo;
// default package



/**
 * AccountCodeId entity. @author MyEclipse Persistence Tools
 */

public class AccountCodeId  implements java.io.Serializable {


    // Fields    

     private String modulecode;
     private String moduleabbr;
     private Integer acNo;
     private String acType;


    // Constructors

    /** default constructor */
    public AccountCodeId() {
    }

    
    /** full constructor */
    public AccountCodeId(String modulecode, String moduleabbr, Integer acNo, String acType) {
        this.modulecode = modulecode;
        this.moduleabbr = moduleabbr;
        this.acNo = acNo;
        this.acType = acType;
    }

   
    // Property accessors

    public String getModulecode() {
        return this.modulecode;
    }
    
    public void setModulecode(String modulecode) {
        this.modulecode = modulecode;
    }

    public String getModuleabbr() {
        return this.moduleabbr;
    }
    
    public void setModuleabbr(String moduleabbr) {
        this.moduleabbr = moduleabbr;
    }

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
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof AccountCodeId) ) return false;
		 AccountCodeId castOther = ( AccountCodeId ) other; 
         
		 return ( (this.getModulecode()==castOther.getModulecode()) || ( this.getModulecode()!=null && castOther.getModulecode()!=null && this.getModulecode().equals(castOther.getModulecode()) ) )
 && ( (this.getModuleabbr()==castOther.getModuleabbr()) || ( this.getModuleabbr()!=null && castOther.getModuleabbr()!=null && this.getModuleabbr().equals(castOther.getModuleabbr()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getModulecode() == null ? 0 : this.getModulecode().hashCode() );
         result = 37 * result + ( getModuleabbr() == null ? 0 : this.getModuleabbr().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         return result;
   }   





}