package com.banking.data.pojo;
// default package



/**
 * ShareCodeId entity. @author MyEclipse Persistence Tools
 */

public class ShareCodeId  implements java.io.Serializable {


    // Fields    

     private Integer shLfNo;
     private String shInd;
     private String shareStat;
     private Integer shDistFr;
     private Integer shDistTo;
     private Integer shCertNo;
     private String shCertDt;
     private Integer certPrtd;


    // Constructors

    /** default constructor */
    public ShareCodeId() {
    }

    
    /** full constructor */
    public ShareCodeId(Integer shLfNo, String shInd, String shareStat, Integer shDistFr, Integer shDistTo, Integer shCertNo, String shCertDt, Integer certPrtd) {
        this.shLfNo = shLfNo;
        this.shInd = shInd;
        this.shareStat = shareStat;
        this.shDistFr = shDistFr;
        this.shDistTo = shDistTo;
        this.shCertNo = shCertNo;
        this.shCertDt = shCertDt;
        this.certPrtd = certPrtd;
    }

   
    // Property accessors

    public Integer getShLfNo() {
        return this.shLfNo;
    }
    
    public void setShLfNo(Integer shLfNo) {
        this.shLfNo = shLfNo;
    }

    public String getShInd() {
        return this.shInd;
    }
    
    public void setShInd(String shInd) {
        this.shInd = shInd;
    }

    public String getShareStat() {
        return this.shareStat;
    }
    
    public void setShareStat(String shareStat) {
        this.shareStat = shareStat;
    }

    public Integer getShDistFr() {
        return this.shDistFr;
    }
    
    public void setShDistFr(Integer shDistFr) {
        this.shDistFr = shDistFr;
    }

    public Integer getShDistTo() {
        return this.shDistTo;
    }
    
    public void setShDistTo(Integer shDistTo) {
        this.shDistTo = shDistTo;
    }

    public Integer getShCertNo() {
        return this.shCertNo;
    }
    
    public void setShCertNo(Integer shCertNo) {
        this.shCertNo = shCertNo;
    }

    public String getShCertDt() {
        return this.shCertDt;
    }
    
    public void setShCertDt(String shCertDt) {
        this.shCertDt = shCertDt;
    }

    public Integer getCertPrtd() {
        return this.certPrtd;
    }
    
    public void setCertPrtd(Integer certPrtd) {
        this.certPrtd = certPrtd;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ShareCodeId) ) return false;
		 ShareCodeId castOther = ( ShareCodeId ) other; 
         
		 return ( (this.getShLfNo()==castOther.getShLfNo()) || ( this.getShLfNo()!=null && castOther.getShLfNo()!=null && this.getShLfNo().equals(castOther.getShLfNo()) ) )
 && ( (this.getShInd()==castOther.getShInd()) || ( this.getShInd()!=null && castOther.getShInd()!=null && this.getShInd().equals(castOther.getShInd()) ) )
 && ( (this.getShareStat()==castOther.getShareStat()) || ( this.getShareStat()!=null && castOther.getShareStat()!=null && this.getShareStat().equals(castOther.getShareStat()) ) )
 && ( (this.getShDistFr()==castOther.getShDistFr()) || ( this.getShDistFr()!=null && castOther.getShDistFr()!=null && this.getShDistFr().equals(castOther.getShDistFr()) ) )
 && ( (this.getShDistTo()==castOther.getShDistTo()) || ( this.getShDistTo()!=null && castOther.getShDistTo()!=null && this.getShDistTo().equals(castOther.getShDistTo()) ) )
 && ( (this.getShCertNo()==castOther.getShCertNo()) || ( this.getShCertNo()!=null && castOther.getShCertNo()!=null && this.getShCertNo().equals(castOther.getShCertNo()) ) )
 && ( (this.getShCertDt()==castOther.getShCertDt()) || ( this.getShCertDt()!=null && castOther.getShCertDt()!=null && this.getShCertDt().equals(castOther.getShCertDt()) ) )
 && ( (this.getCertPrtd()==castOther.getCertPrtd()) || ( this.getCertPrtd()!=null && castOther.getCertPrtd()!=null && this.getCertPrtd().equals(castOther.getCertPrtd()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getShLfNo() == null ? 0 : this.getShLfNo().hashCode() );
         result = 37 * result + ( getShInd() == null ? 0 : this.getShInd().hashCode() );
         result = 37 * result + ( getShareStat() == null ? 0 : this.getShareStat().hashCode() );
         result = 37 * result + ( getShDistFr() == null ? 0 : this.getShDistFr().hashCode() );
         result = 37 * result + ( getShDistTo() == null ? 0 : this.getShDistTo().hashCode() );
         result = 37 * result + ( getShCertNo() == null ? 0 : this.getShCertNo().hashCode() );
         result = 37 * result + ( getShCertDt() == null ? 0 : this.getShCertDt().hashCode() );
         result = 37 * result + ( getCertPrtd() == null ? 0 : this.getCertPrtd().hashCode() );
         return result;
   }   





}