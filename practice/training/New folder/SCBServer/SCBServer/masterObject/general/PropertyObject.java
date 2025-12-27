package masterObject.general;

import java.io.Serializable;

public class PropertyObject implements Serializable
{
    static final long serialVersionUID = 1L;//ship.....20/01/2007
    
	String p_no,addr,e_w,n_s,p_aqd,nature,east_by,west_by,south_by,north_by;
	double value;
	Object tenant[][]=null;
		
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

	public Object[][] getTenantsDetail(){return tenant;}
	public void setTenantsDetail(Object[][] d){this.tenant=d;}

}
