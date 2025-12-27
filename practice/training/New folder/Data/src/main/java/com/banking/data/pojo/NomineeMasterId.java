package com.banking.data.pojo;
// default package



/**
 * NomineeMasterId entity. @author MyEclipse Persistence Tools
 */

public class NomineeMasterId  implements java.io.Serializable {


    // Fields    

     private Integer regNo;
     private Integer cid;
     private String acType;
     private Integer acNo;
     private Integer sex;
     private String name;
     private String dob;
     private String address;
     private String relation;
     private Float percentage;
     private String guardType;
     private String guardName;
     private String guardAddress;
     private Integer guardSex;
     private String guardRel;
     private Integer courtOrderNo;
     private String courtOrderDate;
     private String frDate;
     private String toDate;


    // Constructors

    /** default constructor */
    public NomineeMasterId() {
    }

    
    /** full constructor */
    public NomineeMasterId(Integer regNo, Integer cid, String acType, Integer acNo, Integer sex, String name, String dob, String address, String relation, Float percentage, String guardType, String guardName, String guardAddress, Integer guardSex, String guardRel, Integer courtOrderNo, String courtOrderDate, String frDate, String toDate) {
        this.regNo = regNo;
        this.cid = cid;
        this.acType = acType;
        this.acNo = acNo;
        this.sex = sex;
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.relation = relation;
        this.percentage = percentage;
        this.guardType = guardType;
        this.guardName = guardName;
        this.guardAddress = guardAddress;
        this.guardSex = guardSex;
        this.guardRel = guardRel;
        this.courtOrderNo = courtOrderNo;
        this.courtOrderDate = courtOrderDate;
        this.frDate = frDate;
        this.toDate = toDate;
    }

   
    // Property accessors

    public Integer getRegNo() {
        return this.regNo;
    }
    
    public void setRegNo(Integer regNo) {
        this.regNo = regNo;
    }

    public Integer getCid() {
        return this.cid;
    }
    
    public void setCid(Integer cid) {
        this.cid = cid;
    }

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

    public Integer getSex() {
        return this.sex;
    }
    
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return this.dob;
    }
    
    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    public String getRelation() {
        return this.relation;
    }
    
    public void setRelation(String relation) {
        this.relation = relation;
    }

    public Float getPercentage() {
        return this.percentage;
    }
    
    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    public String getGuardType() {
        return this.guardType;
    }
    
    public void setGuardType(String guardType) {
        this.guardType = guardType;
    }

    public String getGuardName() {
        return this.guardName;
    }
    
    public void setGuardName(String guardName) {
        this.guardName = guardName;
    }

    public String getGuardAddress() {
        return this.guardAddress;
    }
    
    public void setGuardAddress(String guardAddress) {
        this.guardAddress = guardAddress;
    }

    public Integer getGuardSex() {
        return this.guardSex;
    }
    
    public void setGuardSex(Integer guardSex) {
        this.guardSex = guardSex;
    }

    public String getGuardRel() {
        return this.guardRel;
    }
    
    public void setGuardRel(String guardRel) {
        this.guardRel = guardRel;
    }

    public Integer getCourtOrderNo() {
        return this.courtOrderNo;
    }
    
    public void setCourtOrderNo(Integer courtOrderNo) {
        this.courtOrderNo = courtOrderNo;
    }

    public String getCourtOrderDate() {
        return this.courtOrderDate;
    }
    
    public void setCourtOrderDate(String courtOrderDate) {
        this.courtOrderDate = courtOrderDate;
    }

    public String getFrDate() {
        return this.frDate;
    }
    
    public void setFrDate(String frDate) {
        this.frDate = frDate;
    }

    public String getToDate() {
        return this.toDate;
    }
    
    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof NomineeMasterId) ) return false;
		 NomineeMasterId castOther = ( NomineeMasterId ) other; 
         
		 return ( (this.getRegNo()==castOther.getRegNo()) || ( this.getRegNo()!=null && castOther.getRegNo()!=null && this.getRegNo().equals(castOther.getRegNo()) ) )
 && ( (this.getCid()==castOther.getCid()) || ( this.getCid()!=null && castOther.getCid()!=null && this.getCid().equals(castOther.getCid()) ) )
 && ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getSex()==castOther.getSex()) || ( this.getSex()!=null && castOther.getSex()!=null && this.getSex().equals(castOther.getSex()) ) )
 && ( (this.getName()==castOther.getName()) || ( this.getName()!=null && castOther.getName()!=null && this.getName().equals(castOther.getName()) ) )
 && ( (this.getDob()==castOther.getDob()) || ( this.getDob()!=null && castOther.getDob()!=null && this.getDob().equals(castOther.getDob()) ) )
 && ( (this.getAddress()==castOther.getAddress()) || ( this.getAddress()!=null && castOther.getAddress()!=null && this.getAddress().equals(castOther.getAddress()) ) )
 && ( (this.getRelation()==castOther.getRelation()) || ( this.getRelation()!=null && castOther.getRelation()!=null && this.getRelation().equals(castOther.getRelation()) ) )
 && ( (this.getPercentage()==castOther.getPercentage()) || ( this.getPercentage()!=null && castOther.getPercentage()!=null && this.getPercentage().equals(castOther.getPercentage()) ) )
 && ( (this.getGuardType()==castOther.getGuardType()) || ( this.getGuardType()!=null && castOther.getGuardType()!=null && this.getGuardType().equals(castOther.getGuardType()) ) )
 && ( (this.getGuardName()==castOther.getGuardName()) || ( this.getGuardName()!=null && castOther.getGuardName()!=null && this.getGuardName().equals(castOther.getGuardName()) ) )
 && ( (this.getGuardAddress()==castOther.getGuardAddress()) || ( this.getGuardAddress()!=null && castOther.getGuardAddress()!=null && this.getGuardAddress().equals(castOther.getGuardAddress()) ) )
 && ( (this.getGuardSex()==castOther.getGuardSex()) || ( this.getGuardSex()!=null && castOther.getGuardSex()!=null && this.getGuardSex().equals(castOther.getGuardSex()) ) )
 && ( (this.getGuardRel()==castOther.getGuardRel()) || ( this.getGuardRel()!=null && castOther.getGuardRel()!=null && this.getGuardRel().equals(castOther.getGuardRel()) ) )
 && ( (this.getCourtOrderNo()==castOther.getCourtOrderNo()) || ( this.getCourtOrderNo()!=null && castOther.getCourtOrderNo()!=null && this.getCourtOrderNo().equals(castOther.getCourtOrderNo()) ) )
 && ( (this.getCourtOrderDate()==castOther.getCourtOrderDate()) || ( this.getCourtOrderDate()!=null && castOther.getCourtOrderDate()!=null && this.getCourtOrderDate().equals(castOther.getCourtOrderDate()) ) )
 && ( (this.getFrDate()==castOther.getFrDate()) || ( this.getFrDate()!=null && castOther.getFrDate()!=null && this.getFrDate().equals(castOther.getFrDate()) ) )
 && ( (this.getToDate()==castOther.getToDate()) || ( this.getToDate()!=null && castOther.getToDate()!=null && this.getToDate().equals(castOther.getToDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getRegNo() == null ? 0 : this.getRegNo().hashCode() );
         result = 37 * result + ( getCid() == null ? 0 : this.getCid().hashCode() );
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getSex() == null ? 0 : this.getSex().hashCode() );
         result = 37 * result + ( getName() == null ? 0 : this.getName().hashCode() );
         result = 37 * result + ( getDob() == null ? 0 : this.getDob().hashCode() );
         result = 37 * result + ( getAddress() == null ? 0 : this.getAddress().hashCode() );
         result = 37 * result + ( getRelation() == null ? 0 : this.getRelation().hashCode() );
         result = 37 * result + ( getPercentage() == null ? 0 : this.getPercentage().hashCode() );
         result = 37 * result + ( getGuardType() == null ? 0 : this.getGuardType().hashCode() );
         result = 37 * result + ( getGuardName() == null ? 0 : this.getGuardName().hashCode() );
         result = 37 * result + ( getGuardAddress() == null ? 0 : this.getGuardAddress().hashCode() );
         result = 37 * result + ( getGuardSex() == null ? 0 : this.getGuardSex().hashCode() );
         result = 37 * result + ( getGuardRel() == null ? 0 : this.getGuardRel().hashCode() );
         result = 37 * result + ( getCourtOrderNo() == null ? 0 : this.getCourtOrderNo().hashCode() );
         result = 37 * result + ( getCourtOrderDate() == null ? 0 : this.getCourtOrderDate().hashCode() );
         result = 37 * result + ( getFrDate() == null ? 0 : this.getFrDate().hashCode() );
         result = 37 * result + ( getToDate() == null ? 0 : this.getToDate().hashCode() );
         return result;
   }   





}