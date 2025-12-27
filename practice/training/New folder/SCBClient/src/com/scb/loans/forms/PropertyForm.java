package com.scb.loans.forms;
import org.apache.struts.action.ActionForm;

public class PropertyForm extends ActionForm {
	String propertyno,addr,ewbyfeets,nsbyfeets,eastby,westby,northby,southby,propertyacquiredby,nature;
	double value;
	Object tenant[][];
	public String getPropertyno() {
		return propertyno;
	}
	public void setPropertyno(String propertyno) {
		this.propertyno = propertyno;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getEwbyfeets() {
		return ewbyfeets;
	}
	public void setEwbyfeets(String ewbyfeets) {
		this.ewbyfeets = ewbyfeets;
	}
	public String getNsbyfeets() {
		return nsbyfeets;
	}
	public void setNsbyfeets(String nsbyfeets) {
		this.nsbyfeets = nsbyfeets;
	}
	public String getEastby() {
		return eastby;
	}
	public void setEastby(String eastby) {
		this.eastby = eastby;
	}
	public String getWestby() {
		return westby;
	}
	public void setWestby(String westby) {
		this.westby = westby;
	}
	public String getNorthby() {
		return northby;
	}
	public void setNorthby(String northby) {
		this.northby = northby;
	}
	public String getSouthby() {
		return southby;
	}
	public void setSouthby(String southby) {
		this.southby = southby;
	}
	public String getPropertyacquiredby() {
		return propertyacquiredby;
	}
	public void setPropertyacquiredby(String propertyacquiredby) {
		this.propertyacquiredby = propertyacquiredby;
	}
	public String getNature() {
		return nature;
	}
	public void setNature(String nature) {
		this.nature = nature;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public Object[][] getTenant() {
		return tenant;
	}
	public void setTenant(Object[][] tenant) {
		this.tenant = tenant;
	}
}