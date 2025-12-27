package masterObject.share;
import java.io.Serializable;

public class ShareParamObject implements Serializable
{

	int sh_cat,prm_code;
	String prm_desc,prm_freq,prm_ty,prm_gl_key,prm_gl_type,shtype;
	double  prm_amt;
	boolean paid;

	public String getShareType(){return shtype;}
	public void setShareType(String type){shtype=type;}

	public int getPrm_code(){return this.prm_code;}
	public void setPrm_code(int prm_code){this.prm_code = prm_code;}

	public double getPrm_amt(){return this.prm_amt;}
	public void setPrm_amt(double prm_amt){this.prm_amt = prm_amt;}

	public int getSh_cat(){	return this.sh_cat;}
	public void setSh_cat(int sh_cat){this.sh_cat = sh_cat;}

	public String getPrm_desc(){return this.prm_desc;}
	public void setPrm_desc(String prm_desc){this.prm_desc = prm_desc;}

	public String getPrm_freq(){return this.prm_freq;}
	public void setPrm_freq(String prm_freq){this.prm_freq = prm_freq;}

	public String getPrm_ty(){return this.prm_ty;}
	public void setPrm_ty(String prm_ty){this.prm_ty = prm_ty;}

	public String getPrm_gl_key(){return this.prm_gl_key;}
	public void setPrm_gl_key(String prm_gl_key){this.prm_gl_key = prm_gl_key;}

	public boolean isPaid(){return paid;}
	public void setPaid(boolean p){paid=p;}
	

	public void setPrm_gl_type(String prm_gl_type){this.prm_gl_type = prm_gl_type;}
	public String getPrm_gl_type(){return this.prm_gl_type;}
}
