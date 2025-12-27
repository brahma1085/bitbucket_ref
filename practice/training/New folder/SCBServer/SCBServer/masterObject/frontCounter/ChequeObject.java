
package masterObject.frontCounter;

import java.io.Serializable;
import masterObject.general.UserVerifier;

public class ChequeObject implements Serializable
{
	public UserVerifier uv=new UserVerifier();

	String ac_ty,chq_date;
	String exp_date,next_chqdate,post_dated,chq_del,stop_pymnt,chq_issuedate,chq_payee,alt_de_dt,alt_de_time,request_dt,alt_de_user,chq_cancel,ve_tml,ve_user,ve_date;
	String modty;
	int acc_no,backup,f_chq_prev,book_no,fst_chq_no,lst_chq_no,no_leaf,no_bounce,trn_key,chqno;
	int token_no,vstarted, sum;
	String trn_date,cash_pd,stop_user,trn_ty,trn_mode,tml_no,payee_nm;
	double chq_amnt,trn_amt;
	public int chq_no[];

	private int cid;
	public ChequeObject()
	{

		chq_no=new int[20];

	}

//ChequeNO Table
	public String getModuleType(){return modty;}
	public String getAccType(){return ac_ty;}
	public String getChqDate(){return chq_date;}
	public String getExpDate(){return exp_date;}
	public String getNext_ChequeDate(){return next_chqdate;}
	public String getCheque_IssueDate(){return chq_issuedate;}
	public String getCheque_Payee(){return chq_payee;}
	public String getAlt_De_Date(){return alt_de_dt;}
	public String getAlt_De_Time(){return alt_de_time;}

	public int getAccNo(){return acc_no;}
	public String getPost_Dated(){return post_dated;}
	public String getStop_payment(){return stop_pymnt;}
	public String getStop_User(){return stop_user;}
	public String getCheque_Del(){return chq_del;}
	public String getCheque_Cancelled(){return chq_cancel;}
	public String getAlt_De_User(){return alt_de_user;}
	public int getSum(){return sum;}

	public double getChequeAmount(){return chq_amnt;}

//ChequeBook
	
	public int getF_Chq_Prev(){return f_chq_prev;}
	public int getBookNo(){return book_no;}
	public int getFirstChequeNo(){return fst_chq_no;}
	public int getLastChequeNo(){return lst_chq_no;}
	public int getNumLeaf(){return no_leaf;}
	public int getNumBounce(){return no_bounce;}

	public String getRequestDate(){return request_dt;}
	public int  getTrnKey(){return trn_key;}
	public int getBackUp(){return backup;}
	public int getChqNo(){return chqno;}
	public int getChqNo(int index){return chq_no[index];}
	public int getChqNoCount(){return chq_no.length;}

//cheque withdrawal

	public int getTokenNo(){return token_no;}
	public String getCashPD(){return cash_pd;}
	public int getVstarted(){return vstarted;}

	public double getTransAmount(){return trn_amt;}

	public String getTransDate(){return trn_date;}
	public String getTransType(){return trn_ty;}
	public String getTransMode(){return trn_mode;}
	public String getTerminalNo(){return tml_no;}
	public String getPayeeName(){return payee_nm;}


//get
 //ChequeNo Table
	public void setModuleType(String ty){this.modty=ty;}
	public void setChqDate(String chq_date){this.chq_date=chq_date;}
	public void setAccType(String ac_ty){this.ac_ty=ac_ty;}
	public void setChqNo(int chq_no,int index){this.chq_no[index]=chq_no;}

	public void setChqNo(int chqno){this.chqno=chqno;}

	public void setChqNoCount(int chq_no){this.chq_no=new int[chq_no];}
	public void setExpDate(String exp_date){this.exp_date=exp_date;}
	public void setNext_ChequeDate(String next_chqdate){this.next_chqdate=next_chqdate;}
	public void setCheque_IssueDate(String chq_issuedate){this.chq_issuedate=chq_issuedate;}
	public void setCheque_Payee(String chq_payee){this.chq_payee=chq_payee;}
	public void setAlt_De_Date(String alt_de_dt){this.alt_de_dt=alt_de_dt;}
	public void setAlt_De_Time(String alt_de_time){this.alt_de_time=alt_de_time;}


	public void setPost_Dated(String post_dated){this.post_dated=post_dated;}
	public void setStop_payment(String stop_pymnt){this.stop_pymnt=stop_pymnt;}
	public void setStop_User(String stop_user){this.stop_user=stop_user;}
	public void setCheque_Cancelled(String chq_del){this.chq_cancel=chq_del;}
	public void setCheque_Del(String chq_del){this.chq_del=chq_del;}
	public void setAlt_De_User(String alt_de_user){this.alt_de_user=alt_de_user;}
	public void setAccNo(int acc_no){this.acc_no=acc_no;}
	public void setChequeAmount(double chq_amnt){this.chq_amnt=chq_amnt;}
//ChequeBook

	public void setF_Chq_Prev(int f_chq_prev){this.f_chq_prev=f_chq_prev;}
	public void setBookNo(int book_no){this.book_no=book_no;}
	public void setFirstChequeNo(int fst_chq_no){this.fst_chq_no=fst_chq_no;}
	public void setLastChequeNo(int lst_chq_no){this.lst_chq_no=lst_chq_no;}
	public void setNumLeaf(int no_leaf){this.no_leaf=no_leaf;}
	public void setNumBounce(int no_bounce){this.no_bounce=no_bounce;}

	public void setRequestDate(String request_dt){this.request_dt=request_dt;}
	public void  setTrnKey(int trn_key){this.trn_key=trn_key;}
	public void setBackUp(int backup){this.backup=backup;}

//Cheque withdrawal

	public void setTokenNo(int token_no){this.token_no=token_no;}
	public void setCashPD(String cash_pd){this.cash_pd=cash_pd;}
	public void setVstarted(int vstarted){this.vstarted=vstarted;}

	public void setTransAmount(double trn_amt){this.trn_amt=trn_amt;}

	public void setTransDate(String trn_date){this.trn_date=trn_date;}
	public void setTransType(String trn_ty){this.trn_ty=trn_ty;}
	public void setTransMode(String trn_mode){this.trn_mode=trn_mode;}
	public void setTerminalNo(String tml_no){this.tml_no=tml_no;}
	public void setPayeeName(String payee_nm){this.payee_nm=payee_nm;}
	public void setSum(int sum){this.sum=sum;}

	public void setCustomerId(int int1) {
		this.cid=int1;
		
	}
	
	public int getCustomerId(){return cid;}

	public String getVe_date() {
		return ve_date;
	}

	public void setVe_date(String ve_date) {
		this.ve_date = ve_date;
	}

	public String getVe_tml() {
		return ve_tml;
	}

	public void setVe_tml(String ve_tml) {
		this.ve_tml = ve_tml;
	}

	public String getVe_user() {
		return ve_user;
	}

	public void setVe_user(String ve_user) {
		this.ve_user = ve_user;
	}
	

}
