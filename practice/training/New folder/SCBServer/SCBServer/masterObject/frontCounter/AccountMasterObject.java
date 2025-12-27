package masterObject.frontCounter;
import java.io.Serializable;
import masterObject.general.Address;
import masterObject.general.NomineeObject;
import masterObject.general.SignatureInstructionObject;
import masterObject.general.UserVerifier;

public class AccountMasterObject implements Serializable
{

	public Address addr=new Address();
	public UserVerifier uv=new UserVerifier();

	String ac_ty,ac_type,last_trndate,int_pbl_date,int_rbl_date,Intr_name,intrAccType,Nom_name,Relation,ac_status,id_issue_date,ch_bk_issue,ac_opendate,ac_closedate,acc_ty,trn_ty,trn_mode,trn_source,cd_ind,trn_narr,payee_nm,trn_date,chq_dd_date,def_ind,frz_ind,frz_reason;
	int accNo,no_jt_hldrs,last_trn_seq,ps_bk_seq,ldg_seq,intr_acc_no,od_int_rate,cc_com_rt,fr_min_bal,no_id_issue,last_line,trn_key,nom_reg_no,cid,trn_seq_no;
	int acc_no,ch_dd_no,ref_no,ldg_page,backup,acc_type,ac_category,ca_type;
	double trn_amount,cl_bal,pcl_bal;
	String ver,accname;
	String Sac_ty,name,desg,type_op;
	int addr_type;
	String photo,sign;
	int Sac_no,min_lmt,max_lmt,norows,check;
	int cids[];
	int j_addtype[]=null;
	public String tyop[]=null;
	int glref_code;
	NomineeObject nomobj[]=null;
	
	String newacctype;//Added by mohsin
	SignatureInstructionObject siob[]=null;
//Account Master Table
	
	public void setNomineeDetails(NomineeObject[] nom){this.nomobj=nom;}
	public NomineeObject[] getNomineeDetails(){return nomobj;}
	
	public void setSigObj(SignatureInstructionObject[] si){this.siob=si;}
	public SignatureInstructionObject[] getSigObj(){return siob;}


	public String getAccName(){return name;}
	public int getNoRows(){return norows;}	
	public int getGLRefCode(){return glref_code;}	
	public void setGLRefCode(int code){this.glref_code=code;}	
	public String getChqBKIssue(){return ch_bk_issue;}	
	public void setNoRows(int norows){this.norows=norows;}	
	public int getNoOfJointHolders(){return no_jt_hldrs;}
	public int getIntrAccNo(){return intr_acc_no;}
	public int getAccNo(){return accNo;}
	public int getNom_regno(){return nom_reg_no;}
	public int getCid(){return cid;}
	public int getCidLength(){return cids.length;}
	public int[] getJointCid(){return cids;}
	public void setJointCid(int[] cid){this.cids=cid;}

	public int getLastTrnSeq(){return last_trn_seq;}
	public int  getPassBkSeq(){return ps_bk_seq;}
	public int  getLedgerSeq(){return ldg_seq;}
	public int  getOdRate(){return od_int_rate;}	
	public int  getCcCommRate(){return cc_com_rt;}
	public int  getFreezeMinBal(){return fr_min_bal;}
	public int  getNoIdIssue(){return no_id_issue;}
	public int  getLastLine(){return last_line;}
	public int  getTrnKey(){return trn_key;}
	public String getDefaultInd(){return def_ind;}
	public String getFreezeInd(){return frz_ind;}
	public int getCurrentAccType(){return ca_type;}
	public void setCurrentAccType(int ca_type){this.ca_type=ca_type;}

	public String getVerified(){return ver;}
	public int getMailingAddress(){return addr_type;}
	public int getAccountType(){return acc_type;}
	public String getIntroducerName(){return Intr_name;}
	public String getIntrAccType(){return intrAccType;}
	public String getNomineeName(){return Nom_name;}
	public String getRelation(){return Relation;}
	public String getAccType(){return ac_ty;}
	public int getCurrenAccType(){return ca_type;}

	public int getAccCategory(){return ac_category;}
	public String getLastTrnDate(){return last_trndate;}
	public String getIntrstPayDate(){return int_pbl_date;}
	public String getIntrstRecvDate(){return int_rbl_date;}
	public String getAccStatus(){return ac_status;}
	public String getIdIssueDate(){return id_issue_date;}
	public String getAccOpenDate(){return ac_opendate;}
	public String getAccCloseDate(){return ac_closedate;}
	public String getFreezeReason(){return frz_reason;}

//Accounttransaction table
	public double getTransAmount(){return trn_amount;}
	
	public void setTransAmount(double trn_amount){this.trn_amount=trn_amount;}
	
	public int getRef_No(){return ref_no;}
	public void setRef_No(int ref_no){this.ref_no=ref_no;}
	
	public double getPreCloseBal(){return pcl_bal;}
	public double getCloseBal(){return cl_bal;}
	
	public void setCloseBal(double cl_bal){this.cl_bal=cl_bal;}	
	public void setPreCloseBal(double pcl_bal){this.pcl_bal=pcl_bal;}
	
	public void setTransSource(String trn_source){this.trn_source=trn_source;}
	public String getTransSource(){return trn_source;}





//--------------------------------------get over----set started-------------------------------
	
	public void setAccName(String name){this.name=name;}
	public void setChqBKIssue(String ch_bk_issue){this.ch_bk_issue=ch_bk_issue;}
	public void setNom_regno(int nom_reg_no){this.nom_reg_no=nom_reg_no;}
	public void setCid(int cid){this.cid=cid;}
//	public void setCidLength(int len){this.cids=new String[len];}
	public void setTypeofOp(String[] op){this.tyop=op;}
	public void setJointAddrType(int[] op){this.j_addtype=op;}
	public String[] getTypeofOp(){return tyop;}
	public int[] getJointAddrType(){return j_addtype;}
//	public void setCid(int index,String id){this.cids[index]=id;}
	public void setNoOfJointHolders(int no_jt_hldrs){this.no_jt_hldrs=no_jt_hldrs;}
	public void setIntrAccNo(int intr_acc_no){this.intr_acc_no=intr_acc_no;}
	public void setAccNo(int ac_no){this.accNo=ac_no;}

	public void setLastTrnSeq(int last_trn_seq){this.last_trn_seq= last_trn_seq;}
	public void  setPassBkSeq(int ps_bk_seq){this.ps_bk_seq=ps_bk_seq;}
	public void  setLedgerSeq(int ldg_seq){this.ldg_seq=ldg_seq;}
	public void  setOdRate(int od_int_rate){this.od_int_rate=od_int_rate;}	
	public void  setCcCommRate(int cc_com_rt){this.cc_com_rt=cc_com_rt;}
	public void  setFreezeMinBal(int fr_min_bal){this.fr_min_bal=fr_min_bal;}
	public void  setNoIdIssue(int no_id_issue){this.no_id_issue=no_id_issue;}
	public void  setLastLine(int last_line){this.last_line=last_line;}
	public void  setTrnKey(int trn_key){this.trn_key=trn_key;}
	public void  setDefaultInd(String def_ind){this.def_ind=def_ind;}
	public void  setFreezeInd(String frz_ind){this.frz_ind=frz_ind;}



	public void setVerified(String ver){this.ver=ver;}
	public void setMailingAddress(int addr_type){this.addr_type=addr_type;}
	public void setIntroducerName(String Intr_name){this.Intr_name=Intr_name;}
	public void setIntrAccType(String Intr_acc_type){this.intrAccType= Intr_acc_type;}
	public void setNomineeName(String Nom_name){this.Nom_name=Nom_name;}
	public void setRelation(String Relation){this.Relation=Relation;}
	public void setAccType(String ac_ty){this.ac_ty=ac_ty;}
	public void setCurrenAccType(int ca_type){this.ca_type=ca_type;}

	public void setAccCategory(int ac_category){this.ac_category=ac_category;}
	public void setAccountType(int acc_type){this.acc_type=acc_type;}
	public void setLastTrnDate(String last_trndate){this.last_trndate=last_trndate;}
	public void setIntrstPayDate(String int_pbl_date){this.int_pbl_date=int_pbl_date;}
	public void setIntrstRecvDate(String int_rbl_date){this.int_rbl_date=int_rbl_date;}
	public void setAccStatus(String  ac_status){this.ac_status= ac_status;}
	public void setIdIssueDate(String id_issue_date){this.id_issue_date=id_issue_date;}
	public void setAccOpenDate(String ac_opendate){this.ac_opendate=ac_opendate;}
	public void setAccCloseDate(String ac_closedate){this.ac_closedate=ac_closedate;}
	public void setFreezeReason(String frz_reason){this.frz_reason=frz_reason;}
	public String getNewacctype() {
		return newacctype;
	}
	public void setNewacctype(String newacctype) {
		this.newacctype = newacctype;
	}
	public int getCheck() {
		return check;
	}
	public void setCheck(int check) {
		this.check = check;
	}


}
