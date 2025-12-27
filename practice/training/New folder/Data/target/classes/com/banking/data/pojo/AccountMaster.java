package com.banking.data.pojo;
// default package



/**
 * AccountMaster entity. @author MyEclipse Persistence Tools
 */

public class AccountMaster  implements java.io.Serializable {


    // Fields    

     private AccountMasterId id;
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
    public AccountMaster() {
    }

	/** minimal constructor */
    public AccountMaster(AccountMasterId id) {
        this.id = id;
    }
    
    /** full constructor */
    public AccountMaster(AccountMasterId id, Integer cid, Integer addrType, Integer noJtHldr, String chBkIssue, Integer lastTrSeq, String lastTrDate, Integer psBkSeq, Integer ledgerSeq, String intPblDate, String intrAcType, Integer intrAcNo, Integer nomNo, String acStatus, String freezeInd, String acOpendate, String acClosedate, Integer lastLine, String deTml, String deUser, String deDate, String veTml, String veUser, String veDate) {
        this.id = id;
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

    public AccountMasterId getId() {
        return this.id;
    }
    
    public void setId(AccountMasterId id) {
        this.id = id;
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
   








}