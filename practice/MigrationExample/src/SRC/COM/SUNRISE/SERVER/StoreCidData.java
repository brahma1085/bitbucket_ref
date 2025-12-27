package SRC.COM.SUNRISE.SERVER;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.CustomerMasterObject;





public class StoreCidData {
	
	
	public int  storeCId(CustomerMasterObject cm) {
		
	
		Connection con = GetDBConnection.getConnection();
		try {
		
			Statement stmt = con.createStatement();
			Statement stmt_del = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			
			if(cm.getCustomerID()==0)
			{
				ResultSet rs=stmt.executeQuery("select max_renewal_count from Modules where modulecode like '1019000'");
				if(rs.next()) {				
				   cm.setCustomerID(rs.getInt(1)+1);
				}
					
				rs.close();
				stmt.executeUpdate("update Modules set max_renewal_count=max_renewal_count+1 where modulecode like '1019000'");	
			} else {
				
				int h = stmt_del.executeUpdate("delete from CustomerMaster where cid = " + cm.getCustomerID());
				int i = stmt_del.executeUpdate("delete from CustomerAddr where cid = " + cm.getCustomerID());
				
				
			}
			
			
			
			PreparedStatement pstmt=con.prepareStatement("insert into CustomerMaster values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1,1);
			pstmt.setInt(2,cm.getCustomerID());
			pstmt.setInt(3,cm.getCategory());
			pstmt.setString(4,cm.getIntroducerId());
			pstmt.setString(5,cm.getFirstName());
			pstmt.setString(6,cm.getMiddleName());
			
			if(cm.getLastName()!=null)
			pstmt.setString(7,cm.getLastName().trim().toUpperCase());
			else
				pstmt.setString(7,null);
			pstmt.setString(8,cm.getSalute());
			if(cm.getMotherName()!=null)
			pstmt.setString(9,cm.getMotherName().trim().toUpperCase());
			else
				pstmt.setString(9,null);
			
			if(cm.getFatherName()!=null)
				pstmt.setString(10,cm.getFatherName().trim().toUpperCase());
				else
					pstmt.setString(10,null);
			
			if(cm.getSpousename()!=null)
				pstmt.setString(11,cm.getSpousename().trim().toUpperCase());
				else
					pstmt.setString(11,null);
			
			if(cm.getDOB()!=null)
				pstmt.setString(12,cm.getDOB().trim().toUpperCase());
			else
				pstmt.setString(12,null);
			
			if(cm.getSex()!=null)
				pstmt.setString(13,cm.getSex().trim().toUpperCase());
				else
					pstmt.setString(13,null);
			
			if(cm.getMaritalStatus()!=null)
				pstmt.setString(14,cm.getMaritalStatus().trim());
				else
					pstmt.setString(14,null);
			
			pstmt.setInt(15,cm.getSubCategory());				

			if(cm.getBinaryImage()!=null)
			{
				byte image[]=cm.getBinaryImage();
				ByteArrayInputStream bin=new ByteArrayInputStream(image);
				pstmt.setBinaryStream(16,bin,image.length);
			}
			else
				pstmt.setBinaryStream(16,null,0);
			
			if(cm.getBinarySignImage()!=null)
			{
				byte image[]=cm.getBinarySignImage();
				ByteArrayInputStream bin=new ByteArrayInputStream(image);
				pstmt.setBinaryStream(17,bin,image.length);
			}
			else
				pstmt.setBinaryStream(17,null,0);					
				
			if(cm.getNameProof()!=null)
				pstmt.setString(18,cm.getNameProof().trim());
				else
					pstmt.setString(18,null);
			
			if(cm.getAddressProof()!=null)
				pstmt.setString(19,cm.getAddressProof().trim());
				else
					pstmt.setString(19,null);
			if(cm.getSubOccupation()!=null)
				pstmt.setString(22,cm.getSubOccupation().trim());
				else
					pstmt.setString(22,null);
			
			
			if(cm.getScSt()!=null)
				pstmt.setString(23,cm.getScSt().trim().toUpperCase());
				else
					pstmt.setString(23,null);
			
						
			
			pstmt.setString(20,cm.getPanno());
			pstmt.setString(21,cm.getOccupation());
			
			
			if(cm.getCaste()!=null)
				cm.setCaste(cm.getCaste().toUpperCase());
			
			pstmt.setString(24,cm.getCaste());
			if(cm.getNationality()!=null)
				cm.setNationality(cm.getNationality().toUpperCase());
			if(cm.getReligion()!=null)
				cm.setReligion(cm.getReligion().toUpperCase());
			pstmt.setString(25,cm.getNationality());
			pstmt.setString(26,cm.getReligion());
			pstmt.setString(27,cm.getPassPortNumber());
			pstmt.setString(28,cm.getPPIssueDate());
			pstmt.setString(29,cm.getPPExpiryDate());
			
			pstmt.setString(30,cm.getGuardType());
			if(cm.getGuardName()!=null)
			{
				cm.setGuardName(cm.getGuardName().trim().toUpperCase());	
				cm.setGuardRelationship(cm.getGuardRelationship().trim().toUpperCase());
				cm.setGuardAddress(cm.getGuardAddress().trim().toUpperCase());
			}
			
			pstmt.setString(31,cm.getGuardName());
			pstmt.setInt(32,0);
			
			pstmt.setString(33,cm.getGuardSex());
			pstmt.setString(34,cm.getGuardRelationship());
			pstmt.setString(35,cm.getGuardAddress());
			
			pstmt.setString(36,cm.getCourtDate());
			pstmt.setString(37,cm.getUid());
			pstmt.setString(38,cm.getUtml_no());
			pstmt.setString(39,cm.getUdatetime());
			
			pstmt.setString(40,cm.getVdatetime());
			pstmt.setString(41,cm.getVid() );
			pstmt.setString(42,cm.getVdatetime());	
			System.out.println("HI THIS IS GOPINATH");
			pstmt.executeUpdate();
			System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			
			
			PreparedStatement ps = con.prepareStatement("insert into CustomerAddr values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			ps.setInt(1,cm.getCustomerID());
			ps.setInt(2,cm.getAdd_type());
			ps.setString(3,null);
			ps.setString(4,cm.getAddress().trim());
			ps.setString(5,"Bangalore");
			ps.setString(6,"karnataka");
			ps.setString(7,Long.toString(cm.getPin()));
			ps.setString(8,"India");
			ps.setString(9,Long.toString(cm.getPhone()));
			ps.setInt(10,80);
			ps.setInt(11,0);
			ps.setInt(12,0);
			System.out.println("mobile no is "+cm.getMobile());
			ps.setString(13,Long.toString(cm.getMobile()));
			ps.setString(14,"");
			ps.setString(15,cm.getUid());
			ps.setString(16,cm.getUtml_no());
			ps.setString(17,cm.getUdatetime());							
			ps.executeUpdate();
				

			getCustDetails.vec.put( cm.getFirstName(), cm.getCustomerID()+",    "+cm.getAddress());
			
			
		
		} catch (SQLException slq) {
			
			slq.printStackTrace();
		}
		
		return cm.getCustomerID();
		
	}
	
	
	public CustomerMasterObject getCustomerID(int cid) {
		
		CustomerMasterObject cm = null;
		Connection con = GetDBConnection.getConnection();
		try {
		
			Statement stm = con.createStatement();
			Statement stm1 = con.createStatement();
			
			ResultSet rs = stm.executeQuery(" select * from CustomerMaster where cid = " + cid);
			ResultSet rs1 = stm1.executeQuery(" select * from CustomerAddr where cid = " + cid);
			
			if ( rs.next() && rs1.next()) {
				
				cm = new CustomerMasterObject();
				cm.setCustomerID(rs.getInt("cid"));
				cm.setCategory(rs.getInt("custtype"));
				cm.setIntroducerId(rs.getString("introid"));
				cm.setFirstName(rs.getString("fname"));
				
				cm.setMiddleName((rs.getString("mname")!=null?rs.getString("mname"):" "));
				cm.setLastName((rs.getString("lname")!=null?rs.getString("lname"):" "));
				cm.setSalute(rs.getString("salute"));
				cm.setDOB(rs.getString("dob"));
				cm.setSex(rs.getString("sex"));
				cm.setMaritalStatus(rs.getString("marital"));
				cm.setPhoto(rs.getString("photo"));
				cm.setSubCategory(rs.getInt("sub_category"));
				cm.setFatherName(rs.getString("fathername"));
				cm.setMotherName(rs.getString("mothername"));
				cm.setSpousename(rs.getString("spousename"));

				Blob b=rs.getBlob("photo");
				Blob s=rs.getBlob("sign");
				if(b!=null)
					cm.setBinaryImage( b.getBytes(1,(int)b.length()));
				else
					cm.setBinaryImage(null);
				if(s!=null)
					cm.setBinarySignImage( s.getBytes(1,(int)s.length()));
				else
					cm.setBinarySignImage(null);
				

				cm.setNationality(rs.getString("nationality"));
				cm.setReligion(rs.getString("religion"));
				cm.setCaste(rs.getString("caste"));
				cm.setAddressProof(rs.getString("addrproof"));
				cm.setNameProof(rs.getString("nameproof"));
				cm.setSubOccupation(rs.getString("suboccupation"));
				cm.setPanno(rs.getString("panno"));
				cm.setOccupation(rs.getString("occupation"));
				cm.setScSt(rs.getString("scst"));
				cm.setPassPortNumber(rs.getString("passportno"));
				cm.setPPIssueDate(rs.getString("dateofissue"));
				cm.setPPExpiryDate(rs.getString("dateofexpiry"));

				cm.setGuardType(rs.getString("guardiantype"));
				cm.setGuardName(rs.getString("guardianname"));
				cm.setGuardSalute(rs.getString("guardiansalute"));
				cm.setGuardSex(rs.getString("guardiansex"));
				cm.setGuardRelationship(rs.getString("guardianrelation"));
				cm.setGuardAddress(rs.getString("guardianaddress"));
				cm.setCourtDate(rs.getString("courtdate"));

				cm.setUid(rs.getString("de_user"));
				cm.setUtml_no(rs.getString("de_tml"));
				cm.setUdatetime(rs.getString("de_date"));
				cm.setVid(rs.getString("ve_user"));
				cm.setVtml_no(rs.getString("ve_tml"));
				cm.setVdatetime(rs.getString("ve_date"));

				
				cm.setMobile(rs1.getInt("mobile"));
				cm.setPhone(rs1.getInt("phno"));
				cm.setPin(rs1.getInt("pin"));
				cm.setAddress(rs1.getString("address"));
				cm.setAdd_type(rs1.getInt("addr_type"));
			}
		} catch (SQLException sql ) {
			sql.printStackTrace();
		}
		return cm;
	}
	
	

}
