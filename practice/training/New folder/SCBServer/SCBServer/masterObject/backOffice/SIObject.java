package masterObject.backOffice;
public class SIObject implements java.io.Serializable{
	private  String fr_type,to_type;
	private  int prty_no;
 
 public void setFromType(String fr){fr_type=fr;}
 public String getFromType(){return fr_type;}
 public void setToType(String to){ to_type=to;}
 public String getToType(){ return to_type;}
 public void setPriority(int pri){prty_no=pri;}
 public int  getPriority(){return prty_no;}
 //public void setde_tml(String de_tm){ de_tml=de_tm;}
 //public String getde_tml(){return de_tml;}
 //public void setde_user(String de_usr){de_user=de_usr;}
 //public String getde_user(){return de_user;}
// public void setde_dt_time(String de_time){de_dt_time=de_time;}
 //public String getde_dt_time(){return de_dt_time;}
 
 
	
}