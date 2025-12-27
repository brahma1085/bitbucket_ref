package masterObject.generalLedger;
import java.io.Serializable;


public class DayBookObject implements Serializable
{

	String gl_name,flag_net,mthgl_name,mth_date;
	int gl_code,temp_glcode,br_code,cash_glcode;
	double dr_cash,dr_clear,dr_trans,cr_cash,cr_clear,cr_trans,cr_total,dr_total,net,dr_close,cr_close,cash_openbal,close_bal,cash_closebal;
	double mth_total,mthopen_bal,mth_drdiff,mth_crdiff,cashopen_bal,cash_subtotal;
	String trn_date=null,gl_type=null,br_shnm;
	String abbr=null;
    double consol_closebal=0.0,dr_cash_subtotal=0.0,dr_clear_subtotal=0.0,dr_trans_subtotal=0.0,dr_total_subtotal=0.0,cr_cash_subtotal=0.0,cr_clear_subtotal=0.0,cr_trans_subtotal=0.0,cr_total_subtotal=0.0;
    double net_subtotal=0.0,consolopen_bal=0.0; 
    
	public int  getgl_code(){return gl_code;}
	public void setgl_code(int gl_code){this.gl_code=gl_code;}

	public String getgl_name(){return gl_name;}
	public void setgl_name(String gl_name){this.gl_name=gl_name;}

	public double getdr_cash(){return dr_cash;}
	public void setdr_cash(double dr_cash){this.dr_cash=dr_cash;}

	public double getdr_clear(){return dr_clear;}
	public void setdr_clear(double dr_clear){this.dr_clear = dr_clear;}

	public double  getdr_trans(){return dr_trans;}
	public void setdr_trans(double dr_trans){this.dr_trans=dr_trans;}

	public double getcr_cash(){return cr_cash;}
	public void setcr_cash(double cr_cash){this.cr_cash=cr_cash;}

	public double getcr_clear(){return cr_clear;}
	public void setcr_clear(double cr_clear){this.cr_clear = cr_clear;}

	public double  getcr_trans(){return cr_trans;}
	public void setcr_trans(double cr_trans){this.cr_trans=cr_trans;}

	public double  getcr_total(){return cr_total;}
	public void setcr_total(double cr_total){this.cr_total=cr_total;}

	public double  getdr_total(){return dr_total;}
	public void setdr_total(double dr_total){this.dr_total=dr_total;}

	public double  get_net(){return net;}
	public void set_net(double net){this.net=net;}

	public String getflag_net(){return flag_net;}
	public void setflag_net(String flag_net){this.flag_net=flag_net;}

	public double getdr_close(){return dr_close;}
	public void setdr_close(double dr_close){this.dr_close=dr_close;}

	public double getcr_close(){return cr_close;}
	public void setcr_close(double cr_close){this.cr_close=cr_close;}
	
	public void setclose_bal(double close_bal){this.close_bal=close_bal;}
	public double getclose_bal(){return close_bal;}

	public double getcash_openbal(){return cash_openbal;}
	public void setcash_openbal(double cash_openbal){this.cash_openbal=cash_openbal;}

	public int getmthgl_code(){return temp_glcode;}
	public void setmthgl_code(int  temp_glcode){this.temp_glcode=temp_glcode;}

	public String  getmthgl_name(){return mthgl_name;}
	public void setmthgl_name(String  mthgl_name){this.mthgl_name=mthgl_name;}

	public double getmthopen_bal(){return mth_total;}
	public void setmthopen_bal(double mth_total){this.mth_total=mth_total;}

	public String getmthdate(){return mth_date;}
	public void setmthdate(String mth_date){this.mth_date=mth_date;}

	public double getmthdr(){return mth_drdiff;}
	public void setmthdr(double mth_drdiff){this.mth_drdiff=mth_drdiff;}

	public double getmthcr(){return mth_crdiff;}
	public void setmthcr(double mth_crdiff){this.mth_crdiff=mth_crdiff;}
	
	public String getTransactionDate(){return trn_date;}
	public void setTransactionDate(String trn_date){this.trn_date = trn_date;}

	public String getGLType(){return gl_type;}
	public void setGLType(String gl_type){this.gl_type = gl_type;}
	
	public int getbr_code(){return br_code;}
	public void setbr_code(int br_code){this.br_code=br_code;}
	
	public double getcash_closebal(){return cash_closebal;}
	public void setcash_closebal(double cash_closebal){this.cash_closebal=cash_closebal;}
	
	public String getbr_shnm(){return br_shnm;}
	public void setbr_shnm(String br_shnm){this.br_shnm=br_shnm;}
	
	public double getcashopen_bal(){return cashopen_bal;}
	public void setcashopen_bal(double cashopen_bal){this.cashopen_bal=cashopen_bal;}
	
	public double getcash_subtotal(){return cash_subtotal;}
	public void setcash_subtotal(double cash_subtotal){this.cash_subtotal=cash_subtotal;}
	
	public String getgl_type(){return gl_type;}
	public void setgl_type(String gl_type){this.gl_type=gl_type;}
	
	public int getcash_glcode(){return cash_glcode;}
	public void setcash_glcode(int cash_glcode){this.cash_glcode=cash_glcode;}
	
	public String getgl_abbr(){return abbr;}
	public void setgl_abbr(String abbr){this.abbr=abbr;}
	
	public double getcashclose_bal(){return cash_closebal;}
	public void setcashclose_bal(double cash_closebal){this.cash_closebal=cash_closebal;}
	
	public double getconsolclose_bal(){return consol_closebal;}
	public void setconsolclose_bal(double consol_closebal){this.consol_closebal=consol_closebal;}
	
	public double getdr_cash_subtotal(){return dr_cash_subtotal;}
	public void setdr_cash_subtotal(double dr_cash_subtotal){this.dr_cash_subtotal=dr_cash_subtotal;}
	
	public double getdr_clear_subtotal(){return dr_clear_subtotal;}
	public void setdr_clear_subtotal(double dr_clear_subtotal){this.dr_clear_subtotal=dr_clear_subtotal;}
	
	public double getdr_trans_subtotal(){return dr_trans_subtotal;}
	public void setdr_trans_subtotal(double dr_trans_subtotal){this.dr_trans_subtotal=dr_trans_subtotal;}
	
	public double getdr_total_subtotal(){return dr_total_subtotal;}
	public void setdr_total_subtotal(double dr_total_subtotal){this.dr_total_subtotal=dr_total_subtotal;}
	
	public double getcr_cash_subtotal(){return cr_cash_subtotal;}
	public void setcr_cash_subtotal(double cr_cash_subtotal){this.cr_cash_subtotal=cr_cash_subtotal;}
	
	public double getcr_clear_subtotal(){return cr_clear_subtotal;}
	public void setcr_clear_subtotal(double cr_clear_subtotal){this.cr_clear_subtotal=cr_clear_subtotal;}
	
	public double getcr_trans_subtotal(){return cr_trans_subtotal;}
	public void setcr_trans_subtotal(double cr_trans_subtotal){this.cr_trans_subtotal=cr_trans_subtotal;}
	
	public double getcr_total_subtotal(){return cr_total_subtotal;}
	public void setcr_total_subtotal(double cr_total_subtotal){this.cr_total_subtotal=cr_total_subtotal;}
	
	public double get_net_subtotal(){return net_subtotal;}
	public void set_net_subtotal(double net_subtotal){this.net_subtotal=net_subtotal;}
	
	public double getconsolopen_bal(){return consolopen_bal;}
	public void setconsolopen_bal(double consolopen_bal){this.consolopen_bal=consolopen_bal;}
		
}
