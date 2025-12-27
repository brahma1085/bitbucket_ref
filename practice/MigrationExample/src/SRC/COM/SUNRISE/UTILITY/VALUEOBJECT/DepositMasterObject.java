package SRC.COM.SUNRISE.UTILITY.VALUEOBJECT;

import java.util.Hashtable;
import java.util.Vector;

public class DepositMasterObject {

	private String ac_type,  dep_date,mat_date,  next_pay_date, mat_post,post_date,name;
	private String intr_ac_type , rcvd_by, rcvd_ac_type , int_freq,int_mode,trf_ac_type,  int_upto_date ;
	
	private String   ln_availed ,ln_ac_type,  dep_renewed  ,new_rct,  rct_prtd , rct_sign , auto_renewal ;
	private String   close_date , ren_ac_type ,  ldadjreq,  ldgprtdt,  int_paid_date,  de_tml  ,de_user,  de_date ,ve_tml,  ve_user,  ve_date;             
	
	private int ac_no ,cid, add_type, no_jt_hldr, dep_yrs, dep_mths  ,dep_days, intr_ac_no , nom_no , extra_rate_type ,rcvd_ac_no;
	private int trf_ac_no,lst_trn_seq ,ln_ac_no ,rct_no , ren_ac_no ,autorenewal_no,lst_pr_seq ,close_ind ;
	
	private double  mat_amt,exc_amt ,int_rate , dep_amt ;
	
	private String trn_date;
	
	private Vector vec_jointholder;
	
	Hashtable trn_seq;
	

	public int getAc_no() {
		return ac_no;
	}

	public void setAc_no(int ac_no) {
		this.ac_no = ac_no;
	}

	public String getAc_type() {
		return ac_type;
	}

	public void setAc_type(String ac_type) {
		this.ac_type = ac_type;
	}

	public int getAdd_type() {
		return add_type;
	}

	public void setAdd_type(int add_type) {
		this.add_type = add_type;
	}

	public String getAuto_renewal() {
		return auto_renewal;
	}

	public void setAuto_renewal(String auto_renewal) {
		this.auto_renewal = auto_renewal;
	}

	public int getAutorenewal_no() {
		return autorenewal_no;
	}

	public void setAutorenewal_no(int autorenewal_no) {
		this.autorenewal_no = autorenewal_no;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getClose_date() {
		return close_date;
	}

	public void setClose_date(String close_date) {
		this.close_date = close_date;
	}

	public int getClose_ind() {
		return close_ind;
	}

	public void setClose_ind(int close_ind) {
		this.close_ind = close_ind;
	}

	public String getDe_date() {
		return de_date;
	}

	public void setDe_date(String de_date) {
		this.de_date = de_date;
	}

	public String getDe_tml() {
		return de_tml;
	}

	public void setDe_tml(String de_tml) {
		this.de_tml = de_tml;
	}

	public String getDe_user() {
		return de_user;
	}

	public void setDe_user(String de_user) {
		this.de_user = de_user;
	}

	public double getDep_amt() {
		return dep_amt;
	}

	public void setDep_amt(double dep_amt) {
		this.dep_amt = dep_amt;
	}

	public String getDep_date() {
		return dep_date;
	}

	public void setDep_date(String dep_date) {
		this.dep_date = dep_date;
	}

	public int getDep_days() {
		return dep_days;
	}

	public void setDep_days(int dep_days) {
		this.dep_days = dep_days;
	}

	public int getDep_mths() {
		return dep_mths;
	}

	public void setDep_mths(int dep_mths) {
		this.dep_mths = dep_mths;
	}

	public String getDep_renewed() {
		return dep_renewed;
	}

	public void setDep_renewed(String dep_renewed) {
		this.dep_renewed = dep_renewed;
	}

	public int getDep_yrs() {
		return dep_yrs;
	}

	public void setDep_yrs(int dep_yrs) {
		this.dep_yrs = dep_yrs;
	}

	public double getExc_amt() {
		return exc_amt;
	}

	public void setExc_amt(double exc_amt) {
		this.exc_amt = exc_amt;
	}

	public int getExtra_rate_type() {
		return extra_rate_type;
	}

	public void setExtra_rate_type(int extra_rate_type) {
		this.extra_rate_type = extra_rate_type;
	}

	public String getInt_freq() {
		return int_freq;
	}

	public void setInt_freq(String int_freq) {
		this.int_freq = int_freq;
	}

	public String getInt_mode() {
		return int_mode;
	}

	public void setInt_mode(String int_mode) {
		this.int_mode = int_mode;
	}

	public String getInt_paid_date() {
		return int_paid_date;
	}

	public void setInt_paid_date(String int_paid_date) {
		this.int_paid_date = int_paid_date;
	}

	public double getInt_rate() {
		return int_rate;
	}

	public void setInt_rate(double int_rate) {
		this.int_rate = int_rate;
	}

	public String getInt_upto_date() {
		return int_upto_date;
	}

	public void setInt_upto_date(String int_upto_date) {
		this.int_upto_date = int_upto_date;
	}

	public int getIntr_ac_no() {
		return intr_ac_no;
	}

	public void setIntr_ac_no(int intr_ac_no) {
		this.intr_ac_no = intr_ac_no;
	}

	public String getIntr_ac_type() {
		return intr_ac_type;
	}

	public void setIntr_ac_type(String intr_ac_type) {
		this.intr_ac_type = intr_ac_type;
	}

	public String getLdadjreq() {
		return ldadjreq;
	}

	public void setLdadjreq(String ldadjreq) {
		this.ldadjreq = ldadjreq;
	}

	public String getLdgprtdt() {
		return ldgprtdt;
	}

	public void setLdgprtdt(String ldgprtdt) {
		this.ldgprtdt = ldgprtdt;
	}

	public int getLn_ac_no() {
		return ln_ac_no;
	}

	public void setLn_ac_no(int ln_ac_no) {
		this.ln_ac_no = ln_ac_no;
	}

	public String getLn_ac_type() {
		return ln_ac_type;
	}

	public void setLn_ac_type(String ln_ac_type) {
		this.ln_ac_type = ln_ac_type;
	}

	public String getLn_availed() {
		return ln_availed;
	}

	public void setLn_availed(String ln_availed) {
		this.ln_availed = ln_availed;
	}

	public int getLst_pr_seq() {
		return lst_pr_seq;
	}

	public void setLst_pr_seq(int lst_pr_seq) {
		this.lst_pr_seq = lst_pr_seq;
	}

	public int getLst_trn_seq() {
		return lst_trn_seq;
	}

	public void setLst_trn_seq(int lst_trn_seq) {
		this.lst_trn_seq = lst_trn_seq;
	}

	public double getMat_amt() {
		return mat_amt;
	}

	public void setMat_amt(double mat_amt) {
		this.mat_amt = mat_amt;
	}

	public String getMat_date() {
		return mat_date;
	}

	public void setMat_date(String mat_date) {
		this.mat_date = mat_date;
	}

	public String getMat_post() {
		return mat_post;
	}

	public void setMat_post(String mat_post) {
		this.mat_post = mat_post;
	}

	public String getNew_rct() {
		return new_rct;
	}

	public void setNew_rct(String new_rct) {
		this.new_rct = new_rct;
	}

	public String getNext_pay_date() {
		return next_pay_date;
	}

	public void setNext_pay_date(String next_pay_date) {
		this.next_pay_date = next_pay_date;
	}

	public int getNo_jt_hldr() {
		return no_jt_hldr;
	}

	public void setNo_jt_hldr(int no_jt_hldr) {
		this.no_jt_hldr = no_jt_hldr;
	}

	public int getNom_no() {
		return nom_no;
	}

	public void setNom_no(int nom_no) {
		this.nom_no = nom_no;
	}

	public String getPost_date() {
		return post_date;
	}

	public void setPost_date(String post_date) {
		this.post_date = post_date;
	}

	public int getRct_no() {
		return rct_no;
	}

	public void setRct_no(int rct_no) {
		this.rct_no = rct_no;
	}

	public String getRct_prtd() {
		return rct_prtd;
	}

	public void setRct_prtd(String rct_prtd) {
		this.rct_prtd = rct_prtd;
	}

	public String getRct_sign() {
		return rct_sign;
	}

	public void setRct_sign(String rct_sign) {
		this.rct_sign = rct_sign;
	}

	public int getRcvd_ac_no() {
		return rcvd_ac_no;
	}

	public void setRcvd_ac_no(int rcvd_ac_no) {
		this.rcvd_ac_no = rcvd_ac_no;
	}

	public String getRcvd_ac_type() {
		return rcvd_ac_type;
	}

	public void setRcvd_ac_type(String rcvd_ac_type) {
		this.rcvd_ac_type = rcvd_ac_type;
	}

	public String getRcvd_by() {
		return rcvd_by;
	}

	public void setRcvd_by(String rcvd_by) {
		this.rcvd_by = rcvd_by;
	}

	public int getRen_ac_no() {
		return ren_ac_no;
	}

	public void setRen_ac_no(int ren_ac_no) {
		this.ren_ac_no = ren_ac_no;
	}

	public String getRen_ac_type() {
		return ren_ac_type;
	}

	public void setRen_ac_type(String ren_ac_type) {
		this.ren_ac_type = ren_ac_type;
	}

	public int getTrf_ac_no() {
		return trf_ac_no;
	}

	public void setTrf_ac_no(int trf_ac_no) {
		this.trf_ac_no = trf_ac_no;
	}

	public String getTrf_ac_type() {
		return trf_ac_type;
	}

	public void setTrf_ac_type(String trf_ac_type) {
		this.trf_ac_type = trf_ac_type;
	}

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

	public Vector getVec_jointholder() {
		return vec_jointholder;
	}

	public void setVec_jointholder(Vector vec_jointholder) {
		this.vec_jointholder = vec_jointholder;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Hashtable getTrn_seq() {
		return trn_seq;
	}

	public void setTrn_seq(Hashtable trn_seq) {
		this.trn_seq = trn_seq;
	}

	
	
}
