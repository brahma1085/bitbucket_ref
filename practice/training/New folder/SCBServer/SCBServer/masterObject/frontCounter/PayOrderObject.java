package masterObject.frontCounter;
import java.io.Serializable;
import masterObject.general.UserVerifier;

public class PayOrderObject implements Serializable
{
	public UserVerifier uv= new UserVerifier();
	String po_type,po_date,po_payee,po_favour,po_acty,po_made,po_cancel,po_stop,trn_ty,trn_mode,gl_type,gl_name;
	int backup,po_sno,po_acno,po_glcd,trn_seq,trn_key,vstarted;
	double po_amt,comm_amt;
	String trn_sou;

	int ref_sno,payord_no,po_prt_ind,po_csh_ind,po_chq_no,custtype;
	double trn_amt;
	String payord_dt,cd_ind,valid_upto,po_csh_dt,verify;
	int ref_code,sub_code,po_cust_type;
	
//----------- PO Commission Rate ----------------//
    
    private String po_from_date = null,po_to_date = null,po_comm_type = null;                   
    private double po_from_amt = 0.0,po_to_amt = 0.0,po_comm_rate = 0.0,
                   po_min_comm_amt = 0.0,po_extra_comm_rate = 0.0;
    private int po_sub_cat = 0;
    
    public void setPOFromDate(String po_from_date)
    {
       this.po_from_date = po_from_date;
    }
    
    public String getPOFromDate()
    {
       return po_from_date;
    }
    
    public void setPOToDate(String po_to_date)
    {
       this.po_to_date = po_to_date;
    }
    
    public String getPOToDate()
    {
       return po_to_date;
    }
    
    public void setPOFromAmt(double po_from_amt)
    {
       this.po_from_amt = po_from_amt;
    }
    
    public double getPOFromAmt()
    {
       return po_from_amt;
    }
    
    public void setPOToAmt(double po_to_amt)
    {
       this.po_to_amt = po_to_amt;
    }
    
    public double getPOToAmt()
    {
       return po_to_amt;
    }
    
    public void setPOCommRate(double po_comm_rate)
    {
       this.po_comm_rate = po_comm_rate;
    }
    
    public double getPOCommRate()
    {
       return po_comm_rate;
    }
    
    public void setPOCommType(String po_comm_type)
    {
       this.po_comm_type = po_comm_type;
    }
    
    public String getPOCommType()
    {
       return po_comm_type;
    }
    
    public void setPOMinCommAmt(double po_min_comm_amt)
    {
       this.po_min_comm_amt = po_min_comm_amt;
    }
    
    public double getPOMinCommAmt()
    {
       return po_min_comm_amt;
    }
    
    public void setPOSubCat(int po_sub_cat)
    {
       this.po_sub_cat = po_sub_cat;
    }
    
    public int getPOSubCat()
    {
       return po_sub_cat;
    }
    
    public void setPOExtraCommRate(double po_extra_comm_rate)
    {
       this.po_extra_comm_rate = po_extra_comm_rate;
    }
    
    public double getPOExtraCommRate()
    {
       return po_extra_comm_rate;
    }
    
    ////////////
//------------------POMake------------------

	public String getPOType(){return po_type;}
	public int getPOCustType(){return po_cust_type;}
	public int getCustType(){return custtype;}
	public String getPOMakeDate(){return po_date;}
	public String getPOPayee(){return po_payee;}
	public String getPOAccType(){return po_acty;}
	public String getPOFavour(){return po_favour;}
	
	public String getTrnSource(){return trn_sou;}
	public void setTrnSource(String s){this.trn_sou=s;}

	public String getPOMade(){return po_made;}
	public int getPOSerialNo(){return po_sno;}
	public int getPOAccNo(){return po_acno;}
	public int getPOGlCode(){return po_glcd;}
	public String getPOGlType(){return gl_type;}
	public String getPOGlName(){return gl_name;}
	public int getTransSeq(){return trn_seq;}
	public int getTransKey(){return trn_key;}
	public int getVstarted(){return vstarted;}
	public int getBackup(){return backup;}
	
	public int getGLRefCode(){return ref_code;}
	public int getGLSubCode(){return sub_code;}

	public double getPOAmount(){return po_amt;}
	public double getCommissionAmount(){return comm_amt;}

//--------------PO Link & PayOrder--

	public int getRefSerialNo(){return ref_sno;}
	public String getPOCancel(){return po_cancel;}
	public String getPOStop(){return po_stop;}
	public int getPOPrintIndicator(){return po_prt_ind;}
	public int getPOCshIndicator(){return po_csh_ind;}
	public int getPOChqNo(){return po_chq_no;}

	public double getTransAmount(){return trn_amt;}
	public String getVerified(){return verify;}

	public String getPODate(){return payord_dt;}
	public String getCDindicator(){return cd_ind;}
	public String getPOValidUpTo(){return valid_upto;}
	public String getPOCshDate(){return po_csh_dt;}

//-------------------Set methods---------------

	public int getPayOrderNo(){return payord_no;}
//---------------PO Make---------------------------
	public void setPOType(String po_type){this.po_type=po_type;}
	public void setPOCustType(int potype){this.po_cust_type=potype;}
	public void setPOMakeDate(String po_date){this.po_date=po_date;}
	public void setPOPayee(String po_payee){this.po_payee=po_payee;}
	public void setPOAccType(String po_acty){this.po_acty=po_acty;}
	public void setPOFavour(String po_favour){this.po_favour=po_favour;}

	public void setPOMade(String po_made){this.po_made=po_made;}
	public void setPOSerialNo(int po_sno){this.po_sno=po_sno;}
	public void setPOAccNo(int po_acno){this.po_acno=po_acno;}
	public void setPOGlCode(int po_glcd){this.po_glcd=po_glcd;}
	public void setPOGlType(String po_glcd){this.gl_type=po_glcd;}
	public void setPOGlName(String po_glname){this.gl_name=po_glname;}
	public void setTransSeq(int trn_seq){this.trn_seq=trn_seq;}
	public void setTransType(String trn_ty){this.trn_ty=trn_ty;}
	public String getTransType(){return trn_ty;}
	
	public void setTransMode(String trn_mode){this.trn_mode=trn_mode;}
	public String getTransMode(){return trn_mode;}
	
	public void setTransKey(int trn_key){this.trn_key=trn_key;}
	public void setVstarted(int vstarted){this.vstarted=vstarted;}
	public void setBackup(int backup){this.backup=backup;}
	
	public void setGLRefCode(int vstarted){this.ref_code=vstarted;}
	public void setGLSubCode(int backup){this.sub_code=backup;}

	public void setPOAmount(double po_amt){this.po_amt=po_amt;}
	public void setCommissionAmount(double comm_amt){this.comm_amt=comm_amt;}

//------------------PO link & PayOrder--------------

	public void setRefSerialNo(int ref_sno){this.ref_sno=ref_sno;}
	public void setPayOrderNo(int payord_no){this.payord_no=payord_no;}
	public void setPOCancel(String po_cancel){this.po_cancel=po_cancel;}
	public void setPOStop(String po_stop){this.po_stop=po_stop;}
	public void setPOPrvoidIndicator(int po_prt_ind){this.po_prt_ind=po_prt_ind;}
	public void setPOCshIndicator(int po_csh_ind){this.po_csh_ind=po_csh_ind;}
	public void setPOChqNo(int po_chq_no){this.po_chq_no=po_chq_no;}
	public void setTransAmount(double trn_amt){this.trn_amt=trn_amt;}
	public void setPODate(String payord_dt){this.payord_dt=payord_dt;}
	public void setCDindicator(String cd_ind){this.cd_ind=cd_ind;}
	public void setPOValidUpTo(String valid_upto){this.valid_upto=valid_upto;}
	public void setPOCshDate(String po_csh_dt){this.po_csh_dt=po_csh_dt;}
	public void setVerified(String ver){this.verify=ver;}	
	public void setCustType(int custtype){this.custtype=custtype;}


}
