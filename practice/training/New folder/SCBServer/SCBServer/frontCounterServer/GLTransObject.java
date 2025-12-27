package frontCounterServer;

public class GLTransObject
{

	String acc_no,acc_type,trn_type,cd_ind,vtml,vdate,vid,trn_date;
	double amount;
	String gl_type,gl_code,trn_sou;
	int ref_no,trn_seq;


	public String getAccNo(){ return acc_no;}
	public void setAccNo(String acc_no){this.acc_no=acc_no;}

	public String getAccType(){ return acc_type;}
	public void setAccType(String acc_type){this.acc_type=acc_type;}

	public String getTrnType(){ return trn_type;}
	public void setTrnType(String trn_type){this.trn_type=trn_type;}

	public String getTrnDate(){ return trn_date;}
	public void setTrnDate(String trn_type){this.trn_date=trn_type;}

	public String getCdind(){ return cd_ind;}
	public void setCdind(String cd_ind){this.cd_ind=cd_ind;}

	public int getTrnSeq(){ return trn_seq;}
	public void setTrnSeq(int trn_seq){this.trn_seq=trn_seq;}

	public int getRefNo(){ return ref_no;}
	public void setRefNo(int ref_no){this.ref_no=ref_no;}

	public double getAmount(){ return amount;}
	public void setAmount(double amount){this.amount=amount;}

	public String getGLCode(){return gl_code;}
	public void setGLCode(String code){this.gl_code=code;}

	public String getGLType(){return gl_type;}
	public void setGLType(String type){this.gl_type=type;}

	public String getTrnSource(){return trn_sou;}
	public void setTrnSource(String code){this.trn_sou=code;}

	public String getVid(){return vid;}
	public void setVid(String code){this.vid=code;}

	public String getVtml(){return vtml;}
	public void setVtml(String code){this.vtml=code;}

	public String getVDate(){return vdate;}
	public void setVDate(String code){this.vdate=code;}



}
