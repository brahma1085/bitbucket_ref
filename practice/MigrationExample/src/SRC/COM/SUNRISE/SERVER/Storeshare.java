package SRC.COM.SUNRISE.SERVER;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Hashtable;

import SRC.COM.SUNRISE.UTILITY.EXCEPTION.RecordNotInserted;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.ModuleObject;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.NomineeObject;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.ShareMasterObject;



public class Storeshare {
	
	public int  storeSharedet(ShareMasterObject shobj, NomineeObject nom_obj ){
		
		System.out.println("in share server");
		Connection conn = GetDBConnection.getConnection();
		int reg_no = 0;
		
		try {
			
			Statement statement = null;
			
			if (nom_obj != null) {
				
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
				
				if ( nom_obj.getRegno() == 0) {
				ResultSet rs = stmt.executeQuery("select min_period from Modules where modulecode='1019000'");
				
				rs.next();
				 reg_no = rs.getInt("min_period");
				} else {
					
					reg_no = nom_obj.getRegno();
				}
				
				int r = stmt.executeUpdate("update Modules set min_period =  (min_period +1) where modulecode= '1019000'");
				
				PreparedStatement preparedstatement=conn.prepareStatement("insert into NomineeMaster values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,null,null)");
				statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				
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
					
					preparedstatement.setString(3,shobj.getShareAccType());
					
					preparedstatement.setInt(4,shobj.getShareNumber());
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
			
		
			Statement stmt = conn.createStatement();
			Statement stmt_del = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			PreparedStatement pstmt = conn.prepareStatement("insert into ShareMaster values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			int updated=0;

			System.out.println("inserting into sharemaster");
			
			pstmt.setString(1,shobj.getShareAccType());
			
			pstmt.setInt(2,shobj.getShareNumber());
			pstmt.setString(3,"P");
			pstmt.setInt(4, 0);
			pstmt.setInt(5,shobj.getBranchCode());
			pstmt.setInt(6,shobj.getCustomerId());
			pstmt.setInt(7,1);
			pstmt.setInt(8,shobj.getMemberCategory());
			pstmt.setString(9,shobj.getIssueDate());	
			pstmt.setDouble(10,shobj.getShareBalance());
			pstmt.setString(11, null);
			pstmt.setInt(12,0);		
			pstmt.setInt(13,reg_no);
			pstmt.setInt(14,shobj.getNumberCert());
			pstmt.setString(15,shobj.getDivUptoDate());
			pstmt.setString(16,"F");
			pstmt.setString(17,null);
			pstmt.setString(18,null);
			pstmt.setString(19,null);
			pstmt.setString(20,shobj.getPayMode());
			pstmt.setString(21,shobj.getPaymentAcctype());
			pstmt.setInt(22,shobj.getPaymentAccno());
			System.out.println("the ac_no is-----"+shobj.getPaymentAccno());
			
			//check here 06/11/2007
			pstmt.setString(23, null);
			pstmt.setString(24,null);
			pstmt.setString(25,null);
			pstmt.setString(26,null);
			pstmt.setString(27,shobj.getDe_user());
			pstmt.setString(28,shobj.getDe_tml());
			//pstmt.setString(17,ms.uv.getUserDate());
			//pstmt.setString(17,ms.getRelationtoDirector());
			pstmt.setString(29,shobj.getDe_datetime());
			pstmt.setString(30,shobj.getDe_user());
			pstmt.setString(31,shobj.getDe_tml());
			pstmt.setString(32,shobj.getDe_datetime());
			System.out.println("Before Executing Sharemaster...........");
			updated=pstmt.executeUpdate();		
			System.out.println("the updated is========================== "+updated);
			System.out.println("after Executing Share Master...........");
			
			Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE ,ResultSet.CONCUR_UPDATABLE);
			
			ResultSet rs_dist = st.executeQuery("select * from Modules where modulecode ='1001001'");
			rs_dist.next();
			int dist_from = rs_dist.getInt("max_renewal_count")+1; 
			PreparedStatement pre_tran = conn.prepareStatement("insert into ShareTransaction values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			
			Hashtable table = shobj.getCertificate(); 
			Enumeration en = table.keys();
			int dist_to = 0;
			int share_cet = 0;
			
			while( en.hasMoreElements()) {
				
				Object obj = en.nextElement();
				share_cet = new Integer (obj.toString());
				dist_to =  new Integer (table.get(obj).toString()).intValue() + dist_from;
				
				pre_tran.setString(1,shobj.getShareAccType());                           //ac_type       varchar(10)    YES     MUL     (NULL)         
				pre_tran.setString(2,null);						                         //trn_no        int(11)        YES             (NULL)         
				pre_tran.setInt(3, shobj.getShareNumber()); 	                         //ac_no         int(11)        YES     MUL     (NULL)         
				pre_tran.setInt(4, 0);							                         //trn_seq       int(11)        YES             (NULL)         
				pre_tran.setString(5, shobj.getTrn_date());                              //trn_date      varchar(10)    YES     MUL     (NULL)         
				pre_tran.setString(6, "A");						                         //trn_type      varchar(10)    YES             (NULL);         
				pre_tran.setDouble(7, shobj.getShareBalance());							//trn_amt       float(20,2)    YES             (NULL)         
				pre_tran.setInt(8, 0);						                           	//ref_no        int(11)        YES             (NULL)         
				pre_tran.setString(9, "Allotment");				                        //trn_narr      varchar(25)    YES             (NULL)         
				pre_tran.setString(10, "T");				                            //trn_mode      varchar(5)     YES             (NULL)         
				pre_tran.setString(11, shobj.getDe_tml());                              //trn_source    varchar(5)     YES             (NULL)         
				pre_tran.setString(12, "C");											//cd_ind        varchar(5)     YES             (NULL)         
				pre_tran.setString(13, "D"); 				                               //alt_ind       varchar(5)     YES             (NULL)         
				pre_tran.setString(14, "P");												//susp_ind      char(1)        YES             (NULL)         
				pre_tran.setDouble(15, shobj.getBalance());		                         //share_bal     float(20,2)    YES             (NULL);         

				pre_tran.setInt(16, dist_from);											              	//dist_no_from  int(20)        YES             (NULL)         
				pre_tran.setInt(17, dist_to);												           //dist_no_to    int(20)        YES             (NULL)         
				pre_tran.setInt(18, share_cet);											             	//sh_cert_no    int(25)        YES             (NULL)         

				pre_tran.setString(19, null);											   	//sh_cert_dt    varchar(10)    YES             (NULL)         
				pre_tran.setString(20, "F");												//cert_prtd     enum('F','T')  YES             (NULL)         
				pre_tran.setString(21, "P");												//markdel       enum('F','T')  YES             (NULL)         
				pre_tran.setString(22, shobj.getDe_tml());												//de_user       varchar(10)    YES             (NULL)         
				pre_tran.setString(23, shobj.getDe_user());												//de_tml        varchar(10)    YES             (NULL)         
				pre_tran.setString(24, shobj.getDe_datetime());												//de_date       varchar(20)    YES             (NULL)         
				pre_tran.setString(25, shobj.getDe_tml());												//ve_user       varchar(10)    YES             (NULL)         
				pre_tran.setString(26, shobj.getDe_user());												//ve_date       varchar(20)    YES             (NULL)         
				pre_tran.setString(27, shobj.getDe_datetime());												//ve_tml        varchar(10)    YES             (NULL)    

				
				int upd = pre_tran.executeUpdate();
				
				dist_from = dist_to + 1;
			
			}
			
			int cou = st.executeUpdate("update Modules set  max_renewal_count = "+dist_to +" where modulecode = '1001001'");
			
			System.out.println(shobj.getShareAccType()+"--------" + shobj.getShareNumber()+"--------"+shobj.getCustomerId()+"---------"+shobj.getName());
			
			Statement st_sig = conn.createStatement();
			if(st_sig.executeUpdate("insert into SignatureInstruction values('"+shobj.getShareAccType()+"','"+shobj.getShareNumber()+"','"+shobj.getCustomerId()+"','"+shobj.getName()+"',null,null,null,'single')")!=1)
				throw new RecordNotInserted();
			
			return updated;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
	
	public ModuleObject[] getModulecode(int a,String str){
		Connection conn = GetDBConnection.getConnection();
		ResultSet rs=null;
		ModuleObject[] mobj=null;
		try {
		
			Statement stmt = conn.createStatement();
			Statement stmt_del = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			if(a==2){
			rs=stmt.executeQuery("select x.* from Modules x,Modules y where x.modulecode between y.modulecode+1 and y.modulecode+999  and y.modulecode in ("+str+")  order by modulecode");
			}
			
			if(rs.next())
			{	
				rs.last();
				mobj=new ModuleObject[rs.getRow()];
				rs.beforeFirst();
			}
			int i=0;
			while(rs.next())
			{
				mobj[i]=new ModuleObject();
				mobj[i].setModuleCode(rs.getString("modulecode"));
				mobj[i].setModuleDesc(rs.getString("moduledesc"));
				mobj[i].setModuleAbbrv(rs.getString("moduleabbr"));
				i++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return mobj;
	}
	
	
	public ShareMasterObject updateshare(int sharenum){
		
		ShareMasterObject shobj=null;
		Connection conn = GetDBConnection.getConnection();
		ResultSet rs=null;
		NomineeObject nom_obj = null;
		try{
			Statement stmt = conn.createStatement();
			Statement stmt2 = conn.createStatement();
			Statement stmt_del = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			rs=stmt.executeQuery("select * from ShareMaster where ac_no="+sharenum);
			Statement stmt3 = conn.createStatement();
			
			
			
			while(rs.next()){
				
				
				shobj = new ShareMasterObject();
				 shobj.setShareNumber(rs.getInt("ac_no"));
				 
				 System.out.println(shobj.getShareNumber()+" sssssssssssssss no");
				// shobj.setShareType(rs.getString("sh_ind"));
				 shobj.setBranchCode(rs.getInt("br_code"));
				 shobj.setCustomerId(rs.getInt("cid"));
				 shobj.setMemberCategory(rs.getInt("mem_cat"));
				 System.out.println("category---------------"+rs.getInt("mem_cat"));
				 shobj.setIssueDate(rs.getString("mem_issuedate"));
				 shobj.setShareBalance(rs.getDouble("share_val"));
				 shobj.setIntroducerAccno(rs.getInt("intr_ac_no"));
				 shobj.setNomineeno(rs.getInt("nom_no"));
				 shobj.setNumberCert(rs.getInt("no_cert"));
				 shobj.setDivUptoDate(rs.getString("div_uptodt"));
				 shobj.setCloseDate(rs.getString("mem_cl_date"));
				 shobj.setShareStatus(rs.getString("share_stat"));
				 shobj.setPayMode(rs.getString("pay_mode"));
				 shobj.setPaymentAcctype(rs.getString("pay_ac_type"));
				 shobj.setPaymentAccno(rs.getInt("pay_ac_no"));
				 
				 
				 if ( rs.getInt("nom_no") > 0) {
						
						ResultSet rs3 = stmt2.executeQuery("select * from NomineeMaster where reg_no = "+rs.getInt("nom_no")+" and ac_type ='"+rs.getString("ac_type")+"' and  ac_no ="+rs.getInt("ac_no"));
						if ( rs3.next() ) {
							
							System.out.println("inside the NNNNNNNNNNNNNNNNNNNNn");
							 nom_obj = new NomineeObject();
							
							nom_obj.setCid(rs3.getInt("cid"));
							nom_obj.setName(rs3.getString("name"));
							nom_obj.setDob(rs3.getString("dob"));
							nom_obj.setSex(rs3.getInt("sex")); /// 1 male...0 - female
							nom_obj.setRelation(rs3.getString("relation"));
							nom_obj.setAddress(rs3.getString("address"));
							nom_obj.setRegno(rs3.getInt("reg_no"));
							
						}
						 
						shobj.setNominee(nom_obj);
					}
				 
				 
			}
			
			ResultSet rs_tr = stmt3.executeQuery("select distinct (sh_cert_no),(dist_no_to - dist_no_from) as no ,  share_bal from ShareTransaction where ac_no = " + sharenum);
			
			if ( shobj != null) {
			
				if ( rs_tr.next()) {
					
					shobj.setBalance(rs_tr.getDouble("share_bal"));
				}
				
				rs_tr.beforeFirst();
				Hashtable table = new Hashtable();
				while ( rs_tr.next() ) {
				
				
					table.put( rs_tr.getInt("sh_cert_no"), rs_tr.getInt("no"));
					
					
				}
				                    
				shobj.setCertificate(table);
				
			}
			
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	
		
		
		
		
		return shobj;
	}
	
	public int  deleteshare(ShareMasterObject shr_obj,NomineeObject nom){
		
		Connection conn = GetDBConnection.getConnection();
		ResultSet rs=null;
		try{
			
			System.out.println("Delete from db");
			Statement stmt = conn.createStatement();
			Statement stmt_del = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			stmt.execute("delete from ShareMaster where ac_no="+ shr_obj.getShareNumber());
			stmt.execute("delete from NomineeMaster where ac_type = '1001001' and ac_no ="+  shr_obj.getShareNumber());
			stmt.execute("delete from ShareTransaction where ac_no="+ shr_obj.getShareNumber());
			System.out.println("deleted from db");
			
			
			return storeSharedet( shr_obj,nom);
			

		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return 0;
	}
	
}
