package masterObject.frontCounter;
import masterObject.general.GoldObject;
import masterObject.general.IncomeObject;
import masterObject.general.NomineeObject;
import masterObject.general.PropertyObject;
import masterObject.general.SignatureInstructionObject;
import masterObject.general.UserVerifier;
import java.io.Serializable;
import java.util.Vector;

import masterObject.general.VehicleObject;


public class ODCCMasterObject implements Serializable
{
	String lnactype,appndate,paymode,sancdate,ldgprntdt,lsttrndt,clsdt,intuptodt,name,tdactype,scst,rel,shactype;
	String limit_upto,trn_dt,verified;
	int lnacno,nojthldr,appnno,pps_code,lsttrnseq,no_inst,tdacno,modcode,nosurities,dcode,shacno,addrtype;
	int cid;
	boolean loan_sanc,sanc_ver,weak,priority;
	double sanc_amt,req_amt,inst_amt,int_rate,disb_left,cl_amt,sh_add_amt,penal_rate;
	char sex;
	Vector surities,coborrowers,deposits;
	Object relative[][],interest[][],dependent[][];
	int inttype,intcalctype;

	GoldObject goldobj=null;
	IncomeObject income[];
	PropertyObject prop;
		
	Vector loan_sta = new Vector(0,1);
	public UserVerifier uv=new UserVerifier();
	double trn_amount;
	int ref_no,nom_reg_no;
	String ch_bk_issue;
	NomineeObject nomobj[]=null;
	SignatureInstructionObject siob[]=null;
	private double stockValue;
    private String freeze_ind;
    private int ledger_seq;
    private int remd_no;
    private String remd_date;
    private String npa_stage;
    private String npa_date;
    private String default_ind;
    private String open_date;
    private int psect_code;
    private String ac_status;
    private int pass_bk_seq;
    private int last_line;
    private VehicleObject vehicleobject;
	
    public void setTransDate(String trn_dt){this.trn_dt=trn_dt;}
	public String getTransDate(){return trn_dt;}
	
	public void setTransAmount(double trn_amount){this.trn_amount=trn_amount;}
	public double getTransAmount(){return trn_amount;}
	
	public void setRef_No(int ref_no){this.ref_no=ref_no;}
	public int getRef_No(){return ref_no;}
	
	public void setNomineeDetails(NomineeObject[] nom){this.nomobj=nom;}
	public NomineeObject[] getNomineeDetails(){return nomobj;}
	
	public void setSigObj(SignatureInstructionObject[] si){this.siob=si;}
	public SignatureInstructionObject[] getSigObj(){return siob;}
	
	public void setNom_regno(int nom_reg_no){this.nom_reg_no=nom_reg_no;}
	public int getNom_regno(){return nom_reg_no;}
	
	public void setChqBKIssue(String ch_bk_issue){this.ch_bk_issue=ch_bk_issue;}
	public String getChqBKIssue(){return ch_bk_issue;}
	
	public double getPenalRate(){return penal_rate;}
	public void setPenalRate(double penal_rate){this.penal_rate=penal_rate;}

	
	public double getAddShareAmount(){return sh_add_amt;}
	public void setAddShareAmount(double sh_add_amt){this.sh_add_amt=sh_add_amt;}

	
	public double getCloAmount(){return cl_amt;}
	public void setCloAmount(double cl_amt){this.cl_amt=cl_amt;}

	public String getLimitUpto(){return limit_upto;}
	public void setLimitUpto(String cl_amt){this.limit_upto=cl_amt;}

	
	public GoldObject getGoldDet(){return goldobj;}
	public void setGoldDet(GoldObject gobj){goldobj=gobj;}
	
	public Vector getLoanStatus(){return loan_sta;}
	public void setLoanStatus(Vector loan_sta){this.loan_sta=loan_sta;}

	public String getShareAccType(){return shactype;}
	public void setShareAccType(String actype){this.shactype=actype;}

	public int getShareAccNo(){return shacno;}
	public void setShareAccNo(int no){this.shacno=no;}

	public int getCustomerId(){return cid;}
	public void setCustomerId(int no){this.cid=no;}


	public int getInterestType(){return inttype;}
	public void setInterestType(int no){this.inttype=no;}

	public int getInterestRateType(){return intcalctype;}
	public void setInterestRateType(int no){this.intcalctype=no;}

	public char getSexInd(){return sex;}
	public void  setSexInd(char ver){this.sex=ver;}


	public Object[][] getRelatives(){return relative;}
	public void setRelatives(Object obj[][]){this.relative=obj;}


	public IncomeObject[] getIncomeDetails(){return income;}
	public void setIncomeDetails(IncomeObject obj[]){this.income=obj;}

	public Object[][] getInterests(){return interest;}
	public void setInterests(Object obj[][]){this.interest=obj;}

	public Object[][] getDependents(){return dependent;}
	public void setDependents(Object obj[][]){this.dependent=obj;}

	public String getScstInd(){return scst;}
	public void  setScstInd(String ver){this.scst=ver;}


	

	public String getAccType(){return lnactype;}
	public void setAccType(String actype){this.lnactype=actype;}

	public int getMailingAddress(){return addrtype;}
	public void setMailingAddress(int actype){this.addrtype=actype;}

	public String getDepositAccType(){return tdactype;}
	public void setDepositAccType(String actype){this.tdactype=actype;}

	public void setRelative(String rel){this.rel=rel;}
	public String getRelative(){return rel;}
	
	public void setDirectorCode(int code){this.dcode=code;}
	public int getDirectorCode(){return dcode;}

	public String getApplicationDate(){return appndate;}
	public void setApplicationDate(String actype){this.appndate=actype;}

	public int getDepositAccNo(){return tdacno;}
	public void setDepositAccNo(int acno){this.tdacno=acno;}


	public int getAccNo(){return lnacno;}
	public void setAccNo(int acno){this.lnacno=acno;}

	public int getModuleCode(){return modcode;}
	public void setModuleCode(int acno){this.modcode=acno;}

	public int getPurposeCode(){return pps_code;}
	public void setPurposeCode(int acno){this.pps_code=acno;}

	public int getNoOfCoBorrowers(){return nojthldr;}
	public void setNoOfCoBorrowers(int nojthldr){this.nojthldr=nojthldr;}

	public int getNoofJtHldrs(){return nojthldr;}
	public void setNoofJtHldrs(int nojthldr){this.nojthldr=nojthldr;}

	public Vector getSurities(){return surities;}
	public void setSurities(Vector surities){this.surities=surities;}

	public Vector getCoBorrowers(){return coborrowers;}
	public void setCoBorrowers(Vector coborrowers){this.coborrowers=coborrowers;}
	
	public Vector getDeposits(){return deposits;}
	public void setDeposits(Vector surities){this.deposits=surities;}

	public int getNoOfSurities(){return nosurities;}
	public void setNoOfSurities(int nojthldr){this.nosurities=nojthldr;}

	public int getApplicationSrlNo(){return appnno;}
	public void setApplicationSrlNo(int app){this.appnno=app;}

	public int getNoOfInstallments(){return no_inst;}
	public void setNoOfInstallments(int app){this.no_inst=app;}

	public double getInstallmentAmt(){return inst_amt;}
	public void setInstallmentAmt(double app){this.inst_amt=app;}

	public double getDisbursementLeft(){return disb_left;}
	public void setDisbursementLeft(double app){this.disb_left=app;}

	public double getInterestRate(){return int_rate;}
	public void setInterestRate(double app){this.int_rate=app;}

	public String getPayMode(){return paymode;}
	public void setPayMode(String paymode){this.paymode=paymode;}

	public String getSanctionDate(){return sancdate;}
	public void setSanctionDate(String acctype){this.sancdate=acctype;}


	public boolean isLoanSanctioned(){return loan_sanc;}
	public void setLoanSanctioned(boolean acctype){this.loan_sanc=acctype;}

	public boolean isSanctionVerified(){return sanc_ver;}
	public void setSanctionVerified(boolean acctype){this.sanc_ver=acctype;}

	public boolean isWeakerSection(){return weak;}
	public void setWeakerSection(boolean acctype){this.weak=acctype;}

	public boolean isPrioritySector(){return priority;}
	public void setPrioritySector(boolean acctype){this.priority=acctype;}	

	public double getCreditLimit(){return sanc_amt;}
	public void setCreditLimit(double amount){this.sanc_amt=amount;}

	public double getRequiredAmount(){return req_amt;}
	public void setRequiredAmount(double amount){this.req_amt=amount;}

	public String getName(){return name;}
	public void setName(String name){this.name=name;}

	public String getLdgPrntDate(){return ldgprntdt;}
	public void setLdgPrntDate(String ldgprntdt){this.ldgprntdt = ldgprntdt;}

	public String getLastTrndt(){return lsttrndt;}
	public void setLastTrndt(String lsttrndt){this.lsttrndt=lsttrndt;}

	public int getLastTrnSeq(){return lsttrnseq;}
	public void setLastTrnSeq(int lsttrnseq){this.lsttrnseq=lsttrnseq;}

	public String getClosedt(){return clsdt;}
	public void setClosedt(String clsdt){this.clsdt=clsdt;}

	public String getInterestUpto(){return intuptodt;}
	public void setInterestUpto(String intuptodt){this.intuptodt=intuptodt;}

	public PropertyObject getPropertyDetails(){return prop;}
	public void setPropertyDetails(PropertyObject prop){this.prop=prop;}

	public void setStockValue(double stockValue) {
		this.stockValue=stockValue;	
	}
	
	public double getStockValue(){ return stockValue;}

	public void setFreezeInd(String freez) {
	    this.freeze_ind=freez;
    }
	
	public String getFreezeInd() {
	    return freeze_ind;
    }
	
	public void setLedgerSeq(int freez) {
	    this.ledger_seq=freez;
    }

    public int getLedgerSeq() {
        return ledger_seq;
    }
    public int getRemainderNo() {
        return remd_no;
    }
	public void setRemainderNo(int freez) {
	    this.remd_no=freez;
    }
	
	public void setRemainderDate(String freez) {
	    this.remd_date=freez;
    }
	
	public String getRemainderDate() {
	    return remd_date;
    }
	
	public void setNPADate(String freez) {
	    this.npa_date=freez;
    }
	
	public String getNPADate() {
	    return npa_date;
    }
	
    public String getNPAStage() {
        return npa_stage;
    }
	public void setNPAStage(String freez) {
	    this.npa_stage=freez;
    }
    public String getDefaultInd() {
        return default_ind;
    }

    public void setDefaultInd(String def) {
        this.default_ind=def;
    }
    public void setAccOpenDate(String string) {
        this.open_date=string;
    }
    public String getAccOpenDate()
    {
        return open_date;
    }
    public void setSanctionAmount(double double1) {
         this.sanc_amt=double1;
        
    }
    
    public double getSanctionAmount()
    {
        return sanc_amt;
    }
    public void setPrioritySectorCode(int int1) {
        this.psect_code=int1;
        
    }
    
    public int getPrioritySectorCode()
    {
        return psect_code;
    }
    public String getAccountStatus() {
        return ac_status;
    }
    
    public void setAccountStatus(String str) {
        ac_status=str;
    }
    public int getPassBookSeq() {
        return pass_bk_seq;
    }
    
    public void setPassBookSeq(int seq) {
        this.pass_bk_seq=seq;
    }
    public int getLastLine() {
               return last_line;
    }
    
    public void setLastLine(int seq) {
        this.last_line=seq;
    }
    public VehicleObject getVehicleDet() {
        return vehicleobject;
    }


    public void setVehicleDet(VehicleObject vehicle) {
        this.vehicleobject=vehicle;
    }
	public String getVerified() {
		return verified;
	}
	public void setVerified(String verified) {
		this.verified = verified;
	}
	




}
