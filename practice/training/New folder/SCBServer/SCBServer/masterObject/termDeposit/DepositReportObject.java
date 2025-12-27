package masterObject.termDeposit; 

import java.io.Serializable;
import javax.swing.ImageIcon;

public class DepositReportObject implements Serializable
{
    String accname,acctype,photo,sign,lmt_hdg;
	double amount;
	String trn_date,verify,status,open_dt,ver,intro_name,ac_opendate,ch_bk_issue,int_pbl_date;
	int acc_no,scrollno,min_perd,no,acCategory,nom_regno,fr_lmt,to_lmt,total_ac_no;
	String gl_code;
	ImageIcon ic,signimg;
	String maincid;
	String cid;
	String[] jcid;	

	String name,period,matDate,depDate;
	String string_address,string_city,string_state,string_pin;
	double double_dep_amt,double_mat_amt,dep_int_amt;
	int int_add_type;
	
	public void setImage(String str){ic=new ImageIcon(str);}
	public ImageIcon getImage(){return ic;}
	
	public void setSignImage(String str){signimg=new ImageIcon(str);}
	public ImageIcon getSignImage(){return signimg;}
	
	public int getNoOfTrns(){ return no;}
	public void setNoOfTrns(int no){this.no=no;}
	
	public String getGLCode(){ return gl_code;}
	public void setGLCode(String acc_no){this.gl_code=acc_no;}
	
	public String[] getJointCids(){ return jcid;}
	public void setJointCids(String[] jcid){this.jcid=jcid;}

	public String getMainCid(){ return maincid;}
	public void setMainCid(String maincid){this.maincid=maincid;}

	public String getOpenDate(){ return open_dt;}
	public void setOpenDate(String trndate){this.open_dt=trndate;}
	
	public int getAccno(){ return acc_no;}
	public void setAccno(int acc_no){this.acc_no=acc_no;}

	public int getScrollno(){ return scrollno;}
	public void setScrollno(int acc_no){this.scrollno=acc_no;}

	public String getCid(){return cid;}
	public void setCid(String str){this.cid=str;}


	public String getAcctype(){ return acctype;}
	public void setAcctype(String acc_type){this.acctype=acc_type;}

	public String getAccname(){ return accname;}
	public void setAccname(String acc_name){this.accname=acc_name;}

	public double getAmount(){ return amount;}
	public void setAmount(double amount){this.amount=amount;}

	public String getLastTrnDate(){ return trn_date;}
	public void setLastTrnDate(String trndate){this.trn_date=trndate;}

	public String getPhoto(){ return photo;}
	public void setPhoto(String photo){this.photo=photo;}

	public String getSign(){ return sign;}
	public void setSign(String sign){this.sign=sign;}

	public String getVerified(){ return verify;}
	public void setVerified(String verify){this.verify=verify;}

	public String getAccStatus(){return status;}
	public void setAccStatus(String status){this.status=status;}

	public String getIntroName(){return intro_name;}
	public void setIntroName(String intro_name){this.intro_name=intro_name;}	
	
	public String getAcOpenDate(){return ac_opendate;}	
	public void setAcOpenDate(String ac_opendate){this.ac_opendate=ac_opendate;}	
	
	public int getAcCategory(){ return acCategory;}
	public void setAcCategory(int ac_category){this.acCategory=ac_category;}
	
	public int getNomRegno(){ return nom_regno;}
	public void setNomRegno(int nom_regno){this.nom_regno=nom_regno;}
	
	public String getChBkIssue(){ return ch_bk_issue;}
	public void setChBkIssue(String ch_bk_issue){this.ch_bk_issue=ch_bk_issue;}
	
	public String getIntPblDate(){ return int_pbl_date;}
	public void setIntPblDate(String int_pbl_date){this.int_pbl_date=int_pbl_date;}	

	public void setName(String string_name)
	{
		this.name=string_name;	
	}
	public String getName()
	{
		return name;	
	}
	
	
	public void setPeriod(String string_period)
	{
		this.period=string_period;	
	}
	public String getPeriod()
	{
		return period;	
	}
	
	
	public void setMatDate(String string_mat_date)
	{
		this.matDate=string_mat_date;	
	}
	public String getMatDate()
	{
		return matDate;	
	}
	
	
	public void setDepAmt(double double_dep_amt)
	{
		this.double_dep_amt=double_dep_amt;	
	}
	public double getDepAmt()
	{
		return double_dep_amt;	
	}
	

	public void setMatAmt(double double_mat_amt)
	{
		this.double_mat_amt=double_mat_amt;	
	}
	public double getMatAmt()
	{
		return double_mat_amt;	
	}


	public void setAddress(String string_address)
	{
		this.string_address=string_address;	
	}
	public String getAddress()
	{
		return string_address;	
	}
	
	
	public void setState(String string_state)
	{
		this.string_state=string_state;	
	}
	public String getState()
	{
		return string_state;	
	}
	
	
	public void setCity(String string_city)
	{
		this.string_city=string_city;	
	}
	public String getCity()
	{
		return string_city;	
	}

	
	public void setPin(String string_pin)
	{
		this.string_pin= string_pin;	
	}
	public String getPin()
	{
		return  string_pin;	
	}
	
	
	public void setDepDate(String string_dep_date)
	{
		this.depDate=string_dep_date;	
	}
	public String getDepDate()
	{
		return depDate;	
	}
	
	
	public void setAddType(int int_add_type)
	{
		this.int_add_type=int_add_type;	
	}
	public int getAddType()
	{
		return  int_add_type;	
	}	
	
	public String getLmt_hdg() {
		return lmt_hdg;
	}
	
	public void setLmt_hdg(String lmt_hdg) {
		this.lmt_hdg = lmt_hdg;
	}
	
	public int getFr_lmt() {
		return fr_lmt;
	}
	
	public void setFr_lmt(int fr_lmt) {
		this.fr_lmt = fr_lmt;
	}
	
	public int getTo_lmt() {
		return to_lmt;
	}
	
	public void setTo_lmt(int to_lmt) {
		this.to_lmt = to_lmt;
	}
	
	public double getDep_int_amt() {
		return dep_int_amt;
	}
	
	public void setDep_int_amt(double dep_int_amt) {
		this.dep_int_amt = dep_int_amt;
	}
	
	public int getTotal_ac_no() {
		return total_ac_no;
	}
	
	public void setTotal_ac_no(int total_ac_no) {
		this.total_ac_no = total_ac_no;
	}
}





