/*
 * Created on Oct 2, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package masterObject.administrator;

import masterObject.general.UserVerifier;

import java.io.Serializable;

/**
 * @author Murugesh
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TerminalObject implements Serializable
{
	private String ip_address,tml_desc,tml_name;
	private double tml_max_trn_amt;
	private String cash_type;
	private int status;
	public UserVerifier uv=new UserVerifier();

	public void setIPAddress(String ip_address){
		this.ip_address=ip_address;
	}
	public String getIPAddress(){
		return ip_address;
	}

	public void setTmlDesc(String tml_desc){
		this.tml_desc=tml_desc;
	}
	public String getTmlDesc(){
		return tml_desc;
	}

	public void setTmlName(String tml_name){
		this.tml_name=tml_name;
	}
	public String getTmlName(){
		return tml_name;
	}

	public void setMaxAmount(double tml_max_trn_amt){
		this.tml_max_trn_amt=tml_max_trn_amt;
	}
	public double getMaxAmount(){
		return tml_max_trn_amt;
	}

	public void setCashier(String cash_type) {
		this.cash_type = cash_type;
	}

	public String getCashier() {
		return cash_type;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}
	
}
