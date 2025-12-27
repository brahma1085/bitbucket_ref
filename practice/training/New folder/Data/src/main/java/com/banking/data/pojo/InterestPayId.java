package com.banking.data.pojo;
// default package



/**
 * InterestPayId entity. @author MyEclipse Persistence Tools
 */

public class InterestPayId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private Integer acNo;
     private String trnDate;
     private String postInd;
     private Double trnAmt;
     private Double intRate;
     private Double prod1;
     private Double prod2;
     private Double prod3;
     private Double prod4;
     private Double prod5;
     private Double prod6;
     private String poststrt;
     private String postend;
     private String deTml;
     private String deUser;
     private String deDate;


    // Constructors

    /** default constructor */
    public InterestPayId() {
    }

    
    /** full constructor */
    public InterestPayId(String acType, Integer acNo, String trnDate, String postInd, Double trnAmt, Double intRate, Double prod1, Double prod2, Double prod3, Double prod4, Double prod5, Double prod6, String poststrt, String postend, String deTml, String deUser, String deDate) {
        this.acType = acType;
        this.acNo = acNo;
        this.trnDate = trnDate;
        this.postInd = postInd;
        this.trnAmt = trnAmt;
        this.intRate = intRate;
        this.prod1 = prod1;
        this.prod2 = prod2;
        this.prod3 = prod3;
        this.prod4 = prod4;
        this.prod5 = prod5;
        this.prod6 = prod6;
        this.poststrt = poststrt;
        this.postend = postend;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
    }

   
    // Property accessors

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

    public String getTrnDate() {
        return this.trnDate;
    }
    
    public void setTrnDate(String trnDate) {
        this.trnDate = trnDate;
    }

    public String getPostInd() {
        return this.postInd;
    }
    
    public void setPostInd(String postInd) {
        this.postInd = postInd;
    }

    public Double getTrnAmt() {
        return this.trnAmt;
    }
    
    public void setTrnAmt(Double trnAmt) {
        this.trnAmt = trnAmt;
    }

    public Double getIntRate() {
        return this.intRate;
    }
    
    public void setIntRate(Double intRate) {
        this.intRate = intRate;
    }

    public Double getProd1() {
        return this.prod1;
    }
    
    public void setProd1(Double prod1) {
        this.prod1 = prod1;
    }

    public Double getProd2() {
        return this.prod2;
    }
    
    public void setProd2(Double prod2) {
        this.prod2 = prod2;
    }

    public Double getProd3() {
        return this.prod3;
    }
    
    public void setProd3(Double prod3) {
        this.prod3 = prod3;
    }

    public Double getProd4() {
        return this.prod4;
    }
    
    public void setProd4(Double prod4) {
        this.prod4 = prod4;
    }

    public Double getProd5() {
        return this.prod5;
    }
    
    public void setProd5(Double prod5) {
        this.prod5 = prod5;
    }

    public Double getProd6() {
        return this.prod6;
    }
    
    public void setProd6(Double prod6) {
        this.prod6 = prod6;
    }

    public String getPoststrt() {
        return this.poststrt;
    }
    
    public void setPoststrt(String poststrt) {
        this.poststrt = poststrt;
    }

    public String getPostend() {
        return this.postend;
    }
    
    public void setPostend(String postend) {
        this.postend = postend;
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
		 if ( !(other instanceof InterestPayId) ) return false;
		 InterestPayId castOther = ( InterestPayId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getTrnDate()==castOther.getTrnDate()) || ( this.getTrnDate()!=null && castOther.getTrnDate()!=null && this.getTrnDate().equals(castOther.getTrnDate()) ) )
 && ( (this.getPostInd()==castOther.getPostInd()) || ( this.getPostInd()!=null && castOther.getPostInd()!=null && this.getPostInd().equals(castOther.getPostInd()) ) )
 && ( (this.getTrnAmt()==castOther.getTrnAmt()) || ( this.getTrnAmt()!=null && castOther.getTrnAmt()!=null && this.getTrnAmt().equals(castOther.getTrnAmt()) ) )
 && ( (this.getIntRate()==castOther.getIntRate()) || ( this.getIntRate()!=null && castOther.getIntRate()!=null && this.getIntRate().equals(castOther.getIntRate()) ) )
 && ( (this.getProd1()==castOther.getProd1()) || ( this.getProd1()!=null && castOther.getProd1()!=null && this.getProd1().equals(castOther.getProd1()) ) )
 && ( (this.getProd2()==castOther.getProd2()) || ( this.getProd2()!=null && castOther.getProd2()!=null && this.getProd2().equals(castOther.getProd2()) ) )
 && ( (this.getProd3()==castOther.getProd3()) || ( this.getProd3()!=null && castOther.getProd3()!=null && this.getProd3().equals(castOther.getProd3()) ) )
 && ( (this.getProd4()==castOther.getProd4()) || ( this.getProd4()!=null && castOther.getProd4()!=null && this.getProd4().equals(castOther.getProd4()) ) )
 && ( (this.getProd5()==castOther.getProd5()) || ( this.getProd5()!=null && castOther.getProd5()!=null && this.getProd5().equals(castOther.getProd5()) ) )
 && ( (this.getProd6()==castOther.getProd6()) || ( this.getProd6()!=null && castOther.getProd6()!=null && this.getProd6().equals(castOther.getProd6()) ) )
 && ( (this.getPoststrt()==castOther.getPoststrt()) || ( this.getPoststrt()!=null && castOther.getPoststrt()!=null && this.getPoststrt().equals(castOther.getPoststrt()) ) )
 && ( (this.getPostend()==castOther.getPostend()) || ( this.getPostend()!=null && castOther.getPostend()!=null && this.getPostend().equals(castOther.getPostend()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getTrnDate() == null ? 0 : this.getTrnDate().hashCode() );
         result = 37 * result + ( getPostInd() == null ? 0 : this.getPostInd().hashCode() );
         result = 37 * result + ( getTrnAmt() == null ? 0 : this.getTrnAmt().hashCode() );
         result = 37 * result + ( getIntRate() == null ? 0 : this.getIntRate().hashCode() );
         result = 37 * result + ( getProd1() == null ? 0 : this.getProd1().hashCode() );
         result = 37 * result + ( getProd2() == null ? 0 : this.getProd2().hashCode() );
         result = 37 * result + ( getProd3() == null ? 0 : this.getProd3().hashCode() );
         result = 37 * result + ( getProd4() == null ? 0 : this.getProd4().hashCode() );
         result = 37 * result + ( getProd5() == null ? 0 : this.getProd5().hashCode() );
         result = 37 * result + ( getProd6() == null ? 0 : this.getProd6().hashCode() );
         result = 37 * result + ( getPoststrt() == null ? 0 : this.getPoststrt().hashCode() );
         result = 37 * result + ( getPostend() == null ? 0 : this.getPostend().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         return result;
   }   





}