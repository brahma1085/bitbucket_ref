package com.banking.data.pojo;
// default package



/**
 * LoanHistoryId entity. @author MyEclipse Persistence Tools
 */

public class LoanHistoryId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private Integer acNo;
     private String shTy;
     private Integer shNo;
     private String actionParticulars;
     private Double processingCharge;
     private Double shareAmt;
     private String name;
     private Integer stageNo;
     private Integer refNo;
     private String prtDate;
     private String sancDt;
     private Double sancAmt;
     private Double instalmentAmt;
     private Integer noOfInstalment;
     private Double currentinstalment;
     private Double intOverdue;
     private String intUpto;
     private Double penalInt;
     private Double othCharges;
     private Double loanBal;
     private Double prnOverdue;
     private String prtInd;
     private Integer defMnths;
     private String lsttrnDate;
     private String deUser;
     private String deTml;
     private String deDatetime;


    // Constructors

    /** default constructor */
    public LoanHistoryId() {
    }

    
    /** full constructor */
    public LoanHistoryId(String acType, Integer acNo, String shTy, Integer shNo, String actionParticulars, Double processingCharge, Double shareAmt, String name, Integer stageNo, Integer refNo, String prtDate, String sancDt, Double sancAmt, Double instalmentAmt, Integer noOfInstalment, Double currentinstalment, Double intOverdue, String intUpto, Double penalInt, Double othCharges, Double loanBal, Double prnOverdue, String prtInd, Integer defMnths, String lsttrnDate, String deUser, String deTml, String deDatetime) {
        this.acType = acType;
        this.acNo = acNo;
        this.shTy = shTy;
        this.shNo = shNo;
        this.actionParticulars = actionParticulars;
        this.processingCharge = processingCharge;
        this.shareAmt = shareAmt;
        this.name = name;
        this.stageNo = stageNo;
        this.refNo = refNo;
        this.prtDate = prtDate;
        this.sancDt = sancDt;
        this.sancAmt = sancAmt;
        this.instalmentAmt = instalmentAmt;
        this.noOfInstalment = noOfInstalment;
        this.currentinstalment = currentinstalment;
        this.intOverdue = intOverdue;
        this.intUpto = intUpto;
        this.penalInt = penalInt;
        this.othCharges = othCharges;
        this.loanBal = loanBal;
        this.prnOverdue = prnOverdue;
        this.prtInd = prtInd;
        this.defMnths = defMnths;
        this.lsttrnDate = lsttrnDate;
        this.deUser = deUser;
        this.deTml = deTml;
        this.deDatetime = deDatetime;
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

    public String getShTy() {
        return this.shTy;
    }
    
    public void setShTy(String shTy) {
        this.shTy = shTy;
    }

    public Integer getShNo() {
        return this.shNo;
    }
    
    public void setShNo(Integer shNo) {
        this.shNo = shNo;
    }

    public String getActionParticulars() {
        return this.actionParticulars;
    }
    
    public void setActionParticulars(String actionParticulars) {
        this.actionParticulars = actionParticulars;
    }

    public Double getProcessingCharge() {
        return this.processingCharge;
    }
    
    public void setProcessingCharge(Double processingCharge) {
        this.processingCharge = processingCharge;
    }

    public Double getShareAmt() {
        return this.shareAmt;
    }
    
    public void setShareAmt(Double shareAmt) {
        this.shareAmt = shareAmt;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public Integer getStageNo() {
        return this.stageNo;
    }
    
    public void setStageNo(Integer stageNo) {
        this.stageNo = stageNo;
    }

    public Integer getRefNo() {
        return this.refNo;
    }
    
    public void setRefNo(Integer refNo) {
        this.refNo = refNo;
    }

    public String getPrtDate() {
        return this.prtDate;
    }
    
    public void setPrtDate(String prtDate) {
        this.prtDate = prtDate;
    }

    public String getSancDt() {
        return this.sancDt;
    }
    
    public void setSancDt(String sancDt) {
        this.sancDt = sancDt;
    }

    public Double getSancAmt() {
        return this.sancAmt;
    }
    
    public void setSancAmt(Double sancAmt) {
        this.sancAmt = sancAmt;
    }

    public Double getInstalmentAmt() {
        return this.instalmentAmt;
    }
    
    public void setInstalmentAmt(Double instalmentAmt) {
        this.instalmentAmt = instalmentAmt;
    }

    public Integer getNoOfInstalment() {
        return this.noOfInstalment;
    }
    
    public void setNoOfInstalment(Integer noOfInstalment) {
        this.noOfInstalment = noOfInstalment;
    }

    public Double getCurrentinstalment() {
        return this.currentinstalment;
    }
    
    public void setCurrentinstalment(Double currentinstalment) {
        this.currentinstalment = currentinstalment;
    }

    public Double getIntOverdue() {
        return this.intOverdue;
    }
    
    public void setIntOverdue(Double intOverdue) {
        this.intOverdue = intOverdue;
    }

    public String getIntUpto() {
        return this.intUpto;
    }
    
    public void setIntUpto(String intUpto) {
        this.intUpto = intUpto;
    }

    public Double getPenalInt() {
        return this.penalInt;
    }
    
    public void setPenalInt(Double penalInt) {
        this.penalInt = penalInt;
    }

    public Double getOthCharges() {
        return this.othCharges;
    }
    
    public void setOthCharges(Double othCharges) {
        this.othCharges = othCharges;
    }

    public Double getLoanBal() {
        return this.loanBal;
    }
    
    public void setLoanBal(Double loanBal) {
        this.loanBal = loanBal;
    }

    public Double getPrnOverdue() {
        return this.prnOverdue;
    }
    
    public void setPrnOverdue(Double prnOverdue) {
        this.prnOverdue = prnOverdue;
    }

    public String getPrtInd() {
        return this.prtInd;
    }
    
    public void setPrtInd(String prtInd) {
        this.prtInd = prtInd;
    }

    public Integer getDefMnths() {
        return this.defMnths;
    }
    
    public void setDefMnths(Integer defMnths) {
        this.defMnths = defMnths;
    }

    public String getLsttrnDate() {
        return this.lsttrnDate;
    }
    
    public void setLsttrnDate(String lsttrnDate) {
        this.lsttrnDate = lsttrnDate;
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

    public String getDeDatetime() {
        return this.deDatetime;
    }
    
    public void setDeDatetime(String deDatetime) {
        this.deDatetime = deDatetime;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof LoanHistoryId) ) return false;
		 LoanHistoryId castOther = ( LoanHistoryId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getShTy()==castOther.getShTy()) || ( this.getShTy()!=null && castOther.getShTy()!=null && this.getShTy().equals(castOther.getShTy()) ) )
 && ( (this.getShNo()==castOther.getShNo()) || ( this.getShNo()!=null && castOther.getShNo()!=null && this.getShNo().equals(castOther.getShNo()) ) )
 && ( (this.getActionParticulars()==castOther.getActionParticulars()) || ( this.getActionParticulars()!=null && castOther.getActionParticulars()!=null && this.getActionParticulars().equals(castOther.getActionParticulars()) ) )
 && ( (this.getProcessingCharge()==castOther.getProcessingCharge()) || ( this.getProcessingCharge()!=null && castOther.getProcessingCharge()!=null && this.getProcessingCharge().equals(castOther.getProcessingCharge()) ) )
 && ( (this.getShareAmt()==castOther.getShareAmt()) || ( this.getShareAmt()!=null && castOther.getShareAmt()!=null && this.getShareAmt().equals(castOther.getShareAmt()) ) )
 && ( (this.getName()==castOther.getName()) || ( this.getName()!=null && castOther.getName()!=null && this.getName().equals(castOther.getName()) ) )
 && ( (this.getStageNo()==castOther.getStageNo()) || ( this.getStageNo()!=null && castOther.getStageNo()!=null && this.getStageNo().equals(castOther.getStageNo()) ) )
 && ( (this.getRefNo()==castOther.getRefNo()) || ( this.getRefNo()!=null && castOther.getRefNo()!=null && this.getRefNo().equals(castOther.getRefNo()) ) )
 && ( (this.getPrtDate()==castOther.getPrtDate()) || ( this.getPrtDate()!=null && castOther.getPrtDate()!=null && this.getPrtDate().equals(castOther.getPrtDate()) ) )
 && ( (this.getSancDt()==castOther.getSancDt()) || ( this.getSancDt()!=null && castOther.getSancDt()!=null && this.getSancDt().equals(castOther.getSancDt()) ) )
 && ( (this.getSancAmt()==castOther.getSancAmt()) || ( this.getSancAmt()!=null && castOther.getSancAmt()!=null && this.getSancAmt().equals(castOther.getSancAmt()) ) )
 && ( (this.getInstalmentAmt()==castOther.getInstalmentAmt()) || ( this.getInstalmentAmt()!=null && castOther.getInstalmentAmt()!=null && this.getInstalmentAmt().equals(castOther.getInstalmentAmt()) ) )
 && ( (this.getNoOfInstalment()==castOther.getNoOfInstalment()) || ( this.getNoOfInstalment()!=null && castOther.getNoOfInstalment()!=null && this.getNoOfInstalment().equals(castOther.getNoOfInstalment()) ) )
 && ( (this.getCurrentinstalment()==castOther.getCurrentinstalment()) || ( this.getCurrentinstalment()!=null && castOther.getCurrentinstalment()!=null && this.getCurrentinstalment().equals(castOther.getCurrentinstalment()) ) )
 && ( (this.getIntOverdue()==castOther.getIntOverdue()) || ( this.getIntOverdue()!=null && castOther.getIntOverdue()!=null && this.getIntOverdue().equals(castOther.getIntOverdue()) ) )
 && ( (this.getIntUpto()==castOther.getIntUpto()) || ( this.getIntUpto()!=null && castOther.getIntUpto()!=null && this.getIntUpto().equals(castOther.getIntUpto()) ) )
 && ( (this.getPenalInt()==castOther.getPenalInt()) || ( this.getPenalInt()!=null && castOther.getPenalInt()!=null && this.getPenalInt().equals(castOther.getPenalInt()) ) )
 && ( (this.getOthCharges()==castOther.getOthCharges()) || ( this.getOthCharges()!=null && castOther.getOthCharges()!=null && this.getOthCharges().equals(castOther.getOthCharges()) ) )
 && ( (this.getLoanBal()==castOther.getLoanBal()) || ( this.getLoanBal()!=null && castOther.getLoanBal()!=null && this.getLoanBal().equals(castOther.getLoanBal()) ) )
 && ( (this.getPrnOverdue()==castOther.getPrnOverdue()) || ( this.getPrnOverdue()!=null && castOther.getPrnOverdue()!=null && this.getPrnOverdue().equals(castOther.getPrnOverdue()) ) )
 && ( (this.getPrtInd()==castOther.getPrtInd()) || ( this.getPrtInd()!=null && castOther.getPrtInd()!=null && this.getPrtInd().equals(castOther.getPrtInd()) ) )
 && ( (this.getDefMnths()==castOther.getDefMnths()) || ( this.getDefMnths()!=null && castOther.getDefMnths()!=null && this.getDefMnths().equals(castOther.getDefMnths()) ) )
 && ( (this.getLsttrnDate()==castOther.getLsttrnDate()) || ( this.getLsttrnDate()!=null && castOther.getLsttrnDate()!=null && this.getLsttrnDate().equals(castOther.getLsttrnDate()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeDatetime()==castOther.getDeDatetime()) || ( this.getDeDatetime()!=null && castOther.getDeDatetime()!=null && this.getDeDatetime().equals(castOther.getDeDatetime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getShTy() == null ? 0 : this.getShTy().hashCode() );
         result = 37 * result + ( getShNo() == null ? 0 : this.getShNo().hashCode() );
         result = 37 * result + ( getActionParticulars() == null ? 0 : this.getActionParticulars().hashCode() );
         result = 37 * result + ( getProcessingCharge() == null ? 0 : this.getProcessingCharge().hashCode() );
         result = 37 * result + ( getShareAmt() == null ? 0 : this.getShareAmt().hashCode() );
         result = 37 * result + ( getName() == null ? 0 : this.getName().hashCode() );
         result = 37 * result + ( getStageNo() == null ? 0 : this.getStageNo().hashCode() );
         result = 37 * result + ( getRefNo() == null ? 0 : this.getRefNo().hashCode() );
         result = 37 * result + ( getPrtDate() == null ? 0 : this.getPrtDate().hashCode() );
         result = 37 * result + ( getSancDt() == null ? 0 : this.getSancDt().hashCode() );
         result = 37 * result + ( getSancAmt() == null ? 0 : this.getSancAmt().hashCode() );
         result = 37 * result + ( getInstalmentAmt() == null ? 0 : this.getInstalmentAmt().hashCode() );
         result = 37 * result + ( getNoOfInstalment() == null ? 0 : this.getNoOfInstalment().hashCode() );
         result = 37 * result + ( getCurrentinstalment() == null ? 0 : this.getCurrentinstalment().hashCode() );
         result = 37 * result + ( getIntOverdue() == null ? 0 : this.getIntOverdue().hashCode() );
         result = 37 * result + ( getIntUpto() == null ? 0 : this.getIntUpto().hashCode() );
         result = 37 * result + ( getPenalInt() == null ? 0 : this.getPenalInt().hashCode() );
         result = 37 * result + ( getOthCharges() == null ? 0 : this.getOthCharges().hashCode() );
         result = 37 * result + ( getLoanBal() == null ? 0 : this.getLoanBal().hashCode() );
         result = 37 * result + ( getPrnOverdue() == null ? 0 : this.getPrnOverdue().hashCode() );
         result = 37 * result + ( getPrtInd() == null ? 0 : this.getPrtInd().hashCode() );
         result = 37 * result + ( getDefMnths() == null ? 0 : this.getDefMnths().hashCode() );
         result = 37 * result + ( getLsttrnDate() == null ? 0 : this.getLsttrnDate().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeDatetime() == null ? 0 : this.getDeDatetime().hashCode() );
         return result;
   }   





}