package masterObject.generalLedger;

import java.io.Serializable;

/**
 * @author J.ShivaKumaar
 */
public class GLObject implements Serializable
{
	
	String[] tableColumnNames;
	
	String acType,glType,glCode,glName,shType,glStatus,keyDesc,voucherStatus,normalCD,trnType,trnDesc,crDr,deTml,deUser,deDate,from_dt,to_dt,trn_date,Bank_name,addr,br_name,gltypeabbr;
	int multBy,code,br_code;
	double cash_dr,cg_dr,trf_dr,cash_cr,cg_cr,trf_cr,credit_sum,debit_sum;
	String vedate,vetml,veuser,record_type;;
	String post_ind,cash_close,day_close,month_close,date,yearclose;
	String modcode,moddesc,modabbr,lstacno,maxren,minbal,minprd,othrprop,lnkshr,maxamt;
	String fromday,today,trnspermth,lstdate,intrate,stdinst,maxrendays,renintrate,penrate,passbklines,commrateamt,maxcommrate;
	String lstvchscrlno,chqvldprd,minamtchq,minamtclg,maxamtclg,maxamtchq,topmargin,lines,botmargin,lsttrfscrlno,lstrctno,lstvchno,insprd,lncode;
	
	public String[] getTableColumnNames() {
		return this.tableColumnNames;
	}
	public void setTableColumnNames(String[] tableColumnNames) {
		this.tableColumnNames = tableColumnNames;
	}

	public String getAcType() {
		return this.acType;
	}
	public void setAcType(String acType) {
		this.acType = acType;
	}

	public String getCrDr() {
		return this.crDr;
	}
	public void setCrDr(String crDr) {
		this.crDr = crDr;
	}

	public String getGlCode() {
		return this.glCode;
	}
	public void setGlCode(String glCode) {
		this.glCode = glCode;
	}

	public int getMultBy() {
		return this.multBy;
	}
	public void setMultBy(int multBy) {
		this.multBy = multBy;
	}

	public String getTrnType() {
		return this.trnType;
	}
	public void setTrnType(String trnType) {
		this.trnType = trnType;
	}

	public String getDeDate() {
		return this.deDate;
	}
	public void setDeDate(String deDate) {
		this.deDate = deDate;
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
	
	public String getGlType() {
		return this.glType;
	}
	public void setGlType(String glType) {
		this.glType = glType;
	}
	
	public String getGlName() {
		return this.glName;
	}
	public void setGlName(String glName) {
		this.glName = glName;
	}

	public String getGlStatus() {
		return glStatus;
	}
	public void setGlStatus(String glStatus) {
		this.glStatus = glStatus;
	}
	
	public String getNormalCD() {
		return this.normalCD;
	}
	public void setNormalCD(String normalCD) {
		this.normalCD = normalCD;
	}

	public String getShType() {
		return this.shType;
	}
	public void setShType(String shType) {
		this.shType = shType;
	}

	public String getVoucherStatus() {
		return this.voucherStatus;
	}
	public void setVoucherStatus(String voucherStatus) {
		this.voucherStatus = voucherStatus;
	}

	public int getCode() {
		return this.code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	public String getKeyDesc() {
		return this.keyDesc;
	}
	public void setKeyDesc(String keyDesc) {
		this.keyDesc = keyDesc;
	}
	
	public String getTrnDesc() {
		return this.trnDesc;
	}
	public void setTrnDesc(String trnDesc) {
		this.trnDesc = trnDesc;
	}
	
	 // Code Added By Sanjeet...
	
	public String getAddr() {
		return this.addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getBank_name() {
		return this.Bank_name;
	}
	public void setBank_name(String bank_name) {
		this.Bank_name = bank_name;
	}
	public String getTrn_date() {
		return this.trn_date;
	}
	public void setTrn_date(String trn_date) {
		this.trn_date = trn_date;
	}
	public double getCash_cr() {
		return this.cash_cr;
	}
	public void setCash_cr(double cash_cr) {
		this.cash_cr = cash_cr;
	}
	public double getCash_dr() {
		return this.cash_dr;
	}
	public void setCash_dr(double cash_dr) {
		this.cash_dr = cash_dr;
	}
	public double getCg_cr() {
		return this.cg_cr;
	}
	public void setCg_cr(double cg_cr) {
		this.cg_cr = cg_cr;
	}
	public double getCg_dr() {
		return this.cg_dr;
	}
	public void setCg_dr(double cg_dr) {
		this.cg_dr = cg_dr;
	}
	public double getTrf_cr() {
		return this.trf_cr;
	}
	public void setTrf_cr(double trf_cr) {
		this.trf_cr = trf_cr;
	}
	public double getTrf_dr() {
		return this.trf_dr;
	}
	public void setTrf_dr(double trf_dr) {
		this.trf_dr = trf_dr;
	}
	public double getCredit_sum() {
		return this.credit_sum;
	}
	public void setCredit_sum(double credit_sum) {
		this.credit_sum = credit_sum;
	}
	public double getDebit_sum() {
		return this.debit_sum;
	}
	public void setDebit_sum(double debit_sum) {
		this.debit_sum = debit_sum;
	}
	public int getBr_code() {
		return this.br_code;
	}
	public void setBr_code(int br_code) {
		this.br_code = br_code;
	}
	public String getBr_name() {
		return this.br_name;
	}
	public void setBr_name(String br_name) {
		this.br_name = br_name;
	}
	public String getFrom_dt() {
		return this.from_dt;
	}
	
	public void setFrom_dt(String from_dt) {
		this.from_dt = from_dt;
	}
	
	public String getTo_dt() {
		return this.to_dt;
	}
	
	public void setTo_dt(String to_dt) {
		this.to_dt = to_dt;
	}
	public String getGLTypeAbbr(){return this.gltypeabbr;}
	public void setGLTypeAbbr(String gltypeabbr){this.gltypeabbr=gltypeabbr;}
	
	public String getVeTml(){return this.vetml;}
	public void setVeTml(String vetml){this.vetml=vetml;}
	
	public String getVeUser(){return this.veuser;}
	public void setVeUser(String veuser){this.veuser=veuser;}
	
	public  String getVeDate(){return this.vedate;}
	public void setVeDate(String vedate){this.vedate=vedate;}
	
	public  String getRecordType(){return this.record_type;}
	public void setRecordType(String record_type){this.record_type=record_type;}
	
	public  String getDate(){return this.date;}
	public void setDate(String date){this.date=date;}
	
	public  String getCashClose(){return this.cash_close;}
	public void setCashClose(String cash_close){this.cash_close=cash_close;}

	public  String getDayClose(){return this.day_close;}
	public void setDayClose(String day_close){this.day_close=day_close;}
	
	public  String getMonthClose(){return this.month_close;}
	public void setMonthClose(String month_close){this.month_close=month_close;}
	
	public  String getPostInd(){return this.post_ind;}
	public void setPostInd(String post_ind){this.post_ind=post_ind;}

	public  String getModCode(){return this.modcode;}
	public void setModCode(String modcode){this.modcode=modcode;}
	
	public  String getModDesc(){return this.moddesc;}
	public void setModDesc(String moddesc){this.moddesc=moddesc;}
	
	public  String getModAbbr(){return this.modabbr;}
	public void setModAbbr(String modabbr){this.modabbr=modabbr;}
	
	public  String getLstAccNO(){return this.lstacno;}
	public void setLstAccNO(String lstacno){this.lstacno=lstacno;}
	
	public  String getMaxRen(){return this.maxren;}
	public void setMaxRen(String maxren){this.maxren=maxren;}
	
	public  String getMinBal(){return this.minbal;}
	public void setMinBal(String minbal){this.minbal=minbal;}
	
	public  String getMinPeriod(){return this.minprd;}
	public void setMinPeriod(String minprd){this.minprd=minprd;}
	
	public  String getOtherProp(){return this.othrprop;}
	public void setOtherProp(String othrprop){this.othrprop=othrprop;}
	
	public  String getLnkShares(){return this.lnkshr;}
	public void setLnkShares(String lnkshr){this.lnkshr=lnkshr;}

	public  String getMaxAmt(){return this.maxamt;}
	public void setMaxAmt(String maxamt){this.maxamt=maxamt;}
	
	public  String getFromDay(){return this.fromday;}
	public void setFromDay(String fromday){this.fromday=fromday;}
	
	public  String getToDay(){return this.today;}
	public void setToDay(String today){this.today=today;}
	
	public  String getTransPerMth(){return this.trnspermth;}
	public void setTransPerMth(String trnspermth){this.trnspermth=trnspermth;}
	
	public  String getLstDate(){return this.lstdate;}
	public void setLstDate(String lstdate){this.lstdate=lstdate;}

	public  String getIntRate(){return this.intrate;}
	public void setIntRate(String intrate){this.intrate=intrate;}
	
	public  String getStdInst(){return this.stdinst;}
	public void setStdInst(String stdinst){this.stdinst=stdinst;}
	
	public  String getMaxRenDays(){return this.maxrendays;}
	public void setMaxRenDays(String maxrendays){this.maxrendays=maxrendays;}
	
	public  String getRenIntRate(){return this.renintrate;}
	public void setRenIntRate(String renintrate){this.renintrate=renintrate;}
	
	public  String getPenRate(){return this.penrate;}
	public void setPenRate(String penrate){this.penrate=penrate;}
	
	public  String getPassBkLines(){return this.passbklines;}
	public void setPassBkLines(String passbklines){this.passbklines=passbklines;}
	
	public  String getCommRate(){return this.commrateamt;}
	public void setCommRate(String commrateamt){this.commrateamt=commrateamt;}
	
	public  String getMaxCommRate(){return this.maxcommrate;}
	public void setMaxCommRate(String maxcommrate){this.maxcommrate=maxcommrate;}
	
	public  String getVchScrlNo(){return this.lstvchscrlno;}
	public void setVchScrlNo(String lstvchscrlno){this.lstvchscrlno=lstvchscrlno;}
	
	public  String getChqValidPrd(){return this.chqvldprd;}
	public void setChqValidPrd(String chqvldprd){this.chqvldprd=chqvldprd;}
	
	public  String getMinAmtChq(){return this.minamtchq;}
	public void setMinAmtChq(String minamtchq){this.minamtchq=minamtchq;}
	
	public  String getMinAmtClg(){return this.minamtclg;}
	public void setMinAmtClg(String minamtclg){this.minamtclg=minamtclg;}
	
	public  String getMaxAmtChq(){return this.maxamtchq;}
	public void setMaxAmtChq(String maxamtchq){this.maxamtchq=maxamtchq;}
	
	public  String getMaxAmtClg(){return this.maxamtclg;}
	public void setMaxAmtClg(String maxamtclg){this.maxamtclg=maxamtclg;}
	
	public  String getTopMargin(){return this.topmargin;}
	public void setTopMargin(String topmargin){this.topmargin=topmargin;}
	
	public  String getLinesPerPage(){return this.lines;}
	public void setLinesPerPage(String lines){this.lines=lines;}
	
	public  String getBottomMargin(){return this.botmargin;}
	public void setBottomMargin(String botmargin){this.botmargin=botmargin;}
	
	public  String getLstTrfScrlNo(){return this.lsttrfscrlno;}
	public void setLstTrfScrlNo(String lsttrfscrlno){this.lsttrfscrlno=lsttrfscrlno;}
	
	public  String getLstRectNo(){return this.lstrctno;}
	public void setLstRectNo(String lstrctno){this.lstrctno=lstrctno;}
	
	public  String getLstVchNo(){return this.lstvchno;}
	public void setLstVchNo(String lstvchno){this.lstvchno=lstvchno;}
	
	public  String getInsPrd(){return this.insprd;}
	public void setInsPrd(String insprd){this.insprd=insprd;}
	
	public  String getLnCode(){return this.lncode;}
	public void setLnCode(String lncode){this.lncode=lncode;}

	public  String getYearClose(){return this.yearclose;}
	public void setYearClose(String yearclose){this.yearclose=yearclose;}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
}
