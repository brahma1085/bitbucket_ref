package SRC.COM.SUNRISE.SERVER;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import SRC.COM.SUNRISE.UTILITY.Validation;
import SRC.COM.SUNRISE.UTILITY.EXCEPTION.RecordNotFound;
import SRC.COM.SUNRISE.UTILITY.EXCEPTION.RecordNotInserted;
import SRC.COM.SUNRISE.UTILITY.EXCEPTION.RecordNotUpdated;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.DepositMasterObject;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.DepositObject;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.DepositTransactionObject;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.ModuleObject;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.NomineeObject;

public class StoreDepTran {
	
Connection con ;
	
	public StoreDepTran(){
		
		con = GetDBConnection.getConnection();
		
	}
	
	public int StoreDepTran(DepositTransactionObject deptrn ) throws RecordNotInserted
	{
		
		int trnseq=0;
		int reg_no = 0;
		
		Statement statement = null;
		DepositMasterObject dep_mast=null;
		try {
			//geetha
			
			// insertion during deposit that is trn_type='D';
			PreparedStatement ps_int=con.prepareStatement("insert into DepositTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			
			Hashtable table = deptrn.getHash_dep();
			
			
		
			
			Enumeration en = table.keys();
			DepositObject depObj=null;
			while(en.hasMoreElements()) {
				String key=en.nextElement().toString();
				depObj=(DepositObject)table.get(key);
				
				ps_int.setString(1,deptrn.getAccType());
				ps_int.setInt(2,deptrn.getAccNo());
				
				ps_int.setInt(3,depObj.getTrn_seq());
				
				
				
				ps_int.setString(4,depObj.getTrn_date()); 
				ps_int.setString(5,depObj.getTrn_type());
			
				ps_int.setDouble(6,depObj.getDep_amt());
				ps_int.setDouble(7,depObj.getInt_amt());
				ps_int.setDouble(8,0);
				ps_int.setDouble(9,0);
				ps_int.setDouble(10,depObj.getRd_bal());
				ps_int.setString(11,null);
				ps_int.setString(12,depObj.getInterest_date());
				ps_int.setInt(13,0);
				ps_int.setString(14,null);
				ps_int.setString(15,depObj.getTrn_mode());
				ps_int.setString(16,deptrn.getDe_tml());
				ps_int.setString(17,deptrn.getCdind());
				ps_int.setDouble(18,depObj.getCum_int());
				ps_int.setString(19,deptrn.getDe_tml());
				ps_int.setString(20,deptrn.getDe_user());
				ps_int.setString(21,deptrn.getDe_date());
				ps_int.setString(22,deptrn.getDe_tml());
				ps_int.setString(23,deptrn.getDe_user());
				ps_int.setString(24,deptrn.getDe_date());
				
			
				
				System.out.println("before  trn seq.. in store dep trn====");
						
				if(ps_int.executeUpdate()==1) {
					
					System.out.println("sucessfully inserted into tran. iam in storetran..");
				}
				else {
					System.out.println("couldnt insert  tran...");
				}
			
			}
		
			
			}catch (Exception ecx) {
			
			
			ecx.printStackTrace();
			throw new RecordNotInserted();
		}
		
		return 1;
	
	}
	
	public DepositTransactionObject getDepositAccountInfo( String ac_type , int ac_no) throws RecordNotFound 
	{
		
		System.out.println("entering into getDepositAccount()..");
		Object[] obj = new Object[9];
		Hashtable hash_dep=new Hashtable();; 
		DepositObject dep_object=null;
		DepositTransactionObject deptrn=null;
	
		try {
		
			Statement stmt = con.createStatement();
			
			
			ResultSet rs = stmt.executeQuery("select * from DepositTransaction where ac_type = '" + ac_type+"' and ac_no = "+ ac_no);
			
			System.out.println("after resultset...");
			deptrn= new DepositTransactionObject();
			while ( rs.next()) {
				System.out.println("rs.getString(trn_date)>> "+rs.getString("trn_date"));
				
				//DepositMasterObject dep_obj = new DepositMasterObject();
				dep_object =new DepositObject();
				deptrn.setAccType(rs.getString("ac_type"));
				System.out.println("actype ===  "+rs.getString("ac_type"));
				deptrn.setAccNo(rs.getInt("ac_no"));
				
				dep_object.setTrn_date(rs.getString("trn_date"));
				dep_object.setTrn_seq(rs.getInt("trn_seq"));
				dep_object.setDep_amt(rs.getDouble("dep_amt"));
				dep_object.setTrn_type(rs.getString("trn_type"));
				dep_object.setInt_amt(rs.getDouble("int_amt"));
				
				
				dep_object.setCum_int(rs.getDouble("rd_bal"));
				dep_object.setInterest_date(rs.getString("int_date"));
				
				dep_object.setTrn_mode(rs.getString("trn_mode"));
				dep_object.setCum_int(rs.getDouble("cum_int"));
				System.out.println(rs.getInt("trn_seq")+"\t"+dep_object);
				hash_dep.put(""+rs.getInt("trn_seq"),dep_object);
				//dep_obj.setInt_upto_date(rs.getString("int_upto_date"));
				
			}
			deptrn.setHash_dep(hash_dep);
			Enumeration en=deptrn.getHash_dep().keys();
			while(en.hasMoreElements()) {
				String key=en.nextElement().toString();
				System.out.println(((DepositObject)deptrn.getHash_dep().get(key)).getTrn_date());
			}
				
					
		} catch ( Exception ec ) {
			
			throw new RecordNotFound();
		}
		
		
		return deptrn;
	}
	
	public boolean updateDepositAccount(DepositTransactionObject deptrn ) throws RecordNotUpdated
	{

		try {

			Statement stmt = con.createStatement();

			stmt.executeUpdate("delete from  DepositTransaction where ac_type = '"+ deptrn.getAccType()+"' and  ac_no = "+deptrn.getAccNo());
			
			StoreDepTran(deptrn);

		} catch (SQLException sql ) {

			sql.printStackTrace();
			throw new RecordNotUpdated();
		} catch ( RecordNotInserted re ) {

			throw new RecordNotUpdated();
		}


		return true;
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
	
	
	
	
} 

	
	/*String trf_acc_type = "";
	if(depmast.getTranDate()!=null && depmast.getTranDate().equals("T")==true){
		trf_acc_type=depmast.getLastTrndt()+" "+depmast.getLastTrnSeq();
	}
	file_logger.info("cum_interest==>"+depmast.getCumInterest());
	if(depmast.getCumInterest() > 0){
		*//**
		 * Interest payment
		 *//*
		ps_int.setString(1,depmast.getRenType());
		ps_int.setInt(2,depmast.getRenewalAccno());
		ps_int.setInt(3,++trnseq);
		//code added by sanjeet..
		ps_int.setString(4,date);
		//ps_int.setString(4,commonLocal.getSysDate()); 
		ps_int.setString(5,"A"); 
		ps_int.setDouble(6,dep_amt);
		ps_int.setDouble(7,0);
		ps_int.setDouble(8,0);
		ps_int.setDouble(9,depmast.getCumInterest());
		ps_int.setDouble(10,0);
//		code added by sanjeet..
		ps_int.setString(11,date);
		//ps_int.setString(11,commonLocal.getSysDate());
		ps_int.setString(12,null);
		ps_int.setInt(13,0);
		ps_int.setString(14,trf_acc_type);
		ps_int.setString(15,depmast.getTranDate());
		ps_int.setString(16,depmast.userverifier.getUserTml());
		ps_int.setString(17,"D");
		ps_int.setDouble(18,0);
		ps_int.setString(19,depmast.userverifier.getUserTml());
		ps_int.setString(20,depmast.userverifier.getUserId());
		ps_int.setString(21,depmast.userverifier.getUserDate());
		ps_int.setString(22,null);
		ps_int.setString(23,null);
		ps_int.setString(24,null);
		ps_int.executeUpdate();
		*//**
		 * deposit amount 
		 *//*
		ps_int.setString(1,depmast.getRenType());
		ps_int.setInt(2,depmast.getRenewalAccno());
		ps_int.setInt(3,++trnseq);
//		code added by sanjeet..
		ps_int.setString(4,date);
		//ps_int.setString(4,commonLocal.getSysDate()); 
		ps_int.setString(5,"E");
		ps_int.setDouble(6,0);
		ps_int.setDouble(7,0);
		ps_int.setDouble(8,dep_amt);
		ps_int.setDouble(9,0);
		ps_int.setDouble(10,0);
//		code added by sanjeet..
		ps_int.setString(11,date);
		//ps_int.setString(11,commonLocal.getSysDate());
		ps_int.setString(12,null);
		ps_int.setInt(13,0);
		ps_int.setString(14,depmast.getAccType()+" "+depmast.getAccNo());
		ps_int.setString(15,"T");
		ps_int.setString(16,depmast.userverifier.getUserTml());
		ps_int.setString(17,"D");
		ps_int.setDouble(18,0);
		ps_int.setString(19,depmast.userverifier.getUserTml());
		ps_int.setString(20,depmast.userverifier.getUserId());
		ps_int.setString(21,depmast.userverifier.getUserDate());
		ps_int.setString(22,null);
		ps_int.setString(23,null);
		ps_int.setString(24,null);
		ps_int.executeUpdate();
	}
	
	
	///
		
	
	PreparedStatement ps_store_trn=con.prepareStatement("insert into DepositTransaction values(?,?,?,?,'D',?,0,0,0,?,null,null,?,?,?,?,'C',0,?,?,?,null,null,null)");
	
	ps_store_trn.setString(1,deptrn.getAccType());
	
	System.out.println("geetha ac type===..."+deptrn.getAccType());
	ps_store_trn.setInt(2,deptrn.getAccNo());
	System.out.println("geetha ac no ===..."+deptrn.getAccNo());
	ps_store_trn.setInt(3,1);
	ps_store_trn.setString(4,deptrn.getTranDate());
	ps_store_trn.setString(5,"D");
			
	ps_store_trn.setDouble(6,deptrn.getDepositAmt());
	ps_store_trn.setInt(7,0);
	ps_store_trn.setString(8,deptrn.getName());
	ps_store_trn.setString(9,"T");
	ps_store_trn.setDouble(10,deptrn.getRDBalance());
	ps_store_trn.setString(11,deptrn.getMat_date());
	ps_store_trn.setString(12,deptrn.getTranNarration());
	ps_store_trn.setString(13,deptrn.getTranNarration());
	
	ps_store_trn.executeUpdate();
	
	*/
	
	//while loop
	
	//geetha
	

	
	
	
	


