package masterObject.general;

import java.io.Serializable;


public class IncomeObject implements Serializable
{
    static final long serialVersionUID = 1L;//ship.....20/01/2007
    
	String type,name,address,empno,designation,dept,bname,actype,acno,nature,type_goods,goods_cndn,phone_no;	
	int phno,service;
	boolean transferable,confirmed,certenclosed;
	double income,expen,surplus,stk_val,turnover;
    private double net_income;

	public String getTypeOfIncome(){return type;}
	public void setTypeOfIncome(String type){this.type=type;}

	public String getName(){return name;}
	public void setName(String type){this.name=type;}

	public String getAddress(){return address;}
	public void setAddress(String type){this.address=type;}

	public int getPhNo(){return phno;}
	public void setPhNo(int phno){this.phno=phno;}
	
	public String getStringPhNo(){return phone_no;}
	public void setPhNo(String phone_no){this.phone_no=phone_no;}

	public int getService(){return service;}
	public void setService(int phno){this.service=phno;}

	public String getEmpNo(){return empno;}
	public void setEmpNo(String empno){this.empno=empno;}

	public String getDesignation(){return designation;}
	public void setDesignation(String empno){this.designation=empno;}

	public String getDept(){return dept;}
	public void setDept(String empno){this.dept=empno;}

	public boolean isTransferable(){return transferable;}
	public void setTransferable(boolean empno){this.transferable=empno;}

	public boolean isConfirmed(){return confirmed;}
	public void setConfirmed(boolean empno){this.confirmed=empno;}

	public boolean isCertificateEnclosed(){return certenclosed;}
	public void setCertificateEnclosed(boolean empno){this.certenclosed=empno;}

	public double getIncome(){return income;}
	public void setIncome(double d){this.income=d;}

	public double getExpenditure(){return expen;}
	public void setExpenditure(double d){this.expen=d;}

	// public double getSurplus(){return income;} code changed by Murugesh on 26/12/2005
	public double getSurplus(){return surplus;} //  code added by Murugesh on 26/12/2005
	public void setSurplus(double d){this.surplus=d;}

	public String getBankName(){return bname;}
	public void setBankName(String empno){this.bname=empno;}

	public String getAccType(){return actype;}
	public void setAccType(String empno){this.actype=empno;}

	public String getAccNo(){return acno;}
	public void setAccNo(String acno){this.acno=acno;}
	
	public String getNature(){return nature;}
	public void setNature(String acno){this.nature=acno;}
	
	public String getGoodsCondition(){return goods_cndn;}
	public void setGoodsCondition(String acno){this.goods_cndn=acno;}
	
	public String getTypeOfGoods(){return type_goods;}
	public void setTypeOfGoods(String acno){this.type_goods=acno;}
	
	public double getStockValue(){return stk_val;}
	public void setStockValue(double d){this.stk_val=d;}
	
	public double getTurnOver(){return turnover;}
	public void setTurnOver(double d){this.turnover=d;}

	public void setNetIncome(double d) {
        this.net_income=d;
        
    }
	
	public double getNetIncome(){return net_income;}

}
