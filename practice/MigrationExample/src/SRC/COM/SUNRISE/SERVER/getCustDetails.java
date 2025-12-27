//package src.com.sunrise.server;

package SRC.COM.SUNRISE.SERVER;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.TreeMap;

public class getCustDetails {
	
	static getCustDetails cust_det;
	Connection con;
	private getCustDetails() {
		
		con = GetDBConnection.getConnection();
		
	}
	
	public static getCustDetails getInstance() {
		
		if ( cust_det == null) {
			return cust_det = new getCustDetails();
		} else 
			return cust_det;
	}
	
	public static TreeMap vec = null ;
	
	static { 
		

		
		Connection con = null;
		
		try {

			con = GetDBConnection.getConnection();
			Statement stm_cid = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

			ResultSet rs = stm_cid.executeQuery("select cm.cid ,cm.fname ,ca.address from CustomerMaster cm ,CustomerAddr ca where cm.fname  like '_%' and cm.cid = ca.cid order by cm.cid");

			if (rs.next()) {


				vec = new TreeMap();
				rs.beforeFirst();

				while ( rs.next() ) {

					//System.out.println(rs.getString("fname") +"----"+rs.getInt("cid"));
					
					vec.put(  rs.getString("fname") ,rs.getInt("cid")+",    "+ rs.getString("address"));



				}


			} 

		} catch (SQLException sq) {

			sq.printStackTrace();

		} 
	
	}

	public TreeMap  getCustomerType() {

		Connection con = null;
		TreeMap vec = null ;
		try {

			con = GetDBConnection.getConnection();
			Statement stm = con.createStatement();

			ResultSet rs = stm.executeQuery("select * from CustomerType where custcode in(0,1) order by custcode ");

			if (rs.next()) {


				vec = new TreeMap();
				rs.beforeFirst();

				while ( rs.next() ) {

					vec.put(  rs.getInt("custcode") ,rs.getString("custype"));



				}


			} 

		} catch (SQLException sq) {

			sq.printStackTrace();

		} 


		return vec;

	}


	public Hashtable getAddrType() {

		Connection con1 = null;
		Hashtable hast = null ;
		try {

			con1 = GetDBConnection.getConnection();
			Statement stm = con1.createStatement();

			ResultSet rs = stm.executeQuery("select * from AddrTypes");

			if (rs.next()) {


				hast = new Hashtable();
				rs.beforeFirst();

				while ( rs.next() ) {

					hast.put( rs.getString("addr_type"), rs.getInt("number") );



				}


			} 

		} catch (SQLException sq) {

			sq.printStackTrace();

		} 

		return hast;
	}


	public String[] getOccupation() {


		String[] str = null;

		Connection con = GetDBConnection.getConnection();

		try
		{

			Statement stm = con.createStatement();

			ResultSet rs = stm.executeQuery("select * from Occupation");

			if ( rs.next()) {
				rs.last();

				str = new String[rs.getRow()];
				rs.beforeFirst();
				int i = 0 ;
				while (rs.next()) {

					str[i] = rs.getString("occupation");
					i++;
				}



			}
		}catch ( SQLException sw) {

			sw.printStackTrace();
		}



		return str;
	}


	public String[] getSalution() {


		String[] str = null;

		Connection con = GetDBConnection.getConnection();

		try
		{

			Statement stm = con.createStatement();

			ResultSet rs = stm.executeQuery("select * from Salutation");

			if ( rs.next()) {
				rs.last();

				str = new String[rs.getRow()];
				rs.beforeFirst();
				int i = 0 ;
				while (rs.next()) {

					str[i] = rs.getString("salute");
					i++;
				}



			}
		}catch ( SQLException sw) {

			sw.printStackTrace();
		}



		return str;
	}


	public String[] getMartialstatus() {


		String[] str = null;

		Connection con = GetDBConnection.getConnection();

		try
		{

			Statement stm = con.createStatement();

			ResultSet rs = stm.executeQuery("select * from MaritalStat");

			if ( rs.next()) {
				rs.last();

				str = new String[rs.getRow()];
				rs.beforeFirst();
				int i = 0 ;
				while (rs.next()) {

					str[i] = rs.getString("marital");
					i++;
				}



			}
		}catch ( SQLException sw) {

			sw.printStackTrace();
		}



		return str;
	}



	public String[] getAddressProof() {


		String[] str = null;

		Connection con = GetDBConnection.getConnection();

		try
		{

			Statement stm = con.createStatement();

			ResultSet rs = stm.executeQuery("select * from AddressProof");

			if ( rs.next()) {
				rs.last();

				str = new String[rs.getRow()];
				rs.beforeFirst();
				int i = 0 ;
				while (rs.next()) {

					str[i] = rs.getString("addrproof");
					i++;
				}



			}
		}catch ( SQLException sw) {

			sw.printStackTrace();
		}



		return str;
	}


	public String[] getCastes() {


		String[] str = null;

		Connection con = GetDBConnection.getConnection();

		try
		{

			Statement stm = con.createStatement();

			ResultSet rs = stm.executeQuery("select * from Castes");

			if ( rs.next()) {
				rs.last();

				str = new String[rs.getRow()];
				rs.beforeFirst();
				int i = 0 ;
				while (rs.next()) {

					str[i] = rs.getString("scst");
					i++;
				}



			}
		}catch ( SQLException sw) {

			sw.printStackTrace();
		}



		return str;
	}

	public String getCustomerName(int cid) {

		String name = null;

		Connection con =  GetDBConnection.getConnection();
		try {

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(" select * from CustomerMaster where cid =  " + cid);

			if ( rs.next())
				name = rs.getString("fname");


		} catch ( SQLException sql ) {

			sql.printStackTrace();
		}


		return name;
	}

	public int deleteCustomerID(int cid) {
		
		int res = 0;
		
		Connection con = GetDBConnection.getConnection();
		try {
			
			Statement str = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			res = str.executeUpdate("delete from CustomerMaster where cid = " + cid); 
			int res1 = str.executeUpdate("delete from CustomerAddr where cid = " + cid);
				
		} catch (SQLException sq) {
			
			sq.printStackTrace();
		}
		
		return res;
	}
	
	public TreeMap  getCustomerNames() {
		return vec;

	}

}
