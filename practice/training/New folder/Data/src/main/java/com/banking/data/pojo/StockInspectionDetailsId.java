package com.banking.data.pojo;
// default package



/**
 * StockInspectionDetailsId entity. @author MyEclipse Persistence Tools
 */

public class StockInspectionDetailsId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private Integer acNo;
     private String inspDate;
     private Double stockValue;
     private Double creditLimit;
     private String nextInspDate;
     private String deUser;
     private String deTml;
     private String deDate;


    // Constructors

    /** default constructor */
    public StockInspectionDetailsId() {
    }

    
    /** full constructor */
    public StockInspectionDetailsId(String acType, Integer acNo, String inspDate, Double stockValue, Double creditLimit, String nextInspDate, String deUser, String deTml, String deDate) {
        this.acType = acType;
        this.acNo = acNo;
        this.inspDate = inspDate;
        this.stockValue = stockValue;
        this.creditLimit = creditLimit;
        this.nextInspDate = nextInspDate;
        this.deUser = deUser;
        this.deTml = deTml;
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

    public String getInspDate() {
        return this.inspDate;
    }
    
    public void setInspDate(String inspDate) {
        this.inspDate = inspDate;
    }

    public Double getStockValue() {
        return this.stockValue;
    }
    
    public void setStockValue(Double stockValue) {
        this.stockValue = stockValue;
    }

    public Double getCreditLimit() {
        return this.creditLimit;
    }
    
    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getNextInspDate() {
        return this.nextInspDate;
    }
    
    public void setNextInspDate(String nextInspDate) {
        this.nextInspDate = nextInspDate;
    }

    public String getDeUser() {
        return this.deUser;
    }
    
    public void setDeUser(String deUser) {
        this.deUser = deUser;
    }

    public String getDeTml() {
        return this.deTml;
    }
    
    public void setDeTml(String deTml) {
        this.deTml = deTml;
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
		 if ( !(other instanceof StockInspectionDetailsId) ) return false;
		 StockInspectionDetailsId castOther = ( StockInspectionDetailsId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getInspDate()==castOther.getInspDate()) || ( this.getInspDate()!=null && castOther.getInspDate()!=null && this.getInspDate().equals(castOther.getInspDate()) ) )
 && ( (this.getStockValue()==castOther.getStockValue()) || ( this.getStockValue()!=null && castOther.getStockValue()!=null && this.getStockValue().equals(castOther.getStockValue()) ) )
 && ( (this.getCreditLimit()==castOther.getCreditLimit()) || ( this.getCreditLimit()!=null && castOther.getCreditLimit()!=null && this.getCreditLimit().equals(castOther.getCreditLimit()) ) )
 && ( (this.getNextInspDate()==castOther.getNextInspDate()) || ( this.getNextInspDate()!=null && castOther.getNextInspDate()!=null && this.getNextInspDate().equals(castOther.getNextInspDate()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getInspDate() == null ? 0 : this.getInspDate().hashCode() );
         result = 37 * result + ( getStockValue() == null ? 0 : this.getStockValue().hashCode() );
         result = 37 * result + ( getCreditLimit() == null ? 0 : this.getCreditLimit().hashCode() );
         result = 37 * result + ( getNextInspDate() == null ? 0 : this.getNextInspDate().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         return result;
   }   





}