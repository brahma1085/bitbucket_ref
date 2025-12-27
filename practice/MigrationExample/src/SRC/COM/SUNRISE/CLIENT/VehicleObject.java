package SRC.COM.SUNRISE.CLIENT;

import java.io.Serializable;

//create table VehicleMaster(make varchar(100),cost double(20,2),addrdealer
//varchar(150),licno varchar(50),validity varchar(10),type varchar(10),
//permitno varchar(50),pvalidity varchar(10),vehfor varchar(10),
//area varchar(50),addrpark varchar(150),other varchar(250));



public class VehicleObject implements Serializable
{
    static final long serialVersionUID = 1L;//ship.....20/01/2007
    
	String vehfor,type,make,licno,permitno,area,validity;
	String permitvalid,addrparking,addrdealer,other,txt_ac_no,combo_ac_type;
	double cost;

	public String getVehicleType(){return type;}
	public void setVehicleType(String str){this.type=str;}

	public String getVehicleMake(){return make;}
	public void setVehicleMake(String str){this.make=str;}

	public double getVehicleCost(){return cost;}
	public void setVehicleCost(double str){this.cost=str;}

	public String getVehicleFor(){return vehfor;}
	public void setVehicleFor(String str){this.vehfor=str;}

	public String getLicenceNo(){return licno;}
	public void setLicenceNo(String str){this.licno=str;}

	public String getLicenceValidity(){return validity;}
	public void setLicenceValidity(String str){this.validity=str;}

	public String getPermitNo(){return permitno;}
	public void setPermitNo(String str){this.permitno=str;}

	public String getPermitValidity(){return permitvalid;}
	public void setPermitValidity(String str){this.permitvalid=str;}

	public String getArea(){return area;}
	public void setArea(String str){this.area=str;}


	public String getAddressParking(){return addrparking;}
	public void setAddressParking(String str){this.addrparking=str;}

	public String getAddressDealer(){return addrdealer;}
	public void setAddressDealer(String str){this.addrdealer=str;}

	public String getOtherDet(){return other;}
	public void setOtherDet(String str){this.other=str;}
	public String getTxt_ac_no() {
		return txt_ac_no;
	}
	public void setTxt_ac_no(String txt_ac_no) {
		this.txt_ac_no = txt_ac_no;
	}
	public String getCombo_ac_type() {
		return combo_ac_type;
	}
	public void setCombo_ac_type(String combo_ac_type) {
		this.combo_ac_type = combo_ac_type;
	}
}
