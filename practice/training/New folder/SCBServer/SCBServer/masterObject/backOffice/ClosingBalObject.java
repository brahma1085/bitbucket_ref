package masterObject.backOffice;

import java.io.*;

public class ClosingBalObject implements Serializable {
	private static final long serialVersionUID = 1L;
	private double credit, debit, cl_bal, open_bal, interest, additional,
			withdrawal;
	private String name, sh_ind, int_date;
	private int ac_no, pg_no, agt_no;
	private double intop_bal, intcl_bal, int_acd, int_paid, net_clbal;
	private double int_recd = 0, pint_recd = 0, debit_amt, recy;
	private int glcode, flag;
	private String ac_ty, ac_type, trn_dt, trn_ty, trn_seq;
	private String gl_name, mem_cat, sus_ind;
	private double payment_amt = 0.0, reciept_amt = 0.0, close_bal = 0.0,
			openbal = 0.0;
	transient int mem_val;
	private double cash_cr, cash_dr, trf_cr, trf_dr, cheque_cr, cheque_dr;

	public void setAcNo(int ac_no) {
		this.ac_no = ac_no;
	}

	public int getAcNo() {
		return ac_no;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setOpenBal(double open_bal) {
		this.open_bal = open_bal;
	}

	public double getOpenBal() {
		return open_bal;
	}

	public void setClBal(double cl_bal) {
		this.cl_bal = cl_bal;
	}

	public double getClBal() {
		return cl_bal;
	}

	public void setCreditAmt(double credit) {
		this.credit = credit;
	}

	public double getCreditAmt() {
		return credit;
	}

	public void setDebitAmt(double debit) {
		this.debit = debit;
	}

	public double getDebitAmt() {
		return debit;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public double getInterest() {
		return interest;
	}

	public void setAdditionalAmt(double additional) {
		this.additional = additional;
	}

	public double getAdditionalAmt() {
		return additional;
	}

	public void setWithdrawalAmt(double withdrawal) {
		this.withdrawal = withdrawal;
	}

	public double getWithdrawalAmt() {
		return withdrawal;
	}

	public void setShareInd(String sh_ind) {
		this.sh_ind = sh_ind;
	}

	public String getShareInd() {
		return sh_ind;

	}

	public void setPgAcNo(int pg_no) {
		this.pg_no = pg_no;
	}

	public int getPgAcNo() {
		return pg_no;
	}

	public void setIntOpenBal(double intop_bal) {
		this.intop_bal = intop_bal;
	}

	public double getIntOpenBal() {
		return intop_bal;
	}

	public void setIntClBal(double intcl_bal) {
		this.intcl_bal = intcl_bal;
	}

	public double getIntClBal() {
		return intcl_bal;
	}

	public void setIntAcd(double int_acd) {
		this.int_acd = int_acd;
	}

	public double getIntAcd() {
		return int_acd;
	}

	public void setIntPaid(double int_paid) {
		this.int_paid = int_paid;
	}

	public double getIntPaid() {
		return int_paid;
	}

	public void setAgentNo(int agt_no) {
		this.agt_no = agt_no;
	}

	public int getAgentNo() {
		return agt_no;
	}

	public void setNetClBal(double net_clbal) {
		this.net_clbal = net_clbal;
	}

	public double getNetClBal() {
		return net_clbal;
	}

	public void setIntRcd(double int_recd) {
		this.int_recd = int_recd;
	}

	public double getIntRcd() {
		return int_recd;
	}

	public void setPIntRcd(double pint_recd) {
		this.pint_recd = pint_recd;
	}

	public double getPIntRcd() {
		return pint_recd;
	}

	public void setOtherAmt(double debit_amt) {
		this.debit_amt = debit_amt;
	}

	public double getOtherAmt() {
		return debit_amt;
	}

	public void setOtherRecoveryAmt(double recy) {
		this.recy = recy;
	}

	public double getOtherRecoveryAmt() {
		return recy;
	}

	public void setIntDate(String int_date) {
		this.int_date = int_date;
	}

	public String getIntDate() {
		return int_date;
	}

	public void setGLCode(int glcode) {
		this.glcode = glcode;
	}

	public int getGLCode() {
		return glcode;
	}

	public void setRefAcTy(String ac_ty) {
		this.ac_ty = ac_ty;
	}

	public String getRefAcTy() {
		return ac_ty;
	}

	public void setCash_Cr(double cash_cr) {
		this.cash_cr = cash_cr;
	}

	public double getCash_Cr() {
		return cash_cr;
	}

	public void setCash_Dr(double cash_dr) {
		this.cash_dr = cash_dr;
	}

	public double getCash_Dr() {
		return cash_dr;
	}

	public void setTransfer_Cr(double trf_cr) {
		this.trf_cr = trf_cr;
	}

	public double getTransfer_Cr() {
		return trf_cr;
	}

	public void setTransfer_Dr(double trf_dr) {
		this.trf_dr = trf_dr;
	}

	public double getTransfer_Dr() {
		return trf_dr;
	}

	public void setCheque_Cr(double cheque_cr) {
		this.cheque_cr = cheque_cr;
	}

	public double getCheque_Cr() {
		return cheque_cr;
	}

	public void setCheque_Dr(double cheque_dr) {
		this.cheque_dr = cheque_dr;
	}

	public double getCheque_Dr() {
		return cheque_dr;
	}

	public void setTrnDate(String trn_dt) {
		this.trn_dt = trn_dt;
	}

	public String getTrnDate() {
		return trn_dt;
	}

	public void setTrnTy(String trn_ty) {
		this.trn_ty = trn_ty;
	}

	public String getTrnTy() {
		return trn_ty;
	}

	public void setTrnSeq(String trn_seq) {
		this.trn_seq = trn_seq;
	}

	public String getTrnSeq() {
		return trn_seq;
	}

	public void setGLName(String gl_name) {
		this.gl_name = gl_name;
	}

	public String getGLName() {
		return gl_name;
	}

	public void setMemCat(String mem_cat) {
		this.mem_cat = mem_cat;
	}

	public String getMemCat() {
		return mem_cat;
	}

	public void setSusInd(String sus_ind) {
		this.sus_ind = sus_ind;
	}

	public String getSusInd() {
		return sus_ind;
	}

	public void setMemValue(int mem_val) {
		this.mem_val = mem_val;
	}

	public int getMemValue() {
		return mem_val;
	}

	public void setCloseBal(double close_bal) {
		this.close_bal = close_bal;
	}

	public double getCloseBal() {
		return close_bal;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getFlag() {
		return flag;
	}

	public String getAc_type() {
		return ac_type;
	}

	public void setAc_type(String ac_type) {
		this.ac_type = ac_type;
	}
}
