package com.banking.data.pojo;
// default package



/**
 * ShareDividendId entity. @author MyEclipse Persistence Tools
 */

public class ShareDividendId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private Integer acNo;
     private String divDt;
     private Float divAmt;
     private Float ddnAmt;
     private String cdInd;
     private String wrtPrtd;
     private Integer wrtNo;
     private String wrtDt;
     private Float wrtAmt;
     private String wrtCshd;
     private String divAcTy;
     private Integer divAcNo;
     private Integer divAcBr;
     private String payMode;
     private String cvTy;
     private String cvDt;
     private Integer cvNo;
     private String cvPaid;
     private String paydivDt;
     private String deTml;
     private String deUser;
     private String deDate;
     private String veTml;
     private String veUser;
     private String veDate;
     private Integer voucherNo;


    // Constructors

    /** default constructor */
    public ShareDividendId() {
    }

    
    /** full constructor */
    public ShareDividendId(String acType, Integer acNo, String divDt, Float divAmt, Float ddnAmt, String cdInd, String wrtPrtd, Integer wrtNo, String wrtDt, Float wrtAmt, String wrtCshd, String divAcTy, Integer divAcNo, Integer divAcBr, String payMode, String cvTy, String cvDt, Integer cvNo, String cvPaid, String paydivDt, String deTml, String deUser, String deDate, String veTml, String veUser, String veDate, Integer voucherNo) {
        this.acType = acType;
        this.acNo = acNo;
        this.divDt = divDt;
        this.divAmt = divAmt;
        this.ddnAmt = ddnAmt;
        this.cdInd = cdInd;
        this.wrtPrtd = wrtPrtd;
        this.wrtNo = wrtNo;
        this.wrtDt = wrtDt;
        this.wrtAmt = wrtAmt;
        this.wrtCshd = wrtCshd;
        this.divAcTy = divAcTy;
        this.divAcNo = divAcNo;
        this.divAcBr = divAcBr;
        this.payMode = payMode;
        this.cvTy = cvTy;
        this.cvDt = cvDt;
        this.cvNo = cvNo;
        this.cvPaid = cvPaid;
        this.paydivDt = paydivDt;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
        this.veTml = veTml;
        this.veUser = veUser;
        this.veDate = veDate;
        this.voucherNo = voucherNo;
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

    public String getDivDt() {
        return this.divDt;
    }
    
    public void setDivDt(String divDt) {
        this.divDt = divDt;
    }

    public Float getDivAmt() {
        return this.divAmt;
    }
    
    public void setDivAmt(Float divAmt) {
        this.divAmt = divAmt;
    }

    public Float getDdnAmt() {
        return this.ddnAmt;
    }
    
    public void setDdnAmt(Float ddnAmt) {
        this.ddnAmt = ddnAmt;
    }

    public String getCdInd() {
        return this.cdInd;
    }
    
    public void setCdInd(String cdInd) {
        this.cdInd = cdInd;
    }

    public String getWrtPrtd() {
        return this.wrtPrtd;
    }
    
    public void setWrtPrtd(String wrtPrtd) {
        this.wrtPrtd = wrtPrtd;
    }

    public Integer getWrtNo() {
        return this.wrtNo;
    }
    
    public void setWrtNo(Integer wrtNo) {
        this.wrtNo = wrtNo;
    }

    public String getWrtDt() {
        return this.wrtDt;
    }
    
    public void setWrtDt(String wrtDt) {
        this.wrtDt = wrtDt;
    }

    public Float getWrtAmt() {
        return this.wrtAmt;
    }
    
    public void setWrtAmt(Float wrtAmt) {
        this.wrtAmt = wrtAmt;
    }

    public String getWrtCshd() {
        return this.wrtCshd;
    }
    
    public void setWrtCshd(String wrtCshd) {
        this.wrtCshd = wrtCshd;
    }

    public String getDivAcTy() {
        return this.divAcTy;
    }
    
    public void setDivAcTy(String divAcTy) {
        this.divAcTy = divAcTy;
    }

    public Integer getDivAcNo() {
        return this.divAcNo;
    }
    
    public void setDivAcNo(Integer divAcNo) {
        this.divAcNo = divAcNo;
    }

    public Integer getDivAcBr() {
        return this.divAcBr;
    }
    
    public void setDivAcBr(Integer divAcBr) {
        this.divAcBr = divAcBr;
    }

    public String getPayMode() {
        return this.payMode;
    }
    
    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public String getCvTy() {
        return this.cvTy;
    }
    
    public void setCvTy(String cvTy) {
        this.cvTy = cvTy;
    }

    public String getCvDt() {
        return this.cvDt;
    }
    
    public void setCvDt(String cvDt) {
        this.cvDt = cvDt;
    }

    public Integer getCvNo() {
        return this.cvNo;
    }
    
    public void setCvNo(Integer cvNo) {
        this.cvNo = cvNo;
    }

    public String getCvPaid() {
        return this.cvPaid;
    }
    
    public void setCvPaid(String cvPaid) {
        this.cvPaid = cvPaid;
    }

    public String getPaydivDt() {
        return this.paydivDt;
    }
    
    public void setPaydivDt(String paydivDt) {
        this.paydivDt = paydivDt;
    }

    public String getDeTml() {
        return this.deTml;
    }
    
    public void setDeTml(String deTml) {
        this.deTml = deTml;
    }

    public String getDeUser() {
        return this.deUser;
    }
    
    public void setDeUser(String deUser) {
        this.deUser = deUser;
    }

    public String getDeDate() {
        return this.deDate;
    }
    
    public void setDeDate(String deDate) {
        this.deDate = deDate;
    }

    public String getVeTml() {
        return this.veTml;
    }
    
    public void setVeTml(String veTml) {
        this.veTml = veTml;
    }

    public String getVeUser() {
        return this.veUser;
    }
    
    public void setVeUser(String veUser) {
        this.veUser = veUser;
    }

    public String getVeDate() {
        return this.veDate;
    }
    
    public void setVeDate(String veDate) {
        this.veDate = veDate;
    }

    public Integer getVoucherNo() {
        return this.voucherNo;
    }
    
    public void setVoucherNo(Integer voucherNo) {
        this.voucherNo = voucherNo;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ShareDividendId) ) return false;
		 ShareDividendId castOther = ( ShareDividendId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getDivDt()==castOther.getDivDt()) || ( this.getDivDt()!=null && castOther.getDivDt()!=null && this.getDivDt().equals(castOther.getDivDt()) ) )
 && ( (this.getDivAmt()==castOther.getDivAmt()) || ( this.getDivAmt()!=null && castOther.getDivAmt()!=null && this.getDivAmt().equals(castOther.getDivAmt()) ) )
 && ( (this.getDdnAmt()==castOther.getDdnAmt()) || ( this.getDdnAmt()!=null && castOther.getDdnAmt()!=null && this.getDdnAmt().equals(castOther.getDdnAmt()) ) )
 && ( (this.getCdInd()==castOther.getCdInd()) || ( this.getCdInd()!=null && castOther.getCdInd()!=null && this.getCdInd().equals(castOther.getCdInd()) ) )
 && ( (this.getWrtPrtd()==castOther.getWrtPrtd()) || ( this.getWrtPrtd()!=null && castOther.getWrtPrtd()!=null && this.getWrtPrtd().equals(castOther.getWrtPrtd()) ) )
 && ( (this.getWrtNo()==castOther.getWrtNo()) || ( this.getWrtNo()!=null && castOther.getWrtNo()!=null && this.getWrtNo().equals(castOther.getWrtNo()) ) )
 && ( (this.getWrtDt()==castOther.getWrtDt()) || ( this.getWrtDt()!=null && castOther.getWrtDt()!=null && this.getWrtDt().equals(castOther.getWrtDt()) ) )
 && ( (this.getWrtAmt()==castOther.getWrtAmt()) || ( this.getWrtAmt()!=null && castOther.getWrtAmt()!=null && this.getWrtAmt().equals(castOther.getWrtAmt()) ) )
 && ( (this.getWrtCshd()==castOther.getWrtCshd()) || ( this.getWrtCshd()!=null && castOther.getWrtCshd()!=null && this.getWrtCshd().equals(castOther.getWrtCshd()) ) )
 && ( (this.getDivAcTy()==castOther.getDivAcTy()) || ( this.getDivAcTy()!=null && castOther.getDivAcTy()!=null && this.getDivAcTy().equals(castOther.getDivAcTy()) ) )
 && ( (this.getDivAcNo()==castOther.getDivAcNo()) || ( this.getDivAcNo()!=null && castOther.getDivAcNo()!=null && this.getDivAcNo().equals(castOther.getDivAcNo()) ) )
 && ( (this.getDivAcBr()==castOther.getDivAcBr()) || ( this.getDivAcBr()!=null && castOther.getDivAcBr()!=null && this.getDivAcBr().equals(castOther.getDivAcBr()) ) )
 && ( (this.getPayMode()==castOther.getPayMode()) || ( this.getPayMode()!=null && castOther.getPayMode()!=null && this.getPayMode().equals(castOther.getPayMode()) ) )
 && ( (this.getCvTy()==castOther.getCvTy()) || ( this.getCvTy()!=null && castOther.getCvTy()!=null && this.getCvTy().equals(castOther.getCvTy()) ) )
 && ( (this.getCvDt()==castOther.getCvDt()) || ( this.getCvDt()!=null && castOther.getCvDt()!=null && this.getCvDt().equals(castOther.getCvDt()) ) )
 && ( (this.getCvNo()==castOther.getCvNo()) || ( this.getCvNo()!=null && castOther.getCvNo()!=null && this.getCvNo().equals(castOther.getCvNo()) ) )
 && ( (this.getCvPaid()==castOther.getCvPaid()) || ( this.getCvPaid()!=null && castOther.getCvPaid()!=null && this.getCvPaid().equals(castOther.getCvPaid()) ) )
 && ( (this.getPaydivDt()==castOther.getPaydivDt()) || ( this.getPaydivDt()!=null && castOther.getPaydivDt()!=null && this.getPaydivDt().equals(castOther.getPaydivDt()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) )
 && ( (this.getVeTml()==castOther.getVeTml()) || ( this.getVeTml()!=null && castOther.getVeTml()!=null && this.getVeTml().equals(castOther.getVeTml()) ) )
 && ( (this.getVeUser()==castOther.getVeUser()) || ( this.getVeUser()!=null && castOther.getVeUser()!=null && this.getVeUser().equals(castOther.getVeUser()) ) )
 && ( (this.getVeDate()==castOther.getVeDate()) || ( this.getVeDate()!=null && castOther.getVeDate()!=null && this.getVeDate().equals(castOther.getVeDate()) ) )
 && ( (this.getVoucherNo()==castOther.getVoucherNo()) || ( this.getVoucherNo()!=null && castOther.getVoucherNo()!=null && this.getVoucherNo().equals(castOther.getVoucherNo()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getDivDt() == null ? 0 : this.getDivDt().hashCode() );
         result = 37 * result + ( getDivAmt() == null ? 0 : this.getDivAmt().hashCode() );
         result = 37 * result + ( getDdnAmt() == null ? 0 : this.getDdnAmt().hashCode() );
         result = 37 * result + ( getCdInd() == null ? 0 : this.getCdInd().hashCode() );
         result = 37 * result + ( getWrtPrtd() == null ? 0 : this.getWrtPrtd().hashCode() );
         result = 37 * result + ( getWrtNo() == null ? 0 : this.getWrtNo().hashCode() );
         result = 37 * result + ( getWrtDt() == null ? 0 : this.getWrtDt().hashCode() );
         result = 37 * result + ( getWrtAmt() == null ? 0 : this.getWrtAmt().hashCode() );
         result = 37 * result + ( getWrtCshd() == null ? 0 : this.getWrtCshd().hashCode() );
         result = 37 * result + ( getDivAcTy() == null ? 0 : this.getDivAcTy().hashCode() );
         result = 37 * result + ( getDivAcNo() == null ? 0 : this.getDivAcNo().hashCode() );
         result = 37 * result + ( getDivAcBr() == null ? 0 : this.getDivAcBr().hashCode() );
         result = 37 * result + ( getPayMode() == null ? 0 : this.getPayMode().hashCode() );
         result = 37 * result + ( getCvTy() == null ? 0 : this.getCvTy().hashCode() );
         result = 37 * result + ( getCvDt() == null ? 0 : this.getCvDt().hashCode() );
         result = 37 * result + ( getCvNo() == null ? 0 : this.getCvNo().hashCode() );
         result = 37 * result + ( getCvPaid() == null ? 0 : this.getCvPaid().hashCode() );
         result = 37 * result + ( getPaydivDt() == null ? 0 : this.getPaydivDt().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         result = 37 * result + ( getVeTml() == null ? 0 : this.getVeTml().hashCode() );
         result = 37 * result + ( getVeUser() == null ? 0 : this.getVeUser().hashCode() );
         result = 37 * result + ( getVeDate() == null ? 0 : this.getVeDate().hashCode() );
         result = 37 * result + ( getVoucherNo() == null ? 0 : this.getVoucherNo().hashCode() );
         return result;
   }   





}