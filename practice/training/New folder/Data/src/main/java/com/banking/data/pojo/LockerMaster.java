package com.banking.data.pojo;
// default package



/**
 * LockerMaster entity. @author MyEclipse Persistence Tools
 */

public class LockerMaster  implements java.io.Serializable {


    // Fields    

     private LockerMasterId id;
     private Integer cid;
     private Integer addrType;
     private String lockerTy;
     private Integer lockerNo;
     private String lockerPw;
     private Integer noJtHldr;
     private String allotDt;
     private String matDate;
     private Integer reqMths;
     private Integer requiredDays;
     private String opInst;
     private Integer lstTrSeq;
     private String lstTrndt;
     private String closeDt;
     private String shType;
     private Integer shNo;
     private Integer noSecurities;
     private String autoExtn;
     private String intrAcType;
     private Integer intrAcNo;
     private Integer nomNo;
     private String deTml;
     private String deUser;
     private String deDate;
     private String veTml;
     private String veUser;
     private String veDate;
     private String freezeInd;


    // Constructors

    /** default constructor */
    public LockerMaster() {
    }

	/** minimal constructor */
    public LockerMaster(LockerMasterId id, String autoExtn, String freezeInd) {
        this.id = id;
        this.autoExtn = autoExtn;
        this.freezeInd = freezeInd;
    }
    
    /** full constructor */
    public LockerMaster(LockerMasterId id, Integer cid, Integer addrType, String lockerTy, Integer lockerNo, String lockerPw, Integer noJtHldr, String allotDt, String matDate, Integer reqMths, Integer requiredDays, String opInst, Integer lstTrSeq, String lstTrndt, String closeDt, String shType, Integer shNo, Integer noSecurities, String autoExtn, String intrAcType, Integer intrAcNo, Integer nomNo, String deTml, String deUser, String deDate, String veTml, String veUser, String veDate, String freezeInd) {
        this.id = id;
        this.cid = cid;
        this.addrType = addrType;
        this.lockerTy = lockerTy;
        this.lockerNo = lockerNo;
        this.lockerPw = lockerPw;
        this.noJtHldr = noJtHldr;
        this.allotDt = allotDt;
        this.matDate = matDate;
        this.reqMths = reqMths;
        this.requiredDays = requiredDays;
        this.opInst = opInst;
        this.lstTrSeq = lstTrSeq;
        this.lstTrndt = lstTrndt;
        this.closeDt = closeDt;
        this.shType = shType;
        this.shNo = shNo;
        this.noSecurities = noSecurities;
        this.autoExtn = autoExtn;
        this.intrAcType = intrAcType;
        this.intrAcNo = intrAcNo;
        this.nomNo = nomNo;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
        this.veTml = veTml;
        this.veUser = veUser;
        this.veDate = veDate;
        this.freezeInd = freezeInd;
    }

   
    // Property accessors

    public LockerMasterId getId() {
        return this.id;
    }
    
    public void setId(LockerMasterId id) {
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

    public String getLockerTy() {
        return this.lockerTy;
    }
    
    public void setLockerTy(String lockerTy) {
        this.lockerTy = lockerTy;
    }

    public Integer getLockerNo() {
        return this.lockerNo;
    }
    
    public void setLockerNo(Integer lockerNo) {
        this.lockerNo = lockerNo;
    }

    public String getLockerPw() {
        return this.lockerPw;
    }
    
    public void setLockerPw(String lockerPw) {
        this.lockerPw = lockerPw;
    }

    public Integer getNoJtHldr() {
        return this.noJtHldr;
    }
    
    public void setNoJtHldr(Integer noJtHldr) {
        this.noJtHldr = noJtHldr;
    }

    public String getAllotDt() {
        return this.allotDt;
    }
    
    public void setAllotDt(String allotDt) {
        this.allotDt = allotDt;
    }

    public String getMatDate() {
        return this.matDate;
    }
    
    public void setMatDate(String matDate) {
        this.matDate = matDate;
    }

    public Integer getReqMths() {
        return this.reqMths;
    }
    
    public void setReqMths(Integer reqMths) {
        this.reqMths = reqMths;
    }

    public Integer getRequiredDays() {
        return this.requiredDays;
    }
    
    public void setRequiredDays(Integer requiredDays) {
        this.requiredDays = requiredDays;
    }

    public String getOpInst() {
        return this.opInst;
    }
    
    public void setOpInst(String opInst) {
        this.opInst = opInst;
    }

    public Integer getLstTrSeq() {
        return this.lstTrSeq;
    }
    
    public void setLstTrSeq(Integer lstTrSeq) {
        this.lstTrSeq = lstTrSeq;
    }

    public String getLstTrndt() {
        return this.lstTrndt;
    }
    
    public void setLstTrndt(String lstTrndt) {
        this.lstTrndt = lstTrndt;
    }

    public String getCloseDt() {
        return this.closeDt;
    }
    
    public void setCloseDt(String closeDt) {
        this.closeDt = closeDt;
    }

    public String getShType() {
        return this.shType;
    }
    
    public void setShType(String shType) {
        this.shType = shType;
    }

    public Integer getShNo() {
        return this.shNo;
    }
    
    public void setShNo(Integer shNo) {
        this.shNo = shNo;
    }

    public Integer getNoSecurities() {
        return this.noSecurities;
    }
    
    public void setNoSecurities(Integer noSecurities) {
        this.noSecurities = noSecurities;
    }

    public String getAutoExtn() {
        return this.autoExtn;
    }
    
    public void setAutoExtn(String autoExtn) {
        this.autoExtn = autoExtn;
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

    public String getFreezeInd() {
        return this.freezeInd;
    }
    
    public void setFreezeInd(String freezeInd) {
        this.freezeInd = freezeInd;
    }
   








}