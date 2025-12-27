package masterObject.loans;

import java.io.Serializable;

public class NPAObject implements Serializable {

	private String AcType, AssetDesc, FromDate, ToDate;  
	
	private int DaysFrom, DaysTo, MonthsFrom, MonthsTo, AssetCode;
	
	private double ProvPerc;

	public String getAcType() {
		return AcType;
	}

	public void setAcType(String acType) {
		AcType = acType;
	}

	public int getAssetCode() {
		return AssetCode;
	}

	public void setAssetCode(int assetCode) {
		AssetCode = assetCode;
	}

	public String getAssetDesc() {
		return AssetDesc;
	}

	public void setAssetDesc(String assetDesc) {
		AssetDesc = assetDesc;
	}

	public int getDaysFrom() {
		return DaysFrom;
	}

	public void setDaysFrom(int daysFrom) {
		DaysFrom = daysFrom;
	}

	public int getDaysTo() {
		return DaysTo;
	}

	public void setDaysTo(int daysTo) {
		DaysTo = daysTo;
	}

	public String getFromDate() {
		return FromDate;
	}

	public void setFromDate(String fromDate) {
		FromDate = fromDate;
	}

	public int getMonthsFrom() {
		return MonthsFrom;
	}

	public void setMonthsFrom(int monthsFrom) {
		MonthsFrom = monthsFrom;
	}

	public int getMonthsTo() {
		return MonthsTo;
	}

	public void setMonthsTo(int monthsTo) {
		MonthsTo = monthsTo;
	}

	public double getProvPerc() {
		return ProvPerc;
	}

	public void setProvPerc(double provPerc) {
		ProvPerc = provPerc;
	}

	public String getToDate() {
		return ToDate;
	}

	public void setToDate(String toDate) {
		ToDate = toDate;
	}
}
