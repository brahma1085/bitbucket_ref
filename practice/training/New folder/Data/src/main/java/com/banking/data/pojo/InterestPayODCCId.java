package com.banking.data.pojo;
// default package



/**
 * InterestPayODCCId entity. @author MyEclipse Persistence Tools
 */

public class InterestPayODCCId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private Integer acNo;
     private String name;
     private Double prevBal;
     private Double intAmt;
     private Double penalInt;
     private Double calculatedBal;
     private Boolean posting;
     private Boolean exceeds;
     private Double sancAmt;
     private String trnDate;


    // Constructors

    /** default constructor */
    public InterestPayODCCId() {
    }

    
    /** full constructor */
    public InterestPayODCCId(String acType, Integer acNo, String name, Double prevBal, Double intAmt, Double penalInt, Double calculatedBal, Boolean posting, Boolean exceeds, Double sancAmt, String trnDate) {
        this.acType = acType;
        this.acNo = acNo;
        this.name = name;
        this.prevBal = prevBal;
        this.intAmt = intAmt;
        this.penalInt = penalInt;
        this.calculatedBal = calculatedBal;
        this.posting = posting;
        this.exceeds = exceeds;
        this.sancAmt = sancAmt;
        this.trnDate = trnDate;
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

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public Double getPrevBal() {
        return this.prevBal;
    }
    
    public void setPrevBal(Double prevBal) {
        this.prevBal = prevBal;
    }

    public Double getIntAmt() {
        return this.intAmt;
    }
    
    public void setIntAmt(Double intAmt) {
        this.intAmt = intAmt;
    }

    public Double getPenalInt() {
        return this.penalInt;
    }
    
    public void setPenalInt(Double penalInt) {
        this.penalInt = penalInt;
    }

    public Double getCalculatedBal() {
        return this.calculatedBal;
    }
    
    public void setCalculatedBal(Double calculatedBal) {
        this.calculatedBal = calculatedBal;
    }

    public Boolean getPosting() {
        return this.posting;
    }
    
    public void setPosting(Boolean posting) {
        this.posting = posting;
    }

    public Boolean getExceeds() {
        return this.exceeds;
    }
    
    public void setExceeds(Boolean exceeds) {
        this.exceeds = exceeds;
    }

    public Double getSancAmt() {
        return this.sancAmt;
    }
    
    public void setSancAmt(Double sancAmt) {
        this.sancAmt = sancAmt;
    }

    public String getTrnDate() {
        return this.trnDate;
    }
    
    public void setTrnDate(String trnDate) {
        this.trnDate = trnDate;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof InterestPayODCCId) ) return false;
		 InterestPayODCCId castOther = ( InterestPayODCCId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getName()==castOther.getName()) || ( this.getName()!=null && castOther.getName()!=null && this.getName().equals(castOther.getName()) ) )
 && ( (this.getPrevBal()==castOther.getPrevBal()) || ( this.getPrevBal()!=null && castOther.getPrevBal()!=null && this.getPrevBal().equals(castOther.getPrevBal()) ) )
 && ( (this.getIntAmt()==castOther.getIntAmt()) || ( this.getIntAmt()!=null && castOther.getIntAmt()!=null && this.getIntAmt().equals(castOther.getIntAmt()) ) )
 && ( (this.getPenalInt()==castOther.getPenalInt()) || ( this.getPenalInt()!=null && castOther.getPenalInt()!=null && this.getPenalInt().equals(castOther.getPenalInt()) ) )
 && ( (this.getCalculatedBal()==castOther.getCalculatedBal()) || ( this.getCalculatedBal()!=null && castOther.getCalculatedBal()!=null && this.getCalculatedBal().equals(castOther.getCalculatedBal()) ) )
 && ( (this.getPosting()==castOther.getPosting()) || ( this.getPosting()!=null && castOther.getPosting()!=null && this.getPosting().equals(castOther.getPosting()) ) )
 && ( (this.getExceeds()==castOther.getExceeds()) || ( this.getExceeds()!=null && castOther.getExceeds()!=null && this.getExceeds().equals(castOther.getExceeds()) ) )
 && ( (this.getSancAmt()==castOther.getSancAmt()) || ( this.getSancAmt()!=null && castOther.getSancAmt()!=null && this.getSancAmt().equals(castOther.getSancAmt()) ) )
 && ( (this.getTrnDate()==castOther.getTrnDate()) || ( this.getTrnDate()!=null && castOther.getTrnDate()!=null && this.getTrnDate().equals(castOther.getTrnDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getName() == null ? 0 : this.getName().hashCode() );
         result = 37 * result + ( getPrevBal() == null ? 0 : this.getPrevBal().hashCode() );
         result = 37 * result + ( getIntAmt() == null ? 0 : this.getIntAmt().hashCode() );
         result = 37 * result + ( getPenalInt() == null ? 0 : this.getPenalInt().hashCode() );
         result = 37 * result + ( getCalculatedBal() == null ? 0 : this.getCalculatedBal().hashCode() );
         result = 37 * result + ( getPosting() == null ? 0 : this.getPosting().hashCode() );
         result = 37 * result + ( getExceeds() == null ? 0 : this.getExceeds().hashCode() );
         result = 37 * result + ( getSancAmt() == null ? 0 : this.getSancAmt().hashCode() );
         result = 37 * result + ( getTrnDate() == null ? 0 : this.getTrnDate().hashCode() );
         return result;
   }   





}