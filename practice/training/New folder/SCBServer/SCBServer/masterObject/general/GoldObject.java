package masterObject.general;
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

	//-----01/10/2011-----
	int slno,grWeight,netWeight;
	double netRate,rate;
	String desc,appCode;
	
	public int getSlno() {
		return slno;
	}
	public void setSlno(int slno) {
		this.slno = slno;
	}
	public int getGrWeight() {
		return grWeight;
	}
	public void setGrWeight(int grWeight) {
		this.grWeight = grWeight;
	}
	public int getNetWeight() {
		return netWeight;
	}
	public void setNetWeight(int netWeight) {
		this.netWeight = netWeight;
	}
	public double getNetRate() {
		return netRate;
	}
	public void setNetRate(double netRate) {
		this.netRate = netRate;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
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
	