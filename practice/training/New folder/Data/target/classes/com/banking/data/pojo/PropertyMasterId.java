package com.banking.data.pojo;
// default package



/**
 * PropertyMasterId entity. @author MyEclipse Persistence Tools
 */

public class PropertyMasterId  implements java.io.Serializable {


    // Fields    

     private Integer acNo;
     private String acType;
     private String propertyNo;
     private String addr;
     private String eastWest;
     private String northSouth;
     private String eastBy;
     private String westBy;
     private String northBy;
     private String southBy;
     private Double marketValue;
     private String propertyAqd;
     private String nature;
     private String tenantName;
     private Double rentMnth;
     private String type;


    // Constructors

    /** default constructor */
    public PropertyMasterId() {
    }

    
    /** full constructor */
    public PropertyMasterId(Integer acNo, String acType, String propertyNo, String addr, String eastWest, String northSouth, String eastBy, String westBy, String northBy, String southBy, Double marketValue, String propertyAqd, String nature, String tenantName, Double rentMnth, String type) {
        this.acNo = acNo;
        this.acType = acType;
        this.propertyNo = propertyNo;
        this.addr = addr;
        this.eastWest = eastWest;
        this.northSouth = northSouth;
        this.eastBy = eastBy;
        this.westBy = westBy;
        this.northBy = northBy;
        this.southBy = southBy;
        this.marketValue = marketValue;
        this.propertyAqd = propertyAqd;
        this.nature = nature;
        this.tenantName = tenantName;
        this.rentMnth = rentMnth;
        this.type = type;
    }

   
    // Property accessors

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

    public String getPropertyNo() {
        return this.propertyNo;
    }
    
    public void setPropertyNo(String propertyNo) {
        this.propertyNo = propertyNo;
    }

    public String getAddr() {
        return this.addr;
    }
    
    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getEastWest() {
        return this.eastWest;
    }
    
    public void setEastWest(String eastWest) {
        this.eastWest = eastWest;
    }

    public String getNorthSouth() {
        return this.northSouth;
    }
    
    public void setNorthSouth(String northSouth) {
        this.northSouth = northSouth;
    }

    public String getEastBy() {
        return this.eastBy;
    }
    
    public void setEastBy(String eastBy) {
        this.eastBy = eastBy;
    }

    public String getWestBy() {
        return this.westBy;
    }
    
    public void setWestBy(String westBy) {
        this.westBy = westBy;
    }

    public String getNorthBy() {
        return this.northBy;
    }
    
    public void setNorthBy(String northBy) {
        this.northBy = northBy;
    }

    public String getSouthBy() {
        return this.southBy;
    }
    
    public void setSouthBy(String southBy) {
        this.southBy = southBy;
    }

    public Double getMarketValue() {
        return this.marketValue;
    }
    
    public void setMarketValue(Double marketValue) {
        this.marketValue = marketValue;
    }

    public String getPropertyAqd() {
        return this.propertyAqd;
    }
    
    public void setPropertyAqd(String propertyAqd) {
        this.propertyAqd = propertyAqd;
    }

    public String getNature() {
        return this.nature;
    }
    
    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getTenantName() {
        return this.tenantName;
    }
    
    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public Double getRentMnth() {
        return this.rentMnth;
    }
    
    public void setRentMnth(Double rentMnth) {
        this.rentMnth = rentMnth;
    }

    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof PropertyMasterId) ) return false;
		 PropertyMasterId castOther = ( PropertyMasterId ) other; 
         
		 return ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getPropertyNo()==castOther.getPropertyNo()) || ( this.getPropertyNo()!=null && castOther.getPropertyNo()!=null && this.getPropertyNo().equals(castOther.getPropertyNo()) ) )
 && ( (this.getAddr()==castOther.getAddr()) || ( this.getAddr()!=null && castOther.getAddr()!=null && this.getAddr().equals(castOther.getAddr()) ) )
 && ( (this.getEastWest()==castOther.getEastWest()) || ( this.getEastWest()!=null && castOther.getEastWest()!=null && this.getEastWest().equals(castOther.getEastWest()) ) )
 && ( (this.getNorthSouth()==castOther.getNorthSouth()) || ( this.getNorthSouth()!=null && castOther.getNorthSouth()!=null && this.getNorthSouth().equals(castOther.getNorthSouth()) ) )
 && ( (this.getEastBy()==castOther.getEastBy()) || ( this.getEastBy()!=null && castOther.getEastBy()!=null && this.getEastBy().equals(castOther.getEastBy()) ) )
 && ( (this.getWestBy()==castOther.getWestBy()) || ( this.getWestBy()!=null && castOther.getWestBy()!=null && this.getWestBy().equals(castOther.getWestBy()) ) )
 && ( (this.getNorthBy()==castOther.getNorthBy()) || ( this.getNorthBy()!=null && castOther.getNorthBy()!=null && this.getNorthBy().equals(castOther.getNorthBy()) ) )
 && ( (this.getSouthBy()==castOther.getSouthBy()) || ( this.getSouthBy()!=null && castOther.getSouthBy()!=null && this.getSouthBy().equals(castOther.getSouthBy()) ) )
 && ( (this.getMarketValue()==castOther.getMarketValue()) || ( this.getMarketValue()!=null && castOther.getMarketValue()!=null && this.getMarketValue().equals(castOther.getMarketValue()) ) )
 && ( (this.getPropertyAqd()==castOther.getPropertyAqd()) || ( this.getPropertyAqd()!=null && castOther.getPropertyAqd()!=null && this.getPropertyAqd().equals(castOther.getPropertyAqd()) ) )
 && ( (this.getNature()==castOther.getNature()) || ( this.getNature()!=null && castOther.getNature()!=null && this.getNature().equals(castOther.getNature()) ) )
 && ( (this.getTenantName()==castOther.getTenantName()) || ( this.getTenantName()!=null && castOther.getTenantName()!=null && this.getTenantName().equals(castOther.getTenantName()) ) )
 && ( (this.getRentMnth()==castOther.getRentMnth()) || ( this.getRentMnth()!=null && castOther.getRentMnth()!=null && this.getRentMnth().equals(castOther.getRentMnth()) ) )
 && ( (this.getType()==castOther.getType()) || ( this.getType()!=null && castOther.getType()!=null && this.getType().equals(castOther.getType()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getPropertyNo() == null ? 0 : this.getPropertyNo().hashCode() );
         result = 37 * result + ( getAddr() == null ? 0 : this.getAddr().hashCode() );
         result = 37 * result + ( getEastWest() == null ? 0 : this.getEastWest().hashCode() );
         result = 37 * result + ( getNorthSouth() == null ? 0 : this.getNorthSouth().hashCode() );
         result = 37 * result + ( getEastBy() == null ? 0 : this.getEastBy().hashCode() );
         result = 37 * result + ( getWestBy() == null ? 0 : this.getWestBy().hashCode() );
         result = 37 * result + ( getNorthBy() == null ? 0 : this.getNorthBy().hashCode() );
         result = 37 * result + ( getSouthBy() == null ? 0 : this.getSouthBy().hashCode() );
         result = 37 * result + ( getMarketValue() == null ? 0 : this.getMarketValue().hashCode() );
         result = 37 * result + ( getPropertyAqd() == null ? 0 : this.getPropertyAqd().hashCode() );
         result = 37 * result + ( getNature() == null ? 0 : this.getNature().hashCode() );
         result = 37 * result + ( getTenantName() == null ? 0 : this.getTenantName().hashCode() );
         result = 37 * result + ( getRentMnth() == null ? 0 : this.getRentMnth().hashCode() );
         result = 37 * result + ( getType() == null ? 0 : this.getType().hashCode() );
         return result;
   }   





}