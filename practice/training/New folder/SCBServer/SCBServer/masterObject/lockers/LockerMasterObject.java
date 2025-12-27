package masterObject.lockers;
import masterObject.general.NomineeObject;
import masterObject.general.SignatureInstructionObject;
import masterObject.general.UserVerifier;
import java.io.Serializable;
import java.util.Vector;

public class LockerMasterObject implements Serializable
{
    static final long serialVersionUID = 1L;
    
	private String lk_ac_ty,locker_ty,locker_pw,allot_dt,mat_dt,op_inst,close_dt,rent_by,trf_acty,rent_upto,intr_ac_ty,intro_name,auto_extn,cl_de_tml,cl_de_user,cl_de_datetime,cl_ve_tml,cl_ve_user,cl_ve_datetime,addr_ty,name,timein,oper_date,oper_by,trn_dt,trn_dt_time,timeOut; 
	private String modcode,relation,type_op,time_out,security_acty,trn_type,memb_ac_ty,freeze_ind,reason,moduleCode,operBy;
	private int lk_ac_no,locker_no,no_jt_hldr,no_securities,req_mths,lst_tr_seq,memb_no,mailaddr,trf_acno,intr_ac_no,nom_regno,cid,key_no,ac_category,ac_subcategory,required_days,security_acno;
	private double rent_coll,rentColl;
	private int jcid[],jaddr[],reqdMonths,requiredDays,memberNo,nomRegNo;
	private String tyop[]=null;
	private String gl_code,ve_user,sh_code,operDate,oprInstrn,closeDate,totalRentColl,memberType;
	private NomineeObject nomobj[]=null;
	private SignatureInstructionObject siob[]=null;
	
	private String rentUpto,rentBy,addressType,transAcType,timeIn,allotDate;
	private int transAcNo;
	
	Vector deposits;//ship.....24/02/2006

	public UserVerifier uv=new UserVerifier();

	public int getLockerAcNo(){return lockerAcNo;}
	public void setLockerAcNo(int n){this.lockerAcNo=n;}
	
	public void setNomineeDetails(NomineeObject[] nom){this.nomobj=nom;}
	public NomineeObject[] getNomineeDetails(){return nomobj;}
	
	public void setSigObj(SignatureInstructionObject[] si){this.siob=si;}
	public SignatureInstructionObject[] getSigObj(){return siob;}
	
	public int getKeyNo(){return keyNo;}
	public void setKeyNo(int n){this.keyNo=n;}
	int lockerAcNo,keyNo,lockerNo;
	public int getLockerNo(){return lockerNo;}
	public void setLockerNo(int n){this.lockerNo=n;}
	
	public int getNoOfJntHldrs(){return no_jt_hldr;}
	public void setNoOfJntHldrs(int n){this.no_jt_hldr=n;}
	
	//ship......22/02/2006
	public int getNoOfSecurities(){return no_securities;}
	public void setNoOfSecurities(int s){this.no_securities=s;}
	////////////
	
	//ship.....24/02/2006
	public Vector getDeposits(){return deposits;}
	public void setDeposits(Vector surities){this.deposits=surities;}
	///////
	
	public int getReqdMonths(){return reqdMonths;}
	public void setReqdMonths(int n){this.reqdMonths=n;}
	
	public int getRequiredDays(){return requiredDays;}
	public void setRequiredDays(int n){this.requiredDays=n;}
	
	public int getLastTrnSeq(){return lst_tr_seq;}
	public void setLastTrnSeq(int n){this.lst_tr_seq=n;}
	
	public int getMemberNo(){return memberNo;}
	public void setMemberNo(int n){this.memberNo=n;}
	
	public int getTransAcNo(){return transAcNo;}//changed 13/06
	public void setTransAcNo(int n){this.transAcNo=n;}
	
	public double getRentColl(){return rentColl;}
	public void setRentColl(double n){this.rentColl=n;}
	
	public int getIntrAcNo(){return intr_ac_no;}
	public void setIntrAcNo(int n){this.intr_ac_no=n;}
	
	public int getNomRegNo(){return nomRegNo;}
	public void setNomRegNo(int n){this.nomRegNo=n;}
	
	public int getCid(){return cid;}
	public void setCid(int n){this.cid=n;}
	
	public int[] getJointCid(){return jcid;}
	public void setJointCid(int[] cid){this.jcid=cid;}
	
	public int[] getAddr(){return jaddr;}
	public void setAddr(int[] cid){this.jaddr=cid;}

	private String lockerAcType,lockerType;
	
	public String getLockerAcType(){return lockerAcType;}
	public void setLockerAcType(String n){this.lockerAcType=n;}
	
	public String getAddressType(){return addressType;}
	public void setAddressType(String n){this.addressType=n;}
	
	public String getLockerType(){ return lockerType;}
	public void setLockerType(String s){this.lockerType=s;}
	
	public String getLockerPW(){ return locker_pw;}
	public void setLockerPW(String s){this.locker_pw=s;}
	
	public String getAllotDate(){ return allotDate;}
	public void setAllotDate(String s){this.allotDate=s;}
	
	public String getMatDate(){ return mat_dt;}
	public void setMatDate(String s){this.mat_dt=s;}
	
	public String getOprInstrn(){ return oprInstrn;}
	public void setOprInstrn(String s){this.oprInstrn=s;}
	
	public String getShareCode(){ return sh_code;}
	public void setShareCode(String s){this.sh_code=s;}
	
	public String getCloseDate(){ return closeDate;}
	public void setCloseDate(String s){this.closeDate=s;}
	
	public String getRentBy(){ return rentBy;}
	public void setRentBy(String s){this.rentBy=s;}
	
	public String getTransAcType(){ return transAcType;}//changed 13/06/08
	public void setTransAcType(String s){this.transAcType=s;}
    
    public String getSecurityAcType(){ return security_acty;}
    public void setSecurityAcType(String s){this.security_acty=s;}
    
    public int getSecurityAcNo(){ return security_acno;}
    public void setSecurityAcNo(int s){this.security_acno=s;}
    
	public String getRentUpto(){ return rentUpto;}
	public void setRentUpto(String s){this.rentUpto=s;}
	
	public String getAutoExtn(){ return auto_extn;}
	public void setAutoExtn(String s){this.auto_extn=s;}
	
	public String getGLRefCode(){ return gl_code;}
	public void setGLRefCode(String s){this.gl_code=s;}
	
	public String getIntrAcTy(){ return intr_ac_ty;}
	public void setIntrAcTy(String s){this.intr_ac_ty=s;}
	
	public String getIntrName(){ return intro_name;}
	public void setIntrName(String s){this.intro_name=s;}
	
	public String getClosingDETml(){ return cl_de_tml;}
	public void setClosingDETml(String s){this.cl_de_tml=s;}
	
	public String getClosingDEUser(){ return cl_de_user;}
	public void setClosingDEUser(String s){this.cl_de_user=s;}
	
	public String getClosingDEDtTime(){ return cl_de_datetime;}
	public void setClosingDEDtTime(String s){this.cl_de_datetime=s;}
	
	public String getClosingVETml(){ return cl_ve_tml;}
	public void setClosingVETml(String s){this.cl_ve_tml=s;}
	
	public String getClosingVEUser(){ return cl_ve_user;}
	public void setClosingVEUser(String s){this.cl_ve_user=s;}
	
	public String getClosingVEDtTime(){ return cl_ve_datetime;}
	public void setClosingVEDtTime(String s){this.cl_ve_datetime=s;}
	
	public String getName(){ return name;}
	public void setName(String s){this.name=s;}
	
	public String getTimeIn(){ return timeIn;}
	public void setTimeIn(String s){this.timeIn=s;}
	
	public String getTimeOut(){ return timeOut;}
	public void setTimeOut(String s){this.timeOut=s;}
	
	public String getVerId(){ return ve_user;}
	public void setVerId(String s){this.ve_user=s;}
	
	public String getModuleCode(){ return moduleCode;}
	public void setModuleCode(String s){this.moduleCode=s;}
	
	public void setAccCategory(int ac_category){this.ac_category=ac_category;}
	public int getAccCategory(){return ac_category;}
	
	public void setAccSubCategory(int ac_subcategory){this.ac_subcategory=ac_subcategory;}
	public int getAccSubCategory(){return ac_subcategory;}
	
	public int getMailingAddress(){return mailaddr;}
	public void setMailingAddress(int cid){this.mailaddr=cid;}
	
	public String getRelation(){return relation;}
	public void setRelation(String n){this.relation=n;}
	
	public void setTypeofOp(String[] op){this.tyop=op;}
	public String[] getTypeofOp(){return tyop;}
	
	public void setTypeOfOperation(String type_op){this.type_op=type_op;}
	public String getTypeOfOperation(){return type_op;}
    
    public String getOperDate(){ return operDate;}
    public void setOperDate(String s){this.operDate=s;}
    
    public String getOperBy(){ return operBy;}
    public void setOperBy(String s){this.operBy=s;}
    
    //ship.....31/03/2006
    public String getTrnType(){ return trn_type;}
	public void setTrnType(String s){this.trn_type=s;}
    ////////////
	
	//ship.....12/04/2006
	public String getMemberType(){return memberType;}
	public void setMemberType(String n){this.memberType=n;}
	/////////
	
	//ship......04/07/2006
	public String getTrnDate(){ return trn_dt;}
	public void setTrnDate(String s){this.trn_dt=s;}
	
	public String getTrnDateTime(){ return trn_dt_time;}
	public void setTrnDatetime(String s){this.trn_dt_time=s;}
	//////////
	
	//ship......26/07/2006
	public String getFreezeInd(){return freeze_ind;}
	public void setFreezeInd(String s){this.freeze_ind=s;}
	
	public String getReason(){return reason;}
	public void setReason(String s){this.reason=s;}
	//////////////
	public String getTotalRentColl() {
		return totalRentColl;
	}
	public void setTotalRentColl(String totalRentColl) {
		this.totalRentColl = totalRentColl;
	}
}
