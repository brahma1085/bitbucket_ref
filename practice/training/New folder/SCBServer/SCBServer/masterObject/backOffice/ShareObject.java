package masterObject.backOffice;
 
import java.io.Serializable;

public class ShareObject implements Serializable
{
	private  String sh_type,trn_dt,cd_ind,trn_mode,trnNarr,uid,vid,susp_ind,trn_ty,sname,name,date;
	private  int shNo,trn_seq;
	private  double trnAmt;
	
	public String getName(){return this.name;}
	public void setName(String name){this.name=name;}
	
	public String getShType(){return this.sh_type;}
	public void setShType(String sh_type){this.sh_type=sh_type;}
	
	public String getTrnDate(){return this.trn_dt;}
	public void setTrnDt(String trn_dt){this.trn_dt=trn_dt;}
	
	public String getCdInd(){return this.cd_ind;}
	public void setCdInd(String cd_ind){this.cd_ind=cd_ind;}
	
	public String getTrnMode(){return this.trn_mode;}
	public void setTrnMode(String trn_mode){this.trn_mode=trn_mode;}
	
	public String getTrnNarr(){return this.trnNarr;}
	public void setTrnNarr(String trn_narr){this.trnNarr=trn_narr;}
	
	public String getUid(){return this.uid;}
	public void setUid(String uid){this.uid=uid;}
	
	public String getVid(){return this.vid;}
	public void setVid(String vid){this.vid=vid;}
	
	public String getSuspInd(){return this.susp_ind;}
	public void setSuspInd(String susp_ind){this.susp_ind=susp_ind;}
	
	public String getTrnTy(){return this.trn_ty;}
	public void setTrnTy(String trn_ty){this.trn_ty=trn_ty;}
	
	public String getSname(){return this.sname;}
	public void setSname(String sname){this.sname=sname;}
	
	public int getShNo(){return this.shNo;}
	public void setShNo(int sh_no){this.shNo=sh_no;}
	
	public double getTrnAmt(){return this.trnAmt;}
	public void setTrnAmt(double trn_amt){this.trnAmt=trn_amt;}
	
	public void setTrnSeq(int seq){this.trn_seq=seq;}
	public int getTrnSeq(){return this.trn_seq;}
	
	public void setMemCloseDate(String date){this.date=date;}
	public String getMemCloseDate(){return this.date;}
}

