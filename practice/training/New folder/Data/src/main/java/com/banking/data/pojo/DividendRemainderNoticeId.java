package com.banking.data.pojo;
// default package



/**
 * DividendRemainderNoticeId entity. @author MyEclipse Persistence Tools
 */

public class DividendRemainderNoticeId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private Integer acNo;
     private String divDt;
     private Integer templNo;
     private Double divAmt;
     private Integer noticeNo;
     private String deUser;
     private String deTml;
     private String deDate;


    // Constructors

    /** default constructor */
    public DividendRemainderNoticeId() {
    }

    
    /** full constructor */
    public DividendRemainderNoticeId(String acType, Integer acNo, String divDt, Integer templNo, Double divAmt, Integer noticeNo, String deUser, String deTml, String deDate) {
        this.acType = acType;
        this.acNo = acNo;
        this.divDt = divDt;
        this.templNo = templNo;
        this.divAmt = divAmt;
        this.noticeNo = noticeNo;
        this.deUser = deUser;
        this.deTml = deTml;
        this.deDate = deDate;
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

    public Integer getTemplNo() {
        return this.templNo;
    }
    
    public void setTemplNo(Integer templNo) {
        this.templNo = templNo;
    }

    public Double getDivAmt() {
        return this.divAmt;
    }
    
    public void setDivAmt(Double divAmt) {
        this.divAmt = divAmt;
    }

    public Integer getNoticeNo() {
        return this.noticeNo;
    }
    
    public void setNoticeNo(Integer noticeNo) {
        this.noticeNo = noticeNo;
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
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof DividendRemainderNoticeId) ) return false;
		 DividendRemainderNoticeId castOther = ( DividendRemainderNoticeId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getDivDt()==castOther.getDivDt()) || ( this.getDivDt()!=null && castOther.getDivDt()!=null && this.getDivDt().equals(castOther.getDivDt()) ) )
 && ( (this.getTemplNo()==castOther.getTemplNo()) || ( this.getTemplNo()!=null && castOther.getTemplNo()!=null && this.getTemplNo().equals(castOther.getTemplNo()) ) )
 && ( (this.getDivAmt()==castOther.getDivAmt()) || ( this.getDivAmt()!=null && castOther.getDivAmt()!=null && this.getDivAmt().equals(castOther.getDivAmt()) ) )
 && ( (this.getNoticeNo()==castOther.getNoticeNo()) || ( this.getNoticeNo()!=null && castOther.getNoticeNo()!=null && this.getNoticeNo().equals(castOther.getNoticeNo()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getDivDt() == null ? 0 : this.getDivDt().hashCode() );
         result = 37 * result + ( getTemplNo() == null ? 0 : this.getTemplNo().hashCode() );
         result = 37 * result + ( getDivAmt() == null ? 0 : this.getDivAmt().hashCode() );
         result = 37 * result + ( getNoticeNo() == null ? 0 : this.getNoticeNo().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         return result;
   }   





}