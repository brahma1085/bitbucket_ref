package SRC.COM.SUNRISE.SERVER;

//import general.Validations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Hashtable;

import SRC.COM.SUNRISE.UTILITY.Validation;
import SRC.COM.SUNRISE.UTILITY.EXCEPTION.CustomerNotFoundException;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.DepositObject;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.LoanMasterObject;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.LoanObject;
import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.LoanTransactionObject;



public class StoreLoanTran {
Connection con ;
	
	public StoreLoanTran(){
		
		con = GetDBConnection.getConnection();
		
	}
	
	
	
	public int getCid(String actype,int no) throws CustomerNotFoundException{
		int cid=0;
	
		try{
			System.out.println("Actype in store"+actype+"Accno"+no);		
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs=null;
			rs = stmt.executeQuery("select cid from LoanMaster where ac_type='"+actype+"' and ac_no="+no);
			if(rs!=null && rs.next())
				cid=rs.getInt(1);
			
		}catch(SQLException exception){exception.printStackTrace();}
	
		
		return cid;
	}
	
	
	

	public LoanMasterObject getLoanMaster(String actype,int accno){
		LoanMasterObject lnobj=null;
		try{
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select * from LoanMaster where ac_type='"+actype+"' and  ac_no="+accno+" ");
		if(rs.next()){
			lnobj=new LoanMasterObject();
			lnobj.setAccType(rs.getString("ac_type"));
			lnobj.setAccNo(rs.getInt("ac_no"));
			lnobj.setCustomerId(rs.getInt("cid"));
			lnobj.setMailingAddress(rs.getInt("addr_type"));
			lnobj.setApplicationSrlNo(rs.getInt("appn_srl"));
			lnobj.setApplicationDate(rs.getString("appn_date"));
			
			lnobj.setInterestType(rs.getInt("int_type"));
			lnobj.setInterestRate(rs.getDouble("int_rate"));
			lnobj.setInterestRateType(rs.getInt("int_rate_type"));
			lnobj.setInterestRate(rs.getDouble("int_rate"));
			
			lnobj.setRequiredAmount(rs.getDouble("req_amt"));
			lnobj.setSanctionedAmount(rs.getDouble("sanc_amt"));
			lnobj.setPurposeCode(rs.getInt("pps_code"));
			lnobj.setPayMode(rs.getString("pay_mode"));
			lnobj.setPaymentAccno(rs.getInt("pay_ac_no"));
			lnobj.setPaymentAcctype(rs.getString("pay_ac_type"));
			//Vinay
			lnobj.setRemd_no(rs.getInt("remd_no"));
			lnobj.setRemd_date(rs.getString("remd_date"));
			lnobj.setLdgPrntDate(rs.getString("ldgprt_date"));
			lnobj.setLastTrndt(rs.getString("lst_trn_date"));
			lnobj.setLastTrnSeq(rs.getInt("lst_tr_seq"));
			
			lnobj.setClosedt(rs.getString("close_date"));
			
			lnobj.setInterestUpto(rs.getString("int_upto_date"));
			
			lnobj.setLoanSanctioned(rs.getString("loan_sanc").equals("N")?false:true);
			//file_logger.info("=========================Loan Sanctioned"+rs.getString("loan_sanc"));
			//file_logger.info("=========================Loan Sanctioned"+lnobj.isLoanSanctioned());
			lnobj.setSanctionedAmount(rs.getDouble("sanc_amt"));
			lnobj.setNoOfInstallments(rs.getInt("no_inst"));
			lnobj.setInstallmentAmt(rs.getDouble("inst_amt"));
			lnobj.setWeakerSection(rs.getString("wk_sect").equals("N")?false:true);
			//lnobj.setPrioritySector(rs.getString("psect_cd").equals("N")?false:true);
			
			lnobj.setPrior(rs.getInt(("psect_cd")));
			lnobj.setSanctionVerified(rs.getString("sanc_ver").equals("N")?false:true);
			lnobj.setDisbursementLeft(rs.getDouble("disb_left"));
			lnobj.setRelative(rs.getString("rel"));
			
			lnobj.setShareAccType(rs.getString("sh_type"));
			lnobj.setShareAccNo(rs.getInt("sh_no"));
			
			if(rs.getString("holday_mth")!=null)
				lnobj.setHolidayPeriod(rs.getInt("holday_mth"));
			lnobj.setDirectorCode(rs.getInt("dir_code"));
			lnobj.setNoOfSurities(rs.getInt("no_surities"));
			lnobj.setNoOfCoBorrowers(rs.getInt("no_coborrowers"));
			
			lnobj.setDepositAccType(rs.getString("td_ac_type"));
			lnobj.setDepositAccNo(rs.getInt("td_ac_no"));
			
			if(rs.getString("sex_cd").equals("M"))
				lnobj.setSexInd('M');	
			else
				lnobj.setSexInd('F');
			
		}
		}catch(Exception e){e.printStackTrace();}
	  
	   return lnobj;
	}
	
	
	
	public LoanTransactionObject getLoanTranInfo(String acctype,int accno){
		
		LoanTransactionObject lntrn = null;
		LoanObject lob = null;
		Hashtable hash_dep=new Hashtable();
		try{
			Statement stmt = con.createStatement();
			System.out.println("Acctype"+acctype+"Accno"+accno);
			ResultSet rs = stmt.executeQuery("select * from LoanTransaction where ac_type = '" + acctype+"' and trn_type not in ('D','S') and ac_no = "+accno);
			lntrn = new LoanTransactionObject();
			
			System.out.println("+++++++++++++122");
			while(rs.next()){
				lob = new LoanObject();
				System.out.println("+++++++++++++");
				lntrn.setAccType(rs.getString("ac_type"));
				lntrn.setAccNo(rs.getInt("ac_no"));
				/*String sbno = rs.getString("trn_narr");
				System.out.println("SB=====><"+sbno);
				String no = sbno.substring(8,10);
				System.out.println("SB=====><====="+no);
				lntrn.setSbaccno(Integer.parseInt(no));*/
				lob.setTrn_date(rs.getString("trn_date"));
				lob.setTrn_seq(rs.getInt("trn_seq"));
				lob.setTrn_type(rs.getString("trn_type"));
				lob.setAmt(rs.getDouble("trn_amt"));
				lob.setTrn_mode(rs.getString("trn_mode"));
				lob.setInt_date(rs.getString("int_date"));
				System.out.println("=====>"+rs.getString("trn_amt"));
				hash_dep.put(""+rs.getInt("trn_seq"),lob);
			}
			lntrn.setHash_loan(hash_dep);
			Enumeration en=lntrn.getHash_loan().keys();
			while(en.hasMoreElements()) {
				String key=en.nextElement().toString();
				
			}
		}catch(Exception e){e.printStackTrace();}
		System.out.println("Lntrn"+lntrn.toString());
		return lntrn;
	}
	
	
	
	public int storeLoanTransaction(LoanTransactionObject lntrnobj)  {
		int result=0;
		Statement stmt=null;
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ResultSet rs = null;
		int trnseq = 0;
		LoanObject lob = null;
		
		try {
		
		System.out.println("Before Inserting");	
		//stmt.executeUpdate("delete from LoanTransaction where ac_type='"+lntrnobj.getAccType()+"' and ac_no="+lntrnobj.getAccNo()+"  and trn_type not in('D','S')");
		PreparedStatement ps1 = con.prepareStatement("insert into LoanTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		System.out.println("In Row");
		Hashtable table = lntrnobj.getHash_loan();
		System.out.println("Table"+table.size());
		Enumeration en = table.keys();
		
		while(en.hasMoreElements()){
			
		String key=en.nextElement().toString();
		System.out.println("Bang");
		lob = (LoanObject)table.get(key);
		System.out.println("Inn Bang 2");	
		ps1.setString(1,lntrnobj.getAccType()); //actype
		ps1.setInt(2,lntrnobj.getAccNo());
		ps1.setInt(3,lob.getTrn_seq()); //trn_seq
		ps1.setString(4,lob.getTrn_date());//trn_date
		
		System.out.println("Inn Bang");
		if(lob.getTrn_type().equalsIgnoreCase("Int")) 
			ps1.setString(5,"I"); //trn_type
		else
			ps1.setString(5,"R"); //trn_type
		
			
		ps1.setDouble(6,lob.getAmt());//trn_amt
		System.out.println("In Col 6");
		if(lob.getTrn_mode().equalsIgnoreCase("Tr"))
			ps1.setString(7,"T"); //trn_mode
		if(lob.getTrn_mode().equalsIgnoreCase("C"))
			ps1.setString(7,"C"); //trn_mode
		else
			ps1.setString(7,"G"); //trn_mode
		ps1.setString(8,"LN02");//trn_source
		
		ps1.setInt(9,0); //refno
		
		if(lntrnobj.getTranNarration().equalsIgnoreCase("1002001"))
			ps1.setString(10,lntrnobj.getTranNarration()+" "+lntrnobj.getSbaccno()); //trn_narr
		else
			ps1.setString(10,"Csh");
			
		ps1.setString(11,null); //rcy_date
		
		if(lob.getTrn_type().equalsIgnoreCase("Recovery"))
				ps1.setString(12,"C");//cd_ind
		else 
				ps1.setString(12,"D");//cd_ind
		
		ps1.setString(13,null); //int_date
		ps1.setDouble(14,0.00);//pr_amt
		ps1.setDouble(15,0.00); //int_amt
		ps1.setDouble(16,0.00);//pen_amt
		
		ps1.setDouble(17,0.00); //otheramt
		ps1.setDouble(18,0.00);//extraint
		ps1.setDouble(19,10000); //pr_bal
		ps1.setString(20,"LN01");//de_tml
		
		ps1.setString(21,"Vin");//de_user
		ps1.setString(22,"05/05/2008  11:00:00"); //de_date
		ps1.setString(23,"LN01");//ve_tml
		ps1.setString(24,"Vin"); //ve_user
		ps1.setString(25,"05/05/2008  11:00:00");//ve_date
		result = ps1.executeUpdate();
		System.out.println("Result===>"+result);
		}
		//stmt.executeUpdate("update LoanMaster set int_upto_date='"++"' and lst_tr_seq="++" and lst_trn_date='"++"' where ac_type='"+lntrnobj.getAccType()+"' and ac_no="+lntrnobj.getAccNo()+" ");
		
		
		}catch(Exception e) {e.printStackTrace();}
		
		
		if(result>0)
			return result;
		else
			return result;
	}
	
	
	public int updateLoanTransaction(LoanTransactionObject lntrnobj){
		
		
		Statement stmt =null;
		try{
			stmt = con.createStatement();
			stmt.executeUpdate("delete from LoanTransaction where ac_type='"+lntrnobj.getAccType()+"' and ac_no="+lntrnobj.getAccNo()+"  and trn_type not in ('D','S')");
			System.out.println("In Update"+lntrnobj.getTranMode()+"Type"+lntrnobj.getTranType());
			storeLoanTransaction(lntrnobj);
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	
	public int disburseLoan(String acctype,int accno,double amtsanc,Object[][] data,String date){
		
		
		//Restrict the 'S' entries only to Loans accounts. No 'S' entries for LD accounts
		PreparedStatement ps1=null;
		try{
			
			ps1 = con.prepareStatement("insert into LoanTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			
			ps1.setString(1,acctype);
			ps1.setInt(2,accno);
			ps1.setInt(3,1); //trn_seq
			ps1.setString(4,date);//trn_date
			ps1.setString(5,"D"); //trn_type
			ps1.setDouble(6,amtsanc);//trn_amt
			ps1.setString(7,"T"); //trn_mode
			ps1.setString(8,null);//trn_source
			ps1.setInt(9,0); //refno
			ps1.setString(10,null); //trn_narr
			ps1.setString(11,null); //rcy_date
			ps1.setString(12,"D");//cd_ind
			ps1.setString(13,Validation.addDays(date,-1)); //int_date
			
			
			ps1.setInt(14,0);//pr_amt
			ps1.setInt(15,0); //int_amt
			ps1.setInt(16,0);//pen_amt
			
			ps1.setInt(17,0); //otheramt
			ps1.setInt(18,0);//extraint
			ps1.setDouble(19,amtsanc); //pr_bal
			
			
			ps1.setString(20,"LN01");//de_tml
			
			ps1.setString(21,"Vinay");//de_user
			ps1.setString(22,"02/05/2008  11:00:52"); //de_date
			ps1.setString(23,"LN01");//ve_tml
			ps1.setString(24,"Vinay"); //ve_user
			ps1.setString(25,"02/05/2008  11:59:52");//ve_date
			ps1.addBatch();
			
			for(int i=0;i<(data.length-1);i++)
			{
				ps1.setInt(3,0);
				ps1.setString(4,data[i][1].toString());
				ps1.setString(5,"S");
				ps1.setDouble(6,Double.parseDouble(data[i][4].toString()));
				ps1.setString(12,"C");
				ps1.setString(13,Validation.addDays(data[i][1].toString(),-1));
				ps1.setDouble(14,Double.parseDouble(data[i][2].toString()));
				ps1.setDouble(15,Double.parseDouble(data[i][3].toString()));
				ps1.setDouble(19,Double.parseDouble(data[i][5].toString()));
				ps1.setString(7,"");
				ps1.setString(10,"");
				
				ps1.addBatch();
			}
			   ps1.executeBatch();
			   return 1;
		}catch (Exception e) {
            e.printStackTrace();
            return 0;
		}
		
		
		
		
		
	}
	
	
	
	
	
}
