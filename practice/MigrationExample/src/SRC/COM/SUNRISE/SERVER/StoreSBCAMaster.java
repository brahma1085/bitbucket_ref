package SRC.COM.SUNRISE.SERVER;



import java.awt.Stroke;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import SRC.COM.SUNRISE.UTILITY.EXCEPTION.RecordNotFound;
import SRC.COM.SUNRISE.UTILITY.EXCEPTION.RecordNotInserted;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.NomineeObject;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.SBCAObject;

import com.sun.media.sound.HsbParser;



public class StoreSBCAMaster {
	
	Connection con ;
	
	public StoreSBCAMaster(){
		
		con = GetDBConnection.getConnection();
		
	}

	public boolean storeSBCAMaster( NomineeObject nom_obj, SBCAObject sbcaobj ) throws RecordNotInserted 
	{
		int reg_no = 0;
		
		Statement statement = null;
		try {
			
			
			
			if (nom_obj != null) {
				
				Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
				
				 if ( nom_obj.getRegno() == 0 ){
					ResultSet rs = stmt.executeQuery("select min_period from Modules where modulecode='1019000'");
				
					rs.next();
					reg_no = rs.getInt("min_period");
				} else {
					
					reg_no = nom_obj.getRegno();
				}
				        
				
				int r = stmt.executeUpdate("update Modules set min_period =  (min_period +1) where modulecode= '1019000'");
				
				PreparedStatement preparedstatement=con.prepareStatement("insert into NomineeMaster values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,null,null)");
				statement=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				
				{
					System.out.println("insertinginto nominee master...........");
					
					preparedstatement.setInt(1,reg_no);
					System.out.println("iiiiiiiiiiiiiiiiiiiid"+nom_obj.getCid());
					
					if( nom_obj.getCid()<0)
					{
						preparedstatement.setString(2,null);
					}
					else
						preparedstatement.setInt(2,nom_obj.getCid());
					
					preparedstatement.setString(3,sbcaobj.getAc_type());
					
					preparedstatement.setInt(4,sbcaobj.getAc_no());
					preparedstatement.setInt(5,nom_obj.getSex());
					preparedstatement.setString(6,nom_obj.getName());
					preparedstatement.setString(7,nom_obj.getDob());
					preparedstatement.setString(8,nom_obj.getAddress());
					preparedstatement.setString(9,nom_obj.getRelation());
					preparedstatement.setDouble(10,100.00);
					preparedstatement.setString(11,null);
					preparedstatement.setString(12,null);
					preparedstatement.setString(13,null);
					preparedstatement.setInt(14,0);
					preparedstatement.setString(15,null);
					preparedstatement.setInt(16,0);
					preparedstatement.setString(17,null);
					
					if(preparedstatement.executeUpdate()!=1)
						throw new RecordNotInserted();
					System.out.println("inserted..........+");
				}
				
			}
			
			PreparedStatement ps1=con.prepareStatement("insert into AccountMaster values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		    
			
			ps1.setString(1,sbcaobj.getAc_type());
			ps1.setInt(2,sbcaobj.getAc_no());
			ps1.setInt(3,sbcaobj.getCid());
			ps1.setInt(4,sbcaobj.getAddr_type());
			ps1.setInt(5,sbcaobj.getVec_jointholder().size());
			ps1.setString(6,sbcaobj.getCheck_book_issue());
			
			
			
			ps1.setInt(7,0);
			
			ps1.setString(8,null);
			ps1.setInt(9,0);
			ps1.setInt(10,0);
			ps1.setString(11,null);
			ps1.setString(12,sbcaobj.getIntro_ac_type());
			ps1.setInt(13,sbcaobj.getIntro_ac_no());
		
            
			if(nom_obj == null)
			{	
				ps1.setInt(14,reg_no);

			}
			else	 		
				ps1.setInt(14,reg_no);//nom regno

			/*ps1.setString(15,"O");
			ps1.setString(16,"F");*/
			
			ps1.setString(15,sbcaobj.getActive());
			ps1.setString(16,sbcaobj.getFreeze());
			
			ps1.setString(17,sbcaobj.getAc_open_date());
			ps1.setString(18,null);
			ps1.setInt(19,0);

			ps1.setString(20,sbcaobj.getDe_datetime());
			ps1.setString(21,sbcaobj.getDe_tml());
			ps1.setString(22,sbcaobj.getDe_user());
			
			ps1.setString(23,sbcaobj.getDe_datetime());
			ps1.setString(24,sbcaobj.getDe_tml());
			ps1.setString(25,sbcaobj.getDe_user());
			
			
			if ( ps1.executeUpdate() == 1 ) {
				
				
				
				/*for ( int i =0 ; i< sbcaobj.getVec_jointholder().size(); i++) {
					
					if(statement.executeUpdate("insert into JointHolders values('"+sbcaobj.getAc_type()+"','"+sbcaobj.getAc_no()+"','"+sbcaobj.getVec_jointholder().get(i)+"','"+sbcaobj.getAddr_type()+"')")!=1)
						throw new RecordNotInserted();
					
				}*/
				
				Enumeration itr =null;
				if(sbcaobj.getHash_jointholder()!=null){
					 itr=sbcaobj.getHash_jointholder().keys();
				}
				/*Enumeration itr1=null;
				if(sbcaobj.getVec_jointholder()!=null){
					itr1=sbcaobj.getVec_jointholder().elements();
				}*/
				if(itr!=null)
				while (itr.hasMoreElements()) {
					Statement statement2=con.createStatement();
					Object cids = itr.nextElement();
					System.out.println("*****************" + sbcaobj.getHash_jointholder().get(cids));
					if(statement2.executeUpdate("insert into JointHolders values('"+sbcaobj.getAc_type()+"','"+sbcaobj.getAc_no()+"','"+cids+"','"+sbcaobj.getAddr_type()+"')")!=1)
						throw new RecordNotInserted();
					
					if(statement2.executeUpdate("insert into SignatureInstruction values('"+sbcaobj.getAc_type()+"','"+sbcaobj.getAc_no()+"','"+cids+"','"+sbcaobj.getHash_jointholder().get(cids)+"',null,null,null,'EOS')")!=1)
						throw new RecordNotInserted();
				}
				
				Statement st_sig = con.createStatement();
				System.out.println(sbcaobj.getName()+"-------");
				if(st_sig.executeUpdate("insert into SignatureInstruction values('"+sbcaobj.getAc_type()+"','"+sbcaobj.getAc_no()+"','"+sbcaobj.getCid()+"','"+sbcaobj.getName()+"',null,null,null,'single')")!=1)
					throw new RecordNotInserted();
				
			if ( sbcaobj.getCheck_book_issue().equalsIgnoreCase("T")) {
				int book_no = 0;
				
				Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				Statement st_ch = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				
				Hashtable table  = sbcaobj.getCkeckno();
				
				Enumeration en = table.keys();
				
				while( en.hasMoreElements()) {
					
					Object key = en.nextElement(); 
					
					ResultSet r = st.executeQuery("select top_margin from Modules where modulecode='1002000'");
					if ( r.next())
						book_no = r.getInt("top_margin");
					else
						book_no += 1;
					st.executeUpdate("update Modules set top_margin = top_margin +1  where modulecode='1002000'");

					for ( int i = new Integer(key.toString()); i<= new Integer(table.get(key.toString()).toString()).intValue(); i++) {
						if (st_ch.executeUpdate("insert into ChequeNo values('"+sbcaobj.getAc_type()+"',"+ sbcaobj.getAc_no()+","+book_no+","+i+",'F',null,'F',null,null,null,null,null,'F','F',null,null,null,'CL01','YASH','04/07/2007  13:55:29','CL01','YASH','04/07/2007  13:55:50')") != 1)
							throw new RecordNotInserted();

					}
				}
			}
			
			
			PreparedStatement pre_tran = con.prepareStatement(" insert into  AccountTransaction values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");  
			
			pre_tran.setString(1, sbcaobj.getAc_type())	;											//c_type      varchar(10)   YES     MUL     (NULL)         
			pre_tran.setInt(2, sbcaobj.getAc_no());														//ac_no        int(11)       YES     MUL     (NULL)         
			pre_tran.setString(3, sbcaobj.getAc_open_date() );														//trn_date     varchar(10)   YES             (NULL);         
			pre_tran.setString(4, "P");   							//trn_type     varchar(5)    YES             (NULL)         
			pre_tran.setInt(5, 1);									//trn_seq      int(11)       YES             (NULL)         
			pre_tran.setDouble(6, sbcaobj.getBalanceAmount() );									//trn_amt      double(20,2)  YES             (NULL);         
			pre_tran.setString(7, "T");									//trn_mode     varchar(5)    YES             (NULL)         
			pre_tran.setString(8, sbcaobj.getDe_tml() );											//trn_source   varchar(10)   YES             (NULL)         
			pre_tran.setString(9, "C");											//cd_ind       varchar(5)    YES             (NULL)         
			pre_tran.setInt(10, 0);														//chq_dd_no    int(11)       YES             (NULL)         
			pre_tran.setString(11, null);							//chq_dd_date  varchar(10)   YES             (NULL)         
			pre_tran.setString(12, "B/F");														//trn_narr     varchar(30)   YES             (NULL)         
			pre_tran.setInt(13, 0);									//ref_no       int(11)       YES             (NULL)         
			pre_tran.setString(14, null);							//payee_nm     varchar(25)   YES             (NULL)         
			pre_tran.setDouble(15, sbcaobj.getBalanceAmount());		//cl_bal       double(20,2)  YES             (NULL)         
			pre_tran.setInt(16, 0);														//ldg_page     int(7)        YES             (NULL)         
			pre_tran.setString(17, sbcaobj.getDe_tml());														//de_tml       varchar(10)   YES             (NULL)         
			pre_tran.setString(18, sbcaobj.getDe_user());														//de_user      varchar(10)   YES             (NULL)         
			pre_tran.setString(19, sbcaobj.getDe_datetime());														//de_date      varchar(30)   YES             (NULL)         
			pre_tran.setString(20, sbcaobj.getDe_tml());														//ve_tml       varchar(10)   YES             (NULL)         
			pre_tran.setString(21, sbcaobj.getDe_user());														//ve_user      varchar(10)   YES             (NULL)         
			pre_tran.setString(22, sbcaobj.getDe_datetime());														//ve_date      varchar(30)   YES             (NULL)
			
			pre_tran.executeUpdate();
			
			
			} else {
				throw new RecordNotInserted();
			}
			
		
			
			
		} catch( SQLException sql) {
			sql.printStackTrace();
			throw new RecordNotInserted();
		}
		
		return true;
	}
	
	public void updateSBCAMaster(NomineeObject nom_obj, SBCAObject sbcaobj ) throws RecordNotInserted 
	{
		try {
			
			Statement stmt = con.createStatement();
			
			stmt.executeUpdate("delete from  AccountMaster where ac_type = '"+ sbcaobj.getAc_type()+"' and  ac_no = "+sbcaobj.getAc_no());
			stmt.executeUpdate("delete from  NomineeMaster where ac_type = '"+ sbcaobj.getAc_type()+"' and  ac_no = "+sbcaobj.getAc_no());
			stmt.executeUpdate("delete from  JointHolders where ac_type = '"+ sbcaobj.getAc_type()+"' and  ac_no = "+sbcaobj.getAc_no());
			stmt.executeUpdate("delete from  SignatureInstruction where ac_type = '"+ sbcaobj.getAc_type()+"' and  ac_no = "+sbcaobj.getAc_no());
			stmt.executeUpdate("delete from  ChequeNo where ac_type = '"+ sbcaobj.getAc_type()+"' and  ac_no = "+sbcaobj.getAc_no());
			stmt.executeUpdate("delete from  AccountTransaction where ac_type = '"+ sbcaobj.getAc_type()+"' and  ac_no = "+sbcaobj.getAc_no());
			
			storeSBCAMaster(nom_obj, sbcaobj);
			
		} catch (SQLException sql ) {
			
			sql.printStackTrace();
			throw new RecordNotInserted();
		} 
		
	}
	                             
	
	public Object[] getSBCADetail(String ac_type , int ac_no) throws RecordNotFound 
	{
		
		Object[] obj = new Object[2];
		NomineeObject nom_obj = null;
		try {
		
			Statement stmt = con.createStatement();
			Statement stmt1 = con.createStatement();
			Statement stmt2 = con.createStatement();
			Statement stmt3 = con.createStatement();
			Statement stmt4 = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("select * from AccountMaster where ac_type = '" + ac_type+"' and ac_no = "+ ac_no);
			
			if ( rs.next()) {
				
				SBCAObject sbca = new SBCAObject();
				obj[0] = sbca;
				
				sbca.setAc_type( rs.getString("ac_type"));//.equalsIgnoreCase("1002001") ? "SB":"CA");
				sbca.setAc_no(rs.getInt("ac_no"));
				sbca.setCid(rs.getInt("cid"));
				sbca.setCheck_book_issue(rs.getString("ch_bk_issue"));
				sbca.setIntro_ac_type(rs.getString("intr_ac_type"));
				//sbca.setIntro_ac_type(rs.getString("intr_ac_type").equalsIgnoreCase("1002001") ? "SB":"CA");
				sbca.setIntro_ac_no(rs.getInt("intr_ac_no"));
				sbca.setAc_open_date(rs.getString("ac_opendate"));
				sbca.setActive(rs.getString("ac_status"));
				sbca.setFreeze(rs.getString("freeze_ind"));
				
				if ( sbca.getCheck_book_issue().equalsIgnoreCase("T")) {
					
					Hashtable table = new Hashtable();
					
					ResultSet rs1 = stmt1.executeQuery("select max(chq_no) as max ,min(chq_no) as min,book_no from ChequeNo where ac_type = '"+ac_type+"' and ac_no = "+ac_no+" group by book_no");
					while (rs1.next()) {
						
						System.out.println(rs1.getString("min")+" ************>>>>");
						table.put(new Integer(rs1.getString("min")), new Integer(rs1.getString("max")));
						
					}
					
					sbca.setCkeckno(table);
				}
				//select * from JointHolders where ac_type = '1002001' and ac_no = 36368
				
				ResultSet rs2 = stmt1.executeQuery("select * from JointHolders where ac_type = '"+ac_type+"' and ac_no ="+ac_no);
				Hashtable has_cids = new Hashtable();
				if (rs2.next()) {
					
					Vector vec = new Vector();
					
					rs2.beforeFirst();
					
					while(rs2.next()) {
						
						vec.add(new Integer(rs2.getInt("cid")));
						has_cids.put( new Integer(rs2.getInt("cid")), rs2.getString("name"));
					}
					
					sbca.setVec_jointholder(vec);
				}
				
				if ( rs.getInt("nom_no") > 0) {
					
					ResultSet rs3 = stmt2.executeQuery("select * from NomineeMaster where reg_no = "+rs.getInt("nom_no")+" and ac_type ='"+ac_type+"' and  ac_no ="+ac_no);
					if ( rs3.next() ) {
						
						 nom_obj = new NomineeObject();
						
						nom_obj.setCid(rs3.getInt("cid"));
						nom_obj.setName(rs3.getString("name"));
						nom_obj.setDob(rs3.getString("dob"));
						nom_obj.setSex(rs3.getInt("sex")); /// 1 male...0 - female
						nom_obj.setRelation(rs3.getString("relation"));
						nom_obj.setAddress(rs3.getString("address"));
						nom_obj.setRegno(rs3.getInt("reg_no"));
						
					}
					 
					obj[1] = nom_obj;
				} else 
					obj[1] = null;
			
				ResultSet rs_tran = stmt4.executeQuery(" select * from AccountTransaction where ac_type = '"+ ac_type+"' and ac_no =" +ac_no);
				
				if ( rs_tran.next())
					sbca.setBalanceAmount(rs_tran.getDouble("cl_bal"));
			} else 
				
				obj = null;
			
			
				
		} catch( SQLException sql ) {
			
			sql.printStackTrace();
			
		}
		
		
		
		return obj;
	}
	
	
}
