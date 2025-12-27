/*
 * Created on Sep 18, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package masterObject.general;

import java.io.Serializable;
import java.util.Vector;
 

/**
 * @author Murugesh
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ModuleAdminObject implements Serializable
{
    static final long serialVersionUID = 1L;//ship.....20/01/2007
    
public String[] modulevalues ;
    
	public PygmyObject pygmy_obj=null;
	public GLPostObject GLpost[];
	public Vector GLPostVector = new Vector();
	public GLKeyParamObject[] GLkeyparam=null; 
	public GLTransactionType GLtrntypes=null;
	public UserVerifier uv=null;
	private String module_abbr=null,module_desc=null,module_code=null;
	
	private String userID = null,userTml = null,tranDateTime = null;//ship......23/11/2006
	
	public LockerObject locker_obj = null;//ship......21/11/2006

	public ModuleAdminObject()
	{
		
		pygmy_obj = new PygmyObject();
		
		locker_obj = new LockerObject();//ship......21/11/2006
		
		uv = new UserVerifier();
		
		GLtrntypes = new GLTransactionType();
	}
	
	public String getModuleAbbr(){return module_abbr;}	
	public void setModuleAbbr(String module_abbr){this.module_abbr=module_abbr;}
	
	public String getModuleDesc(){return module_desc;}
	public void setModuleDesc(String module_desc){this.module_desc=module_desc;}
	
	public String getModuleCode(){return module_code;}
	public void setModuleCode(String module_code){this.module_code=module_code;}
	
	//ship......23/11/2006
	public String getUserID(){return userID;}	
	public void setUserID(String userID){this.userID=userID;}
	
	public String getUserTml(){return userTml;}	
	public void setUserTml(String userTml){this.userTml=userTml;}
	
	public String getTranDateTime(){return tranDateTime;}	
	public void setTranDateTime(String tranDateTime){this.tranDateTime=tranDateTime;}
	/////////////
	public void setModulesValues(String[] str){
		modulevalues = str;
	}
	
	public String[] getModulesValues(){
		return modulevalues;
	}
	
	
	
	public GLKeyParamObject[] getGLKeyParaInstance(int row)
	{
		this.GLkeyparam = new GLKeyParamObject[row];
		return GLkeyparam;
	}
	public GLPostObject[] getGLPostInstance(int row){
		this.GLpost=new GLPostObject[row];
		return this.GLpost;
	}
	
	public class PygmyObject implements Serializable
	{		
		private double comm_rate,sec_dep_rate,max_amount,min_amount;
		private String agent_type,ac_type,part_withdraw;
		private int penalty_rate,min_period,pygmy_rate,no_jtholders;
		
		public int getMinPeriod(){return min_period;}
		public void setMinPeriod(int min_period){this.min_period=min_period;}
		
		public double getCommisionRate(){return this.comm_rate;}
		public void setCommisionRate(double comm_rate){this.comm_rate=comm_rate;}
		
		public double getSecDepositRate(){return this.sec_dep_rate;}
		public void setSecDepositRate(double sec_dep_rate){this.sec_dep_rate=sec_dep_rate;}
		
		public String getAgentType(){return agent_type;}
		public void setAgentType(String agent_type){this.agent_type=agent_type;}
		
	    public void setMaxAmount(double max_amount){this.max_amount=max_amount;}
		public double getMaxAmount(){return max_amount;}
		   
		public void setMinAmount(double min_amount){this.min_amount=min_amount;}
		public double getMinAmount(){return min_amount;}
		   
		public void setAcType(String ac_type){this.ac_type=ac_type;}	
		public String getAcType(){return ac_type;}
		
		public void setPenaltyRate(int penalty_rate){this.penalty_rate=penalty_rate;}
		public int getPenaltyRate(){return penalty_rate;}
		  
		public void setPartWithdraw(String part_withdraw){this.part_withdraw=part_withdraw;}
		public String getPartWithdraw(){return part_withdraw;}
		
		public void setPygmyRate(int pygmy_rate){this.pygmy_rate=pygmy_rate;}
		public int getPygmyRate(){return pygmy_rate;}
		
		public void setNoJtHolders(int no_jtholders){this.no_jtholders=no_jtholders;}
		public int getNoJtHolders(){return no_jtholders;}
	}
	
	//ship......21/11/2006
	public class LockerObject implements Serializable
	{
	    private int lk_acc_no,max_renewal_days,min_period,max_period,max_jt_hldrs;
	    private String security_acc = null;
	    
	    public void setLkAccNo(int lk_acc_no){this.lk_acc_no=lk_acc_no;}
		public int getLkAccNo(){return lk_acc_no;}
		
		public void setMaxRenewalDays(int max_renewal_days){this.max_renewal_days=max_renewal_days;}
		public int getMaxRenewalDays(){return max_renewal_days;}
		
		public void setMinPeriod(int min_period){this.min_period=min_period;}
		public int getMinPeriod(){return min_period;}
		
		public void setMaxPeriod(int max_period){this.max_period=max_period;}
		public int getMaxPeriod(){return max_period;}
		
		public void setMaxNoOfJtHldrs(int max_jt_hldrs){this.max_jt_hldrs=max_jt_hldrs;}
		public int getMaxNoOfJtHldrs(){return max_jt_hldrs;}
		
		public void setDepositMandatory(String security_acc){this.security_acc=security_acc;}
		public String isDepositMandatory(){return security_acc;}
	}
	//////////////
	
	/**
	 * Common to all Modules
	 */
	public class GLPostObject implements Serializable
	{
		private String ac_type=null,gl_type=null,trn_type=null,cd_ind=null;
		private int multipl_by,gl_code;
		
		public String getAccType(){return ac_type;}
		public void setAccType(String ac_type){this.ac_type=ac_type;}
		
		public String getGLType(){return gl_type;}
		public void setGLType(String gl_type){this.gl_type=gl_type;}
		
		public String getTrnType(){return trn_type;}
		public void setTrnType(String trn_type){this.trn_type=trn_type;}
		
		public String getCDInd(){return cd_ind;}
		public void setCDInd(String cd_ind){this.cd_ind=cd_ind;}
		
		public int getMultiplyBy(){return multipl_by;}
		public void setMultiplyBy(int multipl_by){this.multipl_by=multipl_by;}
		
		public int getGLCode(){return gl_code;}
		public void setGLCode(int gl_code){this.gl_code=gl_code;}
	}
	
	public class GLKeyParamObject implements Serializable
	{
		private String ac_type,key_desc,gl_type;
		private int code,gl_code;
		
		public String getAccType(){return ac_type;}
		public void setAccType(String ac_type){this.ac_type=ac_type;}
		
		public String getGLType(){return gl_type;}
		public void setGLType(String gl_type){this.gl_type=gl_type;}
		
		public String getKeyDesc(){return key_desc;}
		public void setKeyDesc(String key_desc){this.key_desc=key_desc;}
		
		public int getCode(){return code;}
		public void setCode(int code){this.code=code;}
		
		public int getGLCode(){return gl_code;}
		public void setGLCode(int gl_code){this.gl_code=gl_code;}
	}
	
	public class GLTransactionType implements Serializable
	{
		private Vector trn_type,trn_desc;
		
		public Vector getTrnType(){return trn_type;}
		public void setTrnType(Vector trn_type){this.trn_type=trn_type;}
		
		public Vector getTrnDesc(){return trn_desc;}
		public void setTrnDesc(Vector trn_desc){this.trn_desc=trn_desc;}
		
		public int numberOfTrnTypes(){ return trn_type==null?0:trn_type.size(); }
	}
	
	
	}
