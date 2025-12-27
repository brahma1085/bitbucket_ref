package SRC.COM.SUNRISE.SERVER;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sun.org.apache.bcel.internal.generic.LMUL;

import SRC.COM.SUNRISE.CLIENT.VehicleObject;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.LoanMasterObject;

public class StoreVehicle {
	
	Connection conn;
	
	public StoreVehicle(){
		conn = GetDBConnection.getConnection();
	}
	
	public boolean storeVehicle(VehicleObject vcl){
		boolean flg=false;
		try {
			PreparedStatement ps1 = conn.prepareStatement("insert into VehicleMaster values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			
			ps1.setString(1,vcl.getCombo_ac_type());
			ps1.setString(2,vcl.getTxt_ac_no());
			ps1.setString(3,vcl.getVehicleMake());
			ps1.setDouble(4,vcl.getVehicleCost());
			ps1.setString(5,vcl.getAddressDealer());
			ps1.setString(6,vcl.getLicenceNo());
			ps1.setString(7,vcl.getLicenceValidity());
			ps1.setString(8,vcl.getVehicleType());
			ps1.setString(9,vcl.getPermitNo());
			ps1.setString(10,vcl.getPermitValidity());
			ps1.setString(11,vcl.getVehicleFor());
			ps1.setString(12,vcl.getArea());
			ps1.setString(13,vcl.getAddressParking());
			ps1.setString(14,vcl.getOtherDet());
			
			int temp=ps1.executeUpdate();
			System.out.println(temp);
			if(temp>0)
				flg=true;
		} catch (SQLException e) {
				e.printStackTrace();
		}
		return flg;
		
	}
	
	
	
	


}
