package com.banking.data.pojo;
// default package



/**
 * DayCashId entity. @author MyEclipse Persistence Tools
 */

public class DayCashId  implements java.io.Serializable {


    // Fields    

     private Integer scrollNo;
     private String trnDate;
     private String acType;
     private String acNo;
     private String trnType;
     private String othTml;
     private Double cshAmt;
     private String cdInd;
     private String name;
     private Integer tokenNo;
     private Integer vchNo;
     private String vchType;
     private Integer trnSeq;
     private Double runBal;
     private Integer chqNo;
     private Double commAmt;
     private String lockerType;
     private String attached;
     private String deUser;
     private String deTml;
     private String deDate;
     private String veUser;
     private String veDate;
     private String veTml;
     private Integer r1000;
     private Integer p1000;
     private Integer r500;
     private Integer p500;
     private Integer r100;
     private Integer p100;
     private Integer r50;
     private Integer p50;
     private Integer r20;
     private Integer p20;
     private Integer r10;
     private Integer p10;
     private Integer r5;
     private Integer p5;
     private Integer r2;
     private Integer p2;
     private Integer r1;
     private Integer p1;
     private Double rcoins;
     private Double pcoins;
     private String tmlNo;
     private String shareCategory;
     private String custAcType;
     private String custAcNo;
     private String custCode;
     private String poFavourName;


    // Constructors

    /** default constructor */
    public DayCashId() {
    }

	/** minimal constructor */
    public DayCashId(Integer scrollNo, String trnDate) {
        this.scrollNo = scrollNo;
        this.trnDate = trnDate;
    }
    
    /** full constructor */
    public DayCashId(Integer scrollNo, String trnDate, String acType, String acNo, String trnType, String othTml, Double cshAmt, String cdInd, String name, Integer tokenNo, Integer vchNo, String vchType, Integer trnSeq, Double runBal, Integer chqNo, Double commAmt, String lockerType, String attached, String deUser, String deTml, String deDate, String veUser, String veDate, String veTml, Integer r1000, Integer p1000, Integer r500, Integer p500, Integer r100, Integer p100, Integer r50, Integer p50, Integer r20, Integer p20, Integer r10, Integer p10, Integer r5, Integer p5, Integer r2, Integer p2, Integer r1, Integer p1, Double rcoins, Double pcoins, String tmlNo, String shareCategory, String custAcType, String custAcNo, String custCode, String poFavourName) {
        this.scrollNo = scrollNo;
        this.trnDate = trnDate;
        this.acType = acType;
        this.acNo = acNo;
        this.trnType = trnType;
        this.othTml = othTml;
        this.cshAmt = cshAmt;
        this.cdInd = cdInd;
        this.name = name;
        this.tokenNo = tokenNo;
        this.vchNo = vchNo;
        this.vchType = vchType;
        this.trnSeq = trnSeq;
        this.runBal = runBal;
        this.chqNo = chqNo;
        this.commAmt = commAmt;
        this.lockerType = lockerType;
        this.attached = attached;
        this.deUser = deUser;
        this.deTml = deTml;
        this.deDate = deDate;
        this.veUser = veUser;
        this.veDate = veDate;
        this.veTml = veTml;
        this.r1000 = r1000;
        this.p1000 = p1000;
        this.r500 = r500;
        this.p500 = p500;
        this.r100 = r100;
        this.p100 = p100;
        this.r50 = r50;
        this.p50 = p50;
        this.r20 = r20;
        this.p20 = p20;
        this.r10 = r10;
        this.p10 = p10;
        this.r5 = r5;
        this.p5 = p5;
        this.r2 = r2;
        this.p2 = p2;
        this.r1 = r1;
        this.p1 = p1;
        this.rcoins = rcoins;
        this.pcoins = pcoins;
        this.tmlNo = tmlNo;
        this.shareCategory = shareCategory;
        this.custAcType = custAcType;
        this.custAcNo = custAcNo;
        this.custCode = custCode;
        this.poFavourName = poFavourName;
    }

   
    // Property accessors

    public Integer getScrollNo() {
        return this.scrollNo;
    }
    
    public void setScrollNo(Integer scrollNo) {
        this.scrollNo = scrollNo;
    }

    public String getTrnDate() {
        return this.trnDate;
    }
    
    public void setTrnDate(String trnDate) {
        this.trnDate = trnDate;
    }

    public String getAcType() {
        return this.acType;
    }
    
    public void setAcType(String acType) {
        this.acType = acType;
    }

    public String getAcNo() {
        return this.acNo;
    }
    
    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getTrnType() {
        return this.trnType;
    }
    
    public void setTrnType(String trnType) {
        this.trnType = trnType;
    }

    public String getOthTml() {
        return this.othTml;
    }
    
    public void setOthTml(String othTml) {
        this.othTml = othTml;
    }

    public Double getCshAmt() {
        return this.cshAmt;
    }
    
    public void setCshAmt(Double cshAmt) {
        this.cshAmt = cshAmt;
    }

    public String getCdInd() {
        return this.cdInd;
    }
    
    public void setCdInd(String cdInd) {
        this.cdInd = cdInd;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public Integer getTokenNo() {
        return this.tokenNo;
    }
    
    public void setTokenNo(Integer tokenNo) {
        this.tokenNo = tokenNo;
    }

    public Integer getVchNo() {
        return this.vchNo;
    }
    
    public void setVchNo(Integer vchNo) {
        this.vchNo = vchNo;
    }

    public String getVchType() {
        return this.vchType;
    }
    
    public void setVchType(String vchType) {
        this.vchType = vchType;
    }

    public Integer getTrnSeq() {
        return this.trnSeq;
    }
    
    public void setTrnSeq(Integer trnSeq) {
        this.trnSeq = trnSeq;
    }

    public Double getRunBal() {
        return this.runBal;
    }
    
    public void setRunBal(Double runBal) {
        this.runBal = runBal;
    }

    public Integer getChqNo() {
        return this.chqNo;
    }
    
    public void setChqNo(Integer chqNo) {
        this.chqNo = chqNo;
    }

    public Double getCommAmt() {
        return this.commAmt;
    }
    
    public void setCommAmt(Double commAmt) {
        this.commAmt = commAmt;
    }

    public String getLockerType() {
        return this.lockerType;
    }
    
    public void setLockerType(String lockerType) {
        this.lockerType = lockerType;
    }

    public String getAttached() {
        return this.attached;
    }
    
    public void setAttached(String attached) {
        this.attached = attached;
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

    public String getVeDate() {
        return this.veDate;
    }
    
    public void setVeDate(String veDate) {
        this.veDate = veDate;
    }

    public String getVeTml() {
        return this.veTml;
    }
    
    public void setVeTml(String veTml) {
        this.veTml = veTml;
    }

    public Integer getR1000() {
        return this.r1000;
    }
    
    public void setR1000(Integer r1000) {
        this.r1000 = r1000;
    }

    public Integer getP1000() {
        return this.p1000;
    }
    
    public void setP1000(Integer p1000) {
        this.p1000 = p1000;
    }

    public Integer getR500() {
        return this.r500;
    }
    
    public void setR500(Integer r500) {
        this.r500 = r500;
    }

    public Integer getP500() {
        return this.p500;
    }
    
    public void setP500(Integer p500) {
        this.p500 = p500;
    }

    public Integer getR100() {
        return this.r100;
    }
    
    public void setR100(Integer r100) {
        this.r100 = r100;
    }

    public Integer getP100() {
        return this.p100;
    }
    
    public void setP100(Integer p100) {
        this.p100 = p100;
    }

    public Integer getR50() {
        return this.r50;
    }
    
    public void setR50(Integer r50) {
        this.r50 = r50;
    }

    public Integer getP50() {
        return this.p50;
    }
    
    public void setP50(Integer p50) {
        this.p50 = p50;
    }

    public Integer getR20() {
        return this.r20;
    }
    
    public void setR20(Integer r20) {
        this.r20 = r20;
    }

    public Integer getP20() {
        return this.p20;
    }
    
    public void setP20(Integer p20) {
        this.p20 = p20;
    }

    public Integer getR10() {
        return this.r10;
    }
    
    public void setR10(Integer r10) {
        this.r10 = r10;
    }

    public Integer getP10() {
        return this.p10;
    }
    
    public void setP10(Integer p10) {
        this.p10 = p10;
    }

    public Integer getR5() {
        return this.r5;
    }
    
    public void setR5(Integer r5) {
        this.r5 = r5;
    }

    public Integer getP5() {
        return this.p5;
    }
    
    public void setP5(Integer p5) {
        this.p5 = p5;
    }

    public Integer getR2() {
        return this.r2;
    }
    
    public void setR2(Integer r2) {
        this.r2 = r2;
    }

    public Integer getP2() {
        return this.p2;
    }
    
    public void setP2(Integer p2) {
        this.p2 = p2;
    }

    public Integer getR1() {
        return this.r1;
    }
    
    public void setR1(Integer r1) {
        this.r1 = r1;
    }

    public Integer getP1() {
        return this.p1;
    }
    
    public void setP1(Integer p1) {
        this.p1 = p1;
    }

    public Double getRcoins() {
        return this.rcoins;
    }
    
    public void setRcoins(Double rcoins) {
        this.rcoins = rcoins;
    }

    public Double getPcoins() {
        return this.pcoins;
    }
    
    public void setPcoins(Double pcoins) {
        this.pcoins = pcoins;
    }

    public String getTmlNo() {
        return this.tmlNo;
    }
    
    public void setTmlNo(String tmlNo) {
        this.tmlNo = tmlNo;
    }

    public String getShareCategory() {
        return this.shareCategory;
    }
    
    public void setShareCategory(String shareCategory) {
        this.shareCategory = shareCategory;
    }

    public String getCustAcType() {
        return this.custAcType;
    }
    
    public void setCustAcType(String custAcType) {
        this.custAcType = custAcType;
    }

    public String getCustAcNo() {
        return this.custAcNo;
    }
    
    public void setCustAcNo(String custAcNo) {
        this.custAcNo = custAcNo;
    }

    public String getCustCode() {
        return this.custCode;
    }
    
    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getPoFavourName() {
        return this.poFavourName;
    }
    
    public void setPoFavourName(String poFavourName) {
        this.poFavourName = poFavourName;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof DayCashId) ) return false;
		 DayCashId castOther = ( DayCashId ) other; 
         
		 return ( (this.getScrollNo()==castOther.getScrollNo()) || ( this.getScrollNo()!=null && castOther.getScrollNo()!=null && this.getScrollNo().equals(castOther.getScrollNo()) ) )
 && ( (this.getTrnDate()==castOther.getTrnDate()) || ( this.getTrnDate()!=null && castOther.getTrnDate()!=null && this.getTrnDate().equals(castOther.getTrnDate()) ) )
 && ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getTrnType()==castOther.getTrnType()) || ( this.getTrnType()!=null && castOther.getTrnType()!=null && this.getTrnType().equals(castOther.getTrnType()) ) )
 && ( (this.getOthTml()==castOther.getOthTml()) || ( this.getOthTml()!=null && castOther.getOthTml()!=null && this.getOthTml().equals(castOther.getOthTml()) ) )
 && ( (this.getCshAmt()==castOther.getCshAmt()) || ( this.getCshAmt()!=null && castOther.getCshAmt()!=null && this.getCshAmt().equals(castOther.getCshAmt()) ) )
 && ( (this.getCdInd()==castOther.getCdInd()) || ( this.getCdInd()!=null && castOther.getCdInd()!=null && this.getCdInd().equals(castOther.getCdInd()) ) )
 && ( (this.getName()==castOther.getName()) || ( this.getName()!=null && castOther.getName()!=null && this.getName().equals(castOther.getName()) ) )
 && ( (this.getTokenNo()==castOther.getTokenNo()) || ( this.getTokenNo()!=null && castOther.getTokenNo()!=null && this.getTokenNo().equals(castOther.getTokenNo()) ) )
 && ( (this.getVchNo()==castOther.getVchNo()) || ( this.getVchNo()!=null && castOther.getVchNo()!=null && this.getVchNo().equals(castOther.getVchNo()) ) )
 && ( (this.getVchType()==castOther.getVchType()) || ( this.getVchType()!=null && castOther.getVchType()!=null && this.getVchType().equals(castOther.getVchType()) ) )
 && ( (this.getTrnSeq()==castOther.getTrnSeq()) || ( this.getTrnSeq()!=null && castOther.getTrnSeq()!=null && this.getTrnSeq().equals(castOther.getTrnSeq()) ) )
 && ( (this.getRunBal()==castOther.getRunBal()) || ( this.getRunBal()!=null && castOther.getRunBal()!=null && this.getRunBal().equals(castOther.getRunBal()) ) )
 && ( (this.getChqNo()==castOther.getChqNo()) || ( this.getChqNo()!=null && castOther.getChqNo()!=null && this.getChqNo().equals(castOther.getChqNo()) ) )
 && ( (this.getCommAmt()==castOther.getCommAmt()) || ( this.getCommAmt()!=null && castOther.getCommAmt()!=null && this.getCommAmt().equals(castOther.getCommAmt()) ) )
 && ( (this.getLockerType()==castOther.getLockerType()) || ( this.getLockerType()!=null && castOther.getLockerType()!=null && this.getLockerType().equals(castOther.getLockerType()) ) )
 && ( (this.getAttached()==castOther.getAttached()) || ( this.getAttached()!=null && castOther.getAttached()!=null && this.getAttached().equals(castOther.getAttached()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) )
 && ( (this.getVeUser()==castOther.getVeUser()) || ( this.getVeUser()!=null && castOther.getVeUser()!=null && this.getVeUser().equals(castOther.getVeUser()) ) )
 && ( (this.getVeDate()==castOther.getVeDate()) || ( this.getVeDate()!=null && castOther.getVeDate()!=null && this.getVeDate().equals(castOther.getVeDate()) ) )
 && ( (this.getVeTml()==castOther.getVeTml()) || ( this.getVeTml()!=null && castOther.getVeTml()!=null && this.getVeTml().equals(castOther.getVeTml()) ) )
 && ( (this.getR1000()==castOther.getR1000()) || ( this.getR1000()!=null && castOther.getR1000()!=null && this.getR1000().equals(castOther.getR1000()) ) )
 && ( (this.getP1000()==castOther.getP1000()) || ( this.getP1000()!=null && castOther.getP1000()!=null && this.getP1000().equals(castOther.getP1000()) ) )
 && ( (this.getR500()==castOther.getR500()) || ( this.getR500()!=null && castOther.getR500()!=null && this.getR500().equals(castOther.getR500()) ) )
 && ( (this.getP500()==castOther.getP500()) || ( this.getP500()!=null && castOther.getP500()!=null && this.getP500().equals(castOther.getP500()) ) )
 && ( (this.getR100()==castOther.getR100()) || ( this.getR100()!=null && castOther.getR100()!=null && this.getR100().equals(castOther.getR100()) ) )
 && ( (this.getP100()==castOther.getP100()) || ( this.getP100()!=null && castOther.getP100()!=null && this.getP100().equals(castOther.getP100()) ) )
 && ( (this.getR50()==castOther.getR50()) || ( this.getR50()!=null && castOther.getR50()!=null && this.getR50().equals(castOther.getR50()) ) )
 && ( (this.getP50()==castOther.getP50()) || ( this.getP50()!=null && castOther.getP50()!=null && this.getP50().equals(castOther.getP50()) ) )
 && ( (this.getR20()==castOther.getR20()) || ( this.getR20()!=null && castOther.getR20()!=null && this.getR20().equals(castOther.getR20()) ) )
 && ( (this.getP20()==castOther.getP20()) || ( this.getP20()!=null && castOther.getP20()!=null && this.getP20().equals(castOther.getP20()) ) )
 && ( (this.getR10()==castOther.getR10()) || ( this.getR10()!=null && castOther.getR10()!=null && this.getR10().equals(castOther.getR10()) ) )
 && ( (this.getP10()==castOther.getP10()) || ( this.getP10()!=null && castOther.getP10()!=null && this.getP10().equals(castOther.getP10()) ) )
 && ( (this.getR5()==castOther.getR5()) || ( this.getR5()!=null && castOther.getR5()!=null && this.getR5().equals(castOther.getR5()) ) )
 && ( (this.getP5()==castOther.getP5()) || ( this.getP5()!=null && castOther.getP5()!=null && this.getP5().equals(castOther.getP5()) ) )
 && ( (this.getR2()==castOther.getR2()) || ( this.getR2()!=null && castOther.getR2()!=null && this.getR2().equals(castOther.getR2()) ) )
 && ( (this.getP2()==castOther.getP2()) || ( this.getP2()!=null && castOther.getP2()!=null && this.getP2().equals(castOther.getP2()) ) )
 && ( (this.getR1()==castOther.getR1()) || ( this.getR1()!=null && castOther.getR1()!=null && this.getR1().equals(castOther.getR1()) ) )
 && ( (this.getP1()==castOther.getP1()) || ( this.getP1()!=null && castOther.getP1()!=null && this.getP1().equals(castOther.getP1()) ) )
 && ( (this.getRcoins()==castOther.getRcoins()) || ( this.getRcoins()!=null && castOther.getRcoins()!=null && this.getRcoins().equals(castOther.getRcoins()) ) )
 && ( (this.getPcoins()==castOther.getPcoins()) || ( this.getPcoins()!=null && castOther.getPcoins()!=null && this.getPcoins().equals(castOther.getPcoins()) ) )
 && ( (this.getTmlNo()==castOther.getTmlNo()) || ( this.getTmlNo()!=null && castOther.getTmlNo()!=null && this.getTmlNo().equals(castOther.getTmlNo()) ) )
 && ( (this.getShareCategory()==castOther.getShareCategory()) || ( this.getShareCategory()!=null && castOther.getShareCategory()!=null && this.getShareCategory().equals(castOther.getShareCategory()) ) )
 && ( (this.getCustAcType()==castOther.getCustAcType()) || ( this.getCustAcType()!=null && castOther.getCustAcType()!=null && this.getCustAcType().equals(castOther.getCustAcType()) ) )
 && ( (this.getCustAcNo()==castOther.getCustAcNo()) || ( this.getCustAcNo()!=null && castOther.getCustAcNo()!=null && this.getCustAcNo().equals(castOther.getCustAcNo()) ) )
 && ( (this.getCustCode()==castOther.getCustCode()) || ( this.getCustCode()!=null && castOther.getCustCode()!=null && this.getCustCode().equals(castOther.getCustCode()) ) )
 && ( (this.getPoFavourName()==castOther.getPoFavourName()) || ( this.getPoFavourName()!=null && castOther.getPoFavourName()!=null && this.getPoFavourName().equals(castOther.getPoFavourName()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getScrollNo() == null ? 0 : this.getScrollNo().hashCode() );
         result = 37 * result + ( getTrnDate() == null ? 0 : this.getTrnDate().hashCode() );
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getTrnType() == null ? 0 : this.getTrnType().hashCode() );
         result = 37 * result + ( getOthTml() == null ? 0 : this.getOthTml().hashCode() );
         result = 37 * result + ( getCshAmt() == null ? 0 : this.getCshAmt().hashCode() );
         result = 37 * result + ( getCdInd() == null ? 0 : this.getCdInd().hashCode() );
         result = 37 * result + ( getName() == null ? 0 : this.getName().hashCode() );
         result = 37 * result + ( getTokenNo() == null ? 0 : this.getTokenNo().hashCode() );
         result = 37 * result + ( getVchNo() == null ? 0 : this.getVchNo().hashCode() );
         result = 37 * result + ( getVchType() == null ? 0 : this.getVchType().hashCode() );
         result = 37 * result + ( getTrnSeq() == null ? 0 : this.getTrnSeq().hashCode() );
         result = 37 * result + ( getRunBal() == null ? 0 : this.getRunBal().hashCode() );
         result = 37 * result + ( getChqNo() == null ? 0 : this.getChqNo().hashCode() );
         result = 37 * result + ( getCommAmt() == null ? 0 : this.getCommAmt().hashCode() );
         result = 37 * result + ( getLockerType() == null ? 0 : this.getLockerType().hashCode() );
         result = 37 * result + ( getAttached() == null ? 0 : this.getAttached().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         result = 37 * result + ( getVeUser() == null ? 0 : this.getVeUser().hashCode() );
         result = 37 * result + ( getVeDate() == null ? 0 : this.getVeDate().hashCode() );
         result = 37 * result + ( getVeTml() == null ? 0 : this.getVeTml().hashCode() );
         result = 37 * result + ( getR1000() == null ? 0 : this.getR1000().hashCode() );
         result = 37 * result + ( getP1000() == null ? 0 : this.getP1000().hashCode() );
         result = 37 * result + ( getR500() == null ? 0 : this.getR500().hashCode() );
         result = 37 * result + ( getP500() == null ? 0 : this.getP500().hashCode() );
         result = 37 * result + ( getR100() == null ? 0 : this.getR100().hashCode() );
         result = 37 * result + ( getP100() == null ? 0 : this.getP100().hashCode() );
         result = 37 * result + ( getR50() == null ? 0 : this.getR50().hashCode() );
         result = 37 * result + ( getP50() == null ? 0 : this.getP50().hashCode() );
         result = 37 * result + ( getR20() == null ? 0 : this.getR20().hashCode() );
         result = 37 * result + ( getP20() == null ? 0 : this.getP20().hashCode() );
         result = 37 * result + ( getR10() == null ? 0 : this.getR10().hashCode() );
         result = 37 * result + ( getP10() == null ? 0 : this.getP10().hashCode() );
         result = 37 * result + ( getR5() == null ? 0 : this.getR5().hashCode() );
         result = 37 * result + ( getP5() == null ? 0 : this.getP5().hashCode() );
         result = 37 * result + ( getR2() == null ? 0 : this.getR2().hashCode() );
         result = 37 * result + ( getP2() == null ? 0 : this.getP2().hashCode() );
         result = 37 * result + ( getR1() == null ? 0 : this.getR1().hashCode() );
         result = 37 * result + ( getP1() == null ? 0 : this.getP1().hashCode() );
         result = 37 * result + ( getRcoins() == null ? 0 : this.getRcoins().hashCode() );
         result = 37 * result + ( getPcoins() == null ? 0 : this.getPcoins().hashCode() );
         result = 37 * result + ( getTmlNo() == null ? 0 : this.getTmlNo().hashCode() );
         result = 37 * result + ( getShareCategory() == null ? 0 : this.getShareCategory().hashCode() );
         result = 37 * result + ( getCustAcType() == null ? 0 : this.getCustAcType().hashCode() );
         result = 37 * result + ( getCustAcNo() == null ? 0 : this.getCustAcNo().hashCode() );
         result = 37 * result + ( getCustCode() == null ? 0 : this.getCustCode().hashCode() );
         result = 37 * result + ( getPoFavourName() == null ? 0 : this.getPoFavourName().hashCode() );
         return result;
   }   





}