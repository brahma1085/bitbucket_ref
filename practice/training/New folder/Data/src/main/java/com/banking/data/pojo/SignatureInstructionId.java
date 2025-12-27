package com.banking.data.pojo;
// default package



/**
 * SignatureInstructionId entity. @author MyEclipse Persistence Tools
 */

public class SignatureInstructionId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private Integer acNo;
     private Integer cid;
     private String name;
     private String desg;
     private Integer minlmt;
     private Integer maxlmt;
     private String typeofopr;


    // Constructors

    /** default constructor */
    public SignatureInstructionId() {
    }

    
    /** full constructor */
    public SignatureInstructionId(String acType, Integer acNo, Integer cid, String name, String desg, Integer minlmt, Integer maxlmt, String typeofopr) {
        this.acType = acType;
        this.acNo = acNo;
        this.cid = cid;
        this.name = name;
        this.desg = desg;
        this.minlmt = minlmt;
        this.maxlmt = maxlmt;
        this.typeofopr = typeofopr;
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

    public Integer getCid() {
        return this.cid;
    }
    
    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getDesg() {
        return this.desg;
    }
    
    public void setDesg(String desg) {
        this.desg = desg;
    }

    public Integer getMinlmt() {
        return this.minlmt;
    }
    
    public void setMinlmt(Integer minlmt) {
        this.minlmt = minlmt;
    }

    public Integer getMaxlmt() {
        return this.maxlmt;
    }
    
    public void setMaxlmt(Integer maxlmt) {
        this.maxlmt = maxlmt;
    }

    public String getTypeofopr() {
        return this.typeofopr;
    }
    
    public void setTypeofopr(String typeofopr) {
        this.typeofopr = typeofopr;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof SignatureInstructionId) ) return false;
		 SignatureInstructionId castOther = ( SignatureInstructionId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getCid()==castOther.getCid()) || ( this.getCid()!=null && castOther.getCid()!=null && this.getCid().equals(castOther.getCid()) ) )
 && ( (this.getName()==castOther.getName()) || ( this.getName()!=null && castOther.getName()!=null && this.getName().equals(castOther.getName()) ) )
 && ( (this.getDesg()==castOther.getDesg()) || ( this.getDesg()!=null && castOther.getDesg()!=null && this.getDesg().equals(castOther.getDesg()) ) )
 && ( (this.getMinlmt()==castOther.getMinlmt()) || ( this.getMinlmt()!=null && castOther.getMinlmt()!=null && this.getMinlmt().equals(castOther.getMinlmt()) ) )
 && ( (this.getMaxlmt()==castOther.getMaxlmt()) || ( this.getMaxlmt()!=null && castOther.getMaxlmt()!=null && this.getMaxlmt().equals(castOther.getMaxlmt()) ) )
 && ( (this.getTypeofopr()==castOther.getTypeofopr()) || ( this.getTypeofopr()!=null && castOther.getTypeofopr()!=null && this.getTypeofopr().equals(castOther.getTypeofopr()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getCid() == null ? 0 : this.getCid().hashCode() );
         result = 37 * result + ( getName() == null ? 0 : this.getName().hashCode() );
         result = 37 * result + ( getDesg() == null ? 0 : this.getDesg().hashCode() );
         result = 37 * result + ( getMinlmt() == null ? 0 : this.getMinlmt().hashCode() );
         result = 37 * result + ( getMaxlmt() == null ? 0 : this.getMaxlmt().hashCode() );
         result = 37 * result + ( getTypeofopr() == null ? 0 : this.getTypeofopr().hashCode() );
         return result;
   }   





}