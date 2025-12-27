package com.banking.data.pojo;
// default package



/**
 * JointHoldersLogId entity. @author MyEclipse Persistence Tools
 */

public class JointHoldersLogId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private Integer acNo;
     private Integer cid;
     private Integer addrType;


    // Constructors

    /** default constructor */
    public JointHoldersLogId() {
    }

    
    /** full constructor */
    public JointHoldersLogId(String acType, Integer acNo, Integer cid, Integer addrType) {
        this.acType = acType;
        this.acNo = acNo;
        this.cid = cid;
        this.addrType = addrType;
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

    public Integer getAddrType() {
        return this.addrType;
    }
    
    public void setAddrType(Integer addrType) {
        this.addrType = addrType;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof JointHoldersLogId) ) return false;
		 JointHoldersLogId castOther = ( JointHoldersLogId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getCid()==castOther.getCid()) || ( this.getCid()!=null && castOther.getCid()!=null && this.getCid().equals(castOther.getCid()) ) )
 && ( (this.getAddrType()==castOther.getAddrType()) || ( this.getAddrType()!=null && castOther.getAddrType()!=null && this.getAddrType().equals(castOther.getAddrType()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getCid() == null ? 0 : this.getCid().hashCode() );
         result = 37 * result + ( getAddrType() == null ? 0 : this.getAddrType().hashCode() );
         return result;
   }   





}