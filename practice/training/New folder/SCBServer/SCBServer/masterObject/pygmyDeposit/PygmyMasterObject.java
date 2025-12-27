package masterObject.pygmyDeposit;
import masterObject.general.NomineeObject;
import masterObject.general.SignatureInstructionObject;
public class PygmyMasterObject implements java.io.Serializable
{
	private int ref_ind,accNo,jthldr,nomno,lsttrnseq,lstwdno,lnacno,payacno,cid,addtype;
	private String nom_details,accType,agty,acstat,lstintrdt,lstwddt,lnavld,lnacty,paymode,payacty,accOpenDate,acclosedt,ldgrprtdt;
	private int[] array_string_addrtypes;
	private String name,agentName,address,city,state,pin,selectedone;
	private NomineeObject nom[]=null;
	private SignatureInstructionObject[] siob;
	private int[] strings;
	private double wdamt,prevBalance;
	private String agentNo,pay_acc_name,openDt,modulecode;
	
	public String getAccCloseDate(){return acclosedt;}

	public int getAccNo(){return accNo;}

	public String getAccOpenDate(){return accOpenDate;}

	public String getAccStatus(){return acstat;}

	public String getAccType(){return accType;}

	public int getAddrType(){return addtype;}

	public String getAgentName(){return agentName;}

	//public int getAgentNo(){return agno;}
	 
	public String getAgentNo(){return agentNo;}

	public String getAgentType(){return agty;}

	public int getCid(){return cid;}
	public int[] getJointAddrType() {return array_string_addrtypes;}
	public int[] getJointCid() {return strings;}

	public int getJointHolders(){return jthldr;}

	public String getLastIntrDate(){return lstintrdt;}

	public int getLastTrnSeq(){return lsttrnseq;}

	public String getLastWDDate(){return lstwddt;}//WithDrawal Date

	public int getLastWDNo(){return lstwdno;}//WithDrawal Number

	public String getLdgrPrtDate(){return ldgrprtdt;}

	public int getLnAccNo(){return lnacno;}

	public String getLnAccType(){return lnacty;}

	public String getLnAvailed(){return lnavld;}

	public String getName(){return name;}
	public NomineeObject[] getNomineeDetails(){return nom;}

	public int getNomineeNo(){return nomno;}

	public int getPayAccNo(){return payacno;}

	public String getPayAccType(){return payacty;}

	public String getPayMode(){return paymode;}
	public  SignatureInstructionObject[] getSigObj() {return siob;}

	public double getWDAmt(){return wdamt;}//withdrawal Amount
	public double getPrevBalance(){return prevBalance;}
	
	public String getAddress(){return address;}
	
	
	public void setAccCloseDate(String acclosedt){this.acclosedt=acclosedt;}
	public void setAccNo(int acno){this.accNo=acno;}
	public void setAccOpenDate(String acopendt){this.accOpenDate=acopendt;}
	public void setAccStatus(String acstat){this.acstat=acstat;}
	public void setAccType(String acty){this.accType=acty;}
	public void setAddrType(int addtype){this.addtype=addtype;}
	public void setAgentName(String aname){this.agentName=aname;}
	//public void setAgentNo(int agno){this.agno=agno;}
	
	public void setAgentNo(String agentno){this.agentNo=agentno;}
	public void setAgentType(String agty){this.agty=agty;}
	public void setCid(int cid){this.cid=cid;}
	
	public void setJointAddrType(int[] strings) {this.array_string_addrtypes=strings;}
	
	public void setJointCid(int[] strings) {this.strings=strings;}
	public void setJointHolders(int jthldr){this.jthldr=jthldr;}
	public void setLastIntrDate(String lstintrdt){this.lstintrdt=lstintrdt;}
	public void setLastTrnSeq(int lsttrnseq){this.lsttrnseq=lsttrnseq;}
	public void setLastWDDate(String lstwddt){this.lstwddt=lstwddt;}//WithDrawal Date
	public void setLastWDNo(int lstwdno){this.lstwdno=lstwdno;}//WithDrawal Number
	public void setLdgrPrtDate(String ldgrprtdt){this.ldgrprtdt=ldgrprtdt;}
	public void setLnAccNo(int lnacno){this.lnacno=lnacno;}
	public void setLnAccType(String lnacty){this.lnacty=lnacty;}
	public void setLnAvailed(String lnavld){this.lnavld=lnavld;}
	public void setName(String name){this.name=name;}

	public void setNomineeDetails(NomineeObject nom[]){this.nom=nom;}
	public void setNomineeNo(int nomno){this.nomno=nomno;}
	public void setPayAccNo(int payacno){this.payacno=payacno;}
	public void setPayAccType(String payacty){this.payacty=payacty;}
	public void setPayMode(String paymode){this.paymode=paymode;}
	
	public void setSigObj(SignatureInstructionObject[] siob) {this.siob=siob;}
	public void setWDAmt(double wdamt){this.wdamt=wdamt;}//withdrawal Amount	
	public void setPrevBalance(double prevBalance) {this.prevBalance=prevBalance;}
	
	public void setAddress(String address){this.address=address;}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
	public String getNom_details() {
		return nom_details;
	}
	public void setNom_details(String nom_details) {
		this.nom_details = nom_details;
	}
	public int getRef_ind() {
		return ref_ind;
	}
	public void setRef_ind(int ref_ind) {
		this.ref_ind = ref_ind;
	}
    public String getModulecode(){return modulecode;}
    public void setModulecode(String modulecode){this.modulecode=modulecode;}
	
	public String getPay_acc_name() {
		return pay_acc_name;
	}
		public void setPay_acc_name(String pay_acc_name) {
		this.pay_acc_name = pay_acc_name;
	}
		public void setOpenDt(String opendate){ this.openDt=opendate;}
		public String getOpenDt(){return openDt;}

		public String getSelectedone() {
			return selectedone;
		}

		public void setSelectedone(String selectedone) {
			this.selectedone = selectedone;
		}   
		
}