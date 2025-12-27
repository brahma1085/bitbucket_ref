package masterObject.lockers;
import masterObject.general.UserVerifier;

import java.io.Serializable;

public class LockerTransObject implements Serializable
{
    static final long serialVersionUID = 1L;
    
	private String lk_ac_ty,opr_by,op_dttm,name,locker_ty,timein,timeout,rent_upto,trn_dt,trn_dt_time;
	private int lk_ac_no,locker_no,tr_seq,lockerNo;
	private String modcode;
	private String addr,operatedBy,opDate,lockerType;
	
	//ship.....11/07/2006
	private String rent_by,trf_acty;
	private int trf_acno;
	private double rent_coll;
	//////////newly added manu
	private String rentBy,transAcType,rentUpto;
	
	private int transAcNo;
	
	public UserVerifier uv=new UserVerifier();

	public int getLockerAcNo(){return lk_ac_no;}
	public void setLockerAcNo(int n){this.lk_ac_no=n;}
	
	public int getLockerNo(){return lockerNo;}
	public void setLockerNo(int n){this.lockerNo=n;}
	
	public String getLockerType(){return lockerType;}
	public void setLockerType(String n){this.lockerType=n;}
	
	public int getTrnSeq(){return tr_seq;}
	public void setTrnSeq(int n){this.tr_seq=n;}
	
	public String getLockerAcType(){return lk_ac_ty;}
	public void setLockerAcType(String n){this.lk_ac_ty=n;}

	public String getOperatedBy(){ return operatedBy;}
	public void setOperatedBy(String s){this.operatedBy=s;}
	
	public String getOpDate(){ return opDate;}
	public void setOpDate(String s){this.opDate=s;}
	
	public String getTimeIn(){ return timein;}
	public void setTimeIn(String s){this.timein=s;}
	
	public String getTimeOut(){ return timeout;}
	public void setTimeOut(String s){this.timeout=s;}
	
	public String getAddress(){ return addr;}
	public void setAddress(String s){this.addr=s;}
	
	public String getName(){ return name;}
	public void setName(String s){this.name=s;}
	
	public String getModuleCode(){ return modcode;}
	public void setModuleCode(String s){this.modcode=s;}
	
	//ship......31/03/2006
	public String getRentUpto(){ return rentUpto;}
	public void setRentUpto(String s){this.rentUpto=s;}
	//////////
	
	//ship......04/07/2006
	public String getTrnDate(){return trn_dt;}
	public void setTrnDate(String n){this.trn_dt=n;}
	
	public String getTrnDateTime(){return trn_dt_time;}
	public void setTrnDateTime(String n){this.trn_dt_time=n;}
	//////////////
	
	//ship......11/07/2006
	public String getRentBy(){ return rentBy;}
	public void setRentBy(String s){this.rentBy=s;}
	
	public String getTransAcType(){ return transAcType;}
	public void setTransAcType(String s){this.transAcType=s;}
	
	public int getTransAcNo(){return transAcNo;}
	public void setTransAcNo(int n){this.transAcNo=n;}
	
	public double getRentColl(){return rent_coll;}
	public void setRentColl(double n){this.rent_coll=n;}
	//////////////
	public String getLk_ac_ty() {
		return lk_ac_ty;
	}
	public void setLk_ac_ty(String lk_ac_ty) {
		this.lk_ac_ty = lk_ac_ty;
	}
	public String getOpr_by() {
		return opr_by;
	}
	public void setOpr_by(String opr_by) {
		this.opr_by = opr_by;
	}
	public String getOp_dttm() {
		return op_dttm;
	}
	public void setOp_dttm(String op_dttm) {
		this.op_dttm = op_dttm;
	}
	public String getLocker_ty() {
		return locker_ty;
	}
	public void setLocker_ty(String locker_ty) {
		this.locker_ty = locker_ty;
	}
	public String getTimein() {
		return timein;
	}
	public void setTimein(String timein) {
		this.timein = timein;
	}
	public String getTimeout() {
		return timeout;
	}
	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}
	public String getRent_upto() {
		return rent_upto;
	}
	public void setRent_upto(String rent_upto) {
		this.rent_upto = rent_upto;
	}
	public String getTrn_dt() {
		return trn_dt;
	}
	public void setTrn_dt(String trn_dt) {
		this.trn_dt = trn_dt;
	}
	public String getTrn_dt_time() {
		return trn_dt_time;
	}
	public void setTrn_dt_time(String trn_dt_time) {
		this.trn_dt_time = trn_dt_time;
	}
	public int getLk_ac_no() {
		return lk_ac_no;
	}
	public void setLk_ac_no(int lk_ac_no) {
		this.lk_ac_no = lk_ac_no;
	}
	public int getLocker_no() {
		return locker_no;
	}
	public void setLocker_no(int locker_no) {
		this.locker_no = locker_no;
	}
	public int getTr_seq() {
		return tr_seq;
	}
	public void setTr_seq(int tr_seq) {
		this.tr_seq = tr_seq;
	}
	public String getModcode() {
		return modcode;
	}
	public void setModcode(String modcode) {
		this.modcode = modcode;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getRent_by() {
		return rent_by;
	}
	public void setRent_by(String rent_by) {
		this.rent_by = rent_by;
	}
	public String getTrf_acty() {
		return trf_acty;
	}
	public void setTrf_acty(String trf_acty) {
		this.trf_acty = trf_acty;
	}
	public int getTrf_acno() {
		return trf_acno;
	}
	public void setTrf_acno(int trf_acno) {
		this.trf_acno = trf_acno;
	}
	public double getRent_coll() {
		return rent_coll;
	}
	public void setRent_coll(double rent_coll) {
		this.rent_coll = rent_coll;
	}
	public UserVerifier getUv() {
		return uv;
	}
	public void setUv(UserVerifier uv) {
		this.uv = uv;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
}


