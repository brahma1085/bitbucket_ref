package masterObject.loans;
import masterObject.customer.CustomerMasterObject;
import masterObject.general.GoldObject;
import masterObject.general.IncomeObject;
import masterObject.general.PropertyObject;
import masterObject.general.RelativeObject;
import masterObject.general.SignatureInstructionObject;
import masterObject.general.UserVerifier;
import masterObject.general.VehicleObject;
import masterObject.parameters.CustomerObject;
import masterObject.share.ShareMasterObject;

import java.io.Serializable;
import java.util.Vector;


public class LoanMasterObject implements Serializable
{
	String lnactype,appndate,paymode,payacctype,sancdate,ldgprntdt,lsttrndt,clsdt,intuptodt,name,tdactype,scst,rel=null,shactype,loanMod,remd_date;
	int addrtype,holiday_period,prior,remd_no;
	int lnacno,nojthldr,appnno,payaccno,pps_code,lsttrnseq,no_inst,tdacno,modcode,nosurities,dcode=0,shacno;
	int cid;
	boolean loan_sanc,sanc_ver,weak,priority,loan_disb,loan_dis_ver;
	double sanc_amt,req_amt,inst_amt,int_rate,disb_left,cl_amt,sh_add_amt,penal_rate,last_disb_amt;
	char sex,ver;
	Vector surities,coborrowers;
	Object relative[][],interest[][],dependent[][];
	int inttype,intcalctype,mem_cat;
	boolean[] submited_details;
	
	GoldObject goldobj;
	IncomeObject income[];
	PropertyObject prop;
	VehicleObject vobj;
	SignatureInstructionObject signobj[]=null;
	RelativeObject relObj;
	Vector loan_sta = new Vector(0,1);
	
	
	

	public UserVerifier uv=new UserVerifier();
	
	public double getPenalRate(){return penal_rate;}
	public void setPenalRate(double penal_rate){this.penal_rate=penal_rate;}

	
	public double getAddShareAmount(){return sh_add_amt;}
	public void setAddShareAmount(double sh_add_amt){this.sh_add_amt=sh_add_amt;}

	
	public double getCloAmount(){return cl_amt;}
	public void setCloAmount(double cl_amt){this.cl_amt=cl_amt;}


	public GoldObject getGoldDet(){return goldobj;}
	public void setGoldDet(GoldObject gobj){goldobj=gobj;}
	
	public Vector getLoanStatus(){return loan_sta;}
	public void setLoanStatus(Vector loan_sta){this.loan_sta=loan_sta;}


	public VehicleObject getVehicleDet(){return vobj;}
	public void setVehicleDet(VehicleObject obj){vobj=obj;}

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


	public char getVerified(){return ver;}
	public void  setVerified(char ver){this.ver=ver;}

	public String getAccType(){return lnactype;}
	public void setAccType(String actype){this.lnactype=actype;}

	public int getMailingAddress(){return addrtype;}
	public void setMailingAddress(int addrtype){this.addrtype=addrtype;}

	public int getHolidayPeriod(){return holiday_period;}
	public void setHolidayPeriod(int holiday_period){this.holiday_period=holiday_period;}
	
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

	public int getPaymentAccno(){return payaccno;}
	public void setPaymentAccno(int accno){this.payaccno=accno;}

	public String getPaymentAcctype(){return payacctype;}
	public void setPaymentAcctype(String acctype){this.payacctype=acctype;}

	public String getSanctionDate(){return sancdate;}
	public void setSanctionDate(String acctype){this.sancdate=acctype;}


	public boolean isLoanSanctioned(){return loan_sanc;}
	public void setLoanSanctioned(boolean acctype){this.loan_sanc=acctype;}
	
	public boolean isLoanDisbursed(){return loan_disb;}
	public void setLoanDisbursed(boolean loan_disb){this.loan_disb=loan_disb;}

	public boolean isSanctionVerified(){return sanc_ver;}
	public void setSanctionVerified(boolean acctype){this.sanc_ver=acctype;}
	
	public boolean isLoanDisbVerified(){return loan_dis_ver;}
	public void setLoanDisbVerified(boolean loan_dis_ver){this.loan_dis_ver=loan_dis_ver;}

	public boolean isWeakerSection(){return weak;}
	public void setWeakerSection(boolean acctype){this.weak=acctype;}

	public boolean isPrioritySector(){return priority;}
	public void setPrioritySector(boolean acctype){this.priority=acctype;}	

	public double getSanctionedAmount(){return sanc_amt;}
	public void setSanctionedAmount(double amount){this.sanc_amt=amount;}
	
	public double getLastDisbAmount(){return last_disb_amt;}
	public void setLastDisbmount(double last_disb_amt){this.last_disb_amt=last_disb_amt;}

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
	
	// Method's Added by Murugesh on 14/12/2005
	public boolean[] getSubmitedDetails(){return submited_details;}
	public void setSubmitedDetails(boolean[] submited_details){this.submited_details = submited_details;}
	
	public SignatureInstructionObject[] getSignatureDet(){return signobj;}
	public void setSignatureDet(SignatureInstructionObject[] signobj){this.signobj=signobj;}
	public int getPrior() {
		return prior;
	}
	public void setPrior(int prior) {
		this.prior = prior;
	}
	public String getLoanMod() {
		return loanMod;
	}
	public void setLoanMod(String loanMod) {
		this.loanMod = loanMod;
	}
	public int getMem_cat() {
		return mem_cat;
	}
	public void setMem_cat(int mem_cat) {
		this.mem_cat = mem_cat;
	}
	public int getRemd_no() {
		return remd_no;
	}
	public void setRemd_no(int remd_no) {
		this.remd_no = remd_no;
	}
	public String getRemd_date() {
		return remd_date;
	}
	public void setRemd_date(String remd_date) {
		this.remd_date = remd_date;
	}
	
	
}
