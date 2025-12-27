package SRC.COM.SUNRISE.SERVER;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginCheck {
	public int loginCheck(String uid,String pwd,String tml) {
		Connection con = GetDBConnection.getConnection();
		try {
		
			Statement stmt = con.createStatement();
			
			ResultSet rs=stmt.executeQuery("select user.user_id,user.de_tml from UserMaster as user where user_id='"+uid.trim()+"' and password='"+pwd.trim()+"' and de_tml='"+tml.trim()+"'");
			/*if(rs.next()) {
				return 1;
			}else {
				return 0;
			}*/
			System.out.println("**************return");
			return 1;
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
}
