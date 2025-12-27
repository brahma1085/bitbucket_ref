package masterObject.loansOnDeposit;


import masterObject.general.UserVerifier;

import java.io.Serializable;

//lnee_lf_no int,srty_lf_no int,srty_lf2 int,
//nomne_nm varchar(50),tdac_ty varchar(10),tdac_no int,srty_ac_ty varchar(10),
//srty_ac_no int,srty_ty2 varchar(5),srty_no2 int,srty1_br int,srty2_br int,
//psect_cd int,wk_sect char(1),rel varchar(20),
//conv_dt varchar(10),narr varchar(250),propadd varchar(250),
//holday_mth int(2),tr_ty char(1),
//default_ind enum('T','F')
//remd_no int,
//disb_left int,remd_dt varchar(10),dir_code int,

public class LoanMasterObject implements Serializable
{
	private String lnactype,lncategory,appndate,paymode,payacctype,sancdate,ldgprntdt,lsttrndt,clsdt,intuptodt,name,tdactype,scst,addrtype,modcode,trnMode,auto_loan;
	private int lnacno,nojthldr,appnno,payaccno,pps_code,lsttrnseq,no_inst,tdacno,cid;
	private boolean loan_sanc,sanc_ver;
	private double sanc_amt,req_amt,inst_amt,int_rate,pr_bal;
	private char sex,ver;
	int vouher_no;
	double trnamt;

	public UserVerifier uv=new UserVerifier();
	
	public double getInterestRate(){return int_rate;}
	public void setInterestRate(double app){this.int_rate=app;}

	public char getSexInd(){return sex;}
	public void  setSexInd(char ver){this.sex=ver;}

	public String getScstInd(){return scst;}
	public void  setScstInd(String ver){this.scst=ver;}
	
	public int getCustomerId(){return cid;}
	public void setCustomerId(int no){this.cid=no;}
	
	public String getMailingAddress(){return addrtype;}
	public void setMailingAddress(String actype){this.addrtype=actype;}

	public char getVerified(){return ver;}
	public void  setVerified(char ver){this.ver=ver;}

	public String getAccType(){return lnactype;}
	public void setAccType(String actype){this.lnactype=actype;}

	public String getDepositAccType(){return tdactype;}
	public void setDepositAccType(String actype){this.tdactype=actype;}

	public String getLoanCategory(){return lncategory;}
	public void setLoanCategory(String actype){this.lncategory=actype;}

	public String getApplicationDate(){return appndate;}
	public void setApplicationDate(String actype){this.appndate=actype;}

	public int getDepositAccNo(){return tdacno;}
	public void setDepositAccNo(int acno){this.tdacno=acno;}

	public int getAccNo(){return lnacno;}
	public void setAccNo(int acno){this.lnacno=acno;}

	public String getModuleCode(){return modcode;}
	public void setModuleCode(String acno){this.modcode=acno;}

	public int getPurposeCode(){return pps_code;}
	public void setPurposeCode(int acno){this.pps_code=acno;}

	public int getNoofJtHldrs(){return nojthldr;}
	public void setNoofJtHldrs(int nojthldr){this.nojthldr=nojthldr;}

	public int getApplicationSrlNo(){return appnno;}
	public void setApplicationSrlNo(int app){this.appnno=app;}

	public int getNoOfInstallments(){return no_inst;}
	public void setNoOfInstallments(int app){this.no_inst=app;}

	public double getInstallmentAmt(){return inst_amt;}
	public void setInstallmentAmt(double app){this.inst_amt=app;}


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

	public boolean isSanctionVerified(){return sanc_ver;}
	public void setSanctionVerified(boolean acctype){this.sanc_ver=acctype;}


	public double getSanctionedAmount(){return sanc_amt;}
	public void setSanctionedAmount(double amount){this.sanc_amt=amount;}
	
	public double getPrBal(){return pr_bal;}
	public void setPrBal(double pr_bal){this.pr_bal=pr_bal;}


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
	
	public int getVouher_no() {
		return vouher_no;
	}
	public void setVouher_no(int vouher_no) {
		this.vouher_no = vouher_no;
	}
	public double getTrnamt() {
		return trnamt;
	}
	public void setTrnamt(double trnamt) {
		this.trnamt = trnamt;
	}
	
	public String getTrnMode() {
		return trnMode;
	}
	public void setTrnMode(String trnMode) {
		this.trnMode = trnMode;
	}
	public String getAuto_loan() {
		return auto_loan;
	}
	public void setAuto_loan(String auto_loan) {
		this.auto_loan = auto_loan;
	}
}
