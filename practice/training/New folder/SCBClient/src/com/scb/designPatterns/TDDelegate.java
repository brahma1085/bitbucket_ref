package com.scb.designPatterns;

import exceptions.AccountNotFoundException;
import exceptions.ControlNumberAttached;
import exceptions.CustomerNotFoundException;
import exceptions.DateFormatException;
import exceptions.RecordNotInsertedException;
import exceptions.RecordNotUpdatedException;
import exceptions.RecordsNotFoundException;
import exceptions.RecordsNotInsertedException;
import exceptions.ScrollNumberAttached;
import exceptions.Verified;
import general.Validations;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import loanServer.LoanHome;
import loanServer.LoanRemote;
import loansOnDepositServer.LoansOnDepositHome;
import loansOnDepositServer.LoansOnDepositRemote;
import masterObject.customer.CustomerMasterObject;
import masterObject.general.AccSubCategoryObject;
import masterObject.general.AccountObject;
import masterObject.general.ModuleObject;
import masterObject.general.NomineeObject;
import masterObject.general.SignatureInstructionObject;
import masterObject.general.TrfVoucherObject;
import masterObject.loans.LoanMasterObject;
import masterObject.loans.LoanTransactionObject;
import masterObject.loans.PriorityMasterObject;
//import masterObject.loansOnDeposit.LoanMasterObject;
import masterObject.loansOnDeposit.LoanPurposeObject;
import masterObject.loansOnDeposit.LoanReportObject;
import masterObject.termDeposit.DepositIntRate;
import masterObject.termDeposit.DepositIntRepObject;
import masterObject.termDeposit.DepositMasterObject;
import masterObject.termDeposit.DepositReportObject;
import masterObject.termDeposit.DepositTransactionObject;
//import sun.security.krb5.internal.ac;
import termDepositServer.TermDepositHome;
import termDepositServer.TermDepositRemote;

import com.scb.designPatterns.exceptions.ServiceLocatorException;
import commonServer.CommonHome;
import commonServer.CommonLocal;
import commonServer.CommonLocalHome;
import commonServer.CommonRemote;

import customerServer.CustomerHome;
import customerServer.CustomerRemote;

/**
 * Created by IntelliJ IDEA. User: user Date: Dec 1, 2007 Time: 11:53:37 AM To
 * change this template use File | Settings | File Templates.
 */

public class TDDelegate {

	private ServiceLocator servicelocator;
	private CommonRemote commonRemote;
	private CommonHome common_home;
	private CustomerRemote customerRemote;
	private TermDepositRemote termDepositRemote;
	private TermDepositHome termDepositHome;
	private CustomerHome cust_home;
	private ModuleObject[] module_object;
	private String[] details;
	private DepositMasterObject depmast;
	private DepositMasterObject[] depmast_obj;
	private DepositTransactionObject[] deptrn_obj;
	private DepositReportObject[] dep_repobj;
	private CommonLocalHome comm_local;
	private DepositIntRate depint;
	private CustomerMasterObject custObj;
	private CommonLocal comm_locremote;
	private LoansOnDepositRemote loanondeposit_remote;
	private LoansOnDepositHome loansOnDepositHome;
	private LoanRemote loans_remote;
	private LoanHome loans_Home;
	private SignatureInstructionObject[] sign_details;

	private DepositMasterObject deptrn;

	public CustomerRemote getCustomerRemote() {
		return this.customerRemote;
	}

	public CommonRemote getComRemote() {
		return this.commonRemote;
	}

	public TDDelegate() throws RemoteException, CreateException,
			ServiceLocatorException {

		try {

			System.out.println("I am in Deligateeeeeeeeeeeeeeeeeeeeeeeeeeeee");
			comm_local = (CommonLocalHome) ServiceLocator.getInstance()
					.getLocalHome("COMMONLOCALWEB", CommonLocalHome.class);
			comm_locremote = comm_local.create();
			this.termDepositHome = (TermDepositHome) ServiceLocator
					.getInstance().getRemoteHome("TERMDEPOSITWEB",
							TermDepositHome.class);
			this.termDepositRemote = termDepositHome.create();

			this.cust_home = (CustomerHome) ServiceLocator.getInstance()
					.getRemoteHome("CUSTOMERWEB", CustomerHome.class);
			this.customerRemote = cust_home.create();

			this.loansOnDepositHome = (LoansOnDepositHome) ServiceLocator
					.getInstance().getRemoteHome("LOANSONDEPOSITWEB",
							LoansOnDepositHome.class);
			this.loanondeposit_remote = loansOnDepositHome.create();

			this.loans_Home = (LoanHome) ServiceLocator.getInstance()
					.getRemoteHome("LOANSWEB", LoanHome.class);
			this.loans_remote = loans_Home.create();

			getCommonRemote();

		} catch (RemoteException re) {
			re.printStackTrace();
		} catch (ServiceLocatorException se) {
			se.printStackTrace();
		}

	}

	private CommonRemote getCommonRemote() throws RemoteException,
			CreateException, ServiceLocatorException {
		this.common_home = (CommonHome) ServiceLocator.getInstance()
				.getRemoteHome("COMMONWEB", CommonHome.class);
		this.commonRemote = common_home.create();
		return commonRemote;
	}

	public static String getSysdate() {
		Calendar c = Calendar.getInstance();
		try {
			return (Validations.checkDate(c.get(Calendar.DATE) + "/"
					+ (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR)));

		} catch (DateFormatException de) {
			de.printStackTrace();
		}
		return null;

	}

	public static String getFutureSysdateMail() {
		Calendar c = Calendar.getInstance();
		try {
			return (Validations.checkDate((c.get(Calendar.DATE) + 3) + "/"
					+ (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR)));

		} catch (DateFormatException de) {
			de.printStackTrace();
		}
		return null;

	}

	public static String getSysTime() {
		return (new Date().getHours() + ":" + new Date().getMinutes() + ":" + new Date()
				.getSeconds());
	}

	// for deposit Account type combo box
	public ModuleObject[] getModulesTypes() throws RemoteException {
		ModuleObject[] module_object = null;
		System.out.println("inside modules type.........!!");
		module_object = commonRemote.getMainModules(2,
				"1003000,1004000,1005000");
		for (ModuleObject mo : module_object) {
			System.out.println("module code is" + mo.getModuleAbbrv());
		}
		return module_object;
	}

	// for accountRENEWAL------------------
	public ModuleObject[] getModuleMethods(String actype)
			throws RemoteException {
		ModuleObject[] module_object = null;
		System.out.println("inside modules type.........!!");
		module_object = commonRemote.getMainModules(3, actype);
		System.out.println("----In Deligate for valid---> "
				+ module_object[0].getMinAmount());
		if (module_object != null) {
			for (int i = 0; i < module_object.length; i++) {
				System.out.println("The Sumanth is deligate is---> "
						+ module_object[i].getMinAmount());
			}
		}
		for (ModuleObject mo : module_object) {
			System.out.println("module code is" + mo.getModuleAbbrv());
		}
		return module_object;
	}

	public ModuleObject[] getMainModules(int a, String str)
			throws RemoteException {
		ModuleObject[] module_object = null;
		module_object = commonRemote.getMainModules(a, str);

		for (ModuleObject mo : module_object) {
			System.out.println("module code is" + mo.getModuleAbbrv());
		}
		return module_object;

	}

	// Nominee Percentage

	/*
	 * public int TDClosureDataDelete(String ac_type,int ac_no) throws
	 * RemoteException{
	 * 
	 * //int del=termDepositRemote.deletetdclosure(ac_type,ac_no); return del; }
	 */

	// for introducer account types
	public ModuleObject[] getModTypes() throws RemoteException {
		System.out.println("inside modules type!!");
		module_object = commonRemote
				.getMainModules(
						4,
						"'1001000','1002000','1007000','1003000','1004000','1005000','1006000','1018000','1014000','1015000'");
		for (ModuleObject mo : module_object) {
			System.out.println("module code is" + mo.getModuleAbbrv());
		}
		return module_object;
	}

	public String[] getdetails() {
		// String[]
		// details={"Select","Personal","Nominee","SignatureDetails","JointHolders","Loan Details","Introducer"};
		String[] details = { "Select", "Personal", "Introducer",
				"SignatureDetails", "Nominee", "JointHolders" };
		return details;

	}

	public String[] getpayMode() {

		String[] pay_mode = { "Cash", "Transfer", "Q/DD/PO" };
		return pay_mode;
	}

	public ModuleObject[] getpayAcctype() throws RemoteException {
		module_object = commonRemote.getMainModules(4,
				"'1002000','1007000','1018000'");

		for (ModuleObject mo : module_object) {
			System.out.println("module code is" + mo.getModuleAbbrv());
		}
		return module_object;

	}

	public AccSubCategoryObject[] getAccSubCategories(int no)
			throws RemoteException {

		AccSubCategoryObject[] acc = commonRemote.getAccSubCategories(no);
		return acc;
	}

	public String[] getMatCat() throws RemoteException {

		String[] combo_mat_cat = { "NONE", "Category Individual 0.0",
				"Subcategory Individual 1.0" };
		return combo_mat_cat;
	}

	public String[] getauto() throws RemoteException {
		String[] auto = { "NONE", "Deposit Amt", "Maturity Amt" };
		return auto;
	}

	public CustomerMasterObject getCustomer(int cid) throws RemoteException,
			CustomerNotFoundException {
		CustomerMasterObject custObj = null;
		System.out.println("----CID in delegate--->>>>" + cid);
		custObj = this.customerRemote.getCustomer(cid);
		System.out.println("cust id in delegate" + custObj);
		return custObj;

	}

	public String getNomineeAddress(int cid) throws RemoteException,
			CustomerNotFoundException, SQLException {

		String Address = null;
		System.out.println("----CID in Address Block delegate--->>>>" + cid);

		Address = termDepositRemote.getCustomerAddress(cid);

		System.out.println("The Address In Deligate--> " + Address);

		return Address;

	}

	public String getBOD(int cid) throws RemoteException,
			CustomerNotFoundException, SQLException {
		String bod = termDepositRemote.getBOD(cid);
		System.out.println("Date Of Birth=======" + bod);
		return bod;
	}

	public String getNomineeAddrDetails(int reg_no) throws RemoteException {
		String add = null;
		/*
		 * System.out.println("----CID in Address Block delegate--->>>>"+reg_no);
		 * 
		 * String Address=termDepositRemote.getNomineeAddress(reg_no);
		 * 
		 * System.out.println("The Address In Deligate--> "+Address);
		 */
		return add;

	}

	public String[] getInt_freq() throws RemoteException {
		String[] int_freq = { "Select", "Monthly", "Quarterly", "Halfyearly",
				"Yearly", "Onmaturity" };
		return int_freq;
	}

	public boolean checkIntPaid(String ac_type, int ac_no)
			throws RemoteException {

		boolean chkpayind = termDepositRemote.checkIntPaid(ac_type, ac_no);

		return chkpayind;

	}

	/*
	 * public static PygmyDelegate getPygmyDelegateInstance() throws
	 * ApplicationException, SystemException {
	 * 
	 * if ( pygmyinstance != null ) return pygmyinstance; else { try {
	 * 
	 * pygmyinstance = new PygmyDelegate(); return pygmyinstance;
	 * 
	 * }catch (ApplicationException e) {
	 * 
	 * throw e;
	 * 
	 * } catch (SystemException e) {
	 * 
	 * throw e; }
	 * 
	 * }
	 */

	public int getDaysFromTwoDate(String first_date, String second_date)
			throws RemoteException {

		int daysFromTwodays = commonRemote.getDaysFromTwoDate(first_date,
				second_date);
		System.out.println("The DayaFromTwoDays in dDeligate---->> "
				+ daysFromTwodays);
		return daysFromTwodays;

	}

	public double setPayableAmt(String int_freq, String dep_date,
			String mat_date, double dep_amt, double int_rate) {

		int days = 0;
		int total_days = 0;

		double interest_amt = 0.0;

		String dates = null;

		try {
			if (mat_date != null && dep_date != null)
				total_days = commonRemote
						.getDaysFromTwoDate(dep_date, mat_date);
			days = total_days;

			if (int_freq.startsWith("M")) {
				String to_date = commonRemote.getFutureMonthDate(dep_date, 1);
				days = commonRemote.getDaysFromTwoDate(dep_date, to_date);
			} else if (int_freq.startsWith("Q")) {
				System.out.println("INSIDE Quarterly DEposit Date====> "
						+ dep_date);
				String to_date = commonRemote.getFutureMonthDate(dep_date, 3);
				days = commonRemote.getDaysFromTwoDate(dep_date, to_date);
			} else if (int_freq.startsWith("H")) {
				System.out.println("INSIDE HalfYearly DEposit Date====>  "
						+ dep_date);
				String to_date = commonRemote.getFutureMonthDate(dep_date, 6);
				days = commonRemote.getDaysFromTwoDate(dep_date, to_date);
			} else if (int_freq.startsWith("Y")) {
				System.out.println("INSIDE Yearly DEposit Date====>  "
						+ dep_date);
				String to_date = commonRemote.getFutureMonthDate(dep_date, 12);
				days = commonRemote.getDaysFromTwoDate(dep_date, to_date);
			}

			if (days <= total_days) {
				interest_amt = (Math.round(dep_amt * int_rate * days) / (36500));

			}
			System.out.println("Sumnth Intinterest_amt---> " + interest_amt);

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("THE RETURN INTEREST AMT IS------> " + interest_amt);

		return interest_amt;

	}

	public double reinvestmentCalc(double amt, String depdate, String matdate,
			double rate) throws NamingException, RemoteException,
			CreateException {
		/*
		 * //changed by sumanth to COMMONWEB as it was giving exception
		 * javax.naming.NamingException: (COMMONEJBWEB) Name Not found..
		 * //CommonHome commonHome = (CommonHome)
		 * HomeFactory.getFactory().lookUpHome("COMMON"); CommonHome commonHome
		 * = (CommonHome) HomeFactory.getFactory().lookUpHome("COMMONWEB");
		 * CommonRemote commonRemote = commonHome.create();
		 */

		System.out
				.println("&&&&&&&&&&&&&&&&inside reinvestment calc validations ");

		String todate = depdate;
		String nextdate = commonRemote.getFutureMonthDate(todate, 3);
		double pamt = amt;
		int no_of_days = 0;
		while (commonRemote.getDaysFromTwoDate(nextdate, matdate) > 0) {
			no_of_days = commonRemote.getDaysFromTwoDate(todate, nextdate);
			pamt += ((pamt * rate * no_of_days) / (36500));
			todate = nextdate;
			nextdate = commonRemote.getFutureMonthDate(todate, 3);
		}
		no_of_days = commonRemote.getDaysFromTwoDate(todate, matdate);
		pamt += ((pamt * rate * no_of_days) / (36500));
		return Math.round(pamt);
	}

	public double setMaturityAmt(String ac_type, double dep_amt,
			String dep_date, String mat_date, double int_rate) {

		double mat_amt = 0.0;
		// double amount=0.0;

		System.out.println("actype in set maturity amt====" + ac_type);
		if (ac_type.equalsIgnoreCase("1003001")) {

			mat_amt = dep_amt;

			System.out.println("mat amt=====" + mat_amt);

		}
		/*
		 * if(ac_type.equalsIgnoreCase("1004001")){
		 * 
		 * try {
		 * 
		 * mat_amt =Validations.rdInterestCalculation(dep_amt, instl, r, opdate,
		 * cldate);
		 * 
		 * System.out.println("mat_amt of recurring======"+mat_amt);
		 * 
		 * 
		 * } catch (RemoteException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (NamingException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } catch
		 * (CreateException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } }
		 */
		if (ac_type.equalsIgnoreCase("1005001")) {

			try {
				mat_amt = Validations.reinvestmentCalc(dep_amt, dep_date,
						mat_date, int_rate);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CreateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("mat_amt of reinvestment======" + mat_amt);

		}

		System.out
				.println("***************inside mat_amt method****************");

		return mat_amt;
	}

	public AccountObject getAcccountDetails(String acctype, String trn_mode,
			int accno, String date) {
		AccountObject acc_object = null;
		System.out.println("accno in delegate======" + accno);
		try {
			acc_object = commonRemote
					.getAccount(trn_mode, acctype, accno, date);// for account
																// not found
			if (acc_object != null) {
				System.out.println("name of account holder-------"
						+ acc_object.getAccname());
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return acc_object;

	}

	public String getCustmerName(String acc_type, int acc_no)
			throws SQLException, RemoteException {
		String Cname = commonRemote.getAccountHolderName(acc_type, acc_no);
		System.out.println("Cname-----> " + Cname);
		return Cname;
	}

	public AccountObject[] getAccounts(String acctype, int accno, String date)
			throws RemoteException {

		AccountObject acc_object[] = null;
		try {
			acc_object = commonRemote.getAccounts(acctype, accno, date);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return acc_object;
	}

	/*
	 * private void setPayableAmount() {
	 * 
	 * try { if(acc_de) > 0 && txt_maturity_date.getText().length() > 0 ) { int
	 * days=0; int index = combo_interest_freq.getSelectedIndex(); int
	 * total_days =
	 * commonRemote.getDaysFromTwoDate(txt_deposit_date.getText(),txt_maturity_date
	 * .getText()); days = total_days; if(index == 1) { String to_date =
	 * commonRemote.getFutureMonthDate(txt_deposit_date.getText(),1); days =
	 * commonRemote.getDaysFromTwoDate(txt_deposit_date.getText(),to_date); }
	 * else if(index == 2) { String to_date =
	 * commonRemote.getFutureMonthDate(txt_deposit_date.getText(),3); days =
	 * commonRemote.getDaysFromTwoDate(txt_deposit_date.getText(),to_date); }
	 * else if(index == 3) { String to_date =
	 * commonRemote.getFutureMonthDate(txt_deposit_date.getText(),6); days =
	 * commonRemote.getDaysFromTwoDate(txt_deposit_date.getText(),to_date); }
	 * else if(index == 4) { String to_date =
	 * commonRemote.getFutureMonthDate(txt_deposit_date.getText(),12); days =
	 * commonRemote.getDaysFromTwoDate(txt_deposit_date.getText(),to_date); }
	 * if(days <= total_days) {
	 * txt_payable_amount.setText(numberFormat.format(new
	 * Double(Math.round((Double
	 * .parseDouble(txt_deposit_amount.getText())*Double
	 * .parseDouble(txt_interest_rate.getText())*days)/(36500))))); } else {
	 * combo_interest_freq.setSelectedIndex(0); } } } catch (RemoteException e)
	 * { e.printStackTrace(); } }
	 */
	// to set mat date for the text field
	public String getmat_date(int ac_no, int period_of_days)
			throws RemoteException, DateFormatException {
		String curr_date = getSysdate();
		System.out.println("inside  get mat===" + period_of_days
				+ "curr_date==" + curr_date);
		String date_add = commonRemote.getFutureDayDate(curr_date,
				period_of_days);
		System.out.println("dateaddddd====" + date_add);
		// String days =period_of_days + date_add;
		// String days1=Integer.parseInt(days);

		return date_add;
	}

	public String getFutureDayDate(String cur_date, int days)
			throws RemoteException {
		String valu = commonRemote.getFutureDayDate(cur_date, days);
		return valu;
	}

	public double[] setInterestRate(String ac_type, String dep_date,
			String mat_date, int no_of_days, double dep_amt, int cid)
			throws RemoteException, CustomerNotFoundException {
		System.out.println("ac_type" + ac_type);

		CustomerMasterObject custObj = null;
		custObj = customerRemote.getCustomer(cid);
		System.out.println("cid....." + custObj);
		int dp_cattype = custObj.getCategory();
		System.out.println("dp_cattype" + dp_cattype);
		int cat_type = custObj.getSubCategory();
		int days = 0;
		if (mat_date != null)
			days = Validations.dayCompare(dep_date, mat_date);

		System.out.println("days==" + days + " dep_amt--" + dep_amt
				+ " depdate" + dep_date + " maturityDate--" + mat_date + "");

		module_object = commonRemote.getMainModules(2,
				"'1003000','1004000','1005000'");

		double[] int_rate = termDepositRemote.getDepositInterestRate(ac_type,
				dp_cattype, cat_type, Validations.convertYMD(dep_date), days,
				dep_amt);
		for (int i = 0; i < int_rate.length; i++) {
			System.out.println("int length...." + int_rate.length);
		}

		return int_rate;
	}

	// for deleting from Database...
	public int deleteDeposit(int ac_no, String ac_type) {

		DepositMasterObject dep_delete = new DepositMasterObject();

		dep_delete.setAccType(ac_type);
		dep_delete.setAccNo(ac_no);

		int dep_delet = 0;

		try {
			System.out
					.println("**********Inside delete termdeposit***********");

			dep_delet = termDepositRemote.deleteTermDeposit(dep_delete, 0);

			System.out.println("no of records to delete---->>>" + dep_delet);

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dep_delet;

	}

	public int deleteDeposit(DepositMasterObject depositMasterObject,
			String date, int type) throws RemoteException {

		DepositMasterObject dep_delete = new DepositMasterObject();
		int dep_del = 0;

		dep_del = termDepositRemote.deleteTermDeposit(dep_delete, date, 2);

		return dep_del;
	}

	// term Deposit Closure details..

	public DepositMasterObject getMailDetails(String ac_type, String nxtpaydate)
			throws RemoteException, RecordsNotFoundException {
		DepositMasterObject depomail = null;
		System.out.println("ac_type in deligate---> " + ac_type);
		System.out.println("nxtpaydate====> " + nxtpaydate);
		try {

			depomail = termDepositRemote.mailSystem(ac_type, nxtpaydate);
			if (depomail != null) {
				System.out.println("depomail mailin i deligate---> "
						+ depomail.getEmailID());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return depomail;
	}

	public DepositMasterObject getClosureInfo(String ac_type, int ac_no,
			boolean flag) throws RemoteException, AccountNotFoundException {

		deptrn = new DepositMasterObject();
		DepositMasterObject depreinvest[] = new DepositMasterObject[1];

		LoanReportObject loanReportObject = new LoanReportObject();

		double[] depmast_intrate = null;
		String Trantemp = null;
		try {

			System.out
					.println("@@@@@@@ Delegate Method For FIXED DEPOSIT @@@@@@@@");

			System.out.println("acno===" + ac_no);
			System.out.println("ac_typ===" + ac_type);
			double int_paid = 0.0;
			if (ac_type.startsWith("1003")) {

				deptrn = termDepositRemote.getFDClosureData(ac_type, ac_no, 0);
				System.out.println("deptrn=====> get int paid===> "
						+ deptrn.getInterestPaid());
				int_paid = deptrn.getInterestPaid();
				System.out.println("The one value is------> "
						+ deptrn.getTransferType());
				System.out.println("The two value is------> "
						+ deptrn.getTrantypetemp());
				Trantemp = deptrn.getTransferType();

				System.out.println("DDDDDDDDDDDDmmmmmmmmm>  ---> " + Trantemp);
				if (deptrn.getTransferAccno() > 0) {

					System.out.println("GAjaniiiiiiiii---->");
					deptrn = termDepositRemote
							.getTranDetails(ac_type, ac_no, 0);
					System.out.println("gajaniACCCCCCCCTYPe"
							+ deptrn.getTransferAccType());
					System.out.println("GajaniAccccccccNOooooo "
							+ deptrn.getTransferAccno());
				}
				System.out.println("LLLLLLLLLLDDDDDDDD=> "
						+ deptrn.getTrantypetemp());
			} else if (ac_type.startsWith("1005")) {

				depreinvest = termDepositRemote.getReInvestmentClosureDetails(
						ac_no, ac_type, false);

			}
			System.out.println("close_ind in delegate==="
					+ deptrn.getCloseInd());

			String date = TDDelegate.getSysdate();
			int date_diff = (Validations.dayCompare(deptrn.getMaturityDate(),
					date));

			if (date_diff >= 0) {
				System.out.println("date_diff-------" + date_diff);
			}
			if (deptrn.getLoanAvailed().equalsIgnoreCase("T")) {

				loanReportObject = loanondeposit_remote.getLoanDetails(
						deptrn.getLoanAcType(), date, deptrn.getLoanAccno());
				String san_date = (loanReportObject.getSanctionDate());
				double san_amount = (loanReportObject.getSanctionedAmount());
				String ln_name = (loanReportObject.getName());
				String interest_uptodate = (loanReportObject.getIntUptoDate());
				double ln_principle_balance = (loanReportObject
						.getLoanBalance());
				// lbl_interest_balance.setText(String.valueOf(Math.round(loanReportObject.getInterestPayable())));
				double interest = (deptrn.getInterestRate())
						+ (loanReportObject.getLoanIntRate() - deptrn
								.getInterestRate());
				String to_date = Validations.addDays(date, -1);
				double interest_payable_penality = Math.round(loanReportObject
						.getLoanBalance()
						* Validations.dayCompare(
								loanReportObject.getIntUptoDate(), to_date)
						* interest / 36500);
				double interest_balance = (interest_payable_penality);
				double total_balance = (Math.round(loanReportObject
						.getLoanBalance() + (interest_balance)));
				// lbl_total_balance.setText(String.valueOf(Math.round(loanReportObject.getLoanBalance()+loanReportObject.getInterestPayable())));

				deptrn.setInt_loan_payable(interest_payable_penality);
				System.out
						.println("loanReportObject principal balnce=========="
								+ loanReportObject.getLoanBalance());
				deptrn.setLoan_reportobj(loanReportObject);

			}
			// loan availed=T

			else {

				System.out.println("Loan Not Availed!!!");
			}
			deptrn.setInterestPaid(int_paid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		deptrn.setTrantypetemp(Trantemp);
		return deptrn;
	}

	public DepositMasterObject[] getRDClosureDetails(int acno, String type,
			boolean flag) throws RecordsNotFoundException,
			AccountNotFoundException, RemoteException {
		DepositMasterObject dm[] = new DepositMasterObject[1];

		dm = termDepositRemote.getClosureDetails(acno, type, flag);

		return dm;
	}

	public DepositTransactionObject[] getRDReBal(String ac_ty, int ac_no)
			throws RemoteException {
		DepositTransactionObject[] array_balance = null;
		array_balance = termDepositRemote.getRDReBalance(ac_ty, ac_no);
		return array_balance;
	}

	public double getreinvestmentCalc(DepositMasterObject dep_reinvest[],
			double double_interest_rate) throws RemoteException,
			CreateException, NamingException {
		double total_interest_amount;
		String today_date = TDDelegate.getSysdate();
		total_interest_amount = Validations.reinvestmentCalc(
				dep_reinvest[0].getDepositAmt(), dep_reinvest[0].getDepDate(),
				today_date, double_interest_rate);
		return total_interest_amount;
	}

	public DepositMasterObject[] getReinvestmentDetails(String ac_type,
			int ac_no) throws RemoteException, AccountNotFoundException {

		DepositMasterObject dep_reinvest[] = new DepositMasterObject[1];

		try {
			System.out.println("DELEGATE::Inside getReinvestment");
			dep_reinvest = termDepositRemote.getReInvestmentClosureDetails(
					ac_no, ac_type, false);
			System.out.println("DELEGATE::::" + dep_reinvest[0]);

			String today_date = TDDelegate.getSysdate();
			double double_interest_rate;
			double applied_interest_rate;
			double total_interest_amount;
			double interest_amount;
			double interest_payable;

			/**
			 * if maturity date greater or equal to todays date then normal
			 * closure other wise premature closure
			 */
			int int_total_no_of_days = commonRemote.getDaysFromTwoDate(
					today_date, dep_reinvest[0].getMaturityDate());

			System.out.println("int_total_no_of_days----> "
					+ int_total_no_of_days);
			if (int_total_no_of_days > 0) {
				/**
				 * This is premature closure with or with out penalty. if with
				 * penalty get penalty rate from modules table. Get interest
				 * rate from getDepoistInterestRate for interest calculation set
				 * close ind w.r.o closure type.
				 */
				double_interest_rate = dep_reinvest[0].getInterestRate();
				int int_total_days = commonRemote.getDaysFromTwoDate(
						dep_reinvest[0].getDepDate(), today_date);
				double[] array_double_interest_rate = termDepositRemote
						.getDepositInterestRate(ac_type, dep_reinvest[0]
								.getDPType(), dep_reinvest[0].getCategory(),
								Validations.convertYMD(dep_reinvest[0]
										.getDepDate()), int_total_days,
								dep_reinvest[0].getDepositAmt());
				double_interest_rate = array_double_interest_rate[0];
				if (dep_reinvest[0].getExtraRateType() == 2)
					double_interest_rate += array_double_interest_rate[1];
				else if (dep_reinvest[0].getExtraRateType() == 3)
					double_interest_rate += array_double_interest_rate[2];
				else if (dep_reinvest[0].getExtraRateType() == 4)
					double_interest_rate += array_double_interest_rate[1]
							+ array_double_interest_rate[2];
				if (dep_reinvest[0].getCloseInd() == 91) {
					/**
					 * with penalty
					 */
					System.out.println("With Panalty Deligate Close ind is 91");
					// Code written in action Class
					/*
					 * System.out.println("with penalty---->"); ModuleObject
					 * array_moduleobject_moduleinfo[] =
					 * commonRemote.getMainModules(3,ac_type);
					 * double_interest_rate
					 * =double_interest_rate-array_moduleobject_moduleinfo
					 * [0].getPenaltyRate(); if(double_interest_rate < 0)
					 * double_interest_rate = 0;
					 * 
					 * 
					 * applied_interest_rate= (double_interest_rate);
					 * total_interest_amount
					 * =Validations.reinvestmentCalc(dep_reinvest
					 * [0].getDepositAmt
					 * (),dep_reinvest[0].getDepDate(),today_date
					 * ,double_interest_rate, comm_locremote);
					 * interest_amount=(total_interest_amount
					 * -dep_reinvest[0].getDepositAmt());
					 * interest_payable=(interest_amount
					 * -dep_reinvest[0].getInterestPaid()); if(interest_payable
					 * < 0){ interest_amount=(interest_payable); double
					 * total_amount
					 * =(dep_reinvest[0].getDepositAmt()+dep_reinvest
					 * [0].getInterestPaid()-interest_payable); } else{
					 * interest_amount = (interest_payable); double total_amount
					 * = total_interest_amount; }
					 */
				} else {
					/**
					 * with out penalty
					 */
					// int_closeind=92;
					System.out
							.println("with out penalty in Deligateeeeeeeeeee---->");
					/*
					 * if(double_interest_rate < 0) double_interest_rate = 0;
					 * 
					 * applied_interest_rate = (double_interest_rate);
					 * total_interest_amount
					 * =Validations.reinvestmentCalc(dep_reinvest
					 * [0].getDepositAmt(), dep_reinvest[0].getDepDate(),
					 * today_date, double_interest_rate, commonRemote);
					 * 
					 * interest_amount=(total_interest_amount-dep_reinvest[0].
					 * getDepositAmt());
					 * interest_payable=(interest_amount-dep_reinvest
					 * [0].getInterestPaid()); if(interest_payable < 0){
					 * interest_amount= (interest_payable); double
					 * total_amount=(
					 * dep_reinvest[0].getDepositAmt()+dep_reinvest
					 * [0].getInterestPaid()-interest_payable); } else{
					 * interest_amount=(interest_payable); double
					 * total_amount=total_interest_amount; }
					 */
				}
				/*
				 * total_amount = (total_amount); //lbl_close_date = today_date;
				 */
			}

			else {
				/**
				 * Normal closure
				 */
				// int_closeind=90;

				System.out.println("Inside Normal Closure---->");
				applied_interest_rate = (dep_reinvest[0].getInterestRate());
				interest_amount = (dep_reinvest[0].getMaturityAmt()
						- dep_reinvest[0].getInterestPaid() - dep_reinvest[0]
						.getDepositAmt());
				double total_amount = dep_reinvest[0].getMaturityAmt();
				total_amount = (total_amount);
				// lbl_close_date.setText(string_today_date);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return dep_reinvest;
	}

	public double getDepositIntRate(String ac_type, String dp_type,
			String cat_type, int days, double amount, int status) {

		DepositMasterObject depmast = null;

		depmast = new DepositMasterObject();

		DepositMasterObject dep_reinvestment[] = new DepositMasterObject[1];

		double[] depmast_intrate = null;

		ModuleObject[] module_object = null;

		try {

			System.out.println("----" + amount);

			if (ac_type.startsWith("1003")) {
				deptrn = termDepositRemote.getFDClosureData(ac_type,
						deptrn.getAccNo(), 0);

				System.out
						.println("dep amt after fd closure data--------------"
								+ deptrn.getDepositAmt());

			}

			else if (ac_type.startsWith("1005")) {

				dep_reinvestment[0] = new DepositMasterObject();
				if (deptrn != null) {
					System.out.println("deptrn.actype===" + ac_type
							+ "and deptrn.ac_no" + deptrn.getAccNo());

					dep_reinvestment = termDepositRemote
							.getReInvestmentClosureDetails(deptrn.getAccNo(),
									ac_type, false);

					deptrn = dep_reinvestment[0];

					System.out
							.println("dep amt after fd closure data--------------"
									+ deptrn.getDepositAmt());

				}

			}

			/*
			 * String date= deptrn.getDepDate();
			 * 
			 * System.out.println(" dep date intially=="+date);
			 */

		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (AccountNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (RecordsNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String date = deptrn.getDepDate();
		String str = date;

		StringTokenizer st = new StringTokenizer(str, "/");
		String dd = st.nextToken();
		String mm = st.nextToken();
		String yy = st.nextToken();
		str = yy + "-" + mm + "-" + dd;

		System.out.println("date after convert ymd" + str);

		String date1 = deptrn.getDepDate();
		String date2 = getSysdate();

		System.out.println("dep date===" + deptrn.getDepDate());

		System.out.println("close dat===" + getSysdate());

		int date_diff = 0;

		try {

			date_diff = commonRemote.getDaysFromTwoDate(date1, date2);
		}

		catch (RemoteException e1) {
			e1.printStackTrace();
		}

		System.out.println("date difference b/w dep date and close_date=="
				+ date_diff);

		// with penalty

		if (status == 0) {

			if (date_diff > 1) {

				try {

					depmast_intrate = termDepositRemote.getDepositInterestRate(
							ac_type, depmast.getDPType(),
							depmast.getCategory(), str, date_diff,
							depmast.getDepositAmt());

					module_object = commonRemote.getMainModules(2,
							"1003000,1004000,1005000");

					System.out.println("*******before for loop********");

					System.out.println("int rate====" + depmast_intrate[0]);

					double penal_rate = 0.0;

					for (int i = 0; i < module_object.length; i++) {
						System.out.println("module code is"
								+ module_object[i].getModuleAbbrv());

						penal_rate = module_object[i].getPenaltyRate();

						System.out
								.println("penalty rate---------" + penal_rate);

					}

					if (module_object[0].getModuleCode().equalsIgnoreCase(
							"1003001")) {

						System.out.println("if modulecode==1003001");
						depmast_intrate[0] -= penal_rate;

						System.out
								.println("applied rate of int in delegate--------"
										+ depmast_intrate[0]);

						double dep_amt = depmast.getDepositAmt();

						System.out.println("date diff====" + date_diff);

						System.out.println("dep amt==="
								+ deptrn.getDepositAmt());

						// double int_payable = interestAmount(ac_type, dp_type,
						// cat_type, days, deptrn.getDepositAmt(), status);

						// double interest_amount =
						// Math.round((deptrn.getDepositAmt()*date_diff*depmast_intrate[0])/36500);

						// System.out.println("interest amt wen status==0"+int_payable);

					}

					/*
					 * else if(mo.getModuleCode().equalsIgnoreCase("1004001")){
					 * 
					 * System.out.println("if modulecode==1004001");
					 * depmast_intrate[0] -= penal_rate;
					 * 
					 * System.out.println("applied rate of int in delegate--------"
					 * +depmast_intrate[0]); }
					 * 
					 * else
					 * if(mo.getModuleCode().equalsIgnoreCase(("1005001"))){
					 * 
					 * System.out.println("if modulecode==1005001");
					 * depmast_intrate[0] -= penal_rate;
					 * 
					 * System.out.println("applied rate of int in delegate--------"
					 * +depmast_intrate[0]); }
					 */

					// }

					System.out.println("actype === in delegate==" + ac_type);

					System.out.println(" In dep int rate------"
							+ depmast_intrate[0]);
					// return depmast_intrate;

				} catch (RemoteException e) {
					e.printStackTrace();
				}

				System.out.println("premature closure");
			} else {

				System.out.println("date diff in else==" + date_diff);

				try {

					depmast_intrate = termDepositRemote.getDepositInterestRate(
							depmast.getAccType(), depmast.getDPType(),
							depmast.getCategory(), date, date_diff,
							depmast.getDepositAmt());

					System.out.println("actype***************"
							+ depmast.getAccType());

					System.out.println("applied int rate*********" + depmast);

					for (int i = 0; i < depmast_intrate.length; i++) {

						System.out
								.println("depmast_intrate in for loop^^^^^^^^^^^^^"
										+ depmast_intrate[i] + "" + i);

					}

				} catch (RemoteException e) {
					e.printStackTrace();
				}

				System.out.println("Normal closure ");
			}

		}

		// // for without penalty closure

		else if (status == 1) {

			System.out.println("********entering wen status==1********");

			if (date_diff > 1) {

				try {

					depmast_intrate = termDepositRemote.getDepositInterestRate(
							ac_type, depmast.getDPType(),
							depmast.getCategory(), str, date_diff,
							depmast.getDepositAmt());

					System.out.println("int rate 1==" + depmast_intrate[0]);

					module_object = commonRemote.getMainModules(2,
							"1003000,1004000,1005000");

					// ModuleObject mo1;

					System.out.println("*******before for loop********");

					for (ModuleObject mo : module_object) {
						System.out.println("module code is"
								+ mo.getModuleAbbrv());

						double penal_rate = mo.getPenaltyRate();

						System.out
								.println("penalty rate---------" + penal_rate);

						if (mo.getModuleCode().equalsIgnoreCase("1003001")) {

							System.out.println("if modulecode==1003001");
							// depmast_intrate[3] -= penal_rate;

							System.out.println("int rate 11=="
									+ depmast_intrate[0]);

							System.out
									.println("applied rate of int in delegate--------"
											+ depmast_intrate[0]);
							return depmast_intrate[0];
						}

						else if (mo.getModuleCode().equalsIgnoreCase("1004001")) {

							System.out.println("if modulecode==1004001");
							// depmast_intrate[0] -= penal_rate;
							// depmast_intrate[0] = penal_rate;

							System.out
									.println("applied rate of int in delegate--------"
											+ depmast_intrate[0]);
						}

						else if (mo.getModuleCode().equalsIgnoreCase(
								("1005001"))) {

							System.out.println("if modulecode==1005001");
							// depmast_intrate[0] -= penal_rate;

							System.out
									.println("applied rate of int in delegate--------"
											+ depmast_intrate[0]);
						}

					}

					System.out.println("actype === in delegate==" + ac_type);

					System.out.println(" In dep int rate------"
							+ depmast_intrate[0]);
					// return depmast_intrate;

				} catch (RemoteException e) {
					e.printStackTrace();
				}

				System.out.println("premature closure");
				return depmast_intrate[0];
			} else {

				System.out.println("date diff in else==" + date_diff);

				try {

					depmast_intrate = termDepositRemote.getDepositInterestRate(
							depmast.getAccType(), depmast.getDPType(),
							depmast.getCategory(), date, date_diff,
							depmast.getDepositAmt());

					System.out.println("actype***************"
							+ depmast.getAccType());

					System.out.println("applied int rate*********" + depmast);

					for (int i = 0; i < depmast_intrate.length; i++) {

						System.out
								.println("depmast_intrate in for loop^^^^^^^^^^^^^"
										+ depmast_intrate[i] + "" + i);

					}

				} catch (RemoteException e) {
					e.printStackTrace();
				}

				System.out.println("closure  without penalty--------");
			}

		}

		System.out.println("depmast_intrate is^^^^^^^^without penalty^^^^^"
				+ depmast_intrate[1]);

		return depmast_intrate[0];

	}

	public double[] getDepositInterestRate(String ac_type, int dp_type,
			int cat_type, String date, int days, double amount)
			throws RemoteException {
		double arr1[] = { 0, 0, 0 };
		/*
		 * if(ac_type.equalsIgnoreCase("1003001")) {
		 * 
		 * arr1=termDepositRemote.getDepositInterestRate(ac_type,dp_type,cat_type
		 * ,
		 * date,Validations.dayCompare(txt_deposit_date.getText(),txt_maturity_date
		 * .getText()),amount)); }
		 */
		System.out.println("The date is in del;igater is ===> " + date);
		arr1 = termDepositRemote.getDepositInterestRate(ac_type, dp_type,
				cat_type, date, days, amount);

		return arr1;
	}

	/*
	 * public double interestAmount(String ac_type,String dp_type,String
	 * cat_type, int days, double amount,int status,String mat_date,String
	 * dep_date){ double int_amt=0.0; double
	 * dep_int_amount=getDepositIntRate(ac_type, dp_type, cat_type, days,
	 * amount, status); double interest_amount =
	 * Math.round((deptrn.getDepositAmt()*date_diff*depmast_intrate[0])/36500);
	 * return int_amt; }
	 */

	public double getnormalclosure(String ac_type, int ac_no, int close_ind) {

		DepositMasterObject dep_normal = null;
		double int_amount = 0;

		try {

			if (close_ind == 90) {

				int_amount = termDepositRemote.getNormalclosure(deptrn,
						ac_type, ac_no, 90);

			}

			else if (close_ind == 91) {

				System.out.println("closeind====91");

				int_amount = termDepositRemote.getWithPenalty(deptrn, ac_type,
						ac_no, getSysdate(), 91);

				System.out.println("int amount---------------->>" + int_amount);
				System.out.println("deptrn 91 ----->>" + deptrn);

				/*
				 * double int_amt = dep_normal.getInterestAccured();
				 * 
				 * System.out.println("int_amt====with penalty"+int_amt);
				 * 
				 * dep_normal.setInterestAccured(int_amt);
				 */
			}

			else if (close_ind == 92) {

				System.out.println("INSIDE closeind====92");

				int_amount = termDepositRemote.getWithoutPenalty(deptrn,
						ac_type, ac_no, getSysdate(), 92);

				System.out.println("int amount---------------->>" + int_amount);

				System.out.println("deptrn 92----->>" + deptrn);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return int_amount;

	}

	public int closureVerify(DepositMasterObject depmaster) {

		int result = 0;

		int depmast_verify = 0;
		;
		try {

			depmaster.setLoanAvailed("F");

			if (depmaster.getAccType().startsWith("1003")) {

				System.out.println("inside FD VERIFICATION BEAN");

				depmast_verify = termDepositRemote.verifiedFD(depmaster);

			}

			else if (depmaster.getAccType().startsWith("1005")) {

				System.out.println("inside RI VERIFICATION BEAN");

				depmast_verify = termDepositRemote
						.verifiedReInvestment(depmaster);
			} else if (depmaster.getAccType().startsWith("1004")) {
				System.out.println("inside RD VERIFICATION BEAN");
				depmast_verify = termDepositRemote.verifiedRD(depmaster);
			}

			if (depmaster.getTransferType().equalsIgnoreCase("C")) {
				System.out
						.println("voucher no generated====>" + depmast_verify);
				System.out
						.println("Sucessfully Verified  Note Down Voucher No :"
								+ depmast_verify);
			} else if (depmaster.getTransferType().equalsIgnoreCase("Q/DD/PO")) {
				System.out.println("Sucessfully Verified Note Down PO No :"
						+ depmast_verify);
			}

			else if (depmaster.getTransferType().equalsIgnoreCase("T")) {
				System.out.println("Sucessfully Verified and Transfered!!");
			}

			else {
				System.out.println("Account Sucessfully Verified");
			}

			System.out.println("depmaster object----" + depmast_verify);

			System.out.println("------After VERIFICATION-----");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return depmast_verify;
	}

	public DepositMasterObject getRenewalInfo(String ac_type, int ac_no,
			boolean flag) {

		deptrn = new DepositMasterObject();

		try {
			System.out.println("Entering into renewal....");
			System.out.println("ac_type====" + ac_type);
			System.out.println("ac_no====" + ac_no);

			deptrn = termDepositRemote.getRenewalInformation(ac_type, ac_no, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return deptrn;

	}

	public String[] getCombodate(String ac_type) {
		String combo_date[] = null;
		try {
			System.out.println("in get combo date1");

			if (ac_type.equalsIgnoreCase("1003001")) {
				boolean boolen_calculation = termDepositRemote
						.checkQuarter("1003000");
				System.out.println("in get combo date2");
				combo_date = termDepositRemote.getQtrIntCalDate("1003");
				System.out.println("in get combo date3");
				if (combo_date != null)
					for (int i = 0; i <= combo_date.length; i++) {

						System.out.println("combo_date=====" + combo_date[0]);
						System.out.println("combo_date=====" + combo_date[1]);
					}
			} else if (ac_type.equalsIgnoreCase("1004001")) {
				System.out.println("inside 1004001 combo date deligate--->");

				boolean boolen_calculation = termDepositRemote
						.checkQuarter("1004000");
				System.out.println("in get combo date2");
				combo_date = termDepositRemote.getQtrIntCalDate("1004");
				System.out.println("in get combo date3");
				for (int i = 0; i <= combo_date.length; i++) {

					System.out.println("combo_date=====" + combo_date[0]);
					System.out.println("combo_date=====" + combo_date[1]);
				}

			}

			else if (ac_type.startsWith("1005")) {

				boolean boolen_calculation = termDepositRemote
						.checkQuarter("1005000");
				System.out.println("in get combo date re investment");
				combo_date = termDepositRemote.getQtrIntCalDate("1005");
				System.out.println("in get combo date3");
				for (int i = 0; i <= combo_date.length; i++) {

					System.out.println("combo_date=====" + combo_date[0]);
					System.out.println("combo_date=====" + combo_date[1]);
				}

			}
			System.out.println("in get combo date4");
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return combo_date;
	}

	public DepositMasterObject[] getPassbookInfo(String ac_type, int ac_no) {

		System.out.println("ac_type==****===" + ac_type);
		System.out.println("ac_no===****==" + ac_no);

		try {

			depmast_obj = termDepositRemote.getDepositMaster(ac_type, 5, ac_no,
					0, "", "");

			System.out.println("ac_type=====" + ac_type);

		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (AccountNotFoundException e) {
			e.printStackTrace();
		}

		return depmast_obj;

	}

	public DepositTransactionObject[] getPassbooktableInfo(String ac_type,
			int ac_no) {
		try {
			deptrn_obj = termDepositRemote.getDepositTransaction(0, ac_type,
					ac_no, "", "", "", 0);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (RecordsNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("ac_type=====" + ac_type);
		return deptrn_obj;
	}

	public DepositMasterObject[] getMisReport(String ac_type, String frm_date) {

		System.out.println("inside get mis report!!!!");
		System.out.println(" from date==before===" + frm_date);

		DepositMasterObject[] dep_mis = null;
		frm_date = Validations.convertYMD(frm_date);

		System.out.println("frm_date, ymd from_date" + frm_date);

		try {

			dep_mis = termDepositRemote.getDepositMaster(ac_type, 7, 0, 0,
					frm_date, null, 0, null);
			if (dep_mis != null) {

				for (int i = 0; i < dep_mis.length; i++) {
					System.out.println("1st------" + dep_mis[0]);
					System.out.println("1st------" + dep_mis[1]);
					System.out.println("1st------" + dep_mis[2]);
					System.out.println("1st------" + dep_mis[3]);
				}
			}
			System.out.println("end of mis report..");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dep_mis;
	}

	public DepositMasterObject[] getMaturityLegerInfo(String ac_type,
			String frm_date, String to_date, int flag, int option) {
		DepositMasterObject[] dep_ledger = null;
		String Vfrm_date = convertYMDinDeligate(frm_date);
		String Vto_date = convertYMDinDeligate(to_date);
		String string_qry = "";
		flag = 0;
		System.out.println("from date in delegate==" + Vfrm_date);

		System.out.println("to date in delegate==" + Vto_date);
		try {
			System.out.println("inside maturity ledger..");

			if (option == 0) {

				System.out.println("yes u r in option=0");
				// dep_ledger=termDepositRemote.getDepositMaster(null,4,0,0,frm_date,to_date,flag,"");
				dep_ledger = termDepositRemote.getDepositMaster(ac_type, 4, 0,
						0, Vfrm_date, Vto_date, flag, "");
			} else {
				dep_ledger = termDepositRemote.getDepositMaster(ac_type, 4, 0,
						0, Vfrm_date, Vto_date, flag, "");
			}
			if (dep_ledger != null) {
				for (int i = 0; i < dep_ledger.length; i++) {

					System.out.println("outside maturity ledger..");

					System.out.println("1st------" + dep_ledger[i]);
					System.out.println("1st------" + dep_ledger[i]);
					System.out.println("1st------" + dep_ledger[i]);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dep_ledger;

	}

	public static String convertYMDinDeligate(String str) {
		StringTokenizer st = new StringTokenizer(str, "/");
		String dd = st.nextToken();
		String mm = st.nextToken();
		String yy = st.nextToken();
		return (yy + "/" + mm + "/" + dd);
	}

	public DepositTransactionObject[] getAutoRenewalList(String ac_type,
			String from_date, String to_date, int flag, int option) {

		DepositTransactionObject[] dep_auto = null;

		String str = from_date;
		String str1 = to_date;

		try {
			if (option == 0) {

				dep_auto = termDepositRemote.getAutoRenewalList(ac_type, str,
						str1, 0, flag, "");

				for (int i = 0; i < dep_auto.length; i++) {

					if (dep_auto[i].getInterestDate().equalsIgnoreCase("D")) {
						dep_auto[i].setInterestDate("Deposit Amount");
					} else {
						dep_auto[i].setInterestDate("Maturity ");
						// dep_auto[0].setInterestDate("Deposit Amount");

					}

				}
			} else {

				System.out.println(" in else loop option=====> " + option);

				dep_auto = termDepositRemote.getAutoRenewalList(ac_type, str,
						str1, 1, flag, "");

			}

			System.out.println("in getauto renewal list....."
					+ dep_auto[0].getTranDate());

		} catch (RemoteException e) {
			e.printStackTrace();

		} catch (RecordsNotFoundException e) {

			e.printStackTrace();
		}

		return dep_auto;
	}

	public DepositMasterObject[] getIntAccruedInfo(String ac_type,
			String from_date, String to_date, int flag) {

		DepositMasterObject[] depmastobj = null;
		String str = from_date;
		String str1 = to_date;

		/*
		 * from_date=Validations.convertYMD("from_date");
		 * to_date=Validations.convertYMD("to_date");
		 */

		StringTokenizer st = new StringTokenizer(str, "/");
		String dd = st.nextToken();
		String mm = st.nextToken();
		String yy = st.nextToken();
		str = yy + "-" + mm + "-" + dd;

		StringTokenizer st1 = new StringTokenizer(str1, "/");
		String dd1 = st1.nextToken();
		String mm1 = st1.nextToken();
		String yy1 = st1.nextToken();
		str1 = yy1 + "-" + mm1 + "-" + dd1;

		System.out.println("from_date in delegate==" + str);
		System.out.println("to_date in delegete==>" + str1);

		System.out.println(" inside delegate----int acrued ");

		try {

			if (flag == 0) {

				depmastobj = termDepositRemote.getDepositMaster(ac_type, 6, 0,
						0, str, str1, flag, "");

			} else
				depmastobj = termDepositRemote.getDepositMaster(ac_type, 6, 0,
						0, str, str1, 1, "");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (RecordsNotFoundException e) {
			e.printStackTrace();
		}

		return depmastobj;
	}

	public DepositReportObject[] getQuantumLimt(String ac_type, int type) {

		DepositReportObject[] dep_repobj = null;
		try {
			if (type == 1) {
				System.out.println("inside getQuantumLimit...type==" + type);
				dep_repobj = termDepositRemote.getQuantumLimit(ac_type, type);
			} else {
				System.out.println("inside getQuantumLimit..else loop.type=="
						+ type);
				dep_repobj = termDepositRemote.getQuantumLimit(null, type);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dep_repobj;
	}

	public DepositMasterObject[] getQuantumWise(String ac_type, String date,
			int type, int srlno) {

		DepositMasterObject[] depmast = null;
		String str = date;

		System.out.println("type inside quantum=" + type);
		System.out.println("date in delegate===" + date);
		System.out.println("srlno in delegate===" + srlno);

		StringTokenizer st = new StringTokenizer(str, "/");
		String dd = st.nextToken();
		String mm = st.nextToken();
		String yy = st.nextToken();
		str = yy + "-" + mm + "-" + dd;

		System.out.println("date in delegate after validations===" + str);
		try {
			if (type == 1) {

				System.out.println("type==" + type);

				depmast = termDepositRemote.getQuantumDetails(srlno, ac_type,
						str, type);

				if (depmast != null) {
					for (int i = 0; i < depmast.length; i++) {

						/*
						 * if(depmast[i].getCusttype().equalsIgnoreCase("0")){
						 * depmast[i].setCusttype("Individual"); } else{
						 * if(depmast[i].getCusttype().equalsIgnoreCase("1")){
						 * depmast[i].setCusttype("Institute"); } }
						 */

						if (depmast[i].getInterestFrq().equalsIgnoreCase("M")) {
							depmast[i].setInterestFrq("MONTHLY");
						} else if (depmast[i].getInterestFrq()
								.equalsIgnoreCase("Q")) {
							depmast[i].setInterestFrq("QUARTERLY");
						} else if (depmast[i].getInterestFrq()
								.equalsIgnoreCase("H")) {
							depmast[i].setInterestFrq("HALF YEARLY");
						} else if (depmast[i].getInterestFrq()
								.equalsIgnoreCase("YEARLY")) {
							depmast[i].setInterestFrq("YEARLY");
						} else if (depmast[i].getInterestFrq()
								.equalsIgnoreCase("O")) {
							depmast[i].setInterestFrq("ON MATURITY");
						}

						if (depmast[i].getCloseInd() == 0) {
							depmast[i].setClosedt("Opened--------");
						} else if (depmast[i].getCloseInd() != 0) {
							depmast[i].setClosedt("Closed-------");
						}
					}

					System.out.println("from_lmt===" + depmast[0].getFr_lmt());
					System.out.println("to_lmt===" + depmast[0].getTo_lmt());

				}
			} else {

				System.out.println("type==" + type);
				System.out.println("date in delegate=else loop==" + date);
				depmast = termDepositRemote.getQuantumDetails(srlno, ac_type,
						str, type);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return depmast;

	}

	public DepositMasterObject[] getPeriodLimit(String ac_type, int type) {

		DepositMasterObject[] dep_repobj = null;
		try {
			if (type == 0) {
				System.out.println("inside get period limit..");
				dep_repobj = termDepositRemote.getPeriodLimit();
			} else {
				System.out.println("inside get period limit. else loop.");
				dep_repobj = termDepositRemote.getPeriodLimit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dep_repobj;
	}

	public DepositMasterObject[] getPeriodWise(int srlno, String ac_type,
			String date, int type) {

		DepositMasterObject[] depmast = null;

		System.out.println("type==" + type);

		try {
			if (type == 1) {

				System.out.println("type if 1 srlno==" + srlno);
				System.out.println("type if 1 ac_type==" + ac_type);
				System.out.println("type if 1 date==" + date);
				System.out.println("type if 1 type==" + type);
				// Validations.convertDMY1(date);
				String date1 = Validations.convertYMD(date);
				System.out.println(" date date===> " + date1);
				System.out.println("DAte in deligate--00--> "
						+ Validations.convertYMD(date));
				depmast = termDepositRemote.getPeriodDetails(srlno, ac_type,
						date1, type);
			} else {

				System.out.println("type if 2 srlno==" + srlno);
				System.out.println("type if 2 ac_type==" + ac_type);
				System.out.println("type if 2 date==" + date);
				System.out.println("type if 2 type==" + type);
				;
				depmast = termDepositRemote.getPeriodDetails(srlno, ac_type,
						date, type);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return depmast;

	}

	public DepositReportObject[] getLimit(String acc_type, int type)
			throws RecordsNotFoundException {

		DepositReportObject[] depLimit = null;
		try {
			depLimit = termDepositRemote.getLimit(acc_type, type);
			System.out.println("--- in delkl--->>>>>." + depLimit);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return depLimit;
	}

	public DepositMasterObject[] getOpenAccounts(String ac_type,
			String from_date, String to_date, int type) {
		DepositMasterObject[] dep_open = null;
		// int j =0;

		System.out.println("Open Accounts  from date in delegate==="
				+ from_date);
		System.out.println("Open Accounts  to date in delegate===" + to_date);
		System.out.println("Open Accounts  to type in delegate===" + type);
		try {
			if (type == 1) {

				dep_open = termDepositRemote.getOpenAccounts(ac_type,
						from_date, to_date, 1, "");

				for (int i = 0; i < dep_open.length; i++) {

					System.out.println(" inside if loop --open accnts ");

					String details_address = (dep_open[i].address.getAddress()
							+ dep_open[i].address.getCity()
							+ dep_open[i].address.getPin() + dep_open[i].address
							.getCountry());
					dep_open[i].setDetails_address(details_address);

					System.out
							.println("address is****************************** "
									+ details_address);

					if (dep_open[i].getInterestFrq().equalsIgnoreCase("M")) {
						dep_open[i].setInterestFrq("MONTHLY");
					} else if (dep_open[i].getInterestFrq().equalsIgnoreCase(
							"Q")) {
						dep_open[i].setInterestFrq("QUARTERLY");
					} else if (dep_open[i].getInterestFrq().equalsIgnoreCase(
							"H")) {
						dep_open[i].setInterestFrq("HALF YEARLY");
					} else if (dep_open[i].getInterestFrq().equalsIgnoreCase(
							"Y")) {
						dep_open[i].setInterestFrq("YEARLY");
					} else if (dep_open[i].getInterestFrq().equalsIgnoreCase(
							"O")) {
						dep_open[i].setInterestFrq("ON MATURITY");
					}

					if (dep_open[i].getAutoRenewal().equalsIgnoreCase("D")) {
						dep_open[i].setAutoRenewal("Deposit Amt");
					}

					else if (dep_open[i].getAutoRenewal().equalsIgnoreCase("M")) {
						dep_open[i].setAutoRenewal("Maturity Amt");
					}

					else if (dep_open[i].getAutoRenewal().equalsIgnoreCase("N")) {
						dep_open[i].setAutoRenewal("NO");
					}

				}
			}

			else {

				System.out.println(" inside else loop i,e if type!=0");

				dep_open = termDepositRemote.getOpenAccounts(ac_type,
						from_date, to_date, 0, "");

				System.out.println("Receipt no...======"
						+ dep_open[0].getNewReceipt());

				for (int i = 0; i < dep_open.length; i++) {

					String details_address = (dep_open[i].address.getAddress()
							+ dep_open[i].address.getCity()
							+ dep_open[i].address.getPin() + dep_open[i].address
							.getCountry());
					dep_open[i].setDetails_address(details_address);

					System.out
							.println("address is****************************** "
									+ details_address);

					if (dep_open[i].getInterestFrq().equalsIgnoreCase("M")) {
						dep_open[i].setInterestFrq("Monthly");
					} else if (dep_open[i].getInterestFrq().equalsIgnoreCase(
							"Q")) {
						dep_open[i].setInterestFrq("Quarterly");
					} else if (dep_open[i].getInterestFrq().equalsIgnoreCase(
							"H")) {
						dep_open[i].setInterestFrq("Half Yearly");
					} else if (dep_open[i].getInterestFrq().equalsIgnoreCase(
							"YEARLY")) {
						dep_open[i].setInterestFrq("Yearly");
					} else if (dep_open[i].getInterestFrq().equalsIgnoreCase(
							"O")) {
						dep_open[i].setInterestFrq("On Maturity");
					}

					if (dep_open[i].getAutoRenewal().equalsIgnoreCase("D")) {
						dep_open[i].setAutoRenewal("Deposit Amt");
					}

					else if (dep_open[i].getAutoRenewal().equalsIgnoreCase("M")) {
						dep_open[i].setAutoRenewal("Maturity Amt");
					}

					else if (dep_open[i].getAutoRenewal().equalsIgnoreCase("N")) {
						dep_open[i].setAutoRenewal("No");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dep_open;
	}

	public DepositMasterObject[] getcloseAccounts(String ac_type,
			String from_date, String to_date, int type) {
		DepositMasterObject[] dep_close = null;
		int i = 0;

		System.out.println("Close accounts from date in delegate==="
				+ from_date);
		System.out.println(" Close accounts to date in delegate===" + to_date);
		System.out.println(" Close accounts to type in delegate===" + type);
		System.out.println(" for closed accounts@@@@@@@@@@@@@@@@@@@@@@@@@@@");

		try {
			if (type == 1) {

				dep_close = termDepositRemote.getClosedAccounts(ac_type,
						from_date, to_date, 0, "");
				for (i = 0; i < dep_close.length; i++) {
					String details_address = (dep_close[i].address.getCity()
							+ dep_close[i].address.getCountry() + dep_close[i].address
							.getPin());
					System.out.println(" inside if loop and adress is "
							+ details_address);
					dep_close[i].setDetails_address(details_address);
				}

			} else {

				System.out.println(" inside else loop ");

				dep_close = termDepositRemote.getClosedAccounts(ac_type,
						from_date, to_date, 0, "");

				System.out.println("no of Accounts closed====================="
						+ dep_close.length);

				for (i = 0; i < dep_close.length; i++) {

					String details_address = (dep_close[i].address.getCity() + dep_close[i].address
							.getPin());
					System.out.println(" inside if loop and adress is "
							+ details_address);
					dep_close[i].setDetails_address(details_address);

					if (dep_close[i].getInterestFrq().equalsIgnoreCase("M")) {
						dep_close[i].setInterestFrq("Monthly");
					} else if (dep_close[i].getInterestFrq().equalsIgnoreCase(
							"Q")) {
						dep_close[i].setInterestFrq("Quarterly");
					} else if (dep_close[i].getInterestFrq().equalsIgnoreCase(
							"H")) {
						dep_close[i].setInterestFrq("Half Yearly");
					} else if (dep_close[i].getInterestFrq().equalsIgnoreCase(
							"YEARLY")) {
						dep_close[i].setInterestFrq("Yearly");
					} else if (dep_close[i].getInterestFrq().equalsIgnoreCase(
							"O")) {
						dep_close[i].setInterestFrq("On Maturity");
					}

					if (dep_close[i].getAutoRenewal().equalsIgnoreCase("D")) {
						dep_close[i].setAutoRenewal("Deposit Amt");
					}

					else if (dep_close[i].getAutoRenewal()
							.equalsIgnoreCase("M")) {
						dep_close[i].setAutoRenewal("Maturity Amt");
					}

					else if (dep_close[i].getAutoRenewal()
							.equalsIgnoreCase("N")) {
						dep_close[i].setAutoRenewal("No");
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dep_close;
	}

	public DepositMasterObject[] getAllAccounts(String ac_type,
			String from_date, String to_date, int type) {
		System.out.println("I am in side getAllAccounts method===>");
		DepositMasterObject[] dep_all = null;

		try {
			if (type == 2) {

				System.out.println("-----------iam inside ALL ACCOUNTS-------");

				dep_all = termDepositRemote.getAllAccounts(ac_type, from_date,
						to_date, 2);

				System.out.println("period in days====="
						+ dep_all[0].getPeriod_in_days());

				for (int i = 0; i < dep_all.length; i++) {

					String details_address = (dep_all[i].address.getCity() + dep_all[i].address
							.getPin());
					System.out.println(" inside if loop and adress is "
							+ details_address);
					dep_all[i].setDetails_address(details_address);

					if (dep_all[i].getInterestFrq().equalsIgnoreCase("M")) {
						dep_all[i].setInterestFrq("Monthly");
					} else if (dep_all[i].getInterestFrq()
							.equalsIgnoreCase("Q")) {
						dep_all[i].setInterestFrq("Quarterly");
					} else if (dep_all[i].getInterestFrq()
							.equalsIgnoreCase("H")) {
						dep_all[i].setInterestFrq("Half Yearly");
					} else if (dep_all[i].getInterestFrq().equalsIgnoreCase(
							"YEARLY")) {
						dep_all[i].setInterestFrq("Yearly");
					} else if (dep_all[i].getInterestFrq()
							.equalsIgnoreCase("O")) {
						dep_all[i].setInterestFrq("On Maturity");
					}

					if (dep_all[i].getAutoRenewal().equalsIgnoreCase("D")) {
						dep_all[i].setAutoRenewal("Deposit Amt");
					}

					else if (dep_all[i].getAutoRenewal().equalsIgnoreCase("M")) {
						dep_all[i].setAutoRenewal("Maturity Amt");
					}

					else if (dep_all[i].getAutoRenewal().equalsIgnoreCase("N")) {
						dep_all[i].setAutoRenewal("No");
					}

				}
				System.out.println("Total no of records" + dep_all.length);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return dep_all;

	}

	public String[] getopenclosedetails() {

		String[] detail_open = { "All", "OpenAccounts", "CloseAccounts" };

		return detail_open;

	}

	public DepositMasterObject[] getQueryReceipt(String ac_type,
			String receipt_no) {

		DepositMasterObject[] dep_query = null;

		try {

			System.out.println(" receipt no===" + receipt_no);
			System.out.println("entering into delegate");

			dep_query = termDepositRemote.getFDReceiptPrinting(
					Integer.parseInt(receipt_no), 0, 2, ac_type);

			if (dep_query != null) {
				for (int i = 0; i < dep_query.length; i++) {

					String details_address = (dep_query[i].address.getAddress()
							+ dep_query[i].address.getCity()
							+ dep_query[i].address.getPin() + dep_query[i].address
							.getState());
					System.out.println(" inside if loop and adress is "
							+ details_address);

					System.out.println("adresss");
					System.out.println("door no==="
							+ dep_query[i].address.getAddress());
					System.out.println("city==="
							+ dep_query[i].address.getCity());
					System.out
							.println("pin===" + dep_query[i].address.getPin());
					System.out.println("state====="
							+ dep_query[i].address.getState());

					dep_query[i].setDetails_address(details_address);

				}
			}
			System.out.println("geetha inside getquery method delegate");

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (RecordsNotFoundException e) {
			e.printStackTrace();
		}

		return dep_query;
	}

	public byte[] getPhoto(int cid) throws RemoteException, SQLException {
		byte[] imagedata = null;

		System.out.println("Photo cid in deligate--> " + cid);
		imagedata = termDepositRemote.getCustomerPhoto(cid);

		return imagedata;
	}

	public DepositReportObject[] RetriveRenewalNotice(String date1,
			String date2, String ac_type, int type, int flag, String Query)
			throws RemoteException, RecordsNotFoundException {
		DepositReportObject[] dep_rep_obj = null;
		String frm_dt = Validations.convertYMD(date1);
		String to_dt = Validations.convertYMD(date2);
		System.out.println("the date after conversion is" + frm_dt + "    "
				+ to_dt);
		dep_rep_obj = termDepositRemote.RetrieveRenewalNotice(frm_dt, to_dt,
				ac_type, type, flag, Query);
		if (dep_rep_obj != null) {
			for (int i = 0; i < dep_rep_obj.length; i++) {
				System.out.println("The length is " + dep_rep_obj.length);
				System.out.println(dep_rep_obj[i].getAccno());
				System.out.println("^^^^^" + dep_rep_obj);
			}
		}

		return dep_rep_obj;

	}

	/*
	 * This class is used to close Fixed Deposit Account. U can close the
	 * account in three ways and close indicator will be Normal Closure(i.e
	 * after the Maturity Date) and close ind = 1 PreMature Closure with Penalty
	 * and close ind = 2 PreMature Closure with out Penalty and close ind = 3 If
	 * Premature Closure Get the Interest Rate using getDepositInterestRate
	 * Method and Penalty Rate( if premature closure with penalty) from Module
	 * Table.check the interest rate slab from depositdate to todays date
	 * (including deposit amount,account category etc.). Calculate the Interest
	 * Amount from begining and check for already paid interest amount.
	 * 
	 * U can change the payment to Transfer , PayOrder or CashIf account closed
	 * but not yet verified that time close ind will be Normal closure close ind
	 * =90 premature closure with penalty close ind =91 premature closure with
	 * out penalty close ind =92
	 */

	public int storeClosureInfo(DepositMasterObject depmast) {
		int closure_value = 0;
		try {

			System.out
					.println(" inside store closure info actype selected is-----"
							+ depmast.getAccType());

			System.out.println("transfer type in delegate---->>>>>"
					+ depmast.getTransferType());

			System.out.println("close_ind ---------" + depmast.getCloseInd());

			if (depmast.getCloseInd() != 0 && depmast.getCloseInd() < 6) {

				System.out.println("Account closed!!");
			}

			String date = TDDelegate.getSysdate();

			System.out.println("depmast.maturitydate"
					+ depmast.getMaturityDate());
			if (Validations.dayCompare(depmast.getMaturityDate(), date) > 0) {

				System.out.println("days==="
						+ Validations.dayCompare(depmast.getMaturityDate(),
								date));
				// int close_ind = 90;
				// depmast.setCloseInd(close_ind);

				System.out.println("-----normal closure----"
						+ depmast.getCloseInd());
			} else /*
					 * if(Validations.dayCompare(depmast.getMaturityDate(),date)
					 * > 1)
					 */{

				System.out.println("days-----"
						+ Validations.dayCompare(depmast.getMaturityDate(),
								date));

				// int close_ind = 91;
				// depmast.setCloseInd(close_ind);

				System.out
						.println("********premature closure****************    "
								+ depmast.getCloseInd());
			}

			if (depmast.getAccType().startsWith("1003")) {

				System.out.println("for ac_type==='1003'");

				closure_value = termDepositRemote.closeFDAccount(depmast);

			}

			else if (depmast.getAccType().startsWith("1005")) {

				System.out.println("for ac_type==='1005'");

				try {

					// submitReInvestment
					closure_value = termDepositRemote
							.submitReInvestment(depmast);

				} catch (RecordsNotInsertedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			else if (depmast.getAccType().startsWith("1004")) {

				System.out.println("for ac_type==='1004'");

				try {

					// submitReInvestment
					closure_value = termDepositRemote.submitRD(depmast);

				} catch (RecordsNotInsertedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				System.out.println("This Type of Account Type is not Present");
			}

			System.out.println("close ind in storeclosure info---- "
					+ depmast.getCloseInd());
			System.out.println("closure value in delegate ------"
					+ closure_value);

		} catch (RemoteException e) {

			e.printStackTrace();
		} catch (CreateException e) {

			e.printStackTrace();
		} catch (DateFormatException e) {

			e.printStackTrace();
		}
		// return 1;

		System.out.println("closure_value====" + closure_value);
		return closure_value;

	}

	public DepositIntRepObject[] interestCalculation(int type, String ac_type,
			String utml, String uid, String udate) {

		DepositIntRepObject[] dep_int_rep = null;

		udate = getSysdate();
		utml = "ca99";
		uid = "Ship";

		try {
			if (ac_type.equalsIgnoreCase("1003001")) {

				System.out.println("in delegate FDInterest calc");

				dep_int_rep = termDepositRemote.FDInterestCalc(type, uid, utml,
						udate);
				int i = 0;

				if (dep_int_rep[i].getInterestFrq().equalsIgnoreCase("M")) {
					dep_int_rep[i].setInterestFrq("Monthly");
				} else if (dep_int_rep[i].getInterestFrq()
						.equalsIgnoreCase("Q")) {
					dep_int_rep[i].setInterestFrq("Quarterly");
				} else if (dep_int_rep[i].getInterestFrq()
						.equalsIgnoreCase("H")) {
					dep_int_rep[i].setInterestFrq("Half Yearly");
				} else if (dep_int_rep[i].getInterestFrq().equalsIgnoreCase(
						"YEARLY")) {
					dep_int_rep[i].setInterestFrq("Yearly");
				} else if (dep_int_rep[i].getInterestFrq()
						.equalsIgnoreCase("O")) {
					dep_int_rep[i].setInterestFrq("On Maturity");
				}

				int j = 0;
				boolean state = dep_int_rep[j].getState().equalsIgnoreCase("T");

				dep_int_rep[j].setNarration("SUCESSFULL");

				System.out.println("state------------- " + state);

			} else if (ac_type.equalsIgnoreCase("1004001")) {

				System.out.println("in delegate RDInterestCalc");

				dep_int_rep = termDepositRemote.RDInterestCalc(type, utml, uid,
						udate);

			} else if (ac_type.startsWith("1005")) {

				System.out.println("in delegate RIInterestCalc");

				dep_int_rep = termDepositRemote.ReInvestmentInterestCalc(type,
						utml, uid, udate);
			}
			/*
			 * else if(ac_type.equalsIgnoreCase("1005002")){
			 * 
			 * System.out.println("in delegate RIInterestCalc");
			 * 
			 * dep_int_rep = termDepositRemote.ReInvestmentInterestCalc(type,
			 * utml, uid, udate); } else
			 * if(ac_type.equalsIgnoreCase("1005003")){
			 * 
			 * System.out.println("in delegate RIInterestCalc");
			 * 
			 * dep_int_rep = termDepositRemote.ReInvestmentInterestCalc(type,
			 * utml, uid, udate); }
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dep_int_rep;

	}

	public DepositIntRepObject[] quarterlyInterestCalc(int type,
			String ac_type, String utml, String uid, String udate,
			String combo_date) {

		DepositIntRepObject[] dep_int_rep = null;

		udate = getSysdate();
		utml = "ca99";
		uid = "Ship";

		System.out.println("udate====" + udate);
		System.out.println("utml======" + utml);
		System.out.println("uid====" + uid);

		System.out
				.println("**************inside interest calculation in delegate**********");

		try {

			if (ac_type.equalsIgnoreCase("1003001")) {

				System.out.println("in delegate FDInterest calc");

				dep_int_rep = termDepositRemote.FDQuarterlyIntCalc(type, uid,
						utml, udate, combo_date);

				/*
				 * int i=0; boolean state =
				 * dep_int_rep[i].getState().equalsIgnoreCase("T");
				 * 
				 * dep_int_rep[i].setNarration("SUCESSFULL");
				 * 
				 * System.out.println("state------------- "+state);
				 */
			} else if (ac_type.equalsIgnoreCase("1004001")) {

				System.out.println("in delegate RDInterestCalc");

				dep_int_rep = termDepositRemote.RDquarterlyInterest(type, utml,
						uid, udate, combo_date);

			} else if (ac_type.startsWith("1005")) {

				System.out.println("in delegate RIInterestCalc");

				dep_int_rep = termDepositRemote.ReInvestmentQuarterlyIntCalc(
						type, utml, uid, udate, combo_date);
			}

			for (int i = 0; i < dep_int_rep.length; i++) {

				System.out
						.println("acctype-----" + dep_int_rep[i].getAccType());

				System.out.println("deposit int report----"
						+ dep_int_rep[i].getAccNo());

				System.out.println("interest amount in delegate--------"
						+ dep_int_rep[i].getInterestAmt());

			}

		} catch (RecordsNotFoundException re) {
			System.out.println("*********No Records*****");
			re.printStackTrace();
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return dep_int_rep;

	}

	// for TD Ledger............

	public DepositMasterObject[] getLedgerDetails(int from_acno, int to_acno,
			String from_date, String to_date, String ac_type, int type)
			throws DateFormatException {

		DepositReportObject[] depositrep = null;

		DepositMasterObject[] deposit_ledger = null;

		System.out.println("entering into ledger details");

		System.out.println("from ac no in delegate====" + from_acno);

		System.out.println("to acno in delegate=========" + to_acno);

		System.out.println("from date in delegate========" + from_date);

		System.out.println("to date in delegate=======" + to_date);
		System.out.println("to Type in delegate=======" + type);
		if (type == 2 && from_date != null && to_date != null) {
			System.out.println("I am inside Open  accounts type 2");
			deposit_ledger = getOpenAccounts(ac_type, from_date, to_date, 2);
		}
		if (type == 1 && from_date != null && to_date != null) {
			System.out.println("I am inside Closed accounts type 1");
			deposit_ledger = getcloseAccounts(ac_type, from_date, to_date, 1);
		}
		if (type == 0 && from_date != null && to_date != null) {
			System.out.println("I am inside all accounts type 0");
			deposit_ledger = getAllAccounts(ac_type, from_date, to_date, 2);
			// change from 0 to 2 as there is no code for type 0 for all
			// accounts in the bean side.. and its is the same in sunBank.

		}
		try {
			if (type == 2 && from_date.length() == 0 && to_date.length() == 0) {

				System.out.println("inside try block of ledger");
				if (from_date.length() == 0 && to_date.length() == 0) {
					deposit_ledger = termDepositRemote.getDepositMaster(
							ac_type, 2, from_acno, to_acno, null, null, 0, "");
				} else {
					deposit_ledger = termDepositRemote.getDepositMaster(
							ac_type, 2, 0, 0, from_date, to_date, 0, "");
				}
			}

			if (type == 1 && from_date.length() == 0 && to_date.length() == 0) {

				System.out.println("inside type==1 in delegate");
				if (from_date == null && to_date == null) {
					deposit_ledger = termDepositRemote.getDepositMaster(
							ac_type, 1, from_acno, to_acno, null, null, 0, "");
				} else {
					deposit_ledger = termDepositRemote.getDepositMaster(
							ac_type, 1, 0, 0, from_date, to_date, 0, "");
				}

			}

			if (type == 0 && from_date.length() == 0 && to_date.length() == 0) {

				System.out.println("type========0");
				if (from_date == null && to_date == null) {
					deposit_ledger = termDepositRemote.getDepositMaster(
							ac_type, 0, from_acno, to_acno, null, null, 0, "");
				} else {
					deposit_ledger = termDepositRemote.getDepositMaster(
							ac_type, 4, 0, 0, from_date, to_date, 0, "");
				}
			}
			if (deposit_ledger != null) {
				for (int i = 0; i < deposit_ledger.length; i++) {

					System.out.println("acctype-----"
							+ deposit_ledger[i].getAccType());

					System.out.println("deposit int report----"
							+ deposit_ledger[i].getAccNo());

					System.out
							.println("No of open accounts in delegate--------"
									+ deposit_ledger[i].getAccNo());

				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RecordsNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("The value of depositledger----> " + deposit_ledger);
		return deposit_ledger;

	}

	public int submitAdminValues(DepositIntRate depositintrate,
			String table_name) {

		System.out.println("i am in Deligate Admin Values---> " + table_name);
		int value = 0;
		try {
			value = termDepositRemote.insertRow(depositintrate, table_name);
			System.out.println("the asdmin value in deligate-----> " + value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	public int deleteAdminValues(DepositIntRate depositintrate,
			String table_name) {

		System.out.println("i am in Deligate Delete Admin Values---> "
				+ table_name);
		int value = 0;
		try {
			value = termDepositRemote.deleteRow(depositintrate, table_name);
			System.out.println("the asdmin value in deligate-----> " + value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	public int getNewAccountNumber(String column_name, String acc_type,
			int ac_no) throws SQLException {
		System.out.println("The CLooum Name---> " + column_name);
		System.out.println("The Acount Type--> " + acc_type);
		int int_accno = 0;
		if (ac_no == 0)
			int_accno = comm_locremote.getModulesColumn("lst_acc_no", acc_type) + 1;
		// depmast.setAccNo(int_accno);

		return int_accno;
	}

	public int storeDeposit(DepositMasterObject dep_openobj, String ac_type,
			int ac_no, int type) {

		System.out.println("inside store deposit@@@@@@@@@@@@@@@@@@@@@@@@@@");

		DepositMasterObject acc_open_obj = null;

		String udate = getSysdate();
		String utml = "ca99";
		String uid = "Ship";
		int value = 0;
		try {
			if (type == 0) {
				dep_openobj.setAccNo(ac_no);
				System.out.println("inside type ==0 ");
				dep_openobj.setTranDate(getSysdate());
				value = termDepositRemote.storeDepositMaster(dep_openobj, 0);
				System.out.println("the value in delihgate----> " + value);
				return value;
			} else if (type == 1) {
				String next_pay_date = Validations.nextPayDate(dep_openobj
						.getInterestFrq().substring(0, 1), dep_openobj
						.getDepDate(), dep_openobj.getMaturityDate(),
						comm_locremote);
				dep_openobj.setNextPaydt(next_pay_date);
				value = termDepositRemote.storeDepositMaster(dep_openobj, 1);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DateFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ScrollNumberAttached e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ControlNumberAttached e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CreateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return value;
	}

	public int delegateverify(DepositMasterObject dep_verify) {

		int depmastverify = 0;

		ModuleObject[] array_ac_type = null;

		try {
			array_ac_type = getModTypes();

			System.out.println("TYpe in Deligate---->"
					+ dep_verify.getAccType());

			if (dep_verify.getReceivedBy().equalsIgnoreCase("T")) {

				System.out.println("geetha inside transfer ");
				dep_verify.setReceivedBy("T");

				dep_verify.setGLRefCode(Integer.parseInt(array_ac_type[0]
						.getModuleCode()));

				System.out.println("DELEGATE::GL Code:::"
						+ (Integer.parseInt(array_ac_type[0].getModuleCode())));
			}
			depmastverify = termDepositRemote
					.verifyDepositMaster(dep_verify, 0);
			System.out.println("DELEGATE:::  AFTER VERIFICATION &&&&&&&&--> "
					+ depmastverify);

			System.out
					.println("DELEGATE:::  AFTER VERIFICATION ountSIDE LOOP &&&&&&&&--> "
							+ depmastverify);

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CreateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Verified e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return depmastverify;
	}

	public DepositMasterObject getDepositMasterValues(int ac_no, String ac_type) {

		DepositMasterObject depmast_val = new DepositMasterObject();

		try {

			System.out.println("------DELEGATE::Inside getdepositvalues------");

			depmast_val = termDepositRemote.getDepositMaster(ac_type, ac_no);

			System.out.println("DELEGATE::depmast_val-------------->>"
					+ depmast_val);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return depmast_val;

	}

	public int validate(int type, String ac_type, int acc_num) {

		int value = 0;

		try {

			if (type == 0) {

				// type==0 check for cid
				System.out.println("inside type===0");

				value = termDepositRemote.validatefields(type, ac_type);

			}

			else if (type == 2) {

				System.out.println("inside type===2");

				value = termDepositRemote.validatefields(2, ac_type);

			}

			else {

				System.out.println("inside type===1");

				value = termDepositRemote.validatefields(type, ac_type);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return value;

	}

	// code for Receipt Updation

	public DepositMasterObject[] getFdReceiptUpdation(DepositMasterObject[] dm,
			int from_ac_no, int to_ac_no, String ac_type, int flag) {

		DepositMasterObject dep_rec_update[] = null;

		try {

			System.out.println("--------inside FD receipt updation----------");

			if (flag == 0) {

				dep_rec_update = termDepositRemote.getFDReceiptUpdationDetails(
						from_ac_no, to_ac_no, ac_type);

				List lis = new ArrayList();

				if (dep_rec_update != null) {
					for (int j = 0; j < dep_rec_update.length; j++) {

						System.out.println("elements----"
								+ dep_rec_update[0].getAccNo());

					}
				}
				System.out.println("After FD REceipt Updation in delegate");

			}// flag closed

			if (flag == 1) {

				boolean res = termDepositRemote.updateReceiptNumber(dm);
				System.out.println("result in update---->" + res);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dep_rec_update;

	}

	public boolean updateReceiptPrint(DepositMasterObject[] array_dep_object)
			throws RecordNotUpdatedException {
		boolean updated;

		try {
			for (int i = 0; i < array_dep_object.length; i++) {
				System.out
						.println("The changed receipt number in Deligate is----> "
								+ array_dep_object[i].getLastRctNo());
			}
			updated = termDepositRemote.updateReceiptPrinting(array_dep_object);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	public DepositMasterObject[] getReceiptPrinting(int startno, int endno,
			int type, String acc_type) throws RecordsNotFoundException,
			RemoteException {
		DepositMasterObject[] dm = null;

		System.out.println("startno in deligate===> " + startno);
		System.out.println("endno in deligate===> " + endno);
		System.out.println("type in deligate====> " + type);
		System.out.println("acc_type in deligate====> " + acc_type);
		try {

			dm = termDepositRemote.getFDReceiptPrinting(startno, endno, type,
					acc_type);
			System.out.println("The dm in Deligate is ----> " + dm);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dm;
	}

	// code for voucher payment

	public TrfVoucherObject[] getVoucherDetails(int ac_no, String ac_type,
			int flag) {

		TrfVoucherObject trf[] = new TrfVoucherObject[0];

		try {

			System.out
					.println("-------Entering inside Transfer voucher object--------");
			trf = termDepositRemote
					.getDepositTransferVoucher(ac_type, ac_no, 0);

			System.out.println("---------outside transfer cal---------");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return trf;

	}

	public DepositTransactionObject[] autoRenewalInfo(String date) {

		DepositTransactionObject[] dep_tran_auto = new DepositTransactionObject[0];

		try {

			System.out
					.println("--------------Inside Auto Renewal Information-----------");

			dep_tran_auto = termDepositRemote.getAutoRenewalInfo();

			System.out.println("First Record ------>>>"
					+ dep_tran_auto[0].getAccNo());

			System.out.println("No of records for AutoREnewal--------------->>"
					+ dep_tran_auto.length);

			System.out.println("deptrnauto------------------>>>>"
					+ dep_tran_auto[0]);

		} catch (RemoteException e) {

			e.printStackTrace();
		} catch (CreateException e) {

			e.printStackTrace();
		} catch (RecordsNotFoundException e) {

			e.printStackTrace();
		}

		return dep_tran_auto;
	}

	public DepositMasterObject getAmmendmentInfo(int ac_no, String ac_type) {

		DepositMasterObject dep_ammend = new DepositMasterObject();

		try {

			System.out.println("Inside getammendinfo        ac_type--->>"
					+ ac_type);

			dep_ammend = termDepositRemote.getDepositMaster(ac_type, ac_no);

			System.out.println("DELEGATE::Next pay date----->"
					+ dep_ammend.getNextPaydt());

			System.out.println("dep_ammend====" + dep_ammend.getAccType());

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AccountNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dep_ammend;
	}

	public TrfVoucherObject[] storeVoucherDetails(int ac_no, String ac_type,
			TrfVoucherObject[] trf_voucherobj) {

		TrfVoucherObject transfer[] = new TrfVoucherObject[0];
		int trf;

		try {
			trf = termDepositRemote.storeDepositTransferVoucher(trf_voucherobj,
					getSysdate());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CreateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return transfer;

	}

	public int storeDepositTransferVoucher(
			TrfVoucherObject[] array_trfvoucherobject, String date)
			throws CreateException, RecordNotInsertedException, RemoteException {

		TrfVoucherObject transfer[] = new TrfVoucherObject[0];
		int trf;

		System.out.println("Wats up Deligate Trnsfr----->"
				+ array_trfvoucherobject);
		System.out.println("Wats up Deligate Date----->" + date);

		trf = termDepositRemote.storeDepositTransferVoucher(
				array_trfvoucherobject, date);

		System.out.println("Voucher NUmber-----> " + trf);

		return trf;

	}

	public SignatureInstructionObject[] getSignatureDetails(int ac_no,
			String ac_type) throws RemoteException {

		try {

			System.out.println("INSIDE ---SignatureDetais--Deligate---->"
					+ ac_no + "ACtype" + ac_type);
			sign_details = commonRemote.getSignatureDetails(ac_no, ac_type);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return sign_details;

	}

	public int Nominee(NomineeObject[] nom) throws RemoteException,
			RecordsNotFoundException {
		System.out.println("Going to the bean");

		int s = termDepositRemote.SetNomineeDetails(nom);

		System.out.println("THe NOminee NUmber In Deligate Is==> " + s);
		return s;

	}

	public int getnomineePercentage(int acno, String actype)
			throws RemoteException, RecordsNotFoundException {
		int percent = 0;

		percent = termDepositRemote.getNomineePercent(acno, actype);
		return percent;
	}

	public int[] storeautorenewal(DepositTransactionObject[] deptrn_autorenew) {

		int auto_renew[] = null;

		System.out
				.println("Inside store renewal..ac_type........................."
						+ deptrn_autorenew[0].getAccType());
		try {

			auto_renew = termDepositRemote
					.storeAutoRenewalInfo(deptrn_autorenew);

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RecordsNotInsertedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return auto_renew;

	}

	// code ended for receipt updation...

	public String[] getAdminTableNames() {
		String table_names[] = { "DepositIntRate", "DepositQuantumRate",
				"DepositCategoryRate", "PeriodLimit", "QuantumLimit",
				"Products", "QtrDefinition" };
		return table_names;

	}

	public ModuleObject[] getAdminModules() throws RemoteException {
		ModuleObject[] array_moduleobject;
		array_moduleobject = commonRemote.getMainModules(2,
				"'1003000','1004000','1005000'");
		return array_moduleobject;
	}

	public DepositIntRate[] getDepIntRate(String string_td_type,
			String string_table_name) throws RecordsNotFoundException,
			RemoteException {
		DepositIntRate array_depositintrate[] = null;
		array_depositintrate = termDepositRemote.getViewDetailes(
				string_td_type, string_table_name);
		return array_depositintrate;
	}

	public LoanReportObject getLoanDetails_Closure(String actype,
			String clientdate, int acno) throws SQLException, RemoteException {
		LoanReportObject repobj = loanondeposit_remote.getLoanDetails(actype,
				clientdate, acno);
		return repobj;
	}

	public AccountObject getIntroducerAcntDetails(String acType, int acNo)
			throws RemoteException {
		System.out.println("inside Deligate -------PPPPPPPPP--->" + acType);
		System.out.println("inside Deligate -------PPPPPPPPP--->" + acNo);
		AccountObject introObject = null;
		String date = com.scb.common.help.Date.getSysDate();

		introObject = getComRemote().getAccount(null, acType, acNo, date);
		return introObject;
	}

	public String getFutureMonthDate(String cur_date, int month)
			throws RemoteException {
		String str = commonRemote.getFutureMonthDate(cur_date, month);
		return str;
	}

	public NomineeObject[] getNomineeDetails(int reg_no) throws RemoteException {

		NomineeObject[] nominee_details = commonRemote.getNominee(reg_no);

		return nominee_details;

	}

	// Loan Purpose

	public ModuleObject[] getLoanTypes(int a, String str)
			throws RemoteException {

		ModuleObject loantypemod[] = null;

		loantypemod = commonRemote.getMainModules(a, str);
		return loantypemod;
	}

	public LoanPurposeObject[] getLoanPurposeDetails() throws RemoteException {

		System.out.println("I am in Deligate LoanPurposeDetails----> ");
		LoanPurposeObject[] lnpurp = null;

		try {
			lnpurp = loans_remote.getLoanPurposes();

			if (lnpurp != null) {
				for (int i = 0; i < lnpurp.length; i++) {
					System.out.println("In side Deliagate GetLoanPurpose--->"
							+ lnpurp[i]);
				}
			}

		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return lnpurp;
	}

	public String[] getLoanComboDeatils() {
		String items_combo_details[] = { "Personal", "Relative", "Employment",
				"Application", "Loan and Share Details", "Signature",
				"Properpty", "CoBorrower", "Surities", "Vehicle", "Gold" };
		return items_combo_details;
	}

	public PriorityMasterObject[] getPriorityDesc() {

		System.out.println("I am inside getPriorityDesc----->");
		PriorityMasterObject[] priorobj = null;
		try {
			priorobj = loans_remote.getPriorityDesc();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return priorobj;
	}

	public LoanMasterObject getLoanMasterDetailsAdd(int ln_acno, String ln_acty) {
		System.out.println("HIIIIIIIIIIIIIIIIII");
		System.out.println("ln_acno in deligate----> " + ln_acno);
		System.out.println("ln_acty in deligate----> " + ln_acty);
		LoanMasterObject lnobj12 = null;

		try {
			lnobj12 = loans_remote.getLoanMaster(ln_acno, ln_acty);
			if (lnobj12 != null)
				System.out.println("lnobj12------> "
						+ lnobj12.getInterestRate());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lnobj12;
	}

	public double getIntRateDetails(String ln_type, String fdate, int category,
			int period, double amt, int ac_no) {
		double amtsanc, lnkshares, irate, prate;
		double rate = 0;
		try {
			irate = loans_remote.getIntRate(ln_type, fdate, category, period,
					amt, ac_no);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rate;
	}

	public LoanTransactionObject getUnVerifiedDisbursementDetails(
			String ln_type, int acno) {
		LoanTransactionObject ltrn = null;
		System.out
				.println("Hi i am in UnVerifiedDisbursementDetails Deligate=======");
		try {
			ltrn = loans_remote.getUnVerifiedDisbursement(ln_type, acno);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ltrn;
	}

	public double getPenalIntRatedetails(String ln_type, String fdate,
			int category) {
		double prate = 0.0;
		try {
			prate = loans_remote.getPenalIntRate(ln_type, fdate, category);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prate;
	}

	public String[] getReleventDetailsDeligate(String modulecode) {

		System.out.println("Loan Type Deligate====> " + modulecode);
		String[] revldetails = null;
		try {
			revldetails = loans_remote.getReleventDetails(modulecode);
			System.out.println("result ==>" + revldetails[0]);
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return revldetails;
	}

	public Object[][] getRowsviewlog(String str, String column1,
			String column2, int no, String acc_type, int type)
			throws RemoteException {
		System.out.println("view log Deligate" + str);
		System.out.println("view log Deligate" + column1);
		System.out.println("view log Deligate" + column2);
		System.out.println("view log Deligate" + no);
		System.out.println("view log Deligate" + acc_type);
		System.out.println("view log Deligate" + type);

		Object array_object[][] = null;
		try {
			array_object = commonRemote.getRows(str, column1, column2, no,
					acc_type, type);

			if (array_object != null) {
				System.out.println(" i am not null");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return array_object;
	}

}
