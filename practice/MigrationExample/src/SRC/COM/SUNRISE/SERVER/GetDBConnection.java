package SRC.COM.SUNRISE.SERVER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GetDBConnection {
	
	protected static String db_url = "jdbc:mysql://192.168.1.14/RAMNAGAR22022010";
		
	private static Connection con ;
		
	static {
		
		try {
			
			System.out.println("inside the static method ");
			  		
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(db_url,"root","");
		
		} catch( ClassNotFoundException cla) {
			cla.printStackTrace();
			
		}  catch(SQLException sq) {
			
			sq.printStackTrace();
			
		}
		
	}
	
	public static Connection getConnection() {
		try{
		if ( con == null)
			con=DriverManager.getConnection(db_url,"root","");
		}catch(Exception e){
			e.printStackTrace();
		}
		return con;
	}
	

}
