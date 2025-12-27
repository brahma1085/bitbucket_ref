package masterObject.loans;

import masterObject.general.UserVerifier;


public class DocumentsObject implements java.io.Serializable{
	String lntype,opendt,closedt,memtype;
	int acno,doccode,memno;
	boolean flag;

	// Code added by Murugesh on 20/03/2006
	String pledged_date=null,returned_date=null,other_details=null,doc_desc=null;
	public UserVerifier uv=new UserVerifier();
	//
	public String getLntype() {
		return lntype;
	}
	public void setLntype(String lntype) {
		this.lntype = lntype;
	}
	public String getOpendt() {
		return opendt;
	}
	public void setOpendt(String opendt) {
		this.opendt = opendt;
	}
	public String getClosedt() {
		return closedt;
	}
	public void setClosedt(String closedt) {
		this.closedt = closedt;
	}
	public String getMemtype() {
		return memtype;
	}
	public void setMemtype(String memtype) {
		this.memtype = memtype;
	}
	public int getAcno() {
		return acno;
	}
	public void setAcno(int acno) {
		this.acno = acno;
	}
	public int getDoccode() {
		return doccode;
	}
	public void setDoccode(int doccode) {
		this.doccode = doccode;
	}
	public int getMemno() {
		return memno;
	}
	public void setMemno(int memno) {
		this.memno = memno;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getPledged_date() {
		return pledged_date;
	}
	public void setPledged_date(String pledged_date) {
		this.pledged_date = pledged_date;
	}
	public String getReturned_date() {
		return returned_date;
	}
	public void setReturned_date(String returned_date) {
		this.returned_date = returned_date;
	}
	public String getOther_details() {
		return other_details;
	}
	public void setOther_details(String other_details) {
		this.other_details = other_details;
	}
	public String getDoc_desc() {
		return doc_desc;
	}
	public void setDoc_desc(String doc_desc) {
		this.doc_desc = doc_desc;
	}
	public UserVerifier getUv() {
		return uv;
	}
	public void setUv(UserVerifier uv) {
		this.uv = uv;
	}

	
}
