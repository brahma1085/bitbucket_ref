package SRC.COM.SUNRISE.UTILITY;

import java.io.Serializable;

public class PropertyObject implements Serializable
{
    static final long serialVersionUID = 1L;//ship.....20/01/2007
    
	String ac_type,ac_no,p_no,addr,e_w,n_s,p_aqd,nature,east_by,west_by,south_by,north_by,tenant,rent,type;
	double value;
	
	
	public String getTenant() {
		return tenant;
	}
	public void setTenant(String tenant) {
		this.tenant = tenant;
	}
	public String getRent() {
		return rent;
	}
	public void setRent(String rent) {
		this.rent = rent;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAc_type() {
		return ac_type;
	}
	public void setAc_type(String ac_type) {
		this.ac_type = ac_type;
	}
	public String getAc_no() {
		return ac_no;
	}
	public void setAc_no(String ac_no) {
		this.ac_no = ac_no;
	}
		
	public String getPropertyNo(){return p_no;}
	public void setPropertyNo(String p_no){this.p_no=p_no;}

	public String getAddress(){return addr;}
	public void setAddress(String type){this.addr=type;}

	public String getMeasurementEW(){return e_w;}
	public void setMeasurementEW(String type){this.e_w=type;}

	public String getMeasurementNS(){return n_s;}
	public void setMeasurementNS(String type){this.n_s=type;}

	public String getEastBy(){return east_by;}
	public void setEastBy(String e_by){this.east_by=e_by;}

	public String getWestBy(){return west_by;}
	public void setWestBy(String e_by){this.west_by=e_by;}

	public String getSouthBy(){return south_by;}
	public void setSouthBy(String e_by){this.south_by=e_by;}

	public String getNorthBy(){return north_by;}
	public void setNorthBy(String e_by){this.north_by=e_by;}


	public String getPropertyAqdBy(){return p_aqd;}
	public void setPropertyAqdBy(String empno){this.p_aqd=empno;}

	public String getPropertyNature(){return nature;}
	public void setPropertyNature(String empno){this.nature=empno;}


	public double getPropertyValue(){return value;}
	public void setPropertyValue(double d){this.value=d;}

	
	public String getP_no() {
		return p_no;
	}
	public void setP_no(String p_no) {
		this.p_no = p_no;
	}

}
