package com.banking.data.pojo;
// default package



/**
 * ShareBranchDataId entity. @author MyEclipse Persistence Tools
 */

public class ShareBranchDataId  implements java.io.Serializable {


    // Fields    

     private Integer tempNo;
     private Integer acNo;
     private String madePermanent;


    // Constructors

    /** default constructor */
    public ShareBranchDataId() {
    }

    
    /** full constructor */
    public ShareBranchDataId(Integer tempNo, Integer acNo, String madePermanent) {
        this.tempNo = tempNo;
        this.acNo = acNo;
        this.madePermanent = madePermanent;
    }

   
    // Property accessors

    public Integer getTempNo() {
        return this.tempNo;
    }
    
    public void setTempNo(Integer tempNo) {
        this.tempNo = tempNo;
    }

    public Integer getAcNo() {
        return this.acNo;
    }
    
    public void setAcNo(Integer acNo) {
        this.acNo = acNo;
    }

    public String getMadePermanent() {
        return this.madePermanent;
    }
    
    public void setMadePermanent(String madePermanent) {
        this.madePermanent = madePermanent;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ShareBranchDataId) ) return false;
		 ShareBranchDataId castOther = ( ShareBranchDataId ) other; 
         
		 return ( (this.getTempNo()==castOther.getTempNo()) || ( this.getTempNo()!=null && castOther.getTempNo()!=null && this.getTempNo().equals(castOther.getTempNo()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getMadePermanent()==castOther.getMadePermanent()) || ( this.getMadePermanent()!=null && castOther.getMadePermanent()!=null && this.getMadePermanent().equals(castOther.getMadePermanent()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getTempNo() == null ? 0 : this.getTempNo().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getMadePermanent() == null ? 0 : this.getMadePermanent().hashCode() );
         return result;
   }   





}