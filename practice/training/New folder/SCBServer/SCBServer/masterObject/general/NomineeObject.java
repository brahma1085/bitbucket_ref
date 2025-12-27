package masterObject.general;
import java.io.Serializable;


public class NomineeObject implements Serializable
{
    static final long serialVersionUID = 1L;//ship.....20/01/2007
    
	int cid,sex,guard_sex,accno,reg_no,cordno;
	String name,dob,address,relation,guard_name,guard_address,acctype,gtype,corddate,grel;
	double percentage;
	String frm_dt,to_dt;
	
	public double getPercentage(){return percentage;}
	public void setPercentage(double per){percentage=per;}
	
	public int getCustomerId(){return cid;}
	public void setCustomerId(int cid){this.cid=cid;}
	
	public int getCourtOrderNo(){return cordno;}
	public void setCourtOrderNo(int cordno){this.cordno=cordno;}
	
	public String getCourtOrderDate(){return corddate;}
	public void setCourtOrderDate(String corddate){this.corddate=corddate;}
	
	public String getGuardiantype(){return gtype;}
	public void setGuardianType(String gtype){this.gtype=gtype;}

	public int getRegNo(){return reg_no;}
	public void setRegNo(int reg_no){this.reg_no=reg_no;}

	public int getAccNo(){return accno;}
	public void setAccNo(int accno){this.accno=accno;}

	public String getAccType(){return acctype;}
	public void setAccType(String acctype){this.acctype=acctype;}

	public String getGuardRelation(){return grel;}
	public void setGuardRelation(String grel){this.grel=grel;}

	public int getSex(){return sex;}
	public void setSex(int sex){this.sex=sex;}

	public int getGuardSex(){return guard_sex;}
	public void setGuardSex(int cid){this.guard_sex=cid;}

	public String getNomineeName(){return name;}
	public void setNomineeName(String name){this.name=name;}

	public String getNomineeDOB(){return dob;}
	public void setNomineeDOB(String dob){this.dob=dob;}

	public String getNomineeAddress(){return address;}
	public void setNomineeAddress(String address){this.address=address;}

	public String getNomineeRelation(){return relation;}
	public void setNomineeRelation(String relation){this.relation=relation;}

	public String getGuardianName(){return guard_name;}
	public void setGuardianName(String guard_name){this.guard_name=guard_name;}

	public String getGuardianAddress(){return guard_address;}
	public void setGuardianAddress(String guard_address){this.guard_address=guard_address;}
	
	//Added By Karthi==>20/04/2006
	public void setFromDate(String frm_dt){this.frm_dt=frm_dt;}
	public String getFromDate(){return frm_dt;}
	
	public void setToDate(String to_dt){this.to_dt=to_dt;}
	public String getToDate(){return to_dt;}

}
