package masterObject.general;
import java.io.Serializable;

public class TrfVoucherObject implements Serializable
{
    static final long serialVersionUID = 1L;//ship.....20/01/2007
    
	public UserVerifier uv=new UserVerifier();

	String voc_ty,voc_date,acc_ty,tv_prt_ind,tv_pay_ind,dd_pur_ind,pay_ac_ty,tv_pay_dt,pay_mode;
	int voc_no,ac_no,pay_ac_no;
	double trn_amount;
	SignatureInstructionObject[] signatureInstructionObjects = null;
	
	public String getVoucherType(){return voc_ty;}
	public void setVoucherType(String voc_ty){this.voc_ty=voc_ty;}

	public int getVoucherNo(){return voc_no;}
	public void setVoucherNo(int voc_no){this.voc_no=voc_no;}

	public String getVoucherDate(){return voc_date;}
	public void setVoucherDate(String voc_date){this.voc_date=voc_date;}

	public double getTrfAmount(){return trn_amount;}
	public void setTrfAmount(double trn_amount){this.trn_amount=trn_amount;}

	public String getAccType(){return acc_ty;}
	public void setAccType(String acc_ty){this.acc_ty=acc_ty;}

	public int getAccNo(){return ac_no;}
	public void setAccNo(int acno){ac_no=acno;}

	public String getTvPrtInd(){return tv_prt_ind;}
	public void setTvPrtInd(String tv_prt_ind){this.tv_prt_ind=tv_prt_ind;}

	public String getTvPayInd(){return tv_pay_ind;}
	public void setTvPayInd(String tv_pay_ind){this.tv_pay_ind=tv_pay_ind;}

	public String getDDPurInd(){return dd_pur_ind;}
	public void setDDPurInd(String dd_pur_ind){this.dd_pur_ind=dd_pur_ind;}

	public String getPayAccType(){return pay_ac_ty;}
	public void setPayAccType(String pay_ac_ty){this.pay_ac_ty=pay_ac_ty;}

	public String getTvPayDate(){return tv_pay_dt;}
	public void setTvPayDate(String tv_pay_dt){this.tv_pay_dt=tv_pay_dt;}

	public String getPayMode(){return pay_mode;}
	public void setPayMode(String pay_mode){this.pay_mode=pay_mode;}

	public int getPayAccNo(){return pay_ac_no;}
	public void setPayAccNo(int pay_ac_no){this.pay_ac_no=pay_ac_no;}
	/**
	 * @param signatureDetails
	 */
	public void setSigObj(SignatureInstructionObject[] signatureDetails) {
		// TODO Auto-generated method stub
		this.signatureInstructionObjects = signatureDetails;
		
	}
	/**
	 * @return
	 */
	public SignatureInstructionObject[]  getSigObj() {
		// TODO Auto-generated method stub
		return signatureInstructionObjects;
	}
	

}
