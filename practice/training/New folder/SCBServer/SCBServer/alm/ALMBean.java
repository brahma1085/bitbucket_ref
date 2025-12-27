package alm;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import masterObject.alm.ALMMasterObject;
import masterObject.generalLedger.GLReportObject;

public class ALMBean implements SessionBean {

	SessionContext ctx = null;
	Connection conn = null;
	DataSource ds = null;

	public void ejbCreate() {

		try {
			ds = (DataSource) new InitialContext().lookup("java:MySqlDS");

		} catch (NamingException ex) {
			ex.printStackTrace();
		}

	}

	public void ejbActivate() throws EJBException, RemoteException {

	}

	public void ejbPassivate() throws EJBException, RemoteException {

	}

	public void ejbRemove() throws EJBException, RemoteException {

	}

	public void setSessionContext(SessionContext ctx) throws EJBException,
			RemoteException {
		this.ctx = ctx;
	}

	public Connection getConnection() {
		try {
			conn = ds.getConnection("", "");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	// Methods to implement the ALM Modules are follows here
	// Added by Gopinath.S

	public String[] getMonthlySummary(String as_on_date, String gl_code) {
		String date = as_on_date;
		String glcode = gl_code;
		String[] report = null;
		Connection con = null;
		ALMMasterObject alm_object[] = null;
		Statement stmt = null;

		try {

			con = getConnection();
			stmt = con.createStatement();
			ResultSet rs = null;

			// This is for Outflows (Liabilities)
			if (gl_code.startsWith("1")) {
				// For the module code which starts with 1%%%
				rs = stmt
						.executeQuery("select gm.gl_code,gm.gl_name,((sum(cash_cr)+sum(cg_cr)+sum(trf_cr))-(sum(cash_dr)+sum(cg_dr)+sum(trf_dr))) as Amount from GLMaster as gm,MonthlySummary as ms where gm.gl_code=ms.gl_code and ms.yr_mth<=200604 and gm.gl_code like '1_____' group by gm.gl_code;");
			}
			// This is for Inflows  (Assets)
			if (gl_code.startsWith("2")) {
				// For the module code which starts with 2%%%
				stmt
						.executeQuery("select gm.gl_code,gm.gl_name,((sum(cash_dr)+sum(cg_dr)+sum(trf_dr))-(sum(cash_cr)+sum(cg_cr)+sum(trf_cr))) as Amount from GLMaster as gm,MonthlySummary as ms where gm.gl_code=ms.gl_code and ms.yr_mth<=200604 and gm.gl_code like '2_____' group by gm.gl_code;");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return report;
	}
}
