package masterObject.frontCounter;
import java.io.Serializable;

import masterObject.general.Address;
import masterObject.general.UserVerifier;

public class AccountTransObject implements Serializable
{

	public UserVerifier uv=new UserVerifier();
	public Address addr=new Address();
	String acc_ty,trn_ty,trn_mode,trn_date,chq_dd_date,trn_source,cd_ind,transNarr,payee_nm,int_date;
	int ac_no,accNo,ch_dd_no,ref_no,ldg_page,trn_seq_no,last_seq_no;
	double transAmount,cl_bal,int_amt;
	String name;
	int gl_code;

	int ps_bk_seq;
	private double pcl_bal;
    private String open_date;

//Accounttransaction table
	public String getAccType(){return acc_ty;}
	public String getName(){return name;}
	
	public int getAccNo(){return accNo;}
	public int  getPassBkSeq(){return ps_bk_seq;}
	public void  setPassBkSeq(int ps_bk_seq){this.ps_bk_seq=ps_bk_seq;}
	public String getTransType(){return trn_ty;}
	public String getTransMode(){return trn_mode;}
	public String getTransDate(){return trn_date;}
	public String getChqDDDate(){return chq_dd_date;}
	public String getTransSource(){return trn_source;}
	public String getCdInd(){return cd_ind;}
	public String getTransNarr(){return transNarr;}
	public String getPayeeName(){return payee_nm;}

	public int getChqDDNo(){return ch_dd_no;}
	public int getRef_No(){return ref_no;}
	public int getLedgerPage(){return ldg_page;}
	public int getTransSeqNo(){return trn_seq_no;}
	
	public int getGLRefCode(){return gl_code;}
	public void setGLRefCode(int code){this.gl_code=code;}

	public double getTransAmount(){return transAmount;}
	public double getCloseBal(){return cl_bal;}



//Account Transaction table
	public void setAccType(String acc_ty){this.acc_ty=acc_ty;}
	public void setName(String name){this.name=name;}
	public void setAccNo(int acno){accNo=acno;}
	public void setTransType(String trn_ty){this.trn_ty=trn_ty;}
	public void setTransMode(String trn_mode){this.trn_mode=trn_mode;}
	public void setTransDate(String trn_date){this.trn_date=trn_date;}
	public void setChqDDDate(String chq_dd_date){this.chq_dd_date=chq_dd_date;}
	public void setTransSource(String trn_source){this.trn_source=trn_source;}
	public void setCdInd(String cd_ind){this.cd_ind=cd_ind;}
	public void setTransNarr(String trn_narr){this.transNarr=trn_narr;}
	public void setPayeeName(String payee_nm){this.payee_nm=payee_nm;}

	public void setChqDDNo(int ch_dd_no){this.ch_dd_no=ch_dd_no;}
	public void setRef_No(int ref_no){this.ref_no=ref_no;}
	public void setLedgerPage(int ldg_page){this.ldg_page=ldg_page;}
	public void setTransSeqNo(int trn_seq_no){this.trn_seq_no=trn_seq_no;}

	public void setTransAmount(double trn_amount){this.transAmount=trn_amount;}
	public void setCloseBal(double cl_bal){this.cl_bal=cl_bal;}
	
	public void setPreCloseBal(double pcl_bal){this.pcl_bal=pcl_bal;}
	public double getPreCloseBal(){return pcl_bal;}

	public void setAcOpenDate(String string) {
	    this.open_date=string;
    }


	public String getAcOpenDate(){return open_date;}
	public String getInt_date() {
		return int_date;
	}
	public void setInt_date(String int_date) {
		this.int_date = int_date;
	}
	public double getInt_amt() {
		return int_amt;
	}
	public void setInt_amt(double int_amt) {
		this.int_amt = int_amt;
	}
	public int getLast_seq_no() {
		return last_seq_no;
	}
	public void setLast_seq_no(int last_seq_no) {
		this.last_seq_no = last_seq_no;
	}
	public UserVerifier getUv() {
		return uv;
	}
	public void setUv(UserVerifier uv) {
		this.uv = uv;
	}
}
