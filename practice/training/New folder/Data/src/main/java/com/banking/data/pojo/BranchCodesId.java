package com.banking.data.pojo;
// default package



/**
 * BranchCodesId entity. @author MyEclipse Persistence Tools
 */

public class BranchCodesId  implements java.io.Serializable {


    // Fields    

     private String cityCode;
     private String bankCode;
     private String branchCode;
     private String branchName;


    // Constructors

    /** default constructor */
    public BranchCodesId() {
    }

    
    /** full constructor */
    public BranchCodesId(String cityCode, String bankCode, String branchCode, String branchName) {
        this.cityCode = cityCode;
        this.bankCode = bankCode;
        this.branchCode = branchCode;
        this.branchName = branchName;
    }

   
    // Property accessors

    public String getCityCode() {
        return this.cityCode;
    }
    
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getBankCode() {
        return this.bankCode;
    }
    
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBranchCode() {
        return this.branchCode;
    }
    
    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchName() {
        return this.branchName;
    }
    
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof BranchCodesId) ) return false;
		 BranchCodesId castOther = ( BranchCodesId ) other; 
         
		 return ( (this.getCityCode()==castOther.getCityCode()) || ( this.getCityCode()!=null && castOther.getCityCode()!=null && this.getCityCode().equals(castOther.getCityCode()) ) )
 && ( (this.getBankCode()==castOther.getBankCode()) || ( this.getBankCode()!=null && castOther.getBankCode()!=null && this.getBankCode().equals(castOther.getBankCode()) ) )
 && ( (this.getBranchCode()==castOther.getBranchCode()) || ( this.getBranchCode()!=null && castOther.getBranchCode()!=null && this.getBranchCode().equals(castOther.getBranchCode()) ) )
 && ( (this.getBranchName()==castOther.getBranchName()) || ( this.getBranchName()!=null && castOther.getBranchName()!=null && this.getBranchName().equals(castOther.getBranchName()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getCityCode() == null ? 0 : this.getCityCode().hashCode() );
         result = 37 * result + ( getBankCode() == null ? 0 : this.getBankCode().hashCode() );
         result = 37 * result + ( getBranchCode() == null ? 0 : this.getBranchCode().hashCode() );
         result = 37 * result + ( getBranchName() == null ? 0 : this.getBranchName().hashCode() );
         return result;
   }   





}