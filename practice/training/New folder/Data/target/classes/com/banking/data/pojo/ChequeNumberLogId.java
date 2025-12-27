package com.banking.data.pojo;
// default package



/**
 * ChequeNumberLogId entity. @author MyEclipse Persistence Tools
 */

public class ChequeNumberLogId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private Integer acNo;
     private Integer bookNo;
     private Integer chqNo;
     private String postDated;
     private String expDate;
     private String stopPymnt;
     private String nextChqdt;
     private String chqIssDt;
     private String chqPayee;
     private Double chqAmt;
     private String stopUser;
     private String chqDel;
     private String chqCancel;
     private String altDeUser;
     private String altDeDate;
     private Integer tranKey;
     private String deTml;
     private String deUser;
     private String deDate;
     private String veTml;
     private String veUser;
     private String veDate;


    // Constructors

    /** default constructor */
    public ChequeNumberLogId() {
    }

    
    /** full constructor */
    public ChequeNumberLogId(String acType, Integer acNo, Integer bookNo, Integer chqNo, String postDated, String expDate, String stopPymnt, String nextChqdt, String chqIssDt, String chqPayee, Double chqAmt, String stopUser, String chqDel, String chqCancel, String altDeUser, String altDeDate, Integer tranKey, String deTml, String deUser, String deDate, String veTml, String veUser, String veDate) {
        this.acType = acType;
        this.acNo = acNo;
        this.bookNo = bookNo;
        this.chqNo = chqNo;
        this.postDated = postDated;
        this.expDate = expDate;
        this.stopPymnt = stopPymnt;
        this.nextChqdt = nextChqdt;
        this.chqIssDt = chqIssDt;
        this.chqPayee = chqPayee;
        this.chqAmt = chqAmt;
        this.stopUser = stopUser;
        this.chqDel = chqDel;
        this.chqCancel = chqCancel;
        this.altDeUser = altDeUser;
        this.altDeDate = altDeDate;
        this.tranKey = tranKey;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
        this.veTml = veTml;
        this.veUser = veUser;
        this.veDate = veDate;
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

    public Integer getBookNo() {
        return this.bookNo;
    }
    
    public void setBookNo(Integer bookNo) {
        this.bookNo = bookNo;
    }

    public Integer getChqNo() {
        return this.chqNo;
    }
    
    public void setChqNo(Integer chqNo) {
        this.chqNo = chqNo;
    }

    public String getPostDated() {
        return this.postDated;
    }
    
    public void setPostDated(String postDated) {
        this.postDated = postDated;
    }

    public String getExpDate() {
        return this.expDate;
    }
    
    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getStopPymnt() {
        return this.stopPymnt;
    }
    
    public void setStopPymnt(String stopPymnt) {
        this.stopPymnt = stopPymnt;
    }

    public String getNextChqdt() {
        return this.nextChqdt;
    }
    
    public void setNextChqdt(String nextChqdt) {
        this.nextChqdt = nextChqdt;
    }

    public String getChqIssDt() {
        return this.chqIssDt;
    }
    
    public void setChqIssDt(String chqIssDt) {
        this.chqIssDt = chqIssDt;
    }

    public String getChqPayee() {
        return this.chqPayee;
    }
    
    public void setChqPayee(String chqPayee) {
        this.chqPayee = chqPayee;
    }

    public Double getChqAmt() {
        return this.chqAmt;
    }
    
    public void setChqAmt(Double chqAmt) {
        this.chqAmt = chqAmt;
    }

    public String getStopUser() {
        return this.stopUser;
    }
    
    public void setStopUser(String stopUser) {
        this.stopUser = stopUser;
    }

    public String getChqDel() {
        return this.chqDel;
    }
    
    public void setChqDel(String chqDel) {
        this.chqDel = chqDel;
    }

    public String getChqCancel() {
        return this.chqCancel;
    }
    
    public void setChqCancel(String chqCancel) {
        this.chqCancel = chqCancel;
    }

    public String getAltDeUser() {
        return this.altDeUser;
    }
    
    public void setAltDeUser(String altDeUser) {
        this.altDeUser = altDeUser;
    }

    public String getAltDeDate() {
        return this.altDeDate;
    }
    
    public void setAltDeDate(String altDeDate) {
        this.altDeDate = altDeDate;
    }

    public Integer getTranKey() {
        return this.tranKey;
    }
    
    public void setTranKey(Integer tranKey) {
        this.tranKey = tranKey;
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
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ChequeNumberLogId) ) return false;
		 ChequeNumberLogId castOther = ( ChequeNumberLogId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getBookNo()==castOther.getBookNo()) || ( this.getBookNo()!=null && castOther.getBookNo()!=null && this.getBookNo().equals(castOther.getBookNo()) ) )
 && ( (this.getChqNo()==castOther.getChqNo()) || ( this.getChqNo()!=null && castOther.getChqNo()!=null && this.getChqNo().equals(castOther.getChqNo()) ) )
 && ( (this.getPostDated()==castOther.getPostDated()) || ( this.getPostDated()!=null && castOther.getPostDated()!=null && this.getPostDated().equals(castOther.getPostDated()) ) )
 && ( (this.getExpDate()==castOther.getExpDate()) || ( this.getExpDate()!=null && castOther.getExpDate()!=null && this.getExpDate().equals(castOther.getExpDate()) ) )
 && ( (this.getStopPymnt()==castOther.getStopPymnt()) || ( this.getStopPymnt()!=null && castOther.getStopPymnt()!=null && this.getStopPymnt().equals(castOther.getStopPymnt()) ) )
 && ( (this.getNextChqdt()==castOther.getNextChqdt()) || ( this.getNextChqdt()!=null && castOther.getNextChqdt()!=null && this.getNextChqdt().equals(castOther.getNextChqdt()) ) )
 && ( (this.getChqIssDt()==castOther.getChqIssDt()) || ( this.getChqIssDt()!=null && castOther.getChqIssDt()!=null && this.getChqIssDt().equals(castOther.getChqIssDt()) ) )
 && ( (this.getChqPayee()==castOther.getChqPayee()) || ( this.getChqPayee()!=null && castOther.getChqPayee()!=null && this.getChqPayee().equals(castOther.getChqPayee()) ) )
 && ( (this.getChqAmt()==castOther.getChqAmt()) || ( this.getChqAmt()!=null && castOther.getChqAmt()!=null && this.getChqAmt().equals(castOther.getChqAmt()) ) )
 && ( (this.getStopUser()==castOther.getStopUser()) || ( this.getStopUser()!=null && castOther.getStopUser()!=null && this.getStopUser().equals(castOther.getStopUser()) ) )
 && ( (this.getChqDel()==castOther.getChqDel()) || ( this.getChqDel()!=null && castOther.getChqDel()!=null && this.getChqDel().equals(castOther.getChqDel()) ) )
 && ( (this.getChqCancel()==castOther.getChqCancel()) || ( this.getChqCancel()!=null && castOther.getChqCancel()!=null && this.getChqCancel().equals(castOther.getChqCancel()) ) )
 && ( (this.getAltDeUser()==castOther.getAltDeUser()) || ( this.getAltDeUser()!=null && castOther.getAltDeUser()!=null && this.getAltDeUser().equals(castOther.getAltDeUser()) ) )
 && ( (this.getAltDeDate()==castOther.getAltDeDate()) || ( this.getAltDeDate()!=null && castOther.getAltDeDate()!=null && this.getAltDeDate().equals(castOther.getAltDeDate()) ) )
 && ( (this.getTranKey()==castOther.getTranKey()) || ( this.getTranKey()!=null && castOther.getTranKey()!=null && this.getTranKey().equals(castOther.getTranKey()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) )
 && ( (this.getVeTml()==castOther.getVeTml()) || ( this.getVeTml()!=null && castOther.getVeTml()!=null && this.getVeTml().equals(castOther.getVeTml()) ) )
 && ( (this.getVeUser()==castOther.getVeUser()) || ( this.getVeUser()!=null && castOther.getVeUser()!=null && this.getVeUser().equals(castOther.getVeUser()) ) )
 && ( (this.getVeDate()==castOther.getVeDate()) || ( this.getVeDate()!=null && castOther.getVeDate()!=null && this.getVeDate().equals(castOther.getVeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getBookNo() == null ? 0 : this.getBookNo().hashCode() );
         result = 37 * result + ( getChqNo() == null ? 0 : this.getChqNo().hashCode() );
         result = 37 * result + ( getPostDated() == null ? 0 : this.getPostDated().hashCode() );
         result = 37 * result + ( getExpDate() == null ? 0 : this.getExpDate().hashCode() );
         result = 37 * result + ( getStopPymnt() == null ? 0 : this.getStopPymnt().hashCode() );
         result = 37 * result + ( getNextChqdt() == null ? 0 : this.getNextChqdt().hashCode() );
         result = 37 * result + ( getChqIssDt() == null ? 0 : this.getChqIssDt().hashCode() );
         result = 37 * result + ( getChqPayee() == null ? 0 : this.getChqPayee().hashCode() );
         result = 37 * result + ( getChqAmt() == null ? 0 : this.getChqAmt().hashCode() );
         result = 37 * result + ( getStopUser() == null ? 0 : this.getStopUser().hashCode() );
         result = 37 * result + ( getChqDel() == null ? 0 : this.getChqDel().hashCode() );
         result = 37 * result + ( getChqCancel() == null ? 0 : this.getChqCancel().hashCode() );
         result = 37 * result + ( getAltDeUser() == null ? 0 : this.getAltDeUser().hashCode() );
         result = 37 * result + ( getAltDeDate() == null ? 0 : this.getAltDeDate().hashCode() );
         result = 37 * result + ( getTranKey() == null ? 0 : this.getTranKey().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         result = 37 * result + ( getVeTml() == null ? 0 : this.getVeTml().hashCode() );
         result = 37 * result + ( getVeUser() == null ? 0 : this.getVeUser().hashCode() );
         result = 37 * result + ( getVeDate() == null ? 0 : this.getVeDate().hashCode() );
         return result;
   }   





}