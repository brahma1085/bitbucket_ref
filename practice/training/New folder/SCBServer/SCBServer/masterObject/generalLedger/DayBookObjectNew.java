package masterObject.generalLedger;

import java.io.Serializable;

public class DayBookObjectNew implements Serializable{
	String glName,glAbbr;
	int glCode;
	String drCash,drClear,drTrans,drTotal;
	String crCash,crClear,crTrans,crTotal,netTrans;
	double cashCloseBal,cashOpenBal,closeBal,openBal,drClBal,crClBal;
	public String getGlName() {
		return glName;
	}
	public void setGlName(String glName) {
		this.glName = glName;
	}
	public String getGlAbbr() {
		return glAbbr;
	}
	public void setGlAbbr(String glAbbr) {
		this.glAbbr = glAbbr;
	}
	public int getGlCode() {
		return glCode;
	}
	public void setGlCode(int glCode) {
		this.glCode = glCode;
	}
	
	public String getDrCash() {
		return drCash;
	}
	public void setDrCash(String drCash) {
		this.drCash = drCash;
	}
	public String getDrClear() {
		return drClear;
	}
	public void setDrClear(String drClear) {
		this.drClear = drClear;
	}
	public String getDrTrans() {
		return drTrans;
	}
	public void setDrTrans(String drTrans) {
		this.drTrans = drTrans;
	}
	public String getDrTotal() {
		return drTotal;
	}
	public void setDrTotal(String drTotal) {
		this.drTotal = drTotal;
	}
	public String getCrCash() {
		return crCash;
	}
	public void setCrCash(String crCash) {
		this.crCash = crCash;
	}
	public String getCrClear() {
		return crClear;
	}
	public void setCrClear(String crClear) {
		this.crClear = crClear;
	}
	public String getCrTrans() {
		return crTrans;
	}
	public void setCrTrans(String crTrans) {
		this.crTrans = crTrans;
	}
	public String getCrTotal() {
		return crTotal;
	}
	public void setCrTotal(String crTotal) {
		this.crTotal = crTotal;
	}
	public String getNetTrans() {
		return netTrans;
	}
	public void setNetTrans(String netTrans) {
		this.netTrans = netTrans;
	}
	public double getCashCloseBal() {
		return cashCloseBal;
	}
	public void setCashCloseBal(double cashCloseBal) {
		this.cashCloseBal = cashCloseBal;
	}
	public double getCashOpenBal() {
		return cashOpenBal;
	}
	public void setCashOpenBal(double cashOpenBal) {
		this.cashOpenBal = cashOpenBal;
	}
	public double getCloseBal() {
		return closeBal;
	}
	public void setCloseBal(double closeBal) {
		this.closeBal = closeBal;
	}
	public double getOpenBal() {
		return openBal;
	}
	public void setOpenBal(double openBal) {
		this.openBal = openBal;
	}
	public double getDrClBal() {
		return drClBal;
	}
	public void setDrClBal(double drClBal) {
		this.drClBal = drClBal;
	}
	public double getCrClBal() {
		return crClBal;
	}
	public void setCrClBal(double crClBal) {
		this.crClBal = crClBal;
	}

}
