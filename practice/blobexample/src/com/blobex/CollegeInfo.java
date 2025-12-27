package com.blobex;

import java.sql.*;
//import java.util.*;

class CollegeInfo1 implements SQLData {
		private int id;
		private String name;
		CollegeInfo1(int id,String name){
			this.id=id;
			this.name=name;
		}
		public String getSQLTypeName() throws SQLException{
			return "collegeinfo";
		}
		public void readSQL(SQLInput stream,String typeName)throws SQLException{
			this.id=stream.readInt();
			this.name=stream.readString();
		}
		public void writeSQL(SQLOutput stream)throws SQLException
		{
			stream.writeInt(id);
			stream.writeString(name);
		}
	}
public class CollegeInfo{
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","satya");
		String sql="insert into student values(?,?,?)";
		PreparedStatement pst=con.prepareStatement(sql);
		pst.setInt(1,101);
		pst.setString(2,"somu");
		CollegeInfo1 c=new CollegeInfo1(100,"MLR");
		pst.setObject(3, c);
		pst.executeUpdate();
		con.close();
	}
}
