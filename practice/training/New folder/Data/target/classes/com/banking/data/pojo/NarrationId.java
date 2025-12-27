package com.banking.data.pojo;
// default package



/**
 * NarrationId entity. @author MyEclipse Persistence Tools
 */

public class NarrationId  implements java.io.Serializable {


    // Fields    

     private String vchTy;
     private Integer vchNo;
     private String trnDt;
     private Integer seqNo;
     private String narr;


    // Constructors

    /** default constructor */
    public NarrationId() {
    }

    
    /** full constructor */
    public NarrationId(String vchTy, Integer vchNo, String trnDt, Integer seqNo, String narr) {
        this.vchTy = vchTy;
        this.vchNo = vchNo;
        this.trnDt = trnDt;
        this.seqNo = seqNo;
        this.narr = narr;
    }

   
    // Property accessors

    public String getVchTy() {
        return this.vchTy;
    }
    
    public void setVchTy(String vchTy) {
        this.vchTy = vchTy;
    }

    public Integer getVchNo() {
        return this.vchNo;
    }
    
    public void setVchNo(Integer vchNo) {
        this.vchNo = vchNo;
    }

    public String getTrnDt() {
        return this.trnDt;
    }
    
    public void setTrnDt(String trnDt) {
        this.trnDt = trnDt;
    }

    public Integer getSeqNo() {
        return this.seqNo;
    }
    
    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
    }

    public String getNarr() {
        return this.narr;
    }
    
    public void setNarr(String narr) {
        this.narr = narr;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof NarrationId) ) return false;
		 NarrationId castOther = ( NarrationId ) other; 
         
		 return ( (this.getVchTy()==castOther.getVchTy()) || ( this.getVchTy()!=null && castOther.getVchTy()!=null && this.getVchTy().equals(castOther.getVchTy()) ) )
 && ( (this.getVchNo()==castOther.getVchNo()) || ( this.getVchNo()!=null && castOther.getVchNo()!=null && this.getVchNo().equals(castOther.getVchNo()) ) )
 && ( (this.getTrnDt()==castOther.getTrnDt()) || ( this.getTrnDt()!=null && castOther.getTrnDt()!=null && this.getTrnDt().equals(castOther.getTrnDt()) ) )
 && ( (this.getSeqNo()==castOther.getSeqNo()) || ( this.getSeqNo()!=null && castOther.getSeqNo()!=null && this.getSeqNo().equals(castOther.getSeqNo()) ) )
 && ( (this.getNarr()==castOther.getNarr()) || ( this.getNarr()!=null && castOther.getNarr()!=null && this.getNarr().equals(castOther.getNarr()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getVchTy() == null ? 0 : this.getVchTy().hashCode() );
         result = 37 * result + ( getVchNo() == null ? 0 : this.getVchNo().hashCode() );
         result = 37 * result + ( getTrnDt() == null ? 0 : this.getTrnDt().hashCode() );
         result = 37 * result + ( getSeqNo() == null ? 0 : this.getSeqNo().hashCode() );
         result = 37 * result + ( getNarr() == null ? 0 : this.getNarr().hashCode() );
         return result;
   }   





}