package com.banking.data.pojo;
// default package



/**
 * RelativeMasterId entity. @author MyEclipse Persistence Tools
 */

public class RelativeMasterId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private Integer acNo;
     private String reltype;
     private String name;
     private String age;
     private String sex;
     private String marital;
     private String status;
     private String relation;


    // Constructors

    /** default constructor */
    public RelativeMasterId() {
    }

    
    /** full constructor */
    public RelativeMasterId(String acType, Integer acNo, String reltype, String name, String age, String sex, String marital, String status, String relation) {
        this.acType = acType;
        this.acNo = acNo;
        this.reltype = reltype;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.marital = marital;
        this.status = status;
        this.relation = relation;
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

    public String getReltype() {
        return this.reltype;
    }
    
    public void setReltype(String reltype) {
        this.reltype = reltype;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return this.age;
    }
    
    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return this.sex;
    }
    
    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMarital() {
        return this.marital;
    }
    
    public void setMarital(String marital) {
        this.marital = marital;
    }

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public String getRelation() {
        return this.relation;
    }
    
    public void setRelation(String relation) {
        this.relation = relation;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof RelativeMasterId) ) return false;
		 RelativeMasterId castOther = ( RelativeMasterId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getReltype()==castOther.getReltype()) || ( this.getReltype()!=null && castOther.getReltype()!=null && this.getReltype().equals(castOther.getReltype()) ) )
 && ( (this.getName()==castOther.getName()) || ( this.getName()!=null && castOther.getName()!=null && this.getName().equals(castOther.getName()) ) )
 && ( (this.getAge()==castOther.getAge()) || ( this.getAge()!=null && castOther.getAge()!=null && this.getAge().equals(castOther.getAge()) ) )
 && ( (this.getSex()==castOther.getSex()) || ( this.getSex()!=null && castOther.getSex()!=null && this.getSex().equals(castOther.getSex()) ) )
 && ( (this.getMarital()==castOther.getMarital()) || ( this.getMarital()!=null && castOther.getMarital()!=null && this.getMarital().equals(castOther.getMarital()) ) )
 && ( (this.getStatus()==castOther.getStatus()) || ( this.getStatus()!=null && castOther.getStatus()!=null && this.getStatus().equals(castOther.getStatus()) ) )
 && ( (this.getRelation()==castOther.getRelation()) || ( this.getRelation()!=null && castOther.getRelation()!=null && this.getRelation().equals(castOther.getRelation()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getReltype() == null ? 0 : this.getReltype().hashCode() );
         result = 37 * result + ( getName() == null ? 0 : this.getName().hashCode() );
         result = 37 * result + ( getAge() == null ? 0 : this.getAge().hashCode() );
         result = 37 * result + ( getSex() == null ? 0 : this.getSex().hashCode() );
         result = 37 * result + ( getMarital() == null ? 0 : this.getMarital().hashCode() );
         result = 37 * result + ( getStatus() == null ? 0 : this.getStatus().hashCode() );
         result = 37 * result + ( getRelation() == null ? 0 : this.getRelation().hashCode() );
         return result;
   }   





}