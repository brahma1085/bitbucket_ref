package SRC.COM.SUNRISE.UTILITY.VALUEOBJECT;

import java.util.Hashtable;
import java.util.Vector;

public class SBCAObject {

	private String ac_type, intro_ac_type, ac_open_date,check_book_issue , name,freeze,active,de_datetime,de_tml,de_user;
	private int ac_no,intro_ac_no,cid, book_no,seq_from ,seq_to,addr_type;
	private Vector vec_jointholder;
	private Hashtable hash_jointholder;
	double balanceAmount;
	Hashtable ckeckno;
	
	public double getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	public Hashtable getCkeckno() {
		return ckeckno;
	}
	public void setCkeckno(Hashtable ckeckno) {
		this.ckeckno = ckeckno;
	}
	public Vector getVec_jointholder() {
		return vec_jointholder;
	}
	public void setVec_jointholder(Vector vec_jointholder) {
		this.vec_jointholder = vec_jointholder;
	}
	public int getAc_no() {
		return ac_no;
	}
	public void setAc_no(int ac_no) {
		this.ac_no = ac_no;
	}
	public String getAc_open_date() {
		return ac_open_date;
	}
	public void setAc_open_date(String ac_open_date) {
		this.ac_open_date = ac_open_date;
	}
	public String getAc_type() {
		return ac_type;
	}
	public void setAc_type(String ac_type) {
		this.ac_type = ac_type;
	}
	public int getBook_no() {
		return book_no;
	}
	public void setBook_no(int book_no) {
		this.book_no = book_no;
	}
	public String getCheck_book_issue() {
		return check_book_issue;
	}
	public void setCheck_book_issue(String check_book_issue) {
		this.check_book_issue = check_book_issue;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public int getIntro_ac_no() {
		return intro_ac_no;
	}
	public void setIntro_ac_no(int intro_ac_no) {
		this.intro_ac_no = intro_ac_no;
	}
	public String getIntro_ac_type() {
		return intro_ac_type;
	}
	public void setIntro_ac_type(String intro_ac_type) {
		this.intro_ac_type = intro_ac_type;
	}
	public int getSeq_from() {
		return seq_from;
	}
	public void setSeq_from(int seq_from) {
		this.seq_from = seq_from;
	}
	public int getSeq_to() {
		return seq_to;
	}
	public void setSeq_to(int seq_to) {
		this.seq_to = seq_to;
	}
	public int getAddr_type() {
		return addr_type;
	}
	public void setAddr_type(int addr_type) {
		this.addr_type = addr_type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getFreeze() {
		return freeze;
	}
	public void setFreeze(String freeze) {
		this.freeze = freeze;
	}
	public String getDe_datetime() {
		return de_datetime;
	}
	public void setDe_datetime(String de_datetime) {
		this.de_datetime = de_datetime;
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
	public Hashtable getHash_jointholder() {
		return hash_jointholder;
	}
	public void setHash_jointholder(Hashtable hash_jointholder) {
		this.hash_jointholder = hash_jointholder;
	}
	
}
