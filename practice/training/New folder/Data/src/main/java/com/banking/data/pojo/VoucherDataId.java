package com.banking.data.pojo;
// default package



/**
 * VoucherDataId entity. @author MyEclipse Persistence Tools
 */

public class VoucherDataId  implements java.io.Serializable {


    // Fields    

     private String vchType;
     private Integer vchNo;
     private String vchMode;
     private String trnDate;
     private String narration;
     private String modAcType;
     private Integer modAcNo;
     private String trnType;
     private String glSlType;
     private Integer glSlCode;
     private Integer chqNo;
     private String chqDt;
     private Double trnAmt;
     private String cdInd;
     private String cashPdrd;
     private String deUser;
     private String deTml;
     private String deDate;
     private String veUser;
     private String veTml;
     private String veDate;


    // Constructors

    /** default constructor */
    public VoucherDataId() {
    }

    
    /** full constructor */
    public VoucherDataId(String vchType, Integer vchNo, String vchMode, String trnDate, String narration, String modAcType, Integer modAcNo, String trnType, String glSlType, Integer glSlCode, Integer chqNo, String chqDt, Double trnAmt, String cdInd, String cashPdrd, String deUser, String deTml, String deDate, String veUser, String veTml, String veDate) {
        this.vchType = vchType;
        this.vchNo = vchNo;
        this.vchMode = vchMode;
        this.trnDate = trnDate;
        this.narration = narration;
        this.modAcType = modAcType;
        this.modAcNo = modAcNo;
        this.trnType = trnType;
        this.glSlType = glSlType;
        this.glSlCode = glSlCode;
        this.chqNo = chqNo;
        this.chqDt = chqDt;
        this.trnAmt = trnAmt;
        this.cdInd = cdInd;
        this.cashPdrd = cashPdrd;
        this.deUser = deUser;
        this.deTml = deTml;
        this.deDate = deDate;
        this.veUser = veUser;
        this.veTml = veTml;
        this.veDate = veDate;
    }

   
    // Property accessors

    public String getVchType() {
        return this.vchType;
    }
    
    public void setVchType(String vchType) {
        this.vchType = vchType;
    }

    public Integer getVchNo() {
        return this.vchNo;
    }
    
    public void setVchNo(Integer vchNo) {
        this.vchNo = vchNo;
    }

    public String getVchMode() {
        return this.vchMode;
    }
    
    public void setVchMode(String vchMode) {
        this.vchMode = vchMode;
    }

    public String getTrnDate() {
        return this.trnDate;
    }
    
    public void setTrnDate(String trnDate) {
        this.trnDate = trnDate;
    }

    public String getNarration() {
        return this.narration;
    }
    
    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getModAcType() {
        return this.modAcType;
    }
    
    public void setModAcType(String modAcType) {
        this.modAcType = modAcType;
    }

    public Integer getModAcNo() {
        return this.modAcNo;
    }
    
    public void setModAcNo(Integer modAcNo) {
        this.modAcNo = modAcNo;
    }

    public String getTrnType() {
        return this.trnType;
    }
    
    public void setTrnType(String trnType) {
        this.trnType = trnType;
    }

    public String getGlSlType() {
        return this.glSlType;
    }
    
    public void setGlSlType(String glSlType) {
        this.glSlType = glSlType;
    }

    public Integer getGlSlCode() {
        return this.glSlCode;
    }
    
    public void setGlSlCode(Integer glSlCode) {
        this.glSlCode = glSlCode;
    }

    public Integer getChqNo() {
        return this.chqNo;
    }
    
    public void setChqNo(Integer chqNo) {
        this.chqNo = chqNo;
    }

    public String getChqDt() {
        return this.chqDt;
    }
    
    public void setChqDt(String chqDt) {
        this.chqDt = chqDt;
    }

    public Double getTrnAmt() {
        return this.trnAmt;
    }
    
    public void setTrnAmt(Double trnAmt) {
        this.trnAmt = trnAmt;
    }

    public String getCdInd() {
        return this.cdInd;
    }
    
    public void setCdInd(String cdInd) {
        this.cdInd = cdInd;
    }

    public String getCashPdrd() {
        return this.cashPdrd;
    }
    
    public void setCashPdrd(String cashPdrd) {
        this.cashPdrd = cashPdrd;
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

    public String getVeUser() {
        return this.veUser;
    }
    
    public void setVeUser(String veUser) {
        this.veUser = veUser;
    }

    public String getVeTml() {
        return this.veTml;
    }
    
    public void setVeTml(String veTml) {
        this.veTml = veTml;
    }

    public String getVeDate() {
        return this.veDate;
    }
    
    public void setVeDate(String veDate) {
        this.veDate = veDate;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof VoucherDataId) ) return false;
		 VoucherDataId castOther = ( VoucherDataId ) other; 
         
		 return ( (this.getVchType()==castOther.getVchType()) || ( this.getVchType()!=null && castOther.getVchType()!=null && this.getVchType().equals(castOther.getVchType()) ) )
 && ( (this.getVchNo()==castOther.getVchNo()) || ( this.getVchNo()!=null && castOther.getVchNo()!=null && this.getVchNo().equals(castOther.getVchNo()) ) )
 && ( (this.getVchMode()==castOther.getVchMode()) || ( this.getVchMode()!=null && castOther.getVchMode()!=null && this.getVchMode().equals(castOther.getVchMode()) ) )
 && ( (this.getTrnDate()==castOther.getTrnDate()) || ( this.getTrnDate()!=null && castOther.getTrnDate()!=null && this.getTrnDate().equals(castOther.getTrnDate()) ) )
 && ( (this.getNarration()==castOther.getNarration()) || ( this.getNarration()!=null && castOther.getNarration()!=null && this.getNarration().equals(castOther.getNarration()) ) )
 && ( (this.getModAcType()==castOther.getModAcType()) || ( this.getModAcType()!=null && castOther.getModAcType()!=null && this.getModAcType().equals(castOther.getModAcType()) ) )
 && ( (this.getModAcNo()==castOther.getModAcNo()) || ( this.getModAcNo()!=null && castOther.getModAcNo()!=null && this.getModAcNo().equals(castOther.getModAcNo()) ) )
 && ( (this.getTrnType()==castOther.getTrnType()) || ( this.getTrnType()!=null && castOther.getTrnType()!=null && this.getTrnType().equals(castOther.getTrnType()) ) )
 && ( (this.getGlSlType()==castOther.getGlSlType()) || ( this.getGlSlType()!=null && castOther.getGlSlType()!=null && this.getGlSlType().equals(castOther.getGlSlType()) ) )
 && ( (this.getGlSlCode()==castOther.getGlSlCode()) || ( this.getGlSlCode()!=null && castOther.getGlSlCode()!=null && this.getGlSlCode().equals(castOther.getGlSlCode()) ) )
 && ( (this.getChqNo()==castOther.getChqNo()) || ( this.getChqNo()!=null && castOther.getChqNo()!=null && this.getChqNo().equals(castOther.getChqNo()) ) )
 && ( (this.getChqDt()==castOther.getChqDt()) || ( this.getChqDt()!=null && castOther.getChqDt()!=null && this.getChqDt().equals(castOther.getChqDt()) ) )
 && ( (this.getTrnAmt()==castOther.getTrnAmt()) || ( this.getTrnAmt()!=null && castOther.getTrnAmt()!=null && this.getTrnAmt().equals(castOther.getTrnAmt()) ) )
 && ( (this.getCdInd()==castOther.getCdInd()) || ( this.getCdInd()!=null && castOther.getCdInd()!=null && this.getCdInd().equals(castOther.getCdInd()) ) )
 && ( (this.getCashPdrd()==castOther.getCashPdrd()) || ( this.getCashPdrd()!=null && castOther.getCashPdrd()!=null && this.getCashPdrd().equals(castOther.getCashPdrd()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) )
 && ( (this.getVeUser()==castOther.getVeUser()) || ( this.getVeUser()!=null && castOther.getVeUser()!=null && this.getVeUser().equals(castOther.getVeUser()) ) )
 && ( (this.getVeTml()==castOther.getVeTml()) || ( this.getVeTml()!=null && castOther.getVeTml()!=null && this.getVeTml().equals(castOther.getVeTml()) ) )
 && ( (this.getVeDate()==castOther.getVeDate()) || ( this.getVeDate()!=null && castOther.getVeDate()!=null && this.getVeDate().equals(castOther.getVeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getVchType() == null ? 0 : this.getVchType().hashCode() );
         result = 37 * result + ( getVchNo() == null ? 0 : this.getVchNo().hashCode() );
         result = 37 * result + ( getVchMode() == null ? 0 : this.getVchMode().hashCode() );
         result = 37 * result + ( getTrnDate() == null ? 0 : this.getTrnDate().hashCode() );
         result = 37 * result + ( getNarration() == null ? 0 : this.getNarration().hashCode() );
         result = 37 * result + ( getModAcType() == null ? 0 : this.getModAcType().hashCode() );
         result = 37 * result + ( getModAcNo() == null ? 0 : this.getModAcNo().hashCode() );
         result = 37 * result + ( getTrnType() == null ? 0 : this.getTrnType().hashCode() );
         result = 37 * result + ( getGlSlType() == null ? 0 : this.getGlSlType().hashCode() );
         result = 37 * result + ( getGlSlCode() == null ? 0 : this.getGlSlCode().hashCode() );
         result = 37 * result + ( getChqNo() == null ? 0 : this.getChqNo().hashCode() );
         result = 37 * result + ( getChqDt() == null ? 0 : this.getChqDt().hashCode() );
         result = 37 * result + ( getTrnAmt() == null ? 0 : this.getTrnAmt().hashCode() );
         result = 37 * result + ( getCdInd() == null ? 0 : this.getCdInd().hashCode() );
         result = 37 * result + ( getCashPdrd() == null ? 0 : this.getCashPdrd().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         result = 37 * result + ( getVeUser() == null ? 0 : this.getVeUser().hashCode() );
         result = 37 * result + ( getVeTml() == null ? 0 : this.getVeTml().hashCode() );
         result = 37 * result + ( getVeDate() == null ? 0 : this.getVeDate().hashCode() );
         return result;
   }   





}