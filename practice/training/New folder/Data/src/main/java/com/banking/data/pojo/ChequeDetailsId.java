package com.banking.data.pojo;
// default package



/**
 * ChequeDetailsId entity. @author MyEclipse Persistence Tools
 */

public class ChequeDetailsId  implements java.io.Serializable {


    // Fields    

     private Integer ctrlNo;
     private String date;
     private String crAcType;
     private Integer crAcNo;
     private Double crAmt;
     private Double discAmt;
     private String postInd;
     private String discInd;
     private String loanAcType;
     private Integer loanAcNo;
     private String deTml;
     private String deUser;
     private String deDtTime;
     private String veTml;
     private String veUser;
     private String veDtTime;


    // Constructors

    /** default constructor */
    public ChequeDetailsId() {
    }

    
    /** full constructor */
    public ChequeDetailsId(Integer ctrlNo, String date, String crAcType, Integer crAcNo, Double crAmt, Double discAmt, String postInd, String discInd, String loanAcType, Integer loanAcNo, String deTml, String deUser, String deDtTime, String veTml, String veUser, String veDtTime) {
        this.ctrlNo = ctrlNo;
        this.date = date;
        this.crAcType = crAcType;
        this.crAcNo = crAcNo;
        this.crAmt = crAmt;
        this.discAmt = discAmt;
        this.postInd = postInd;
        this.discInd = discInd;
        this.loanAcType = loanAcType;
        this.loanAcNo = loanAcNo;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDtTime = deDtTime;
        this.veTml = veTml;
        this.veUser = veUser;
        this.veDtTime = veDtTime;
    }

   
    // Property accessors

    public Integer getCtrlNo() {
        return this.ctrlNo;
    }
    
    public void setCtrlNo(Integer ctrlNo) {
        this.ctrlNo = ctrlNo;
    }

    public String getDate() {
        return this.date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }

    public String getCrAcType() {
        return this.crAcType;
    }
    
    public void setCrAcType(String crAcType) {
        this.crAcType = crAcType;
    }

    public Integer getCrAcNo() {
        return this.crAcNo;
    }
    
    public void setCrAcNo(Integer crAcNo) {
        this.crAcNo = crAcNo;
    }

    public Double getCrAmt() {
        return this.crAmt;
    }
    
    public void setCrAmt(Double crAmt) {
        this.crAmt = crAmt;
    }

    public Double getDiscAmt() {
        return this.discAmt;
    }
    
    public void setDiscAmt(Double discAmt) {
        this.discAmt = discAmt;
    }

    public String getPostInd() {
        return this.postInd;
    }
    
    public void setPostInd(String postInd) {
        this.postInd = postInd;
    }

    public String getDiscInd() {
        return this.discInd;
    }
    
    public void setDiscInd(String discInd) {
        this.discInd = discInd;
    }

    public String getLoanAcType() {
        return this.loanAcType;
    }
    
    public void setLoanAcType(String loanAcType) {
        this.loanAcType = loanAcType;
    }

    public Integer getLoanAcNo() {
        return this.loanAcNo;
    }
    
    public void setLoanAcNo(Integer loanAcNo) {
        this.loanAcNo = loanAcNo;
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

    public String getDeDtTime() {
        return this.deDtTime;
    }
    
    public void setDeDtTime(String deDtTime) {
        this.deDtTime = deDtTime;
    }

    public String getVeTml() {
        return this.veTml;
    }
    
    public void setVeTml(String veTml) {
        this.veTml = veTml;
    }

    public String getVeUser() {
        return this.veUser;
    }
    
    public void setVeUser(String veUser) {
        this.veUser = veUser;
    }

    public String getVeDtTime() {
        return this.veDtTime;
    }
    
    public void setVeDtTime(String veDtTime) {
        this.veDtTime = veDtTime;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ChequeDetailsId) ) return false;
		 ChequeDetailsId castOther = ( ChequeDetailsId ) other; 
         
		 return ( (this.getCtrlNo()==castOther.getCtrlNo()) || ( this.getCtrlNo()!=null && castOther.getCtrlNo()!=null && this.getCtrlNo().equals(castOther.getCtrlNo()) ) )
 && ( (this.getDate()==castOther.getDate()) || ( this.getDate()!=null && castOther.getDate()!=null && this.getDate().equals(castOther.getDate()) ) )
 && ( (this.getCrAcType()==castOther.getCrAcType()) || ( this.getCrAcType()!=null && castOther.getCrAcType()!=null && this.getCrAcType().equals(castOther.getCrAcType()) ) )
 && ( (this.getCrAcNo()==castOther.getCrAcNo()) || ( this.getCrAcNo()!=null && castOther.getCrAcNo()!=null && this.getCrAcNo().equals(castOther.getCrAcNo()) ) )
 && ( (this.getCrAmt()==castOther.getCrAmt()) || ( this.getCrAmt()!=null && castOther.getCrAmt()!=null && this.getCrAmt().equals(castOther.getCrAmt()) ) )
 && ( (this.getDiscAmt()==castOther.getDiscAmt()) || ( this.getDiscAmt()!=null && castOther.getDiscAmt()!=null && this.getDiscAmt().equals(castOther.getDiscAmt()) ) )
 && ( (this.getPostInd()==castOther.getPostInd()) || ( this.getPostInd()!=null && castOther.getPostInd()!=null && this.getPostInd().equals(castOther.getPostInd()) ) )
 && ( (this.getDiscInd()==castOther.getDiscInd()) || ( this.getDiscInd()!=null && castOther.getDiscInd()!=null && this.getDiscInd().equals(castOther.getDiscInd()) ) )
 && ( (this.getLoanAcType()==castOther.getLoanAcType()) || ( this.getLoanAcType()!=null && castOther.getLoanAcType()!=null && this.getLoanAcType().equals(castOther.getLoanAcType()) ) )
 && ( (this.getLoanAcNo()==castOther.getLoanAcNo()) || ( this.getLoanAcNo()!=null && castOther.getLoanAcNo()!=null && this.getLoanAcNo().equals(castOther.getLoanAcNo()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDtTime()==castOther.getDeDtTime()) || ( this.getDeDtTime()!=null && castOther.getDeDtTime()!=null && this.getDeDtTime().equals(castOther.getDeDtTime()) ) )
 && ( (this.getVeTml()==castOther.getVeTml()) || ( this.getVeTml()!=null && castOther.getVeTml()!=null && this.getVeTml().equals(castOther.getVeTml()) ) )
 && ( (this.getVeUser()==castOther.getVeUser()) || ( this.getVeUser()!=null && castOther.getVeUser()!=null && this.getVeUser().equals(castOther.getVeUser()) ) )
 && ( (this.getVeDtTime()==castOther.getVeDtTime()) || ( this.getVeDtTime()!=null && castOther.getVeDtTime()!=null && this.getVeDtTime().equals(castOther.getVeDtTime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getCtrlNo() == null ? 0 : this.getCtrlNo().hashCode() );
         result = 37 * result + ( getDate() == null ? 0 : this.getDate().hashCode() );
         result = 37 * result + ( getCrAcType() == null ? 0 : this.getCrAcType().hashCode() );
         result = 37 * result + ( getCrAcNo() == null ? 0 : this.getCrAcNo().hashCode() );
         result = 37 * result + ( getCrAmt() == null ? 0 : this.getCrAmt().hashCode() );
         result = 37 * result + ( getDiscAmt() == null ? 0 : this.getDiscAmt().hashCode() );
         result = 37 * result + ( getPostInd() == null ? 0 : this.getPostInd().hashCode() );
         result = 37 * result + ( getDiscInd() == null ? 0 : this.getDiscInd().hashCode() );
         result = 37 * result + ( getLoanAcType() == null ? 0 : this.getLoanAcType().hashCode() );
         result = 37 * result + ( getLoanAcNo() == null ? 0 : this.getLoanAcNo().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDtTime() == null ? 0 : this.getDeDtTime().hashCode() );
         result = 37 * result + ( getVeTml() == null ? 0 : this.getVeTml().hashCode() );
         result = 37 * result + ( getVeUser() == null ? 0 : this.getVeUser().hashCode() );
         result = 37 * result + ( getVeDtTime() == null ? 0 : this.getVeDtTime().hashCode() );
         return result;
   }   





}