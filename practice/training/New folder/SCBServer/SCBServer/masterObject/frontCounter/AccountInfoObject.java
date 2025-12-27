package masterObject.frontCounter;

import masterObject.general.Address;
import masterObject.general.SignatureInstructionObject;

import java.io.Serializable;
import javax.swing.ImageIcon;

public class AccountInfoObject implements Serializable
{
	String acc_name,acc_type,trn_date,verify,status,open_dt,gl_code,freeze_ind,default_ind,int_pbl_date,sanc_date;
	double amount,prev_balance;
	String chq_iss;
	int acc_no,scroll_no,no,category,sub_category,cid,trn_seq;

	SignatureInstructionObject[] siob=null;
	ImageIcon ic,signimg;
	
	//OD/CC
	
	double creditlimit;
	String limit_upto;	
	public Address addr=new Address();
	private double shadow_balance;
	private String gl_type,gl_name;

	public void setImage(String str){ic=new ImageIcon(str);}
	public ImageIcon getImage(){return ic;}
	
	public void setSignImage(String str){signimg=new ImageIcon(str);}
	public ImageIcon getSignImage(){return signimg;}
	
	public int getNoOfTrns(){ return no;}
	public void setNoOfTrns(int no){this.no=no;}
	
	public int getCategory(){ return category;}
	public void setCategory(int no){this.category=no;}
	
	public int getTrnSeq(){ return trn_seq;}
	public void setTrnSeq(int seq){this.trn_seq=seq;}
	
	public int getSubCategory(){ return sub_category;}
	public void setSubCategory(int no){this.sub_category=no;}

	public String getGLCode(){ return gl_code;}
	public void setGLCode(String acc_no){this.gl_code=acc_no;}	
	
	public String getOpenDate(){ return open_dt;}
	public void setOpenDate(String trndate){this.open_dt=trndate;}

	public int getAccno(){ return acc_no;}
	public void setAccno(int acc_no){this.acc_no=acc_no;}

	public int getScrollno(){ return scroll_no;}
	public void setScrollno(int acc_no){this.scroll_no=acc_no;}

	public int getCid(){return cid;}
	public void setCid(int str){this.cid=str;}


	public String getAcctype(){ return acc_type;}
	public void setAcctype(String acc_type){this.acc_type=acc_type;}

	public String getAccname(){ return acc_name;}
	public void setAccname(String acc_name){this.acc_name=acc_name;}

	public double getAmount(){ return amount;}
	public void setAmount(double amount){this.amount=amount;}

	public String getLastTrnDate(){ return trn_date;}
	public void setLastTrnDate(String trndate){this.trn_date=trndate;}

	public String getVerified(){ return verify;}
	public void setVerified(String verify){this.verify=verify;}

	public String getAccStatus(){return status;}
	public void setAccStatus(String status){this.status=status;}
	
	public double getCreditLimit(){ return creditlimit;}
	public void setCreditLimit(double amount){this.creditlimit=amount;}

	public String getLimitUpto(){return limit_upto;}
	public void setLimitUpto(String status){this.limit_upto=status;}
	
	public String getFreezeInd(){return freeze_ind;}
	public void setFreezeInd(String freeze_ind){this.freeze_ind=freeze_ind;}
	
	public String getDefaultInd(){return default_ind;}
	public void setDefaultInd(String default_ind){this.default_ind=default_ind;}
	
	public SignatureInstructionObject[] getSignatureInstruction(){return siob;}
	public void setSignatureInstruction(SignatureInstructionObject[] siob){this.siob=siob;}
	
	public double getShadowBalance(){ return shadow_balance;}
	public void setShadowBalance(double amount){this.shadow_balance=amount;}
	
	public double getPrevBalance(){ return prev_balance;}
	public void setPrevBalance(double prev_bal){this.prev_balance=prev_bal;}

	public String getInterestPayableDate(){return int_pbl_date;}
	public void setInterestPayableDate(String int_pbl_date){this.int_pbl_date=int_pbl_date;}
	
	public void setGLType(String string) {this.gl_type=string;}	
	public String getGLType() {return gl_type;}
	
	public void setGLName(String glname) {this.gl_name=glname;}	
	public String getGLName() {return gl_name;}
	
	public String getSanctionDate(){return sanc_date;}
	public void setSanctionDate(String sanc_date){this.sanc_date=sanc_date;}
	
	public String  getChequeIssued(){return chq_iss;}
	public void setChequeIssued(String chq_iss){this.chq_iss=chq_iss;}
	
}





