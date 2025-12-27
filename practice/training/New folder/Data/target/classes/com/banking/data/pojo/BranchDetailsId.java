package com.banking.data.pojo;
// default package



/**
 * BranchDetailsId entity. @author MyEclipse Persistence Tools
 */

public class BranchDetailsId  implements java.io.Serializable {


    // Fields    

     private String branchCode;
     private String branchName;
     private String branchAddr;


    // Constructors

    /** default constructor */
    public BranchDetailsId() {
    }

    
    /** full constructor */
    public BranchDetailsId(String branchCode, String branchName, String branchAddr) {
        this.branchCode = branchCode;
        this.branchName = branchName;
        this.branchAddr = branchAddr;
    }

   
    // Property accessors

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

    public String getBranchAddr() {
        return this.branchAddr;
    }
    
    public void setBranchAddr(String branchAddr) {
        this.branchAddr = branchAddr;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof BranchDetailsId) ) return false;
		 BranchDetailsId castOther = ( BranchDetailsId ) other; 
         
		 return ( (this.getBranchCode()==castOther.getBranchCode()) || ( this.getBranchCode()!=null && castOther.getBranchCode()!=null && this.getBranchCode().equals(castOther.getBranchCode()) ) )
 && ( (this.getBranchName()==castOther.getBranchName()) || ( this.getBranchName()!=null && castOther.getBranchName()!=null && this.getBranchName().equals(castOther.getBranchName()) ) )
 && ( (this.getBranchAddr()==castOther.getBranchAddr()) || ( this.getBranchAddr()!=null && castOther.getBranchAddr()!=null && this.getBranchAddr().equals(castOther.getBranchAddr()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getBranchCode() == null ? 0 : this.getBranchCode().hashCode() );
         result = 37 * result + ( getBranchName() == null ? 0 : this.getBranchName().hashCode() );
         result = 37 * result + ( getBranchAddr() == null ? 0 : this.getBranchAddr().hashCode() );
         return result;
   }   





}