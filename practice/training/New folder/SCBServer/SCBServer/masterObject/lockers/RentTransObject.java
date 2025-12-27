package masterObject.lockers;
import masterObject.general.UserVerifier;

import java.io.Serializable;

public class RentTransObject implements Serializable
{
    static final long serialVersionUID = 1L;
    
	private String lk_ac_ty,locker_ty,trn_dt,trn_sou,trn_mode,trf_acty,name;
	private int lk_ac_no,locker_no,trf_acno,vno;
	private double rent;
	private String addr;

	public UserVerifier uv=new UserVerifier();

	public int getLockerAcNo(){return lk_ac_no;}
	public void setLockerAcNo(int n){this.lk_ac_no=n;}
	
	public String getAddress(){ return addr;}
	public void setAddress(String s){this.addr=s;}
	
	public int getLockerNo(){return locker_no;}
	public void setLockerNo(int n){this.locker_no=n;}
	
	public String getLockerType(){return locker_ty;}
	public void setLockerType(String n){this.locker_ty=n;}
	
	public String getLockerAcType(){return lk_ac_ty;}
	public void setLockerAcType(String n){this.lk_ac_ty=n;}

	public String getTrnSource(){ return trn_sou;}
	public void setTrnSource(String s){this.trn_sou=s;}
	
	public String getName(){ return name;}
	public void setName(String s){this.name=s;}
	
	public String getTrnMode(){ return trn_mode;}
	public void setTrnMode(String s){this.trn_mode=s;}
	
	public String getTrnDate(){ return trn_dt;}
	public void setTrnDate(String s){this.trn_dt=s;}
	
	public String getTrfAcType(){ return trf_acty;}
	public void setTrfAcType(String s){this.trf_acty=s;}
	
	public int getTrfAcNo(){ return trf_acno;}
	public void setTrfAcNo(int s){this.trf_acno=s;}
	
	public int getTrfVoucherNo(){ return vno;}
	public void setTrfVoucherNo(int s){this.vno=s;}
	
	public double getRentAmt(){ return rent;}
	public void setRentAmt(double s){this.rent=s;}
	
	

}


