package SRC.COM.SUNRISE.SERVER;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sun.org.apache.bcel.internal.generic.LMUL;

import SRC.COM.SUNRISE.UTILITY.VALUEOBJECT.LoanMasterObject;

public class StoreLoan {
	
	Connection conn;
	
	public StoreLoan(){
		conn = GetDBConnection.getConnection();
	}
	
	public boolean storeLoanMaster(LoanMasterObject loanmast){
		boolean flg=false;
		try {
			PreparedStatement ps1 = conn.prepareStatement("insert into LoanMaster values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			
			ps1.setString(1,loanmast.getAccType());
			ps1.setInt(2,loanmast.getAccNo());
			ps1.setInt(3,loanmast.getNoOfCoBorrowers());
			ps1.setInt(4,loanmast.getNoOfSurities());
			ps1.setInt(5,loanmast.getCustomerId());
			ps1.setInt(6,loanmast.getMailingAddress());
			ps1.setInt(7,0);
			ps1.setString(8,loanmast.getApplicationDate());
			ps1.setDouble(9,loanmast.getRequiredAmount());
			ps1.setInt(10,loanmast.getShareAccNo());
			ps1.setString(11,"1001001");
			ps1.setInt(12,0);
			ps1.setInt(13,0);
			ps1.setString(14,loanmast.getDepositAccType());
			ps1.setInt(15,loanmast.getDepositAccNo());
			ps1.setInt(16,loanmast.getInterestType());//int_type
			
			ps1.setInt(17,loanmast.getInterestRateType());
			ps1.setDouble(18,loanmast.getInterestRate());
			ps1.setInt(19,0);
			ps1.setString(20,"N");
			ps1.setInt(21,loanmast.getSexInd());
			ps1.setString(22,null);
			ps1.setInt(23,0);
			ps1.setString(24,null);
			ps1.setInt(25,0);
			ps1.setString(26,loanmast.getSanctionDate());
			ps1.setDouble(27,loanmast.getSanctionedAmount());
			ps1.setString(28,"Y");
			ps1.setString(29,"Y");
			ps1.setInt(30,loanmast.getNoOfInstallments());
			ps1.setDouble(31,loanmast.getInstallmentAmt());
			ps1.setString(32,null);
			ps1.setString(33,null);
			ps1.setInt(33,0);
			
			ps1.setString(34,"N");
			ps1.setString(35,null);
			ps1.setString(36,loanmast.getPayMode());
			ps1.setString(37,loanmast.getPaymentAcctype());
			ps1.setInt(38,loanmast.getPaymentAccno());
			ps1.setInt(39,0);
			ps1.setString(40,null);
			ps1.setDouble(41,loanmast.getDisbursementLeft());
			ps1.setString(42,null);
			ps1.setString(43,null);
			ps1.setString(44,null);
			ps1.setString(45,"Vinay");
			ps1.setString(46,"Vinay");
			ps1.setString(47,"Vinay");
			ps1.setString(48,"Vinay");
			ps1.setString(49,"Vinay");
			ps1.setString(50,"Vinay");
			ps1.setString(51,"Vinay");
			int temp=ps1.executeUpdate();
			System.out.println(temp);
			if(temp>0)
				flg=true;
		} catch (SQLException e) {
				e.printStackTrace();
		}
		return flg;
		
	}
	
	public LoanMasterObject getLoanMaster(String actype,int accno){
		LoanMasterObject lnobj=null;
		try{
		Statement stmt = conn.createStatement();
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
	
	public ResultSet LoanPurposes()
	{
		ResultSet rs=null;
		try{
		Statement stmt=conn.createStatement();
		rs=stmt.executeQuery("select * from LoanPurposes");
		}
		catch(Exception e){e.printStackTrace();}
		return rs;
	}
	
	public ResultSet getting_priority()
	{
		ResultSet rs=null;
		try{
			Statement stmt=conn.createStatement();
			rs=stmt.executeQuery("select mid(pr_desc,1,30) from PriorityMaster");
		   }
		catch(Exception e){e.printStackTrace();}
		
		return rs;
	}


}
