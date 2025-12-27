package masterObject.backOffice;
import masterObject.general.UserVerifier;


public class SIEntryObject implements java.io.Serializable
{

	private int instNo,priorityNo,fromAccNo,toAccNo,loanOpt,periodMonths,periodDays, noExec,execTime,int_expiry_days;
	private String int_del_ind,string_frm_acc_type, toType,dueDt,lastExec,string_alt_de_tml,string_alt_de_user,string_alt_de_dt_time, delDate,string_alt_ve_tml,string_alt_ve_user,string_alt_ve_dt_time,string_name,string_date;
	private double amount,double_amt_adj;
	private String frommodAbbrv,tomodAbbrv,strloanopt,toaccholdername,fromaccholdername;
	
  public   UserVerifier obj_userverifier=new UserVerifier();
   

 public void setInstNo(int n){instNo=n;}
 public int getInstNo(){return instNo;}


 public void setPriorityNo(int n){priorityNo=n;}
 public int getPriorityNo(){return priorityNo;}


 public void setFromAccNo(int n){fromAccNo=n;}
 public int getFromAccNo(){return fromAccNo;}

 public void setToType(String str){toType=str;}
 public String getToType(){return toType;}

 public void setToAccNo(int n){toAccNo=n;}
 public int getToAccNo(){return toAccNo;}


 public void setLoanOpt(int n){loanOpt=n;}
 public int getLoanOpt(){return loanOpt;}


 public void setPeriodMonths(int n){periodMonths=n;}
 public int getPeriodMonths(){return periodMonths;}

 public void setPeriodDays(int n){periodDays=n;}
 public int getPeriodDays(){return periodDays;}

 public void setAmount(double n){amount=n;}
 public double getAmount(){return amount;}

 public void setAmtAdj(double n){double_amt_adj=n;}
 public double getAmtAdj(){return double_amt_adj;}

 public void setNoExec(int n){noExec=n;}
 public int getNoExec(){return noExec;}

 public void setExecTime(int n){execTime=n;}
 public int getExecTime(){return execTime;}


 public void setExpiryDays(int n){int_expiry_days=n;}
 public int getExpiryDays(){return int_expiry_days;}

 public void setDelInd(String n){int_del_ind=n;}
 public String getDelInd(){return int_del_ind;}

 public void setFromType(String str){string_frm_acc_type=str;}
 public String getFromType(){return string_frm_acc_type;}

 public void setName(String str){string_name=str;}
 public String getName(){return string_name;}

 public void setDueDt(String str){dueDt=str;}
 public String getDueDt(){return dueDt;}

 public void setLastExec(String str){lastExec=str;}
 public String getLastExec(){return lastExec;}

public void setAltDeTml(String str){string_alt_de_tml=str;}
 public String getAltDeTml(){return string_alt_de_tml;}

 public void setAltDeUser(String str){string_alt_de_user=str;}
 public String getAltDeUser(){return string_alt_de_user;}


 public void setAltDeDtTime(String str){string_alt_de_dt_time=str;}
 public String getAltDeDtTime(){return string_alt_de_dt_time;}


 public void setDelDate(String str){delDate=str;}
 public String getDelDate(){return delDate;}
 
 public void setAltVeTml(String str){string_alt_ve_tml=str;}
 public String getAltVeTml(){return string_alt_ve_tml;}

 public void setAltVeUser(String str){string_alt_ve_user=str;}
 public String getAltVeUser(){return string_alt_ve_user;}


 public void setAltVeDtTime(String str){string_alt_ve_dt_time=str;}
 public String getAltVeDtTime(){return string_alt_ve_dt_time;}
 
 public void setDate(String str){string_date=str;}
 public String getDate(){return string_date;}

 public String getFrommodAbbrv() {
	return frommodAbbrv;
}
public void setFrommodAbbrv(String frommodAbbrv) {
	this.frommodAbbrv = frommodAbbrv;
}
public String getTomodAbbrv() {
	return tomodAbbrv;
}
public void setTomodAbbrv(String tomodAbbrv) {
	this.tomodAbbrv = tomodAbbrv;
}
public String getStrloanopt() {
	return strloanopt;
}
public void setStrloanopt(String strloanopt) {
	this.strloanopt = strloanopt;
}
public String getToaccholdername() {
	return toaccholdername;
}
public void setToaccholdername(String toaccholdername) {
	this.toaccholdername = toaccholdername;
}
public String getFromaccholdername() {
	return fromaccholdername;
}
public void setFromaccholdername(String fromaccholdername) {
	this.fromaccholdername = fromaccholdername;
}

 
 
 


 }



