/*
 * Created on Apr 29, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package masterObject.generalLedger;

import java.io.Serializable;

/**
 * @author Murugesh
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GLReportObject implements Serializable 
{
	String date=null,firstDate=null,second_date=null,gl_type=null,gl_abbr=null,gl_name=null;
	String br_code=null,br_name=null,normal_cd=null;
	String ac_type,ac_no,trn_seq,trn_desc,cd_ind,trn_mode,ac_abbr,name;
	int gl_code;
	double balance=0.0,first_date_balance=0.0,second_date_balance=0.0,close_bal=0.0,open_bal=0.0,trn_amt=0.0;
	
	public void setDate(String date){this.date=date;}
	public String getDate(){return date;}
	
	public void setFirstDate(String first_date){this.firstDate=first_date;}
	public String getFirstDate(){return firstDate;}
	
	public void setSecondDate(String second_date){this.second_date=second_date;}
	public String getSecondDate(){return second_date;}
	
	public void setGLType(String gl_type){this.gl_type=gl_type;}
	public String getGLType(){return gl_type;}
	
	public void setGLAbbr(String gl_abbr){this.gl_abbr=gl_abbr;}
	public String getGLAbbr(){return gl_abbr;}
	
	public void setGLName(String gl_name){this.gl_name=gl_name;}
	public String getGLName(){return gl_name;}
	
	public void setGLCode(int gl_code){this.gl_code=gl_code;}
	public int getGLCode(){return gl_code;}

	public void setBranchCode(String br_code){this.br_code=br_code;}
	public String getBranchCode(){return br_code;}

	public void setBranchName(String br_name){this.br_name=br_name;}
	public String getBranchName(){return br_name;}
	
	public void setBalance(double balance){this.balance=balance;}
	public double getBalance(){return balance;}
	
	public void setFirstDateBalance(double first_date_balance){this.first_date_balance=first_date_balance;}
	public double getFirstDateBalance(){return first_date_balance;}
	
	public void setSecondDateBalance(double second_date_balance){this.second_date_balance=second_date_balance;}
	public double getSecondDateBalance(){return second_date_balance;}
	
	public void setClosingBalance(double close_bal){this.close_bal=close_bal;}
	public double getClosingBalance(){return close_bal;}

	public void setOpeningBalance(double open_bal){this.open_bal=open_bal;}
	public double getOpeningBalance(){return open_bal;}
	
	public void setNormalCD(String normal_cd){this.normal_cd=normal_cd;}
	public String getNormalCD(){return normal_cd;}
	
	public void setAcAbbr(String ac_abbr){this.ac_abbr=ac_abbr;}
	public String getAcAbbr(){return ac_abbr;}
	
	public void setAcNo(String ac_no){this.ac_no=ac_no;}
	public String getAcNo(){return ac_no;}
	
	public void setAcType(String br_name){this.ac_type=ac_type;}
	public String getAcType(){return ac_type;}
	
	public void setName(String name){this.name=name;}
	public String getName(){return name;}
	
	public void setTrnMode(String trn_mode){this.trn_mode=trn_mode;}
	public String getTrnMode(){return trn_mode;}
	
	public void setCdInd(String cd_ind){this.cd_ind=cd_ind;}
	public String getCdInd(){return cd_ind;}
	
	public void setTrnSeq(String trn_seq){this.trn_seq=trn_seq;}
	public String getTrnseq(){return trn_seq;}
	
	public void setTrnDesc(String trn_desc){this.trn_desc=trn_desc;}
	public String getTrnDesc(){return trn_desc;}
	
	public void setTrnAmt(double trn_amt){this.trn_amt=trn_amt;}
	public double getTrnAmt(){return trn_amt;}
	
	
	
	
	
	
	
}
