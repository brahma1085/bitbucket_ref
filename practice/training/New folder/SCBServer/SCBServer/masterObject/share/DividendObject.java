package masterObject.share;
import masterObject.general.Address;

import java.io.Serializable;

public class DividendObject implements Serializable
{


	double div_rate,drfAmount,divAmount,shvalue;
	String divDate,sHType,divacctype,branchname,name,payMode,wdate,date,tml,uid,pay_type;
	int caldone,divAccNo,cvpaid,customerID,branchCode,wno,voucherNo,cvNumber,sHNumber,shnum;
	char caloption;
	public Address addr=new Address();
	
 
	
	// branch name

	public String getName(){return name;}
	public void setName(String name){this.name=name;}
	public String getBranchName(){ return branchname;}
	//   get DivRate....
	public double getDivRate(){return div_rate;}
	public double getDrfAmount(){return drfAmount;}
	public String getDivDate(){return divDate;}
	public int getCalDone(){return caldone;}
	public char getCalOption(){return caloption;}

	//  get ShareDiv
	public String getSHType(){return sHType;}
	public int getSHNumber(){return sHNumber;}
	public int getCVPaid(){return cvpaid;}
	public double getDivAmount(){return divAmount;}
	public String getDivAccType(){return divacctype;}
	public int getDivAccNo(){return divAccNo;}
	public String getPayMode(){return payMode;}
	
	public int getCustomerID(){return customerID;}
	public int getBranchCode(){return branchCode;}
	
	
	//get DivPayment
	public double getShareValue(){return shvalue;}
	public String getWarrantDate(){return wdate;}
	public int getWarrantNo(){return wno;}
	public int getVoucherNo(){return voucherNo;}
	
	//  set branch

	public void setBranchName(String branchname){this.branchname = branchname;}
	//  set DivRate
	public void setDivRate(float div_rate){this.div_rate=div_rate;}
	public void setDrfAmount(float drf_amt){this.drfAmount=drf_amt;}
	public void setDivDate(String divdate){this.divDate=divdate;}
	public void setCalDone(int caldone){this.caldone=caldone;}
	public void setCalOption(char caloption){this.caloption=caloption;}

	//  set ShareDiv
	public void setSHType(String shtype){this.sHType=shtype;}
	public void setSHNumber(int shnumber){this.sHNumber=shnumber;}
	public void setCVPaid(int cvpaid){this.cvpaid=cvpaid;}
	public void setDivAmount(double divamount){this.divAmount=divamount;}
	public void setDivAccType(String divacctype){this.divacctype=divacctype;}
	public void setDivAccNo(int divaccno){this.divAccNo=divaccno;}
	public void setPayMode(String paymode){this.payMode=paymode;}

	public void setCustomerID(int cid){this.customerID=cid;}
	public void setBranchCode(int bcode){this.branchCode=bcode;}
	
	public void setShareValue(double shvalue){this.shvalue=shvalue;}
	public void setWarrantDate(String wdate){this.wdate=wdate;}
	public void setWarrantNo(int wno){this.wno=wno;}
	public void setVoucherNo(int vno){this.voucherNo=vno;}
	
	public void setCvNumber(int cvno){this.cvNumber=cvno;}
	public int getCvNumber(){return cvNumber;}
	
	public void setVerified(String ve_id){this.uid=ve_id;}
	public String getVerified(){return uid;}
	
	public void setVeTml(String ve_tml){this.tml=ve_tml;}
	public String getVeTml(){return tml;}
	
	public void setVeDate(String ve_date){this.date=ve_date;}
	public String getVeDate(){return date;}
	
	public void setPaymentType(String paytype){this.pay_type=paytype;}
	public String getPaymentType(){return pay_type;}
	public int getShnum() {
		return shnum;
	}
	public void setShnum(int shnum) {
		this.shnum = shnum;
	}
	
	
}
