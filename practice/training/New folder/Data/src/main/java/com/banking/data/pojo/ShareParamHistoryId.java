package com.banking.data.pojo;
// default package



/**
 * ShareParamHistoryId entity. @author MyEclipse Persistence Tools
 */

public class ShareParamHistoryId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private Integer memCat;
     private Integer prmCode;
     private String prmDesc;
     private Double prmAmt;
     private String prmFreq;
     private String prmTy;
     private String prmGlKey;


    // Constructors

    /** default constructor */
    public ShareParamHistoryId() {
    }

	/** minimal constructor */
    public ShareParamHistoryId(Integer prmCode) {
        this.prmCode = prmCode;
    }
    
    /** full constructor */
    public ShareParamHistoryId(String acType, Integer memCat, Integer prmCode, String prmDesc, Double prmAmt, String prmFreq, String prmTy, String prmGlKey) {
        this.acType = acType;
        this.memCat = memCat;
        this.prmCode = prmCode;
        this.prmDesc = prmDesc;
        this.prmAmt = prmAmt;
        this.prmFreq = prmFreq;
        this.prmTy = prmTy;
        this.prmGlKey = prmGlKey;
    }

   
    // Property accessors

    public String getAcType() {
        return this.acType;
    }
    
    public void setAcType(String acType) {
        this.acType = acType;
    }

    public Integer getMemCat() {
        return this.memCat;
    }
    
    public void setMemCat(Integer memCat) {
        this.memCat = memCat;
    }

    public Integer getPrmCode() {
        return this.prmCode;
    }
    
    public void setPrmCode(Integer prmCode) {
        this.prmCode = prmCode;
    }

    public String getPrmDesc() {
        return this.prmDesc;
    }
    
    public void setPrmDesc(String prmDesc) {
        this.prmDesc = prmDesc;
    }

    public Double getPrmAmt() {
        return this.prmAmt;
    }
    
    public void setPrmAmt(Double prmAmt) {
        this.prmAmt = prmAmt;
    }

    public String getPrmFreq() {
        return this.prmFreq;
    }
    
    public void setPrmFreq(String prmFreq) {
        this.prmFreq = prmFreq;
    }

    public String getPrmTy() {
        return this.prmTy;
    }
    
    public void setPrmTy(String prmTy) {
        this.prmTy = prmTy;
    }

    public String getPrmGlKey() {
        return this.prmGlKey;
    }
    
    public void setPrmGlKey(String prmGlKey) {
        this.prmGlKey = prmGlKey;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ShareParamHistoryId) ) return false;
		 ShareParamHistoryId castOther = ( ShareParamHistoryId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getMemCat()==castOther.getMemCat()) || ( this.getMemCat()!=null && castOther.getMemCat()!=null && this.getMemCat().equals(castOther.getMemCat()) ) )
 && ( (this.getPrmCode()==castOther.getPrmCode()) || ( this.getPrmCode()!=null && castOther.getPrmCode()!=null && this.getPrmCode().equals(castOther.getPrmCode()) ) )
 && ( (this.getPrmDesc()==castOther.getPrmDesc()) || ( this.getPrmDesc()!=null && castOther.getPrmDesc()!=null && this.getPrmDesc().equals(castOther.getPrmDesc()) ) )
 && ( (this.getPrmAmt()==castOther.getPrmAmt()) || ( this.getPrmAmt()!=null && castOther.getPrmAmt()!=null && this.getPrmAmt().equals(castOther.getPrmAmt()) ) )
 && ( (this.getPrmFreq()==castOther.getPrmFreq()) || ( this.getPrmFreq()!=null && castOther.getPrmFreq()!=null && this.getPrmFreq().equals(castOther.getPrmFreq()) ) )
 && ( (this.getPrmTy()==castOther.getPrmTy()) || ( this.getPrmTy()!=null && castOther.getPrmTy()!=null && this.getPrmTy().equals(castOther.getPrmTy()) ) )
 && ( (this.getPrmGlKey()==castOther.getPrmGlKey()) || ( this.getPrmGlKey()!=null && castOther.getPrmGlKey()!=null && this.getPrmGlKey().equals(castOther.getPrmGlKey()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getMemCat() == null ? 0 : this.getMemCat().hashCode() );
         result = 37 * result + ( getPrmCode() == null ? 0 : this.getPrmCode().hashCode() );
         result = 37 * result + ( getPrmDesc() == null ? 0 : this.getPrmDesc().hashCode() );
         result = 37 * result + ( getPrmAmt() == null ? 0 : this.getPrmAmt().hashCode() );
         result = 37 * result + ( getPrmFreq() == null ? 0 : this.getPrmFreq().hashCode() );
         result = 37 * result + ( getPrmTy() == null ? 0 : this.getPrmTy().hashCode() );
         result = 37 * result + ( getPrmGlKey() == null ? 0 : this.getPrmGlKey().hashCode() );
         return result;
   }   





}