package com.banking.data.pojo;
// default package



/**
 * BranchMasterId entity. @author MyEclipse Persistence Tools
 */

public class BranchMasterId  implements java.io.Serializable {


    // Fields    

     private Integer brCode;
     private String brName;
     private String brShnm;
     private String address;
     private String brAcType;
     private Integer brAcNo;
     private String computerised;
     private String brType;
     private Integer glCode;
     private String glType;
     private String brComp;
     private String deTml;
     private String deUser;
     private String deDate;


    // Constructors

    /** default constructor */
    public BranchMasterId() {
    }

	/** minimal constructor */
    public BranchMasterId(Integer brCode) {
        this.brCode = brCode;
    }
    
    /** full constructor */
    public BranchMasterId(Integer brCode, String brName, String brShnm, String address, String brAcType, Integer brAcNo, String computerised, String brType, Integer glCode, String glType, String brComp, String deTml, String deUser, String deDate) {
        this.brCode = brCode;
        this.brName = brName;
        this.brShnm = brShnm;
        this.address = address;
        this.brAcType = brAcType;
        this.brAcNo = brAcNo;
        this.computerised = computerised;
        this.brType = brType;
        this.glCode = glCode;
        this.glType = glType;
        this.brComp = brComp;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
    }

   
    // Property accessors

    public Integer getBrCode() {
        return this.brCode;
    }
    
    public void setBrCode(Integer brCode) {
        this.brCode = brCode;
    }

    public String getBrName() {
        return this.brName;
    }
    
    public void setBrName(String brName) {
        this.brName = brName;
    }

    public String getBrShnm() {
        return this.brShnm;
    }
    
    public void setBrShnm(String brShnm) {
        this.brShnm = brShnm;
    }

    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    public String getBrAcType() {
        return this.brAcType;
    }
    
    public void setBrAcType(String brAcType) {
        this.brAcType = brAcType;
    }

    public Integer getBrAcNo() {
        return this.brAcNo;
    }
    
    public void setBrAcNo(Integer brAcNo) {
        this.brAcNo = brAcNo;
    }

    public String getComputerised() {
        return this.computerised;
    }
    
    public void setComputerised(String computerised) {
        this.computerised = computerised;
    }

    public String getBrType() {
        return this.brType;
    }
    
    public void setBrType(String brType) {
        this.brType = brType;
    }

    public Integer getGlCode() {
        return this.glCode;
    }
    
    public void setGlCode(Integer glCode) {
        this.glCode = glCode;
    }

    public String getGlType() {
        return this.glType;
    }
    
    public void setGlType(String glType) {
        this.glType = glType;
    }

    public String getBrComp() {
        return this.brComp;
    }
    
    public void setBrComp(String brComp) {
        this.brComp = brComp;
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
		 if ( !(other instanceof BranchMasterId) ) return false;
		 BranchMasterId castOther = ( BranchMasterId ) other; 
         
		 return ( (this.getBrCode()==castOther.getBrCode()) || ( this.getBrCode()!=null && castOther.getBrCode()!=null && this.getBrCode().equals(castOther.getBrCode()) ) )
 && ( (this.getBrName()==castOther.getBrName()) || ( this.getBrName()!=null && castOther.getBrName()!=null && this.getBrName().equals(castOther.getBrName()) ) )
 && ( (this.getBrShnm()==castOther.getBrShnm()) || ( this.getBrShnm()!=null && castOther.getBrShnm()!=null && this.getBrShnm().equals(castOther.getBrShnm()) ) )
 && ( (this.getAddress()==castOther.getAddress()) || ( this.getAddress()!=null && castOther.getAddress()!=null && this.getAddress().equals(castOther.getAddress()) ) )
 && ( (this.getBrAcType()==castOther.getBrAcType()) || ( this.getBrAcType()!=null && castOther.getBrAcType()!=null && this.getBrAcType().equals(castOther.getBrAcType()) ) )
 && ( (this.getBrAcNo()==castOther.getBrAcNo()) || ( this.getBrAcNo()!=null && castOther.getBrAcNo()!=null && this.getBrAcNo().equals(castOther.getBrAcNo()) ) )
 && ( (this.getComputerised()==castOther.getComputerised()) || ( this.getComputerised()!=null && castOther.getComputerised()!=null && this.getComputerised().equals(castOther.getComputerised()) ) )
 && ( (this.getBrType()==castOther.getBrType()) || ( this.getBrType()!=null && castOther.getBrType()!=null && this.getBrType().equals(castOther.getBrType()) ) )
 && ( (this.getGlCode()==castOther.getGlCode()) || ( this.getGlCode()!=null && castOther.getGlCode()!=null && this.getGlCode().equals(castOther.getGlCode()) ) )
 && ( (this.getGlType()==castOther.getGlType()) || ( this.getGlType()!=null && castOther.getGlType()!=null && this.getGlType().equals(castOther.getGlType()) ) )
 && ( (this.getBrComp()==castOther.getBrComp()) || ( this.getBrComp()!=null && castOther.getBrComp()!=null && this.getBrComp().equals(castOther.getBrComp()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getBrCode() == null ? 0 : this.getBrCode().hashCode() );
         result = 37 * result + ( getBrName() == null ? 0 : this.getBrName().hashCode() );
         result = 37 * result + ( getBrShnm() == null ? 0 : this.getBrShnm().hashCode() );
         result = 37 * result + ( getAddress() == null ? 0 : this.getAddress().hashCode() );
         result = 37 * result + ( getBrAcType() == null ? 0 : this.getBrAcType().hashCode() );
         result = 37 * result + ( getBrAcNo() == null ? 0 : this.getBrAcNo().hashCode() );
         result = 37 * result + ( getComputerised() == null ? 0 : this.getComputerised().hashCode() );
         result = 37 * result + ( getBrType() == null ? 0 : this.getBrType().hashCode() );
         result = 37 * result + ( getGlCode() == null ? 0 : this.getGlCode().hashCode() );
         result = 37 * result + ( getGlType() == null ? 0 : this.getGlType().hashCode() );
         result = 37 * result + ( getBrComp() == null ? 0 : this.getBrComp().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         return result;
   }   





}