package com.banking.data.pojo;
// default package



/**
 * PygmyMaster entity. @author MyEclipse Persistence Tools
 */

public class PygmyMaster  implements java.io.Serializable {


    // Fields    

     private PygmyMasterId id;
     private Integer cid;
     private Integer addrType;
     private Integer noJtHldr;
     private String agtType;
     private Integer agtNo;
     private Integer nomNo;
     private String status;
     private Integer lstTrnSeq;
     private String lstIntDt;
     private String lstWdlDt;
     private Integer lstWdlNo;
     private String lnAvld;
     private String lnAcType;
     private Integer lnAcNo;
     private Double wdlAmt;
     private String payMode;
     private String payAcType;
     private Integer payAcNo;
     private String openDate;
     private String closeDate;
     private String ldgrprtdt;
     private String deTml;
     private String deUser;
     private String deDate;
     private String veTml;
     private String veUser;
     private String veDate;
     private Integer refInd;


    // Constructors

    /** default constructor */
    public PygmyMaster() {
    }

	/** minimal constructor */
    public PygmyMaster(PygmyMasterId id) {
        this.id = id;
    }
    
    /** full constructor */
    public PygmyMaster(PygmyMasterId id, Integer cid, Integer addrType, Integer noJtHldr, String agtType, Integer agtNo, Integer nomNo, String status, Integer lstTrnSeq, String lstIntDt, String lstWdlDt, Integer lstWdlNo, String lnAvld, String lnAcType, Integer lnAcNo, Double wdlAmt, String payMode, String payAcType, Integer payAcNo, String openDate, String closeDate, String ldgrprtdt, String deTml, String deUser, String deDate, String veTml, String veUser, String veDate, Integer refInd) {
        this.id = id;
        this.cid = cid;
        this.addrType = addrType;
        this.noJtHldr = noJtHldr;
        this.agtType = agtType;
        this.agtNo = agtNo;
        this.nomNo = nomNo;
        this.status = status;
        this.lstTrnSeq = lstTrnSeq;
        this.lstIntDt = lstIntDt;
        this.lstWdlDt = lstWdlDt;
        this.lstWdlNo = lstWdlNo;
        this.lnAvld = lnAvld;
        this.lnAcType = lnAcType;
        this.lnAcNo = lnAcNo;
        this.wdlAmt = wdlAmt;
        this.payMode = payMode;
        this.payAcType = payAcType;
        this.payAcNo = payAcNo;
        this.openDate = openDate;
        this.closeDate = closeDate;
        this.ldgrprtdt = ldgrprtdt;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
        this.veTml = veTml;
        this.veUser = veUser;
        this.veDate = veDate;
        this.refInd = refInd;
    }

   
    // Property accessors

    public PygmyMasterId getId() {
        return this.id;
    }
    
    public void setId(PygmyMasterId id) {
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

    public String getAgtType() {
        return this.agtType;
    }
    
    public void setAgtType(String agtType) {
        this.agtType = agtType;
    }

    public Integer getAgtNo() {
        return this.agtNo;
    }
    
    public void setAgtNo(Integer agtNo) {
        this.agtNo = agtNo;
    }

    public Integer getNomNo() {
        return this.nomNo;
    }
    
    public void setNomNo(Integer nomNo) {
        this.nomNo = nomNo;
    }

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getLstTrnSeq() {
        return this.lstTrnSeq;
    }
    
    public void setLstTrnSeq(Integer lstTrnSeq) {
        this.lstTrnSeq = lstTrnSeq;
    }

    public String getLstIntDt() {
        return this.lstIntDt;
    }
    
    public void setLstIntDt(String lstIntDt) {
        this.lstIntDt = lstIntDt;
    }

    public String getLstWdlDt() {
        return this.lstWdlDt;
    }
    
    public void setLstWdlDt(String lstWdlDt) {
        this.lstWdlDt = lstWdlDt;
    }

    public Integer getLstWdlNo() {
        return this.lstWdlNo;
    }
    
    public void setLstWdlNo(Integer lstWdlNo) {
        this.lstWdlNo = lstWdlNo;
    }

    public String getLnAvld() {
        return this.lnAvld;
    }
    
    public void setLnAvld(String lnAvld) {
        this.lnAvld = lnAvld;
    }

    public String getLnAcType() {
        return this.lnAcType;
    }
    
    public void setLnAcType(String lnAcType) {
        this.lnAcType = lnAcType;
    }

    public Integer getLnAcNo() {
        return this.lnAcNo;
    }
    
    public void setLnAcNo(Integer lnAcNo) {
        this.lnAcNo = lnAcNo;
    }

    public Double getWdlAmt() {
        return this.wdlAmt;
    }
    
    public void setWdlAmt(Double wdlAmt) {
        this.wdlAmt = wdlAmt;
    }

    public String getPayMode() {
        return this.payMode;
    }
    
    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public String getPayAcType() {
        return this.payAcType;
    }
    
    public void setPayAcType(String payAcType) {
        this.payAcType = payAcType;
    }

    public Integer getPayAcNo() {
        return this.payAcNo;
    }
    
    public void setPayAcNo(Integer payAcNo) {
        this.payAcNo = payAcNo;
    }

    public String getOpenDate() {
        return this.openDate;
    }
    
    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public String getCloseDate() {
        return this.closeDate;
    }
    
    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public String getLdgrprtdt() {
        return this.ldgrprtdt;
    }
    
    public void setLdgrprtdt(String ldgrprtdt) {
        this.ldgrprtdt = ldgrprtdt;
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

    public Integer getRefInd() {
        return this.refInd;
    }
    
    public void setRefInd(Integer refInd) {
        this.refInd = refInd;
    }
   








}