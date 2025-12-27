package com.banking.data.pojo;
// default package



/**
 * ChequeLogId entity. @author MyEclipse Persistence Tools
 */

public class ChequeLogId  implements java.io.Serializable {


    // Fields    

     private String clgDate;
     private Integer clgNo;
     private Integer sendTo;
     private Integer ctrlNo;
     private String trnType;
     private String clgType;
     private Integer ackNo;
     private Integer docSou;
     private Integer docDest;
     private String docBs;
     private Integer noDocs;
     private Double docTot;
     private String multCr;
     private String compName;
     private String chqddpo;
     private Integer qdpNo;
     private String qdpDt;
     private String retNorm;
     private Integer prevCtrlNo;
     private String trfType;
     private String crAcType;
     private Integer crAcNo;
     private Float poComm;
     private String drAcType;
     private Integer drAcNo;
     private String payeeName;
     private String bankCd;
     private String brName;
     private Double trnAmt;
     private String toBounce;
     private String despInd;
     private String postInd;
     private String discInd;
     private Double discAmt;
     private Double discChg;
     private String expClgdt;
     private Integer expClgno;
     private String letSent;
     private Double mchgAmt;
     private Double fineAmt;
     private String loanAcType;
     private Integer loanAcNo;
     private String deTml;
     private String deUser;
     private String deDtTime;
     private String veTml;
     private String veUser;
     private String veDtTime;


    // Constructors

    /** default constructor */
    public ChequeLogId() {
    }

	/** minimal constructor */
    public ChequeLogId(Integer ctrlNo) {
        this.ctrlNo = ctrlNo;
    }
    
    /** full constructor */
    public ChequeLogId(String clgDate, Integer clgNo, Integer sendTo, Integer ctrlNo, String trnType, String clgType, Integer ackNo, Integer docSou, Integer docDest, String docBs, Integer noDocs, Double docTot, String multCr, String compName, String chqddpo, Integer qdpNo, String qdpDt, String retNorm, Integer prevCtrlNo, String trfType, String crAcType, Integer crAcNo, Float poComm, String drAcType, Integer drAcNo, String payeeName, String bankCd, String brName, Double trnAmt, String toBounce, String despInd, String postInd, String discInd, Double discAmt, Double discChg, String expClgdt, Integer expClgno, String letSent, Double mchgAmt, Double fineAmt, String loanAcType, Integer loanAcNo, String deTml, String deUser, String deDtTime, String veTml, String veUser, String veDtTime) {
        this.clgDate = clgDate;
        this.clgNo = clgNo;
        this.sendTo = sendTo;
        this.ctrlNo = ctrlNo;
        this.trnType = trnType;
        this.clgType = clgType;
        this.ackNo = ackNo;
        this.docSou = docSou;
        this.docDest = docDest;
        this.docBs = docBs;
        this.noDocs = noDocs;
        this.docTot = docTot;
        this.multCr = multCr;
        this.compName = compName;
        this.chqddpo = chqddpo;
        this.qdpNo = qdpNo;
        this.qdpDt = qdpDt;
        this.retNorm = retNorm;
        this.prevCtrlNo = prevCtrlNo;
        this.trfType = trfType;
        this.crAcType = crAcType;
        this.crAcNo = crAcNo;
        this.poComm = poComm;
        this.drAcType = drAcType;
        this.drAcNo = drAcNo;
        this.payeeName = payeeName;
        this.bankCd = bankCd;
        this.brName = brName;
        this.trnAmt = trnAmt;
        this.toBounce = toBounce;
        this.despInd = despInd;
        this.postInd = postInd;
        this.discInd = discInd;
        this.discAmt = discAmt;
        this.discChg = discChg;
        this.expClgdt = expClgdt;
        this.expClgno = expClgno;
        this.letSent = letSent;
        this.mchgAmt = mchgAmt;
        this.fineAmt = fineAmt;
        this.loanAcType = loanAcType;
        this.loanAcNo = loanAcNo;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDtTime = deDtTime;
        this.veTml = veTml;
        this.veUser = veUser;
        this.veDtTime = veDtTime;
    }

   
    // Property accessors

    public String getClgDate() {
        return this.clgDate;
    }
    
    public void setClgDate(String clgDate) {
        this.clgDate = clgDate;
    }

    public Integer getClgNo() {
        return this.clgNo;
    }
    
    public void setClgNo(Integer clgNo) {
        this.clgNo = clgNo;
    }

    public Integer getSendTo() {
        return this.sendTo;
    }
    
    public void setSendTo(Integer sendTo) {
        this.sendTo = sendTo;
    }

    public Integer getCtrlNo() {
        return this.ctrlNo;
    }
    
    public void setCtrlNo(Integer ctrlNo) {
        this.ctrlNo = ctrlNo;
    }

    public String getTrnType() {
        return this.trnType;
    }
    
    public void setTrnType(String trnType) {
        this.trnType = trnType;
    }

    public String getClgType() {
        return this.clgType;
    }
    
    public void setClgType(String clgType) {
        this.clgType = clgType;
    }

    public Integer getAckNo() {
        return this.ackNo;
    }
    
    public void setAckNo(Integer ackNo) {
        this.ackNo = ackNo;
    }

    public Integer getDocSou() {
        return this.docSou;
    }
    
    public void setDocSou(Integer docSou) {
        this.docSou = docSou;
    }

    public Integer getDocDest() {
        return this.docDest;
    }
    
    public void setDocDest(Integer docDest) {
        this.docDest = docDest;
    }

    public String getDocBs() {
        return this.docBs;
    }
    
    public void setDocBs(String docBs) {
        this.docBs = docBs;
    }

    public Integer getNoDocs() {
        return this.noDocs;
    }
    
    public void setNoDocs(Integer noDocs) {
        this.noDocs = noDocs;
    }

    public Double getDocTot() {
        return this.docTot;
    }
    
    public void setDocTot(Double docTot) {
        this.docTot = docTot;
    }

    public String getMultCr() {
        return this.multCr;
    }
    
    public void setMultCr(String multCr) {
        this.multCr = multCr;
    }

    public String getCompName() {
        return this.compName;
    }
    
    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getChqddpo() {
        return this.chqddpo;
    }
    
    public void setChqddpo(String chqddpo) {
        this.chqddpo = chqddpo;
    }

    public Integer getQdpNo() {
        return this.qdpNo;
    }
    
    public void setQdpNo(Integer qdpNo) {
        this.qdpNo = qdpNo;
    }

    public String getQdpDt() {
        return this.qdpDt;
    }
    
    public void setQdpDt(String qdpDt) {
        this.qdpDt = qdpDt;
    }

    public String getRetNorm() {
        return this.retNorm;
    }
    
    public void setRetNorm(String retNorm) {
        this.retNorm = retNorm;
    }

    public Integer getPrevCtrlNo() {
        return this.prevCtrlNo;
    }
    
    public void setPrevCtrlNo(Integer prevCtrlNo) {
        this.prevCtrlNo = prevCtrlNo;
    }

    public String getTrfType() {
        return this.trfType;
    }
    
    public void setTrfType(String trfType) {
        this.trfType = trfType;
    }

    public String getCrAcType() {
        return this.crAcType;
    }
    
    public void setCrAcType(String crAcType) {
        this.crAcType = crAcType;
    }

    public Integer getCrAcNo() {
        return this.crAcNo;
    }
    
    public void setCrAcNo(Integer crAcNo) {
        this.crAcNo = crAcNo;
    }

    public Float getPoComm() {
        return this.poComm;
    }
    
    public void setPoComm(Float poComm) {
        this.poComm = poComm;
    }

    public String getDrAcType() {
        return this.drAcType;
    }
    
    public void setDrAcType(String drAcType) {
        this.drAcType = drAcType;
    }

    public Integer getDrAcNo() {
        return this.drAcNo;
    }
    
    public void setDrAcNo(Integer drAcNo) {
        this.drAcNo = drAcNo;
    }

    public String getPayeeName() {
        return this.payeeName;
    }
    
    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getBankCd() {
        return this.bankCd;
    }
    
    public void setBankCd(String bankCd) {
        this.bankCd = bankCd;
    }

    public String getBrName() {
        return this.brName;
    }
    
    public void setBrName(String brName) {
        this.brName = brName;
    }

    public Double getTrnAmt() {
        return this.trnAmt;
    }
    
    public void setTrnAmt(Double trnAmt) {
        this.trnAmt = trnAmt;
    }

    public String getToBounce() {
        return this.toBounce;
    }
    
    public void setToBounce(String toBounce) {
        this.toBounce = toBounce;
    }

    public String getDespInd() {
        return this.despInd;
    }
    
    public void setDespInd(String despInd) {
        this.despInd = despInd;
    }

    public String getPostInd() {
        return this.postInd;
    }
    
    public void setPostInd(String postInd) {
        this.postInd = postInd;
    }

    public String getDiscInd() {
        return this.discInd;
    }
    
    public void setDiscInd(String discInd) {
        this.discInd = discInd;
    }

    public Double getDiscAmt() {
        return this.discAmt;
    }
    
    public void setDiscAmt(Double discAmt) {
        this.discAmt = discAmt;
    }

    public Double getDiscChg() {
        return this.discChg;
    }
    
    public void setDiscChg(Double discChg) {
        this.discChg = discChg;
    }

    public String getExpClgdt() {
        return this.expClgdt;
    }
    
    public void setExpClgdt(String expClgdt) {
        this.expClgdt = expClgdt;
    }

    public Integer getExpClgno() {
        return this.expClgno;
    }
    
    public void setExpClgno(Integer expClgno) {
        this.expClgno = expClgno;
    }

    public String getLetSent() {
        return this.letSent;
    }
    
    public void setLetSent(String letSent) {
        this.letSent = letSent;
    }

    public Double getMchgAmt() {
        return this.mchgAmt;
    }
    
    public void setMchgAmt(Double mchgAmt) {
        this.mchgAmt = mchgAmt;
    }

    public Double getFineAmt() {
        return this.fineAmt;
    }
    
    public void setFineAmt(Double fineAmt) {
        this.fineAmt = fineAmt;
    }

    public String getLoanAcType() {
        return this.loanAcType;
    }
    
    public void setLoanAcType(String loanAcType) {
        this.loanAcType = loanAcType;
    }

    public Integer getLoanAcNo() {
        return this.loanAcNo;
    }
    
    public void setLoanAcNo(Integer loanAcNo) {
        this.loanAcNo = loanAcNo;
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

    public String getDeDtTime() {
        return this.deDtTime;
    }
    
    public void setDeDtTime(String deDtTime) {
        this.deDtTime = deDtTime;
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

    public String getVeDtTime() {
        return this.veDtTime;
    }
    
    public void setVeDtTime(String veDtTime) {
        this.veDtTime = veDtTime;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ChequeLogId) ) return false;
		 ChequeLogId castOther = ( ChequeLogId ) other; 
         
		 return ( (this.getClgDate()==castOther.getClgDate()) || ( this.getClgDate()!=null && castOther.getClgDate()!=null && this.getClgDate().equals(castOther.getClgDate()) ) )
 && ( (this.getClgNo()==castOther.getClgNo()) || ( this.getClgNo()!=null && castOther.getClgNo()!=null && this.getClgNo().equals(castOther.getClgNo()) ) )
 && ( (this.getSendTo()==castOther.getSendTo()) || ( this.getSendTo()!=null && castOther.getSendTo()!=null && this.getSendTo().equals(castOther.getSendTo()) ) )
 && ( (this.getCtrlNo()==castOther.getCtrlNo()) || ( this.getCtrlNo()!=null && castOther.getCtrlNo()!=null && this.getCtrlNo().equals(castOther.getCtrlNo()) ) )
 && ( (this.getTrnType()==castOther.getTrnType()) || ( this.getTrnType()!=null && castOther.getTrnType()!=null && this.getTrnType().equals(castOther.getTrnType()) ) )
 && ( (this.getClgType()==castOther.getClgType()) || ( this.getClgType()!=null && castOther.getClgType()!=null && this.getClgType().equals(castOther.getClgType()) ) )
 && ( (this.getAckNo()==castOther.getAckNo()) || ( this.getAckNo()!=null && castOther.getAckNo()!=null && this.getAckNo().equals(castOther.getAckNo()) ) )
 && ( (this.getDocSou()==castOther.getDocSou()) || ( this.getDocSou()!=null && castOther.getDocSou()!=null && this.getDocSou().equals(castOther.getDocSou()) ) )
 && ( (this.getDocDest()==castOther.getDocDest()) || ( this.getDocDest()!=null && castOther.getDocDest()!=null && this.getDocDest().equals(castOther.getDocDest()) ) )
 && ( (this.getDocBs()==castOther.getDocBs()) || ( this.getDocBs()!=null && castOther.getDocBs()!=null && this.getDocBs().equals(castOther.getDocBs()) ) )
 && ( (this.getNoDocs()==castOther.getNoDocs()) || ( this.getNoDocs()!=null && castOther.getNoDocs()!=null && this.getNoDocs().equals(castOther.getNoDocs()) ) )
 && ( (this.getDocTot()==castOther.getDocTot()) || ( this.getDocTot()!=null && castOther.getDocTot()!=null && this.getDocTot().equals(castOther.getDocTot()) ) )
 && ( (this.getMultCr()==castOther.getMultCr()) || ( this.getMultCr()!=null && castOther.getMultCr()!=null && this.getMultCr().equals(castOther.getMultCr()) ) )
 && ( (this.getCompName()==castOther.getCompName()) || ( this.getCompName()!=null && castOther.getCompName()!=null && this.getCompName().equals(castOther.getCompName()) ) )
 && ( (this.getChqddpo()==castOther.getChqddpo()) || ( this.getChqddpo()!=null && castOther.getChqddpo()!=null && this.getChqddpo().equals(castOther.getChqddpo()) ) )
 && ( (this.getQdpNo()==castOther.getQdpNo()) || ( this.getQdpNo()!=null && castOther.getQdpNo()!=null && this.getQdpNo().equals(castOther.getQdpNo()) ) )
 && ( (this.getQdpDt()==castOther.getQdpDt()) || ( this.getQdpDt()!=null && castOther.getQdpDt()!=null && this.getQdpDt().equals(castOther.getQdpDt()) ) )
 && ( (this.getRetNorm()==castOther.getRetNorm()) || ( this.getRetNorm()!=null && castOther.getRetNorm()!=null && this.getRetNorm().equals(castOther.getRetNorm()) ) )
 && ( (this.getPrevCtrlNo()==castOther.getPrevCtrlNo()) || ( this.getPrevCtrlNo()!=null && castOther.getPrevCtrlNo()!=null && this.getPrevCtrlNo().equals(castOther.getPrevCtrlNo()) ) )
 && ( (this.getTrfType()==castOther.getTrfType()) || ( this.getTrfType()!=null && castOther.getTrfType()!=null && this.getTrfType().equals(castOther.getTrfType()) ) )
 && ( (this.getCrAcType()==castOther.getCrAcType()) || ( this.getCrAcType()!=null && castOther.getCrAcType()!=null && this.getCrAcType().equals(castOther.getCrAcType()) ) )
 && ( (this.getCrAcNo()==castOther.getCrAcNo()) || ( this.getCrAcNo()!=null && castOther.getCrAcNo()!=null && this.getCrAcNo().equals(castOther.getCrAcNo()) ) )
 && ( (this.getPoComm()==castOther.getPoComm()) || ( this.getPoComm()!=null && castOther.getPoComm()!=null && this.getPoComm().equals(castOther.getPoComm()) ) )
 && ( (this.getDrAcType()==castOther.getDrAcType()) || ( this.getDrAcType()!=null && castOther.getDrAcType()!=null && this.getDrAcType().equals(castOther.getDrAcType()) ) )
 && ( (this.getDrAcNo()==castOther.getDrAcNo()) || ( this.getDrAcNo()!=null && castOther.getDrAcNo()!=null && this.getDrAcNo().equals(castOther.getDrAcNo()) ) )
 && ( (this.getPayeeName()==castOther.getPayeeName()) || ( this.getPayeeName()!=null && castOther.getPayeeName()!=null && this.getPayeeName().equals(castOther.getPayeeName()) ) )
 && ( (this.getBankCd()==castOther.getBankCd()) || ( this.getBankCd()!=null && castOther.getBankCd()!=null && this.getBankCd().equals(castOther.getBankCd()) ) )
 && ( (this.getBrName()==castOther.getBrName()) || ( this.getBrName()!=null && castOther.getBrName()!=null && this.getBrName().equals(castOther.getBrName()) ) )
 && ( (this.getTrnAmt()==castOther.getTrnAmt()) || ( this.getTrnAmt()!=null && castOther.getTrnAmt()!=null && this.getTrnAmt().equals(castOther.getTrnAmt()) ) )
 && ( (this.getToBounce()==castOther.getToBounce()) || ( this.getToBounce()!=null && castOther.getToBounce()!=null && this.getToBounce().equals(castOther.getToBounce()) ) )
 && ( (this.getDespInd()==castOther.getDespInd()) || ( this.getDespInd()!=null && castOther.getDespInd()!=null && this.getDespInd().equals(castOther.getDespInd()) ) )
 && ( (this.getPostInd()==castOther.getPostInd()) || ( this.getPostInd()!=null && castOther.getPostInd()!=null && this.getPostInd().equals(castOther.getPostInd()) ) )
 && ( (this.getDiscInd()==castOther.getDiscInd()) || ( this.getDiscInd()!=null && castOther.getDiscInd()!=null && this.getDiscInd().equals(castOther.getDiscInd()) ) )
 && ( (this.getDiscAmt()==castOther.getDiscAmt()) || ( this.getDiscAmt()!=null && castOther.getDiscAmt()!=null && this.getDiscAmt().equals(castOther.getDiscAmt()) ) )
 && ( (this.getDiscChg()==castOther.getDiscChg()) || ( this.getDiscChg()!=null && castOther.getDiscChg()!=null && this.getDiscChg().equals(castOther.getDiscChg()) ) )
 && ( (this.getExpClgdt()==castOther.getExpClgdt()) || ( this.getExpClgdt()!=null && castOther.getExpClgdt()!=null && this.getExpClgdt().equals(castOther.getExpClgdt()) ) )
 && ( (this.getExpClgno()==castOther.getExpClgno()) || ( this.getExpClgno()!=null && castOther.getExpClgno()!=null && this.getExpClgno().equals(castOther.getExpClgno()) ) )
 && ( (this.getLetSent()==castOther.getLetSent()) || ( this.getLetSent()!=null && castOther.getLetSent()!=null && this.getLetSent().equals(castOther.getLetSent()) ) )
 && ( (this.getMchgAmt()==castOther.getMchgAmt()) || ( this.getMchgAmt()!=null && castOther.getMchgAmt()!=null && this.getMchgAmt().equals(castOther.getMchgAmt()) ) )
 && ( (this.getFineAmt()==castOther.getFineAmt()) || ( this.getFineAmt()!=null && castOther.getFineAmt()!=null && this.getFineAmt().equals(castOther.getFineAmt()) ) )
 && ( (this.getLoanAcType()==castOther.getLoanAcType()) || ( this.getLoanAcType()!=null && castOther.getLoanAcType()!=null && this.getLoanAcType().equals(castOther.getLoanAcType()) ) )
 && ( (this.getLoanAcNo()==castOther.getLoanAcNo()) || ( this.getLoanAcNo()!=null && castOther.getLoanAcNo()!=null && this.getLoanAcNo().equals(castOther.getLoanAcNo()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDtTime()==castOther.getDeDtTime()) || ( this.getDeDtTime()!=null && castOther.getDeDtTime()!=null && this.getDeDtTime().equals(castOther.getDeDtTime()) ) )
 && ( (this.getVeTml()==castOther.getVeTml()) || ( this.getVeTml()!=null && castOther.getVeTml()!=null && this.getVeTml().equals(castOther.getVeTml()) ) )
 && ( (this.getVeUser()==castOther.getVeUser()) || ( this.getVeUser()!=null && castOther.getVeUser()!=null && this.getVeUser().equals(castOther.getVeUser()) ) )
 && ( (this.getVeDtTime()==castOther.getVeDtTime()) || ( this.getVeDtTime()!=null && castOther.getVeDtTime()!=null && this.getVeDtTime().equals(castOther.getVeDtTime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getClgDate() == null ? 0 : this.getClgDate().hashCode() );
         result = 37 * result + ( getClgNo() == null ? 0 : this.getClgNo().hashCode() );
         result = 37 * result + ( getSendTo() == null ? 0 : this.getSendTo().hashCode() );
         result = 37 * result + ( getCtrlNo() == null ? 0 : this.getCtrlNo().hashCode() );
         result = 37 * result + ( getTrnType() == null ? 0 : this.getTrnType().hashCode() );
         result = 37 * result + ( getClgType() == null ? 0 : this.getClgType().hashCode() );
         result = 37 * result + ( getAckNo() == null ? 0 : this.getAckNo().hashCode() );
         result = 37 * result + ( getDocSou() == null ? 0 : this.getDocSou().hashCode() );
         result = 37 * result + ( getDocDest() == null ? 0 : this.getDocDest().hashCode() );
         result = 37 * result + ( getDocBs() == null ? 0 : this.getDocBs().hashCode() );
         result = 37 * result + ( getNoDocs() == null ? 0 : this.getNoDocs().hashCode() );
         result = 37 * result + ( getDocTot() == null ? 0 : this.getDocTot().hashCode() );
         result = 37 * result + ( getMultCr() == null ? 0 : this.getMultCr().hashCode() );
         result = 37 * result + ( getCompName() == null ? 0 : this.getCompName().hashCode() );
         result = 37 * result + ( getChqddpo() == null ? 0 : this.getChqddpo().hashCode() );
         result = 37 * result + ( getQdpNo() == null ? 0 : this.getQdpNo().hashCode() );
         result = 37 * result + ( getQdpDt() == null ? 0 : this.getQdpDt().hashCode() );
         result = 37 * result + ( getRetNorm() == null ? 0 : this.getRetNorm().hashCode() );
         result = 37 * result + ( getPrevCtrlNo() == null ? 0 : this.getPrevCtrlNo().hashCode() );
         result = 37 * result + ( getTrfType() == null ? 0 : this.getTrfType().hashCode() );
         result = 37 * result + ( getCrAcType() == null ? 0 : this.getCrAcType().hashCode() );
         result = 37 * result + ( getCrAcNo() == null ? 0 : this.getCrAcNo().hashCode() );
         result = 37 * result + ( getPoComm() == null ? 0 : this.getPoComm().hashCode() );
         result = 37 * result + ( getDrAcType() == null ? 0 : this.getDrAcType().hashCode() );
         result = 37 * result + ( getDrAcNo() == null ? 0 : this.getDrAcNo().hashCode() );
         result = 37 * result + ( getPayeeName() == null ? 0 : this.getPayeeName().hashCode() );
         result = 37 * result + ( getBankCd() == null ? 0 : this.getBankCd().hashCode() );
         result = 37 * result + ( getBrName() == null ? 0 : this.getBrName().hashCode() );
         result = 37 * result + ( getTrnAmt() == null ? 0 : this.getTrnAmt().hashCode() );
         result = 37 * result + ( getToBounce() == null ? 0 : this.getToBounce().hashCode() );
         result = 37 * result + ( getDespInd() == null ? 0 : this.getDespInd().hashCode() );
         result = 37 * result + ( getPostInd() == null ? 0 : this.getPostInd().hashCode() );
         result = 37 * result + ( getDiscInd() == null ? 0 : this.getDiscInd().hashCode() );
         result = 37 * result + ( getDiscAmt() == null ? 0 : this.getDiscAmt().hashCode() );
         result = 37 * result + ( getDiscChg() == null ? 0 : this.getDiscChg().hashCode() );
         result = 37 * result + ( getExpClgdt() == null ? 0 : this.getExpClgdt().hashCode() );
         result = 37 * result + ( getExpClgno() == null ? 0 : this.getExpClgno().hashCode() );
         result = 37 * result + ( getLetSent() == null ? 0 : this.getLetSent().hashCode() );
         result = 37 * result + ( getMchgAmt() == null ? 0 : this.getMchgAmt().hashCode() );
         result = 37 * result + ( getFineAmt() == null ? 0 : this.getFineAmt().hashCode() );
         result = 37 * result + ( getLoanAcType() == null ? 0 : this.getLoanAcType().hashCode() );
         result = 37 * result + ( getLoanAcNo() == null ? 0 : this.getLoanAcNo().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDtTime() == null ? 0 : this.getDeDtTime().hashCode() );
         result = 37 * result + ( getVeTml() == null ? 0 : this.getVeTml().hashCode() );
         result = 37 * result + ( getVeUser() == null ? 0 : this.getVeUser().hashCode() );
         result = 37 * result + ( getVeDtTime() == null ? 0 : this.getVeDtTime().hashCode() );
         return result;
   }   





}