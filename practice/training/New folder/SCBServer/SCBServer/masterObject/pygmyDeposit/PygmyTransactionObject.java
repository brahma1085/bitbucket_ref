package masterObject.pygmyDeposit;
public class PygmyTransactionObject implements java.io.Serializable
{
    private int ref_ind,accNo,trnseq,refno;
    private String to_colldt,to_trndt,accType,tranDate,trnty,trnmode,trnsou,CDInd,tranNarration,collectionDate,intrfrom,name,ve_tml,recvFrom;
    private double debAmt,crAmt,tranAmt,amtpd,closeBal,prnpd,intrpd,prevBalance,total_remittance,bforward;

    
	public int getAccNo(){return accNo;}

	public String getAccType(){return accType;}

	public double getAmtPaid(){return amtpd;}

	public String getCDInd(){return CDInd;}

	public double getCloseBal(){return closeBal;}

	public String getCollectionDate(){return collectionDate;}

	public String getInterestFrom(){return intrfrom;}

	public double getInterestPaid(){return intrpd;}

	public double getPrnPaid(){return prnpd;}//principal paid

	public int getRefNo(){return refno;}

	public double getTranAmt(){return tranAmt;}

	public String getTranDate(){return tranDate;}

	public String getTranMode(){return trnmode;}

	public String getTranNarration(){return tranNarration;}

	public int getTranSeq(){return trnseq;}

	public String getTranSource(){return trnsou;}

	public String getTranType(){return trnty;}
	public String getName(){return name;}
	public double getPrevBalance(){return prevBalance;}
	public String getVe_tml(){return ve_tml;}
	
	
	public void setAccNo(int acno){this.accNo=acno ;}
	public void setAccType(String acty){this.accType=acty ;}
	public void setAmtPaid(double amtpd){this.amtpd=amtpd ;}
	public void setCDInd(String cdind){this.CDInd=cdind ;}
	public void setCloseBal(double clbal){this.closeBal=clbal ;}
	public void setCollectionDate(String colldt){this.collectionDate=colldt ;}
	public void setInterestFrom(String intrfrom){this.intrfrom=intrfrom ;}
	public void setInterestPaid(double intrpd){this.intrpd=intrpd ;}
	public void setPrnPaid(double prnpd){this.prnpd=prnpd;}
	public void setRefNo(int refno){this.refno=refno ;}
	public void setTranAmt(double trnamt){this.tranAmt=trnamt ;}
	public void setTranDate(String trndt){this.tranDate=trndt ;}
	public void setTranMode(String trnmode){this.trnmode=trnmode ;}
	public void setTranNarration(String trnnarr){this.tranNarration=trnnarr ;}
	public void setTranSeq(int trnseq){this.trnseq=trnseq ;}
	public void setTranSource(String trnsou){this.trnsou=trnsou ;}
	public void setTranType(String trnty){this.trnty=trnty ;}
    public void setName(String name){this.name=name;}
    public void setPrevBalance(double prev_balance){this.prevBalance=prev_balance;}
    public void setVe_tml(String ve_tml){this.ve_tml=ve_tml;}

	public double getTotal_remittance() {
		return total_remittance;
	}
	public void setTotal_remittance(double total_remittance) {
		this.total_remittance = total_remittance;
	}
	public String getTo_colldt() {
		return to_colldt;
	}
	public void setTo_colldt(String to_colldt) {
		this.to_colldt = to_colldt;
	}
	public String getTo_trndt() {
		return to_trndt;
	}
	public void setTo_trndt(String to_trndt) {
		this.to_trndt = to_trndt;
	}
	public int getRef_ind() {
		return ref_ind;
	}
	public void setRef_ind(int ref_ind) {
		this.ref_ind = ref_ind;
	}
    public void setRecvFrom(String recvfrom){
        this.recvFrom=recvfrom;
    }
    public String getRecvFrom(){
        return recvFrom;
    }

	public double getBforward() {
		return bforward;
	}

	public void setBforward(double bforward) {
		this.bforward = bforward;
	}

	public double getDebAmt() {
		return debAmt;
	}

	public void setDebAmt(double debAmt) {
		this.debAmt = debAmt;
	}

	public double getCrAmt() {
		return crAmt;
	}

	public void setCrAmt(double crAmt) {
		this.crAmt = crAmt;
	}

	
}
