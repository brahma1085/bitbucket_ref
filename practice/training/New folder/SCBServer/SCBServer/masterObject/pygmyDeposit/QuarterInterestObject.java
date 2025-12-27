package masterObject.pygmyDeposit;

import java.io.Serializable;

public class QuarterInterestObject implements Serializable
{
    private double intamt,clBal;
    private int pygmyNo;
    private String nomName,nomRelation,intPdUpto;

    private String pygmyType,intDate,uid,tml,time;

	public double getIntAmount(){return intamt;}

	public String getIntDate(){return intDate;}

	public int getPygmyNo(){return pygmyNo;}
    
	public String getPygmyType(){return pygmyType;}

	public String getTime(){return time;}

	public String getTml(){return tml;}

	public String getUid(){return uid;}
	public void setIntAmount(double intamt){this.intamt=intamt;}
	public void setIntDate(String intdt){this.intDate=intdt;}
	public void setPygmyNo(int pgno){this.pygmyNo=pgno;}
	public void  setPygmyType(String pgtype){this.pygmyType=pgtype;}
	public void setTime(String time){this.time=time;}
	public void setTml(String tml){this.tml=tml;}
	public void setUid(String uid){this.uid=uid;}
	
	/*public String getIntdt() {
		return intdt;
	}
	public void setIntdt(String intdt) {
		this.intdt = intdt;
	}*/
	
	
	public double getClBal() {
		return clBal;
	}

	public void setClBal(double clBal) {
		this.clBal = clBal;
	}

	public String getNomRelation() {
		return nomRelation;
	}

	public void setNomRelation(String nomRelation) {
		this.nomRelation = nomRelation;
	}

	

	public String getIntPdUpto() {
		return intPdUpto;
	}

	public void setIntPdUpto(String intPdUpto) {
		this.intPdUpto = intPdUpto;
	}

	public String getNomName() {
		return nomName;
	}

	public void setNomName(String nomName) {
		this.nomName = nomName;
	}

	
	
}