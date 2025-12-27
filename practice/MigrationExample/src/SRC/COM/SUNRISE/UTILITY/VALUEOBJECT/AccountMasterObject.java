package SRC.COM.SUNRISE.UTILITY.VALUEOBJECT;

public class AccountMasterObject {

	String ac_ty,ac_type,last_trndate,int_pbl_date,int_rbl_date,Intr_name,Intr_acc_type,Nom_name,Relation,ac_status,id_issue_date,ch_bk_issue,ac_opendate,ac_closedate,acc_ty,trn_ty,trn_mode,trn_source,cd_ind,trn_narr,payee_nm,trn_date,chq_dd_date,def_ind,frz_ind,frz_reason;
	int ac_no,no_jt_hldrs,last_trn_seq,ps_bk_seq,ldg_seq,intr_acc_no,od_int_rate,cc_com_rt,fr_min_bal,no_id_issue,last_line,trn_key,nom_reg_no,cid,trn_seq_no;
	int acc_no,ch_dd_no,ref_no,ldg_page,backup,acc_type,ac_category,ca_type;
	double trn_amount,cl_bal,pcl_bal;
	String ver,accname;
	String Sac_ty,name,desg,type_op;
	int addr_type;
	String photo,sign;
	int Sac_no,min_lmt,max_lmt,norows;
	int cids[];
	int j_addtype[]=null;
	public String tyop[]=null;
	int glref_code;
	
	public int getAc_category() {
		return ac_category;
	}
	public void setAc_category(int ac_category) {
		this.ac_category = ac_category;
	}
	public String getAc_closedate() {
		return ac_closedate;
	}
	public void setAc_closedate(String ac_closedate) {
		this.ac_closedate = ac_closedate;
	}
	public int getAc_no() {
		return ac_no;
	}
	public void setAc_no(int ac_no) {
		this.ac_no = ac_no;
	}
	public String getAc_opendate() {
		return ac_opendate;
	}
	public void setAc_opendate(String ac_opendate) {
		this.ac_opendate = ac_opendate;
	}
	public String getAc_status() {
		return ac_status;
	}
	public void setAc_status(String ac_status) {
		this.ac_status = ac_status;
	}
	public String getAc_ty() {
		return ac_ty;
	}
	public void setAc_ty(String ac_ty) {
		this.ac_ty = ac_ty;
	}
	public String getAc_type() {
		return ac_type;
	}
	public void setAc_type(String ac_type) {
		this.ac_type = ac_type;
	}
	public int getAcc_no() {
		return acc_no;
	}
	public void setAcc_no(int acc_no) {
		this.acc_no = acc_no;
	}
	public String getAcc_ty() {
		return acc_ty;
	}
	public void setAcc_ty(String acc_ty) {
		this.acc_ty = acc_ty;
	}
	public int getAcc_type() {
		return acc_type;
	}
	public void setAcc_type(int acc_type) {
		this.acc_type = acc_type;
	}
	public String getAccname() {
		return accname;
	}
	public void setAccname(String accname) {
		this.accname = accname;
	}
	public int getAddr_type() {
		return addr_type;
	}
	public void setAddr_type(int addr_type) {
		this.addr_type = addr_type;
	}
	public int getBackup() {
		return backup;
	}
	public void setBackup(int backup) {
		this.backup = backup;
	}
	public int getCa_type() {
		return ca_type;
	}
	public void setCa_type(int ca_type) {
		this.ca_type = ca_type;
	}
	public int getCc_com_rt() {
		return cc_com_rt;
	}
	public void setCc_com_rt(int cc_com_rt) {
		this.cc_com_rt = cc_com_rt;
	}
	public String getCd_ind() {
		return cd_ind;
	}
	public void setCd_ind(String cd_ind) {
		this.cd_ind = cd_ind;
	}
	public String getCh_bk_issue() {
		return ch_bk_issue;
	}
	public void setCh_bk_issue(String ch_bk_issue) {
		this.ch_bk_issue = ch_bk_issue;
	}
	public int getCh_dd_no() {
		return ch_dd_no;
	}
	public void setCh_dd_no(int ch_dd_no) {
		this.ch_dd_no = ch_dd_no;
	}
	public String getChq_dd_date() {
		return chq_dd_date;
	}
	public void setChq_dd_date(String chq_dd_date) {
		this.chq_dd_date = chq_dd_date;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public int[] getCids() {
		return cids;
	}
	public void setCids(int[] cids) {
		this.cids = cids;
	}
	public double getCl_bal() {
		return cl_bal;
	}
	public void setCl_bal(double cl_bal) {
		this.cl_bal = cl_bal;
	}
	public String getDef_ind() {
		return def_ind;
	}
	public void setDef_ind(String def_ind) {
		this.def_ind = def_ind;
	}
	public String getDesg() {
		return desg;
	}
	public void setDesg(String desg) {
		this.desg = desg;
	}
	public int getFr_min_bal() {
		return fr_min_bal;
	}
	public void setFr_min_bal(int fr_min_bal) {
		this.fr_min_bal = fr_min_bal;
	}
	public String getFrz_ind() {
		return frz_ind;
	}
	public void setFrz_ind(String frz_ind) {
		this.frz_ind = frz_ind;
	}
	public String getFrz_reason() {
		return frz_reason;
	}
	public void setFrz_reason(String frz_reason) {
		this.frz_reason = frz_reason;
	}
	public int getGlref_code() {
		return glref_code;
	}
	public void setGlref_code(int glref_code) {
		this.glref_code = glref_code;
	}
	public String getId_issue_date() {
		return id_issue_date;
	}
	public void setId_issue_date(String id_issue_date) {
		this.id_issue_date = id_issue_date;
	}
	public String getInt_pbl_date() {
		return int_pbl_date;
	}
	public void setInt_pbl_date(String int_pbl_date) {
		this.int_pbl_date = int_pbl_date;
	}
	public String getInt_rbl_date() {
		return int_rbl_date;
	}
	public void setInt_rbl_date(String int_rbl_date) {
		this.int_rbl_date = int_rbl_date;
	}
	public int getIntr_acc_no() {
		return intr_acc_no;
	}
	public void setIntr_acc_no(int intr_acc_no) {
		this.intr_acc_no = intr_acc_no;
	}
	public String getIntr_acc_type() {
		return Intr_acc_type;
	}
	public void setIntr_acc_type(String intr_acc_type) {
		Intr_acc_type = intr_acc_type;
	}
	public String getIntr_name() {
		return Intr_name;
	}
	public void setIntr_name(String intr_name) {
		Intr_name = intr_name;
	}
	public int[] getJ_addtype() {
		return j_addtype;
	}
	public void setJ_addtype(int[] j_addtype) {
		this.j_addtype = j_addtype;
	}
	public int getLast_line() {
		return last_line;
	}
	public void setLast_line(int last_line) {
		this.last_line = last_line;
	}
	public int getLast_trn_seq() {
		return last_trn_seq;
	}
	public void setLast_trn_seq(int last_trn_seq) {
		this.last_trn_seq = last_trn_seq;
	}
	public String getLast_trndate() {
		return last_trndate;
	}
	public void setLast_trndate(String last_trndate) {
		this.last_trndate = last_trndate;
	}
	public int getLdg_page() {
		return ldg_page;
	}
	public void setLdg_page(int ldg_page) {
		this.ldg_page = ldg_page;
	}
	public int getLdg_seq() {
		return ldg_seq;
	}
	public void setLdg_seq(int ldg_seq) {
		this.ldg_seq = ldg_seq;
	}
	public int getMax_lmt() {
		return max_lmt;
	}
	public void setMax_lmt(int max_lmt) {
		this.max_lmt = max_lmt;
	}
	public int getMin_lmt() {
		return min_lmt;
	}
	public void setMin_lmt(int min_lmt) {
		this.min_lmt = min_lmt;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNo_id_issue() {
		return no_id_issue;
	}
	public void setNo_id_issue(int no_id_issue) {
		this.no_id_issue = no_id_issue;
	}
	public int getNo_jt_hldrs() {
		return no_jt_hldrs;
	}
	public void setNo_jt_hldrs(int no_jt_hldrs) {
		this.no_jt_hldrs = no_jt_hldrs;
	}
	public String getNom_name() {
		return Nom_name;
	}
	public void setNom_name(String nom_name) {
		Nom_name = nom_name;
	}
	public int getNom_reg_no() {
		return nom_reg_no;
	}
	public void setNom_reg_no(int nom_reg_no) {
		this.nom_reg_no = nom_reg_no;
	}
	public int getNorows() {
		return norows;
	}
	public void setNorows(int norows) {
		this.norows = norows;
	}
	public int getOd_int_rate() {
		return od_int_rate;
	}
	public void setOd_int_rate(int od_int_rate) {
		this.od_int_rate = od_int_rate;
	}
	public String getPayee_nm() {
		return payee_nm;
	}
	public void setPayee_nm(String payee_nm) {
		this.payee_nm = payee_nm;
	}
	public double getPcl_bal() {
		return pcl_bal;
	}
	public void setPcl_bal(double pcl_bal) {
		this.pcl_bal = pcl_bal;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public int getPs_bk_seq() {
		return ps_bk_seq;
	}
	public void setPs_bk_seq(int ps_bk_seq) {
		this.ps_bk_seq = ps_bk_seq;
	}
	public int getRef_no() {
		return ref_no;
	}
	public void setRef_no(int ref_no) {
		this.ref_no = ref_no;
	}
	public String getRelation() {
		return Relation;
	}
	public void setRelation(String relation) {
		Relation = relation;
	}
	public int getSac_no() {
		return Sac_no;
	}
	public void setSac_no(int sac_no) {
		Sac_no = sac_no;
	}
	public String getSac_ty() {
		return Sac_ty;
	}
	public void setSac_ty(String sac_ty) {
		Sac_ty = sac_ty;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public double getTrn_amount() {
		return trn_amount;
	}
	public void setTrn_amount(double trn_amount) {
		this.trn_amount = trn_amount;
	}
	public String getTrn_date() {
		return trn_date;
	}
	public void setTrn_date(String trn_date) {
		this.trn_date = trn_date;
	}
	public int getTrn_key() {
		return trn_key;
	}
	public void setTrn_key(int trn_key) {
		this.trn_key = trn_key;
	}
	public String getTrn_mode() {
		return trn_mode;
	}
	public void setTrn_mode(String trn_mode) {
		this.trn_mode = trn_mode;
	}
	public String getTrn_narr() {
		return trn_narr;
	}
	public void setTrn_narr(String trn_narr) {
		this.trn_narr = trn_narr;
	}
	public int getTrn_seq_no() {
		return trn_seq_no;
	}
	public void setTrn_seq_no(int trn_seq_no) {
		this.trn_seq_no = trn_seq_no;
	}
	public String getTrn_source() {
		return trn_source;
	}
	public void setTrn_source(String trn_source) {
		this.trn_source = trn_source;
	}
	public String getTrn_ty() {
		return trn_ty;
	}
	public void setTrn_ty(String trn_ty) {
		this.trn_ty = trn_ty;
	}
	public String[] getTyop() {
		return tyop;
	}
	public void setTyop(String[] tyop) {
		this.tyop = tyop;
	}
	public String getType_op() {
		return type_op;
	}
	public void setType_op(String type_op) {
		this.type_op = type_op;
	}
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}
	
	
}
