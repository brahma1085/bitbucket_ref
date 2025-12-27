package SRC.COM.SUNRISE.SERVER;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import SRC.COM.SUNRISE.UTILITY.EXCEPTION.RecordNotFound;
import SRC.COM.SUNRISE.UTILITY.EXCEPTION.RecordNotInserted;
import SRC.COM.SUNRISE.UTILITY.EXCEPTION.RecordNotUpdated;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.DepositMasterObject;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.NomineeObject;





public class StoreDeposit {

Connection con ;
	
	public StoreDeposit(){
		
		con = GetDBConnection.getConnection();
		
	}
	
	public boolean storeDepositMaster( NomineeObject nom_obj, DepositMasterObject depobj ) throws RecordNotInserted
	{
		
		
		int reg_no = 0;
		
		Statement statement = null;
		try {
			
			if ( nom_obj != null) {
				
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
					
					preparedstatement.setString(3,depobj.getAc_type());
					
					preparedstatement.setInt(4,depobj.getAc_no() );
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
			
			PreparedStatement ps1 = con.prepareStatement("insert into DepositMaster values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			/*ac_type   ac_no     cid  add_type  autorenewal_no  no_jt_hldr  dep_date    mat_date   
			 *  dep_yrs  dep_mths  dep_days*/
			
			ps1.setString(1, depobj.getAc_type() );
			ps1.setInt(2, depobj.getAc_no() );
			ps1.setInt(3, depobj.getCid() );
			ps1.setInt(4, depobj.getAdd_type());
			ps1.setInt(5, 0);
			ps1.setInt(6, depobj.getNo_jt_hldr());
			ps1.setString(7, depobj.getDep_date());
			ps1.setString(8, depobj.getMat_date() );
			ps1.setInt(9, depobj.getDep_yrs() );
			ps1.setInt( 10 , depobj.getDep_mths());
			ps1.setInt(11, depobj.getDep_days());
			ps1.setDouble(12, depobj.getDep_amt() );
			ps1.setDouble(13, depobj.getMat_amt() );
			ps1.setString(14, depobj.getNext_pay_date());
			ps1.setString(15, depobj.getMat_post());
			ps1.setString(16, depobj.getPost_date());
			ps1.setString(17, depobj.getIntr_ac_type());
			ps1.setInt(18, depobj.getIntr_ac_no() );
			ps1.setInt(19, reg_no);
			ps1.setDouble(20, depobj.getInt_rate() );
			ps1.setDouble( 21, depobj.getExtra_rate_type() );
			ps1.setString(22, depobj.getRcvd_by() );
			ps1.setString(23, depobj.getRcvd_ac_type() );
			ps1.setInt(24, depobj.getRcvd_ac_no());
			ps1.setString(25, depobj.getInt_freq());
			ps1.setString(26, depobj.getInt_mode());
			ps1.setString(27, depobj.getTrf_ac_type());
			ps1.setInt(28, depobj.getTrf_ac_no());
			ps1.setString(29, depobj.getInt_upto_date());
			ps1.setInt(30, depobj.getLst_trn_seq());
			ps1.setInt(31, depobj.getLst_pr_seq());
			ps1.setString(32, depobj.getLn_availed());
			ps1.setString(33, depobj.getLn_ac_type());
			ps1.setInt(34, depobj.getLn_ac_no());
			ps1.setString(35, depobj.getDep_renewed());
			ps1.setString(36, depobj.getNew_rct());
			ps1.setInt(37, depobj.getRct_no());
			ps1.setString(38, depobj.getRct_prtd());
			ps1.setString(39, depobj.getRct_sign());
			ps1.setString(40, null);
			ps1.setDouble(41, 0.00 );
			ps1.setInt(42, 0);
			ps1.setString(43, null);
			ps1.setString(44, null);
			ps1.setInt(45, 0);
			ps1.setString(46, "F");
			ps1.setString(47, null);
			ps1.setString(48, depobj.getInt_paid_date());
			
			ps1.setString(49, "F");
			ps1.setString(50, "F");
			ps1.setString(51, "F");
			ps1.setString(52, "F");
			ps1.setString(53, "F");
			ps1.setString(54, "F");
			
			if ( ps1.executeUpdate() == 1 ) {
				
				for ( int i =0 ; i< depobj.getVec_jointholder().size(); i++) {
					
					if(statement.executeUpdate("insert into JointHolders values('"+depobj.getAc_type()+"','"+depobj.getAc_no()+"','"+depobj.getVec_jointholder().get(i)+"','"+depobj.getAdd_type()+"')")!=1)
						throw new RecordNotInserted();
					
				}
				
				Statement st_sig = con.createStatement();
				System.out.println(depobj.getName()+"-------");
				if(st_sig.executeUpdate("insert into SignatureInstruction values('"+depobj.getAc_type()+"','"+depobj.getAc_no()+"','"+depobj.getCid()+"','"+depobj.getName()+"',null,null,null,'single')")!=1)
					throw new RecordNotInserted();
			}  else {
				throw new RecordNotInserted();
			}
			
			//added  by geetha
			PreparedStatement ps_store_trn=con.prepareStatement("insert into DepositTransaction values(?,?,?,?,'D',?,0,0,0,?,null,null,?,?,?,?,'C',0,?,?,?,null,null,null)");
			
			ps_store_trn.setString(1,depobj.getAc_type());
			ps_store_trn.setInt(2,depobj.getAc_no());
			ps_store_trn.setInt(3,1);
			ps_store_trn.setString(4,depobj.getDe_date());
			ps_store_trn.setString(5,"D");
					
			ps_store_trn.setDouble(6,depobj.getDep_amt());
			ps_store_trn.setInt(7,0);
			ps_store_trn.setString(8,depobj.getName());
			ps_store_trn.setString(9,"T");
			ps_store_trn.setString(10,depobj.getDe_tml());
			ps_store_trn.setString(11,depobj.getDe_user());
			ps_store_trn.setString(12,depobj.getDe_date());
			
			ps_store_trn.setString(13,depobj.getDe_date());
			ps_store_trn.executeUpdate();
			
			
			if(ps_store_trn.executeUpdate()==1) {
				System.out.println("sucessfully inserted into tran...");
			}
			else {
				System.out.println("couldnt insert  tran...");
			}
			//geetha
			
		}
			
		catch (Exception ecx) {
			
			
			ecx.printStackTrace();
			throw new RecordNotInserted();
		}
		
		return true;
	}
	
	public Object[] getDepositAccount( String ac_type , int ac_no) throws RecordNotFound 
	{
		
		System.out.println("entering into getDepositAccount()..");
		Object[] obj = new Object[2];
		
		NomineeObject nom_obj = null;
		try {
		
			Statement stmt = con.createStatement();
			Statement stmt1 = con.createStatement();
			Statement stmt2 = con.createStatement();
			Statement stmt3 = con.createStatement();
			Statement stmt4 = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("select * from DepositMaster where ac_type = '" + ac_type+"' and ac_no = "+ ac_no);
			
			System.out.println("after resultset...");
			
			if ( rs.next()) {
				
				DepositMasterObject dep_obj = new DepositMasterObject();
				obj[0] = dep_obj;
				
				dep_obj.setAc_type(rs.getString("ac_type"));
				System.out.println("actype ===  "+rs.getString("ac_type"));
				dep_obj.setAc_no(rs.getInt("ac_no"));
				dep_obj.setCid(rs.getInt("cid"));
				dep_obj.setNo_jt_hldr(rs.getInt("no_jt_hldr"));
				dep_obj.setDep_date(rs.getString("dep_date"));
				dep_obj.setMat_date(rs.getString("mat_date"));
				
				
				dep_obj.setDep_days(rs.getInt("dep_days"));
				dep_obj.setDep_amt(rs.getDouble("dep_amt"));
				dep_obj.setMat_amt(rs.getDouble("mat_amt"));
				dep_obj.setNext_pay_date(rs.getString("next_pay_date"));
				dep_obj.setMat_post(rs.getString("mat_post"));
				
				dep_obj.setIntr_ac_type(rs.getString("intr_ac_type"));
				dep_obj.setIntr_ac_no(rs.getInt("intr_ac_no"));
				dep_obj.setNom_no(rs.getInt("nom_no"));
				dep_obj.setInt_rate(rs.getDouble("int_rate"));
				dep_obj.setRcvd_by(rs.getString("rcvd_by"));
				dep_obj.setRcvd_ac_type(rs.getString("rcvd_ac_type"));
				dep_obj.setRcvd_ac_no(rs.getInt("rcvd_ac_no"));
			
				dep_obj.setInt_freq(rs.getString("int_freq"));
				dep_obj.setInt_mode(rs.getString("int_mode"));
				dep_obj.setTrf_ac_type(rs.getString("trf_ac_type"));
				dep_obj.setTrf_ac_no(rs.getInt("trf_ac_no"));
				dep_obj.setInt_upto_date(rs.getString("int_upto_date"));
				
				dep_obj.setLn_availed(rs.getString("ln_availed"));
				dep_obj.setLn_ac_type(rs.getString("ln_ac_type"));
				dep_obj.setLn_ac_no(rs.getInt("ln_ac_no"));
				dep_obj.setRct_no(rs.getInt("rct_no"));
				dep_obj.setInt_paid_date(rs.getString("int_paid_date"));
			
				
				ResultSet rs2 = stmt1.executeQuery("select * from JointHolders where ac_type = '"+ac_type+"' and ac_no ="+ac_no);
				if (rs2.next()) {
					
					Vector vec = new Vector();
					rs2.beforeFirst();
					
					while(rs2.next()) {
						
						vec.add(new Integer(rs2.getInt("cid")));
					}
					
					dep_obj.setVec_jointholder(vec);
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
			
			} else 
				obj = null;
			
		} catch ( Exception ec ) {
			
			throw new RecordNotFound();
		}
		
		
		return obj;
	}
	
	public boolean updateDepositAccount(NomineeObject nom_obj, DepositMasterObject depobj ) throws RecordNotUpdated
	{

		try {

			Statement stmt = con.createStatement();

			stmt.executeUpdate("delete from  DepositMaster where ac_type = '"+ depobj.getAc_type()+"' and  ac_no = "+depobj.getAc_no());
			stmt.executeUpdate("delete from  NomineeMaster where ac_type = '"+ depobj.getAc_type()+"' and  ac_no = "+depobj.getAc_no());
			stmt.executeUpdate("delete from  JointHolders where ac_type = '"+ depobj.getAc_type()+"' and  ac_no = "+depobj.getAc_no());
			stmt.executeUpdate("delete from  SignatureInstruction where ac_type = '"+ depobj.getAc_type()+"' and  ac_no = "+depobj.getAc_no());

			storeDepositMaster(nom_obj, depobj);

		} catch (SQLException sql ) {

			sql.printStackTrace();
			throw new RecordNotUpdated();
		} catch ( RecordNotInserted re ) {

			throw new RecordNotUpdated();
		}


		return true;
	}
	
	
	
} 
