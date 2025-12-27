package com.banking.data.pojo;
// default package



/**
 * VoucherNarrationId entity. @author MyEclipse Persistence Tools
 */

public class VoucherNarrationId  implements java.io.Serializable {


    // Fields    

     private String vchTy;
     private Integer vchNo;
     private String trnDt;
     private String narration;


    // Constructors

    /** default constructor */
    public VoucherNarrationId() {
    }

    
    /** full constructor */
    public VoucherNarrationId(String vchTy, Integer vchNo, String trnDt, String narration) {
        this.vchTy = vchTy;
        this.vchNo = vchNo;
        this.trnDt = trnDt;
        this.narration = narration;
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

    public String getNarration() {
        return this.narration;
    }
    
    public void setNarration(String narration) {
        this.narration = narration;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof VoucherNarrationId) ) return false;
		 VoucherNarrationId castOther = ( VoucherNarrationId ) other; 
         
		 return ( (this.getVchTy()==castOther.getVchTy()) || ( this.getVchTy()!=null && castOther.getVchTy()!=null && this.getVchTy().equals(castOther.getVchTy()) ) )
 && ( (this.getVchNo()==castOther.getVchNo()) || ( this.getVchNo()!=null && castOther.getVchNo()!=null && this.getVchNo().equals(castOther.getVchNo()) ) )
 && ( (this.getTrnDt()==castOther.getTrnDt()) || ( this.getTrnDt()!=null && castOther.getTrnDt()!=null && this.getTrnDt().equals(castOther.getTrnDt()) ) )
 && ( (this.getNarration()==castOther.getNarration()) || ( this.getNarration()!=null && castOther.getNarration()!=null && this.getNarration().equals(castOther.getNarration()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getVchTy() == null ? 0 : this.getVchTy().hashCode() );
         result = 37 * result + ( getVchNo() == null ? 0 : this.getVchNo().hashCode() );
         result = 37 * result + ( getTrnDt() == null ? 0 : this.getTrnDt().hashCode() );
         result = 37 * result + ( getNarration() == null ? 0 : this.getNarration().hashCode() );
         return result;
   }   





}