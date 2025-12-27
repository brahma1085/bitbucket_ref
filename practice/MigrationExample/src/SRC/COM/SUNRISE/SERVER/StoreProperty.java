package SRC.COM.SUNRISE.SERVER;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import SRC.COM.SUNRISE.UTILITY.*;
import com.sun.org.apache.bcel.internal.generic.LMUL;

import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.LoanMasterObject;

public class StoreProperty {
	
	Connection conn;
	
	public StoreProperty(){
		conn = GetDBConnection.getConnection();
	}
	
	public boolean storePropertyMaster(PropertyObject propertyobj){
		boolean flg=false;
		try {
			PreparedStatement ps1 = conn.prepareStatement("insert into PropertyMaster values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			
			ps1.setString(1,propertyobj.getAc_no());
			ps1.setString(2,propertyobj.getAc_type());
			ps1.setString(3,propertyobj.getPropertyNo());
			ps1.setString(4,propertyobj.getAddress());
			ps1.setString(5,propertyobj.getMeasurementEW());
			ps1.setString(6,propertyobj.getMeasurementNS());
			ps1.setString(7,propertyobj.getEastBy());
			ps1.setString(8,propertyobj.getWestBy());
			ps1.setString(9,propertyobj.getNorthBy());
			ps1.setString(10,propertyobj.getSouthBy());
			ps1.setDouble(11,propertyobj.getPropertyValue());
			ps1.setString(12,propertyobj.getPropertyAqdBy());
			ps1.setString(13,propertyobj.getPropertyNature());
			ps1.setString(14,propertyobj.getTenant());
			ps1.setString(15,propertyobj.getRent());
			ps1.setString(16,propertyobj.getType());
			int temp=ps1.executeUpdate();
			System.out.println(temp);
			if(temp>0)
				flg=true;
		} catch (SQLException e) {
				e.printStackTrace();
		}
		return flg;
		
	}
	
	
	public ResultSet accesssProperty(int acc_no,int ac_typ)
	{
		ResultSet rs=null;
		try{
		Statement stmt=conn.createStatement();
		
		
		 rs=stmt.executeQuery("select * from PropertyMaster where ac_no="+acc_no+ "and ac_type="+ac_typ+"'");
		return rs;
	}
		catch(Exception e){e.printStackTrace();}
		return rs;
	}
	


}
