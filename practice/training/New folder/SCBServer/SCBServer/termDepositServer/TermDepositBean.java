package termDepositServer;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import client.HomeFactory;

import masterObject.customer.CustomerMasterObject;
import masterObject.frontCounter.AccountInfoObject;
import masterObject.frontCounter.AccountTransObject;
import masterObject.frontCounter.PayOrderObject;
import masterObject.general.AccountObject;
import masterObject.general.NomineeObject;
import masterObject.general.SignatureInstructionObject;
import masterObject.general.TrfVoucherObject;
import masterObject.termDeposit.DepositIntRate;
import masterObject.termDeposit.DepositIntRepObject;
import masterObject.termDeposit.DepositMasterObject;
import masterObject.termDeposit.DepositReportObject;
import masterObject.termDeposit.DepositTransactionObject;

import commonServer.CommonHome;
import commonServer.CommonLocal;
import commonServer.CommonLocalHome;
import commonServer.CommonRemote;
import commonServer.GLTransObject;

import customerServer.CustomerLocal;
import customerServer.CustomerLocalHome;
import exceptions.AccountClosedException;
import exceptions.AccountNotFoundException;
import exceptions.ControlNumberAttached;
import exceptions.CustomerNotFoundException;
import exceptions.DateFormatException;
import exceptions.FreezedAccountException;
import exceptions.InOperativeAccountException;
import exceptions.RecordNotInsertedException;
import exceptions.RecordNotUpdatedException;
import exceptions.RecordsNotFoundException;
import exceptions.RecordsNotInsertedException;
import exceptions.ScrollNumberAttached;
import exceptions.SignatureNotInsertedException;
import exceptions.Verified;
import frontCounterServer.FrontCounterLocal;
import frontCounterServer.FrontCounterLocalHome;
import general.Validations;

/**
 * @author Athul
 */

public class TermDepositBean implements SessionBean {
	private CommonLocalHome commonLocalHome = null;
	CommonLocal commonLocal = null;
	private DataSource ds;
	SessionContext sessionContext = null;
	int gc = 0;
	private ArrayList vector = new ArrayList();
	private FrontCounterLocalHome front_local_home = null;
	private CustomerLocalHome cust_local_home = null;

	public Context getContext() {
		try {
			return new InitialContext();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int firstMethod() {
		Connection con = null;
		try {
			con = getConnection();
			Statement st = con.createStatement();
			st.executeUpdate("update DepositMasterLog set dep_yrs=666");
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			try {
				con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return 1;
	}

	public int secondMethod() {
		Connection con = null;
		try {
			con = getConnection();
			Statement st = con.createStatement();
			st.executeUpdate("Update DepositMasterLog set dep_mths=777");
		} catch (SQLException e) {
			e.printStackTrace();
			sessionContext.setRollbackOnly();
			return 0;
		} catch (Exception e1) {
			e1.printStackTrace();
			sessionContext.setRollbackOnly();
			return 0;
		} finally {
			try {
				con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return 1;
	}

	public int storeGenparam() {
		int count = 0;
		Connection con = null;
		System.out.println("with in Method");
		try {
			con = getConnection();
			Statement st = con.createStatement();
			while (count == 0) {
				try {
					st
							.executeUpdate("update DepositMasterLog set dep_yrs=" + 625);

					secondMethod();

					st
							.executeUpdate("update DepositMasterLog set dep_yrs=" + 525);
					PreparedStatement ps = con.prepareStatement("commit");
					ps.executeUpdate();

					PreparedStatement pss = con.prepareStatement("commit");
					pss.executeUpdate();

				} catch (SQLException e) {
					e.printStackTrace();
					PreparedStatement ps = con.prepareStatement("rollback");
					ps.executeUpdate();
				}
				count++;
			}
		} catch (SQLException e) {
			System.out.println("outer1");
			sessionContext.setRollbackOnly();
			return 0;
		} catch (Exception e) {
			sessionContext.setRollbackOnly();
			return 0;
		} finally {
			System.out.println("finnaly");
			try {
				con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return 1;
	}

	public void ejbCreate() {
		try {
			Context ctx = getContext();
			commonLocalHome = (CommonLocalHome) ctx.lookup("COMMONLOCALWEB");
			front_local_home = (FrontCounterLocalHome) ctx
					.lookup("FRONTCOUNTERLOCALWEB");
			cust_local_home = (CustomerLocalHome) ctx
					.lookup("CUSTOMERLOCALWEB");
			System.out.println(" Compiled TDBean....");
			ds = (javax.sql.DataSource) ctx.lookup("java:MySqlDS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private Connection getConnection() throws SQLException {
		Connection con = ds.getConnection("root", "");
		if (con == null)
			throw new SQLException();
		return con;
	}

	public boolean attached(int ac_no, String ac_type, boolean att_ind,
			String date) {

		ResultSet rs = null;
		Statement stmt = null;

		Connection conn = null;
		int scroll_no;
		String trn_date = date;

		try {
			conn = getConnection();
			stmt = conn.createStatement();

			if (att_ind == true) {

				rs = stmt
						.executeQuery("update DayCash set ac_no=0,attached='F' where attached='T' and scroll_date = '"
								+ date
								+ "' and ac_type like '"
								+ ac_type
								+ "' ");

				System.out.println("inside attached functionnnn..");
			} else
				att_ind = true;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return false;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return true;

	}

	// 10-11-2011
	public boolean storeSignatureDetails(SignatureInstructionObject a[],
			int acno) throws SignatureNotInsertedException {
		System.out.println("Storing SignatureDetails...");
		System.out.println("The length of signature is" + a.length);
		Connection conn = null;
		try {
			conn = getConnection();
			System.out.println("Teh ac_no in sign is" + acno);
			PreparedStatement ps = conn
					.prepareStatement("insert into SignatureInstruction values(?,?,?,?,?,?,?,?)");
			// for(int i=0;i<a.length;i++)
			// {
			ps.setString(1, a[0].getSAccType());
			ps.setInt(2, acno);
			ps.setInt(3, a[0].getCid());
			ps.setString(4, a[0].getName());
			ps.setString(5, a[0].getDesignation());
			ps.setInt(6, a[0].getMinLimit());
			ps.setInt(7, a[0].getMaxLimit());
			ps.setString(8, a[0].getTypeOfOperation());
			if (ps.executeUpdate() != 1) {
				System.out.println("inside not inserted signature");
				throw new SignatureNotInsertedException();
			}
			// }
		} catch (SQLException exception) {
			exception.printStackTrace();
			sessionContext.setRollbackOnly();
		} catch (Exception e) {
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}

		return true;
	}

	public int storeDepositMaster(DepositMasterObject depmast, int type)
			throws DateFormatException, ScrollNumberAttached,
			ControlNumberAttached, NamingException, CreateException {
		int int_accno = 0;
		ResultSet rs_store = null, rs_trn = null;
		Statement stmt_store = null, stmt_trn = null;
		Connection conn = null;
		String utml = "ca99";
		String uid = "Ship";

		/**
		 * type 0 --> creating deposit account type 1 --> deposit account
		 * ammendments type 2 --> renewal of old deposit account and creating
		 * new account
		 */
		try {
			conn = getConnection();
			commonLocal = commonLocalHome.create();
			stmt_store = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			stmt_trn = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);

			if (type == 0 || type == 2) {

				System.out.println("type=== 0 || 2");
				if (depmast.getReceivedBy().equalsIgnoreCase("C")) {
					rs_store = stmt_store
							.executeQuery("select attached from DayCash where attached='F' and trn_date='"
									+ depmast.getDepDate()
									+ "' and scroll_no="
									+ depmast.getReceivedAccno());
					rs_store.last();

					if (rs_store.getRow() <= 0)
						throw new ScrollNumberAttached();
				} else if (depmast.getReceivedBy().equalsIgnoreCase("G")) {

					rs_store = stmt_store
							.executeQuery("select post_ind from Cheque where post_ind='F' and ctrl_no="
									+ depmast.getReceivedAccno());
					rs_store.last();

					// commented by geetha for cbs....
					/*
					 * if(rs_store.getRow() > 0) throw new
					 * ControlNumberAttached();
					 */

					if (rs_store.getRow() <= 0)
						throw new ControlNumberAttached();
				}
				int_accno = depmast.getAccNo();
				if (int_accno == 0)
					int_accno = commonLocal.getModulesColumn("lst_acc_no",
							depmast.getAccType()) + 1;
				depmast.setAccNo(int_accno);// Code changed by sanjeet..
				System.out
						.println("**************geetha for cbs*****************");
				PreparedStatement ps_store = conn
						.prepareStatement("insert into DepositMaster values(?,?,?,?,0,?,?,?,?,?,?,?,?,?,'N',null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'F',null,null,'F','F',0,'F','F',?,?,?,null,?,?,'F',null,null,?,?,?,null,null,null)");
				ps_store.setString(1, depmast.getAccType());
				ps_store.setInt(2, int_accno);
				ps_store.setInt(3, depmast.getCustomerId());
				ps_store.setInt(4, depmast.getMailingAddress());
				ps_store.setInt(5, depmast.getNoofJtHldrs());
				ps_store.setString(6, depmast.getDepDate());
				ps_store.setString(7, depmast.getMaturityDate());
				ps_store.setInt(8, depmast.getDepositDays() / 365);
				ps_store.setInt(9, depmast.getDepositDays() / 30);
				ps_store.setInt(10, depmast.getDepositDays());
				ps_store.setDouble(11, depmast.getDepositAmt());
				ps_store.setDouble(12, depmast.getMaturityAmt());

				String nextpaydate = "";
				if (depmast.getAccType().substring(0, 4).equals("1003")) {
					nextpaydate = Validations.nextPayDate(depmast
							.getInterestFrq().substring(0, 1), depmast
							.getDepDate(), depmast.getMaturityDate(),
							commonLocal);
				} else
					nextpaydate = Validations.nextPayDate("Q", depmast
							.getDepDate(), depmast.getMaturityDate(),
							commonLocal);
				ps_store.setString(13, Validations.checkDate(nextpaydate));

				System.out.println("next pay date ====" + nextpaydate);
				ps_store.setString(13, nextpaydate);
				ps_store.setString(14, depmast.getIntroAccType());
				ps_store.setInt(15, depmast.getIntroAccno());
				NomineeObject nom[] = depmast.getNomineeDetails();
				if (nom != null) {
					System.out.println("Nom. Length = " + nom.length);
					int int_nom_no = commonLocal.storeNominee(nom, depmast
							.getAccType(), depmast.getAccNo(), depmast
							.getDepDate());
					System.out.println("Calling storeNominee");
					ps_store.setInt(16, int_nom_no);
				} else
					ps_store.setInt(16, 0);
				ps_store.setDouble(17, depmast.getInterestRate());
				ps_store.setDouble(18, depmast.getExtraRateType());
				ps_store.setString(19, depmast.getReceivedBy());
				ps_store.setString(20, depmast.getReceivedAccType());
				ps_store.setInt(21, depmast.getReceivedAccno());
				ps_store.setString(22, depmast.getInterestFrq());
				ps_store.setString(23, depmast.getInterestMode());
				ps_store.setString(24, depmast.getTransferAccType());
				ps_store.setInt(25, depmast.getTransferAccno());
				ps_store.setString(26, commonLocal.getFutureDayDate(depmast
						.getDepDate(), -1));
				ps_store.setString(27, "1");
				ps_store.setInt(28, 0);
				ps_store.setString(29, depmast.getAutoRenewal());
				ps_store.setDouble(30, depmast.getExcessAmt());
				ps_store.setInt(31, depmast.getCloseInd());
				ps_store.setString(32, depmast.getRenType());
				ps_store.setInt(33, depmast.getRenewalAccno());
				ps_store.setString(34, depmast.userverifier.getUserTml());
				ps_store.setString(35, depmast.userverifier.getUserId());
				// code changed by sanjeet on 29/05/2006..
				ps_store.setString(36, depmast.userverifier.getUserDate());
				// ps_store.setString(36,commonLocal.getSysDateTime());
				if (depmast.getSigObj() != null)
					storeSignatureDetails(depmast.getSigObj(), depmast
							.getAccNo());

				System.out.println("after getsign");
				if (depmast.getJointCid() != null) {
					System.out.println("inside joint cid");
					int addrtype[] = depmast.getJointAddrType();
					int jcids[] = depmast.getJointCid();
					for (int i = 0; i < jcids.length; i++) {
						stmt_store.addBatch("insert into JointHolders values('"
								+ depmast.getAccType() + "','"
								+ depmast.getAccNo() + "','" + jcids[i] + "','"
								+ addrtype[i] + "')");
					}
					stmt_store.executeBatch();
				}
				System.out.println("after joint cid");
				ps_store.executeUpdate();
				System.out.println("inserted in dep master");
				PreparedStatement ps_store_trn = conn
						.prepareStatement("insert into DepositTransaction values(?,?,?,?,'D',?,0,0,0,?,null,null,?,?,?,?,'C',0,?,?,?,null,null,null)");
				ps_store_trn.setString(1, depmast.getAccType());
				ps_store_trn.setInt(2, int_accno);
				ps_store_trn.setInt(3, 1);
				System.out.println("TranDate in DepositTransaction is---> "
						+ depmast.userverifier.getUserDate());
				ps_store_trn.setString(4, depmast.userverifier.getUserDate());
				ps_store_trn.setDouble(5, depmast.getDepositAmt());
				if (depmast.getAccType().substring(0, 4).equals("1004"))
					ps_store_trn.setDouble(6, depmast.getDepositAmt());
				else
					ps_store_trn.setDouble(6, 0);
				if (depmast.getReceivedBy().equals("N")) {
					ps_store_trn.setInt(7, 0);
					ps_store_trn.setString(8, depmast.getRenType() + "  "
							+ depmast.getRenewalAccno());
					ps_store_trn.setString(9, "T");
					ps_store_trn.setString(10, depmast.userverifier
							.getUserTml());
				} else if (depmast.getReceivedBy().equals("T")) {

					System.out.println("..................inside transfer 'T'.........");

					ps_store_trn.setInt(7, 0);
					ps_store_trn.setString(8, (depmast.getReceivedAccType()+ "  " + depmast.getReceivedAccno()));
					System.out.println("inside transferrrrrrrrrrr TTT");

					ps_store_trn.setString(9, depmast.getReceivedBy());
					ps_store_trn.setString(10, depmast.userverifier
							.getUserTml());
					System.out.println("end of tra");

				} else if (depmast.getReceivedBy().equals("C")) {
					System.out.println("ytfuiyeotyugioyuoiuuyi========="
							+ depmast.getReceivedBy() + " date "
							+ depmast.getString_scroll_date() + " scroll "
							+ depmast.getReceivedAccno());
					ps_store_trn.setInt(7, depmast.getReceivedAccno());
					ps_store_trn.setString(8, "By Cash Scr No "
							+ depmast.getReceivedAccno());
					ps_store_trn.setString(9, depmast.getReceivedBy());
					rs_store = stmt_store
							.executeQuery("select de_tml from DayCash where trn_date='"
									+ depmast.getString_scroll_date()
									+ "' and scroll_no="
									+ depmast.getReceivedAccno());
					if (rs_store.next()) {

						// added by geetha for cbs
						ps_store_trn.setString(10, utml);
						// ps_store_trn.setString(10,rs_store.getString(1));
						System.out.println("detml==>" + rs_store.getString(1));
					}

					/*
					 * *** should be updated at the time of Verification -
					 * Modified by Riswan ***
					 */
					/* *//**
					 * update daycash
					 */
					/*
					 */

					stmt_store
							.executeUpdate("update DayCash set ac_no="
									+ depmast.getAccNo()
									+ ",attached='T',ve_user=null, ve_date=null, ve_tml=null where scroll_no="
									+ depmast.getReceivedAccno()
									+ " and ac_type ='" + depmast.getAccType()
									+ "'");
					System.out.println("----after update-----");
				} else if (depmast.getReceivedBy().equals("G")) {
					ps_store_trn.setInt(7, depmast.getReceivedAccno());
					ps_store_trn.setString(8, "By Chq/DD/PO Ctrl No "
							+ depmast.getReceivedAccno());
					ps_store_trn.setString(9, depmast.getReceivedBy());

					rs_store = stmt_store
							.executeQuery("select de_tml from Cheque where ctrl_no="
									+ depmast.getReceivedAccno());
					if (rs_store.next())
						ps_store_trn.setString(10, rs_store.getString(1));
					System.out.println("updated" + depmast.getAccNo() + " "
							+ depmast.getReceivedAccno());
					stmt_store.executeUpdate("update Cheque set cr_ac_no="
							+ depmast.getAccNo() + " where ctrl_no="
							+ depmast.getReceivedAccno());
				}
				ps_store_trn.setString(11, depmast.userverifier.getUserTml());
				ps_store_trn.setString(12, depmast.userverifier.getUserId());
				// code changed by sanjeet ..
				// ps_store_trn.setString(13,commonLocal.getSysDateTime());
				ps_store_trn.setString(13, depmast.userverifier.getUserDate());
				ps_store_trn.executeUpdate();

				if (depmast.getNewReceipt() != null) {
					if (depmast.getNewReceipt().equalsIgnoreCase("True")) {
						PreparedStatement stmt_1 = conn.prepareStatement("Update DepositMaster set rct_prtd='F',new_rct='T',rct_no="+ depmast.getReceiptno()+ " where ac_no="+ depmast.getAccNo()+ " and ac_type='"+ depmast.getAccType() + "'   ");
						if (stmt_1.executeUpdate() <= 0)
							throw new SQLException();
					} else {
						PreparedStatement stmt_1 = conn
								.prepareStatement("Update DepositMaster set rct_prtd='T',new_rct='F',rct_no="
										+ depmast.getReceiptno()
										+ " where ac_no="
										+ depmast.getAccNo()
										+ " and ac_type='"
										+ depmast.getAccType() + "'   ");
						if (stmt_1.executeUpdate() <= 0)
							throw new SQLException();
					}
				}
				stmt_store.executeUpdate("update DepositMaster set close_date='"+ depmast.getDepDate()+"',ren_ac_type='" + depmast.getAccType() + "',ren_ac_no="+ int_accno+ ",close_ind=9 where ac_type='"+ depmast.getAccType() + "' and ac_no="+ depmast.getAccount_number());
			}
			// ///////////////
			if (type == 1) {

				PreparedStatement ps_update = conn
						.prepareStatement("update DepositMaster set int_freq=?,int_mode=?,trf_ac_type=?,trf_ac_no=?,dep_yrs=?,dep_mths=?,dep_days=?,mat_date=?,mat_amt=?,int_rate=?,ve_tml=?,ve_user=?,ve_date=?,auto_renewal=?,next_pay_date=?,nom_no=?,add_type=?,no_jt_hldr=?,intr_ac_type=?,intr_ac_no=?,extra_rate_type=? where ac_type='"
								+ depmast.getAccType()
								+ "' and ac_no="
								+ depmast.getAccNo());
				ps_update.setString(1, depmast.getInterestFrq());
				ps_update.setString(2, depmast.getInterestMode());
				ps_update.setString(3, depmast.getTransferAccType());
				ps_update.setInt(4, depmast.getTransferAccno());
				ps_update.setInt(5, depmast.getDepositDays() / 365);
				ps_update.setInt(6, depmast.getDepositDays() / 30);
				ps_update.setInt(7, depmast.getDepositDays());
				ps_update.setString(8, depmast.getMaturityDate());
				ps_update.setDouble(9, depmast.getMaturityAmt());
				ps_update.setDouble(10, depmast.getInterestRate());
				ps_update.setString(11, depmast.userverifier.getUserTml());
				ps_update.setString(12, depmast.userverifier.getUserId());
				ps_update.setString(13, depmast.userverifier.getUserDate());
				ps_update.setString(14, depmast.getAutoRenewal());
				/*
				 * String nextpaydate="";
				 * if(depmast.getAccType().substring(0,4).equals("1003"))
				 * nextpaydate
				 * =Validations.nextPayDate(depmast.getInterestFrq().substring
				 * (0,
				 * 1),depmast.getDepDate(),depmast.getMaturityDate(),commonLocal
				 * ); else
				 * nextpaydate=Validations.nextPayDate("Q",depmast.getDepDate
				 * (),depmast.getMaturityDate(),commonLocal);
				 */
				String nextpaydate = depmast.getNextPaydt();
				System.out.println("Next in else==>" + nextpaydate);
				if (Validations.dayCompare(nextpaydate, depmast
						.getMaturityDate()) == -1) {
					nextpaydate = depmast.getMaturityDate();
				}
				System.out.println("Check it out====> " + nextpaydate);
				ps_update.setString(15, Validations.checkDate(nextpaydate));
				NomineeObject nom[] = depmast.getNomineeDetails();
				if (nom != null) {
					System.out.println("Coming inside ...Nom.length = "
							+ nom.length);
					int int_nom_no = commonLocal.storeNominee(nom, depmast
							.getAccType(), depmast.getAccNo(), getSysDate());
					ps_update.setInt(16, int_nom_no);
				} else
					ps_update.setInt(16, 0);
				ps_update.setInt(17, depmast.getMailingAddress());
				ps_update.setInt(18, depmast.getNoofJtHldrs());
				ps_update.setString(19, depmast.getIntroAccType());
				ps_update.setInt(20, depmast.getIntroAccno());
				ps_update.setInt(21, depmast.getExtraRateType());
				int_accno = ps_update.executeUpdate();
				// stmt_store.executeUpdate("" +" into DepositMasterLog select *
				// from DepositMaster where ac_no="+depmast.getAccNo()+" and
				// ac_type='"+depmast.getAccType()+"'");
				stmt_store
						.executeUpdate("insert into DepositMasterLog select * from DepositMaster where ac_no="
								+ depmast.getAccNo()
								+ " and ac_type='"
								+ depmast.getAccType() + "'");
				stmt_store
						.executeUpdate("delete from JointHolders where ac_type='"
								+ depmast.getAccType()
								+ "' and ac_no="
								+ depmast.getAccNo());
				stmt_store
						.executeUpdate("delete from SignatureInstruction where ac_type='"
								+ depmast.getAccType()
								+ "' and ac_no="
								+ depmast.getAccNo());

				if (depmast.getSigObj() != null)
					commonLocal.storeSignatureDetails(depmast.getSigObj(),
							depmast.getAccNo());
				if (depmast.getJointCid() != null) {
					int addrtype[] = depmast.getJointAddrType();
					int jcids[] = depmast.getJointCid();
					for (int i = 0; i < jcids.length; i++) {
						stmt_store.addBatch("insert into JointHolders values('"
								+ depmast.getAccType() + "','"
								+ depmast.getAccNo() + "','" + jcids[i] + "','"
								+ addrtype[i] + "')");
					}
					stmt_store.executeBatch();
				}

				// Changes done from here
				double int_amt = 0, extra_amt = 0, int_paid = 0, dep_amt = 0, dep_paid = 0;
				int trn_seq = 0;
				rs_trn = stmt_trn
						.executeQuery("select sum(int_amt) as int_amt,sum(int_paid) as int_paid,max(trn_seq) as trn_seq,sum(dep_amt) as dep_amt,sum(dep_paid) as dep_paid from DepositTransaction where ac_type='"
								+ depmast.getAccType()
								+ "' and ac_no="
								+ depmast.getAccNo() + "");
				if (rs_trn.next()) {
					int_amt = rs_trn.getDouble("int_amt");
					int_paid = rs_trn.getDouble("int_paid");
					trn_seq = rs_trn.getInt("trn_seq");
					dep_amt = rs_trn.getDouble("dep_amt");
					dep_paid = rs_trn.getDouble("dep_paid");
				}
				System.out.println("amt==>" + depmast.getInterestAccured());
				extra_amt = depmast.getInterestAccured() - (int_amt);
				int dpcattype = depmast.getCategory();
				/**
				 * Interest on fd
				 */
				PreparedStatement ps_int = conn
						.prepareStatement("insert into DepositTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				ps_int.setString(1, depmast.getAccType());
				ps_int.setInt(2, depmast.getAccNo());
				ps_int.setInt(3, trn_seq + 1);
				ps_int.setString(4, getSysDate());
				ps_int.setString(5, "I");
				ps_int.setDouble(6, 0);
				ps_int.setDouble(7, extra_amt);
				ps_int.setDouble(8, 0);
				ps_int.setDouble(9, 0);
				ps_int.setDouble(10, ((dep_amt - dep_paid)
						+ (int_amt - int_paid) + extra_amt));
				ps_int.setString(11, null);
				// ps_int.setString(12,commonLocal.getFutureDayDate(close_date,-1));
				ps_int.setString(12, depmast.getInterestUpto());
				ps_int.setInt(13, 0);
				ps_int.setString(14, "By Interest");
				ps_int.setString(15, "T");
				ps_int.setString(16, depmast.userverifier.getUserTml());
				ps_int.setString(17, "C");
				ps_int.setDouble(18, ((int_amt - int_paid) + extra_amt));
				ps_int.setString(19, depmast.userverifier.getUserTml());
				ps_int.setString(20, depmast.userverifier.getUserId());
				ps_int.setString(21, depmast.userverifier.getUserDate());
				ps_int.setString(22, depmast.userverifier.getUserTml());
				ps_int.setString(23, depmast.userverifier.getUserId());
				ps_int.setString(24, depmast.userverifier.getUserDate());
				ps_int.executeUpdate();

				/**
				 * Interest amount GL iff interest amount less than or equal to
				 * 0 i.e debit from FD account Gl credit to profit gl
				 */
				if (extra_amt <= 0) {
					GLTransObject trnobj = null;
					if (dpcattype == 0)
						dpcattype = 3;
					else
						dpcattype = 4;
					/**
					 * debit fd account gl
					 */
					rs_store = stmt_store
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type = "
									+ depmast.getAccType()
									+ " and trn_type='I' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					if (rs_store.next()) {
						trnobj = new GLTransObject();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_store.getString("gk.gl_type"));
						trnobj.setGLCode(rs_store.getString("gk.gl_code"));
						trnobj.setTrnMode("T");
						trnobj
								.setAmount(extra_amt
										* rs_store.getInt("mult_by"));
						trnobj.setCdind("D");
						trnobj.setAccType(depmast.getAccType());
						trnobj.setAccNo(String.valueOf(depmast.getAccNo()));
						trnobj.setTrnType("P");
						trnobj.setRefNo(0);
						trnobj.setTrnSeq(trn_seq + 1);
						trnobj.setVtml(depmast.userverifier.getUserTml());
						trnobj.setVid(depmast.userverifier.getUserId());
						trnobj.setVDate(depmast.userverifier.getUserDate());
						commonLocal.storeGLTransaction(trnobj);
					}

					dpcattype = dpcattype + 4;
					/**
					 * Profit Gl
					 */
					rs_store = stmt_store
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type = "
									+ depmast.getAccType()
									+ " and trn_type='P' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					if (rs_store.next()) {
						trnobj = new GLTransObject();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_store.getString("gk.gl_type"));
						trnobj.setGLCode(rs_store.getString("gk.gl_code"));
						trnobj.setTrnMode("T");
						trnobj
								.setAmount(extra_amt
										* rs_store.getInt("mult_by"));
						trnobj.setCdind("C");
						trnobj.setAccType(depmast.getAccType());
						trnobj.setAccNo(String.valueOf(depmast.getAccNo()));
						trnobj.setTrnType("P");
						trnobj.setRefNo(0);
						trnobj.setTrnSeq(trn_seq + 1);
						trnobj.setVtml(depmast.userverifier.getUserTml());
						trnobj.setVid(depmast.userverifier.getUserId());
						trnobj.setVDate(depmast.userverifier.getUserDate());
						commonLocal.storeGLTransaction(trnobj);
					}
				}
				/**
				 * if interest > 0 i.e credit to FD payable Gl debit loss
				 * account
				 */
				else {
					GLTransObject trnobj = null;
					if (dpcattype == 0)
						dpcattype = 3;
					else
						dpcattype = 4;
					/**
					 * credit to FD payable GL
					 */
					rs_store = stmt_store
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type = "
									+ depmast.getAccType()
									+ " and trn_type='I' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					if (rs_store.next()) {
						trnobj = new GLTransObject();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_store.getString("gk.gl_type"));
						trnobj.setGLCode(rs_store.getString("gk.gl_code"));
						trnobj.setTrnMode("T");
						trnobj
								.setAmount(extra_amt
										* rs_store.getInt("mult_by"));
						trnobj.setCdind("C");
						trnobj.setAccType(depmast.getAccType());
						trnobj.setAccNo(String.valueOf(depmast.getAccNo()));
						trnobj.setTrnType("I");
						trnobj.setRefNo(0);
						trnobj.setTrnSeq(trn_seq + 1);
						trnobj.setVtml(depmast.userverifier.getUserTml());
						trnobj.setVid(depmast.userverifier.getUserId());
						trnobj.setVDate(depmast.userverifier.getUserDate());
						commonLocal.storeGLTransaction(trnobj);
					}

					dpcattype = dpcattype + 2;
					/**
					 * Debit to loss Gl
					 */
					rs_store = stmt_store
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type = "
									+ depmast.getAccType()
									+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					if (rs_store.next()) {
						trnobj = new GLTransObject();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_store.getString("gk.gl_type"));
						trnobj.setGLCode(rs_store.getString("gk.gl_code"));
						trnobj.setTrnMode("T");
						trnobj
								.setAmount(extra_amt
										* rs_store.getInt("mult_by"));
						trnobj.setCdind("D");
						trnobj.setAccType(depmast.getAccType());
						trnobj.setAccNo(String.valueOf(depmast.getAccNo()));
						trnobj.setTrnType("P");
						trnobj.setRefNo(0);
						trnobj.setTrnSeq(trn_seq + 1);
						trnobj.setVtml(depmast.userverifier.getUserTml());
						trnobj.setVid(depmast.userverifier.getUserId());
						trnobj.setVDate(depmast.userverifier.getUserDate());
						commonLocal.storeGLTransaction(trnobj);
					}
				}
			}
			/**
			 * Type 2 for renewal i.e write deposit trn for old account
			 */
			if (type == 2) {
				// ResultSet rs_trn = null;
				System.out.println("sssssss==>" + depmast.getRenType() + "--"
						+ depmast.getRenewalAccno());
				// Statement stmt_trn =
				// conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
				rs_trn = stmt_trn
						.executeQuery("select max(trn_seq) as lst_trn_seq,dm.dep_amt,dm.mat_date,int_freq,sum(dt.int_amt) as int_amt,sum(dt.int_paid) as int_paid from DepositTransaction dt,DepositMaster dm where dm.ac_type='"
								+ depmast.getRenType()
								+ "' and dm.ac_no="
								+ depmast.getRenewalAccno()
								+ " and dm.ac_type=dt.ac_type and dm.ac_no=dt.ac_no group by dt.ac_no");
				rs_trn.next();
				int trnseq = rs_trn.getInt("lst_trn_seq");
				double dep_amt = rs_trn.getInt("dep_amt");
				double double_cuminterest = 0;
				if (depmast.getRenType().substring(0, 4).equals("1005"))
					double_cuminterest = rs_trn.getDouble("int_amt")
							+ depmast.getDepositPaid();
				/**
				 * For Interest
				 */
				PreparedStatement ps_int = conn
						.prepareStatement("insert into DepositTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				ps_int.setString(1, depmast.getRenType());
				ps_int.setInt(2, depmast.getRenewalAccno());
				ps_int.setInt(3, ++trnseq);
				// code added by sanjeet..
				ps_int.setString(4, getSysDate());
				// ps_int.setString(4,commonLocal.getSysDate());
				ps_int.setString(5, "E");
				ps_int.setDouble(6, dep_amt);
				ps_int.setDouble(7, depmast.getDepositPaid());
				ps_int.setDouble(8, 0);
				ps_int.setDouble(9, 0);
				ps_int.setDouble(10, 0);
				ps_int.setString(11, null);
				ps_int.setString(12, commonLocal.getFutureDayDate(rs_trn
						.getString("mat_date"), -1));
				ps_int.setInt(13, 0);
				ps_int.setString(14, "By Interest");
				ps_int.setString(15, "T");
				ps_int.setString(16, depmast.userverifier.getUserTml());
				ps_int.setString(17, "C");
				ps_int.setDouble(18, double_cuminterest);
				ps_int.setString(19, depmast.userverifier.getUserTml());
				ps_int.setString(20, depmast.userverifier.getUserId());
				ps_int.setString(21, depmast.userverifier.getUserDate());
				ps_int.setString(22, null);
				ps_int.setString(23, null);
				ps_int.setString(24, null);
				ps_int.executeUpdate();

				String trf_acc_type = "";
				if (depmast.getTranDate() != null
						&& depmast.getTranDate().equals("T") == true) {
					trf_acc_type = depmast.getLastTrndt() + " "
							+ depmast.getLastTrnSeq();
				}
				System.out
						.println("cum_interest==>" + depmast.getCumInterest());
				if (depmast.getCumInterest() > 0) {
					/**
					 * Interest payment
					 */
					ps_int.setString(1, depmast.getRenType());
					ps_int.setInt(2, depmast.getRenewalAccno());
					ps_int.setInt(3, ++trnseq);
					// code added by sanjeet..
					ps_int.setString(4, getSysDate());
					// ps_int.setString(4,commonLocal.getSysDate());
					ps_int.setString(5, "A");
					ps_int.setDouble(6, dep_amt);
					ps_int.setDouble(7, 0);
					ps_int.setDouble(8, 0);
					ps_int.setDouble(9, depmast.getCumInterest());
					ps_int.setDouble(10, 0);
					// code added by sanjeet..
					ps_int.setString(11, getSysDate());
					// ps_int.setString(11,commonLocal.getSysDate());
					ps_int.setString(12, null);
					ps_int.setInt(13, 0);
					ps_int.setString(14, trf_acc_type);
					ps_int.setString(15, depmast.getTranDate());
					ps_int.setString(16, depmast.userverifier.getUserTml());
					ps_int.setString(17, "D");
					ps_int.setDouble(18, 0);
					ps_int.setString(19, depmast.userverifier.getUserTml());
					ps_int.setString(20, depmast.userverifier.getUserId());
					ps_int.setString(21, depmast.userverifier.getUserDate());
					ps_int.setString(22, null);
					ps_int.setString(23, null);
					ps_int.setString(24, null);
					ps_int.executeUpdate();
					/**
					 * deposit amount
					 */
					ps_int.setString(1, depmast.getRenType());
					ps_int.setInt(2, depmast.getRenewalAccno());
					ps_int.setInt(3, ++trnseq);
					// code added by sanjeet..
					ps_int.setString(4, getSysDate());
					// ps_int.setString(4,commonLocal.getSysDate());
					ps_int.setString(5, "E");
					ps_int.setDouble(6, 0);
					ps_int.setDouble(7, 0);
					ps_int.setDouble(8, dep_amt);
					ps_int.setDouble(9, 0);
					ps_int.setDouble(10, 0);
					// code added by sanjeet..
					ps_int.setString(11, getSysDate());
					// ps_int.setString(11,commonLocal.getSysDate());
					ps_int.setString(12, null);
					ps_int.setInt(13, 0);
					ps_int.setString(14, depmast.getAccType() + " "
							+ depmast.getAccNo());
					ps_int.setString(15, "T");
					ps_int.setString(16, depmast.userverifier.getUserTml());
					ps_int.setString(17, "D");
					ps_int.setDouble(18, 0);
					ps_int.setString(19, depmast.userverifier.getUserTml());
					ps_int.setString(20, depmast.userverifier.getUserId());
					ps_int.setString(21, depmast.userverifier.getUserDate());
					ps_int.setString(22, null);
					ps_int.setString(23, null);
					ps_int.setString(24, null);
					ps_int.executeUpdate();
				} else {
					/**
					 * deposit amount and interest payment
					 */
					double double_payable = depmast.getDepositPaid();
					ps_int.setString(1, depmast.getRenType());
					ps_int.setInt(2, depmast.getRenewalAccno());
					ps_int.setInt(3, ++trnseq);
					// code added by sanjeet..
					ps_int.setString(4, getSysDate());
					// ps_int.setString(4,commonLocal.getSysDate());
					ps_int.setString(5, "E");
					ps_int.setDouble(6, 0);
					ps_int.setDouble(7, 0);
					ps_int.setDouble(8, dep_amt);
					ps_int.setDouble(9, double_payable);
					ps_int.setDouble(10, 0);
					// code added by sanjeet..
					ps_int.setString(11, getSysDate());
					// ps_int.setString(11,commonLocal.getSysDate());
					ps_int.setString(12, null);
					ps_int.setInt(13, 0);
					ps_int.setString(14, depmast.getAccType() + " "
							+ depmast.getAccNo());
					ps_int.setString(15, "T");
					ps_int.setString(16, depmast.userverifier.getUserTml());
					ps_int.setString(17, "D");
					ps_int.setDouble(18, 0);
					ps_int.setString(19, depmast.userverifier.getUserTml());
					ps_int.setString(20, depmast.userverifier.getUserId());
					ps_int.setString(21, depmast.userverifier.getUserDate());
					ps_int.setString(22, null);
					ps_int.setString(23, null);
					ps_int.setString(24, null);
					ps_int.executeUpdate();
				}
				stmt_store
						.executeUpdate("update DepositMaster set ren_ac_type='"
								+ depmast.getAccType() + "',ren_ac_no="
								+ depmast.getAccNo()
								+ ",close_ind=9 where ac_type='"
								+ depmast.getRenType() + "' and ac_no="
								+ depmast.getRenewalAccno());
			}
		} catch (SQLException exception_store) {
			exception_store.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordNotInsertedException();
		} catch (Exception e) {
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			commonLocal = null;
		}
		System.out
				.println("I am in TDBean New AcountNUmber=====> " + int_accno);
		return int_accno;
	}

	public DepositMasterObject mailSystem(String ac_type, String nxtpaydate)
			throws RemoteException, RecordsNotFoundException {
		Connection conn = null;
		DepositMasterObject mailaddress = null;
		Statement stmt = null, stmt_narr = null;
		ResultSet rs = null, rs_narr = null;
		try {
			System.out.println("Ia im in MailBean ac_type------> " + ac_type);
			System.out.println("Ia im in MailBean nxtpaydate------> "
					+ nxtpaydate);

			conn = getConnection();
			commonLocal = commonLocalHome.create();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt_narr = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			int i = 0;
			rs = stmt
					.executeQuery("select cm.fname,ca.email,dm.next_pay_date from CustomerAddr ca,DepositMaster dm,CustomerMaster cm where dm.cid=ca.cid and dm.ac_type='"
							+ ac_type
							+ "' and dm.next_pay_date='"
							+ nxtpaydate
							+ "' group by dm.next_pay_date");
			// rs=stmt.executeQuery("select cm.fname,ca.email,dm.next_pay_date
			// from CustomerAddr ca,DepositMaster dm,CustomerMaster cm where
			// dm.cid=ca.cid and dm.ac_type='1004001' and
			// dm.next_pay_date='18/07/2009' group by dm.next_pay_date");
			while (rs.next()) {
				mailaddress = new DepositMasterObject();
				System.out.println("email---> " + rs.getString("email"));
				mailaddress.setCloseInd(i);
				mailaddress.setEmail(rs.getString("email"));
				mailaddress.setNextPaydt(rs.getString("next_pay_date"));
				mailaddress.setName(rs.getString("fname"));
				i++;
			}
			rs.last();
			if (rs.getRow() == 0) {
				return null;
			}

		} catch (SQLException ex1) {
			ex1.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordNotInsertedException();
		} catch (Exception e) {
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return mailaddress;
	}

	public int deleteTermDeposit(DepositMasterObject depositMasterObject,
			int type) throws RecordNotInsertedException {
		Connection conn = null;
		try {
			conn = getConnection();
			commonLocal = commonLocalHome.create();
			String acctype = depositMasterObject.getAccType();
			int accno = depositMasterObject.getAccNo();
			System.out.println("ac_type and ac_no==>" + acctype + "--" + accno);
			Statement stmt_del = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			if (type == 2) {

				/*
				 * ResultSet rs_main = stmt_del.executeQuery("select
				 * ren_ac_type,ren_ac_no from DepositMaster where
				 * ac_no="+accno+" and ac_type='"+acctype+"'");
				 * if(rs_main.next()){ String
				 * ren_acctype=rs_main.getString("ren_ac_type"); int
				 * ren_ac_no=rs_main.getInt("ren_ac_no");
				 * System.out.println("ren_no==>"+ren_ac_no);
				 * System.out.println("ren_type==>"+ren_acctype);
				 * stmt_del.executeUpdate("delete from DepositTransaction where
				 * ac_no="+ren_ac_no+" and ac_type='"+ren_acctype+"'");
				 * stmt_del.executeUpdate("delete from JointHolders where
				 * ac_type='"+ren_acctype+"' and ac_no="+ren_ac_no);
				 * stmt_del.executeUpdate("delete from NomineeMaster where
				 * ac_type='"+ren_acctype+"' and ac_no="+ren_ac_no);
				 * stmt_del.executeUpdate("delete from SignatureInstruction
				 * where ac_type='"+ren_acctype+"' and ac_no="+ren_ac_no);
				 * stmt_del.executeUpdate("delete from DepositMaster where
				 * ac_no="+ren_ac_no+" and ac_type='"+ren_acctype+"'");
				 * 
				 * stmt_del.executeUpdate("update DayCash set
				 * ac_no=0,attached='F' where trn_date='"+getSysDate()+"' and
				 * ac_type='"+ren_acctype+"' and ac_no="+ren_ac_no);
				 * stmt_del.executeUpdate("update Cheque set
				 * cr_ac_no=0,post_ind='F' where cr_ac_type='"+ren_acctype+"'
				 * and cr_ac_no="+ren_ac_no); } stmt_del.executeUpdate("delete
				 * from DepositTransaction where ac_no="+accno+" and
				 * ac_type='"+acctype+"' and trn_type in ('E','L','A')");
				 * stmt_del.executeUpdate("update DepositMaster set
				 * close_ind=0,ren_ac_type=null,ren_ac_no=0 where
				 * ac_no="+accno+" and ac_type='"+acctype+"'");
				 */

				ResultSet rs_main = stmt_del
						.executeQuery("select ren_ac_type,ren_ac_no from DepositMaster where ac_no="
								+ accno + " and ac_type='" + acctype + "'");
				if (rs_main.next()) {
					String ren_acctype = rs_main.getString("ren_ac_type");
					int ren_ac_no = rs_main.getInt("ren_ac_no");
					System.out.println("ren_no==>" + ren_ac_no);
					System.out.println("ren_type==>" + ren_acctype);
					stmt_del
							.executeUpdate("delete from DepositTransaction where ac_no="
									+ ren_ac_no
									+ " and ac_type='"
									+ ren_acctype
									+ "' and trn_type in ('E','L','A')");
					stmt_del
							.executeUpdate("update DepositMaster set close_ind=0,ren_ac_type=null,ren_ac_no=0 where ac_no="
									+ ren_ac_no
									+ " and ac_type='"
									+ ren_acctype + "'");
					// stmt_del.executeUpdate("update Modules set
					// lst_acc_no=lst_acc_no-1 where modulecode='"+acctype+"'");
				}
				stmt_del
						.executeUpdate("delete from DepositTransaction where ac_no="
								+ accno + " and ac_type='" + acctype + "'");
				stmt_del
						.executeUpdate("delete from JointHolders where ac_type='"
								+ acctype + "' and ac_no=" + accno);
				stmt_del
						.executeUpdate("delete from NomineeMaster where ac_type='"
								+ acctype + "' and ac_no=" + accno);
				stmt_del
						.executeUpdate("delete from SignatureInstruction where ac_type='"
								+ acctype + "' and ac_no=" + accno);
				stmt_del.executeUpdate("delete from DepositMaster where ac_no="
						+ accno + " and ac_type='" + acctype + "'");

				stmt_del
						.executeUpdate("update DayCash set ac_no=0,attached='F' where trn_date='"
								+ getSysDate()
								+ "' and ac_type='"
								+ acctype
								+ "' and ac_no=" + accno);
				stmt_del
						.executeUpdate("update Cheque set cr_ac_no=0,post_ind='F' where cr_ac_type='"
								+ acctype + "' and cr_ac_no=" + accno);

			} else {
				stmt_del
						.executeUpdate("delete from DepositTransaction where ac_no="
								+ accno + " and ac_type='" + acctype + "'");
				stmt_del
						.executeUpdate("delete from JointHolders where ac_type='"
								+ acctype + "' and ac_no=" + accno);
				stmt_del
						.executeUpdate("delete from NomineeMaster where ac_type='"
								+ acctype + "' and ac_no=" + accno);
				stmt_del
						.executeUpdate("delete from SignatureInstruction where ac_type='"
								+ acctype + "' and ac_no=" + accno);
				stmt_del.executeUpdate("delete from DepositMaster where ac_no="
						+ accno + " and ac_type='" + acctype + "'");

				stmt_del
						.executeUpdate("update DayCash set ac_no=0,attached='F' where trn_date='"
								+ getSysDate()
								+ "' and ac_type='"
								+ depositMasterObject.getAccType()
								+ "' and ac_no="
								+ depositMasterObject.getAccNo());
				stmt_del
						.executeUpdate("update Cheque set cr_ac_no=0,post_ind='F' where cr_ac_type='"
								+ depositMasterObject.getAccType()
								+ "' and cr_ac_no="
								+ depositMasterObject.getAccNo());
			}
		} catch (SQLException ex1) {
			ex1.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordNotInsertedException();
		} catch (CreateException e) {
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		} catch (Exception e) {
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 1;
	}

	public int deleteTermDeposit(DepositMasterObject depositMasterObject,
			String date, int type) throws RecordNotInsertedException {
		Connection conn = null;
		try {
			conn = getConnection();
			commonLocal = commonLocalHome.create();
			String acctype = depositMasterObject.getAccType();
			int accno = depositMasterObject.getAccNo();
			// file_logger.info("ac_type and ac_no==>"+acctype+"--"+accno);
			System.out.println("int type==========>" + type);
			Statement stmt_del = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			if (type == 2) {

				/*
				 * ResultSet rs_main = stmt_del.executeQuery("select
				 * ren_ac_type,ren_ac_no from DepositMaster where
				 * ac_no="+accno+" and ac_type='"+acctype+"'");
				 * if(rs_main.next()){ String
				 * ren_acctype=rs_main.getString("ren_ac_type"); int
				 * ren_ac_no=rs_main.getInt("ren_ac_no");
				 * file_logger.info("ren_no==>"+ren_ac_no);
				 * file_logger.info("ren_type==>"+ren_acctype);
				 * stmt_del.executeUpdate("delete from DepositTransaction where
				 * ac_no="+ren_ac_no+" and ac_type='"+ren_acctype+"'");
				 * stmt_del.executeUpdate("delete from JointHolders where
				 * ac_type='"+ren_acctype+"' and ac_no="+ren_ac_no);
				 * stmt_del.executeUpdate("delete from NomineeMaster where
				 * ac_type='"+ren_acctype+"' and ac_no="+ren_ac_no);
				 * stmt_del.executeUpdate("delete from SignatureInstruction
				 * where ac_type='"+ren_acctype+"' and ac_no="+ren_ac_no);
				 * stmt_del.executeUpdate("delete from DepositMaster where
				 * ac_no="+ren_ac_no+" and ac_type='"+ren_acctype+"'");
				 * 
				 * stmt_del.executeUpdate("update DayCash set
				 * ac_no=0,attached='F' where trn_date='"+getSysDate()+"' and
				 * ac_type='"+ren_acctype+"' and ac_no="+ren_ac_no);
				 * stmt_del.executeUpdate("update Cheque set
				 * cr_ac_no=0,post_ind='F' where cr_ac_type='"+ren_acctype+"'
				 * and cr_ac_no="+ren_ac_no); } stmt_del.executeUpdate("delete
				 * from DepositTransaction where ac_no="+accno+" and
				 * ac_type='"+acctype+"' and trn_type in ('E','L','A')");
				 * stmt_del.executeUpdate("update DepositMaster set
				 * close_ind=0,ren_ac_type=null,ren_ac_no=0 where
				 * ac_no="+accno+" and ac_type='"+acctype+"'");
				 */

				ResultSet rs_main = stmt_del
						.executeQuery("select ren_ac_type,ren_ac_no from DepositMaster where ac_no="
								+ accno + " and ac_type='" + acctype + "'");
				if (rs_main.next()) {
					String ren_acctype = rs_main.getString("ren_ac_type");
					int ren_ac_no = rs_main.getInt("ren_ac_no");
					// file_logger.info("ren_no==>"+ren_ac_no);
					// file_logger.info("ren_type==>"+ren_acctype);
					stmt_del
							.executeUpdate("delete from DepositTransaction where ac_no="
									+ ren_ac_no
									+ " and ac_type='"
									+ ren_acctype
									+ "' and trn_type in ('E','L','A')");
					stmt_del
							.executeUpdate("update DepositMaster set close_ind=0,ren_ac_type=null,ren_ac_no=0 where ac_no="
									+ ren_ac_no
									+ " and ac_type='"
									+ ren_acctype + "'");
					// stmt_del.executeUpdate("update Modules set
					// lst_acc_no=lst_acc_no-1 where modulecode='"+acctype+"'");
				}
				stmt_del
						.executeUpdate("delete from DepositTransaction where ac_no="
								+ accno + " and ac_type='" + acctype + "'");
				stmt_del
						.executeUpdate("delete from JointHolders where ac_type='"
								+ acctype + "' and ac_no=" + accno);
				stmt_del
						.executeUpdate("delete from NomineeMaster where ac_type='"
								+ acctype + "' and ac_no=" + accno);
				stmt_del
						.executeUpdate("delete from SignatureInstruction where ac_type='"
								+ acctype + "' and ac_no=" + accno);
				stmt_del.executeUpdate("delete from DepositMaster where ac_no="
						+ accno + " and ac_type='" + acctype + "'");

				stmt_del
						.executeUpdate("update DayCash set ac_no=0,attached='F' where trn_date='"
								+ date
								+ "' and ac_type='"
								+ acctype
								+ "' and ac_no=" + accno);
				stmt_del
						.executeUpdate("update Cheque set cr_ac_no=0 where cr_ac_type='"
								+ acctype + "' and cr_ac_no=" + accno);

			} else {
				stmt_del
						.executeUpdate("delete from DepositTransaction where ac_no="
								+ accno + " and ac_type='" + acctype + "'");
				stmt_del
						.executeUpdate("delete from JointHolders where ac_type='"
								+ acctype + "' and ac_no=" + accno);
				stmt_del
						.executeUpdate("delete from NomineeMaster where ac_type='"
								+ acctype + "' and ac_no=" + accno);
				stmt_del
						.executeUpdate("delete from SignatureInstruction where ac_type='"
								+ acctype + "' and ac_no=" + accno);
				stmt_del.executeUpdate("delete from DepositMaster where ac_no="
						+ accno + " and ac_type='" + acctype + "'");

				stmt_del
						.executeUpdate("update DayCash set ac_no=0,attached='F' where trn_date='"
								+ date
								+ "' and ac_type='"
								+ depositMasterObject.getAccType()
								+ "' and ac_no="
								+ depositMasterObject.getAccNo());
				stmt_del
						.executeUpdate("update Cheque set cr_ac_no=0 where cr_ac_type='"
								+ depositMasterObject.getAccType()
								+ "' and cr_ac_no="
								+ depositMasterObject.getAccNo());
			}
		} catch (SQLException ex1) {
			ex1.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordNotInsertedException();
		} catch (CreateException e) {
			e.printStackTrace();
			sessionContext.setRollbackOnly();
			// file_logger.info(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			sessionContext.setRollbackOnly();
			// file_logger.info(e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				// file_logger.info(e.getMessage());
			}
		}
		return 1;
	}

	public DepositMasterObject getDepositMaster(String acctype, int fr_acno)
			throws AccountNotFoundException {
		DepositMasterObject depositmasterobject = null;
		ResultSet rs_dep = null, rs_extra = null;
		Connection conn = null;
		try {
			conn = getConnection();
			commonLocal = commonLocalHome.create();
			Statement stmt_dep = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			Statement stmt_extra = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs_dep = stmt_dep
					.executeQuery("select dm.*,concat_ws(' ',fname,mname,lname) as name from DepositMaster dm,CustomerMaster cm where cm.cid=dm.cid and dm.ac_type='"
							+ acctype + "' and dm.ac_no=" + fr_acno + "");
			rs_dep.last();
			if (rs_dep.getRow() == 0 || rs_dep == null) {

				return null;

			}
			depositmasterobject = new DepositMasterObject();
			rs_dep.beforeFirst();
			while (rs_dep.next()) {
				depositmasterobject = new DepositMasterObject();
				if (rs_dep.getString("ve_user") == null)
					depositmasterobject.setVerified("F");
				else
					depositmasterobject.setVerified("T");
				depositmasterobject.setAccType(acctype);
				depositmasterobject.setAccNo(rs_dep.getInt("ac_no"));
				depositmasterobject.setCustomerId(rs_dep.getInt("cid"));
				depositmasterobject
						.setMailingAddress(rs_dep.getInt("add_type"));
				depositmasterobject.setNoofJtHldrs(rs_dep.getInt("no_jt_hldr"));
				depositmasterobject.setMaturityDate(rs_dep
						.getString("mat_date"));
				depositmasterobject.setDepDate(rs_dep.getString("dep_date"));
				depositmasterobject.setDepositDays(rs_dep.getInt("dep_days"));
				depositmasterobject.setDepositAmt(rs_dep.getDouble("dep_amt"));
				depositmasterobject.setMaturityAmt(rs_dep.getDouble("mat_amt"));
				depositmasterobject.setIntroAccno(rs_dep.getInt("intr_ac_no"));
				depositmasterobject.setIntroAccType(rs_dep
						.getString("intr_ac_type"));
				depositmasterobject.setNomineeRegNo(rs_dep.getInt("nom_no"));
				depositmasterobject.setInterestRate(rs_dep
						.getDouble("int_rate"));
				depositmasterobject.setReceivedBy(rs_dep.getString("rcvd_by"));
				depositmasterobject.setExcessAmt(rs_dep.getDouble("exc_amt"));
				depositmasterobject.setRenType(rs_dep.getString("ren_ac_type"));
				depositmasterobject.setRenewalAccno(rs_dep.getInt("ren_ac_no"));
				if (depositmasterobject.getReceivedBy().equals("T")) {
					depositmasterobject.setReceivedAccno(rs_dep
							.getInt("rcvd_ac_no"));
					depositmasterobject.setReceivedAccType(rs_dep
							.getString("rcvd_ac_type"));
				} else {
					depositmasterobject.setReceivedAccno(rs_dep
							.getInt("rcvd_ac_no"));
					depositmasterobject.setReceivedAccType("");
				}

				depositmasterobject
						.setInterestFrq(rs_dep.getString("int_freq"));
				depositmasterobject.setInterestMode(rs_dep
						.getString("int_mode"));

				// changed by sumanth depositmasterobject.getIntmode() to
				// depositmasterobject.getReceivedBy()

				if (depositmasterobject.getReceivedBy().equals("T")) {
					depositmasterobject.setTransferAccType(rs_dep
							.getString("trf_ac_type"));
					depositmasterobject.setTransferAccno(rs_dep
							.getInt("trf_ac_no"));
				}
				depositmasterobject.setInterestUpto(rs_dep
						.getString("int_upto_date"));
				depositmasterobject.setNextPaydt(rs_dep
						.getString("next_pay_date"));
				depositmasterobject.setLoanAvailed(rs_dep
						.getString("ln_availed"));
				depositmasterobject.setReceiptno(rs_dep.getInt("rct_no"));
				depositmasterobject.setAutoRenewal(rs_dep
						.getString("auto_renewal"));
				depositmasterobject.setCloseInd(rs_dep.getInt("close_ind"));
				depositmasterobject.userverifier.setUserTml(rs_dep
						.getString("de_tml"));
				depositmasterobject.userverifier.setUserId(rs_dep
						.getString("de_user"));
				depositmasterobject.userverifier.setUserDate(rs_dep
						.getString("de_date"));
				depositmasterobject.setName(rs_dep.getString("name"));
				depositmasterobject.setExtraRateType(rs_dep
						.getInt("extra_rate_type"));

				rs_extra = stmt_extra
						.executeQuery("select cid,addr_type from JointHolders where ac_type='"
								+ depositmasterobject.getAccType()
								+ "' and ac_no="
								+ depositmasterobject.getAccNo());
				int j = 0;
				int addrtype[] = null;
				int cids[] = null;
				if (rs_extra.next()) {
					rs_extra.last();
					addrtype = new int[rs_extra.getRow()];
					cids = new int[rs_extra.getRow()];
					rs_extra.beforeFirst();
				}
				while (rs_extra.next()) {
					addrtype[j] = rs_extra.getInt(2);
					cids[j++] = rs_extra.getInt(1);
				}
				depositmasterobject.setJointAddrType(addrtype);
				depositmasterobject.setJointCid(cids);
				depositmasterobject.setNomineeDetails(commonLocal
						.getNominee(depositmasterobject.getNomineeRegNo()));
				depositmasterobject.setSigObj(commonLocal.getSignatureDetails(
						depositmasterobject.getAccNo(), depositmasterobject
								.getAccType()));

			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new AccountNotFoundException();
		} catch (CreateException e) {
			e.printStackTrace();
			throw new AccountNotFoundException();
		}

		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			commonLocal = null;
		}
		return depositmasterobject;
	}

	public DepositMasterObject[] getDepositMaster(String acctype, int type,
			int fr_acno, int to_acno, String fr_date, String to_date)
			throws AccountNotFoundException {
		DepositMasterObject array_depositmasterobject[] = null;
		ResultSet rs_dep = null, rs_extra = null;
		Connection conn = null;
		try {
			conn = getConnection();
			commonLocal = commonLocalHome.create();
			Statement stmt_dep = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			Statement stmt_extra = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			if (type == 0)
				rs_dep = stmt_dep
						.executeQuery("select distinct dm.ac_no,dm.*,cm.fname,cm.mname,cm.lname from DepositMaster dm,CustomerMaster cm,DepositTransaction dt where cm.cid=dm.cid and dm.ac_type='"
								+ acctype
								+ "' and dm.ac_no between "
								+ fr_acno
								+ " and "
								+ to_acno
								+ " and dm.close_ind=0 and dm.ac_no=dt.ac_no and dm.ac_type=dt.ac_type order by dm.ac_no");
			else if (type == 1)
				rs_dep = stmt_dep
						.executeQuery("select distinct dm.ac_no,dm.*,cm.fname,cm.mname,cm.lname from DepositMaster dm,CustomerMaster cm,DepositTransaction dt where cm.cid=dm.cid and dm.ac_type='"
								+ acctype
								+ "' and dm.ac_no=dt.ac_no and concat(right(dm.close_date,4),'-',mid(dm.close_date,locate('/',dm.close_date)+1,(locate('/',dm.close_date,4)-locate('/',dm.close_date)-1)),'-',left(dm.close_date,locate('/',dm.close_date)-1)) between '"
								+ fr_date
								+ "' and '"
								+ to_date
								+ "' and dm.close_ind=0 order by dm.ac_no");
			else if (type == 2)
				rs_dep = stmt_dep
						.executeQuery("select distinct dm.ac_no,dm.*,cm.fname,cm.mname,cm.lname from DepositMaster dm,CustomerMaster cm,DepositTransaction dt where cm.cid=dm.cid and dm.ac_type='"
								+ acctype
								+ "' and dm.ac_no=dt.ac_no and dm.ac_no between "
								+ fr_acno
								+ " and "
								+ to_acno
								+ " and concat(right(dt.trn_date,4),'-',mid(dt.trn_date,locate('/',dt.trn_date)+1,(locate('/',dt.trn_date,4)-locate('/',dt.trn_date)-1)),'-',left(dt.trn_date,locate('/',dt.trn_date)-1)) between '"
								+ fr_date
								+ "' and '"
								+ to_date
								+ "' order by dm.ac_no");
			else if (type == 3)
				rs_dep = stmt_dep
						.executeQuery("select distinct dm.ac_no,dm.*,cm.fname,cm.mname,cm.lname from DepositMaster dm,CustomerMaster cm where cm.cid=dm.cid and dm.ac_type='"
								+ acctype
								+ "' and dm.close_ind=0 order by dm.ac_no");
			else if (type == 4)
				rs_dep = stmt_dep
						.executeQuery("select distinct dm.ac_no,dm.*,cm.fname,cm.mname,cm.lname from DepositMaster dm,CustomerMaster cm where cm.cid=dm.cid and dm.ac_type='"
								+ acctype
								+ "' and concat(right(dm.mat_date,4),'-',mid(dm.mat_date,locate('/',dm.mat_date)+1,(locate('/',dm.mat_date,4)-locate('/',dm.mat_date)-1)),'-',left(dm.mat_date,locate('/',dm.mat_date)-1)) between '"
								+ fr_date
								+ "' and '"
								+ to_date
								+ "' order by dm.ac_no");
			else if (type == 5)
				rs_dep = stmt_dep
						.executeQuery("select distinct dm.*,concat_ws(' ',fname,mname,lname) as name,ca.phno,ca.mobile,ca.email from DepositMaster dm,CustomerMaster cm ,CustomerAddr ca where cm.cid=dm.cid and cm.cid=ca.cid and dm.ac_type='"
								+ acctype + "' and dm.ac_no=" + fr_acno);
			else if (type == 6)
				rs_dep = stmt_dep
						.executeQuery("select distinct dm.ac_no,dm.*,cm.fname,cm.mname,cm.lname,dt.int_amt from CustomerMaster cm,DepositMaster dm,DepositTransaction dt where dm.ac_type='"
								+ acctype
								+ "' and concat(right(dm.int_upto_date,4),'-',mid(dm.int_upto_date,locate('/',dm.int_upto_date)+1,(locate('/',dm.int_upto_date,4)-locate('/',dm.int_upto_date)-1)),'-',left(dm.int_upto_date,locate('/',dm.int_upto_date)-1)) between '"
								+ fr_date
								+ "' and '"
								+ to_date
								+ "'and cm.cid=dm.cid and dt.ac_type='"
								+ acctype
								+ "' and dt.ac_no=dm.ac_no and trn_type='I' order by dm.ac_no");
			rs_dep.last();
			if (rs_dep.getRow() == 0)
				throw new AccountNotFoundException();
			array_depositmasterobject = new DepositMasterObject[rs_dep.getRow()];
			rs_dep.beforeFirst();
			int i = 0;
			while (rs_dep.next()) {
				array_depositmasterobject[i] = new DepositMasterObject();
				if (rs_dep.getString("ve_user") == null)
					array_depositmasterobject[i].setVerified("F");
				else
					array_depositmasterobject[i].setVerified("T");
				array_depositmasterobject[i].setAccType(acctype);
				array_depositmasterobject[i].setAccNo(rs_dep.getInt("ac_no"));
				array_depositmasterobject[i]
						.setCustomerId(rs_dep.getInt("cid"));
				array_depositmasterobject[i].setMailingAddress(rs_dep
						.getInt("add_type"));
				array_depositmasterobject[i].setNoofJtHldrs(rs_dep
						.getInt("no_jt_hldr"));
				array_depositmasterobject[i].setMaturityDate(rs_dep
						.getString("mat_date"));
				array_depositmasterobject[i].setDepDate(rs_dep
						.getString("dep_date"));
				array_depositmasterobject[i].setDepositDays(rs_dep
						.getInt("dep_days"));
				array_depositmasterobject[i].setDepositAmt(rs_dep
						.getDouble("dep_amt"));
				array_depositmasterobject[i].setMaturityAmt(rs_dep
						.getDouble("mat_amt"));
				array_depositmasterobject[i].setIntroAccno(rs_dep
						.getInt("intr_ac_no"));
				array_depositmasterobject[i].setIntroAccType(rs_dep
						.getString("intr_ac_type"));
				array_depositmasterobject[i].setNomineeRegNo(rs_dep
						.getInt("nom_no"));
				array_depositmasterobject[i].setInterestRate(rs_dep
						.getDouble("int_rate"));
				array_depositmasterobject[i].setReceivedBy(rs_dep
						.getString("rcvd_by"));
				array_depositmasterobject[i].setExcessAmt(rs_dep
						.getDouble("exc_amt"));
				array_depositmasterobject[i].setRenType(rs_dep
						.getString("ren_ac_type"));
				array_depositmasterobject[i].setRenewalAccno(rs_dep
						.getInt("ren_ac_no"));

				array_depositmasterobject[i].setPhoneNo(rs_dep
						.getString("ca.phno"));
				array_depositmasterobject[i].setMobileNo(rs_dep
						.getString("ca.mobile"));
				array_depositmasterobject[i].setEmailID(rs_dep
						.getString("ca.email"));

				if (array_depositmasterobject[i].getReceivedBy() != null
						&& array_depositmasterobject[i].getReceivedBy()
								.equalsIgnoreCase("T")) {
					array_depositmasterobject[i].setReceivedAccno(rs_dep
							.getInt("rcvd_ac_no"));
					array_depositmasterobject[i].setReceivedAccType(rs_dep
							.getString("rcvd_ac_type"));
				} else {
					array_depositmasterobject[i].setReceivedAccno(rs_dep
							.getInt("rcvd_ac_no"));
					array_depositmasterobject[i].setReceivedAccType("");
				}

				array_depositmasterobject[i].setInterestFrq(rs_dep
						.getString("int_freq"));
				array_depositmasterobject[i].setInterestMode(rs_dep
						.getString("int_mode"));
				if (array_depositmasterobject[i].getInterestMode() != null
						&& array_depositmasterobject[i].getInterestMode()
								.equals("T")) {
					array_depositmasterobject[i].setTransferAccType(rs_dep
							.getString("trf_ac_type"));
					array_depositmasterobject[i].setTransferAccno(rs_dep
							.getInt("trf_ac_no"));
				}
				array_depositmasterobject[i].setInterestUpto(rs_dep
						.getString("int_upto_date"));
				array_depositmasterobject[i].setLoanAvailed(rs_dep
						.getString("ln_availed"));
				array_depositmasterobject[i].setLoanAcType(rs_dep
						.getString("ln_ac_type"));
				array_depositmasterobject[i].setLoanAccno(rs_dep
						.getInt("ln_ac_no"));
				array_depositmasterobject[i].setReceiptno(rs_dep
						.getInt("rct_no"));
				array_depositmasterobject[i].setAutoRenewal(rs_dep
						.getString("auto_renewal"));
				array_depositmasterobject[i].setCloseInd(rs_dep
						.getInt("close_ind"));
				array_depositmasterobject[i].userverifier.setUserTml(rs_dep
						.getString("de_tml"));
				array_depositmasterobject[i].userverifier.setUserId(rs_dep
						.getString("de_user"));
				array_depositmasterobject[i].userverifier.setUserDate(rs_dep
						.getString("de_date"));
				array_depositmasterobject[i].setName(rs_dep.getString("name"));

				if (type == 5) {
					rs_extra = stmt_extra
							.executeQuery("select cid,addr_type from JointHolders where ac_type='"
									+ array_depositmasterobject[i].getAccType()
									+ "' and ac_no="
									+ array_depositmasterobject[i].getAccNo());
					int j = 0;
					int addrtype[] = null;
					int cids[] = null;
					if (rs_extra.next()) {
						rs_extra.last();
						addrtype = new int[rs_extra.getRow()];
						cids = new int[rs_extra.getRow()];
						rs_extra.beforeFirst();
					}
					while (rs_extra.next()) {
						addrtype[j] = rs_extra.getInt(2);
						cids[j++] = rs_extra.getInt(1);
					}
					array_depositmasterobject[i].setJointAddrType(addrtype);
					array_depositmasterobject[i].setJointCid(cids);
					array_depositmasterobject[i].setNomineeDetails(commonLocal
							.getNominee(array_depositmasterobject[i]
									.getNomineeRegNo()));
					array_depositmasterobject[i].setSigObj(commonLocal
							.getSignatureDetails(array_depositmasterobject[i]
									.getAccNo(), array_depositmasterobject[i]
									.getAccType()));
				}
				if (type == 3) {
					rs_extra = stmt_extra
							.executeQuery("select cum_int from DepositTransaction where ac_no="
									+ array_depositmasterobject[i].getAccNo()
									+ " and ac_type='"
									+ array_depositmasterobject[i].getAccType()
									+ "' and trn_type='I' order by trn_seq desc");
					if (rs_extra.next())
						array_depositmasterobject[i].setCumInterest(rs_extra
								.getDouble(1));
					else
						array_depositmasterobject[i].setCumInterest(0);
				}
				if (type == 4) {
					rs_extra = stmt_extra
							.executeQuery("select dep_paid,int_paid from DepositTransaction where ac_no="
									+ array_depositmasterobject[i].getAccNo()
									+ " and ac_type='"
									+ array_depositmasterobject[i].getAccType()
									+ "' order by trn_seq desc");
					if (rs_extra.next()) {
						array_depositmasterobject[i].setDepositPaid(rs_extra
								.getDouble(1));
						array_depositmasterobject[i].setInterestPaid(rs_extra
								.getDouble(2));
					} else {
						array_depositmasterobject[i].setDepositPaid(0);
						array_depositmasterobject[i].setInterestPaid(0);
					}
				}
				if (type == 6)
					array_depositmasterobject[i].setExcessAmt(rs_extra
							.getDouble("dt.int_amt"));// for Intr Accrued
				i++;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new AccountNotFoundException();
		} catch (CreateException e) {
			e.printStackTrace();
			throw new AccountNotFoundException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			commonLocal = null;
		}
		return array_depositmasterobject;
	}

	public DepositTransactionObject[] getDepositTransaction(int type,
			String acctype, int accno, String fr_date, String to_date)
			throws AccountNotFoundException {
		DepositTransactionObject array_deposittransactionobject[] = null;
		ResultSet rs = null, rs_narr = null;
		Connection conn = null;
		Statement stmt = null, stmt_narr = null;
		String trn_narration = null;
		StringTokenizer trn_token = null;

		try {
			conn = getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt_narr = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			if (type == 0)
				rs = stmt
						.executeQuery("select * from DepositTransaction where ac_type='"
								+ acctype
								+ "' and ac_no="
								+ accno
								+ " and ve_user is not null order by trn_seq");
			else if (type == 1)
				rs = stmt
						.executeQuery("select dt.* from DepositMaster dm,CustomerMaster cm,DepositTransaction dt where cm.cid=dm.cid and dm.ac_type='"
								+ acctype
								+ "' and dm.ac_no="
								+ accno
								+ " and dt.ac_no=dm.ac_no and dt.ac_type=dm.ac_type and dm.close_ind=0 order by dt.trn_seq");
			else if (type == 2)
				rs = stmt
						.executeQuery("select dt.* from DepositMaster dm,CustomerMaster cm,DepositTransaction dt where cm.cid=dm.cid and dm.ac_type='"
								+ acctype
								+ "' and dm.ac_no="
								+ accno
								+ " and dt.ac_type=dm.ac_type and dm.ac_no=dt.ac_no and concat(right(dt.trn_date,4),'-',mid(dt.trn_date,locate('/',dt.trn_date)+1,(locate('/',dt.trn_date,4)-locate('/',dt.trn_date)-1)),'-',left(dt.trn_date,locate('/',dt.trn_date)-1)) between '"
								+ fr_date
								+ "' and '"
								+ to_date
								+ "' order by dt.trn_seq");
			else
				rs = stmt
						.executeQuery("select * from DepositTransaction where ac_type='"
								+ acctype + "' and ac_no=" + accno);
			rs.last();
			if (rs.getRow() == 0)
				throw new AccountNotFoundException();
			array_deposittransactionobject = new DepositTransactionObject[rs
					.getRow()];
			rs.beforeFirst();
			int i = 0;
			while (rs.next()) {
				array_deposittransactionobject[i] = new DepositTransactionObject();
				array_deposittransactionobject[i].setTranDate(rs
						.getString("trn_date"));
				array_deposittransactionobject[i].setTranType(rs
						.getString("trn_type"));
				array_deposittransactionobject[i].setDepositAmt(rs
						.getDouble("dep_amt"));
				array_deposittransactionobject[i].setInterestAmt(rs
						.getDouble("int_amt"));
				array_deposittransactionobject[i].setTranMode(rs
						.getString("trn_mode"));
				array_deposittransactionobject[i].setReferenceNo(rs
						.getInt("ref_no"));
				if (type == 0) {
					array_deposittransactionobject[i].setRDBalance(rs
							.getDouble("rd_bal"));
					array_deposittransactionobject[i].setCumInterest(rs
							.getDouble("cum_int"));
					array_deposittransactionobject[i].setDepositPaid(rs
							.getDouble("dep_paid"));
					array_deposittransactionobject[i].setInterestPaid(rs
							.getDouble("int_paid"));
				}
				trn_narration = rs.getString("trn_narr");
				if (trn_narration != null) {
					try {
						trn_token = new StringTokenizer(trn_narration, " ");
						if (trn_token.hasMoreTokens()) {
							rs_narr = stmt_narr
									.executeQuery("select moduleabbr from Modules where modulecode='"
											+ trn_token.nextToken() + "'");
							if (rs_narr.next()) {
								trn_narration = rs_narr.getString("moduleabbr")
										+ " " + trn_token.nextToken();
							}
						}
					} catch (Exception exe) {
						exe.printStackTrace();
					}
				}
				// array_deposittransactionobject[i].setTranNarration(rs.getString("trn_narr"));
				array_deposittransactionobject[i]
						.setTranNarration(trn_narration);
				array_deposittransactionobject[i].setCdind(rs
						.getString("cd_ind"));
				array_deposittransactionobject[i].obj_userverifier.setUserId(rs
						.getString("de_user"));
				array_deposittransactionobject[i].obj_userverifier.setVerId(rs
						.getString("ve_user"));
				i++;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new AccountNotFoundException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return (array_deposittransactionobject);
	}

	public int verifyDepositMaster(DepositMasterObject depmast, int type)
			throws CreateException, Verified {
		int int_dp_type = 0;
		String string_cash_tml = null;
		ResultSet rs = null;
		Statement stmt = null;
		String trn_mode = null;
		Connection conn = null;
		String dr_ac_type = null;
		int dr_ac_no = 0;
		ResultSet rs_cheque = null;
		try {
			System.out.println("Inside verifyDM ACOUNT type... "
					+ depmast.getAccType());
			conn = getConnection();
			commonLocal = commonLocalHome.create();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			if (depmast.getReceivedBy().equals("T")) {
				trn_mode = "T";
				dr_ac_type = depmast.getReceivedAccType();
				dr_ac_no = depmast.getReceivedAccno();
			} else if (depmast.getReceivedBy().equals("C")) {
				trn_mode = "C";
			} else {
				trn_mode = "G";
			}
			// fix for the internal clearing which updates the trn_mode to G,
			// instead it should be T , by Murugesh 26/06/2007
			if (trn_mode.equalsIgnoreCase("G")) {
				rs_cheque = conn
						.createStatement()
						.executeQuery(
								"select clg_type, dr_ac_type ,dr_ac_no,chqddpo  from Cheque where post_ind='T' and ctrl_no= "
										+ depmast.getReceivedAccno()
										+ " and doc_sou=doc_dest");

				if (rs_cheque.next()
						&& rs_cheque.getString("clg_type").trim()
								.equalsIgnoreCase("T")
						&& rs_cheque.getString("chqddpo").trim()
								.equalsIgnoreCase("C")) {
					trn_mode = "T";
					dr_ac_type = rs_cheque.getString("dr_ac_type");
					dr_ac_no = rs_cheque.getInt("dr_ac_no");
				}
			}

			if (type == 0) {

				rs = stmt
						.executeQuery("select ac_no from DepositMaster where ac_type='"
								+ depmast.getAccType()
								+ "' and ac_no="
								+ depmast.getAccNo()
								+ " and ve_tml is not null");
				rs.last();
				if (rs.getRow() != 0)
					throw new Verified();

				Statement stmt_cash_tml = conn.createStatement();
				ResultSet rs_cash_tml = stmt_cash_tml
						.executeQuery("select de_tml from DayCash where scroll_no="
								+ depmast.getReceivedAccno() + " ");
				if (rs_cash_tml.next())
					string_cash_tml = rs_cash_tml.getString("de_tml");

				stmt.executeUpdate("update DepositTransaction set trn_source='"
						+ string_cash_tml + "', ve_user='"
						+ depmast.userverifier.getVerId() + "',ve_tml='"
						+ depmast.userverifier.getVerTml() + "',ve_date='"
						+ depmast.userverifier.getVerDate()
						+ "' where ac_type='" + depmast.getAccType()
						+ "' and ac_no=" + depmast.getAccNo());
				stmt.executeUpdate("update DepositMaster set ve_user='"
						+ depmast.userverifier.getVerId() + "',ve_tml='"
						+ depmast.userverifier.getVerTml() + "',ve_date='"
						+ depmast.userverifier.getVerDate()
						+ "' where ac_type='" + depmast.getAccType()
						+ "' and ac_no=" + depmast.getAccNo());
				// Verifying the scroll No.
				stmt.executeUpdate("update DayCash set ac_no="
						+ depmast.getAccNo() + ",attached='T', ve_user='"
						+ depmast.userverifier.getVerId() + "',ve_date='"
						+ depmast.userverifier.getVerDate() + "',ve_tml='"
						+ depmast.userverifier.getVerTml()
						+ "' where scroll_no=" + depmast.getReceivedAccno()
						+ " and trn_date='" + getSysDate() + "'");

				stmt
						.executeUpdate("insert into DepositMasterLog select * from DepositMaster where ac_no="
								+ depmast.getAccNo()
								+ " and ac_type='"
								+ depmast.getAccType() + "'");
				if (depmast.getDPType() == 0)
					int_dp_type = 1;
				else if (depmast.getDPType() == 1)
					int_dp_type = 2;
				else
					int_dp_type = 1;
				System.out.println("int_dp_type======> " + int_dp_type);
				System.out.println("depmast.getAccType()----> "
						+ depmast.getAccType());
				rs = stmt
						.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
								+ int_dp_type
								+ " and gk.ac_type = '"
								+ depmast.getAccType()
								+ "' and trn_type='D' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
				rs.next();
				GLTransObject trnobj = new GLTransObject();
				// code changed by sanjeet..
				//if(trnobj!=null){
				trnobj.setTrnDate(getSysDate());
				// trnobj.setTrnDate(commonLocal.getSysDate());
				trnobj.setGLType(rs.getString("gk.gl_type"));
				trnobj.setGLCode(rs.getString("gk.gl_code"));
				if (depmast.getReceivedBy().equals("T"))
					trnobj.setTrnMode("T");
				else if (depmast.getReceivedBy().equals("C"))
					trnobj.setTrnMode("C");
				else
					trnobj.setTrnMode("G");
				trnobj
						.setAmount(depmast.getDepositAmt()
								* rs.getInt("mult_by"));
				trnobj.setCdind("C");
				trnobj.setAccType(depmast.getAccType());
				trnobj.setAccNo(String.valueOf(depmast.getAccNo()));
				trnobj.setTrnType("D");
				if (depmast.getReceivedBy().equals("T"))
					trnobj.setRefNo(0);
				else if (depmast.getReceivedBy().equals("C"))
					trnobj.setRefNo(depmast.getReceivedAccno());
				else
					trnobj.setRefNo(depmast.getReceivedAccno());
				trnobj.setTrnSeq(1);
				trnobj.setVtml(depmast.userverifier.getVerTml());
				trnobj.setVid(depmast.userverifier.getVerId());
				trnobj.setVDate(depmast.userverifier.getVerDate());
				commonLocal.storeGLTransaction(trnobj);
				//}

				if (depmast.getReceivedBy().equals("T")) {
					AccountTransObject am = new AccountTransObject();
					am.setAccType(depmast.getReceivedAccType());
					am.setAccNo(depmast.getReceivedAccno());
					am.setTransDate(getSysDate());
					am.setTransType("P");
					am.setTransAmount(depmast.getDepositAmt());
					am.setTransMode("T");
					am.setTransSource(depmast.userverifier.getVerTml());
					am.setCdInd("D");
					am.setChqDDNo(0);
					am.setChqDDDate("");
					am.setTransNarr(depmast.getAccType() + " "
							+ String.valueOf(depmast.getAccNo()));
					am.setRef_No(0);
					am.setPayeeName(depmast.getName());
					am.setCloseBal(depmast.getDepositAmt());
					am.setLedgerPage(0);
					am.uv.setUserTml(depmast.userverifier.getVerTml());
					am.uv.setUserId(depmast.userverifier.getVerId());
					am.uv.setUserDate(depmast.userverifier.getVerDate());
					am.uv.setVerTml(depmast.userverifier.getVerTml());
					am.uv.setVerId(depmast.userverifier.getVerId());
					am.uv.setVerDate(depmast.userverifier.getVerDate());

					commonLocal.storeAccountTransaction(am);
				} else if (depmast.getReceivedBy().equals("C")) {
					// rs = stmt.executeQuery("select
					// gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost
					// gp where code = 1 and gk.ac_type = '1019001' and
					// trn_type='R' and cr_dr='D' and gk.ac_type = gp.ac_type
					// and gp.gl_code = gk.gl_code");
					rs = stmt
							.executeQuery("Select gl_type,gl_code from GLKeyParam where ac_type='1019001' ");
					if (rs != null) {
						rs.next();
						// sanjeet
						trnobj.setTrnDate(getSysDate());
						// trnobj.setTrnDate(commonLocal.getSysDate());
						trnobj.setGLType(rs.getString("gl_type"));
						trnobj.setGLCode(rs.getString("gl_code"));
						trnobj.setTrnMode("C");
						// trnobj.setAmount(depmast.getDepositAmt()*rs.getInt("mult_by"));
						trnobj.setAmount(depmast.getDepositAmt());
						trnobj.setCdind("D");
						trnobj.setAccType(depmast.getAccType());
						trnobj.setAccNo(String.valueOf(depmast.getAccNo()));
						trnobj.setTrnType("R");
						trnobj.setTrnSeq(0);
						trnobj.setVtml(depmast.userverifier.getVerTml());
						trnobj.setVid(depmast.userverifier.getVerId());
						commonLocal.storeGLTransaction(trnobj);
					}

				} else if (depmast.getReceivedBy().equals("G")) {
					rs = stmt
							.executeQuery("select gl_code,gl_type from GLKeyParam where code = 3 and ac_type = '1011001'");
					rs.next();
					// sanjeet
					trnobj.setTrnDate(getSysDate());
					// trnobj.setTrnDate(commonLocal.getSysDate());
					trnobj.setGLType(rs.getString("gl_type"));
					trnobj.setGLCode(rs.getString("gl_code"));
					trnobj.setTrnMode("T");
					trnobj.setAmount(depmast.getDepositAmt());
					trnobj.setCdind("D");
					trnobj.setAccType(depmast.getAccType());
					trnobj.setAccNo(String.valueOf(depmast.getAccNo()));
					trnobj.setTrnType("P");
					trnobj.setTrnSeq(0);
					trnobj.setVtml(depmast.userverifier.getVerTml());
					trnobj.setVid(depmast.userverifier.getVerId());
					commonLocal.storeGLTransaction(trnobj);
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			sessionContext.setRollbackOnly();
			return 0;
		//	throw new RecordNotInsertedException();
		} catch (Exception e) {
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			commonLocal = null;
		}
		return 1;
	}

	/**
	 * Method for Recurring Daily Interest calculation.
	 * 
	 * @throws DateFormatException
	 */
	public DepositIntRepObject[] RDInterestCalc(int type, String uid,
			String utml, String udate) throws RecordsNotFoundException {
		DepositIntRepObject array_depositintrepobject[] = null;
		String interest_upto_date, nextpaydate, from_date, today_date, to_date;
		ResultSet rs_main = null, rs_trn = null;
		Statement stat_main = null, stmt_trn = null;
		DepositTransactionObject deptrnobj[] = null;
		Connection conn = null;
		System.out.println("Inside RDInterestCalc()...");
		try {
			conn = getConnection();
			commonLocal = commonLocalHome.create();
			stat_main = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt_trn = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			today_date = getSysDate();
			if (type == 0) {

				rs_main = stat_main.executeQuery("select dm.ac_no,next_pay_date,dm.int_rate,int_upto_date,mat_date,ac_type,custtype,moduleabbr,concat_ws(' ',fname,mname,lname) as name,int_mode,trf_ac_type,trf_ac_no from CustomerMaster cm,DepositMaster dm,Modules where ac_type like '1004%' and cm.cid=dm.cid and ac_type = modulecode and mat_post='N' and close_ind=0 and int_upto_date != next_pay_date and concat(right(next_pay_date,4),'-',mid(next_pay_date,locate('/',next_pay_date)+1,(locate('/',next_pay_date,4)-locate('/',next_pay_date)-1)),'-',left(next_pay_date,locate('/',next_pay_date)-1))<='"
								+ Validations.convertYMD(getSysDate())
								+ "' and dm.ve_tml is not null order by dm.ac_no");
				rs_main.last();
				if (rs_main.getRow() == 0)
					throw new SQLException();
				int count = 0;
				rs_main.beforeFirst();
				while (rs_main.next()) {
					if (commonLocal.getDaysFromTwoDate(rs_main
							.getString("int_upto_date"), rs_main
							.getString("next_pay_date")) == 1
							|| commonLocal.getDaysFromTwoDate(rs_main
									.getString("int_upto_date"), rs_main
									.getString("mat_date")) == 1)
						continue;
					count++;
				}
				array_depositintrepobject = new DepositIntRepObject[count];
				rs_main.beforeFirst();
				int i = 0;
				while (rs_main.next()) {
					try {
						if (commonLocal.getDaysFromTwoDate(rs_main
								.getString("int_upto_date"), rs_main
								.getString("next_pay_date")) == 1
								|| commonLocal.getDaysFromTwoDate(rs_main
										.getString("int_upto_date"), rs_main
										.getString("mat_date")) == 1)
							continue;

						array_depositintrepobject[i] = new DepositIntRepObject();
						array_depositintrepobject[i].setState("T");
						int accno = 0, lasttrnseq = 0;
						String date1 = null, date2 = null;
						double interest_amount = 0, interest_rate = 0, rdbal = 0, cum_int = 0;
						interest_upto_date = rs_main.getString("int_upto_date");
						nextpaydate = rs_main.getString("next_pay_date");
						String mode = rs_main.getString("int_mode");
						String dptype = rs_main.getString("dm.ac_type");
						interest_rate = rs_main.getDouble("dm.int_rate");
						int dpcattype = rs_main.getInt("custtype");
						if (dpcattype == 0)
							dpcattype = 3;
						else
							dpcattype = 4;
						date1 = commonLocal.getFutureDayDate(
								interest_upto_date, 1);
						System.out.println("date1==>" + date1);
						// from_date = Validations.convertYMD(date1);
						to_date = Validations.convertYMD(nextpaydate);
						accno = rs_main.getInt("dm.ac_no");
						array_depositintrepobject[i].setAccType(rs_main.getString("moduleabbr"));
						array_depositintrepobject[i].setAccNo(accno);
						array_depositintrepobject[i].setName(rs_main.getString("name"));
						array_depositintrepobject[i].setInterestFrq("Quarterly");
						array_depositintrepobject[i].setInterestUpto(rs_main.getString("int_upto_date"));
						array_depositintrepobject[i].setInterestMode(mode);
						array_depositintrepobject[i].setInterestRate(interest_rate);

						rs_trn = stmt_trn.executeQuery("select rd_bal,cum_int,trn_seq from DepositTransaction where ac_type='"
										+ rs_main.getString("ac_type")
										+ "' and ac_no="
										+ accno
										+ " and ve_tml is not null order by trn_seq desc limit 1");
						if (rs_trn.next()) {
							// rdbal=rs_trn.getDouble("rd_bal");
							// cum_int = rs_trn.getDouble("cum_int");
							lasttrnseq = rs_trn.getInt("trn_seq");
						}

						String trn_date = null;
						rs_trn = stmt_trn
								.executeQuery("select trn_date,dep_amt,rd_bal,cum_int,trn_seq from DepositTransaction where ac_type='"
										+ rs_main.getString("ac_type")
										+ "' and ac_no="
										+ accno
										+ " and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) >='"
										+ Validations.convertYMD(date1)
										+ "' and trn_type='I' and ve_tml is not null order by trn_seq limit 1");
						if (rs_trn.next()) {
							   trn_date = rs_trn.getString("trn_date");
						    }
						deptrnobj = getRDReBalance(dptype, accno); 
						rdbal = deptrnobj[deptrnobj.length - 1].getRDBalance();
						cum_int = deptrnobj[deptrnobj.length - 1].getCumInterest();

						double rdbal_cal = 0;
						double rdbal_prd = 0;
						int m = 0;
						if (trn_date != null) {
							// from_date=Validations.convertYMD(commonLocal.getFutureDayDate(trn_date,-1));
							from_date = Validations.convertYMD(trn_date);
						} else {
							trn_date = date1;
							from_date = Validations.convertYMD(trn_date);
						}
						rs_trn = stmt_trn.executeQuery("select trn_date,dep_amt,rd_bal,cum_int,trn_seq from DepositTransaction where ac_type='"
										+ rs_main.getString("ac_type")
										+ "' and ac_no="
										+ accno
										+ " and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"
										+ from_date
										+ "' and '"
										+ to_date
										+ "' and trn_type='D' and ve_tml is not null order by trn_seq");
						while (rs_trn.next()) {
							for (m = 0; m < deptrnobj.length; m++) {
								if (deptrnobj[m].getTranDate().equals(trn_date)) {
									rdbal_cal = deptrnobj[m].getRDBalance();
									break;
								}

							}
							date2 = rs_trn.getString("trn_date");
							rdbal_prd += (rdbal_cal
									* commonLocal.getDaysFromTwoDate(date1,
											date2) * interest_rate) / 36500;
							date1 = date2;
							trn_date = date1;
							// trn_date1=date2;
						}

						rdbal_cal = deptrnobj[deptrnobj.length - 1]
								.getRDBalance();
						System.out.println("RD_BAL==>" + rdbal_cal);
						System.out.println("days==>"
								+ commonLocal.getDaysFromTwoDate(date1,
										nextpaydate));
						rdbal_prd += (rdbal_cal
								* commonLocal.getDaysFromTwoDate(date1,
										nextpaydate) * interest_rate) / 36500;
						interest_amount = Math.round(rdbal_prd);

						/*
						 * rs_trn=stmt_trn.executeQuery("select
						 * trn_date,dep_amt,rd_bal,cum_int,trn_seq from
						 * DepositTransaction where ac_type='"+dptype+"' and
						 * ac_no="+accno+" and
						 * concat(right(trn_date,4),'-',mid(trn_date
						 * ,locate('/',trn_date
						 * )+1,(locate('/',trn_date,4)-locate
						 * ('/',trn_date)-1)),'-'
						 * ,left(trn_date,locate('/',trn_date)-1)) between
						 * '"+from_date+"' and '"+to_date+"' and trn_type='D'
						 * and ve_tml is not null order by trn_seq");
						 * if(rs_trn.next()) { //date1 =interest_upto_date;
						 * 
						 * double rdbal_cal = 0; double rdbal_prd = 0;
						 * while(rs_trn.next()) {
						 * 
						 * date2 = rs_trn.getString("trn_date");
						 * //date2=commonLocal.getFutureDayDate(date2,-1);
						 * System.out.println("date2==>"+date2);
						 * //System.out.println
						 * ("rd_bal==>"+rs_trn.getDouble("rd_bal")); rdbal_cal =
						 * rs_trn
						 * .getDouble("rd_bal")-rs_trn.getDouble("dep_amt");
						 * System.out.println("RD_BAL==>"+rdbal_cal);
						 * System.out.
						 * println("days==>"+commonLocal.getDaysFromTwoDate
						 * (date1,date2)); rdbal_prd +=
						 * (rdbal_cal*commonLocal.getDaysFromTwoDate
						 * (date1,date2)*interest_rate)/36500;
						 * 
						 * date1 = date2; rdbal_cal =
						 * rs_trn.getDouble("rd_bal"); }
						 * 
						 * System.out.println("days==>"+commonLocal.
						 * getDaysFromTwoDate(date1,nextpaydate)); rdbal_prd +=
						 * (rdbal_cal*commonLocal.getDaysFromTwoDate(date1,
						 * nextpaydate)*interest_rate)/36500; //interest_amount
						 * = Math.round((rdbal_prd*interest_rate)/36500);
						 * interest_amount=Math.round(rdbal_prd); } else{
						 * interest_amount =
						 * Math.round((rdbal*commonLocal.getDaysFromTwoDate
						 * (date1,nextpaydate)*interest_rate)/36500); }
						 */
						rdbal += interest_amount;
						cum_int += interest_amount;
						array_depositintrepobject[i].setDepositAmt(rdbal
								- cum_int);
						array_depositintrepobject[i]
								.setInterestAmt(interest_amount);
						System.out.println("while 5 next");
						PreparedStatement ps_trn = conn
								.prepareStatement("insert into DepositTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
						ps_trn.setString(1, dptype);
						ps_trn.setInt(2, accno);
						ps_trn.setInt(3, ++lasttrnseq);
						ps_trn.setString(4, today_date);
						ps_trn.setString(5, "I");
						ps_trn.setDouble(6, 0);
						ps_trn.setDouble(7, interest_amount);
						ps_trn.setDouble(8, 0);
						ps_trn.setDouble(9, 0);
						ps_trn.setDouble(10, rdbal);
						ps_trn.setString(11, null);
						ps_trn.setString(12, commonLocal.getFutureDayDate(
								nextpaydate, -1));
						ps_trn.setInt(13, 0);
						ps_trn.setString(14, "By Int");
						ps_trn.setString(15, "T");
						ps_trn.setString(16, uid);
						ps_trn.setString(17, "C");
						ps_trn.setDouble(18, cum_int);
						ps_trn.setString(19, utml);
						ps_trn.setString(20, uid);
						ps_trn.setString(21, udate);
						ps_trn.setString(22, utml);
						ps_trn.setString(23, uid);
						ps_trn.setString(24, udate);
						ps_trn.executeUpdate();
						/**
						 * for rd ind/institute int_DUE
						 * 
						 */
						rs_trn = stmt_trn
								.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
										+ dpcattype
										+ " and gk.ac_type = '"
										+ dptype
										+ "' and trn_type='I' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
						if (rs_trn.next()) {
							System.out.println(" am inside GL");
							GLTransObject trnobj = new GLTransObject();
							trnobj.setTrnDate(today_date);
							trnobj.setGLType(rs_trn.getString("gk.gl_type"));
							trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
							trnobj.setTrnMode("T");
							trnobj.setAmount(interest_amount
									* rs_trn.getInt("mult_by"));
							trnobj.setCdind("C");
							trnobj.setAccType(dptype);
							trnobj.setAccNo(String.valueOf(accno));
							trnobj.setTrnType("I");
							trnobj.setRefNo(0);
							trnobj.setTrnSeq(lasttrnseq);
							trnobj.setVtml(utml);
							trnobj.setVid(uid);
							trnobj.setVDate(udate);
							commonLocal.storeGLTransaction(trnobj);
							System.out.println("updated GL");
						}
						dpcattype = dpcattype + 2;
						/**
						 * for rd ind/institute int_PD loss account
						 * 
						 */
						rs_trn = stmt_trn
								.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
										+ dpcattype
										+ " and gk.ac_type = '"
										+ dptype
										+ "' and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
						if (rs_trn.next()) {
							System.out.println("i am in updation process");
							GLTransObject trnobj = new GLTransObject();
							trnobj.setTrnDate(today_date);
							trnobj.setGLType(rs_trn.getString("gk.gl_type"));
							trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
							trnobj.setTrnMode("T");
							trnobj.setAmount(interest_amount
									* rs_trn.getInt("mult_by"));
							trnobj.setCdind("D");
							trnobj.setAccType(dptype);
							trnobj.setAccNo(String.valueOf(accno));
							trnobj.setTrnType("P");
							trnobj.setRefNo(0);
							trnobj.setTrnSeq(lasttrnseq);
							trnobj.setVtml(utml);
							trnobj.setVid(uid);
							trnobj.setVDate(udate);
							commonLocal.storeGLTransaction(trnobj);
							System.out.println("12121221212121221");
						}
						String next_paydate = commonLocal.getFutureMonthDate(
								nextpaydate, 3);
						if (commonLocal.getDaysFromTwoDate(next_paydate,
								rs_main.getString("mat_date")) <= 0)
							next_paydate = rs_main.getString("mat_date");
						stmt_trn
								.executeUpdate("update DepositMaster set next_pay_date='"
										+ next_paydate
										+ "',int_upto_date='"
										+ commonLocal.getFutureDayDate(
												nextpaydate, -1)
										+ "',lst_trn_seq="
										+ lasttrnseq
										+ " where ac_type='"
										+ dptype
										+ "' and ac_no=" + accno);

						array_depositintrepobject[i]
								.setInterestFrq("Quarterly");
						array_depositintrepobject[i]
								.setInterestUpto(commonLocal.getFutureDayDate(
										nextpaydate, -1));
						array_depositintrepobject[i].setInterestMode(mode);

						PreparedStatement ps_commit = conn
								.prepareStatement("commit");
						ps_commit.execute();

					} catch (SQLException e) {
						e.printStackTrace();
						PreparedStatement ps_rollback = conn
								.prepareStatement("rollback");
						ps_rollback.execute();
						array_depositintrepobject[i].setState("F");
						array_depositintrepobject[i].setInterestAmt(0);
						array_depositintrepobject[i].setNarration(null);
					}
					i++;
				}
				stmt_trn.executeUpdate("update Modules set lst_intdt='"
						+ today_date + "' where modulecode=1004000");
			} else if (type == 1) {
				rs_main = stat_main
						.executeQuery("select concat_ws(' ',fname,mname,lname) as name,dm.cid,dm.dep_amt,dm.int_rate,dm.int_upto_date,dm.int_freq,dt.* from CustomerMaster as cm,DepositTransaction as dt left join DepositMaster as dm on dt.ac_type=dm.ac_type and dt.ac_no=dm.ac_no where  dt.trn_date='"
								+ getSysDate()
								+ "' and cm.cid=dm.cid and dt.trn_type='I' and dt.ac_type like '1004%' order by dt.ac_no");
				rs_main.last();
				if (rs_main.getRow() == 0) {
					return null;
				} else {
					array_depositintrepobject = new DepositIntRepObject[rs_main
							.getRow()];
					rs_main.beforeFirst();
					int i = 0;
					while (rs_main.next()) {
						array_depositintrepobject[i] = new DepositIntRepObject();
						rs_trn = stmt_trn
								.executeQuery("select moduleabbr from Modules where modulecode='"
										+ rs_main.getString("ac_type") + "'");
						if (rs_trn.next()) {
							array_depositintrepobject[i].setAccType(rs_trn
									.getString("moduleabbr"));
						}
						array_depositintrepobject[i].setAccNo(rs_main
								.getInt("ac_no"));
						array_depositintrepobject[i].setName(rs_main
								.getString("name"));
						array_depositintrepobject[i].setDepositAmt(rs_main
								.getDouble("dep_amt"));
						array_depositintrepobject[i].setInterestFrq(rs_main
								.getString("int_freq"));
						array_depositintrepobject[i].setInterestRate(rs_main
								.getDouble("int_rate"));
						array_depositintrepobject[i].setInterestMode(rs_main
								.getString("trn_mode"));
						array_depositintrepobject[i].setInterestUpto(rs_main
								.getString("int_upto_date"));
						array_depositintrepobject[i].setInterestAmt(rs_main
								.getDouble("int_amt"));
						array_depositintrepobject[i].setNarration(rs_main
								.getString("trn_narr"));
						array_depositintrepobject[i].setState("T");
						i++;
					}
				}
				System.out.println("Returning from RDInterestCalc()....");

			}
		} catch (SQLException exception) {
			exception.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordsNotFoundException();
		} catch (CreateException e) {
			e.printStackTrace();
			sessionContext.setRollbackOnly();
			e.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordsNotFoundException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			commonLocal = null;
		}
		return array_depositintrepobject;
	}

	/**
	 * Recuuring Quarterly Interest Calculation Here no entries in any tables.
	 * 
	 * @throws DateFormatException
	 */

	public DepositIntRepObject[] RDquarterlyInterest(int type, String uid,
			String utml, String udate, String combo_date)
			throws RecordsNotFoundException {
		/**
		 * Type 1 for normal calculation Type 2 for Report
		 */
		DepositIntRepObject array_depositintrepobject[] = null;
		DepositTransactionObject deptrnobj[] = null;
		Statement stmt_main = null, stat_trn = null;
		Connection conn = null;
		ResultSet rs_main = null, rs_trn = null, rs = null;
		try {
			conn = getConnection();
			commonLocal = commonLocalHome.create();
			stmt_main = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stat_trn = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			if (type == 0) {
				int i;
				String from_date, to_date;
				String date;

				rs_main = stmt_main
						.executeQuery("select prod_date from Products");
				rs_main.next();
				date = rs_main.getString(1);
				// stmt_main.executeUpdate("insert into DQI select * from
				// DepositQuarterlyInterest where ac_type like '1004%' order by
				// ac_no");
				// stmt_main.executeUpdate("delete from DepositQuarterlyInterest
				// where ac_type like '1004%'");
				rs_main = stmt_main
						.executeQuery("select distinct ac_no,dm.int_rate,int_upto_date,next_pay_date,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,dep_amt,mat_amt,ac_type,moduleabbr from Modules,CustomerMaster cm,DepositMaster dm where ac_type like '1004%' and mat_post='N' and close_ind=0 and cm.cid=dm.cid and ac_type=modulecode and concat(right(mat_date,4),'-',mid(mat_date,locate('/',mat_date)+1,(locate('/',mat_date,4)-locate('/',mat_date)-1)),'-',left(mat_date,locate('/',mat_date)-1))>='"
								+ Validations.convertYMD(date)
								+ "' and concat(right(next_pay_date,4),'-',mid(next_pay_date,locate('/',next_pay_date)+1,(locate('/',next_pay_date,4)-locate('/',next_pay_date)-1)),'-',left(next_pay_date,locate('/',next_pay_date)-1))>'"
								+ Validations.convertYMD(date)
								+ "' and concat(right(next_pay_date,4),'-',mid(next_pay_date,locate('/',next_pay_date)+1,(locate('/',next_pay_date,4)-locate('/',next_pay_date)-1)),'-',left(next_pay_date,locate('/',next_pay_date)-1)) not in ('"
								+ Validations.convertYMD(date)
								+ "') order by dm.ac_type,dm.ac_no");
				rs_main.last();
				if (rs_main.getRow() == 0)
					throw new RecordsNotFoundException();
				array_depositintrepobject = new DepositIntRepObject[rs_main
						.getRow()];
				rs_main.beforeFirst();
				i = 0;
				while (rs_main.next()) {
					int acno = rs_main.getInt("ac_no");
					int lasttrnseq = 0;
					double interest_rate = rs_main.getDouble("dm.int_rate");
					double rdbal = 0, interest_amount = 0, cum_int = 0;
					String intupto = rs_main.getString("int_upto_date");
					String date1 = null, date2 = null, trn_date = null, trn_date1 = null;
					// date1 = commonLocal.getFutureDayDate(intupto,1);
					date1 = intupto;

					to_date = Validations.convertYMD(date);
					System.out.println("RD Acc No: " + acno);

					rs = stat_trn
							.executeQuery("select trn_date,dep_amt,rd_bal,cum_int,trn_seq from DepositTransaction where ac_type='"
									+ rs_main.getString("ac_type")
									+ "' and ac_no="
									+ acno
									+ " and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) >='"
									+ Validations.convertYMD(date1)
									+ "' and trn_type='I' and ve_tml is not null order by trn_seq limit 1");
					if (rs.next()) {
						trn_date = rs.getString("trn_date");
					}
					deptrnobj = getRDReBalance(rs_main.getString("ac_type"),
							acno);
					rdbal = deptrnobj[deptrnobj.length - 1].getRDBalance();
					cum_int = deptrnobj[deptrnobj.length - 1].getCumInterest();

					double rdbal_cal = 0;
					double rdbal_prd = 0;
					double amt = 0;
					int m = 0;
					if (trn_date != null) {
						// from_date=Validations.convertYMD(commonLocal.getFutureDayDate(trn_date,-1));
						from_date = Validations.convertYMD(trn_date);
					} else {
						trn_date = commonLocal.getFutureDayDate(date1, 1);
						from_date = Validations.convertYMD(trn_date);
					}
					rs_trn = stat_trn
							.executeQuery("select trn_date,dep_amt,rd_bal,cum_int,trn_seq from DepositTransaction where ac_type='"
									+ rs_main.getString("ac_type")
									+ "' and ac_no="
									+ acno
									+ " and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"
									+ from_date
									+ "' and '"
									+ to_date
									+ "' and trn_type='D' and ve_tml is not null order by trn_seq");
					while (rs_trn.next()) {
						for (m = 0; m < deptrnobj.length; m++) {
							if (deptrnobj[m].getTranDate().equals(trn_date)) {
								rdbal_cal = deptrnobj[m].getRDBalance();
								break;
							}

						}
						date2 = rs_trn.getString("trn_date");
						System.out.println("date2==>" + date2);
						System.out.println("RD_BAL==>" + rdbal_cal);
						System.out.println("days==>"
								+ commonLocal.getDaysFromTwoDate(date1, date2));
						rdbal_prd += (rdbal_cal
								* commonLocal.getDaysFromTwoDate(date1, date2) * interest_rate) / 36500;
						date1 = date2;
						trn_date = date1;
						// trn_date1=date2;
					}

					rdbal_cal = deptrnobj[deptrnobj.length - 1].getRDBalance();
					System.out.println("RD_BAL==>" + rdbal_cal);
					System.out.println("days==>"
							+ commonLocal.getDaysFromTwoDate(date1, date));
					rdbal_prd += (rdbal_cal
							* commonLocal.getDaysFromTwoDate(date1, date) * interest_rate) / 36500;
					interest_amount = Math.round(rdbal_prd);

					array_depositintrepobject[i] = new DepositIntRepObject();
					array_depositintrepobject[i].setAccType(rs_main
							.getString("moduleabbr"));
					array_depositintrepobject[i].setAccNo(acno);
					array_depositintrepobject[i].setName(rs_main
							.getString("name"));
					array_depositintrepobject[i].setInterestUpto(intupto);
					array_depositintrepobject[i].setDepositAmt(rs_main
							.getDouble("dep_amt"));
					array_depositintrepobject[i].setLastBalance(rdbal);
					array_depositintrepobject[i].setMaturityAmt(rs_main
							.getDouble("mat_amt"));
					array_depositintrepobject[i].setInterestRate(interest_rate);
					array_depositintrepobject[i]
							.setInterestAmt(interest_amount);

					stat_trn
							.executeUpdate("insert into DepositQuarterlyInterest values('"
									+ getSysDate()
									+ "','"
									+ rs_main.getString("ac_type")
									+ "',"
									+ acno
									+ ",'"
									+ array_depositintrepobject[i].getName()
									+ "',"
									+ array_depositintrepobject[i]
											.getDepositAmt()
									+ ","
									+ array_depositintrepobject[i]
											.getMaturityAmt()
									+ ","
									+ interest_rate
									+ ","
									+ rdbal
									+ ",'"
									+ intupto
									+ "',"
									+ interest_amount
									+ ",'"
									+ utml + "','" + uid + "','" + udate + "')");
					i++;
				}
				rs_main = stmt_main
						.executeQuery("select month from QtrDefinition where qtr_defn='T' order by month");
				StringTokenizer st = new StringTokenizer(date, "/");
				// String day = st.nextToken();

				int day = Integer.parseInt(st.nextToken());
				int month = Integer.parseInt(st.nextToken());
				int year = Integer.parseInt(st.nextToken());
				boolean check_flag = false;
				month = month % 12;
				while (rs_main.next()) {
					if (month < rs_main.getInt("month"))
						break;
					else if (rs_main.isLast() == true) {
						check_flag = true;
						break;
					} else
						continue;
				}
				if (check_flag) {
					rs_main.first();
					month = rs_main.getInt("month");
					year++;
				} else if (rs_main.isFirst() == true) {
					month = rs_main.getInt("month");
					year++;
				}

				// added by geetha on 24/04/08

				if (month == 1 || month == 3 || month == 5 || month == 7
						|| month == 8 || month == 10 || month == 12) {
					day = 31;
				} else if (month == 4 || month == 6 || month == 9
						|| month == 11) {
					day = 30;
				} else if (month == 2) {
					day = 28;
				}

				else
					month = rs_main.getInt("month");
				date = Validations.convertYMD(day + "/" + String.valueOf(month)
						+ "/" + String.valueOf(year));
				rs_main = stmt_main
						.executeQuery("select date_format(date_sub(date_add(date_sub('"
								+ date
								+ "',interval dayofmonth('"
								+ date
								+ "')-1 day),interval 1 month),interval 1 day),'%d/%m/%Y')");
				rs_main.next();
				if (stmt_main.executeUpdate("update Products set prod_date ='"
						+ rs_main.getString(1) + "'") == 0)
					throw new SQLException();

			} else if (type == 1) {
				rs_main = stmt_main
						.executeQuery("select dp.*,moduleabbr from DepositQuarterlyInterest dp,Modules where ac_type=modulecode and ac_type like '1004%' and dp.trn_date='"
								+ combo_date + "'");
				rs_main.last();
				if (rs_main.getRow() == 0)
					throw new RecordsNotFoundException();
				array_depositintrepobject = new DepositIntRepObject[rs_main
						.getRow()];
				rs_main.beforeFirst();
				int i = 0;
				while (rs_main.next()) {
					array_depositintrepobject[i] = new DepositIntRepObject();
					array_depositintrepobject[i].setAccType(rs_main
							.getString("moduleabbr"));
					array_depositintrepobject[i].setAccNo(rs_main.getInt(3));
					array_depositintrepobject[i].setName(rs_main.getString(4));
					array_depositintrepobject[i].setDepositAmt(rs_main
							.getDouble(5));
					array_depositintrepobject[i].setMaturityAmt(rs_main
							.getDouble(6));
					array_depositintrepobject[i].setInterestRate(rs_main
							.getDouble(7));
					array_depositintrepobject[i].setLastBalance(rs_main
							.getDouble(8));
					array_depositintrepobject[i].setInterestUpto(rs_main
							.getString(9));
					array_depositintrepobject[i].setInterestAmt(rs_main
							.getDouble(10));
					i++;
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordsNotFoundException();
		} catch (CreateException ce) {
			ce.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordsNotFoundException();
		} catch (Exception e) {
			e.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordsNotFoundException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();

			}
			commonLocal = null;
		}
		return array_depositintrepobject;
	}

	/**
	 * ReInvestment Daily Interest Calculation
	 * 
	 * @throws DateFormatException
	 */
	public DepositIntRepObject[] ReInvestmentInterestCalc(int type, String uid,
			String utml, String udate) throws RecordsNotFoundException,
			DateFormatException {
		DepositIntRepObject array_depositintrepobject[] = null;
		ResultSet rs_main = null, rs_trn = null;
		Connection conn = null;
		try {
			conn = getConnection();
			commonLocal = commonLocalHome.create();
			// sanjeet..
			String today_date = getSysDate();
			// String today_date = commonLocal.getSysDate();
			Statement stmt_main = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			Statement stmt_trn = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			if (type == 0) {
				rs_main = stmt_main
						.executeQuery("select dm.*,concat_ws(' ',fname,mname,lname) as name,moduleabbr,custtype from DepositMaster dm,CustomerMaster cm,Modules where cm.cid=dm.cid and dm.ac_type = modulecode and concat(right(next_pay_date,4),'-',mid(next_pay_date,locate('/' ,next_pay_date)+1,(locate('/',next_pay_date,4)-locate('/',next_pay_date)-1)),'-',left(next_pay_date,locate('/',next_pay_date)-1)) <= '"
								+ Validations.convertYMD(getSysDate())
								+ "' and close_ind =0 and ac_type like '1005%' order by ac_type,ac_no");
				rs_main.last();
				if (rs_main.getRow() == 0)
					throw new RecordsNotFoundException();
				int count = 0;
				rs_main.beforeFirst();
				while (rs_main.next()) {
					if (commonLocal.getDaysFromTwoDate(rs_main
							.getString("int_upto_date"), rs_main
							.getString("next_pay_date")) == 1
							|| commonLocal.getDaysFromTwoDate(rs_main
									.getString("int_upto_date"), rs_main
									.getString("mat_date")) == 1)
						continue;
					count++;
				}
				array_depositintrepobject = new DepositIntRepObject[count];
				rs_main.beforeFirst();
				int i = 0;
				while (rs_main.next()) {
					try {
						if (commonLocal.getDaysFromTwoDate(rs_main
								.getString("int_upto_date"), rs_main
								.getString("next_pay_date")) == 1
								|| commonLocal.getDaysFromTwoDate(rs_main
										.getString("int_upto_date"), rs_main
										.getString("mat_date")) == 1)
							continue;
						rs_trn = stmt_trn
								.executeQuery("select cum_int,trn_seq from DepositTransaction where ac_type='"
										+ rs_main.getString("ac_type")
										+ "' and ac_no="
										+ rs_main.getInt("ac_no")
										+ " and ve_tml is not null order by trn_seq desc limit 1");
						rs_trn.next();

						array_depositintrepobject[i] = new DepositIntRepObject();
						int lstseq = 0, acno = 0, dptype = 0;
						double amount = 0, interest_rate = 0, cum_int = 0;
						String uptodate = null, paydate = null, actype = null, mode = null;
						amount = rs_main.getDouble("dep_amt");
						cum_int = rs_trn.getDouble("cum_int");
						interest_rate = rs_main.getDouble("dm.int_rate");
						uptodate = commonLocal.getFutureDayDate(rs_main
								.getString("int_upto_date"), 1);
						paydate = rs_main.getString("next_pay_date");
						lstseq = rs_trn.getInt("trn_seq");
						actype = rs_main.getString("ac_type");
						acno = rs_main.getInt("ac_no");
						mode = rs_main.getString("int_mode");
						dptype = rs_main.getInt("custtype");

						if (dptype == 0)
							dptype = 3;
						else
							dptype = 4;
						int days = commonLocal.getDaysFromTwoDate(uptodate,
								paydate);
						double interest = Math.round(((amount + cum_int)
								* interest_rate * days) / (36500));
						array_depositintrepobject[i].setState("T");
						array_depositintrepobject[i].setAccType(rs_main
								.getString("moduleabbr"));
						array_depositintrepobject[i].setAccNo(acno);
						array_depositintrepobject[i].setName(rs_main
								.getString("name"));
						array_depositintrepobject[i]
								.setInterestFrq("Quarterly");
						array_depositintrepobject[i].setInterestUpto(rs_main
								.getString("int_upto_date"));
						array_depositintrepobject[i].setInterestMode(mode);
						array_depositintrepobject[i].setDepositAmt(amount);
						array_depositintrepobject[i]
								.setInterestRate(interest_rate);

						PreparedStatement ps1 = conn
								.prepareStatement("insert into DepositTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
						ps1.setString(1, actype);
						ps1.setInt(2, acno);
						ps1.setInt(3, ++lstseq);
						ps1.setString(4, today_date);
						ps1.setString(5, "I");
						ps1.setDouble(6, amount);
						ps1.setDouble(7, interest);
						ps1.setDouble(8, 0);
						ps1.setDouble(9, 0);
						ps1.setDouble(10, 0);
						ps1.setString(11, null);
						ps1.setString(12, commonLocal.getFutureDayDate(paydate,
								-1));
						ps1.setInt(13, 0);
						ps1.setString(14, "By Int");
						ps1.setString(15, "T");
						ps1.setString(16, uid);
						ps1.setString(17, "C");
						ps1.setDouble(18, cum_int + interest);
						ps1.setString(19, utml);
						ps1.setString(20, uid);
						ps1.setString(21, udate);
						ps1.setString(22, utml);
						ps1.setString(23, uid);
						ps1.setString(24, udate);
						ps1.executeUpdate();

						/**
						 * Credir to account gl
						 */
						rs_trn = stmt_trn
								.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
										+ dptype
										+ " and gk.ac_type = '"
										+ actype
										+ "' and trn_type='I' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
						if (rs_trn.next()) {
							GLTransObject trnobj = new GLTransObject();
							trnobj.setTrnDate(today_date);
							trnobj.setGLType(rs_trn.getString("gk.gl_type"));
							trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
							trnobj.setTrnMode("T");
							trnobj.setAmount(interest
									* rs_trn.getInt("mult_by"));
							trnobj.setCdind("C");
							trnobj.setAccType(actype);
							trnobj.setAccNo(String.valueOf(acno));
							trnobj.setTrnType("I");
							trnobj.setRefNo(0);
							trnobj.setTrnSeq(lstseq);
							trnobj.setVtml(utml);
							trnobj.setVid(uid);
							trnobj.setVDate(udate);
							commonLocal.storeGLTransaction(trnobj);
						}

						dptype = dptype + 2;
						/**
						 * Debit to loss account
						 */
						rs_trn = stmt_trn
								.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
										+ dptype
										+ " and gk.ac_type = '"
										+ actype
										+ "' and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
						if (rs_trn.next()) {
							GLTransObject trnobj = new GLTransObject();
							trnobj.setTrnDate(today_date);
							trnobj.setGLType(rs_trn.getString("gk.gl_type"));
							trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
							trnobj.setTrnMode("T");
							trnobj.setAmount(interest
									* rs_trn.getInt("mult_by"));
							trnobj.setCdind("D");
							trnobj.setAccType(actype);
							trnobj.setAccNo(String.valueOf(acno));
							trnobj.setTrnType("P");
							trnobj.setRefNo(0);
							trnobj.setTrnSeq(lstseq);
							trnobj.setVtml(utml);
							trnobj.setVid(uid);
							trnobj.setVDate(udate);
							commonLocal.storeGLTransaction(trnobj);
						}
						rs_trn = stmt_trn
								.executeQuery("select DATE_FORMAT(DAte_ADD('"
										+ Validations.convertYMD(paydate)
										+ "' ,INTERVAL 3 MONTH),'%d/%m/%Y')");
						rs_trn.next();
						String next_paydate = rs_trn.getString(1);
						if (Validations.dayCompare(next_paydate, rs_main
								.getString("mat_date")) <= 0)
							next_paydate = rs_main.getString("mat_date");
						stmt_trn
								.executeUpdate("update DepositMaster set next_pay_date='"
										+ next_paydate
										+ "',int_upto_date='"
										+ Validations.addDays(paydate, -1)
										+ "',lst_trn_seq="
										+ lstseq
										+ " where ac_type='"
										+ actype
										+ "' and ac_no=" + acno);

						array_depositintrepobject[i]
								.setInterestUpto(commonLocal.getFutureDayDate(
										paydate, -1));
						array_depositintrepobject[i].setInterestAmt(interest);

						PreparedStatement ps_rollback = conn
								.prepareStatement("commit");
						ps_rollback.execute();
					} catch (SQLException e) {
						e.printStackTrace();
						PreparedStatement ps_rollback = conn
								.prepareStatement("rollback");
						ps_rollback.execute();
						array_depositintrepobject[i].setState("F");
						array_depositintrepobject[i].setInterestAmt(0);
						array_depositintrepobject[i].setNarration(null);
					}
					i++;
				}
				stmt_main.executeUpdate("update Modules set lst_intdt='"
						+ today_date + "' where modulecode=1005000");
			} else if (type == 1) {
				rs_main = stmt_main
						.executeQuery("select concat_ws(' ',fname,mname,lname) as name,dm.cid,dm.dep_amt,dm.int_rate,dm.int_upto_date,dm.int_freq,dt.* from CustomerMaster as cm,DepositTransaction as dt left join DepositMaster as dm on dt.ac_type=dm.ac_type and dt.ac_no=dm.ac_no where  dt.trn_date='"
								+ getSysDate()
								+ "' and cm.cid=dm.cid and dt.trn_type='I' and dt.ac_type like '1005%' order by dt.ac_type,dt.ac_no");
				rs_main.last();
				if (rs_main.getRow() == 0)
					throw new RecordsNotFoundException();
				array_depositintrepobject = new DepositIntRepObject[rs_main
						.getRow()];
				rs_main.beforeFirst();
				int i = 0;
				while (rs_main.next()) {
					array_depositintrepobject[i] = new DepositIntRepObject();
					rs_trn = stmt_trn
							.executeQuery("select moduleabbr from Modules where modulecode='"
									+ rs_main.getString("ac_type") + "'");
					if (rs_trn.next()) {
						array_depositintrepobject[i].setAccType(rs_trn
								.getString("moduleabbr"));
					}
					array_depositintrepobject[i].setAccNo(rs_main
							.getInt("ac_no"));
					array_depositintrepobject[i].setName(rs_main
							.getString("name"));
					array_depositintrepobject[i].setDepositAmt(rs_main
							.getDouble("dep_amt"));
					array_depositintrepobject[i].setInterestFrq(rs_main
							.getString("int_freq"));
					array_depositintrepobject[i].setInterestRate(rs_main
							.getDouble("int_rate"));
					array_depositintrepobject[i].setInterestMode(rs_main
							.getString("trn_mode"));
					array_depositintrepobject[i].setInterestUpto(rs_main
							.getString("int_upto_date"));
					array_depositintrepobject[i].setInterestAmt(rs_main
							.getDouble("int_amt"));
					array_depositintrepobject[i].setNarration(rs_main
							.getString("trn_narr"));
					array_depositintrepobject[i].setState("T");
					i++;
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordsNotFoundException();
		} catch (CreateException ce) {
			ce.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordsNotFoundException();
		} catch (Exception e) {
			e.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordsNotFoundException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			commonLocal = null;
		}
		return array_depositintrepobject;
	}

	/**
	 * Fixed deposit daily interest calculation
	 * 
	 * @throws DateFormatException
	 * @throws FreezedAccountException
	 * @throws InOperativeAccountException
	 * @throws AccountClosedException
	 */

	public DepositIntRepObject[] FDInterestCalc(int type, String uid, String utml, String udate) throws RecordsNotFoundException {
		DepositIntRepObject array_depositintrepobject[] = null;
		ResultSet rs_main = null, rs_trn = null;
		Connection conn = null;
		try {
			conn = getConnection();
			commonLocal = commonLocalHome.create();
			String todate = getSysDate();
			Statement stmt_main = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			Statement stmt_trn = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			if (type == 0) {
				rs_main = stmt_main.executeQuery("select dm.*,concat_ws(' ',fname,mname,lname) as name,moduleabbr,custtype from DepositMaster dm,CustomerMaster cm,Modules where cm.cid=dm.cid and dm.ac_type = modulecode and concat(right(next_pay_date,4),'-',mid(next_pay_date,locate('/' ,next_pay_date)+1,(locate('/',next_pay_date,4)-locate('/',next_pay_date)-1)),'-',left(next_pay_date,locate('/',next_pay_date)-1)) <= '"+ Validations.convertYMD(getSysDate())+ "' and close_ind =0 and dm.ac_type like '1003%'");
				rs_main.last();
				if (rs_main.getRow() == 0)
					throw new RecordsNotFoundException();
				int count = 0;
				rs_main.beforeFirst();
				while (rs_main.next()) {
					if (commonLocal.getDaysFromTwoDate(rs_main.getString("int_upto_date"), rs_main.getString("next_pay_date")) == 1|| commonLocal.getDaysFromTwoDate(rs_main.getString("int_upto_date"), rs_main.getString("mat_date")) == 1)
						continue;
					count++;
				}
				array_depositintrepobject = new DepositIntRepObject[count];
				rs_main.beforeFirst();
				int i = 0;
				while (rs_main.next()) {
					try {
						if (commonLocal.getDaysFromTwoDate(rs_main.getString("int_upto_date"), rs_main.getString("next_pay_date")) == 1	|| commonLocal.getDaysFromTwoDate(rs_main.getString("int_upto_date"), rs_main.getString("mat_date")) == 1)
							continue;
						rs_trn = stmt_trn.executeQuery("select max(trn_seq) from DepositTransaction where ac_type='"+ rs_main.getString("ac_type")+ "' and ac_no="+ rs_main.getString("ac_no"));
						rs_trn.next();
						array_depositintrepobject[i] = new DepositIntRepObject();
						array_depositintrepobject[i].setState("T");
						int lstseq = 0, acno = 0, racno = 0, dptype = 0, ret_value = 0;
						double amt = 0, rate = 0;
						String uptodate = "", paydate = "", actype = "", ractype = "", mode = "", set_value = "";
						lstseq = rs_trn.getInt(1);
						amt = rs_main.getDouble("dep_amt");
						rate = rs_main.getDouble("dm.int_rate");
						uptodate = commonLocal.getFutureDayDate(rs_main.getString("int_upto_date"), 1);
						paydate = rs_main.getString("next_pay_date");
						actype = rs_main.getString("ac_type");
						acno = rs_main.getInt("ac_no");
						mode = rs_main.getString("int_mode");
						ractype = rs_main.getString("trf_ac_type");
						racno = rs_main.getInt("trf_ac_no");
						String frq = rs_main.getString("int_freq");
						dptype = rs_main.getInt("custtype");
						if (dptype == 0)
							dptype = 3;
						else
							dptype = 4;
						int days = commonLocal.getDaysFromTwoDate(uptodate,	paydate);
						double interest = Math.round((amt * rate * days)/ (365 * 100));
						double interest_monthly = Math.round((amt * rate)/ (1200 + rate));
						array_depositintrepobject[i].setAccType(rs_main.getString("moduleabbr"));
						array_depositintrepobject[i].setAccNo(acno);
						array_depositintrepobject[i].setName(rs_main.getString("name"));
						array_depositintrepobject[i].setDepositAmt(amt);
						array_depositintrepobject[i].setInterestRate(rate);
						if (frq.equalsIgnoreCase("M"))
							array_depositintrepobject[i].setInterestAmt(interest_monthly);
						else
							array_depositintrepobject[i].setInterestAmt(interest);
						if (frq.equalsIgnoreCase("M"))
							array_depositintrepobject[i].setInterestFrq("Monthly");
						else if (frq.equalsIgnoreCase("Q"))
							array_depositintrepobject[i].setInterestFrq("Quarterly");
						else if (frq.equalsIgnoreCase("H"))
							array_depositintrepobject[i].setInterestFrq("Half Yearly");
						else if (frq.equalsIgnoreCase("Y"))
							array_depositintrepobject[i].setInterestFrq("Yearly");
						else
							array_depositintrepobject[i].setInterestFrq("On Maturity");
						array_depositintrepobject[i].setInterestUpto(rs_main.getString("int_upto_date"));
						array_depositintrepobject[i].setInterestMode(mode);
						PreparedStatement ps_insert = conn.prepareStatement("insert into DepositTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
						ps_insert.setString(1, actype);
						ps_insert.setInt(2, acno);
						ps_insert.setInt(3, ++lstseq);
						ps_insert.setString(4, todate);
						ps_insert.setString(5, "I");
						ps_insert.setDouble(6, rs_main.getDouble("dep_amt"));
						if (frq.equalsIgnoreCase("M"))
							ps_insert.setDouble(7, interest_monthly);
						else
							ps_insert.setDouble(7, interest);
						ps_insert.setDouble(8, 0);
						ps_insert.setDouble(9, 0);
						ps_insert.setDouble(10, 0);
						ps_insert.setString(11, null);
						ps_insert.setString(12, commonLocal.getFutureDayDate(paydate, -1));
						ps_insert.setInt(13, 0);
						ps_insert.setString(14, "By Int");
						ps_insert.setString(15, "T");
						ps_insert.setString(16, uid);
						ps_insert.setString(17, "C");
						ps_insert.setDouble(18, 0);
						ps_insert.setString(19, utml);
						ps_insert.setString(20, uid);
						ps_insert.setString(21, udate);
						ps_insert.setString(22, utml);
						ps_insert.setString(23, uid);
						ps_insert.setString(24, udate);
						//Credit to account payable gl
						rs_trn = stmt_trn.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+ dptype+ " and gk.ac_type = '"	+ actype+ "' and trn_type='I' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
						if (rs_trn.next()) {
							GLTransObject trnobj = new GLTransObject();
							trnobj.setTrnDate(todate);
							trnobj.setGLType(rs_trn.getString("gk.gl_type"));
							trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
							trnobj.setTrnMode("T");
							if (frq.equalsIgnoreCase("M"))
								trnobj.setAmount(interest_monthly* rs_trn.getInt("mult_by"));
							else
								trnobj.setAmount(interest* rs_trn.getInt("mult_by"));
							trnobj.setCdind("C");
							trnobj.setAccType(actype);
							trnobj.setAccNo(String.valueOf(acno));
							trnobj.setTrnType("I");
							trnobj.setRefNo(0);
							trnobj.setTrnSeq(lstseq);
							trnobj.setVtml(utml);
							trnobj.setVid(uid);
							trnobj.setVDate(udate);
							commonLocal.storeGLTransaction(trnobj);
						}
						dptype = dptype + 2;
						//Debit Loss account
						rs_trn = stmt_trn.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "+ dptype+ " and gk.ac_type = '"+ actype	+ "' and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
						if (rs_trn.next()) {
							GLTransObject trnobj = new GLTransObject();
							trnobj.setTrnDate(todate);
							trnobj.setGLType(rs_trn.getString("gk.gl_type"));
							trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
							trnobj.setTrnMode("T");
							if (frq.equalsIgnoreCase("M"))
								trnobj.setAmount(interest_monthly* rs_trn.getInt("mult_by"));
							else
								trnobj.setAmount(interest* rs_trn.getInt("mult_by"));
							trnobj.setCdind("D");
							trnobj.setAccType(actype);
							trnobj.setAccNo(String.valueOf(acno));
							trnobj.setTrnType("P");
							trnobj.setRefNo(0);
							trnobj.setTrnSeq(lstseq);
							trnobj.setVtml(utml);
							trnobj.setVid(uid);
							trnobj.setVDate(udate);
							commonLocal.storeGLTransaction(trnobj);
						}
						String intpd_dt = rs_main.getString("int_paid_date");
						if (!frq.equalsIgnoreCase("O") && mode != null) {
							intpd_dt = todate;
							ps_insert.setInt(3, ++lstseq);
							ps_insert.setString(5, "P");
							ps_insert.setDouble(7, 0);
							ps_insert.setDouble(8, 0);
							if (frq.equalsIgnoreCase("M"))
								ps_insert.setDouble(9, interest_monthly);
							else
								ps_insert.setDouble(9, interest);
							ps_insert.setString(11, todate);
							ps_insert.setString(12, null);
							if (mode.equals("C")) {
								ps_insert.setString(14, "");
								ps_insert.setString(15, "C");
							} else if (mode.equals("P")) {
								ps_insert.setString(14, "");
								ps_insert.setString(15, "P");
							} else if (mode.equals("T")) {
								ps_insert.setString(14, ractype + " " + racno);
								ps_insert.setString(15, "T");
							}
							ps_insert.setString(17, "D");
							ps_insert.executeUpdate();
							if (mode.equalsIgnoreCase("P")) {
								PayOrderObject po = new PayOrderObject();
								po.setPOType("I");
								po.setPOCustType(0);
								po.setPODate(todate);
								po.setPOPayee(array_depositintrepobject[i].getName());
								po.setPOAccType(actype);
								po.setPOAccNo(acno);
								po.setPOGlCode(0);
								if (frq.equalsIgnoreCase("M"))
									po.setPOAmount(interest_monthly);
								else
									po.setPOAmount(interest);
								po.setCommissionAmount(0);
								po.uv.setUserTml(utml);
								po.uv.setUserId(uid);
								po.uv.setUserDate(udate);
								ret_value = commonLocal.storePayOrder(po);
								rs_trn = stmt_trn.executeQuery("select * from GLKeyParam where ac_type=1016001 and code=1");
								if (rs_trn.next()) {
									GLTransObject trnobj = new GLTransObject();
									trnobj.setTrnDate(todate);
									trnobj.setGLType(rs_trn.getString("gl_type"));
									trnobj.setGLCode(rs_trn.getString("gl_code"));
									trnobj.setTrnMode("T");
									if (frq.equalsIgnoreCase("M"))
										trnobj.setAmount(interest_monthly);
									else
										trnobj.setAmount(interest);
									trnobj.setCdind("C");
									trnobj.setAccType(actype);
									trnobj.setAccNo(String.valueOf(acno));
									trnobj.setTrnType("P");
									trnobj.setRefNo(0);
									trnobj.setTrnSeq(lstseq);
									trnobj.setVtml(utml);
									trnobj.setVid(uid);
									trnobj.setVDate(udate);
									commonLocal.storeGLTransaction(trnobj);
								}
								rs_trn = stmt_trn.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = 3 and gk.ac_type = '"+ actype+ "' and trn_type='I' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
								if (rs_trn.next()) {
									GLTransObject trnobj = new GLTransObject();
									trnobj.setTrnDate(todate);
									trnobj.setGLType(rs_trn.getString("gk.gl_type"));
									trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
									trnobj.setTrnMode("T");
									if (frq.equalsIgnoreCase("M"))
										trnobj.setAmount(interest_monthly* rs_trn.getInt("mult_by"));
									else
										trnobj.setAmount(interest* rs_trn.getInt("mult_by"));
									trnobj.setCdind("D");
									trnobj.setAccType(actype);
									trnobj.setAccNo(String.valueOf(acno));
									trnobj.setTrnType("I");
									trnobj.setRefNo(0);
									trnobj.setTrnSeq(lstseq);
									trnobj.setVtml(utml);
									trnobj.setVid(uid);
									trnobj.setVDate(udate);
									commonLocal.storeGLTransaction(trnobj);
								}
							} else if (mode.equalsIgnoreCase("T")) {
								if (!ractype.equalsIgnoreCase("1007003")) {
									rs_trn = stmt_trn.executeQuery("select moduleabbr from Modules where modulecode='"+ ractype + "'");
									rs_trn.next();
									set_value = rs_trn.getString(1) + " "+ racno;
									AccountTransObject am = new AccountTransObject();
									am.setAccType(ractype);
									am.setAccNo(racno);
									am.setTransDate(getSysDate());
									am.setTransType("R");
									if (frq.equalsIgnoreCase("M"))
										am.setTransAmount(interest_monthly);
									else
										am.setTransAmount(interest);
									am.setTransMode("T");
									am.setTransSource(utml);
									am.setCdInd("C");
									am.setChqDDNo(0);
									am.setChqDDDate("");
									am.setTransNarr(actype + " " + acno);
									am.setRef_No(0);
									am.setPayeeName(array_depositintrepobject[i].getName());
									if (frq.equalsIgnoreCase("M"))
										am.setCloseBal(interest_monthly);
									else
										am.setCloseBal(interest);
									am.setLedgerPage(0);
									am.uv.setUserTml(utml);
									am.uv.setUserId(uid);
									am.uv.setUserDate(udate);
									am.uv.setVerTml(utml);
									am.uv.setVerId(uid);
									am.uv.setVerDate(udate);
									commonLocal.storeAccountTransaction(am);
								}
								rs_trn = stmt_trn.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = 3 and gk.ac_type = '"+ actype+ "' and trn_type='I' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
								if (rs_trn.next()) {
									GLTransObject trnobj = new GLTransObject();
									trnobj.setTrnDate(todate);
									trnobj.setGLType(rs_trn.getString("gk.gl_type"));
									trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
									trnobj.setTrnMode("T");
									if (frq.equalsIgnoreCase("M"))
										trnobj.setAmount(interest_monthly* rs_trn.getInt("mult_by"));
									else
										trnobj.setAmount(interest* rs_trn.getInt("mult_by"));
									trnobj.setCdind("D");
									trnobj.setAccType(actype);
									trnobj.setAccNo(String.valueOf(acno));
									trnobj.setTrnType("I");
									trnobj.setRefNo(0);
									trnobj.setTrnSeq(lstseq);
									trnobj.setVtml(utml);
									trnobj.setVid(uid);
									trnobj.setVDate(udate);
									commonLocal.storeGLTransaction(trnobj);
								}
							} else if (mode.equalsIgnoreCase("C")) {
								TrfVoucherObject trf = new TrfVoucherObject();
								trf.setVoucherType("P");
								trf.setVoucherNo(0);
								if (frq.equalsIgnoreCase("M"))
									trf.setTrfAmount(interest_monthly);
								else
									trf.setTrfAmount(interest);
								trf.setAccType(actype);
								trf.setAccNo(acno);
								trf.setTvPrtInd("F");
								trf.setTvPayInd("F");
								trf.setDDPurInd("F");
								trf.setPayMode("C");
								trf.setTvPayDate(getSysDate());
								trf.setPayAccNo(0);
								trf.setPayAccType("NULL");
								trf.uv.setUserTml(utml);
								trf.uv.setUserId(uid);
								trf.uv.setUserDate(udate);
								ret_value = commonLocal.storeTrfVoucher(trf,getSysDate(), udate);
							}
							if (mode.equals("C")) {
								set_value = "Vch_No" + ret_value;
								stmt_trn.executeUpdate("update DepositTransaction set trn_narr='"+ set_value+ "',ref_no="+ ret_value+ " where ac_type='"+ actype+ "' and ac_no="+ acno+ " and trn_seq=" + lstseq);
							} else if (mode.equals("P")) {
								set_value = "PO No " + ret_value;
								stmt_trn.executeUpdate("update DepositTransaction set trn_narr='"+ set_value+ "',ref_no="+ ret_value+ " where ac_type='"+ actype+ "' and ac_no="+ acno+ " and trn_seq=" + lstseq);
							}
							array_depositintrepobject[i].setNarration(set_value);
						} else {
							ResultSet rs_abbr = null;
							rs_abbr = stmt_trn.executeQuery("select moduleabbr from Modules where modulecode='"	+ ractype + "'");
							if (rs_abbr.next()) {
								array_depositintrepobject[i].setNarration(rs_abbr.getString(1)+ " " + racno);
							}
						}
						stmt_trn.executeUpdate("update DepositMaster set int_paid_date='"+ intpd_dt	+ "',next_pay_date='"+ Validations.nextPayDate(rs_main.getString("int_freq"),paydate, rs_main.getString("mat_date"),commonLocal)+ "',int_upto_date='"+ commonLocal.getFutureDayDate(paydate,-1)+ "',lst_trn_seq="+ lstseq+ " where ac_type='"+ actype+ "' and ac_no=" + acno);
						array_depositintrepobject[i].setInterestUpto(commonLocal.getFutureDayDate(paydate, -1));
						array_depositintrepobject[i].setRefNo(0);
						PreparedStatement ps_commit = conn.prepareStatement("commit");
						ps_commit.execute();
					} catch (SQLException e) {
						e.printStackTrace();
						PreparedStatement ps_rollback = conn.prepareStatement("rollback");
						ps_rollback.execute();
						array_depositintrepobject[i].setState("F");
						array_depositintrepobject[i].setInterestAmt(0);
						array_depositintrepobject[i].setNarration(null);
					} catch (RemoteException e) {
						e.printStackTrace();
						e.printStackTrace();
						PreparedStatement ps_rollback = conn.prepareStatement("rollback");
						ps_rollback.execute();
						array_depositintrepobject[i].setState("F");
						array_depositintrepobject[i].setInterestAmt(0);
						array_depositintrepobject[i].setNarration(null);
					} catch (NamingException e) {
						e.printStackTrace();
						e.printStackTrace();
						PreparedStatement ps_rollback = conn.prepareStatement("rollback");
						ps_rollback.execute();
						array_depositintrepobject[i].setState("F");
						array_depositintrepobject[i].setInterestAmt(0);
						array_depositintrepobject[i].setNarration(null);
					}
					i++;
				}
				stmt_main.executeUpdate("update Modules set lst_intdt='"+ todate + "' where modulecode='1003000'");
				PreparedStatement ps_commit = conn.prepareStatement("commit");
				ps_commit.execute();
			} else if (type == 1) {
				rs_main = stmt_main.executeQuery("select concat_ws(' ',fname,mname,lname) as name,dm.cid,dm.dep_amt,dm.int_rate,dm.int_upto_date,dm.int_freq,dt.* from CustomerMaster as cm,DepositTransaction as dt left join DepositMaster as dm on dt.ac_type=dm.ac_type and dt.ac_no=dm.ac_no where  dt.trn_date='"	+ getSysDate()+ "' and cm.cid=dm.cid and dt.trn_type='I' and dt.ac_type like '1003%' order by dt.ac_no");
				rs_main.last();
				if (rs_main.getRow() == 0)
					return null;
				array_depositintrepobject = new DepositIntRepObject[rs_main.getRow()];
				rs_main.beforeFirst();
				int i = 0;
				while (rs_main.next()){
					array_depositintrepobject[i] = new DepositIntRepObject();
					rs_trn = stmt_trn.executeQuery("select moduleabbr from Modules where modulecode='"+ rs_main.getString("ac_type") + "'");
					if (rs_trn.next()) {
						array_depositintrepobject[i].setAccType(rs_trn.getString("moduleabbr"));
					}
					array_depositintrepobject[i].setAccNo(rs_main.getInt("ac_no"));
					array_depositintrepobject[i].setName(rs_main.getString("name"));
					array_depositintrepobject[i].setDepositAmt(rs_main.getDouble("dep_amt"));
					array_depositintrepobject[i].setInterestFrq(rs_main.getString("int_freq"));
					array_depositintrepobject[i].setInterestRate(rs_main.getDouble("int_rate"));
					array_depositintrepobject[i].setInterestMode(rs_main.getString("trn_mode"));
					array_depositintrepobject[i].setInterestUpto(rs_main.getString("int_upto_date"));
					array_depositintrepobject[i].setInterestAmt(rs_main.getDouble("int_amt"));
					array_depositintrepobject[i].setNarration(rs_main.getString("trn_narr"));
					array_depositintrepobject[i].setState("T");
					i++;
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordsNotFoundException();
		} catch (CreateException ce) {
			ce.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordsNotFoundException();
		} catch (Exception e) {
			e.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordsNotFoundException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			commonLocal = null;
		}
		return array_depositintrepobject;
	}

	public boolean checkQuarter(String code) {
		Connection conn = null;
		try {
			conn = getConnection();
			commonLocal = commonLocalHome.create();
			// sanjeet..
			String today = getSysDate();
			// String today= commonLocal.getSysDate();
			ResultSet rs_prod = null;
			Statement stat_prod = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			if (code.equals("1004000"))
				rs_prod = stat_prod
						.executeQuery("select prod_date from Products ");
			else if (code.equals("1003000"))
				rs_prod = stat_prod
						.executeQuery("select dpdl_date from Products ");
			else
				rs_prod = stat_prod
						.executeQuery("select rinve_prod_date from Products ");
			rs_prod.next();
			if (commonLocal.getDaysFromTwoDate(today, rs_prod.getString(1)) > 0)
				return false;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		} catch (CreateException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			commonLocal = null;
		}
		return true;
	}

	public boolean checkDayCalc(String code) {
		Connection conn = null;
		try {
			conn = getConnection();
			commonLocal = commonLocalHome.create();
			// sanjeet..
			String today = getSysDate();
			// String today= commonLocal.getSysDate();
			ResultSet rs_prod = null;
			Statement stat_prod = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			if (code.equals("1004000"))
				rs_prod = stat_prod
						.executeQuery("select prod_date from Products ");
			else if (code.equals("1003000"))
				rs_prod = stat_prod
						.executeQuery("select dpdl_date from Products ");
			else
				rs_prod = stat_prod
						.executeQuery("select rinve_prod_date from Products ");
			rs_prod.next();
			// if(Validations.dayCompare(today,rs_prod.getString(1)) <= 0 )
			if (commonLocal.getDaysFromTwoDate(today, rs_prod.getString(1)) <= 0) // Changed
				// by
				// Riswan
				return false;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		} catch (CreateException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			commonLocal = null;
		}
		return true;
	}

	/**
	 * ReInvestment Quarterly Interest Calculation No entries in any tables.
	 * 
	 * @throws DateFormatException
	 */
	public DepositIntRepObject[] ReInvestmentQuarterlyIntCalc(int type,
			String uid, String utml, String udate, String combo_date)
			throws RecordsNotFoundException {
		DepositIntRepObject array_depositintrepobject[] = null;
		Connection conn = null;
		/**
		 * Type 0 for interest calculation Type 1 for Report
		 */
		try {
			conn = getConnection();
			commonLocal = commonLocalHome.create();
			Statement stmt_main = conn
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			Statement stmt_trn = conn
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			ResultSet rs_main = null, rs_trn = null;
			if (type == 0) {
				rs_main = stmt_main
						.executeQuery("select rinve_prod_date from Products");
				rs_main.next();
				String quarter_date = rs_main.getString(1);
				// stmt_main.executeUpdate("insert into DQI select * from
				// DepositQuarterlyInterest where ac_type like '1005%' order by
				// ac_no");
				// stmt_main.executeUpdate("delete from DepositQuarterlyInterest
				// where ac_type like '1005%'");
				rs_main = stmt_main
						.executeQuery("select distinct ac_no,dm.int_rate,int_upto_date,next_pay_date,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,dep_amt,mat_amt,ac_type,moduleabbr from Modules,CustomerMaster cm,DepositMaster dm where ac_type like '1005%' and mat_post='N' and close_ind=0 and cm.cid=dm.cid and ac_type=modulecode and concat(right(mat_date,4),'-',mid(mat_date,locate('/',mat_date)+1,(locate('/',mat_date,4)-locate('/',mat_date)-1)),'-',left(mat_date,locate('/',mat_date)-1))>='"
								+ Validations.convertYMD(quarter_date)
								+ "' and concat(right(next_pay_date,4),'-',mid(next_pay_date,locate('/',next_pay_date)+1,(locate('/',next_pay_date,4)-locate('/',next_pay_date)-1)),'-',left(next_pay_date,locate('/',next_pay_date)-1))>'"
								+ Validations.convertYMD(quarter_date)
								+ "' and concat(right(next_pay_date,4),'-',mid(next_pay_date,locate('/',next_pay_date)+1,(locate('/',next_pay_date,4)-locate('/',next_pay_date)-1)),'-',left(next_pay_date,locate('/',next_pay_date)-1)) not in ('"
								+ Validations.convertYMD(quarter_date)
								+ "') order by dm.ac_type,dm.ac_no");
				rs_main.last();
				if (rs_main.getRow() == 0)
					throw new RecordsNotFoundException();
				array_depositintrepobject = new DepositIntRepObject[rs_main
						.getRow()];
				rs_main.beforeFirst();
				int i = 0;
				PreparedStatement pstmt = conn
						.prepareStatement("insert into DepositQuarterlyInterest values('"
								+ getSysDate()
								+ "',?,?,?,?,?,?,?,?,?,?,?,'"
								+ udate + "')");

				while (rs_main.next()) {
					rs_trn = stmt_trn
							.executeQuery("select cum_int from DepositTransaction where ac_type='"
									+ rs_main.getString("ac_type")
									+ "' and ac_no="
									+ rs_main.getInt("ac_no")
									+ " and ve_tml is not null order by trn_seq desc limit 1");
					rs_trn.next();
					double total_amount = rs_main.getDouble("dep_amt")
							+ rs_trn.getDouble("cum_int");
					double rate = rs_main.getDouble("int_rate");
					String int_upto_date = rs_main.getString("int_upto_date");
					// double
					// intamt=Math.round((total_amount*rate*commonLocal.getDaysFromTwoDate(commonLocal.getFutureDayDate(int_upto_date,1),quarter_date))/(36500));
					int days = commonLocal.getDaysFromTwoDate(int_upto_date,
							quarter_date);
					if (days <= 0)
						days = 0;
					long intamt = Math
							.round((total_amount * rate * days) / (36500));
					array_depositintrepobject[i] = new DepositIntRepObject();
					array_depositintrepobject[i].setAccType(rs_main
							.getString("moduleabbr"));
					array_depositintrepobject[i].setAccNo(rs_main
							.getInt("dm.ac_no"));
					array_depositintrepobject[i].setName(rs_main
							.getString("name"));
					array_depositintrepobject[i].setInterestUpto(int_upto_date);
					array_depositintrepobject[i].setDepositAmt(rs_main
							.getDouble("dep_amt"));
					array_depositintrepobject[i].setLastBalance(total_amount);
					array_depositintrepobject[i].setMaturityAmt(rs_main
							.getDouble("mat_amt"));
					array_depositintrepobject[i].setInterestRate(rate);
					array_depositintrepobject[i].setInterestAmt(intamt);

					pstmt.setString(1, rs_main.getString("dm.ac_type"));
					pstmt.setInt(2, array_depositintrepobject[i].getAccNo());
					pstmt.setString(3, array_depositintrepobject[i].getName());
					pstmt.setDouble(4, array_depositintrepobject[i]
							.getDepositAmt());
					pstmt.setDouble(5, array_depositintrepobject[i]
							.getMaturityAmt());
					pstmt.setDouble(6, array_depositintrepobject[i]
							.getInterestRate());
					pstmt.setDouble(7, array_depositintrepobject[i]
							.getLastBalance());
					pstmt.setString(8, array_depositintrepobject[i]
							.getInterestUpto());
					pstmt.setDouble(9, array_depositintrepobject[i]
							.getInterestAmt());
					pstmt.setString(10, utml);
					pstmt.setString(11, uid);
					pstmt.executeUpdate();
					i++;
				}
				rs_main = stmt_main
						.executeQuery("select month from QtrDefinition where qtr_defn='T' order by month");
				StringTokenizer st = new StringTokenizer(quarter_date, "/");
				// String day = st.nextToken();
				int day = Integer.parseInt(st.nextToken());

				int month = Integer.parseInt(st.nextToken());
				int year = Integer.parseInt(st.nextToken());
				boolean check_flag = false;
				month = month % 12;
				while (rs_main.next()) {
					if (month < rs_main.getInt("month"))
						break;
					else if (rs_main.isLast() == true) {
						check_flag = true;
						break;
					} else
						continue;
				}
				if (check_flag) {
					rs_main.first();
					month = rs_main.getInt("month");
					year++;
				} else if (rs_main.isFirst() == true) {
					month = rs_main.getInt("month");
					year++;
				}
				// added by geetha on 24/04/08

				if (month == 1 || month == 3 || month == 5 || month == 7
						|| month == 8 || month == 10 || month == 12) {
					day = 31;
				} else if (month == 4 || month == 6 || month == 9
						|| month == 11) {
					day = 30;
				} else if (month == 2) {
					day = 28;
				}

				else
					month = rs_main.getInt("month");
				quarter_date = Validations.convertYMD(day + "/"
						+ String.valueOf(month) + "/" + String.valueOf(year));
				rs_main = stmt_main
						.executeQuery("select date_format(date_sub(date_add(date_sub('"
								+ quarter_date
								+ "',interval dayofmonth('"
								+ quarter_date
								+ "')-1 day),interval 1 month),interval 1 day),'%d/%m/%Y')");
				rs_main.next();
				stmt_main
						.executeUpdate("update Products set rinve_prod_date ='"
								+ rs_main.getString(1) + "'");
			} else if (type == 1) {
				rs_main = stmt_main
						.executeQuery("select dp.*,moduleabbr from DepositQuarterlyInterest dp,Modules where ac_type=modulecode and ac_type like '1005%' and dp.trn_date='"
								+ combo_date + "' order by ac_type,ac_no");
				rs_main.last();
				if (rs_main.getRow() == 0)
					throw new RecordsNotFoundException();
				array_depositintrepobject = new DepositIntRepObject[rs_main
						.getRow()];
				rs_main.beforeFirst();
				int i = 0;
				while (rs_main.next()) {
					array_depositintrepobject[i] = new DepositIntRepObject();
					array_depositintrepobject[i].setAccType(rs_main
							.getString("moduleabbr"));
					array_depositintrepobject[i].setAccNo(rs_main.getInt(3));
					array_depositintrepobject[i].setName(rs_main.getString(4));
					array_depositintrepobject[i].setDepositAmt(rs_main
							.getDouble(5));
					array_depositintrepobject[i].setMaturityAmt(rs_main
							.getDouble(6));
					array_depositintrepobject[i].setInterestRate(rs_main
							.getDouble(7));
					array_depositintrepobject[i].setLastBalance(rs_main
							.getDouble(8));
					array_depositintrepobject[i].setInterestUpto(rs_main
							.getString(9));
					array_depositintrepobject[i].setInterestAmt(rs_main
							.getDouble(10));
					i++;
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordsNotFoundException();
		} catch (CreateException ce) {
			ce.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordsNotFoundException();
		} catch (Exception e) {
			e.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordsNotFoundException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return array_depositintrepobject;
	}

	/**
	 * Fixed Deposit Quaterly Interest Calculation. No entries in any tables.
	 * 
	 * @throws DateFormatException
	 */

	public DepositIntRepObject[] FDQuarterlyIntCalc(int type, String uid,
			String utml, String udate, String combo_date)
			throws RecordsNotFoundException {
		DepositIntRepObject array_depositintrepobject[] = null;
		Connection conn = null;
		try {

			System.out.println("type-->" + type);
			System.out.println("uid----> " + uid);
			System.out.println("utml---> " + utml);
			System.out.println("udate----->" + udate);
			System.out.println("combo_date-----> " + combo_date);
			System.out.println("Inside FDQuarterlyIntCalc()....");
			conn = getConnection();
			commonLocal = commonLocalHome.create();
			Statement stmt_mian = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rs_mian = null;
			if (type == 0) {
				rs_mian = stmt_mian
						.executeQuery("select dpdl_date from Products");
				rs_mian.next();
				String date = rs_mian.getString(1);
				System.out.println("date-----> " + date);
				// stmt_mian.executeUpdate("insert into DQI select * from
				// DepositQuarterlyInterest where ac_type like '1003%' order by
				// ac_no");
				// stmt_mian.executeUpdate("delete from DepositQuarterlyInterest
				// where ac_type like '1003%'");
				rs_mian = stmt_mian
						.executeQuery("select distinct ac_no,dm.int_rate,int_upto_date,next_pay_date,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,dep_amt,mat_amt,ac_type,moduleabbr from Modules,CustomerMaster cm,DepositMaster dm where ac_type like '1003%' and mat_post='N' and close_ind=0 and cm.cid=dm.cid and ac_type=modulecode and concat(right(mat_date,4),'-',mid(mat_date,locate('/',mat_date)+1,(locate('/',mat_date,4)-locate('/',mat_date)-1)),'-',left(mat_date,locate('/',mat_date)-1))>='"
								+ Validations.convertYMD(date)
								+ "' and concat(right(next_pay_date,4),'-',mid(next_pay_date,locate('/',next_pay_date)+1,(locate('/',next_pay_date,4)-locate('/',next_pay_date)-1)),'-',left(next_pay_date,locate('/',next_pay_date)-1))>'"
								+ Validations.convertYMD(date)
								+ "' and concat(right(next_pay_date,4),'-',mid(next_pay_date,locate('/',next_pay_date)+1,(locate('/',next_pay_date,4)-locate('/',next_pay_date)-1)),'-',left(next_pay_date,locate('/',next_pay_date)-1)) not in ('"
								+ Validations.convertYMD(date)
								+ "') order by dm.ac_no");
				rs_mian.last();
				if (rs_mian.getRow() == 0)
					return null;
				array_depositintrepobject = new DepositIntRepObject[rs_mian
						.getRow()];
				rs_mian.beforeFirst();
				int i = 0;
				// System.out.println("Accno===="+rs_mian.getString("ac_no"));

				PreparedStatement pstmt = conn
						.prepareStatement("insert into DepositQuarterlyInterest values('"
								+ getSysDate()
								+ "',?,?,?,?,?,?,?,?,?,?,?,'"
								+ udate + "')");
				while (rs_mian.next()) {
					double amt = rs_mian.getDouble("dep_amt");
					double rate = rs_mian.getDouble("int_rate");
					String accrdate = rs_mian.getString("int_upto_date");
					System.out.println("int upto date====" + accrdate);
					System.out.println("date=====" + date);
					// int days =
					// commonLocal.getDaysFromTwoDate(commonLocal.getFutureDayDate(accrdate,1),date);
					int days = commonLocal.getDaysFromTwoDate(accrdate, date);
					if (days <= 0)
						days = 0;
					long intamt = Math.round((amt * rate * days) / (36500));

					System.out.println("*****FD Quarterly BEAN values******");
					System.out.println("amount=====" + amt);
					System.out.println("rate=======" + rate);
					System.out.println("days=======" + days);

					System.out.println("quarterly int amt in bean====="
							+ intamt);

					array_depositintrepobject[i] = new DepositIntRepObject();
					array_depositintrepobject[i].setAccType(rs_mian
							.getString("moduleabbr"));
					array_depositintrepobject[i].setAccNo(rs_mian
							.getInt("dm.ac_no"));
					array_depositintrepobject[i].setName(rs_mian
							.getString("name"));
					array_depositintrepobject[i].setInterestUpto(accrdate);
					array_depositintrepobject[i].setDepositAmt(amt);
					array_depositintrepobject[i].setLastBalance(rs_mian
							.getDouble("dep_amt"));
					array_depositintrepobject[i].setMaturityAmt(rs_mian
							.getDouble("mat_amt"));
					array_depositintrepobject[i].setInterestRate(rate);
					array_depositintrepobject[i].setInterestAmt(intamt);

					pstmt.setString(1, rs_mian.getString("dm.ac_type"));
					pstmt.setInt(2, array_depositintrepobject[i].getAccNo());
					pstmt.setString(3, array_depositintrepobject[i].getName());
					pstmt.setDouble(4, array_depositintrepobject[i]
							.getDepositAmt());
					pstmt.setDouble(5, array_depositintrepobject[i]
							.getMaturityAmt());
					pstmt.setDouble(6, array_depositintrepobject[i]
							.getInterestRate());
					pstmt.setDouble(7, array_depositintrepobject[i]
							.getLastBalance());
					pstmt.setString(8, array_depositintrepobject[i]
							.getInterestUpto());
					pstmt.setDouble(9, array_depositintrepobject[i]
							.getInterestAmt());
					pstmt.setString(10, utml);
					pstmt.setString(11, uid);
					pstmt.executeUpdate();
					i++;
				}
				rs_mian = stmt_mian
						.executeQuery("select month from QtrDefinition where qtr_defn='T' order by month");
				StringTokenizer st = new StringTokenizer(date, "/");
				int day = Integer.parseInt(st.nextToken());

				int month = Integer.parseInt(st.nextToken());
				int year = Integer.parseInt(st.nextToken());
				boolean check_flag = false;
				month = month % 12;
				while (rs_mian.next()) {
					if (month < rs_mian.getInt("month"))
						break;
					else if (rs_mian.isLast() == true) {
						check_flag = true;
						break;
					} else
						continue;
				}
				if (check_flag) {
					rs_mian.first();
					month = rs_mian.getInt("month");
					year++;
				} else if (rs_mian.isFirst() == true) {
					month = rs_mian.getInt("month");
					year++;
				} else
					month = rs_mian.getInt("month");
				// added by geetha on 24/04/08

				if (month == 1 || month == 3 || month == 5 || month == 7
						|| month == 8 || month == 10 || month == 12) {
					day = 31;
				} else if (month == 4 || month == 6 || month == 9
						|| month == 11) {
					day = 30;
				} else if (month == 2) {
					day = 28;
				}
				date = Validations.convertYMD(day + "/" + String.valueOf(month)
						+ "/" + String.valueOf(year));
				rs_mian = stmt_mian
						.executeQuery("select date_format(date_sub(date_add(date_sub('"
								+ date
								+ "',interval dayofmonth('"
								+ date
								+ "')-1 day),interval 1 month),interval 1 day),'%d/%m/%Y')");
				rs_mian.next();
				if (stmt_mian.executeUpdate("update Products set dpdl_date ='"
						+ rs_mian.getString(1) + "'") == 0)
					throw new SQLException();
			} else if (type == 1) {
				rs_mian = stmt_mian
						.executeQuery("select dp.*,moduleabbr from DepositQuarterlyInterest dp,Modules where ac_type=modulecode and ac_type like '1003%' and dp.trn_date='"
								+ combo_date + "'");
				rs_mian.last();
				if (rs_mian.getRow() == 0)
					throw new RecordsNotFoundException();
				array_depositintrepobject = new DepositIntRepObject[rs_mian
						.getRow()];
				rs_mian.beforeFirst();
				int i = 0;
				while (rs_mian.next()) {
					array_depositintrepobject[i] = new DepositIntRepObject();
					array_depositintrepobject[i].setAccType(rs_mian
							.getString("moduleabbr"));
					array_depositintrepobject[i].setAccNo(rs_mian.getInt(3));
					array_depositintrepobject[i].setName(rs_mian.getString(4));
					array_depositintrepobject[i].setDepositAmt(rs_mian
							.getDouble(5));
					array_depositintrepobject[i].setMaturityAmt(rs_mian
							.getDouble(6));
					array_depositintrepobject[i].setInterestRate(rs_mian
							.getDouble(7));
					array_depositintrepobject[i].setLastBalance(rs_mian
							.getDouble(8));
					array_depositintrepobject[i].setInterestUpto(rs_mian
							.getString(9));
					array_depositintrepobject[i].setInterestAmt(rs_mian
							.getDouble(10));
					i++;
				}
			}

			System.out.println("Returning from  FDQuarterlyIntCalc()....");

		} catch (SQLException ex) {
			ex.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordsNotFoundException();
		} catch (CreateException ce) {
			ce.printStackTrace();
			sessionContext.setRollbackOnly();
		} catch (Exception e) {
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return (array_depositintrepobject);
	}

	public ArrayList getQuaterlyInterestVector() {
		return vector;
	}

	public byte[] getCustomerPhoto(int cid) throws RemoteException,
			SQLException {
		byte[] imagedata = null;
		Connection conn = null;

		ResultSet res = null;
		System.out.println("In Customerphoto bean " + cid);
		try {
			conn = getConnection();
			Statement stmt_mian = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			res = stmt_mian
					.executeQuery("select photo from CustomerMaster where cid="
							+ cid + " limit 1");
			if (res != null) {
				System.out.println("Res Is not null of photo==>" + res);
				while (res.next()) {
					imagedata = res.getBytes("photo");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return imagedata;
	}

	public String getBOD(int cid) throws RemoteException,
			CustomerNotFoundException, SQLException {
		String bod = null;

		Connection conn = null;
		conn = getConnection();
		System.out.println("Connection is Openend !!!!!!!!!");
		Statement stmt = conn.createStatement();

		System.out.println("Statement Executed !!!!!!!!!!!!!!!!!");

		try {

			ResultSet rs1 = stmt
					.executeQuery("select  dob from CustomerMaster where cid="
							+ cid + " ");

			while (rs1.next()) {

				bod = rs1.getString(1);

				System.out.println("The b'date here is -----" + bod);

			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return bod;
	}

	public String getCustomerAddress(int cid) throws RemoteException,
			CustomerNotFoundException, SQLException {
		String addr = null;

		Connection conn = null;
		conn = getConnection();

		Statement stmt = conn.createStatement();

		try {

			ResultSet rs1 = stmt
					.executeQuery("select  address from CustomerAddr where cid="
							+ cid + " ");

			while (rs1.next()) {

				addr = rs1.getString(1);

			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return addr;

	}

	public int deleteRD(int acno, String acty)
			throws RecordsNotInsertedException {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt
					.executeUpdate("delete from DepositTransaction where trn_type in ('E','L') and ac_no ="
							+ acno + " and ac_type='" + acty + "'");
			stmt
					.executeUpdate("update DepositMaster set close_ind=0 ,exc_amt=0,close_date=null where ac_no="
							+ acno + " and ac_type='" + acty + "'");

		} catch (SQLException exception_delete) {
			exception_delete.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordsNotInsertedException();
		} catch (Exception e) {
			e.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordsNotInsertedException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 1;
	}

	public int submitRD(DepositMasterObject depositMasterObject)
			throws RecordsNotInsertedException {
		ResultSet rs_main = null;
		double extra_amount = 0.0;
		Connection conn = null;
		String rd_ac_ty = depositMasterObject.getAccType();
		int rd_ac_no = depositMasterObject.getAccNo();
		String paymode = depositMasterObject.getTransferType();
		String trf_ac_type = depositMasterObject.getTransferAccType();
		int trf_ac_no = depositMasterObject.getTransferAccno();
		String dtml = depositMasterObject.userverifier.getUserTml();
		String duser = depositMasterObject.userverifier.getUserId();
		String close_date = depositMasterObject.getClosedt();
		String trn_narr = "";
		if (paymode.equals("T"))
			trn_narr = trf_ac_type + " " + trf_ac_no;
		try {
			conn = getConnection();
			commonLocal = commonLocalHome.create();
			Statement stmt_main = conn
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			rs_main = stmt_main
					.executeQuery("select trn_seq,rd_bal,cum_int from DepositTransaction where ac_type = '"
							+ rd_ac_ty
							+ "' and ac_no="
							+ rd_ac_no
							+ " order by trn_seq desc limit 1");
			rs_main.next();
			int trnseq = rs_main.getInt("trn_seq");
			double rd_bal = rs_main.getInt("rd_bal");

			double cum_int = rd_bal
					- depositMasterObject.getDepositAmtReceived();
			/*
			 * if(cum_int == depositMasterObject.getCumInterest())
			 * extra_amount=0.0; else
			 * extra_amount=depositMasterObject.getCumInterest()-cum_int;
			 */
			/**
			 * For Interest
			 */
			PreparedStatement ps_int = conn
					.prepareStatement("insert into DepositTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps_int.setString(1, rd_ac_ty);
			ps_int.setInt(2, rd_ac_no);
			ps_int.setInt(3, ++trnseq);
			ps_int.setString(4, getSysDate());
			ps_int.setString(5, "E");
			ps_int.setDouble(6, 0);
			ps_int.setDouble(7, depositMasterObject.getExcessAmt());
			ps_int.setDouble(8, 0);
			ps_int.setDouble(9, 0);
			ps_int.setDouble(10, rd_bal + depositMasterObject.getExcessAmt());
			ps_int.setString(11, null);
			ps_int.setString(12, commonLocal.getFutureDayDate(close_date, -1));
			ps_int.setInt(13, 0);
			ps_int.setString(14, "By Interest");
			ps_int.setString(15, "T");
			ps_int.setString(16, dtml);
			ps_int.setString(17, "C");
			ps_int.setDouble(18, cum_int + depositMasterObject.getExcessAmt());
			ps_int.setString(19, dtml);
			ps_int.setString(20, duser);
			ps_int
					.setString(21, depositMasterObject.userverifier
							.getUserDate());
			ps_int.setString(22, null);
			ps_int.setString(23, null);
			ps_int.setString(24, null);
			ps_int.executeUpdate();

			// cum_int+= depositMasterObject.getExcessAmt();
			double double_final_dep_amt = depositMasterObject
					.getDepositAmtReceived();
			double double_final_interest_amount = depositMasterObject
					.getCumInterest();

			/**
			 * deposit amount and interest payment and loan payment
			 */
			if (depositMasterObject.getLoanAvailed().equals("T")) {
				double loan_amount = depositMasterObject.getDepositPaid()
						+ depositMasterObject.getInterestPaid();
				if (loan_amount >= double_final_dep_amt
						+ double_final_interest_amount) {
					loan_amount = double_final_dep_amt
							+ double_final_interest_amount;
					double_final_dep_amt = 0;
					double_final_interest_amount = 0;
				} else if (loan_amount <= double_final_interest_amount) {
					double_final_interest_amount = double_final_interest_amount
							- loan_amount;
				} else if (loan_amount <= double_final_interest_amount
						+ double_final_dep_amt) {
					double_final_dep_amt = (double_final_dep_amt + double_final_interest_amount)
							- loan_amount;
					double_final_interest_amount = 0;
				}
				if (loan_amount > 0) {
					ps_int.setString(1, rd_ac_ty);
					ps_int.setInt(2, rd_ac_no);
					ps_int.setInt(3, ++trnseq);
					ps_int.setString(4, getSysDate());
					ps_int.setString(5, "L");
					ps_int.setDouble(6, 0);
					ps_int.setDouble(7, 0);
					ps_int.setDouble(8, loan_amount);
					ps_int.setDouble(9, 0);
					ps_int.setDouble(10, 0);
					ps_int.setString(11, getSysDate());
					ps_int.setString(12, null);
					ps_int.setInt(13, 0);
					ps_int.setString(14, depositMasterObject.getLoanAcType()
							+ " " + depositMasterObject.getLoanAccno());
					ps_int.setString(15, "T");
					ps_int.setString(16, dtml);
					ps_int.setString(17, "D");
					ps_int.setDouble(18, 0);
					ps_int.setString(19, dtml);
					ps_int.setString(20, duser);
					ps_int.setString(21, depositMasterObject.userverifier
							.getUserDate());
					ps_int.setString(22, null);
					ps_int.setString(23, null);
					ps_int.setString(24, null);
					ps_int.executeUpdate();
				}
				if (double_final_dep_amt > 0) {
					ps_int.setString(1, rd_ac_ty);
					ps_int.setInt(2, rd_ac_no);
					ps_int.setInt(3, ++trnseq);
					ps_int.setString(4, getSysDate());
					ps_int.setString(5, "E");
					ps_int.setDouble(6, 0);
					ps_int.setDouble(7, 0);
					ps_int.setDouble(8, double_final_dep_amt);
					ps_int.setDouble(9, double_final_interest_amount);
					ps_int.setDouble(10, 0);
					ps_int.setString(11, getSysDate());
					ps_int.setString(12, null);
					ps_int.setInt(13, 0);
					ps_int.setString(14, trn_narr);
					ps_int.setString(15, paymode);
					ps_int.setString(16, dtml);
					ps_int.setString(17, "D");
					ps_int.setDouble(18, 0);
					ps_int.setString(19, dtml);
					ps_int.setString(20, duser);
					ps_int.setString(21, depositMasterObject.userverifier
							.getUserDate());
					ps_int.setString(22, null);
					ps_int.setString(23, null);
					ps_int.setString(24, null);
					ps_int.executeUpdate();
				}
			} else {
				ps_int.setString(1, rd_ac_ty);
				ps_int.setInt(2, rd_ac_no);
				ps_int.setInt(3, ++trnseq);
				ps_int.setString(4, getSysDate());
				ps_int.setString(5, "E");
				ps_int.setDouble(6, 0);
				ps_int.setDouble(7, 0);
				ps_int.setDouble(8, double_final_dep_amt);
				ps_int.setDouble(9, double_final_interest_amount);
				ps_int.setDouble(10, 0);
				ps_int.setString(11, getSysDate());
				ps_int.setString(12, null);
				ps_int.setInt(13, 0);
				ps_int.setString(14, trn_narr);
				ps_int.setString(15, paymode);
				ps_int.setString(16, dtml);
				ps_int.setString(17, "D");
				ps_int.setDouble(18, 0);
				ps_int.setString(19, dtml);
				ps_int.setString(20, duser);
				ps_int.setString(21, depositMasterObject.userverifier
						.getUserDate());
				ps_int.setString(22, null);
				ps_int.setString(23, null);
				ps_int.setString(24, null);
				ps_int.executeUpdate();
			}
			stmt_main.executeUpdate("update DepositMaster set close_ind='"
					+ depositMasterObject.getCloseInd() + "',exc_amt="
					+ depositMasterObject.getExcessAmt() + ",close_date='"
					+ getSysDate() + "' where ac_type='" + rd_ac_ty
					+ "' and ac_no = " + rd_ac_no);
		} catch (SQLException we) {
			we.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordsNotInsertedException();
		} catch (CreateException e) {
			e.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordsNotInsertedException();
		} catch (Exception ex) {
			ex.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordsNotInsertedException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			commonLocal = null;
		}
		return 1;
	}

	/**
	 * Get details for RD closure.
	 */
	public DepositMasterObject[] getClosureDetails(int acno, String type,
			boolean flag) throws RecordsNotFoundException,
			AccountNotFoundException {
		DepositMasterObject dm[] = new DepositMasterObject[1];
		Connection conn = null;
		ResultSet rs_main = null, rs_trn = null, rs_extra = null;
		Statement stmt_main, stmt_trn, stmt_extra;
		int close_ind = 0;

		System.out.println("geetha in bean class..." + acno);

		try {
			conn = getConnection();
			commonLocal = commonLocalHome.create();
			stmt_main = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			stmt_trn = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			stmt_extra = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			if (flag == true)
				rs_main = stmt_main
						.executeQuery("select concat(ifnull(cm.fname,' '),' ',ifnull(cm.mname,' '),' ',ifnull(cm.lname,' ')) as name,dm.*,IFNULL(dm.ve_user,'') as veuser,dt.dep_paid,dt.int_paid,custtype,penalty_rate,sub_category,trn_narr,dm.ln_availed,dm.ln_ac_type,dm.ln_ac_no,trn_mode from DepositMaster dm,CustomerMaster cm, DepositTransaction dt,Modules where dm.ac_type=modulecode and dm.cid=cm.cid and dm.ac_type='"
								+ type
								+ "' and dm.ac_no="
								+ acno
								+ " and dt.ac_type='"
								+ type
								+ "' and dt.ac_no="
								+ acno
								+ "  order by dt.trn_seq desc limit 1");
			else
				rs_main = stmt_main
						.executeQuery("select concat(ifnull(cm.fname,' '),' ',ifnull(cm.mname,' '),' ',ifnull(cm.lname,' ')) as name,dm.*,IFNULL(dm.ve_user,'') as veuser,(dt.rd_bal-dt.cum_int) as total_dep_amt,penalty_rate,custtype,sub_category,dm.ln_availed,dm.ln_ac_type,dm.ln_ac_no,trn_narr,trn_mode  from DepositMaster dm,CustomerMaster cm, DepositTransaction dt,Modules where dm.ac_type=modulecode and dm.cid=cm.cid and dm.ac_type='"
								+ type
								+ "' and dm.ac_no="
								+ acno
								+ " and dt.ac_type='"
								+ type
								+ "' and dt.ac_no="
								+ acno
								+ " and dt.trn_type not in ('E','L') order by dt.trn_seq desc limit 1");
			dm[0] = new DepositMasterObject();
			rs_main.last();
			if (rs_main.getRow() > 0) {
				rs_main.first();
				close_ind = rs_main.getInt("close_ind");
				if (close_ind == 0 || close_ind > 6) {
					if (flag == false) {
						rs_trn = stmt_trn
								.executeQuery("select trn_date,dep_amt from DepositTransaction where ac_type='"
										+ type
										+ "' and ac_no ="
										+ acno
										+ " and trn_type = 'D' order by trn_seq");
						rs_trn.last();
						if (rs_trn.getRow() == 0)
							throw new RecordsNotFoundException();
						dm = new DepositMasterObject[rs_trn.getRow() + 1];
						rs_trn.beforeFirst();
					}
					dm[0] = new DepositMasterObject();
					dm[0].setAccNo(acno);
					dm[0].setAccType(type);
					dm[0].setName(rs_main.getString("name"));
					dm[0].setDepDate(rs_main.getString("dep_date"));
					dm[0].setDepositAmt(rs_main.getDouble("dep_amt"));
					dm[0].setMaturityAmt(rs_main.getDouble("mat_amt"));
					dm[0].setInterestRate(rs_main.getDouble("int_rate"));
					dm[0].setMaturityDate(rs_main.getString("mat_date"));
					dm[0].setNomineeRegNo(rs_main.getInt("nom_no"));
					dm[0].setVerified(rs_main.getString("veuser"));
					dm[0].setLoanAvailed(rs_main.getString("dm.ln_availed"));
					dm[0].setLoanAcType(rs_main.getString("dm.ln_ac_type"));
					dm[0].setLoanAccno(rs_main.getInt("dm.ln_ac_no"));

					rs_extra = stmt_extra
							.executeQuery("select trn_mode,trn_narr from DepositTransaction where ac_type='"
									+ type
									+ "' and ac_no ="
									+ acno
									+ " order by trn_seq desc limit 1");
					rs_extra.next();
					dm[0].setTransferAccType(rs_extra.getString("trn_narr"));
					dm[0].setTransferType(rs_extra.getString("trn_mode"));
					if (flag == false) {
						dm[0].setRDBalance(rs_main.getDouble("total_dep_amt"));
						dm[0].setDepositDays(rs_main.getInt("dep_days"));
						dm[0].setInterestMode(rs_main.getString("int_mode"));
						dm[0].setTransferAccType(rs_main
								.getString("trf_ac_type"));
						dm[0].setTransferAccno(rs_main.getInt("trf_ac_no"));
						dm[0].setExtraRateType(rs_main
								.getInt("extra_rate_type"));
						dm[0].setDPType(rs_main.getInt("custtype"));
						dm[0].setCategory(rs_main.getInt("sub_category"));
						dm[0].setNoofJtHldrs(rs_main.getInt("no_jt_hldr"));
						dm[0].setCustomerId(rs_main.getInt("dm.cid"));
						dm[0].setMailingAddress(rs_main.getInt("add_type"));
						dm[0]
								.setIntroAccType(rs_main
										.getString("intr_ac_type"));
						dm[0].setIntroAccno(rs_main.getInt("intr_ac_no"));
					} else {
						dm[0].setDepositPaid(rs_main.getDouble("dep_paid"));
						dm[0].setInterestPaid(rs_main.getDouble("int_paid"));
						dm[0].setInterestMode(rs_main.getString("int_mode"));
						dm[0].setTransferAccType(rs_main.getString("trn_narr"));
						dm[0].setTransferAccno(rs_main.getInt("trf_ac_no"));
						dm[0].setExtraRateType(rs_main
								.getInt("extra_rate_type"));
						dm[0].setDPType(rs_main.getInt("custtype"));
						dm[0].setCategory(rs_main.getInt("sub_category"));
						dm[0].setNoofJtHldrs(rs_main.getInt("no_jt_hldr"));
						dm[0].setCustomerId(rs_main.getInt("dm.cid"));
						dm[0].setMailingAddress(rs_main.getInt("add_type"));
						dm[0]
								.setIntroAccType(rs_main
										.getString("intr_ac_type"));
						dm[0].setIntroAccno(rs_main.getInt("intr_ac_no"));
					}
					dm[0].setCloseInd(rs_main.getInt("close_ind"));
					dm[0].setPenaltyRate(rs_main.getDouble("penalty_rate"));
					rs_main = stmt_main
							.executeQuery("select cid,addr_type from JointHolders where ac_type='"
									+ dm[0].getAccType()
									+ "' and ac_no='"
									+ dm[0].getAccNo() + "'");
					int j = 0;
					int addrtype[] = null;
					int cids[] = null;
					if (rs_main.next()) {
						rs_main.last();
						addrtype = new int[rs_main.getRow()];
						cids = new int[rs_main.getRow()];
						rs_main.beforeFirst();
						while (rs_main.next()) {
							addrtype[j] = rs_main.getInt(2);
							cids[j++] = rs_main.getInt(1);
						}
					}

					// Code added by sanjeet..
					rs_main = stmt_main
							.executeQuery("select sum(int_paid),max(cum_int) from DepositTransaction where ac_type='"
									+ type
									+ "' and ac_no="
									+ acno
									+ "  and trn_type != 'E' and trn_type != 'L'");
					if (rs_main.next()) {
						dm[0].setInterestPaid(rs_main.getDouble(1));
						dm[0].setCumInterest(rs_main.getDouble(2));
					}
					rs_main = stmt_main
							.executeQuery("select trn_mode,trn_narr from DepositTransaction where ac_type='"
									+ type
									+ "' and ac_no="
									+ acno
									+ "  order by trn_seq desc limit 1");
					rs_main.last();
					if (rs_main.getRow() > 0) {
						rs_main.first();
						dm[0].setTransferType(rs_main.getString("trn_mode"));
						if (rs_main.getString("trn_mode").equalsIgnoreCase("T")) {
							dm[0].setTransferAccType(rs_main
									.getString("trn_narr"));
						}
					}
					dm[0].setJointAddrType(addrtype);
					dm[0].setJointCid(cids);
					dm[0].setNomineeDetails(commonLocal.getNominee(dm[0]
							.getNomineeRegNo()));
					dm[0].setSigObj(commonLocal.getSignatureDetails(dm[0]
							.getAccNo(), dm[0].getAccType()));
					if (flag == false) {
						int i = 1;
						while (rs_trn.next()) {
							dm[i] = new DepositMasterObject();
							dm[i].setTranDate(rs_trn.getString("trn_date"));
							dm[i].setTranAmt(rs_trn.getDouble("dep_amt"));
							i++;
						}
						rs_trn.close();
					}
					rs_main.close();
				} else
					dm[0].setCloseInd(close_ind);
			} else
				// throw new RecordsNotFoundException();
				return null;

		} catch (SQLException ee) {
			ee.printStackTrace();
			throw new AccountNotFoundException();
		} catch (CreateException e) {
			e.printStackTrace();
			throw new AccountNotFoundException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			commonLocal = null;
		}
		return dm;
	}

	/**
	 * Verification for RD
	 * 
	 * @throws DateFormatException
	 * @throws RecordsNotInsertedException
	 * @throws FreezedAccountException
	 * @throws InOperativeAccountException
	 * @throws AccountClosedException
	 */
	public int verifiedRD(DepositMasterObject dm)
			throws RecordsNotInsertedException {
		String vtml = dm.userverifier.getVerTml();
		String vuser = dm.userverifier.getVerId();
		int closeind = 0, dpcattype = 0, modcode = 0, olddpcat = 0;
		Connection conn = null;
		ResultSet rs_main = null, rs_trn = null;
		Statement stmt_main = null, stmt_trn = null;
		GLTransObject trnobj = null;
		int int_return_value = 0;
		try {
			if (dm.getCloseInd() == 90)
				closeind = 1;
			else if (dm.getCloseInd() == 91)
				closeind = 2;
			else if (dm.getCloseInd() == 92)
				closeind = 3;
			else if (dm.getCloseInd() == 93)
				closeind = 4;

			double dep_amount = dm.getDepositAmtReceived();
			double interest_amount = dm.getCumInterest();

			conn = getConnection();
			commonLocal = commonLocalHome.create();
			stmt_main = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			stmt_trn = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			dpcattype = dm.getDPType();
			olddpcat = dpcattype;
			modcode = Integer.parseInt(dm.getAccType());
			rs_main = stmt_main
					.executeQuery("select int_amt,trn_seq,dep_paid,int_paid,trn_type from DepositTransaction where ac_type='"
							+ dm.getAccType()
							+ "' and ac_no= "
							+ dm.getAccNo()
							+ " and trn_type in ('E','L') order by trn_seq");
			rs_main.next();

			if (rs_main.getDouble("int_amt") >= 0) {
				if (dpcattype == 0)
					dpcattype = 3;
				else
					dpcattype = 4;
				/**
				 * credit to rd gl
				 */
				rs_trn = stmt_trn
						.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
								+ dpcattype
								+ " and gk.ac_type = "
								+ modcode
								+ " and trn_type='I' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
				if (rs_trn.next()) {
					trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_trn.getString("gk.gl_type"));
					trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
					trnobj.setTrnMode("T");
					trnobj.setAmount(rs_main.getDouble("int_amt")
							* rs_trn.getInt("mult_by"));
					trnobj.setCdind("C");
					trnobj.setAccType(dm.getAccType());
					trnobj.setAccNo(String.valueOf(dm.getAccNo()));
					trnobj.setTrnType("I");
					trnobj.setRefNo(0);
					trnobj.setTrnSeq(rs_main.getInt("trn_seq"));
					trnobj.setVtml(vtml);
					trnobj.setVid(vuser);
					trnobj.setVDate(dm.userverifier.getVerDate());
					commonLocal.storeGLTransaction(trnobj);
				}

				dpcattype = dpcattype + 2;
				/**
				 * Debit loss account
				 */
				rs_trn = stmt_trn
						.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
								+ dpcattype
								+ " and gk.ac_type = "
								+ modcode
								+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
				if (rs_trn.next()) {
					trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_trn.getString("gk.gl_type"));
					trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
					trnobj.setTrnMode("T");
					trnobj.setAmount(rs_main.getDouble("int_amt")
							* rs_trn.getInt("mult_by"));
					trnobj.setCdind("D");
					trnobj.setAccType(dm.getAccType());
					trnobj.setAccNo(String.valueOf(dm.getAccNo()));
					trnobj.setTrnType("P");
					trnobj.setRefNo(0);
					trnobj.setTrnSeq(rs_main.getInt("trn_seq"));
					trnobj.setVtml(vtml);
					trnobj.setVid(vuser);
					trnobj.setVDate(dm.userverifier.getVerDate());
					commonLocal.storeGLTransaction(trnobj);
				}
			} else {
				if (dpcattype == 0)
					dpcattype = 3;
				else
					dpcattype = 4;
				/**
				 * Debit RD gl
				 */
				rs_trn = stmt_trn
						.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
								+ dpcattype
								+ " and gk.ac_type = "
								+ modcode
								+ " and trn_type='I' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
				if (rs_trn.next()) {
					trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_trn.getString("gk.gl_type"));
					trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
					trnobj.setTrnMode("T");
					trnobj.setAmount(rs_main.getDouble("int_amt") * (-1));
					trnobj.setCdind("D");
					trnobj.setAccType(dm.getAccType());
					trnobj.setAccNo(String.valueOf(dm.getAccNo()));
					trnobj.setTrnType("P");
					trnobj.setRefNo(0);
					trnobj.setTrnSeq(rs_main.getInt("trn_seq"));
					trnobj.setVtml(vtml);
					trnobj.setVid(vuser);
					trnobj.setVDate(dm.userverifier.getVerDate());
					commonLocal.storeGLTransaction(trnobj);
				}

				dpcattype = dpcattype + 4;
				/**
				 * credit Profit account or Interest payable
				 */
				rs_trn = stmt_trn
						.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
								+ dpcattype
								+ " and gk.ac_type = "
								+ modcode
								+ " and trn_type='P' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
				if (rs_trn.next()) {
					trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_trn.getString("gk.gl_type"));
					trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
					trnobj.setTrnMode("T");
					trnobj.setAmount(rs_main.getDouble("int_amt")
							* rs_trn.getInt("mult_by"));
					trnobj.setCdind("C");
					trnobj.setAccType(dm.getAccType());
					trnobj.setAccNo(String.valueOf(dm.getAccNo()));
					trnobj.setTrnType("I");
					trnobj.setRefNo(0);
					trnobj.setTrnSeq(rs_main.getInt("trn_seq"));
					trnobj.setVtml(vtml);
					trnobj.setVid(vuser);
					trnobj.setVDate(dm.userverifier.getVerDate());
					commonLocal.storeGLTransaction(trnobj);
				}
			}
			stmt_trn
					.executeUpdate("update DepositTransaction set trn_type='I',ve_tml='"
							+ vtml
							+ "',ve_user='"
							+ vuser
							+ "',ve_date ='"
							+ dm.userverifier.getVerDate()
							+ "' where trn_type='E' and trn_seq ="
							+ rs_main.getInt("trn_seq")
							+ " and ac_type='"
							+ dm.getAccType() + "' and ac_no=" + dm.getAccNo());

			int trnseq = 0;
			rs_main.next();

			if (rs_main.isLast()) {
				trnseq = rs_main.getInt("trn_seq");
				dpcattype = olddpcat;
				if (dpcattype == 0)
					dpcattype = 1;
				else
					dpcattype = 2;
				if (rs_main.getString("trn_type").equals("L")) {
					/**
					 * credit to loan account loan recovery gl
					 */
					rs_trn = stmt_trn
							.executeQuery("select max(trn_seq) from LoanTransaction where ac_type='"
									+ dm.getLoanAcType()
									+ "' and ac_no="
									+ dm.getLoanAccno());
					rs_trn.next();
					int ln_trn_seq = rs_trn.getInt(1) + 1;
					PreparedStatement pstmt = conn
							.prepareStatement("insert into LoanTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					pstmt.setString(1, dm.getLoanAcType());
					pstmt.setInt(2, dm.getLoanAccno());
					pstmt.setInt(3, ln_trn_seq);
					pstmt.setString(4, getSysDate());
					pstmt.setString(5, "R");
					pstmt.setDouble(6, rs_main.getDouble("dep_paid"));
					pstmt.setString(7, "T");
					pstmt.setString(8, vtml);
					pstmt.setString(9, null);
					pstmt.setString(10, dm.getAccType() + " " + dm.getAccNo());
					pstmt.setString(11, null);
					pstmt.setString(12, "C");
					pstmt.setString(13, commonLocal.getFutureDayDate(
							getSysDate(), -1));
					pstmt.setDouble(14, rs_main.getDouble("dep_paid")
							- dm.getInterestPaid());
					pstmt.setDouble(15, dm.getInterestPaid());
					pstmt.setDouble(16, 0);
					pstmt.setDouble(17, 0);
					pstmt.setDouble(18, 0);
					pstmt.setDouble(19, 0);
					pstmt.setString(20, vtml);
					pstmt.setString(21, vuser);
					pstmt.setString(22, dm.userverifier.getVerDate());
					pstmt.setString(23, vtml);
					pstmt.setString(24, vuser);
					pstmt.setString(25, dm.userverifier.getVerDate());
					pstmt.executeUpdate();

					dpcattype = 1;
					rs_trn = stmt_trn
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type ='"
									+ dm.getLoanAcType()
									+ "' and trn_type='R' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					if (rs_trn.next()) {
						// trnobj=new GLTransObject();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_trn.getString("gk.gl_type"));
						trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
						trnobj.setTrnMode("T");
						trnobj.setAmount((rs_main.getDouble("dep_paid") - dm
								.getInterestPaid())
								* rs_trn.getInt("mult_by"));
						trnobj.setCdind("C");
						trnobj.setAccType(dm.getLoanAcType());
						trnobj.setAccNo(String.valueOf(dm.getLoanAccno()));
						trnobj.setTrnType("R");
						trnobj.setRefNo(0);
						trnobj.setTrnSeq(ln_trn_seq);
						trnobj.setVtml(vtml);
						trnobj.setVid(vuser);
						trnobj.setVDate(dm.userverifier.getVerDate());
						commonLocal.storeGLTransaction(trnobj);
					}

					dpcattype = 2;
					rs_trn = stmt_trn
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type ='"
									+ dm.getLoanAcType()
									+ "' and trn_type='R' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					if (rs_trn.next()) {
						trnobj = new GLTransObject();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_trn.getString("gk.gl_type"));
						trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
						trnobj.setTrnMode("T");
						trnobj.setAmount((dm.getInterestPaid())
								* rs_trn.getInt("mult_by"));
						trnobj.setCdind("C");
						trnobj.setAccType(dm.getLoanAcType());
						trnobj.setAccNo(String.valueOf(dm.getLoanAccno()));
						trnobj.setTrnType("R");
						trnobj.setRefNo(0);
						trnobj.setTrnSeq(ln_trn_seq);
						trnobj.setVtml(vtml);
						trnobj.setVid(vuser);
						trnobj.setVDate(dm.userverifier.getVerDate());
						commonLocal.storeGLTransaction(trnobj);
					}

					stmt_trn.executeUpdate("update LoanMaster set close_date='"
							+ getSysDate() + "' where ac_type='"
							+ dm.getLoanAcType() + "' and ac_no="
							+ dm.getLoanAccno());

					dpcattype = olddpcat;
					if (dpcattype == 0)
						dpcattype = 1;
					else
						dpcattype = 2;
					rs_trn = stmt_trn
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type = "
									+ modcode
									+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					if (rs_trn.next()) {
						trnobj = new GLTransObject();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_trn.getString("gk.gl_type"));
						trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
						trnobj.setTrnMode("T");
						trnobj.setAmount((dep_amount)
								* rs_trn.getInt("mult_by"));
						trnobj.setCdind("D");
						trnobj.setAccType(dm.getAccType());
						trnobj.setAccNo(String.valueOf(dm.getAccNo()));
						trnobj.setTrnType("P");
						trnobj.setRefNo(0);
						trnobj.setTrnSeq(trnseq);
						trnobj.setVtml(vtml);
						trnobj.setVid(vuser);
						trnobj.setVDate(dm.userverifier.getVerDate());
						commonLocal.storeGLTransaction(trnobj);
					}

					dpcattype += 2;
					rs_trn = stmt_trn
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type = "
									+ modcode
									+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					if (rs_trn.next()) {
						trnobj = new GLTransObject();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_trn.getString("gk.gl_type"));
						trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
						trnobj.setTrnMode("T");
						trnobj.setAmount((interest_amount)
								* rs_trn.getInt("mult_by"));
						trnobj.setCdind("D");
						trnobj.setAccType(dm.getAccType());
						trnobj.setAccNo(String.valueOf(dm.getAccNo()));
						trnobj.setTrnType("P");
						trnobj.setRefNo(0);
						trnobj.setTrnSeq(trnseq);
						trnobj.setVtml(vtml);
						trnobj.setVid(vuser);
						trnobj.setVDate(dm.userverifier.getVerDate());
						commonLocal.storeGLTransaction(trnobj);
					}

					stmt_trn
							.executeUpdate("update DepositTransaction set trn_type='P',ve_tml='"
									+ vtml
									+ "',ve_user='"
									+ vuser
									+ "',ve_date = '"
									+ dm.userverifier.getVerDate()
									+ "' where trn_type='L' and ac_type='"
									+ dm.getAccType()
									+ "' and ac_no="
									+ dm.getAccNo());
				} else {
					if (dm.getTransferType().equalsIgnoreCase("P")) {
						PayOrderObject po = new PayOrderObject();
						po.setPOType("P");
						po.setPOCustType(0);
						po.setPOPayee(dm.getName());
						po.setPOAccType(dm.getAccType());
						po.setPOAccNo(dm.getAccNo());
						po.setPOGlCode(0);
						po.setPOAmount(rs_main.getDouble("dep_paid")
								+ rs_main.getDouble("int_paid"));
						po.setCommissionAmount(0);
						po.uv.setUserTml(vtml);
						po.uv.setUserId(vuser);
						po.uv.setUserDate(dm.userverifier.getUserDate());
						po.uv.setVerTml(vtml);
						po.uv.setVerId(vuser);
						po.uv.setVerDate(dm.userverifier.getVerDate());
						int_return_value = commonLocal.storePayOrder(po);
						stmt_trn
								.executeUpdate("update DepositTransaction set ref_no="
										+ int_return_value
										+ ",trn_narr='By PO,PO no="
										+ int_return_value
										+ "' where trn_type='E' and ac_type='"
										+ dm.getAccType()
										+ "' and ac_no="
										+ dm.getAccNo()
										+ " and trn_seq="
										+ trnseq);
						/**
						 * PayOrder Credit
						 */
						rs_trn = stmt_trn
								.executeQuery("select * from GLKeyParam where ac_type=1016001 and code=1");
						if (rs_trn.next()) {
							// trnobj=new GLTransObject();
							trnobj.setTrnDate(getSysDate());
							trnobj.setGLType(rs_trn.getString("gl_type"));
							trnobj.setGLCode(rs_trn.getString("gl_code"));
							trnobj.setTrnMode("T");
							trnobj
									.setAmount((rs_main.getDouble("dep_paid") + rs_main
											.getDouble("int_paid")));
							trnobj.setCdind("C");
							trnobj.setAccType(dm.getAccType());
							trnobj.setAccNo(String.valueOf(dm.getAccNo()));
							trnobj.setTrnType("P");
							trnobj.setRefNo(0);
							trnobj.setTrnSeq(trnseq);
							trnobj.setVtml(vtml);
							trnobj.setVid(vuser);
							trnobj.setVDate(dm.userverifier.getVerDate());
							commonLocal.storeGLTransaction(trnobj);
						}
					} else if (dm.getTransferType().equalsIgnoreCase("T")) {
						AccountTransObject am = new AccountTransObject();
						/*
						 * StringTokenizer st = new
						 * StringTokenizer(dm.getTransferType()," "); String
						 * accty = st.nextToken(); int accno=
						 * Integer.parseInt(st.nextToken());
						 */
						am.setAccType(dm.getTransferAccType());
						am.setAccNo(dm.getTransferAccno());
						am.setTransDate(getSysDate());
						am.setTransType("R");
						am.setTransAmount(rs_main.getDouble("dep_paid")
								+ rs_main.getDouble("int_paid"));
						am.setTransMode("T");
						am.setTransSource(vtml);
						am.setCdInd("C");
						am.setChqDDNo(0);
						am.setChqDDDate("");
						am.setTransNarr(dm.getAccType() + " "
								+ String.valueOf(dm.getAccNo()));
						am.setRef_No(0);
						am.setPayeeName(dm.getName());
						am.setCloseBal(rs_main.getDouble("dep_paid")
								+ rs_main.getDouble("int_paid"));
						am.setLedgerPage(0);
						am.uv.setUserTml(vtml);
						am.uv.setUserId(vuser);
						am.uv.setUserDate(dm.userverifier.getUserDate());
						am.uv.setVerTml(vtml);
						am.uv.setVerId(vuser);
						am.uv.setVerDate(dm.userverifier.getVerDate());
						int_return_value = commonLocal
								.storeAccountTransaction(am);

						stmt_trn
								.executeUpdate("update DepositTransaction set trn_narr='"
										+ dm.getTransferAccType()
										+ " "
										+ dm.getTransferAccno()
										+ "' where ac_type='"
										+ dm.getAccType()
										+ "' and ac_no="
										+ dm.getAccNo()
										+ " and trn_seq="
										+ rs_main.getInt("trn_seq"));
					} else if (dm.getTransferType().equalsIgnoreCase("C")) {
						int_return_value = commonLocal.getModulesColumn(
								"lst_voucher_scroll_no", "1019000");

						PreparedStatement pstmt_insert = conn
								.prepareStatement("insert into DayCash (scroll_no,trn_type,trn_date,ac_type,ac_no,csh_amt,cd_ind,vch_type,attached,vch_no,de_user,de_tml,de_date,ve_user,ve_tml,ve_date,name) values (0,'P',?,?,?,?,'C','P','F',?,?,?,?,?,?,?,?)");
						pstmt_insert.setString(1, getSysDate());
						pstmt_insert.setString(2, dm.getAccType());
						pstmt_insert.setInt(3, dm.getAccNo());
						pstmt_insert.setDouble(4, rs_main.getDouble("dep_paid")
								+ rs_main.getDouble("int_paid"));
						pstmt_insert.setInt(5, int_return_value);
						pstmt_insert.setString(6, vuser);
						pstmt_insert.setString(7, vtml);
						pstmt_insert.setString(8, dm.userverifier.getVerDate());
						pstmt_insert.setString(9, vuser);
						pstmt_insert.setString(10, vtml);
						pstmt_insert
								.setString(11, dm.userverifier.getVerDate());
						pstmt_insert.setString(12, dm.getName());
						pstmt_insert.executeUpdate();
						stmt_trn
								.executeUpdate("update DepositTransaction set ref_no="
										+ int_return_value
										+ ",trn_narr='By Cash vch no"
										+ int_return_value
										+ "' where ac_type='"
										+ dm.getAccType()
										+ "' and ac_no="
										+ dm.getAccNo()
										+ " and trn_seq="
										+ rs_main.getInt("trn_seq"));
						/**
						 * cash payment gl
						 */
						// rs_trn = stmt_trn.executeQuery("select
						// gk.gl_code,mult_by,gk.gl_type from GLKeyParam
						// gk,GLPost gp where code = 1 and gk.ac_type = 1999001
						// and trn_type='P' and cr_dr='C' and gk.ac_type =
						// gp.ac_type and gp.gl_code = gk.gl_code");
						rs_trn = stmt_trn
								.executeQuery("select * from GLKeyParam where ac_type=1019001 and code=1");
						if (rs_trn.next()) {
							trnobj = new GLTransObject();
							trnobj.setTrnDate(getSysDate());
							trnobj.setGLType(rs_trn.getString("gl_type"));
							trnobj.setGLCode(rs_trn.getString("gl_code"));
							trnobj.setTrnMode("C");
							trnobj
									.setAmount((rs_main.getDouble("dep_paid") + rs_main
											.getDouble("int_paid")));
							trnobj.setCdind("C");
							trnobj.setAccType(dm.getAccType());
							trnobj.setAccNo(String.valueOf(dm.getAccNo()));
							trnobj.setTrnType("P");
							trnobj.setRefNo(0);
							trnobj.setTrnSeq(trnseq);
							trnobj.setVtml(vtml);
							trnobj.setVid(vuser);
							trnobj.setVDate(dm.userverifier.getVerDate());
							commonLocal.storeGLTransaction(trnobj);
						}
					}

					dpcattype = olddpcat;
					if (dpcattype == 0)
						dpcattype = 1;
					else
						dpcattype = 2;
					// GLTransObject trnobj=null;
					rs_trn = stmt_trn
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type = "
									+ modcode
									+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					if (rs_trn.next()) {
						trnobj = new GLTransObject();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_trn.getString("gk.gl_type"));
						trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
						if (dm.getTransferType().equalsIgnoreCase("C"))
							trnobj.setTrnMode("C");
						else
							trnobj.setTrnMode("T");
						trnobj.setAmount((dep_amount)
								* rs_trn.getInt("mult_by"));
						trnobj.setCdind("D");
						trnobj.setAccType(dm.getAccType());
						trnobj.setAccNo(String.valueOf(dm.getAccNo()));
						trnobj.setTrnType("P");
						trnobj.setRefNo(0);
						trnobj.setTrnSeq(trnseq);
						trnobj.setVtml(vtml);
						trnobj.setVid(vuser);
						trnobj.setVDate(dm.userverifier.getVerDate());
						commonLocal.storeGLTransaction(trnobj);
					}

					dpcattype += 2;
					rs_trn = stmt_trn
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type = "
									+ modcode
									+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					if (rs_trn.next()) {
						trnobj = new GLTransObject();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_trn.getString("gk.gl_type"));
						trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
						if (dm.getTransferType().equalsIgnoreCase("C"))
							trnobj.setTrnMode("C");
						else
							trnobj.setTrnMode("T");
						trnobj.setAmount((interest_amount)
								* rs_trn.getInt("mult_by"));
						trnobj.setCdind("D");
						trnobj.setAccType(dm.getAccType());
						trnobj.setAccNo(String.valueOf(dm.getAccNo()));
						trnobj.setTrnType("P");
						trnobj.setRefNo(0);
						trnobj.setTrnSeq(trnseq);
						trnobj.setVtml(vtml);
						trnobj.setVid(vuser);
						trnobj.setVDate(dm.userverifier.getVerDate());
						commonLocal.storeGLTransaction(trnobj);
					}
				}
			}

			else {
				trnseq = rs_main.getInt("trn_seq");
				dpcattype = olddpcat;
				/**
				 * credit to loan account loan recovery gl
				 */
				rs_trn = stmt_trn
						.executeQuery("select max(trn_seq) from LoanTransaction where ac_type='"
								+ dm.getLoanAcType()
								+ "' and ac_no="
								+ dm.getLoanAccno());
				rs_trn.next();
				int ln_trn_seq = rs_trn.getInt(1) + 1;
				PreparedStatement pstmt = conn
						.prepareStatement("insert into LoanTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				pstmt.setString(1, dm.getLoanAcType());
				pstmt.setInt(2, dm.getLoanAccno());
				pstmt.setInt(3, ln_trn_seq);
				pstmt.setString(4, getSysDate());
				pstmt.setString(5, "R");
				pstmt.setDouble(6, rs_main.getDouble("dep_paid"));
				pstmt.setString(7, "T");
				pstmt.setString(8, vtml);
				pstmt.setString(9, null);
				pstmt.setString(10, dm.getAccType() + " " + dm.getAccNo());
				pstmt.setString(11, null);
				pstmt.setString(12, "C");
				pstmt.setString(13, commonLocal.getFutureDayDate(getSysDate(),
						-1));
				pstmt.setDouble(14, rs_main.getDouble("dep_paid")
						- dm.getInterestPaid());
				pstmt.setDouble(15, dm.getInterestPaid());
				pstmt.setDouble(16, 0);
				pstmt.setDouble(17, 0);
				pstmt.setDouble(18, 0);
				pstmt.setDouble(19, 0);
				pstmt.setString(20, vtml);
				pstmt.setString(21, vuser);
				pstmt.setString(22, dm.userverifier.getVerDate());
				pstmt.setString(23, vtml);
				pstmt.setString(24, vuser);
				pstmt.setString(25, dm.userverifier.getVerDate());
				pstmt.executeUpdate();

				dpcattype = 1;
				// GLTransObject trnobj=null;
				rs_trn = stmt_trn
						.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
								+ dpcattype
								+ " and gk.ac_type ='"
								+ dm.getLoanAcType()
								+ "' and trn_type='R' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
				if (rs_trn.next()) {
					trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_trn.getString("gk.gl_type"));
					trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
					trnobj.setTrnMode("T");
					trnobj.setAmount((rs_main.getDouble("dep_paid") - dm
							.getInterestPaid())
							* rs_trn.getInt("mult_by"));
					trnobj.setCdind("C");
					trnobj.setAccType(dm.getLoanAcType());
					trnobj.setAccNo(String.valueOf(dm.getLoanAccno()));
					trnobj.setTrnType("R");
					trnobj.setRefNo(0);
					trnobj.setTrnSeq(ln_trn_seq);
					trnobj.setVtml(vtml);
					trnobj.setVid(vuser);
					trnobj.setVDate(dm.userverifier.getVerDate());
					commonLocal.storeGLTransaction(trnobj);
				}

				dpcattype = 2;
				rs_trn = stmt_trn
						.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
								+ dpcattype
								+ " and gk.ac_type ='"
								+ dm.getLoanAcType()
								+ "' and trn_type='R' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
				if (rs_trn.next()) {
					trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_trn.getString("gk.gl_type"));
					trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
					trnobj.setTrnMode("T");
					trnobj.setAmount((dm.getInterestPaid())
							* rs_trn.getInt("mult_by"));
					trnobj.setCdind("C");
					trnobj.setAccType(dm.getLoanAcType());
					trnobj.setAccNo(String.valueOf(dm.getLoanAccno()));
					trnobj.setTrnType("R");
					trnobj.setRefNo(0);
					trnobj.setTrnSeq(ln_trn_seq);
					trnobj.setVtml(vtml);
					trnobj.setVid(vuser);
					trnobj.setVDate(dm.userverifier.getVerDate());
					commonLocal.storeGLTransaction(trnobj);
				}

				stmt_trn.executeUpdate("update LoanMaster set close_date='"
						+ getSysDate() + "' where ac_type='"
						+ dm.getLoanAcType() + "' and ac_no="
						+ dm.getLoanAccno());

				if (dpcattype == 0)
					dpcattype = 1;
				else
					dpcattype = 2;
				double loan_interest_amount = 0;
				if (interest_amount > 0) {
					dpcattype += 2;
					if (interest_amount > rs_main.getDouble("dep_paid")) {
						loan_interest_amount = rs_main.getDouble("dep_paid");
						interest_amount -= rs_main.getDouble("dep_paid");
					} else {
						loan_interest_amount = interest_amount;
						interest_amount = 0;
					}
					rs_trn = stmt_trn
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type = "
									+ modcode
									+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					if (rs_trn.next()) {
						trnobj = new GLTransObject();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_trn.getString("gk.gl_type"));
						trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
						trnobj.setTrnMode("T");
						trnobj.setAmount((loan_interest_amount)
								* rs_trn.getInt("mult_by"));
						trnobj.setCdind("D");
						trnobj.setAccType(dm.getAccType());
						trnobj.setAccNo(String.valueOf(dm.getAccNo()));
						trnobj.setTrnType("P");
						trnobj.setRefNo(0);
						trnobj.setTrnSeq(trnseq);
						trnobj.setVtml(vtml);
						trnobj.setVid(vuser);
						trnobj.setVDate(dm.userverifier.getVerDate());
						commonLocal.storeGLTransaction(trnobj);
					}

					dpcattype -= 2;
					loan_interest_amount = rs_main.getDouble("dep_paid")
							- loan_interest_amount;
				} else {
					loan_interest_amount = rs_main.getDouble("dep_paid");
				}

				if (loan_interest_amount > 0) {
					rs_trn = stmt_trn
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type = "
									+ modcode
									+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					if (rs_trn.next()) {
						trnobj = new GLTransObject();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_trn.getString("gk.gl_type"));
						trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
						trnobj.setTrnMode("T");
						trnobj.setAmount((loan_interest_amount)
								* rs_trn.getInt("mult_by"));
						trnobj.setCdind("D");
						trnobj.setAccType(dm.getAccType());
						trnobj.setAccNo(String.valueOf(dm.getAccNo()));
						trnobj.setTrnType("P");
						trnobj.setRefNo(0);
						trnobj.setTrnSeq(trnseq);
						trnobj.setVtml(vtml);
						trnobj.setVid(vuser);
						trnobj.setVDate(dm.userverifier.getVerDate());
						commonLocal.storeGLTransaction(trnobj);
					}
				}
				stmt_trn
						.executeUpdate("update DepositTransaction set trn_type='P',ve_tml='"
								+ vtml
								+ "',ve_user='"
								+ vuser
								+ "',ve_date = '"
								+ dm.userverifier.getVerDate()
								+ "' where trn_type='L' and ac_type='"
								+ dm.getAccType()
								+ "' and ac_no="
								+ dm.getAccNo());

				rs_main.next();
				trnseq = rs_main.getInt("trn_seq");

				if (dm.getTransferType().equalsIgnoreCase("P")) {
					PayOrderObject po = new PayOrderObject();
					po.setPOType("P");
					po.setPOCustType(0);
					po.setPOPayee(dm.getName());
					po.setPOAccType(dm.getAccType());
					po.setPOAccNo(dm.getAccNo());
					po.setPOGlCode(0);
					po.setPOAmount(rs_main.getDouble("dep_paid")
							+ rs_main.getDouble("int_paid"));
					po.setCommissionAmount(0);
					po.uv.setUserTml(vtml);
					po.uv.setUserId(vuser);
					po.uv.setUserDate(dm.userverifier.getUserDate());
					po.uv.setVerTml(vtml);
					po.uv.setVerId(vuser);
					po.uv.setVerDate(dm.userverifier.getVerDate());
					int_return_value = commonLocal.storePayOrder(po);
					stmt_main
							.executeUpdate("update DepositTransaction set ref_no="
									+ int_return_value
									+ ",trn_narr='By PO,PO no="
									+ int_return_value
									+ "' where trn_type='E' and ac_type='"
									+ dm.getAccType()
									+ "' and ac_no="
									+ dm.getAccNo() + " and trn_seq=" + trnseq);
					/**
					 * PayOrder Credit
					 */
					// rs_trn = stmt_trn.executeQuery("select
					// gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost
					// gp where code = 1 and gk.ac_type = 1999003 and
					// trn_type='P' and cr_dr='C' and gk.ac_type = gp.ac_type
					// and gp.gl_code = gk.gl_code");
					rs_trn = stmt_trn
							.executeQuery("select * from GLKeyParam where ac_type=1016001 and code=1");
					if (rs_trn.next()) {
						trnobj = new GLTransObject();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_trn.getString("gl_type"));
						trnobj.setGLCode(rs_trn.getString("gl_code"));
						trnobj.setTrnMode("T");
						trnobj
								.setAmount((rs_main.getDouble("dep_paid") + rs_main
										.getDouble("int_paid")));
						trnobj.setCdind("C");
						trnobj.setAccType(dm.getAccType());
						trnobj.setAccNo(String.valueOf(dm.getAccNo()));
						trnobj.setTrnType("P");
						trnobj.setRefNo(0);
						trnobj.setTrnSeq(trnseq);
						trnobj.setVtml(vtml);
						trnobj.setVid(vuser);
						trnobj.setVDate(dm.userverifier.getVerDate());
						commonLocal.storeGLTransaction(trnobj);
					}

				} else if (dm.getTransferType().equalsIgnoreCase("T")) {
					AccountTransObject am = new AccountTransObject();
					// StringTokenizer st = new
					// StringTokenizer(dm.getTransferType()," ");
					String accty = dm.getTransferAccType();
					int accno = dm.getTransferAccno();
					am.setAccType(accty);
					am.setAccNo(accno);
					am.setTransDate(getSysDate());
					am.setTransType("R");
					am.setTransAmount(rs_main.getDouble("dep_paid")
							+ rs_main.getDouble("int_paid"));
					am.setTransMode("T");
					am.setTransSource(vtml);
					am.setCdInd("C");
					am.setChqDDNo(0);
					am.setChqDDDate("");
					am.setTransNarr(dm.getAccType() + " "
							+ String.valueOf(dm.getAccNo()));
					am.setRef_No(0);
					am.setPayeeName(dm.getName());
					am.setCloseBal(rs_main.getDouble("dep_paid")
							+ rs_main.getDouble("int_paid"));
					am.setLedgerPage(0);
					am.uv.setUserTml(vtml);
					am.uv.setUserId(vuser);
					am.uv.setUserDate(dm.userverifier.getVerDate());
					am.uv.setVerTml(vtml);
					am.uv.setVerId(vuser);
					am.uv.setVerDate(dm.userverifier.getVerDate());
					int_return_value = commonLocal.storeAccountTransaction(am);

					stmt_trn
							.executeUpdate("update DepositTransaction set trn_narr='"
									+ accty
									+ " "
									+ accno
									+ "' where ac_type='"
									+ dm.getAccType()
									+ "' and ac_no="
									+ dm.getAccNo()
									+ " and trn_seq="
									+ rs_main.getInt("trn_seq"));
				} else if (dm.getTransferType().equalsIgnoreCase("C")) {
					int_return_value = commonLocal.getModulesColumn(
							"lst_voucher_scroll_no", "1019000");
					PreparedStatement pstmt_insert = conn
							.prepareStatement("insert into DayCash (scroll_no,trn_type,trn_date,ac_type,ac_no,csh_amt,cd_ind,vch_type,attached,vch_no,de_user,de_tml,de_date,ve_user,ve_tml,ve_date,name) values (0,'P',?,?,?,?,'C','P','F',?,?,?,?,?,?,?,?)");
					pstmt_insert.setString(1, getSysDate());
					pstmt_insert.setString(2, dm.getAccType());
					pstmt_insert.setInt(3, dm.getAccNo());
					pstmt_insert.setDouble(4, rs_main.getDouble("dep_paid")
							+ rs_main.getDouble("int_paid"));
					pstmt_insert.setInt(5, int_return_value);
					pstmt_insert.setString(6, vuser);
					pstmt_insert.setString(7, vtml);
					pstmt_insert.setString(8, dm.userverifier.getVerDate());
					pstmt_insert.setString(9, vuser);
					pstmt_insert.setString(10, vtml);
					pstmt_insert.setString(11, dm.userverifier.getVerDate());
					pstmt_insert.setString(12, dm.getName());
					pstmt_insert.executeUpdate();
					stmt_trn
							.executeUpdate("update DepositTransaction set ref_no="
									+ int_return_value
									+ ",trn_narr='By Cash vch no"
									+ int_return_value
									+ "' where ac_type='"
									+ dm.getAccType()
									+ "' and ac_no="
									+ dm.getAccNo()
									+ " and trn_seq="
									+ rs_main.getInt("trn_seq"));
					/**
					 * cash payment gl
					 */
					// rs_trn = stmt_trn.executeQuery("select
					// gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost
					// gp where code = 1 and gk.ac_type = 1999001 and
					// trn_type='P' and cr_dr='C' and gk.ac_type = gp.ac_type
					// and gp.gl_code = gk.gl_code");
					rs_trn = stmt_trn
							.executeQuery("select * from GLKeyParam where ac_type=1019001 and code=1");
					if (rs_trn.next()) {
						trnobj = new GLTransObject();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_trn.getString("gl_type"));
						trnobj.setGLCode(rs_trn.getString("gl_code"));
						trnobj.setTrnMode("C");
						trnobj
								.setAmount((rs_main.getDouble("dep_paid") + rs_main
										.getDouble("int_paid")));
						trnobj.setCdind("C");
						trnobj.setAccType(dm.getAccType());
						trnobj.setAccNo(String.valueOf(dm.getAccNo()));
						trnobj.setTrnType("P");
						trnobj.setRefNo(0);
						trnobj.setTrnSeq(trnseq);
						trnobj.setVtml(vtml);
						trnobj.setVid(vuser);
						trnobj.setVDate(dm.userverifier.getVerDate());
						commonLocal.storeGLTransaction(trnobj);
					}
				}
				/**
				 * check for int_paid -ve or +ve if paid == 0 directly debit
				 * from FD account gl if paid >0 debit from FD acc and FD
				 * payable Gl
				 */
				if (rs_main.getDouble("int_paid") == 0) {
					/**
					 * debit from FD account GL
					 */
					dpcattype = olddpcat;
					if (dpcattype == 0)
						dpcattype = 1;
					else
						dpcattype = 2;
					rs_trn = stmt_trn
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type = "
									+ modcode
									+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					if (rs_trn.next()) {
						trnobj = new GLTransObject();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_trn.getString("gk.gl_type"));
						trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
						if (dm.getTransferType().equalsIgnoreCase("C"))
							trnobj.setTrnMode("C");
						else
							trnobj.setTrnMode("T");
						trnobj
								.setAmount((rs_main.getDouble("dep_paid") + rs_main
										.getDouble("int_paid"))
										* rs_trn.getInt("mult_by"));
						trnobj.setCdind("D");
						trnobj.setAccType(dm.getAccType());
						trnobj.setAccNo(String.valueOf(dm.getAccNo()));
						trnobj.setTrnType("P");
						trnobj.setRefNo(0);
						trnobj.setTrnSeq(trnseq);
						trnobj.setVtml(vtml);
						trnobj.setVid(vuser);
						trnobj.setVDate(dm.userverifier.getVerDate());
						commonLocal.storeGLTransaction(trnobj);
					}
				} else if (rs_main.getDouble("int_paid") > 0) {
					/**
					 * debit FD acc Gl
					 */
					dpcattype = olddpcat;
					if (dpcattype == 0)
						dpcattype = 1;
					else
						dpcattype = 2;
					rs_trn = stmt_trn
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type = "
									+ modcode
									+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					if (rs_trn.next()) {
						trnobj = new GLTransObject();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_trn.getString("gk.gl_type"));
						trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
						if (dm.getTransferType().equalsIgnoreCase("C"))
							trnobj.setTrnMode("C");
						else
							trnobj.setTrnMode("T");
						trnobj.setAmount((rs_main.getDouble("dep_paid"))
								* rs_trn.getInt("mult_by"));
						trnobj.setCdind("D");
						trnobj.setAccType(dm.getAccType());
						trnobj.setAccNo(String.valueOf(dm.getAccNo()));
						trnobj.setTrnType("P");
						trnobj.setRefNo(0);
						trnobj.setTrnSeq(trnseq);
						trnobj.setVtml(vtml);
						trnobj.setVid(vuser);
						trnobj.setVDate(dm.userverifier.getVerDate());
						commonLocal.storeGLTransaction(trnobj);
					}

					dpcattype += 2;
					rs_trn = stmt_trn
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type = "
									+ modcode
									+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					if (rs_trn.next()) {
						trnobj = new GLTransObject();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_trn.getString("gk.gl_type"));
						trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
						if (dm.getTransferType().equalsIgnoreCase("C"))
							trnobj.setTrnMode("C");
						else
							trnobj.setTrnMode("T");
						trnobj.setAmount((rs_main.getDouble("int_paid"))
								* rs_trn.getInt("mult_by"));
						trnobj.setCdind("D");
						trnobj.setAccType(dm.getAccType());
						trnobj.setAccNo(String.valueOf(dm.getAccNo()));
						trnobj.setTrnType("P");
						trnobj.setRefNo(0);
						trnobj.setTrnSeq(trnseq);
						trnobj.setVtml(vtml);
						trnobj.setVid(vuser);
						trnobj.setVDate(dm.userverifier.getVerDate());
						commonLocal.storeGLTransaction(trnobj);
					}
				}
			}
			stmt_trn
					.executeUpdate("update DepositTransaction set trn_type='P',ve_tml='"
							+ vtml
							+ "',ve_user='"
							+ vuser
							+ "',ve_date = '"
							+ dm.userverifier.getVerDate()
							+ "' where trn_type='E' and ac_type='"
							+ dm.getAccType()
							+ "' and ac_no="
							+ dm.getAccNo()
							+ " and trn_seq=" + trnseq);
			stmt_main.executeUpdate("update DepositMaster set close_ind="
					+ closeind + ",close_date = '" + getSysDate()
					+ "',int_paid_date='" + getSysDate() + "',int_upto_date='"
					+ commonLocal.getFutureDayDate(dm.getClosedt(), -1)
					+ "' where ac_no=" + dm.getAccNo() + " and ac_type='"
					+ dm.getAccType() + "'");
		} catch (SQLException ve) {
			ve.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordsNotInsertedException();
		} catch (CreateException e) {
			e.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordsNotInsertedException();
		} catch (Exception ex) {
			ex.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordsNotInsertedException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			commonLocal = null;
		}
		return int_return_value;
	}

	public double[] getDepositInterestRate(String ac_type, int dp_type,
			int cat_type, String date, int days, double amount) {
		double arr[] = { 0, 0, 0 };
		Statement stmt_main = null;
		Connection conn = null;
		try {

			conn = getConnection();
			stmt_main = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			System.out.println("days========" + days + "actype====" + ac_type);
			ResultSet rs_main = stmt_main
					.executeQuery("select int_rate from DepositInterestRate where "
							+ days
							+ " between days_fr and days_to and ac_type='"
							+ ac_type
							+ "' and '"
							+ (date)
							+ "' between concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1)) and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))");
			if (rs_main.next())
				arr[0] = rs_main.getDouble("int_rate");
			rs_main = stmt_main
					.executeQuery("select extra_int_rate from DepositQuantumRate where "
							+ amount
							+ " between min_amt and max_amt and category = "
							+ dp_type
							+ " and "
							+ days
							+ " between days_fr and days_to and ac_type='"
							+ ac_type
							+ "' and '"
							+ date
							+ "' between concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1)) and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))");
			if (rs_main.next())
				arr[1] = rs_main.getDouble("extra_int_rate");
			System.out.println("arr[1] sumanth in bean" + arr[1]);

			rs_main = stmt_main
					.executeQuery("select extra_int_rate from DepositCategoryRate where category = "
							+ cat_type
							+ " and "
							+ days
							+ " between days_fr and days_to and ac_type='"
							+ ac_type
							+ "' and '"
							+ date
							+ "' between concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1)) and concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))");
			if (rs_main.next())
				arr[2] = rs_main.getDouble("extra_int_rate");
			System.out.println("arr[2]sumanth in bean" + arr[2]);

			System.out.println("arr[0] sumanth  in bean" + arr[0]);
			rs_main.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		System.out.println("arrrrrrrr before return iiiiiin bean------"
				+ arr[0]);
		return arr;
	}

	public DepositMasterObject[] getReInvestmentClosureDetails(int acno,
			String type, boolean flag) throws AccountNotFoundException,
			RecordsNotFoundException {
		DepositMasterObject array_depositmasterobject[] = new DepositMasterObject[1];
		ResultSet rs_main = null, rs_extra = null;
		Statement stmt_main, stmt_extra = null;
		int close_ind = 0;
		Connection conn = null;
		try {
			conn = getConnection();
			commonLocal = commonLocalHome.create();
			array_depositmasterobject[0] = new DepositMasterObject();
			stmt_main = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			stmt_extra = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);

			if (flag == true)
				rs_main = stmt_main
						.executeQuery("select concat(ifnull(cm.fname,' '),' ',ifnull(cm.mname,' '),' ',ifnull(cm.lname,' ')) as name,dm.*,IFNULL(dm.ve_user,'') as veuser,dt.dep_paid,dt.int_paid,dt.trn_narr,dt.trn_mode,penalty_rate,custtype,sub_category,dm.ln_availed,dm.ln_ac_type,dm.ln_ac_no from DepositMaster dm,CustomerMaster cm, DepositTransaction dt,Modules where dm.ac_type=modulecode and dm.cid=cm.cid and dm.ac_type='"
								+ type
								+ "' and dm.ac_no="
								+ acno
								+ " and dt.ac_type='"
								+ type
								+ "' and dt.ac_no="
								+ acno
								+ " order by dt.trn_seq desc limit 1");
			else
				rs_main = stmt_main
						.executeQuery("select concat(ifnull(cm.fname,' '),' ',ifnull(cm.mname,' '),' ',ifnull(cm.lname,' ')) as name,dm.*,IFNULL(dm.ve_user,'') as veuser,dt.cum_int,penalty_rate,custtype,sub_category,dm.ln_availed,dm.ln_ac_type,dm.ln_ac_no,trn_mode,trn_narr from DepositMaster dm,CustomerMaster cm, DepositTransaction dt,Modules where dm.ac_type=modulecode and dm.cid=cm.cid and dm.ac_type='"
								+ type
								+ "' and dm.ac_no="
								+ acno
								+ " and dt.ac_type='"
								+ type
								+ "' and dt.ac_no="
								+ acno
								+ " and dt.trn_type not in ('E','L') order by dt.trn_seq desc limit 1");
			rs_main.last();
			if (rs_main.getRow() == 0)
				throw new RecordsNotFoundException();
			if (rs_main.getRow() > 0) {
				rs_main.first();
				close_ind = rs_main.getInt("close_ind");
				if (close_ind == 0 || close_ind > 6) {
					array_depositmasterobject[0].setAccNo(acno);
					array_depositmasterobject[0].setAccType(type);
					array_depositmasterobject[0].setName(rs_main
							.getString("name"));
					array_depositmasterobject[0].setDepDate(rs_main
							.getString("dep_date"));
					array_depositmasterobject[0].setDepositAmt(rs_main
							.getDouble("dep_amt"));
					array_depositmasterobject[0].setMaturityAmt(rs_main
							.getDouble("mat_amt"));
					array_depositmasterobject[0].setInterestRate(rs_main
							.getDouble("int_rate"));
					array_depositmasterobject[0].setMaturityDate(rs_main
							.getString("mat_date"));
					array_depositmasterobject[0].setNomineeRegNo(rs_main
							.getInt("nom_no"));
					array_depositmasterobject[0].setVerified(rs_main
							.getString("veuser"));
					array_depositmasterobject[0].setLoanAvailed(rs_main
							.getString("dm.ln_availed"));
					array_depositmasterobject[0].setLoanAcType(rs_main
							.getString("dm.ln_ac_type"));
					array_depositmasterobject[0].setLoanAccno(rs_main
							.getInt("dm.ln_ac_no"));
					array_depositmasterobject[0].setInterestFrq(rs_main
							.getString("dm.int_freq"));
					array_depositmasterobject[0].setInterestUpto(rs_main
							.getString("dm.int_upto_date"));
					array_depositmasterobject[0].setReceiptno(rs_main
							.getInt("dm.rct_no"));

					rs_extra = stmt_extra
							.executeQuery("select trn_mode,trn_narr from DepositTransaction where ac_type='"
									+ type
									+ "' and ac_no ="
									+ acno
									+ " order by trn_seq desc limit 1");
					rs_extra.next();
					array_depositmasterobject[0].setTransferAccType(rs_extra
							.getString("trn_narr"));
					array_depositmasterobject[0].setTransferType(rs_extra
							.getString("trn_mode"));

					if (flag == false) {
						array_depositmasterobject[0].setCumInterest(rs_main
								.getDouble("dt.cum_int"));
						array_depositmasterobject[0].setDepositDays(rs_main
								.getInt("dep_days"));
						array_depositmasterobject[0].setInterestMode(rs_main
								.getString("int_mode"));
						array_depositmasterobject[0].setTransferAccType(rs_main
								.getString("trf_ac_type"));
						array_depositmasterobject[0].setTransferAccno(rs_main
								.getInt("trf_ac_no"));
						array_depositmasterobject[0].setExtraRateType(rs_main
								.getInt("extra_rate_type"));
						array_depositmasterobject[0].setDPType(rs_main
								.getInt("custtype"));
						array_depositmasterobject[0].setCategory(rs_main
								.getInt("sub_category"));
						array_depositmasterobject[0].setNoofJtHldrs(rs_main
								.getInt("no_jt_hldr"));
						array_depositmasterobject[0].setCustomerId(rs_main
								.getInt("dm.cid"));
						array_depositmasterobject[0].setMailingAddress(rs_main
								.getInt("dm.add_type"));
						array_depositmasterobject[0].setIntroAccType(rs_main
								.getString("intr_ac_type"));
						array_depositmasterobject[0].setIntroAccno(rs_main
								.getInt("intr_ac_no"));
						array_depositmasterobject[0].setInterestFrq(rs_main
								.getString("dm.int_freq"));
						array_depositmasterobject[0].setInterestUpto(rs_main
								.getString("dm.int_upto_date"));
						array_depositmasterobject[0].setReceiptno(rs_main
								.getInt("dm.rct_no"));

					} else {
						array_depositmasterobject[0].setDepositPaid(rs_main
								.getDouble("dep_paid"));
						array_depositmasterobject[0].setInterestPaid(rs_main
								.getDouble("int_paid"));
						array_depositmasterobject[0].setInterestMode(rs_main
								.getString("int_mode"));
						array_depositmasterobject[0].setTransferAccType(rs_main
								.getString("trn_narr"));
						array_depositmasterobject[0].setTransferAccno(rs_main
								.getInt("trf_ac_no"));
						array_depositmasterobject[0].setExtraRateType(rs_main
								.getInt("extra_rate_type"));
						array_depositmasterobject[0].setDPType(rs_main
								.getInt("custtype"));
						array_depositmasterobject[0].setCategory(rs_main
								.getInt("sub_category"));
						array_depositmasterobject[0].setNoofJtHldrs(rs_main
								.getInt("no_jt_hldr"));
						array_depositmasterobject[0].setCustomerId(rs_main
								.getInt("dm.cid"));
						array_depositmasterobject[0].setMailingAddress(rs_main
								.getInt("dm.add_type"));
						array_depositmasterobject[0].setIntroAccType(rs_main
								.getString("intr_ac_type"));
						array_depositmasterobject[0].setIntroAccno(rs_main
								.getInt("intr_ac_no"));
						array_depositmasterobject[0].setInterestFrq(rs_main
								.getString("dm.int_freq"));

					}
					array_depositmasterobject[0].setCloseInd(close_ind);
					array_depositmasterobject[0].setPenaltyRate(rs_main
							.getDouble("penalty_rate"));
					rs_main = stmt_main
							.executeQuery("select cid,addr_type from JointHolders where ac_type='"
									+ array_depositmasterobject[0].getAccType()
									+ "' and ac_no='"
									+ array_depositmasterobject[0].getAccNo()
									+ "'");
					int j = 0;
					int addrtype[] = null;
					int cids[] = null;
					if (rs_main.next()) {
						rs_main.last();
						addrtype = new int[rs_main.getRow()];
						cids = new int[rs_main.getRow()];
						rs_main.beforeFirst();
						while (rs_main.next()) {
							addrtype[j] = rs_main.getInt(2);
							cids[j++] = rs_main.getInt(1);
						}
					}
					// code added by sanjeet..
					rs_main = stmt_main
							.executeQuery("select sum(int_amt),sum(int_paid),max(cum_int) from DepositTransaction where ac_type='"
									+ type
									+ "' and ac_no="
									+ acno
									+ "  and trn_type != 'E' and trn_type != 'L'");
					if (rs_main.next()) {
						array_depositmasterobject[0].setRDBalance(rs_main
								.getDouble(1));
						array_depositmasterobject[0].setInterestPaid(rs_main
								.getDouble(2));
						array_depositmasterobject[0].setCumInterest(rs_main
								.getDouble(3));
					}

					rs_main = stmt_main
							.executeQuery("select trn_mode,trn_narr from DepositTransaction where ac_type='"
									+ type
									+ "' and ac_no="
									+ acno
									+ "  order by trn_seq desc limit 1");
					rs_main.last();
					if (rs_main.getRow() > 0) {
						rs_main.first();
						array_depositmasterobject[0].setTransferType(rs_main
								.getString("trn_mode"));
						if (rs_main.getString("trn_mode").equalsIgnoreCase("T")) {
							array_depositmasterobject[0]
									.setTransferAccType(rs_main
											.getString("trn_narr"));
						}
					}

					array_depositmasterobject[0].setJointAddrType(addrtype);
					array_depositmasterobject[0].setJointCid(cids);
					array_depositmasterobject[0].setNomineeDetails(commonLocal
							.getNominee(array_depositmasterobject[0]
									.getNomineeRegNo()));
					array_depositmasterobject[0].setSigObj(commonLocal
							.getSignatureDetails(array_depositmasterobject[0]
									.getAccNo(), array_depositmasterobject[0]
									.getAccType()));
				} else
					array_depositmasterobject[0].setCloseInd(close_ind);
			} else
				throw new RecordsNotFoundException();
		} catch (SQLException ee) {
			ee.printStackTrace();
			throw new AccountNotFoundException();
		} catch (CreateException e) {
			e.printStackTrace();
			throw new AccountNotFoundException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			commonLocal = null;
		}
		return array_depositmasterobject;
	}

	public int submitReInvestment(DepositMasterObject depositMasterObject)
			throws RecordsNotInsertedException {
		Connection conn = null;
		ResultSet rs_main = null;
		double extra_amount = 0.0;
		String rd_ac_ty = depositMasterObject.getAccType();
		int rd_ac_no = depositMasterObject.getAccNo();
		String paymode = depositMasterObject.getTransferType();
		String trf_ac_type = depositMasterObject.getTransferAccType();
		int trf_ac_no = depositMasterObject.getTransferAccno();
		String dtml = depositMasterObject.userverifier.getUserTml();
		String duser = depositMasterObject.userverifier.getUserId();
		String close_date = depositMasterObject.getClosedt();
		String trn_narr = "";
		if (paymode.equals("T"))
			trn_narr = trf_ac_type + " " + trf_ac_no;

		System.out.println("Date---> " + getSysDate());
		System.out.println("trf_ac_type--> " + trf_ac_type);
		System.out.println("trf_ac_no---> " + trf_ac_no);
		System.out.println("inside submit reinvestment bean " + trn_narr);

		try {
			conn = getConnection();
			commonLocal = commonLocalHome.create();
			Statement stmt_main = conn
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			rs_main = stmt_main
					.executeQuery("select max(trn_seq) as lst_trn_seq,dm.dep_amt,int_freq,sum(dt.int_amt) as int_amt,sum(dt.int_paid) from DepositTransaction dt,DepositMaster dm where dm.ac_type='"
							+ rd_ac_ty
							+ "' and dm.ac_no="
							+ rd_ac_no
							+ " and dm.ac_type=dt.ac_type and dm.ac_no=dt.ac_no group by dt.ac_no");
			rs_main.next();
			int trnseq = rs_main.getInt("lst_trn_seq");
			double dep_amt = rs_main.getInt("dep_amt");
			double cum_int = rs_main.getInt("int_amt");
			System.out.println("hey its me...");
			System.out
					.println("cum_int" + depositMasterObject.getCumInterest());
			if (cum_int == depositMasterObject.getCumInterest())
				extra_amount = 0.0;
			else
				extra_amount = depositMasterObject.getCumInterest() - cum_int;
			/**
			 * For Interest
			 */
			PreparedStatement ps_int = conn
					.prepareStatement("insert into DepositTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps_int.setString(1, rd_ac_ty);
			ps_int.setInt(2, rd_ac_no);
			ps_int.setInt(3, ++trnseq);
			ps_int.setString(4, getSysDate());
			ps_int.setString(5, "E");
			ps_int.setDouble(6, 0);
			ps_int.setDouble(7, extra_amount);
			ps_int.setDouble(8, 0);
			ps_int.setDouble(9, 0);
			ps_int.setDouble(10, 0);
			ps_int.setString(11, null);
			ps_int.setString(12, commonLocal.getFutureDayDate(close_date, -1));
			ps_int.setInt(13, 0);
			ps_int.setString(14, "By Interest");
			ps_int.setString(15, "T");
			ps_int.setString(16, dtml);
			ps_int.setString(17, "C");
			ps_int.setDouble(18, cum_int + extra_amount);
			ps_int.setString(19, dtml);
			ps_int.setString(20, duser);
			ps_int
					.setString(21, depositMasterObject.userverifier
							.getUserDate());
			ps_int.setString(22, null);
			ps_int.setString(23, null);
			ps_int.setString(24, null);
			ps_int.executeUpdate();

			cum_int += extra_amount;
			/*
			 * double double_final_dep_amt = dep_amt; double
			 * double_final_interest_amount=cum_int;
			 */
			double double_final_dep_amt = 0, double_final_interest_amount = 0;
			if (cum_int < 0) {
				double_final_interest_amount = 0;
				double_final_dep_amt = dep_amt + cum_int;
			} else {
				double_final_interest_amount = cum_int;
				double_final_dep_amt = dep_amt;
			}
			/**
			 * deposit amount and interest payment and loan payment
			 */
			if (depositMasterObject.getLoanAvailed().equals("T")) {
				System.out.println("Inside loan Avil True in bean-->");
				double loan_amount = depositMasterObject.getDepositPaid()
						+ depositMasterObject.getInterestPaid();
				if (loan_amount >= double_final_dep_amt
						+ double_final_interest_amount) {
					loan_amount = double_final_dep_amt
							+ double_final_interest_amount;
					double_final_dep_amt = 0;
					double_final_interest_amount = 0;
				} else if (loan_amount <= double_final_interest_amount) {
					double_final_interest_amount = double_final_interest_amount
							- loan_amount;
				} else if (loan_amount <= double_final_interest_amount
						+ double_final_dep_amt) {
					double_final_dep_amt = (double_final_dep_amt + double_final_interest_amount)
							- loan_amount;
					double_final_interest_amount = 0;
				}
				if (loan_amount > 0) {
					ps_int.setString(1, rd_ac_ty);
					ps_int.setInt(2, rd_ac_no);
					ps_int.setInt(3, ++trnseq);
					ps_int.setString(4, getSysDate());
					ps_int.setString(5, "L");
					ps_int.setDouble(6, 0);
					ps_int.setDouble(7, 0);
					ps_int.setDouble(8, loan_amount);
					ps_int.setDouble(9, 0);
					ps_int.setDouble(10, 0);
					ps_int.setString(11, getSysDate());
					ps_int.setString(12, null);
					ps_int.setInt(13, 0);
					ps_int.setString(14, depositMasterObject.getLoanAcType()
							+ " " + depositMasterObject.getLoanAccno());
					ps_int.setString(15, "T");
					ps_int.setString(16, dtml);
					ps_int.setString(17, "D");
					ps_int.setDouble(18, 0);
					ps_int.setString(19, dtml);
					ps_int.setString(20, duser);
					ps_int.setString(21, depositMasterObject.userverifier
							.getUserDate());
					ps_int.setString(22, null);
					ps_int.setString(23, null);
					ps_int.setString(24, null);
					ps_int.executeUpdate();
				}
				if (double_final_dep_amt > 0) {
					ps_int.setString(1, rd_ac_ty);
					ps_int.setInt(2, rd_ac_no);
					ps_int.setInt(3, ++trnseq);
					ps_int.setString(4, getSysDate());
					ps_int.setString(5, "E");
					ps_int.setDouble(6, 0);
					ps_int.setDouble(7, 0);
					ps_int.setDouble(8, double_final_dep_amt);
					ps_int.setDouble(9, double_final_interest_amount);
					ps_int.setDouble(10, 0);
					ps_int.setString(11, getSysDate());
					ps_int.setString(12, null);
					ps_int.setInt(13, 0);
					ps_int.setString(14, trn_narr);
					ps_int.setString(15, paymode);
					ps_int.setString(16, dtml);
					ps_int.setString(17, "D");
					ps_int.setDouble(18, 0);
					ps_int.setString(19, dtml);
					ps_int.setString(20, duser);
					ps_int.setString(21, depositMasterObject.userverifier
							.getUserDate());
					ps_int.setString(22, null);
					ps_int.setString(23, null);
					ps_int.setString(24, null);
					ps_int.executeUpdate();
				}
			} else {

				System.out.println("Loan Avil is False ELSE Block " + trn_narr);

				ps_int.setString(1, rd_ac_ty);
				ps_int.setInt(2, rd_ac_no);
				ps_int.setInt(3, ++trnseq);
				ps_int.setString(4, getSysDate());
				ps_int.setString(5, "E");
				ps_int.setDouble(6, 0);
				ps_int.setDouble(7, 0);
				ps_int.setDouble(8, double_final_dep_amt);
				ps_int.setDouble(9, double_final_interest_amount);
				ps_int.setDouble(10, 0);
				ps_int.setString(11, getSysDate());
				ps_int.setString(12, null);
				ps_int.setInt(13, 0);
				ps_int.setString(14, trn_narr);
				ps_int.setString(15, paymode);
				ps_int.setString(16, dtml);
				ps_int.setString(17, "D");
				ps_int.setDouble(18, 0);
				ps_int.setString(19, dtml);
				ps_int.setString(20, duser);
				ps_int.setString(21, depositMasterObject.userverifier
						.getUserDate());
				ps_int.setString(22, null);
				ps_int.setString(23, null);
				ps_int.setString(24, null);
				ps_int.executeUpdate();
			}
			stmt_main.executeUpdate("update DepositMaster set close_ind='"
					+ depositMasterObject.getCloseInd() + "',exc_amt="
					+ extra_amount + ",close_date='" + getSysDate()
					+ "' where ac_type='" + rd_ac_ty + "' and ac_no = "
					+ rd_ac_no);

		} catch (SQLException we) {
			we.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordsNotInsertedException();
		} catch (CreateException e) {
			e.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordsNotInsertedException();
		} catch (Exception ex) {
			ex.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordsNotInsertedException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			commonLocal = null;
		}
		return 1;
	}

	public int verifiedReInvestment(DepositMasterObject dm)
			throws RecordsNotInsertedException {
		int closeind = 0, dpcattype = 0, modcode = 0, olddpcat = 0;
		Connection conn = null;
		ResultSet rs_main = null, rs_trn = null;
		String vtml = dm.userverifier.getVerTml(), vuser = dm.userverifier
				.getVerId();
		int int_return_value = 0, trnseq = 0;
		try {
			conn = getConnection();
			commonLocal = commonLocalHome.create();
			Statement stmt_main = conn
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			Statement stmt_trn = conn
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);

			dpcattype = dm.getDPType();
			olddpcat = dpcattype;
			modcode = Integer.parseInt(dm.getAccType());

			System.out.println("dm.actype======" + dm.getAccType());
			System.out.println("dm.ac_no======" + dm.getAccNo());
			System.out.println("dm.getCloseInd()====> " + dm.getCloseInd());
			System.out.println("transfer type---------------"
					+ dm.getTransferType());
			System.out.println("inside verify BEAN CLASS========="
					+ dm.getCloseInd());
			System.out
					.println("Received by mode=========" + dm.getReceivedBy());
			System.out.println("---dpcattype----> " + dpcattype);
			System.out.println("----olddpcat---- " + olddpcat);

			rs_main = stmt_main
					.executeQuery("select int_amt,trn_seq,dep_paid,int_paid,trn_type from DepositTransaction where ac_type='"
							+ dm.getAccType()
							+ "' and ac_no= "
							+ dm.getAccNo()
							+ " and trn_type in ('E','L') order by trn_seq");
			rs_main.next();

			if (dm.getCloseInd() == 90)
				closeind = 1;
			else if (dm.getCloseInd() == 91)
				closeind = 2;
			else if (dm.getCloseInd() == 92)
				closeind = 3;
			double dep_amount = dm.getDepositAmt();
			double interest_amount = dm.getCumInterest();

			System.out.println("no of rows rs_main------->>>>"
					+ rs_main.getRow());

			/*
			 * double int_amt = rs_main.getDouble("int_amt");
			 * 
			 * 
			 * System.out.println("trn_seq==intially==>"+rs_main.getInt("trn_seq"
			 * ));
			 * 
			 * System.out.println("int_amt in BEAN===="+int_amt);
			 */

			if (rs_main.getRow() != 0 && rs_main.getDouble("int_amt") >= 0) {
				System.out.println("dpcattype---> " + dpcattype);
				if (dpcattype == 0)
					dpcattype = 3;
				else
					dpcattype = 4;
				/**
				 * credit to rd gl
				 */

				rs_trn = stmt_trn
						.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
								+ dpcattype
								+ " and gk.ac_type = "
								+ modcode
								+ " and trn_type='I' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
				rs_trn.next();
				GLTransObject trnobj = new GLTransObject();
				trnobj.setTrnDate(getSysDate());
				trnobj.setGLType(rs_trn.getString("gk.gl_type"));
				trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
				trnobj.setTrnMode("T");
				trnobj.setAmount(rs_main.getDouble("int_amt")
						* rs_trn.getInt("mult_by"));
				trnobj.setCdind("C");
				trnobj.setAccType(dm.getAccType());
				trnobj.setAccNo(String.valueOf(dm.getAccNo()));
				trnobj.setTrnType("I");
				trnobj.setRefNo(0);
				trnobj.setTrnSeq(rs_main.getInt("trn_seq"));
				trnobj.setVtml(vtml);
				trnobj.setVid(vuser);
				trnobj.setVDate(dm.userverifier.getVerDate());
				commonLocal.storeGLTransaction(trnobj);

				dpcattype = dpcattype + 2;
				/**
				 * Debit loss account
				 */
				rs_trn = stmt_trn
						.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
								+ dpcattype
								+ " and gk.ac_type = "
								+ modcode
								+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
				rs_trn.next();
				trnobj = new GLTransObject();
				trnobj.setTrnDate(getSysDate());
				trnobj.setGLType(rs_trn.getString("gk.gl_type"));
				trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
				trnobj.setTrnMode("T");
				trnobj.setAmount(rs_main.getDouble("int_amt")
						* rs_trn.getInt("mult_by"));
				trnobj.setCdind("D");
				trnobj.setAccType(dm.getAccType());
				trnobj.setAccNo(String.valueOf(dm.getAccNo()));
				trnobj.setTrnType("P");
				trnobj.setRefNo(0);
				trnobj.setTrnSeq(rs_main.getInt("trn_seq"));
				trnobj.setVtml(vtml);
				trnobj.setVid(vuser);
				trnobj.setVDate(dm.userverifier.getVerDate());
				commonLocal.storeGLTransaction(trnobj);
			} else {
				if (dpcattype == 0)
					dpcattype = 3;
				else
					dpcattype = 4;
				/**
				 * Debit RD gl
				 */
				rs_trn = stmt_trn
						.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
								+ dpcattype
								+ " and gk.ac_type = "
								+ modcode
								+ " and trn_type='I' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
				rs_trn.next();
				GLTransObject trnobj = new GLTransObject();
				trnobj.setTrnDate(getSysDate());
				trnobj.setGLType(rs_trn.getString("gk.gl_type"));
				trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
				trnobj.setTrnMode("T");
				trnobj.setAmount(rs_main.getDouble("int_amt")
						* rs_trn.getInt("mult_by"));
				trnobj.setCdind("D");
				trnobj.setAccType(dm.getAccType());
				trnobj.setAccNo(String.valueOf(dm.getAccNo()));
				trnobj.setTrnType("P");
				trnobj.setRefNo(0);
				trnobj.setTrnSeq(rs_main.getInt("trn_seq"));
				trnobj.setVtml(vtml);
				trnobj.setVid(vuser);
				trnobj.setVDate(dm.userverifier.getVerDate());
				commonLocal.storeGLTransaction(trnobj);

				dpcattype = dpcattype + 4;
				/**
				 * credit Profit account or Interest payable
				 */
				rs_trn = stmt_trn
						.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
								+ dpcattype
								+ " and gk.ac_type = "
								+ modcode
								+ " and trn_type='P' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
				rs_trn.next();
				trnobj = new GLTransObject();
				trnobj.setTrnDate(getSysDate());
				trnobj.setGLType(rs_trn.getString("gk.gl_type"));
				trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
				trnobj.setTrnMode("T");
				trnobj.setAmount(rs_main.getDouble("int_amt")
						* rs_trn.getInt("mult_by"));
				trnobj.setCdind("C");
				trnobj.setAccType(dm.getAccType());
				trnobj.setAccNo(String.valueOf(dm.getAccNo()));
				trnobj.setTrnType("I");
				trnobj.setRefNo(0);
				trnobj.setTrnSeq(rs_main.getInt("trn_seq"));
				trnobj.setVtml(vtml);
				trnobj.setVid(vuser);
				trnobj.setVDate(dm.userverifier.getVerDate());
				commonLocal.storeGLTransaction(trnobj);
			}
			stmt_trn
					.executeUpdate("update DepositTransaction set trn_type='I',ve_tml='"
							+ vtml
							+ "',ve_user='"
							+ vuser
							+ "',ve_date ='"
							+ dm.userverifier.getVerDate()
							+ "' where trn_type='E' and trn_seq ="
							+ rs_main.getInt("trn_seq")
							+ " and ac_type='"
							+ dm.getAccType() + "' and ac_no=" + dm.getAccNo());

			rs_main.next();

			if (rs_main.isLast()) {
				trnseq = rs_main.getInt("trn_seq");
				dpcattype = olddpcat;
				if (dpcattype == 0)
					dpcattype = 1;
				else
					dpcattype = 2;
				if (rs_main.getString("trn_type").equals("L")) {
					/**
					 * credit to loan account loan recovery gl
					 */
					rs_trn = stmt_trn
							.executeQuery("select max(trn_seq) from LoanTransaction where ac_type='"
									+ dm.getLoanAcType()
									+ "' and ac_no="
									+ dm.getLoanAccno());
					rs_trn.next();
					int ln_trn_seq = rs_trn.getInt(1) + 1;
					PreparedStatement pstmt = conn
							.prepareStatement("insert into LoanTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					pstmt.setString(1, dm.getLoanAcType());
					pstmt.setInt(2, dm.getLoanAccno());
					pstmt.setInt(3, ln_trn_seq);
					pstmt.setString(4, getSysDate());
					pstmt.setString(5, "R");
					pstmt.setDouble(6, rs_main.getDouble("dep_paid"));
					pstmt.setString(7, "T");
					pstmt.setString(8, vtml);
					pstmt.setString(9, null);
					pstmt.setString(10, dm.getAccType() + " " + dm.getAccNo());
					pstmt.setString(11, null);
					pstmt.setString(12, "C");
					pstmt.setString(13, commonLocal.getFutureDayDate(
							getSysDate(), -1));
					pstmt.setDouble(14, rs_main.getDouble("dep_paid")
							- dm.getInterestPaid());
					pstmt.setDouble(15, dm.getInterestPaid());
					pstmt.setDouble(16, 0);
					pstmt.setDouble(17, 0);
					pstmt.setDouble(18, 0);
					pstmt.setDouble(19, 0);
					pstmt.setString(20, vtml);
					pstmt.setString(21, vuser);
					pstmt.setString(22, dm.userverifier.getVerDate());
					pstmt.setString(23, vtml);
					pstmt.setString(24, vuser);
					pstmt.setString(25, dm.userverifier.getVerDate());
					pstmt.executeUpdate();

					dpcattype = 1;
					rs_trn = stmt_trn
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type ='"
									+ dm.getLoanAcType()
									+ "' and trn_type='R' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					rs_trn.next();
					GLTransObject trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_trn.getString("gk.gl_type"));
					trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
					trnobj.setTrnMode("T");
					trnobj.setAmount((rs_main.getDouble("dep_paid") - dm
							.getInterestPaid())
							* rs_trn.getInt("mult_by"));
					trnobj.setCdind("C");
					trnobj.setAccType(dm.getLoanAcType());
					trnobj.setAccNo(String.valueOf(dm.getLoanAccno()));
					trnobj.setTrnType("R");
					trnobj.setRefNo(0);
					trnobj.setTrnSeq(ln_trn_seq);
					trnobj.setVtml(vtml);
					trnobj.setVid(vuser);
					trnobj.setVDate(dm.userverifier.getVerDate());
					commonLocal.storeGLTransaction(trnobj);

					dpcattype = 2;
					rs_trn = stmt_trn
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type ='"
									+ dm.getLoanAcType()
									+ "' and trn_type='R' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					rs_trn.next();
					trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_trn.getString("gk.gl_type"));
					trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
					trnobj.setTrnMode("T");
					trnobj.setAmount((dm.getInterestPaid())
							* rs_trn.getInt("mult_by"));
					trnobj.setCdind("C");
					trnobj.setAccType(dm.getLoanAcType());
					trnobj.setAccNo(String.valueOf(dm.getLoanAccno()));
					trnobj.setTrnType("R");
					trnobj.setRefNo(0);
					trnobj.setTrnSeq(ln_trn_seq);
					trnobj.setVtml(vtml);
					trnobj.setVid(vuser);
					trnobj.setVDate(dm.userverifier.getVerDate());
					commonLocal.storeGLTransaction(trnobj);

					stmt_trn.executeUpdate("update LoanMaster set close_date='"
							+ getSysDate() + "' where ac_type='"
							+ dm.getLoanAcType() + "' and ac_no="
							+ dm.getLoanAccno());

					dpcattype = olddpcat;
					if (dpcattype == 0)
						dpcattype = 1;
					else
						dpcattype = 2;
					rs_trn = stmt_trn
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type = "
									+ modcode
									+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					rs_trn.next();
					trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_trn.getString("gk.gl_type"));
					trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
					trnobj.setTrnMode("T");
					trnobj.setAmount((dep_amount) * rs_trn.getInt("mult_by"));
					trnobj.setCdind("D");
					trnobj.setAccType(dm.getAccType());
					trnobj.setAccNo(String.valueOf(dm.getAccNo()));
					trnobj.setTrnType("P");
					trnobj.setRefNo(0);
					trnobj.setTrnSeq(trnseq);
					trnobj.setVtml(vtml);
					trnobj.setVid(vuser);
					trnobj.setVDate(dm.userverifier.getVerDate());
					commonLocal.storeGLTransaction(trnobj);

					dpcattype += 2;
					rs_trn = stmt_trn
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type = "
									+ modcode
									+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					rs_trn.next();
					trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_trn.getString("gk.gl_type"));
					trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
					trnobj.setTrnMode("T");
					trnobj.setAmount((interest_amount)
							* rs_trn.getInt("mult_by"));
					trnobj.setCdind("D");
					trnobj.setAccType(dm.getAccType());
					trnobj.setAccNo(String.valueOf(dm.getAccNo()));
					trnobj.setTrnType("P");
					trnobj.setRefNo(0);
					trnobj.setTrnSeq(trnseq);
					trnobj.setVtml(vtml);
					trnobj.setVid(vuser);
					trnobj.setVDate(dm.userverifier.getVerDate());
					commonLocal.storeGLTransaction(trnobj);

					stmt_trn
							.executeUpdate("update DepositTransaction set trn_type='P',ve_tml='"
									+ vtml
									+ "',ve_user='"
									+ vuser
									+ "',ve_date = '"
									+ dm.userverifier.getVerDate()
									+ "' where trn_type='L' and ac_type='"
									+ dm.getAccType()
									+ "' and ac_no="
									+ dm.getAccNo());
				} else {
					if (dm.getInterestMode().equalsIgnoreCase("P")) {
						PayOrderObject po = new PayOrderObject();
						po.setPOType("P");
						po.setPOCustType(0);
						po.setPOPayee(dm.getName());
						po.setPOAccType(dm.getAccType());
						po.setPOAccNo(dm.getAccNo());
						po.setPOGlCode(0);
						po.setPOAmount(rs_main.getDouble("dep_paid")
								+ rs_main.getDouble("int_paid"));
						po.setCommissionAmount(0);
						po.uv.setUserTml(vtml);
						po.uv.setUserId(vuser);
						po.uv.setUserDate(dm.userverifier.getVerDate());
						po.uv.setVerTml(vtml);
						po.uv.setVerId(vuser);
						po.uv.setVerDate(dm.userverifier.getVerDate());

						int_return_value = commonLocal.storePayOrder(po);
						stmt_trn
								.executeUpdate("update DepositTransaction set ref_no="
										+ int_return_value
										+ ",trn_narr='By PO,PO no="
										+ int_return_value
										+ "' where trn_type='E' and ac_type='"
										+ dm.getAccType()
										+ "' and ac_no="
										+ dm.getAccNo()
										+ " and trn_seq="
										+ trnseq);
						/**
						 * PayOrder Credit
						 */
						// rs_trn = stmt_trn.executeQuery("select
						// gk.gl_code,mult_by,gk.gl_type from GLKeyParam
						// gk,GLPost gp where code = 1 and gk.ac_type = 1999003
						// and trn_type='P' and cr_dr='C' and gk.ac_type =
						// gp.ac_type and gp.gl_code = gk.gl_code");
						rs_trn = stmt_trn
								.executeQuery("select * from GLKeyParam where ac_type=1016001 and code=1");
						rs_trn.next();
						GLTransObject trnobj = new GLTransObject();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_trn.getString("gl_type"));
						trnobj.setGLCode(rs_trn.getString("gl_code"));
						trnobj.setTrnMode("T");
						trnobj
								.setAmount((rs_main.getDouble("dep_paid") + rs_main
										.getDouble("int_paid")));
						trnobj.setCdind("C");
						trnobj.setAccType(dm.getAccType());
						trnobj.setAccNo(String.valueOf(dm.getAccNo()));
						trnobj.setTrnType("P");
						trnobj.setRefNo(0);
						trnobj.setTrnSeq(trnseq);
						trnobj.setVtml(vtml);
						trnobj.setVid(vuser);
						trnobj.setVDate(dm.userverifier.getVerDate());
						commonLocal.storeGLTransaction(trnobj);
					} else if (dm.getInterestMode().equalsIgnoreCase("T")) {
						AccountTransObject am = new AccountTransObject();
						// StringTokenizer st = new
						// StringTokenizer(dm.getTransferType()," ");
						// String accty = st.nextToken();
						// int accno= Integer.parseInt(st.nextToken());
						am.setAccType(dm.getTransferAccType());
						am.setAccNo(dm.getTransferAccno());
						am.setTransDate(getSysDate());
						am.setTransType("R");
						am.setTransAmount(rs_main.getDouble("dep_paid")
								+ rs_main.getDouble("int_paid"));
						am.setTransMode("T");
						am.setTransSource(vtml);
						am.setCdInd("C");
						am.setChqDDNo(0);
						am.setChqDDDate("");
						am.setTransNarr(dm.getAccType() + " "
								+ String.valueOf(dm.getAccNo()));
						am.setRef_No(0);
						am.setPayeeName(dm.getName());
						am.setCloseBal(rs_main.getDouble("dep_paid")
								+ rs_main.getDouble("int_paid"));
						am.setLedgerPage(0);
						am.uv.setUserTml(vtml);
						am.uv.setUserId(vuser);
						am.uv.setUserDate(dm.userverifier.getVerDate());
						am.uv.setVerTml(vtml);
						am.uv.setVerId(vuser);
						am.uv.setVerDate(dm.userverifier.getVerDate());
						int_return_value = commonLocal
								.storeAccountTransaction(am);

						stmt_trn
								.executeUpdate("update DepositTransaction set trn_narr='"
										+ dm.getTransferAccType()
										+ " "
										+ dm.getTransferAccno()
										+ "' where ac_type='"
										+ dm.getAccType()
										+ "' and ac_no="
										+ dm.getAccNo()
										+ " and trn_seq="
										+ rs_main.getInt("trn_seq"));
					} else if (dm.getInterestMode().equalsIgnoreCase("C")) {
						int_return_value = commonLocal.getModulesColumn(
								"lst_voucher_scroll_no", "1019000");

						PreparedStatement pstmt_insert = conn
								.prepareStatement("insert into DayCash (scroll_no,trn_type,trn_date,ac_type,ac_no,csh_amt,cd_ind,vch_type,attached,vch_no,de_user,de_tml,de_date,ve_user,ve_tml,ve_date,name) values (0,'P',?,?,?,?,'C','P','F',?,?,?,?,?,?,?,?)");
						pstmt_insert.setString(1, getSysDate());
						pstmt_insert.setString(2, dm.getAccType());
						pstmt_insert.setInt(3, dm.getAccNo());
						pstmt_insert.setDouble(4, rs_main.getDouble("dep_paid")
								+ rs_main.getDouble("int_paid"));
						pstmt_insert.setInt(5, int_return_value);
						pstmt_insert.setString(6, vuser);
						pstmt_insert.setString(7, vtml);
						pstmt_insert.setString(8, dm.userverifier.getVerDate());
						pstmt_insert.setString(9, vuser);
						pstmt_insert.setString(10, vtml);
						pstmt_insert
								.setString(11, dm.userverifier.getVerDate());
						pstmt_insert.setString(12, dm.getName());
						pstmt_insert.executeUpdate();
						stmt_trn
								.executeUpdate("update DepositTransaction set ref_no="
										+ int_return_value
										+ ",trn_narr='By Cash vch no"
										+ int_return_value
										+ "' where ac_type='"
										+ dm.getAccType()
										+ "' and ac_no="
										+ dm.getAccNo()
										+ " and trn_seq="
										+ rs_main.getInt("trn_seq"));
						/**
						 * cash payment gl
						 */
						rs_trn = stmt_trn
								.executeQuery("select * from GLKeyParam where ac_type=1019001 and code=1");
						rs_trn.next();
						GLTransObject trnobj = new GLTransObject();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_trn.getString("gl_type"));
						trnobj.setGLCode(rs_trn.getString("gl_code"));
						trnobj.setTrnMode("C");
						trnobj
								.setAmount((rs_main.getDouble("dep_paid") + rs_main
										.getDouble("int_paid")));
						trnobj.setCdind("C");
						trnobj.setAccType(dm.getAccType());
						trnobj.setAccNo(String.valueOf(dm.getAccNo()));
						trnobj.setTrnType("P");
						trnobj.setRefNo(0);
						trnobj.setTrnSeq(trnseq);
						trnobj.setVtml(vtml);
						trnobj.setVid(vuser);
						trnobj.setVDate(dm.userverifier.getVerDate());
						commonLocal.storeGLTransaction(trnobj);
					}

					dpcattype = olddpcat;
					if (dpcattype == 0)
						dpcattype = 1;
					else
						dpcattype = 2;
					rs_trn = stmt_trn
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type = "
									+ modcode
									+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					rs_trn.next();
					GLTransObject trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_trn.getString("gk.gl_type"));
					trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
					if (dm.getTransferType().equalsIgnoreCase("C"))
						trnobj.setTrnMode("C");
					else
						trnobj.setTrnMode("T");
					trnobj.setAmount((dep_amount) * rs_trn.getInt("mult_by"));
					trnobj.setCdind("D");
					trnobj.setAccType(dm.getAccType());
					trnobj.setAccNo(String.valueOf(dm.getAccNo()));
					trnobj.setTrnType("P");
					trnobj.setRefNo(0);
					trnobj.setTrnSeq(trnseq);
					trnobj.setVtml(vtml);
					trnobj.setVid(vuser);
					trnobj.setVDate(dm.userverifier.getVerDate());
					commonLocal.storeGLTransaction(trnobj);

					dpcattype += 2;
					rs_trn = stmt_trn
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type = "
									+ modcode
									+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					rs_trn.next();
					trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_trn.getString("gk.gl_type"));
					trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
					if (dm.getTransferType().equalsIgnoreCase("C"))
						trnobj.setTrnMode("C");
					else
						trnobj.setTrnMode("T");
					trnobj.setAmount((interest_amount)
							* rs_trn.getInt("mult_by"));
					trnobj.setCdind("D");
					trnobj.setAccType(dm.getAccType());
					trnobj.setAccNo(String.valueOf(dm.getAccNo()));
					trnobj.setTrnType("P");
					trnobj.setRefNo(0);
					trnobj.setTrnSeq(trnseq);
					trnobj.setVtml(vtml);
					trnobj.setVid(vuser);
					trnobj.setVDate(dm.userverifier.getVerDate());
					commonLocal.storeGLTransaction(trnobj);
				}
			}

			else {

				// System.out.println("trnseq===before==>>>>>"+rs_main.getInt("trn_seq"));

				trnseq = rs_main.getInt("trn_seq");

				System.out.println("trnseq===after==>>>>>" + trnseq);
				System.out.println(" olddpcat in Esle block---> " + olddpcat);
				dpcattype = olddpcat;
				/**
				 * credit to loan account loan recovery gl
				 */
				rs_trn = stmt_trn
						.executeQuery("select max(trn_seq) from LoanTransaction where ac_type='"
								+ dm.getLoanAcType()
								+ "' and ac_no="
								+ dm.getLoanAccno());
				rs_trn.next();
				int ln_trn_seq = rs_trn.getInt(1) + 1;
				PreparedStatement pstmt = conn
						.prepareStatement("insert into LoanTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				pstmt.setString(1, dm.getLoanAcType());
				pstmt.setInt(2, dm.getLoanAccno());
				pstmt.setInt(3, ln_trn_seq);
				pstmt.setString(4, getSysDate());
				pstmt.setString(5, "R");
				pstmt.setDouble(6, rs_main.getDouble("dep_paid"));
				pstmt.setString(7, "T");
				pstmt.setString(8, vtml);
				pstmt.setString(9, null);
				pstmt.setString(10, dm.getAccType() + " " + dm.getAccNo());
				pstmt.setString(11, null);
				pstmt.setString(12, "C");
				pstmt.setString(13, commonLocal.getFutureDayDate(getSysDate(),
						-1));
				pstmt.setDouble(14, rs_main.getDouble("dep_paid")
						- dm.getInterestPaid());
				pstmt.setDouble(15, dm.getInterestPaid());
				pstmt.setDouble(16, 0);
				pstmt.setDouble(17, 0);
				pstmt.setDouble(18, 0);
				pstmt.setDouble(19, 0);
				pstmt.setString(20, vtml);
				pstmt.setString(21, vuser);
				pstmt.setString(22, dm.userverifier.getVerDate());
				pstmt.setString(23, vtml);
				pstmt.setString(24, vuser);
				pstmt.setString(25, dm.userverifier.getVerDate());
				pstmt.executeUpdate();

				dpcattype = 1;
				System.out.println("dm.getLoanAcType() " + dm.getLoanAcType());
				System.out.println("dm.getLoanAcType() " + dm.getLoanAcType());

				rs_trn = stmt_trn
						.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
								+ dpcattype
								+ " and gk.ac_type ='"
								+ dm.getLoanAcType()
								+ "' and trn_type='R' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
				rs_trn.next();
				GLTransObject trnobj = new GLTransObject();
				trnobj.setTrnDate(getSysDate());
				trnobj.setGLType(rs_trn.getString("gk.gl_type"));
				trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
				trnobj.setTrnMode("T");
				trnobj.setAmount((rs_main.getDouble("dep_paid") - dm
						.getInterestPaid())
						* rs_trn.getInt("mult_by"));
				trnobj.setCdind("C");
				trnobj.setAccType(dm.getLoanAcType());
				trnobj.setAccNo(String.valueOf(dm.getLoanAccno()));
				trnobj.setTrnType("R");
				trnobj.setRefNo(0);
				trnobj.setTrnSeq(ln_trn_seq);
				trnobj.setVtml(vtml);
				trnobj.setVid(vuser);
				trnobj.setVDate(dm.userverifier.getVerDate());
				commonLocal.storeGLTransaction(trnobj);

				dpcattype = 2;
				rs_trn = stmt_trn
						.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
								+ dpcattype
								+ " and gk.ac_type ='"
								+ dm.getLoanAcType()
								+ "' and trn_type='R' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
				rs_trn.next();
				trnobj = new GLTransObject();
				trnobj.setTrnDate(getSysDate());
				trnobj.setGLType(rs_trn.getString("gk.gl_type"));
				trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
				trnobj.setTrnMode("T");
				trnobj.setAmount((dm.getInterestPaid())
						* rs_trn.getInt("mult_by"));
				trnobj.setCdind("C");
				trnobj.setAccType(dm.getLoanAcType());
				trnobj.setAccNo(String.valueOf(dm.getLoanAccno()));
				trnobj.setTrnType("R");
				trnobj.setRefNo(0);
				trnobj.setTrnSeq(ln_trn_seq);
				trnobj.setVtml(vtml);
				trnobj.setVid(vuser);
				trnobj.setVDate(dm.userverifier.getVerDate());
				commonLocal.storeGLTransaction(trnobj);

				stmt_trn.executeUpdate("update LoanMaster set close_date='"
						+ getSysDate() + "' where ac_type='"
						+ dm.getLoanAcType() + "' and ac_no="
						+ dm.getLoanAccno());

				if (dpcattype == 0)
					dpcattype = 1;
				else
					dpcattype = 2;
				double loan_interest_amount = 0;
				System.out.println("interest_amount--> " + interest_amount);
				if (interest_amount > 0) {
					dpcattype += 2;
					if (interest_amount > rs_main.getDouble("dep_paid")) {
						loan_interest_amount = rs_main.getDouble("dep_paid");
						interest_amount -= rs_main.getDouble("dep_paid");
					} else {
						loan_interest_amount = interest_amount;
						interest_amount = 0;
					}
					rs_trn = stmt_trn
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type = "
									+ modcode
									+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					rs_trn.next();
					trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_trn.getString("gk.gl_type"));
					trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
					trnobj.setTrnMode("T");
					trnobj.setAmount((loan_interest_amount)
							* rs_trn.getInt("mult_by"));
					trnobj.setCdind("D");
					trnobj.setAccType(dm.getAccType());
					trnobj.setAccNo(String.valueOf(dm.getAccNo()));
					trnobj.setTrnType("P");
					trnobj.setRefNo(0);
					trnobj.setTrnSeq(trnseq);
					trnobj.setVtml(vtml);
					trnobj.setVid(vuser);
					trnobj.setVDate(dm.userverifier.getVerDate());
					commonLocal.storeGLTransaction(trnobj);

					dpcattype -= 2;
					loan_interest_amount = rs_main.getDouble("dep_paid")
							- loan_interest_amount;
				} else {
					loan_interest_amount = rs_main.getDouble("dep_paid");
				}
				System.out.println("loan_interest_amount---> "
						+ loan_interest_amount);
				System.out.println("&&&&dpcattype&&&---> " + dpcattype);
				if (loan_interest_amount > 0) {
					rs_trn = stmt_trn
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type = "
									+ modcode
									+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					rs_trn.next();
					trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_trn.getString("gk.gl_type"));
					trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
					trnobj.setTrnMode("T");
					trnobj.setAmount((loan_interest_amount)
							* rs_trn.getInt("mult_by"));
					trnobj.setCdind("D");
					trnobj.setAccType(dm.getAccType());
					trnobj.setAccNo(String.valueOf(dm.getAccNo()));
					trnobj.setTrnType("P");
					trnobj.setRefNo(0);
					trnobj.setTrnSeq(trnseq);
					trnobj.setVtml(vtml);
					trnobj.setVid(vuser);
					trnobj.setVDate(dm.userverifier.getVerDate());
					commonLocal.storeGLTransaction(trnobj);
				}
				stmt_trn
						.executeUpdate("update DepositTransaction set trn_type='P',ve_tml='"
								+ vtml
								+ "',ve_user='"
								+ vuser
								+ "',ve_date = '"
								+ dm.userverifier.getVerDate()
								+ "' where trn_type='L' and ac_type='"
								+ dm.getAccType()
								+ "' and ac_no="
								+ dm.getAccNo());

				rs_main.next();
				trnseq = rs_main.getInt("trn_seq");

				if (dm.getInterestMode().equalsIgnoreCase("P")) {
					PayOrderObject po = new PayOrderObject();
					po.setPOType("P");
					po.setPOCustType(0);
					po.setPOPayee(dm.getName());
					po.setPOAccType(dm.getAccType());
					po.setPOAccNo(dm.getAccNo());
					po.setPOGlCode(0);
					po.setPOAmount(rs_main.getDouble("dep_paid")
							+ rs_main.getDouble("int_paid"));
					po.setCommissionAmount(0);
					po.uv.setUserTml(vtml);
					po.uv.setUserId(vuser);
					po.uv.setUserDate(dm.userverifier.getVerDate());
					po.uv.setVerTml(vtml);
					po.uv.setVerId(vuser);
					po.uv.setVerDate(dm.userverifier.getVerDate());
					int_return_value = commonLocal.storePayOrder(po);
					stmt_main
							.executeUpdate("update DepositTransaction set ref_no="
									+ int_return_value
									+ ",trn_narr='By PO,PO no="
									+ int_return_value
									+ "' where trn_type='E' and ac_type='"
									+ dm.getAccType()
									+ "' and ac_no="
									+ dm.getAccNo() + " and trn_seq=" + trnseq);
					/**
					 * PayOrder Credit
					 */
					rs_trn = stmt_trn
							.executeQuery("select * from GLKeyParam where ac_type=1016001 and code=1");
					rs_trn.next();
					trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_trn.getString("gl_type"));
					trnobj.setGLCode(rs_trn.getString("gl_code"));
					trnobj.setTrnMode("T");
					trnobj.setAmount((rs_main.getDouble("dep_paid") + rs_main
							.getDouble("int_paid")));
					trnobj.setCdind("C");
					trnobj.setAccType(dm.getAccType());
					trnobj.setAccNo(String.valueOf(dm.getAccNo()));
					trnobj.setTrnType("P");
					trnobj.setRefNo(0);
					trnobj.setTrnSeq(trnseq);
					trnobj.setVtml(vtml);
					trnobj.setVid(vuser);
					trnobj.setVDate(dm.userverifier.getVerDate());
					commonLocal.storeGLTransaction(trnobj);

				} else if (dm.getInterestMode().equalsIgnoreCase("T")) {
					AccountTransObject am = new AccountTransObject();
					/*
					 * StringTokenizer st = new
					 * StringTokenizer(dm.getTransferType()," "); String accty =
					 * st.nextToken(); int accno=
					 * Integer.parseInt(st.nextToken());
					 */
					am.setAccType(dm.getTransferAccType());
					am.setAccNo(dm.getTransferAccno());
					am.setTransDate(getSysDate());
					am.setTransType("R");
					am.setTransAmount(rs_main.getDouble("dep_paid")
							+ rs_main.getDouble("int_paid"));
					am.setTransMode("T");
					am.setTransSource(vtml);
					am.setCdInd("C");
					am.setChqDDNo(0);
					am.setChqDDDate("");
					am.setTransNarr(dm.getAccType() + " "
							+ String.valueOf(dm.getAccNo()));
					am.setRef_No(0);
					am.setPayeeName(dm.getName());
					am.setCloseBal(rs_main.getDouble("dep_paid")
							+ rs_main.getDouble("int_paid"));
					am.setLedgerPage(0);
					am.uv.setUserTml(vtml);
					am.uv.setUserId(vuser);
					am.uv.setUserDate(dm.userverifier.getVerDate());
					am.uv.setVerTml(vtml);
					am.uv.setVerId(vuser);
					am.uv.setVerDate(dm.userverifier.getVerDate());
					int_return_value = commonLocal.storeAccountTransaction(am);

					stmt_trn
							.executeUpdate("update DepositTransaction set trn_narr='"
									+ dm.getTransferAccType()
									+ " "
									+ dm.getTransferAccno()
									+ "' where ac_type='"
									+ dm.getAccType()
									+ "' and ac_no="
									+ dm.getAccNo()
									+ " and trn_seq="
									+ rs_main.getInt("trn_seq"));
				} else if (dm.getInterestMode().equalsIgnoreCase("C")) {
					int_return_value = commonLocal.getModulesColumn(
							"lst_voucher_scroll_no", "1019000");
					PreparedStatement pstmt_insert = conn
							.prepareStatement("insert into DayCash (scroll_no,trn_type,trn_date,ac_type,ac_no,csh_amt,cd_ind,vch_type,attached,vch_no,de_user,de_tml,de_date,ve_user,ve_tml,ve_date,name) values (0,'P',?,?,?,?,'C','P','F',?,?,?,?,?,?,?,?)");
					pstmt_insert.setString(1, getSysDate());
					pstmt_insert.setString(2, dm.getAccType());
					pstmt_insert.setInt(3, dm.getAccNo());
					pstmt_insert.setDouble(4, rs_main.getDouble("dep_paid")
							+ rs_main.getDouble("int_paid"));
					pstmt_insert.setInt(5, int_return_value);
					pstmt_insert.setString(6, vuser);
					pstmt_insert.setString(7, vtml);
					pstmt_insert.setString(8, dm.userverifier.getVerDate());
					pstmt_insert.setString(9, vuser);
					pstmt_insert.setString(10, vtml);
					pstmt_insert.setString(11, dm.userverifier.getVerDate());
					pstmt_insert.setString(12, dm.getName());
					pstmt_insert.executeUpdate();
					stmt_trn
							.executeUpdate("update DepositTransaction set ref_no="
									+ int_return_value
									+ ",trn_narr='By Cash vch no"
									+ int_return_value
									+ "' where ac_type='"
									+ dm.getAccType()
									+ "' and ac_no="
									+ dm.getAccNo()
									+ " and trn_seq="
									+ rs_main.getInt("trn_seq"));
					/**
					 * cash payment gl
					 */
					rs_trn = stmt_trn
							.executeQuery("select * from GLKeyParam where ac_type=1019001 and code=1");
					rs_trn.next();
					trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_trn.getString("gl_type"));
					trnobj.setGLCode(rs_trn.getString("gl_code"));
					trnobj.setTrnMode("C");
					trnobj.setAmount((rs_main.getDouble("dep_paid") + rs_main
							.getDouble("int_paid")));
					trnobj.setCdind("C");
					trnobj.setAccType(dm.getAccType());
					trnobj.setAccNo(String.valueOf(dm.getAccNo()));
					trnobj.setTrnType("P");
					trnobj.setRefNo(0);
					trnobj.setTrnSeq(trnseq);
					trnobj.setVtml(vtml);
					trnobj.setVid(vuser);
					trnobj.setVDate(dm.userverifier.getVerDate());
					commonLocal.storeGLTransaction(trnobj);
				}

				/**
				 * check for int_paid -ve or +ve if paid == 0 directly debit
				 * from FD account gl if paid >0 debit from FD acc and FD
				 * payable Gl
				 */
				if (rs_main.getDouble("int_paid") == 0) {
					/**
					 * debit from FD account GL
					 */
					dpcattype = olddpcat;
					if (dpcattype == 0)
						dpcattype = 1;
					else
						dpcattype = 2;
					rs_trn = stmt_trn
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type = "
									+ modcode
									+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					rs_trn.next();
					trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_trn.getString("gk.gl_type"));
					trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
					if (dm.getTransferType().equalsIgnoreCase("C"))
						trnobj.setTrnMode("C");
					else
						trnobj.setTrnMode("T");
					trnobj.setAmount((rs_main.getDouble("dep_paid") + rs_main
							.getDouble("int_paid"))
							* rs_trn.getInt("mult_by"));
					trnobj.setCdind("D");
					trnobj.setAccType(dm.getAccType());
					trnobj.setAccNo(String.valueOf(dm.getAccNo()));
					trnobj.setTrnType("P");
					trnobj.setRefNo(0);
					trnobj.setTrnSeq(trnseq);
					trnobj.setVtml(vtml);
					trnobj.setVid(vuser);
					trnobj.setVDate(dm.userverifier.getVerDate());
					commonLocal.storeGLTransaction(trnobj);
				} else if (rs_main.getDouble("int_paid") > 0) {
					/**
					 * debit FD acc Gl
					 */
					dpcattype = olddpcat;
					if (dpcattype == 0)
						dpcattype = 1;
					else
						dpcattype = 2;
					rs_trn = stmt_trn
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type = "
									+ modcode
									+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					rs_trn.next();
					trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_trn.getString("gk.gl_type"));
					trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
					if (dm.getTransferType().equalsIgnoreCase("C"))
						trnobj.setTrnMode("C");
					else
						trnobj.setTrnMode("T");
					trnobj.setAmount((rs_main.getDouble("dep_paid"))
							* rs_trn.getInt("mult_by"));
					trnobj.setCdind("D");
					trnobj.setAccType(dm.getAccType());
					trnobj.setAccNo(String.valueOf(dm.getAccNo()));
					trnobj.setTrnType("P");
					trnobj.setRefNo(0);
					trnobj.setTrnSeq(trnseq);
					trnobj.setVtml(vtml);
					trnobj.setVid(vuser);
					trnobj.setVDate(dm.userverifier.getVerDate());
					commonLocal.storeGLTransaction(trnobj);

					dpcattype += 2;
					rs_trn = stmt_trn
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type = "
									+ modcode
									+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					rs_trn.next();
					trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_trn.getString("gk.gl_type"));
					trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
					if (dm.getTransferType().equalsIgnoreCase("C"))
						trnobj.setTrnMode("C");
					else
						trnobj.setTrnMode("T");
					trnobj.setAmount((rs_main.getDouble("int_paid"))
							* rs_trn.getInt("mult_by"));
					trnobj.setCdind("D");
					trnobj.setAccType(dm.getAccType());
					trnobj.setAccNo(String.valueOf(dm.getAccNo()));
					trnobj.setTrnType("P");
					trnobj.setRefNo(0);
					trnobj.setTrnSeq(trnseq);
					trnobj.setVtml(vtml);
					trnobj.setVid(vuser);
					trnobj.setVDate(dm.userverifier.getVerDate());
					commonLocal.storeGLTransaction(trnobj);
				}
			}
			stmt_trn
					.executeUpdate("update DepositTransaction set trn_type='P',ve_tml='"
							+ vtml
							+ "',ve_user='"
							+ vuser
							+ "',ve_date = '"
							+ dm.userverifier.getVerDate()
							+ "' where trn_type='E' and ac_type='"
							+ dm.getAccType()
							+ "' and ac_no="
							+ dm.getAccNo()
							+ " and trn_seq=" + trnseq);
			stmt_main.executeUpdate("update DepositMaster set close_ind="
					+ closeind + ",close_date = '" + getSysDate()
					+ "',int_paid_date='" + getSysDate() + "',int_upto_date='"
					+ commonLocal.getFutureDayDate(dm.getClosedt(), -1)
					+ "' where ac_no=" + dm.getAccNo() + " and ac_type='"
					+ dm.getAccType() + "'");
		} catch (SQLException ve) {
			ve.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordsNotInsertedException();
		} catch (CreateException e) {
			e.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordsNotInsertedException();
		} catch (Exception ex) {
			ex.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordsNotInsertedException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			commonLocal = null;
		}
		System.out.println("int_return_value-------END of VERFy Bean---> "
				+ int_return_value);
		return int_return_value;
	}

	public double getReInvestmentInterestAmount(String depdate, double amt,
			double intrate) {
		double int_amt = 0;
		Connection conn = null;
		try {
			conn = getConnection();
			commonLocal = commonLocalHome.create();
			ResultSet rs_int = null;
			Statement stmt_int = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			String today = getSysDate();
			while (true) {
				rs_int = stmt_int.executeQuery("select DATE_FORMAT(DATE_ADD('"
						+ Validations.convertYMD(depdate)
						+ "',INTERVAL 3 MONTH),'%d/%m/%Y')");
				rs_int.next();
				if (Validations.dayCompare(rs_int.getString(1), today) > 0) {
					int_amt += Math
							.round((amt * intrate * Validations.dayCompare(
									depdate, rs_int.getString(1))) / (36500));
					depdate = rs_int.getString(1);
				} else {
					int_amt += Math.round((amt * intrate * Validations
							.dayCompare(depdate, today)) / (36500));
					break;
				}
			}
		} catch (SQLException p) {
			p.printStackTrace();
			return 0;
		} catch (CreateException e) {
			e.printStackTrace();
			return 0;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			commonLocal = null;
		}
		return int_amt;
	}

	public DepositMasterObject getFDClosureData(String acctype, int accno,
			int int_type) throws AccountNotFoundException,
			RecordsNotFoundException {
		System.out.println("int_type == " + int_type);
		System.out.println("Account Type == >" + acctype);
		System.out.println("Account No == >" + accno);
		DepositMasterObject depositmasterobject = null;
		ResultSet rs_main = null;
		Connection conn = null;
		try {
			conn = getConnection();
			commonLocal = commonLocalHome.create();
			Statement stmt_main = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs_main = stmt_main
					.executeQuery("select dm.*,IFNULL(dm.ve_user,'') as veuser,concat_ws(' ',fname,mname,lname) as name,custtype,sub_category from DepositMaster dm,CustomerMaster cm where dm.cid=cm.cid and dm.ac_type='"
							+ acctype + "' and dm.ac_no=" + accno);
			rs_main.last();
			if (rs_main.getRow() == 0)
				throw new AccountNotFoundException();
			rs_main.first();
			depositmasterobject = new DepositMasterObject();
			depositmasterobject.setAccType(acctype);
			depositmasterobject.setAccNo(accno);
			depositmasterobject.setName(rs_main.getString("name"));
			depositmasterobject.setDepDate(rs_main.getString("dm.dep_date"));
			depositmasterobject.setMaturityDate(rs_main
					.getString("dm.mat_date"));
			depositmasterobject.setReceiptno(rs_main.getInt("dm.rct_no"));
			depositmasterobject.setDepositAmt(rs_main.getDouble("dm.dep_amt"));
			depositmasterobject.setMaturityAmt(rs_main.getDouble("dm.mat_amt"));
			depositmasterobject
					.setInterestRate(rs_main.getFloat("dm.int_rate"));
			depositmasterobject.setCloseInd(rs_main.getInt("dm.close_ind"));
			depositmasterobject.setInterestUpto(rs_main
					.getString("dm.int_upto_date"));
			depositmasterobject.setInterestMode(rs_main
					.getString("dm.int_mode"));
			depositmasterobject.setTransferType(rs_main
					.getString("dm.trf_ac_type"));
			depositmasterobject
					.setTransferAccno(rs_main.getInt("dm.trf_ac_no"));
			depositmasterobject.setCustomerId(rs_main.getInt("dm.cid"));
			depositmasterobject.setExtraRateType(rs_main
					.getInt("dm.extra_rate_type"));
			depositmasterobject.setDPType(rs_main.getInt("custtype"));
			depositmasterobject.setCategory(rs_main.getInt("sub_category"));
			depositmasterobject
					.setInterestFrq(rs_main.getString("dm.int_freq"));
			depositmasterobject.setNoofJtHldrs(rs_main.getInt("dm.no_jt_hldr"));
			depositmasterobject
					.setMailingAddress(rs_main.getInt("dm.add_type"));
			depositmasterobject.setNomineeRegNo(rs_main.getInt("dm.nom_no"));
			depositmasterobject.setIntroAccType(rs_main
					.getString("dm.intr_ac_type"));
			depositmasterobject.setIntroAccno(rs_main.getInt("dm.intr_ac_no"));
			depositmasterobject.setRenType(rs_main.getString("dm.ren_ac_type"));
			depositmasterobject.setRenewalAccno(rs_main.getInt("dm.ren_ac_no"));
			depositmasterobject.setExcessAmt(rs_main.getDouble("dm.exc_amt"));
			depositmasterobject.setReceivedBy(rs_main.getString("dm.rcvd_by"));
			depositmasterobject.setReceivedAccType(rs_main
					.getString("dm.rcvd_ac_type"));
			depositmasterobject.setReceivedAccno(rs_main
					.getInt("dm.rcvd_ac_no"));
			depositmasterobject.setAutoRenewal(rs_main
					.getString("auto_renewal"));
			depositmasterobject.setVerified(rs_main.getString("veuser"));
			depositmasterobject.setLoanAvailed(rs_main.getString("ln_availed"));
			System.out.println("ln_availed==>"
					+ rs_main.getString("ln_availed"));
			depositmasterobject.setLoanAcType(rs_main.getString("ln_ac_type"));
			depositmasterobject.setLoanAccno(rs_main.getInt("ln_ac_no"));

			if (depositmasterobject.getNoofJtHldrs() > 0) {
				rs_main = stmt_main
						.executeQuery("select cid,addr_type from JointHolders where ac_type='"
								+ depositmasterobject.getAccType()
								+ "' and ac_no='"
								+ depositmasterobject.getAccNo() + "'");
				int j = 0;
				int addrtype[] = null;
				int cids[] = null;
				if (rs_main.next()) {
					rs_main.last();
					addrtype = new int[rs_main.getRow()];
					cids = new int[rs_main.getRow()];
					rs_main.beforeFirst();
				}
				while (rs_main.next()) {
					addrtype[j] = rs_main.getInt(2);
					cids[j++] = rs_main.getInt(1);
				}
				depositmasterobject.setJointAddrType(addrtype);
				depositmasterobject.setJointCid(cids);
			}
			depositmasterobject.setNomineeDetails(commonLocal
					.getNominee(depositmasterobject.getNomineeRegNo()));
			depositmasterobject.setSigObj(commonLocal.getSignatureDetails(
					depositmasterobject.getAccNo(), depositmasterobject
							.getAccType()));
			rs_main = stmt_main
					.executeQuery("select sum(int_amt),sum(int_paid),max(cum_int) from DepositTransaction where ac_type='"
							+ acctype
							+ "' and ac_no="
							+ accno
							+ "  and trn_type != 'E' and trn_type != 'L'");
			if (rs_main.next()) {
				depositmasterobject.setRDBalance(rs_main.getDouble(1));
				depositmasterobject.setInterestPaid(rs_main.getDouble(2));
				depositmasterobject.setCumInterest(rs_main.getDouble(3));
			}
			rs_main = stmt_main
					.executeQuery("select trn_mode,trn_narr from DepositTransaction where ac_type='"
							+ acctype
							+ "' and ac_no="
							+ accno
							+ "  order by trn_seq desc limit 1");
			rs_main.last();
			if (rs_main.getRow() > 0) {
				rs_main.first();
				depositmasterobject.setTransferType(rs_main
						.getString("trn_mode"));
				depositmasterobject.setTrantypetemp(rs_main
						.getString("trn_mode"));
				if (rs_main.getString("trn_mode").equalsIgnoreCase("T")) {
					depositmasterobject.setTransferAccType(rs_main
							.getString("trn_narr"));
				}
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RecordsNotFoundException();
		} catch (CreateException e) {
			e.printStackTrace();
			throw new AccountNotFoundException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			commonLocal = null;
		}
		return depositmasterobject;
	}

	// *****************************************
	public DepositMasterObject getTranDetails(String acctype, int accno,
			int int_type) throws AccountNotFoundException,
			RecordsNotFoundException {
		System.out.println("int_type == " + int_type);
		System.out.println("Account Type == >" + acctype);
		System.out.println("Account No == >" + accno);
		DepositMasterObject depositmasterobject = null;
		ResultSet rs_main = null;
		Connection conn = null;
		try {
			conn = getConnection();
			commonLocal = commonLocalHome.create();
			Statement stmt_main = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs_main = stmt_main
					.executeQuery("select dm.*,IFNULL(dm.ve_user,'') as veuser,concat_ws(' ',fname,mname,lname) as name,custtype,sub_category from DepositMaster dm,CustomerMaster cm where dm.cid=cm.cid and dm.ac_type='"
							+ acctype + "' and dm.ac_no=" + accno);
			rs_main.last();
			if (rs_main.getRow() == 0)
				throw new AccountNotFoundException();
			rs_main.first();
			depositmasterobject = new DepositMasterObject();
			depositmasterobject.setAccType(acctype);
			depositmasterobject.setAccNo(accno);
			depositmasterobject.setName(rs_main.getString("name"));
			depositmasterobject.setDepDate(rs_main.getString("dm.dep_date"));
			depositmasterobject.setMaturityDate(rs_main
					.getString("dm.mat_date"));
			depositmasterobject.setReceiptno(rs_main.getInt("dm.rct_no"));
			depositmasterobject.setDepositAmt(rs_main.getDouble("dm.dep_amt"));
			depositmasterobject.setMaturityAmt(rs_main.getDouble("dm.mat_amt"));
			depositmasterobject
					.setInterestRate(rs_main.getFloat("dm.int_rate"));
			depositmasterobject.setCloseInd(rs_main.getInt("dm.close_ind"));
			depositmasterobject.setInterestUpto(rs_main
					.getString("dm.int_upto_date"));
			depositmasterobject.setInterestMode(rs_main
					.getString("dm.int_mode"));
			depositmasterobject.setTransferType(rs_main
					.getString("dm.trf_ac_type"));
			depositmasterobject
					.setTransferAccno(rs_main.getInt("dm.trf_ac_no"));
			depositmasterobject.setCustomerId(rs_main.getInt("dm.cid"));
			depositmasterobject.setExtraRateType(rs_main
					.getInt("dm.extra_rate_type"));
			depositmasterobject.setDPType(rs_main.getInt("custtype"));
			depositmasterobject.setCategory(rs_main.getInt("sub_category"));
			depositmasterobject
					.setInterestFrq(rs_main.getString("dm.int_freq"));
			depositmasterobject.setNoofJtHldrs(rs_main.getInt("dm.no_jt_hldr"));
			depositmasterobject
					.setMailingAddress(rs_main.getInt("dm.add_type"));
			depositmasterobject.setNomineeRegNo(rs_main.getInt("dm.nom_no"));
			depositmasterobject.setIntroAccType(rs_main
					.getString("dm.intr_ac_type"));
			depositmasterobject.setIntroAccno(rs_main.getInt("dm.intr_ac_no"));
			depositmasterobject.setRenType(rs_main.getString("dm.ren_ac_type"));
			depositmasterobject.setRenewalAccno(rs_main.getInt("dm.ren_ac_no"));
			depositmasterobject.setExcessAmt(rs_main.getDouble("dm.exc_amt"));
			depositmasterobject.setReceivedBy(rs_main.getString("dm.rcvd_by"));
			depositmasterobject.setReceivedAccType(rs_main
					.getString("dm.rcvd_ac_type"));
			depositmasterobject.setReceivedAccno(rs_main
					.getInt("dm.rcvd_ac_no"));
			depositmasterobject.setAutoRenewal(rs_main
					.getString("auto_renewal"));
			depositmasterobject.setVerified(rs_main.getString("veuser"));
			depositmasterobject.setLoanAvailed(rs_main.getString("ln_availed"));
			System.out.println("ln_availed sumanth==>"
					+ rs_main.getString("ln_availed"));
			System.out.println("ln_ac_type sumanth==>"
					+ rs_main.getString("ln_ac_type"));
			depositmasterobject.setLoanAcType(rs_main.getString("ln_ac_type"));
			System.out.println("ln_ac_no sumanth==>"
					+ rs_main.getString("ln_ac_no"));
			depositmasterobject.setLoanAccno(rs_main.getInt("ln_ac_no"));

			if (depositmasterobject.getTransferAccno() > 0) {
				System.out.println("I am inside 6074 in TDBean----> "
						+ depositmasterobject.getTransferAccno());
				rs_main = stmt_main
						.executeQuery("select trf_ac_no,trf_ac_type from DepositMaster where ac_type='"
								+ acctype + "' and ac_no=" + accno + "");
				rs_main.last();
				System.out.println("rs_main===> " + rs_main.getRow());
				rs_main.beforeFirst();
				while (rs_main.next()) {
					depositmasterobject.setTransferAccType(rs_main
							.getString("trf_ac_type"));
					depositmasterobject.setTransferAccno(rs_main
							.getInt("trf_ac_no"));
				}
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RecordsNotFoundException();
		} catch (CreateException e) {
			e.printStackTrace();
			throw new AccountNotFoundException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			commonLocal = null;
		}
		return depositmasterobject;
	}

	public DepositMasterObject getRenewalInformation(String acctype, int accno,
			int int_type) throws AccountNotFoundException,
			RecordsNotFoundException {
		System.out.println("int_type == " + int_type);
		DepositMasterObject depositmasterobject = null;
		ResultSet rs_main = null;
		Connection conn = null;
		try {
			conn = getConnection();
			commonLocal = commonLocalHome.create();
			Statement stmt_main = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs_main = stmt_main
					.executeQuery("select dm.*,IFNULL(dm.ve_user,'') as veuser,concat_ws(' ',ifnull(fname,' '),ifnull(mname,' '),ifnull(lname,' ')) as name,custtype,sub_category from DepositMaster dm,CustomerMaster cm where dm.cid=cm.cid and dm.ac_type='"
							+ acctype + "' and dm.ac_no=" + accno);
			rs_main.last();
			if (rs_main.getRow() == 0)
				throw new AccountNotFoundException();
			rs_main.first();
			depositmasterobject = new DepositMasterObject();
			depositmasterobject.setAccType(acctype);
			depositmasterobject.setAccNo(accno);
			depositmasterobject.setName(rs_main.getString("name"));
			depositmasterobject.setDepDate(rs_main.getString("dm.dep_date"));
			depositmasterobject.setMaturityDate(rs_main
					.getString("dm.mat_date"));
			depositmasterobject.setReceiptno(rs_main.getInt("dm.rct_no"));
			depositmasterobject.setDepositDays(rs_main.getInt("dm.dep_days"));
			depositmasterobject.setDepositAmt(rs_main.getDouble("dm.dep_amt"));
			depositmasterobject.setMaturityAmt(rs_main.getDouble("dm.mat_amt"));
			depositmasterobject
					.setInterestRate(rs_main.getFloat("dm.int_rate"));
			depositmasterobject.setCloseInd(rs_main.getInt("dm.close_ind"));
			depositmasterobject.setInterestUpto(rs_main
					.getString("dm.int_upto_date"));
			depositmasterobject.setInterestMode(rs_main
					.getString("dm.int_mode"));
			System.out.println("Transfer Type==>>"
					+ rs_main.getString("dm.trf_ac_type"));
			depositmasterobject.setTransferType(rs_main
					.getString("dm.trf_ac_type"));
			depositmasterobject
					.setTransferAccno(rs_main.getInt("dm.trf_ac_no"));
			depositmasterobject.setCustomerId(rs_main.getInt("dm.cid"));
			depositmasterobject.setExtraRateType(rs_main
					.getInt("dm.extra_rate_type"));
			depositmasterobject.setDPType(rs_main.getInt("custtype"));
			depositmasterobject.setLoanAvailed(rs_main
					.getString("dm.ln_availed"));
			depositmasterobject.setCategory(rs_main.getInt("sub_category"));
			depositmasterobject
					.setInterestFrq(rs_main.getString("dm.int_freq"));
			depositmasterobject.setNoofJtHldrs(rs_main.getInt("dm.no_jt_hldr"));
			depositmasterobject
					.setMailingAddress(rs_main.getInt("dm.add_type"));
			depositmasterobject.setNomineeRegNo(rs_main.getInt("dm.nom_no"));
			depositmasterobject.setIntroAccType(rs_main
					.getString("dm.intr_ac_type"));
			depositmasterobject.setIntroAccno(rs_main.getInt("dm.intr_ac_no"));
			depositmasterobject.setRenType(rs_main.getString("dm.ren_ac_type"));
			depositmasterobject.setRenewalAccno(rs_main.getInt("dm.ren_ac_no"));
			depositmasterobject.setReceivedBy(rs_main.getString("dm.rcvd_by"));
			depositmasterobject.setReceivedAccType(rs_main
					.getString("dm.rcvd_ac_type"));
			depositmasterobject.setReceivedAccno(rs_main
					.getInt("dm.rcvd_ac_no"));
			depositmasterobject.setAutoRenewal(rs_main
					.getString("auto_renewal"));
			depositmasterobject.setVerified(rs_main.getString("veuser"));

			if (depositmasterobject.getNoofJtHldrs() > 0) {
				rs_main = stmt_main
						.executeQuery("select cid,addr_type from JointHolders where ac_type='"
								+ depositmasterobject.getAccType()
								+ "' and ac_no='"
								+ depositmasterobject.getAccNo() + "'");
				int j = 0;
				int addrtype[] = null;
				int cids[] = null;
				if (rs_main.next()) {
					rs_main.last();
					addrtype = new int[rs_main.getRow()];
					cids = new int[rs_main.getRow()];
					rs_main.beforeFirst();
				}
				while (rs_main.next()) {
					addrtype[j] = rs_main.getInt(2);
					cids[j++] = rs_main.getInt(1);
				}
				depositmasterobject.setJointAddrType(addrtype);
				depositmasterobject.setJointCid(cids);
			}
			depositmasterobject.setNomineeDetails(commonLocal
					.getNominee(depositmasterobject.getNomineeRegNo()));
			depositmasterobject.setSigObj(commonLocal.getSignatureDetails(
					depositmasterobject.getAccNo(), depositmasterobject
							.getAccType()));
			/**
			 * For FD Renewal type 0 means data entry type 1 means verification
			 */

			rs_main = stmt_main
					.executeQuery("select sum(int_amt),sum(int_paid),max(cum_int) from DepositTransaction where ac_type='"
							+ acctype
							+ "' and ac_no="
							+ accno
							+ "  and trn_type not in ('E','L','A')");
			if (rs_main.next()) {
				depositmasterobject.setRDBalance(rs_main.getDouble(1));
				depositmasterobject.setInterestPaid(rs_main.getDouble(2));
				depositmasterobject.setCumInterest(rs_main.getDouble(3));
			}
			rs_main = stmt_main
					.executeQuery("select trn_mode,trn_narr from DepositTransaction where ac_type='"
							+ acctype
							+ "' and ac_no="
							+ accno
							+ "  and trn_type='A'");
			// rs_main.last();
			// depositmasterobject.setTransferType("");
			if (rs_main.next()) {
				// rs_main.first();
				depositmasterobject.setTransferType(rs_main
						.getString("trn_mode"));
				if (rs_main.getString("trn_mode").equalsIgnoreCase("T")) {
					depositmasterobject.setTransferAccType(rs_main
							.getString("trn_narr"));
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RecordsNotFoundException();
		} catch (CreateException e) {
			e.printStackTrace();
			throw new AccountNotFoundException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			commonLocal = null;
		}
		return depositmasterobject;
	}

	public int closeFDAccount(DepositMasterObject depositMasterObject)
			throws CreateException, DateFormatException {
		Connection conn = null;
		ResultSet rs_trn = null;
		String actype = depositMasterObject.getAccType();
		int acno = depositMasterObject.getAccNo();
		String paymode = depositMasterObject.getTransferType();
		String trn_narr = "";

		System.out.println("paymode inside bean----"
				+ depositMasterObject.getTransferType());

		if (paymode.equalsIgnoreCase("T")) {
			trn_narr = depositMasterObject.getTransferAccType() + " "
					+ depositMasterObject.getTransferAccno();
		}
		int type = depositMasterObject.getAutoRenwlNo();
		int closeind = depositMasterObject.getCloseInd();
		double amount_interest = depositMasterObject.getCumInterest();
		String duser = depositMasterObject.userverifier.getVerId();
		String dtml = depositMasterObject.userverifier.getVerTml();
		String ddate = depositMasterObject.userverifier.getVerDate();
		try {
			conn = getConnection();
			commonLocal = commonLocalHome.create();
			Statement stmt_trn = conn
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			/**
			 * for Delete and update
			 */
			if (type == 1 || type == 2) {
				stmt_trn
						.executeUpdate("update DepositMaster set close_ind=0,close_date=null where ac_type='"
								+ actype + "' and ac_no=" + acno);
				stmt_trn
						.executeUpdate("delete from DepositTransaction where (trn_type='E' or trn_type = 'L') and ac_type='"
								+ actype + "' and ac_no=" + acno);
			}
			/**
			 * for Submit and update
			 */
			if (type == 0 || type == 2) {
				rs_trn = stmt_trn
						.executeQuery("select max(trn_seq) as lst_trn_seq,dm.dep_amt,int_freq,sum(dt.int_amt) as int_amt,sum(dt.int_paid) from DepositTransaction dt,DepositMaster dm where dm.ac_type='"
								+ actype
								+ "' and dm.ac_no="
								+ acno
								+ " and dm.ac_type=dt.ac_type and dm.ac_no=dt.ac_no group by dt.ac_no");
				rs_trn.next();
				int trnseq = rs_trn.getInt("lst_trn_seq");
				double dep_amt = rs_trn.getInt("dep_amt");
				/**
				 * For Interest
				 */
				PreparedStatement ps_int = conn
						.prepareStatement("insert into DepositTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				ps_int.setString(1, actype);
				ps_int.setInt(2, acno);
				ps_int.setInt(3, ++trnseq);
				ps_int.setString(4, getSysDate());
				ps_int.setString(5, "E");
				ps_int.setDouble(6, dep_amt);
				ps_int.setDouble(7, amount_interest);
				ps_int.setDouble(8, 0);
				ps_int.setDouble(9, 0);
				ps_int.setDouble(10, 0);
				ps_int.setString(11, null);
				ps_int.setString(12, Validations.addDays(getSysDate(), -1));
				ps_int.setInt(13, 0);
				ps_int.setString(14, "By Interest");
				ps_int.setString(15, "T");
				ps_int.setString(16, dtml);
				ps_int.setString(17, "C");
				ps_int.setDouble(18, 0);
				ps_int.setString(19, dtml);
				ps_int.setString(20, duser);
				ps_int.setString(21, ddate);
				ps_int.setString(22, null);
				ps_int.setString(23, null);
				ps_int.setString(24, null);
				ps_int.executeUpdate();

				double double_final_dep_amt = 0, double_final_interest_amount = 0;
				if (amount_interest < 0) {
					double_final_interest_amount = 0;
					double_final_dep_amt = dep_amt + amount_interest;
				} else {
					double_final_interest_amount = amount_interest;
					double_final_dep_amt = dep_amt;
				}
				/**
				 * deposit amount and interest payment and loan payment
				 */
				if (depositMasterObject.getLoanAvailed().equals("T")) {
					double loan_amount = depositMasterObject.getDepositPaid()
							+ depositMasterObject.getInterestPaid();
					if (loan_amount >= double_final_dep_amt
							+ double_final_interest_amount) {
						loan_amount = double_final_dep_amt
								+ double_final_interest_amount;
						double_final_dep_amt = 0;
						double_final_interest_amount = 0;
					} else if (loan_amount <= double_final_interest_amount) {
						double_final_interest_amount = double_final_interest_amount
								- loan_amount;
					} else if (loan_amount <= double_final_interest_amount
							+ double_final_dep_amt) {
						double_final_dep_amt = (double_final_dep_amt + double_final_interest_amount)
								- loan_amount;
						double_final_interest_amount = 0;
					}
					if (loan_amount > 0) {
						ps_int.setString(1, actype);
						ps_int.setInt(2, acno);
						ps_int.setInt(3, ++trnseq);
						ps_int.setString(4, getSysDate());
						ps_int.setString(5, "L");
						ps_int.setDouble(6, 0);
						ps_int.setDouble(7, 0);
						ps_int.setDouble(8, loan_amount);
						ps_int.setDouble(9, 0);
						ps_int.setDouble(10, 0);
						ps_int.setString(11, getSysDate());
						ps_int.setString(12, null);
						ps_int.setInt(13, 0);
						ps_int.setString(14, depositMasterObject
								.getLoanAcType()
								+ " " + depositMasterObject.getLoanAccno());
						ps_int.setString(15, "T");
						ps_int.setString(16, dtml);
						ps_int.setString(17, "D");
						ps_int.setDouble(18, 0);
						ps_int.setString(19, dtml);
						ps_int.setString(20, duser);
						ps_int.setString(21, ddate);
						ps_int.setString(22, null);
						ps_int.setString(23, null);
						ps_int.setString(24, null);
						ps_int.executeUpdate();
					}
					if (double_final_dep_amt > 0) {
						ps_int.setString(1, actype);
						ps_int.setInt(2, acno);
						ps_int.setInt(3, ++trnseq);
						ps_int.setString(4, getSysDate());
						ps_int.setString(5, "E");
						ps_int.setDouble(6, 0);
						ps_int.setDouble(7, 0);
						ps_int.setDouble(8, double_final_dep_amt);
						ps_int.setDouble(9, double_final_interest_amount);
						ps_int.setDouble(10, 0);
						ps_int.setString(11, getSysDate());
						ps_int.setString(12, null);
						ps_int.setInt(13, 0);
						ps_int.setString(14, trn_narr);
						ps_int.setString(15, paymode);
						ps_int.setString(16, dtml);
						ps_int.setString(17, "D");
						ps_int.setDouble(18, 0);
						ps_int.setString(19, dtml);
						ps_int.setString(20, duser);
						ps_int.setString(21, ddate);
						ps_int.setString(22, null);
						ps_int.setString(23, null);
						ps_int.setString(24, null);
						ps_int.executeUpdate();
					}
				} else {
					ps_int.setString(1, actype);
					ps_int.setInt(2, acno);
					ps_int.setInt(3, ++trnseq);
					ps_int.setString(4, getSysDate());
					ps_int.setString(5, "E");
					ps_int.setDouble(6, 0);
					ps_int.setDouble(7, 0);
					ps_int.setDouble(8, double_final_dep_amt);
					ps_int.setDouble(9, double_final_interest_amount);
					ps_int.setDouble(10, 0);
					ps_int.setString(11, getSysDate());
					ps_int.setString(12, null);
					ps_int.setInt(13, 0);
					ps_int.setString(14, trn_narr);
					ps_int.setString(15, paymode);
					ps_int.setString(16, dtml);
					ps_int.setString(17, "D");
					ps_int.setDouble(18, 0);
					ps_int.setString(19, dtml);
					ps_int.setString(20, duser);
					ps_int.setString(21, ddate);
					ps_int.setString(22, null);
					ps_int.setString(23, null);
					ps_int.setString(24, null);
					ps_int.executeUpdate();
				}
				stmt_trn.executeUpdate("update DepositMaster set close_ind="
						+ closeind + ",close_date='" + getSysDate()
						+ "' where ac_type='" + actype + "' and ac_no=" + acno);
			}

		} catch (SQLException v) {
			v.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordNotInsertedException();
		} catch (Exception e) {
			e.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordNotInsertedException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			commonLocal = null;
		}
		return 1;
	}

	/**
	 * Verification of Fixed Deposit.
	 * 
	 * @throws CreateException
	 * @throws DateFormatException
	 * @throws FreezedAccountException
	 * @throws InOperativeAccountException
	 * @throws AccountClosedException
	 */
	public int verifiedFD(DepositMasterObject dm)
			throws RecordsNotInsertedException {
		int int_return_value = 0, closeind = 0, trnseq = 0, dpcattype = 0, modcode = 0, dpcatold = 0;
		ResultSet rs_main = null, rs_trn = null;
		Connection conn = null;

		System.out.println("transfer type---------------"
				+ dm.getTransferType());
		System.out.println("inside verify BEAN CLASS========="
				+ dm.getCloseInd());
		System.out.println("De-user===" + dm.userverifier.getUserId());
		System.out.println("De-TML===" + dm.userverifier.getUserTml());
		System.out.println("De-Date===" + dm.userverifier.getUserDate());
		System.out.println("Name of customer-----> " + dm.getName());

		try {
			conn = getConnection();
			commonLocal = commonLocalHome.create();
			Statement stmt_trn = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			Statement stmt_main = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			if (dm.getCloseInd() == 90)
				closeind = 1;
			else if (dm.getCloseInd() == 91)
				closeind = 2;
			else if (dm.getCloseInd() == 92)
				closeind = 3;
			double dep_amount = dm.getDepositAmt();
			double interest_amount = dm.getCumInterest();

			System.out.println("inside verify BEAN CLASS=========" + closeind);

			/** For GL Transaction indi/institute.. */
			dpcattype = dm.getDPType();
			dpcatold = dpcattype;

			System.out.println("modecode-----" + modcode);

			System.out.println("after parsing----" + (dm.getAccType()));
			System.out.println("ac_no======" + dm.getAccNo());
			modcode = Integer.parseInt(dm.getAccType());

			System.out.println("modceode after-----" + modcode);
			// commented by geetha for CBS
			// modcode = Integer.parseInt(dm.getAccType());

			rs_main = stmt_main
					.executeQuery("select trn_seq,int_amt,dep_paid,int_paid,trn_type from DepositTransaction where ac_type = '"
							+ dm.getAccType()
							+ "' and ac_no = "
							+ dm.getAccNo()
							+ " and (trn_type ='E' or trn_type = 'L') order by trn_seq");

			rs_main.next();

			// System.out.println("trn_seq*******"+rs_main.getInt("trn_seq"));
			trnseq = rs_main.getInt("trn_seq");
			// System.out.println("trn_seq====="+trnseq);
			String vtml = dm.userverifier.getVerTml(), vuser = dm.userverifier
					.getVerId();
			String vdate = dm.userverifier.getVerDate();
			// System.out.println();
			/**
			 * Interest amount GL iff interest amount less than or equal to 0
			 * i.e debit from FD account Gl credit to profit gl
			 */
			if (rs_main.getDouble("int_amt") <= 0) {
				GLTransObject trnobj = null;
				if (dpcattype == 0)
					dpcattype = 3;
				else
					dpcattype = 4;
				/**
				 * debit fd account gl
				 */

				System.out.println("inside  debit fd account gl");
				rs_trn = stmt_trn
						.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
								+ dpcattype
								+ " and gk.ac_type = "
								+ modcode
								+ " and trn_type='I' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
				if (rs_trn.next()) {
					trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_trn.getString("gk.gl_type"));
					trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
					trnobj.setTrnMode("T");
					trnobj.setAmount(rs_main.getDouble("int_amt")
							* rs_trn.getInt("mult_by"));
					trnobj.setCdind("D");
					trnobj.setAccType(dm.getAccType());
					trnobj.setAccNo(String.valueOf(dm.getAccNo()));
					trnobj.setTrnType("P");
					trnobj.setRefNo(0);
					trnobj.setTrnSeq(trnseq);
					trnobj.setVtml(vtml);
					trnobj.setVid(vuser);
					trnobj.setVDate(dm.userverifier.getVerDate());
					commonLocal.storeGLTransaction(trnobj);
				}

				dpcattype = dpcattype + 4;
				/**
				 * Profit Gl
				 */
				rs_trn = stmt_trn
						.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
								+ dpcattype
								+ " and gk.ac_type = "
								+ modcode
								+ " and trn_type='P' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
				if (rs_trn.next()) {
					trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_trn.getString("gk.gl_type"));
					trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
					trnobj.setTrnMode("T");
					trnobj.setAmount(rs_main.getDouble("int_amt")
							* rs_trn.getInt("mult_by"));
					trnobj.setCdind("C");
					trnobj.setAccType(dm.getAccType());
					trnobj.setAccNo(String.valueOf(dm.getAccNo()));
					trnobj.setTrnType("P");
					trnobj.setRefNo(0);
					trnobj.setTrnSeq(trnseq);
					trnobj.setVtml(vtml);
					trnobj.setVid(vuser);
					trnobj.setVDate(dm.userverifier.getVerDate());
					commonLocal.storeGLTransaction(trnobj);
				}

				interest_amount = 0;
				dep_amount = dep_amount + rs_main.getDouble("int_amt");
			}
			/**
			 * if interest > 0 i.e credit to FD payable Gl debit loss account
			 */
			else {
				GLTransObject trnobj = null;
				if (dpcattype == 0)
					dpcattype = 3;
				else
					dpcattype = 4;
				/**
				 * credit to FD payable GL
				 */

				System.out.println("inside-----credit to FD payable GL");
				rs_trn = stmt_trn
						.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
								+ dpcattype
								+ " and gk.ac_type = "
								+ modcode
								+ " and trn_type='I' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
				if (rs_trn.next()) {
					trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_trn.getString("gk.gl_type"));
					trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
					trnobj.setTrnMode("T");
					trnobj.setAmount(rs_main.getDouble("int_amt")
							* rs_trn.getInt("mult_by"));
					trnobj.setCdind("C");
					trnobj.setAccType(dm.getAccType());
					trnobj.setAccNo(String.valueOf(dm.getAccNo()));
					trnobj.setTrnType("I");
					trnobj.setRefNo(0);
					trnobj.setTrnSeq(trnseq);
					trnobj.setVtml(vtml);
					trnobj.setVid(vuser);
					trnobj.setVDate(dm.userverifier.getVerDate());
					commonLocal.storeGLTransaction(trnobj);
				}

				dpcattype = dpcattype + 2;
				/**
				 * Debit to loss Gl
				 */
				rs_trn = stmt_trn
						.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
								+ dpcattype
								+ " and gk.ac_type = "
								+ modcode
								+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
				if (rs_trn.next()) {
					trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_trn.getString("gk.gl_type"));
					trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
					trnobj.setTrnMode("T");
					trnobj.setAmount(rs_main.getDouble("int_amt")
							* rs_trn.getInt("mult_by"));
					trnobj.setCdind("D");
					trnobj.setAccType(dm.getAccType());
					trnobj.setAccNo(String.valueOf(dm.getAccNo()));
					trnobj.setTrnType("P");
					trnobj.setRefNo(0);
					trnobj.setTrnSeq(trnseq);
					trnobj.setVtml(vtml);
					trnobj.setVid(vuser);
					trnobj.setVDate(dm.userverifier.getVerDate());
					commonLocal.storeGLTransaction(trnobj);
				}
			}
			int_return_value = stmt_trn
					.executeUpdate("update DepositTransaction set trn_type='I',ve_tml='"
							+ vtml
							+ "',ve_user='"
							+ vuser
							+ "',ve_date ='"
							+ dm.userverifier.getVerDate()
							+ "' where trn_type='E' and trn_seq ="
							+ trnseq
							+ " and ac_type='"
							+ dm.getAccType()
							+ "' and ac_no=" + dm.getAccNo());

			rs_main.next();
			if (rs_main.isLast()) {
				trnseq = rs_main.getInt("trn_seq");
				dpcattype = dpcatold;
				if (dpcattype == 0)
					dpcattype = 1;
				else
					dpcattype = 2;
				if (rs_main.getString("trn_type").equals("L")) {
					/**
					 * credit to loan account loan recovery gl
					 */

					System.out
							.println("inside   credit to loan account * loan recovery gl");
					rs_trn = stmt_trn
							.executeQuery("select max(trn_seq) from LoanTransaction where ac_type='"
									+ dm.getLoanAcType()
									+ "' and ac_no="
									+ dm.getLoanAccno());
					rs_trn.next();
					int ln_trn_seq = rs_trn.getInt(1) + 1;
					PreparedStatement pstmt = conn
							.prepareStatement("insert into LoanTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					pstmt.setString(1, dm.getLoanAcType());
					pstmt.setInt(2, dm.getLoanAccno());
					pstmt.setInt(3, ln_trn_seq);
					pstmt.setString(4, getSysDate());
					pstmt.setString(5, "R");
					pstmt.setDouble(6, rs_main.getDouble("dep_paid"));
					pstmt.setString(7, "T");
					pstmt.setString(8, vtml);
					pstmt.setString(9, null);
					pstmt.setString(10, dm.getAccType() + " " + dm.getAccNo());
					pstmt.setString(11, null);
					pstmt.setString(12, "C");
					pstmt.setString(13, commonLocal.getFutureDayDate(
							getSysDate(), -1));
					pstmt.setDouble(14, rs_main.getDouble("dep_paid")
							- dm.getInterestPaid());
					pstmt.setDouble(15, dm.getInterestPaid());
					pstmt.setDouble(16, 0);
					pstmt.setDouble(17, 0);
					pstmt.setDouble(18, 0);
					pstmt.setDouble(19, 0);
					pstmt.setString(20, vtml);
					pstmt.setString(21, vuser);
					pstmt.setString(22, dm.userverifier.getVerDate());
					pstmt.setString(23, vtml);
					pstmt.setString(24, vuser);
					pstmt.setString(25, dm.userverifier.getVerDate());
					pstmt.executeUpdate();

					dpcattype = 1;
					rs_trn = stmt_trn
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type ='"
									+ dm.getLoanAcType()
									+ "' and trn_type='R' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					rs_trn.next();
					GLTransObject trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_trn.getString("gk.gl_type"));
					trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
					trnobj.setTrnMode("T");
					trnobj.setAmount((rs_main.getDouble("dep_paid") - dm
							.getInterestPaid())
							* rs_trn.getInt("mult_by"));
					trnobj.setCdind("C");
					trnobj.setAccType(dm.getLoanAcType());
					trnobj.setAccNo(String.valueOf(dm.getLoanAccno()));
					trnobj.setTrnType("R");
					trnobj.setRefNo(0);
					trnobj.setTrnSeq(ln_trn_seq);
					trnobj.setVtml(vtml);
					trnobj.setVid(vuser);
					trnobj.setVDate(dm.userverifier.getVerDate());
					commonLocal.storeGLTransaction(trnobj);

					dpcattype = 2;
					rs_trn = stmt_trn
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type ='"
									+ dm.getLoanAcType()
									+ "' and trn_type='R' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					rs_trn.next();
					trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_trn.getString("gk.gl_type"));
					trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
					trnobj.setTrnMode("T");
					trnobj.setAmount((dm.getInterestPaid())
							* rs_trn.getInt("mult_by"));
					trnobj.setCdind("C");
					trnobj.setAccType(dm.getLoanAcType());
					trnobj.setAccNo(String.valueOf(dm.getLoanAccno()));
					trnobj.setTrnType("R");
					trnobj.setRefNo(0);
					trnobj.setTrnSeq(ln_trn_seq);
					trnobj.setVtml(vtml);
					trnobj.setVid(vuser);
					trnobj.setVDate(dm.userverifier.getVerDate());
					commonLocal.storeGLTransaction(trnobj);

					stmt_trn.executeUpdate("update LoanMaster set close_date='"
							+ getSysDate() + "' where ac_type='"
							+ dm.getLoanAcType() + "' and ac_no="
							+ dm.getLoanAccno());

					dpcattype = dpcatold;
					if (dpcattype == 0)
						dpcattype = 1;
					else
						dpcattype = 2;
					rs_trn = stmt_trn
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type = "
									+ modcode
									+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					rs_trn.next();
					trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_trn.getString("gk.gl_type"));
					trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
					trnobj.setTrnMode("T");
					trnobj.setAmount((dep_amount) * rs_trn.getInt("mult_by"));
					trnobj.setCdind("D");
					trnobj.setAccType(dm.getAccType());
					trnobj.setAccNo(String.valueOf(dm.getAccNo()));
					trnobj.setTrnType("P");
					trnobj.setRefNo(0);
					trnobj.setTrnSeq(trnseq);
					trnobj.setVtml(vtml);
					trnobj.setVid(vuser);
					trnobj.setVDate(dm.userverifier.getVerDate());
					commonLocal.storeGLTransaction(trnobj);

					dpcattype += 2;
					rs_trn = stmt_trn
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type = "
									+ modcode
									+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					rs_trn.next();
					trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_trn.getString("gk.gl_type"));
					trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
					trnobj.setTrnMode("T");
					trnobj.setAmount((interest_amount)
							* rs_trn.getInt("mult_by"));
					trnobj.setCdind("D");
					trnobj.setAccType(dm.getAccType());
					trnobj.setAccNo(String.valueOf(dm.getAccNo()));
					trnobj.setTrnType("P");
					trnobj.setRefNo(0);
					trnobj.setTrnSeq(trnseq);
					trnobj.setVtml(vtml);
					trnobj.setVid(vuser);
					trnobj.setVDate(dm.userverifier.getVerDate());
					commonLocal.storeGLTransaction(trnobj);
					stmt_trn
							.executeUpdate("update DepositTransaction set trn_type='P',ve_tml='"
									+ vtml
									+ "',ve_user='"
									+ vuser
									+ "',ve_date = '"
									+ dm.userverifier.getVerDate()
									+ "' where trn_type='L' and ac_type='"
									+ dm.getAccType()
									+ "' and ac_no="
									+ dm.getAccNo());
				} else {

					System.out.println("inside Sumanth  P====> "
							+ dm.getTransferType());
					// addedd G as P is not defined in the CBS so for time being
					// its added
					System.out.println("See thism in Bean pls===> "
							+ dm.getTrantypetemp());
					if (dm.getTransferType().equalsIgnoreCase("P")
							|| dm.getTransferType().equalsIgnoreCase("G")) {
						PayOrderObject po = new PayOrderObject();
						po.setPOType("P");
						po.setPOCustType(0);
						po.setPOPayee(dm.getName());
						po.setPOAccType(dm.getAccType());
						po.setPOAccNo(dm.getAccNo());
						po.setPOGlCode(0);
						po.setPOAmount(rs_main.getDouble("dep_paid")
								+ rs_main.getDouble("int_paid"));
						po.setCommissionAmount(0);
						po.uv.setUserTml(vtml);
						po.uv.setUserId(vuser);
						po.uv.setUserDate(dm.userverifier.getVerDate());
						po.uv.setVerTml(vtml);
						po.uv.setVerId(vuser);
						po.uv.setVerDate(dm.userverifier.getVerDate());
						int_return_value = commonLocal.storePayOrder(po);
						stmt_trn
								.executeUpdate("update DepositTransaction set ref_no="
										+ int_return_value
										+ ",trn_narr='By PO,PO no="
										+ int_return_value
										+ "' where trn_type='E' and ac_type='"
										+ dm.getAccType()
										+ "' and ac_no="
										+ dm.getAccNo()
										+ " and trn_seq="
										+ trnseq);
						/**
						 * PayOrder Credit
						 */
						rs_trn = stmt_trn
								.executeQuery("select * from GLKeyParam where ac_type=1016001 and code=1");
						rs_trn.next();
						GLTransObject trnobj = new GLTransObject();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_trn.getString("gl_type"));
						trnobj.setGLCode(rs_trn.getString("gl_code"));
						trnobj.setTrnMode("T");
						trnobj
								.setAmount((rs_main.getDouble("dep_paid") + rs_main
										.getDouble("int_paid")));
						trnobj.setCdind("C");
						trnobj.setAccType(dm.getAccType());
						trnobj.setAccNo(String.valueOf(dm.getAccNo()));
						trnobj.setTrnType("P");
						trnobj.setRefNo(0);
						trnobj.setTrnSeq(trnseq);
						trnobj.setVtml(vtml);
						trnobj.setVid(vuser);
						trnobj.setVDate(dm.userverifier.getVerDate());
						commonLocal.storeGLTransaction(trnobj);
					} else if (dm.getTransferType().equalsIgnoreCase("T")) {
						AccountTransObject am = new AccountTransObject();
						String accty = dm.getTransferAccType();
						int accno = dm.getTransferAccno();
						am.setAccType(accty);
						am.setAccNo(accno);
						am.setTransDate(getSysDate());
						am.setTransType("R");
						am.setTransAmount(rs_main.getDouble("dep_paid")
								+ rs_main.getDouble("int_paid"));
						am.setTransMode("T");
						am.setTransSource(vtml);
						am.setCdInd("C");
						am.setChqDDNo(0);
						am.setChqDDDate("");
						am.setTransNarr(dm.getAccType() + " "
								+ String.valueOf(dm.getAccNo()));
						am.setRef_No(0);
						am.setPayeeName(dm.getName());
						am.setCloseBal(rs_main.getDouble("dep_paid")
								+ rs_main.getDouble("int_paid"));
						am.setLedgerPage(0);
						am.uv.setUserTml(vtml);
						am.uv.setUserId(vuser);
						am.uv.setUserDate(dm.userverifier.getVerDate());
						am.uv.setVerTml(vtml);
						am.uv.setVerId(vuser);
						am.uv.setVerDate(dm.userverifier.getVerDate());
						int_return_value = commonLocal
								.storeAccountTransaction(am);

						stmt_trn
								.executeUpdate("update DepositTransaction set trn_narr='"
										+ accty
										+ " "
										+ accno
										+ "' where ac_type='"
										+ dm.getAccType()
										+ "' and ac_no="
										+ dm.getAccNo()
										+ " and trn_seq="
										+ rs_main.getInt("trn_seq"));

						// Code added by sanjeet..

						/*
						 * rs_trn = stmt_trn.executeQuery("select
						 * gk.gl_code,mult_by,gk.gl_type from GLKeyParam
						 * gk,GLPost gp where code = 1 and gk.ac_type =
						 * '"+accty+"' and trn_type='P' and cr_dr='C' and
						 * gk.ac_type = gp.ac_type and gp.gl_code =
						 * gk.gl_code"); rs_trn.next(); GLTransObject trnobj=new
						 * GLTransObject(); trnobj.setTrnDate(getSysDate());
						 * trnobj.setGLType(rs_trn.getString("gk.gl_type"));
						 * trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
						 * trnobj.setTrnMode("T");
						 * trnobj.setAmount((rs_main.getDouble
						 * ("dep_paid")+rs_main
						 * .getDouble("int_paid"))*rs_trn.getInt("mult_by"));
						 * trnobj.setCdind("C");
						 * trnobj.setAccType(dm.getAccType());
						 * trnobj.setAccNo(String.valueOf(dm.getAccNo()));
						 * trnobj.setTrnType("P"); trnobj.setRefNo(0);
						 * trnobj.setTrnSeq(trnseq); trnobj.setVtml(vtml);
						 * trnobj.setVid(vuser);
						 * trnobj.setVDate(dm.userverifier.getVerDate());
						 * commonLocal.storeGLTransaction(trnobj);
						 */
					} else if (dm.getTransferType().equalsIgnoreCase("C")) {
						int_return_value = commonLocal.getModulesColumn(
								"lst_voucher_scroll_no", "1019000");

						// PreparedStatement pstmt_insert=
						// conn.prepareStatement("insert into DayCash
						// (scroll_no,trn_type,trn_date,ac_type,ac_no,csh_amt,cd_ind,vch_type,attached,vch_no,de_user,de_tml,de_date,ve_user,ve_tml,ve_date,name)
						// values
						// (0,'P',?,?,?,?,'C','C','F',?,?,?,?,null,null,null,?)");
						// //changed by Shiva, vch_type='P' made 'C',
						// Reference--Shivappa. Date:-26/09/2005,12:40pm;
						// Refered by Ship, Updated Verification fields..
						PreparedStatement pstmt_insert = conn
								.prepareStatement("insert into DayCash (scroll_no,trn_type,trn_date,ac_type,ac_no,csh_amt,cd_ind,vch_type,attached,vch_no,de_user,de_tml,de_date,ve_user,ve_tml,ve_date,name) values (0,'P',?,?,?,?,'C','P','F',?,?,?,?,?,?,?,?)");
						pstmt_insert.setString(1, getSysDate());
						pstmt_insert.setString(2, dm.getAccType());
						pstmt_insert.setInt(3, dm.getAccNo());
						pstmt_insert.setDouble(4, rs_main.getDouble("dep_paid")
								+ rs_main.getDouble("int_paid"));
						pstmt_insert.setInt(5, int_return_value);
						Statement st = conn.createStatement();
						ResultSet r = st
								.executeQuery("select de_user,de_tml,de_date from DepositTransaction where ac_type='"
										+ dm.getAccType()
										+ "' and ac_no="
										+ dm.getAccNo()
										+ " and trn_type='E' and dep_amt=0 and ve_tml is null");
						r.next();
						System.out
								.println("AT 7096 in Term Deposit Bean=========================");
						// Uncomment this part of code if you want de_user,etc
						// in your Transaction table,this was commented to get
						// de_user in Voucher table.
						/*
						 * pstmt_insert.setString(6,r.getString(1));
						 * pstmt_insert.setString(7,r.getString(2));
						 * pstmt_insert.setString(8,r.getString(3));
						 */
						pstmt_insert.setString(6, vuser);
						pstmt_insert.setString(7, vtml);
						pstmt_insert.setString(8, dm.userverifier.getVerDate());
						pstmt_insert.setString(9, vuser);
						pstmt_insert.setString(10, vtml);
						pstmt_insert
								.setString(11, dm.userverifier.getVerDate());

						pstmt_insert.setString(12, dm.getName());
						pstmt_insert.executeUpdate();
						stmt_trn
								.executeUpdate("update DepositTransaction set ref_no="
										+ int_return_value
										+ ",trn_narr='By Cash vch no"
										+ int_return_value
										+ "' where ac_type='"
										+ dm.getAccType()
										+ "' and ac_no="
										+ dm.getAccNo()
										+ " and trn_seq="
										+ rs_main.getInt("trn_seq"));
						/**
						 * cash payment gl
						 */
						// rs_trn = stmt_trn.executeQuery("select
						// gk.gl_code,mult_by,gk.gl_type from GLKeyParam
						// gk,GLPost gp where code = 1 and gk.ac_type = 1999001
						// and trn_type='P' and cr_dr='C' and gk.ac_type =
						// gp.ac_type and gp.gl_code = gk.gl_code");
						// referd by Ship for CASH GL
						rs_trn = stmt_trn
								.executeQuery("select gl_type,gl_code from GLKeyParam where ac_type='1019001'");
						rs_trn.next();
						GLTransObject trnobj = new GLTransObject();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_trn.getString("gl_type"));
						trnobj.setGLCode(rs_trn.getString("gl_code"));
						trnobj.setTrnMode("C");
						// trnobj.setAmount((rs_main.getDouble("dep_paid")+rs_main.getDouble("int_paid"))*rs_trn.getInt("mult_by"));
						trnobj
								.setAmount((rs_main.getDouble("dep_paid") + rs_main
										.getDouble("int_paid")));
						trnobj.setCdind("C");
						trnobj.setAccType(dm.getAccType());
						trnobj.setAccNo(String.valueOf(dm.getAccNo()));
						trnobj.setTrnType("P");
						trnobj.setRefNo(0);
						trnobj.setTrnSeq(trnseq);
						trnobj.setVtml(vtml);
						trnobj.setVid(vuser);
						trnobj.setVDate(dm.userverifier.getVerDate());
						commonLocal.storeGLTransaction(trnobj);
					}

					dpcattype = dpcatold;
					if (dpcattype == 0)
						dpcattype = 1;
					else
						dpcattype = 2;
					rs_trn = stmt_trn
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type = "
									+ modcode
									+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					rs_trn.next();
					GLTransObject trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_trn.getString("gk.gl_type"));
					trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
					if (dm.getTransferType().equalsIgnoreCase("C"))
						trnobj.setTrnMode("C");
					else
						trnobj.setTrnMode("T");
					trnobj.setAmount((dep_amount) * rs_trn.getInt("mult_by"));
					trnobj.setCdind("D");
					trnobj.setAccType(dm.getAccType());
					trnobj.setAccNo(String.valueOf(dm.getAccNo()));
					trnobj.setTrnType("P");
					trnobj.setRefNo(0);
					trnobj.setTrnSeq(trnseq);
					trnobj.setVtml(vtml);
					trnobj.setVid(vuser);
					trnobj.setVDate(dm.userverifier.getVerDate());
					commonLocal.storeGLTransaction(trnobj);

					dpcattype += 2;
					rs_trn = stmt_trn
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type = "
									+ modcode
									+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					rs_trn.next();
					trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_trn.getString("gk.gl_type"));
					trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
					if (dm.getTransferType().equalsIgnoreCase("C"))
						trnobj.setTrnMode("C");
					else
						trnobj.setTrnMode("T");
					trnobj.setAmount((interest_amount)
							* rs_trn.getInt("mult_by"));
					trnobj.setCdind("D");
					trnobj.setAccType(dm.getAccType());
					trnobj.setAccNo(String.valueOf(dm.getAccNo()));
					trnobj.setTrnType("P");
					trnobj.setRefNo(0);
					trnobj.setTrnSeq(trnseq);
					trnobj.setVtml(vtml);
					trnobj.setVid(vuser);
					trnobj.setVDate(dm.userverifier.getVerDate());
					commonLocal.storeGLTransaction(trnobj);
				}
			} else {
				System.out.println("trn_seq------" + rs_main.getInt("trn_seq"));
				trnseq = rs_main.getInt("trn_seq");
				System.out.println("trn_seq------" + trnseq);
				dpcattype = dpcatold;
				/**
				 * credit to loan account loan recovery gl
				 */
				rs_trn = stmt_trn
						.executeQuery("select max(trn_seq) from LoanTransaction where ac_type='"
								+ dm.getLoanAcType()
								+ "' and ac_no="
								+ dm.getLoanAccno());
				rs_trn.next();
				int ln_trn_seq = rs_trn.getInt(1) + 1;
				PreparedStatement pstmt = conn
						.prepareStatement("insert into LoanTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				pstmt.setString(1, dm.getLoanAcType());
				pstmt.setInt(2, dm.getLoanAccno());
				pstmt.setInt(3, ln_trn_seq);
				pstmt.setString(4, getSysDate());
				pstmt.setString(5, "R");
				pstmt.setDouble(6, rs_main.getDouble("dep_paid"));
				pstmt.setString(7, "T");
				pstmt.setString(8, vtml);
				pstmt.setString(9, null);
				pstmt.setString(10, dm.getAccType() + " " + dm.getAccNo());
				pstmt.setString(11, null);
				pstmt.setString(12, "C");
				pstmt.setString(13, commonLocal.getFutureDayDate(getSysDate(),
						-1));
				pstmt.setDouble(14, rs_main.getDouble("dep_paid")
						- dm.getInterestPaid());
				pstmt.setDouble(15, dm.getInterestPaid());
				pstmt.setDouble(16, 0);
				pstmt.setDouble(17, 0);
				pstmt.setDouble(18, 0);
				pstmt.setDouble(19, 0);
				pstmt.setString(20, vtml);
				pstmt.setString(21, vuser);
				pstmt.setString(22, dm.userverifier.getVerDate());
				pstmt.setString(23, vtml);
				pstmt.setString(24, vuser);
				pstmt.setString(25, dm.userverifier.getVerDate());
				pstmt.executeUpdate();

				dpcattype = 1;

				System.out.println("credit to loan account * loan recovery gl");

				System.out.println("dm.ac_type=====" + dm.getAccType());
				rs_trn = stmt_trn
						.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
								+ dpcattype
								+ " and gk.ac_type ='"
								+ dm.getLoanAcType()
								+ "' and trn_type='R' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
				rs_trn.next();
				GLTransObject trnobj = new GLTransObject();
				trnobj.setTrnDate(getSysDate());
				trnobj.setGLType(rs_trn.getString("gk.gl_type"));
				trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
				trnobj.setTrnMode("T");
				trnobj.setAmount((rs_main.getDouble("dep_paid") - dm
						.getInterestPaid())
						* rs_trn.getInt("mult_by"));
				trnobj.setCdind("C");
				trnobj.setAccType(dm.getLoanAcType());
				trnobj.setAccNo(String.valueOf(dm.getLoanAccno()));
				trnobj.setTrnType("R");
				trnobj.setRefNo(0);
				trnobj.setTrnSeq(ln_trn_seq);
				trnobj.setVtml(vtml);
				trnobj.setVid(vuser);
				trnobj.setVDate(dm.userverifier.getVerDate());
				commonLocal.storeGLTransaction(trnobj);

				dpcattype = 2;

				rs_trn = stmt_trn
						.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
								+ dpcattype
								+ " and gk.ac_type ='"
								+ dm.getLoanAcType()
								+ "' and trn_type='R' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
				rs_trn.next();
				trnobj = new GLTransObject();
				trnobj.setTrnDate(getSysDate());
				trnobj.setGLType(rs_trn.getString("gk.gl_type"));
				trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
				trnobj.setTrnMode("T");
				trnobj.setAmount((dm.getInterestPaid())
						* rs_trn.getInt("mult_by"));
				trnobj.setCdind("C");
				trnobj.setAccType(dm.getLoanAcType());
				trnobj.setAccNo(String.valueOf(dm.getLoanAccno()));
				trnobj.setTrnType("R");
				trnobj.setRefNo(0);
				trnobj.setTrnSeq(ln_trn_seq);
				trnobj.setVtml(vtml);
				trnobj.setVid(vuser);
				trnobj.setVDate(dm.userverifier.getVerDate());
				commonLocal.storeGLTransaction(trnobj);

				stmt_trn.executeUpdate("update LoanMaster set close_date='"
						+ getSysDate() + "' where ac_type='"
						+ dm.getLoanAcType() + "' and ac_no="
						+ dm.getLoanAccno());

				if (dpcattype == 0)
					dpcattype = 1;
				else
					dpcattype = 2;
				double loan_interest_amount = 0;
				if (interest_amount > 0) {
					dpcattype += 2;
					if (interest_amount > rs_main.getDouble("dep_paid")) {
						loan_interest_amount = rs_main.getDouble("dep_paid");
						interest_amount -= rs_main.getDouble("dep_paid");
					} else {
						loan_interest_amount = interest_amount;
						interest_amount = 0;
					}
					rs_trn = stmt_trn
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type = "
									+ modcode
									+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					rs_trn.next();
					trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_trn.getString("gk.gl_type"));
					trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
					trnobj.setTrnMode("T");
					trnobj.setAmount((loan_interest_amount)
							* rs_trn.getInt("mult_by"));
					trnobj.setCdind("D");
					trnobj.setAccType(dm.getAccType());
					trnobj.setAccNo(String.valueOf(dm.getAccNo()));
					trnobj.setTrnType("P");
					trnobj.setRefNo(0);
					trnobj.setTrnSeq(trnseq);
					trnobj.setVtml(vtml);
					trnobj.setVid(vuser);
					trnobj.setVDate(dm.userverifier.getVerDate());
					commonLocal.storeGLTransaction(trnobj);

					dpcattype -= 2;
					loan_interest_amount = rs_main.getDouble("dep_paid")
							- loan_interest_amount;
				} else {
					loan_interest_amount = rs_main.getDouble("dep_paid");
				}

				if (loan_interest_amount > 0) {

					System.out.println("interest > 0");
					rs_trn = stmt_trn
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type = "
									+ modcode
									+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					rs_trn.next();
					trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_trn.getString("gk.gl_type"));
					trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
					trnobj.setTrnMode("T");
					trnobj.setAmount((loan_interest_amount)
							* rs_trn.getInt("mult_by"));
					trnobj.setCdind("D");
					trnobj.setAccType(dm.getAccType());
					trnobj.setAccNo(String.valueOf(dm.getAccNo()));
					trnobj.setTrnType("P");
					trnobj.setRefNo(0);
					trnobj.setTrnSeq(trnseq);
					trnobj.setVtml(vtml);
					trnobj.setVid(vuser);
					trnobj.setVDate(dm.userverifier.getVerDate());
					commonLocal.storeGLTransaction(trnobj);
				}
				stmt_trn
						.executeUpdate("update DepositTransaction set trn_type='P',ve_tml='"
								+ vtml
								+ "',ve_user='"
								+ vuser
								+ "',ve_date = '"
								+ dm.userverifier.getVerDate()
								+ "' where trn_type='L' and ac_type='"
								+ dm.getAccType()
								+ "' and ac_no="
								+ dm.getAccNo());

				rs_main.next();
				trnseq = rs_main.getInt("trn_seq");

				if (dm.getTransferType().equalsIgnoreCase("P")) {
					PayOrderObject po = new PayOrderObject();
					po.setPOType("P");
					po.setPOCustType(0);
					po.setPOPayee(dm.getName());
					po.setPOAccType(dm.getAccType());
					po.setPOAccNo(dm.getAccNo());
					po.setPOGlCode(0);
					po.setPOAmount(rs_main.getDouble("dep_paid")
							+ rs_main.getDouble("int_paid"));
					po.setCommissionAmount(0);
					po.uv.setUserTml(vtml);
					po.uv.setUserId(vuser);
					po.uv.setUserDate(dm.userverifier.getVerDate());
					po.uv.setVerTml(vtml);
					po.uv.setVerId(vuser);
					po.uv.setVerDate(dm.userverifier.getVerDate());

					int_return_value = commonLocal.storePayOrder(po);
					stmt_main
							.executeUpdate("update DepositTransaction set ref_no="
									+ int_return_value
									+ ",trn_narr='By PO,PO no="
									+ int_return_value
									+ "' where trn_type='E' and ac_type='"
									+ dm.getAccType()
									+ "' and ac_no="
									+ dm.getAccNo() + " and trn_seq=" + trnseq);
					/**
					 * PayOrder Credit
					 */
					rs_trn = stmt_trn
							.executeQuery("select * from GLKeyParam where ac_type=1016001 and code=1");
					rs_trn.next();
					trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_trn.getString("gl_type"));
					trnobj.setGLCode(rs_trn.getString("gl_code"));
					trnobj.setTrnMode("T");
					trnobj.setAmount((rs_main.getDouble("dep_paid") + rs_main
							.getDouble("int_paid")));
					trnobj.setCdind("C");
					trnobj.setAccType(dm.getAccType());
					trnobj.setAccNo(String.valueOf(dm.getAccNo()));
					trnobj.setTrnType("P");
					trnobj.setRefNo(0);
					trnobj.setTrnSeq(trnseq);
					trnobj.setVtml(vtml);
					trnobj.setVid(vuser);
					trnobj.setVDate(dm.userverifier.getVerDate());
					commonLocal.storeGLTransaction(trnobj);

				} else if (dm.getTransferType().equalsIgnoreCase("T")) {
					AccountTransObject am = new AccountTransObject();
					String accty = dm.getTransferAccType();
					int accno = dm.getTransferAccno();
					am.setAccType(accty);
					am.setAccNo(accno);
					am.setTransDate(getSysDate());
					am.setTransType("P");
					am.setTransAmount(rs_main.getDouble("dep_paid")
							+ rs_main.getDouble("int_paid"));
					am.setTransMode("P");
					am.setTransSource(vtml);
					am.setCdInd("C");
					am.setChqDDNo(0);
					am.setChqDDDate("");
					am.setTransNarr(dm.getAccType() + " "
							+ String.valueOf(dm.getAccNo()));
					am.setRef_No(0);
					am.setPayeeName(dm.getName());
					am.setCloseBal(rs_main.getDouble("dep_paid")
							+ rs_main.getDouble("int_paid"));
					am.setLedgerPage(0);
					am.uv.setUserTml(vtml);
					am.uv.setUserId(vuser);
					am.uv.setUserDate(dm.userverifier.getVerDate());
					am.uv.setVerTml(vtml);
					am.uv.setVerId(vuser);
					am.uv.setVerDate(dm.userverifier.getVerDate());
					int_return_value = commonLocal.storeAccountTransaction(am);

					stmt_trn
							.executeUpdate("update DepositTransaction set trn_narr='"
									+ accty
									+ " "
									+ accno
									+ "' where ac_type='"
									+ dm.getAccType()
									+ "' and ac_no="
									+ dm.getAccNo()
									+ " and trn_seq="
									+ rs_main.getInt("trn_seq"));
				} else if (dm.getTransferType().equalsIgnoreCase("C")) {
					int_return_value = commonLocal.getModulesColumn(
							"lst_voucher_scroll_no", "1019000");
					// PreparedStatement pstmt_insert=
					// conn.prepareStatement("insert into DayCash
					// (scroll_no,trn_type,trn_date,ac_type,ac_no,csh_amt,cd_ind,vch_type,attached,vch_no,de_user,de_tml,de_date,ve_user,ve_tml,ve_date,name)
					// values
					// (0,'P',?,?,?,?,'C','P','F',?,?,?,?,null,null,null,?)");
					PreparedStatement pstmt_insert = conn
							.prepareStatement("insert into DayCash (scroll_no,trn_type,trn_date,ac_type,ac_no,csh_amt,cd_ind,vch_type,attached,vch_no,de_user,de_tml,de_date,ve_user,ve_tml,ve_date,name) values (0,'P',?,?,?,?,'C','P','F',?,?,?,?,?,?,?,?)");
					pstmt_insert.setString(1, getSysDate());
					pstmt_insert.setString(2, dm.getAccType());
					pstmt_insert.setInt(3, dm.getAccNo());
					pstmt_insert.setDouble(4, rs_main.getDouble("dep_paid")
							+ rs_main.getDouble("int_paid"));
					pstmt_insert.setInt(5, int_return_value);
					pstmt_insert.setString(6, vuser);
					pstmt_insert.setString(7, vtml);
					pstmt_insert.setString(8, dm.userverifier.getVerDate());
					// code added by sanjeet..
					pstmt_insert.setString(9, vuser);
					pstmt_insert.setString(10, vtml);
					pstmt_insert.setString(11, dm.userverifier.getVerDate());
					System.out.println("Customer name-----> " + dm.getName());

					pstmt_insert.setString(12, dm.getName());
					pstmt_insert.executeUpdate();
					stmt_trn
							.executeUpdate("update DepositTransaction set ref_no="
									+ int_return_value
									+ ",trn_narr='By Cash vch no"
									+ int_return_value
									+ "' where ac_type='"
									+ dm.getAccType()
									+ "' and ac_no="
									+ dm.getAccNo()
									+ " and trn_seq="
									+ rs_main.getInt("trn_seq"));
					/**
					 * cash payment gl
					 */
					rs_trn = stmt_trn
							.executeQuery("select * from GLKeyParam where ac_type=1019001 and code=1");
					rs_trn.next();
					trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_trn.getString("gl_type"));
					trnobj.setGLCode(rs_trn.getString("gl_code"));
					trnobj.setTrnMode("C");
					trnobj.setAmount((rs_main.getDouble("dep_paid") + rs_main
							.getDouble("int_paid")));
					trnobj.setCdind("C");
					trnobj.setAccType(dm.getAccType());
					trnobj.setAccNo(String.valueOf(dm.getAccNo()));
					trnobj.setTrnType("P");
					trnobj.setRefNo(0);
					trnobj.setTrnSeq(trnseq);
					trnobj.setVtml(vtml);
					trnobj.setVid(vuser);
					trnobj.setVDate(dm.userverifier.getVerDate());
					commonLocal.storeGLTransaction(trnobj);
				}

				/**
				 * check for int_paid -ve or +ve if paid == 0 directly debit
				 * from FD account gl if paid >0 debit from FD acc and FD
				 * payable Gl
				 */
				if (rs_main.getDouble("int_paid") == 0) {
					/**
					 * debit from FD account GL
					 */

					System.out.println("inside debit from FD account GL");
					dpcattype = dpcatold;
					if (dpcattype == 0)
						dpcattype = 1;
					else
						dpcattype = 2;
					rs_trn = stmt_trn
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type = "
									+ modcode
									+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					rs_trn.next();
					trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_trn.getString("gk.gl_type"));
					trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
					if (dm.getTransferType().equalsIgnoreCase("C"))
						trnobj.setTrnMode("C");
					else
						trnobj.setTrnMode("T");
					trnobj.setAmount((rs_main.getDouble("dep_paid") + rs_main
							.getDouble("int_paid"))
							* rs_trn.getInt("mult_by"));
					trnobj.setCdind("D");
					trnobj.setAccType(dm.getAccType());
					trnobj.setAccNo(String.valueOf(dm.getAccNo()));
					trnobj.setTrnType("P");
					trnobj.setRefNo(0);
					trnobj.setTrnSeq(trnseq);
					trnobj.setVtml(vtml);
					trnobj.setVid(vuser);
					trnobj.setVDate(dm.userverifier.getVerDate());
					commonLocal.storeGLTransaction(trnobj);
				} else if (rs_main.getDouble("int_paid") > 0) {
					/**
					 * debit FD acc Gl
					 */

					System.out.println("inside====******==debit FD acc GL");
					dpcattype = dpcatold;
					if (dpcattype == 0)
						dpcattype = 1;
					else
						dpcattype = 2;
					rs_trn = stmt_trn
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type = "
									+ modcode
									+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					if (rs_trn.next()) {
						trnobj = new GLTransObject();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_trn.getString("gk.gl_type"));
						trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
						if (dm.getTransferType().equalsIgnoreCase("C"))
							trnobj.setTrnMode("C");
						else
							trnobj.setTrnMode("T");
						trnobj.setAmount((rs_main.getDouble("dep_paid"))
								* rs_trn.getInt("mult_by"));
						trnobj.setCdind("D");
						trnobj.setAccType(dm.getAccType());
						trnobj.setAccNo(String.valueOf(dm.getAccNo()));
						trnobj.setTrnType("P");
						trnobj.setRefNo(0);
						trnobj.setTrnSeq(trnseq);
						trnobj.setVtml(vtml);
						trnobj.setVid(vuser);
						trnobj.setVDate(dm.userverifier.getVerDate());
						commonLocal.storeGLTransaction(trnobj);
					}

					dpcattype += 2;
					rs_trn = stmt_trn
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type = "
									+ modcode
									+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					if (rs_trn.next()) {
						trnobj = new GLTransObject();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_trn.getString("gk.gl_type"));
						trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
						if (dm.getTransferType().equalsIgnoreCase("C"))
							trnobj.setTrnMode("C");
						else
							trnobj.setTrnMode("T");
						trnobj.setAmount((rs_main.getDouble("int_paid"))
								* rs_trn.getInt("mult_by"));
						trnobj.setCdind("D");
						trnobj.setAccType(dm.getAccType());
						trnobj.setAccNo(String.valueOf(dm.getAccNo()));
						trnobj.setTrnType("P");
						trnobj.setRefNo(0);
						trnobj.setTrnSeq(trnseq);
						trnobj.setVtml(vtml);
						trnobj.setVid(vuser);
						trnobj.setVDate(dm.userverifier.getVerDate());
						commonLocal.storeGLTransaction(trnobj);
					}
				}
			}
			stmt_trn
					.executeUpdate("update DepositTransaction set trn_type='P',ve_tml='"
							+ vtml
							+ "',ve_user='"
							+ vuser
							+ "',ve_date = '"
							+ dm.userverifier.getVerDate()
							+ "' where trn_type='E' and ac_type='"
							+ dm.getAccType()
							+ "' and ac_no="
							+ dm.getAccNo()
							+ " and trn_seq=" + trnseq);
			stmt_main.executeUpdate("update DepositMaster set close_ind="
					+ closeind + ",close_date = '" + getSysDate()
					+ "',int_paid_date='" + getSysDate() + "',int_upto_date='"
					+ commonLocal.getFutureDayDate(dm.getClosedt(), -1)
					+ "' where ac_no=" + dm.getAccNo() + " and ac_type='"
					+ dm.getAccType() + "'");
		} catch (SQLException ve) {
			ve.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordsNotInsertedException();
		} catch (CreateException e) {
			e.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordsNotInsertedException();
		} catch (Exception v) {
			v.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordNotInsertedException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			commonLocal = null;
		}
		return int_return_value;
	}

	/**
	 * Verification of Deposit Renewal i.e verify the old account then verify
	 * new account
	 * 
	 * @throws Verified
	 * @throws RecordsNotInsertedException
	 * @throws CreateException
	 * @throws DateFormatException
	 * @throws FreezedAccountException
	 * @throws InOperativeAccountException
	 * @throws AccountClosedException
	 */
	public int verifyDepositRenewal(DepositMasterObject depmast, int type)
			throws Verified, RecordsNotInsertedException {
		ResultSet rs_main = null, rs_trn = null;
		Statement stmt_main = null, stmt_trn = null;
		Connection conn = null;
		String modcode;
		int int_return_value = 0;
		/**
		 * type 0 means FD Renewal
		 */
		try {
			conn = getConnection();
			commonLocal = commonLocalHome.create();
			stmt_main = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			stmt_trn = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			if (type == 0) {
				rs_trn = stmt_main
						.executeQuery("select ac_no from DepositMaster where ac_type='"
								+ depmast.getAccType()
								+ "' and ac_no="
								+ depmast.getAccNo()
								+ " and ve_tml is not null");
				rs_trn.last();
				if (rs_trn.getRow() > 0)
					throw new Verified();

				rs_main = stmt_main
						.executeQuery("select trn_seq,dt.dep_amt,int_amt,dep_paid,int_paid,mat_date,trn_mode,trn_narr,custtype from CustomerMaster cm,DepositTransaction dt,DepositMaster dm where cm.cid=dm.cid and dm.ac_type=dt.ac_type and dm.ac_no=dt.ac_no and dm.ac_type ='"
								+ depmast.getRenType()
								+ "' and dm.ac_no= "
								+ depmast.getRenewalAccno()
								+ " and trn_type in ('E','A') order by trn_seq");
				rs_main.next();
				int dpcattype = rs_main.getInt("custtype");
				int olddpcat = dpcattype;
				modcode = depmast.getRenType();
				int int_lst_tr_seq = rs_main.getInt("trn_seq");
				/**
				 * Credit to FD payable Gl Debit to loss Gl
				 */
				if (dpcattype == 0)
					dpcattype = 3;
				else
					dpcattype = 4;
				rs_trn = stmt_trn
						.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
								+ dpcattype
								+ " and gk.ac_type = "
								+ modcode
								+ " and trn_type='I' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
				rs_trn.next();
				GLTransObject trnobj = new GLTransObject();
				trnobj.setTrnDate(getSysDate());
				trnobj.setGLType(rs_trn.getString("gk.gl_type"));
				trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
				trnobj.setTrnMode("T");
				trnobj.setAmount(rs_main.getDouble("int_amt")
						* rs_trn.getInt("mult_by"));
				trnobj.setCdind("C");
				trnobj.setAccType(depmast.getRenType());
				trnobj.setAccNo(String.valueOf(depmast.getRenewalAccno()));
				trnobj.setTrnType("I");
				trnobj.setRefNo(0);
				trnobj.setTrnSeq(int_lst_tr_seq);
				trnobj.setVtml(depmast.userverifier.getVerTml());
				trnobj.setVid(depmast.userverifier.getVerId());
				trnobj.setVDate(depmast.userverifier.getVerDate());
				commonLocal.storeGLTransaction(trnobj);

				dpcattype += 2;
				rs_trn = stmt_trn
						.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
								+ dpcattype
								+ " and gk.ac_type = "
								+ modcode
								+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
				rs_trn.next();
				trnobj = new GLTransObject();
				trnobj.setTrnDate(getSysDate());
				trnobj.setGLType(rs_trn.getString("gk.gl_type"));
				trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
				trnobj.setTrnMode("T");
				trnobj.setAmount(rs_main.getDouble("int_amt")
						* rs_trn.getInt("mult_by"));
				trnobj.setCdind("D");
				trnobj.setAccType(depmast.getRenType());
				trnobj.setAccNo(String.valueOf(depmast.getRenewalAccno()));
				trnobj.setTrnType("P");
				trnobj.setRefNo(0);
				trnobj.setTrnSeq(int_lst_tr_seq);
				trnobj.setVtml(depmast.userverifier.getVerTml());
				trnobj.setVid(depmast.userverifier.getVerId());
				trnobj.setVDate(depmast.userverifier.getVerDate());
				commonLocal.storeGLTransaction(trnobj);

				/**
				 * update int_upto_date
				 */
				stmt_trn
						.executeUpdate("update DepositMaster set int_upto_date='"
								+ commonLocal.getFutureDayDate(rs_main
										.getString("mat_date"), -1)
								+ "' where ac_no="
								+ depmast.getRenewalAccno()
								+ " and ac_type='" + depmast.getRenType() + "'");
				stmt_trn
						.executeUpdate("update DepositTransaction set trn_type='I',ve_tml='"
								+ depmast.userverifier.getVerTml()
								+ "',ve_user='"
								+ depmast.userverifier.getVerId()
								+ "',ve_date = '"
								+ depmast.userverifier.getVerDate()
								+ "' where trn_seq="
								+ int_lst_tr_seq
								+ " and trn_type='E' and ac_type='"
								+ depmast.getRenType()
								+ "' and ac_no="
								+ depmast.getRenewalAccno());

				rs_main.next();
				int_lst_tr_seq = rs_main.getInt("trn_seq");
				/**
				 * check for refund amount. if yes debit fd payable then credit
				 * any one T/PO/Cash if no debit fd payable credit to new acc
				 * for all cases debit fd acc gl credit to new acc gl
				 */
				if (rs_main.isLast() == false) {
					dpcattype = olddpcat;
					if (dpcattype == 0)
						dpcattype = 3;
					else
						dpcattype = 4;
					/**
					 * if yes debit fd gl post any one and update dep trn
					 */
					if (rs_main.getString("trn_mode").equals("P")) {
						PayOrderObject po = new PayOrderObject();
						po.setPOType("P");
						po.setPOCustType(0);
						po.setPOPayee(depmast.getName());
						po.setPOAccType(depmast.getRenType());
						po.setPOAccNo(depmast.getRenewalAccno());
						po.setPOGlCode(0);
						po.setPOAmount(rs_main.getDouble("int_paid"));
						po.setCommissionAmount(0);
						po.uv.setUserTml(depmast.userverifier.getVerTml());
						po.uv.setUserId(depmast.userverifier.getVerId());
						po.uv.setUserDate(depmast.userverifier.getVerDate());
						po.uv.setVerTml(depmast.userverifier.getVerTml());
						po.uv.setVerId(depmast.userverifier.getVerId());
						po.uv.setVerDate(depmast.userverifier.getVerDate());
						int_return_value = commonLocal.storePayOrder(po);
						stmt_trn
								.executeUpdate("update DepositTransaction set trn_type='P',ref_no="
										+ int_return_value
										+ ",trn_narr='By PO,PO no="
										+ int_return_value
										+ "',ve_tml='"
										+ depmast.userverifier.getVerTml()
										+ "',ve_user='"
										+ depmast.userverifier.getVerId()
										+ "',ve_date = '"
										+ depmast.userverifier.getVerDate()
										+ "' where trn_seq="
										+ int_lst_tr_seq
										+ " and trn_type='A' and ac_type='"
										+ depmast.getRenType()
										+ "' and ac_no="
										+ depmast.getRenewalAccno());
						/**
						 * PayOrder Credit
						 */
						rs_trn = stmt_trn
								.executeQuery("select * from GLKeyParam where ac_type=1016001 and code=1");
						rs_trn.next();
						trnobj = new GLTransObject();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_trn.getString("gl_type"));
						trnobj.setGLCode(rs_trn.getString("gl_code"));
						trnobj.setTrnMode("T");
						trnobj.setAmount((rs_main.getDouble("int_paid")));
						trnobj.setCdind("C");
						trnobj.setAccType(depmast.getRenType());
						trnobj.setAccNo(String.valueOf(depmast
								.getRenewalAccno()));
						trnobj.setTrnType("P");
						trnobj.setRefNo(0);
						trnobj.setTrnSeq(int_lst_tr_seq);
						trnobj.setVtml(depmast.userverifier.getVerTml());
						trnobj.setVid(depmast.userverifier.getVerId());
						trnobj.setVDate(depmast.userverifier.getVerDate());
						commonLocal.storeGLTransaction(trnobj);

						rs_trn = stmt_trn
								.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
										+ dpcattype
										+ " and gk.ac_type = "
										+ modcode
										+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
						rs_trn.next();
						trnobj = new GLTransObject();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_trn.getString("gk.gl_type"));
						trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
						trnobj.setTrnMode("T");
						trnobj.setAmount((rs_main.getDouble("int_paid"))
								* rs_trn.getInt("mult_by"));
						trnobj.setCdind("D");
						trnobj.setAccType(depmast.getRenType());
						trnobj.setAccNo(String.valueOf(depmast
								.getRenewalAccno()));
						trnobj.setTrnType("P");
						trnobj.setRefNo(0);
						trnobj.setTrnSeq(int_lst_tr_seq);
						trnobj.setVtml(depmast.userverifier.getVerTml());
						trnobj.setVid(depmast.userverifier.getVerId());
						trnobj.setVDate(depmast.userverifier.getVerDate());
						commonLocal.storeGLTransaction(trnobj);
					} else if (rs_main.getString("trn_mode").equals("T")) {
						AccountTransObject am = new AccountTransObject();
						// StringTokenizer st = new
						// StringTokenizer(rs_main.getString("trn_narr")," ");
						// String accty = st.nextToken();
						// int accno= Integer.parseInt(st.nextToken());
						am.setAccType(depmast.getTransferAccType());
						am.setAccNo(depmast.getTransferAccno());
						am.setTransDate(getSysDate());
						am.setTransType("R");
						am.setTransAmount(rs_main.getDouble("int_paid"));
						am.setTransMode("T");
						am.setTransSource(depmast.userverifier.getVerTml());
						am.setCdInd("C");
						am.setChqDDNo(0);
						am.setChqDDDate("");
						am.setTransNarr(depmast.getRenType() + " "
								+ depmast.getRenewalAccno());
						am.setRef_No(0);
						am.setPayeeName(depmast.getName());
						am.setCloseBal(rs_main.getDouble("dep_paid")
								+ rs_main.getDouble("int_paid"));
						am.setLedgerPage(0);
						am.uv.setUserTml(depmast.userverifier.getVerTml());
						am.uv.setUserId(depmast.userverifier.getVerId());
						am.uv.setUserDate(depmast.userverifier.getVerDate());
						am.uv.setVerTml(depmast.userverifier.getVerTml());
						am.uv.setVerId(depmast.userverifier.getVerId());
						am.uv.setVerDate(depmast.userverifier.getVerDate());
						commonLocal.storeAccountTransaction(am);

						rs_trn = stmt_trn
								.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
										+ dpcattype
										+ " and gk.ac_type = "
										+ modcode
										+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
						rs_trn.next();
						trnobj = new GLTransObject();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_trn.getString("gk.gl_type"));
						trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
						trnobj.setTrnMode("T");
						trnobj.setAmount((rs_main.getDouble("int_paid"))
								* rs_trn.getInt("mult_by"));
						trnobj.setCdind("D");
						trnobj.setAccType(depmast.getRenType());
						trnobj.setAccNo(String.valueOf(depmast
								.getRenewalAccno()));
						trnobj.setTrnType("P");
						trnobj.setRefNo(0);
						trnobj.setTrnSeq(int_lst_tr_seq);
						trnobj.setVtml(depmast.userverifier.getVerTml());
						trnobj.setVid(depmast.userverifier.getVerId());
						trnobj.setVDate(depmast.userverifier.getVerDate());
						commonLocal.storeGLTransaction(trnobj);

						stmt_trn
								.executeUpdate("update DepositTransaction set trn_type='P',ve_tml='"
										+ depmast.userverifier.getVerTml()
										+ "',ve_user='"
										+ depmast.userverifier.getVerId()
										+ "',ve_date = '"
										+ depmast.userverifier.getVerDate()
										+ "' where trn_seq="
										+ int_lst_tr_seq
										+ " and trn_type='A' and ac_type='"
										+ depmast.getRenType()
										+ "' and ac_no="
										+ depmast.getRenewalAccno());
					} else if (rs_main.getString("trn_mode").equals("C")) {
						int_return_value = commonLocal.getModulesColumn(
								"lst_voucher_scroll_no", "1019000");
						PreparedStatement pstmt_insert = conn
								.prepareStatement("insert into DayCash (scroll_no,trn_type,trn_date,ac_type,ac_no,csh_amt,cd_ind,vch_type,attached,vch_no,de_user,de_tml,de_date,ve_user,ve_tml,ve_date,name) values (0,'P',?,?,?,?,'C','P','F',?,?,?,?,?,?,?,?)");
						pstmt_insert.setString(1, getSysDate());
						pstmt_insert.setString(2, depmast.getRenType());
						pstmt_insert.setInt(3, depmast.getRenewalAccno());
						pstmt_insert.setDouble(4, rs_main.getDouble("dep_paid")
								+ rs_main.getDouble("int_paid"));
						pstmt_insert.setInt(5, int_return_value);
						pstmt_insert.setString(6, depmast.userverifier
								.getVerId());
						pstmt_insert.setString(7, depmast.userverifier
								.getVerTml());
						pstmt_insert.setString(8, depmast.userverifier
								.getVerDate());
						pstmt_insert.setString(9, depmast.userverifier
								.getVerId());
						pstmt_insert.setString(10, depmast.userverifier
								.getVerTml());
						pstmt_insert.setString(11, depmast.userverifier
								.getVerDate());
						;
						pstmt_insert.setString(12, depmast.getName());
						pstmt_insert.executeUpdate();
						stmt_trn
								.executeUpdate("update DepositTransaction set trn_type='P',ref_no="
										+ int_return_value
										+ ",trn_narr='By Cash vch no"
										+ int_return_value
										+ "',ve_tml='"
										+ depmast.userverifier.getVerTml()
										+ "',ve_user='"
										+ depmast.userverifier.getVerId()
										+ "',ve_date = '"
										+ depmast.userverifier.getVerDate()
										+ "' where trn_seq="
										+ int_lst_tr_seq
										+ " and trn_type='A' and ac_type='"
										+ depmast.getRenType()
										+ "' and ac_no="
										+ depmast.getRenewalAccno());

						// rs_trn = stmt_trn.executeQuery("select
						// gk.gl_code,gk.gl_type,mult_by from GLKeyParam
						// gk,GLPost gp where code = 1 and gk.ac_type = 1999001
						// and trn_type='P' and cr_dr='C' and gk.ac_type =
						// gp.ac_type and gp.gl_code = gk.gl_code");
						// Modified by Riswan => CASH GL
						rs_trn = stmt_trn
								.executeQuery("select gl_type,gl_code from GLKeyParam where ac_type='1019001'");
						rs_trn.next();
						trnobj = new GLTransObject();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_trn.getString("gl_type"));
						trnobj.setGLCode(rs_trn.getString("gl_code"));
						trnobj.setTrnMode("C");
						trnobj.setAmount((rs_main.getDouble("int_paid")));
						trnobj.setCdind("C");
						trnobj.setAccType(depmast.getRenType());
						trnobj.setAccNo(String.valueOf(depmast
								.getRenewalAccno()));
						trnobj.setTrnType("P");
						trnobj.setRefNo(0);
						trnobj.setTrnSeq(int_lst_tr_seq);
						trnobj.setVtml(depmast.userverifier.getVerTml());
						trnobj.setVid(depmast.userverifier.getVerId());
						trnobj.setVDate(depmast.userverifier.getVerDate());
						commonLocal.storeGLTransaction(trnobj);
						// debit for gl code..
						rs_trn = stmt_trn
								.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
										+ dpcattype
										+ " and gk.ac_type = "
										+ modcode
										+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
						rs_trn.next();
						trnobj = new GLTransObject();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_trn.getString("gk.gl_type"));
						trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
						trnobj.setTrnMode("C");
						trnobj.setAmount((rs_main.getDouble("int_paid"))
								* rs_trn.getInt("mult_by"));
						trnobj.setCdind("D");
						trnobj.setAccType(depmast.getRenType());
						trnobj.setAccNo(String.valueOf(depmast
								.getRenewalAccno()));
						trnobj.setTrnType("P");
						trnobj.setRefNo(0);
						trnobj.setTrnSeq(int_lst_tr_seq);
						trnobj.setVtml(depmast.userverifier.getVerTml());
						trnobj.setVid(depmast.userverifier.getVerId());
						trnobj.setVDate(depmast.userverifier.getVerDate());
						commonLocal.storeGLTransaction(trnobj);
					}
					rs_main.next();
					int_lst_tr_seq = rs_main.getInt("trn_seq");
				} else {
					dpcattype = olddpcat;
					if (dpcattype == 0)
						dpcattype = 3;
					else
						dpcattype = 4;
					/**
					 * debit fd payable gl
					 */
					rs_trn = stmt_trn
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type = "
									+ modcode
									+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					rs_trn.next();
					trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_trn.getString("gk.gl_type"));
					trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
					trnobj.setTrnMode("T");
					trnobj.setAmount((rs_main.getDouble("int_paid"))
							* rs_trn.getInt("mult_by"));
					trnobj.setCdind("D");
					trnobj.setAccType(depmast.getRenType());
					trnobj.setAccNo(String.valueOf(depmast.getRenewalAccno()));
					trnobj.setTrnType("P");
					trnobj.setRefNo(0);
					trnobj.setTrnSeq(int_lst_tr_seq);
					trnobj.setVtml(depmast.userverifier.getVerTml());
					trnobj.setVid(depmast.userverifier.getVerId());
					trnobj.setVDate(depmast.userverifier.getVerDate());
					commonLocal.storeGLTransaction(trnobj);
				}
				/**
				 * Debit FD acc gl for old account
				 */
				dpcattype = olddpcat;
				if (dpcattype == 0)
					dpcattype = 1;
				else
					dpcattype = 2;
				rs_trn = stmt_trn
						.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
								+ dpcattype
								+ " and gk.ac_type = "
								+ modcode
								+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
				rs_trn.next();
				trnobj = new GLTransObject();
				trnobj.setTrnDate(getSysDate());
				trnobj.setGLType(rs_trn.getString("gk.gl_type"));
				trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
				trnobj.setTrnMode("T");
				trnobj.setAmount((rs_main.getDouble("dep_paid"))
						* rs_trn.getInt("mult_by"));
				trnobj.setCdind("D");
				trnobj.setAccType(depmast.getRenType());
				trnobj.setAccNo(String.valueOf(depmast.getRenewalAccno()));
				trnobj.setTrnType("P");
				trnobj.setRefNo(0);
				trnobj.setTrnSeq(int_lst_tr_seq);
				trnobj.setVtml(depmast.userverifier.getVerTml());
				trnobj.setVid(depmast.userverifier.getVerId());
				trnobj.setVDate(depmast.userverifier.getVerDate());
				commonLocal.storeGLTransaction(trnobj);

				/**
				 * update depositmaster and trn for old account
				 */
				stmt_trn
						.executeUpdate("update DepositTransaction set trn_type='P',ve_tml='"
								+ depmast.userverifier.getVerTml()
								+ "',ve_user='"
								+ depmast.userverifier.getVerId()
								+ "',ve_date = '"
								+ depmast.userverifier.getVerDate()
								+ "' where trn_seq="
								+ int_lst_tr_seq
								+ " and trn_type='E' and ac_type='"
								+ depmast.getRenType()
								+ "' and ac_no="
								+ depmast.getRenewalAccno());
				stmt_trn
						.executeUpdate("update DepositMaster set close_ind=4,close_date='"
								+ getSysDate()
								+ "' where ac_no="
								+ depmast.getRenewalAccno()
								+ " and ac_type='"
								+ depmast.getRenType() + "'");

				/**
				 * if extra amount received
				 */
				double double_extra_dep_amount = (depmast.getDepositAmt())
						- (rs_main.getDouble("dep_paid") + rs_main
								.getDouble("int_paid"));
				if (depmast.getReceivedBy().equals("T")) {
					AccountTransObject am = new AccountTransObject();
					am.setAccType(depmast.getReceivedAccType());
					am.setAccNo(depmast.getReceivedAccno());
					am.setTransDate(getSysDate());
					am.setTransType("P");
					am.setTransAmount(double_extra_dep_amount);
					am.setTransMode("T");
					am.setTransSource(depmast.userverifier.getVerTml());
					am.setCdInd("D");
					am.setChqDDNo(0);
					am.setChqDDDate("");
					am.setTransNarr(depmast.getAccType() + " "
							+ String.valueOf(depmast.getAccNo()));
					am.setRef_No(0);
					am.setPayeeName(depmast.getName());
					am.setCloseBal(double_extra_dep_amount);
					am.setLedgerPage(0);
					am.uv.setUserTml(depmast.userverifier.getVerTml());
					am.uv.setUserId(depmast.userverifier.getVerId());
					am.uv.setUserDate(depmast.userverifier.getVerDate());
					am.uv.setVerTml(depmast.userverifier.getVerTml());
					am.uv.setVerId(depmast.userverifier.getVerId());
					am.uv.setVerDate(depmast.userverifier.getVerDate());
					commonLocal.storeAccountTransaction(am);
				} else if (depmast.getReceivedBy().equals("C")) {
					// rs_trn = stmt_trn.executeQuery("select
					// gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost
					// gp where code = 1 and gk.ac_type = '1999001' and
					// trn_type='R' and cr_dr='D' and gk.ac_type = gp.ac_type
					// and gp.gl_code = gk.gl_code");
					rs_trn = stmt_trn
							.executeQuery("select * from GLKeyParam where ac_type='1019001' and code=1");
					rs_trn.next();
					trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_trn.getString("gl_type"));
					trnobj.setGLCode(rs_trn.getString("gl_code"));
					trnobj.setTrnMode("C");
					trnobj.setAmount(double_extra_dep_amount);
					trnobj.setCdind("D");
					trnobj.setAccType(depmast.getAccType());
					trnobj.setAccNo(String.valueOf(depmast.getAccNo()));
					trnobj.setTrnType("R");
					trnobj.setTrnSeq(0);
					trnobj.setVtml(depmast.userverifier.getVerTml());
					trnobj.setVid(depmast.userverifier.getVerId());
					// code added by sanjeet.
					trnobj.setVDate(depmast.userverifier.getVerDate());
					commonLocal.storeGLTransaction(trnobj);
				} else if (depmast.getReceivedBy().equals("G")) {
					rs_trn = stmt_trn
							.executeQuery("select gl_code,gl_type from GLKeyParam where code = 3 and ac_type = '1011001'");
					rs_trn.next();
					trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_trn.getString("gl_type"));
					trnobj.setGLCode(rs_trn.getString("gl_code"));
					trnobj.setTrnMode("T");
					trnobj.setAmount(double_extra_dep_amount);
					trnobj.setCdind("D");
					trnobj.setAccType(null);
					trnobj.setAccNo(String.valueOf(0));
					trnobj.setTrnType("P");
					trnobj.setTrnSeq(0);
					trnobj.setVtml(depmast.userverifier.getVerTml());
					trnobj.setVid(depmast.userverifier.getVerId());
					// code added by sanjeet.
					trnobj.setVDate(depmast.userverifier.getVerDate());
					commonLocal.storeGLTransaction(trnobj);
				}
				/**
				 * for new fd gl
				 */
				dpcattype = depmast.getDPType();
				olddpcat = dpcattype;
				modcode = depmast.getAccType();
				if (dpcattype == 0)
					dpcattype = 1;
				else
					dpcattype = 2;
				int_lst_tr_seq = 1;
				if (depmast.getReceivedBy().equals("C"))// This is the credit
				// entry to the new
				// account.
				{
					rs_trn = stmt_trn
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type = "
									+ modcode
									+ " and trn_type='D' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					if (rs_trn.next()) {
						trnobj = new GLTransObject();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_trn.getString("gk.gl_type"));
						trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
						trnobj.setTrnMode("C");
						trnobj.setAmount(double_extra_dep_amount);
						trnobj.setCdind("C");
						trnobj.setAccType(depmast.getAccType());
						trnobj.setAccNo(String.valueOf(depmast.getAccNo()));
						trnobj.setTrnType("D");
						trnobj.setRefNo(0);
						trnobj.setTrnSeq(1);
						trnobj.setVtml(depmast.userverifier.getVerTml());
						trnobj.setVid(depmast.userverifier.getVerId());
						trnobj.setVDate(depmast.userverifier.getVerDate());
						commonLocal.storeGLTransaction(trnobj);
					}

					rs_trn = stmt_trn
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type = "
									+ modcode
									+ " and trn_type='D' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					if (rs_trn.next()) {
						trnobj = new GLTransObject();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_trn.getString("gk.gl_type"));
						trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
						trnobj.setTrnMode("T");
						trnobj
								.setAmount((depmast.getDepositAmt() - double_extra_dep_amount)
										* rs_trn.getInt("mult_by"));
						trnobj.setCdind("C");
						trnobj.setAccType(depmast.getAccType());
						trnobj.setAccNo(String.valueOf(depmast.getAccNo()));
						trnobj.setTrnType("D");
						trnobj.setRefNo(0);
						trnobj.setTrnSeq(1);
						trnobj.setVtml(depmast.userverifier.getVerTml());
						trnobj.setVid(depmast.userverifier.getVerId());
						trnobj.setVDate(depmast.userverifier.getVerDate());
						commonLocal.storeGLTransaction(trnobj);
					}
				} else {
					rs_trn = stmt_trn
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dpcattype
									+ " and gk.ac_type = "
									+ modcode
									+ " and trn_type='D' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					if (rs_trn.next()) {
						trnobj = new GLTransObject();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_trn.getString("gk.gl_type"));
						trnobj.setGLCode(rs_trn.getString("gk.gl_code"));
						trnobj.setTrnMode("T");
						trnobj.setAmount(depmast.getDepositAmt()
								* rs_trn.getInt("mult_by"));
						trnobj.setCdind("C");
						trnobj.setAccType(depmast.getAccType());
						trnobj.setAccNo(String.valueOf(depmast.getAccNo()));
						trnobj.setTrnType("D");
						trnobj.setRefNo(0);
						trnobj.setTrnSeq(1);
						trnobj.setVtml(depmast.userverifier.getVerTml());
						trnobj.setVid(depmast.userverifier.getVerId());
						trnobj.setVDate(depmast.userverifier.getVerDate());
						commonLocal.storeGLTransaction(trnobj);
					}
				}

				stmt_trn.executeUpdate("update DepositTransaction set ve_tml='"
						+ depmast.userverifier.getVerTml() + "',ve_user='"
						+ depmast.userverifier.getVerId() + "',ve_date = '"
						+ depmast.userverifier.getVerDate() + "' where ac_no="
						+ depmast.getAccNo() + " and ac_type='"
						+ depmast.getAccType() + "'");
				stmt_trn
						.executeUpdate("update DepositMaster set close_ind=0,ve_user='"
								+ depmast.userverifier.getVerId()
								+ "',ve_tml='"
								+ depmast.userverifier.getVerTml()
								+ "',ve_date='"
								+ depmast.userverifier.getVerDate()
								+ "' where ac_type='"
								+ depmast.getAccType()
								+ "' and ac_no=" + depmast.getAccNo());
				stmt_trn
						.executeUpdate("insert into DepositMasterLog select * from DepositMaster where ac_no="
								+ depmast.getAccNo()
								+ " and ac_type='"
								+ depmast.getAccType() + "'");
				stmt_trn
						.executeUpdate("insert into DepositMasterLog select * from DepositMaster where ac_no="
								+ depmast.getRenewalAccno()
								+ " and ac_type='"
								+ depmast.getRenType() + "'");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordsNotInsertedException();
		} catch (CreateException e) {
			e.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordsNotInsertedException();
		} catch (Exception v) {
			v.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordNotInsertedException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			commonLocal = null;
		}
		return int_return_value;
	}

	public DepositTransactionObject[] getAutoRenewalList(String ac_type,
			String date_fr, String date_to, int type, int flag, String query)
			throws RecordsNotFoundException {
		System.out.println("from date====" + date_fr);
		System.out.println("to date====" + date_to);

		String frdate = Validations.convertYMD(date_fr);
		String todate = Validations.convertYMD(date_to);

		System.out.println(" validation ---- from date====" + frdate);
		System.out.println("validation------to date====" + todate);

		DepositTransactionObject array_deposittransactionobject[] = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ResultSet rs_mst = null;
			Statement st_mst = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			if (type == 0) {
				if (flag == 0)
					rs_mst = st_mst
							.executeQuery("select moduleabbr,dm.ac_type,dm.ac_no,dep_date,mat_date,dep_days,dm.dep_amt,mat_amt,auto_renewal,ren_ac_type,ren_ac_no from DepositMaster dm,Modules where modulecode=dm.ac_type and dep_renewed='T' and concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1,(locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,locate('/',close_date)-1)) between '"
									+ frdate
									+ "' and '"
									+ todate
									+ "' order by dm.ac_type,dm.ac_no");
				else
					rs_mst = st_mst
							.executeQuery("select moduleabbr,dm.ac_type,dm.ac_no,dep_date,mat_date,dep_days,dm.dep_amt,mat_amt,auto_renewal,ren_ac_type,ren_ac_no from DepositMaster dm,Modules where modulecode=dm.ac_type and dep_renewed='T' and concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1,(locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,locate('/',close_date)-1)) between '"
									+ frdate
									+ "' and '"
									+ todate
									+ "' and ("
									+ query + ") order by dm.ac_type,dm.ac_no");
			} else if (type == 1) {
				if (flag == 0)
					rs_mst = st_mst
							.executeQuery("select moduleabbr,dm.ac_type,dm.ac_no,dep_date,mat_date,dep_days,dm.dep_amt,mat_amt,auto_renewal,ren_ac_type,ren_ac_no from DepositMaster dm,Modules where dm.ac_type='"
									+ ac_type
									+ "' and modulecode=dm.ac_type and dep_renewed='T' and concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1,(locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,locate('/',close_date)-1)) between '"
									+ frdate
									+ "' and '"
									+ todate
									+ "' order by dm.ac_type,dm.ac_no");
				else
					rs_mst = st_mst
							.executeQuery("select moduleabbr,dm.ac_type,dm.ac_no,dep_date,mat_date,dep_days,dm.dep_amt,mat_amt,auto_renewal,ren_ac_type,ren_ac_no from DepositMaster dm,Modules where dm.ac_type='"
									+ ac_type
									+ "' and modulecode=dm.ac_type and dep_renewed='T' and concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1,(locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,locate('/',close_date)-1)) between '"
									+ frdate
									+ "' and '"
									+ todate
									+ "'and ("
									+ query + ") order by dm.ac_type,dm.ac_no");
			}
			rs_mst.last();
			if (rs_mst.getRow() == 0)
				throw new RecordsNotFoundException();
			array_deposittransactionobject = new DepositTransactionObject[rs_mst
					.getRow()];
			rs_mst.beforeFirst();
			int i = 0;
			while (rs_mst.next()) {
				array_deposittransactionobject[i] = new DepositTransactionObject();
				array_deposittransactionobject[i].setTranSou(rs_mst
						.getString("moduleabbr"));
				array_deposittransactionobject[i].setAccType(rs_mst
						.getString("ac_type"));
				array_deposittransactionobject[i].setAccNo(rs_mst
						.getInt("ac_no"));
				array_deposittransactionobject[i].setCdind(rs_mst
						.getString("dep_date"));
				array_deposittransactionobject[i].setPaidDate(rs_mst
						.getString("mat_date"));
				array_deposittransactionobject[i].setDepositAmt(rs_mst
						.getDouble("dm.dep_amt"));
				array_deposittransactionobject[i].setRDBalance(rs_mst
						.getDouble("mat_amt"));
				array_deposittransactionobject[i].setInterestDate(rs_mst
						.getString("auto_renewal"));
				array_deposittransactionobject[i].setTranKey(rs_mst
						.getInt("dep_days"));
				array_deposittransactionobject[i].setReferenceNo(rs_mst
						.getInt("ren_ac_no"));
				i++;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return array_deposittransactionobject;
	}

	public DepositTransactionObject[] getAutoRenewalInfo()
			throws CreateException, RecordsNotFoundException {
		DepositTransactionObject array_deposittransactionobject[] = null;
		Connection conn = null;
		try {

			System.out.println("Inside BEAN:::::::getAutoRenewalInfo");

			System.out.println("Date in bean:::::");
			conn = getConnection();
			commonLocal = commonLocalHome.create();
			ResultSet rs_mst = null;
			Statement st_mst = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			rs_mst = st_mst
					.executeQuery("select concat_ws(' ',ifnull(cm.fname,' '),ifnull(cm.mname,' '),ifnull(cm.lname,'')) as name,moduleabbr,dm.ac_type,dm.ac_no,autorenewal_no,dep_date,mat_date,dep_days,dm.dep_amt,mat_amt,auto_renewal,ln_availed,int_upto_date,sum(int_amt-int_paid) as interest_amount,ln_ac_type,max_renewal_count,max_renewal_days,renewal_int_rate from DepositMaster dm,Modules,DepositTransaction dt,CustomerMaster cm where dm.ac_type not like '1004%' and dm.ac_type=modulecode and auto_renewal != 'N' and close_ind=0 and dm.ac_type=dt.ac_type and dm.ac_no=dt.ac_no and ren_ac_type is null and concat(right(mat_date,4),'-',mid(mat_date,locate('/',mat_date)+1, (locate('/',mat_date,4)-locate('/',mat_date)-1)),'-',left(mat_date,locate('/',mat_date)-1))<='"
							+ Validations.convertYMD(getSysDate())
							+ "' and dm.cid=cm.cid group by dt.ac_no order by dm.ac_type,dm.ac_no");
			rs_mst.last();
			if (rs_mst.getRow() == 0)
				throw new RecordsNotFoundException();
			array_deposittransactionobject = new DepositTransactionObject[rs_mst
					.getRow()];
			rs_mst.beforeFirst();
			int i = 0;
			while (rs_mst.next()) {
				array_deposittransactionobject[i] = new DepositTransactionObject();
				array_deposittransactionobject[i].setTranSou(rs_mst
						.getString("moduleabbr"));
				array_deposittransactionobject[i].setAccType(rs_mst
						.getString("dm.ac_type"));
				array_deposittransactionobject[i].setAccNo(rs_mst
						.getInt("dm.ac_no"));
				array_deposittransactionobject[i].setCdind(rs_mst
						.getString("dep_date"));
				array_deposittransactionobject[i].setPaidDate(rs_mst
						.getString("mat_date"));
				array_deposittransactionobject[i].setDepositAmt(rs_mst
						.getDouble("dep_amt"));
				array_deposittransactionobject[i].setRDBalance(rs_mst
						.getDouble("interest_amount"));
				array_deposittransactionobject[i].setReferenceNo(rs_mst
						.getInt("autorenewal_no"));
				array_deposittransactionobject[i].setInterestDate(rs_mst
						.getString("auto_renewal"));
				array_deposittransactionobject[i].setTranKey(rs_mst
						.getInt("dep_days"));
				array_deposittransactionobject[i].setTranDate(rs_mst
						.getString("ln_availed"));
				array_deposittransactionobject[i].setTranMode(rs_mst
						.getString("int_upto_date"));
				array_deposittransactionobject[i].setTranNarration(rs_mst
						.getString("name"));
				array_deposittransactionobject[i].setCumInterest(0);
				array_deposittransactionobject[i].setTranSequence(rs_mst
						.getInt("max_renewal_count"));
				if (rs_mst.getString("ln_ac_type") != null
						&& rs_mst.getString("ln_availed").equals("F")) {
					int ren_days = commonLocal.getDaysFromTwoDate(rs_mst
							.getString("mat_date"), getSysDate());
					if (ren_days > rs_mst.getInt("max_renewal_days")) {
						double extra_int_amt = Math
								.round(((array_deposittransactionobject[i]
										.getDepositAmt() + array_deposittransactionobject[i]
										.getRDBalance())
										* rs_mst.getDouble("renewal_int_rate") * ren_days) / (36500));
						array_deposittransactionobject[i]
								.setCumInterest(extra_int_amt);
					}
				}
				i++;
			}
		} catch (SQLException autorenewal) {
			autorenewal.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			commonLocal = null;
		}

		return array_deposittransactionobject;
	}

	public int[] storeAutoRenewalInfo(DepositTransactionObject[] deptrnobj)
			throws RecordsNotInsertedException {
		int value = 0, lst_acc_no = 0;
		double new_int_rate = 0;
		Connection conn = null;
		int renewed_nos[] = null;
		try {

			System.out.println("Entering Into BEAN:::::StoreAutoRenewalInfo()");
			conn = getConnection();
			commonLocal = commonLocalHome.create();
			ResultSet rs_main = null, rs_gl = null;
			Statement stmt_main = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			Statement st_gl = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);

			for (int i = 0; i < deptrnobj.length; i++) {
				try {

					System.out
							.println("INSIDE TRY BLOCK:::::StoreAutoRenewalInfo()");
					System.out.println("Is it 'M'"
							+ deptrnobj[i].getInterestDate());
					System.out.println("ac_no in bean----->>>"
							+ deptrnobj[i].getAccNo());
					System.out.println("ac_type in bean----->>"
							+ deptrnobj[i].getAccType());

					rs_main = stmt_main
							.executeQuery("select dm.*,custtype,sub_category,trn_seq from DepositTransaction dt,DepositMaster dm,CustomerMaster cm where dm.ac_type=dt.ac_type and dm.ac_no=dt.ac_no and dm.cid=cm.cid and dm.ac_type = '"
									+ deptrnobj[i].getAccType()
									+ "' and dm.ac_no="
									+ deptrnobj[i].getAccNo()
									+ " order by trn_seq desc limit 1");
					rs_main.next();
					int trn_seq = rs_main.getInt("trn_seq");
					int dp_type = rs_main.getInt("custtype");
					int modulecode_type = 1;// for FD Renewal
					if (deptrnobj[i].getAccType().substring(0, 4)
							.equals("1005"))
						modulecode_type = 2;// For ReInvestment Renewal
					if (dp_type == 0)
						dp_type = 1;
					else
						dp_type = 2;
					double double_final_transfer_amt = 0;
					/**
					 * credit fd interest payable gl from extra interest rate
					 * i.e extra interest to cust iff data > maturity data + no
					 * of renewal days
					 */
					if (deptrnobj[i].getCumInterest() > 0) {
						rs_gl = st_gl
								.executeQuery("select cum_int from DepositTransaction where ac_type='"
										+ deptrnobj[i].getAccType()
										+ "' and ac_no="
										+ deptrnobj[i].getAccNo()
										+ " order by trn_seq desc limit 1");
						rs_gl.next();
						double double_extra_interest_amount = 0;
						if (modulecode_type == 2)
							double_extra_interest_amount = deptrnobj[i]
									.getCumInterest()
									+ rs_gl.getDouble("cum_int");
						PreparedStatement ps_int = conn
								.prepareStatement("insert into DepositTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
						ps_int.setString(1, deptrnobj[i].getAccType());
						ps_int.setInt(2, deptrnobj[i].getAccNo());
						ps_int.setInt(3, ++trn_seq);
						ps_int.setString(4, getSysDate());
						ps_int.setString(5, "I");
						ps_int.setDouble(6, deptrnobj[i].getDepositAmt());
						ps_int.setDouble(7, deptrnobj[i].getCumInterest());
						ps_int.setDouble(8, 0);
						ps_int.setDouble(9, 0);
						ps_int.setDouble(10, 0);
						ps_int.setString(11, null);
						ps_int.setString(12, commonLocal.getFutureDayDate(
								getSysDate(), -1));
						ps_int.setInt(13, 0);
						ps_int.setString(14, "By Extra Interest");
						ps_int.setString(15, "T");
						ps_int.setString(16, deptrnobj[i].obj_userverifier
								.getUserTml());
						ps_int.setString(17, "C");
						ps_int.setDouble(18, double_extra_interest_amount);
						ps_int.setString(19, deptrnobj[i].obj_userverifier
								.getUserTml());
						ps_int.setString(20, deptrnobj[i].obj_userverifier
								.getUserId());
						ps_int.setString(21, deptrnobj[i].obj_userverifier
								.getUserDate());
						ps_int.setString(22, deptrnobj[i].obj_userverifier
								.getUserTml());
						ps_int.setString(23, deptrnobj[i].obj_userverifier
								.getUserId());
						ps_int.setString(24, deptrnobj[i].obj_userverifier
								.getUserDate());
						ps_int.executeUpdate();

						dp_type = dp_type + 2;
						rs_gl = st_gl
								.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
										+ dp_type
										+ " and gk.ac_type = "
										+ deptrnobj[i].getAccType()
										+ " and trn_type='I' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
						rs_gl.next();
						GLTransObject trnobj = new GLTransObject();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_gl.getString("gk.gl_type"));
						trnobj.setGLCode(rs_gl.getString("gk.gl_code"));
						trnobj.setTrnMode("T");
						trnobj.setAmount(deptrnobj[i].getCumInterest()
								* rs_gl.getInt("mult_by"));
						trnobj.setCdind("C");
						trnobj.setAccType(deptrnobj[i].getAccType());
						trnobj
								.setAccNo(String.valueOf(deptrnobj[i]
										.getAccNo()));
						trnobj.setTrnType("I");
						trnobj.setRefNo(0);
						trnobj.setTrnSeq(trn_seq);
						trnobj.setVtml(deptrnobj[i].obj_userverifier
								.getUserTml());
						trnobj
								.setVid(deptrnobj[i].obj_userverifier
										.getUserId());
						// code added by sanjeet.
						trnobj.setVDate(deptrnobj[i].obj_userverifier
								.getUserDate());
						value = commonLocal.storeGLTransaction(trnobj);

						dp_type = dp_type + 2;
						rs_gl = st_gl
								.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
										+ dp_type
										+ " and gk.ac_type = "
										+ deptrnobj[i].getAccType()
										+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
						rs_gl.next();
						trnobj = new GLTransObject();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_gl.getString("gk.gl_type"));
						trnobj.setGLCode(rs_gl.getString("gk.gl_code"));
						trnobj.setTrnMode("T");
						trnobj.setAmount(deptrnobj[i].getCumInterest()
								* rs_gl.getInt("mult_by"));
						trnobj.setCdind("D");
						trnobj.setAccType(deptrnobj[i].getAccType());
						trnobj
								.setAccNo(String.valueOf(deptrnobj[i]
										.getAccNo()));
						trnobj.setTrnType("P");
						trnobj.setRefNo(0);
						trnobj.setTrnSeq(trn_seq);
						trnobj.setVtml(deptrnobj[i].obj_userverifier
								.getUserTml());
						trnobj
								.setVid(deptrnobj[i].obj_userverifier
										.getUserId());
						// code added by sanjeet.
						trnobj.setVDate(deptrnobj[i].obj_userverifier
								.getUserDate());
						value = commonLocal.storeGLTransaction(trnobj);

						dp_type = dp_type - 4;
					}
					if (deptrnobj[i].getInterestDate().equals("M")) {
						PreparedStatement ps_int = conn
								.prepareStatement("insert into DepositTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
						ps_int.setString(1, deptrnobj[i].getAccType());
						ps_int.setInt(2, deptrnobj[i].getAccNo());
						ps_int.setInt(3, ++trn_seq);
						ps_int.setString(4, getSysDate());
						ps_int.setString(5, "P");
						ps_int.setDouble(6, 0);
						ps_int.setDouble(7, 0);
						ps_int.setDouble(8, deptrnobj[i].getDepositAmt());
						ps_int.setDouble(9, deptrnobj[i].getRDBalance()
								+ deptrnobj[i].getCumInterest());
						ps_int.setDouble(10, 0);
						ps_int.setString(11, getSysDate());
						ps_int.setString(12, null);
						ps_int.setInt(13, 0);
						ps_int.setString(14, "Auto Renewed");
						ps_int.setString(15, "T");
						ps_int.setString(16, deptrnobj[i].obj_userverifier
								.getUserTml());
						ps_int.setString(17, "D");
						ps_int.setDouble(18, 0);
						ps_int.setString(19, deptrnobj[i].obj_userverifier
								.getUserTml());
						ps_int.setString(20, deptrnobj[i].obj_userverifier
								.getUserId());
						ps_int.setString(21, deptrnobj[i].obj_userverifier
								.getUserDate());
						ps_int.setString(22, deptrnobj[i].obj_userverifier
								.getUserTml());
						ps_int.setString(23, deptrnobj[i].obj_userverifier
								.getUserId());
						ps_int.setString(24, deptrnobj[i].obj_userverifier
								.getUserDate());
						ps_int.executeUpdate();

						dp_type = dp_type + 2;
						/**
						 * debit interest payable
						 */
						rs_gl = st_gl
								.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
										+ dp_type
										+ " and gk.ac_type = "
										+ deptrnobj[i].getAccType()
										+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
						rs_gl.next();
						GLTransObject trnobj = new GLTransObject();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_gl.getString("gk.gl_type"));
						trnobj.setGLCode(rs_gl.getString("gk.gl_code"));
						trnobj.setTrnMode("T");
						trnobj.setAmount(deptrnobj[i].getRDBalance()
								+ deptrnobj[i].getCumInterest()
								* rs_gl.getInt("mult_by"));
						trnobj.setCdind("D");
						trnobj.setAccType(deptrnobj[i].getAccType());
						trnobj
								.setAccNo(String.valueOf(deptrnobj[i]
										.getAccNo()));
						trnobj.setTrnType("P");
						trnobj.setRefNo(0);
						trnobj.setTrnSeq(trn_seq);
						trnobj.setVtml(deptrnobj[i].obj_userverifier
								.getUserTml());
						trnobj
								.setVid(deptrnobj[i].obj_userverifier
										.getUserId());
						// code added by sanjeet.
						trnobj.setVDate(deptrnobj[i].obj_userverifier
								.getUserDate());
						value = commonLocal.storeGLTransaction(trnobj);

						/**
						 * debit acc gl
						 */
						dp_type = dp_type - 2;
						rs_gl = st_gl
								.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
										+ dp_type
										+ " and gk.ac_type = "
										+ deptrnobj[i].getAccType()
										+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
						rs_gl.next();
						trnobj = new GLTransObject();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_gl.getString("gk.gl_type"));
						trnobj.setGLCode(rs_gl.getString("gk.gl_code"));
						trnobj.setTrnMode("T");
						trnobj.setAmount(deptrnobj[i].getDepositAmt()
								* rs_gl.getInt("mult_by"));
						trnobj.setCdind("D");
						trnobj.setAccType(deptrnobj[i].getAccType());
						trnobj
								.setAccNo(String.valueOf(deptrnobj[i]
										.getAccNo()));
						trnobj.setTrnType("P");
						trnobj.setRefNo(0);
						trnobj.setTrnSeq(trn_seq);
						trnobj.setVtml(deptrnobj[i].obj_userverifier
								.getUserTml());
						trnobj
								.setVid(deptrnobj[i].obj_userverifier
										.getUserId());
						// code added by sanjeet.
						trnobj.setVDate(deptrnobj[i].obj_userverifier
								.getUserDate());
						value = commonLocal.storeGLTransaction(trnobj);

						/**
						 * for new acc total amount is
						 */
						double_final_transfer_amt = deptrnobj[i]
								.getDepositAmt()
								+ deptrnobj[i].getRDBalance()
								+ deptrnobj[i].getCumInterest();
						st_gl
								.executeUpdate("update DepositMaster set int_paid_date='"
										+ getSysDate()
										+ "' where ac_type='"
										+ deptrnobj[i].getAccType()
										+ "' and ac_no="
										+ deptrnobj[i].getAccNo());
					} else {
						/**
						 * debit interest amt trf to T/PO/Cash
						 */
						if (deptrnobj[i].getRDBalance() > 0
								|| deptrnobj[i].getCumInterest() > 0) {
							PreparedStatement ps_int = conn
									.prepareStatement("insert into DepositTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
							ps_int.setString(1, deptrnobj[i].getAccType());
							ps_int.setInt(2, deptrnobj[i].getAccNo());
							ps_int.setInt(3, ++trn_seq);
							ps_int.setString(4, getSysDate());
							ps_int.setString(5, "P");
							ps_int.setDouble(6, deptrnobj[i].getDepositAmt());
							ps_int.setDouble(7, 0);
							ps_int.setDouble(8, 0);
							ps_int.setDouble(9, deptrnobj[i].getRDBalance()
									+ deptrnobj[i].getCumInterest());
							ps_int.setDouble(10, 0);
							ps_int.setString(11, getSysDate());
							ps_int.setString(12, null);
							ps_int.setInt(13, 0);
							ps_int.setString(14, "");
							ps_int.setString(15, rs_main.getString("int_mode"));
							ps_int.setString(16, deptrnobj[i].obj_userverifier
									.getUserTml());
							ps_int.setString(17, "D");
							ps_int.setDouble(18, 0);
							ps_int.setString(19, deptrnobj[i].obj_userverifier
									.getUserTml());
							ps_int.setString(20, deptrnobj[i].obj_userverifier
									.getUserId());
							ps_int.setString(21, deptrnobj[i].obj_userverifier
									.getUserDate());
							ps_int.setString(22, deptrnobj[i].obj_userverifier
									.getUserTml());
							ps_int.setString(23, deptrnobj[i].obj_userverifier
									.getUserId());
							ps_int.setString(24, deptrnobj[i].obj_userverifier
									.getUserDate());
							ps_int.executeUpdate();

							if (rs_main.getString("int_mode").equalsIgnoreCase(
									"P")) {
								PayOrderObject po = new PayOrderObject();
								po.setPOType("I");
								po.setPOCustType(0);
								po.setPOPayee(deptrnobj[i].getTranNarration());
								po.setPOAccType(deptrnobj[i].getAccType());
								po.setPOAccNo(deptrnobj[i].getAccNo());
								po.setPOGlCode(0);
								po.setPOAmount(deptrnobj[i].getRDBalance()
										+ deptrnobj[i].getCumInterest());
								po.setCommissionAmount(0);
								po.uv.setUserTml(deptrnobj[i].obj_userverifier
										.getUserTml());
								po.uv.setUserId(deptrnobj[i].obj_userverifier
										.getUserId());
								po.uv.setUserDate(deptrnobj[i].obj_userverifier
										.getUserDate());
								commonLocal.storePayOrder(po);
								rs_gl = st_gl
										.executeQuery("select * from GLKeyParam where ac_type=1016001 and code=1");
								rs_gl.next();
								GLTransObject trnobj = new GLTransObject();
								trnobj.setTrnDate(getSysDate());
								trnobj.setGLType(rs_gl.getString("gl_type"));
								trnobj.setGLCode(rs_gl.getString("gl_code"));
								trnobj.setTrnMode("T");
								trnobj
										.setAmount((deptrnobj[i].getRDBalance() + deptrnobj[i]
												.getCumInterest()));
								trnobj.setCdind("C");
								trnobj.setAccType(deptrnobj[i].getAccType());
								trnobj.setAccNo(String.valueOf(deptrnobj[i]
										.getAccNo()));
								trnobj.setTrnType("P");
								trnobj.setRefNo(0);
								trnobj.setTrnSeq(trn_seq);
								trnobj.setVtml(deptrnobj[i].obj_userverifier
										.getUserTml());
								trnobj.setVid(deptrnobj[i].obj_userverifier
										.getUserId());
								// code added by sanjeet.
								trnobj.setVDate(deptrnobj[i].obj_userverifier
										.getUserDate());
								value = commonLocal.storeGLTransaction(trnobj);

								/**
								 * update deposit trn
								 */
								st_gl
										.executeUpdate("update DepositTransaction set ref_no="
												+ value
												+ ",trn_narr='PO No "
												+ value
												+ "' where trn_seq="
												+ trn_seq
												+ " and ac_type='"
												+ deptrnobj[i].getAccType()
												+ "' and ac_no="
												+ deptrnobj[i].getAccNo());
							} else if (rs_main.getString("int_mode")
									.equalsIgnoreCase("T")) {
								AccountTransObject am = new AccountTransObject();
								am.setAccType(rs_main.getString("trf_ac_type"));
								am.setAccNo(rs_main.getInt("trf_ac_no"));
								am.setTransDate(getSysDate());
								am.setTransType("R");
								am.setTransAmount(deptrnobj[i].getRDBalance()
										+ deptrnobj[i].getCumInterest());
								am.setTransMode("T");
								am.setTransSource(deptrnobj[i].obj_userverifier
										.getUserTml());
								am.setCdInd("C");
								am.setChqDDNo(0);
								am.setChqDDDate("");
								am.setTransNarr(deptrnobj[i].getAccType() + " "
										+ deptrnobj[i].getAccNo());
								am.setRef_No(0);
								am
										.setPayeeName(deptrnobj[i]
												.getTranNarration());
								am.setCloseBal(deptrnobj[i].getRDBalance()
										+ deptrnobj[i].getCumInterest());
								am.setLedgerPage(0);
								am.uv.setUserTml(deptrnobj[i].obj_userverifier
										.getUserTml());
								am.uv.setUserId(deptrnobj[i].obj_userverifier
										.getUserId());
								am.uv.setUserDate(deptrnobj[i].obj_userverifier
										.getUserDate());
								am.uv.setVerTml(deptrnobj[i].obj_userverifier
										.getUserTml());
								am.uv.setVerId(deptrnobj[i].obj_userverifier
										.getUserId());
								am.uv.setVerDate(deptrnobj[i].obj_userverifier
										.getUserDate());
								value = commonLocal.storeAccountTransaction(am);
								/**
								 * update deposit trn
								 */
								value = st_gl
										.executeUpdate("update DepositTransaction set trn_narr='"
												+ am.getAccType()
												+ " "
												+ am.getAccNo()
												+ "' where trn_seq="
												+ trn_seq
												+ " and ac_type='"
												+ deptrnobj[i].getAccType()
												+ "' and ac_no="
												+ deptrnobj[i].getAccNo());
							} else if (rs_main.getString("int_mode")
									.equalsIgnoreCase("C")) {
								TrfVoucherObject trf = new TrfVoucherObject();
								trf.setVoucherType("P");
								trf.setVoucherNo(0);
								trf.setTrfAmount(deptrnobj[i].getRDBalance()
										+ deptrnobj[i].getCumInterest());
								trf.setAccType(deptrnobj[i].getAccType());
								trf.setAccNo(deptrnobj[i].getAccNo());
								trf.setTvPrtInd("F");
								trf.setTvPayInd("F");
								trf.setDDPurInd("F");
								trf.setPayMode("");
								trf.setTvPayDate("");
								trf.setPayAccNo(0);
								trf.setPayAccType("");
								trf.uv.setUserTml(deptrnobj[i].obj_userverifier
										.getUserTml());
								trf.uv.setUserId(deptrnobj[i].obj_userverifier
										.getUserId());
								trf.uv
										.setUserDate(deptrnobj[i].obj_userverifier
												.getUserDate());
								commonLocal.storeTrfVoucher(trf, getSysDate(),
										deptrnobj[i].obj_userverifier
												.getUserDate());
								// rs_gl = st_gl.executeQuery("select
								// gk.gl_code,mult_by,gk.gl_type from GLKeyParam
								// gk,GLPost gp where code = 1 and gk.ac_type =
								// 1999001 and trn_type='P' and cr_dr='C' and
								// gk.ac_type = gp.ac_type and gp.gl_code =
								// gk.gl_code");
								rs_gl = st_gl
										.executeQuery("select * from GLKeyParam where ac_type='1019001' and code=1");
								rs_gl.next();
								GLTransObject trnobj = new GLTransObject();
								trnobj.setTrnDate(getSysDate());
								trnobj.setGLType(rs_gl.getString("gl_type"));
								trnobj.setGLCode(rs_gl.getString("gl_code"));
								trnobj.setTrnMode("T");
								trnobj
										.setAmount((deptrnobj[i].getRDBalance() + deptrnobj[i]
												.getCumInterest()));
								trnobj.setCdind("C");
								trnobj.setAccType(deptrnobj[i].getAccType());
								trnobj.setAccNo(String.valueOf(deptrnobj[i]
										.getAccNo()));
								trnobj.setTrnType("P");
								trnobj.setRefNo(0);
								trnobj.setTrnSeq(trn_seq);
								trnobj.setVtml(deptrnobj[i].obj_userverifier
										.getUserTml());
								trnobj.setVid(deptrnobj[i].obj_userverifier
										.getUserId());
								// code added by sanjeet.
								trnobj.setVDate(deptrnobj[i].obj_userverifier
										.getUserDate());
								value = commonLocal.storeGLTransaction(trnobj);

								/**
								 * update deposit trn
								 */
								st_gl
										.executeUpdate("update DepositTransaction set ref_no="
												+ value
												+ ",trn_narr='Trf Vch No "
												+ value
												+ "' where trn_seq="
												+ trn_seq
												+ " and ac_type='"
												+ deptrnobj[i].getAccType()
												+ "' and ac_no="
												+ deptrnobj[i].getAccNo());
							}
							/**
							 * debit interest payable
							 */
							dp_type = dp_type + 2;
							rs_gl = st_gl
									.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
											+ dp_type
											+ " and gk.ac_type = "
											+ deptrnobj[i].getAccType()
											+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
							rs_gl.next();
							GLTransObject trnobj = new GLTransObject();
							trnobj.setTrnDate(getSysDate());
							trnobj.setGLType(rs_gl.getString("gk.gl_type"));
							trnobj.setGLCode(rs_gl.getString("gk.gl_code"));
							trnobj.setTrnMode("T");
							trnobj
									.setAmount((deptrnobj[i].getRDBalance() + deptrnobj[i]
											.getCumInterest())
											* rs_gl.getInt("mult_by"));
							trnobj.setCdind("D");
							trnobj.setAccType(deptrnobj[i].getAccType());
							trnobj.setAccNo(String.valueOf(deptrnobj[i]
									.getAccNo()));
							trnobj.setTrnType("P");
							trnobj.setRefNo(0);
							trnobj.setTrnSeq(trn_seq);
							trnobj.setVtml(deptrnobj[i].obj_userverifier
									.getUserTml());
							trnobj.setVid(deptrnobj[i].obj_userverifier
									.getUserId());
							// code added by sanjeet.
							trnobj.setVDate(deptrnobj[i].obj_userverifier
									.getUserDate());
							commonLocal.storeGLTransaction(trnobj);
							dp_type = dp_type - 2;
						}

						PreparedStatement ps_int = conn
								.prepareStatement("insert into DepositTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
						ps_int.setString(1, deptrnobj[i].getAccType());
						ps_int.setInt(2, deptrnobj[i].getAccNo());
						ps_int.setInt(3, ++trn_seq);
						ps_int.setString(4, getSysDate());
						ps_int.setString(5, "P");
						ps_int.setDouble(6, 0);
						ps_int.setDouble(7, 0);
						ps_int.setDouble(8, deptrnobj[i].getDepositAmt());
						ps_int.setDouble(9, 0);
						ps_int.setDouble(10, 0);
						ps_int.setString(11, getSysDate());
						ps_int.setString(12, null);
						ps_int.setInt(13, 0);
						ps_int.setString(14, "Auto Renewed");
						ps_int.setString(15, "T");
						ps_int.setString(16, deptrnobj[i].obj_userverifier
								.getUserTml());
						ps_int.setString(17, "D");
						ps_int.setDouble(18, 0);
						ps_int.setString(19, deptrnobj[i].obj_userverifier
								.getUserTml());
						ps_int.setString(20, deptrnobj[i].obj_userverifier
								.getUserId());
						ps_int.setString(21, deptrnobj[i].obj_userverifier
								.getUserDate());
						ps_int.setString(22, deptrnobj[i].obj_userverifier
								.getUserTml());
						ps_int.setString(23, deptrnobj[i].obj_userverifier
								.getUserId());
						ps_int.setString(24, deptrnobj[i].obj_userverifier
								.getUserDate());
						ps_int.executeUpdate();
						/**
						 * debit acc gl
						 */
						rs_gl = st_gl
								.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
										+ dp_type
										+ " and gk.ac_type = "
										+ deptrnobj[i].getAccType()
										+ " and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
						GLTransObject trnobj = new GLTransObject();
						rs_gl.next();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_gl.getString("gk.gl_type"));
						trnobj.setGLCode(rs_gl.getString("gk.gl_code"));
						trnobj.setTrnMode("T");
						trnobj.setAmount(deptrnobj[i].getDepositAmt()
								* rs_gl.getInt("mult_by"));
						trnobj.setCdind("D");
						trnobj.setAccType(deptrnobj[i].getAccType());
						trnobj
								.setAccNo(String.valueOf(deptrnobj[i]
										.getAccNo()));
						trnobj.setTrnType("P");
						trnobj.setRefNo(0);
						trnobj.setTrnSeq(trn_seq);
						trnobj.setVtml(deptrnobj[i].obj_userverifier
								.getUserTml());
						trnobj
								.setVid(deptrnobj[i].obj_userverifier
										.getUserId());
						// code added by sanjeet.
						trnobj.setVDate(deptrnobj[i].obj_userverifier
								.getUserDate());
						commonLocal.storeGLTransaction(trnobj);

						/**
						 * for new acc total amount is
						 */
						double_final_transfer_amt = deptrnobj[i]
								.getDepositAmt();
						st_gl
								.executeUpdate("update DepositMaster set int_paid_date='"
										+ getSysDate()
										+ "' where ac_type='"
										+ deptrnobj[i].getAccType()
										+ "' and ac_no="
										+ deptrnobj[i].getAccNo());
					}
					/**
					 * create new account insert into dep master and trn
					 */

					PreparedStatement ps = conn
							.prepareStatement("insert into DepositMaster values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					lst_acc_no = commonLocal.getModulesColumn("lst_acc_no",
							deptrnobj[i].getAccType()) + 1;
					System.out.println("lst_ac_no" + lst_acc_no);
					renewed_nos = new int[deptrnobj.length];
					renewed_nos[i] = lst_acc_no;
					ps.setString(1, rs_main.getString("ac_type"));
					ps.setInt(2, lst_acc_no);
					ps.setInt(3, rs_main.getInt("cid"));
					ps.setInt(4, rs_main.getInt("add_type"));
					ps.setInt(5, rs_main.getInt("autorenewal_no") + 1);
					ps.setInt(6, rs_main.getInt("no_jt_hldr"));
					String dep_date = "";
					if (deptrnobj[i].getCumInterest() == 0)
						dep_date = commonLocal.getFutureDayDate(rs_main
								.getString("mat_date"), 1);
					else
						dep_date = getSysDate();
					String mat_date = commonLocal.getFutureDayDate(dep_date,
							rs_main.getInt("dep_days"));
					double array_int_rate[] = getDepositInterestRate(rs_main
							.getString("ac_type"), rs_main.getInt("custtype"),
							rs_main.getInt("sub_category"), Validations
									.convertYMD(dep_date), rs_main
									.getInt("dep_days"),
							double_final_transfer_amt);
					new_int_rate = array_int_rate[0];

					if (rs_main.getInt("extra_rate_type") == 2)
						new_int_rate += array_int_rate[1];
					else if (rs_main.getInt("extra_rate_type") == 3)
						new_int_rate += array_int_rate[2];
					else if (rs_main.getInt("extra_rate_type") == 4)
						new_int_rate += array_int_rate[1] + array_int_rate[2];

					System.out.println("int_rate=>" + new_int_rate);
					ps.setString(7, dep_date);
					ps.setString(8, mat_date);
					ps.setInt(9, rs_main.getInt("dep_yrs"));
					ps.setInt(10, rs_main.getInt("dep_mths"));
					ps.setInt(11, rs_main.getInt("dep_days"));
					ps.setDouble(12, double_final_transfer_amt);
					if (modulecode_type == 1)
						ps.setDouble(13, double_final_transfer_amt);
					else
						ps.setDouble(13, reinvestmentCalc(
								double_final_transfer_amt, getSysDate(),
								mat_date, new_int_rate));
					ps.setString(14, Validations.checkDate(Validations
							.nextPayDate(rs_main.getString("int_freq"),
									dep_date, mat_date, commonLocal)));
					ps.setString(15, null);
					ps.setString(16, null);
					ps.setString(17, rs_main.getString("intr_ac_type"));
					ps.setInt(18, rs_main.getInt("intr_ac_no"));
					ps.setInt(19, rs_main.getInt("nom_no"));
					ps.setDouble(20, new_int_rate);
					ps.setInt(21, rs_main.getInt("extra_rate_type"));
					ps.setString(22, "T");
					ps.setString(23, deptrnobj[i].getAccType());
					ps.setInt(24, deptrnobj[i].getAccNo());
					ps.setString(25, rs_main.getString("int_freq"));
					ps.setString(26, rs_main.getString("int_mode"));
					ps.setString(27, rs_main.getString("trf_ac_type"));
					ps.setInt(28, rs_main.getInt("trf_ac_no"));
					ps
							.setString(29, commonLocal.getFutureDayDate(
									dep_date, -1));
					ps.setString(30, getSysDate());
					ps.setInt(31, 1);
					ps.setString(32, "F");
					ps.setString(33, null);
					ps.setString(34, null);
					ps.setString(35, "F");
					ps.setString(36, "F");
					ps.setInt(37, rs_main.getInt("rct_no"));
					ps.setString(38, "F");
					ps.setString(39, "F");
					ps.setString(40, rs_main.getString("auto_renewal"));
					ps.setDouble(41, 0);
					ps.setInt(42, 0);
					ps.setString(43, null);
					ps.setString(44, null);
					ps.setInt(45, 0);
					ps.setString(46, "F");
					ps.setString(47, null);
					ps.setString(48, null);
					ps
							.setString(49, deptrnobj[i].obj_userverifier
									.getUserTml());
					ps.setString(50, deptrnobj[i].obj_userverifier.getUserId());
					ps.setString(51, deptrnobj[i].obj_userverifier
							.getUserDate());
					ps
							.setString(52, deptrnobj[i].obj_userverifier
									.getUserTml());
					ps.setString(53, deptrnobj[i].obj_userverifier.getUserId());
					ps.setString(54, deptrnobj[i].obj_userverifier
							.getUserDate());
					ps.executeUpdate();
					PreparedStatement ps_int = conn
							.prepareStatement("insert into DepositTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					ps_int.setString(1, deptrnobj[i].getAccType());
					ps_int.setInt(2, lst_acc_no);
					ps_int.setInt(3, 1);
					ps_int.setString(4, getSysDate());
					ps_int.setString(5, "D");
					ps_int.setDouble(6, double_final_transfer_amt);
					ps_int.setDouble(7, 0);
					ps_int.setDouble(8, 0);
					ps_int.setDouble(9, 0);
					ps_int.setDouble(10, 0);
					ps_int.setString(11, null);
					ps_int.setString(12, null);
					ps_int.setInt(13, 0);
					ps_int.setString(14, "Auto Renewed ");
					ps_int.setString(15, "T");
					ps_int.setString(16, deptrnobj[i].obj_userverifier
							.getUserTml());
					ps_int.setString(17, "C");
					ps_int.setDouble(18, 0);
					ps_int.setString(19, deptrnobj[i].obj_userverifier
							.getUserTml());
					ps_int.setString(20, deptrnobj[i].obj_userverifier
							.getUserId());
					ps_int.setString(21, deptrnobj[i].obj_userverifier
							.getUserDate());
					ps_int.setString(22, deptrnobj[i].obj_userverifier
							.getUserTml());
					ps_int.setString(23, deptrnobj[i].obj_userverifier
							.getUserId());
					ps_int.setString(24, deptrnobj[i].obj_userverifier
							.getUserDate());
					ps_int.executeUpdate();
					/**
					 * credit to new Gl
					 */
					rs_gl = st_gl
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ dp_type
									+ " and gk.ac_type = "
									+ deptrnobj[i].getAccType()
									+ " and trn_type='D' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					GLTransObject trnobj = new GLTransObject();
					rs_gl.next();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_gl.getString("gk.gl_type"));
					trnobj.setGLCode(rs_gl.getString("gk.gl_code"));
					trnobj.setTrnMode("T");
					trnobj.setAmount(double_final_transfer_amt
							* rs_gl.getInt("mult_by"));
					trnobj.setCdind("C");
					trnobj.setAccType(deptrnobj[i].getAccType());
					trnobj.setAccNo(String.valueOf(lst_acc_no));
					trnobj.setTrnType("D");
					trnobj.setRefNo(0);
					trnobj.setTrnSeq(1);
					trnobj.setVtml(deptrnobj[i].obj_userverifier.getUserTml());
					trnobj.setVid(deptrnobj[i].obj_userverifier.getUserId());
					// code added by sanjeet.
					trnobj
							.setVDate(deptrnobj[i].obj_userverifier
									.getUserDate());
					commonLocal.storeGLTransaction(trnobj);

					/**
					 * For Joint holders iff greater than 0
					 */
					if (rs_main.getInt("no_jt_hldr") > 0) {
						st_gl
								.executeUpdate("insert into JointHolders select ac_type,null,cid,addr_type from JointHolders where ac_type='"
										+ deptrnobj[i].getAccType()
										+ "' and ac_no="
										+ deptrnobj[i].getAccNo());
						st_gl.executeUpdate("update JointHolders set ac_no="
								+ lst_acc_no + " where ac_type='"
								+ deptrnobj[i].getAccType()
								+ "' and ac_no is null");
					}
					st_gl
							.executeUpdate("insert into SignatureInstruction select ac_type,null,cid,name,desg,minlmt,maxlmt,typeofopr from SignatureInstruction where ac_type='"
									+ deptrnobj[i].getAccType()
									+ "' and ac_no=" + deptrnobj[i].getAccNo());
					st_gl
							.executeUpdate("update SignatureInstruction set ac_no="
									+ lst_acc_no
									+ " where ac_type='"
									+ deptrnobj[i].getAccType()
									+ "' and ac_no is null");
					/**
					 * for nomineee
					 */
					if (rs_main.getInt("nom_no") > 0) {
						/*
						 * st_gl.executeUpdate(
						 * "insert into NomineeMaster select reg_no,cid,ac_type,null,sex,name,dob,address,relation,percentage,guard_type,guard_name,guard_address,guard_sex,guard_rel,court_order_no,court_order_date,fr_date,to_date from NomineeMaster where ac_type='"
						 * + deptrnobj[i].getAccType() + "' and ac_no=" +
						 * deptrnobj[i].getAccNo());
						 */
						st_gl.executeUpdate("update NomineeMaster set ac_no="
								+ lst_acc_no + " where ac_type='"
								+ deptrnobj[i].getAccType() + "' and ac_no="
								+ deptrnobj[i].getAccNo());
					}
					/**
					 * update old deposit master
					 */
					st_gl
							.executeUpdate("update DepositMaster set dep_renewed='T',close_ind=1,close_date='"
									+ getSysDate()
									+ "',ren_ac_type='"
									+ deptrnobj[i].getAccType()
									+ "',ren_ac_no="
									+ lst_acc_no
									+ " where ac_type='"
									+ deptrnobj[i].getAccType()
									+ "' and ac_no=" + deptrnobj[i].getAccNo());

					PreparedStatement pss = conn.prepareStatement("commit");
					pss.execute();

				} catch (SQLException trn) {
					PreparedStatement pss = conn.prepareStatement("rollback");
					pss.execute();
					sessionContext.setRollbackOnly();
				} catch (DateFormatException e) {
					e.printStackTrace();
					PreparedStatement pss = conn.prepareStatement("rollback");
					pss.execute();
					sessionContext.setRollbackOnly();
				} catch (RemoteException e) {
					e.printStackTrace();
					PreparedStatement pss = conn.prepareStatement("rollback");
					pss.execute();
					sessionContext.setRollbackOnly();
				} catch (NamingException e) {
					e.printStackTrace();
					PreparedStatement pss = conn.prepareStatement("rollback");
					pss.execute();
					sessionContext.setRollbackOnly();
				} catch (Exception v) {
					v.printStackTrace();
					PreparedStatement pss = conn.prepareStatement("rollback");
					pss.execute();
					sessionContext.setRollbackOnly();
				}

			}
		} catch (SQLException store) {
			store.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordsNotInsertedException();
		} catch (CreateException e) {
			e.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordsNotInsertedException();
		} catch (Exception v) {
			v.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordNotInsertedException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			commonLocal = null;
		}
		return renewed_nos;
	}

	public DepositIntRate[] getViewDetailes(String deptype, String table_name)
			throws RecordsNotFoundException {
		DepositIntRate depint[] = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ResultSet rs_int = null;
			Statement stmt_int = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			if (table_name.equals("DepositIntRate"))
				rs_int = stmt_int
						.executeQuery("select date_fr,date_to,days_fr,days_to,int_rate from DepositInterestRate where ac_type = '"
								+ deptype
								+ "' order by concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1)) , concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1)) ,days_fr ");
			else if (table_name.equals("DepositQuantumRate"))
				rs_int = stmt_int
						.executeQuery("select date_fr,date_to,days_fr,days_to,extra_int_rate,category,min_amt,max_amt from DepositQuantumRate where ac_type = '"
								+ deptype
								+ "' order by concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1)) , concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1)),days_fr,category,min_amt");
			else if (table_name.equals("DepositCategoryRate"))
				rs_int = stmt_int
						.executeQuery("select date_fr,date_to,days_fr,days_to,extra_int_rate,category ,extra_lnint_rate from DepositCategoryRate where ac_type = '"
								+ deptype
								+ "' order by concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1)) , concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1)),days_fr,category");
			else if (table_name.equals("PeriodLimit"))
				rs_int = stmt_int
						.executeQuery("select mod_ty,srl_no,lmt_hdg,fr_lmt,to_lmt,de_tml,de_user,de_date,de_time from PeriodLimit where mod_ty = '"
								+ deptype + "' order by srl_no");
			else if (table_name.equals("QuantumLimit"))
				rs_int = stmt_int
						.executeQuery("select mod_ty,srl_no,lmt_hdg,fr_lmt,to_lmt,de_tml,de_user,de_date,de_time from QuantumLimit where mod_ty = '"
								+ deptype + "' order by srl_no");
			else if (table_name.equals("Products"))
				rs_int = stmt_int
						.executeQuery("select dpdl_date,prod_date,rinve_prod_date from Products ");
			else if (table_name.equals("QtrDefinition"))
				rs_int = stmt_int
						.executeQuery("select month,qtr_defn,hyr_defn,yr_defn,de_tml,de_user,de_date from QtrDefinition order by month");
			rs_int.last();

			if (rs_int.getRow() == 0)
				throw new RecordsNotFoundException();
			depint = new DepositIntRate[rs_int.getRow()];
			rs_int.beforeFirst();
			int i = 0;
			while (rs_int.next()) {
				if (table_name.equals("PeriodLimit")) {
					depint[i] = new DepositIntRate();
					depint[i].setMod_ty(rs_int.getString("mod_ty"));
					depint[i].setSrl_no(rs_int.getInt("srl_no"));
					depint[i].setLmt_hdg(rs_int.getString("lmt_hdg"));
					depint[i].setFr_lmt(rs_int.getInt("fr_lmt"));
					depint[i].setTo_lmt(rs_int.getInt("to_lmt"));
					depint[i].setDe_tml(rs_int.getString("de_tml"));
					depint[i].setDe_user(rs_int.getString("de_user"));
					depint[i].setDe_date(rs_int.getString("de_date"));
					depint[i].setDe_time(rs_int.getString("de_time"));
					i++;
				} else if (table_name.equals("QuantumLimit")) {
					depint[i] = new DepositIntRate();
					depint[i].setMod_ty(rs_int.getString("mod_ty"));
					depint[i].setSrl_no(rs_int.getInt("srl_no"));
					depint[i].setLmt_hdg(rs_int.getString("lmt_hdg"));
					depint[i].setFr_lmt(rs_int.getInt("fr_lmt"));
					depint[i].setTo_lmt(rs_int.getInt("to_lmt"));
					depint[i].setDe_tml(rs_int.getString("de_tml"));
					depint[i].setDe_user(rs_int.getString("de_user"));
					depint[i].setDe_date(rs_int.getString("de_date"));
					depint[i].setDe_time(rs_int.getString("de_time"));
					i++;
				} else if (table_name.equals("Products")) {
					depint[i] = new DepositIntRate();
					depint[i].setDpdl_date(rs_int.getString("dpdl_date"));
					depint[i].setProd_date(rs_int.getString("prod_date"));
					depint[i].setRinve_prod_date(rs_int
							.getString("rinve_prod_date"));
					i++;
				} else if (table_name.equals("QtrDefinition")) {
					depint[i] = new DepositIntRate();
					depint[i].setMonth(rs_int.getInt("month"));
					depint[i].setQtr_defn(rs_int.getString("qtr_defn"));
					depint[i].setHyr_defn(rs_int.getString("hyr_defn"));
					depint[i].setYr_defn(rs_int.getString("yr_defn"));
					depint[i].setDe_tml(rs_int.getString("de_tml"));
					depint[i].setDe_user(rs_int.getString("de_user"));
					depint[i].setDe_date(rs_int.getString("de_date"));
					i++;
				} else {
					depint[i] = new DepositIntRate();
					depint[i].setDateFrom(rs_int.getString("date_fr"));
					depint[i].setDateTo(rs_int.getString("date_to"));
					depint[i].setDaysFrom(rs_int.getInt("days_fr"));
					depint[i].setDaysTo(rs_int.getInt("days_to"));
					if (table_name.equals("DepositQuantumRate")) {
						depint[i]
								.setIntRate(rs_int.getDouble("extra_int_rate"));
						depint[i].setCategory(rs_int.getInt("category"));
						depint[i].setMinAmt(rs_int.getDouble("min_amt"));
						depint[i].setMaxAmt(rs_int.getDouble("max_amt"));

					} else if (table_name.equals("DepositCategoryRate")) {
						depint[i]
								.setIntRate(rs_int.getDouble("extra_int_rate"));
						depint[i].setCategory(rs_int.getInt("category"));
						depint[i].setMinAmt(rs_int
								.getDouble("extra_lnint_rate"));
					} else {
						depint[i].setIntRate(rs_int.getDouble("int_rate"));
					}
					i++;
				}
			}
		} catch (SQLException view) {
			view.printStackTrace();
			throw new RecordsNotFoundException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return depint;
	}

	public int insertRow(DepositIntRate depositintrate, String table_name)
			throws CreateException, RecordNotInsertedException,
			DateFormatException {
		int value = 1, no_of_days = -1;
		String date_to = null;
		Connection conn = null;
		try {
			System.out.println("execution started..");
			System.out.println(" the table name" + table_name);
			conn = getConnection();
			commonLocal = commonLocalHome.create();
			ResultSet rs_int = null;
			Statement stmt_int = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			PreparedStatement ps = null, ps1 = null;
			if (table_name.equals("PeriodLimit")) {
				if (depositintrate.getFr_lmt() == 0) {
					stmt_int
							.executeUpdate("insert into PeriodLimitLog select * from PeriodLimit where mod_ty="
									+ depositintrate.getMod_ty() + "");
					Statement delete = conn.createStatement();
					delete
							.executeUpdate("delete from PeriodLimit where mod_ty="
									+ depositintrate.getMod_ty() + "");
				}
				rs_int = stmt_int.executeQuery("Select * from PeriodLimit");
				ps = conn
						.prepareStatement("insert into PeriodLimit values(?,?,?,?,?,?,?,?,?,null)");
				System.out.println("99999");
				ps.setString(1, depositintrate.getMod_ty());
				ps.setInt(2, depositintrate.getSrl_no());
				ps.setString(3, depositintrate.getLmt_hdg());
				ps.setInt(4, depositintrate.getFr_lmt());
				ps.setInt(5, depositintrate.getTo_lmt());
				ps.setString(6, depositintrate.getDe_tml());
				ps.setString(7, depositintrate.getDe_user());
				ps.setString(8, depositintrate.getDe_date());
				ps.setString(9, depositintrate.getDe_time());
				value = ps.executeUpdate();
			} else if (table_name.equals("QuantumLimit")) {
				rs_int = stmt_int.executeQuery("Select * from QuantumLimit");
				ps = conn
						.prepareStatement("insert into QuantumLimit values(?,?,?,?,?,?,?,?,?,null)");
				System.out.println("99999");
				ps.setString(1, depositintrate.getMod_ty());
				ps.setInt(2, depositintrate.getSrl_no());
				ps.setString(3, depositintrate.getLmt_hdg());
				ps.setInt(4, depositintrate.getFr_lmt());
				ps.setInt(5, depositintrate.getTo_lmt());
				ps.setString(6, depositintrate.getDe_tml());
				ps.setString(7, depositintrate.getDe_user());
				ps.setString(8, depositintrate.getDe_date());
				ps.setString(9, depositintrate.getDe_time());
				value = ps.executeUpdate();
			} else if (table_name.equals("Products")) {
				rs_int = stmt_int.executeQuery("select * from Products");
				ps = conn
						.prepareStatement("insert into Products values(?,?,?)");
				System.out.println("33333");
				ps.setString(1, depositintrate.getDpdl_date());
				ps.setString(2, depositintrate.getProd_date());
				ps.setString(3, depositintrate.getRinve_prod_date());
				value = ps.executeUpdate();
			} else if (table_name.equals("QtrDefinition")) {
				rs_int = stmt_int.executeQuery("select * from QtrDefinition");
				ps = conn
						.prepareStatement("insert into QtrDefinition values(?,?,?,?,?,?,?)");
				ps.setInt(1, depositintrate.getMonth());
				ps.setString(2, depositintrate.getQtr_defn());
				ps.setString(3, depositintrate.getHyr_defn());
				ps.setString(4, depositintrate.getYr_defn());
				ps.setString(5, depositintrate.getDe_tml());
				ps.setString(6, depositintrate.getDe_user());
				ps.setString(7, depositintrate.getDe_date());
				value = ps.executeUpdate();
			} else {

				depositintrate.setDateTo("31/12/9999");
				System.out.println("  **** //// ++++ date is"
						+ depositintrate.getDateFrom());
				date_to = Validations.addDays(depositintrate.getDateFrom(), -1);
				if (table_name.equals("DepositIntRate"))
					rs_int = stmt_int
							.executeQuery("select * from DepositInterestRate where ac_type ='"
									+ depositintrate.getDPAccType()
									+ "' and date_fr='"
									+ depositintrate.getDateFrom()
									+ "' and days_fr <="
									+ depositintrate.getDaysTo()
									+ " and days_to >="
									+ depositintrate.getDaysFrom());
				else if (table_name.equals("DepositQuantumRate"))
					rs_int = stmt_int
							.executeQuery("select * from DepositQuantumRate where ac_type ='"
									+ depositintrate.getDPAccType()
									+ "' and date_fr='"
									+ depositintrate.getDateFrom()
									+ "' and category="
									+ depositintrate.getCategory()
									+ " and days_fr <="
									+ depositintrate.getDaysTo()
									+ " and days_to >="
									+ depositintrate.getDaysFrom()
									+ " and min_amt <="
									+ depositintrate.getMaxAmt()
									+ " and max_amt >="
									+ depositintrate.getMinAmt());
				else if (table_name.equals("DepositCategoryRate"))
					rs_int = stmt_int
							.executeQuery("select * from DepositCategoryRate where ac_type ='"
									+ depositintrate.getDPAccType()
									+ "' and date_fr='"
									+ depositintrate.getDateFrom()
									+ "' and category="
									+ depositintrate.getCategory()
									+ " and days_fr <="
									+ depositintrate.getDaysTo()
									+ " and days_to >="
									+ depositintrate.getDaysFrom());

				rs_int.last();
				System.out.println("query executed..");

				if (rs_int.getRow() != 0)
					value = 0;
				else {
					System.out.println("111111");
					rs_int.first();
					if (table_name.equals("DepositIntRate"))
						rs_int = stmt_int
								.executeQuery("select date_fr from DepositInterestRate where days_fr <= "
										+ depositintrate.getDaysTo()
										+ " and days_to >= "
										+ depositintrate.getDaysFrom()
										+ " and date_to='"
										+ depositintrate.getDateTo()
										+ "' and ac_type ='"
										+ depositintrate.getDPAccType() + "'");
					else if (table_name.equals("DepositQuantumRate"))
						rs_int = stmt_int
								.executeQuery("select date_fr from DepositQuantumRate where ac_type ='"
										+ depositintrate.getDPAccType()
										+ "' and date_to='"
										+ depositintrate.getDateTo()
										+ "' and category="
										+ depositintrate.getCategory()
										+ " and days_fr <= "
										+ depositintrate.getDaysTo()
										+ " and days_to >= "
										+ depositintrate.getDaysFrom()
										+ " and min_amt <="
										+ depositintrate.getMaxAmt()
										+ " and max_amt >="
										+ depositintrate.getMinAmt());
					else if (table_name.equals("DepositCategoryRate"))
						rs_int = stmt_int
								.executeQuery("select date_fr from DepositCategoryRate where ac_type ='"
										+ depositintrate.getDPAccType()
										+ "' and date_to='"
										+ depositintrate.getDateTo()
										+ "' and category="
										+ depositintrate.getCategory()
										+ " and days_fr <="
										+ depositintrate.getDaysTo()
										+ " and days_to >="
										+ depositintrate.getDaysFrom());

					rs_int.last();
					System.out.println("22222");
					if (rs_int.getRow() > 0) {

						rs_int.first();
						String from_date = rs_int.getString("date_fr");
						no_of_days = Validations.dayCompare(from_date,
								depositintrate.getDateFrom());
						// value=0;
					}
					if (no_of_days >= 0) {
						if (table_name.equals("DepositIntRate"))
							value = stmt_int
									.executeUpdate("update DepositInterestRate set date_to ='"
											+ date_to
											+ "' where days_fr <= "
											+ depositintrate.getDaysTo()
											+ " and days_to >= "
											+ depositintrate.getDaysFrom()
											+ " and date_to='"
											+ depositintrate.getDateTo()
											+ "' and ac_type ='"
											+ depositintrate.getDPAccType()
											+ "'");
						else if (table_name.equals("DepositQuantumRate"))
							value = stmt_int
									.executeUpdate("update DepositQuantumRate set date_to ='"
											+ date_to
											+ "' where ac_type ='"
											+ depositintrate.getDPAccType()
											+ "' and date_to='"
											+ depositintrate.getDateTo()
											+ "' and category="
											+ depositintrate.getCategory()
											+ " and days_fr <= "
											+ depositintrate.getDaysTo()
											+ " and days_to >= "
											+ depositintrate.getDaysFrom()
											+ " and min_amt <="
											+ depositintrate.getMaxAmt()
											+ " and max_amt >="
											+ depositintrate.getMinAmt());
						else if (table_name.equals("DepositCategoryRate"))
							value = stmt_int
									.executeUpdate("update DepositCategoryRate set date_to ='"
											+ date_to
											+ "' where ac_type ='"
											+ depositintrate.getDPAccType()
											+ "' and date_to='"
											+ depositintrate.getDateTo()
											+ "' and category="
											+ depositintrate.getCategory()
											+ " and days_fr <="
											+ depositintrate.getDaysTo()
											+ " and days_to >="
											+ depositintrate.getDaysFrom());
					}
				}

				if (value > 0) {
					System.out.println("@@@@@@@");
					value = 0;
					if (table_name.equals("DepositIntRate"))
						ps = conn
								.prepareStatement("insert into DepositInterestRate values(?,?,?,?,?,?,?,?,?)");
					else if (table_name.equals("DepositQuantumRate"))
						ps = conn
								.prepareStatement("insert into DepositQuantumRate values(?,?,?,?,?,?,?,?,?,?,?,?)");
					else if (table_name.equals("DepositCategoryRate"))
						ps = conn
								.prepareStatement("insert into DepositCategoryRate values(?,?,?,?,?,?,?,?,?,?,?)");

					System.out.println("!!!!!");
					if (table_name.equals("DepositIntRate")) {
						ps.setString(1, depositintrate.getDPAccType());
						ps.setString(2, depositintrate.getDateFrom());
						ps.setString(3, depositintrate.getDateTo());
						ps.setInt(4, depositintrate.getDaysFrom());
						ps.setInt(5, depositintrate.getDaysTo());
						ps.setDouble(6, depositintrate.getIntRate());
						ps.setString(7, depositintrate.obj_userverifier
								.getUserId());
						ps.setString(8, depositintrate.obj_userverifier
								.getUserTml());
						ps.setString(9, depositintrate.obj_userverifier
								.getUserDate());
						value = ps.executeUpdate();
					} else if (table_name.equals("DepositQuantumRate")) {
						System.out.println("8888");
						ps.setString(1, depositintrate.getDPAccType());
						ps.setInt(2, depositintrate.getCategory());
						ps.setString(3, depositintrate.getDateFrom());
						ps.setString(4, depositintrate.getDateTo());
						ps.setInt(5, depositintrate.getDaysFrom());
						ps.setInt(6, depositintrate.getDaysTo());
						ps.setDouble(7, depositintrate.getMinAmt());
						ps.setDouble(8, depositintrate.getMaxAmt());
						ps.setDouble(9, depositintrate.getIntRate());
						ps.setString(10, depositintrate.obj_userverifier
								.getUserId());
						ps.setString(11, depositintrate.obj_userverifier
								.getUserTml());
						ps.setString(12, depositintrate.obj_userverifier
								.getUserDate());
						value = ps.executeUpdate();
					} else if (table_name.equals("DepositCategoryRate")) {
						ps.setString(1, depositintrate.getDPAccType());
						ps.setInt(2, depositintrate.getCategory());
						ps.setString(3, depositintrate.getDateFrom());
						ps.setString(4, depositintrate.getDateTo());
						ps.setInt(5, depositintrate.getDaysFrom());
						ps.setInt(6, depositintrate.getDaysTo());
						ps.setDouble(7, depositintrate.getIntRate());
						ps.setDouble(8, depositintrate.getMinAmt());
						ps.setString(9, depositintrate.obj_userverifier
								.getUserId());
						ps.setString(10, depositintrate.obj_userverifier
								.getUserTml());
						ps.setString(11, depositintrate.obj_userverifier
								.getUserDate());
						value = ps.executeUpdate();
					}
				}
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordNotInsertedException();
		} catch (Exception e) {
			e.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordNotInsertedException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			commonLocal = null;
		}
		System.out.println("value is" + value);
		return value;
	}

	public int deleteRow(DepositIntRate depositintrate, String table_name)
			throws CreateException, DateFormatException {
		int days_from = 0, days_to = 0, minday, maxday, category = 0;
		double amount_from = 0.0, amount_to = 0.0, minamt = 0.0, maxamt = 0.0;
		boolean boolean_flag = true;
		Connection conn = null;
		try {
			conn = getConnection();
			commonLocal = commonLocalHome.create();
			ResultSet rs_int = null;
			Statement stmt_int = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);

			if (table_name.equals("PeriodLimit")) {
				boolean_flag = false;
				rs_int = stmt_int
						.executeQuery("select * from PeriodLimit where mod_ty='"
								+ depositintrate.getMod_ty() + "'");
				if (rs_int.next())
					boolean_flag = true;
				while (boolean_flag) {
					stmt_int
							.executeUpdate("delete from PeriodLimit where mod_ty='"
									+ depositintrate.getMod_ty()
									+ "' and lmt_hdg='"
									+ depositintrate.getLmt_hdg()
									+ "' and fr_lmt='"
									+ depositintrate.getFr_lmt()
									+ "' and to_lmt='"
									+ depositintrate.getTo_lmt() + "'");
					boolean_flag = false;
				}
			} else if (table_name.equals("QuantumLimit")) {
				boolean_flag = false;
				rs_int = stmt_int
						.executeQuery("select * from QuantumLimit where mod_ty='"
								+ depositintrate.getMod_ty() + "'");
				if (rs_int.next())
					boolean_flag = true;
				while (boolean_flag) {
					stmt_int
							.executeUpdate("delete from QuantumLimit where mod_ty='"
									+ depositintrate.getMod_ty()
									+ "' and lmt_hdg='"
									+ depositintrate.getLmt_hdg()
									+ "' and fr_lmt='"
									+ depositintrate.getFr_lmt()
									+ "' and to_lmt='"
									+ depositintrate.getTo_lmt() + "'");
					boolean_flag = false;
				}
			} else if (table_name.equals("Products")) {

				boolean_flag = false;
				rs_int = stmt_int.executeQuery("select * from Products");
				if (rs_int.next())
					boolean_flag = true;
				while (boolean_flag) {
					System.out.println("date 1 "
							+ depositintrate.getDpdl_date());
					System.out.println("date 2 "
							+ depositintrate.getProd_date());
					System.out.println("date 3 "
							+ depositintrate.getRinve_prod_date());
					stmt_int
							.executeUpdate("delete from Products where dpdl_date='"
									+ depositintrate.getDpdl_date()
									+ "' and prod_date='"
									+ depositintrate.getProd_date()
									+ "' and rinve_prod_date='"
									+ depositintrate.getRinve_prod_date() + "'");
					boolean_flag = false;
				}
			} else if (table_name.equals("QtrDefinition")) {

				boolean_flag = false;
				rs_int = stmt_int.executeQuery("select * from QtrDefinition");
				if (rs_int.next())
					boolean_flag = true;
				while (boolean_flag) {
					stmt_int
							.executeUpdate("delete from QtrDefinition where month="
									+ depositintrate.getMonth() + "");
					boolean_flag = false;
				}
			} else {

				if (depositintrate.getDateTo().equals("Future Date")) {
					String date_to = Validations.addDays(depositintrate
							.getDateFrom(), -1);
					days_from = depositintrate.getDaysFrom();
					days_to = depositintrate.getDaysTo();
					amount_from = depositintrate.getMinAmt();
					amount_to = depositintrate.getMaxAmt();
					category = depositintrate.getCategory();
					if (table_name.equals("DepositIntRate")) {
						boolean_flag = false;
						rs_int = stmt_int
								.executeQuery("select * from DepositInterestRate where ac_type='"
										+ depositintrate.getDPAccType()
										+ "' and date_fr='"
										+ depositintrate.getDateFrom()
										+ "' and date_to='31/12/9999' and days_fr="
										+ depositintrate.getDaysFrom()
										+ " and days_to="
										+ depositintrate.getDaysTo()
										+ " and left(de_date,10)='"
										+ getSysDate() + "'");
						if (rs_int.next())
							boolean_flag = true;
						while (boolean_flag) {
							rs_int = stmt_int
									.executeQuery("select min(days_fr),max(days_to) from DepositInterestRate where ac_type='"
											+ depositintrate.getDPAccType()
											+ "' and date_to='"
											+ date_to
											+ "' and days_fr <= "
											+ days_to
											+ " and days_to >=" + days_from);
							rs_int.next();
							if (rs_int.getString(1) != null) {
								minday = rs_int.getInt(1);
								maxday = rs_int.getInt(2);
								rs_int = stmt_int
										.executeQuery("select days_to from DepositInterestRate where ac_type='"
												+ depositintrate.getDPAccType()
												+ "' and date_to ='31/12/9999' and days_to ="
												+ maxday);
								if (rs_int.next()) {
									days_to = maxday;
								} else {
									rs_int = stmt_int
											.executeQuery("select days_fr,days_to from DepositInterestRate where ac_type='"
													+ depositintrate
															.getDPAccType()
													+ "' and date_to ='31/12/9999' and "
													+ days_to
													+ "<"
													+ maxday
													+ " and days_fr > "
													+ days_to
													+ " order by days_fr");
									if (rs_int.next()) {
										if (rs_int.getInt(1) < maxday) {
											days_to = rs_int.getInt(2);
											continue;
										}
									}
								}
								rs_int = stmt_int
										.executeQuery("select days_fr from DepositInterestRate where ac_type='"
												+ depositintrate.getDPAccType()
												+ "' and date_to ='31/12/9999'  and days_fr ="
												+ minday);
								if (rs_int.next()) {
									days_from = minday;
								} else {
									rs_int = stmt_int
											.executeQuery("select days_to,days_fr from DepositInterestRate where ac_type='"
													+ depositintrate
															.getDPAccType()
													+ "' and date_to ='31/12/9999' and "
													+ days_from
													+ " >"
													+ minday
													+ " and days_to <"
													+ days_from
													+ " order by days_to desc");
									if (rs_int.next()) {
										if (rs_int.getInt(1) > minday) {
											days_from = rs_int.getInt(2);
											continue;
										}
									}
								}
								/**
								 * update previous and delete current row
								 */
								stmt_int
										.executeUpdate("delete from DepositInterestRate where ac_type='"
												+ depositintrate.getDPAccType()
												+ "' and date_to='31/12/9999' and days_fr <= "
												+ days_to
												+ " and days_to >="
												+ days_from);
								stmt_int
										.executeUpdate("update DepositInterestRate set date_to='31/12/9999' where ac_type='"
												+ depositintrate.getDPAccType()
												+ "' and date_to='"
												+ date_to
												+ "' and days_fr <= "
												+ maxday
												+ " and days_to >=" + minday);
							} else {
								/**
								 * Normal delete
								 */
								stmt_int
										.executeUpdate("delete from DepositInterestRate where ac_type='"
												+ depositintrate.getDPAccType()
												+ "' and date_to='31/12/9999' and days_fr ="
												+ days_from
												+ " and days_to ="
												+ days_to);
							}
							boolean_flag = false;
						}

					} else if (table_name.equals("DepositCategoryRate")) {
						boolean_flag = false;
						rs_int = stmt_int
								.executeQuery("select * from DepositCategoryRate where ac_type='"
										+ depositintrate.getDPAccType()
										+ "' and category="
										+ depositintrate.getCategory()
										+ " and date_fr='"
										+ depositintrate.getDateFrom()
										+ "' and date_to='31/12/9999' and days_fr="
										+ depositintrate.getDaysFrom()
										+ " and days_to="
										+ depositintrate.getDaysTo()
										+ " and left(de_date,10)='"
										+ getSysDate() + "'");
						if (rs_int.next())
							boolean_flag = true;
						while (boolean_flag) {
							rs_int = stmt_int
									.executeQuery("select min(days_fr),max(days_to) from DepositCategoryRate where ac_type='"
											+ depositintrate.getDPAccType()
											+ "' and category="
											+ depositintrate.getCategory()
											+ " and date_to='"
											+ date_to
											+ "' and days_fr <= "
											+ days_to
											+ " and days_to >=" + days_from);
							rs_int.next();
							if (rs_int.getString(1) != null) {
								minday = rs_int.getInt(1);
								maxday = rs_int.getInt(2);
								rs_int = stmt_int
										.executeQuery("select days_to from DepositCategoryRate where ac_type='"
												+ depositintrate.getDPAccType()
												+ "' and date_to ='31/12/9999' and category="
												+ depositintrate.getCategory()
												+ " and days_to =" + maxday);
								if (rs_int.next()) {
									days_to = maxday;
								} else {
									rs_int = stmt_int
											.executeQuery("select days_fr,days_to from DepositCategoryRate where ac_type='"
													+ depositintrate
															.getDPAccType()
													+ "' and date_to ='31/12/9999' and category="
													+ depositintrate
															.getCategory()
													+ " and "
													+ days_to
													+ "<"
													+ maxday
													+ " and days_fr > "
													+ days_to
													+ " order by days_fr");
									if (rs_int.next()) {
										if (rs_int.getInt(1) < maxday) {
											days_to = rs_int.getInt(2);
											continue;
										}
									}
								}
								rs_int = stmt_int
										.executeQuery("select days_fr from DepositCategoryRate where ac_type='"
												+ depositintrate.getDPAccType()
												+ "' and date_to ='31/12/9999' and category="
												+ depositintrate.getCategory()
												+ " and days_fr =" + minday);
								if (rs_int.next()) {
									days_from = minday;
								} else {
									rs_int = stmt_int
											.executeQuery("select days_to,days_fr from DepositCategoryRate where ac_type='"
													+ depositintrate
															.getDPAccType()
													+ "' and date_to ='31/12/9999' and category="
													+ depositintrate
															.getCategory()
													+ " and "
													+ days_from
													+ " >"
													+ minday
													+ " and days_to <"
													+ days_from
													+ " order by days_to desc");
									if (rs_int.next()) {
										if (rs_int.getInt(1) > minday) {
											days_from = rs_int.getInt(2);
											continue;
										}
									}
								}
								/**
								 * update and delete
								 */
								stmt_int
										.executeUpdate("delete from DepositCategoryRate where ac_type='"
												+ depositintrate.getDPAccType()
												+ "' and date_to='31/12/9999' and  category="
												+ depositintrate.getCategory()
												+ " and days_fr <= "
												+ days_to
												+ " and days_to >=" + days_from);
								stmt_int
										.executeUpdate("update DepositCategoryRate set date_to='31/12/9999' where ac_type='"
												+ depositintrate.getDPAccType()
												+ "' and date_to='"
												+ date_to
												+ "' and category="
												+ depositintrate.getCategory()
												+ " and days_fr <= "
												+ maxday
												+ " and days_to >=" + minday);
							} else {
								/**
								 * Normal delete
								 */
								stmt_int
										.executeUpdate("delete from DepositCategoryRate where ac_type='"
												+ depositintrate.getDPAccType()
												+ "' and date_to='31/12/9999' and category="
												+ category
												+ " and days_fr ="
												+ days_from
												+ " and days_to ="
												+ days_to);
							}
							boolean_flag = false;
						}
					} else if (table_name.equals("DepositQuantumRate")) {
						boolean_flag = false;
						rs_int = stmt_int
								.executeQuery("select * from DepositQuantumRate where ac_type='"
										+ depositintrate.getDPAccType()
										+ "' and category="
										+ depositintrate.getCategory()
										+ " and date_fr='"
										+ depositintrate.getDateFrom()
										+ "' and date_to='31/12/9999' and days_fr="
										+ depositintrate.getDaysFrom()
										+ " and days_to="
										+ depositintrate.getDaysTo()
										+ " and min_amt="
										+ depositintrate.getMinAmt()
										+ " and max_amt="
										+ depositintrate.getMaxAmt()
										+ " and left(de_date,10)='"
										+ getSysDate() + "'");
						if (rs_int.next())
							boolean_flag = true;
						while (boolean_flag) {
							boolean_flag = false;
							rs_int = stmt_int
									.executeQuery("select min(days_fr),max(days_to),min(min_amt),max(max_amt) from DepositQuantumRate where ac_type='"
											+ depositintrate.getDPAccType()
											+ "' and category="
											+ depositintrate.getCategory()
											+ " and date_to='"
											+ date_to
											+ "' and days_fr <= "
											+ days_to
											+ " and days_to >="
											+ days_from
											+ " and min_amt <= "
											+ amount_to
											+ " and max_amt >= " + amount_from);
							rs_int.next();
							if (rs_int.getString(1) != null) {
								minday = rs_int.getInt(1);
								maxday = rs_int.getInt(2);
								minamt = rs_int.getInt(3);
								maxamt = rs_int.getInt(4);
								rs_int = stmt_int
										.executeQuery("select days_to from DepositQuantumRate where ac_type='"
												+ depositintrate.getDPAccType()
												+ "' and date_to ='31/12/9999' and category="
												+ depositintrate.getCategory()
												+ " and days_to =" + maxday);
								if (rs_int.next()) {
									days_to = maxday;
								} else {
									rs_int = stmt_int
											.executeQuery("select days_fr,days_to from DepositQuantumRate where ac_type='"
													+ depositintrate
															.getDPAccType()
													+ "' and date_to ='31/12/9999' and category="
													+ depositintrate
															.getCategory()
													+ " and "
													+ days_to
													+ "<"
													+ maxday
													+ " and days_fr > "
													+ days_to
													+ " order by days_fr");
									if (rs_int.next()) {
										if (rs_int.getInt(1) < maxday) {
											days_to = rs_int.getInt(2);
											continue;
										}
									}
								}
								rs_int = stmt_int
										.executeQuery("select days_fr from DepositQuantumRate where ac_type='"
												+ depositintrate.getDPAccType()
												+ "' and date_to ='31/12/9999' and category="
												+ depositintrate.getCategory()
												+ " and days_fr =" + minday);
								if (rs_int.next()) {
									days_from = minday;
								} else {
									rs_int = stmt_int
											.executeQuery("select days_to,days_fr from DepositQuantumRate where ac_type='"
													+ depositintrate
															.getDPAccType()
													+ "' and date_to ='31/12/9999' and category="
													+ depositintrate
															.getCategory()
													+ " and "
													+ days_from
													+ " >"
													+ minday
													+ " and days_to <"
													+ days_from
													+ " order by days_to desc");
									if (rs_int.next()) {
										if (rs_int.getInt(1) > minday) {
											days_from = rs_int.getInt(2);
											continue;
										}
									}
								}
								rs_int = stmt_int
										.executeQuery("select max_amt from DepositQuantumRate where ac_type='"
												+ depositintrate.getDPAccType()
												+ "' and date_to ='31/12/9999' and category="
												+ depositintrate.getCategory()
												+ " and days_fr <="
												+ days_to
												+ " and days_to >= "
												+ days_from
												+ " and max_amt ="
												+ maxamt);
								if (rs_int.next()) {
									amount_to = maxamt;
								} else {
									rs_int = stmt_int
											.executeQuery("select min_amt,max_amt from DepositQuantumRate where ac_type='"
													+ depositintrate
															.getDPAccType()
													+ "' and date_to ='31/12/9999' and category="
													+ depositintrate
															.getCategory()
													+ " and days_fr <="
													+ days_to
													+ " and days_to >= "
													+ days_from
													+ " and "
													+ amount_to
													+ " < "
													+ maxamt
													+ " and min_amt > "
													+ amount_to
													+ " order by min_amt");
									if (rs_int.next()) {
										if (rs_int.getDouble(1) < maxamt) {
											amount_to = rs_int.getDouble(2);
											continue;
										}
									}
								}
								rs_int = stmt_int
										.executeQuery("select min_amt from DepositQuantumRate where ac_type='"
												+ depositintrate.getDPAccType()
												+ "' and date_to ='31/12/9999' and category="
												+ depositintrate.getCategory()
												+ " and days_fr <="
												+ days_to
												+ " and days_to >= "
												+ days_from
												+ " and min_amt ="
												+ minamt);
								if (rs_int.next()) {
									amount_from = minamt;
								} else {
									rs_int = stmt_int
											.executeQuery("select max_amt,min_amt from DepositQuantumRate where ac_type='"
													+ depositintrate
															.getDPAccType()
													+ "' and date_to ='31/12/9999' and category="
													+ depositintrate
															.getCategory()
													+ " and days_fr <="
													+ days_to
													+ " and days_to >= "
													+ days_from
													+ " and "
													+ amount_from
													+ " >"
													+ minamt
													+ " and max_amt <"
													+ amount_from
													+ " order by max_amt desc");
									if (rs_int.next()) {
										if (rs_int.getDouble(1) > minamt) {
											amount_from = rs_int.getDouble(2);
											System.out.println(amount_from);
											continue;
										}
									}
								}
								/**
								 * update and delete
								 */
								stmt_int
										.executeUpdate("delete from DepositQuantumRate where ac_type='"
												+ depositintrate.getDPAccType()
												+ "' and date_to='31/12/9999' and  category="
												+ depositintrate.getCategory()
												+ " and days_fr >= "
												+ days_from
												+ " and days_to <="
												+ days_to
												+ " and min_amt >= "
												+ amount_from
												+ " and max_amt <= "
												+ amount_to);
								stmt_int
										.executeUpdate("update DepositQuantumRate set date_to='31/12/9999' where ac_type='"
												+ depositintrate.getDPAccType()
												+ "' and date_to='"
												+ date_to
												+ "' and category="
												+ depositintrate.getCategory()
												+ " and days_fr >= "
												+ minday
												+ " and days_to <="
												+ maxday
												+ " and min_amt >= "
												+ minamt
												+ " and max_amt <= " + maxamt);
							} else {
								/**
								 * delete
								 */
								stmt_int
										.executeUpdate("delete from DepositQuantumRate where ac_type='"
												+ depositintrate.getDPAccType()
												+ "' and date_to='31/12/9999' and category="
												+ category
												+ " and days_fr ="
												+ days_from
												+ " and days_to ="
												+ days_to
												+ " and min_amt="
												+ amount_from
												+ " and max_amt="
												+ amount_to);
							}
							boolean_flag = false;
						}
					}
				}
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordNotInsertedException();
		} catch (Exception e) {
			e.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordNotInsertedException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			commonLocal = null;
		}
		return 1;
	}

	public int updateRow(DepositIntRate depint, int rowcount, String table_name)
			throws CreateException, DateFormatException {
		Connection conn = null;
		try {
			conn = getConnection();
			commonLocal = commonLocalHome.create();
			if (depint.getDateTo().equals("Future Date")) {
				ResultSet rs_main = null;
				Statement stmt_main = conn.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				PreparedStatement ps = null;
				if (table_name.equals("DepositIntRate"))
					rs_main = stmt_main
							.executeQuery("select date_fr,date_to,days_fr,days_to,int_rate from DepositInterestRate where ac_type = '"
									+ depint.getDPAccType()
									+ "' order by concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1)) , concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))");
				else if (table_name.equals("DepositQuantumRate"))
					rs_main = stmt_main
							.executeQuery("select date_fr,date_to,days_fr,days_to,extra_int_rate,category,min_amt,max_amt from DepositQuantumRate where ac_type = '"
									+ depint.getDPAccType()
									+ "' order by concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1)) , concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))");
				else if (table_name.equals("DepositCategoryRate"))
					rs_main = stmt_main
							.executeQuery("select date_fr,date_to,days_fr,days_to,extra_int_rate,category from DepositCategoryRate where ac_type = '"
									+ depint.getDPAccType()
									+ "' order by concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1)) , concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))");
				rs_main.relative(rowcount);
				int no_of_days = Validations.dayCompare(rs_main.getString(1),
						depint.getDateFrom());
				if (no_of_days != 0) {
					String date_to_cur = Validations.addDays(depint
							.getDateFrom(), -1);
					String date_to_pre = Validations.addDays(rs_main
							.getString(1), -1);
					if (table_name.equals("DepositIntRate"))
						stmt_main
								.executeUpdate("update DepositInterestRate set date_to ='"
										+ date_to_cur
										+ "',days_fr="
										+ depint.getDaysFrom()
										+ ",days_to="
										+ depint.getDaysTo()
										+ " where days_fr="
										+ rs_main.getInt(3)
										+ " and days_to="
										+ rs_main.getInt(4)
										+ " and date_to='"
										+ date_to_pre
										+ "' and ac_type ='"
										+ depint.getDPAccType() + "'");
					else if (table_name.equals("DepositQuantumRate"))
						stmt_main
								.executeUpdate("update DepositQuantumRate set date_to ='"
										+ date_to_cur
										+ "' where days_fr="
										+ rs_main.getInt(3)
										+ " and days_to="
										+ rs_main.getInt(4)
										+ " and date_to='"
										+ date_to_pre
										+ "' and ac_type ='"
										+ depint.getDPAccType()
										+ "' and category="
										+ rs_main.getDouble(6)
										+ " and min_amt="
										+ rs_main.getDouble(7)
										+ " and max_amt="
										+ rs_main.getDouble(8));
					else if (table_name.equals("DepositCategoryRate"))
						stmt_main
								.executeUpdate("update DepositCategoryRate set date_to ='"
										+ date_to_cur
										+ "' where days_fr="
										+ rs_main.getInt(3)
										+ " and days_to="
										+ rs_main.getInt(4)
										+ " and date_to='"
										+ date_to_pre
										+ "' and ac_type ='"
										+ depint.getDPAccType()
										+ "' and category="
										+ rs_main.getDouble(6));
				}
			}

		} catch (SQLException e3) {
			e3.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordNotInsertedException();
		} catch (Exception e2) {
			e2.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordNotInsertedException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			commonLocal = null;
		}
		return 1;
	}

	/*
	 * public int updateRow(DepositIntRate depint,int rowcount,String
	 * table_name) throws CreateException,RecordNotInsertedException,
	 * DateFormatException { Connection conn = null; try { conn =
	 * getConnection(); commonLocal = commonLocalHome.create();
	 * if(depint.getDateTo().equals("Future Date")) { ResultSet rs_main=null;
	 * Statement stmt_main
	 * =conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE
	 * ,ResultSet.CONCUR_UPDATABLE); PreparedStatement ps=null;
	 * if(table_name.equals("DepositIntRate")) rs_main =
	 * stmt_main.executeQuery("select date_fr,date_to,days_fr,days_to,int_rate
	 * from DepositInterestRate where ac_type = '"+depint.getDPAccType()+"'
	 * order by
	 * concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate
	 * ('/',
	 * date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr
	 * )-1)) ,
	 * concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate
	 * ('/',
	 * date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to
	 * )-1))"); else if(table_name.equals("DepositQuantumRate")) rs_main =
	 * stmt_main.executeQuery("select
	 * date_fr,date_to,days_fr,days_to,extra_int_rate,category,min_amt,max_amt
	 * from DepositQuantumRate where ac_type = '"+depint.getDPAccType()+"' order
	 * by
	 * concat(right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/'
	 * ,
	 * date_fr,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1
	 * )) ,
	 * concat(right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate
	 * ('/',
	 * date_to,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to
	 * )-1))"); else if(table_name.equals("DepositCategoryRate")) rs_main =
	 * stmt_main.executeQuery("select
	 * date_fr,date_to,days_fr,days_to,extra_int_rate,category from
	 * DepositCategoryRate where ac_type = '"+depint.getDPAccType()+"' order by
	 * concat
	 * (right(date_fr,4),'-',mid(date_fr,locate('/',date_fr)+1,(locate('/',
	 * date_fr
	 * ,4)-locate('/',date_fr)-1)),'-',left(date_fr,locate('/',date_fr)-1)) ,
	 * concat
	 * (right(date_to,4),'-',mid(date_to,locate('/',date_to)+1,(locate('/',
	 * date_to
	 * ,4)-locate('/',date_to)-1)),'-',left(date_to,locate('/',date_to)-1))");
	 * rs_main.relative(rowcount); int no_of_days =
	 * Validations.dayCompare(rs_main.getString(1),depint.getDateFrom());
	 * if(no_of_days!=0) { String
	 * date_to_cur=Validations.addDays(depint.getDateFrom(),-1); String
	 * date_to_pre=Validations.addDays(rs_main.getString(1),-1);
	 * if(table_name.equals("DepositIntRate")) stmt_main.executeUpdate("update
	 * DepositInterestRate set date_to ='"+date_to_cur+"' where
	 * days_fr="+rs_main.getInt(3)+" and days_to="+rs_main.getInt(4)+" and
	 * date_to='"+date_to_pre+"' and ac_type ='"+depint.getDPAccType()+"'");
	 * else if(table_name.equals("DepositQuantumRate"))
	 * stmt_main.executeUpdate("update DepositQuantumRate set date_to
	 * ='"+date_to_cur+"' where days_fr="+rs_main.getInt(3)+" and
	 * days_to="+rs_main.getInt(4)+" and date_to='"+date_to_pre+"' and ac_type
	 * ='"+depint.getDPAccType()+"' and category="+rs_main.getDouble(6)+" and
	 * min_amt="+rs_main.getDouble(7)+" and max_amt="+rs_main.getDouble(8));
	 * else if(table_name.equals("DepositCategoryRate"))
	 * stmt_main.executeUpdate("update DepositCategoryRate set date_to
	 * ='"+date_to_cur+"' where days_fr="+rs_main.getInt(3)+" and
	 * days_to="+rs_main.getInt(4)+" and date_to='"+date_to_pre+"' and ac_type
	 * ='"+depint.getDPAccType()+"' and category="+rs_main.getDouble(6)); }
	 * if(table_name.equals("DepositIntRate")) stmt_main.executeUpdate("delete
	 * from DepositInterestRate where date_fr='"+rs_main.getString(1)+"' and
	 * date_to ='"+rs_main.getString(2)+"' and days_fr="+rs_main.getInt(3)+" and
	 * days_to="+rs_main.getInt(4)+" and ac_type ='"+depint.getDPAccType()+"'");
	 * else if(table_name.equals("DepositQuantumRate"))
	 * stmt_main.executeUpdate("delete from DepositQuantumRate where
	 * date_fr='"+rs_main.getString(1)+"' and date_to
	 * ='"+rs_main.getString(2)+"' and days_fr="+rs_main.getInt(3)+" and
	 * days_to="+rs_main.getInt(4)+" and ac_type ='"+depint.getDPAccType()+"'
	 * and category="+rs_main.getDouble(6)+" and
	 * min_amt="+rs_main.getDouble(7)+" and max_amt="+rs_main.getDouble(8));
	 * else if(table_name.equals("DepositCategoryRate"))
	 * stmt_main.executeUpdate("delete from DepositCategoryRate where
	 * date_fr='"+rs_main.getString(1)+"' and date_to
	 * ='"+rs_main.getString(2)+"' and days_fr="+rs_main.getInt(3)+" and
	 * days_to="+rs_main.getInt(4)+" and ac_type ='"+depint.getDPAccType()+"'
	 * and category="+rs_main.getDouble(6));
	 * 
	 * if(table_name.equals("DepositIntRate")) ps=conn.prepareStatement("insert
	 * into DepositInterestRate values(?,?,?,?,?,?,?,?,?)"); else
	 * if(table_name.equals("DepositQuantumRate"))
	 * ps=conn.prepareStatement("insert into DepositQuantumRate
	 * values(?,?,?,?,?,?,?,?,?,?,?,?)"); else
	 * if(table_name.equals("DepositCategoryRate"))
	 * ps=conn.prepareStatement("insert into DepositCategoryRate
	 * values(?,?,?,?,?,?,?,?,?,?)"); ps.setString(1,depint.getDPAccType());
	 * if(table_name.equals("DepositIntRate")){
	 * ps.setString(2,depint.getDateFrom()); ps.setString(3,"31/12/9999");
	 * ps.setInt(4,depint.getDaysFrom()); ps.setInt(5,depint.getDaysTo());
	 * ps.setDouble(6,depint.getIntRate());
	 * ps.setString(7,depint.obj_userverifier.getUserId());
	 * ps.setString(8,depint.obj_userverifier.getUserTml());
	 * ps.setString(9,commonLocal.getSysDateTime()); ps.executeUpdate(); } else
	 * if(table_name.equals("DepositQuantumRate")){
	 * ps.setInt(2,depint.getCategory()); ps.setString(3,depint.getDateFrom());
	 * ps.setString(4,"31/12/9999"); ps.setInt(5,depint.getDaysFrom());
	 * ps.setInt(6,depint.getDaysTo()); ps.setDouble(7,depint.getMinAmt());
	 * ps.setDouble(8,depint.getMaxAmt()); ps.setDouble(9,depint.getIntRate());
	 * ps.setString(10,depint.obj_userverifier.getUserId());
	 * ps.setString(11,depint.obj_userverifier.getUserTml());
	 * ps.setString(12,commonLocal.getSysDateTime()); ps.executeUpdate(); } else
	 * if(table_name.equals("DepositCategoryRate")){
	 * ps.setInt(2,depint.getCategory()); ps.setString(3,depint.getDateFrom());
	 * ps.setString(4,"31/12/9999"); ps.setInt(5,depint.getDaysFrom());
	 * ps.setInt(6,depint.getDaysTo()); ps.setDouble(7,depint.getIntRate());
	 * ps.setDouble(8,depint.getMinAmt());
	 * ps.setString(9,depint.obj_userverifier.getUserId());
	 * ps.setString(10,depint.obj_userverifier.getUserTml());
	 * ps.setString(11,commonLocal.getSysDateTime()); ps.executeUpdate(); } }
	 * 
	 * }catch(SQLException e3){ e3.printStackTrace();
	 * sessionContext.setRollbackOnly(); throw new RecordNotInsertedException();
	 * } finally{ try { conn.close(); } catch (SQLException e) {
	 * e.printStackTrace(); } commonLocal = null; } return 1; }
	 */

	public TrfVoucherObject[] getDepositTransferVoucher(String string_acc_type,
			int int_acc_no, int type) throws CreateException,
			RecordsNotFoundException {
		TrfVoucherObject array_trfvoucherobject[] = null;
		Connection conn = null;
		ResultSet rs_voucher = null;

		try {
			conn = getConnection();
			commonLocal = commonLocalHome.create();
			Statement stmt_voucher = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			String sub_ac_type = string_acc_type.substring(0, 4);
			System.out.println("ac_type" + sub_ac_type);
			System.out.println("ac_type=>" + string_acc_type);
			System.out.println("ac_no==>" + int_acc_no);
			// String vch_pay_ind=null;

			/*
			 * rs_voucher=stmt_voucher.executeQuery("select vch_pay_ind from
			 * InterestTransferVoucher where ac_type='"+string_acc_type+"' and
			 * ac_no="+int_acc_no+""); while(rs_voucher.next())
			 * vch_pay_ind=rs_voucher.getString("vch_pay_ind");
			 * System.out.println("vch_pay"+vch_pay_ind);
			 */

			if (string_acc_type.startsWith("1003")
					|| string_acc_type.startsWith("1004")
					|| string_acc_type.startsWith("1005")) {
				// if(vch_pay_ind.equalsIgnoreCase("F"))
				if (type == 0)
					rs_voucher = stmt_voucher
							.executeQuery("select vch_no,vch_date,vch_amount,vch_pay_ind,custtype,concat_ws(' ',fname,mname,lname) as name from InterestTransferVoucher tf,DepositMaster dm,CustomerMaster cm where dm.cid = cm.cid and tf.ac_type='"
									+ string_acc_type
									+ "' and tf.ac_no="
									+ int_acc_no
									+ " and vch_pay_ind = 'F' and dm.ac_type='"
									+ string_acc_type
									+ "' and dm.ac_no="
									+ int_acc_no);// concat() added by
				// Sanjeet///before 1003000
				else
					rs_voucher = stmt_voucher
							.executeQuery("select vch_no,vch_date,vch_amount,vch_pay_ind,custtype,concat_ws(' ',fname,mname,lname) as name from InterestTransferVoucher tf,DepositMaster dm,CustomerMaster cm where dm.cid = cm.cid and tf.ac_type='"
									+ string_acc_type
									+ "' and tf.ac_no="
									+ int_acc_no
									+ " and vch_pay_ind = 'T' and dm.ac_type='"
									+ string_acc_type
									+ "' and dm.ac_no="
									+ int_acc_no);// concat() added by
				// Sanjeet///before 1003000

			}
			rs_voucher.last();
			if (rs_voucher.getRow() == 0) {
				return null;
			}

			array_trfvoucherobject = new TrfVoucherObject[rs_voucher.getRow()];
			rs_voucher.beforeFirst();
			int i = 0;

			while (rs_voucher.next()) {
				array_trfvoucherobject[i] = new TrfVoucherObject();
				array_trfvoucherobject[i].setVoucherNo(rs_voucher
						.getInt("vch_no"));
				array_trfvoucherobject[i].setVoucherDate(rs_voucher
						.getString("vch_date"));
				array_trfvoucherobject[i].setTrfAmount(rs_voucher
						.getDouble("vch_amount"));
				array_trfvoucherobject[i].setTvPayInd(rs_voucher
						.getString("vch_pay_ind"));
				array_trfvoucherobject[i].setPayAccNo(rs_voucher
						.getInt("custtype"));
				array_trfvoucherobject[i].setAccType(string_acc_type);
				array_trfvoucherobject[i].setAccNo(int_acc_no);
				array_trfvoucherobject[i].setVoucherType(rs_voucher
						.getString("name"));
				i++;
			}
			array_trfvoucherobject[0].setSigObj(commonLocal
					.getSignatureDetails(int_acc_no, string_acc_type));
		} catch (SQLException trf_voucher) {
			trf_voucher.printStackTrace();
			array_trfvoucherobject = null;
			throw new RecordsNotFoundException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			commonLocal = null;
		}
		return array_trfvoucherobject;
	}

	public int storeDepositTransferVoucher(
			TrfVoucherObject[] array_trfvoucherobject) throws CreateException {
		int int_return_value = 0;
		Connection conn = null;
		try {
			System.out.println("Inside storeDepositTransferVoucher()...");
			conn = getConnection();
			commonLocal = commonLocalHome.create();
			ResultSet rs_voucher = null, rs = null;
			double double_total_amount = 0;
			int trn_seq = 0;
			PreparedStatement pstmt_insert = null;
			Statement stmt_voucher = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			Statement stmt = conn.createStatement();
			rs = stmt
					.executeQuery("select trn_seq from DepositTransaction where ac_type='"
							+ array_trfvoucherobject[0].getAccType()
							+ "' and ac_no="
							+ array_trfvoucherobject[0].getAccNo()
							+ " order by trn_seq desc limit 1");
			if (rs.next()) {
				trn_seq = rs.getInt("trn_seq");
			}
			rs.beforeFirst();
			System.out.println("trn_seq==>" + trn_seq);
			if (array_trfvoucherobject[0].getPayMode().equals("C")) {
				System.out.println("CASH MODE...");
				int_return_value = commonLocal.getModulesColumn(
						"lst_voucher_scroll_no", "1019000");
				for (int i = 0; i < array_trfvoucherobject.length; i++) {
					// stmt_voucher.addBatch("update InterestTransferVoucher set
					// vch_pay_ind='T',vch_pay_date='"+getSysDate()+"',pay_mode='C',
					// pay_ac_no="+int_return_value+" where
					// vch_no="+array_trfvoucherobject[i].getVoucherNo()+" and
					// ac_type='"+array_trfvoucherobject[i].getAccType()+"' and
					// ac_no="+array_trfvoucherobject[i].getAccNo()+" and
					// vch_date='"+array_trfvoucherobject[i].getVoucherDate()+"'");
					double_total_amount += array_trfvoucherobject[i]
							.getTrfAmount();
					pstmt_insert = conn
							.prepareStatement("update DepositTransaction set ref_no="
									+ int_return_value
									+ ",trn_mode='C' where ac_type=? and ac_no=? and trn_type='P' and ref_no=?");
					pstmt_insert.setString(1, array_trfvoucherobject[i]
							.getAccType());
					pstmt_insert
							.setInt(2, array_trfvoucherobject[i].getAccNo());
					pstmt_insert.setInt(3, array_trfvoucherobject[i]
							.getVoucherNo());
					pstmt_insert.executeUpdate();
				}
				stmt_voucher.executeBatch();

				pstmt_insert = conn
						.prepareStatement("insert into DayCash (scroll_no,trn_type,trn_date,ac_type,ac_no,csh_amt,cd_ind,vch_type,attached,vch_no,de_user,de_tml,de_date,ve_user,ve_tml,ve_date,name) values (0,'P',?,?,?,?,'C','I','F',?,?,?,?,?,?,?,?)");
				pstmt_insert.setString(1, getSysDate());
				pstmt_insert.setString(2, array_trfvoucherobject[0]
						.getAccType());
				pstmt_insert.setInt(3, array_trfvoucherobject[0].getAccNo());
				pstmt_insert.setDouble(4, double_total_amount);
				pstmt_insert.setInt(5, int_return_value);
				pstmt_insert.setString(6, array_trfvoucherobject[0]
						.getTvPrtInd());
				pstmt_insert.setString(7, array_trfvoucherobject[0]
						.getTvPayInd());
				pstmt_insert.setString(8, array_trfvoucherobject[0]
						.getTvPayDate());
				pstmt_insert.setString(9, array_trfvoucherobject[0]
						.getTvPrtInd());
				pstmt_insert.setString(10, array_trfvoucherobject[0]
						.getTvPayInd());
				pstmt_insert.setString(11, array_trfvoucherobject[0]
						.getTvPayDate());
				pstmt_insert.setString(12, array_trfvoucherobject[0]
						.getVoucherType());
				pstmt_insert.executeUpdate();

				/** * Updation of DepositTransaction ** */
				// Added by Riswan
				pstmt_insert = conn
						.prepareStatement("update DepositTransaction set ref_no="
								+ int_return_value
								+ ",trn_mode='C' where ac_type=? and ac_no=? and trn_type='P' and ref_no=?");
				pstmt_insert.setString(1, array_trfvoucherobject[0]
						.getAccType());
				pstmt_insert.setInt(2, array_trfvoucherobject[0].getAccNo());
				pstmt_insert
						.setInt(3, array_trfvoucherobject[0].getVoucherNo());
				pstmt_insert.executeUpdate();

				System.out.println("2..");
				/**
				 * Gl for Cash credit
				 */
				rs_voucher = stmt_voucher
						.executeQuery("select * from GLKeyParam where ac_type='1019001' and code=1");
				if (rs_voucher.next()) {
					GLTransObject trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_voucher.getString("gl_type"));
					trnobj.setGLCode(rs_voucher.getString("gl_code"));
					trnobj.setTrnMode("C");
					trnobj.setAmount(double_total_amount);
					trnobj.setCdind("C");
					trnobj.setAccType(array_trfvoucherobject[0].getAccType());
					trnobj.setAccNo(String.valueOf(array_trfvoucherobject[0]
							.getAccNo()));
					trnobj.setTrnType("P");
					trnobj.setRefNo(int_return_value);
					trnobj.setTrnSeq(trn_seq);
					trnobj.setVtml(array_trfvoucherobject[0].getTvPayInd());
					trnobj.setVid(array_trfvoucherobject[0].getTvPrtInd());
					trnobj.setVDate(array_trfvoucherobject[0].getTvPayDate());
					commonLocal.storeGLTransaction(trnobj);
				}
				/**
				 * Gl for trf voucher debit
				 */
				rs_voucher = stmt_voucher
						.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = 3 and gk.ac_type = '"
								+ array_trfvoucherobject[0].getAccType()
								+ "' and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
				if (rs_voucher.next()) {
					GLTransObject trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_voucher.getString("gk.gl_type"));
					trnobj.setGLCode(rs_voucher.getString("gk.gl_code"));
					trnobj.setTrnMode("C");
					trnobj.setAmount(double_total_amount
							* rs_voucher.getInt("mult_by"));
					trnobj.setCdind("D");
					trnobj.setAccType(array_trfvoucherobject[0].getAccType());
					trnobj.setAccNo(String.valueOf(array_trfvoucherobject[0]
							.getAccNo()));
					trnobj.setTrnType("P");
					trnobj.setRefNo(int_return_value);
					trnobj.setTrnSeq(trn_seq);
					trnobj.setVtml(array_trfvoucherobject[0].getTvPayInd());
					trnobj.setVid(array_trfvoucherobject[0].getTvPrtInd());
					trnobj.setVDate(array_trfvoucherobject[0].getTvPayDate());
					commonLocal.storeGLTransaction(trnobj);
				}
				for (int i = 0; i < array_trfvoucherobject.length; i++) {
					stmt_voucher
							.addBatch("update InterestTransferVoucher set vch_pay_ind='T',vch_pay_date='"
									+ getSysDate()
									+ "',pay_mode='C',pay_ac_no="
									+ int_return_value
									+ " where vch_no="
									+ array_trfvoucherobject[i].getVoucherNo()
									+ " and ac_type='"
									+ array_trfvoucherobject[i].getAccType()
									+ "' and ac_no="
									+ array_trfvoucherobject[i].getAccNo()
									+ " and vch_date='"
									+ array_trfvoucherobject[i]
											.getVoucherDate() + "'");
				}
				stmt_voucher.executeBatch();
				// int_return_value = -1;

			} else if (array_trfvoucherobject[0].getPayMode().equals("P")) {
				System.out.println("PAY ORDER MODE");
				for (int i = 0; i < array_trfvoucherobject.length; i++) {
					double_total_amount += array_trfvoucherobject[i]
							.getTrfAmount();
				}
				PayOrderObject po = new PayOrderObject();
				po.setPOType("P");
				po.setPOCustType(0);
				po.setPOPayee(array_trfvoucherobject[0].getVoucherType());
				po.setPOAccType(array_trfvoucherobject[0].getAccType());
				po.setPOAccNo(array_trfvoucherobject[0].getAccNo());
				po.setPOGlCode(0);
				po.setPOAmount(double_total_amount);
				po.setCommissionAmount(0);
				po.uv.setUserTml(array_trfvoucherobject[0].getTvPayInd());
				po.uv.setUserId(array_trfvoucherobject[0].getTvPrtInd());
				int_return_value = commonLocal.storePayOrder(po);

				System.out.println("1..");
				/** ** Updation of DepositTransaction *** */
				// Added by Riswan
				pstmt_insert = conn
						.prepareStatement("update DepositTransaction set trn_mode='P',trn_narr='Pay Order' where ac_type=? and ac_no=? and trn_type='P' and ref_no=?");
				pstmt_insert.setString(1, array_trfvoucherobject[0]
						.getAccType());
				pstmt_insert.setInt(2, array_trfvoucherobject[0].getAccNo());
				pstmt_insert
						.setInt(3, array_trfvoucherobject[0].getVoucherNo());
				// pstmt_insert.executeUpdate();

				if (pstmt_insert.executeUpdate() != 0)
					System.out.println("Tran recrd Updated..");
				else
					System.out.println("Unable to Update trn recrd..");

				System.out.println("2..");
				/**
				 * Gl for po credit
				 */
				rs_voucher = stmt_voucher
						.executeQuery("select * from GLKeyParam where ac_type='1016001' and code=1");
				if (rs_voucher.next()) {
					GLTransObject trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_voucher.getString("gl_type"));
					trnobj.setGLCode(rs_voucher.getString("gl_code"));
					trnobj.setTrnMode("T");
					trnobj.setAmount(double_total_amount);
					trnobj.setCdind("C");
					trnobj.setAccType(array_trfvoucherobject[0].getAccType());
					trnobj.setAccNo(String.valueOf(array_trfvoucherobject[0]
							.getAccNo()));
					trnobj.setTrnType("P");
					trnobj.setRefNo(int_return_value);
					trnobj.setTrnSeq(0);
					trnobj.setVtml(array_trfvoucherobject[0].getTvPayInd());
					trnobj.setVid(array_trfvoucherobject[0].getTvPrtInd());
					commonLocal.storeGLTransaction(trnobj);
				}
				/**
				 * Gl for trf voucher debit
				 */
				rs_voucher = stmt_voucher
						.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = 3 and gk.ac_type = '"
								+ array_trfvoucherobject[0].getAccType()
								+ "' and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
				if (rs_voucher.next()) {
					GLTransObject trnobj = new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(rs_voucher.getString("gk.gl_type"));
					trnobj.setGLCode(rs_voucher.getString("gk.gl_code"));
					trnobj.setTrnMode("T");
					trnobj.setAmount(double_total_amount
							* rs_voucher.getInt("mult_by"));
					trnobj.setCdind("D");
					trnobj.setAccType(array_trfvoucherobject[0].getAccType());
					trnobj.setAccNo(String.valueOf(array_trfvoucherobject[0]
							.getAccNo()));
					trnobj.setTrnType("P");
					trnobj.setRefNo(int_return_value);
					trnobj.setTrnSeq(trn_seq);
					trnobj.setVtml(array_trfvoucherobject[0].getTvPayInd());
					trnobj.setVid(array_trfvoucherobject[0].getTvPrtInd());
					commonLocal.storeGLTransaction(trnobj);
				}
				for (int i = 0; i < array_trfvoucherobject.length; i++) {
					stmt_voucher
							.addBatch("update InterestTransferVoucher set vch_pay_ind='T',vch_pay_date='"
									+ getSysDate()
									+ "',pay_mode='P',pay_ac_no="
									+ int_return_value
									+ " where vch_no="
									+ array_trfvoucherobject[i].getVoucherNo()
									+ " and ac_type='"
									+ array_trfvoucherobject[i].getAccType()
									+ "' and ac_no="
									+ array_trfvoucherobject[i].getAccNo()
									+ " and vch_date='"
									+ array_trfvoucherobject[i]
											.getVoucherDate() + "'");
				}
				stmt_voucher.executeBatch();
				// int_return_value = -1;
			} else if (array_trfvoucherobject[0].getPayMode().equals("T")) {
				System.out.println("TRANSFER MODE..");
				for (int i = 0; i < array_trfvoucherobject.length; i++) {
					AccountTransObject am = new AccountTransObject();
					am.setAccType(array_trfvoucherobject[i].getPayAccType());
					am.setAccNo(array_trfvoucherobject[i].getPayAccNo());
					am.setTransDate(getSysDate());
					am.setTransType("R");
					am.setTransAmount(array_trfvoucherobject[i].getTrfAmount());
					am.setTransMode("T");
					am.setTransSource(array_trfvoucherobject[i].getTvPayInd());
					am.setCdInd("C");
					am.setChqDDNo(0);
					am.setChqDDDate("");
					am.setTransNarr(array_trfvoucherobject[i].getAccType()
							+ " " + array_trfvoucherobject[i].getAccNo());
					am.setRef_No(int_return_value);
					am.setPayeeName(array_trfvoucherobject[i].getVoucherType());
					am.setCloseBal(array_trfvoucherobject[i].getTrfAmount());
					am.setLedgerPage(0);
					am.uv.setUserTml(array_trfvoucherobject[i].getTvPayInd());
					am.uv.setUserId(array_trfvoucherobject[i].getTvPrtInd());
					am.uv.setVerTml(array_trfvoucherobject[i].getTvPayInd());
					am.uv.setVerId(array_trfvoucherobject[i].getTvPrtInd());
					commonLocal.storeAccountTransaction(am);
					// int_return_value = -1;

					/** * Updation of DepositTransaction ** */
					// Added by Riswan
					pstmt_insert = conn
							.prepareStatement("update DepositTransaction set trn_narr='"
									+ "Trf  "
									+ array_trfvoucherobject[i].getPayAccType()
									+ "-"
									+ array_trfvoucherobject[0].getPayAccNo()
									+ "',trn_mode='T' where ac_type=? and ac_no=? and ref_no=?");
					pstmt_insert.setString(1, array_trfvoucherobject[i]
							.getAccType());
					pstmt_insert
							.setInt(2, array_trfvoucherobject[i].getAccNo());
					pstmt_insert.setInt(3, array_trfvoucherobject[i]
							.getVoucherNo());
					if (pstmt_insert.executeUpdate() != 0)
						System.out.println("Tran recrd Updated..");
					else
						System.out.println("Unable to Update trn recrd..");

					/**
					 * Gl for trf credit
					 */
					rs_voucher = stmt_voucher
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = 3 and gk.ac_type = '"
									+ array_trfvoucherobject[0].getPayAccType()
									+ "' and trn_type='P' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					if (rs_voucher.next()) {
						GLTransObject trnobj = new GLTransObject();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_voucher.getString("gk.gl_type"));
						trnobj.setGLCode(rs_voucher.getString("gk.gl_code"));
						trnobj.setTrnMode("T");
						trnobj.setAmount(double_total_amount
								* rs_voucher.getInt("mult_by"));
						trnobj.setCdind("C");
						trnobj.setAccType(array_trfvoucherobject[0]
								.getPayAccType());
						trnobj.setAccNo(String
								.valueOf(array_trfvoucherobject[0]
										.getPayAccNo()));
						trnobj.setTrnType("P");
						trnobj.setRefNo(int_return_value);
						trnobj.setTrnSeq(trn_seq);
						trnobj.setVtml(array_trfvoucherobject[0].getTvPayInd());
						trnobj.setVid(array_trfvoucherobject[0].getTvPrtInd());
						commonLocal.storeGLTransaction(trnobj);
					}

					/**
					 * Gl for trf voucher debit
					 */
					rs_voucher = stmt_voucher
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = 3 and gk.ac_type = '"
									+ array_trfvoucherobject[0].getAccType()
									+ "' and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					if (rs_voucher.next()) {
						GLTransObject trnobj = new GLTransObject();
						trnobj.setTrnDate(getSysDate());
						trnobj.setGLType(rs_voucher.getString("gk.gl_type"));
						trnobj.setGLCode(rs_voucher.getString("gk.gl_code"));
						trnobj.setTrnMode("T");
						trnobj.setAmount(array_trfvoucherobject[i]
								.getTrfAmount()
								* rs_voucher.getInt("mult_by"));
						trnobj.setCdind("D");
						trnobj.setAccType(array_trfvoucherobject[0]
								.getAccType());
						trnobj.setAccNo(String
								.valueOf(array_trfvoucherobject[0].getAccNo()));
						trnobj.setTrnType("P");
						trnobj.setRefNo(int_return_value);
						trnobj.setTrnSeq(trn_seq);
						trnobj.setVtml(array_trfvoucherobject[0].getTvPayInd());
						trnobj.setVid(array_trfvoucherobject[0].getTvPrtInd());
						commonLocal.storeGLTransaction(trnobj);
					}
				}
				for (int i = 0; i < array_trfvoucherobject.length; i++) {
					stmt_voucher
							.addBatch("update InterestTransferVoucher set vch_pay_ind='T',vch_pay_date='"
									+ getSysDate()
									+ "',pay_mode='T',pay_ac_type='"
									+ array_trfvoucherobject[0].getPayAccType()
									+ "',pay_ac_no="
									+ array_trfvoucherobject[0].getPayAccNo()
									+ " where vch_no="
									+ array_trfvoucherobject[i].getVoucherNo()
									+ " and ac_type='"
									+ array_trfvoucherobject[i].getAccType()
									+ "' and ac_no="
									+ array_trfvoucherobject[i].getAccNo()
									+ " and vch_date='"
									+ array_trfvoucherobject[i]
											.getVoucherDate() + "'");
				}
				stmt_voucher.executeBatch();
			}

			/**
			 * Update deposit transaction for paid date
			 */
			for (int i = 0; i < array_trfvoucherobject.length; i++) {
				stmt_voucher
						.addBatch("update DepositTransaction set paid_date='"
								+ getSysDate() + "' where ac_type='"
								+ array_trfvoucherobject[0].getAccType()
								+ "' and ac_no="
								+ array_trfvoucherobject[0].getAccNo()
								+ " and ref_no="
								+ array_trfvoucherobject[0].getVoucherNo());
			}
			stmt_voucher.executeBatch();

			System.out
					.println("Returning from storeDepositTransferVoucher()...");

		} catch (SQLException exception) {
			exception.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordNotInsertedException();
		} catch (Exception e2) {
			e2.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordNotInsertedException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			commonLocal = null;
		}
		return int_return_value;
	}

	public DepositTransactionObject[] getPrintRecords(String acctype,
			int accno, int type, String trn_date)
			throws RecordsNotFoundException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		DepositTransactionObject array_deposittransactionobject[] = null;
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			if (type == 1)
				rs = stmt
						.executeQuery("select dt.*,lst_pr_seq from DepositTransaction dt,DepositMaster dm where dt.ac_type='"
								+ acctype
								+ "' and dt.ac_no="
								+ accno
								+ " and dt.ac_no=dm.ac_no and dt.ac_type=dm.ac_type and dt.trn_seq > dm.lst_trn_seq order by trn_seq");
			else
				rs = stmt
						.executeQuery("select dt.*,lst_pr_seq from DepositTransaction dt,DepositMaster dm where dt.ac_type='"
								+ acctype
								+ "' and dt.ac_no="
								+ accno
								+ " and dt.ac_no=dm.ac_no and dt.ac_type=dm.ac_type and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) >='"
								+ Validations.convertYMD(trn_date)
								+ "' order by trn_seq");
			rs.last();
			if (rs.getRow() == 0)
				throw new RecordsNotFoundException();
			array_deposittransactionobject = new DepositTransactionObject[rs
					.getRow()];
			rs.beforeFirst();
			int i = 0;
			while (rs.next()) {
				array_deposittransactionobject[i] = new DepositTransactionObject();
				array_deposittransactionobject[i].setTranDate(rs
						.getString("trn_date"));
				array_deposittransactionobject[i].setTranSequence(rs
						.getLong("trn_seq"));
				array_deposittransactionobject[i].setTranType(rs
						.getString("trn_type"));
				array_deposittransactionobject[i].setDepositAmt(rs
						.getDouble("dep_amt"));
				array_deposittransactionobject[i].setInterestAmt(rs
						.getDouble("int_amt"));
				array_deposittransactionobject[i].setTranMode(rs
						.getString("trn_mode"));
				array_deposittransactionobject[i].setReferenceNo(rs
						.getInt("lst_pr_seq"));
				array_deposittransactionobject[i].setRDBalance(rs
						.getDouble("rd_bal"));
				array_deposittransactionobject[i].setCumInterest(rs
						.getDouble("cum_int"));
				array_deposittransactionobject[i].setDepositPaid(rs
						.getDouble("dep_paid"));
				array_deposittransactionobject[i].setInterestPaid(rs
						.getDouble("int_paid"));
				array_deposittransactionobject[i].setTranNarration(rs
						.getString("trn_narr"));
				array_deposittransactionobject[i].setCdind(rs
						.getString("cd_ind"));
				array_deposittransactionobject[i].obj_userverifier.setUserId(rs
						.getString("de_user"));
				array_deposittransactionobject[i].obj_userverifier.setVerId(rs
						.getString("ve_user"));
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RecordsNotFoundException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return array_deposittransactionobject;
	}

	public void updatePrintSequence(long lst_trn_seq, int lst_pr_seq,
			String acc_type, int acc_no, int type) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			/**
			 * 1 for print option
			 */
			if (type == 1)
				stmt.executeUpdate("update DepositMaster set lst_trn_seq="
						+ lst_trn_seq + ",lst_pr_seq=" + lst_pr_seq
						+ " where ac_type='" + acc_type + "' and ac_no="
						+ acc_no);
			else
				stmt.executeUpdate("update DepositMaster set lst_pr_seq="
						+ lst_pr_seq + " where ac_type='" + acc_type
						+ "' and ac_no=" + acc_no);
		} catch (SQLException exception) {
			exception.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordNotInsertedException();
		} catch (Exception e2) {
			e2.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordNotInsertedException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public AccountObject[] RetrieveRenewalNotice(String fdate, String tdate,
			int p1, int p2) throws RecordsNotFoundException,
			DateFormatException {
		AccountObject[] dm = null;
		Connection conn = null;
		try {
			conn = getConnection();
			Statement stmt_mian = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			StringTokenizer fd = new StringTokenizer(tdate, "-");
			String y1, d1, m1;
			y1 = fd.nextToken();
			m1 = fd.nextToken();
			d1 = fd.nextToken();
			String date1 = Validations.addDays(d1 + "/" + m1 + "/" + y1, -p1);
			String date2 = Validations.convertYMD(date1);

			String str1 = "concat(right(dm.mat_date,4),'-',mid(dm.mat_date,locate('/',dm.mat_date)+1,(locate('/',dm.mat_date,4)-locate('/',dm.mat_date)-1)),'-',left(dm.mat_date,locate('/',dm.mat_date)-1))";
			String str = "concat(right(dm.dep_date,4),'-',mid(dm.dep_date,locate('/',dm.dep_date)+1,(locate('/',dm.dep_date,4)-locate('/',dm.mat_date)-1)),'-',left(dm.dep_date,locate('/',dm.dep_date)-1))";

			ResultSet rs = stmt_mian
					.executeQuery("select cm.fname,cm.mname,cm.lname,ca.address,ca.city,ca.pin,ca.state,ca.country,dm.mat_date from CustomerAddr ca,DepositMaster dm,CustomerMaster cm where dm.cid=cm.cid and cm.cid=ca.cid and close_ind=0 and dm.dep_days between "
							+ p1
							+ " and "
							+ p2
							+ " and "
							+ str
							+ " <= '"
							+ date2
							+ "' and "
							+ str1
							+ " between  '"
							+ fdate
							+ "' and '" + tdate + "'  ");
			rs.last();
			if (rs.getRow() == 0)
				throw new RecordsNotFoundException();
			dm = new AccountObject[rs.getRow()];
			rs.beforeFirst();

			int i = 0;
			while (rs.next()) {
				dm[i] = new AccountObject();
				dm[i].setAccname(rs.getString(1) + " " + rs.getString(2) + " "
						+ rs.getString(3));
				/*
				 * dm[i].addr.setAddress(rs.getString(4));
				 * dm[i].addr.setCity(rs.getString(5));
				 * dm[i].addr.setPin(rs.getString(6));
				 * dm[i].addr.setState(rs.getString(7));
				 * dm[i].addr.setCountry(rs.getString(8));
				 * dm[i].setLastTrnDate(rs.getString(9));//for Maturity date
				 */
				i++;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dm;
	}

	public boolean updateReceiptNumber(DepositMasterObject[] dm)
			throws RecordNotUpdatedException {
		Connection conn = null;
		Statement stmt;
		boolean updated = false;

		try {
			System.out.println("LENGTH = " + dm.length);
			conn = getConnection();
			stmt = conn.createStatement();

			for (int i = 0; i < dm.length; i++) {
				if (dm[i].isRctUpdate() == true) {
					System.out.println("1.......");
					;
					System.out.println("AC NO = " + dm[i].getAccNo());
					System.out.println("RCT NO = " + dm[i].getReceiptno());
					System.out.println("ACC TYPE = " + dm[i].getAccType());

					if (stmt.executeUpdate("update DepositMaster set rct_no ="
							+ dm[i].getReceiptno()
							+ ",new_rct='T' where ac_no=" + dm[i].getAccNo()
							+ " and ac_type='" + dm[i].getAccType() + "' ") == 0) {
						updated = false;
						throw new RecordNotUpdatedException();

					} else
						updated = true;

				}
			}

		} catch (SQLException ex) {
			sessionContext.setRollbackOnly();
			ex.printStackTrace();
			System.out.println("in catch");
			updated = false;
		} catch (Exception e2) {
			e2.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordNotInsertedException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return updated;
	}

	public DepositMasterObject[] getFDReceiptPrinting(int startno, int endno,
			int type, String acc_type) throws RecordsNotFoundException {
		DepositMasterObject[] dm = null;
		Connection conn = null;
		System.out.println("startno" + startno);
		System.out.println("endno" + endno);
		System.out.println("type" + type);
		System.out.println("acc_type " + acc_type);
		try {
			conn = getConnection();
			Statement stmt_mian = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			Statement stmt_trn = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null, rs_trn = null;
			if (type == 0)// print
			{// rs=stmt_mian.executeQuery("select
				// moduleabbr,dm.ac_type,dm.ac_no,concat_ws('
				// ',fname,mname,lname) as
				// name,address,city,pin,state,country,dm.dep_date,dm.mat_date,dm.dep_amt,dm.mat_amt,dm.nom_no,dm.int_rate,dm.int_mode,dm.new_rct,dm.rct_no,dm.rct_prtd,dm.rct_sign,dm.rcvd_by,dm.dep_days,dm.rcvd_ac_no,dm.de_user,dm.ve_user
				// from Modules,CustomerMaster cm,CustomerAddr ca,DepositMaster
				// dm where dm.ac_type='"+acc_type+"' and dm.ac_type=modulecode
				// and dm.cid=cm.cid and cm.cid=ca.cid and dm.ac_no between
				// "+startno+ " and "+endno+" and rct_prtd='F' and dm.add_type =
				// ca.addr_type and dm.close_ind=0 order by dm.ac_no ");
				// rs=stmt_mian.executeQuery("select
				// mo.moduleabbr,dm.ac_type,dm.ac_no,concat_ws('
				// ',fname,mname,lname) as
				// name,ca.address,ca.city,ca.pin,ca.state,ca.country,dm.dep_date,dm.mat_date,dm.dep_amt,dm.mat_amt,dm.nom_no,dm.int_rate,dm.int_mode,dm.new_rct,dm.rct_no,dm.rct_prtd,dm.rct_sign,dm.rcvd_by,dm.dep_days,dm.rcvd_ac_no,dm.de_user,dm.ve_user,nm.relation
				// from Modules mo,CustomerMaster cm,CustomerAddr
				// ca,DepositMaster dm,NomineeMaster nm where
				// dm.ac_type='"+acc_type+"' and dm.ac_type=mo.modulecode and
				// dm.cid=cm.cid and cm.cid=ca.cid and dm.ac_type=nm.ac_type and
				// dm.ac_no=nm.ac_no and dm.ac_no between " +startno+ " and " +
				// endno+ " and dm.rct_prtd='F' and dm.rct_no=0 and dm.add_type
				// = ca.addr_type order by dm.ac_no desc");// and dm.rct_no=0
				// added by Shiva and dm.close_ind=0 removed
				rs = stmt_mian
						.executeQuery("select mo.moduleabbr,dm.ac_type,dm.ac_no,concat_ws(' ',fname,mname,lname) as name,ca.address,ca.city,ca.pin,ca.state,ca.country,dm.dep_date,dm.mat_date,dm.dep_amt,dm.mat_amt,dm.nom_no,dm.int_rate,dm.int_mode,dm.new_rct,dm.rct_no,dm.rct_prtd,dm.rct_sign,dm.rcvd_by,dm.dep_days,dm.rcvd_ac_no,dm.de_user,dm.ve_user from Modules mo,CustomerMaster cm,CustomerAddr ca,DepositMaster dm where dm.ac_type='"
								+ acc_type
								+ "' and dm.ac_type=mo.modulecode and dm.cid=cm.cid and cm.cid=ca.cid and dm.ac_no between "
								+ startno
								+ " and "
								+ endno
								+ " and dm.rct_prtd='F' and dm.rct_no=0  and dm.add_type = ca.addr_type  order by dm.ac_no desc");// and
				// dm.rct_no=0
				// added
				// by
				// Sanjeet
				// and
				// nominee
				// deyails
				// removed
				System.out.println("1...");
			} else if (type == 1)// reprint
			{
				rs = stmt_mian
						.executeQuery("select moduleabbr,dm.ac_type,dm.ac_no,concat_ws(' ',fname,mname,lname) as name,address,city,pin,state,country,dm.dep_date,dm.mat_date,dm.dep_amt,dm.mat_amt,dm.nom_no,dm.int_rate,dm.int_mode,dm.new_rct,dm.rct_no,dm.rct_prtd,dm.rct_sign,dm.rcvd_by,dm.dep_days,dm.rcvd_ac_no,dm.de_user,dm.ve_user from Modules,CustomerMaster cm,CustomerAddr ca,DepositMaster dm where dm.ac_type='"
								+ acc_type
								+ "' and dm.ac_type=modulecode and dm.cid=cm.cid and cm.cid=ca.cid and dm.ac_no between "
								+ startno
								+ " and "
								+ endno
								+ " and rct_prtd='T' and dm.add_type = ca.addr_type and dm.close_ind=0 order by dm.ac_no ");
				System.out.println("2...");
			} else {
				rs = stmt_mian
						.executeQuery("select moduleabbr,dm.ac_type,dm.ac_no,concat_ws(' ',fname,mname,lname) as name,address,city,pin,state,country,dm.dep_date,dm.mat_date,dm.dep_amt,dm.mat_amt,dm.nom_no,dm.int_rate,dm.int_mode,dm.new_rct,dm.rct_no,dm.rct_prtd,dm.rct_sign,dm.rcvd_by,dm.dep_days,dm.rcvd_ac_no,dm.de_user,dm.ve_user from Modules,CustomerMaster cm,CustomerAddr ca,DepositMaster dm where dm.ac_type='"
								+ acc_type
								+ "' and dm.ac_type=modulecode and dm.cid=cm.cid and cm.cid=ca.cid and rct_prtd='T' and dm.rct_no="
								+ startno
								+ " and dm.add_type = ca.addr_type  order by dm.ac_no ");
				System.out.println("3...");
			}
			rs.last();
			if (rs.getRow() == 0)
				return null;
			// throw new RecordsNotFoundException();
			dm = new DepositMasterObject[rs.getRow()];
			rs.beforeFirst();
			int i = 0;

			int m = 0;

			while (rs.next()) {
				dm[i] = new DepositMasterObject();
				dm[i].setAccType(rs.getString(1));
				dm[i].setAccNo(rs.getInt(3));
				dm[i].setName(rs.getString("name"));
				dm[i].address.setAddress(rs.getString(5));
				dm[i].address.setCity(rs.getString(6));
				dm[i].address.setPin(rs.getString(7));
				dm[i].address.setState(rs.getString(8));
				dm[i].address.setCountry(rs.getString(9));
				dm[i].setDepDate(rs.getString(10));
				dm[i].setMaturityDate(rs.getString(11));
				dm[i].setDepositAmt(rs.getDouble(12));
				dm[i].setMaturityAmt(rs.getDouble(13));
				dm[i].setNomineeRegNo(rs.getInt(14));
				dm[i].setInterestRate(rs.getDouble(15));
				dm[i].setInterestMode(rs.getString(16));
				dm[i].setNewReceipt(rs.getString(17));
				dm[i].setReceiptno(rs.getInt("dm.rct_no"));

				System.out.println("RS = " + rs.getInt("dm.rct_no"));
				System.out.println("OBJECT  = " + dm[i].getReceiptno());
				dm[i].setReceiptPrtd(rs.getString(19));
				dm[i].setReceiptSign(rs.getString(20));
				dm[i].setDepositDays(rs.getInt(22));
				if (rs.getString(23).equals("T"))
					dm[i].setAutoRenwlNo(0);
				else
					dm[i].setAutoRenwlNo(rs.getInt(23));
				dm[i].userverifier.setUserId(rs.getString("dm.de_user"));
				dm[i].userverifier.setVerId(rs.getString("dm.ve_user"));

				if (m == 0) {
					rs_trn = stmt_trn
							.executeQuery("select lst_rct_no from Modules where modulecode='"
									+ acc_type + "' ");
					rs_trn.next();
					dm[i].setLastRctNo(rs_trn.getInt(1));
					m++;
				}
				i++;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			sessionContext.setRollbackOnly();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dm;
	}

	public boolean updateReceiptPrinting(DepositMasterObject[] array_dep_object)
			throws RecordNotUpdatedException {
		Connection conn = null;
		String ac_type = null;
		System.out.println("in update receipt.........."
				+ array_dep_object[0].getAccNo());
		System.out.println("LENGTH = " + array_dep_object.length);
		System.out.println("ACC TYPE =  " + array_dep_object[0].getAccType());
		System.out.println("Receipt number----> "
				+ array_dep_object[0].getReceiptno());
		System.out.println("MOduleCode=====> "
				+ array_dep_object[0].getModulecode());
		try {
			conn = getConnection();
			ResultSet rs = null;

			Statement stmt = conn.createStatement();
			// rs=stmt.executeQuery("Select modulecode from Modules where
			// moduleabbr='"+array_dep_object[0].getAccType()+"' ");
			// rs.next();
			ac_type = array_dep_object[0].getAccType();
			System.out.println("ACC TYPE =  " + ac_type);

			for (int i = 0; i < array_dep_object.length; i++) {
				PreparedStatement prep_stmt = conn
						.prepareStatement("Update DepositMaster set rct_no=?,rct_prtd='T' where ac_no=? and ac_type=? ");

				prep_stmt.setInt(1, array_dep_object[i].getReceiptno());
				prep_stmt.setInt(2, array_dep_object[i].getAccNo());
				prep_stmt.setString(3, ac_type);
				prep_stmt.executeUpdate();
			}

			PreparedStatement prep_stmt_1 = conn
					.prepareStatement("Update Modules set lst_rct_no=? where  modulecode=? ");
			prep_stmt_1.setInt(1, array_dep_object[array_dep_object.length - 1]
					.getReceiptno());
			prep_stmt_1.setString(2, ac_type);
			prep_stmt_1.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			sessionContext.setRollbackOnly();
			return false;
		} catch (Exception e2) {
			e2.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new RecordNotInsertedException();
		} finally {
			try {
				conn.close();

			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

		return true;
	}

	public DepositMasterObject[] RetrievePeriod(String date_to, String acc_type)
			throws RecordsNotFoundException {
		DepositMasterObject[] array_depositmasterobject = null;
		Connection conn = null;
		try {
			conn = getConnection();
			ResultSet rs_main = null;
			Statement stmt_main = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			rs_main = stmt_main
					.executeQuery("select dm.ac_no,dm.dep_date,dm.mat_date,dm.dep_amt,dep_days from DepositMaster dm where concat(right(dm.dep_date,4),'-',mid(dm.dep_date,locate('/',dm.dep_date)+1,(locate('/',dm.dep_date,4)-locate('/',dm.dep_date)-1)),'-',left(dm.dep_date,locate('/',dm.dep_date)-1)) <= '"
							+ date_to
							+ "' and ac_type='"
							+ acc_type
							+ "'and close_ind=0");
			// rs_main=stmt_main.executeQuery("select * from
			// DepositMaster,CustomerMaster where mat_date between
			// '"+txt_date1.getText()"' and '"+txt_date2.getText()"' and
			// DepositMaster.cid=CustomerMaster.cid")
			rs_main.last();
			if (rs_main.getRow() == 0)
				throw new RecordsNotFoundException();
			array_depositmasterobject = new DepositMasterObject[rs_main
					.getRow()];
			rs_main.beforeFirst();
			int i = 0;
			while (rs_main.next()) {
				array_depositmasterobject[i] = new DepositMasterObject();
				array_depositmasterobject[i].setAccNo(rs_main.getInt(1));
				array_depositmasterobject[i].setDepDate(rs_main.getString(2));
				array_depositmasterobject[i].setDepositAmt(rs_main.getFloat(4));
				array_depositmasterobject[i].setDepositDays(rs_main.getInt(5));
				i++;
			}
			rs_main.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RecordsNotFoundException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return array_depositmasterobject;
	}

	public DepositMasterObject[] RetrieveFD(String date_fr, String date_to,
			int open) throws RecordsNotFoundException {
		DepositMasterObject[] array_depositmasterobject = null;
		Connection conn = null;
		try {
			conn = getConnection();
			Statement stmt_main = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet rs_main = null;
			if (open == 0)
				rs_main = stmt_main
						.executeQuery("select dm.ac_no,concat(' ',cm.fname,cm.mname,cm.lname) as name,custtype,sub_category,dm.dep_date,dm.mat_date,dm.dep_days,dm.int_rate,dm.dep_renewal,dm.int_freq,dm.dep_amt,dm.mat_amt,dm.int_mode,dm.rct_no,ca.address,ca.city,ca.pin,ca.state,ca.country from DepositMaster dm,CustomerMaster cm,CustomerAddr ca where concat(right(dm.dep_date,4),'-',mid(dm.dep_date,locate('/',dm.dep_date)+1,(locate('/',dm.dep_date,4)-locate('/',dm.dep_date)-1)),'-',left(dm.dep_date,locate('/',dm.dep_date)-1)) between '"
								+ date_fr
								+ "' and '"
								+ date_to
								+ "' and close_ind=0 and dep_renewal='F' and new_rct='F' and cm.cid=dm.cid and cm.cid=ca.cid and cm.custtype=ca.addr_type");
			else
				// 07/10/2011 close_dt and dep_renewal replaced as close_date
				// and dep_renewed

				rs_main = stmt_main
						.executeQuery("select dm.ac_no,concat(' ',cm.fname,cm.mname,cm.lname) as name,custtype,sub_category,dm.dep_date,dm.mat_date,dm.dep_days,dm.int_rate,dm.dep_renewed,dm.int_freq,dm.dep_amt,dm.mat_amt,dm.int_mode,dm.rct_no,ca.address,ca.city,ca.pin,ca.state,ca.country from DepositMaster dm,CustomerMaster cm,CustomerAddr ca where concat(right(dm.close_date,4),'-',mid(dm.close_date,locate('/',dm.close_date)+1,(locate('/',dm.close_date,4)-locate('/',dm.close_date)-1)),'-',left(dm.close_date,locate('/',dm.close_date)-1)) between '"
								+ date_fr
								+ "' and '"
								+ date_to
								+ "' and close_ind !=0 and cm.cid=dm.cid and cm.cid=ca.cid and cm.custtype=ca.addr_type");

			/*
			 * rs_main = stmt_main.executeQuery(
			 * "select dm.ac_no,concat(' ',cm.fname,cm.mname,cm.lname) as name,custtype,sub_category,dm.dep_date,dm.mat_date,dm.dep_days,dm.int_rate,dm.dep_renewal,dm.int_freq,dm.dep_amt,dm.mat_amt,dm.int_mode,dm.rct_no,ca.address,ca.city,ca.pin,ca.state,ca.country from DepositMaster dm,CustomerMaster cm,CustomerAddr ca where concat(right(dm.close_dt,4),'-',mid(dm.close_dt,locate('/',dm.close_dt)+1,(locate('/',dm.close_dt,4)-locate('/',dm.close_dt)-1)),'-',left(dm.close_dt,locate('/',dm.close_dt)-1)) between '"
			 * + date_fr + "' and '" + date_to +
			 * "' and close_ind !=0 and cm.cid=dm.cid and cm.cid=ca.cid and cm.custtype=ca.addr_type"
			 * );
			 */
			rs_main.last();
			if (rs_main.getRow() == 0)
				throw new RecordsNotFoundException();
			array_depositmasterobject = new DepositMasterObject[rs_main
					.getRow()];
			rs_main.beforeFirst();
			int i = 0;
			while (rs_main.next()) {
				array_depositmasterobject[i] = new DepositMasterObject();
				array_depositmasterobject[i].setAccNo(rs_main
						.getInt("dm.ac_no"));
				array_depositmasterobject[i].setName(rs_main.getString("name"));
				array_depositmasterobject[i].setDPType(rs_main
						.getInt("custtype"));
				array_depositmasterobject[i].setCategory(rs_main
						.getInt("sub_category"));
				array_depositmasterobject[i].setDepDate(rs_main
						.getString("dep_date"));
				array_depositmasterobject[i].setMaturityDate(rs_main
						.getString("mat_date"));
				array_depositmasterobject[i].setDepositDays(rs_main
						.getInt("dep_days"));
				array_depositmasterobject[i].setInterestRate(rs_main
						.getFloat("int_rate"));
				array_depositmasterobject[i].setDepositRenew(rs_main
						.getString("dep_renewal"));
				array_depositmasterobject[i].setInterestFrq(rs_main
						.getString("int_freq"));
				array_depositmasterobject[i].setDepositAmt(rs_main
						.getFloat("dep_amt"));
				array_depositmasterobject[i].setMaturityAmt(rs_main
						.getFloat("mat_amt"));
				array_depositmasterobject[i].setInterestMode(rs_main
						.getString("int_mode"));
				array_depositmasterobject[i].setReceiptno(rs_main
						.getInt("rct_no"));
				array_depositmasterobject[i].address.setAddress(rs_main
						.getString("ca.address"));
				array_depositmasterobject[i].address.setCity(rs_main
						.getString("ca.city"));
				array_depositmasterobject[i].address.setPin(rs_main
						.getString("ca.pin"));
				array_depositmasterobject[i].address.setState(rs_main
						.getString("ca.state"));
				array_depositmasterobject[i].address.setCountry(rs_main
						.getString("ca.country"));
				i++;
			}
			rs_main.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RecordsNotFoundException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return array_depositmasterobject;
	}

	public DepositReportObject[] RetrieveRenewalNotice(String date1,
			String date2, String ac_type, int type, int flag, String Query)
			throws RecordsNotFoundException {
		DepositReportObject[] array_obj_user = null;
		int i = 0;
		Connection conn = null;
		try {
			conn = getConnection();
			ResultSet rs_mst = null;
			String qry1 = null;
			// Code Changed @Sanjeet

			// String qry1="Select distinct
			// ac_type,ac_no,dep_amt,mat_amt,mat_date,dep_yrs,dep_mths,dep_days,add_type,cid,dep_date
			// from DepositMaster where close_ind=0 and
			// concat(right(mat_date,4),'-',mid(mat_date,locate('/',mat_date)+1,(locate('/',mat_date,4)-locate('/',mat_date)-1)),'-',left(mat_date,locate('/',mat_date)-1))=date_add(date_format(sysdate(),'%Y-%m-%d'),interval
			// 30 day) ";
			if (type == 0) {
				if (flag == 0) {
					qry1 = "select * from DepositMaster where concat(right(mat_date,4),'-',mid(mat_date,locate('/',mat_date)+1,(locate('/',mat_date,4)-locate('/',mat_date)-1)),'-',left(mat_date,locate('/',mat_date)-1)) between '"
							+ date1 + "' and '" + date2 + "' order by ac_no";
				} else {
					qry1 = "select * from DepositMaster where concat(right(mat_date,4),'-',mid(mat_date,locate('/',mat_date)+1,(locate('/',mat_date,4)-locate('/',mat_date)-1)),'-',left(mat_date,locate('/',mat_date)-1)) between '"
							+ date1
							+ "' and '"
							+ date2
							+ "' and ("
							+ Query
							+ ") order by ac_no";
				}
			} else if (type == 1) {
				if (flag == 0) {
					qry1 = "select * from DepositMaster where ac_type='"
							+ ac_type
							+ "' and concat(right(mat_date,4),'-',mid(mat_date,locate('/',mat_date)+1,(locate('/',mat_date,4)-locate('/',mat_date)-1)),'-',left(mat_date,locate('/',mat_date)-1)) between '"
							+ date1 + "' and '" + date2 + "' order by ac_no";
				} else {
					qry1 = "select * from DepositMaster where ac_type='"
							+ ac_type
							+ "' and concat(right(mat_date,4),'-',mid(mat_date,locate('/',mat_date)+1,(locate('/',mat_date,4)-locate('/',mat_date)-1)),'-',left(mat_date,locate('/',mat_date)-1)) between '"
							+ date1 + "' and '" + date2 + "' and (" + Query
							+ ") order by ac_no";
				}
			}
			//
			Statement stmt = conn.createStatement();
			rs_mst = stmt.executeQuery(qry1);
			rs_mst.last();
			if (rs_mst.getRow() == 0)
				throw new RecordsNotFoundException();
			array_obj_user = new DepositReportObject[rs_mst.getRow()];
			rs_mst.beforeFirst();
			while (rs_mst.next()) {
				array_obj_user[i] = new DepositReportObject();
				array_obj_user[i].setAcctype(rs_mst.getString("ac_type"));
				array_obj_user[i].setAccno(rs_mst.getInt("ac_no"));
				array_obj_user[i].setDepAmt(rs_mst.getDouble("dep_amt"));
				array_obj_user[i].setMatAmt(rs_mst.getDouble("mat_amt"));
				array_obj_user[i].setMatDate(rs_mst.getString("mat_date"));
				// array_obj_user[i].setPeriod( rs_mst.getString("dep_yrs")+"
				// Years, "+ rs_mst.getString("dep_mths")+" Months, "+
				// rs_mst.getString("dep_days")+" Days");
				array_obj_user[i].setPeriod(rs_mst.getString("dep_days"));
				array_obj_user[i].setAddType(rs_mst.getInt("add_type"));
				array_obj_user[i].setCid(String.valueOf(rs_mst.getInt("cid")));
				array_obj_user[i].setDepDate(rs_mst.getString("dep_date"));
				i++;
			}
			ResultSet rs2 = null;
			for (i = 0; i < array_obj_user.length; i++) {
				Statement s2 = conn.createStatement();
				String qry2 = "Select moduleabbr from Modules where modulecode="
						+ array_obj_user[i].getAcctype();
				rs2 = s2.executeQuery(qry2);
				if (rs2.next())
					array_obj_user[i].setAcctype(rs2.getString("moduleabbr"));
			}
			ResultSet rs3 = null;
			Statement s3 = conn.createStatement();
			for (i = 0; i < array_obj_user.length; i++) {
				String qry3 = "Select concat_ws(' ',fname,mname,lname) as name from CustomerMaster cm,DepositMaster dm where ac_no="
						+ array_obj_user[i].getAccno() + " and cm.cid=dm.cid";
				rs3 = s3.executeQuery(qry3);
				if (rs3.next())
					array_obj_user[i].setName(rs3.getString("name"));
			}
			ResultSet rs4 = null;
			Statement s4 = conn.createStatement();
			for (i = 0; i < array_obj_user.length; i++) {
				String qry4 = "Select address,city,state,pin from CustomerAddr where cid="
						+ array_obj_user[i].getCid()
						+ " and addr_type="
						+ array_obj_user[i].getAddType() + "";
				rs4 = s4.executeQuery(qry4);
				if (rs4.next()) {
					array_obj_user[i].setAddress(rs4.getString("address"));
					array_obj_user[i].setCity(rs4.getString("city"));
					array_obj_user[i].setState(rs4.getString("state"));
					array_obj_user[i].setPin(rs4.getString("pin"));
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new RecordsNotFoundException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return array_obj_user;
	}

	public DepositMasterObject[] getOpenAccounts(String string_account_type,
			String string_from_date, String string_to_date, int type,
			String query) throws RecordsNotFoundException {
		DepositMasterObject[] array_obj_open = null;
		String fdate = Validations.convertYMD(string_from_date);
		String tdate = Validations.convertYMD(string_to_date);

		int j = 0;

		System.out.println("fdate in bean====" + fdate);
		System.out.println("todate in bean===" + tdate);

		System.out.println("entering inside open accounts method");

		Connection conn = null;
		try {
			conn = getConnection();
			Statement s1 = conn.createStatement();
			ResultSet rs1;
			if (type == 0) {
				rs1 = s1
						.executeQuery("Select distinct concat(IFNULL(fname,' '),' ',IFNULL(mname,' ' ),' ',IFNULL(lname,' ')) as name,ac_no,dep_date,mat_date,dep_days,dm.int_rate,auto_renewal,int_freq,dep_amt,mat_amt,int_mode,rct_no,close_ind,close_date,cm.custtype,ac.subcatdesc, ca.*  from DepositMaster dm, AccountSubCategory ac, CustomerAddr ca, CustomerMaster cm, Modules m where concat(right(dep_date,4),'-',mid(dep_date,locate('/',dep_date)+1,(locate('/',dep_date,4)-locate('/',dep_date)-1)),'-',left(dep_date,locate('/',dep_date)-1)) between '"
								+ fdate
								+ "' and '"
								+ tdate
								+ "' and dm.cid=cm.cid and cm.custtype=ac.catcode and cm.sub_category=ac.subcatcode and ca.cid = cm.cid and dm.add_type=ca.addr_type and m.modulecode=dm.ac_type and  dm.ac_type='"
								+ string_account_type + "' order by dm.ac_no");
			} else {
				rs1 = s1
						.executeQuery("Select distinct concat(IFNULL(fname,' '),' ',IFNULL(mname,' ' ),' ',IFNULL(lname,' ')) as name,ac_no,dep_date,mat_date,dep_days,dm.int_rate,auto_renewal,int_freq,dep_amt,mat_amt,int_mode,rct_no,close_ind,close_date,cm.custtype,ac.subcatdesc, ca.*  from DepositMaster dm, AccountSubCategory ac, CustomerAddr ca, CustomerMaster cm, Modules m where concat(right(dep_date,4),'-',mid(dep_date,locate('/',dep_date)+1,(locate('/',dep_date,4)-locate('/',dep_date)-1)),'-',left(dep_date,locate('/',dep_date)-1)) between '"
								+ fdate
								+ "' and '"
								+ tdate
								+ "' and dm.cid=cm.cid and cm.custtype=ac.catcode and cm.sub_category=ac.subcatcode and ca.cid = cm.cid and dm.add_type=ca.addr_type and m.modulecode=dm.ac_type and  dm.ac_type='"
								+ string_account_type
								+ "' and ("
								+ query
								+ ") order by dm.ac_no");
			}
			// rs1=s1.executeQuery(qry1);
			rs1.last();
			if (rs1.getRow() == 0)
				return null;
			array_obj_open = new DepositMasterObject[rs1.getRow()];
			rs1.beforeFirst();
			while (rs1.next()) {
				array_obj_open[j] = new DepositMasterObject();
				array_obj_open[j].setName(rs1.getString("name"));
				array_obj_open[j].setAccNo(rs1.getInt("ac_no"));
				array_obj_open[j].address.setAddress(rs1
						.getString("ca.address"));
				array_obj_open[j].address.setCity(rs1.getString("ca.city"));
				array_obj_open[j].address.setState(rs1.getString("ca.state"));
				array_obj_open[j].address.setPin(rs1.getString("ca.pin"));
				array_obj_open[j]
						.setDepositType(rs1.getString("ac.subcatdesc"));
				array_obj_open[j].setCategory(rs1.getInt("cm.custtype"));
				array_obj_open[j].setDepDate(rs1.getString("dep_date"));
				array_obj_open[j].setMaturityDate(rs1.getString("mat_date"));
				array_obj_open[j].setDepositDays(rs1.getInt("dep_days"));
				array_obj_open[j].setInterestRate(rs1.getInt("dm.int_rate"));
				array_obj_open[j].setAutoRenewal(rs1.getString("auto_renewal"));
				array_obj_open[j].setInterestFrq(rs1.getString("int_freq"));
				array_obj_open[j].setDepositAmt(rs1.getDouble("dep_amt"));
				array_obj_open[j].setMaturityAmt(rs1.getDouble("mat_amt"));
				array_obj_open[j].setInterestMode(rs1.getString("int_mode"));
				array_obj_open[j].setReceiptno(rs1.getInt("rct_no"));
				array_obj_open[j].setCloseInd(rs1.getInt("close_ind"));
				array_obj_open[j].setClosedt(rs1.getString("close_date"));
				j++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RecordsNotFoundException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return array_obj_open;

	}

	public DepositMasterObject[] getClosedAccounts(String string_account_type,
			String string_from_date, String string_to_date, int type,
			String query) throws RecordsNotFoundException {
		DepositMasterObject[] array_obj_open = null;
		String fdate = Validations.convertYMD(string_from_date);
		String tdate = Validations.convertYMD(string_to_date);
		int j = 0;
		Connection conn = null;
		try {
			conn = getConnection();
			Statement s1 = conn.createStatement();
			ResultSet rs1;
			if (type == 0) {
				rs1 = s1
						.executeQuery("Select distinct concat(IFNULL(fname,' '),' ',IFNULL(mname,' ' ),' ',IFNULL(lname,' ')) as name,ac_no,dep_date,mat_date,dep_days,dm.int_rate,auto_renewal,int_freq,dep_amt,mat_amt,int_mode,rct_no,close_ind,close_date,cm.custtype, ac.subcatdesc, ca.*  from DepositMaster dm, AccountSubCategory ac, CustomerAddr ca, CustomerMaster cm, Modules m where concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1,(locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,locate('/',close_date)-1)) between '"
								+ fdate
								+ "' and '"
								+ tdate
								+ "' and dm.cid=cm.cid and cm.custtype=ac.catcode and cm.sub_category=ac.subcatcode and ca.cid = cm.cid and dm.add_type=ca.addr_type and m.modulecode=dm.ac_type and  dm.ac_type='"
								+ string_account_type + "' order by dm.ac_no  ");
			} else {
				rs1 = s1
						.executeQuery("Select distinct concat(IFNULL(fname,' '),' ',IFNULL(mname,' ' ),' ',IFNULL(lname,' ')) as name,ac_no,dep_date,mat_date,dep_days,dm.int_rate,auto_renewal,int_freq,dep_amt,mat_amt,int_mode,rct_no,close_ind,close_date,cm.custtype, ac.subcatdesc, ca.*  from DepositMaster dm, AccountSubCategory ac, CustomerAddr ca, CustomerMaster cm, Modules m where concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1,(locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,locate('/',close_date)-1)) between '"
								+ fdate
								+ "' and '"
								+ tdate
								+ "' and dm.cid=cm.cid and cm.custtype=ac.catcode and cm.sub_category=ac.subcatcode and ca.cid = cm.cid and dm.add_type=ca.addr_type and m.modulecode=dm.ac_type and  dm.ac_type='"
								+ string_account_type
								+ "' and ("
								+ query
								+ ") order by dm.ac_no ");
			}
			// rs1=s1.executeQuery(qry1);
			rs1.last();
			if (rs1.getRow() == 0)
				throw new RecordsNotFoundException();
			array_obj_open = new DepositMasterObject[rs1.getRow()];
			rs1.beforeFirst();
			while (rs1.next()) {
				array_obj_open[j] = new DepositMasterObject();
				array_obj_open[j].setName(rs1.getString("name"));
				array_obj_open[j].setAccNo(rs1.getInt("ac_no"));
				array_obj_open[j].address.setAddress(rs1
						.getString("ca.address"));
				array_obj_open[j].address.setCity(rs1.getString("ca.city"));
				array_obj_open[j].address.setState(rs1.getString("ca.state"));
				array_obj_open[j].address.setPin(rs1.getString("ca.pin"));
				array_obj_open[j]
						.setDepositType(rs1.getString("ac.subcatdesc"));
				array_obj_open[j].setCategory(rs1.getInt("cm.custtype"));
				array_obj_open[j].setDepDate(rs1.getString("dep_date"));
				array_obj_open[j].setMaturityDate(rs1.getString("mat_date"));
				array_obj_open[j].setDepositDays(rs1.getInt("dep_days"));
				array_obj_open[j].setInterestRate(rs1.getInt("dm.int_rate"));
				array_obj_open[j].setAutoRenewal(rs1.getString("auto_renewal"));
				array_obj_open[j].setInterestFrq(rs1.getString("int_freq"));
				array_obj_open[j].setDepositAmt(rs1.getDouble("dep_amt"));
				array_obj_open[j].setMaturityAmt(rs1.getDouble("mat_amt"));
				array_obj_open[j].setInterestMode(rs1.getString("int_mode"));
				array_obj_open[j].setReceiptno(rs1.getInt("rct_no"));
				array_obj_open[j].setCloseInd(rs1.getInt("close_ind"));
				array_obj_open[j].setClosedt(rs1.getString("close_date"));
				j++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new RecordsNotFoundException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return array_obj_open;
	}

	// added by geetha for cbs reports for both open and closed accs

	public DepositMasterObject[] getAllAccounts(String string_account_type,
			String string_from_date, String string_to_date, int type)
			throws RecordsNotFoundException {
		DepositMasterObject[] array_open_close = null;
		String fdate = Validations.convertYMD(string_from_date);
		String tdate = Validations.convertYMD(string_to_date);

		System.out.println("IN bean");
		System.out.println(string_account_type);
		System.out.println(string_from_date);
		System.out.println(string_to_date);
		System.out.println(fdate);
		System.out.println(tdate);
		System.out.println(type);

		int j = 0;
		Connection conn = null;
		try {
			conn = getConnection();
			Statement s1 = conn.createStatement();

			Statement s2 = conn.createStatement();

			ResultSet rs1, rs2;
			/*
			 * if(type==0){ rs1=s1.executeQuery("Select distinct
			 * concat(IFNULL(fname,' '),' ',IFNULL(mname,' ' ),'
			 * ',IFNULL(lname,' ')) as
			 * name,ac_no,dep_date,mat_date,dep_days,dm.int_rate
			 * ,auto_renewal,int_freq
			 * ,dep_amt,mat_amt,int_mode,rct_no,close_ind,close_date
			 * ,cm.custtype, ac.subcatdesc, ca.* from DepositMaster dm,
			 * AccountSubCategory ac, CustomerAddr ca, CustomerMaster cm,
			 * Modules m where
			 * concat(right(close_date,4),'-',mid(close_date,locate
			 * ('/',close_date
			 * )+1,(locate('/',close_date,4)-locate('/',close_date
			 * )-1)),'-',left(close_date,locate('/',close_date)-1)) between
			 * '"+fdate+"' and '"+tdate+"' and dm.cid=cm.cid and
			 * cm.custtype=ac.catcode and cm.sub_category=ac.subcatcode and
			 * ca.cid = cm.cid and dm.add_type=ca.addr_type and
			 * m.modulecode=dm.ac_type and dm.ac_type='"+string_account_type+"'
			 * order by dm.ac_no "); } else{
			 * 
			 * rs1=s1.executeQuery("Select distinct concat(IFNULL(fname,' '),'
			 * ',IFNULL(mname,' ' ),' ',IFNULL(lname,' ')) as
			 * name,ac_no,dep_date
			 * ,mat_date,dep_days,dm.int_rate,auto_renewal,int_freq
			 * ,dep_amt,mat_amt
			 * ,int_mode,rct_no,close_ind,close_date,cm.custtype, ac.subcatdesc,
			 * ca.* from DepositMaster dm, AccountSubCategory ac, CustomerAddr
			 * ca, CustomerMaster cm, Modules m where
			 * concat(right(close_date,4),
			 * '-',mid(close_date,locate('/',close_date
			 * )+1,(locate('/',close_date
			 * ,4)-locate('/',close_date)-1)),'-',left(
			 * close_date,locate('/',close_date)-1)) between '"+fdate+"' and
			 * '"+tdate+"' and dm.cid=cm.cid and cm.custtype=ac.catcode and
			 * cm.sub_category=ac.subcatcode and ca.cid = cm.cid and
			 * dm.add_type=ca.addr_type and m.modulecode=dm.ac_type and
			 * dm.ac_type='"+string_account_type+"' order by dm.ac_no "); }
			 * //rs1=s1.executeQuery(qry1);
			 * 
			 * if(type==0){
			 * 
			 * rs2=s1.executeQuery("Select distinct concat(IFNULL(fname,' '),'
			 * ',IFNULL(mname,' ' ),' ',IFNULL(lname,' ')) as
			 * name,ac_no,dep_date
			 * ,mat_date,dep_days,dm.int_rate,auto_renewal,int_freq
			 * ,dep_amt,mat_amt
			 * ,int_mode,rct_no,close_ind,close_date,cm.custtype,ac.subcatdesc,
			 * ca.* from DepositMaster dm, AccountSubCategory ac, CustomerAddr
			 * ca, CustomerMaster cm, Modules m where
			 * concat(right(dep_date,4),'-'
			 * ,mid(dep_date,locate('/',dep_date)+1,(
			 * locate('/',dep_date,4)-locate
			 * ('/',dep_date)-1)),'-',left(dep_date,locate('/',dep_date)-1))
			 * between '"+fdate+"' and '"+tdate+"' and dm.cid=cm.cid and
			 * cm.custtype=ac.catcode and cm.sub_category=ac.subcatcode and
			 * ca.cid = cm.cid and dm.add_type=ca.addr_type and
			 * m.modulecode=dm.ac_type and dm.ac_type='"+string_account_type+"'
			 * order by dm.ac_no"); } else{ rs2=s1.executeQuery("Select distinct
			 * concat(IFNULL(fname,' '),' ',IFNULL(mname,' ' ),'
			 * ',IFNULL(lname,' ')) as
			 * name,ac_no,dep_date,mat_date,dep_days,dm.int_rate
			 * ,auto_renewal,int_freq
			 * ,dep_amt,mat_amt,int_mode,rct_no,close_ind,close_date
			 * ,cm.custtype,ac.subcatdesc, ca.* from DepositMaster dm,
			 * AccountSubCategory ac, CustomerAddr ca, CustomerMaster cm,
			 * Modules m where
			 * concat(right(dep_date,4),'-',mid(dep_date,locate('/'
			 * ,dep_date)+1,(
			 * locate('/',dep_date,4)-locate('/',dep_date)-1)),'-',
			 * left(dep_date,locate('/',dep_date)-1)) between '"+fdate+"' and
			 * '"+tdate+"' and dm.cid=cm.cid and cm.custtype=ac.catcode and
			 * cm.sub_category=ac.subcatcode and ca.cid = cm.cid and
			 * dm.add_type=ca.addr_type and m.modulecode=dm.ac_type and
			 * dm.ac_type='"+string_account_type+"' order by dm.ac_no"); }
			 */
			/*
			 * rs1.last(); rs2.last();
			 */
			if (type == 2) {
				System.out.println(" inside type======2");
				rs1 = s1
						.executeQuery("Select distinct concat(IFNULL(fname,' '),' ',IFNULL(mname,' ' ),' ',IFNULL(lname,' ')) as name,ac_no,dep_date,mat_date,dep_days,dm.int_rate,auto_renewal,int_freq,dep_amt,mat_amt,int_mode,rct_no,close_ind,close_date,cm.custtype, ac.subcatdesc, ca.*  from DepositMaster dm, AccountSubCategory ac, CustomerAddr ca, CustomerMaster cm, Modules m where concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1,(locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,locate('/',close_date)-1)) between '"
								+ fdate
								+ "' and '"
								+ tdate
								+ "' and dm.cid=cm.cid and cm.custtype=ac.catcode and cm.sub_category=ac.subcatcode and ca.cid = cm.cid and dm.add_type=ca.addr_type and m.modulecode=dm.ac_type and  dm.ac_type='"
								+ string_account_type + "' order by dm.ac_no  ");
				System.out.println("&&&&&&&&&&&&&&&&&&&& after rs1&&&&&&&&&&&&"
						+ rs1);
				rs2 = s2
						.executeQuery("Select distinct concat(IFNULL(fname,' '),' ',IFNULL(mname,' ' ),' ',IFNULL(lname,' ')) as name,ac_no,dep_date,mat_date,dep_days,dm.int_rate,auto_renewal,int_freq,dep_amt,mat_amt,int_mode,rct_no,close_ind,close_date,cm.custtype,ac.subcatdesc, ca.*  from DepositMaster dm, AccountSubCategory ac, CustomerAddr ca, CustomerMaster cm, Modules m where concat(right(dep_date,4),'-',mid(dep_date,locate('/',dep_date)+1,(locate('/',dep_date,4)-locate('/',dep_date)-1)),'-',left(dep_date,locate('/',dep_date)-1)) between '"
								+ fdate
								+ "' and '"
								+ tdate
								+ "' and dm.cid=cm.cid and cm.custtype=ac.catcode and cm.sub_category=ac.subcatcode and ca.cid = cm.cid and dm.add_type=ca.addr_type and m.modulecode=dm.ac_type and  dm.ac_type='"
								+ string_account_type + "' order by dm.ac_no");

				System.out.println("&&&&&&&&&&&&&&&&&&&& after rs2&&&&&&&&&&&&"+ rs2);
				rs1.last();
				System.out
						.println("&&&&&&&&&&&&&&&&&&&& after rs1 last&&&&&&&&&&&&");
				rs2.last();
				System.out
						.println("&&&&&&&&&&&&&&&&&&&& after rs2 last&&&&&&&&&&&&");

				if (rs1.getRow() == 0)
					throw new RecordsNotFoundException();

				array_open_close = new DepositMasterObject[rs1.getRow()
						+ rs2.getRow()];
				rs1.beforeFirst();

				while (rs1.next()) {
					array_open_close[j] = new DepositMasterObject();
					array_open_close[j].setName(rs1.getString("name"));
					array_open_close[j].setAccNo(rs1.getInt("ac_no"));
					array_open_close[j].address.setAddress(rs1
							.getString("ca.address"));
					array_open_close[j].address.setCity(rs1
							.getString("ca.city"));
					array_open_close[j].address.setState(rs1
							.getString("ca.state"));
					array_open_close[j].address.setPin(rs1.getString("ca.pin"));
					array_open_close[j].setDepositType(rs1
							.getString("ac.subcatdesc"));
					array_open_close[j].setCategory(rs1.getInt("cm.custtype"));
					array_open_close[j].setDepDate(rs1.getString("dep_date"));
					array_open_close[j].setMaturityDate(rs1
							.getString("mat_date"));
					array_open_close[j].setDepositDays(rs1.getInt("dep_days"));
					array_open_close[j].setInterestRate(rs1
							.getInt("dm.int_rate"));
					array_open_close[j].setAutoRenewal(rs1
							.getString("auto_renewal"));
					array_open_close[j].setInterestFrq(rs1
							.getString("int_freq"));
					array_open_close[j].setDepositAmt(rs1.getDouble("dep_amt"));
					array_open_close[j]
							.setMaturityAmt(rs1.getDouble("mat_amt"));
					array_open_close[j].setInterestMode(rs1
							.getString("int_mode"));
					array_open_close[j].setReceiptno(rs1.getInt("rct_no"));
					array_open_close[j].setCloseInd(rs1.getInt("close_ind"));
					array_open_close[j].setClosedt(rs1.getString("close_date"));
					j++;
				}

				if (type == 2) {

					System.out.println("geeetha nayak here....");
					// array_open_close=new
					// DepositMasterObject[rs1.getRow()+rs2.getRow()];
					rs2.beforeFirst();

					System.out
							.println("^^^^^^^^^^^^^^^^^^^in second loop setting values^^^^^^^^^^^^^");

					while (rs2.next()) {
						array_open_close[j] = new DepositMasterObject();
						array_open_close[j].setName(rs2.getString("name"));
						array_open_close[j].setAccNo(rs2.getInt("ac_no"));
						array_open_close[j].address.setAddress(rs2
								.getString("ca.address"));
						array_open_close[j].address.setCity(rs2
								.getString("ca.city"));
						array_open_close[j].address.setState(rs2
								.getString("ca.state"));
						array_open_close[j].address.setPin(rs2
								.getString("ca.pin"));
						array_open_close[j].setDepositType(rs2
								.getString("ac.subcatdesc"));
						array_open_close[j].setCategory(rs2
								.getInt("cm.custtype"));
						array_open_close[j].setDepDate(rs2
								.getString("dep_date"));
						array_open_close[j].setMaturityDate(rs2
								.getString("mat_date"));
						array_open_close[j].setDepositDays(rs2
								.getInt("dep_days"));
						array_open_close[j].setInterestRate(rs2
								.getInt("dm.int_rate"));
						array_open_close[j].setAutoRenewal(rs2
								.getString("auto_renewal"));
						array_open_close[j].setInterestFrq(rs2
								.getString("int_freq"));
						array_open_close[j].setDepositAmt(rs2
								.getDouble("dep_amt"));
						array_open_close[j].setMaturityAmt(rs2
								.getDouble("mat_amt"));
						array_open_close[j].setInterestMode(rs2
								.getString("int_mode"));
						array_open_close[j].setReceiptno(rs2.getInt("rct_no"));
						array_open_close[j]
								.setCloseInd(rs2.getInt("close_ind"));
						array_open_close[j].setClosedt(rs2
								.getString("close_date"));
						j++;
					}

				}

			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new RecordsNotFoundException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return array_open_close;
	}

	public int getNomineePercent(int accno, String ac_type)
			throws RemoteException {
		int per = 0;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs, rs1 = null;
		System.out.println("The length of nominee is " + accno);
		try {
			con = getConnection();
			stmt = con.createStatement();
			System.out.println("Before query");
			rs = stmt
					.executeQuery("select sum(percentage) as per from NomineeMaster where ac_no="
							+ accno + " and ac_type='" + ac_type + "'");
			System.out.println("afetr query");
			while (rs.next()) {
				per = rs.getInt("per");
				System.out.println("i want percentage " + per);
			}
			return per;
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			try {
				con.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return per;
	}

	public int SetNomineeDetails(NomineeObject[] nom) throws RemoteException {
		int id = 0;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs, rs1 = null;
		System.out.println("The length of nominee is " + nom.length);
		try {
			con = getConnection();
			stmt = con.createStatement();
			if (nom != null) {
				Context ctx = getContext();
				Object obj = ctx.lookup("COMMONWEB");
				commonServer.CommonHome home = (commonServer.CommonHome) obj;
				CommonRemote cremote = home.create();
				System.out.println("Executing insert query...............");
				System.out.println("Ac_type----> " + nom[0].getAccType());
				System.out.println("Ac_type----> " + nom[0].getAccNo());
				System.out.println("Ac_type----> " + nom[0].getFromDate());
				System.out.println("Ac_type----> " + nom[0].getCustomerId());

				id = cremote.storeNominee(nom, nom[0].getAccType(), nom[0]
						.getAccNo(), nom[0].getFromDate());
				System.out.println("iDDDDDDDDDDDDD is" + id);
				System.out.println("After ID----> " + nom[0].getAccType());
				System.out.println("After ID----> " + nom[0].getAccNo());
				System.out.println("customer id" + nom[0].getCustomerId());
				int done = stmt
						.executeUpdate("update DepositMaster set nom_no=" + id
								+ " where ac_no=" + nom[0].getAccNo()
								+ " and ac_type='" + nom[0].getAccType() + "'");
				System.out.println("The done value is " + done);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			try {
				con.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return id;
	}

	public DepositMasterObject[] getPeriodLimit()
			throws RecordsNotFoundException {

		DepositMasterObject[] array_obj_period = null;
		int j = 0;
		Connection conn = null;
		try {
			conn = getConnection();
			Statement s1 = conn.createStatement();
			ResultSet rs1 = null;
			String qry1 = "Select distinct mod_ty,srl_no,lmt_hdg,fr_lmt,to_lmt from PeriodLimit order by mod_ty ";
			rs1 = s1.executeQuery(qry1);
			rs1.last();
			if (rs1.getRow() == 0)
				//throw new RecordsNotFoundException();
				return null;
			array_obj_period = new DepositMasterObject[rs1.getRow()];
			rs1.beforeFirst();
			while (rs1.next()) {
				array_obj_period[j] = new DepositMasterObject();
				array_obj_period[j].setAccType(String.valueOf(rs1
						.getInt("mod_ty")));
				array_obj_period[j].setSrl_no(rs1.getInt("srl_no"));
				array_obj_period[j].setLimit(rs1.getString("lmt_hdg"));
				array_obj_period[j].setFr_lmt(rs1.getInt("fr_lmt"));
				array_obj_period[j].setTo_lmt(rs1.getInt("to_lmt"));
				j++;
			}
			ResultSet rs2 = null;
			for (int i = 0; i < array_obj_period.length; i++) {
				Statement s2 = conn.createStatement();
				String qry2 = "Select moduleabbr,moduledesc from Modules where modulecode="
						+ Integer.parseInt(array_obj_period[i].getAccType());
				rs2 = s2.executeQuery(qry2);
				if (rs2.next()) {
					array_obj_period[i].setModuleabbr(rs2
							.getString("moduleabbr"));
					array_obj_period[i].setModuletype(rs2
							.getString("moduledesc"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RecordsNotFoundException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return array_obj_period;
	}

	public boolean checkIntPaid(String ac_type, int ac_no) {
		ResultSet rs = null;
		Statement stmt = null;
		Connection con = null;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt
					.executeQuery("select * from InterestTransferVoucher where ac_type like '"
							+ ac_type
							+ "' and ac_no = "
							+ ac_no
							+ " and vch_pay_ind = 'F'");
			if (rs.next()) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// file_logger.info(e.getMessage());
			return false;
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return true;

	}

	public DepositReportObject[] getLimit(String acc_type, int type)
			throws RecordsNotFoundException {
		System.out.println("acc_type.." + acc_type);
		System.out.println("type ..==>" + type);

		DepositReportObject[] array_obj_limit = null;
		int j = 0;

		Connection conn = null;
		try {
			conn = getConnection();
			Statement s1 = conn.createStatement();
			ResultSet rs1 = null;
			if (type == 1)
				rs1 = s1
						.executeQuery("select srl_no,lmt_hdg from PeriodLimit where mod_ty='"
								+ acc_type + "' order by mod_ty,srl_no");
			else
				rs1 = s1
						.executeQuery("select srl_no,lmt_hdg from PeriodLimit where mod_ty=1003001 order by mod_ty,srl_no");

			rs1.last();
			if (rs1.getRow() == 0)
				throw new RecordsNotFoundException();
			array_obj_limit = new DepositReportObject[rs1.getRow()];
			rs1.beforeFirst();
			while (rs1.next()) {
				array_obj_limit[j] = new DepositReportObject();
				array_obj_limit[j].setScrollno(rs1.getInt("srl_no"));
				array_obj_limit[j].setLmt_hdg(rs1.getString("lmt_hdg"));

				j++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RecordsNotFoundException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return array_obj_limit;
	}

	public DepositReportObject[] getQuantumLimit(String acc_type, int type)
			throws RecordsNotFoundException {
		System.out.println("acc_type.." + acc_type);
		System.out.println("type ..==>" + type);

		DepositReportObject[] array_obj_limit = null;
		int j = 0;

		Connection conn = null;
		try {
			conn = getConnection();
			Statement s1 = conn.createStatement();
			ResultSet rs1 = null;
			if (type == 1)
				rs1 = s1
						.executeQuery("select srl_no,lmt_hdg from QuantumLimit where mod_ty='"
								+ acc_type + "' order by mod_ty,srl_no");
			else
				rs1 = s1
						.executeQuery("select srl_no,lmt_hdg from QuantumLimit where mod_ty=1003001 order by mod_ty,srl_no");

			rs1.last();
			if (rs1.getRow() == 0)
				//throw new RecordsNotFoundException();
				return null;
			array_obj_limit = new DepositReportObject[rs1.getRow()];
			rs1.beforeFirst();
			while (rs1.next()) {
				array_obj_limit[j] = new DepositReportObject();
				array_obj_limit[j].setScrollno(rs1.getInt("srl_no"));
				array_obj_limit[j].setLmt_hdg(rs1.getString("lmt_hdg"));

				j++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RecordsNotFoundException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return array_obj_limit;
	}

	public DepositMasterObject[] getPeriodDetails(int srlno,
			String string_account_type, String string_date, int type)
			throws RecordsNotFoundException {
		double double_dep_amt = 0, double_int_paid = 0, double_dep_paid = 0, rd_bal = 0;
		double double_int_amt = 0;
		String Covdat = null;
		int j = 0, i = 0, cat = 0;
		int int_first_record = 0, int_no_records = 0, int_from_limit = 0, int_to_limit = 0;
		String string_from_date = null, string_to_date = null, ac_type = null, name = null, dep_ty = null, int_upto_date = null;
		DepositMasterObject[] array_obj_customer = null;
		Connection conn = null;
		Statement s1 = null;
		ResultSet rs1 = null;

		try {
			conn = getConnection();
			s1 = conn.createStatement();
			if (type == 1) {
				System.out.println("I am in type 1");
				rs1 = s1
						.executeQuery("Select fr_lmt,to_lmt,lmt_hdg from PeriodLimit where srl_no="
								+ srlno
								+ " and mod_ty='"
								+ string_account_type
								+ "'");
			} else
				rs1 = s1
						.executeQuery("Select fr_lmt,to_lmt,lmt_hdg from PeriodLimit where srl_no="
								+ srlno + " limit 1 ");
			if (rs1.next()) {
				int_from_limit = rs1.getInt("fr_lmt");
				int_to_limit = rs1.getInt("to_lmt");
			}

			System.out.println("int_from_limit===> " + int_from_limit);
			System.out.println("int_to_limit====> " + int_to_limit);
			System.out.println("string_date===> " + string_date);
			if (int_from_limit == 0)
				string_from_date = "01/04/1980";
			else

				Covdat = Validations.convertDMY(string_date);
			System.out.println("Covdat===> " + Covdat);
			System.out.println("Converted date is===> "
					+ Validations.convertDMY(string_date));
			string_from_date = Validations.addDays(Covdat, int_from_limit);

			string_to_date = Validations.addDays(Covdat, int_to_limit);

			System.out.println("SUMANTH string_date===> " + string_date);
			System.out.println("SUMANTH string_from_date==> "
					+ string_from_date);
			System.out.println("SUMANTH string_to_date===> " + string_to_date);
			System.out.println("SUMANTH YMD FROM===> "
					+ Validations.convertYMD(string_from_date));
			System.out.println("SUMANTH YMD TO===> "
					+ Validations.convertYMD(string_to_date));

			s1.executeUpdate("drop table if exists  fd_values");
			if (type == 1)
				s1
						.executeUpdate("create temporary table fd_values select distinct * from DepositMaster where ac_type='"
								+ string_account_type
								+ "' and (concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1, (locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,locate('/',close_date)-1))>'"
								+ string_date
								+ "' or close_date is null) and (concat(right(dep_date,4),'-',mid(dep_date,locate('/',dep_date)+1, (locate('/',dep_date,4)-locate('/',dep_date)-1)),'-',left(dep_date,locate('/',dep_date)-1))<='"
								+ string_date
								+ "') and (concat(right(mat_date,4),'-',mid(mat_date,locate('/',mat_date)+1, (locate('/',mat_date,4)-locate('/',mat_date)-1)),'-',left(mat_date,locate('/',mat_date)-1)) between '"
								+ Validations.convertYMD(string_from_date)
								+ "' and '"
								+ Validations.convertYMD(string_to_date)
								+ "') order by ac_no");
			else
				s1
						.executeUpdate("create temporary table fd_values select distinct * from DepositMaster where (concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1, (locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,locate('/',close_date)-1))>'"
								+ string_date
								+ "' or close_date is null) and (concat(right(dep_date,4),'-',mid(dep_date,locate('/',dep_date)+1, (locate('/',dep_date,4)-locate('/',dep_date)-1)),'-',left(dep_date,locate('/',dep_date)-1))<='"
								+ string_date
								+ "') and (concat(right(mat_date,4),'-',mid(mat_date,locate('/',mat_date)+1, (locate('/',mat_date,4)-locate('/',mat_date)-1)),'-',left(mat_date,locate('/',mat_date)-1)) between '"
								+ Validations.convertYMD(string_from_date)
								+ "' and '"
								+ Validations.convertYMD(string_to_date)
								+ "') order by ac_type,ac_no");

			rs1 = s1
					.executeQuery("select distinct td.ac_no,fd.* from fd_values fd , DepositTransaction as td where fd.ac_type =+ td.ac_type and fd.ac_no =+ td.ac_no and (concat(right(td.trn_date,4),'-',mid(td.trn_date,locate('/',td.trn_date)+1, (locate('/',td.trn_date,4)-locate('/',td.trn_date)-1)),'-',left(td.trn_date,locate('/',td.trn_date)-1)) <='"
							+ string_date + "' )");
			rs1.last();
			if (rs1.getRow() == 0)
				return null;
			array_obj_customer = new DepositMasterObject[rs1.getRow()];
			rs1.beforeFirst();
			while (rs1.next()) {
				array_obj_customer[j] = new DepositMasterObject();
				array_obj_customer[j].setAccNo(rs1.getInt("ac_no"));
				array_obj_customer[j].setFr_lmt(int_from_limit);
				array_obj_customer[j].setTo_lmt(int_to_limit);
				array_obj_customer[j].setDepDate(rs1.getString("dep_date"));
				array_obj_customer[j]
						.setMaturityDate(rs1.getString("mat_date"));
				array_obj_customer[j].setDepositDays(rs1.getInt("dep_days"));
				array_obj_customer[j]
						.setInterestRate(rs1.getDouble("int_rate"));
				array_obj_customer[j].setInterestFrq(rs1.getString("int_freq"));
				array_obj_customer[j].setDepositAmt(rs1.getDouble("dep_amt"));
				array_obj_customer[j].setMaturityAmt(rs1.getDouble("mat_amt"));
				array_obj_customer[j]
						.setInterestMode(rs1.getString("int_mode"));
				array_obj_customer[j].setClosedt(rs1.getString("close_date"));
				array_obj_customer[j].setCustomerId(rs1.getInt("cid"));
				array_obj_customer[j].setAccType(rs1.getString("ac_type"));
				j++;
			}

			Statement s2 = conn.createStatement();
			ResultSet rs2 = null;
			for (i = 0; i < array_obj_customer.length; i++) {
				double_dep_amt = 0;
				double_dep_paid = 0;
				double_int_amt = 0;
				double_int_paid = 0;

				rs2 = s2.executeQuery("Select sum(dep_amt) as dep_amt,sum(int_amt) as int_amt,sum(dep_paid) as dep_paid,sum(int_paid) as int_paid,trn_type,cd_ind from DepositTransaction where ac_no="
								+ array_obj_customer[i].getAccNo()
								+ " and ac_type='"
								+ array_obj_customer[i].getAccType()
								+ "' and  concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<='"
								+ string_date + "' group by trn_type,cd_ind");
				while (rs2.next()) {
					if (rs2.getString("trn_type").equalsIgnoreCase("D")
							&& rs2.getString("cd_ind").equalsIgnoreCase("C"))
						double_dep_amt = rs2.getDouble("dep_amt");
					if (rs2.getString("trn_type").equalsIgnoreCase("I")
							&& rs2.getString("cd_ind").equalsIgnoreCase("C"))
						double_int_amt = rs2.getDouble("int_amt");
					if (rs2.getString("trn_type").equalsIgnoreCase("P")
							&& rs2.getString("cd_ind").equalsIgnoreCase("D")) {
						double_dep_paid = rs2.getDouble("dep_paid");
						double_int_paid = rs2.getDouble("int_paid");
					}
				}
				array_obj_customer[i].setDepositAmtReceived(double_dep_amt
						- double_dep_paid);
				array_obj_customer[i].setInterestAccured(double_int_amt);
				array_obj_customer[i].setInterestPaid(double_int_paid);
				array_obj_customer[i].setRDBalance(array_obj_customer[i]
						.getDepositAmt()
						+ array_obj_customer[i].getInterestAccured()
						- array_obj_customer[i].getInterestPaid());

				rs2 = s2
						.executeQuery("Select concat(IFNULL(fname,' '),' ',IFNULL(mname,' ' ),' ',IFNULL(lname,' ')) as name,cm.custtype,cm.sub_category,moduleabbr from CustomerMaster cm,DepositMaster dm,Modules m where dm.ac_type='"
								+ array_obj_customer[i].getAccType()
								+ "' and dm.ac_no="
								+ array_obj_customer[i].getAccNo()
								+ " and cm.cid=dm.cid and dm.ac_type=m.modulecode");
				if (rs2.next()) {
					name = rs2.getString("name");
					dep_ty = rs2.getString("sub_category");
					cat = rs2.getInt("custtype");
					ac_type = rs2.getString("moduleabbr");
				}
				array_obj_customer[i].setName(name);
				array_obj_customer[i].setDepositType(dep_ty);
				array_obj_customer[i].setCategory(cat);

				rs2 = s2
						.executeQuery("Select trn_seq,int_date from DepositTransaction where ac_no="
								+ array_obj_customer[i].getAccNo()
								+ " and ac_type='"
								+ array_obj_customer[i].getAccType()
								+ "' and trn_type='I' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<='"
								+ string_date
								+ "' order by trn_seq desc limit 1");
				if (rs2.next()) {
					int_upto_date = rs2.getString("int_date");
				}
				array_obj_customer[i].setAccType(ac_type);
				array_obj_customer[i].setInterestUpto(int_upto_date);
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return array_obj_customer;
	}

	/*
	 * public DepositReportObject[] getPeriodDetails(int srlno, String
	 * string_account_type,String string_date,int type) throws
	 * RecordsNotFoundException { DepositReportObject[] array_obj_period=null;
	 * int j=0,int_from_limit=0,int_to_limit=0,ac_no=0; double
	 * double_int_amt=0,double_int_paid=0; String acc_type=null;
	 * System.out.println("Type==>"+type);
	 * System.out.println("Acc.type:"+string_account_type);
	 * 
	 * Connection conn = null; try { conn = getConnection(); Statement
	 * s1=conn.createStatement(); Statement s2=conn.createStatement(); Statement
	 * s3=conn.createStatement(); ResultSet rs; ResultSet rs1,rs2; if(type==1)
	 * rs=s1.executeQuery("Select fr_lmt,to_lmt,lmt_hdg from PeriodLimit where
	 * srl_no="+srlno+" and mod_ty='"+string_account_type+"'"); else
	 * rs=s1.executeQuery("Select fr_lmt,to_lmt,lmt_hdg from PeriodLimit where
	 * srl_no="+srlno+" limit 1 ");
	 * 
	 * if(rs.next()) { int_from_limit=rs.getInt("fr_lmt");
	 * int_to_limit=rs.getInt("to_lmt"); } rs.beforeFirst(); rs.last();
	 * if(type==1) rs2=s3.executeQuery("select ac_no,ac_type from DepositMaster
	 * where ac_type="+string_account_type+" and dep_days between
	 * "+int_from_limit+" and "+int_to_limit+" and
	 * (concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1,
	 * (locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,
	 * locate('/',close_date)-1))> '"+string_date+"' or close_date is null) and
	 * (concat(right(dep_date,4),'-',mid(dep_date,locate('/',dep_date)+1,
	 * (locate
	 * ('/',dep_date,4)-locate('/',dep_date)-1)),'-',left(dep_date,locate(
	 * '/',dep_date)-1))<='"+string_date+"') order by
	 * ac_no,ac_type"); else rs2=s3.executeQuery("select ac_no,ac_type from
	 * DepositMaster where dep_days between "+int_from_limit+" and
	 * "+int_to_limit+" and
	 * (concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1,
	 * (locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,
	 * locate('/',close_date)-1))> '"+string_date+"' or close_date is null) and
	 * (concat(right(dep_date,4),'-',mid(dep_date,locate('/',dep_date)+1,
	 * (locate
	 * ('/',dep_date,4)-locate('/',dep_date)-1)),'-',left(dep_date,locate(
	 * '/',dep_date)-1))<='"+string_date+"') order by ac_no,ac_type");
	 * 
	 * rs2.last(); if(rs2.getRow()==0) throw new RecordsNotFoundException();
	 * array_obj_period=new DepositReportObject[rs2.getRow()];
	 * rs2.beforeFirst(); while(rs2.next()) { array_obj_period[j]=new
	 * DepositReportObject(); array_obj_period[j].setFr_lmt(int_from_limit);
	 * array_obj_period[j].setTo_lmt(int_to_limit);
	 * array_obj_period[j].setAccno(rs2.getInt("ac_no"));
	 * array_obj_period[j].setAcctype(rs2.getString("ac_type"));
	 * 
	 * if(type==1) rs1=s2.executeQuery("select count(*) as total,sum(dep_amt) as
	 * dep_amt from DepositMaster where ac_type='"+string_account_type+"' and
	 * dep_days between "+int_from_limit+" and "+int_to_limit+" and
	 * (concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1,
	 * (locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,
	 * locate('/',close_date)-1))> '"+string_date+"' or close_date is null) and
	 * (concat(right(dep_date,4),'-',mid(dep_date,locate('/',dep_date)+1,
	 * (locate
	 * ('/',dep_date,4)-locate('/',dep_date)-1)),'-',left(dep_date,locate(
	 * '/',dep_date)-1))<='"+string_date+"') order by
	 * ac_no"); else rs1=s2.executeQuery("select count(*) as total,sum(dep_amt)
	 * as dep_amt from DepositMaster where dep_days between "+int_from_limit+"
	 * and "+int_to_limit+" and
	 * (concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1,
	 * (locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,
	 * locate('/',close_date)-1))> '"+string_date+"' or close_date is null) and
	 * (concat(right(dep_date,4),'-',mid(dep_date,locate('/',dep_date)+1,
	 * (locate
	 * ('/',dep_date,4)-locate('/',dep_date)-1)),'-',left(dep_date,locate(
	 * '/',dep_date)-1))<='"+string_date+"') order by ac_no");
	 * 
	 * 
	 * while(rs1.next()) { //array_obj_period[j]=new DepositReportObject();
	 * 
	 * array_obj_period[j].setTotal_ac_no( rs1.getInt("total") );
	 * array_obj_period[j].setDepAmt( rs1.getDouble("dep_amt") ); }
	 * 
	 * 
	 * 
	 * rs1=s2.executeQuery("Select sum(int_amt) as int_amt from
	 * DepositTransaction where ac_no="+array_obj_period[j].getAccno()+" and
	 * ac_type='"+array_obj_period[j].getAcctype()+"' and cd_ind='C' and
	 * trn_type='I' and
	 * concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date
	 * )+1,(locate('/'
	 * ,trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate
	 * ('/',trn_date)-1))<='"+string_date+"'
	 * "); while(rs1.next()) { double_int_amt+=rs1.getDouble("int_amt"); }
	 * rs1=s2.executeQuery("Select sum(int_paid) as int_paid from
	 * DepositTransaction where ac_no="+array_obj_period[j].getAccno()+" and
	 * ac_type='"+array_obj_period[j].getAcctype()+"' and cd_ind='D' and
	 * trn_type='P' and
	 * concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date
	 * )+1,(locate('/'
	 * ,trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate
	 * ('/',trn_date)-1))<='"+string_date+"'
	 * "); while(rs1.next()) { double_int_paid+=rs1.getDouble("int_paid"); }
	 * array_obj_period[j].setDep_int_amt(double_int_amt-double_int_paid);
	 * array_obj_period
	 * [j].setAmount(array_obj_period[j].getDepAmt()+double_int_amt
	 * -double_int_paid); j++; }
	 * 
	 * 
	 * 
	 * }catch(SQLException e){ e.printStackTrace(); throw new
	 * RecordsNotFoundException(); } finally{ try { conn.close(); } catch
	 * (SQLException e) { e.printStackTrace(); } } return array_obj_period;
	 * 
	 * }
	 */

	public DepositMasterObject[] getQuantumDetails(int srlno,
			String string_account_type, String string_date, int type)
			throws RecordsNotFoundException {
		double double_dep_amt = 0, double_int_paid = 0, double_dep_paid = 0, rd_bal = 0;
		double double_int_amt = 0;
		int j = 0, i = 0, cat = 0;
		int int_first_record = 0, int_no_records = 0, int_from_limit = 0, int_to_limit = 0;
		String string_from_date = null, string_to_date = null, ac_type = null, name = null, dep_ty = null, int_upto_date = null;
		DepositMasterObject[] array_obj_customer = null;
		Connection conn = null;
		Statement s1 = null;
		ResultSet rs1 = null;
		System.out.println("srlno in bean==" + srlno);
		System.out.println("date in bean side==" + string_date);
		System.out.println("type in bean side----------------" + type);
		try {
			conn = getConnection();
			s1 = conn.createStatement();

			if (type == 1)
				rs1 = s1
						.executeQuery("Select fr_lmt,to_lmt,lmt_hdg from QuantumLimit where srl_no="
								+ srlno
								+ " and mod_ty='"
								+ string_account_type
								+ "'");
			else
				rs1 = s1
						.executeQuery("Select fr_lmt,to_lmt,lmt_hdg from QuantumLimit where srl_no="
								+ srlno + " limit 1 ");
			if (rs1.next()) {
				int_from_limit = rs1.getInt("fr_lmt");
				int_to_limit = rs1.getInt("to_lmt");
			}
			s1.executeUpdate("drop table if exists  fd_values");
			if (type == 1)
				s1
						.executeUpdate("create temporary table fd_values select distinct * from DepositMaster where ac_type='"
								+ string_account_type
								+ "' and (concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1, (locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,locate('/',close_date)-1))>'"
								+ string_date
								+ "' or close_date is null) and (concat(right(dep_date,4),'-',mid(dep_date,locate('/',dep_date)+1, (locate('/',dep_date,4)-locate('/',dep_date)-1)),'-',left(dep_date,locate('/',dep_date)-1))<='"
								+ string_date
								+ "') and dep_amt between "
								+ int_from_limit
								+ " and "
								+ int_to_limit
								+ " order by ac_no");
			else
				s1
						.executeUpdate("create temporary table fd_values select distinct * from DepositMaster where (concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1, (locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,locate('/',close_date)-1))>'"
								+ string_date
								+ "' or close_date is null) and (concat(right(dep_date,4),'-',mid(dep_date,locate('/',dep_date)+1, (locate('/',dep_date,4)-locate('/',dep_date)-1)),'-',left(dep_date,locate('/',dep_date)-1))<='"
								+ string_date
								+ "') and dep_amt between "
								+ int_from_limit
								+ " and "
								+ int_to_limit
								+ " order by ac_type,ac_no");

			rs1 = s1
					.executeQuery("select distinct td.ac_no,fd.* from fd_values fd , DepositTransaction as td where fd.ac_type =+ td.ac_type and fd.ac_no =+ td.ac_no and (concat(right(td.trn_date,4),'-',mid(td.trn_date,locate('/',td.trn_date)+1, (locate('/',td.trn_date,4)-locate('/',td.trn_date)-1)),'-',left(td.trn_date,locate('/',td.trn_date)-1)) <='"
							+ string_date + "' )");
			rs1.last();
			if (rs1.getRow() == 0) {
				// throw new RecordsNotFoundException();
				return null;
			}
			array_obj_customer = new DepositMasterObject[rs1.getRow()];
			rs1.beforeFirst();
			while (rs1.next()) {
				array_obj_customer[j] = new DepositMasterObject();
				array_obj_customer[j].setAccNo(rs1.getInt("ac_no"));
				array_obj_customer[j].setFr_lmt(int_from_limit);
				array_obj_customer[j].setTo_lmt(int_to_limit);
				array_obj_customer[j].setDepDate(rs1.getString("dep_date"));
				array_obj_customer[j]
						.setMaturityDate(rs1.getString("mat_date"));
				array_obj_customer[j].setDepositDays(rs1.getInt("dep_days"));
				array_obj_customer[j]
						.setInterestRate(rs1.getDouble("int_rate"));
				array_obj_customer[j].setInterestFrq(rs1.getString("int_freq"));
				array_obj_customer[j].setDepositAmt(rs1.getDouble("dep_amt"));
				array_obj_customer[j].setMaturityAmt(rs1.getDouble("mat_amt"));
				array_obj_customer[j]
						.setInterestMode(rs1.getString("int_mode"));
				array_obj_customer[j].setClosedt(rs1.getString("close_date"));
				array_obj_customer[j].setCustomerId(rs1.getInt("cid"));
				array_obj_customer[j].setAccType(rs1.getString("ac_type"));
				j++;
			}

			Statement s2 = conn.createStatement();
			ResultSet rs2 = null;
			for (i = 0; i < array_obj_customer.length; i++) {
				double_dep_amt = 0;
				double_dep_paid = 0;
				double_int_amt = 0;
				double_int_paid = 0;

				rs2 = s2
						.executeQuery("Select sum(dep_amt) as dep_amt,sum(int_amt) as int_amt,sum(dep_paid) as dep_paid,sum(int_paid) as int_paid,trn_type,cd_ind from DepositTransaction where ac_no="
								+ array_obj_customer[i].getAccNo()
								+ " and ac_type='"
								+ array_obj_customer[i].getAccType()
								+ "' and  concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<='"
								+ string_date + "' group by trn_type,cd_ind");
				while (rs2.next()) {
					if (rs2.getString("trn_type").equalsIgnoreCase("D")
							&& rs2.getString("cd_ind").equalsIgnoreCase("C"))
						double_dep_amt = rs2.getDouble("dep_amt");
					if (rs2.getString("trn_type").equalsIgnoreCase("I")
							&& rs2.getString("cd_ind").equalsIgnoreCase("C"))
						double_int_amt = rs2.getDouble("int_amt");
					if (rs2.getString("trn_type").equalsIgnoreCase("P")
							&& rs2.getString("cd_ind").equalsIgnoreCase("D")) {
						double_dep_paid = rs2.getDouble("dep_paid");
						double_int_paid = rs2.getDouble("int_paid");
					}
				}
				array_obj_customer[i].setDepositAmtReceived(double_dep_amt
						- double_dep_paid);
				array_obj_customer[i].setInterestAccured(double_int_amt);
				array_obj_customer[i].setInterestPaid(double_int_paid);
				array_obj_customer[i].setRDBalance(array_obj_customer[i]
						.getDepositAmt()
						+ array_obj_customer[i].getInterestAccured()
						- array_obj_customer[i].getInterestPaid());

				rs2 = s2
						.executeQuery("Select concat(IFNULL(fname,' '),' ',IFNULL(mname,' ' ),' ',IFNULL(lname,' ')) as name,cm.custtype,cm.sub_category,moduleabbr from CustomerMaster cm,DepositMaster dm,Modules m where dm.ac_type='"
								+ array_obj_customer[i].getAccType()
								+ "' and dm.ac_no="
								+ array_obj_customer[i].getAccNo()
								+ " and cm.cid=dm.cid and dm.ac_type=m.modulecode");
				if (rs2.next()) {
					name = rs2.getString("name");
					dep_ty = rs2.getString("sub_category");
					cat = rs2.getInt("custtype");
					ac_type = rs2.getString("moduleabbr");
				}
				array_obj_customer[i].setName(name);
				array_obj_customer[i].setDepositType(dep_ty);
				array_obj_customer[i].setCategory(cat);

				rs2 = s2
						.executeQuery("Select trn_seq,int_date from DepositTransaction where ac_no="
								+ array_obj_customer[i].getAccNo()
								+ " and ac_type='"
								+ array_obj_customer[i].getAccType()
								+ "' and trn_type='I' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<='"
								+ string_date
								+ "' order by trn_seq desc limit 1");
				if (rs2.next()) {
					int_upto_date = rs2.getString("int_date");
				}
				array_obj_customer[i].setAccType(ac_type);
				array_obj_customer[i].setInterestUpto(int_upto_date);
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return array_obj_customer;
	}

	/*
	 * public DepositReportObject[] getQuantumDetails(int srlno, String
	 * string_account_type,String string_date,int type) throws
	 * RecordsNotFoundException { DepositReportObject[] array_obj_period=null;
	 * int j=0; double double_from_limit=0; double double_to_limit=0;
	 * System.out.println("Type==>"+type);
	 * System.out.println("Acc.type:"+string_account_type);
	 * 
	 * Connection conn = null; try { conn = getConnection(); Statement
	 * s1=conn.createStatement(); Statement s2=conn.createStatement(); ResultSet
	 * rs; ResultSet rs1; if(type==1) rs=s1.executeQuery("Select
	 * fr_lmt,to_lmt,lmt_hdg from QuantumLimit where srl_no="+srlno+" and
	 * mod_ty='"+string_account_type+"'"); else rs=s1.executeQuery("Select
	 * fr_lmt,to_lmt,lmt_hdg from QuantumLimit where srl_no="+srlno+" limit 1
	 * ");
	 * 
	 * if(rs.next()) { double_from_limit=rs.getDouble("fr_lmt");
	 * double_to_limit=rs.getDouble("to_lmt"); } rs.beforeFirst(); rs.last();
	 * if(type==1) rs1=s2.executeQuery("select count(*) as total,sum(dep_amt) as
	 * dep_amt from DepositMaster where ac_type='"+string_account_type+"' and
	 * dep_amt between "+double_from_limit+" and "+double_to_limit+" and
	 * (concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1,
	 * (locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,
	 * locate('/',close_date)-1))> '"+string_date+"' or close_date is null) and
	 * (concat(right(dep_date,4),'-',mid(dep_date,locate('/',dep_date)+1,
	 * (locate
	 * ('/',dep_date,4)-locate('/',dep_date)-1)),'-',left(dep_date,locate(
	 * '/',dep_date)-1))<='"+string_date+"') order by
	 * ac_no"); else rs1=s2.executeQuery("select count(*) as total,sum(dep_amt)
	 * as dep_amt from DepositMaster where dep_amt between "+double_from_limit+"
	 * and "+double_to_limit+" and
	 * (concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1,
	 * (locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,
	 * locate('/',close_date)-1))> '"+string_date+"' or close_date is null) and
	 * (concat(right(dep_date,4),'-',mid(dep_date,locate('/',dep_date)+1,
	 * (locate
	 * ('/',dep_date,4)-locate('/',dep_date)-1)),'-',left(dep_date,locate(
	 * '/',dep_date)-1))<='"+string_date+"') order by ac_no");
	 * 
	 * array_obj_period=new DepositReportObject[rs.getRow()];
	 * //array_obj_period=new DepositReportObject[rs1.getRow()];
	 * rs.beforeFirst();
	 * 
	 * while(rs.next()&& rs1.next()) { array_obj_period[j]=new
	 * DepositReportObject();
	 * array_obj_period[j].setLmt_hdg(rs.getString("lmt_hdg"));
	 * array_obj_period[j].setAccno( rs1.getInt("total") );
	 * array_obj_period[j].setDepAmt( rs1.getDouble("dep_amt") );
	 * 
	 * j++; }
	 * 
	 * }catch(SQLException e){ e.printStackTrace(); throw new
	 * RecordsNotFoundException(); } finally{ try { conn.close(); } catch
	 * (SQLException e) { e.printStackTrace(); } } return array_obj_period;
	 * 
	 * }
	 */

	public DepositMasterObject[] getDepositMaster(String acctype, int type,
			int facno, int tacno, String fdate, String tdate, int flag,
			String query) throws RecordsNotFoundException {

		DepositMasterObject depmast[] = null;
		ResultSet rs = null, rs3 = null, rs4 = null, rs5 = null;
		Connection conn = null;

		/*
		 * fdate=Validations.convertYMD("from_date");
		 * tdate=Validations.convertYMD("to_date");
		 */

		System.out.println(" from ac_no in bean==" + facno);
		System.out.println("to ac_no in bean=====" + tacno);
		System.out.println("type= in the bean====" + type);
		System.out.println("The value of FLage is ---> " + flag);
		System.out.println(" from date in bean==" + fdate);
		System.out.println("to date in bean=====" + tdate);
		System.out.println("The value of Query in bean is===> " + query);
		try {
			conn = getConnection();
			Statement stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			Statement stat = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			if (type == 0)
				rs = stmt
						.executeQuery("select distinct dm.ac_no,dm.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name from DepositMaster dm,CustomerMaster cm where cm.cid=dm.cid and dm.ac_type='"
								+ acctype
								+ "' and ((dm.ac_no between "
								+ facno
								+ " and "
								+ tacno
								+ ") || (concat(right(dep_date,4),'-',mid(dep_date,locate('/',dep_date)+1,(locate('/',dep_date,4)-locate('/',dep_date)-1)),'-',left(dep_date,locate('/',dep_date)-1)) between '"
								+ fdate
								+ "' and '"
								+ tdate
								+ "')) and dm.close_ind=0 and dm.ve_tml is not null order by dm.ac_no");
			// rs=stmt.executeQuery("select distinct dm.ac_no,dm.*,concat_ws('
			// ',cm.fname,cm.mname,cm.lname) as name from DepositMaster
			// dm,CustomerMaster cm where cm.cid=dm.cid and
			// dm.ac_type='"+acctype+"' and dm.ac_no between "+facno+" and
			// "+tacno+" and dm.close_ind=0 order by dm.ac_no");
			else if (type == 1)
				rs = stmt
						.executeQuery("select distinct dm.ac_no,dm.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name from DepositMaster dm,CustomerMaster cm where cm.cid=dm.cid and dm.ac_type='"
								+ acctype
								+ "' and ((dm.ac_no between "
								+ facno
								+ " and "
								+ tacno
								+ ") || (concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1,(locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,locate('/',close_date)-1)) between '"
								+ fdate
								+ "' and '"
								+ tdate
								+ "'))and dm.close_ind > 0 and dm.ve_tml is not null order by dm.ac_no");
			// rs=stmt.executeQuery("select distinct dm.ac_no,dm.*,concat_ws('
			// ',cm.fname,cm.mname,cm.lname) as name from DepositMaster
			// dm,CustomerMaster cm where cm.cid=dm.cid and
			// dm.ac_type='"+acctype+"' and
			// concat(right(dm.close_date,4),'-',mid(dm.close_date,locate('/',dm.close_date)+1,(locate('/',dm.close_date,4)-locate('/',dm.close_date)-1)),'-',left(dm.close_date,locate('/',dm.close_date)-1))
			// between '"+fdate+"' and '"+tdate+"' and dm.close_ind in (1,2,3)
			// order by dm.ac_no");
			else if (type == 2)
				rs = stmt
						.executeQuery("select distinct dm.ac_no,dm.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name from DepositMaster dm,CustomerMaster cm where cm.cid=dm.cid and dm.ac_type='"
								+ acctype
								+ "' and ((dm.ac_no between "
								+ facno
								+ " and "
								+ tacno
								+ ") || (concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1,(locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,locate('/',close_date)-1)) between '"
								+ fdate
								+ "' and '"
								+ tdate
								+ "' || concat(right(dep_date,4),'-',mid(dep_date,locate('/',dep_date)+1,(locate('/',dep_date,4)-locate('/',dep_date)-1)),'-',left(dep_date,locate('/',dep_date)-1)) between '"
								+ fdate
								+ "' and '"
								+ tdate
								+ "')) and dm.ve_tml is not null order by dm.ac_no");
			// rs=stmt.executeQuery("select distinct dm.ac_no,dm.*,concat_ws('
			// ',cm.fname,cm.mname,cm.lname) as name from DepositMaster
			// dm,CustomerMaster cm,DepositTransaction dt where cm.cid=dm.cid
			// and dm.ac_type='"+acctype+"' and dm.ac_no=dt.ac_no and dm.ac_no
			// between "+facno+" and "+tacno+" and
			// concat(right(dt.trn_date,4),'-',mid(dt.trn_date,locate('/',dt.trn_date)+1,(locate('/',dt.trn_date,4)-locate('/',dt.trn_date)-1)),'-',left(dt.trn_date,locate('/',dt.trn_date)-1))
			// between '"+fdate+"' and '"+tdate+"' order by dm.ac_no");
			else if (type == 3)
				rs = stmt
						.executeQuery("select distinct dm.ac_no,dm.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name from DepositMaster dm,CustomerMaster cm where cm.cid=dm.cid and dm.ac_type='"
								+ acctype
								+ "' and dm.close_ind!=2 order by dm.ac_no");
			else if (type == 4) {
				if (flag == 0) {
					// rs=stmt.executeQuery("select distinct
					// dm.ac_no,dm.*,concat_ws(' ',cm.fname,cm.mname,cm.lname)
					// as name,dt.cum_int from DepositMaster
					// dm,DepositTransaction dt,CustomerMaster cm where
					// cm.cid=dm.cid and dm.ac_type='"+acctype+"'and
					// dm.ac_no=dt.ac_no and dm.ac_type=dt.ac_type and
					// concat(right(dm.mat_date,4),'-',mid(dm.mat_date,locate('/',dm.mat_date)+1,(locate('/',dm.mat_date,4)-locate('/',dm.mat_date)-1)),'-',left(dm.mat_date,locate('/',dm.mat_date)-1))
					// between '"+fdate+"' and '"+tdate+"' order by dm.ac_no");
					rs = stmt
							.executeQuery("select distinct dm.ac_no,dm.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name from DepositMaster dm,CustomerMaster cm where cm.cid=dm.cid and dm.ac_type='"
									+ acctype
									+ "' and concat(right(dm.mat_date,4),'-',mid(dm.mat_date,locate('/',dm.mat_date)+1,(locate('/',dm.mat_date,4)-locate('/',dm.mat_date)-1)),'-',left(dm.mat_date,locate('/',dm.mat_date)-1)) between '"
									+ fdate
									+ "' and '"
									+ tdate
									+ "' order by dm.ac_no");
				} else {
					// rs=stmt.executeQuery("select distinct
					// dm.ac_no,dm.*,concat_ws(' ',cm.fname,cm.mname,cm.lname)
					// as name,dt.cum_int from DepositMaster
					// dm,DepositTransaction dt,CustomerMaster cm where
					// cm.cid=dm.cid and dm.ac_type='"+acctype+"'and
					// dm.ac_no=dt.ac_no and dm.ac_type=dt.ac_type and
					// concat(right(dm.mat_date,4),'-',mid(dm.mat_date,locate('/',dm.mat_date)+1,(locate('/',dm.mat_date,4)-locate('/',dm.mat_date)-1)),'-',left(dm.mat_date,locate('/',dm.mat_date)-1))
					// between '"+fdate+"' and '"+tdate+"'and ("+query+") order
					// by dm.ac_no");
					rs = stmt
							.executeQuery("select distinct dm.ac_no,dm.*,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name from DepositMaster dm,CustomerMaster cm where cm.cid=dm.cid and dm.ac_type='"
									+ acctype
									+ "' and concat(right(dm.mat_date,4),'-',mid(dm.mat_date,locate('/',dm.mat_date)+1,(locate('/',dm.mat_date,4)-locate('/',dm.mat_date)-1)),'-',left(dm.mat_date,locate('/',dm.mat_date)-1)) between '"
									+ fdate
									+ "' and '"
									+ tdate
									+ "'and ("
									+ query + ") order by dm.ac_no");
				}
			} else if (type == 5)
				rs = stmt
						.executeQuery("select DepositMaster.*,concat_ws(' ',fname,mname,lname) as name from DepositMaster,CustomerMaster where CustomerMaster.cid=DepositMaster.cid and DepositMaster.ac_type='"
								+ acctype
								+ "' and DepositMaster.ac_no="
								+ facno);
			else if (type == 6) {
				if (flag == 0) {

					System.out.println("geetha u r inside flag=0");
					// rs=stmt.executeQuery("select distinct
					// dm.ac_no,dm.*,concat_ws(' ',cm.fname,cm.mname,cm.lname)
					// as name,dt.int_amt,dt.cum_int from CustomerMaster
					// cm,DepositMaster dm,DepositTransaction dt where
					// dm.ac_type='"+acctype+"' and
					// concat(right(int_date,4),'-',mid(int_date,locate('/',int_date)+1,
					// (locate('/',int_date,4)-locate('/',int_date)-1)),'-',left(int_date,locate('/',int_date)-1))
					// between '"+fdate+"'and '"+tdate+"' and cm.cid=dm.cid and
					// dt.ac_type=dm.ac_type and dt.ac_no=dm.ac_no and
					// trn_type='I' order by dm.ac_no");
					rs = stmt
							.executeQuery("select dt.*,dm.* from DepositTransaction dt left join DepositMaster dm on dt.ac_type=dm.ac_type and dt.ac_no=dm.ac_no where dt.ac_type='"
									+ acctype
									+ "' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"
									+ fdate
									+ "'and '"
									+ tdate
									+ "' and dt.trn_type='I' order by dt.ac_no ");
				} else {
					System.out.println("geetha u r inside flag!=0");
					// rs=stmt.executeQuery("select distinct
					// dm.ac_no,dm.*,concat_ws(' ',cm.fname,cm.mname,cm.lname)
					// as name,dt.int_amt,dt.cum_int from CustomerMaster
					// cm,DepositMaster dm,DepositTransaction dt where
					// dm.ac_type='"+acctype+"' and
					// concat(right(int_date,4),'-',mid(int_date,locate('/',int_date)+1,
					// (locate('/',int_date,4)-locate('/',int_date)-1)),'-',left(int_date,locate('/',int_date)-1))
					// between '"+fdate+"'and '"+tdate+"' and cm.cid=dm.cid and
					// dt.ac_type=dm.ac_type and dt.ac_no=dm.ac_no and
					// trn_type='I' and ("+query+") order by dm.ac_no");
					rs = stmt
							.executeQuery("select dt.*,dm.* from DepositTransaction dt left join DepositMaster dm on dt.ac_type=dm.ac_type and dt.ac_no=dm.ac_no where dt.ac_type='"
									+ acctype
									+ "' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"
									+ fdate
									+ "'and '"
									+ tdate
									+ "' and dt.trn_type='I' and ("
									+ query
									+ ") order by dt.ac_no ");
				}
			} else if (type == 7) {
				if (flag == 0) {
					System.out.println("geetha u r inside flag=0");
					// rs=stmt.executeQuery("select distinct
					// dm.ac_no,dt.int_amt,dm.*,concat(cm.fname,'
					// ',IFNULL(cm.mname,''),IFNULL(cm.lname,'')) as name from
					// CustomerMaster cm,DepositMaster dm,DepositTransaction dt
					// where dm.ac_type='"+acctype+"' and
					// concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,
					// (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))
					// <'"+fdate+"' and cm.cid=dm.cid and dt.ac_type=dm.ac_type
					// and dt.ac_no=dm.ac_no and close_ind=0 and dt.trn_type='I'
					// and dt.cd_ind='C' order by dm.ac_no");
					// rs=stmt.executeQuery("select distinct dm.* from
					// DepositMaster dm,DepositTransaction dt where
					// dm.ac_type='"+acctype+"' and dm.ac_type=dt.ac_type and
					// dm.ac_no=dt.ac_no and
					// concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,
					// (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<'"+fdate+"'
					// order by ac_no");
					stmt.executeUpdate("drop table if exists  fd_values");
					stmt.executeUpdate("create temporary table fd_values select distinct * from DepositMaster where ac_type='"
									+ acctype
									+ "' and (concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1, (locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,locate('/',close_date)-1))>'"
									+ fdate
									+ "' or close_date is null) and (concat(right(dep_date,4),'-',mid(dep_date,locate('/',dep_date)+1, (locate('/',dep_date,4)-locate('/',dep_date)-1)),'-',left(dep_date,locate('/',dep_date)-1))<='"
									+ fdate + "') order by ac_no");
					rs = stmt
							.executeQuery("select distinct dt.ac_no,dm.* from fd_values dm , DepositTransaction as dt where dm.ac_type =+ dt.ac_type and dm.ac_no =+ dt.ac_no and (concat(right(dt.trn_date,4),'-',mid(dt.trn_date,locate('/',dt.trn_date)+1, (locate('/',dt.trn_date,4)-locate('/',dt.trn_date)-1)),'-',left(dt.trn_date,locate('/',dt.trn_date)-1)) <='"
									+ fdate + "' )");

				} else {
					System.out.println("geetha u r inside flag!=0");
					stmt.executeUpdate("drop table if exists  fd_values");
					stmt
							.executeUpdate("create temporary table fd_values select distinct * from DepositMaster where ac_type='"
									+ acctype
									+ "' and (concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1, (locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,locate('/',close_date)-1))>'"
									+ fdate
									+ "' or close_date is null) and (concat(right(dep_date,4),'-',mid(dep_date,locate('/',dep_date)+1, (locate('/',dep_date,4)-locate('/',dep_date)-1)),'-',left(dep_date,locate('/',dep_date)-1))<='"
									+ fdate
									+ "') and ("
									+ query
									+ ") order by ac_no");
					rs = stmt.executeQuery("select distinct dt.ac_no,dm.* from fd_values dm , DepositTransaction as dt where dm.ac_type =+ dt.ac_type and dm.ac_no =+ dt.ac_no and (concat(right(dt.trn_date,4),'-',mid(dt.trn_date,locate('/',dt.trn_date)+1, (locate('/',dt.trn_date,4)-locate('/',dt.trn_date)-1)),'-',left(dt.trn_date,locate('/',dt.trn_date)-1)) <='"
									+ fdate + "' )");
					// rs=stmt.executeQuery("select distinct
					// dm.ac_no,dt.int_amt,dm.*,concat(cm.fname,'
					// ',IFNULL(cm.mname,''),IFNULL(cm.lname,'')) as name from
					// CustomerMaster cm,DepositMaster dm,DepositTransaction dt
					// where dm.ac_type='"+acctype+"' and
					// concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,
					// (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))
					// <'"+fdate+"' and cm.cid=dm.cid and dt.ac_type=dm.ac_type
					// and dt.ac_no=dm.ac_no and close_ind=0 and dt.trn_type='I'
					// and dt.cd_ind='C'and ("+query+") order by dm.ac_no");
				}
			}
			rs.last();
			if (rs.getRow() == 0) {
				return null;
				// changed to return null; by sumanth to show alert message as
				// Records not found
				// throw new RecordsNotFoundException();
			}
			depmast = new DepositMasterObject[rs.getRow()];
			rs.beforeFirst();
			int i = 0;
			while (rs.next()) {
				depmast[i] = new DepositMasterObject();
				if (rs.getString("ve_user") == null)
				depmast[i].setVerified("F");
				else
				depmast[i].setVerified("T");
				depmast[i].setAccType("acctype");
				depmast[i].setAccNo(rs.getInt("ac_no"));
				depmast[i].setCustomerId(rs.getInt("cid"));
				depmast[i].setMailingAddress(rs.getInt("add_type"));
				depmast[i].setNoofJtHldrs(rs.getInt("no_jt_hldr"));
				depmast[i].setMaturityDate(rs.getString("mat_date"));
				depmast[i].setDepDate(rs.getString("dep_date"));
				depmast[i].setDepositDays(rs.getInt("dep_days"));
				depmast[i].setDepositAmt(rs.getDouble("dm.dep_amt"));
				depmast[i].setMaturityAmt(rs.getDouble("mat_amt"));
				depmast[i].setIntroAccno(rs.getInt("intr_ac_no"));
				depmast[i].setIntroAccType(rs.getString("intr_ac_type"));
				depmast[i].setNomineeRegNo(rs.getInt("nom_no"));
				depmast[i].setInterestRate(rs.getDouble("int_rate"));
				depmast[i].setReceivedBy(rs.getString("rcvd_by"));
				depmast[i].setExcessAmt(rs.getDouble("exc_amt"));
				depmast[i].setRenType(rs.getString("ren_ac_type"));
				depmast[i].setRenewalAccno(rs.getInt("ren_ac_no"));

				if (depmast[i].getReceivedBy() != null
						&& depmast[i].getReceivedBy().equals("C")) {
					depmast[i].setReceivedAccno(rs.getInt("rcvd_ac_no"));
					depmast[i].setReceivedAccType("");
				} else if (depmast[i].getReceivedBy() != null
						&& depmast[i].getReceivedBy().equals("T")) {
					depmast[i].setReceivedAccno(rs.getInt("rcvd_ac_no"));
					depmast[i].setReceivedAccType(rs.getString("rcvd_ac_type"));
				}
				depmast[i].setInterestFrq(rs.getString("int_freq"));
				depmast[i].setInterestMode(rs.getString("int_mode"));

				if (rs.getString("int_mode") != null
						&& rs.getString("int_mode").equals("T")) {
					depmast[i].setTransferAccType(rs.getString("trf_ac_type"));
					depmast[i].setTransferAccno(rs.getInt("trf_ac_no"));
				}
				depmast[i].setInterestUpto(rs.getString("int_upto_date"));
				depmast[i].setLoanAvailed(rs.getString("ln_availed"));
				depmast[i].setReceiptno(rs.getInt("rct_no"));
				depmast[i].setAutoRenewal(rs.getString("auto_renewal"));
				depmast[i].setCloseInd(rs.getInt("close_ind"));
				depmast[i].userverifier.setUserTml(rs.getString("de_tml"));
				depmast[i].userverifier.setUserId(rs.getString("de_user"));
				depmast[i].userverifier.setUserDate(rs.getString("de_date"));
				if (type != 6 && type != 7)
				{
					depmast[i].setName(rs.getString("name"));
				}

				if (type == 5) {
					rs3 = stat
							.executeQuery("select cid,addr_type from JointHolders where ac_type='"
									+ depmast[i].getAccType()
									+ "' and ac_no="
									+ depmast[i].getAccNo());
					int j = 0;
					int addrtype[] = null;
					int cids[] = null;
					if (rs3.next()) {
						rs3.last();
						addrtype = new int[rs3.getRow()];
						cids = new int[rs3.getRow()];
						rs3.beforeFirst();
					}
					while (rs3.next()) {
						addrtype[j] = rs3.getInt(2);
						cids[j++] = rs3.getInt(1);
					}
					depmast[i].setJointAddrType(addrtype);
					depmast[i].setJointCid(cids);
					depmast[i].setNomineeDetails(commonLocal
							.getNominee(depmast[i].getNomineeRegNo()));
					depmast[i].setSigObj(commonLocal.getSignatureDetails(
							depmast[i].getAccNo(), depmast[i].getAccType()));
				} else if (type == 3) {
					rs3 = stat
							.executeQuery("select cum_int from DepositTransaction where ac_no="
									+ depmast[i].getAccNo()
									+ " and ac_type='"
									+ depmast[i].getAccType()
									+ "' and trn_ty='I' order by trn_seq desc");

					if (rs3.next())
						depmast[i].setCumInterest(rs3.getDouble(1));
					else
						depmast[i].setCumInterest(0);
				} else if (type == 4) {
					// rs3=stat.executeQuery("select dep_paid,int_paid from
					// DepositTransaction where ac_no="+depmast[i].getAccNo()+"
					// and ac_type='"+depmast[i].getAccType()+"' order by
					// trn_seq desc");
					rs3 = stat
							.executeQuery("select sum(int_amt)from DepositTransaction where ac_type='"
									+ rs.getString("dm.ac_type")
									+ "' and ac_no="
									+ rs.getInt("dm.ac_no")
									+ " and trn_type='I'");
					if (rs3.next()) {
						depmast[i].setInterestAccured(rs3.getDouble(1));
					} else {
						depmast[i].setInterestAccured(0);
					}
					depmast[i].setCumInterest(rs3.getDouble(1));
					rs4 = stat
							.executeQuery("select sum(int_Paid),sum(dep_paid)from DepositTransaction where ac_type='"
									+ rs.getString("dm.ac_type")
									+ "' and ac_no="
									+ rs.getInt("dm.ac_no")
									+ " and (trn_type='I' || trn_type='P')");
					if (rs4.next()) {
						depmast[i].setInterestPaid(rs4.getDouble(1));
						depmast[i].setDepositPaid(rs4.getDouble(2));
					} else {
						depmast[i].setInterestPaid(0);
						depmast[i].setDepositPaid(0);
					}

				}
				if (type == 6) {
					depmast[i].setCumInterest(rs.getDouble("dt.cum_int"));
					depmast[i].setInterestAccured(rs.getDouble("dt.int_amt"));
					rs3 = stat
							.executeQuery("select concat_ws(' ',fname,mname,lname) as name from CustomerMaster where cid="
									+ rs.getInt("dm.cid") + "");
					if (rs3.next()) {
						depmast[i].setName(rs3.getString(1));
					}
					if (acctype.startsWith("1003")) {
						rs4 = stat
								.executeQuery("select sum(int_amt)from DepositTransaction where ac_type='"
										+ rs.getString("dm.ac_type")
										+ "' and ac_no="
										+ rs.getInt("dm.ac_no")
										+ " and trn_type='I' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) <='"
										+ rs.getString("dt.trn_date") + "' ");
						if (rs4.next()) {
							depmast[i].setCumInterest(rs4.getDouble(1));
						}
					}
				}
				if (type == 7) {
					double double_dep_amt = 0, double_int_paid = 0, double_dep_paid = 0, rd_bal = 0;
					double double_int_amt = 0;
					String name = null, dep_ty = null, ac_type = null;
					int cat = 0;
					rs3 = stat.executeQuery("Select sum(dep_amt) as dep_amt,sum(int_amt) as int_amt,sum(dep_paid) as dep_paid,sum(int_paid) as int_paid,trn_type,cd_ind from DepositTransaction where ac_no="
									+ depmast[i].getAccNo()
									+ " and ac_type='"
									+ depmast[i].getAccType()
									+ "' and  concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<='"
									+ fdate + "' group by trn_type,cd_ind");
					while (rs3.next()) {
						if (rs3.getString("trn_type").equalsIgnoreCase("D")
								&& rs3.getString("cd_ind")
										.equalsIgnoreCase("C"))
							double_dep_amt = rs3.getDouble("dep_amt");
						if (rs3.getString("trn_type").equalsIgnoreCase("I")
								&& rs3.getString("cd_ind")
										.equalsIgnoreCase("C"))
							double_int_amt = rs3.getDouble("int_amt");
						if (rs3.getString("trn_type").equalsIgnoreCase("P")
								&& rs3.getString("cd_ind")
										.equalsIgnoreCase("D")) {
							double_dep_paid = rs3.getDouble("dep_paid");
							double_int_paid = rs3.getDouble("int_paid");
						}
					}
					depmast[i].setDepositAmtReceived(double_dep_amt
							- double_dep_paid);
					depmast[i].setInterestAccured(double_int_amt);
					depmast[i].setInterestPaid(double_int_paid);
					depmast[i].setRDBalance(depmast[i].getDepositAmtReceived()
							+ depmast[i].getInterestAccured()
							- depmast[i].getInterestPaid());

					rs3 = stat
							.executeQuery("Select concat(IFNULL(fname,' '),' ',IFNULL(mname,' ' ),' ',IFNULL(lname,' ')) as name,cm.custtype,cm.sub_category,moduleabbr from CustomerMaster cm,DepositMaster dm,Modules m where dm.ac_type='"
									+ depmast[i].getAccType()
									+ "' and dm.ac_no="
									+ depmast[i].getAccNo()
									+ " and cm.cid=dm.cid and dm.ac_type=m.modulecode");
					if (rs3.next()) {
						name = rs3.getString("name");
						dep_ty = rs3.getString("sub_category");
						cat = rs3.getInt("custtype");
						ac_type = rs3.getString("moduleabbr");
					}
					depmast[i].setName(name);
					depmast[i].setDepositType(dep_ty);
					depmast[i].setCategory(cat);
					/*
					 * double double_CD=0,double_DP=0;
					 * rs3=stat.executeQuery("select sum(int_amt) as int_amt
					 * from DepositTransaction where ac_type='"+acctype+"' and
					 * ac_no="+depmast[i].getAccNo()+" and
					 * concat(right(trn_date,
					 * 4),'-',mid(trn_date,locate('/',trn_date)+1,
					 * (locate('/',trn_date
					 * ,4)-locate('/',trn_date)-1)),'-',left(
					 * trn_date,locate('/',trn_date)-1)) <'"+fdate+"' ");
					 * if(rs3.next()) {
					 * depmast[i].setInterestAccured(rs3.getDouble("int_amt"));
					 * } rs3=stat.executeQuery("select sum(dep_amt) as CD from
					 * DepositTransaction where ac_type='"+acctype+"' and
					 * ac_no="+depmast[i].getAccNo()+" and
					 * concat(right(trn_date,
					 * 4),'-',mid(trn_date,locate('/',trn_date)+1,
					 * (locate('/',trn_date
					 * ,4)-locate('/',trn_date)-1)),'-',left(
					 * trn_date,locate('/',trn_date)-1)) <'"+fdate+"' and
					 * cd_ind='C' and trn_type='D' order by
					 * ac_no"); if(rs3.next()) { double_CD=rs3.getDouble("CD");
					 * } rs4=stat.executeQuery("select sum(dep_amt) as DP from
					 * DepositTransaction where ac_type='"+acctype+"' and
					 * ac_no="+depmast[i].getAccNo()+" and
					 * concat(right(trn_date,
					 * 4),'-',mid(trn_date,locate('/',trn_date)+1,
					 * (locate('/',trn_date
					 * ,4)-locate('/',trn_date)-1)),'-',left(
					 * trn_date,locate('/',trn_date)-1)) <'"+fdate+"' and
					 * cd_ind='D' and trn_type='P' order by
					 * ac_no"); if(rs4.next()) { double_DP=rs4.getDouble("DP");
					 * } depmast[i].setDepositAmtReceived(double_CD-double_DP);
					 * rs5=stat.executeQuery("select rd_bal from
					 * DepositTransaction where ac_type='1004001' and
					 * ac_no="+depmast[i].getAccNo()+" and
					 * concat(right(trn_date,
					 * 4),'-',mid(trn_date,locate('/',trn_date)+1,
					 * (locate('/',trn_date
					 * ,4)-locate('/',trn_date)-1)),'-',left(
					 * trn_date,locate('/',trn_date)-1)) <'"+fdate+"'order by
					 * trn_seq"); if(rs5.next()) { rs5.last();
					 * depmast[i].setRDBalance(rs5.getDouble("rd_bal")); }
					 */
				}
				i++;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			// throw new RecordsNotFoundException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return depmast;
	}

	public DepositTransactionObject[] getDepositTransaction(int type,
			String acctype, int no, String fdate, String tdate, String query,
			int flag) throws RecordsNotFoundException {
		DepositTransactionObject deptrn[] = null;
		ResultSet rs = null, rs_narr = null;
		Connection conn = null;
		Statement stmt = null, stmt_narr = null;
		String trn_narration = null;
		StringTokenizer trn_token = null;
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			if (type == 0) {
				if (flag == 0)
					rs = stmt
							.executeQuery("select * from DepositTransaction where ac_type='"
									+ acctype
									+ "' and ac_no="
									+ no
									+ "  order by trn_seq");
				else
					rs = stmt
							.executeQuery("select * from DepositTransaction where ac_type='"
									+ acctype
									+ "' and ac_no="
									+ no
									+ " and ("
									+ query + ") order by trn_seq");
			} else if (type == 1) {
				rs = stmt
						.executeQuery("select dt.* from DepositMaster dm,CustomerMaster cm,DepositTransaction dt where cm.cid=dm.cid and dm.ac_type='"
								+ acctype
								+ "' and dm.ac_no="
								+ no
								+ " and dt.ac_no=dm.ac_no and dm.close_ind=2 order by dt.trn_seq");
			} else if (type == 2) {
				rs = stmt
						.executeQuery("select dt.* from DepositMaster dm,CustomerMaster cm,DepositTransaction dt where cm.cid=dm.cid and dm.ac_type='"
								+ acctype
								+ "' and dm.ac_no="
								+ no
								+ " and dm.ac_no=dt.ac_no and concat(right(dt.trn_date,4),'-',mid(dt.trn_date,locate('/',dt.trn_date)+1,(locate('/',dt.trn_date,4)-locate('/',dt.trn_date)-1)),'-',left(dt.trn_date,locate('/',dt.trn_date)-1)) between '"
								+ fdate
								+ "' and '"
								+ tdate
								+ "' order by dt.trn_seq");
			} else
				rs = stmt
						.executeQuery("select * from DepositTransaction where ac_type='"
								+ acctype + "' and ac_no=" + no);
			rs.last();
			if (rs.getRow() == 0)
				throw new RecordsNotFoundException();
			deptrn = new DepositTransactionObject[rs.getRow()];
			rs.beforeFirst();
			int i = 0;
			while (rs.next()) {
				deptrn[i] = new DepositTransactionObject();
				deptrn[i].setTranDate(rs.getString(4));
				deptrn[i].setTranType(rs.getString(5));
				deptrn[i].setDepositAmt(rs.getDouble(6));
				deptrn[i].setInterestAmt(rs.getDouble(7));
				deptrn[i].setTranMode(rs.getString(15));
				deptrn[i].setReferenceNo(rs.getInt(13));
				if (type == 0) {
					deptrn[i].setRDBalance(rs.getDouble("rd_bal"));
					deptrn[i].setCumInterest(rs.getDouble("cum_int"));
					deptrn[i].setDepositPaid(rs.getDouble("dep_paid"));
					deptrn[i].setInterestPaid(rs.getDouble("int_paid"));

				}
				/*
				 * if(deptrn[i].getTranMode().equals("C"))
				 * deptrn[i].setTranNarration("Csh
				 * "+deptrn[i].getReferenceNo()); else
				 * if(deptrn[i].getTranMode().equals("T"))
				 * deptrn[i].setTranNarration("Trf "+rs.getString(14)); else
				 * if(deptrn[i].getTranType().equals("I"))
				 * deptrn[i].setTranNarration("Csh ");
				 */
				trn_narration = rs.getString("trn_narr");
				if (trn_narration != null) {
					try {
						stmt_narr = conn.createStatement();
						trn_token = new StringTokenizer(trn_narration, " ");
						if (trn_token.hasMoreTokens()) {
							rs_narr = stmt_narr
									.executeQuery("select moduleabbr from Modules where modulecode='"
											+ trn_token.nextToken() + "'");
							if (rs_narr.next()) {
								trn_narration = rs_narr.getString("moduleabbr")
										+ " " + trn_token.nextToken();
							}
						}
					} catch (Exception exe) {
						exe.printStackTrace();
					}
				}
				deptrn[i].setTranNarration(trn_narration);
				deptrn[i].setCdind(rs.getString(17));
				deptrn[i].setPaidDate(rs.getString("trn_date"));
				/*
				 * deptrn[i].obj_userverifier.setUserId(rs.getString(20));
				 * deptrn[i].obj_userverifier.setVerId(rs.getString(23));
				 */
				deptrn[i].setDe_user(rs.getString("de_user"));
				deptrn[i].setVe_user(rs.getString("ve_user"));
				deptrn[i].setTrn_seq(Integer.parseInt(rs.getString("trn_seq")));
				i++;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RecordsNotFoundException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return (deptrn);
	}

	public DepositMasterObject[] getFDReceiptVerification(int start_no,
			int end_no, String ac_type) throws RecordsNotFoundException {
		System.out.println("Calling ...1223");
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		DepositMasterObject[] dm = null;

		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt
					.executeQuery("select moduleabbr,dm.ac_type,dm.ac_no,concat_ws(' ',fname,mname,lname) as name,address,city,pin,state,country,dm.dep_date,dm.mat_date,dm.dep_amt,dm.mat_amt,dm.nom_no,dm.int_rate,dm.int_mode,dm.new_rct,dm.rct_no,dm.rct_prtd,dm.rct_sign,dm.rcvd_by,dm.dep_days,dm.rcvd_ac_no,dm.de_user,dm.ve_user from Modules,CustomerMaster cm,CustomerAddr ca,DepositMaster dm where dm.ac_type='"
							+ ac_type
							+ "' and dm.ac_type=modulecode and dm.cid=cm.cid and cm.cid=ca.cid and dm.ac_no between "
							+ start_no
							+ " and "
							+ end_no
							+ " and rct_prtd='T' and rct_sign='F' and rct_no!=0 and dm.add_type = ca.addr_type and dm.close_ind=0 order by dm.ac_no desc ");
			rs.last();
			dm = new DepositMasterObject[rs.getRow()];
			rs.beforeFirst();

			int i = 0;

			while (rs.next()) {
				dm[i] = new DepositMasterObject();

				dm[i].setAccNo(rs.getInt("dm.ac_no"));
				dm[i].setName(rs.getString("name"));
				dm[i].setDepDate(rs.getString("dm.dep_date"));
				dm[i].setMaturityDate(rs.getString("dm.mat_date"));
				dm[i].setDepositAmt(rs.getDouble("dm.dep_amt"));
				dm[i].setMaturityAmt(rs.getDouble("dm.mat_amt"));
				dm[i].setReceiptno(rs.getInt("dm.rct_no"));

				i++;
			}

		} catch (SQLException se) {
			se.printStackTrace();
			sessionContext.setRollbackOnly();
		} catch (Exception e2) {
			e2.printStackTrace();
			sessionContext.setRollbackOnly();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return dm;
	}

	public DepositMasterObject[] getFDReceiptUpdationDetails(int start_no,
			int end_no, String ac_type) throws RecordsNotFoundException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Statement stmt_1 = null;
		ResultSet rs_1 = null;
		DepositMasterObject dm[] = null;

		try {
			System.out.println("1......." + ac_type);
			conn = getConnection();

			stmt_1 = conn.createStatement();
			rs_1 = stmt_1
					.executeQuery("Select modulecode from Modules where modulecode='"
							+ ac_type + "' ");
			rs_1.next();
			String actype = rs_1.getString("modulecode").trim();

			System.out.println("actype======in bean" + actype);

			System.out.println("start_no===" + start_no);

			System.out.println("ending no=====" + end_no);

			stmt = conn.createStatement();
			rs = stmt
					.executeQuery("Select ac_no,dep_date,mat_date,dep_amt,mat_amt,int_rate,rct_no,concat_ws(' ',fname,mname,lname) as name from DepositMaster dm,CustomerMaster cm where cm.cid=dm.cid and ac_no between "
							+ start_no
							+ " and "
							+ end_no
							+ " and ac_type='"
							+ actype
							+ "' and (auto_renewal='M' or auto_renewal='D' or auto_renewal='Y') and auto_renewal!='N' and rct_prtd='T' and close_date is null and new_rct='F' ");

			System.out.println("no of records=====>>>" + rs.getRow());

			if (!rs.next()) {
				// throw new RecordsNotFoundException(); Changee by sumanth for
				// RecodsNotFound Exception to display on jsp
				return null;
			}
			rs.last();
			dm = new DepositMasterObject[rs.getRow()];
			rs.beforeFirst();

			int i = 0;

			while (rs.next()) {
				dm[i] = new DepositMasterObject();

				dm[i].setAccNo(rs.getInt("ac_no"));
				dm[i].setDepDate(rs.getString("dep_date"));
				dm[i].setMaturityDate(rs.getString("mat_date"));
				dm[i].setDepositAmt(rs.getDouble("dep_amt"));
				dm[i].setMaturityAmt(rs.getDouble("mat_amt"));
				dm[i].setInterestRate(rs.getDouble("int_rate"));
				dm[i].setReceiptno(rs.getInt("rct_no"));
				dm[i].setName(rs.getString("name"));

				i++;
			}

		} catch (SQLException sql) {
			sql.printStackTrace();
		}

		finally {
			try {
				conn.close();
			} catch (SQLException sql) {
				sql.printStackTrace();
			}
		}

		return dm;
	}

	public int storeDepositTransferVoucher(
			TrfVoucherObject[] array_trfvoucherobject, String date)
			throws CreateException, RecordNotInsertedException {

		System.out.println("hello dude------->1");
		int int_return_value = 0;
		Connection conn = null;
		try {
			System.out.println("Inside storeDepositTransferVoucher()...1234");
			conn = getConnection();
			commonLocal = commonLocalHome.create();
			ResultSet rs_voucher = null, rs = null;
			double double_total_amount = 0;
			int trn_seq = 0;
			System.out.println("hello dude------->2" + array_trfvoucherobject);
			System.out.println("hello dude------->3" + date);
			PreparedStatement pstmt_insert = null;
			Statement stmt_voucher = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			Statement stmt = conn.createStatement();
			rs = stmt
					.executeQuery("select trn_seq from DepositTransaction where ac_type='"
							+ array_trfvoucherobject[0].getAccType()
							+ "' and ac_no="
							+ array_trfvoucherobject[0].getAccNo()
							+ " order by trn_seq desc limit 1");
			if (rs.next()) {
				trn_seq = rs.getInt("trn_seq");
			}
			rs.beforeFirst();
			System.out.println("trn_seq==>" + trn_seq);

			if (array_trfvoucherobject[0].getPayMode().equals("C")) {
				System.out.println("CASH MODE...");
				int_return_value = commonLocal.getModulesColumn(
						"lst_voucher_scroll_no", "1019000");
				for (int i = 0; i < array_trfvoucherobject.length; i++) {
					// stmt_voucher.addBatch("update InterestTransferVoucher set
					// vch_pay_ind='T',vch_pay_date='"+getSysDate()+"',pay_mode='C',
					// pay_ac_no="+int_return_value+" where
					// vch_no="+array_trfvoucherobject[i].getVoucherNo()+" and
					// ac_type='"+array_trfvoucherobject[i].getAccType()+"' and
					// ac_no="+array_trfvoucherobject[i].getAccNo()+" and
					// vch_date='"+array_trfvoucherobject[i].getVoucherDate()+"'");
					double_total_amount += array_trfvoucherobject[i]
							.getTrfAmount();
					pstmt_insert = conn
							.prepareStatement("update DepositTransaction set ref_no="
									+ int_return_value
									+ ",trn_mode='C' where ac_type=? and ac_no=? and trn_type='P' and ref_no=?");
					pstmt_insert.setString(1, array_trfvoucherobject[i]
							.getAccType());
					pstmt_insert
							.setInt(2, array_trfvoucherobject[i].getAccNo());
					pstmt_insert.setInt(3, array_trfvoucherobject[i]
							.getVoucherNo());
					pstmt_insert.executeUpdate();
				}
				stmt_voucher.executeBatch();

				pstmt_insert = conn
						.prepareStatement("insert into DayCash (scroll_no,trn_type,trn_date,ac_type,ac_no,csh_amt,cd_ind,vch_type,attached,vch_no,de_user,de_tml,de_date,ve_user,ve_tml,ve_date,name) values (0,'P',?,?,?,?,'C','I','F',?,?,?,?,?,?,?,?)");
				pstmt_insert.setString(1, date);
				pstmt_insert.setString(2, array_trfvoucherobject[0]
						.getAccType());
				pstmt_insert.setInt(3, array_trfvoucherobject[0].getAccNo());
				pstmt_insert.setDouble(4, double_total_amount);
				pstmt_insert.setInt(5, int_return_value);
				pstmt_insert.setString(6, array_trfvoucherobject[0]
						.getTvPrtInd());
				pstmt_insert.setString(7, array_trfvoucherobject[0]
						.getTvPayInd());
				pstmt_insert.setString(8, array_trfvoucherobject[0]
						.getTvPayDate());
				pstmt_insert.setString(9, array_trfvoucherobject[0]
						.getTvPrtInd());
				pstmt_insert.setString(10, array_trfvoucherobject[0]
						.getTvPayInd());
				pstmt_insert.setString(11, array_trfvoucherobject[0]
						.getTvPayDate());
				pstmt_insert.setString(12, array_trfvoucherobject[0]
						.getVoucherType());
				pstmt_insert.executeUpdate();

				/** * Updation of DepositTransaction ** */
				// Added by Riswan
				pstmt_insert = conn
						.prepareStatement("update DepositTransaction set ref_no="
								+ int_return_value
								+ ",trn_mode='C' where ac_type=? and ac_no=? and trn_type='P' and ref_no=?");
				pstmt_insert.setString(1, array_trfvoucherobject[0]
						.getAccType());
				pstmt_insert.setInt(2, array_trfvoucherobject[0].getAccNo());
				pstmt_insert
						.setInt(3, array_trfvoucherobject[0].getVoucherNo());
				pstmt_insert.executeUpdate();

				System.out.println("2..");
				/**
				 * Gl for Cash credit
				 */
				rs_voucher = stmt_voucher
						.executeQuery("select * from GLKeyParam where ac_type='1019001' and code=1");
				if (rs_voucher.next()) {
					GLTransObject trnobj = new GLTransObject();
					trnobj.setTrnDate(date);
					trnobj.setGLType(rs_voucher.getString("gl_type"));
					trnobj.setGLCode(rs_voucher.getString("gl_code"));
					trnobj.setTrnMode("C");
					trnobj.setAmount(double_total_amount);
					trnobj.setCdind("C");
					trnobj.setAccType(array_trfvoucherobject[0].getAccType());
					trnobj.setAccNo(String.valueOf(array_trfvoucherobject[0]
							.getAccNo()));
					trnobj.setTrnType("P");
					trnobj.setRefNo(int_return_value);
					trnobj.setTrnSeq(trn_seq);
					trnobj.setVtml(array_trfvoucherobject[0].getTvPayInd());
					trnobj.setVid(array_trfvoucherobject[0].getTvPrtInd());
					trnobj.setVDate(array_trfvoucherobject[0].getTvPayDate());
					commonLocal.storeGLTransaction(trnobj);
				}
				/**
				 * Gl for trf voucher debit
				 */
				rs_voucher = stmt_voucher
						.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = 3 and gk.ac_type = '"
								+ array_trfvoucherobject[0].getAccType()
								+ "' and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
				if (rs_voucher.next()) {
					GLTransObject trnobj = new GLTransObject();
					trnobj.setTrnDate(date);
					trnobj.setGLType(rs_voucher.getString("gk.gl_type"));
					trnobj.setGLCode(rs_voucher.getString("gk.gl_code"));
					trnobj.setTrnMode("C");
					trnobj.setAmount(double_total_amount
							* rs_voucher.getInt("mult_by"));
					trnobj.setCdind("D");
					trnobj.setAccType(array_trfvoucherobject[0].getAccType());
					trnobj.setAccNo(String.valueOf(array_trfvoucherobject[0]
							.getAccNo()));
					trnobj.setTrnType("P");
					trnobj.setRefNo(int_return_value);
					trnobj.setTrnSeq(trn_seq);
					trnobj.setVtml(array_trfvoucherobject[0].getTvPayInd());
					trnobj.setVid(array_trfvoucherobject[0].getTvPrtInd());
					trnobj.setVDate(array_trfvoucherobject[0].getTvPayDate());
					commonLocal.storeGLTransaction(trnobj);
				}
				for (int i = 0; i < array_trfvoucherobject.length; i++) {
					System.out.println("Account NUmber==>"
							+ array_trfvoucherobject[0].getAccNo());
					System.out.println("AccType====>"
							+ array_trfvoucherobject[0].getAccType());
					System.out.println("VoucherDate-1-1-1-->"
							+ array_trfvoucherobject[0].getVoucherDate());
					System.out.println("VoucherNo-2-2-2-->"
							+ array_trfvoucherobject[0].getVoucherNo());
					System.out.println("AccNo-3-3-3-->"
							+ array_trfvoucherobject[0].getAccNo());
					System.out.println("PayAccType-4-4-4-->"
							+ array_trfvoucherobject[0].getPayAccType());
					System.out.println("PayAccNo-3-3-3-->"
							+ array_trfvoucherobject[0].getPayAccNo());
					System.out.println("Date==>" + date);
					System.out.println("int_return_value======>"
							+ int_return_value);
					stmt_voucher
							.addBatch("update InterestTransferVoucher set vch_pay_ind='T',vch_pay_date='"
									+ date
									+ "',pay_mode='C',pay_ac_no="
									+ int_return_value
									+ " where vch_no="
									+ array_trfvoucherobject[i].getVoucherNo()
									+ " and ac_type='"
									+ array_trfvoucherobject[i].getAccType()
									+ "' and ac_no="
									+ array_trfvoucherobject[i].getAccNo()
									+ " and vch_date='"
									+ array_trfvoucherobject[i]
											.getVoucherDate() + "'");

				}
				stmt_voucher.executeBatch();
				// int_return_value = -1;

			} else if (array_trfvoucherobject[0].getPayMode().equals("P")) {
				System.out.println("PAY ORDER MODE");
				for (int i = 0; i < array_trfvoucherobject.length; i++) {
					double_total_amount += array_trfvoucherobject[i]
							.getTrfAmount();
				}
				PayOrderObject po = new PayOrderObject();
				po.setPOType("P");
				po.setPOCustType(0);
				po.setPOPayee(array_trfvoucherobject[0].getVoucherType());
				po.setPODate(date);
				po.setPOAccType(array_trfvoucherobject[0].getAccType());
				po.setPOAccNo(array_trfvoucherobject[0].getAccNo());
				po.setPOGlCode(0);
				po.setPOAmount(double_total_amount);
				po.setCommissionAmount(0);
				po.uv.setUserTml(array_trfvoucherobject[0].getTvPayInd());
				po.uv.setUserId(array_trfvoucherobject[0].getTvPrtInd());
				po.uv.setUserDate(date);
				po.uv.setVerTml(array_trfvoucherobject[0].getTvPayInd());
				po.uv.setVerId(array_trfvoucherobject[0].getTvPrtInd());
				po.uv.setVerDate(date);
				int_return_value = commonLocal.storePayOrder(po);

				System.out.println("1..");
				/** ** Updation of DepositTransaction *** */
				// Added by Riswan
				pstmt_insert = conn
						.prepareStatement("update DepositTransaction set trn_mode='P',trn_narr='Pay Order' where ac_type=? and ac_no=? and trn_type='P' and ref_no=?");
				pstmt_insert.setString(1, array_trfvoucherobject[0]
						.getAccType());
				pstmt_insert.setInt(2, array_trfvoucherobject[0].getAccNo());
				pstmt_insert
						.setInt(3, array_trfvoucherobject[0].getVoucherNo());
				// pstmt_insert.executeUpdate();

				if (pstmt_insert.executeUpdate() != 0)
					System.out.println("Tran recrd Updated..");
				else
					System.out.println("Unable to Update trn recrd..");

				System.out.println("2..");
				/**
				 * Gl for po credit
				 */
				rs_voucher = stmt_voucher
						.executeQuery("select * from GLKeyParam where ac_type='1016001' and code=1");
				if (rs_voucher.next()) {
					GLTransObject trnobj = new GLTransObject();
					trnobj.setTrnDate(date);
					trnobj.setGLType(rs_voucher.getString("gl_type"));
					trnobj.setGLCode(rs_voucher.getString("gl_code"));
					trnobj.setTrnMode("T");
					trnobj.setAmount(double_total_amount);
					trnobj.setCdind("C");
					trnobj.setAccType(array_trfvoucherobject[0].getAccType());
					trnobj.setAccNo(String.valueOf(array_trfvoucherobject[0]
							.getAccNo()));
					// trnobj.setTrnType("P");
					trnobj.setRefNo(int_return_value);
					trnobj.setTrnSeq(0);
					trnobj.setVtml(array_trfvoucherobject[0].getTvPayInd());
					trnobj.setVid(array_trfvoucherobject[0].getTvPrtInd());
					trnobj.setVDate(array_trfvoucherobject[0].getTvPayDate());
					commonLocal.storeGLTransaction(trnobj);
				}
				/**
				 * Gl for trf voucher debit
				 */
				rs_voucher = stmt_voucher
						.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = 3 and gk.ac_type = '"
								+ array_trfvoucherobject[0].getAccType()
								+ "' and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
				if (rs_voucher.next()) {
					GLTransObject trnobj = new GLTransObject();
					trnobj.setTrnDate(date);
					trnobj.setGLType(rs_voucher.getString("gk.gl_type"));
					trnobj.setGLCode(rs_voucher.getString("gk.gl_code"));
					trnobj.setTrnMode("T");
					trnobj.setAmount(double_total_amount
							* rs_voucher.getInt("mult_by"));
					trnobj.setCdind("D");
					trnobj.setAccType(array_trfvoucherobject[0].getAccType());
					trnobj.setAccNo(String.valueOf(array_trfvoucherobject[0]
							.getAccNo()));
					trnobj.setTrnType("P");
					trnobj.setRefNo(int_return_value);
					trnobj.setTrnSeq(trn_seq);
					trnobj.setVtml(array_trfvoucherobject[0].getTvPayInd());
					trnobj.setVid(array_trfvoucherobject[0].getTvPrtInd());
					trnobj.setVDate(array_trfvoucherobject[0].getTvPayDate());
					commonLocal.storeGLTransaction(trnobj);
				}
				for (int i = 0; i < array_trfvoucherobject.length; i++) {

					System.out
							.println("hey where are youuuuuuuuuuuuuuuuuuuuu PayOrder Mode--->");
					stmt_voucher
							.addBatch("update InterestTransferVoucher set vch_pay_ind='T',vch_pay_date='"
									+ date
									+ "',pay_mode='P',pay_ac_no="
									+ int_return_value
									+ " where vch_no="
									+ array_trfvoucherobject[i].getVoucherNo()
									+ " and ac_type='"
									+ array_trfvoucherobject[i].getAccType()
									+ "' and ac_no="
									+ array_trfvoucherobject[i].getAccNo()
									+ " and vch_date='"
									+ array_trfvoucherobject[i]
											.getVoucherDate() + "'");
				}
				stmt_voucher.executeBatch();
				// int_return_value = -1;
			} else if (array_trfvoucherobject[0].getPayMode().equals("T")) {
				System.out.println("TRANSFER MODE..");
				for (int i = 0; i < array_trfvoucherobject.length; i++) {
					AccountTransObject am = new AccountTransObject();
					am.setAccType(array_trfvoucherobject[i].getPayAccType());
					am.setAccNo(array_trfvoucherobject[i].getPayAccNo());
					am.setTransDate(date);
					am.setTransType("R");
					am.setTransAmount(array_trfvoucherobject[i].getTrfAmount());
					am.setTransMode("T");
					am.setTransSource(array_trfvoucherobject[i].getTvPayInd());
					am.setCdInd("C");
					am.setChqDDNo(0);
					am.setChqDDDate("");
					am.setTransNarr(array_trfvoucherobject[i].getAccType()
							+ " " + array_trfvoucherobject[i].getAccNo());
					am.setRef_No(int_return_value);
					am.setPayeeName(array_trfvoucherobject[i].getVoucherType());
					am.setCloseBal(array_trfvoucherobject[i].getTrfAmount());
					am.setLedgerPage(0);
					am.uv.setUserTml(array_trfvoucherobject[i].getTvPayInd());
					am.uv.setUserId(array_trfvoucherobject[i].getTvPrtInd());
					am.uv.setUserDate(array_trfvoucherobject[i].getTvPayDate());
					am.uv.setVerTml(array_trfvoucherobject[i].getTvPayInd());
					am.uv.setVerId(array_trfvoucherobject[i].getTvPrtInd());
					am.uv.setVerDate(array_trfvoucherobject[i].getTvPayDate());
					commonLocal.storeAccountTransaction(am);
					FrontCounterLocal front_local = front_local_home.create();
					AccountInfoObject ac_info = front_local.getAccountInfo(am
							.getAccType(), am.getAccNo());
					CustomerLocal cust_local = cust_local_home.create();
					CustomerMasterObject customer = cust_local
							.getCustomer(ac_info.getCid());
					if (customer == null)
						throw new CustomerNotFoundException();
					// int_return_value = -1;

					/** * Updation of DepositTransaction ** */
					// Added by Riswan
					pstmt_insert = conn
							.prepareStatement("update DepositTransaction set trn_narr='"
									+ "Trf  "
									+ array_trfvoucherobject[i].getPayAccType()
									+ "-"
									+ array_trfvoucherobject[0].getPayAccNo()
									+ "',trn_mode='T' where ac_type=? and ac_no=? and ref_no=?");
					pstmt_insert.setString(1, array_trfvoucherobject[i]
							.getAccType());
					pstmt_insert
							.setInt(2, array_trfvoucherobject[i].getAccNo());
					pstmt_insert.setInt(3, array_trfvoucherobject[i]
							.getVoucherNo());
					if (pstmt_insert.executeUpdate() != 0)
						System.out.println("Tran recrd Updated..");
					else
						System.out.println("Unable to Update trn recrd..");

					/**
					 * Gl for trf credit
					 */
					int code = customer.getCategory() + 1;
					rs_voucher = stmt_voucher
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = "
									+ code
									+ " and gk.ac_type = '"
									+ array_trfvoucherobject[0].getPayAccType()
									+ "' and trn_type='R' and cr_dr='C' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					if (rs_voucher.next()) {
						GLTransObject trnobj = new GLTransObject();
						trnobj.setTrnDate(date);
						trnobj.setGLType(rs_voucher.getString("gk.gl_type"));
						trnobj.setGLCode(rs_voucher.getString("gk.gl_code"));
						trnobj.setTrnMode("T");
						trnobj.setAmount(double_total_amount
								* rs_voucher.getInt("mult_by"));
						trnobj.setCdind("C");
						trnobj.setAccType(array_trfvoucherobject[0]
								.getPayAccType());
						trnobj.setAccNo(String
								.valueOf(array_trfvoucherobject[0]
										.getPayAccNo()));
						trnobj.setTrnType("P");
						trnobj.setRefNo(int_return_value);
						trnobj.setTrnSeq(trn_seq);
						trnobj.setVtml(array_trfvoucherobject[0].getTvPayInd());
						trnobj.setVid(array_trfvoucherobject[0].getTvPrtInd());
						trnobj.setVDate(array_trfvoucherobject[0]
								.getTvPayDate());
						// commonLocal.storeGLTransaction(trnobj);
					}

					/**
					 * Gl for trf voucher debit
					 */
					rs_voucher = stmt_voucher
							.executeQuery("select gk.gl_code,mult_by,gk.gl_type from GLKeyParam gk,GLPost gp where code = 3 and gk.ac_type = '"
									+ array_trfvoucherobject[0].getAccType()
									+ "' and trn_type='P' and cr_dr='D' and gk.ac_type = gp.ac_type and gp.gl_code = gk.gl_code");
					if (rs_voucher.next()) {
						GLTransObject trnobj = new GLTransObject();
						trnobj.setTrnDate(date);
						trnobj.setGLType(rs_voucher.getString("gk.gl_type"));
						trnobj.setGLCode(rs_voucher.getString("gk.gl_code"));
						trnobj.setTrnMode("T");
						trnobj.setAmount(array_trfvoucherobject[i]
								.getTrfAmount()
								* rs_voucher.getInt("mult_by"));
						trnobj.setCdind("D");
						trnobj.setAccType(array_trfvoucherobject[0]
								.getAccType());
						trnobj.setAccNo(String
								.valueOf(array_trfvoucherobject[0].getAccNo()));
						trnobj.setTrnType("P");
						trnobj.setRefNo(int_return_value);
						trnobj.setTrnSeq(trn_seq);
						trnobj.setVtml(array_trfvoucherobject[0].getTvPayInd());
						trnobj.setVid(array_trfvoucherobject[0].getTvPrtInd());
						trnobj.setVDate(array_trfvoucherobject[0]
								.getTvPayDate());
						commonLocal.storeGLTransaction(trnobj);
					}
				}
				for (int i = 0; i < array_trfvoucherobject.length; i++) {

					System.out.println("hellooo T mode---===>>>>>");
					stmt_voucher
							.addBatch("update InterestTransferVoucher set vch_pay_ind='T',vch_pay_date='"
									+ date
									+ "',pay_mode='T',pay_ac_type='"
									+ array_trfvoucherobject[0].getPayAccType()
									+ "',pay_ac_no="
									+ array_trfvoucherobject[0].getPayAccNo()
									+ " where vch_no="
									+ array_trfvoucherobject[i].getVoucherNo()
									+ " and ac_type='"
									+ array_trfvoucherobject[i].getAccType()
									+ "' and ac_no="
									+ array_trfvoucherobject[i].getAccNo()
									+ " and vch_date='"
									+ array_trfvoucherobject[i]
											.getVoucherDate() + "'");
				}
				stmt_voucher.executeBatch();
			}

			/**
			 * Update deposit transaction for paid date
			 */
			for (int i = 0; i < array_trfvoucherobject.length; i++) {
				stmt_voucher
						.addBatch("update DepositTransaction set paid_date='"
								+ date + "' where ac_type='"
								+ array_trfvoucherobject[0].getAccType()
								+ "' and ac_no="
								+ array_trfvoucherobject[0].getAccNo()
								+ " and ref_no="
								+ array_trfvoucherobject[0].getVoucherNo());
			}
			stmt_voucher.executeBatch();

			System.out
					.println("Returning from storeDepositTransferVoucher()...");

		} catch (SQLException exception) {
			exception.printStackTrace();
			sessionContext.setRollbackOnly();
			System.out.println(exception.getMessage());
			throw new RecordNotInsertedException();
		} catch (Exception e2) {
			e2.printStackTrace();
			sessionContext.setRollbackOnly();
			System.out.println(e2.getMessage());
			throw new RecordNotInsertedException();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			commonLocal = null;
		}
		System.out.println("In bean Voucher bean====> " + int_return_value);
		return int_return_value;
	}

	public int verifyReceipts(DepositMasterObject[] object)
			throws RecordNotUpdatedException {

		int result = 0;
		Connection conn = null;

		try {
			conn = getConnection();
			PreparedStatement ps = conn
					.prepareStatement("update DepositMaster set rct_sign='T' where ac_no=? and ac_type=? ");
			for (int i = 0; i < object.length; i++) {
				ps.setInt(1, object[i].getAccNo());
				ps.setString(2, object[i].getAccType());
				ps.addBatch();
			}
			if (object.length != 0 && ps.executeBatch().length == object.length)
				return 1;
			else
				throw new RecordNotUpdatedException();

		} catch (SQLException sql) {
			sessionContext.setRollbackOnly();
			sql.printStackTrace();
		} catch (RecordNotUpdatedException rec) {
			sessionContext.setRollbackOnly();
			rec.printStackTrace();
		} catch (Exception e2) {
			e2.printStackTrace();
			sessionContext.setRollbackOnly();
		} finally {
			try {
				conn.close();
			} catch (SQLException sql) {
				sql.printStackTrace();
			}
		}
		return result;
	}

	public void setSessionContext(SessionContext ctx) throws EJBException {
		sessionContext = ctx;
	}

	public void ejbRemove() throws EJBException {
	}

	public void ejbActivate() throws EJBException {
		System.out.println("Activate");
	}

	public void ejbPassivate() throws EJBException {
		System.out.println("passivate");
	}

	/*
	 * public DepositMasterObject[] RetrievePeriodWiseReport(int srlno,String
	 * acc_type,String string_date,int type) throws
	 * RecordsNotFoundException,RemoteException {
	 * System.out.println("acc_type==>"+acc_type);
	 * System.out.println("type==>"+type); System.out.println("srlno==>"+srlno);
	 * System.out.println(" date==>"+string_date); DepositMasterObject[]
	 * array_obj_open=null; double double_from_limit=0; double
	 * double_to_limit=0,double_dep_amt=0,double_int_amt=0,double_int_paid=0;
	 * String int_upto_date=null; int j=0,cid=0,ac_no=0; Connection conn = null;
	 * try {
	 * 
	 * conn = getConnection(); Statement s1=conn.createStatement(); Statement
	 * s2=conn.createStatement(); Statement s3=conn.createStatement(); Statement
	 * s4=conn.createStatement(); Statement s5=conn.createStatement(); Statement
	 * s6=conn.createStatement(); Statement s7=conn.createStatement(); Statement
	 * s8=conn.createStatement(); Statement s9=conn.createStatement(); Statement
	 * s10=conn.createStatement(); ResultSet rs,rs1,rs2,rs3,rs4; if(type==1)
	 * rs=s1.executeQuery("Select fr_lmt,to_lmt,lmt_hdg from PeriodLimit where
	 * srl_no="+srlno+" and mod_ty='"+acc_type+"' "); else
	 * rs=s1.executeQuery("Select fr_lmt,to_lmt,lmt_hdg from PeriodLimit where
	 * srl_no="+srlno+" limit 1 ");
	 * 
	 * if(rs.next()){ double_from_limit=rs.getDouble("fr_lmt");
	 * double_to_limit=rs.getDouble("to_lmt"); } rs.beforeFirst();
	 * 
	 * s2.executeUpdate("drop table if exists dep_dat"); System.out.println("I
	 * am in type "+type); if(type==1){ System.out.println("I am in creation
	 * Process.."); s3.executeUpdate("create temporary table dep_dat as Select
	 * distinct
	 * ac_no,ac_type,m.moduleabbr,dep_date,mat_date,dep_days,dm.int_rate
	 * ,auto_renewal
	 * ,int_freq,dep_amt,mat_amt,int_mode,rct_no,close_ind,close_date
	 * ,cid,add_type from DepositMaster dm,Modules m where
	 * m.modulecode=dm.ac_type and dm.ac_type='"+acc_type+"' and dm.dep_days
	 * between "+double_from_limit+" and "+double_to_limit+" and
	 * (concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1,
	 * (locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,
	 * locate('/',close_date)-1))> '"+string_date+"' or close_date is null) and
	 * (concat(right(dep_date,4),'-',mid(dep_date,locate('/',dep_date)+1,
	 * (locate
	 * ('/',dep_date,4)-locate('/',dep_date)-1)),'-',left(dep_date,locate(
	 * '/',dep_date)-1))<='"+string_date+"') order by
	 * ac_no"); s3.executeUpdate("alter table dep_dat add
	 * Index(ac_no)"); s3.executeUpdate("alter table dep_dat add Index(cid)");
	 * s3.executeUpdate("alter table dep_dat add Index(mat_date)");
	 * System.out.println("I have altered table"); } else{
	 * s3.executeUpdate("create temporary table dep_dat as Select distinct
	 * ac_no,
	 * ac_type,m.moduleabbr,dep_date,mat_date,dep_days,dm.int_rate,auto_renewal
	 * ,int_freq
	 * ,dep_amt,mat_amt,int_mode,rct_no,close_ind,close_date,cid,add_type from
	 * DepositMaster dm,Modules m where m.modulecode=dm.ac_type and dm.dep_days
	 * between "+double_from_limit+" and "+double_to_limit+" and
	 * (concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1,
	 * (locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,
	 * locate('/',close_date)-1))> '"+string_date+"' or close_date is null) and
	 * (concat(right(dep_date,4),'-',mid(dep_date,locate('/',dep_date)+1,
	 * (locate
	 * ('/',dep_date,4)-locate('/',dep_date)-1)),'-',left(dep_date,locate(
	 * '/',dep_date)-1))<='"+string_date+"') order by
	 * m.moduleabbr,ac_no"); s3.executeUpdate("alter table dep_dat add
	 * Index(ac_no)"); s3.executeUpdate("alter table dep_dat add Index(cid)");
	 * s3.executeUpdate("alter table dep_dat add Index(mat_date)"); }
	 * System.out.println("i have created dep_dat");
	 * 
	 * s4.executeUpdate("drop table if exists cus_dat");
	 * s5.executeUpdate("create temporary table cus_dat as select distinct
	 * concat(IFNULL(fname,' '),' ',IFNULL(mname,' ' ),' ',IFNULL(lname,' ')) as
	 * name,te.cid,te.ac_no,cm.custtype,cm.sub_category from dep_dat te left
	 * join CustomerMaster as cm on te.cid=cm.cid"); s5.executeUpdate("alter
	 * table cus_dat add Index(ac_no)"); s5.executeUpdate("alter table cus_dat
	 * add Index(cid)"); System.out.println("I have created cus_dat");
	 * s6.executeUpdate("drop table if exists Accsub_dat");
	 * s7.executeUpdate("create temporary table Accsub_dat as select distinct
	 * td.ac_no,td.cid,td.sub_category,ac.subcatdesc,ac.subcatcode from cus_dat
	 * td left join AccountSubCategory as ac on td.sub_category=ac.subcatcode");
	 * s7.executeUpdate("alter table Accsub_dat add Index(ac_no)");
	 * s7.executeUpdate("alter table Accsub_dat add Index(cid)");
	 * System.out.println("i have created accsub_dat"); s8.executeUpdate("drop
	 * table if exists cusad_dat"); s9.executeUpdate("create temporary table
	 * cusad_dat as select distinct
	 * addr_type,address,city,state,pin,country,phno
	 * ,mobile,email,te.cid,te.ac_no,te.add_type from dep_dat te left join
	 * CustomerAddr as ca on te.cid=ca.cid and
	 * te.add_type=ca.addr_type"); s9.executeUpdate("alter table cusad_dat add
	 * Index(ac_no)"); s9.executeUpdate("alter table cusad_dat add Index(cid)");
	 * System.out.println("I have created cusad_dat");
	 * //rs1=s10.executeQuery("select
	 * name,dd.ac_no,moduleabbr,address,city,state
	 * ,pin,subcatdesc,custtype,dep_date
	 * ,mat_date,dep_days,int_rate,auto_renewal,
	 * int_freq,dep_amt,mat_amt,int_mode,rct_no,close_ind,close_date from
	 * dep_dat dd,cus_dat cd,Accsub_dat ad,cusad_dat cad where dd.ac_no=cd.ac_no
	 * and dd.ac_no=ad.ac_no and dd.ac_no=cad.ac_no");
	 * s10.executeUpdate("drop table if exists temp1");
	 * s10.executeUpdate("create temporary table temp1 as select distinct
	 * name,dd
	 * .ac_no,dd.ac_type,moduleabbr,custtype,dep_date,mat_date,dep_days,int_rate
	 * ,
	 * auto_renewal,int_freq,dep_amt,mat_amt,int_mode,rct_no,close_ind,close_date
	 * ,dd.cid from dep_dat dd,cus_dat cd where dd.ac_no=cd.ac_no and
	 * dd.cid=cd.cid"); s10.executeUpdate("alter table temp1 add index(ac_no)");
	 * s10.executeUpdate("alter table temp1 add index(cid)");
	 * s10.executeUpdate("drop table if exists temp2");
	 * s10.executeUpdate("create temporary table temp2 as select distinct
	 * ad.ac_no,ad.cid,address,city,state,pin,subcatdesc from Accsub_dat
	 * ad,cusad_dat cad where ad.ac_no=cad.ac_no and ad.cid=cad.cid");
	 * s10.executeUpdate("alter table temp2 add index(ac_no)");
	 * s10.executeUpdate("alter table temp2 add index(cid)");
	 * 
	 * rs1=s10.executeQuery("select distinct
	 * t1.*,address,city,state,pin,subcatdesc from temp1 t1,temp2 t2 where
	 * t1.ac_no=t2.ac_no and t1.cid=t2.cid"); System.out.println("...I have
	 * executed the last Query..."); rs1.last(); //////////////// >>>>> error
	 * if(rs1.getRow()==0) throw new RecordsNotFoundException();
	 * array_obj_open=new DepositMasterObject[rs1.getRow()]; rs1.beforeFirst();
	 * while( rs1.next() ) { array_obj_open[j]=new DepositMasterObject();
	 * array_obj_open[j].setName( rs1.getString("name") );
	 * array_obj_open[j].setAccNo( rs1.getInt("ac_no") );
	 * array_obj_open[j].setAccType( rs1.getString("moduleabbr") );
	 * array_obj_open[j].address.setAddress( rs1.getString("address") );
	 * array_obj_open[j].address.setCity( rs1.getString("city") );
	 * array_obj_open[j].address.setState( rs1.getString("state") );
	 * array_obj_open[j].address.setPin( rs1.getString("pin") );
	 * array_obj_open[j].setDepositType( rs1.getString("subcatdesc") );
	 * array_obj_open[j].setCategory( rs1.getInt("custtype") );
	 * array_obj_open[j].setDepDate( rs1.getString("dep_date") );
	 * array_obj_open[j].setMaturityDate( rs1.getString("mat_date") );
	 * array_obj_open[j].setDepositDays( rs1.getInt("dep_days") );
	 * array_obj_open[j].setInterestRate( rs1.getInt("int_rate") );
	 * array_obj_open[j].setAutoRenewal( rs1.getString("auto_renewal") );
	 * array_obj_open[j].setInterestFrq( rs1.getString("int_freq") );
	 * array_obj_open[j].setDepositAmt( rs1.getDouble("dep_amt") );
	 * array_obj_open[j].setMaturityAmt( rs1.getDouble("mat_amt") );
	 * array_obj_open[j].setInterestMode( rs1.getString("int_mode") );
	 * array_obj_open[j].setReceiptno( rs1.getInt("rct_no") );
	 * array_obj_open[j].setCloseInd( rs1.getInt("close_ind") );
	 * array_obj_open[j].setClosedt( rs1.getString("close_date") );
	 * array_obj_open[j].setCustomerId(rs1.getInt("cid"));
	 * 
	 * 
	 * rs2=s2.executeQuery("Select sum(dep_amt) as dep_amt from
	 * DepositTransaction where ac_no="+rs1.getInt("ac_no")+" and
	 * ac_type='"+rs1.getString("ac_type")+"' and cd_ind='C' and trn_type='D'
	 * and
	 * concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate
	 * ('/'
	 * ,trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date
	 * )-1))<='"+string_date+"'
	 * "); while(rs2.next()) { double_dep_amt=rs2.getDouble("dep_amt"); }
	 * 
	 * rs2=s2.executeQuery("Select sum(int_amt) as int_amt from
	 * DepositTransaction where ac_no="+rs1.getInt("ac_no")+" and
	 * ac_type='"+rs1.getString("ac_type")+"' and cd_ind='C' and trn_type='I'
	 * and
	 * concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate
	 * ('/'
	 * ,trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date
	 * )-1))<='"+string_date+"'
	 * "); while(rs2.next()) { double_int_amt=rs2.getDouble("int_amt"); }
	 * array_obj_open[j].setInterestAccured( double_int_amt);
	 * rs2=s2.executeQuery("Select sum(int_paid) as int_paid from
	 * DepositTransaction where ac_no="+rs1.getInt("ac_no")+" and
	 * ac_type='"+rs1.getString("ac_type")+"' and cd_ind='D' and trn_type='P'
	 * and
	 * concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate
	 * ('/'
	 * ,trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date
	 * )-1))<='"+string_date+"'
	 * "); while(rs2.next()) { double_int_paid=rs2.getDouble("int_paid"); }
	 * array_obj_open[j].setInterestPaid(double_int_paid);
	 * array_obj_open[j].setRDBalance
	 * (double_dep_amt+double_int_amt-double_int_paid);
	 * rs2=s2.executeQuery("Select trn_seq,int_date from DepositTransaction
	 * where ac_no="+rs1.getInt("ac_no")+" and
	 * ac_type='"+rs1.getString("ac_type")+"' and trn_type='I' and
	 * concat(right(trn_date
	 * ,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/'
	 * ,trn_date,4)-locate
	 * ('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date
	 * )-1))<='"+string_date+"' order by trn_seq desc limit 1");
	 * while(rs2.next()) { int_upto_date=rs2.getString("int_date"); }
	 * array_obj_open[j].setInterestUpto(int_upto_date); j++; }
	 * 
	 * 
	 * }catch(SQLException e1){ e1.printStackTrace(); throw new
	 * RecordsNotFoundException(); } finally{ try { conn.close(); } catch
	 * (SQLException e) { e.printStackTrace(); } } return array_obj_open; }
	 */

	/*
	 * public DepositMasterObject[] RetrieveQuantumWiseReport(int srlno,String
	 * acc_type,String string_date,int type) throws
	 * RecordsNotFoundException,RemoteException {
	 * System.out.println("acc_type==>"+acc_type);
	 * System.out.println("type==>"+type); System.out.println("srlno==>"+srlno);
	 * System.out.println(" date==>"+string_date); DepositMasterObject[]
	 * array_obj_open=null; double double_from_limit=0; double
	 * double_to_limit=0; int j=0,cid=0,ac_no=0; Connection conn = null; try {
	 * 
	 * conn = getConnection(); Statement s1=conn.createStatement(); Statement
	 * s2=conn.createStatement(); Statement s3=conn.createStatement(); Statement
	 * s4=conn.createStatement(); Statement s5=conn.createStatement(); Statement
	 * s6=conn.createStatement(); Statement s7=conn.createStatement(); Statement
	 * s8=conn.createStatement(); Statement s9=conn.createStatement(); Statement
	 * s10=conn.createStatement(); ResultSet rs,rs1,rs2,rs3; if(type==1)
	 * rs=s1.executeQuery("Select fr_lmt,to_lmt,lmt_hdg from QuantumLimit where
	 * srl_no="+srlno+" and mod_ty='"+acc_type+"' "); else
	 * rs=s1.executeQuery("Select fr_lmt,to_lmt,lmt_hdg from QuantumLimit where
	 * srl_no="+srlno+" limit 1 ");
	 * 
	 * if(rs.next()){ double_from_limit=rs.getDouble("fr_lmt");
	 * double_to_limit=rs.getDouble("to_lmt"); } rs.beforeFirst();
	 * 
	 * s2.executeUpdate("drop table if exists dep_dat"); System.out.println("I
	 * am in type "+type); if(type==1){ System.out.println("I am in creation
	 * Process.."); s3.executeUpdate("create temporary table dep_dat as Select
	 * distinct
	 * ac_no,m.moduleabbr,dep_date,mat_date,dep_days,dm.int_rate,auto_renewal
	 * ,int_freq
	 * ,dep_amt,mat_amt,int_mode,rct_no,close_ind,close_date,cid,add_type from
	 * DepositMaster dm,Modules m where m.modulecode=dm.ac_type and
	 * dm.ac_type='"+acc_type+"' and dm.dep_amt between "+double_from_limit+"
	 * and "+double_to_limit+" and
	 * (concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1,
	 * (locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,
	 * locate('/',close_date)-1))> '"+string_date+"' or close_date is null) and
	 * (concat(right(dep_date,4),'-',mid(dep_date,locate('/',dep_date)+1,
	 * (locate
	 * ('/',dep_date,4)-locate('/',dep_date)-1)),'-',left(dep_date,locate(
	 * '/',dep_date)-1))<='"+string_date+"') order by
	 * ac_no"); s3.executeUpdate("alter table dep_dat add
	 * Index(ac_no)"); s3.executeUpdate("alter table dep_dat add Index(cid)");
	 * s3.executeUpdate("alter table dep_dat add Index(mat_date)");
	 * System.out.println("I have altered table"); } else{
	 * s3.executeUpdate("create temporary table dep_dat as Select distinct
	 * ac_no,
	 * m.moduleabbr,dep_date,mat_date,dep_days,dm.int_rate,auto_renewal,int_freq
	 * ,dep_amt,mat_amt,int_mode,rct_no,close_ind,close_date,cid,add_type from
	 * DepositMaster dm,Modules m where m.modulecode=dm.ac_type and dm.dep_amt
	 * between "+double_from_limit+" and "+double_to_limit+" and
	 * (concat(right(close_date,4),'-',mid(close_date,locate('/',close_date)+1,
	 * (locate('/',close_date,4)-locate('/',close_date)-1)),'-',left(close_date,
	 * locate('/',close_date)-1))> '"+string_date+"' or close_date is null) and
	 * (concat(right(dep_date,4),'-',mid(dep_date,locate('/',dep_date)+1,
	 * (locate
	 * ('/',dep_date,4)-locate('/',dep_date)-1)),'-',left(dep_date,locate(
	 * '/',dep_date)-1))<='"+string_date+"') order by
	 * m.moduleabbr,ac_no"); s3.executeUpdate("alter table dep_dat add
	 * Index(ac_no)"); s3.executeUpdate("alter table dep_dat add Index(cid)");
	 * s3.executeUpdate("alter table dep_dat add Index(mat_date)"); }
	 * System.out.println("i have created dep_dat");
	 * 
	 * s4.executeUpdate("drop table if exists cus_dat");
	 * s5.executeUpdate("create temporary table cus_dat as select distinct
	 * concat(IFNULL(fname,' '),' ',IFNULL(mname,' ' ),' ',IFNULL(lname,' ')) as
	 * name,te.cid,te.ac_no,cm.custtype,cm.sub_category from dep_dat te left
	 * join CustomerMaster as cm on te.cid=cm.cid"); s5.executeUpdate("alter
	 * table cus_dat add Index(ac_no)"); s5.executeUpdate("alter table cus_dat
	 * add Index(cid)"); System.out.println("I have created cus_dat");
	 * s6.executeUpdate("drop table if exists Accsub_dat");
	 * s7.executeUpdate("create temporary table Accsub_dat as select distinct
	 * td.ac_no,td.cid,td.sub_category,ac.subcatdesc,ac.subcatcode from cus_dat
	 * td left join AccountSubCategory as ac on td.sub_category=ac.subcatcode");
	 * s7.executeUpdate("alter table Accsub_dat add Index(ac_no)");
	 * s7.executeUpdate("alter table Accsub_dat add Index(cid)");
	 * System.out.println("i have created accsub_dat"); s8.executeUpdate("drop
	 * table if exists cusad_dat"); s9.executeUpdate("create temporary table
	 * cusad_dat as select distinct
	 * addr_type,address,city,state,pin,country,phno
	 * ,mobile,email,te.cid,te.ac_no,te.add_type from dep_dat te left join
	 * CustomerAddr as ca on te.cid=ca.cid and
	 * te.add_type=ca.addr_type"); s9.executeUpdate("alter table cusad_dat add
	 * Index(ac_no)"); s9.executeUpdate("alter table cusad_dat add Index(cid)");
	 * System.out.println("I have created cusad_dat");
	 * //rs1=s10.executeQuery("select
	 * name,dd.ac_no,moduleabbr,address,city,state
	 * ,pin,subcatdesc,custtype,dep_date
	 * ,mat_date,dep_days,int_rate,auto_renewal,
	 * int_freq,dep_amt,mat_amt,int_mode,rct_no,close_ind,close_date from
	 * dep_dat dd,cus_dat cd,Accsub_dat ad,cusad_dat cad where dd.ac_no=cd.ac_no
	 * and dd.ac_no=ad.ac_no and dd.ac_no=cad.ac_no");
	 * s10.executeUpdate("drop table if exists temp1");
	 * s10.executeUpdate("create temporary table temp1 as select distinct
	 * name,dd
	 * .ac_no,moduleabbr,custtype,dep_date,mat_date,dep_days,int_rate,auto_renewal
	 * ,int_freq,dep_amt,mat_amt,int_mode,rct_no,close_ind,close_date,dd.cid
	 * from dep_dat dd,cus_dat cd where dd.ac_no=cd.ac_no and dd.cid=cd.cid");
	 * s10.executeUpdate("alter table temp1 add index(ac_no)");
	 * s10.executeUpdate("alter table temp1 add index(cid)");
	 * s10.executeUpdate("drop table if exists temp2");
	 * s10.executeUpdate("create temporary table temp2 as select distinct
	 * ad.ac_no,ad.cid,address,city,state,pin,subcatdesc from Accsub_dat
	 * ad,cusad_dat cad where ad.ac_no=cad.ac_no and ad.cid=cad.cid");
	 * s10.executeUpdate("alter table temp2 add index(ac_no)");
	 * s10.executeUpdate("alter table temp2 add index(cid)");
	 * 
	 * rs1=s10.executeQuery("select distinct
	 * t1.*,address,city,state,pin,subcatdesc from temp1 t1,temp2 t2 where
	 * t1.ac_no=t2.ac_no and t1.cid=t2.cid"); System.out.println("...I have
	 * executed the last Query..."); rs1.last(); //////////////// >>>>> error
	 * if(rs1.getRow()==0) throw new RecordsNotFoundException();
	 * array_obj_open=new DepositMasterObject[rs1.getRow()]; rs1.beforeFirst();
	 * while( rs1.next() ) { array_obj_open[j]=new DepositMasterObject();
	 * array_obj_open[j].setName( rs1.getString("name") );
	 * array_obj_open[j].setAccNo( rs1.getInt("ac_no") );
	 * array_obj_open[j].setAccType( rs1.getString("moduleabbr") );
	 * array_obj_open[j].address.setAddress( rs1.getString("address") );
	 * array_obj_open[j].address.setCity( rs1.getString("city") );
	 * array_obj_open[j].address.setState( rs1.getString("state") );
	 * array_obj_open[j].address.setPin( rs1.getString("pin") );
	 * array_obj_open[j].setDepositType( rs1.getString("subcatdesc") );
	 * array_obj_open[j].setCategory( rs1.getInt("custtype") );
	 * array_obj_open[j].setDepDate( rs1.getString("dep_date") );
	 * array_obj_open[j].setMaturityDate( rs1.getString("mat_date") );
	 * array_obj_open[j].setDepositDays( rs1.getInt("dep_days") );
	 * array_obj_open[j].setInterestRate( rs1.getInt("int_rate") );
	 * array_obj_open[j].setAutoRenewal( rs1.getString("auto_renewal") );
	 * array_obj_open[j].setInterestFrq( rs1.getString("int_freq") );
	 * array_obj_open[j].setDepositAmt( rs1.getDouble("dep_amt") );
	 * array_obj_open[j].setMaturityAmt( rs1.getDouble("mat_amt") );
	 * array_obj_open[j].setInterestMode( rs1.getString("int_mode") );
	 * array_obj_open[j].setReceiptno( rs1.getInt("rct_no") );
	 * array_obj_open[j].setCloseInd( rs1.getInt("close_ind") );
	 * array_obj_open[j].setClosedt( rs1.getString("close_date") ); j++; }
	 * 
	 * 
	 * }catch(SQLException e1){ e1.printStackTrace(); throw new
	 * RecordsNotFoundException(); } finally{ try { conn.close(); } catch
	 * (SQLException e) { e.printStackTrace(); } } return array_obj_open; }
	 */

	// Code added by sanjeet...
	String getSysDate() {
		Calendar c = Calendar.getInstance();
		String s = c.get(Calendar.DATE) + "/" + (c.get(Calendar.MONTH) + 1)
				+ "/" + c.get(Calendar.YEAR);
		try {
			return (Validations.checkDate(s));
		} catch (DateFormatException e) {
			e.printStackTrace();
		}
		return null;
	}

	public float InsertTermDepTran(String ac_type, int ac_no, float amt,
			String date, String cd_ind, int ref_no, String de_user,
			String de_tml, String de_date, String ve_user, String ve_tml,
			String ve_date) throws RecordsNotFoundException,
			IllegalAccessException {
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		float balance;
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			// rs=stmt.executeQuery("select distinct
			// dm.dep_amt,dt.cd_ind,dt.trn_seq from DepositMaster
			// dm,DepositTransaction dt where dt.ac_type='"+ac_type+"' and
			// dt.ac_no="+ac_no+" and dt.ac_no=dm.ac_no and
			// dt.ac_type=dm.ac_type order by dt.trn_seq");
			rs = stmt
					.executeQuery("select trn_seq,rd_bal from DepositTransaction where ac_type='"
							+ ac_type
							+ "' and ac_no="
							+ ac_no
							+ " order by trn_seq desc limit 1");
			rs.last();
			if (rs.getRow() == 0)
				throw new RecordsNotFoundException();
			else {
				rs.beforeFirst();
				rs.next();
				if (cd_ind.equalsIgnoreCase("C"))// type C ==>For CreditEntry
					pstmt = conn
							.prepareStatement("insert into DepositTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				else { // type D ==>For DebitEntry
					balance = rs.getFloat("rd_bal");
					if (balance < amt) {
						System.out
								.println("Balance is less than your debit Amount...");
						throw new IllegalAccessException(String
								.valueOf(balance));
					} else {
						pstmt = conn
								.prepareStatement("insert into DepositTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					}
				}

				pstmt.setString(1, ac_type);
				pstmt.setInt(2, ac_no);
				int trn_seq = (rs.getInt("trn_seq") + 1);
				pstmt.setInt(3, trn_seq);
				pstmt.setString(4, date);

				pstmt.setFloat(6, amt);
				float total_c = rs.getFloat("rd_bal") + amt;
				float total_d = rs.getFloat("rd_bal") - amt;

				if (cd_ind.equalsIgnoreCase("C")) {
					pstmt.setString(5, "D");
					pstmt.setFloat(10, total_c);
					pstmt.setString(17, "C");
				} else {
					pstmt.setString(5, "P");
					pstmt.setFloat(10, total_d);
					pstmt.setString(17, "D");
				}

				pstmt.setFloat(7, 0);
				pstmt.setFloat(8, 0);
				pstmt.setFloat(9, 0);

				pstmt.setString(11, null);
				pstmt.setString(12, null);
				pstmt.setInt(13, ref_no);
				pstmt.setString(14, "Voucher");
				pstmt.setString(15, "T");
				pstmt.setString(16, de_tml);

				pstmt.setFloat(18, 0);
				pstmt.setString(19, de_tml);
				pstmt.setString(20, de_user);
				pstmt.setString(21, de_date);
				pstmt.setString(22, ve_tml);
				pstmt.setString(23, ve_user);
				pstmt.setString(24, ve_date);

				stmt.executeUpdate("update DepositMaster set lst_trn_seq="
						+ trn_seq + " where ac_type='" + ac_type
						+ "' and ac_no=" + ac_no);

				float a = pstmt.executeUpdate();
				if (a == 1.0) {
					System.out.println("Successfully Inserted....");
					return a;
				} else {
					System.out.println("Unable to  Inser....");
					return 0;
				}

			}

		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
			sessionContext.setRollbackOnly();
		} catch (Exception e2) {
			e2.printStackTrace();
			sessionContext.setRollbackOnly();
		} finally {
			try {
				conn.close();
			} catch (SQLException sqlex) {
				sqlex.printStackTrace();
			}

		}
		return 0;
	}

	// code to be used only during rd closure
	public double RDInterestClosure(String actype, int accno, String date,
			double int_rate, String dep_date) throws SQLException {
		ResultSet rs_trn = null;
		Statement stmt_trn = null;
		Connection conn = null;
		String date1 = null, date2 = null;
		double int_amount = 0, dep_amt = 0;
		DepositTransactionObject[] array_balance = null;
		int no_of_days = 0, i = 0;
		try {
			conn = getConnection();
			commonLocal = commonLocalHome.create();
			stmt_trn = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			date1 = dep_date;
			array_balance = getRDReBalance(actype, accno);
			rs_trn = stmt_trn
					.executeQuery("select trn_date,dep_amt,rd_bal,cum_int,trn_seq from DepositTransaction where ac_type='"
							+ actype
							+ "' and ac_no="
							+ accno
							+ " order by trn_seq");
			if (rs_trn.next()) {
				double rdbal_cal = 0, rdbal_prd = 0;
				// dep_amt=rs_trn.getDouble("dep_amt");
				while (rs_trn.next()) {
					date2 = rs_trn.getString("trn_date");
					System.out.println("date2==>" + date2);
					rdbal_cal = array_balance[i].getRDBalance();
					System.out.println("RD_BAL==>" + rdbal_cal);
					no_of_days = commonLocal.getDaysFromTwoDate(date1, date2);
					System.out.println("days==>" + no_of_days);
					rdbal_prd += (rdbal_cal * no_of_days * int_rate) / 36500;

					date1 = date2;
					i++;
				}
				System.out.println("days==>"
						+ commonLocal.getDaysFromTwoDate(date1, date));
				System.out.println("rd_bal==>"
						+ array_balance[array_balance.length - 1]
								.getRDBalance());
				rdbal_prd += ((array_balance[array_balance.length - 1]
						.getRDBalance())
						* commonLocal.getDaysFromTwoDate(date1, date) * int_rate) / 36500;
				int_amount = Math.round(rdbal_prd);
			}
			return (int_amount);
		} catch (Exception e) {
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		} finally {
			conn.close();
			commonLocal = null;
		}
		return (0);
	}

	public DepositTransactionObject[] getRDReBalance(String ac_ty, int ac_no) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		DepositTransactionObject[] array_balance = null;
		int j = 0, i = 0;
		double rd_bal = 0, cum_int = 0;
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt
					.executeQuery("select trn_seq,trn_date,trn_type,sum(dep_amt) as dep_amt,sum(int_amt) as int_amt,sum(dep_paid) as dep_paid,sum(int_paid) as int_paid,rd_bal from DepositTransaction where ac_type='"
							+ ac_ty
							+ "' and ac_no="
							+ ac_no
							+ " and ve_tml is not null and ve_user is not null group by trn_date order by trn_seq");
			rs.last();
			if (rs.getRow() == 0)
				throw new RecordsNotFoundException();
			array_balance = new DepositTransactionObject[rs.getRow()];
			rs.beforeFirst();
			while (rs.next()) {
				array_balance[j] = new DepositTransactionObject();
				array_balance[j].setDepositAmt(rs.getDouble("dep_amt"));
				array_balance[j].setInterestAmt(rs.getDouble("int_amt"));
				array_balance[j].setDepositPaid(rs.getDouble("dep_paid"));
				array_balance[j].setInterestPaid(rs.getDouble("int_paid"));
				array_balance[j].setTranDate(rs.getString("trn_date"));
				j++;
			}

			for (i = 0; i < array_balance.length; i++) {
				rd_bal += (array_balance[i].getDepositAmt()
						+ array_balance[i].getInterestAmt()
						- array_balance[i].getDepositPaid() - array_balance[i]
						.getInterestPaid());
				array_balance[i].setRDBalance(rd_bal);
				cum_int += (array_balance[i].getInterestAmt() - array_balance[i]
						.getInterestPaid());
				array_balance[i].setCumInterest(cum_int);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return array_balance;
	}

	public String[] getQtrIntCalDate(String ac_type) {
		String[] date = null;
		ResultSet rs = null;
		Statement stmt = null;
		Connection con = null;
		System.out.println("in get combo date2" + ac_type);
		int i = 0;
		try {
			con = getConnection();
			stmt = con.createStatement();
			rs = stmt
					.executeQuery("select distinct trn_date from DepositQuarterlyInterest where ac_type like '"
							+ ac_type + "%'");
			rs.last();
			if (rs.getRow() == 0)
				return null;
			date = new String[rs.getRow()];
			rs.beforeFirst();
			while (rs.next()) {
				date[i] = rs.getString("trn_date");
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return date;

	}

	// Method for Validations like customer not found,account not found

	public int validatefields(int type, String ac_type) {

		Connection con = null;

		Statement stat = null;
		ResultSet rs = null;
		int ac_no = 0;

		try {

			con = getConnection();
			stat = con.createStatement();

			if (type == 0) {

				rs = stat
						.executeQuery("select max(cid) as cid from CustomerMaster");

				if (rs.next())
					ac_no = rs.getInt("cid");

				System.out.println("**********bean classs**cid==" + ac_no);

			}

			if (type == 1) {

				rs = stat
						.executeQuery("select lst_acc_no from Modules where ac_type= '"
								+ ac_type + "%' ");

				ac_no = rs.getInt("lst_acc_no");

				System.out.println("acno in new method====" + ac_no);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return ac_no;
	}

	// code added by geetha for CBS on 02/07/2008.

	// code for normal closure....for cbs.

	public double getNormalclosure(DepositMasterObject depmast_obj,
			String ac_type, int ac_no, int close_ind) throws SQLException,
			RemoteException {

		ResultSet rs_normal = null;
		Statement stmt_normal = null;

		Connection conn = null;
		try {
			commonLocal = commonLocalHome.create();
		} catch (CreateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		DepositMasterObject depmast = null;
		double interest_rate = 0.0, interest_amount = 0.0;
		System.out
				.println("################inside NormalClosureBean###########");

		try {

			conn = getConnection();
			stmt_normal = conn.createStatement();
			rs_normal = stmt_normal
					.executeQuery("select distinct dm.dep_date,dm.mat_date,dm.int_rate,dm.dep_amt,dm.int_upto_date,dt.int_paid from DepositMaster dm,DepositTransaction dt where dm.ac_type='"
							+ ac_type + "' and dm.ac_no=dt.ac_no");

			rs_normal.last();

			System.out.println("#####1###########");

			int int_no_of_days;
			double double_interest_amount = 0.0;

			// interest_rate = rs_normal.getDouble("int_rate");
			double dep_amt = rs_normal.getDouble("dep_amt");

			double int_rate = rs_normal.getDouble("int_rate");

			double int_paid = 0.00;// rs_normal.getDouble("int_paid");

			String dep_date = rs_normal.getString("dep_date");

			System.out.println("dep_date----------" + dep_date);
			String mat_date = rs_normal.getString("mat_date");

			System.out.println("mat_date in bean---------" + mat_date);

			System.out.println("interest rate for normal closure----"
					+ interest_rate);

			System.out.println("WHEN ACTYPE IS 1003!!!!!");
			int_no_of_days = commonLocal.getDaysFromTwoDate(dep_date, mat_date);

			System.out.println("int_no_of_days in TD Bean---> "
					+ int_no_of_days);
			// specific to FD normal closure....

			if (ac_type.startsWith("1003")) {

				if (close_ind == 90) {

					// fre_ind is the frequency indicator...
					System.out.println("Inside Normal Closure!!!");
					int fre_ind = 0;
					if (depmast_obj.getInterestFrq().equalsIgnoreCase("M"))
						fre_ind = 1;
					else if (depmast_obj.getInterestFrq().equalsIgnoreCase("Q"))
						fre_ind = 3;
					else if (depmast_obj.getInterestFrq().equalsIgnoreCase("H"))
						fre_ind = 6;
					else if (depmast_obj.getInterestFrq().equalsIgnoreCase("Y"))
						fre_ind = 12;

					else {

						double_interest_amount = Math.round((dep_amt
								* int_no_of_days * int_rate) / 36500);

						interest_amount = (Math.round(double_interest_amount
								- int_paid));

						System.out.println("interest_amount====>>>>"
								+ interest_amount);
					}

					if (fre_ind != 0) {

						String int_upto_date = Validations
								.addDays(dep_date, -1);
						System.out.println("date1 " + int_upto_date);

						StringTokenizer st = new StringTokenizer(int_upto_date,
								"/");
						int D = Integer.parseInt(st.nextToken());
						int M = Integer.parseInt(st.nextToken()) - 1;
						int Y = Integer.parseInt(st.nextToken());

						System.out.println("#####2###########");
						GregorianCalendar grgcal = new GregorianCalendar(Y, M,
								D);
						String temp_int_upto_date = null, maturity_date = Validations
								.addDays(mat_date, -1);
						int days = 0;
						while (Validations.dayCompare(int_upto_date,
								maturity_date) > 0) {

							grgcal.add(Calendar.MONTH, fre_ind);
							temp_int_upto_date = Validations.checkDate(grgcal
									.get(Calendar.DATE)
									+ "/"
									+ (grgcal.get(Calendar.MONTH) + 1)
									+ "/" + grgcal.get(Calendar.YEAR));

							System.out.println("temp int_upto_date"
									+ temp_int_upto_date);
							System.out.println("maturity date" + maturity_date);

							if (Validations.dayCompare(temp_int_upto_date,
									maturity_date) < 0)
								temp_int_upto_date = maturity_date;
							System.out.println(" Murugesh 5 no of days:"
									+ Validations.dayCompare(int_upto_date,
											temp_int_upto_date));
							double_interest_amount += Math
									.round((dep_amt
											* Validations.dayCompare(
													int_upto_date,
													temp_int_upto_date) * int_rate) / 36500);
							System.out.println(" amount=>"
									+ double_interest_amount);
							if (temp_int_upto_date
									.equalsIgnoreCase(maturity_date))
								break;
							int_upto_date = temp_int_upto_date;
						}
						System.out.println(" amount 999 ===>>>"
								+ double_interest_amount);
						if (Validations.dayCompare(rs_normal
								.getString("int_upto_date"), mat_date) != 1) {
							interest_amount = (Math
									.round(double_interest_amount - int_paid));
						} else {
							interest_amount = 0.00;
						}
					}

				}
				// end of normal closure , //int_closeind=90;
				// commented for RI
			}// end for ac_type 1003

			// for ac_type='1005001'
			else if (ac_type.startsWith("1005")) {

				System.out
						.println("entering inside RI IN BEAN INTEREST amount");
				System.out.println("depmast_obj.getMaturityAmt() "
						+ depmast_obj.getMaturityAmt());

				System.out.println("depmast_obj.getInterestPaid()"
						+ depmast_obj.getInterestPaid());
				System.out.println("depmast_obj.getDepositAmt()"
						+ depmast_obj.getDepositAmt());

				interest_amount = (Math.round(depmast_obj.getMaturityAmt()
						- depmast_obj.getInterestPaid()
						- depmast_obj.getDepositAmt()));

				System.out
						.println(" RI------interest amount" + interest_amount);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return interest_amount;
	}

	/*
	 * public int getRiClosure(String ac_type,double int_rate){
	 * 
	 * 
	 * try{
	 * 
	 * interest_amount.setText(String.valueOf(Math.round(.getMaturityAmt()-
	 * array_depositmasterobject
	 * [0].getInterestPaid()-array_depositmasterobject[0].getDepositAmt()))); }
	 * 
	 * 
	 * return; }
	 */

	// this method is for with penalty...
	public double getWithPenalty(DepositMasterObject dep_mast_obj,
			String ac_type, int ac_no, String date, int close_ind)
			throws SQLException, RemoteException {

		DepositMasterObject depmastobject = null;

		double double_interest_amt = 0.0;

		int int_no_of_days = 0;
		double interest_amount;
		double total_amount_payable = 0.0;
		double applied_inrerest_rate = 0.0;
		ResultSet rs = null;
		Connection con = null;
		Statement stmt_closure = null;

		try {
			commonLocal = commonLocalHome.create();
		} catch (CreateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out
				.println("@@@@@@@@@@@@@@@@@with penalty BEAN@@@@@@@@@@@@@@@@@");

		try {

			con = getConnection();
			stmt_closure = con.createStatement();

			rs = stmt_closure
					.executeQuery("select penalty_rate from Modules where modulecode='1003001'");
			rs.next();

			double penal_rate = rs.getDouble("penalty_rate");

			System.out.println("penalty rate--------" + penal_rate);

			String mat_date = dep_mast_obj.getMaturityDate();
			String todays_date = date;

			System.out.println("mat_date in BEAN--------->" + mat_date);
			System.out.println("todays datein BEAN-------->" + todays_date);

			if (commonLocal.getDaysFromTwoDate(mat_date, todays_date) < 0) {

				double double_interest_rate = 0;

				// for premature closure with penalty
				if (close_ind == 91) {

					System.out
							.println("*************before calling commonlocal************"
									+ dep_mast_obj.getDepDate());

					int_no_of_days = commonLocal.getDaysFromTwoDate(
							dep_mast_obj.getDepDate(), date);

					System.out
							.println("*************after calling commonlocal************");

					double array_double_interest_rate[] = getDepositInterestRate(
							ac_type, dep_mast_obj.getDPType(), dep_mast_obj
									.getCategory(), Validations
									.convertYMD(dep_mast_obj.getDepDate()),
							int_no_of_days, dep_mast_obj.getDepositAmt());

					double_interest_rate = array_double_interest_rate[0];

					if (dep_mast_obj.getExtraRateType() == 2)
						double_interest_rate += array_double_interest_rate[1];
					else if (dep_mast_obj.getExtraRateType() == 3)
						double_interest_rate += array_double_interest_rate[2];
					else if (dep_mast_obj.getExtraRateType() == 4)
						double_interest_rate += array_double_interest_rate[1]
								+ array_double_interest_rate[2];

					System.out.println("dep amount in BEAN===="
							+ dep_mast_obj.getDepositAmt() + "^^^^"
							+ int_no_of_days + "int_no_of_days"
							+ "   int rate&&&&&&" + double_interest_rate);
					//	

					{
						/**
						 * With penalty
						 */
						// System.out.println("rate==>"+array_moduleobject_fd[combo_fd_acctype.getSelectedIndex()].getPenaltyRate());
						System.out.println("double_int_rate==>>"
								+ double_interest_rate);

						// double_interest_rate -=
						// array_moduleobject_fd[combo_fd_acctype.getSelectedIndex()].getPenaltyRate();
						double_interest_rate -= penal_rate;

						System.out
								.println("--------------penalty in rate for with penalty-----"
										+ double_interest_rate);

						if (double_interest_rate < 0)
							double_interest_rate = 0;

						System.out.println("int_rate after penalty"
								+ double_interest_rate);
						applied_inrerest_rate = double_interest_rate;

						System.out.println("applied int rate************"
								+ applied_inrerest_rate);

						System.out.println("I am in calculation Process:"
								+ dep_mast_obj.getInterestFrq());
						int fre_ind = 0;
						if (dep_mast_obj.getInterestFrq().equalsIgnoreCase("M"))
							fre_ind = 1;
						else if (dep_mast_obj.getInterestFrq()
								.equalsIgnoreCase("Q"))
							fre_ind = 3;
						else if (dep_mast_obj.getInterestFrq()
								.equalsIgnoreCase("H"))
							fre_ind = 6;
						else if (dep_mast_obj.getInterestFrq()
								.equalsIgnoreCase("Y"))
							fre_ind = 12;
						else {
							double_interest_amt = Math
									.round((dep_mast_obj.getDepositAmt()
											* int_no_of_days * double_interest_rate) / 36500);
							/*
							 * if(depositmasterobject.getInterestPaid()>double_interest_amount
							 * )
							 * lbl_interest_amount.setText(String.valueOf(Math.
							 * round(double_interest_amount-depositmasterobject.
							 * getInterestPaid()))); else
							 * lbl_interest_amount.setText
							 * (String.valueOf(Math.round
							 * (double_interest_amount-
							 * depositmasterobject.getInterestPaid())));
							 */
							if (dep_mast_obj.getInterestPaid() == 0) {
								interest_amount = (Math
										.round(double_interest_amt));
							} else {
								interest_amount = (Math
										.round(double_interest_amt
												- dep_mast_obj
														.getInterestPaid()));
							}

							System.out
									.println("penalty int rate***************:"
											+ double_interest_amt);
						}

						if (fre_ind != 0) {
							String int_upto_date = Validations.addDays(
									dep_mast_obj.getDepDate(), -1);
							System.out.println("date1 " + int_upto_date);

							StringTokenizer st = new StringTokenizer(
									int_upto_date, "/");
							int D = Integer.parseInt(st.nextToken());
							int M = Integer.parseInt(st.nextToken()) - 1;
							int Y = Integer.parseInt(st.nextToken());

							GregorianCalendar grgcal = new GregorianCalendar(Y,
									M, D);
							String temp_int_upto_date = null;
							String maturity_date = Validations.addDays(
									dep_mast_obj.getMaturityDate(), -1), close_date = date;
							// mmmmmmmm
							while (Validations.dayCompare(int_upto_date,
									close_date) > 0) {
								grgcal.add(Calendar.MONTH, fre_ind);
								temp_int_upto_date = Validations
										.checkDate(grgcal.get(Calendar.DATE)
												+ "/"
												+ (grgcal.get(Calendar.MONTH) + 1)
												+ "/"
												+ grgcal.get(Calendar.YEAR));
								System.out.println("temp int_upto_date"
										+ temp_int_upto_date);
								System.out.println("close date" + close_date);
								System.out.println(" day comp "
										+ double_interest_amt);
								if (Validations.dayCompare(temp_int_upto_date,
										close_date) <= 0) {
									temp_int_upto_date = close_date;
									temp_int_upto_date = Validations.addDays(
											temp_int_upto_date, -1);
								}
								System.out.println(" Murugesh 2 no of days:"
										+ Validations.dayCompare(int_upto_date,
												temp_int_upto_date));
								double_interest_amt += Math
										.round((dep_mast_obj.getDepositAmt()
												* Validations.dayCompare(
														int_upto_date,
														temp_int_upto_date) * double_interest_rate) / 36500);
								System.out.println(" amount=>"
										+ double_interest_amt);
								if (temp_int_upto_date
										.equalsIgnoreCase(Validations.addDays(
												close_date, -1)))
									break;
								int_upto_date = temp_int_upto_date;
							}
							System.out.println(" amount 6666 ===>>>"
									+ double_interest_amt);
							/*
							 * if(depositmasterobject.getInterestPaid()>double_interest_amount
							 * )
							 * lbl_interest_amount.setText(String.valueOf(Math.
							 * round(double_interest_amount-depositmasterobject.
							 * getInterestPaid()))); else
							 * lbl_interest_amount.setText
							 * (String.valueOf(Math.round
							 * (double_interest_amount-
							 * depositmasterobject.getInterestPaid())));
							 */

							if (dep_mast_obj.getInterestPaid() == 0) {

								double interest_amount_payable = (Math
										.round(double_interest_amt
												- dep_mast_obj
														.getInterestPaid()));
								System.out.println("saaaaaa"
										+ interest_amount_payable);
								total_amount_payable = (dep_mast_obj
										.getDepositAmt() + interest_amount_payable);
								System.out.println("riiiiiii"
										+ String.valueOf((dep_mast_obj
												.getDepositAmt())
												+ interest_amount_payable));
								interest_amount = (Math
										.round(double_interest_amt));
								double_interest_amt = interest_amount_payable;
							} else {
								// geeetha
								double interest_amount_payable = (Math
										.round(double_interest_amt
												- dep_mast_obj
														.getInterestPaid()));
								System.out.println("saaaaaa"
										+ interest_amount_payable);
								interest_amount = (Math
										.round(double_interest_amt
												- dep_mast_obj
														.getInterestPaid()));
								total_amount_payable = (dep_mast_obj
										.getDepositAmt() + interest_amount_payable);
								System.out.println("riiiiiiiiiiiiiiii"
										+ String.valueOf((dep_mast_obj
												.getDepositAmt())
												+ interest_amount_payable));

								double_interest_amt = interest_amount_payable;
							}

						}
						/*
						 * lbl_close_type_selected.setText("Premature closure
						 * with Penalty"); int_closeind=91;
						 */

					}
				}

				// /
				/*
				 * double_interest_amt =
				 * Math.round((dep_mast_obj.getDepositAmt()
				 * *int_no_of_days*double_interest_rate)/36500);
				 * //double_interest_amt
				 * =Math.round(dep_mast_obj.getDepositAmt()*
				 * int_no_of_days*double_interest_rate);
				 * 
				 * System.out.println("interest
				 * amount---------->"+double_interest_amt);
				 * 
				 * 
				 * if(dep_mast_obj.getInterestPaid()==0){
				 * 
				 * System.out.println("wen interest paid ==0");
				 * 
				 * interest_amount = double_interest_amt;
				 * 
				 * 
				 * depmastobject.setInterestAccured(interest_amount);
				 * 
				 * System.out.println("interest amount-----"+interest_amount); }
				 * else{
				 * 
				 * //interest_amount = double_interest_amt -=
				 * dep_mast_obj.getInterestPaid();
				 * 
				 * System.out.println("interest
				 * amount-----"+double_interest_amt);
				 * 
				 * //depmastobject.setInterestAccured(double_interest_amt); }
				 */

				// end of premature with penalty
				/*
				 * //code for Premature closure without penalty
				 * 
				 * else if(close_ind==92){
				 * 
				 * int_no_of_days=commonLocal.getDaysFromTwoDate(dep_mast_obj.
				 * getDepDate(),date);
				 * 
				 * System.out.println("no of days--wop------"+int_no_of_days);
				 * 
				 * double array_double_interest_rate[]=
				 * getDepositInterestRate(ac_type, dep_mast_obj.getDPType(),
				 * dep_mast_obj
				 * .getCategory(),Validations.convertYMD(dep_mast_obj
				 * .getDepDate()),int_no_of_days,dep_mast_obj.getDepositAmt());
				 * 
				 * System.out.println("interest rate for without
				 * penalty------------"+array_double_interest_rate[0]);
				 * 
				 * 
				 * double_interest_rate=array_double_interest_rate[0];
				 * 
				 * System.out.println("double_interest_rate---1----"+
				 * double_interest_rate);
				 * 
				 * if(dep_mast_obj.getExtraRateType()==2) double_interest_rate
				 * += array_double_interest_rate[1]; else
				 * if(dep_mast_obj.getExtraRateType()==3) double_interest_rate
				 * += array_double_interest_rate[2]; else
				 * if(dep_mast_obj.getExtraRateType()==4) double_interest_rate
				 * +=
				 * array_double_interest_rate[1]+array_double_interest_rate[2];
				 * 
				 * System.out.println("double_interest_rate---2--------"+
				 * double_interest_rate);
				 * 
				 * 
				 * System.out.println("I am in calculation Process for WOP");
				 * int fre_ind=0;
				 * if(dep_mast_obj.getInterestFrq().equalsIgnoreCase("M"))
				 * fre_ind=1; else
				 * if(dep_mast_obj.getInterestFrq().equalsIgnoreCase("Q"))
				 * fre_ind=3; else
				 * if(dep_mast_obj.getInterestFrq().equalsIgnoreCase("H"))
				 * fre_ind=6; else
				 * if(dep_mast_obj.getInterestFrq().equalsIgnoreCase("Y"))
				 * fre_ind=12; else{
				 * 
				 * fre_ind=0; System.out.println("Hi i am one ---dep
				 * amt---"+dep_mast_obj.getDepositAmt());
				 * 
				 * 
				 * 
				 * double_interest_amt =
				 * Math.round((dep_mast_obj.getDepositAmt()
				 * *int_no_of_days*double_interest_rate)/36500);
				 * System.out.println("amt 3333 ==>"+double_interest_amt);
				 * if(depositmasterobject
				 * .getInterestPaid()>double_interest_amount)
				 * lbl_interest_amount
				 * .setText(String.valueOf(Math.round(double_interest_amount
				 * -depositmasterobject.getInterestPaid()))); else
				 * lbl_interest_amount
				 * .setText(String.valueOf(Math.round(double_interest_amount
				 * -depositmasterobject.getInterestPaid())));
				 * if(dep_mast_obj.getInterestPaid() == 0) {
				 * interest_amount=(Math.round(double_interest_amt)); } else {
				 * interest_amount
				 * =(Math.round(double_interest_amt-dep_mast_obj.getInterestPaid
				 * ())); //geethaa
				 * System.out.println("hmmmmm...."+Math.round(double_interest_amt
				 * -dep_mast_obj.getInterestPaid())); } } /////
				 * 
				 * 
				 * if(fre_ind !=0 ){ String
				 * int_upto_date=Validations.addDays(dep_mast_obj
				 * .getDepDate(),-1);
				 * System.out.println("date1 "+int_upto_date);
				 * 
				 * StringTokenizer st=new StringTokenizer(int_upto_date,"/");
				 * int D=Integer.parseInt(st.nextToken()); int
				 * M=Integer.parseInt(st.nextToken())-1; int
				 * Y=Integer.parseInt(st.nextToken());
				 * 
				 * GregorianCalendar grgcal=new GregorianCalendar(Y,M,D); String
				 * temp_int_upto_date
				 * =null,maturity_date=Validations.addDays(dep_mast_obj
				 * .getMaturityDate(),-1);
				 * if(Validations.dayCompare(date,maturity_date) > 0) {
				 * maturity_date = Validations.addDays(date,-1); } int days=0;
				 * while(Validations.dayCompare(int_upto_date,maturity_date)>0){
				 * grgcal.add(Calendar.MONTH,fre_ind);
				 * temp_int_upto_date=Validations
				 * .checkDate(grgcal.get(Calendar.DATE
				 * )+"/"+(grgcal.get(Calendar.
				 * MONTH)+1)+"/"+grgcal.get(Calendar.YEAR));
				 * System.out.println(" int_upto_date"+int_upto_date);
				 * if(Validations
				 * .dayCompare(temp_int_upto_date,maturity_date)<0) {
				 * System.out.println("maturity date"+maturity_date);
				 * temp_int_upto_date=maturity_date; } System.out.println("temp
				 * int_upto_date"+temp_int_upto_date); System.out.println("
				 * Murugesh 3 no of
				 * days:"+Validations.dayCompare(int_upto_date,temp_int_upto_date));
				 * System.out.println("checking th
				 * value....."+double_interest_amt);
				 * System.out.println("interest rate...."+double_interest_rate);
				 * double_interest_amt +=
				 * Math.round((dep_mast_obj.getDepositAmt(
				 * )*Validations.dayCompare
				 * (int_upto_date,temp_int_upto_date)*double_interest_rate
				 * )/36500);
				 * System.out.println(" amount=>"+double_interest_amt);
				 * if(temp_int_upto_date.equalsIgnoreCase(maturity_date)) break;
				 * int_upto_date=temp_int_upto_date; } System.out.println("
				 * amount 88888 ===>>>"+double_interest_amt);
				 * if(dep_mast_obj.getInterestPaid()>double_interest_amount)
				 * lbl_interest_amount
				 * .setText(String.valueOf(Math.round(double_interest_amount
				 * -dep_mast_obj.getInterestPaid()))); else
				 * lbl_interest_amount.setText
				 * (String.valueOf(Math.round(double_interest_amount
				 * -dep_mast_obj.getInterestPaid())));
				 * if(dep_mast_obj.getInterestPaid() == 0) { //geetha on
				 * 31/jan/08. double
				 * interest_amount_payable=Math.round(double_interest_amt);
				 * System.out.println("gaaaa"+interest_amount_payable);
				 * total_amount_payable
				 * =((dep_mast_obj.getDepositAmt())+interest_amount_payable);
				 * System.out.println("maaaa" +
				 * String.valueOf((dep_mast_obj.getDepositAmt
				 * ())+interest_amount_payable));
				 * 
				 * interest_amount= (Math.round(double_interest_amt)); } else {
				 * double
				 * interest_amount_payable=(Math.round(double_interest_amt
				 * -dep_mast_obj.getInterestPaid())); total_amount_payable =
				 * (dep_mast_obj.getDepositAmt()+interest_amount_payable);
				 * System.out.println("gaaaa"+interest_amount_payable);
				 * 
				 * double_interest_amt = interest_amount_payable;
				 * System.out.println("int amount
				 * setting===="+double_interest_amt);
				 * 
				 * 
				 * //interest_amount=(Math.round(double_interest_amt-dep_mast_obj
				 * .getInterestPaid())); } } //with out penalty }
				 */
			}

			/*
			 * if(fre_ind !=0){ String
			 * int_upto_date=Validations.addDays(dep_mast_obj.getDepDate(),-1);
			 * System.out.println("date1 "+int_upto_date);
			 * 
			 * StringTokenizer st=new StringTokenizer(int_upto_date,"/"); int
			 * D=Integer.parseInt(st.nextToken()); int
			 * M=Integer.parseInt(st.nextToken())-1; int
			 * Y=Integer.parseInt(st.nextToken());
			 * 
			 * GregorianCalendar grgcal=new GregorianCalendar(Y,M,D); String
			 * temp_int_upto_date
			 * =null,maturity_date=Validations.addDays(dep_mast_obj
			 * .getMaturityDate(),-1);
			 * if(Validations.dayCompare(date,maturity_date) > 0) {
			 * maturity_date = Validations.addDays(date,-1); }
			 * 
			 * int days=0;
			 * while(Validations.dayCompare(int_upto_date,maturity_date)>0){
			 * grgcal.add(Calendar.MONTH,fre_ind);
			 * temp_int_upto_date=Validations
			 * .checkDate(grgcal.get(Calendar.DATE)
			 * +"/"+(grgcal.get(Calendar.MONTH
			 * )+1)+"/"+grgcal.get(Calendar.YEAR));
			 * System.out.println("temp int_upto_date"+temp_int_upto_date);
			 * System.out.println("maturity date"+maturity_date);
			 * if(Validations.dayCompare(temp_int_upto_date,maturity_date)<0)
			 * temp_int_upto_date=maturity_date; System.out.println(" no of
			 * days:"+Validations.dayCompare(int_upto_date,temp_int_upto_date));
			 * double_interest_amt +=
			 * Math.round((dep_mast_obj.getDepositAmt()*Validations
			 * .dayCompare(int_upto_date
			 * ,temp_int_upto_date)*double_interest_rate)/36500);
			 * System.out.println(" amount=>"+double_interest_amt);
			 * if(temp_int_upto_date.equalsIgnoreCase(maturity_date)) break;
			 * int_upto_date=temp_int_upto_date; } System.out.println(" amount
			 * 4444 ===>>>"+double_interest_amt); System.out.println(" int paid
			 * " + dep_mast_obj.getInterestPaid()); // code changed by Murugesh
			 * on 04/07/2007
			 * 
			 * if(depositmasterobject.getInterestPaid()>double_interest_amount)
			 * lbl_interest_amount
			 * .setText(String.valueOf(Math.round(double_interest_amount
			 * -depositmasterobject.getInterestPaid()))); else
			 * lbl_interest_amount
			 * .setText(String.valueOf(Math.round(depositmasterobject
			 * .getInterestPaid()-double_interest_amount)));
			 * 
			 * if(dep_mast_obj.getInterestPaid() == 0) {
			 * interest_amount=(Math.round(double_interest_amt)); } else {
			 * interest_amount
			 * =(Math.round(double_interest_amt-dep_mast_obj.getInterestPaid
			 * ())); }
			 * 
			 * //} //("Premature closure without Penalty");
			 */

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {

				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return double_interest_amt;
	}

	public double getWithoutPenalty(DepositMasterObject dep_mast_obj,
			String ac_type, int ac_no, String date, int close_ind)
			throws SQLException, RemoteException {

		DepositMasterObject depmastobject = null;

		double double_interest_amt = 0.0;

		int int_no_of_days = 0;
		double interest_amount;
		double total_amount_payable = 0.0;
		double applied_inrerest_rate = 0.0;
		ResultSet rs = null;
		Connection con = null;
		Statement stmt_closure = null;

		try {
			commonLocal = commonLocalHome.create();
		} catch (CreateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out
				.println("@@@@@@@@@@@@@@@@@ without penalty BEAN @@@@@@@@@@@@@@@@@");
		System.out.println("close_ind========" + dep_mast_obj.getCloseInd());

		try {

			con = getConnection();
			stmt_closure = con.createStatement();

			rs = stmt_closure
					.executeQuery("select penalty_rate from Modules where modulecode='1003001'");
			rs.next();

			double penal_rate = rs.getDouble("penalty_rate");

			System.out.println("penalty rate--------" + penal_rate);

			String mat_date = dep_mast_obj.getMaturityDate();
			String todays_date = date;

			System.out.println("mat_date in BEAN--------->" + mat_date);
			System.out.println("todays datein BEAN-------->" + todays_date);

			if (commonLocal.getDaysFromTwoDate(mat_date, todays_date) < 0) {

				double double_interest_rate = 0;

				if (dep_mast_obj.getAccType().startsWith("1003")) {
					// code for Premature closure without penalty

					if (close_ind == 92) {

						int_no_of_days = commonLocal.getDaysFromTwoDate(
								dep_mast_obj.getDepDate(), date);

						System.out.println("no of days--wop------"
								+ int_no_of_days);

						double array_double_interest_rate[] = getDepositInterestRate(
								ac_type, dep_mast_obj.getDPType(), dep_mast_obj
										.getCategory(), Validations
										.convertYMD(dep_mast_obj.getDepDate()),
								int_no_of_days, dep_mast_obj.getDepositAmt());

						System.out
								.println("interest rate for without penalty------------"
										+ array_double_interest_rate[0]);

						double_interest_rate = array_double_interest_rate[0];

						System.out.println("double_interest_rate---1----"
								+ double_interest_rate);

						if (dep_mast_obj.getExtraRateType() == 2)
							double_interest_rate += array_double_interest_rate[1];
						else if (dep_mast_obj.getExtraRateType() == 3)
							double_interest_rate += array_double_interest_rate[2];
						else if (dep_mast_obj.getExtraRateType() == 4)
							double_interest_rate += array_double_interest_rate[1]
									+ array_double_interest_rate[2];

						System.out.println("double_interest_rate---2--------"
								+ double_interest_rate);

						System.out
								.println("I am in calculation Process for WOP");
						int fre_ind = 0;
						if (dep_mast_obj.getInterestFrq().equalsIgnoreCase("M"))
							fre_ind = 1;
						else if (dep_mast_obj.getInterestFrq()
								.equalsIgnoreCase("Q"))
							fre_ind = 3;
						else if (dep_mast_obj.getInterestFrq()
								.equalsIgnoreCase("H"))
							fre_ind = 6;
						else if (dep_mast_obj.getInterestFrq()
								.equalsIgnoreCase("Y"))
							fre_ind = 12;
						else {

							fre_ind = 0;
							System.out.println("Hi i am one ---dep amt---"
									+ dep_mast_obj.getDepositAmt());

							double_interest_amt = Math
									.round((dep_mast_obj.getDepositAmt()
											* int_no_of_days * double_interest_rate) / 36500);
							System.out.println("amt 3333 ==>"
									+ double_interest_amt);
							/*
							 * if(depositmasterobject.getInterestPaid()>double_interest_amount
							 * )
							 * lbl_interest_amount.setText(String.valueOf(Math.
							 * round(double_interest_amount-depositmasterobject.
							 * getInterestPaid()))); else
							 * lbl_interest_amount.setText
							 * (String.valueOf(Math.round
							 * (double_interest_amount-
							 * depositmasterobject.getInterestPaid())));
							 */
							if (dep_mast_obj.getInterestPaid() == 0) {
								interest_amount = (Math
										.round(double_interest_amt));
							} else {
								interest_amount = (Math
										.round(double_interest_amt
												- dep_mast_obj
														.getInterestPaid()));
								// geethaa
								System.out.println("hmmmmm...."
										+ Math.round(double_interest_amt
												- dep_mast_obj
														.getInterestPaid()));
							}
						}

						if (fre_ind != 0) {
							String int_upto_date = Validations.addDays(
									dep_mast_obj.getDepDate(), -1);
							System.out.println("date1 " + int_upto_date);

							StringTokenizer st = new StringTokenizer(
									int_upto_date, "/");
							int D = Integer.parseInt(st.nextToken());
							int M = Integer.parseInt(st.nextToken()) - 1;
							int Y = Integer.parseInt(st.nextToken());

							GregorianCalendar grgcal = new GregorianCalendar(Y,
									M, D);
							String temp_int_upto_date = null, maturity_date = Validations
									.addDays(dep_mast_obj.getMaturityDate(), -1);
							if (Validations.dayCompare(date, maturity_date) > 0) {
								maturity_date = Validations.addDays(date, -1);
							}
							int days = 0;
							while (Validations.dayCompare(int_upto_date,
									maturity_date) > 0) {
								grgcal.add(Calendar.MONTH, fre_ind);
								temp_int_upto_date = Validations
										.checkDate(grgcal.get(Calendar.DATE)
												+ "/"
												+ (grgcal.get(Calendar.MONTH) + 1)
												+ "/"
												+ grgcal.get(Calendar.YEAR));
								System.out.println(" int_upto_date"
										+ int_upto_date);
								if (Validations.dayCompare(temp_int_upto_date,
										maturity_date) < 0) {
									System.out.println("maturity date"
											+ maturity_date);
									temp_int_upto_date = maturity_date;
								}
								System.out.println("temp int_upto_date"
										+ temp_int_upto_date);
								System.out.println(" Murugesh 3 no of days:"
										+ Validations.dayCompare(int_upto_date,
												temp_int_upto_date));
								System.out.println("checking th value....."
										+ double_interest_amt);
								System.out.println("interest rate...."
										+ double_interest_rate);
								double_interest_amt += Math
										.round((dep_mast_obj.getDepositAmt()
												* Validations.dayCompare(
														int_upto_date,
														temp_int_upto_date) * double_interest_rate) / 36500);
								System.out.println(" amount=>"
										+ double_interest_amt);
								if (temp_int_upto_date
										.equalsIgnoreCase(maturity_date))
									break;
								int_upto_date = temp_int_upto_date;
							}
							System.out.println(" amount 88888 ===>>>"
									+ double_interest_amt);
							/*
							 * if(dep_mast_obj.getInterestPaid()>double_interest_amount
							 * )
							 * lbl_interest_amount.setText(String.valueOf(Math.
							 * round
							 * (double_interest_amount-dep_mast_obj.getInterestPaid
							 * ()))); else
							 * lbl_interest_amount.setText(String.valueOf
							 * (Math.round
							 * (double_interest_amount-dep_mast_obj.getInterestPaid
							 * ())));
							 */
							if (dep_mast_obj.getInterestPaid() == 0) {
								// geetha on 31/jan/08.
								double interest_amount_payable = Math
										.round(double_interest_amt);
								System.out.println("gaaaa"
										+ interest_amount_payable);
								total_amount_payable = ((dep_mast_obj
										.getDepositAmt()) + interest_amount_payable);
								System.out.println("maaaa"
										+ String.valueOf((dep_mast_obj
												.getDepositAmt())
												+ interest_amount_payable));

								interest_amount = (Math
										.round(double_interest_amt));
							} else {
								double interest_amount_payable = (Math
										.round(double_interest_amt
												- dep_mast_obj
														.getInterestPaid()));
								total_amount_payable = (dep_mast_obj
										.getDepositAmt() + interest_amount_payable);
								System.out.println("gaaaa"
										+ interest_amount_payable);

								double_interest_amt = interest_amount_payable;
								System.out.println("int amount setting===="
										+ double_interest_amt);

								// interest_amount=(Math.round(double_interest_amt-dep_mast_obj.getInterestPaid()));
							}

						}
						// with out penalty
					}

				}

			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			try {
				con.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return double_interest_amt;
	}

	public double reinvestmentCalc(double amt, String depdate, String matdate,
			double rate) throws NamingException, RemoteException,
			CreateException {

		Context ctx = getContext();
		Object obj = ctx.lookup("COMMONWEB");
		commonServer.CommonHome home = (commonServer.CommonHome) obj;
		CommonRemote cremote = home.create();
		String todate = depdate;
		String nextdate = cremote.getFutureMonthDate(todate, 3);
		double pamt = amt;
		int no_of_days = 0;
		while (cremote.getDaysFromTwoDate(nextdate, matdate) > 0) {
			no_of_days = cremote.getDaysFromTwoDate(todate, nextdate);
			pamt += ((pamt * rate * no_of_days) / (36500));
			todate = nextdate;
			nextdate = cremote.getFutureMonthDate(todate, 3);
		}
		no_of_days = cremote.getDaysFromTwoDate(todate, matdate);
		pamt += ((pamt * rate * no_of_days) / (36500));
		return Math.round(pamt);
	}

}// End of Class Body
/*
 * public double getClosureMode(DepositMasterObject depositmasterobject,String
 * ac_type,int ac_no,String date){
 * 
 * 
 * if(depositmasterobject.getCloseInd()==91||depositmasterobject.getCloseInd()==92
 * ) { int
 * int_no_of_days=commonLocal.getDaysFromTwoDate(depositmasterobject.getDepDate
 * (),date); double double_interest_rate=0; double
 * array_double_interest_rate[]=getDepositInterestRate
 * (ac_type,depositmasterobject
 * .getDPType(),depositmasterobject.getCategory(),Validations
 * .convertYMD(depositmasterobject
 * .getDepDate()),int_no_of_days,depositmasterobject.getDepositAmt()); double
 * interest_amount=0.0;
 * 
 * 
 * System.out.println("array_double_interest_rate[]"+array_double_interest_rate[0
 * ]); double_interest_rate=array_double_interest_rate[0];
 * 
 * if(depositmasterobject.getExtraRateType()==2) double_interest_rate +=
 * array_double_interest_rate[1]; else
 * if(depositmasterobject.getExtraRateType()==3) double_interest_rate +=
 * array_double_interest_rate[2]; else
 * if(depositmasterobject.getExtraRateType()==4) double_interest_rate +=
 * array_double_interest_rate[1]+array_double_interest_rate[2];
 * 
 * if(depositmasterobject.getCloseInd()==91) {
 */

/**
 * With penalty
 */
/*
 * double_interest_rate -= depositmasterobject.getPenaltyRate();
 * System.out.println
 * ("depositmasterobject.getPenaltyRate"+depositmasterobject.getPenaltyRate());
 * if(double_interest_rate < 0) double_interest_rate = 0;
 * //lbl_applied_inrerest_rate.setText(String.valueOf(double_interest_rate));
 * System.out.println("I am in calculation
 * Process--A---:"+depositmasterobject.getInterestFrq()); int fre_ind=0;
 * if(depositmasterobject.getInterestFrq().equalsIgnoreCase("M")) fre_ind=1;
 * else if(depositmasterobject.getInterestFrq().equalsIgnoreCase("Q"))
 * fre_ind=3; else
 * if(depositmasterobject.getInterestFrq().equalsIgnoreCase("H")) fre_ind=6;
 * else if(depositmasterobject.getInterestFrq().equalsIgnoreCase("Y"))
 * fre_ind=12; else{ double double_interest_amount =
 * Math.round((depositmasterobject
 * .getDepositAmt()*int_no_of_days*double_interest_rate)/36500);
 * if(depositmasterobject.getInterestPaid() == 0) { interest_amount =
 * (Math.round(double_interest_amount)); System.out.println("interest
 * amt^^^^^^^^^^^"+interest_amount);
 * 
 * 
 * //lbl_interest_amount.setText(String.valueOf(Math.round(double_interest_amount
 * ))); } else { interest_amount =
 * (Math.round(double_interest_amount-depositmasterobject.getInterestPaid()));
 * 
 * System.out.println("interest amt^^^^^^else^^^^^"+interest_amount);
 * //lbl_interest_amount
 * .setText(String.valueOf(Math.round(double_interest_amount
 * -depositmasterobject.getInterestPaid()))); }
 * 
 * 
 * double
 * double_total_amount=(depositmasterobject.getDepositAmt()+depositmasterobject
 * .getInterestPaid()-double_interest_amount);
 * 
 * //lbl_total_amount_payable.setText(String.valueOf(double_total_amount));
 * 
 * System.out.println("penalty int
 * rate***************:"+double_interest_amount); System.out.println("int
 * payable////////////"+double_total_amount); }
 * 
 * if(fre_ind !=0 ){ String
 * int_upto_date=Validations.addDays(depositmasterobject.getDepDate(),-1);
 * System.out.println("date1 "+int_upto_date);
 * 
 * StringTokenizer st=new StringTokenizer(int_upto_date,"/"); int
 * D=Integer.parseInt(st.nextToken()); int M=Integer.parseInt(st.nextToken())-1;
 * int Y=Integer.parseInt(st.nextToken());
 * 
 * GregorianCalendar grgcal=new GregorianCalendar(Y,M,D); String
 * temp_int_upto_date
 * =null,maturity_date=Validations.addDays(depositmasterobject.
 * getMaturityDate(),-1),close_date=date;
 * while(Validations.dayCompare(int_upto_date,close_date)>0){
 * grgcal.add(Calendar.MONTH,fre_ind);
 * temp_int_upto_date=Validations.checkDate(grgcal
 * .get(Calendar.DATE)+"/"+(grgcal
 * .get(Calendar.MONTH)+1)+"/"+grgcal.get(Calendar.YEAR));
 * System.out.println("temp int_upto_date"+temp_int_upto_date);
 * System.out.println("close date"+close_date);
 * if(Validations.dayCompare(temp_int_upto_date,close_date)<=0) {
 * temp_int_upto_date=close_date; temp_int_upto_date =
 * Validations.addDays(temp_int_upto_date, -1); } System.out.println("Murugesh
 * no of days:"+Validations.dayCompare(int_upto_date,temp_int_upto_date));
 * double double_interest_amount;
 * 
 * double_interest_amount +=
 * (Math.round((depositmasterobject.getDepositAmt()*Validations
 * .dayCompare(int_upto_date,temp_int_upto_date)*double_interest_rate)/36500));
 * System.out.println(" amount=>"+double_interest_amount);
 * if(temp_int_upto_date.equalsIgnoreCase( Validations.addDays(close_date, -1)))
 * break; int_upto_date=temp_int_upto_date; System.out.println(" amount 2222
 * ===>>>"+double_interest_amount); }
 * 
 * if(depositmasterobject.getInterestPaid()>double_interest_amount)
 * lbl_interest_amount
 * .setText(String.valueOf(Math.round(double_interest_amount-depositmasterobject
 * .getInterestPaid()))); else
 * lbl_interest_amount.setText(String.valueOf(Math.round
 * (depositmasterobject.getInterestPaid()-double_interest_amount)));
 * if(depositmasterobject.getInterestPaid() == 0) {
 * 
 * 
 * //lbl_interest_amount.setText(String.valueOf(Math.round(double_interest_amount
 * ))); } else { //
 * lbl_interest_amount.setText(String.valueOf(Math.round(double_interest_amount
 * -depositmasterobject.getInterestPaid()))); } }
 * 
 * //lbl_close_type_selected.setText("Premature closure with Penalty"); } else {
 *//**
 * with out penalty
 */
/*
 * 
 * System.out.println("I am in calculation Process."); int fre_ind=0;
 * if(depositmasterobject.getInterestFrq().equalsIgnoreCase("M")) fre_ind=1;
 * else if(depositmasterobject.getInterestFrq().equalsIgnoreCase("Q"))
 * fre_ind=3; else
 * if(depositmasterobject.getInterestFrq().equalsIgnoreCase("H")) fre_ind=6;
 * else if(depositmasterobject.getInterestFrq().equalsIgnoreCase("Y"))
 * fre_ind=12; else{ fre_ind=0; System.out.println("Hi i am one"); double
 * double_interest_amount ; double_interest_amount =
 * Math.round((depositmasterobject
 * .getDepositAmt()*int_no_of_days*double_interest_rate)/36500);
 * System.out.println("amt 3333 ==>"+double_interest_amount);
 * if(depositmasterobject.getInterestPaid()>double_interest_amount)
 * lbl_interest_amount
 * .setText(String.valueOf(Math.round(double_interest_amount-depositmasterobject
 * .getInterestPaid()))); else
 * lbl_interest_amount.setText(String.valueOf(Math.round
 * (double_interest_amount-depositmasterobject.getInterestPaid())));
 * if(depositmasterobject.getInterestPaid() == 0) {
 * interest_amount=(Math.round(double_interest_amount)); } else {
 * interest_amount
 * =(Math.round(double_interest_amount-depositmasterobject.getInterestPaid()));
 * //geethaaSystem.out.println("hmmmmm...."+Math.round(double_interest_amount-
 * depositmasterobject.getInterestPaid())); } }
 * 
 * if(fre_ind !=0){ String
 * int_upto_date=Validations.addDays(depositmasterobject.getDepDate(),-1);
 * System.out.println("date1 "+int_upto_date);
 * 
 * StringTokenizer st=new StringTokenizer(int_upto_date,"/"); int
 * D=Integer.parseInt(st.nextToken()); int M=Integer.parseInt(st.nextToken())-1;
 * int Y=Integer.parseInt(st.nextToken());
 * 
 * GregorianCalendar grgcal=new GregorianCalendar(Y,M,D); String
 * temp_int_upto_date
 * =null,maturity_date=Validations.addDays(depositmasterobject.
 * getMaturityDate(),-1); if(Validations.dayCompare(date,maturity_date) > 0) {
 * maturity_date = Validations.addDays(date,-1); }
 * 
 * int days=0; while(Validations.dayCompare(int_upto_date,maturity_date)>0){
 * grgcal.add(Calendar.MONTH,fre_ind);
 * temp_int_upto_date=Validations.checkDate(grgcal
 * .get(Calendar.DATE)+"/"+(grgcal
 * .get(Calendar.MONTH)+1)+"/"+grgcal.get(Calendar.YEAR));
 * System.out.println("temp int_upto_date"+temp_int_upto_date);
 * System.out.println("maturity date"+maturity_date);
 * if(Validations.dayCompare(temp_int_upto_date,maturity_date)<0)
 * temp_int_upto_date=maturity_date; System.out.println(" no of
 * days:"+Validations.dayCompare(int_upto_date,temp_int_upto_date));
 * 
 * double double_interest_amount =
 * Math.round((depositmasterobject.getDepositAmt(
 * )*Validations.dayCompare(int_upto_date
 * ,temp_int_upto_date)*double_interest_rate)/36500);
 * System.out.println(" amount=>"+double_interest_amount);
 * if(temp_int_upto_date.equalsIgnoreCase(maturity_date)) break;
 * int_upto_date=temp_int_upto_date; System.out.println(" amount 4444
 * ===>>>"+double_interest_amount); }
 * 
 * System.out.println(" int paid " + depositmasterobject.getInterestPaid());
 * 
 * if(depositmasterobject.getInterestPaid() == 0) {
 * interest_amount=(Math.round(double_interest_amount)); } else {
 * interest_amount
 * =(Math.round(double_interest_amount-depositmasterobject.getInterestPaid())));
 * } // }
 * //lbl_close_type_selected.setText("Premature closure without Penalty"); } }
 * 
 * 
 * 
 * return 0.0; }
 */

