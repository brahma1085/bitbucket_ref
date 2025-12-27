package com.banking.data.pojo;
// default package



/**
 * AccountMasterLogId entity. @author MyEclipse Persistence Tools
 */

public class AccountMasterLogId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private Integer acNo;
     private Integer cid;
     private Integer addrType;
     private Integer noJtHldr;
     private String chBkIssue;
     private Integer lastTrSeq;
     private String lastTrDate;
     private Integer psBkSeq;
     private Integer ledgerSeq;
     private String intPblDate;
     private String intrAcType;
     private Integer intrAcNo;
     private Integer nomNo;
     private String acStatus;
     private String freezeInd;
     private String acOpendate;
     private String acClosedate;
     private Integer lastLine;
     private String deTml;
     private String deUser;
     private String deDate;
     private String veTml;
     private String veUser;
     private String veDate;


    // Constructors

    /** default constructor */
    public AccountMasterLogId() {
    }

	/** minimal constructor */
    public AccountMasterLogId(String acType, Integer acNo) {
        this.acType = acType;
        this.acNo = acNo;
    }
    
    /** full constructor */
    public AccountMasterLogId(String acType, Integer acNo, Integer cid, Integer addrType, Integer noJtHldr, String chBkIssue, Integer lastTrSeq, String lastTrDate, Integer psBkSeq, Integer ledgerSeq, String intPblDate, String intrAcType, Integer intrAcNo, Integer nomNo, String acStatus, String freezeInd, String acOpendate, String acClosedate, Integer lastLine, String deTml, String deUser, String deDate, String veTml, String veUser, String veDate) {
        this.acType = acType;
        this.acNo = acNo;
        this.cid = cid;
        this.addrType = addrType;
        this.noJtHldr = noJtHldr;
        this.chBkIssue = chBkIssue;
        this.lastTrSeq = lastTrSeq;
        this.lastTrDate = lastTrDate;
        this.psBkSeq = psBkSeq;
        this.ledgerSeq = ledgerSeq;
        this.intPblDate = intPblDate;
        this.intrAcType = intrAcType;
        this.intrAcNo = intrAcNo;
        this.nomNo = nomNo;
        this.acStatus = acStatus;
        this.freezeInd = freezeInd;
        this.acOpendate = acOpendate;
        this.acClosedate = acClosedate;
        this.lastLine = lastLine;
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

    public Integer getNoJtHldr() {
        return this.noJtHldr;
    }
    
    public void setNoJtHldr(Integer noJtHldr) {
        this.noJtHldr = noJtHldr;
    }

    public String getChBkIssue() {
        return this.chBkIssue;
    }
    
    public void setChBkIssue(String chBkIssue) {
        this.chBkIssue = chBkIssue;
    }

    public Integer getLastTrSeq() {
        return this.lastTrSeq;
    }
    
    public void setLastTrSeq(Integer lastTrSeq) {
        this.lastTrSeq = lastTrSeq;
    }

    public String getLastTrDate() {
        return this.lastTrDate;
    }
    
    public void setLastTrDate(String lastTrDate) {
        this.lastTrDate = lastTrDate;
    }

    public Integer getPsBkSeq() {
        return this.psBkSeq;
    }
    
    public void setPsBkSeq(Integer psBkSeq) {
        this.psBkSeq = psBkSeq;
    }

    public Integer getLedgerSeq() {
        return this.ledgerSeq;
    }
    
    public void setLedgerSeq(Integer ledgerSeq) {
        this.ledgerSeq = ledgerSeq;
    }

    public String getIntPblDate() {
        return this.intPblDate;
    }
    
    public void setIntPblDate(String intPblDate) {
        this.intPblDate = intPblDate;
    }

    public String getIntrAcType() {
        return this.intrAcType;
    }
    
    public void setIntrAcType(String intrAcType) {
        this.intrAcType = intrAcType;
    }

    public Integer getIntrAcNo() {
        return this.intrAcNo;
    }
    
    public void setIntrAcNo(Integer intrAcNo) {
        this.intrAcNo = intrAcNo;
    }

    public Integer getNomNo() {
        return this.nomNo;
    }
    
    public void setNomNo(Integer nomNo) {
        this.nomNo = nomNo;
    }

    public String getAcStatus() {
        return this.acStatus;
    }
    
    public void setAcStatus(String acStatus) {
        this.acStatus = acStatus;
    }

    public String getFreezeInd() {
        return this.freezeInd;
    }
    
    public void setFreezeInd(String freezeInd) {
        this.freezeInd = freezeInd;
    }

    public String getAcOpendate() {
        return this.acOpendate;
    }
    
    public void setAcOpendate(String acOpendate) {
        this.acOpendate = acOpendate;
    }

    public String getAcClosedate() {
        return this.acClosedate;
    }
    
    public void setAcClosedate(String acClosedate) {
        this.acClosedate = acClosedate;
    }

    public Integer getLastLine() {
        return this.lastLine;
    }
    
    public void setLastLine(Integer lastLine) {
        this.lastLine = lastLine;
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
		 if ( !(other instanceof AccountMasterLogId) ) return false;
		 AccountMasterLogId castOther = ( AccountMasterLogId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getCid()==castOther.getCid()) || ( this.getCid()!=null && castOther.getCid()!=null && this.getCid().equals(castOther.getCid()) ) )
 && ( (this.getAddrType()==castOther.getAddrType()) || ( this.getAddrType()!=null && castOther.getAddrType()!=null && this.getAddrType().equals(castOther.getAddrType()) ) )
 && ( (this.getNoJtHldr()==castOther.getNoJtHldr()) || ( this.getNoJtHldr()!=null && castOther.getNoJtHldr()!=null && this.getNoJtHldr().equals(castOther.getNoJtHldr()) ) )
 && ( (this.getChBkIssue()==castOther.getChBkIssue()) || ( this.getChBkIssue()!=null && castOther.getChBkIssue()!=null && this.getChBkIssue().equals(castOther.getChBkIssue()) ) )
 && ( (this.getLastTrSeq()==castOther.getLastTrSeq()) || ( this.getLastTrSeq()!=null && castOther.getLastTrSeq()!=null && this.getLastTrSeq().equals(castOther.getLastTrSeq()) ) )
 && ( (this.getLastTrDate()==castOther.getLastTrDate()) || ( this.getLastTrDate()!=null && castOther.getLastTrDate()!=null && this.getLastTrDate().equals(castOther.getLastTrDate()) ) )
 && ( (this.getPsBkSeq()==castOther.getPsBkSeq()) || ( this.getPsBkSeq()!=null && castOther.getPsBkSeq()!=null && this.getPsBkSeq().equals(castOther.getPsBkSeq()) ) )
 && ( (this.getLedgerSeq()==castOther.getLedgerSeq()) || ( this.getLedgerSeq()!=null && castOther.getLedgerSeq()!=null && this.getLedgerSeq().equals(castOther.getLedgerSeq()) ) )
 && ( (this.getIntPblDate()==castOther.getIntPblDate()) || ( this.getIntPblDate()!=null && castOther.getIntPblDate()!=null && this.getIntPblDate().equals(castOther.getIntPblDate()) ) )
 && ( (this.getIntrAcType()==castOther.getIntrAcType()) || ( this.getIntrAcType()!=null && castOther.getIntrAcType()!=null && this.getIntrAcType().equals(castOther.getIntrAcType()) ) )
 && ( (this.getIntrAcNo()==castOther.getIntrAcNo()) || ( this.getIntrAcNo()!=null && castOther.getIntrAcNo()!=null && this.getIntrAcNo().equals(castOther.getIntrAcNo()) ) )
 && ( (this.getNomNo()==castOther.getNomNo()) || ( this.getNomNo()!=null && castOther.getNomNo()!=null && this.getNomNo().equals(castOther.getNomNo()) ) )
 && ( (this.getAcStatus()==castOther.getAcStatus()) || ( this.getAcStatus()!=null && castOther.getAcStatus()!=null && this.getAcStatus().equals(castOther.getAcStatus()) ) )
 && ( (this.getFreezeInd()==castOther.getFreezeInd()) || ( this.getFreezeInd()!=null && castOther.getFreezeInd()!=null && this.getFreezeInd().equals(castOther.getFreezeInd()) ) )
 && ( (this.getAcOpendate()==castOther.getAcOpendate()) || ( this.getAcOpendate()!=null && castOther.getAcOpendate()!=null && this.getAcOpendate().equals(castOther.getAcOpendate()) ) )
 && ( (this.getAcClosedate()==castOther.getAcClosedate()) || ( this.getAcClosedate()!=null && castOther.getAcClosedate()!=null && this.getAcClosedate().equals(castOther.getAcClosedate()) ) )
 && ( (this.getLastLine()==castOther.getLastLine()) || ( this.getLastLine()!=null && castOther.getLastLine()!=null && this.getLastLine().equals(castOther.getLastLine()) ) )
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
         result = 37 * result + ( getCid() == null ? 0 : this.getCid().hashCode() );
         result = 37 * result + ( getAddrType() == null ? 0 : this.getAddrType().hashCode() );
         result = 37 * result + ( getNoJtHldr() == null ? 0 : this.getNoJtHldr().hashCode() );
         result = 37 * result + ( getChBkIssue() == null ? 0 : this.getChBkIssue().hashCode() );
         result = 37 * result + ( getLastTrSeq() == null ? 0 : this.getLastTrSeq().hashCode() );
         result = 37 * result + ( getLastTrDate() == null ? 0 : this.getLastTrDate().hashCode() );
         result = 37 * result + ( getPsBkSeq() == null ? 0 : this.getPsBkSeq().hashCode() );
         result = 37 * result + ( getLedgerSeq() == null ? 0 : this.getLedgerSeq().hashCode() );
         result = 37 * result + ( getIntPblDate() == null ? 0 : this.getIntPblDate().hashCode() );
         result = 37 * result + ( getIntrAcType() == null ? 0 : this.getIntrAcType().hashCode() );
         result = 37 * result + ( getIntrAcNo() == null ? 0 : this.getIntrAcNo().hashCode() );
         result = 37 * result + ( getNomNo() == null ? 0 : this.getNomNo().hashCode() );
         result = 37 * result + ( getAcStatus() == null ? 0 : this.getAcStatus().hashCode() );
         result = 37 * result + ( getFreezeInd() == null ? 0 : this.getFreezeInd().hashCode() );
         result = 37 * result + ( getAcOpendate() == null ? 0 : this.getAcOpendate().hashCode() );
         result = 37 * result + ( getAcClosedate() == null ? 0 : this.getAcClosedate().hashCode() );
         result = 37 * result + ( getLastLine() == null ? 0 : this.getLastLine().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         result = 37 * result + ( getVeTml() == null ? 0 : this.getVeTml().hashCode() );
         result = 37 * result + ( getVeUser() == null ? 0 : this.getVeUser().hashCode() );
         result = 37 * result + ( getVeDate() == null ? 0 : this.getVeDate().hashCode() );
         return result;
   }   





}