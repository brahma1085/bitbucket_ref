package masterObject.pygmyDeposit;

import masterObject.general.SignatureInstructionObject;

public class AgentMasterObject implements java.io.Serializable
{
	private int[] array_string_addrtypes;
	private int jt_cid,int_cid,int_agentno,int_personal_acno,int_joint_acno,intrAccNo,int_joint_hldrs,int_security_cid,int_close_ind;
	private SignatureInstructionObject[] siob=null;
	private String agentType,string_appoint_date,string_personal_account,string_joint_account,string_intr_account,string_close_date,Name,string_joint_name,string_security_name,string_detml,string_deuser,string_vetml,string_veuser,fname,close_indicator,pers_acctype,jt_acctype;
	//private int[] strings;
	//private int[] agentnos;
    private int agentNumber,ref_ind,scroll_no;
    private int[] jt_cids,jt_addr_types;
    private double scroll_amoount;
    private String intrAccType;

	public int[] getJt_addr_types() {
		return jt_addr_types;
	}
	public void setJt_addr_types(int[] jt_addr_types) {
		this.jt_addr_types = jt_addr_types;
	}
	public int[] getJt_cids() {
		return jt_cids;
	}
	public void setJt_cids(int[] jt_cids) {
		this.jt_cids = jt_cids;
	}
    public int getRef_Ind(){return ref_ind;}
    public void setRef_Ind(int ref_ind){this.ref_ind=ref_ind;}
    	
    public int getAccNo(){return int_personal_acno;}

	public String getAccType(){return string_personal_account;}

	public int getAgentNo(){return int_agentno;}
    //public int[] getAgentNumbers(){return agentnos;} //To initialize
	
	public int getAgentNumber(){return agentNumber;} 
	
	public String getAgentType(){return agentType;}

	public String getAppointDate(){return string_appoint_date;}

	public int getCid(){return int_cid;}

	public String getCloseDate(){return string_close_date;}

	public int getCloseInd(){return int_close_ind;}

	public String getDeTml(){return string_detml;}

	public String getDeUser(){return string_deuser;}

	public int getJointAccNo(){return int_joint_acno;}

	public String getJointAccType(){return string_joint_account;}
	public int[] getJointAddrType() {return array_string_addrtypes;}
	public int getJointCid() {return jt_cid;}

	public int getJointHldrs(){return int_joint_hldrs;}

	public String getJointName(){return string_joint_name;}

	public String getName(){return Name;}
    
    //public int getIntrAccNo(){return int_intr_acno;}

    //public String getIntrAccType(){return string_intr_account;}

	public String getSecurityName(){return string_security_name;}
	
	public  SignatureInstructionObject[] getSigObj() {return siob;}

	public String getVeTml(){return string_vetml;}

	public String getVeUser(){return string_veuser;}
	
	
	
	public void setAccNo(int acno){this.int_personal_acno=acno ;}
	public void setAccType(String acty){this.string_personal_account=acty ;}
	public void setAgentNo(int agtno){this.int_agentno=agtno ;}
	//public void setAgentNumbers(int[] agentnos){this.agentnos=agentnos;} //To initialize
	public void  setAgentNumber(int agentno){this.agentNumber=agentno;}
	
	public void setAgentType(String agtty){this.agentType=agtty ;}
	public void setAppointDate(String aptdt){this.string_appoint_date=aptdt ;}
	public void setCid(int cid){this.int_cid=cid;}
	public void setCloseDate(String cldate){this.string_close_date=cldate ;}
	public void setCloseInd(int clind){this.int_close_ind=clind;}
	public void setDeTml(String detml){this.string_detml=detml;}
	public void setDeUser(String deuser){this.string_deuser=deuser;}
	public void setJointAccNo(int jtacno){this.int_joint_acno=jtacno ;}
	public void setJointAccType(String jtacty){this.string_joint_account=jtacty ;}
	
	public void setJointAddrType(int[] strings) {this.array_string_addrtypes=strings;}
	
	public void setJointCid(int jt_cid) {this.jt_cid=jt_cid;}
	public void setJointHldrs(int jthldrs){this.int_joint_hldrs=jthldrs ;}
	public void setJointName(String jtname){this.string_joint_name=jtname;}
	public void setName(String name){this.Name=name;}
    
    //public void setIntrAccNo(int intracno){this.int_intr_acno=intracno ;}
    //public void setIntrAccType(String intracty){this.string_intr_account=intracty ;}
	
	public void setSecurityName(String secname){this.string_security_name=secname;}

	public void setSigObj(SignatureInstructionObject[] siob) {this.siob=siob;}
	public void setVeTml(String vetml){this.string_vetml=vetml;}
	public void setVeUser(String veuser){this.string_veuser=veuser;}
   
	public int getScroll_no() {
		return scroll_no;
	}
	public void setScroll_no(int scroll_no) {
		this.scroll_no = scroll_no;
	}
	public double getScroll_amoount() {
		return scroll_amoount;
	}
	public void setScroll_amoount(double scroll_amoount) {
		this.scroll_amoount = scroll_amoount;
	}
	public void setFname(String fname){this.fname=fname;}
	public String getFname(){return fname;}
    
    public void setCloseIndicator(String close_ind){this.close_indicator=close_ind;}
    public String getCloseIndicator(){return close_indicator;}
    
    public void setPersonalAccType(String pers_acctype){this.pers_acctype=pers_acctype;}
    public String getPersonalAccType(){return pers_acctype;}
    
    public void setJtAccType(String jt_acctype){this.jt_acctype=jt_acctype;}
    public String getJtAccType(){return jt_acctype;}
	public int getInt_security_cid() {
		return int_security_cid;
	}
	public void setInt_security_cid(int int_security_cid) {
		this.int_security_cid = int_security_cid;
	}
	public String getIntrAccType() {
		return intrAccType;
	}
	public void setIntrAccType(String intrAccType) {
		this.intrAccType = intrAccType;
	}
	public int getIntrAccNo() {
		return intrAccNo;
	}
	public void setIntrAccNo(int intrAccNo) {
		this.intrAccNo = intrAccNo;
	}
	
	
	
	
	
	
 }
