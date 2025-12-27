package SRC.COM.SUNRISE.UTILITY.VALUEOBJECT;

import java.io.Serializable;
import java.util.Vector;

public class GoldObject implements Serializable
{
    static final long serialVersionUID = 1L;//ship.....20/01/2007
    
	String date,time,accty,name;
	int accno;	
	Object goldobj[][];
	Vector vec=null;
	int empno;

	public int getApprisersCode(){return empno;}
	public void setApprisersCode(int empno){this.empno=empno;}

	public String getApprisersName(){return name;}
		public void setApprisersName(String empno){this.name=empno;}

	public String getAccType(){return accty;}
	public void setAccType(String accty){this.accty=accty;}

	public int getAccNo(){return accno;}
	public void setAccNo(int empno){this.accno=empno;}

	public String getApprisalDate(){return date;}
	public void setApprisalDate(String empno){this.date=empno;}

	public String getApprisalTime(){return time;}
	public void setApprisalTime(String empno){this.time=empno;}

	public Object[][] getGoldDet(){return goldobj;}
	public void setGoldDet(Object obj[][]){goldobj=obj;}
	
	public Vector getGoldDetVector(){return vec;}
	public void setGoldDetVector(Vector vect){vec=vect;}
	
	

}

/*public class DetailsOfGold implements Serializable
{
	int sno,grwgt,netwgt,netgold;
	double netrate,rate;
	String desc;

	public String getDescription(){return desc;}
	public void setDescription(String empno){this.desc=empno;}

	public int getSrlNo(){return sno;}
	public void setSrlNo(int no){this.sno=no;}

	public int getGrossWeight(){return grwgt;}
	public void setSrlNo(int no){this.grwgt=no;}

	public int getNetWeight(){return netwgt;}
	public void setNetWeight(int no){this.netwgt=no;}

	public int getNetGold(){return netgold;}
	public void setNetGold(int no){this.netgold=no;}

	public double getNetRate(){return netrate;}
	public void setNetRate(double no){this.netrate=no;}

	public double getRate(){return rate;}
	public void setRate(double no){this.rate=no;}

}*/
