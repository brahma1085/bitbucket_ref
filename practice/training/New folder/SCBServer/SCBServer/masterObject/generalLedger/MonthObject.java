/*
 * Created on Mar 30, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package masterObject.generalLedger;
import java.io.Serializable;
/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MonthObject implements Serializable
{

	String gl_type,gl_name,date,moduleabbr;
	double open_bal,net_trans,openingbal,closingbal;
	int gl_code;
	String br_code;
	
	public int  getgl_code(){return gl_code;}
	public void setgl_code(int gl_code){this.gl_code=gl_code;}

	public String getgl_name(){return gl_name;}
	public void setgl_name(String gl_name){this.gl_name=gl_name;}
	
	public String getgl_type(){return gl_type;}
	public void setgl_type(String gl_type){this.gl_type=gl_type;}
	
	public double getnet_trans(){return net_trans;}
	public void setnet_trans(double net_trans){this.net_trans=net_trans;}
	
	public double getopen_bal(){return open_bal;}
	public void setopen_bal(double open_bal){this.open_bal=open_bal;}
	
	public String getdate(){return date;}
	public void setdate(String date){this.date=date;}
	
	public String getbr_code(){return br_code;}
	public void setbr_code(String br_code){this.br_code=br_code;}
	
	public String getmoduleabbr(){return moduleabbr;}
	public void setmoduleabbr(String moduleabbr){this.moduleabbr=moduleabbr;}
	
	public double getClosingBal(){return closingbal;}
	public void setClosingBal(double closingbal){this.closingbal =closingbal;}
	
	public double getOpeningBal(){return openingbal;}
	public void setOpeningBal(double openingbal){this.openingbal =openingbal;}
	
	
	}
	



