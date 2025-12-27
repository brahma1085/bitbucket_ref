 package masterObject.backOffice;
 
 public class SIDoneObject implements java.io.Serializable
{
 	private  int siNo,fromAccNo,toAccNo;
 	private  String dueDt,intDt,string_complete_ind,string_de_tml,string_de_user,string_de_dt_time;
 	private   double trnAmt, prinAmt, intAmt, penalAmt, otherAmt;
    private String tomodAbbrv,frommodAbbrv,toaccholdername,fromaccholdername;

    
 public void setSiNo(int int_instn_no){this.siNo=int_instn_no; }
 public int getSiNo(){return siNo;}

 public void setDueDt(String dt){dueDt=dt;}
 public String getDueDt(){return dueDt;}

 public void setIntDt(String dt){intDt=dt;}
 public String getIntDt(){return intDt;}

 public void setComplete_ind(String s){string_complete_ind=s;}
 public String getComplete_ind(){return string_complete_ind;}

 public void setDeTml(String s){string_de_tml=s;}
 public String getDeTml(){return string_de_tml;}

 public void setDeUser(String s){string_de_user=s;}
 public String getDeUser(){return string_de_user;}

 public void setDeDtTime(String s){string_de_dt_time=s;}
 public String getDeDtTime(){return string_de_dt_time;}

 public void setTrnAmt(double amt){trnAmt=amt;}
 public double getTrnAmt(){return trnAmt;}


 public void setPrinAmt(double amt){prinAmt=amt;}
 public double getPrinAmt(){return prinAmt;}

 public void setIntAmt(double amt){intAmt=amt;}
 public double getIntAmt(){return intAmt;}

 public void setPenalAmt(double amt){penalAmt=amt;}
 public double getPenalAmt(){return penalAmt;}

 public void setOtherAmt(double amt){otherAmt=amt;}
 public double getOtherAmt(){return otherAmt;}

 
public String getTomodAbbrv() {return tomodAbbrv;}
public void setTomodAbbrv(String tomodAbbrv) {this.tomodAbbrv = tomodAbbrv;}

public String getFrommodAbbrv() {return frommodAbbrv;}
public void setFrommodAbbrv(String frommodAbbrv) {this.frommodAbbrv = frommodAbbrv;}

public String getToaccholdername() {return toaccholdername;}
public void setToaccholdername(String toaccholdername) {this.toaccholdername = toaccholdername;}

public String getFromaccholdername() {return fromaccholdername;}
public void setFromaccholdername(String fromaccholdername) {this.fromaccholdername = fromaccholdername;}
public int getFromAccNo() {	return fromAccNo;}
public void setFromAccNo(int fromAccNo) {this.fromAccNo = fromAccNo;}

public int getToAccNo() {return toAccNo;}
public void setToAccNo(int toAccNo) {this.toAccNo = toAccNo;}

}

