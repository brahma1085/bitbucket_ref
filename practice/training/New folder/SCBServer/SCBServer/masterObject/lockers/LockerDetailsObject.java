package masterObject.lockers;
import masterObject.general.UserVerifier;

import java.io.Serializable;

public class LockerDetailsObject implements Serializable
{
    static final long serialVersionUID = 1L;
    
	private String lockerType,lk_ty,desc,date_fr,date_to,lk_st,sieze,lk_acty,trn_dt,length,height,depth,master_key;
	private double rate,security_deposit;
	private int locker_no,key_no,rowno,cols,cab_no,lk_acno;
	private int row_no,col_no,days_fr,days_to;

	
	
	public UserVerifier uv=new UserVerifier();
	
	public String getLockerType(){return lockerType;}
	public void setLockerType(String n){this.lockerType=n;}
	
	public String getLockerAcType(){return lk_acty;}
	public void setLockerAcType(String n){this.lk_acty=n;}
	
	public String getDescription(){return desc;}
	public void setDescription(String n){this.desc=n;}

	public String getDateFrom(){ return date_fr;}
	public void setDateFrom(String s){this.date_fr=s;}
	
	public String getDateTo(){ return date_to;}
	public void setDateTo(String s){this.date_to=s;}
    
    public int getDaysFrom(){ return days_fr;}
    public void setDaysFrom(int s){this.days_fr=s;}
    
    public int getDaysTo(){ return days_to;}
    public void setDaysTo(int s){this.days_to=s;}
    
	public String getLockerStatus(){ return lk_st;}
	public void setLockerStatus(String s){this.lk_st=s;}
	
	public String getSiezeInd(){ return sieze;}
	public void setSiezeInd(String s){this.sieze=s;}
	
	public int getLockerNo(){return locker_no;}
	public void setLockerNo(int n){this.locker_no=n;}
	
	public int getLockerAcNo(){return lk_acno;}
	public void setLockerAcNo(int n){this.lk_acno=n;}
	
	public int getKeyNo(){return key_no;}
	public void setKeyNo(int n){this.key_no=n;}
	
	public String getMasterKey(){return master_key;}
	public void setMasterKey(String n){this.master_key=n;}
	
	public int getRowNo(){return rowno;}
	public void setRowNo(int n){this.rowno=n;}
	
	public int getRowNum(){return row_no;}
	public void setRowNum(int n){this.row_no=n;}
	
	public int getColNo(){return col_no;}
	public void setColNo(int n){this.col_no=n;}
	
	public int getCols(){return cols;}
	public void setCols(int n){this.cols=n;}
	
	public int getCabNo(){return cab_no;}
	public void setCabNo(int n){this.cab_no=n;}
	
	public double getLockerRate(){return rate;}
	public void setLockerRate(double n){this.rate=n;}
	
	public double getSecurityDeposit(){return security_deposit;}
	public void setSecurityDeposit(double n){this.security_deposit=n;}
	
    //ship.....14/02/2007
	/*public double getLockerLength(){return length;}
	public void setLockerLength(double n){this.length=n;}
	
	public double getLockerHeight(){return height;}
	public void setLockerHeight(double n){this.height=n;}

	public double getLockerDepth(){return depth;}
	public void setLockerDepth(double n){this.depth=n;}*/
    
    public String getLockerLength(){return length;}
    public void setLockerLength(String n){this.length=n;}
    
    public String getLockerHeight(){return height;}
    public void setLockerHeight(String n){this.height=n;}

    public String getLockerDepth(){return depth;}
    public void setLockerDepth(String n){this.depth=n;}
    //////////////

	//ship......04/07/2006
	public String getTrnDate(){return trn_dt;}
	public void setTrnDate(String s){this.trn_dt=s;}
	////////////
}