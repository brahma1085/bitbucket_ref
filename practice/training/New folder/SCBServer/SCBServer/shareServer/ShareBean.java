
package shareServer;

/* Suraj. Bagewadi*/
import exceptions.DateFormatException;
import exceptions.RecordNotUpdatedException;
import exceptions.RecordsNotFoundException;
import exceptions.ScrollNotFoundException;
import exceptions.SignatureNotInsertedException;
import masterObject.frontCounter.AccountTransObject;
import masterObject.frontCounter.PayOrderObject;
import masterObject.general.AccountObject;
import masterObject.general.Address;
import masterObject.general.AddressTypesObject;
import masterObject.general.ModuleObject;
import masterObject.general.NomineeObject;
import masterObject.general.SignatureInstructionObject;
import general.Validations;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Vector;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import client.HomeFactory;

import masterObject.share.DirectorMasterObject;
import masterObject.share.DividendObject;
import masterObject.share.DividendRateObject;
import masterObject.share.ShareCategoryObject;
import masterObject.share.ShareMasterObject;
import masterObject.share.ShareParamObject;
import masterObject.share.ShareReportObject;

import commonServer.CommonHome;
import commonServer.CommonLocal;
import commonServer.CommonLocalHome;
import commonServer.CommonRemote;
import commonServer.GLTransObject;

public class ShareBean implements SessionBean 
{
	javax.sql.DataSource ds=null;
	transient CommonLocalHome common_local_home;
	transient CommonLocal common_local;
	transient CommonRemote common_remote;
	transient CommonHome common_home;
	Context jndiContext ;
	SessionContext sessionContext = null;
	
	public ShareBean()
	{
		/*try{	
		 Context ctx=new InitialContext();			
		 ds=(javax.sql.DataSource)ctx.lookup("java:MySqlDS");	
		 }catch(Exception ex){ex.printStackTrace();}
		 */
	}
	
	public void setSessionContext(SessionContext arg0) throws EJBException {
		sessionContext = arg0;		
	}
	public void ejbCreate()
	{System.out.println("share ejb create in stateless session bean");
	try {
		jndiContext =new InitialContext();
		common_local_home=(CommonLocalHome)jndiContext.lookup("COMMONLOCALWEB");
		common_local = common_local_home.create();
		
		common_home=(CommonHome)jndiContext.lookup("COMMONWEB");
		common_remote = common_home.create();
	} catch (NamingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (CreateException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	try
	{            
		ds=(DataSource)jndiContext.lookup("java:MySqlDS");   
	}
	catch(Exception ex)
	{ex.printStackTrace();}
	}
	public void ejbRemove() throws EJBException {}
	public void ejbActivate() throws EJBException{}
	public void ejbPassivate() throws EJBException{}
	
	
	public AddressTypesObject[] getAddressTypes()
	{
		AddressTypesObject acc[] =null;
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("select number,addrtype from AddrTypes");
			rs.last();
			acc=new  AddressTypesObject[rs.getRow()];
			rs.beforeFirst();
			int i=0;
			while(rs.next())
			{
				acc[i]=new AddressTypesObject();
				acc[i].setAddressCode(rs.getInt(1));
				acc[i].setAddressDesc(rs.getString(2));
				i++;
			}
			
		}catch(Exception e){e.printStackTrace();}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}
		return acc;
	}
	/**
	 * Using this Method we have to retrive all the details reg. CertificatePrinting.
	 * Tables used are:
	 * 		a)ShareMaster
	 * 		b)ShareTransaction
	 * 		c)ShareType
	 * 		d)NomineeMaster 
	 * The input we are giving is from(starting) account no.,to(ending) account no,account type and membercategory
	 * We have to increment the certificate number(no_cert)  in ShareMaster table.
	 */
	public ShareReportObject[] printCertificate(int fromacno,int toacno,String actype,int memcat) throws RecordsNotFoundException
	{
		System.out.println("Inside certificate printing");
		System.out.println("acno=="+fromacno);
		System.out.println("actype=="+actype);
		ShareReportObject arr[]=null;
		Connection conn=null;
		
		ResultSet rs=null,rs1=null;
		
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			Statement stmt1=conn.createStatement();
			//rs=stmt.executeQuery("select concat(cm.fname,' ',cm.mname,' ',cm.lname)as name,ca.address,ca.city,ca.pin,nm.name,nm.relation,st.shareval,st.catname,sm.mem_issuedate,sht.share_bal,sht.trn_narr,sht.dist_no_from,sht.dist_no_to from CustomerAddr ca,CustomerMaster cm ,ShareMaster  sm left join NomineeMaster nm on nm.reg_no=sm.nom_no,ShareTransaction sht,ShareType st where  st.ac_type=sm.ac_type and sht.ac_no=sm.ac_no and st.mem_cat=sm.mem_cat and   sm.cid=cm.cid and cm.cid=ca.cid and ca.addr_type=sm.addr_type and sm.ac_no="+acno+" and sm.ac_type='"+actype+"' and sht.sh_cert_no is null");
			//rs=stmt.executeQuery("select concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,moduleabbr,sm.pay_mode,sm.ac_no,sm.ac_type,sm.pay_ac_no,sm.pay_ac_type,sm.no_cert,ca.address,ca.city,ca.pin,nm.name,nm.relation,nm.reg_no,st.shareval,st.catname,sm.mem_issuedate,sht.share_bal,sht.trn_amt,sht.trn_narr,sht.dist_no_from,sht.dist_no_to,sht.sh_cert_no from Modules,CustomerAddr ca,CustomerMaster cm ,ShareMaster  sm,ShareTransaction sht,NomineeMaster nm,ShareType st where nm.reg_no=sm.nom_no and st.ac_type=sm.ac_type and sht.ac_no=sm.ac_no and sht.ac_type=sm.ac_type and modulecode=sm.ac_type and st.mem_cat=sm.mem_cat and   sm.cid=cm.cid and cm.cid=ca.cid and ca.addr_type=sm.addr_type and sm.ac_no between "+fromacno+" and "+toacno+" and sm.ac_type='"+actype+"' and sm.sh_ind='P' and mem_cl_date is null and sht.trn_type='A' and sht.cd_ind='C' and sht.susp_ind='P'and sm.mem_cat="+memcat+"  and sm.share_stat is null and nm.to_date is null");
			rs=stmt.executeQuery("select concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,sht.trn_seq,moduleabbr,sm.pay_mode,sm.ac_no,sm.ac_type,sm.mem_cat,sm.pay_ac_no,sm.pay_ac_type,sm.no_cert,ca.address,ca.city,ca.pin,st.shareval,st.catname,sm.mem_issuedate,sht.share_bal,sht.trn_amt,sht.trn_narr,sht.dist_no_from,sht.dist_no_to,sht.sh_cert_no from Modules,CustomerAddr ca,CustomerMaster cm ,ShareMaster  sm,ShareTransaction sht,ShareType st where st.ac_type=sm.ac_type and sht.ac_no=sm.ac_no and sht.ac_type=sm.ac_type and modulecode=sm.ac_type and st.mem_cat=sm.mem_cat and   sm.cid=cm.cid and cm.cid=ca.cid and ca.addr_type=sm.addr_type and sm.ac_no between "+fromacno+" and "+toacno+" and sm.ac_type='"+actype+"' and sm.sh_ind='P' and mem_cl_date is null and sht.trn_type='A' and sht.cd_ind='C' and sht.susp_ind='P'  and sm.share_stat is null and trn_seq = (select max(sq.trn_seq) from ShareTransaction  as sq where sht.ac_type = sq.ac_type and sq.ac_no = sht.ac_no)");//Code added by sanjeet.
			rs.last();
			System.out.println("no of rows"+rs.getRow());
			if(rs.getRow()==0)
				throw new RecordsNotFoundException();
			arr =new ShareReportObject[rs.getRow()];
			rs.beforeFirst();
			int i=0;
			while(rs.next())
			{	
				
				arr[i]=new ShareReportObject();
				arr[i].setShareCertDate(getSysDate());
				arr[i].setName(rs.getString("name"));
				arr[i].setAddress(rs.getString("ca.address"));
				arr[i].setCity(rs.getString("ca.city"));
				arr[i].setPin(rs.getInt("ca.pin"));
				arr[i].setAccno(rs.getInt("sm.ac_no"));
				arr[i].setMem_cat(rs.getString(("sm.mem_cat")));
				rs1=stmt1.executeQuery("select cid,name,reg_no,relation from NomineeMaster where ac_type="+actype+" and ac_no='"+arr[i].getAccno()+"'");
				if(rs1.next()){
					arr[i].setNomineeName(rs1.getString("name"));
					arr[i].setNomineeRelation(rs1.getString("relation"));
					arr[i].setNomineeRegNo(rs1.getInt("reg_no"));
				}
				double shval=rs.getDouble("st.shareval");
				System.out.println("share value = "+shval);
				arr[i].setShareVal(shval);
				arr[i].setShareType(rs.getString("st.catname"));
				arr[i].setIssueDate(rs.getString("sm.mem_issuedate"));
				arr[i].setNumberofShares((int)(rs.getDouble("sht.trn_amt")/shval));
				System.out.println("No of shares....: = "+arr[i].getNumberofShares());
				arr[i].setCertNumberFrom(rs.getInt("sht.dist_no_from"));
				arr[i].setCertNumberTo(rs.getInt("sht.dist_no_to"));
				arr[i].setShareCertNumber(rs.getInt("sht.sh_cert_no"));
				arr[i].setAcctype(rs.getString("moduleabbr"));
				//Added By Karthi-->31/01/2006
				String pay_mode=rs.getString("sm.pay_mode");
				if(pay_mode.equalsIgnoreCase("C"))
					arr[i].setPayMode("Cash");
				else if(pay_mode.equalsIgnoreCase("T"))
				{arr[i].setPayMode("Transfer");arr[i].setTransNarr(rs.getString("sht.trn_narr"));}
				else if(pay_mode.equalsIgnoreCase("W"))
					arr[i].setPayMode("Warrant");
				else if(pay_mode.equalsIgnoreCase("B"))
					arr[i].setPayMode("Branch");
				
				arr[i].setPaymentAccno(rs.getInt("sm.pay_ac_no"));
				arr[i].setPaymentAcctype(rs.getString("sm.pay_ac_type"));
				int no_cert=rs.getInt("sm.no_cert");
				if(no_cert>0)
					stmt.executeUpdate("insert into ShareMasterLog select * from ShareMaster where ac_no="+arr[i].getAccno()+" and ac_type='"+actype+"'");
				
				if(stmt.executeUpdate("update ShareMaster set no_cert="+(no_cert+1)+" where ac_no="+arr[i].getAccno()+" and ac_type='"+actype+"' ")==0)
					throw new RecordNotUpdatedException();
				i++;
				System.out.println("i=="+i);
			}		
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}
		return arr;
		
	}
	
	public ShareReportObject[] printOneCertificate(int fromacno,int toacno,String actype,int memcat) throws RecordsNotFoundException, RecordNotUpdatedException, DateFormatException
	{
		int cert_no=0;
		System.out.println("Inside certificate printing.........printonecertificate");
		System.out.println("acno=="+fromacno);
		System.out.println("cert_no "+cert_no);
		
		System.out.println("actype=="+actype);
		System.out.println("memcat= "+memcat);
		ShareReportObject arr[]=null;
		Connection conn=null;
		int i=0;
		ResultSet rs=null,rs1=null;
		ResultSet rs_cert_no=null,rs_bcode=null;
		try{
			System.out.println("1...........");
			conn=getConnection();
			Statement stmt=conn.createStatement();
			Statement stmt1=conn.createStatement();
			Statement stmt_cert_no=conn.createStatement();
			Statement stmt_bcode=conn.createStatement();
			System.out.println("2...........");
			
			//rs_cert_no=stmt_cert_no.executeQuery("select lst_certallot_no from GenParam");
			rs_cert_no=stmt_cert_no.executeQuery("select max_renewal_count from Modules where modulecode='"+actype+"'");//max_renewal_count-->CertificatePrinting
			System.out.println("2111...........");
			if(rs_cert_no.next())
				cert_no=rs_cert_no.getInt(1);
			cert_no=cert_no+1;
			
			System.out.println("cert_no "+cert_no);
			
			rs=stmt.executeQuery("select concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,moduleabbr,sm.ac_no,sm.ac_type,sm.pay_ac_no,sm.pay_ac_type,ca.address,ca.city,ca.pin,nm.name,nm.relation,nm.reg_no,st.shareval,st.catname,sm.mem_issuedate,sht.share_bal,sht.trn_narr,sht.dist_no_from,sht.dist_no_to,sm.br_code from Modules,CustomerAddr ca,CustomerMaster cm ,ShareMaster  sm,ShareTransaction sht,NomineeMaster nm,ShareType st where nm.reg_no=sm.nom_no and st.ac_type=sm.ac_type and sht.ac_no=sm.ac_no and sht.ac_type=sm.ac_type and modulecode=sm.ac_type and st.mem_cat=sm.mem_cat and   sm.cid=cm.cid and cm.cid=ca.cid and ca.addr_type=sm.addr_type and sm.ac_no between "+fromacno+" and "+toacno+"  and sm.ac_type='"+actype+"' and sht.sh_cert_no is null and sm.sh_ind='P' and mem_cl_date is null and sht.trn_type='A' and sht.cd_ind='C' and sht.susp_ind='P'and sm.mem_cat="+memcat+" and sm.share_stat is null and mem_cl_date is null and nm.to_date is null");
			System.out.println("3..........");
			rs.last();
			System.out.println("no of rows"+rs.getRow());
			if(rs.getRow()==0)
				throw new RecordsNotFoundException();
			System.out.println("if there r records");
			
			arr =new ShareReportObject[rs.getRow()];
			rs.beforeFirst();
			
			System.out.println("i..........."+i);
			while(rs.next())
			{		
				if(i>0)
					cert_no++;
				System.out.println("cert_no++..........."+cert_no);
				System.out.println("4...........");
				arr[i]=new ShareReportObject();
				arr[i].setShareCertDate(getSysDate());
				arr[i].setName(rs.getString("name"));
				arr[i].setAddress(rs.getString("ca.address"));
				arr[i].setCity(rs.getString("ca.city"));
				arr[i].setPin(rs.getInt("ca.pin"));
				arr[i].setNomineeName(rs.getString("nm.name"));
				arr[i].setNomineeRelation(rs.getString("nm.relation"));
				arr[i].setNomineeRegNo(rs.getInt("nm.reg_no"));
				arr[i].setShareVal(rs.getDouble("st.shareval"));
				arr[i].setShareType(rs.getString("st.catname"));
				arr[i].setIssueDate(rs.getString("sm.mem_issuedate"));
				arr[i].setNumberofShares(rs.getInt("sht.share_bal"));
				arr[i].setCertNumberFrom(rs.getInt("sht.dist_no_from"));
				arr[i].setCertNumberTo(rs.getInt("sht.dist_no_to"));
				arr[i].setAccno(rs.getInt("sm.ac_no"));
				arr[i].setAcctype(rs.getString("moduleabbr"));
				arr[i].setPaymentAccno(rs.getInt("sm.pay_ac_no"));
				arr[i].setPaymentAcctype(rs.getString("sm.pay_ac_type"));
				
				int bcode=rs.getInt("sm.br_code");
				System.out.println("int bcode==="+bcode);
				rs_bcode=stmt_bcode.executeQuery(" select br_name from BranchMaster where br_code="+bcode+"  ");
				String str_bcode=null;
				if(rs_bcode.next()) {
					str_bcode=rs_bcode.getString("br_name");
					System.out.println("string bcode==="+str_bcode);
				}
				rs_bcode.close();
				arr[i].setBranchName(str_bcode);
				arr[i].setShareCertNumber(cert_no);
				System.out.println("5...........");
				rs1=stmt1.executeQuery("select sh_cert_no from ShareTransaction where sh_cert_no="+cert_no+" ");
				System.out.println("6..........");
				rs1.last();
				System.out.println("no of rows wiht the given no of cert "+rs1.getRow());
				if(rs1.getRow()==0)
				{
					System.out.println("7...........");
					if(stmt1.executeUpdate("update ShareTransaction set sh_cert_no="+cert_no+",sh_cert_dt='"+getSysDate()+"',cert_prtd='T' where ac_no=("+rs.getInt("sm.ac_no")+") and ac_type='"+rs.getString("sm.ac_type")+"'  ")==0)
						throw new RecordNotUpdatedException();
					System.out.println("8...........");
				}
				else
					return null;
				
				System.out.println("9...........");
				System.out.println("i=="+i);
			}
			rs.close();
		}catch(SQLException e)
		{
			e.printStackTrace();
			sessionContext.setRollbackOnly();
			return null;
		}
		catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		finally{
			try{
				conn.close();
			}catch(SQLException e){e.printStackTrace();}
		}
		return arr;
		
	}
	
	public ShareReportObject[] branchwiseSummary(String str) 
	{
		
		ShareReportObject arr[]=null;
		Connection conn=null;
		ResultSet rs=null;
		
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			
			if(str!=null)
			{
				System.out.println("String Query is:"+str);
				//rs=stmt.executeQuery("select BranchMaster.br_code,BranchMaster.br_name,sum(ShareTransaction.share_bal/ShareMaster.share_val),sum(ShareMaster.share_val) from ShareMaster,ShareTransaction right join BranchMaster on  ShareMaster.br_code=BranchMaster.br_code where ShareMaster.ac_type=ShareTransaction.ac_type  and ShareMaster.ac_no=ShareTransaction.ac_no and ShareMaster.mem_cl_date is null and BranchMaster.br_type!='BK' and  ("+str+") group by BranchMaster.br_code,ShareMaster.ac_type,ShareMaster.ac_no");
				if(str.startsWith("bm."))
				{
					StringTokenizer st=new StringTokenizer(str,".");
					st.nextToken();
					String s=st.nextToken();
					StringTokenizer st1=new StringTokenizer(str,"=");
					String s1= st1.nextToken();
					System.out.println("s1==>"+s1);
					if(s1.equalsIgnoreCase("bm.br_code "))
						rs=stmt.executeQuery("select sm.ac_no,sm.temp_no,sm.mem_cat,sm.br_code,sht.shareval,bm.br_name,sum(st.trn_amt/sht.shareval) as num_of_shares,sum(st.trn_amt) as share_bal from ShareMaster sm,ShareType sht,ShareTransaction st,BranchMaster bm where sm.br_code=bm.br_code and sm.mem_cat=sht.mem_cat and sm.ac_type=st.ac_type and sm.ac_no=st.ac_no and sm.mem_cl_date is null and bm.br_type!='BK' and ("+str+") group by bm.br_code,sm.ac_type,sm.ac_no ");
					else
						rs=stmt.executeQuery("select sm.ac_no,sm.temp_no,sm.mem_cat,sm.br_code,sht.shareval,bm.br_name,sum(st.trn_amt/sht.shareval) as num_of_shares,sum(st.trn_amt) as share_bal from ShareMaster sm,ShareType sht,ShareTransaction st,BranchMaster bm where sm.br_code=bm.br_code and sm.mem_cat=sht.mem_cat and sm.ac_type=st.ac_type and sm.ac_no=st.ac_no and sm.mem_cl_date is null and bm.br_type!='BK' group by bm.br_code,sm.ac_type,sm.ac_no having ("+str+")"); 
				}
				else
					rs=stmt.executeQuery("select sm.ac_no,sm.temp_no,sm.mem_cat,sm.br_code,sht.shareval,bm.br_name,sum(st.trn_amt/sht.shareval) as num_of_shares,sum(st.trn_amt) as share_bal from ShareMaster sm,ShareType sht,ShareTransaction st,BranchMaster bm where sm.br_code=bm.br_code and sm.mem_cat=sht.mem_cat and sm.ac_type=st.ac_type and sm.ac_no=st.ac_no and sm.mem_cl_date is null and bm.br_type!='BK' group by bm.br_code,sm.ac_type,sm.ac_no having ("+str+")");
				//	rs=stmt.executeQuery("select BranchMaster.br_code,BranchMaster.br_name,sum(ShareTransaction.share_bal/ShareMaster.share_val),sum(ShareMaster.share_val) from ShareMaster,ShareTransaction right join BranchMaster on  ShareMaster.br_code=BranchMaster.br_code where  ShareMaster.mem_cl_date is null and BranchMaster.br_type!='BK' and  ("+str+") group by BranchMaster.br_code");
			}
			else
			{
				//rs=stmt.executeQuery("select BranchMaster.br_code,BranchMaster.br_name,sum(ShareTransaction.share_bal/ShareMaster.share_val),sum(ShareMaster.share_val) from ShareMaster,ShareTransaction right join BranchMaster on  ShareMaster.br_code=BranchMaster.br_code where ShareMaster.ac_type=ShareTransaction.ac_type  and ShareMaster.ac_no=ShareTransaction.ac_no and ShareMaster.mem_cl_date is null and BranchMaster.br_type!='BK'  group by BranchMaster.br_code,ShareMaster.ac_type,ShareMaster.ac_no");
				rs=stmt.executeQuery("select sm.ac_no,sm.temp_no,sm.mem_cat,sm.br_code,sht.shareval,bm.br_name,sum(st.trn_amt/sht.shareval) as num_of_shares,sum(st.trn_amt) as share_bal from ShareMaster sm,ShareType sht,ShareTransaction st,BranchMaster bm where sm.br_code=bm.br_code and sm.mem_cat=sht.mem_cat and sm.ac_type=st.ac_type and sm.ac_no=st.ac_no and sm.mem_cl_date is null and bm.br_type!='BK' group by bm.br_code,sm.ac_type,sm.ac_no");
			}
			
			////rs=stmt.executeQuery("select BranchMaster.br_code,BranchMaster.br_name,sum(ShareTransaction.share_bal/ShareMaster.share_val),sum(ShareMaster.share_val) from ShareMaster,ShareTransaction right join BranchMaster on  ShareMaster.br_code=BranchMaster.br_code where  ShareMaster.mem_cl_date is null and BranchMaster.br_type!='BK'  group by BranchMaster.br_code");
			
			rs.last();
			arr =new ShareReportObject[rs.getRow()];
			rs.beforeFirst();
			
			int i=0;
			while(rs.next())
			{
				arr[i]=new ShareReportObject();
				
				arr[i].setBranchCode(rs.getInt("br_code"));
				arr[i].setBranchName(rs.getString("br_name"));
				//arr[i].setNumberofShares(rs.getInt(3));
				arr[i].setNumberofShares(rs.getInt("num_of_shares"));
				//arr[i].setShareVal(rs.getFloat(4));
				arr[i].setShareVal(rs.getFloat("share_bal"));
				
				i++;
			}
		}catch(Exception e){e.printStackTrace();}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}
		
		return arr;
		
	}
	
	//public ShareReportObject[] shareRegistryReport(String date2,String str) throws RemoteException
	public ShareReportObject[] shareRegistryReport(String date1,String date2,String str) 
	{
		System.out.println("string == "+str);
		System.out.println("From: "+date1);
		System.out.println("To: "+date2);
		ShareReportObject arr[]=null;
		Connection conn=null;
		Statement stmt_intrname=null;
		ResultSet rs=null;
		int num_shares=0;	
		
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();	
			stmt_intrname=conn.createStatement();
			if(str==null)
				//rs=stmt.executeQuery("select SM.ac_type,SM.ln_availed,SM.ac_no,CM.fname,CM.mname,CM.lname,CA.address,CA.city,CA.pin,SM.intr_ac_type,SM.intr_ac_no,NM.name,NM.relation,SM.share_val,SM.div_uptodt,ST.share_bal from ShareTransaction ST, ShareMaster SM left join  NomineeMaster NM on NM.reg_no=SM.nom_no,CustomerMaster CM,CustomerAddr CA where SM.cid=CM.cid and SM.cid=CA.cid and concat(right(SM.mem_issuedate,4),'-',mid(SM.mem_issuedate,locate('/',SM.mem_issuedate)+1, (locate('/',SM.mem_issuedate,4)-locate('/',SM.mem_issuedate)-1)),'-',left(SM.mem_issuedate,locate('/',SM.mem_issuedate)-1)) <= '"+Validations.convertYMD(date2)+"' and SM.mem_cl_date is null and CA.addr_type=SM.addr_type");
				rs=stmt.executeQuery("select SM.ac_type,SM.ac_no,SM.sh_ind,SM.temp_no,SM.mem_cat,SM.ln_availed,SM.share_val,(SM.share_val/ST.shareval) as num_shares,SM.intr_ac_type,SM.intr_ac_no,SM.div_uptodt,concat_ws(' ',CM.fname,CM.mname,CM.lname)as cname,CA.address,CA.city,CA.pin,NM.name,NM.relation from ShareMaster SM,ShareType ST,CustomerMaster CM,CustomerAddr CA,NomineeMaster NM where SM.mem_cat=ST.mem_cat and SM.cid=CM.cid and CM.cid=CA.cid and SM.nom_no=NM.reg_no and concat(right(SM.mem_issuedate,4),'-',mid(SM.mem_issuedate,locate('/',SM.mem_issuedate)+1, (locate('/',SM.mem_issuedate,4)-locate('/',SM.mem_issuedate)-1)),'-',left(SM.mem_issuedate,locate('/',SM.mem_issuedate)-1)) between '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and SM.mem_cl_date is  null and SM.addr_type=CA.addr_type order by SM.ac_no");//Karthi
			else
				rs=stmt.executeQuery("select SM.ac_type,SM.ac_no,SM.sh_ind,SM.temp_no,SM.mem_cat,SM.ln_availed,SM.share_val,(SM.share_val/ST.shareval) as num_shares,SM.intr_ac_type,SM.intr_ac_no,SM.div_uptodt,concat_ws(' ',CM.fname,CM.mname,CM.lname)as cname,CA.address,CA.city,CA.pin,NM.name,NM.relation from ShareMaster SM,ShareType ST,CustomerMaster CM,CustomerAddr CA,NomineeMaster NM where SM.mem_cat=ST.mem_cat and SM.cid=CM.cid and CM.cid=CA.cid and SM.nom_no=NM.reg_no and concat(right(SM.mem_issuedate,4),'-',mid(SM.mem_issuedate,locate('/',SM.mem_issuedate)+1, (locate('/',SM.mem_issuedate,4)-locate('/',SM.mem_issuedate)-1)),'-',left(SM.mem_issuedate,locate('/',SM.mem_issuedate)-1)) between '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and SM.mem_cl_date is  null and SM.addr_type=CA.addr_type and ("+str+") order by SM.ac_no");//Karthi
			
			rs.last();
			arr =new ShareReportObject[rs.getRow()];
			rs.beforeFirst();
			int i=0;
			while(rs.next())
			{
				num_shares=(int)(rs.getFloat("num_shares"));
				//num_shares=rs.getInt("ST.share_bal")/rs.getInt("SM.share_val");
				arr[i]=new ShareReportObject();
				arr[i].setShareAccType(rs.getString("SM.ac_type"));
				
				if(rs.getString("SM.sh_ind").equalsIgnoreCase("P"))
					arr[i].setShareNumber(rs.getInt("SM.ac_no"));
				else
					arr[i].setShareNumber(rs.getInt("SM.temp_no"));
				
				//arr[i].setName(rs.getString("CM.fname")+" "+rs.getString("CM.mname")+" "+rs.getString("CM.lname"));
				arr[i].setName(rs.getString("cname"));//Karthi
				arr[i].setAddress(rs.getString("CA.address"));
				arr[i].setCity(rs.getString("CA.city"));
				arr[i].setPin(rs.getInt("CA.pin"));
				//arr[i].setAddress("address");
				arr[i].setShareVal(rs.getFloat("SM.share_val"));
				arr[i].setNumberofShares(num_shares);
				arr[i].setAcctype(rs.getString("SM.intr_ac_type"));
				int intr_ac_no=rs.getInt("SM.intr_ac_no");
				arr[i].setAccno(intr_ac_no);
				//Added by Karthi==>27/04/2006
				Context ctx=getInitialContext();
				Object obj=ctx.lookup("COMMONWEB");
				commonServer.CommonHome home=(commonServer.CommonHome)obj;
				CommonRemote cremote=home.create();
				arr[i].setIntrName(cremote.getAccountHolderName(arr[i].getAcctype(),arr[i].getAccno()));
				//Lookup
				//if(arr[i].getAccno()!=0)
				//	arr[i].setIntrName(ServerImpl.common.getAccount(arr[i].getAcctype(),arr[i].getAccno()).getAccname());		
				arr[i].setNomineeName(rs.getString("NM.name"));
				arr[i].setNomineeRelation(rs.getString("NM.relation"));
				arr[i].setDivUptoDate(rs.getString("SM.div_uptodt"));
				arr[i].setLoanAvailed( rs.getString("SM.ln_availed"));
				
				//System.out.println("I === "+i);
				i++;
			}
		}catch(Exception e){e.printStackTrace();}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}
		if(arr!=null)
			System.out.println("         Full    ");
		else
			System.out.println("         NotFull    ");
		return arr;
	}
	
	public ShareReportObject[] shareAllotmentWithdrawal(String date1,String date2,String str)
	{
		System.out.println("String == "+str);
		ShareReportObject arr[]=null;
		Connection conn=null;
		ResultSet rs=null;
		int num_shares=0;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			
			//rs=stmt.executeQuery("select SM.ac_type,SM.ac_no,SM.temp_no,SM.sh_ind,concat_ws(' ',CM.fname,CM.mname,CM.lname)as name,CA.address,CA.city,CA.pin,SM.intr_ac_type,SM.intr_ac_no,NM.name,NM.relation,ST.share_bal,ST.trn_amt,ST.sh_cert_no,sht.shareval,ST.sh_cert_dt,SM.div_uptodt,ST.dist_no_from,ST.dist_no_to,ST.trn_date,ST.trn_type,SM.share_val from CustomerMaster CM ,CustomerAddr  CA,ShareType sht,ShareMaster SM left join NomineeMaster NM on NM.reg_no=SM.nom_no,ShareTransaction ST where  CM.cid=CA.cid and CM.cid=SM.cid and SM.mem_cat=sht.mem_cat and SM.ac_no=ST.ac_no and concat(right(ST.trn_date,4),'-',mid(ST.trn_date,locate('/',ST.trn_date)+1, (locate('/',ST.trn_date,4)-locate('/',ST.trn_date)-1)),'-',left(ST.trn_date,locate('/',ST.trn_date)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and SM.mem_cl_date is null and CA.addr_type=SM.addr_type order by SM.ac_type,SM.ac_no");
			if(str==null){
				//rs=stmt.executeQuery("select SM.ac_type,SM.sh_ind,SM.ac_no,SM.temp_no,concat_ws(' ',CM.fname,CM.mname,CM.lname)as name,CA.address,CA.city,CA.pin,ST.share_bal,ST.trn_amt,ST.sh_cert_no,sht.shareval,ST.sh_cert_dt,ST.dist_no_from,ST.dist_no_to,ST.trn_date,ST.trn_type,SM.share_val,ST.trn_amt/sht.shareval from ShareMaster SM,ShareTransaction ST ,ShareType sht,CustomerMaster CM ,CustomerAddr CA where SM.mem_cl_date is null and ST.trn_type!='I' and SM.ac_no=ST.ac_no and SM.mem_cat=sht.mem_cat and concat(right(ST.trn_date,4),'-',mid(ST.trn_date,locate('/',ST.trn_date)+1, (locate('/',ST.trn_date,4)-locate('/',ST.trn_date)-1)),'-',left(ST.trn_date,locate('/',ST.trn_date)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and CM.cid=SM.cid and CM.cid=CA.cid and CA.addr_type=SM.addr_type order by SM.ac_no");
				rs=stmt.executeQuery("select SM.ac_type,SM.sh_ind,SM.ac_no,SM.temp_no,SM.br_code,SM.mem_issuedate,concat_ws(' ',CM.fname,CM.mname,CM.lname)as name,CM.cid,CA.address,CA.city,CA.pin,ST.share_bal,ST.trn_amt,ST.sh_cert_no,sht.shareval,ST.sh_cert_dt,ST.dist_no_from,ST.dist_no_to,ST.trn_date,ST.trn_type,SM.share_val,ST.trn_amt/sht.shareval from ShareMaster SM,ShareTransaction ST ,ShareType sht,CustomerMaster CM ,CustomerAddr CA where ST.trn_type!='I' and SM.ac_no=ST.ac_no and SM.mem_cat=sht.mem_cat and concat(right(ST.trn_date,4),'-',mid(ST.trn_date,locate('/',ST.trn_date)+1, (locate('/',ST.trn_date,4)-locate('/',ST.trn_date)-1)),'-',left(ST.trn_date,locate('/',ST.trn_date)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and CM.cid=SM.cid and CM.cid=CA.cid and CA.addr_type=SM.addr_type order by SM.ac_no");
				System.out.println("OPEN");
			}
			else{
				//rs=stmt.executeQuery("select SM.ac_type,SM.sh_ind,SM.ac_no,SM.temp_no,concat_ws(' ',CM.fname,CM.mname,CM.lname)as name,CA.address,CA.city,CA.pin,ST.share_bal,ST.trn_amt,ST.sh_cert_no,sht.shareval,ST.sh_cert_dt,ST.dist_no_from,ST.dist_no_to,ST.trn_date,ST.trn_type,SM.share_val,ST.trn_amt/sht.shareval from ShareMaster SM,ShareTransaction ST ,ShareType sht,CustomerMaster CM ,CustomerAddr CA where SM.mem_cl_date is null and ST.trn_type!='I' and ("+str+") and SM.ac_no=ST.ac_no and SM.mem_cat=sht.mem_cat and concat(right(ST.trn_date,4),'-',mid(ST.trn_date,locate('/',ST.trn_date)+1, (locate('/',ST.trn_date,4)-locate('/',ST.trn_date)-1)),'-',left(ST.trn_date,locate('/',ST.trn_date)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and CM.cid=SM.cid and CM.cid=CA.cid and CA.addr_type=SM.addr_type order by SM.ac_no");
				rs=stmt.executeQuery("select SM.ac_type,SM.sh_ind,SM.ac_no,SM.temp_no,concat_ws(' ',CM.fname,CM.mname,CM.lname)as name,CA.address,CA.city,CA.pin,ST.share_bal,ST.trn_amt,ST.sh_cert_no,sht.shareval,ST.sh_cert_dt,ST.dist_no_from,ST.dist_no_to,ST.trn_date,ST.trn_type,SM.share_val,ST.trn_amt/sht.shareval from ShareMaster SM,ShareTransaction ST ,ShareType sht,CustomerMaster CM ,CustomerAddr CA where ST.trn_type!='I' and ("+str+") and SM.ac_no=ST.ac_no and SM.mem_cat=sht.mem_cat and concat(right(ST.trn_date,4),'-',mid(ST.trn_date,locate('/',ST.trn_date)+1, (locate('/',ST.trn_date,4)-locate('/',ST.trn_date)-1)),'-',left(ST.trn_date,locate('/',ST.trn_date)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and CM.cid=SM.cid and CM.cid=CA.cid and CA.addr_type=SM.addr_type order by SM.ac_no");
				System.out.println("CLOSE");
			}
			rs.last();
			arr =new ShareReportObject[rs.getRow()];
			System.out.println("No.of Records==>"+arr.length);
			rs.beforeFirst();
			int i=0;
			while(rs.next())
			{
				/*if(rs.getInt("SM.share_val")!=0)
				 num_shares=(int)(rs.getFloat("ST.trn_amt")/rs.getFloat("sht.shareval"));*/
				
				num_shares=(int)(rs.getFloat("ST.trn_amt/sht.shareval"));
				
				arr[i]=new ShareReportObject();
				arr[i].setShareAccType(rs.getString("SM.ac_type"));
				arr[i].setShareStatus(rs.getString("SM.sh_ind"));
				//Changed by Karthi
				if(rs.getString("sh_ind").equalsIgnoreCase("P"))
					arr[i].setShareNumber(rs.getInt("SM.ac_no"));
				else
					arr[i].setShareNumber(rs.getInt("SM.temp_no"));
				
				//arr[i].setName(rs.getString("CM.fname")+" "+rs.getString("CM.mname")+" "+rs.getString("CM.lname"));
				arr[i].setName(rs.getString("name"));
				arr[i].setCustomerId(rs.getInt("CM.cid"));
				arr[i].setAddress(rs.getString("CA.address"));
				arr[i].setCity(rs.getString("CA.city"));
				arr[i].setPin(rs.getInt("CA.pin"));
				
				//arr[i].setShareVal(rs.getFloat("SM.share_val"));
				arr[i].setShareVal(rs.getFloat("ST.trn_amt"));
				arr[i].setNumberofShares(num_shares);
				arr[i].setTransDate(rs.getString("ST.trn_date"));
				arr[i].setTransType(rs.getString("ST.trn_type"));
				arr[i].setShareCertNumber(rs.getInt("ST.sh_cert_no"));
				arr[i].setShareCertDate(rs.getString("ST.sh_cert_dt"));
				arr[i].setCertNumberFrom(rs.getInt("ST.dist_no_from"));
				arr[i].setCertNumberTo(rs.getInt("ST.dist_no_to"));			
				arr[i].setBranchCode(rs.getInt("SM.br_code"));
				arr[i].setIssueDate(rs.getString("SM.mem_issuedate"));
				i++;
			}
		}catch(Exception e){e.printStackTrace();}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}
		return arr;
	}
	
	public ShareReportObject[] shareTempPermanent(int j,String date1,String date2,String str)
	{
		ShareReportObject arr[]=null;
		Connection conn=null;
		ResultSet rs=null;
		StringTokenizer st=null,st1=null;
		String mem_cat=null,first=null,middle=null,last=null,m="=",l="!=",num_shares=null;
		System.out.println("string_query:"+str);
		
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			if(j==8)//Temporary shares
			{
				if(str!=null)
				{
					//Added by Karthi==>14/04/2006
					if(str.startsWith("sm.mem_cat "))
					{
						
						if(str.startsWith("sm.mem_cat like")) 
						{
							st=new StringTokenizer(str," ");
							first=st.nextToken();
							middle=st.nextToken();
							last=st.nextToken();
							str=first+m+last;
							st1=new StringTokenizer(str,"=");
							st1.nextToken();
							mem_cat=st1.nextToken();
							System.out.println("Sharecatagory==>"+mem_cat);
							rs=stmt.executeQuery("select distinct sm.temp_no,sm.mem_cat,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.sh_ind,sm.br_code,sm.share_val,st.shareval,sm.mem_issuedate,br.br_name,catname from ShareMaster sm,CustomerMaster cm,CustomerAddr ca ,BranchMaster br,ShareType st where concat(right(sm.mem_issuedate,4),'-',mid(sm.mem_issuedate,locate('/',sm.mem_issuedate)+1, (locate('/',sm.mem_issuedate,4)-locate('/',sm.mem_issuedate)-1)),'-',left(sm.mem_issuedate,locate('/',sm.mem_issuedate)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and st.mem_cat=sm.mem_cat and sh_ind='T'and cm.cid=ca.cid and cm.cid=sm.cid and sm.br_code=br.br_code and  ca.addr_type=sm.addr_type and sm.mem_cl_date is null and st.catname="+mem_cat+" order by sm.ac_no");
						}
						else  if(str.startsWith("sm.mem_cat not like"))
						{
							st=new StringTokenizer(str," ");
							first=st.nextToken();
							middle=st.nextToken();
							st.nextToken();
							last=st.nextToken();
							str=first+l+last;
							st1=new StringTokenizer(str,"!=");
							st1.nextToken();
							mem_cat=st1.nextToken();
							System.out.println("Sharecatagory==>"+mem_cat);
							rs=stmt.executeQuery("select distinct sm.temp_no,sm.mem_cat,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.sh_ind,sm.br_code,sm.share_val,st.shareval,sm.mem_issuedate,br.br_name,catname from ShareMaster sm,CustomerMaster cm,CustomerAddr ca ,BranchMaster br,ShareType st where concat(right(sm.mem_issuedate,4),'-',mid(sm.mem_issuedate,locate('/',sm.mem_issuedate)+1, (locate('/',sm.mem_issuedate,4)-locate('/',sm.mem_issuedate)-1)),'-',left(sm.mem_issuedate,locate('/',sm.mem_issuedate)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and st.mem_cat=sm.mem_cat and sh_ind='T'and cm.cid=ca.cid and cm.cid=sm.cid and sm.br_code=br.br_code and  ca.addr_type=sm.addr_type and sm.mem_cl_date is null and st.catname!="+mem_cat+" order by sm.ac_no");
						}
					}
					//Added by Karthi==>17/04/2006
					else if(str.startsWith("sm.num_shares <") || str.startsWith("sm.num_shares > ")||str.startsWith("sm.num_shares <=")|| str.startsWith("sm.num_shares >=") || str.startsWith("sm.num_shares =") || str.startsWith("sm.num_shares <>"))
					{
						if(str.startsWith("sm.num_shares <")&& str.length()==18)
						{
							st=new StringTokenizer(str,"<");
							st.nextToken();
							num_shares=st.nextToken();
							System.out.println("Num.of shares:"+num_shares);
							rs=stmt.executeQuery("select distinct sm.temp_no,sm.mem_cat,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.sh_ind,sm.br_code,sm.share_val,st.shareval,sm.mem_issuedate,br.br_name,catname from ShareMaster sm,CustomerMaster cm,CustomerAddr ca ,BranchMaster br,ShareType st where concat(right(sm.mem_issuedate,4),'-',mid(sm.mem_issuedate,locate('/',sm.mem_issuedate)+1, (locate('/',sm.mem_issuedate,4)-locate('/',sm.mem_issuedate)-1)),'-',left(sm.mem_issuedate,locate('/',sm.mem_issuedate)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and sm.share_val<("+num_shares+"*st.shareval) and st.mem_cat=sm.mem_cat and sh_ind='T'and cm.cid=ca.cid and cm.cid=sm.cid and sm.br_code=br.br_code and  ca.addr_type=sm.addr_type and sm.mem_cl_date is null order by sm.ac_no");
						}
						else if(str.startsWith("sm.num_shares >")&&str.length()==18 )
						{
							st=new StringTokenizer(str,">");
							st.nextToken();
							num_shares=st.nextToken();
							System.out.println("Num.of shares:"+num_shares);
							rs=stmt.executeQuery("select distinct sm.temp_no,sm.mem_cat,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.sh_ind,sm.br_code,sm.share_val,st.shareval,sm.mem_issuedate,br.br_name,catname from ShareMaster sm,CustomerMaster cm,CustomerAddr ca ,BranchMaster br,ShareType st where concat(right(sm.mem_issuedate,4),'-',mid(sm.mem_issuedate,locate('/',sm.mem_issuedate)+1, (locate('/',sm.mem_issuedate,4)-locate('/',sm.mem_issuedate)-1)),'-',left(sm.mem_issuedate,locate('/',sm.mem_issuedate)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and sm.share_val>("+num_shares+"*st.shareval) and st.mem_cat=sm.mem_cat and sh_ind='T'and cm.cid=ca.cid and cm.cid=sm.cid and sm.br_code=br.br_code and  ca.addr_type=sm.addr_type and sm.mem_cl_date is null order by sm.ac_no");
						}
						else if(str.startsWith("sm.num_shares <=")&& str.length()==19)
						{
							st=new StringTokenizer(str,"<=");
							st.nextToken();
							num_shares=st.nextToken();
							System.out.println("Num.of shares:"+num_shares);
							rs=stmt.executeQuery("select distinct sm.temp_no,sm.mem_cat,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.sh_ind,sm.br_code,sm.share_val,st.shareval,sm.mem_issuedate,br.br_name,catname from ShareMaster sm,CustomerMaster cm,CustomerAddr ca ,BranchMaster br,ShareType st where concat(right(sm.mem_issuedate,4),'-',mid(sm.mem_issuedate,locate('/',sm.mem_issuedate)+1, (locate('/',sm.mem_issuedate,4)-locate('/',sm.mem_issuedate)-1)),'-',left(sm.mem_issuedate,locate('/',sm.mem_issuedate)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and sm.share_val<=("+num_shares+"*st.shareval) and st.mem_cat=sm.mem_cat and sh_ind='T'and cm.cid=ca.cid and cm.cid=sm.cid and sm.br_code=br.br_code and  ca.addr_type=sm.addr_type and sm.mem_cl_date is null order by sm.ac_no");
						}
						else if(str.startsWith("sm.num_shares >=")&& str.length()==19)
						{
							st=new StringTokenizer(str,">=");
							st.nextToken();
							num_shares=st.nextToken();
							System.out.println("Num.of shares:"+num_shares);
							rs=stmt.executeQuery("select distinct sm.temp_no,sm.mem_cat,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.sh_ind,sm.br_code,sm.share_val,st.shareval,sm.mem_issuedate,br.br_name,catname from ShareMaster sm,CustomerMaster cm,CustomerAddr ca ,BranchMaster br,ShareType st where concat(right(sm.mem_issuedate,4),'-',mid(sm.mem_issuedate,locate('/',sm.mem_issuedate)+1, (locate('/',sm.mem_issuedate,4)-locate('/',sm.mem_issuedate)-1)),'-',left(sm.mem_issuedate,locate('/',sm.mem_issuedate)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and sm.share_val>=("+num_shares+"*st.shareval) and st.mem_cat=sm.mem_cat and sh_ind='T'and cm.cid=ca.cid and cm.cid=sm.cid and sm.br_code=br.br_code and  ca.addr_type=sm.addr_type and sm.mem_cl_date is null order by sm.ac_no");
						}
						else if(str.startsWith("sm.num_shares ="))
						{
							st=new StringTokenizer(str,"=");
							st.nextToken();
							num_shares=st.nextToken();
							System.out.println("Num.of shares:"+num_shares);
							rs=stmt.executeQuery("select distinct sm.temp_no,sm.mem_cat,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.sh_ind,sm.br_code,sm.share_val,st.shareval,sm.mem_issuedate,br.br_name,catname from ShareMaster sm,CustomerMaster cm,CustomerAddr ca ,BranchMaster br,ShareType st where concat(right(sm.mem_issuedate,4),'-',mid(sm.mem_issuedate,locate('/',sm.mem_issuedate)+1, (locate('/',sm.mem_issuedate,4)-locate('/',sm.mem_issuedate)-1)),'-',left(sm.mem_issuedate,locate('/',sm.mem_issuedate)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and sm.share_val=("+num_shares+"*st.shareval) and st.mem_cat=sm.mem_cat and sh_ind='T'and cm.cid=ca.cid and cm.cid=sm.cid and sm.br_code=br.br_code and  ca.addr_type=sm.addr_type and sm.mem_cl_date is null order by sm.ac_no");
						}
						else if(str.startsWith("sm.num_shares <>"))
						{
							st=new StringTokenizer(str,"<>");
							st.nextToken();
							num_shares=st.nextToken();
							System.out.println("Num.of shares:"+num_shares);
							rs=stmt.executeQuery("select distinct sm.temp_no,sm.mem_cat,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.sh_ind,sm.br_code,sm.share_val,st.shareval,sm.mem_issuedate,br.br_name,catname from ShareMaster sm,CustomerMaster cm,CustomerAddr ca ,BranchMaster br,ShareType st where concat(right(sm.mem_issuedate,4),'-',mid(sm.mem_issuedate,locate('/',sm.mem_issuedate)+1, (locate('/',sm.mem_issuedate,4)-locate('/',sm.mem_issuedate)-1)),'-',left(sm.mem_issuedate,locate('/',sm.mem_issuedate)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and sm.share_val<>("+num_shares+"*st.shareval) and st.mem_cat=sm.mem_cat and sh_ind='T'and cm.cid=ca.cid and cm.cid=sm.cid and sm.br_code=br.br_code and  ca.addr_type=sm.addr_type and sm.mem_cl_date is null order by sm.ac_no");
						}
					}
					else
						rs=stmt.executeQuery("select distinct sm.temp_no,sm.mem_cat,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.sh_ind,sm.br_code,sm.share_val,st.shareval,sm.mem_issuedate,br.br_name,catname from ShareMaster sm,CustomerMaster cm,CustomerAddr ca ,BranchMaster br,ShareType st where concat(right(sm.mem_issuedate,4),'-',mid(sm.mem_issuedate,locate('/',sm.mem_issuedate)+1, (locate('/',sm.mem_issuedate,4)-locate('/',sm.mem_issuedate)-1)),'-',left(sm.mem_issuedate,locate('/',sm.mem_issuedate)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and st.mem_cat=sm.mem_cat and sh_ind='T'and cm.cid=ca.cid and cm.cid=sm.cid and sm.br_code=br.br_code and  ca.addr_type=sm.addr_type and sm.mem_cl_date is null and ("+str+") order by sm.ac_no");
				}
				else
					rs=stmt.executeQuery("select distinct sm.temp_no,sm.mem_cat,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.sh_ind,sm.br_code,sm.share_val,st.shareval,sm.mem_issuedate,br.br_name,catname from ShareMaster sm,CustomerMaster cm,CustomerAddr ca ,BranchMaster br,ShareType st where concat(right(sm.mem_issuedate,4),'-',mid(sm.mem_issuedate,locate('/',sm.mem_issuedate)+1, (locate('/',sm.mem_issuedate,4)-locate('/',sm.mem_issuedate)-1)),'-',left(sm.mem_issuedate,locate('/',sm.mem_issuedate)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and st.mem_cat=sm.mem_cat and sh_ind='T'and cm.cid=ca.cid and cm.cid=sm.cid and sm.br_code=br.br_code and  ca.addr_type=sm.addr_type and sm.mem_cl_date is null order by sm.ac_no");
			}
			if(j==9)//Permanent shares
			{
				if(str!=null)
				{
					//Added by Karthi==>14/04/2006
					if(str.startsWith("sm.mem_cat "))
					{
						
						if(str.startsWith("sm.mem_cat like")) 
						{
							/* str1=new StringTokenizer(str," like");
							 str1.nextToken();
							 mem_cat=str1.nextToken();
							 System.out.println("Sharecatagory==>"+mem_cat);
							 System.out.println(" ===1.    "+str1.nextToken()); 
							 System.out.println(" ===2.    "+str1.nextToken()); 
							 System.out.println(" ===3.    "+str1.nextToken()); */
							st=new StringTokenizer(str," ");
							first=st.nextToken();
							middle=st.nextToken();
							last=st.nextToken();
							str=first+m+last;
							st1=new StringTokenizer(str,"=");
							st1.nextToken();
							mem_cat=st1.nextToken();
							System.out.println("Sharecatagory==>"+mem_cat);
							rs=stmt.executeQuery("select distinct sm.temp_no,sm.mem_cat,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.sh_ind,sm.br_code,sm.share_val,st.shareval,sm.mem_issuedate,br.br_name,catname from ShareMaster sm,CustomerMaster cm,CustomerAddr ca ,BranchMaster br,ShareType st where concat(right(sm.mem_issuedate,4),'-',mid(sm.mem_issuedate,locate('/',sm.mem_issuedate)+1, (locate('/',sm.mem_issuedate,4)-locate('/',sm.mem_issuedate)-1)),'-',left(sm.mem_issuedate,locate('/',sm.mem_issuedate)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and st.mem_cat=sm.mem_cat and sh_ind='P'and cm.cid=ca.cid and cm.cid=sm.cid and sm.br_code=br.br_code and  ca.addr_type=sm.addr_type and sm.mem_cl_date is null and st.catname="+mem_cat+"  order by sm.ac_no");
						}
						else  if(str.startsWith("sm.mem_cat not like"))
						{
							st=new StringTokenizer(str," ");
							first=st.nextToken();
							middle=st.nextToken();
							st.nextToken();
							last=st.nextToken();
							str=first+l+last;
							st1=new StringTokenizer(str,"!=");
							st1.nextToken();
							mem_cat=st1.nextToken();
							System.out.println("Sharecatagory==>"+mem_cat);
							rs=stmt.executeQuery("select distinct sm.temp_no,sm.mem_cat,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.sh_ind,sm.br_code,sm.share_val,st.shareval,sm.mem_issuedate,br.br_name,catname from ShareMaster sm,CustomerMaster cm,CustomerAddr ca ,BranchMaster br,ShareType st where concat(right(sm.mem_issuedate,4),'-',mid(sm.mem_issuedate,locate('/',sm.mem_issuedate)+1, (locate('/',sm.mem_issuedate,4)-locate('/',sm.mem_issuedate)-1)),'-',left(sm.mem_issuedate,locate('/',sm.mem_issuedate)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and st.mem_cat=sm.mem_cat and sh_ind='P'and cm.cid=ca.cid and cm.cid=sm.cid and sm.br_code=br.br_code and  ca.addr_type=sm.addr_type and sm.mem_cl_date is null and st.catname!="+mem_cat+"  order by sm.ac_no");
						}
					}
					//Added by Karthi==>17/04/2006
					else if(str.startsWith("sm.num_shares <") || str.startsWith("sm.num_shares > ")||str.startsWith("sm.num_shares <=")|| str.startsWith("sm.num_shares >=") || str.startsWith("sm.num_shares =") || str.startsWith("sm.num_shares <>"))
					{
						if(str.startsWith("sm.num_shares <")&& str.length()==18)
						{
							st=new StringTokenizer(str,"<");
							st.nextToken();
							num_shares=st.nextToken();
							System.out.println("Num.of shares:"+num_shares);
							rs=stmt.executeQuery("select distinct sm.temp_no,sm.mem_cat,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.sh_ind,sm.br_code,sm.share_val,st.shareval,sm.mem_issuedate,br.br_name,catname from ShareMaster sm,CustomerMaster cm,CustomerAddr ca ,BranchMaster br,ShareType st where concat(right(sm.mem_issuedate,4),'-',mid(sm.mem_issuedate,locate('/',sm.mem_issuedate)+1, (locate('/',sm.mem_issuedate,4)-locate('/',sm.mem_issuedate)-1)),'-',left(sm.mem_issuedate,locate('/',sm.mem_issuedate)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and sm.share_val<("+num_shares+"*st.shareval) and st.mem_cat=sm.mem_cat and sh_ind='P'and cm.cid=ca.cid and cm.cid=sm.cid and sm.br_code=br.br_code and  ca.addr_type=sm.addr_type and sm.mem_cl_date is null order by sm.ac_no");
						}
						else if(str.startsWith("sm.num_shares >")&&str.length()==18 )
						{
							st=new StringTokenizer(str,">");
							st.nextToken();
							num_shares=st.nextToken();
							System.out.println("Num.of shares:"+num_shares);
							rs=stmt.executeQuery("select distinct sm.temp_no,sm.mem_cat,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.sh_ind,sm.br_code,sm.share_val,st.shareval,sm.mem_issuedate,br.br_name,catname from ShareMaster sm,CustomerMaster cm,CustomerAddr ca ,BranchMaster br,ShareType st where concat(right(sm.mem_issuedate,4),'-',mid(sm.mem_issuedate,locate('/',sm.mem_issuedate)+1, (locate('/',sm.mem_issuedate,4)-locate('/',sm.mem_issuedate)-1)),'-',left(sm.mem_issuedate,locate('/',sm.mem_issuedate)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and sm.share_val>("+num_shares+"*st.shareval) and st.mem_cat=sm.mem_cat and sh_ind='P'and cm.cid=ca.cid and cm.cid=sm.cid and sm.br_code=br.br_code and  ca.addr_type=sm.addr_type and sm.mem_cl_date is null order by sm.ac_no");
						}
						else if(str.startsWith("sm.num_shares <=")&& str.length()==19)
						{
							st=new StringTokenizer(str,"<=");
							st.nextToken();
							num_shares=st.nextToken();
							System.out.println("Num.of shares:"+num_shares);
							rs=stmt.executeQuery("select distinct sm.temp_no,sm.mem_cat,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.sh_ind,sm.br_code,sm.share_val,st.shareval,sm.mem_issuedate,br.br_name,catname from ShareMaster sm,CustomerMaster cm,CustomerAddr ca ,BranchMaster br,ShareType st where concat(right(sm.mem_issuedate,4),'-',mid(sm.mem_issuedate,locate('/',sm.mem_issuedate)+1, (locate('/',sm.mem_issuedate,4)-locate('/',sm.mem_issuedate)-1)),'-',left(sm.mem_issuedate,locate('/',sm.mem_issuedate)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and sm.share_val<=("+num_shares+"*st.shareval) and st.mem_cat=sm.mem_cat and sh_ind='P'and cm.cid=ca.cid and cm.cid=sm.cid and sm.br_code=br.br_code and  ca.addr_type=sm.addr_type and sm.mem_cl_date is null order by sm.ac_no");
						}
						else if(str.startsWith("sm.num_shares >=")&& str.length()==19)
						{
							st=new StringTokenizer(str,">=");
							st.nextToken();
							num_shares=st.nextToken();
							System.out.println("Num.of shares:"+num_shares);
							rs=stmt.executeQuery("select distinct sm.temp_no,sm.mem_cat,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.sh_ind,sm.br_code,sm.share_val,st.shareval,sm.mem_issuedate,br.br_name,catname from ShareMaster sm,CustomerMaster cm,CustomerAddr ca ,BranchMaster br,ShareType st where concat(right(sm.mem_issuedate,4),'-',mid(sm.mem_issuedate,locate('/',sm.mem_issuedate)+1, (locate('/',sm.mem_issuedate,4)-locate('/',sm.mem_issuedate)-1)),'-',left(sm.mem_issuedate,locate('/',sm.mem_issuedate)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and sm.share_val>=("+num_shares+"*st.shareval) and st.mem_cat=sm.mem_cat and sh_ind='P'and cm.cid=ca.cid and cm.cid=sm.cid and sm.br_code=br.br_code and  ca.addr_type=sm.addr_type and sm.mem_cl_date is null order by sm.ac_no");
						}
						else if(str.startsWith("sm.num_shares ="))
						{
							st=new StringTokenizer(str,"=");
							st.nextToken();
							num_shares=st.nextToken();
							System.out.println("Num.of shares:"+num_shares);
							rs=stmt.executeQuery("select distinct sm.temp_no,sm.mem_cat,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.sh_ind,sm.br_code,sm.share_val,st.shareval,sm.mem_issuedate,br.br_name,catname from ShareMaster sm,CustomerMaster cm,CustomerAddr ca ,BranchMaster br,ShareType st where concat(right(sm.mem_issuedate,4),'-',mid(sm.mem_issuedate,locate('/',sm.mem_issuedate)+1, (locate('/',sm.mem_issuedate,4)-locate('/',sm.mem_issuedate)-1)),'-',left(sm.mem_issuedate,locate('/',sm.mem_issuedate)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and sm.share_val=("+num_shares+"*st.shareval) and st.mem_cat=sm.mem_cat and sh_ind='P'and cm.cid=ca.cid and cm.cid=sm.cid and sm.br_code=br.br_code and  ca.addr_type=sm.addr_type and sm.mem_cl_date is null order by sm.ac_no");
						}
						else if(str.startsWith("sm.num_shares <>"))
						{
							st=new StringTokenizer(str,"<>");
							st.nextToken();
							num_shares=st.nextToken();
							System.out.println("Num.of shares:"+num_shares);
							rs=stmt.executeQuery("select distinct sm.temp_no,sm.mem_cat,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.sh_ind,sm.br_code,sm.share_val,st.shareval,sm.mem_issuedate,br.br_name,catname from ShareMaster sm,CustomerMaster cm,CustomerAddr ca ,BranchMaster br,ShareType st where concat(right(sm.mem_issuedate,4),'-',mid(sm.mem_issuedate,locate('/',sm.mem_issuedate)+1, (locate('/',sm.mem_issuedate,4)-locate('/',sm.mem_issuedate)-1)),'-',left(sm.mem_issuedate,locate('/',sm.mem_issuedate)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and sm.share_val<>("+num_shares+"*st.shareval) and st.mem_cat=sm.mem_cat and sh_ind='P'and cm.cid=ca.cid and cm.cid=sm.cid and sm.br_code=br.br_code and  ca.addr_type=sm.addr_type and sm.mem_cl_date is null order by sm.ac_no");
						}
					}
					else
						rs=stmt.executeQuery("select distinct sm.temp_no,sm.mem_cat,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.sh_ind,sm.br_code,sm.share_val,st.shareval,sm.mem_issuedate,br.br_name,catname from ShareMaster sm,CustomerMaster cm,CustomerAddr ca ,BranchMaster br,ShareType st where concat(right(sm.mem_issuedate,4),'-',mid(sm.mem_issuedate,locate('/',sm.mem_issuedate)+1, (locate('/',sm.mem_issuedate,4)-locate('/',sm.mem_issuedate)-1)),'-',left(sm.mem_issuedate,locate('/',sm.mem_issuedate)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and st.mem_cat=sm.mem_cat and sh_ind='P'and cm.cid=ca.cid and cm.cid=sm.cid and sm.br_code=br.br_code and  ca.addr_type=sm.addr_type and sm.mem_cl_date is null and ("+str+") order by sm.ac_no"); 
				}
				else
					rs=stmt.executeQuery("select distinct sm.ac_no,sm.mem_cat,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.sh_ind,sm.share_val,st.shareval,sm.br_code,sm.mem_issuedate,br.br_name,catname,shareval from ShareMaster sm,CustomerMaster cm,CustomerAddr ca ,BranchMaster br,ShareType st where concat(right(sm.mem_issuedate,4),'-',mid(sm.mem_issuedate,locate('/',sm.mem_issuedate)+1, (locate('/',sm.mem_issuedate,4)-locate('/',sm.mem_issuedate)-1)),'-',left(sm.mem_issuedate,locate('/',sm.mem_issuedate)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and st.mem_cat=sm.mem_cat and sh_ind='P'and cm.cid=ca.cid and cm.cid=sm.cid and sm.br_code=br.br_code and  ca.addr_type=sm.addr_type and sm.mem_cl_date is null order by sm.ac_no");
			}
			rs.last();
			arr =new ShareReportObject[rs.getRow()];
			int i=0;
			rs.beforeFirst();
			while(rs.next())
			{
				
				arr[i]=new ShareReportObject();
				arr[i].setShareNumber(rs.getInt(1));
				//arr[i].setName(rs.getString("cm.fname")+" "+rs.getString("cm.mname")+" "+rs.getString("cm.lname"));
				arr[i].setName(rs.getString("name"));
				arr[i].setAddress(rs.getString("ca.address"));
				//System.out.println("Address==>"+arr[i].getAddress());
				arr[i].setCity(rs.getString("ca.city"));
				arr[i].setPin(rs.getInt("ca.pin"));
				
				arr[i].setShareStatus(rs.getString("sm.sh_ind"));
				arr[i].setBranchCode(rs.getInt("sm.br_code"));
				arr[i].setBranchName(rs.getString("br.br_name"));
				
				//arr[i].setNumberofShares(rs.getInt("sm.num_shares"));
				arr[i].setIssueDate(rs.getString("sm.mem_issuedate"));
				arr[i].setShareType(rs.getString("catname"));
				//Added by Karthi-->02/12/2005
				arr[i].setShareVal(rs.getDouble("sm.share_val"));
				arr[i].setPerShrValue(rs.getDouble("st.shareval"));
				arr[i].setNumberofShares((int)(arr[i].getShareVal()/arr[i].getPerShrValue()));
				i++;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}
		
		return arr;
	}
	
	//Share Ledger Report
	
	public ShareReportObject[] shareReport(int j,String date1,String date2,int x,String str)
	{
		System.out.println("date1 == "+date1);
		System.out.println("date2 == "+date2);
		ShareReportObject arr[]=null;
		Connection conn=null;
		
		
		ResultSet rs=null,rs_status=null;;
		int num_shares=0;
		try
		{
			conn=getConnection();
			if(j==4)//j=4 for ShareLedger
			{
			    
				Statement stmt=conn.createStatement();
				Statement stmt1=conn.createStatement();
				Statement stmt_intrname=conn.createStatement();//Added by Karthi
				
				rs_status=stmt.executeQuery("select sh_ind from ShareMaster where ac_no="+x+" and ac_type='"+str+"'");
				if(rs_status.next() && rs_status.getString("sh_ind").equals("P"))
				{
					System.out.println("sh_ind :P");
					rs=stmt.executeQuery("select distinct st.share_bal,st.sh_cert_no,sm.mem_cat,sm.mem_cl_date,st.trn_type,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,ca.address,ca.city,ca.pin,sm.ac_type,sm.ln_availed,sm.br_code,sm.div_uptodt,st.trn_date,st.trn_narr,st.trn_amt,sm.share_val,st.cd_ind,sm.intr_ac_type,sm.intr_ac_no,sm.nom_no,br_name,sm.pay_mode,st.trn_mode,st.sh_cert_no,st.sh_cert_dt,st.ref_no,sm.pay_ac_type,sm.pay_ac_no,sh.shareval from CustomerMaster cm ,BranchMaster,CustomerAddr  ca,ShareMaster  sm ,ShareTransaction st,ShareType sh where st.ac_no="+x+" and sm.cid=cm.cid and cm.cid=ca.cid and sm.ac_type=st.ac_type and sm.ac_no=st.ac_no and sm.br_code=BranchMaster.br_code and ca.addr_type=sm.addr_type and sm.mem_cat=sh.mem_cat and sm.mem_cl_date is null  order by st.trn_seq");
				}
				else
				{
					System.out.println("sh_ind :T");
					rs=stmt.executeQuery("select distinct st.share_bal,st.sh_cert_no,sm.mem_cat,sm.mem_cl_date,st.trn_type,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,ca.address,ca.city,ca.pin,sm.ac_type,sm.ln_availed,sm.br_code,sm.div_uptodt,st.trn_date,st.trn_narr,st.trn_amt,sm.share_val,st.cd_ind,sm.intr_ac_type,sm.intr_ac_no,sm.nom_no,br_name,sm.pay_mode,st.trn_mode,st.sh_cert_no,st.sh_cert_dt,st.ref_no,sm.pay_ac_type,sm.pay_ac_no,sh.shareval from CustomerMaster cm ,BranchMaster,CustomerAddr  ca,ShareMaster  sm ,ShareTransaction st,ShareType sh where st.ac_no="+x+" and sm.cid=cm.cid and cm.cid=ca.cid and sm.ac_type=st.ac_type and sm.temp_no=st.ac_no and sm.br_code=BranchMaster.br_code and ca.addr_type=sm.addr_type and sm.mem_cat=sh.mem_cat and sm.mem_cl_date is null  order by st.trn_seq");
				}
				if(rs.last())
				{	
					arr =new ShareReportObject[rs.getRow()];
					rs.beforeFirst();
				}	
				
				int i=0;
				double d=0;
				
				while(rs.next())
				{
					arr[i]=new ShareReportObject();
					
					if(rs.getInt("sm.share_val")!=0)
						num_shares=(int)(rs.getFloat("st.share_bal")/rs.getFloat("sh.shareval"));
					System.out.println("@@Num_shr : "+num_shares);
					{
						if(i==0)
						{
							arr[i].setShareAccType(rs.getString("ac_type"));//Added by Karthi
							arr[i].setIntrAcctype(rs.getString("sm.intr_ac_type"));
							arr[i].setIntrAccno(rs.getInt("sm.intr_ac_no"));
							if(rs.getString("mem_cl_date")==null)
								arr[i].setAccStatus("O");
							else
								arr[i].setAccStatus("C");
							
							//if(arr[i].getIntrAccno()!=0)
							//arr[i].setIntrName(CommonRemote.getAccount(arr[i].getIntrAcctype(),arr[i].getIntrAccno()).getAccname() );
							
							//Added by Karthi-->Get the Intr.name
							ResultSet rs_intrname=stmt_intrname.executeQuery("Select sm.cid,concat_ws(' ',cm.fname,cm.mname,cm.lname)as iname  from ShareMaster sm,CustomerMaster cm where sm.ac_no="+arr[i].getIntrAccno()+" and sm.cid=cm.cid ");
							rs_intrname.last();
							if(rs_intrname.getRow()>0)
							{
								rs_intrname.beforeFirst();
								rs_intrname.next();
								arr[i].setIntrName(rs_intrname.getString("iname"));
								//arr[i].setIntrName(rs_intrname.getString(3));
							}
							else
								arr[i].setIntrName(" ");
							
							int nom_no=rs.getInt("sm.nom_no");
							Statement stmt2=conn.createStatement();
							ResultSet rs3=stmt2.executeQuery("Select name,relation from NomineeMaster where reg_no="+nom_no+" ");// relation-->Added by Karthi
							rs3.last();
							if(rs3.getRow()>0)
							{
								rs3.beforeFirst();
								rs3.next();
								arr[i].setNomineeName(rs3.getString("name"));
								arr[i].setNomineeRelation(rs3.getString("relation"));
								System.out.println("Name="+arr[i].getNomineeName());
							}
							else
								arr[i].setNomineeName(" ");
							
							arr[i].setNumberofShares(num_shares);
							arr[i].setBranchName(rs.getString("br_name"));
							//arr[i].setName(rs.getString("cm.fname")+" "+rs.getString("cm.mname")+" "+rs.getString("cm.lname"));
							arr[i].setName(rs.getString("name"));//Karthi
							arr[i].setShareCertNumber(rs.getInt("st.sh_cert_no"));//Karthi
							arr[i].setShareCertDate(rs.getString("st.sh_cert_dt"));
							arr[i].setAddress(rs.getString("ca.address"));
							arr[i].setCity(rs.getString("ca.city"));
							arr[i].setPin(rs.getInt("ca.pin"));
							arr[i].setBranchCode(rs.getInt("sm.br_code"));
							arr[i].setDivUptoDate(rs.getString("sm.div_uptodt"));
							
							ResultSet rs1=stmt1.executeQuery("select catname,shareval from ShareType where mem_cat="+rs.getInt("mem_cat")+" and ac_type='"+arr[i].getShareAccType()+"'");
							if(rs1.next())
							{
								arr[i].setShareStatus(rs1.getString("catname"));
								d=rs1.getDouble("shareval");
							}
							arr[i].setPayMode(rs.getString("sm.pay_mode"));
							arr[i].setPaymentAccno(rs.getInt("sm.pay_ac_no"));
							arr[i].setPaymentAcctype(rs.getString("sm.pay_ac_type"));
							arr[i].setLoanAvailed(rs.getString("sm.ln_availed"));
						}
					}
					//System.out.println("1............");
					arr[i].setShareCertNumber(rs.getInt("st.sh_cert_no"));
					arr[i].setShareCertDate(rs.getString("st.sh_cert_dt"));
					arr[i].setTransDate(rs.getString("st.trn_date"));
					arr[i].setShareVal(rs.getFloat("sm.share_val"));
					//System.out.println("1............");
					
					arr[i].setReceivedMode(rs.getString("st.trn_mode"));
					
					if(arr[i].getReceivedMode().equals("T") || arr[i].getReceivedMode().equals("B"))
					{    
						/*String narr=rs.getString("st.trn_narr");
						 if(narr!=null)
						 arr[i].setTransNarr("Trf. "+narr);
						 else
						 arr[i].setTransNarr("Trf. 0");*/
						//Added By Karthi-->15/06/2006
						String tran_narr=rs.getString("st.trn_narr");
						System.out.println("tran_narr :"+tran_narr);
						if(tran_narr!=null)
						{
							if(tran_narr.equalsIgnoreCase("Voucher"))
							{int ref_no=rs.getInt("st.ref_no");
							arr[i].setTransNarr("Voucher "+ref_no);}
							else
							{
								if(rs.getString("trn_type").equalsIgnoreCase("A")||rs.getString("trn_type").equalsIgnoreCase("W"))
								{
									int index=tran_narr.indexOf(" ");
									System.out.println("index"+index);
									String ac_type=tran_narr.substring(0,index);
									System.out.println("ac_type"+ac_type);
									int last_index=tran_narr.indexOf(" ");
									System.out.println("last_index"+last_index);
									System.out.println("end index"+tran_narr.length());
									String ac_no=tran_narr.substring(last_index,tran_narr.length());
									System.out.println("acno"+ac_no);
									//arr[i].setTransNarr(rs.getString("st.trn_narr"));
									Statement stmt_mc=conn.createStatement();
									ResultSet rs_mc=stmt_mc.executeQuery("select moduleabbr from Modules where 	modulecode='"+ac_type+"'");
									rs_mc.next();
									String 	module_abbr=rs_mc.getString(1);
									String trn_narr=module_abbr+" "+ac_no;
									System.out.println("trn_narr"+trn_narr);
									if(rs.getString("st.cd_ind").equals("D"))
										arr[i].setTransNarr("Transferred  To:"+trn_narr);
									else
										arr[i].setTransNarr("Transferred Frm:"+trn_narr);
								}
								else if(rs.getString("st.trn_type").equalsIgnoreCase("I"))
								{
									int index1=tran_narr.indexOf(" ");
									System.out.println("index"+index1);
									String tran_narr1=tran_narr.substring(index1,tran_narr.length()).trim();
									System.out.println("Actual trn.narr:"+tran_narr1);
									int index2=tran_narr1.indexOf(" ");
									System.out.println("index2 :"+index2);
									String ac_type=tran_narr1.substring(0,index2).trim();
									System.out.println("Ac_type : "+ac_type);
									String ac_no=tran_narr1.substring(index2,tran_narr1.length()).trim();
									System.out.println("acno"+ac_no);
									//arr[i].setTransNarr(rs.getString("st.trn_narr"));
									Statement stmt_mc=conn.createStatement();
									ResultSet rs_mc=stmt_mc.executeQuery("select moduleabbr from Modules where 	modulecode='"+ac_type+"' ");
									rs_mc.next();
									String 	module_abbr=rs_mc.getString(1);
									String trn_narr=module_abbr+" "+ac_no;
									System.out.println("trn_narr"+trn_narr);
									if(rs.getString("st.cd_ind").equals("D"))
										arr[i].setTransNarr("Dividend Transferred To:"+trn_narr);
									else
										arr[i].setTransNarr("Dividend Transferred Frm:"+trn_narr);
								}
							}
							
						}
						else
						{
							if(rs.getString("st.cd_ind").equals("D"))
								arr[i].setTransNarr("Transferred To:  --");
							else
								arr[i].setTransNarr("Transferred Frm:  --");
						}
					}	
					else if(arr[i].getReceivedMode().equals("C"))
						arr[i].setTransNarr("Cash Scroll No. "+rs.getString("st.ref_no"));
					else if(arr[i].getReceivedMode().equals("PO"))
						arr[i].setTransNarr("PayOrdr. "+rs.getString("st.ref_no"));
					//Added by Karthi 24/05/2006
					else if(arr[i].getReceivedMode().equals("G"))
						arr[i].setTransNarr("Control No. "+rs.getString("st.ref_no"));
					
					
					double trnamt=rs.getFloat("st.trn_amt");			
					if(rs.getString("st.cd_ind").equals("C"))
					{arr[i].setCreditAmount(trnamt);arr[i].setDebitAmount(0);System.out.println("** Credit Amt. :"+arr[i].getCreditAmount());}
					else if(rs.getString("st.cd_ind").equals("D"))
					{arr[i].setDebitAmount(trnamt);arr[i].setCreditAmount(0);System.out.println("** Debit Amt. :"+arr[i].getDebitAmount());}
					
					String ty=rs.getString("st.trn_type");
					System.out.println("***trn_type : "+ty);
					if(ty.equals("A") || ty.equals("W"))
					{arr[i].setshare_bal((int)(trnamt/d));}
					else if(ty.equals("I"))
					{arr[i].setshare_bal(num_shares);}
					
					arr[i].setTransType(ty);
					arr[i].setcd_ind(rs.getString("st.cd_ind"));
					i++;
				}
			}
			if(j==3)//j=3 for passbook
			{
//				conn=getConnection();
				Statement stmt=conn.createStatement();
				Statement stmt1=conn.createStatement();
				
				rs_status=stmt.executeQuery("select sh_ind from ShareMaster where ac_no="+x+" and ac_type='"+str+"'");
				
				/**Swaran--changed the query to display pass book details for closed accounts also.
				 */
				//rs=stmt.executeQuery("select sm.mem_cat,st.trn_type,cm.fname,cm.mname,cm.lname,ca.address,ca.city,ca.pin,sm.ln_availed,sm.br_code,sm.div_uptodt,st.trn_date,st.trn_narr,st.trn_amt,sm.share_val,st.cd_ind,sm.intr_ac_type,sm.intr_ac_no,sm.nom_no,br_name,sm.pay_mode,st.trn_mode,st.sh_cert_no,st.sh_cert_dt,st.ref_no,sm.pay_ac_type,sm.pay_ac_no,sm.cid from CustomerMaster cm ,BranchMaster,CustomerAddr  ca,ShareMaster  sm ,ShareTransaction st where st.ac_no="+x+"  and st.ac_type='"+str+"' and sm.cid=cm.cid and cm.cid=ca.cid and sm.ac_type=st.ac_type and sm.ac_no=st.ac_no and sm.br_code=BranchMaster.br_code and ca.addr_type=sm.addr_type   and sm.mem_cl_date is null order by st.trn_no");
				if(rs_status.next() && rs_status.getString("sh_ind").equalsIgnoreCase("P"))
					rs=stmt.executeQuery("select sm.mem_cat,sm.mem_cl_date,st.trn_type,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.ln_availed,sm.br_code,sm.div_uptodt,st.trn_date,st.trn_narr,st.ref_no,st.trn_amt,st.share_bal,sm.share_val,st.cd_ind,sm.intr_ac_type,sm.intr_ac_no,sm.nom_no,br_name,sm.pay_mode,st.trn_mode,st.sh_cert_no,st.sh_cert_dt,st.ref_no,sm.pay_ac_type,sm.pay_ac_no,sm.cid,st.de_user,st.ve_user from CustomerMaster cm ,BranchMaster,CustomerAddr  ca,ShareMaster  sm ,ShareTransaction st where st.ac_no="+x+"  and st.ac_type='"+str+"' and sm.cid=cm.cid and cm.cid=ca.cid and sm.ac_type=st.ac_type and sm.ac_no=st.ac_no and sm.br_code=BranchMaster.br_code and ca.addr_type=sm.addr_type and st.ve_user is not null order by st.trn_seq");
				else
					rs=stmt.executeQuery("select sm.mem_cat,sm.mem_cl_date,st.trn_type,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.ln_availed,sm.br_code,sm.div_uptodt,st.trn_date,st.trn_narr,st.ref_no,st.trn_amt,st.share_bal,sm.share_val,st.cd_ind,sm.intr_ac_type,sm.intr_ac_no,sm.nom_no,br_name,sm.pay_mode,st.trn_mode,st.sh_cert_no,st.sh_cert_dt,st.ref_no,sm.pay_ac_type,sm.pay_ac_no,sm.cid,st.de_user,st.ve_user from CustomerMaster cm ,BranchMaster,CustomerAddr  ca,ShareMaster  sm ,ShareTransaction st where st.ac_no="+x+"  and st.ac_type='"+str+"' and sm.cid=cm.cid and cm.cid=ca.cid and sm.ac_type=st.ac_type and sm.temp_no=st.ac_no and sm.br_code=BranchMaster.br_code and ca.addr_type=sm.addr_type and st.ve_user is not null order by st.trn_seq");
				if(rs.last())
				{	
					arr =new ShareReportObject[rs.getRow()];
					rs.beforeFirst();
				}
				int i=0;
				double d=0;
				
				while(rs.next())
				{
					arr[i]=new ShareReportObject();
					
					if(i==0)
					{
						
						arr[i].setShareAccType(str);
						arr[i].setBranchName(rs.getString("br_name"));
						//arr[i].setName(rs.getString("cm.fname")+" "+rs.getString("cm.mname")+" "+rs.getString("cm.lname"));
						arr[i].setName(rs.getString("name"));
						if(rs.getString("mem_cl_date")==null)
							arr[i].setAccStatus("O");
						else
							arr[i].setAccStatus("C");
						
						arr[i].setCustomerId(rs.getInt("sm.cid"));
						arr[i].setAddress(rs.getString("ca.address"));
						arr[i].setCity(rs.getString("ca.city"));
						arr[i].setPin(rs.getInt("ca.pin"));
						arr[i].setBranchCode(rs.getInt("sm.br_code"));
						arr[i].setDivUptoDate(rs.getString("sm.div_uptodt"));
						ResultSet rs1=stmt1.executeQuery("select catname,shareval from ShareType where mem_cat="+rs.getInt(1)+" and ac_type='"+arr[i].getShareAccType()+"'");
						if(rs1.next())
						{
							arr[i].setShareStatus(rs1.getString("catname"));
							d=rs1.getDouble("shareval");
						}
						arr[i].setLoanAvailed(rs.getString("sm.ln_availed"));
					}
					arr[i].setdeUserId(rs.getString("st.de_user"));
					arr[i].setVeUserId(rs.getString("st.ve_user"));
					arr[i].setTransDate(rs.getString("st.trn_date"));
					System.out.println("sharevale"+rs.getFloat("sm.share_val"));
					arr[i].setShareVal(rs.getFloat("sm.share_val"));
					arr[i].setReceivedMode(rs.getString("st.trn_mode"));
					
					if(arr[i].getReceivedMode().equals("T") || arr[i].getReceivedMode().equals("B") )
					{	
						String tran_narr=rs.getString("st.trn_narr");
						System.out.println("tran_narr :"+tran_narr);
						if(tran_narr!=null)
						{
							if(tran_narr.equalsIgnoreCase("Voucher"))
							{int ref_no=rs.getInt("st.ref_no");
							arr[i].setTransNarr("Voucher "+ref_no);}
							else
							{
								if(rs.getString("trn_type").equalsIgnoreCase("A") || rs.getString("trn_type").equalsIgnoreCase("W"))
								{
									int index=tran_narr.indexOf(" ");
									System.out.println("index"+index);
									String ac_type=tran_narr.substring(0,index);
									System.out.println("ac_type"+ac_type);
									int last_index=tran_narr.indexOf(" ");
									System.out.println("last_index"+last_index);
									System.out.println("end index"+tran_narr.length());
									String ac_no=tran_narr.substring(last_index,tran_narr.length());
									System.out.println("acno"+ac_no);
									//arr[i].setTransNarr(rs.getString("st.trn_narr"));
									Statement stmt_mc=conn.createStatement();
									ResultSet rs_mc=stmt_mc.executeQuery("select moduleabbr from Modules where 	modulecode='"+ac_type+"' ");
									rs_mc.next();
									String 	module_abbr=rs_mc.getString(1);
									String trn_narr=module_abbr+" "+ac_no;
									System.out.println("trn_narr"+trn_narr);
									if(rs.getString("st.cd_ind").equals("D"))
										arr[i].setTransNarr("Transferred  To:"+trn_narr);
									else
										arr[i].setTransNarr("Transferred Frm:"+trn_narr);
								}
								else if(rs.getString("trn_type").equalsIgnoreCase("I"))
								{
									int index1=tran_narr.indexOf(" ");
									System.out.println("index"+index1);
									String tran_narr1=tran_narr.substring(index1,tran_narr.length()).trim();
									System.out.println("Actual trn.narr:"+tran_narr1);
									int index2=tran_narr1.indexOf(" ");
									System.out.println("index2 :"+index2);
									String ac_type=tran_narr1.substring(0,index2).trim();
									System.out.println("Ac_type : "+ac_type);
									String ac_no=tran_narr1.substring(index2,tran_narr1.length()).trim();
									System.out.println("acno"+ac_no);
									//arr[i].setTransNarr(rs.getString("st.trn_narr"));
									Statement stmt_mc=conn.createStatement();
									ResultSet rs_mc=stmt_mc.executeQuery("select moduleabbr from Modules where 	modulecode='"+ac_type+"' ");
									rs_mc.next();
									String 	module_abbr=rs_mc.getString(1);
									String trn_narr=module_abbr+" "+ac_no;
									System.out.println("trn_narr"+trn_narr);
									if(rs.getString("st.cd_ind").equals("D"))
										arr[i].setTransNarr("Dividend Transferred To:"+trn_narr);
									else
										arr[i].setTransNarr("Dividend Transferred Frm:"+trn_narr);
								}
							}
						}
						else
						{
							if(rs.getString("st.cd_ind").equals("D"))
								arr[i].setTransNarr("Transferred To:  --");
							else
								arr[i].setTransNarr("Transferred Frm:  --");
						}
					}
					else if(arr[i].getReceivedMode().equals("C"))
						arr[i].setTransNarr("Cash Scroll No. "+rs.getString("st.ref_no"));
					else if(arr[i].getReceivedMode().equals("PO"))
						arr[i].setTransNarr("PayOrder. "+rs.getString("st.ref_no"));
					//Added by Karthi 24/05/2006
					else if(arr[i].getReceivedMode().equals("G"))
						arr[i].setTransNarr("Control No. "+rs.getString("st.ref_no"));
					
					
					double trnamt=rs.getFloat("st.trn_amt");
					
					if(rs.getString("st.cd_ind").equals("C"))
					{arr[i].setCreditAmount(trnamt);arr[i].setDebitAmount(0);System.out.println("** Credit Amt. :"+arr[i].getCreditAmount());}
					else if(rs.getString("st.cd_ind").equals("D"))
					{arr[i].setDebitAmount(trnamt);arr[i].setCreditAmount(0);System.out.println("** Debit Amt. :"+arr[i].getDebitAmount());}
					
					
					String ty=rs.getString("st.trn_type");
					
					System.out.println("Trn. Type :"+ty);
					
					arr[i].setTransType(ty);
					System.out.println("------------------Transaction Amount"+trnamt +"Share value "+d);
					
					if(ty.equals("A") || ty.equals("W"))
					{
						//arr[i].setshare_bal((int)(trnamt));
						arr[i].setshare_bal(rs.getInt("st.share_bal"));
						System.out.println("Th ebalance after ecah trancsaction is ------>"+rs.getInt("st.share_bal"));
						System.out.println("share_bal"+(int)(trnamt));
					}
					else if(ty.equalsIgnoreCase("I"))
						arr[i].setshare_bal((int)(rs.getDouble("st.share_bal")/d));
					arr[i].setcd_ind(rs.getString("st.cd_ind"));
					System.out.println(".........");
					arr[i].setCustomerId(rs.getInt("sm.cid"));
					System.out.println(".........++++++++");
					i++;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}
		return arr;
	}
	
	//////////////////////////////////////////////////////
	
	public ShareReportObject[] voterList(String str)
	{
		
		ShareReportObject arr[]=null;
		Connection conn=null;
		ResultSet rs=null;
		System.out.println("string==>"+str);
		
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			
			if(str!=null)
				rs=stmt.executeQuery("select  distinct sm.ac_no,concat_ws('',cm.fname,cm.mname,cm.lname) as name,ca.address,ca.city,ca.pin,sm.sh_ind from ShareMaster sm,CustomerMaster cm,CustomerAddr ca ,BranchMaster br,ShareType stype where sm.mem_cl_date is null and stype.vote_pow='yes' and sm.sh_ind='P'and sm.mem_cat=stype.mem_cat and sm.cid=ca.cid and sm.cid=cm.cid and sm.br_code=br.br_code and ca.addr_type=sm.addr_type and ("+str+")");	
			else
				rs=stmt.executeQuery("select  distinct sm.ac_no,concat_ws('',cm.fname,cm.mname,cm.lname) as name,ca.address,ca.city,ca.pin,sm.sh_ind from ShareMaster sm,CustomerMaster cm,CustomerAddr ca ,BranchMaster br,ShareType stype where sm.mem_cl_date is null and stype.vote_pow='yes' and sm.sh_ind='P'and sm.mem_cat=stype.mem_cat and sm.cid=ca.cid and sm.cid=cm.cid and sm.br_code=br.br_code and ca.addr_type=sm.addr_type");	
			
			rs.last();
			arr =new ShareReportObject[rs.getRow()];
			int i=0;
			rs.beforeFirst();
			
			while(rs.next())
			{
				
				arr[i]=new ShareReportObject();
				arr[i].setShareNumber(rs.getInt("sm.ac_no"));
				//arr[i].setName(rs.getString("cm.fname")+" "+rs.getString("cm.mname")+" "+rs.getString("cm.lname"));
				arr[i].setName(rs.getString("name"));
				arr[i].setAddress(rs.getString("ca.address"));
				arr[i].setCity(rs.getString("ca.city"));
				arr[i].setPin(rs.getInt("ca.pin"));
				arr[i].setShareStatus(rs.getString("sm.sh_ind"));
				i++;
			}
		}catch(Exception e){e.printStackTrace();}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}
		
		return arr;
	}
	
	public ShareReportObject[] shareOpenClosed(int j,String date1,String date2,String str)
	{
		System.out.println("string == "+str);
		ShareReportObject arr[]=null;
		Connection conn=null;
		ResultSet rs=null;
		StringTokenizer str1=null;
		String num_shares=null;
		
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			
			if(j==6)//Opened shares
			{
				System.out.println("OPENED SHARES");
				if(str==null)
				
					rs=stmt.executeQuery("select distinct sm.ac_no,sm.ac_type,sm.temp_no,sm.mem_cat,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.share_val,st.shareval,sm.sh_ind,sm.br_code,sm.mem_issuedate,sm.cid,cm.scst,cm.sex from CustomerMaster cm ,CustomerAddr  ca,ShareMaster sm,ShareType st where sm.mem_cl_date is null and concat(right(sm.mem_issuedate,4),'-',mid(sm.mem_issuedate,locate('/',sm.mem_issuedate)+1, (locate('/',sm.mem_issuedate,4)-locate('/',sm.mem_issuedate)-1)),'-',left(sm.mem_issuedate,locate('/',sm.mem_issuedate)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"'and cm.cid=ca.cid and cm.cid=sm.cid and ca.addr_type=sm.addr_type and sm.mem_cat=st.mem_cat order by sm.ac_no");
				//Added by Karthi==>14/04/2006
				else if(str.startsWith("sm.num_shares <") || str.startsWith("sm.num_shares > ")||str.startsWith("sm.num_shares <=")|| str.startsWith("sm.num_shares >=") || str.startsWith("sm.num_shares =") || str.startsWith("sm.num_shares <>"))
				{
					if(str.startsWith("sm.num_shares <")&& str.length()==18)
					{
						str1=new StringTokenizer(str,"<");
						str1.nextToken();
						num_shares=str1.nextToken();
						System.out.println("Num.of shares:"+num_shares);
						rs=stmt.executeQuery("select distinct sm.ac_no,sm.ac_type,sm.temp_no,sm.mem_cat,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.share_val,st.shareval,sm.sh_ind,sm.br_code,sm.mem_issuedate,sm.cid,cm.scst,cm.sex from CustomerMaster cm ,CustomerAddr  ca,ShareMaster sm,ShareType st where sm.mem_cl_date is null and concat(right(sm.mem_issuedate,4),'-',mid(sm.mem_issuedate,locate('/',sm.mem_issuedate)+1, (locate('/',sm.mem_issuedate,4)-locate('/',sm.mem_issuedate)-1)),'-',left(sm.mem_issuedate,locate('/',sm.mem_issuedate)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and sm.share_val<("+num_shares+"*st.shareval) and cm.cid=ca.cid and cm.cid=sm.cid and ca.addr_type=sm.addr_type and sm.mem_cat=st.mem_cat order by sm.ac_no");
					}
					else if(str.startsWith("sm.num_shares >")&& str.length()==18)
					{
						str1=new StringTokenizer(str,">");
						str1.nextToken();
						num_shares=str1.nextToken();
						System.out.println("Num.of shares:"+num_shares);
						rs=stmt.executeQuery("select distinct sm.ac_no,sm.ac_type,sm.temp_no,sm.mem_cat,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.share_val,st.shareval,sm.sh_ind,sm.br_code,sm.mem_issuedate,sm.cid,cm.scst,cm.sex from CustomerMaster cm ,CustomerAddr  ca,ShareMaster sm,ShareType st where sm.mem_cl_date is null and concat(right(sm.mem_issuedate,4),'-',mid(sm.mem_issuedate,locate('/',sm.mem_issuedate)+1, (locate('/',sm.mem_issuedate,4)-locate('/',sm.mem_issuedate)-1)),'-',left(sm.mem_issuedate,locate('/',sm.mem_issuedate)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and sm.share_val>("+num_shares+"*st.shareval) and cm.cid=ca.cid and cm.cid=sm.cid and ca.addr_type=sm.addr_type and sm.mem_cat=st.mem_cat order by sm.ac_no");
					}
					else if(str.startsWith("sm.num_shares <=")&& str.length()==19)
					{
						str1=new StringTokenizer(str,"<=");
						str1.nextToken();
						num_shares=str1.nextToken();
						System.out.println("Num.of shares:"+num_shares);
						rs=stmt.executeQuery("select distinct sm.ac_no,sm.ac_type,sm.temp_no,sm.mem_cat,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.share_val,st.shareval,sm.sh_ind,sm.br_code,sm.mem_issuedate,sm.cid,cm.scst,cm.sex from CustomerMaster cm ,CustomerAddr  ca,ShareMaster sm,ShareType st where sm.mem_cl_date is null and concat(right(sm.mem_issuedate,4),'-',mid(sm.mem_issuedate,locate('/',sm.mem_issuedate)+1, (locate('/',sm.mem_issuedate,4)-locate('/',sm.mem_issuedate)-1)),'-',left(sm.mem_issuedate,locate('/',sm.mem_issuedate)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and sm.share_val<=("+num_shares+"*st.shareval) and cm.cid=ca.cid and cm.cid=sm.cid and ca.addr_type=sm.addr_type and sm.mem_cat=st.mem_cat order by sm.ac_no");
					}
					else if(str.startsWith("sm.num_shares >=")&& str.length()==19)
					{
						str1=new StringTokenizer(str,">=");
						str1.nextToken();
						num_shares=str1.nextToken();
						System.out.println("Num.of shares:"+num_shares);
						rs=stmt.executeQuery("select distinct sm.ac_no,sm.ac_type,sm.temp_no,sm.mem_cat,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.share_val,st.shareval,sm.sh_ind,sm.br_code,sm.mem_issuedate,sm.cid,cm.scst,cm.sex from CustomerMaster cm ,CustomerAddr  ca,ShareMaster sm,ShareType st where sm.mem_cl_date is null and concat(right(sm.mem_issuedate,4),'-',mid(sm.mem_issuedate,locate('/',sm.mem_issuedate)+1, (locate('/',sm.mem_issuedate,4)-locate('/',sm.mem_issuedate)-1)),'-',left(sm.mem_issuedate,locate('/',sm.mem_issuedate)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and sm.share_val>=("+num_shares+"*st.shareval) and cm.cid=ca.cid and cm.cid=sm.cid and ca.addr_type=sm.addr_type and sm.mem_cat=st.mem_cat order by sm.ac_no");
					}
					else if(str.startsWith("sm.num_shares ="))
					{
						str1=new StringTokenizer(str,"=");
						str1.nextToken();
						num_shares=str1.nextToken();
						System.out.println("Num.of shares:"+num_shares);
						rs=stmt.executeQuery("select distinct sm.ac_no,sm.ac_type,sm.temp_no,sm.mem_cat,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.share_val,st.shareval,sm.sh_ind,sm.br_code,sm.mem_issuedate,sm.cid,cm.scst,cm.sex from CustomerMaster cm ,CustomerAddr  ca,ShareMaster sm,ShareType st where sm.mem_cl_date is null and concat(right(sm.mem_issuedate,4),'-',mid(sm.mem_issuedate,locate('/',sm.mem_issuedate)+1, (locate('/',sm.mem_issuedate,4)-locate('/',sm.mem_issuedate)-1)),'-',left(sm.mem_issuedate,locate('/',sm.mem_issuedate)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and sm.share_val=("+num_shares+"*st.shareval) and cm.cid=ca.cid and cm.cid=sm.cid and ca.addr_type=sm.addr_type and sm.mem_cat=st.mem_cat order by sm.ac_no");
					}
					else if(str.startsWith("sm.num_shares <>"))
					{
						str1=new StringTokenizer(str,"<>");
						str1.nextToken();
						num_shares=str1.nextToken();
						System.out.println("Num.of shares:"+num_shares);
						rs=stmt.executeQuery("select distinct sm.ac_no,sm.ac_type,sm.temp_no,sm.mem_cat,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.share_val,st.shareval,sm.sh_ind,sm.br_code,sm.mem_issuedate,sm.cid,cm.scst,cm.sex from CustomerMaster cm ,CustomerAddr  ca,ShareMaster sm,ShareType st where sm.mem_cl_date is null and concat(right(sm.mem_issuedate,4),'-',mid(sm.mem_issuedate,locate('/',sm.mem_issuedate)+1, (locate('/',sm.mem_issuedate,4)-locate('/',sm.mem_issuedate)-1)),'-',left(sm.mem_issuedate,locate('/',sm.mem_issuedate)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and sm.share_val<>("+num_shares+"*st.shareval) and cm.cid=ca.cid and cm.cid=sm.cid and ca.addr_type=sm.addr_type and sm.mem_cat=st.mem_cat order by sm.ac_no");
					}
				}
				else
					rs=stmt.executeQuery("select distinct sm.ac_no,sm.ac_type,sm.temp_no,sm.mem_cat,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.share_val,st.shareval,sm.sh_ind,sm.br_code,sm.mem_issuedate,sm.cid,cm.scst,cm.sex from CustomerMaster cm ,CustomerAddr  ca,ShareMaster sm,ShareType st where sm.mem_cl_date is null and concat(right(sm.mem_issuedate,4),'-',mid(sm.mem_issuedate,locate('/',sm.mem_issuedate)+1, (locate('/',sm.mem_issuedate,4)-locate('/',sm.mem_issuedate)-1)),'-',left(sm.mem_issuedate,locate('/',sm.mem_issuedate)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and cm.cid=ca.cid and cm.cid=sm.cid and ca.addr_type=sm.addr_type and sm.mem_cat=st.mem_cat and ("+str+") order by sm.ac_no");
			}
			//rs=stmt.executeQuery("select distinct sm.ac_no,sm.temp_no,cm.fname,cm.mname,cm.lname,ca.address,ca.city,ca.pin,sm.share_val,sm.sh_ind,sm.br_code,sm.num_shares,sm.mem_issuedate,sm.cid,cm.scst,cm.sex from CustomerMaster cm ,CustomerAddr  ca,ShareMaster  sm where sm.mem_cl_date is null and concat(right(sm.mem_issuedate,4),'-',mid(sm.mem_issuedate,locate('/',sm.mem_issuedate)+1, (locate('/',sm.mem_issuedate,4)-locate('/',sm.mem_issuedate)-1)),'-',left(sm.mem_issuedate,locate('/',sm.mem_issuedate)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and cm.cid=ca.cid and cm.cid=sm.cid and ca.addr_type=sm.addr_type");
			if(j==7)//Closed Shares
			{
				System.out.println("CLOSED SHARES");
				if(str==null)
					rs=stmt.executeQuery("select distinct sm.ac_no,sm.ac_type,sm.temp_no,sm.mem_cat,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.share_val,st.shareval,sm.sh_ind,sm.br_code,sm.mem_issuedate,sm.cid,cm.scst,cm.sex from CustomerMaster cm ,CustomerAddr  ca,ShareMaster sm,ShareType st where sm.mem_cl_date is not null and concat(right(sm.mem_cl_date,4),'-',mid(sm.mem_cl_date,locate('/',sm.mem_cl_date)+1, (locate('/',sm.mem_cl_date,4)-locate('/',sm.mem_cl_date)-1)),'-',left(sm.mem_cl_date,locate('/',sm.mem_cl_date)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and cm.cid=ca.cid and cm.cid=sm.cid and ca.addr_type=sm.addr_type and sm.mem_cat=st.mem_cat order by sm.ac_no");
				//Added by Karthi==>14/04/2006
				else if(str.startsWith("sm.num_shares <") || str.startsWith("sm.num_shares > ")||str.startsWith("sm.num_shares <=")|| str.startsWith("sm.num_shares >=") || str.startsWith("sm.num_shares =") || str.startsWith("sm.num_shares <>"))
				{
					if(str.startsWith("sm.num_shares <")&& str.length()==18)
					{
						str1=new StringTokenizer(str,"<");
						str1.nextToken();
						num_shares=str1.nextToken();
						System.out.println("Num.of shares:"+num_shares);
						rs=stmt.executeQuery("select distinct sm.ac_no,sm.ac_type,sm.temp_no,sm.mem_cat,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.share_val,st.shareval,sm.sh_ind,sm.br_code,sm.mem_issuedate,sm.cid,cm.scst,cm.sex from CustomerMaster cm ,CustomerAddr  ca,ShareMaster sm,ShareType st where sm.mem_cl_date is not null and concat(right(sm.mem_issuedate,4),'-',mid(sm.mem_issuedate,locate('/',sm.mem_issuedate)+1, (locate('/',sm.mem_issuedate,4)-locate('/',sm.mem_issuedate)-1)),'-',left(sm.mem_issuedate,locate('/',sm.mem_issuedate)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and sm.share_val<("+num_shares+"*st.shareval) and cm.cid=ca.cid and cm.cid=sm.cid and ca.addr_type=sm.addr_type and sm.mem_cat=st.mem_cat order by sm.ac_no");
					}
					else if(str.startsWith("sm.num_shares >")&& str.length()==18)
					{
						str1=new StringTokenizer(str,">");
						str1.nextToken();
						num_shares=str1.nextToken();
						System.out.println("Num.of shares:"+num_shares);
						rs=stmt.executeQuery("select distinct sm.ac_no,sm.ac_type,sm.temp_no,sm.mem_cat,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.share_val,st.shareval,sm.sh_ind,sm.br_code,sm.mem_issuedate,sm.cid,cm.scst,cm.sex from CustomerMaster cm ,CustomerAddr  ca,ShareMaster sm,ShareType st where sm.mem_cl_date is not null and concat(right(sm.mem_issuedate,4),'-',mid(sm.mem_issuedate,locate('/',sm.mem_issuedate)+1, (locate('/',sm.mem_issuedate,4)-locate('/',sm.mem_issuedate)-1)),'-',left(sm.mem_issuedate,locate('/',sm.mem_issuedate)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and sm.share_val>("+num_shares+"*st.shareval) and cm.cid=ca.cid and cm.cid=sm.cid and ca.addr_type=sm.addr_type and sm.mem_cat=st.mem_cat order by sm.ac_no");
					}
					else if(str.startsWith("sm.num_shares <=")&& str.length()==19)
					{
						str1=new StringTokenizer(str,"<=");
						str1.nextToken();
						num_shares=str1.nextToken();
						System.out.println("Num.of shares:"+num_shares);
						rs=stmt.executeQuery("select distinct sm.ac_no,sm.ac_type,sm.temp_no,sm.mem_cat,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.share_val,st.shareval,sm.sh_ind,sm.br_code,sm.mem_issuedate,sm.cid,cm.scst,cm.sex from CustomerMaster cm ,CustomerAddr  ca,ShareMaster sm,ShareType st where sm.mem_cl_date is not null and concat(right(sm.mem_issuedate,4),'-',mid(sm.mem_issuedate,locate('/',sm.mem_issuedate)+1, (locate('/',sm.mem_issuedate,4)-locate('/',sm.mem_issuedate)-1)),'-',left(sm.mem_issuedate,locate('/',sm.mem_issuedate)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and sm.share_val<=("+num_shares+"*st.shareval) and cm.cid=ca.cid and cm.cid=sm.cid and ca.addr_type=sm.addr_type and sm.mem_cat=st.mem_cat order by sm.ac_no");
					}
					else if(str.startsWith("sm.num_shares >=")&& str.length()==19)
					{
						str1=new StringTokenizer(str,">=");
						str1.nextToken();
						num_shares=str1.nextToken();
						System.out.println("Num.of shares:"+num_shares);
						rs=stmt.executeQuery("select distinct sm.ac_no,sm.ac_type,sm.temp_no,sm.mem_cat,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.share_val,st.shareval,sm.sh_ind,sm.br_code,sm.mem_issuedate,sm.cid,cm.scst,cm.sex from CustomerMaster cm ,CustomerAddr  ca,ShareMaster sm,ShareType st where sm.mem_cl_date is not null and concat(right(sm.mem_issuedate,4),'-',mid(sm.mem_issuedate,locate('/',sm.mem_issuedate)+1, (locate('/',sm.mem_issuedate,4)-locate('/',sm.mem_issuedate)-1)),'-',left(sm.mem_issuedate,locate('/',sm.mem_issuedate)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and sm.share_val>=("+num_shares+"*st.shareval) and cm.cid=ca.cid and cm.cid=sm.cid and ca.addr_type=sm.addr_type and sm.mem_cat=st.mem_cat order by sm.ac_no");
					}
					else if(str.startsWith("sm.num_shares ="))
					{
						str1=new StringTokenizer(str,"=");
						str1.nextToken();
						num_shares=str1.nextToken();
						System.out.println("Num.of shares:"+num_shares);
						rs=stmt.executeQuery("select distinct sm.ac_no,sm.ac_type,sm.temp_no,sm.mem_cat,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.share_val,st.shareval,sm.sh_ind,sm.br_code,sm.mem_issuedate,sm.cid,cm.scst,cm.sex from CustomerMaster cm ,CustomerAddr  ca,ShareMaster sm,ShareType st where sm.mem_cl_date is not null and concat(right(sm.mem_issuedate,4),'-',mid(sm.mem_issuedate,locate('/',sm.mem_issuedate)+1, (locate('/',sm.mem_issuedate,4)-locate('/',sm.mem_issuedate)-1)),'-',left(sm.mem_issuedate,locate('/',sm.mem_issuedate)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and sm.share_val=("+num_shares+"*st.shareval) and cm.cid=ca.cid and cm.cid=sm.cid and ca.addr_type=sm.addr_type and sm.mem_cat=st.mem_cat order by sm.ac_no");
					}
					else if(str.startsWith("sm.num_shares <>"))
					{
						str1=new StringTokenizer(str,"<>");
						str1.nextToken();
						num_shares=str1.nextToken();
						System.out.println("Num.of shares:"+num_shares);
						rs=stmt.executeQuery("select distinct sm.ac_no,sm.ac_type,sm.temp_no,sm.mem_cat,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.share_val,st.shareval,sm.sh_ind,sm.br_code,sm.mem_issuedate,sm.cid,cm.scst,cm.sex from CustomerMaster cm ,CustomerAddr  ca,ShareMaster sm,ShareType st where sm.mem_cl_date is not null and concat(right(sm.mem_issuedate,4),'-',mid(sm.mem_issuedate,locate('/',sm.mem_issuedate)+1, (locate('/',sm.mem_issuedate,4)-locate('/',sm.mem_issuedate)-1)),'-',left(sm.mem_issuedate,locate('/',sm.mem_issuedate)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and sm.share_val<>("+num_shares+"*st.shareval) and cm.cid=ca.cid and cm.cid=sm.cid and ca.addr_type=sm.addr_type and sm.mem_cat=st.mem_cat order by sm.ac_no");
					}
				}
				else
					rs=stmt.executeQuery("select distinct sm.ac_no,sm.ac_type,sm.temp_no,sm.mem_cat,concat_ws(' ',cm.fname,cm.mname,cm.lname)as name,ca.address,ca.city,ca.pin,sm.share_val,st.shareval,sm.sh_ind,sm.br_code,sm.mem_issuedate,sm.cid,cm.scst,cm.sex from CustomerMaster cm ,CustomerAddr  ca,ShareMaster sm,ShareType st where sm.mem_cl_date is not null and concat(right(sm.mem_cl_date,4),'-',mid(sm.mem_cl_date,locate('/',sm.mem_cl_date)+1, (locate('/',sm.mem_cl_date,4)-locate('/',sm.mem_cl_date)-1)),'-',left(sm.mem_cl_date,locate('/',sm.mem_cl_date)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and "+str+" and cm.cid=ca.cid and cm.cid=sm.cid and ca.addr_type=sm.addr_type and sm.mem_cat=st.mem_cat order by sm.ac_no");
			}
			//rs=stmt.executeQuery("select distinct sm.ac_no,sm.temp_no,cm.fname,cm.mname,cm.lname,ca.address,ca.city,ca.pin,sm.share_val,sm.sh_ind,sm.br_code,sm.num_shares,sm.mem_issuedate,sm.cid,cm.scst,cm.sex from CustomerMaster cm ,CustomerAddr  ca,ShareMaster sm where sm.mem_cl_date is not null and concat(right(sm.mem_cl_date,4),'-',mid(sm.mem_cl_date,locate('/',sm.mem_cl_date)+1, (locate('/',sm.mem_cl_date,4)-locate('/',sm.mem_cl_date)-1)),'-',left(sm.mem_cl_date,locate('/',sm.mem_cl_date)-1)) BETWEEN '"+Validations.convertYMD(date1)+"' and '"+Validations.convertYMD(date2)+"' and cm.cid=ca.cid and cm.cid=sm.cid and ca.addr_type=sm.addr_type");
			
			rs.last();
			arr =new ShareReportObject[rs.getRow()];
			int i=0,memcat=0;
			double shvalue=0;
			rs.beforeFirst();
			while(rs.next())
			{
				arr[i]=new ShareReportObject();
				arr[i].setShareStatus(rs.getString("sm.sh_ind"));
				if(arr[i].getShareStatus().equals("P"))
					arr[i].setShareNumber(rs.getInt("sm.ac_no"));
				else
					arr[i].setShareNumber(rs.getInt("sm.temp_no"));
				arr[i].setShareAccType(rs.getString("sm.ac_type"));
				//arr[i].setName(rs.getString("cm.fname")+" "+rs.getString("cm.mname")+" "+rs.getString("cm.lname"));
				arr[i].setName(rs.getString("name"));
				arr[i].setAddress(rs.getString("ca.address"));
				arr[i].setCity(rs.getString("ca.city"));
				arr[i].setPin(rs.getInt("ca.pin"));
				
				arr[i].setCustomerId(rs.getInt("sm.cid"));
				shvalue=rs.getDouble("sm.share_val");
				arr[i].setShareVal(shvalue);
				arr[i].setPerShrValue(rs.getDouble("st.shareval"));
				arr[i].setBranchCode(rs.getInt("sm.br_code"));
				//Added by Karthi-->01/12/2005
				arr[i].setNumberofShares((int)(arr[i].getShareVal()/arr[i].getPerShrValue()));
				arr[i].setIssueDate(rs.getString("sm.mem_issuedate"));
				arr[i].setSex(rs.getString("cm.sex"));
				arr[i].setScSt(rs.getString("cm.scst"));
				i++;
			}
		}catch(Exception e){e.printStackTrace();}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}		
		return arr;
	}
	
	public double getMinBalance(String module_code)
	{
		Connection conn=null;
		double min_bal=0;
		try
		{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			System.out.println("module_code"+module_code);
			ResultSet rs=stmt.executeQuery("select min_bal from Modules where modulecode='"+module_code+"'  ");
			if(rs.next())
				min_bal=rs.getDouble("min_bal");
			
			System.out.println("min_bal"+min_bal);
		}catch(Exception e){e.printStackTrace();}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}	
		return min_bal;
	}
	
	public ShareMasterObject[] getLoanDetails(int shno,String shtype)
	{
		ShareMasterObject shobj []=null;
		Connection conn=null;
		
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();	
			ResultSet rs=stmt.executeQuery("select lnk_shares,moduleabbr,LoanMaster.ac_no,disb_left,pr_bal  from Modules,ShareMaster,LoanMaster,LoanTransaction where  LoanMaster.sh_no="+shno+" and LoanMaster.sh_type="+shtype+" and LoanTransaction.ac_type=LoanMaster.ac_type  and LoanTransaction.ac_no=LoanMaster.ac_no and lst_tr_seq=trn_seq and ln_availed='T' and Modules.modulecode=LoanMaster.ac_type and ShareMaster.ac_no="+shno+" and ShareMaster.ac_type='"+shtype+"'");
			rs.last();
			shobj=new ShareMasterObject[rs.getRow()];
			System.out.println("THe length of loan details is "+rs.getRow());
			
			rs.beforeFirst();
			int i=0;
			while(rs.next())
			{
				 shobj[i]=new ShareMasterObject();
				shobj[i].setLnk_shares(rs.getString("lnk_shares"));
				shobj[i].setModuleabbr(rs.getString("moduleabbr"));
				shobj[i].setAc_no(rs.getString("ac_no"));
				shobj[i].setDisb_left(rs.getString("disb_left"));
				shobj[i].setPr_bal(rs.getString("pr_bal"));
				
				i++;
			}				
		}catch(Exception e){e.printStackTrace();}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}		
		
		return (shobj);	
	}
	
	/*public int storeShare(ShareMasterObject ms,int type) throws RemoteException
	 {
	 
	 int updated=0;
	 Connection conn=null;
	 
	 try{
	 conn=getConnection();
	 Statement stmt=conn.createStatement();
	 Statement stmtdc=conn.createStatement();
	 PreparedStatement pstmt=null;
	 //Type 0 means new share allotment
	  //type 1 means additonal allotment
	   if(type==0)
	   {
	   if(ms.getShareNumber()==0)
	   {
	   System.out.println(".......1...................");
	   ResultSet rs=null;
	   if(ms.getShareStatus().equals("T"))
	   {
	   System.out.println(".......1A...................");
	   rs=stmt.executeQuery("select lst_tsh_no from GenParam");					
	   }
	   else
	   rs=stmt.executeQuery("select lst_acc_no from Modules where modulecode="+ms.getShareAccType());
	   rs.next();			
	   ms.setShareNumber(rs.getInt(1)+1);
	   rs.close();
	   
	   if(ms.getShareStatus().equals("T"))
	   {
	   System.out.println(".......1A...................");
	   stmt.executeUpdate("update GenParam set lst_tsh_no=lst_tsh_no+1");
	   }
	   else
	   {
	   stmt.executeUpdate("update Modules  set lst_acc_no=lst_acc_no+1 where modulecode="+ms.getShareAccType());
	   }
	   System.out.println(".......2...................");
	   }
	   else
	   {
	   System.out.println("mss.storesharenmumber(update)"+ms.getShareNumber());
	   System.out.println("mss.storeshareacctype(update)"+ms.getShareAccType());
	   System.out.println("mss.storeshareshind(update)"+ms.getShareStatus());
	   
	   if(ms.getShareStatus().equals("P"))
	   stmt.executeUpdate("delete from ShareMaster where ac_no="+ms.getShareNumber()+"  and ac_type='"+ms.getShareAccType()+"'");
	   else
	   stmt.executeUpdate("delete from ShareMaster where temp_no="+ms.getShareNumber()+"  and ac_type='"+ms.getShareAccType()+"'");
	   
	   stmt.executeUpdate("delete from ShareTransaction where ac_no="+ms.getShareNumber()+"  and ac_type='"+ms.getShareAccType()+"' and susp_ind='"+ms.getShareStatus()+"'");
	   
	   }
	   
	   
	   if(ms.getShareStatus().equals("T"))
	   {
	   System.out.println(".......inside store..................");
	   //int num_shares=rs.getInt("st.share_bal")/rs.getInt("sm.share_val");
	    pstmt=conn.prepareStatement("insert into ShareMaster(ac_type,temp_no,sh_ind,br_code,cid,mem_cat,mem_issuedate,share_val,intr_ac_type,intr_ac_no,nom_no, pay_mode,pay_ac_type,pay_ac_no,de_user,de_tml,de_date,rel_director,div_uptodt,addr_type,ln_availed) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'F')");
	    System.out.println(".......after store..................."+ms.getBranchName());
	    }
	    else
	    {
	    System.out.println("Executing insert query...............");
	    pstmt=conn.prepareStatement("insert into ShareMaster(ac_type,ac_no,sh_ind,br_code,cid,mem_cat,mem_issuedate,share_val,intr_ac_type,intr_ac_no,nom_no, pay_mode,pay_ac_type,pay_ac_no,de_user,de_tml,de_date,rel_director,div_uptodt,addr_type,ln_availed) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'F')");
	    }
	    System.out.println("inserting into sharemaster");
	    System.out.println("...actype"+ms.getShareAccType());
	    System.out.println("....acno"+ms.getShareNumber());
	    System.out.println("....shareval"+ms.getShareVal());
	    pstmt.setString(1,ms.getShareAccType());			
	    pstmt.setInt(2,ms.getShareNumber());
	    pstmt.setString(3,ms.getShareStatus());
	    pstmt.setInt(4,ms.getBranchCode());
	    pstmt.setInt(5,ms.getCustomerId());
	    pstmt.setInt(6,ms.getShareType());
	    ms.setIssueDate(getSysDate());
	    pstmt.setString(7,ms.getIssueDate());
	    //pstmt.setDouble(8,ms.getNumberofShares());	
	     pstmt.setDouble(8,ms.getShareVal());	
	     
	     pstmt.setString(9,ms.getIntroducerAcctype());
	     pstmt.setInt(10,ms.getIntroducerAccno());
	     NomineeObject nom[]=ms.getNomineeDetails();
	     System.out.println("Executing insert query...............");
	     
	     
	     Context ctx=getInitialContext();
	     Object obj=ctx.lookup("COMMON");
	     commonServer.CommonHome home=(commonServer.CommonHome)obj;
	     CommonRemote cremote=home.create();
	     
	     System.out.println("Executing insert query...............");
	     int id=cremote.storeNominee(nom,ms.getShareAccType(),ms.getShareNumber());
	     pstmt.setInt(11,id);
	     pstmt.setString(12,ms.getPayMode());
	     pstmt.setString(13,ms.getPaymentAcctype());
	     pstmt.setInt(14,ms.getPaymentAccno());
	     
	     pstmt.setString(15,ms.uv.getUserId());
	     pstmt.setString(16,ms.uv.getUserTml());
	     pstmt.setString(17,ms.uv.getUserDate());
	     pstmt.setString(18,ms.getRelationtoDirector());
	     pstmt.setString(19,Validations.addDays(getSysDate(),-1));
	     pstmt.setInt(20,ms.getMailingAddress());
	     System.out.println("Before Executing Sharemaster...........");
	     updated=pstmt.executeUpdate();					
	     System.out.println("after Executing Share Transaction...........");
	     }
	     if(type==1 ||type==2|| updated==1)
	     {
	     Statement stmt1=conn.createStatement();
	     PreparedStatement ps1=null;
	     //type 1,0 means additional share
	      //type 2 means withdrawal of share 
	       
	       Statement stmt2=conn.createStatement();
	       Statement stmt3=conn.createStatement();
	       
	       int trnno=0;
	       int acno=0,trnno1=0;;
	       String actype="",veuser="";
	       if(ms.getTranNumber()==0)
	       {	
	       
	       ResultSet rs=stmt1.executeQuery("select sh_trn_no from GenParam");
	       
	       if(rs.next())
	       trnno=rs.getInt("sh_trn_no");
	       
	       trnno++;
	       
	       stmt.executeUpdate("update GenParam set sh_trn_no="+trnno);
	       System.out.println("final trn no isss,,,,,"+trnno);
	       }
	       else
	       {	
	       
	       stmt3.executeUpdate("delete from ShareTransaction where ac_no="+ms.getShareNumber()+"  and ac_type='"+ms.getShareAccType()+"' and trn_no='"+ms.getTranNumber()+"' and ve_user is null");
	       System.out.println("final trn no isss the old one..."+ms.getTranNumber());
	       trnno=ms.getTranNumber();
	       ResultSet rs1=stmt2.executeQuery("select ac_type,ac_no,trn_no,ve_user from ShareTransaction where trn_no="+ms.getTranNumber()+" and ac_no="+ms.getShareNumber()+"  and ac_type='"+ms.getShareAccType()+"' ");
	       while(rs1.next())
	       {	acno=rs1.getInt("ac_no");
	       actype=rs1.getString("ac_type");
	       trnno1=rs1.getInt("trn_no");
	       veuser=rs1.getString("ve_user");
	       System.out.println("view for update acno"+acno+" type"+actype+" trn no"+trnno1+" ve user"+veuser);
	       }
	       try
	       {
	       if(veuser==null)
	       {
	       System.out.println("ve user is nulllllllllll");
	       if(trnno==trnno1)			
	       stmt3.executeUpdate("delete from ShareTransaction where ac_no="+acno+"  and ac_type='"+actype+"' and trn_no='"+trnno1+"' and ve_user is null");
	       else
	       trnno++;
	       }
	       else
	       trnno++;
	       }
	       catch(Exception e){e.printStackTrace();}
	       
	       rs1.close();
	       }
	       
	       
	       
	       
	       if(type==1 || type==0)
	       ps1=conn.prepareStatement("insert into ShareTransaction values(?,"+trnno+",?,date_format(sysdate(),'%d/%m/%Y'),'A',?,?,?,?,?,'C',?,?,?,?,?,null,null,'F','F',?,?,date_format(sysdate(),'%d/%m/%Y %H:%i:%s'),null,null,null)");
	       else
	       ps1=conn.prepareStatement("insert into ShareTransaction values(?,"+trnno+",?,date_format(sysdate(),'%d/%m/%Y'),'W',?,?,?,?,?,'D',?,?,?,?,?,null,null,null,'F',?,?,date_format(sysdate(),'%d/%m/%Y %H:%i:%s'),null,null,null)");
	       
	       System.out.println("inserting into sharetransaction");
	       System.out.println("...actype"+ms.getShareAccType());
	       System.out.println("....acno"+ms.getShareNumber());
	       System.out.println("....number of shares"+ms.getNumberofShares());
	       ps1.setString(1,ms.getShareAccType());
	       ps1.setInt(2,ms.getShareNumber());
	       ps1.setDouble(3,ms.getAmount());
	       
	       if(ms.getRecievedMode().equals("C"))
	       {
	       ps1.setInt(4,ms.getRecievedAccno());
	       ps1.setString(5,null);
	       }
	       else
	       {
	       ps1.setString(5,(ms.getRecievedAcctype()+"  "+ms.getRecievedAccno()));
	       ps1.setInt(4,0);
	       }
	       
	       
	       ps1.setString(6,ms.getRecievedMode());
	       
	       System.out.println("received......mode id"+ms.getRecievedMode());
	       if(ms.getRecievedMode().equals("C"))
	       {
	       System.out.println("updateingg daycash......");
	       stmtdc.executeUpdate("update DayCash set attached='T',ac_no="+ms.getShareNumber()+" where scroll_no="+ms.getRecievedAccno()+" ");
	       }
	       
	       else if(ms.getRecievedMode().equals("T"))
	       {
	       AccountTransObject at=new AccountTransObject(); 
	       
	       System.out.println("object at created||||||||||||"+ms.getRecievedMode());
	       at.setAccType(ms.getRecievedAcctype());
	       at.setAccNo(ms.getRecievedAccno());
	       
	       at.setTransType("P");
	       at.setTransAmount(ms.getAmount());
	       at.setTransMode(ms.getRecievedMode());
	       at.setTransSource(ms.uv.getUserTml());
	       at.setTransDate(getSysDate());
	       
	       at.setCdInd("C");
	       
	       at.setChqDDNo(0);
	       at.setChqDDDate("");
	       at.setTransNarr(ms.getShareAccType()+" "+ms.getShareNumber());
	       at.setRef_No(0);
	       at.setPayeeName("");
	       at.setCloseBal(ms.getAmount());
	       at.setLedgerPage(0);
	       at.uv.setUserTml(ms.uv.getUserTml());
	       at.uv.setUserId(ms.uv.getUserId());
	       at.uv.setVerTml(ms.uv.getUserTml());
	       at.uv.setVerId(ms.uv.getUserId());
	       at.setGLRefCode(Integer.parseInt(ms.getRecievedAcctype()));
	       
	       System.out.println("commonremote b4");
	       CommonHome commonhome=null;
	       CommonRemote commonremote=null;
	       commonremote=commonhome.create();
	       
	       int ret=common_local.storeAccountTransaction(at);
	       
	       System.out.println("Transfer updater acctran table"+ret);
	       
	       
	       
	       }
	       else if(ms.getRecievedMode().equals("B"))
	       {
	       
	       }
	       else if(ms.getRecievedMode().equals("G"))
	       {
	       
	       }
	       
	       System.out.println("received......mode idafffter"+ms.getRecievedMode());
	       ps1.setString(7,ms.uv.getUserTml());
	       ps1.setString(8,"D");
	       ps1.setString(9,ms.getShareStatus());
	       ps1.setDouble(10,ms.getNumberofShares());
	       ps1.setInt(11,0);
	       ps1.setInt(12,0);
	       ps1.setString(13,ms.uv.getUserId());
	       ps1.setString(14,ms.uv.getUserTml());
	       System.out.println("Before Executing Share Transaction...........");
	       ps1.executeUpdate();
	       System.out.println("after Executing Share Transaction...........");
	       
	       if(type==1 || type==2)
	       ms.setShareNumber(trnno);
	       //stmt.executeUpdate("update GenParam set sh_trn_no="+trnno);
	        
	        }
	        }catch(Exception e){e.printStackTrace();}
	        finally{
	        try{
	        conn.close();
	        }catch(Exception e){e.printStackTrace();}
	        }		
	        
	        return (ms.getShareNumber());
	        }*/
	public int[] getTempShareNos() //Swaran
	{
		Connection conn=null;
		int temp_no[]=null;
		int i=0;
		try{
			conn=getConnection();
			
			Statement stmt=conn.createStatement();
			ResultSet rs=null;
			rs=stmt.executeQuery("select distinct(temp_no) from ShareBranchData where made_permanent='F' ");
			rs.last();
			int row_count=rs.getRow();
			rs.beforeFirst();
			temp_no = new int[row_count];
			System.out.println("row count out"+row_count);
			while(rs.next())
			{
				//temp_no[i]=new int[i];
				//temp_no = new int[i];
				System.out.println("i  b4"+i);
				System.out.println("rs.getInt"+rs.getInt("temp_no"));
				
				temp_no[i]=rs.getInt("temp_no");
				
				System.out.println("temp_no from sm"+temp_no[i]);
				System.out.println("i  after"+i);
				i++;
			}
			
			rs.close();
		}
		catch(Exception e){e.printStackTrace();}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}		
		
		return (temp_no);
	}
	
	public int shareRegularization(ShareMasterObject[] sm) //Swaran
	{
		Connection conn=null;
		try
		{
			conn=getConnection();
			Statement stmt=conn.createStatement();	
			ResultSet rs=null;
			
			for(int i=0;i<sm.length;i++)
			{	
				System.out.println("temp_no.........."+sm[i].getTempShareNumber());
				System.out.println("perm_no.........."+sm[i].getShareNumber());
				rs=stmt.executeQuery("select temp_no from ShareBranchData where temp_no="+sm[i].getTempShareNumber()+" ");
				rs.last();
				
				if(rs.getRow()!=0)
				{	
					
					System.out.println("result set nottttttt empty length is "+rs.getRow());
					System.out.println("temp_no inside.........."+sm[i].getTempShareNumber());
					System.out.println("perm_no inside.........."+sm[i].getShareNumber());
					stmt.executeUpdate("update ShareMaster set ac_no="+sm[i].getShareNumber()+",sh_ind='P' where temp_no="+sm[i].getTempShareNumber()+" ");
					//stmt.executeUpdate("update ShareTransaction set ac_no="+sm[i].getShareNumber()+",susp_ind='P' where temp_no="+sm[i].getTempShareNumber()+" ");// suraj  commented this on 03/04/2008
					stmt.executeUpdate("update ShareTransaction set ac_no="+sm[i].getShareNumber()+",susp_ind='P' where ac_no="+sm[i].getTempShareNumber()+" ");
					stmt.executeUpdate("update ShareBranchData set ac_no="+sm[i].getShareNumber()+",made_permanent='T' where temp_no="+sm[i].getTempShareNumber()+" ");
					System.out.println("updated");
					return 1;
				}
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}	
		
		return 0;
	}
	public int saveTempNo(int ac_number) //Swaran
	{
		Connection conn=null;
		String check_loan=null;
		System.out.println("The ac no in share bean is-------->"+ac_number);
		try
		{
			
			conn=getConnection();
			Statement stmt=conn.createStatement();	
			ResultSet rs=null;
			
			
			//should check if share number is already added or  not........?
			rs=stmt.executeQuery("select temp_no from ShareBranchData where temp_no="+ac_number+" ");
			rs.last();
			System.out.println("After query.........................");
			if(rs.getRow()!=0)
			{	
				System.out.println("result set empty");
				stmt.executeUpdate("insert into ShareBranchData(temp_no,made_permanent) values ("+ac_number+",'F') ");
				System.out.println("inserted");
				return 1;
			}
			System.out.println("After return.......................");
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}	
		
		return 0;
	}
	public int getRelationCode(String name)
	{
		Connection conn=null;
		int rel_code=0;
		
		try
		{
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");   
			System.out.println("code========"+name);
			conn=getConnection();
			Statement stmt=conn.createStatement();	
			ResultSet rs=null;
			rs=stmt.executeQuery("select rel_code from DirectorRelation where relation_type='"+name+"' ");
			if(rs.next())
				rel_code=rs.getInt("rel_code");
			
			System.out.println("relation type"+rel_code);
			
			rs.close();
		}catch(Exception e){e.printStackTrace();}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}	
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");  
		
		return (rel_code);
	}
	public String getRelationDesc(int code)
	{
		Connection conn=null;
		String rel_type=null;
		int i=0;
		try
		{
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");   
			System.out.println("code========"+code);
			conn=getConnection();
			Statement stmt=conn.createStatement();	
			ResultSet rs=null;
			rs=stmt.executeQuery("select relation_type from DirectorRelation where rel_code="+code+" ");
			if(rs.next())
				rel_type=rs.getString("relation_type");
			
			System.out.println("relation type"+rel_type);
			
			rs.close();
		}catch(Exception e){e.printStackTrace();}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}	
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");  
		
		return (rel_type);
		//System.out.println("::::::::::::::::::::::;;----------");  
	}
	public int[] getDirectorRelation() //Swaran
	{
		Connection conn=null;
		int rel_code[]=null;
		int i=0;
		try
		{
			System.out.println("::::::::::::::::::::::;;");    
			conn=getConnection();
			Statement stmt=conn.createStatement();	
			ResultSet rs=null;
			rs=stmt.executeQuery("select rel_code from DirectorRelation");
			rs.last();
			int row_count=rs.getRow();
			rs.beforeFirst();
			System.out.println("rs.getrow........."+row_count);
			rel_code = new int[row_count];
			while(rs.next())
			{
				rel_code[i]=rs.getInt("rel_code");
				i++;
				
			}
			rs.close();
		}catch(Exception e){e.printStackTrace();}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}	
		System.out.println("::::::::::::::::::::::;;");  
		
		return (rel_code);
		//System.out.println("::::::::::::::::::::::;;----------");  
		
	}
	public String checkLoans(String acctype,int acno) //Swaran
	{
		Connection conn=null;
		String check_loan=null;
		try
		{
			
			conn=getConnection();
			Statement stmt=conn.createStatement();	
			ResultSet rs=null;
			rs=stmt.executeQuery("select ln_availed from ShareMaster where ac_no="+acno+" and ac_type='"+acctype+"'");
			rs.next();
			check_loan=rs.getString(1);
			
			System.out.println("acctype"+acctype);
			System.out.println("accno"+acno);
			System.out.println("loan availes"+check_loan);
			
			
		}catch(Exception e){e.printStackTrace();}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}		
		/*if(check_loan.equals("F"))
		 return (check_loan);
		 else if(check_loan.equals("T"))*/
		return (check_loan);
	}
	public int closeShare(ShareMasterObject cms)
	{
		Connection conn=null;
		try
		{
			
			conn=getConnection();
			Statement stmt=conn.createStatement();	
			stmt.executeUpdate("update ShareMaster set mem_cl_date=date_format(sysdate(),'%d/%m/%Y') where ac_no="+cms.getShareNumber()+" and ac_type='"+cms.getShareAccType()+"' and sh_ind='P'");
			System.out.println("verification...share closure&&sh_ind=p"+cms.getShareNumber());
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}		
		
		return (cms.getShareNumber());
	}
	//public int checkShareTransaction(String actype,int acno) throws RemoteException
	public String[] checkShareTransaction(String actype,int acno)
	{
		int trn_no=0;
		String[] s=new String[2];
		Connection conn=null;
		try
		{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			Statement stmt_temp=conn.createStatement();
			ResultSet rs_temp=null;
			String str_sh_ind=null;
			/*if((String.valueOf(acno)!="500%"))
			 {
			 System.out.println("True");
			 //rsa=stmta.executeQuery("select sh_ind,ac_no,temp_no from ShareMaster where ac_type='"+actype+"' and ac_no="+shareno+" ");
			  rs_temp=stmt_temp.executeQuery("select sh_ind,ac_no,temp_no from ShareMaster where ac_type='"+actype+"' and temp_no="+acno+" ");
			  }
			  else
			  //rsa=stmta.executeQuery("select sh_ind,ac_no,temp_no from ShareMaster where ac_type='"+actype+"' and temp_no="+shareno+" ");
			   rs_temp=stmt_temp.executeQuery("select sh_ind,ac_no,temp_no from ShareMaster where ac_type='"+actype+"' and ac_no="+acno+" ");
			   
			   if(rs_temp.next())
			   str_sh_ind=rs_temp.getString("sh_ind");
			   
			   else
			   str_sh_ind=null;
			   
			   System.out.println("print share indicator (result set)"+str_sh_ind);	
			   //int acno1=0;
			    if(str_sh_ind==null||str_sh_ind.equals("T"))
			    acno=rs_temp.getInt("temp_no");
			    else
			    acno=rs_temp.getInt("ac_no");
			    
			    //System.out.println("The ACC.NO is :"+acno1);*/
			ResultSet rs_max=stmt.executeQuery("select max(trn_seq) as trn_seq from ShareTransaction where ac_type='"+actype+"' and ac_no="+acno+" ");
			rs_max.next();
			int max_trn_seq=rs_max.getInt("trn_seq");
			System.out.println("max_trn_seq number"+max_trn_seq);  
			rs_max.close();
			
			ResultSet rs_ver=stmt.executeQuery("select susp_ind,trn_no,ve_user,ve_tml from ShareTransaction where trn_seq="+max_trn_seq+" and ac_type='"+actype+"' and ac_no="+acno+" ");
			rs_ver.next();
			String ve_user=rs_ver.getString("ve_user");
			String ve_tml=rs_ver.getString("ve_tml");
			trn_no=rs_ver.getInt("trn_no");
			
			System.out.println("ve_user"+ve_user);  
			System.out.println("ve_tml"+ve_tml);  
			System.out.println("trn_no"+trn_no);  
			//Commented by Karthi-->19/06/2006
			/* if(ve_user==null ||ve_tml==null)
			 {
			 System.out.println("breaaaaaaaaaaaaak");  
			 trn_no=0;
			 }*/
			
			if(ve_user==null ||ve_tml==null)
			{
				System.out.println("breaaaaaaaaaaaaak");  
				s[0]="False";
			}
			else
				s[0]="True";
			//s[0]=String.valueOf(trn_no);
			s[1]=rs_ver.getString("susp_ind");
			System.out.println("continueeeeeeeeee");  
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}		
		System.out.println("trn_no"+trn_no);  
		
		//return (trn_no);
		return s;
	}
	
	public int storeShare(ShareMasterObject ms,int type,int old_scroll,int new_scroll) 
	{
		System.out.println("Am i Building????????");
		double share_balance=0;
		int updated=0;
		Connection conn=null;
		int lst_trn_seq=0,trnno=0;
		double lst_share_bal=0;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			Statement stmtdc=conn.createStatement();
			Statement stmt_trn_seq=conn.createStatement();
			Statement stmt_share_bal=conn.createStatement();
			Statement stmt_name=conn.createStatement();
			Statement stmt_trnseq=conn.createStatement();
			PreparedStatement pstmt=null;
			//Type 0 means new share allotment
			//type 1 means additonal allotment
			//type 2 means withdrawal
			if(type==0)
			{
				System.out.println("*******************STORE SHARE");
				 System.out.println(ms.getShareVal());
				 System.out.println(ms.getNumberofShares());
				 System.out.println(ms.getSignatureDetails());
				System.out.println("*******************STORE SHARE");
				if(ms.getShareNumber()==0)
				{
					System.out.println(".......1...................");
					ResultSet rs=null;
					//if(ms.getShareStatus().equals("T")
					/**
					 * Depending upon the Share Type either Direct(Permanent) or Suspense(Temporary) you have to generate the account number. 
					 * 
					 * For Head Office shares(both per. oe temp.)we are generating same serious of number.
					 */
					if(ms.getShareStatus().equals("T")&&ms.getBranchCode()!=1)//Karthi
					{
						//For Temporary Shares
						System.out.println(".......1A...................");
						//rs=stmt.executeQuery("select lst_tsh_no from GenParam");
						rs=stmt.executeQuery("select lst_voucher_scroll_no from Modules where modulecode='"+ms.getShareAccType()+"'");//Temprory No.
					}
					else
						////For Permanent Shares
						rs=stmt.executeQuery("select lst_acc_no from Modules where modulecode='"+ms.getShareAccType()+"'");
					rs.next();			
					ms.setShareNumber(rs.getInt(1)+1);
					rs.close();
					
					if(ms.getShareStatus().equals("T")&& ms.getBranchCode()!=1)
					{
						System.out.println(".......1A...................");
						//stmt.executeUpdate("update GenParam set lst_tsh_no=lst_tsh_no+1");
						stmt.executeUpdate("update Modules set lst_voucher_scroll_no=lst_voucher_scroll_no+1 where modulecode='"+ms.getShareAccType()+"'");//
					}
					else
					{
						System.out.println("update Modules...........");
						stmt.executeUpdate("update Modules  set lst_acc_no=lst_acc_no+1 where modulecode='"+ms.getShareAccType()+"'");
					}
					System.out.println(".......2...................");
				}
				else
				{ 
					//For Updation
					ResultSet rs_trn_no=null;
					System.out.println("mss.storesharenmumber(update)"+ms.getShareNumber());
					System.out.println("mss.storeshareacctype(update)"+ms.getShareAccType());
					System.out.println("mss.storeshareshind(update)"+ms.getShareStatus());
					
					if(ms.getShareStatus().equals("P"))
						stmt.executeUpdate("delete from ShareMaster where ac_no="+ms.getShareNumber()+"  and ac_type='"+ms.getShareAccType()+"'");
					else
						stmt.executeUpdate("delete from ShareMaster where temp_no="+ms.getShareNumber()+"  and ac_type='"+ms.getShareAccType()+"'");
					
					//rs_trn_no=stmt.executeQuery("select trn_no from ShareTransaction where ac_no="+ms.getShareNumber()+"  and ac_type='"+ms.getShareAccType()+"' and susp_ind='"+ms.getShareStatus()+"'");
					rs_trn_no=stmt.executeQuery("select trn_no from ShareTransaction where ac_no="+ms.getShareNumber()+"  and ac_type='"+ms.getShareAccType()+"' ");
					rs_trn_no.next();
					trnno=rs_trn_no.getInt(1);
					ms.setTranNumber(trnno);
					System.out.println("********trn_no*****"+ms.getTranNumber());
					//stmt.executeUpdate("delete from ShareTransaction where ac_no="+ms.getShareNumber()+"  and ac_type='"+ms.getShareAccType()+"' and susp_ind='"+ms.getShareStatus()+"'");
					stmt.executeUpdate("delete from ShareTransaction where ac_no="+ms.getShareNumber()+"  and ac_type='"+ms.getShareAccType()+"' ");
				}
				if(ms.getShareStatus().equals("T"))
				{
					System.out.println(".......inside store..................");
					//int num_shares=rs.getInt("st.share_bal")/rs.getInt("sm.share_val");
					if(ms.getBranchCode()!=1)
						pstmt=conn.prepareStatement("insert into ShareMaster(ac_type,temp_no,sh_ind,br_code,cid,mem_cat,mem_issuedate,share_val,intr_ac_type,intr_ac_no,nom_no, pay_mode,pay_ac_type,pay_ac_no,de_user,de_tml,de_date,rel_code,lst_trn_seq,div_uptodt,addr_type,ln_availed,rel_director) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'"+ms.uv.getUserDate()+"',?,0,?,?,'F',?)");
					else
						pstmt=conn.prepareStatement("insert into ShareMaster(ac_type,ac_no,sh_ind,br_code,cid,mem_cat,mem_issuedate,share_val,intr_ac_type,intr_ac_no,nom_no, pay_mode,pay_ac_type,pay_ac_no,de_user,de_tml,de_date,rel_code,lst_trn_seq,div_uptodt,addr_type,ln_availed,rel_director) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'"+ms.uv.getUserDate()+"',?,0,?,?,'F',?)");
					System.out.println(".......after store..................."+ms.getBranchName());
				}
				else
				{
					System.out.println("Executing insert query...............");
					pstmt=conn.prepareStatement("insert into ShareMaster(ac_type,ac_no,sh_ind,br_code,cid,mem_cat,mem_issuedate,share_val,intr_ac_type,intr_ac_no,nom_no, pay_mode,pay_ac_type,pay_ac_no,de_user,de_tml,de_date,rel_code,lst_trn_seq,div_uptodt,addr_type,ln_availed,rel_director) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'"+ms.uv.getUserDate()+"',?,0,?,?,'F',?)");
				}
				System.out.println("inserting into sharemaster");
				System.out.println("...actype"+ms.getShareAccType());
				System.out.println("....acno"+ms.getShareNumber());
				System.out.println("....shareval"+ms.getShareVal());
				System.out.println(".....appln date"+ms.getIssueDate());
				System.out.println("...... share category"+ms.getMemberCategory());
				/*if(ms.getShareStatus().equals("T"))
				 {    
				 System.out.println("...actype........TTTTT"+ms.getShareAccType());
				 System.out.println("....acno.........TTTTT"+ms.getShareNumber());
				 System.out.println("....tempacno.........TTTTT"+ms.getTempShareNumber());
				 ms.setTempShareNumber(ms.getShareNumber());
				 ms.setShareNumber(0);
				 System.out.println("...actype........TTTTTafter"+ms.getShareAccType());
				 System.out.println("....acno.........TTTTTafter"+ms.getShareNumber());
				 System.out.println("....tempacno.........TTTTTafter"+ms.getTempShareNumber());
				 }    */
				System.out.println("...actype........PPPP"+ms.getShareAccType());
				System.out.println("....acno.........PPPPP"+ms.getShareNumber());
				System.out.println("....tempacno.........PPPPPP"+ms.getTempShareNumber());
				pstmt.setString(1,ms.getShareAccType());			
				pstmt.setInt(2,ms.getShareNumber());
				pstmt.setString(3,ms.getShareStatus());
				pstmt.setInt(4,ms.getBranchCode());
				pstmt.setInt(5,ms.getCustomerId());
				pstmt.setInt(6,ms.getShareType());
				//ms.setIssueDate(getSysDate());
				pstmt.setString(7,ms.getIssueDate());
				//pstmt.setDouble(8,ms.getNumberofShares());	
				pstmt.setDouble(8,ms.getShareVal());	
				pstmt.setString(9,ms.getIntroducerAcctype());
				pstmt.setInt(10,ms.getIntroducerAccno());
				NomineeObject nom[]=ms.getNomineeDetails();
				
				System.out.println("Executing insert query...............");
				/**
				 * If the customer have Nominee then we have to store nominee details in NomineeMaster table.
				 * for that we use the method that is in common bean(storeNominee()).
				 * 
				 * signature instruction details of the customer also store in Signature Instruction table.
				 * for this we use the method that is in common bean(storeSignatureDetails()).
				 * 
				 * 
				 */
				if(nom!=null)
				{
					Context ctx=getInitialContext();
					Object obj=ctx.lookup("COMMONWEB");
					commonServer.CommonHome home=(commonServer.CommonHome)obj;
					CommonRemote cremote=home.create();
					System.out.println("Executing insert query...............");
					int id=cremote.storeNominee(nom,ms.getShareAccType(),ms.getShareNumber(),ms.getIssueDate());
					pstmt.setInt(11,id);
				}
				else if(nom==null)
					pstmt.setInt(11,0);
				
				SignatureInstructionObject sig[]=ms.getSignatureDetails();
				
				if(sig!=null)
				{
					for(int i=0;i<sig.length;i++){
						System.out.println("The sign details in the bean is===="+sig[i].toString());
					}
					
			
					
					
					
				/*	commonServer.CommonHome home=(commonServer.CommonHome)HomeFactory.getFactory().lookUpHome("COMMONWEB");
					common_remote=home.create();	
					common_local.storeSignatureDetails(ms.getSignatureDetails(),ms.getShareNumber());
				*/	
				    	
					storeSignatureDetails(ms.getSignatureDetails(), ms.getShareNumber());
				}
				
				pstmt.setString(12,ms.getPayMode());
				pstmt.setString(13,ms.getPaymentAcctype());
				pstmt.setInt(14,ms.getPaymentAccno());
				pstmt.setString(15,ms.uv.getUserId());
				pstmt.setString(16,ms.uv.getUserTml());
				//pstmt.setString(17,ms.uv.getUserDate());
				//pstmt.setString(17,ms.getRelationtoDirector());
				pstmt.setString(17,ms.getRelationtoDirector());
				pstmt.setString(18,Validations.addDays(getSysDate(),-1));
				pstmt.setInt(19,ms.getMailingAddress());
				pstmt.setString(20,ms.getRelationDesc());
				System.out.println("Before Executing Sharemaster...........");
				updated=pstmt.executeUpdate();					
				System.out.println("after Executing Share Master...........");
			}
			if(type==1 ||type==2|| updated==1)
			{
				Statement stmt1=conn.createStatement();
				PreparedStatement ps1=null;
				Statement stmt3=conn.createStatement();
				ResultSet rs_trn_seq=null;
				ResultSet rs_share_bal=null;
				System.out.println("**********type*********"+type);
				System.out.println("ms.getShareNumber"+ms.getShareNumber());
				System.out.println("ms.getTempShareNumber"+ms.getTempShareNumber());
				System.out.println("ms.getShareType"+ms.getShareAccType());
				System.out.println(" ms.getShareStatus()"+ms.getShareStatus());
				if(type==1 || type==2)
					stmt1.executeUpdate("insert into ShareMasterLog select * from ShareMaster where ac_no="+ms.getShareNumber()+" and ac_type='"+ms.getShareAccType()+"'");
				if(ms.getShareStatus().equals("P")||(ms.getShareStatus().equals("T")&& ms.getBranchCode()==1))
				{      
					rs_trn_seq=stmt_trn_seq.executeQuery("select lst_trn_seq from ShareMaster where ac_type='"+ms.getShareAccType()+"' and ac_no="+ms.getShareNumber()+" ");
					System.out.println("PPPPPPP");
				}
				else if(ms.getShareStatus().equals("T"))
				{    
					if((ms.getTempShareNumber()==0) && (ms.getShareNumber()!=0))
					{   
						System.out.println("ms.getShareNumber    ________"+ms.getShareNumber());
						System.out.println("ms.getTempShareNumber_________"+ms.getTempShareNumber());
						ms.setTempShareNumber(ms.getShareNumber());
						// ms.setShareNumber(0);
						System.out.println("ms.getShareNumber ----------"+ms.getShareNumber());
						System.out.println("ms.getTempShareNumber-----------"+ms.getTempShareNumber());
					}
					else
					{
						System.out.println("ms.getShareNumber    ________no change"+ms.getShareNumber());
						System.out.println("ms.getTempShareNumber_________no change"+ms.getTempShareNumber());
					}
					if(type==2)
						rs_trn_seq=stmt_trn_seq.executeQuery("select lst_trn_seq from ShareMaster where ac_type='"+ms.getShareAccType()+"' and temp_no="+ms.getTempShareNumber()+" ");
					else if(type==1)
						//rs_trn_seq=stmt_trn_seq.executeQuery("select lst_trn_seq from ShareMaster where ac_type='"+ms.getShareAccType()+"' and ac_no="+ms.getTempShareNumber()+" ");
						rs_trn_seq=stmt_trn_seq.executeQuery("select lst_trn_seq from ShareMaster where ac_type='"+ms.getShareAccType()+"' and ac_no="+ms.getShareNumber()+" ");
					else if(type==0 && ms.getBranchCode()!=1)
						rs_trn_seq=stmt_trn_seq.executeQuery("select lst_trn_seq from ShareMaster where ac_type='"+ms.getShareAccType()+"' and temp_no="+ms.getTempShareNumber()+" ");
					else if(type==0 && ms.getBranchCode()==1)
						rs_trn_seq=stmt_trn_seq.executeQuery("select lst_trn_seq from ShareMaster where ac_type='"+ms.getShareAccType()+"' and ac_no="+ms.getTempShareNumber()+" "); 
					System.out.println("TTTTTTTTTT");
				}
				rs_trn_seq.next();
				lst_trn_seq=rs_trn_seq.getInt("lst_trn_seq");
				System.out.println("rs_trn_seq.get Int"+rs_trn_seq.getInt("lst_trn_seq"));
				rs_trn_seq.close();
				System.out.println("lst_trn_seq b4"+lst_trn_seq);
				
				if(lst_trn_seq!=0)
				{
					System.out.println("if(lst_trn_seq!=0)");
					if(ms.getShareStatus().equals("P") ||(ms.getShareStatus().equals("T")&& ms.getBranchCode()==1))
						rs_share_bal=stmt_share_bal.executeQuery("select share_bal from ShareTransaction where trn_seq="+lst_trn_seq+" and ac_type='"+ms.getShareAccType()+"' and ac_no="+ms.getShareNumber()+" ");
					else if(ms.getShareStatus().equals("T"))
					{
						if(type==1)
							rs_share_bal=stmt_share_bal.executeQuery("select share_bal from ShareTransaction where trn_seq="+lst_trn_seq+" and ac_type='"+ms.getShareAccType()+"' and ac_no="+ms.getShareNumber()+" ");
						else
							rs_share_bal=stmt_share_bal.executeQuery("select share_bal from ShareTransaction where trn_seq="+lst_trn_seq+" and ac_type='"+ms.getShareAccType()+"' and ac_no="+ms.getTempShareNumber()+" ");
					}
					rs_share_bal.next();
					lst_share_bal=rs_share_bal.getDouble("share_bal");
					//added here ....
					/*if(type==2){
						if(lst_share_bal>0.00){
							
						}
					}*/
					
					System.out.println("lst_share_bal"+lst_share_bal);
					rs_share_bal.close();
				}
				
				/**
				 * For Every transaction we have to generate the transaction number(voucher number).
				 * Using this number only we will do updation and verification.(Addl.allotment & withdrawal) 
				 */
				
				if(ms.getTranNumber()==0)
				{	
					
					//ResultSet rs=stmt1.executeQuery("select sh_trn_no from GenParam");
					ResultSet rs=stmt1.executeQuery("select last_trf_scroll_no from Modules where modulecode='"+ms.getShareAccType()+"'");//
					if(rs.next())
						//trnno=rs.getInt("sh_trn_no");
						trnno=rs.getInt("last_trf_scroll_no");//
					
					trnno++;
					//stmt.executeUpdate("update GenParam set sh_trn_no="+trnno);
					stmt.executeUpdate("update Modules set last_trf_scroll_no="+trnno+" where modulecode='"+ms.getShareAccType()+"'");//
					System.out.println("final trn no isss,,,,,"+trnno);
					//trn_seq=lst_trn_seq;
					lst_trn_seq++;
					System.out.println("lst_trn_seq...SUBMIT"+lst_trn_seq);
					
				}
				else
				{	
					stmt3.executeUpdate("delete from ShareTransaction where ac_no="+ms.getShareNumber()+"  and ac_type='"+ms.getShareAccType()+"' and trn_no='"+ms.getTranNumber()+"' and ve_user is null");
					stmt3.executeUpdate("delete from DayCash where vch_no='"+ms.getTranNumber()+"'");
					stmt.executeUpdate("update ShareMaster set mem_cl_date=null where ac_no="+ms.getShareNumber()+" and ac_type='"+ms.getShareAccType()+"' and sh_ind='P'");
					System.out.println("final trn no isss the old one..."+ms.getTranNumber());
					trnno=ms.getTranNumber();
					/*ResultSet rs1=stmt2.executeQuery("select ac_type,ac_no,trn_no,ve_user from ShareTransaction where trn_no="+ms.getTranNumber()+" and ac_no="+ms.getShareNumber()+"  and ac_type='"+ms.getShareAccType()+"' ");
					 while(rs1.next())
					 {	acno=rs1.getInt("ac_no");
					 actype=rs1.getString("ac_type");
					 trnno1=rs1.getInt("trn_no");
					 veuser=rs1.getString("ve_user");
					 System.out.println("view for update acno"+acno+" type"+actype+" trn no"+trnno1+" ve user"+veuser);
					 }
					 try
					 {
					 if(veuser==null)
					 {
					 System.out.println("ve user is nulllllllllll");
					 if(trnno==trnno1)			
					 stmt3.executeUpdate("delete from ShareTransaction where ac_no="+acno+"  and ac_type='"+actype+"' and trn_no='"+trnno1+"' and ve_user is null");
					 else
					 trnno++;
					 }
					 else
					 trnno++;
					 }
					 catch(Exception e){e.printStackTrace();}
					 
					 rs1.close();*/
					lst_trn_seq++;
					System.out.println("lst_trn_seq ...UPDATE"+lst_trn_seq);
				}
				
				if(type==1 || type==0)
					//ps1=conn.prepareStatement("insert into ShareTransaction values(?,"+trnno+",?,?,date_format(sysdate(),'%d/%m/%Y'),'A',?,?,?,?,?,'C',?,?,?,?,?,null,null,'F','F',?,?,date_format(sysdate(),'%d/%m/%Y %H:%i:%s'),null,null,null)");
					ps1=conn.prepareStatement("insert into ShareTransaction values(?,"+trnno+",?,?,'"+ms.getIssueDate()+"','A',?,?,?,?,?,'C',?,?,?,?,?,"+ms.getNumberofShares()+",null,'F','F',?,?,'"+ms.uv.getUserDate()+"',null,null,null)");
				else
				{
					if(type==2 && ms.getRecievedMode().equalsIgnoreCase("Clg"))
						ps1=conn.prepareStatement("insert into ShareTransaction values(?,"+trnno+",?,?,'"+ms.getTransferDate()+"','W',?,?,?,?,?,'D',?,?,?,?,?,null,null,'T','F',?,?,?,'"+ms.uv.getVerId()+"','"+ms.uv.getVerDate()+"','"+ms.uv.getVerTml()+"')");
					else if(type==2 && !(ms.getRecievedMode().equalsIgnoreCase("Clg")))
						ps1=conn.prepareStatement("insert into ShareTransaction values(?,"+trnno+",?,?,'"+ms.getTransferDate()+"','W',?,?,?,?,?,'D',?,?,?,?,?,null,null,'F','F',?,?,'"+ms.uv.getUserDate()+"',null,null,null)");
				}
				
				System.out.println("ms.getGLCode()"+ms.getGLCode());
				System.out.println("ms.getShareStatus()"+ms.getShareStatus());
				System.out.println("ms.getShareTrnType()"+ms.getShareTrnType());
				//System.out.println("ms.getSuspGLCode();"+ms.getSuspGLCode());
				
				
				System.out.println("inserting into sharetransaction");
				System.out.println("...actype"+ms.getShareAccType());
				System.out.println("....acno"+ms.getShareNumber());
				System.out.println("....number of shares"+ms.getNumberofShares());
				ps1.setString(1,ms.getShareAccType());
				if(ms.getShareStatus().equalsIgnoreCase("P") ||(ms.getShareStatus().equalsIgnoreCase("T")&& ms.getBranchCode()==1) )
					ps1.setInt(2,ms.getShareNumber());
				else 
				{
					if(type==1)
						ps1.setInt(2,ms.getShareNumber());
					else
						ps1.setInt(2,ms.getTempShareNumber());
				}
				ps1.setInt(3,lst_trn_seq);
				System.out.println("The amount is"+ms.getAmount()); 
				ps1.setDouble(4,ms.getAmount());
				
				if(ms.getRecievedMode().equals("C"))
				{
					if(type==2)
					{
						//ps1.setInt(5,0);
						ps1.setInt(5,trnno);
						//ps1.setString(6,null);
						ps1.setString(6,("Csh Vch."+trnno));
					}
					else 
					{
						ps1.setInt(5,ms.getRecievedAccno());
						ps1.setString(6,("Csh Scrno."+ms.getRecievedAccno()));;
					}
					
				}
				
				else if(ms.getRecievedMode().equals("T"))
				{
					ps1.setString(6,(ms.getRecievedAcctype()+"  "+ms.getRecievedAccno()));
					ps1.setInt(5,0);
				}
				else if(ms.getRecievedMode().equals("PO"))
				{
					ps1.setInt(5,0);
					ps1.setString(6,"PayOrdr ."+ms.getTranNumber());
				}
				else if(ms.getRecievedMode().equals("B"))
				{
					ps1.setString(6,(ms.getRecievedAcctype()+"  "+ms.getRecievedAccno()));
					ps1.setInt(5,0);
				}
				else if(ms.getRecievedMode().equalsIgnoreCase("G"))
				{
					ps1.setInt(5,ms.getRecievedAccno());
					ps1.setString(6,("Ctrl No "+ms.getRecievedAccno()));;
				}
				ps1.setString(7,ms.getRecievedMode());
				
				System.out.println("received......mode id"+ms.getRecievedMode());
				System.out.println("received......acc no id"+ms.getRecievedAccno());
				
				/**
				 * Suppose you are select the payment mode as 'Cash'(for share allotment),then you have attach that particular scroll no. to this account holder.
				 */
				if(ms.getRecievedMode().equals("C"))
				{
					if(type==1 ||updated==1)
					{	
						System.out.println("updateingg daycash......");
						System.out.println("Inside paymode==C.....");
						System.out.println("Old Scroll no: "+old_scroll);
						System.out.println("ScrlNo2: "+new_scroll);
						//System.out.println("Dt: "+MainScreen.head.getSysDate());
						if(old_scroll!=0)
						{	
							if(old_scroll!=new_scroll) 
								stmtdc.executeUpdate("update DayCash set attached='F',ac_no=0 where trn_date='"+ms.getIssueDate()+"' and scroll_no="+old_scroll);
						}        
						stmtdc.executeUpdate("update DayCash set attached='T',ac_no="+ms.getShareNumber()+" where scroll_no="+ms.getRecievedAccno()+" ");
					}
					else if(type==2)//share withdrawal
					{	
						
						System.out.println("updateingg daycash.share withdrawallll.....");
						System.out.println("Inside paymode==C.....");
						System.out.println("Old Scroll no: "+old_scroll);
						System.out.println("ScrlNo2: "+new_scroll);
						System.out.println("tran number "+trnno);
						//stmtdc.executeUpdate("update DayCash set attached='T',ac_no="+ms.getShareNumber()+" where scroll_no="+ms.getRecievedAccno()+" ");
						//stmtdc.executeUpdate("insert into DayCash(ac_type,ac_no,trn_date,trn_type,csh_amt,cd_ind,name,vch_no,attached,de_user,de_tml,de_date) values('"+ms.getShareAccType()+"',"+ms.getShareNumber()+",'"+common_remote.getSysDate()+"','P',"+ms.getAmount()+",'C','"+ms.getName()+"',"+trnno+",'F','"+ms.uv.getUserId()+"','"+ms.uv.getUserTml()+"','"+ms.uv.getUserDate()+"') ");
						//Changed by Karthi
						int no=ms.getShareNumber();
						String actype=ms.getShareAccType();
						if(ms.getShareStatus().equals("P"))
							no=ms.getShareNumber();
						else
							no=ms.getTempShareNumber();
						
						//PreparedStatement pstmt1=conn.prepareStatement("insert into DayCash(scroll_no,trn_date,ac_type,ac_no,trn_type,csh_amt,cd_ind,name,vch_no,vch_type,trn_seq,attached,de_user,de_tml,de_date,share_category) values(0,'"+getSysDate()+"','"+ms.getShareAccType()+"',"+no+",'P',"+ms.getAmount()+",'C',?,"+trnno+",?,?,'F','"+ms.uv.getUserId()+"','"+ms.uv.getUserTml()+"','"+ms.uv.getUserDate()+"',?) ");//Karthi--28/06/2006
						PreparedStatement pstmt1=conn.prepareStatement("insert into DayCash(scroll_no,trn_date,ac_type,ac_no,trn_type,csh_amt,cd_ind,name,vch_no,vch_type,trn_seq,attached,de_user,de_tml,de_date,share_category) values(0,'"+ms.getTransferDate()+"','"+ms.getShareAccType()+"',"+no+",'W',"+ms.getAmount()+",'C',?,"+trnno+",?,?,'F','"+ms.uv.getUserId()+"','"+ms.uv.getUserTml()+"','"+ms.uv.getUserDate()+"',?) ");
						ResultSet rs_name=stmt_name.executeQuery("select concat_ws(' ',fname,mname,lname)as name from CustomerMaster where cid='"+ms.getCustomerId()+"'");
						rs_name.next();
						pstmt1.setString(1,rs_name.getString("name"));
						if(ms.getWithdrawalType()==0)
							pstmt1.setString(2,"W");
						else
							pstmt1.setString(2,"C");
						ResultSet rs_trnseq=stmt_trnseq.executeQuery("select  lst_trn_seq from ShareMaster where ac_type='"+actype+"' and ac_no="+no+" ");
						rs_trnseq.next();
						//pstmt1.setInt(3,rs_trnseq.getInt("lst_trn_seq"));
						pstmt1.setInt(3,rs_trnseq.getInt("lst_trn_seq")+1);//Karthi-->07/01/2006
						
						/*if(ms.getMemberCategory()==1)
						 pstmt1.setString(4,"Regular");
						 else if(ms.getMemberCategory()==2)
						 pstmt1.setString(4,"Associate");
						 else if(ms.getMemberCategory()==3)
						 pstmt1.setString(4,"Nominal");
						 else
						 pstmt1.setString(4,null);*/
						//Changed By Karthi-->07/01/2006
						if(ms.getMemberCategory()!=0)
							pstmt1.setInt(4,ms.getMemberCategory());
						else
							pstmt1.setInt(4,0);
						
						pstmt1.executeUpdate();
					}
				}
				/*
				 else if(ms.getRecievedMode().equals("T"))
				 {
				 AccountTransObject at=new AccountTransObject(); 
				 
				 System.out.println("object at created||||||||||||"+ms.getRecievedMode());
				 at.setAccType(ms.getRecievedAcctype());
				 at.setAccNo(ms.getRecievedAccno());
				 
				 at.setTransType("P");
				 at.setTransAmount(ms.getAmount());
				 at.setTransMode(ms.getRecievedMode());
				 at.setTransSource(ms.uv.getUserTml());
				 at.setTransDate(getSysDate());
				 
				 at.setCdInd("C");
				 
				 at.setChqDDNo(0);
				 at.setChqDDDate("");
				 at.setTransNarr(ms.getShareAccType()+" "+ms.getShareNumber());
				 at.setRef_No(0);
				 at.setPayeeName("");
				 at.setCloseBal(ms.getAmount());
				 at.setLedgerPage(0);
				 at.uv.setUserTml(ms.uv.getUserTml());
				 at.uv.setUserId(ms.uv.getUserId());
				 at.uv.setVerTml(ms.uv.getUserTml());
				 at.uv.setVerId(ms.uv.getUserId());
				 at.setGLRefCode(Integer.parseInt(ms.getRecievedAcctype()));
				 
				 System.out.println("commonremote b4");
				 CommonHome commonhome=null;
				 CommonRemote commonremote=null;
				 commonremote=commonhome.create();
				 
				 int ret=common_local.storeAccountTransaction(at);
				 
				 System.out.println("Transfer updater acctran table"+ret);
				 
				 
				 
				 }
				 else if(ms.getRecievedMode().equals("B"))
				 {
				 
				 }
				 else if(ms.getRecievedMode().equals("G"))
				 {
				 
				 }*/
				
				System.out.println("received......mode idafffter"+ms.getRecievedMode());
				if(type==2)
					ps1.setString(8,ms.uv.getUserTml());
				else
					ps1.setString(8,ms.uv.getUserTml());
				ps1.setString(9,"D");
				ps1.setString(10,ms.getShareStatus());
				//ps1.setDouble(10,ms.getNumberofShares());
				
				//ps1.setDouble(11,ms.getShareBalance());
				Statement stmt_mult_by=conn.createStatement();
				ResultSet rs_mult_by=null;
				if(type==1 ||type==2)
				{
					System.out.println("acctype"+ms.getShareAccType());
					System.out.println("mem cat"+ms.getMemberCategory());
					System.out.println("sh ind"+ms.getShareStatus());
					
					String prm_type=null;
					if(ms.getShareStatus().equals("P"))
						prm_type="D";//D-->Direct
					if(ms.getShareStatus().equals("T"))
						prm_type="S";//S-->Suspense
					
					System.out.println("prm_type"+prm_type);
					
					
					ResultSet rs_gl=stmt.executeQuery("select prm_gl_code from ShareParam where ac_type='"+ms.getShareAccType()+"' and mem_cat="+ms.getMemberCategory()+" and prm_ty='"+prm_type+"'");
					rs_gl.next();
					
					String gl_code=rs_gl.getString("prm_gl_code");
					System.out.println("gl_code"+gl_code);
					rs_gl.close();
					if(type==1) 
						rs_mult_by=stmt_mult_by.executeQuery("select mult_by from GLPost where gl_code="+gl_code+" and ac_type='"+ms.getShareAccType()+"' and trn_type='A' and cr_dr='C'");
					else if(type==2)
						rs_mult_by=stmt_mult_by.executeQuery("select mult_by from GLPost where gl_code="+gl_code+" and ac_type='"+ms.getShareAccType()+"' and trn_type='W' and cr_dr='D'");
					
					rs_mult_by.next();
					double mult_by=rs_mult_by.getInt("mult_by");
					
					System.out.println("mult_by"+mult_by);
					rs_mult_by.close();
					double current_share_balance=0;
					System.out.println("the  last balance is "+lst_share_bal);
					System.out.println("** Miscell. Amount : "+ms.getMiscAmount());
					System.out.println("** Share Amount : "+ms.getAmount());
					System.out.println("***ms.getNumberofShares() = "+ms.getNumberofShares());
					System.out.println("***ms.getShareVal() = "+ms.getShareVal());
					if(type==1){
						current_share_balance=(ms.getNumberofShares()*ms.getShareVal()*mult_by);//ms.getShareVal() 
						System.out.println("THE CURRENT BALANCE IS---------> "+current_share_balance);
					}
					else if(type==2)
						current_share_balance=(ms.getAmount()*mult_by);
					//double current_share_balance=ms.getAmount();
					//double share_balance=lst_share_bal+current_share_balance;
					//double share_balance=0;
					System.out.println("***current_share_balance = "+current_share_balance);
					if(type==1)
						share_balance=lst_share_bal+current_share_balance;
					else if(type==2)
						share_balance=lst_share_bal-current_share_balance;
					
					System.out.println("current_share_balance:"+current_share_balance);
					System.out.println("share_balance:"+share_balance);
					ps1.setDouble(11,share_balance);
				}
				if(type==0)
				{      
					//ps1.setDouble(11,(ms.getNumberofShares()*ms.getShareVal()));
					System.out.println("THIS IS DURING STORE SHARE");
					System.out.println("The Share Val"+ms.getShareVal());
					ps1.setDouble(11,ms.getShareVal());
					System.out.println("NO of shares is"+ms.getNumberofShares()+"SHArE value"+ms.getShareVal()+"The balance is "+ms.getNumberofShares()*ms.getShareVal());
					
					}
				
				ps1.setInt(12,0);
				ps1.setInt(13,0);
				ps1.setString(14,ms.uv.getUserId());
				ps1.setString(15,ms.uv.getUserTml());
				if(type==2 && ms.getRecievedMode().equalsIgnoreCase("Clg"))
					ps1.setString(16,ms.uv.getUserDate());
				System.out.println("Before Executing Share Transaction...........");
				ps1.executeUpdate();
				System.out.println("after Executing Share Transaction...........");
				
				if(updated==1)
				{
					System.out.println("UPDATE LST_TRN_NO");
					System.out.println("lst_trn_seq"+lst_trn_seq);
					
					System.out.println("UPDATE LST_TRN_NO ");
					if(ms.getShareStatus().equals("P")||(ms.getShareStatus().equals("T")&& ms.getBranchCode()==1))
					{    
						System.out.println("PPPPPPPPP");
						stmt_trn_seq.executeUpdate("update ShareMaster set lst_trn_seq="+lst_trn_seq+" where ac_type='"+ms.getShareAccType()+"' and ac_no="+ms.getShareNumber()+" ");
						System.out.println("shno"+ms.getShareNumber());
						System.out.println("lst_trn_seq"+lst_trn_seq);
						//code addedby Amzad
						/*if(ms.getShareStatus().equals("P")||(ms.getShareStatus().equals("T") && type==1)){
							lst_trn_seq=lst_trn_seq+1;
							stmt_trn_seq.executeUpdate("update ShareMaster set lst_trn_seq="+lst_trn_seq+",share_val="+share_balance+"where ac_type='"+ms.getShareAccType()+"' and ac_no="+ms.getShareNumber()+" ");
						}*/
						//code added By Amzad
						
					}
					else if(ms.getShareStatus().equals("T"))
					{	
						System.out.println("TTTTTT");
						if(type==1)
							stmt_trn_seq.executeUpdate("update ShareMaster set lst_trn_seq="+lst_trn_seq+" where ac_type='"+ms.getShareAccType()+"' and ac_no="+ms.getTempShareNumber()+" ");
						else
							stmt_trn_seq.executeUpdate("update ShareMaster set lst_trn_seq="+lst_trn_seq+" where ac_type='"+ms.getShareAccType()+"' and temp_no="+ms.getTempShareNumber()+" ");
						System.out.println("lst_trn_seq"+lst_trn_seq);
						System.out.println("tempshno"+ms.getTempShareNumber());
						
					}
					System.out.println("UPDATE LST_TRN_NO IN SHAREMASTER");
				}
				if(type==1 || type==2)
					ms.setShareNumber(trnno);
				//stmt.executeUpdate("update GenParam set sh_trn_no="+trnno);
				//stmt_trn_seq.executeUpdate("update ShareMaster set lst_trn_seq="+lst_trn_seq+" where ac_type='"+ms.getShareAccType()+"' and ac_no="+ms.getShareNumber()+" ");
			}
			System.out.println("ms.getShareNumber()"+ms.getShareNumber());
			
			
			
			
			return (ms.getShareNumber());
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
			return -1;
		}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}		
		/*System.out.println("ms.getShareNumber()"+ms.getShareNumber());
		 return (ms.getShareNumber());*/
	}
	/*public ShareMasterObject getShare(String actype,int shareno) throws RemoteException
	 {
	 //1--->Permanent share,0--->Temperory shares
	  ShareMasterObject ms=null;
	  Connection conn=null;
	  int type;
	  String str_sh_ind=null;
	  try{
	  conn=getConnection();
	  Statement stmt=conn.createStatement();
	  
	  ResultSet rs=null;
	  Statement stmt1=conn.createStatement();		
	  ResultSet rs1=null;			
	  ResultSet rsa=null;
	  Statement stmta=conn.createStatement();	
	  
	  rsa=stmta.executeQuery("select sh_ind from ShareMaster where ac_type='"+actype+"' and ac_no="+shareno+" ");
	  if(rsa.next())
	  str_sh_ind=rsa.getString("sh_ind");
	  else
	  str_sh_ind=null;
	  
	  System.out.println("print share indicator (result set)"+str_sh_ind);	
	  
	  if(str_sh_ind==null)
	  type=0;
	  else
	  type=1;
	  
	  if(str_sh_ind.equals("P"))	
	  {	type=1;}
	  else if(str_sh_ind.equals("T"))
	  {	type=0;}
	  
	  rsa.close();	
	  
	  System.out.println("Type b4 select ..............."+type);
	  if(type==1)
	  rs=stmt.executeQuery("select ShareMaster.*  from ShareMaster where ac_no="+shareno+" and  ac_type='"+actype+"' and  sh_ind='P'");
	  else if(type==0)
	  rs=stmt.executeQuery("select ShareMaster.* from ShareMaster  where temp_no="+shareno+" and  ac_type='"+actype+"' and  sh_ind='T' ");
	  
	  System.out.println("Type ..............."+type);
	  if(type==1)
	  rs1=stmt1.executeQuery("select * from ShareTransaction  where ac_no="+shareno +" and ac_type='"+actype+"' and susp_ind='P'");
	  else if(type==0)
	  rs1=stmt1.executeQuery("select * from ShareTransaction  where ac_no="+shareno +" and ac_type='"+actype+"' and susp_ind='T'");
	  
	  if(rs.next())
	  {
	  System.out.println("Type ..............."+type);
	  ms=new ShareMasterObject();
	  if(rs.getString("ve_user")==null) //verified
	  ms.setVerified("F");
	  else
	  ms.setVerified("T");
	  
	  ms.setShareAccType(rs.getString("ac_type"));			
	  ms.setShareNumber(rs.getInt("ac_no")); //not yet verified
	  ms.setTempShareNumber(rs.getInt("temp_no")); //not yet verified
	  ms.setBranchCode(rs.getInt("br_code"));
	  ms.setCustomerId(rs.getInt("cid"));
	  ms.setShareType(rs.getInt("mem_cat"));
	  
	  ms.setShareStatus(rs.getString("sh_ind"));
	  System.out.println("Share status"+ms.getShareStatus());
	  
	  Statement stmt2=conn.createStatement();
	  ResultSet rs3=stmt2.executeQuery("select shareval,sh_cap,sus_sh_cap from ShareType where mem_cat="+ms.getShareType() +" and ac_type="+ms.getShareAccType());
	  rs3.next();
	  ms.setShareVal(rs3.getFloat("shareval"));
	  ms.setGLCode(rs3.getString("sh_cap"));
	  ms.setSuspGLCode(rs3.getString("sus_sh_cap"));
	  rs3.close();
	  stmt2.close();
	  
	  ms.setShareBalance(rs.getFloat("share_val"));
	  
	  ms.setNumberofShares(ms.getShareBalance()/ms.getShareVal());
	  ms.setPayMode(rs.getString("pay_mode"));
	  ms.setPaymentAccno(rs.getInt("pay_ac_no"));
	  ms.setPaymentAcctype(rs.getString("pay_ac_type"));
	  
	  ms.setIntroducerAccno(rs.getInt("intr_ac_no"));
	  ms.setIntroducerAcctype(rs.getString("intr_ac_type"));			
	  
	  ms.setNomineeno(rs.getInt("nom_no"));
	  System.out.println("setnomineeeeno.........."+rs.getInt("nom_no"));
	  Context ctx=getInitialContext();
	  Object obj=ctx.lookup("COMMON");
	  commonServer.CommonHome home=(commonServer.CommonHome)obj;
	  CommonRemote cremote=home.create();
	  ms.setNomineeDetails(cremote.getNominee(ms.getNomineeno()));
	  //ms.setNomineeDetails(cremote.getNominee(ms.getNomineeno(),ms.getShareAccType()));
	   System.out.println("getnominee func changed");
	   ms.setLoanAvailed(rs.getString("ln_availed"));
	   ms.setRelationtoDirector(rs.getString("rel_director"));
	   ms.uv.setUserId(rs.getString("de_user"));
	   ms.uv.setUserTml(rs.getString("de_tml"));
	   ms.uv.setUserDate(rs.getString("de_date"));
	   ms.uv.setVerId(rs.getString("ve_user"));
	   ms.uv.setVerTml(rs.getString("ve_tml"));
	   ms.uv.setVerDate(rs.getString("ve_date"));
	   
	   
	   
	   if(rs1.next())
	   {
	   System.out.println("Type ..............."+type);
	   System.out.println("Goint inside");
	   ms.setAmount(rs1.getFloat("trn_amt"));
	   ms.setRecievedMode(rs1.getString("trn_mode"));
	   ms.setNumberofShares(rs1.getInt("share_bal"));
	   System.out.println("Received Mode"+ms.getRecievedMode());
	   if(ms.getRecievedMode().equals("C"))
	   {
	   System.out.println("Received Acc no"+rs1.getInt("ref_no"));
	   ms.setRecievedAccno(rs1.getInt("ref_no"));
	   }
	   else if (ms.getRecievedMode().equals("T"))
	   {
	   if(rs1.getString("trn_narr").length()>0)
	   {
	   StringTokenizer st=new StringTokenizer(rs1.getString("trn_narr"));
	   ms.setRecievedAcctype(st.nextToken());
	   ms.setRecievedAccno(Integer.parseInt(st.nextToken()));
	   
	   }
	   }
	   }
	   }
	   
	   }catch(Exception e){e.printStackTrace();}
	   finally{
	   try{
	   conn.close();
	   }catch(Exception e){e.printStackTrace();}
	   }		
	   
	   return ms;
	   }*/
	public ShareMasterObject getShare(String actype,int shareno) 
	{
		//1--->Permanent share,0--->Temperory shares
		ShareMasterObject ms=null;
		Connection conn=null;
		int type;
		double allotment_total=0;
		double withdrawal_total=0;
		double share_bal_total=0;
		double no_of_shares=0;
		float share_val=0;
		int row_count=0,br_code=0;
		String str_sh_ind=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			
			ResultSet rs=null;
			Statement stmt1=conn.createStatement();		
			ResultSet rs1=null;			
			ResultSet rsa=null;
			ResultSet rs_share_val=null;
			ms=new ShareMasterObject();
			Statement stmta=conn.createStatement();	
			Statement stmt_share_val=conn.createStatement();
			rsa=stmta.executeQuery("select sh_ind,ac_no,temp_no,br_code from ShareMaster where ac_type='"+actype+"' and ac_no="+shareno+" ");
			/*if((String.valueOf(shareno)!="500%"))
			 {
			 System.out.println("True");
			 rsa=stmta.executeQuery("select sh_ind,ac_no,temp_no from ShareMaster where ac_type='"+actype+"' and ac_no="+shareno+" ");
			 //rsa=stmta.executeQuery("select sh_ind,ac_no,temp_no from ShareMaster where ac_type='"+actype+"' and temp_no="+shareno+" ");
			  }
			  else
			  //rsa=stmta.executeQuery("select sh_ind,ac_no,temp_no from ShareMaster where ac_type='"+actype+"' and temp_no="+shareno+" ");
			   rsa=stmta.executeQuery("select sh_ind,ac_no,temp_no from ShareMaster where ac_type='"+actype+"' and ac_no="+shareno+" ");*/
			if(rsa.next())
			{
				str_sh_ind=rsa.getString("sh_ind");
				br_code=rsa.getInt(4);
			}
			
			else
				str_sh_ind=null;
			
			System.out.println("print share indicator (result set)"+str_sh_ind);	
			//int acno=0;
			if(str_sh_ind==null ||(str_sh_ind.equals("T")&& br_code!=1 ))
			{
				type=0;
				//acno=rsa.getInt("temp_no");
			}
			else
			{
				type=1;
				//acno=rsa.getInt("ac_no");
			}
			
			/*if(str_sh_ind.equals("P"))	
			 {	type=1;}
			 else if(str_sh_ind.equals("T"))
			 {	type=0;}*/
			
			rsa.close();	
			
			System.out.println("Type b4 select ..............."+type);
			if(type==1)
			{
				if(br_code!=1)
					rs=stmt.executeQuery("select ShareMaster.*  from ShareMaster where ac_no="+shareno+" and  ac_type='"+actype+"' and  sh_ind='P'");
				else
					rs=stmt.executeQuery("select ShareMaster.*  from ShareMaster where ac_no="+shareno+" and  ac_type='"+actype+"' ");
			}
			else if(type==0)
				rs=stmt.executeQuery("select ShareMaster.* from ShareMaster  where temp_no="+shareno+" and  ac_type='"+actype+"' and  sh_ind='T' ");
			
			System.out.println("Type ..............."+type);
			if(type==1)
			{
				if(br_code!=1)
					rs1=stmt1.executeQuery("select * from ShareTransaction  where ac_no="+shareno +" and ac_type='"+actype+"' and susp_ind='P'");
				else
					rs1=stmt1.executeQuery("select * from ShareTransaction  where ac_no="+shareno +" and ac_type='"+actype+"'");
				
			}
			else if(type==0)
			{
				rs1=stmt1.executeQuery("select * from ShareTransaction  where ac_no="+shareno +" and ac_type='"+actype+"' and susp_ind='T'");
			}
			rs1.last();
			row_count=rs1.getRow();
			System.out.println("row count ..............."+row_count);
			//ms.setTrnMode(rs1.getString("trn_mode"));
			rs1.beforeFirst();
			if(rs.next())
			{
				System.out.println("Type ..............."+type);
				//ms=new ShareMasterObject();
				System.out.println("@@ve_user:"+rs.getString("ve_user"));
				if(rs.getString("ve_user")==null) //verified
					ms.setVerified("F");
				else
					ms.setVerified("T");
				
				ms.setShareAccType(rs.getString("ac_type"));			
				ms.setShareNumber(rs.getInt("ac_no")); //not yet verified
				ms.setTempShareNumber(rs.getInt("temp_no")); //not yet verified
				ms.setBranchCode(rs.getInt("br_code"));
				ms.setCustomerId(rs.getInt("cid"));
				ms.setShareType(rs.getInt("mem_cat"));
				ms.setIssueDate(rs.getString("mem_issuedate"));
				ms.setShareStatus(rs.getString("sh_ind"));
				ms.setMemberCategory(rs.getInt("mem_cat"));
				System.out.println("Share status"+ms.getShareStatus());
				
				/*Statement stmt2=conn.createStatement();
				 ResultSet rs3=stmt2.executeQuery("select shareval,sh_cap,sus_sh_cap from ShareType where mem_cat="+ms.getShareType() +" and ac_type="+ms.getShareAccType());
				 rs3.next();
				 ms.setShareVal(rs3.getFloat("shareval"));
				 ms.setGLCode(rs3.getString("sh_cap"));
				 ms.setSuspGLCode(rs3.getString("sus_sh_cap"));
				 
				 rs3.close();
				 stmt2.close();
				 
				 //ms.setShareBalance(rs.getFloat("share_val"));Swaran
				  //ms.setShareBalance(rs1.getFloat("share_bal"));
				   
				   ms.setNumberofShares(ms.getShareBalance()/ms.getShareVal());*/
				ms.setPayMode(rs.getString("pay_mode"));
				ms.setPaymentAccno(rs.getInt("pay_ac_no"));
				ms.setPaymentAcctype(rs.getString("pay_ac_type"));
				System.out.println("@@rs.getString(mem_cl_date):"+rs.getString("mem_cl_date"));
				/*if(rs.getString("mem_cl_date").equals("")||rs.getString("mem_cl_date")==null)
				 ms.setCloseDate(null);
				 else*/
				ms.setCloseDate(rs.getString("mem_cl_date"));
				System.out.println("mem_cl_date"+rs.getString("mem_cl_date"));
				ms.setIntroducerAccno(rs.getInt("intr_ac_no"));
				ms.setIntroducerAcctype(rs.getString("intr_ac_type"));			
				
				ms.setNomineeno(rs.getInt("nom_no"));
				System.out.println("setnomineeeeno.........."+rs.getInt("nom_no"));
				Context ctx=getInitialContext();
				Object obj=ctx.lookup("COMMONWEB");
				commonServer.CommonHome home=(commonServer.CommonHome)obj;
				CommonRemote cremote=home.create();
				if(ms.getNomineeno()!=0)
					ms.setNomineeDetails(cremote.getNominee(ms.getNomineeno()));
				SignatureInstructionObject[] sig=cremote.getSignatureDetails(shareno,actype);
				if(sig!=null)
					ms.setSignatureDetails(sig);
				
				//ms.setNomineeDetails(cremote.getNominee(ms.getNomineeno(),ms.getShareAccType()));
				System.out.println("getnominee func changed");
				ms.setLoanAvailed(rs.getString("ln_availed"));
				ms.setRelationtoDirector(rs.getString("rel_code"));
				ms.setRelationDesc(rs.getString("rel_director"));
				ms.uv.setUserId(rs.getString("de_user"));
				ms.uv.setUserTml(rs.getString("de_tml"));
				ms.uv.setUserDate(rs.getString("de_date"));
				ms.uv.setVerId(rs.getString("ve_user"));
				ms.uv.setVerTml(rs.getString("ve_tml"));
				ms.uv.setVerDate(rs.getString("ve_date"));
				ms.setDisplayShareBalance(rs.getDouble("share_val"));
				
				//Added by Karthi==>18/04/2006
				ms.setMailingAddress(rs.getInt("addr_type"));
				ms.setDivUptoDate(rs.getString("div_uptodt"));
				String ldgprnt=rs.getString("ldgprtdt");
				/*  System.out.println("@@ldgprnt==>"+ldgprnt);
				 if(ldgprnt.equals("")||ldgprnt==null)
				 ms.setLdgPrntDate(null);
				 else*/
				ms.setLdgPrntDate(ldgprnt);
				ms.setTrnSeq(rs.getInt("lst_trn_seq"));
				ms.setNumberCert(rs.getInt("no_cert"));
				
				String trf_date=rs.getString("trf_date");
				/* if(trf_date.equals("")||trf_date==null)
				 ms.setTransferDate(null);
				 else*/
				ms.setTransferDate(trf_date);
				
				String share_stat=rs.getString("share_stat");
				/* if(share_stat.equals("")||share_stat==null)
				 ms.setShrState(null);
				 else*/
				ms.setShrState(share_stat);
				
				
				if(rs1.next())
				{
					
					System.out.println("Type ..............."+type);
					System.out.println("Row count ..............."+row_count);
					System.out.println("Goint inside");
					ms.setAmount(rs1.getFloat("trn_amt"));
					ms.setRecievedMode(rs1.getString("trn_mode"));
					//ms.setTranNumber(rs1.getInt("trn_no"));
					//ms.setNumberofShares(no_of_shares);
					
					Statement stmt2=conn.createStatement();
					//ResultSet rs3=stmt2.executeQuery("select shareval,sh_cap,sus_sh_cap from ShareType where mem_cat="+ms.getShareType() +" and ac_type="+ms.getShareAccType());
					ResultSet rs3=stmt2.executeQuery("select shareval from ShareType where mem_cat="+ms.getShareType() +" and ac_type='"+ms.getShareAccType()+"'");
					rs3.next();
					ms.setShareVal(rs3.getFloat("shareval"));
					//ms.setGLCode(rs3.getString("sh_cap"));
					//ms.setSuspGLCode(rs3.getString("sus_sh_cap"));
					if(row_count>1)    
					{
						System.out.println("row_count>1 ..............."+row_count);
						rs_share_val=stmt_share_val.executeQuery("select * from ShareTransaction  where ac_no="+shareno +" and ac_type='"+actype+"' and ve_user is not null");
						while(rs_share_val.next())
						{
							if(rs_share_val.getString("trn_type").equals("A"))
							{	System.out.println("share_bal_allotment"+rs_share_val.getInt("trn_amt"));
							allotment_total+=rs_share_val.getInt("trn_amt");
							}
							else if(rs_share_val.getString("trn_type").equals("W"))
							{	
								System.out.println("share_bal_withdrawal"+rs_share_val.getInt("trn_amt"));
								withdrawal_total+=rs_share_val.getInt("trn_amt");
							}
						}
						share_bal_total=allotment_total-withdrawal_total;
						share_val=rs3.getFloat("shareval");
						no_of_shares=share_bal_total/share_val;
						System.out.println("allotment_total"+allotment_total);
						System.out.println("withdrawal_total"+withdrawal_total);
						System.out.println("share_bal__total"+share_bal_total);
						System.out.println("share_val"+share_val);
						System.out.println("no_of_shares"+no_of_shares);
						System.out.println(".................................");
					}
					else if(row_count==1)
					{	//share_bal_total=rs1.getInt("share_bal");
						if(rs1.getString("trn_type").equalsIgnoreCase("A") && rs1.getString("ve_user")==null)
						{
							//no_of_shares=rs1.getFloat("trn_amt")/rs3.getFloat("shareval");//07/07/2006
							no_of_shares=rs1.getInt("sh_cert_no");
							//share_bal_total=rs1.getFloat("trn_amt");//07/07/2006
							share_bal_total=no_of_shares*ms.getShareVal();
						}
						else 
						{
							no_of_shares=rs1.getFloat("trn_amt")/rs3.getFloat("shareval");
							share_bal_total=rs1.getFloat("trn_amt");
						}
					}
					System.out.println("share_bal"+rs1.getFloat("trn_amt"));
					System.out.println("share_val"+ rs3.getFloat("shareval"));
					System.out.println("row_count==1 ..............."+row_count);		
					System.out.println("no_of_shares ...row count==1"+no_of_shares);
					rs3.close();
					stmt2.close();
					//ms.setShareBalance(rs.getFloat("share_val"));Swaran
					ms.setShareBalance(rs1.getFloat("trn_amt"));
					//ms.setShareBalance(share_bal_total);//Karthi-->21/12/2005
					//ms.setNumberofShares(ms.getShareBalance()/ms.getShareVal());
					ms.setNumberofShares(no_of_shares);
					//ms.setNumberofShares(rs1.getInt("share_bal"));
					System.out.println("Received Mode"+ms.getRecievedMode());
					if(ms.getRecievedMode().equals("C"))
					{
						System.out.println("Received Acc no"+rs1.getInt("ref_no"));
						ms.setRecievedAccno(rs1.getInt("ref_no"));
					}
					else if (ms.getRecievedMode().equals("T"))
					{
						if(rs1.getString("trn_narr")!=null && rs1.getString("trn_narr").length()>0)
						{
							StringTokenizer st=new StringTokenizer(rs1.getString("trn_narr"));
							ms.setRecievedAcctype(st.nextToken());
							ms.setRecievedAccno(Integer.parseInt(st.nextToken()));
						}
						else
						{
							ms.setRecievedAcctype("");
							ms.setRecievedAccno(0);
						}
					}
				}
			}
			
		}catch(RemoteException rex){
			rex.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}		
		return ms;
	}
	public int confirmShare(ShareMasterObject sm[],String apdate,int type)
	{
		//type1--->New share allotment.2--->additional share allotment
		Connection conn=null;
       System.out.println("The date is"+apdate);
		for(int i=0;i<sm.length;i++)
		{
			System.out.println("i=="+i);
			System.out.println("sm[i].tempno"+sm[i].getTempShareNumber());
		}
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			Statement cpstmt=conn.createStatement();
			if(type==1)
			{
				ResultSet rs2=null;
				for(int i=0;i<sm.length;i++)
				{
					//no need for acccount no. genertion  karthi-->16/02/2006
					/*ResultSet rs=stmt.executeQuery("select lst_acc_no from Modules where modulecode="+sm[i].getShareAccType());
					 int no=0;
					 if(rs.next())
					 no=rs.getInt(1);
					 no=no+1;
					 System.out.println("1..........");
					 stmt.executeUpdate("update ShareMaster set ac_no="+(no)+",sh_ind='P',mem_issuedate='"+apdate+"' where temp_no="+sm[i].getTempShareNumber());*/
					stmt.executeUpdate("insert into ShareMasterLog select * from ShareMaster where ac_no="+sm[i].getTempShareNumber()+" and ac_type='"+sm[i].getShareAccType()+"'");
					stmt.executeUpdate("update ShareMaster set sh_ind='P',mem_issuedate='"+apdate+"' where ac_no="+sm[i].getTempShareNumber());
					Statement stmt2=conn.createStatement();
					//ResultSet rs1=stmt2.executeQuery("select lst_certallot_no from GenParam ");
					ResultSet rs1=stmt2.executeQuery("select max_renewal_count,std_inst from Modules where modulecode='"+sm[i].getShareAccType()+"'");//max_renewal_count-->Certificate Printing
					int cfrom=0,shno=0;
					rs1.next();
					cfrom=rs1.getInt(1)+1;
					shno=rs1.getInt(2)+1;
					rs1.close();

					for(int j=cfrom;j<(cfrom+sm[i].getNumberofShares());j++)
					{//stmt2.executeUpdate("insert into CertificateMaster values("+j+","+(no)+",'"+sm[i].getShareAccType()+"','"+getSysDate()+"',null)");
						System.out.println("inside certificatemaster.....");
						stmt2.executeUpdate("insert into CertificateMaster values("+j+","+sm[i].getTempShareNumber()+",'"+sm[i].getShareAccType()+"','"+apdate+"',null)");
					}

					//stmt2.executeUpdate("update GenParam set lst_certallot_no="+(cfrom+sm[i].getNumberofShares()-1));
					stmt2.executeUpdate("update Modules set max_renewal_count="+(cfrom+sm[i].getNumberofShares()-1)+",std_inst="+shno+" where modulecode='"+sm[i].getShareAccType()+"'");
					System.out.println("After updation.....");
					//stmt.executeUpdate("update ShareTransaction set ac_no="+(no)+",dist_no_from="+cfrom+",dist_no_to=share_bal+"+(cfrom-1)+",susp_ind='P' where ac_no="+sm[i].getTempShareNumber()+" and susp_ind='T'");//karthi-->16/02/2006
					stmt.executeUpdate("update ShareTransaction set dist_no_from="+cfrom+",dist_no_to="+(cfrom+sm[i].getNumberofShares()-1)+",susp_ind='P',sh_cert_no="+shno+" where ac_no="+sm[i].getTempShareNumber()+" and susp_ind='T'");
					//stmt.executeUpdate("update ShareMaster set mem_cl_date=date_format(sysdate(),'%d/%m/%Y') where sh_ind='T'");
					//stmt.executeUpdate("update Modules set lst_acc_no="+no+" where modulecode="+sm[i].getShareAccType());//karthi-->16/02/2006
					//GLTransObject trnobj=new GLTransObject();
					
					String prm_type=null;
					if(sm[i].getShareStatus().equals("P"))
						prm_type="D";
					if(sm[i].getShareStatus().equals("T"))
						prm_type="S";

					System.out.println("prm_type"+prm_type);
					ResultSet rs_gl=stmt.executeQuery("select prm_gl_code,prm_gl_type from ShareParam where ac_type='"+sm[i].getShareAccType()+"' and mem_cat="+sm[i].getMemberCategory()+" and prm_ty='S'");
					rs_gl.next();
					String gl_code=rs_gl.getString("prm_gl_code");
					String gl_type=rs_gl.getString("prm_gl_type");
					System.out.println("gl_code"+gl_code);
					System.out.println("gl_type"+gl_type);
					rs_gl.close();
					rs2=cpstmt.executeQuery("select mult_by from GLPost where ac_type='"+sm[i].getShareAccType()+"' and gl_code='"+gl_code+"'  and trn_type='A' and cr_dr='C'");
					rs2.next();
					GLTransObject trnobj=new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(gl_type);
					trnobj.setGLCode(gl_code);
					trnobj.setTrnMode(sm[i].getRecievedMode());
					trnobj.setCdind("D");
					//trnobj.setAmount(sm.getShareVal()*sm.getNumberofShares()*rs.getInt(1));
					int mult_by=rs2.getInt(1);
					if(mult_by>0)
						trnobj.setAmount(sm[i].getAmount()*mult_by);
					else
						trnobj.setAmount(sm[i].getAmount()*mult_by*(-1));

					trnobj.setAccType(sm[i].getShareAccType());
					trnobj.setAccNo(String.valueOf(sm[i].getTempShareNumber()));
					Statement stmt_trnseq=conn.createStatement();
					ResultSet rs_trnseq=stmt_trnseq.executeQuery("select trn_no from ShareTransaction where ac_no="+sm[i].getTempShareNumber()+" and ac_type='"+sm[i].getShareAccType()+"' and susp_ind='P' ");
					rs_trnseq.next();
					trnobj.setTrnSeq(1);
					trnobj.setTrnType("A");
					trnobj.setRefNo(rs_trnseq.getInt("trn_no"));
					trnobj.setVtml(sm[i].uv.getVerTml());
					trnobj.setVid(sm[i].uv.getVerId());
					trnobj.setVDate(sm[i].uv.getVerDate());
					storeGLTransaction(trnobj);

					/*rs2=cpstmt.executeQuery("select mult_by from GLPost where ac_type='"+sm[i].getShareAccType()+"' and gl_code='"+gl_code+"'  and trn_type='A' and cr_dr='C'");
					 rs2.next();*/
					rs_gl=stmt.executeQuery("select prm_gl_code,prm_gl_type from ShareParam where ac_type='"+sm[i].getShareAccType()+"' and mem_cat="+sm[i].getMemberCategory()+" and prm_ty='D'");
					rs_gl.next();
					gl_code=rs_gl.getString("prm_gl_code");
					gl_type=rs_gl.getString("prm_gl_type");
					System.out.println("gl_code"+gl_code);
					System.out.println("gl_type"+gl_type);
					rs_gl.close();
					rs2=cpstmt.executeQuery("select mult_by from GLPost where ac_type='"+sm[i].getShareAccType()+"' and gl_code='"+gl_code+"'  and trn_type='A' and cr_dr='C'");
					rs2.next();
					trnobj.setGLCode(gl_code);
					trnobj.setGLType(gl_type);
					trnobj.setCdind("C");
					//trnobj.setAccNo(String.valueOf(no));//Karthi-->16/02/2006
					trnobj.setAccNo(String.valueOf(sm[i].getTempShareNumber()));
					trnobj.setAmount(sm[i].getAmount()*rs2.getInt(1));
					storeGLTransaction(trnobj);

				}	
				return 1;
			}
			else if(type==2)
			{
				/**
				 * For type 2 code not changed by me as confirmation of shares not done 
				 * presently for additional suspense shares....Swaran
				 */

				ResultSet rs2=null;

				for(int i=0;i<sm.length;i++)
				{

					/*if(sm[i].getShareStatus()!=null)
					 if(sm[i].getShareStatus().equals("D"))
					 {*/
					Statement stmt2=conn.createStatement();
					//ResultSet rs1=stmt2.executeQuery("select lst_certallot_no from GenParam");
					ResultSet rs1=stmt2.executeQuery("select max_renewal_count,std_inst from Modules where modulecode='"+sm[i].getShareAccType()+"'");
					int cfrom=0,shno=0;
					rs1.next();
					cfrom=rs1.getInt(1)+1;
					shno=rs1.getInt(2)+1;
					rs1.close();

					for(int j=cfrom;j<(cfrom+sm[i].getNumberofShares());j++)
						stmt2.executeUpdate("insert into CertificateMaster values("+j+","+sm[i].getShareNumber()+",'"+sm[i].getShareAccType()+"','"+getSysDate()+"',null)");

					//stmt2.executeUpdate("update GenParam set lst_certallot_no="+(cfrom+sm[i].getNumberofShares()-1));
					stmt2.executeUpdate("update Modules set max_renewal_count="+(cfrom+sm[i].getNumberofShares()-1)+",std_inst="+shno+" where modulecode='"+sm[i].getShareAccType()+"'");
					stmt.executeUpdate("update ShareTransaction set dist_no_from="+cfrom+",dist_no_to="+(cfrom+sm[i].getNumberofShares()-1)+",susp_ind='P',sh_cert_no="+shno+" where trn_no="+sm[i].getTempShareNumber());
					//stmt.executeUpdate("update ShareMaster set num_shares=num_shares+"+sm[i].getNumberofShares()+",share_val=share_val+"+sm[i].getAmount()+" where ac_no="+sm[i].getShareNumber());

					ResultSet rs_gl=stmt.executeQuery("select prm_gl_code,prm_gl_type from ShareParam where ac_type='"+sm[i].getShareAccType()+"' and mem_cat="+sm[i].getMemberCategory()+" and prm_ty='S'");
					rs_gl.next();
					String gl_code=rs_gl.getString("prm_gl_code");
					String gl_type=rs_gl.getString("prm_gl_type");
					System.out.println("gl_code"+gl_code);
					System.out.println("gl_type"+gl_type);
					rs_gl.close();
					rs2=cpstmt.executeQuery("select mult_by from GLPost where ac_type='"+sm[i].getShareAccType()+"' and gl_code='"+gl_code+"'  and trn_type='A' and cr_dr='C'");
					rs2.next();

					/*rs2=cpstmt.executeQuery("select mult_by from GLPost where ac_type='"+sm[i].getShareAccType()+"' and gl_code='"+sm[i].getSuspGLCode()+"'  and trn_type='W' and cr_dr='D'");
					 rs2.next();*/
					GLTransObject	trnobj=new GLTransObject();
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(gl_type);
					trnobj.setGLCode(gl_code);
					trnobj.setTrnMode(sm[i].getRecievedMode());
					trnobj.setCdind("D");
					trnobj.setAmount(sm[i].getAmount()*rs2.getInt(1));
					trnobj.setAccType(sm[i].getShareAccType());
					trnobj.setAccNo(String.valueOf(sm[i].getTempShareNumber()));
					Statement stmt_trnseq=conn.createStatement();
					ResultSet rs_trnseq=stmt_trnseq.executeQuery("select trn_seq,trn_no from ShareTransaction where trn_no="+sm[i].getTempShareNumber()+" and ac_type='"+sm[i].getShareAccType()+"' and susp_ind='P' ");
					rs_trnseq.next();
					trnobj.setTrnSeq(rs_trnseq.getInt(1));
					trnobj.setTrnType("A");
					trnobj.setRefNo(rs_trnseq.getInt(2));
					trnobj.setVtml(sm[i].uv.getVerTml());
					trnobj.setVid(sm[i].uv.getVerId());
					trnobj.setVDate(sm[i].uv.getVerDate());
					storeGLTransaction(trnobj);

					rs_gl=stmt.executeQuery("select prm_gl_code,prm_gl_type from ShareParam where ac_type='"+sm[i].getShareAccType()+"' and mem_cat="+sm[i].getMemberCategory()+" and prm_ty='D'");
					rs_gl.next();
					gl_code=rs_gl.getString("prm_gl_code");
					gl_type=rs_gl.getString("prm_gl_type");
					System.out.println("gl_code"+gl_code);
					System.out.println("gl_type"+gl_type);
					rs_gl.close();
					rs2=cpstmt.executeQuery("select mult_by from GLPost where ac_type='"+sm[i].getShareAccType()+"' and gl_code='"+gl_code+"'  and trn_type='A' and cr_dr='C'");
					rs2.next();
					trnobj.setGLCode(gl_code);
					trnobj.setGLType(gl_code);
					trnobj.setCdind("C");
					trnobj.setAmount(sm[i].getAmount()*rs2.getInt(1));
					storeGLTransaction(trnobj);
					//}
				}
				return 1;
			}

		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}		
		return 0;
	}
	
	public int deleteShare(int no,String shtype)
	{
		Connection conn=null;
		String str_sh_ind=null;
		int refno=0;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rsa=null;
			ResultSet rsdc=null;
			int type=0;
			Statement stmta=conn.createStatement();	
			Statement stmtdc=conn.createStatement();				
			rsa=stmta.executeQuery("select sh_ind from ShareMaster where ac_type='"+shtype+"' and ac_no="+no+" ");
			
			if(rsa.next())
				str_sh_ind=rsa.getString("sh_ind");
			else
				str_sh_ind=null;
			
			System.out.println("print share indicator (delete rs)"+str_sh_ind);	
			
			if(str_sh_ind==null)
				type=0;
			else
				type=1;
			
			rsa.close();	
			//to set daycash scroll as unattached when deletion is done......Swaran
			rsdc=stmtdc.executeQuery("select ref_no from ShareTransaction where ac_no="+no+" and ac_type='"+shtype+"' and ve_user is null");
			if(rsdc!=null)
			{
				if(rsdc.next())
					refno=rsdc.getInt("ref_no");
				rsdc.close();
				stmt.executeUpdate("update DayCash set attached='F',ac_no=0 where scroll_no="+refno+" ");
				System.out.println("daycash scroll unattached following deletion");
			}
			if(type==1)
			{
				stmt.executeUpdate("insert into ShareMasterLog select * from ShareMaster where ac_no="+no+" and ac_type='"+shtype+"'");
				stmt.executeUpdate("delete from ShareMaster where ac_no="+no+" and ac_type='"+shtype+"'");
			}
			
			//stmt.executeUpdate("delete from ShareMaster where ac_no="+ms.getShareNumber()+"  and ac_type='"+ms.getShareAccType()+"'");
			else
			{
				stmt.executeUpdate("insert into ShareMasterLog select * from ShareMaster where temp_no="+no+" and ac_type='"+shtype+"'");
				stmt.executeUpdate("delete from ShareMaster where temp_no="+no+" and ac_type='"+shtype+"'");
			}
			//stmt.executeUpdate("delete from ShareMaster where ac_no="+no+" and ac_type='"+shtype+"'");
			return(stmt.executeUpdate("delete from ShareTransaction where ac_no="+no+" and ac_type='"+shtype+"'"));
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}		
		return(0); 
	}
	
	public int deleteShareTran(int no,int type)
	{
		Connection conn=null;
		int refno=0;
		//int trn_seq=0;
		String actype=null;
		int acno=0;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			Statement stmtdc=conn.createStatement();
			//Statement stmt_master=conn.createStatement();
			ResultSet rsdc=null;
			//to set daycash scroll as unattached when deletion is done......Swaran
			rsdc=stmtdc.executeQuery("select ac_type,ac_no,ref_no,trn_seq from ShareTransaction where trn_no="+no+" and ve_user is null");
			if(rsdc.next())
				actype=rsdc.getString("ac_type");
			acno=rsdc.getInt("ac_no");
			refno=rsdc.getInt("ref_no");
			//trn_seq=rsdc.getInt("trn_seq");
			//trn_seq--;
			rsdc.close();
			System.out.println("actype........."+ actype);
			System.out.println("acno........."+ acno);
			//System.out.println("trn_seq........."+ trn_seq);
			System.out.println("refno........."+ refno);
			if(type==0)
			{	
				stmt.executeUpdate("update DayCash set attached='F',ac_no=0 where scroll_no="+refno+" ");
				System.out.println("daycash scroll unattached following deletion");			
				//stmt_master.executeUpdate("update ShareMaster set lst_trn_seq="+trn_seq+" where ac_type='"+actype+"' and ac_no="+acno+" ");
				return(stmt.executeUpdate("delete from ShareTransaction where trn_no="+no+" and trn_type='A' "));
			}
			else if(type==1)
			{
				//stmt_master.executeUpdate("update ShareMaster set lst_trn_seq=(trn_seq-1) where ac_type='"+actype+"' and ac_no="+acno+" ");
				return(stmt.executeUpdate("delete from ShareTransaction where trn_no="+no+" and trn_type='W' "));
			}
			//return(stmt.executeUpdate("delete from ShareTransaction where ac_no="+no+" and ac_type='"+shtype+"'"));......not needed(Swaran)
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}		
		return(0); 
	}
	
	
	public ShareParamObject[] getShCategoryParameters(int cat_no,String actype)
	{
		ShareParamObject sh[]=null;
		Connection conn=null;
		
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("select * from ShareParam  where mem_cat="+cat_no+" and ac_type='"+actype+"' and prm_freq is not null order by prm_code limit 4");
			int i=0;
			while(rs.next())
				i++;
			sh=new ShareParamObject[i];
			rs.first();
			for(int j=0;j<i;j++)
			{
				sh[j]=new ShareParamObject();
				sh[j].setShareType(rs.getString("ac_type"));
				sh[j].setSh_cat(rs.getInt("mem_cat"));
				sh[j].setPrm_code(rs.getInt("prm_code"));
				sh[j].setPrm_desc(rs.getString("prm_desc"));
				sh[j].setPrm_amt(rs.getFloat("prm_amt"));
				sh[j].setPrm_freq(rs.getString("prm_freq"));
				sh[j].setPrm_ty(rs.getString("prm_ty"));
				sh[j].setPrm_gl_key(rs.getString("prm_gl_code"));
				sh[j].setPrm_gl_type(rs.getString("prm_gl_type"));
				rs.next();
			}
		}catch(Exception e){e.printStackTrace();}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}		
		return sh;
	}
	public ShareCategoryObject[] getShareCategories(int no,String actype)
	{
		ShareCategoryObject shcat[]=null;
		Connection conn=null;
		System.out.println("Acc. Type:"+actype);
		System.out.println("Number:"+no);
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs;
			if(no==0)
				rs=stmt.executeQuery("select * from ShareType where ac_type='"+actype+"'");
			else
				rs=stmt.executeQuery("select * from ShareType where mem_cat="+no +" and ac_type='"+actype+"'");
			
			int i=0;
			rs.last();
			shcat=new ShareCategoryObject[rs.getRow()];
			rs.beforeFirst();
			while(rs.next())
			{
				shcat[i]=new ShareCategoryObject();
				shcat[i].setShareType(rs.getString("ac_type"));
				shcat[i].setShCat(rs.getInt("mem_cat"));
				shcat[i].setCatName(rs.getString("catname"));
				shcat[i].setMinShare(rs.getInt("minshare"));
				shcat[i].setMaxShare(rs.getInt("maxshare"));
				shcat[i].setShareVal(rs.getFloat("shareval"));
				//shcat[i].setCapitalGLCode(rs.getString("sh_cap"));
				//shcat[i].setSuspenceGLCode(rs.getString("sus_sh_cap"));
				i++;
			}
			
		}catch(Exception e){e.printStackTrace();}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}		
		System.out.println("Length==>"+shcat.length);
		return shcat;
		
	}
	
	public DirectorMasterObject[] getDirectorName(String date)throws RecordsNotFoundException
	{
		Connection conn=null;
		DirectorMasterObject[] dir_mobj=null;
		
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			System.out.println("Director Code.......");
			System.out.println("The date:"+date);
			stmt=conn.createStatement();
			//ResultSet rs=stmt.executeQuery("select concat_ws(' ',cm.fname,cm.mname,cm.lname) as name from CustomerMaster cm,DirectorMaster dm where cm.cid="+id+" and cm.cid=dm.cid");
			//ResultSet rs=stmt.executeQuery("select dm.cid,dm.id,dm.from_date,dm.to_date,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name from CustomerMaster cm,DirectorMaster dm where cm.cid=dm.cid and concat(right('"+date+"',4),'-',mid('"+date+"',locate('/','"+date+"')+1,(locate('/','"+date+"',4)-locate('/','"+date+"')-1)),'-',left('"+date+"',locate('/','"+date+"')-1)) BETWEEN concat(right(dm.from_date,4),'-',mid(dm.from_date,locate('/',dm.from_date)+1,(locate('/',dm.from_date,4)-locate('/',dm.from_date)-1)),'-',left(dm.from_date,locate('/',dm.from_date)-1)) and concat(right(dm.to_date,4),'-',mid(dm.to_date,locate('/',dm.to_date)+1,(locate('/',dm.to_date,4)-locate('/',dm.to_date)-1)),'-',left(dm.to_date,locate('/',dm.to_date)-1)) order by id");
			ResultSet rs=stmt.executeQuery("select dm.cid,dm.director_code,dm.from_date,dm.to_date,cm.fname from CustomerMaster cm,DirectorMaster dm where cm.cid=dm.cid and concat(right('"+date+"',4),'-',mid('"+date+"',locate('/','"+date+"')+1,(locate('/','"+date+"',4)-locate('/','"+date+"')-1)),'-',left('"+date+"',locate('/','"+date+"')-1)) BETWEEN concat(right(dm.from_date,4),'-',mid(dm.from_date,locate('/',dm.from_date)+1,(locate('/',dm.from_date,4)-locate('/',dm.from_date)-1)),'-',left(dm.from_date,locate('/',dm.from_date)-1)) and concat(right(dm.to_date,4),'-',mid(dm.to_date,locate('/',dm.to_date)+1,(locate('/',dm.to_date,4)-locate('/',dm.to_date)-1)),'-',left(dm.to_date,locate('/',dm.to_date)-1)) order by dm.director_code");
			//System.out.println("getdirectorname"+rs.getString(1));
			rs.last();
			if(rs.getRow()==0)
			{	
				System.out.println("result set empty");
				throw new RecordsNotFoundException();
			}
			System.out.println("result set notttt empty");
			dir_mobj=new DirectorMasterObject[rs.getRow()];
			rs.beforeFirst();
			//System.out.println("getdirectorname........."+rs.getString("name"));
			int i=0;
			while(rs.next())
			{
				dir_mobj[i]=new DirectorMasterObject();
				dir_mobj[i].setDirectorId(rs.getInt("director_code"));
				//dir_mobj[i].setDirectorName(rs.getString("name"));
				dir_mobj[i].setDirectorName(rs.getString("cm.fname"));
				i++;
			}
		}catch(Exception e){e.printStackTrace();}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}		
		return dir_mobj;
	}
	public AccountObject[] getCheques(String acctype,int accno)
	{
		AccountObject a[]=null;
		Connection conn=null;
		
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("select ctrl_no,cr_ac_type,cr_ac_no,payee_name,trn_amt,desp_ind,post_ind,ve_user from Cheque where post_ind='T' and cr_ac_type='"+acctype+"' and cr_ac_no="+accno);
			rs.last();	
			AccountObject ac[]=new AccountObject[rs.getRow()];
			rs.beforeFirst();
			int i=0;
			System.out.println("Length"+ac.length);
			while(rs.next())
			{
				System.out.println("Before calling");
				a[i]=new AccountObject();
				a[i].setCid(rs.getInt("ctrl_no"));
				a[i].setAcctype(rs.getString("cr_ac_type"));
				a[i].setAccno(rs.getInt("cr_ac_no"));
				a[i].setAccname(rs.getString("payee_name"));
				a[i].setAmount(rs.getDouble("trn_amt"));
				i++;
			}
		}catch(Exception e){e.printStackTrace();}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}		
		
		return a;
	}
	public int storeShareTypes(Vector vec)
	{
		Connection conn=null;
		System.out.println("inside storeShareTypes Func.....");
		
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			//PreparedStatement ps=conn.prepareStatement("insert into ShareType values(?,?,?,?,?,?,?,?,?)");
			PreparedStatement ps=conn.prepareStatement("insert into ShareType values(?,?,?,?,?,?,?)");
			for(int i=0;i<vec.size();i++)
			{
				Object obj[]=(Object[])vec.get(i);
				System.out.println("The vector at the bean is");
				System.out.println(vec.get(i).toString());
				stmt.executeUpdate("delete from ShareType where ac_type='"+obj[0].toString()+"' and mem_cat="+obj[1].toString());
				ps.setString(1,obj[0].toString());
				ps.setString(2,obj[1].toString());
				ps.setString(3,obj[2].toString());
				ps.setString(4,obj[3].toString());
				ps.setString(5,obj[4].toString());
				ps.setString(6,obj[5].toString());
				ps.setString(7,obj[6].toString());
				//ps.setString(8,obj[7].toString());
				//ps.setString(9,obj[8].toString());
				ps.addBatch();			
			}
			int a[]=ps.executeBatch();
			return (a[0]);		
		}catch(SQLException exception){
			sessionContext.setRollbackOnly();
			exception.printStackTrace();
		}catch(Exception exception){
			sessionContext.setRollbackOnly();
			exception.printStackTrace();
		}
		finally 
		{
			try{conn.close();}catch(SQLException ex){ex.printStackTrace();}
		}
		return 1;
	}
	public ShareMasterObject getShareTransaction(int trnno,int type,String date)
	{
		//type 0 for additional shares,1 for withdrawal shares
		ShareMasterObject ms=null;
		Connection conn=null;
		double allotment_total=0;
		double withdrawal_total=0;
		double share_bal_total=0;
		double no_of_shares=0;
		double share_balance=0;
		Statement stmt=null,stmt1=null,stmt_total=null;
		ResultSet rs=null,rs1=null,rs3=null,rs_total=null;
		try{
			conn=getConnection();
			stmt=conn.createStatement();
			stmt1=conn.createStatement();
			stmt_total=conn.createStatement();
			/* For withdrawal   */
            System.out.println("The trn_no is"+trnno);
            System.out.println("The type is"+type);
            System.out.println("The date is "+date);
			
			rs1=stmt1.executeQuery("select * from ShareTransaction  where trn_no="+ trnno +" and trn_date='"+date+"' and ve_user is null" );
			if(rs1.next())
			{
				String actype=rs1.getString("ac_type");
				int acno=rs1.getInt("ac_no");
				ms=new ShareMasterObject();
				if(rs1.getString("ve_user")==null) //verified
					ms.setVerified("F");
				else
					ms.setVerified("T");
				ms.setShareTrnType(rs1.getString("trn_type"));
				ms.setRecievedMode(rs1.getString("trn_mode"));
				if(ms.getRecievedMode().equals("C"))
					ms.setRecievedAccno(rs1.getInt("ref_no"));
				else if(ms.getRecievedMode().equals("T") || ms.getRecievedMode().equals("B"))
				{
					StringTokenizer st=new StringTokenizer(rs1.getString("trn_narr"));
					ms.setRecievedAcctype(st.nextToken());
					ms.setRecievedAccno(Integer.parseInt(st.nextToken()));
				}
				else if(ms.getRecievedMode().equalsIgnoreCase("G"))//Karthi-->23/05/06
					ms.setRecievedAccno(rs1.getInt("ref_no"));
				if(type==0){
					ms.setTrnamt(rs1.getString("trn_amt"));
					System.out.println("The trn amt is===================== "+rs1.getFloat("trn_amt"));
				}
				else
					ms.setAmount(rs1.getFloat("share_bal"));
				System.out.println("Amount==>"+rs1.getFloat("share_bal"));
				ms.setShareNumber(rs1.getInt("ac_no"));
				ms.setTranNumber(rs1.getInt("trn_no"));
				ms.setNumberCert(rs1.getInt("sh_cert_no"));
				System.out.println("The transaction amount is"+rs1.getString("trn_amt"));
				ms.setTrnamt(rs1.getString("trn_amt"));
				//share_balance=rs1.getInt("trn_amt");
				ms.setShareStatus(rs1.getString("susp_ind"));
				ms.uv.setUserId(rs1.getString("de_user"));
				ms.uv.setUserTml(rs1.getString("de_tml"));
				ms.uv.setUserDate(rs1.getString("de_date"));
				ms.uv.setVerId(rs1.getString("ve_user"));
				ms.uv.setVerTml(rs1.getString("ve_tml"));
				ms.uv.setVerDate(rs1.getString("ve_date"));
				
				rs=stmt.executeQuery("Select * from ShareMaster where ac_type='"+actype+"' and ac_no="+ms.getShareNumber());
				rs.next();
				ms.setShareAccType(rs.getString("ac_type"));			
				ms.setBranchCode(rs.getInt("br_code"));
				ms.setCustomerId(rs.getInt("cid"));
				ms.setMemberCategory(rs.getInt("mem_cat"));
				ms.setCloseDate(rs.getString("mem_cl_date"));
				
				Statement stmt2=conn.createStatement();
				rs3=stmt2.executeQuery("select shareval from ShareType where mem_cat="+ms.getMemberCategory() +" and ac_type='"+ms.getShareAccType()+"'");
				rs3.next();
				ms.setShareVal(rs3.getFloat("shareval"));
				
				if(type==0)
				{
					ms.setShareBalance(rs1.getInt("sh_cert_no"));
				}
				
				if(type==0){
				rs_total=stmt_total.executeQuery("select * from ShareTransaction where ac_no="+acno +" and ac_type='"+actype+"' and ve_user is not null");
				}
				else if(type==1){
					rs_total=stmt_total.executeQuery("select * from ShareTransaction where ac_no="+acno +" and ac_type='"+actype+"' and ve_user is  null");
					
				}
				
				
				while(rs_total.next())
				{
					System.out.println("The trn type is"+rs_total.getString("trn_type"));
					System.out.println("The number of shares left 111111 after withdrrwal are"+rs_total.getDouble("share_bal")/100.00);
					if(rs_total.getString("trn_type").equals("A"))
					{	
						allotment_total+=rs_total.getDouble("trn_amt");
					}
					else if(rs_total.getString("trn_type").equals("W"))
					{	
						withdrawal_total+=rs_total.getDouble("trn_amt");
						ms.setTrnamt(String.valueOf(rs_total.getDouble("trn_amt")));
						ms.setWith_shares((int) (rs_total.getDouble("trn_amt")/ms.getShareVal()));
						ms.setNumberofShares(rs_total.getDouble("share_bal")/100.00);
						System.out.println("The number of shares left is after withdrrwal are"+rs_total.getDouble("share_bal")/100.00);
						
					}
					
				}
				share_bal_total=allotment_total-withdrawal_total;
				ms.setAmount(share_bal_total);
				System.out.println("The share bal total is======================= "+share_bal_total);
				System.out.println(rs1.getFloat("trn_amt"));
				
				float share_val=rs3.getFloat("shareval");
				System.out.println("The share_val is============================="+share_val);
				no_of_shares=share_bal_total/share_val;
				System.out.println("The no of shares are=========================="+no_of_shares);
				
				if(type==1){
					ms.setNumberofShares(rs_total.getDouble("share_bal")/100.00);
				}
				else
				
				ms.setNumberofShares(no_of_shares);
				stmt2.close();
				ms.setShareBalance(share_balance/share_val);
				ms.setLoanAvailed(rs.getString("ln_availed"));
				rs3.close();
			}
		}catch(Exception e){e.printStackTrace();}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}		
		
		return ms;
	}
	public DividendRateObject[] getDivRate(String fdate,String tdate)
	{
		ResultSet rs;
		DividendRateObject[] div=null;
		System.out.println("from date...getdivrate"+fdate);
		System.out.println("to date...getdivrate"+tdate);
		int i=0;
		Connection conn=null;
		
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			rs = stmt.executeQuery("select fr_date,to_date,div_rate,drf_amt,cal_done from ShareDivRate where concat(right(to_date,4),'-',mid(to_date,locate('/',to_date)+1,(locate('/',to_date,4)-locate('/',to_date)-1)),'-',left(to_date,locate('/',to_date)-1))>='"+Validations.convertYMD(fdate)+"' and concat(right(fr_date,4),'-',mid(fr_date,locate('/',fr_date)+1,(locate('/',fr_date,4)-locate('/',fr_date)-1)),'-',left(fr_date,locate('/',fr_date)-1))<='"+Validations.convertYMD(tdate)+"'");
			rs.last();
			i=rs.getRow();
			System.out.println("i...getdivrate"+i);
			div=new DividendRateObject[i];
			rs.beforeFirst();
			for(int j=0;j<i;j++)
			{
				System.out.println("j(in loop)...getdivrate"+j);
				rs.next();
				div[j]=new DividendRateObject();
				if(j==0)
					div[j].setFromDate(fdate);
				else
					div[j].setFromDate(rs.getString("fr_date"));
				
				if(j==(i-1))
					div[j].setToDate(tdate);
				else
					div[j].setToDate(rs.getString("to_date"));
				
				div[j].setRate(rs.getFloat("div_rate"));
				div[j].setAmount(rs.getFloat("drf_amt"));
				div[j].setCalDone(rs.getString("cal_done"));
				
				System.out.println("div[j]fromdate...getdivrate"+div[j].getFromDate());
				System.out.println("div[j]todate.getdivrate"+div[j].getToDate());
				System.out.println("div[j]rate...getdivrate"+div[j].getRate());
				System.out.println("div[j],amt,...getdivrate"+div[j].getAmount());
				System.out.println("div[j]caldone...getdivrate"+div[j].getCalDone());
			}
			
		}catch(Exception e){e.printStackTrace();}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}		
		return div;
	}
	public int calculateDividend(DividendRateObject[] div,String detml,String deuser,String dedate,int type)
	{
		System.out.println("Entering Calculate Dividend:");
		int ret_val=-2;
		double sum=0.0,total =0.0;
		double amt=0.0;
		String ac_type=null,div_date=null;
		String[] trn_date=null;
		int lst_voucher_no=0,ac_no=0,no_of_days=0,count=0,frm_to_days=0;
		ResultSet rs=null,rs_no=null;
		Connection conn=null;
		
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			Statement cpstmt=conn.createStatement();
			Statement cpstmt1=conn.createStatement();
			/*lst_voucher_no = common_local.getModulesColumn("lst_voucher_no","1001001");
			 System.out.println("lst_voucher_no"+lst_voucher_no);*/
			
			ResultSet rs2;
			if(type==1||type==3)
			{
				System.out.println("type == "+type);				
				System.out.println("div Length : "+div.length); 
				for(int i=0;i<div.length;i++)
				{
					System.out.println("THE CAL DONE IS "+div[0].getCalDone());
					try
					{
						double divamt=0;
						if(type==3)
						{
							//stmt.executeUpdate("delete from ShareDividend where concat(right(div_dt,4),'-',mid(div_dt,locate('/',div_dt)+1,(locate('/',div_dt,4)-locate('/',div_dt)-1)),'-',left(div_dt,locate('/',div_dt)-1)) between '"+Validations.convertYMD(div[i].getFromDate())+"' and '"+Validations.convertYMD(div[i].getToDate())+"'");
							//stmt.executeUpdate("update ShareDivRate set cal_done='F' where '"+Validations.convertYMD(div[i].getFromDate())+"' between where concat(right(fr_date,4),'-',mid(fr_date,locate('/',fr_date)+1,(locate('/',fr_date,4)-locate('/',fr_date)-1)),'-',left(fr_date,locate('/',fr_date)-1)) and concat(right(to_date,4),'-',mid(to_date,locate('/',to_date)+1,(locate('/',to_date,4)-locate('/',to_date)-1)),'-',left(to_date,locate('/',to_date)-1))");//
							stmt.executeUpdate("update ShareDivRate set cal_done='F' where '"+Validations.convertYMD(div[i].getFromDate())+"' between  concat(right(fr_date,4),'-',mid(fr_date,locate('/',fr_date)+1,(locate('/',fr_date,4)-locate('/',fr_date)-1)),'-',left(fr_date,locate('/',fr_date)-1)) and concat(right(to_date,4),'-',mid(to_date,locate('/',to_date)+1,(locate('/',to_date,4)-locate('/',to_date)-1)),'-',left(to_date,locate('/',to_date)-1))");//Karthi
							div[i].setCalDone("F");
						}
						if(div[i].getCalDone().equalsIgnoreCase("F"))
						{
							
							frm_to_days=common_local.getDaysFromTwoDate(div[i].getFromDate(),div[i].getToDate());
							for(int loop=1;loop<3;loop++)
							{
								if(loop==1)
								{//For active accounts
									System.out.println("loop1");
									rs=stmt.executeQuery("select ac_no,div_uptodt,pay_mode,pay_ac_type,pay_ac_no,ac_type from ShareMaster where concat(right( mem_issuedate,4),'-',mid( mem_issuedate,locate('/', mem_issuedate)+1,(locate('/', mem_issuedate,4)-locate('/', mem_issuedate)-1)),'-',left( mem_issuedate,locate('/', mem_issuedate)-1)) <='"+Validations.convertYMD(div[i].getToDate())+"' and ac_no is not null and ac_no!=0 and div_uptodt is not null and mem_cl_date is null and ve_user is not null order by ac_no");
								}	
								if(loop==2)
								{//For accounts closed in this financial year
									System.out.println("loop2");
									rs=stmt.executeQuery("select ac_no,div_uptodt,mem_cl_date,pay_mode,pay_ac_type,pay_ac_no,ac_type from ShareMaster where ac_no is not null and mem_cl_date is not null and concat(right(mem_cl_date,4),'-',mid(mem_cl_date,locate('/',mem_cl_date)+1,(locate('/',mem_cl_date,4)-locate('/',mem_cl_date)-1)),'-',left(mem_cl_date,locate('/',mem_cl_date)-1)) between '"+Validations.convertYMD(div[i].getFromDate())+"' and '"+Validations.convertYMD(div[i].getToDate())+"'and ac_no!=0 order by ac_no");
								}
								rs.last();
								System.out.println("rs ShareMaster no of rows:"+rs.getRow());
								rs.beforeFirst();
								while(rs.next())
								{
									ac_type=rs.getString("ac_type");
									ac_no=rs.getInt("ac_no");
									div_date=rs.getString("div_uptodt");
									rs2 = cpstmt.executeQuery("select trn_date,trn_amt,share_bal from ShareTransaction where ac_type='"+ac_type+"'and ac_no="+ac_no+" and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1,(locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1)) between '"+Validations.convertYMD(div_date)+"' and '"+Validations.convertYMD(div[i].getToDate())+"'");
									rs2.last();
									System.out.println("rs2.getRow()==>"+rs2.getRow());
									trn_date=new String[rs2.getRow()+2];
									System.out.println("trn_date.length==>"+trn_date.length);
									trn_date[0]=div_date;
									count=rs2.getRow();
									int m=1;
									rs2.beforeFirst();
									while(rs2!=null && rs2.next())
									{
										trn_date[m]=rs2.getString("trn_date");
										m++;
									}
									trn_date[m]=div[i].getToDate();
									for(int k=1;k<trn_date.length;k++)
									{
										if(trn_date[k].equalsIgnoreCase(trn_date[k-1]))
											continue;
										amt=getClosingBalance(ac_type,ac_no,trn_date[k-1]);
										//no_of_days=common_local.getDaysFromTwoDate(trn_date[k-1],common_remote.getFutureDayDate(trn_date[k],-1));
										no_of_days=common_local.getDaysFromTwoDate(trn_date[k-1],trn_date[k]);
										amt*=no_of_days;
										//amt=((amt/frm_to_days)*(div[i].getRate()/100));
										amt=((amt/365)*(div[i].getRate()/100));
										total+=amt;
									}
									rs_no=cpstmt1.executeQuery("Select ln_modulecode from Modules where modulecode='"+ac_type+"'");
									rs_no.next();
									lst_voucher_no=rs_no.getInt("ln_modulecode");
									lst_voucher_no=lst_voucher_no+1;
									cpstmt1.executeUpdate("update Modules set ln_modulecode="+lst_voucher_no+" where modulecode='"+ac_type+"'");
									divamt=Math.round(total);
									System.out.println("Execution: "+cpstmt.executeUpdate("insert into ShareDividend(ac_type,ac_no,div_dt,div_amt,ddn_amt,cd_ind,de_tml,de_user,de_date,pay_mode,div_ac_ty,div_ac_no,cv_paid,wrt_prtd,voucher_no) values('"+ac_type+"',"+ac_no+",'"+div[i].getToDate()+"',"+divamt+","+div[i].getAmount()+",'C','"+detml+"','"+deuser+"','"+dedate+"','"+rs.getString("pay_mode")+"','"+rs.getString("pay_ac_type")+"',"+rs.getInt("pay_ac_no")+",'F','F',"+lst_voucher_no+")"));
									ac_type=null;ac_no=0;divamt=0;total=0;trn_date=null;
								}//end of 'rs' while loop
								if(loop>1)
								{
									stmt.executeUpdate("update ShareDivRate set cal_done='T' where '"+Validations.convertYMD(div[i].getToDate())+"' between concat(right(fr_date,4),'-',mid(fr_date,locate('/',fr_date)+1,(locate('/',fr_date,4)-locate('/',fr_date)-1)),'-',left(fr_date,locate('/',fr_date)-1)) and concat(right(to_date,4),'-',mid(to_date,locate('/',to_date)+1,(locate('/',to_date,4)-locate('/',to_date)-1)),'-',left(to_date,locate('/',to_date)-1))");
									ret_val=1;
								}
								
							}//end of for loop
						} // end of if loop
					}catch(Exception ii){System.out.println("main"+ii);ii.printStackTrace();}
				}
			}
			/*stmt.executeUpdate("update Modules set lst_voucher_no="+--lst_voucher_no+" where modulecode='1001001' ");
			 System.out.println("updated modules"+lst_voucher_no);*/
			if(type==2)
			{
				ret_val=stmt.executeUpdate("insert into ShareDivRate(fr_date,to_date,div_rate,drf_amt,cal_done,cal_opt,de_tml,de_user,de_date)values('"+div[0].getFromDate()+"','"+div[0].getToDate()+"',"+div[0].getRate()+","+div[0].getAmount()+",'F','','"+deuser+"','"+detml+"','"+dedate+"')");
			}
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}		
		return ret_val;
	}
	//	Dividends Transfer		
	
	public int main_transfer(String share_type,String utml,String uid,String udate,String date)throws RecordsNotFoundException
	{
		
		System.out.println("utml : "+utml);
		System.out.println("uid :"+uid);
		ResultSet res=null,rs=null,rs_1=null;
		PreparedStatement ps1=null;
		int ret=0,divaccno=0,acno=0,voucher_no=0;
		double divamt,ddnamt,netamt=0;
		String divacctype=null,actype=null,div_dt=null;
		Connection conn=null;
		
		try{
			System.out.println("getting connection");
			conn=getConnection();
			Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			Statement cstmt=conn.createStatement();
			Statement stmt2=conn.createStatement();
			System.out.println("gottt connection");
			System.out.println("to execute query 1");
			res=stmt.executeQuery("select ac_type,ac_no,div_dt,div_ac_no,div_ac_ty,div_amt,ddn_amt,cv_paid,pay_mode from ShareDividend where ac_type='"+share_type+"' and cv_paid='F' and pay_mode='T' ");
			System.out.println("executed query 1");
			res.last();
			if(res.getRow()==0)
			{	
				System.out.println("result set empty");
				throw new RecordsNotFoundException();
				
			}
			System.out.println("no of rows"+res.getRow());
			res.beforeFirst();
			while(res.next())
			{
				rs=cstmt.executeQuery("select lst_rct_no from Modules where modulecode='"+share_type+"'");
				rs.next();
				voucher_no=rs.getInt("lst_rct_no");
				if(voucher_no==0)
					cstmt.executeUpdate("Update Modules set lst_rct_no="+(++voucher_no)+" where modulecode='"+share_type+"'");
				
				cstmt.executeUpdate("Update Modules set lst_rct_no=lst_rct_no+1 where modulecode='"+share_type+"'");
				System.out.println("Current Row 1= "+res.getRow());
				acno=res.getInt("ac_no");
				actype=res.getString("ac_type");
				div_dt=res.getString("div_dt");
				divaccno=res.getInt("div_ac_no");
				divacctype=res.getString("div_ac_ty");
				divamt=res.getDouble("div_amt");
				ddnamt=res.getDouble("ddn_amt");
				netamt=divamt-ddnamt;
				System.out.println("inside while looooop");
				System.out.println("Current Row 2 = "+res.getRow());
				if(divamt>ddnamt)
				{	
					System.out.println("divant>ddnamt");
					AccountTransObject at=new AccountTransObject(); 
					//at=null;
					System.out.println("object at created"+divacctype);
					at.setAccType(divacctype);
					at.setAccNo(divaccno);
					at.setTransType("R");
					at.setTransAmount(divamt-ddnamt);
					at.setTransMode("T");
					at.setTransSource(utml);
					at.setTransDate(date);
					at.setCdInd("C");
					at.setChqDDNo(0);
					at.setChqDDDate("");
					at.setTransNarr(actype+" "+acno);
					at.setRef_No(voucher_no);
					at.setPayeeName("");
					at.setCloseBal(divamt-ddnamt);
					at.setLedgerPage(0);
					at.uv.setUserTml(utml);
					at.uv.setUserId(uid);
					at.uv.setUserDate(udate);
					at.uv.setVerTml(utml);
					at.uv.setVerId(uid);
					at.uv.setVerDate(udate);
					//at.setGLRefCode(Integer.parseInt(divacctype));
					//Changed By Karthi-->12/01/2006
					if(divacctype!=null)
						at.setGLRefCode(Integer.parseInt(divacctype));
					else
						at.setGLRefCode(0);
					//at.setGLRefCode(1002001);
					
					System.out.println("Current Row 3 = "+res.getRow());
					System.out.println("commonremote b4");
					/*CommonHome commonhome=null;
					 CommonRemote commonremote=null;
					 commonremote=commonhome.create();*/
					ret=common_local.storeAccountTransaction(at);
					System.out.println("*******DATA********");
					System.out.println("Voucher no"+voucher_no);
					System.out.println("Date"+date);
					System.out.println(uid +"  "+utml+" "+divaccno+"   "+divacctype+"  "+udate);
					System.out.println("*******DATA end*******");
					cstmt.executeUpdate("update ShareDividend set cv_no="+voucher_no+", cv_paid='T',cv_dt='"+date+"',paydiv_dt='"+date+"',ve_user='"+uid+"',ve_tml='"+utml+"',ve_date='"+udate+"' where div_ac_no="+divaccno+" and div_ac_ty='"+divacctype+"'");
					System.out.println("commonremote after function cal");
					System.out.println("Current Row 4 = "+res.getRow());
					Statement stmt1=conn.createStatement();
					int trnno=0;
					//ResultSet rs_1=stmt1.executeQuery("select sh_trn_no from GenParam");
					/*ResultSet rs_1=stmt1.executeQuery("select last_trf_scroll_no from Modules where modulecode='"+share_type+"'");
					 if(rs_1.next())
					 //trnno=rs_1.getInt("sh_trn_no");
					  trnno=rs_1.getInt("last_trf_scroll_no");
					  
					  System.out.println("trn no"+trnno);
					  trnno++;
					  System.out.println("trn no++"+trnno);		
					  //stmt1.executeUpdate("update GenParam set sh_trn_no="+trnno);
					   stmt1.executeUpdate("update Modules set last_trf_scroll_no="+trnno+" where modulecode='"+share_type+"'");
					   System.out.println("final trn no isss,,,,,"+trnno);*/
					System.out.println("----------------Voucher Data--------------------");
					System.out.println(actype+""+ "ac_no"+acno+"cv_no"+voucher_no);
					System.out.println("----------------Voucher Data--------------------");
					rs_1=stmt2.executeQuery("select cv_no,voucher_no,de_user,de_tml,de_date,ve_user,ve_tml,ve_date from ShareDividend where ac_type='"+actype+"' and ac_no="+acno+" and cv_no="+voucher_no);
					rs_1.next();
					if(rs_1.getInt("voucher_no")!=0)
						trnno=rs_1.getInt("voucher_no");
					ResultSet rs_trn=stmt1.executeQuery("select * from ShareTransaction where ac_type='"+actype+"' and ac_no="+acno+" order by trn_seq desc limit 1");
					rs_trn.next();
					ShareMasterObject ms=new ShareMasterObject();
					ms.setShareAccType(rs_trn.getString("ac_type"));
					ms.setShareNumber(rs_trn.getInt("ac_no"));
					ms.setShareStatus(rs_trn.getString("susp_ind"));
					int lst_trn_seq=rs_trn.getInt("trn_seq");
					lst_trn_seq++;
					System.out.println("lst_trn_seq"+lst_trn_seq);
					double lst_share_bal=rs_trn.getDouble("share_bal");
					System.out.println("Insert into ShareTrans....0000000000");
					if(trnno!=0)
						ps1=conn.prepareStatement("insert into ShareTransaction values(?,"+trnno+",?,?,'"+date+"','I',?,?,?,?,?,'D',?,?,?,?,?,null,null,'F','F',?,?,?,?,?,?)");
					else
						ps1=conn.prepareStatement("insert into ShareTransaction values(?,null,?,?,'"+date+"','I',?,?,?,?,?,'D',?,?,?,?,?,null,null,'F','F',?,?,?,?,?,?)");
					
					ps1.setString(1,ms.getShareAccType());
					ps1.setInt(2,ms.getShareNumber());
					ps1.setInt(3,lst_trn_seq);
					ps1.setDouble(4,netamt);
					ps1.setInt(5,voucher_no);
					StringTokenizer st=new StringTokenizer(div_dt,"/");
					st.nextToken();
					st.nextToken();
					String yr=st.nextToken();
					int pre_yr=Integer.parseInt(yr)-1;
					ps1.setString(6,String.valueOf(pre_yr).subSequence(2,4)+"-"+yr.subSequence(2,4)+" "+divacctype+" "+divaccno);
					//ps1.setString(6,divacctype+" "+divaccno);
					ps1.setString(7,"T");
					ps1.setString(8,utml);
					ps1.setString(9,"D");
					ps1.setString(10,ms.getShareStatus());
					ResultSet rs_master=stmt1.executeQuery("select mem_cat from ShareMaster where ac_type='"+actype+"' and ac_no="+acno+" ");
					System.out.println("Current Row 7 3333333333 = "+res.getRow());	
					rs_master.next();
					ms.setMemberCategory(rs_master.getInt("mem_cat"));
					System.out.println("Current Row 7 4444444444 = "+res.getRow());	
					Statement stmt_123=conn.createStatement();
					ResultSet rs_gl=stmt_123.executeQuery("select prm_gl_code,prm_gl_type from ShareParam where ac_type='"+actype+"' and mem_cat="+ms.getMemberCategory()+" and prm_ty='I'");
					System.out.println("Current Row 7 555555555 = "+res.getRow());	
					rs_gl.next();
					System.out.println("Current Row 7 6666666666666 = "+res.getRow());	
					String gl_code=rs_gl.getString("prm_gl_code");
					String gl_type=rs_gl.getString("prm_gl_type");
					System.out.println("gl_code:"+gl_code);
					System.out.println("gl_type:"+gl_type);
					//rs_gl.close();
					System.out.println("Current Row 7 7777777777777 = "+res.getRow());	
					ps1.setDouble(11,lst_share_bal);
					
					System.out.println("Current Row 8 = "+res.getRow());
					
					ps1.setInt(12,0);
					ps1.setInt(13,0);
					ps1.setString(14,rs_1.getString("de_user"));
					ps1.setString(15,rs_1.getString("de_tml"));
					ps1.setString(16,rs_1.getString("de_date"));
					ps1.setString(17,rs_1.getString("ve_user"));
					ps1.setString(18,rs_1.getString("ve_date"));
					ps1.setString(19,rs_1.getString("ve_tml"));
					System.out.println("Before Executing Share Transaction...........");
					ps1.executeUpdate();
					System.out.println("after Executing Share Transaction...........");	
					System.out.println("Insertedd into ShareTrans....0000000000");
					
					stmt1.executeUpdate("update ShareMaster set lst_trn_seq="+lst_trn_seq+" where ac_type='"+actype+"' and ac_no="+acno+" ");
					
					//GLPOsting
					
					GLTransObject trnobj=new GLTransObject();
					
					trnobj.setTrnDate(date);
					trnobj.setGLType(gl_type);
					
					/*if(sm.getShareStatus().equals("T"))
					 trnobj.setGLCode(sm.getSuspGLCode());				
					 else
					 trnobj.setGLCode(sm.getGLCode());
					 */
					
					trnobj.setGLCode(gl_code);
					trnobj.setTrnMode("T");
					trnobj.setAmount(netamt);
					trnobj.setCdind("D");
					//trnobj.setAccType(actype+" "+ms.getShareStatus());
					trnobj.setAccType(actype);
					trnobj.setAccNo(String.valueOf(acno));
					trnobj.setTrnType("I");
					trnobj.setTrnSeq(lst_trn_seq);
					trnobj.setVtml(utml);
					trnobj.setVid(uid);
					trnobj.setVDate(udate);
					System.out.println("Before writing into Share GL");
					storeGLTransaction(trnobj);
					System.out.println("after writing into Share GL");
					System.out.println("Current Row = "+res.getRow());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		finally{
			try{
				System.out.println("conn.to close");
				conn.close();
				System.out.println("conn.closed");
			}catch(Exception e){e.printStackTrace();}
		}	
		return ret;
	}	
	
	public int transfer(DividendObject[] update_div_transfer,String utml,String uid,String udate,int type,String date)throws RecordsNotFoundException
	{
		System.out.println("Type....:"+type);
		ResultSet rs=null;
		int ret=0;
		double divamt=0,ddnamt=0;		
		Connection conn=null;
		double double_total_amount=0;
		int lst_voucher_no=0;
		try{
			System.out.println("getting connection");
			conn=getConnection();
			Statement stmt=conn.createStatement();
			Statement cstmt=conn.createStatement();
			Statement stmt1=conn.createStatement();
			System.out.println("gottt connection");
			
			System.out.println("to execute query 1");
			
			//lst_rct_no-->VoucherNo. Generation
			lst_voucher_no = common_local.getModulesColumn("lst_rct_no",update_div_transfer[0].getSHType());//Added by karthi-->08/03/2006
			if(lst_voucher_no==0)
			{
				lst_voucher_no=lst_voucher_no = common_local.getModulesColumn("lst_rct_no",update_div_transfer[0].getSHType());//Added by karthi-->08/03/2006;
				
			}
			for(int i=0;i<update_div_transfer.length;i++)
			{
				System.out.println("div_ac_no...tranfser"+update_div_transfer[i].getDivAccNo());
				System.out.println("div_ac_type...tranfser"+update_div_transfer[i].getDivAccType());
				System.out.println("_ac_no...tranfser"+update_div_transfer[i].getSHNumber());
				System.out.println("_ac_type......tranfser"+update_div_transfer[i].getSHType());
				
				//rs=stmt.executeQuery("select ac_type,ac_no,div_ac_no,div_ac_ty,div_amt,ddn_amt,cv_paid from ShareDividend where div_ac_no="+update_div_transfer[i].getDivAccNo()+" and ac_no="+update_div_transfer[i].getSHNumber()+" and ac_type='"+update_div_transfer[i].getSHType()+"' and div_ac_ty='"+update_div_transfer[i].getDivAccType()+"'  and div_dt='"+update_div_transfer[i].getDivDate()+"' and cv_paid='F' ");
				rs=stmt.executeQuery("select ac_type,ac_no,div_ac_no,div_ac_ty,div_amt,ddn_amt,cv_paid,voucher_no from ShareDividend where ac_no="+update_div_transfer[i].getSHNumber()+" and ac_type='"+update_div_transfer[i].getSHType()+"' and cv_paid='F' ");
				System.out.println("executed query 1");
				rs.last();
				if(rs.getRow()==0)
				{	
					System.out.println("result set empty");
					throw new RecordsNotFoundException();
					
				}
				rs.beforeFirst();
				while(rs.next())
				{
					/*accno=rs.getInt("ac_type");
					 acctype=rs.getString("ac_no");
					 divaccno=rs.getInt("div_ac_no");
					 divacctype=rs.getString("div_ac_ty");*/
					divamt=rs.getDouble("div_amt");
					ddnamt=rs.getDouble("ddn_amt");
					double_total_amount=(divamt-ddnamt);
					System.out.println("inside while looooop");
					/*rs1 = cpstmt.executeQuery("select cl_bal,trn_seq from AccountTransaction where ac_no="+accno+" and ac_type='"+sbty+"' order by trn_seq desc limit 1");
					 rs1.next();
					 bal = rs1.getDouble(1);
					 //using different trn_seq get trn_seq from GenParam,de_tml,de_user,ve_tml,ve_user and update them..---
					  seqno = rs1.getInt(2)+1;
					  bal=bal+divamt-ddnamt;
					  rs1.close();*/
					if(divamt>ddnamt)
					{	
						System.out.println("divant>ddnamt");
						if(type==0)
						{
							System.out.println("Type...:"+type);
							//Added at the time of verification-->karthi
							/*	AccountTransObject at=new AccountTransObject(); 
							 //at=null;
							  //System.out.println("object at created"+sbty);
							   at.setAccType(update_div_transfer[i].getDivAccType());
							   at.setAccNo(update_div_transfer[i].getDivAccNo());
							   at.setTransType("R");
							   at.setTransAmount(divamt-ddnamt);
							   at.setTransMode("Dvd");
							   at.setTransSource(utml);
							   at.setTransDate(getSysDate());
							   at.setCdInd("C");
							   at.setChqDDNo(0);
							   at.setChqDDDate("");
							   String dt=update_div_transfer[i].getDivDate();
							   StringTokenizer st = new StringTokenizer(dt,"/");
							   String yr=null;
							   while(st.hasMoreTokens())
							   yr=st.nextToken();
							   System.out.println("Year is...:"+yr);
							   at.setTransNarr("Dvd. "+update_div_transfer[i].getSHNumber()+" yr."+yr);
							   //at.setTransNarr("Dvdnd&DDN  "+update_div_transfer[i].getSHNumber());
							    //at.setRef_No(0);
							     at.setRef_No(update_div_transfer[i].getVoucherNo());
							     at.setPayeeName("");
							     at.setCloseBal(divamt-ddnamt);
							     at.setLedgerPage(0);
							     at.uv.setUserTml(utml);
							     at.uv.setUserId(uid);
							     at.uv.setVerTml(utml);
							     at.uv.setVerId(uid);
							     if(update_div_transfer[i].getDivAccType()!=null)
							     at.setGLRefCode(Integer.parseInt(update_div_transfer[i].getDivAccType()));
							     else
							     at.setGLRefCode(0);
							     
							     System.out.println("commonremote b4");
							     //CommonHome commonhome=null;
							      // CommonRemote commonremote=null;
							       // commonremote=commonhome.create();
							        
							        ret=common_local.storeAccountTransaction(at);*/
							//cstmt.executeUpdate("update ShareDividend set cv_paid='T',pay_mode='T' where div_ac_no="+update_div_transfer[i].getDivAccNo()+" and ac_no="+update_div_transfer[i].getSHNumber()+" and ac_type='"+update_div_transfer[i].getSHType()+"' and div_ac_ty='"+update_div_transfer[i].getDivAccType()+"' and div_dt='"+update_div_transfer[i].getDivDate()+"'");
							//cstmt.executeUpdate("update ShareDividend set cv_paid='T',pay_mode='T',div_ac_no="+update_div_transfer[i].getDivAccNo()+",div_ac_ty='"+update_div_transfer[i].getDivAccType()+"' where ac_no="+update_div_transfer[i].getSHNumber()+" and ac_type='"+update_div_transfer[i].getSHType()+"' and div_dt='"+update_div_transfer[i].getDivDate()+"'");
							cstmt.executeUpdate("update ShareDividend set cv_paid='T',pay_mode='T',div_ac_no="+update_div_transfer[i].getDivAccNo()+",div_ac_ty='"+update_div_transfer[i].getDivAccType()+"',cv_no="+lst_voucher_no+",cv_dt='"+date+"',paydiv_dt='"+date+"' where ac_no="+update_div_transfer[i].getSHNumber()+" and ac_type='"+update_div_transfer[i].getSHType()+"' and div_dt='"+update_div_transfer[i].getDivDate()+"'");
						}
						else
						{
							System.out.println("**Type...:"+type);
							cstmt.executeUpdate("update ShareDividend set cv_paid='T',pay_mode='T',div_ac_no="+update_div_transfer[i].getDivAccNo()+",div_ac_ty='"+update_div_transfer[i].getDivAccType()+"' where ac_no="+update_div_transfer[i].getSHNumber()+" and ac_type='"+update_div_transfer[i].getSHType()+"' and div_dt='"+update_div_transfer[i].getDivDate()+"' and cv_no="+update_div_transfer[i].getVoucherNo()+" ");
						}
						System.out.println("commonremote after function cal");
					}
					
				}
				if(type==0)
				{
					System.out.println("Type...:"+type);
					
					/*int trnno=0;
					 //ResultSet rs_1=stmt1.executeQuery("select sh_trn_no from GenParam");
					  ResultSet rs_1=stmt1.executeQuery("select last_trf_scroll_no from Modules where modulecode='"+update_div_transfer[i].getSHType()+"'");
					  if(rs_1.next())
					  //trnno=rs_1.getInt("sh_trn_no");
					   trnno=rs_1.getInt("last_trf_scroll_no");
					   
					   System.out.println("trn no"+trnno);
					   trnno++;
					   System.out.println("trn no++"+trnno);		
					   //stmt1.executeUpdate("update GenParam set sh_trn_no="+trnno);
					    stmt1.executeUpdate("update Modules set last_trf_scroll_no="+trnno+" where modulecode='"+update_div_transfer[i].getSHType()+"'");
					    System.out.println("final trn no isss,,,,,"+trnno);*/
					
					//Karthi-->from
					/*Statement stmt_mult_by=conn.createStatement();
					 ResultSet rs_mult_by=null;
					 
					 ResultSet rs_master=stmt1.executeQuery("select mem_cat from ShareMaster where ac_type='"+update_div_transfer[i].getSHType()+"' and ac_no="+update_div_transfer[i].getSHNumber()+" ");
					 rs_master.next();
					 ms.setMemberCategory(rs_master.getInt("mem_cat"));
					 
					 ResultSet rs_gl=stmt.executeQuery("select prm_gl_code,prm_gl_type from ShareParam where ac_type='"+update_div_transfer[i].getSHType()+"' and mem_cat="+ms.getMemberCategory()+" and prm_ty='I'");
					 rs_gl.next();
					 
					 String gl_code=rs_gl.getString("prm_gl_code");
					 System.out.println("gl_code"+gl_code);
					 
					 String gl_type=rs_gl.getString("prm_gl_type");
					 System.out.println("gl_code"+gl_type);
					 
					 rs_gl.close();*///karthi-->to
					
					/* rs_mult_by=stmt_mult_by.executeQuery("select mult_by from GLPost where gl_code="+gl_code+" and ac_type='"+update_div_transfer[i].getSHType()+"' and trn_type='I' and cr_dr='D'");
					 
					 
					 rs_mult_by.next();
					 double mult_by=rs_mult_by.getInt("mult_by");
					 
					 System.out.println("mult_by"+mult_by);
					 rs_mult_by.close();
					 double current_share_balance=(double_total_amount*mult_by);*/
					/*double current_share_balance=double_total_amount;	
					 double share_balance=lst_share_bal-current_share_balance;
					 
					 System.out.println("current_share_balance"+current_share_balance);
					 System.out.println("share_balance"+share_balance);*/
					
					//GLPOsting
					
					//Karthi-->from
					/*GLTransObject trnobj=new GLTransObject();
					 
					 trnobj.setTrnDate(getSysDate());
					 trnobj.setGLType(gl_type);
					 if(sm.getShareStatus().equals("T"))
					 trnobj.setGLCode(sm.getSuspGLCode());				
					 else
					 trnobj.setGLCode(sm.getGLCode());
					 
					 
					 trnobj.setGLCode(gl_code);
					 
					 trnobj.setTrnMode("T");
					 
					 trnobj.setAmount(double_total_amount);
					 
					 
					 
					 trnobj.setCdind("D");
					 //trnobj.setAccType(update_div_transfer[i].getSHType()+" "+ms.getShareStatus());
					  trnobj.setAccType(update_div_transfer[i].getSHType());
					  
					  trnobj.setAccNo(String.valueOf(update_div_transfer[i].getSHNumber()));
					  
					  trnobj.setTrnType("I");
					  trnobj.setVtml(utml);
					  trnobj.setVid(uid);
					  trnobj.setVDate(common_remote.getSysDateTime());
					  System.out.println("Before writing into Share GL");
					  storeGLTransaction(trnobj);
					  System.out.println("after writing into Share GL");*///karthi-->to
					
					System.out.println("rs.to close");
					rs.close();	
					System.out.println("rs.closed");
				}
				else
				{
					System.out.println("**Type...:"+type);
					cstmt.executeUpdate("update ShareTransaction set trn_narr='"+update_div_transfer[i].getDivAccType()+" "+update_div_transfer[i].getDivAccNo()+"',trn_mode='T',trn_source='"+utml+"' where ac_type='"+update_div_transfer[i].getSHType()+"' and ac_no="+update_div_transfer[i].getSHNumber()+" and ref_no="+update_div_transfer[i].getVoucherNo()+" and trn_type='I'");
				}
				//This is added at the time of verification-->karthi
				/*if(type==0)
				 {
				 Statement stmt_mult_by=conn.createStatement();
				 ResultSet rs_mult_by=null;
				 
				 ResultSet rs_master=stmt1.executeQuery("select mem_cat from ShareMaster where ac_type='"+update_div_transfer[i].getSHType()+"' and ac_no="+update_div_transfer[i].getSHNumber()+" ");
				 rs_master.next();
				 //ms.setMemberCategory(rs_master.getInt("mem_cat"));
				  int mem_cat=rs_master.getInt("mem_cat");
				  
				  ResultSet rs_gl=stmt.executeQuery("select prm_gl_code,prm_gl_type from ShareParam where ac_type='"+update_div_transfer[i].getSHType()+"' and mem_cat="+mem_cat+" and prm_ty='I'");
				  rs_gl.next();
				  
				  String gl_code=rs_gl.getString("prm_gl_code");
				  System.out.println("gl_code"+gl_code);
				  
				  String gl_type=rs_gl.getString("prm_gl_type");
				  System.out.println("gl_code"+gl_type);
				  
				  rs_gl.close();
				  
				  GLTransObject trnobj=new GLTransObject();
				  
				  trnobj.setTrnDate(getSysDate());
				  trnobj.setGLType(gl_type);
				  // if(sm.getShareStatus().equals("T"))
				   //trnobj.setGLCode(sm.getSuspGLCode());				
				    //else
				     //trnobj.setGLCode(sm.getGLCode());
				      
				      trnobj.setGLCode(gl_code);
				      trnobj.setTrnMode("T");
				      trnobj.setAmount(double_total_amount);
				      trnobj.setCdind("D");
				      //trnobj.setAccType(update_div_transfer[i].getSHType()+" "+ms.getShareStatus());
				       trnobj.setAccType(update_div_transfer[i].getSHType());
				       trnobj.setAccNo(String.valueOf(update_div_transfer[i].getSHNumber()));
				       trnobj.setTrnType("I");
				       trnobj.setVtml(utml);
				       trnobj.setVid(uid);
				       trnobj.setVDate(common_remote.getSysDateTime());
				       System.out.println("Before writing into Share GL");
				       storeGLTransaction(trnobj);
				       System.out.println("after writing into Share GL");
				       }
				       */ 
			}	
			//return 1;
			return lst_voucher_no;
		}catch(Exception ex){
			ex.printStackTrace();
			sessionContext.setRollbackOnly();
			return 0;
		}
		finally{
			try{
				System.out.println("conn.to close");
				conn.close();
				System.out.println("conn.closed");
			}catch(Exception e){e.printStackTrace();}
		}	
		//return ret;
	}	
	public DividendObject[] main_warrant(String share_type,String utml,String uid)throws RecordsNotFoundException
	{
		System.out.println("utml  main warrant"+utml);
		System.out.println("uid  main warrant"+uid);
		ResultSet rs=null,rs1=null,rs2=null;
		PreparedStatement ps1=null;
		int norows=0;
		int j=0,acno=0;
		String actype=null;
		double divamt=0,ddnamt=0,netamt=0;
		DividendObject[] div_warrant = null;
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			Statement vstmt=conn.createStatement();
			Statement cstmt=conn.createStatement();
			Statement fstmt=conn.createStatement();
			rs2=fstmt.executeQuery("select div_amt,ddn_amt,ac_no from ShareDividend where ac_type='"+share_type+"' and cv_paid='F' and pay_mode='W' and wrt_prtd='F'");
			rs2.last();
			if(rs2.getRow()==0)
			{	
				throw new RecordsNotFoundException();
			}
			rs2.beforeFirst();

			while(rs2.next())	
			{	
				rs=vstmt.executeQuery("select lst_voucher_no from Modules where modulecode='"+share_type+"'");
				rs.next();
				int c=rs.getInt("lst_warrant_no");			
				System.out.println("last_warrant_noccc"+c);
				rs.close();
				int wrt_no=c+1;
				System.out.println("last_warrant_no..after increment"+wrt_no);
				cstmt.executeUpdate("update Modules set lst_voucher_no="+wrt_no+" where modulecode='"+share_type+"'");
				divamt=rs2.getFloat("div_amt");
				ddnamt=rs2.getFloat("ddn_amt");
				netamt=rs2.getFloat("div_amt")-rs2.getFloat("ddn_amt");
				int ac_no=rs2.getInt("ac_no");

				//cstmt.executeUpdate("update ShareDividend set pay_mode='W' where div_ac_no="+update_div_warrant[i].getDivAccNo()+" and ac_no="+update_div_warrant[i].getSHNumber()+" and ac_type='"+update_div_warrant[i].getSHType()+"' and div_ac_ty='"+update_div_warrant[i].getDivAccType()+"' and div_dt='"+update_div_warrant[i].getDivDate()+"'");
				cstmt.executeUpdate("update ShareDividend set wrt_prtd='T',wrt_dt=date_format(sysdate(),'%d/%m/%Y'),paydiv_dt=date_format(sysdate(),'%d/%m/%Y'),wrt_no="+wrt_no+",wrt_amt="+netamt+",ve_user='"+uid+"',ve_tml='"+utml+"',ve_date='"+getSysDate()+""+getSysTime()+"' where ac_type='"+share_type+"' and ac_no="+ac_no+" and pay_mode='W'");
				//cstmt.executeUpdate("update GenParam set lst_warrant_no="+--wrt_no+" ");

				rs1=stmt.executeQuery("select distinct sd.ac_type,sd.ac_no,sd.wrt_no,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,ca.address,ca.city,ca.pin,sd.div_amt,sd.ddn_amt,sd.div_dt,sd.cv_paid,sd.pay_mode,sd.wrt_dt,sm.share_val from ShareDividend sd,ShareMaster sm, CustomerMaster cm, CustomerAddr ca where sd.ac_type='"+share_type+"' and sd.pay_mode='W' and sd.wrt_prtd='T' and sd.wrt_dt is not null and sd.ac_no = sm.ac_no and sm.cid = cm.cid and cm.cid =ca.cid ");
				rs1.last();
				if(rs1.getRow()==0)
				{	
					throw new RecordsNotFoundException();
				}
				norows=rs1.getRow();
				div_warrant = new DividendObject[norows];
				rs1.beforeFirst();			
				while(rs1.next())				
				{
					actype=rs1.getString("sd.ac_type");
					acno=rs1.getInt("sd.ac_no");
					netamt=rs1.getFloat("sd.div_amt")-rs1.getFloat("sd.ddn_amt");
					div_warrant[j] = new DividendObject();	
					div_warrant[j].setSHType(rs1.getString("sd.ac_type"));
					div_warrant[j].setSHNumber(rs1.getInt("sd.ac_no"));
					div_warrant[j].setWarrantNo(rs1.getInt("sd.wrt_no"));
					div_warrant[j].setName(rs1.getString("name"));
					div_warrant[j].addr=new Address();
					div_warrant[j].addr.setAddress(rs1.getString("ca.address"));
					div_warrant[j].addr.setCity(rs1.getString("ca.city"));
					div_warrant[j].addr.setPin(rs1.getString("ca.pin"));
					div_warrant[j].setDivAmount(rs1.getFloat("sd.div_amt"));
					div_warrant[j].setDrfAmount(rs1.getFloat("sd.ddn_amt"));
					div_warrant[j].setWarrantDate(rs1.getString("sd.wrt_dt"));
					div_warrant[j].setShareValue(rs1.getDouble("sm.share_val"));
					j++;
					//}		


					Statement stmt1=conn.createStatement();
					int trnno=0;
					//ResultSet rs_1=stmt1.executeQuery("select sh_trn_no from GenParam");
					/*ResultSet rs_1=stmt1.executeQuery("select last_trf_scroll_no from Modules where modulecode='"+share_type+"'");
					 if(rs_1.next())
					 //trnno=rs_1.getInt("sh_trn_no");
					  trnno=rs_1.getInt("last_trf_scroll_no");

					  System.out.println("trn no"+trnno);
					  trnno++;
					  System.out.println("trn no++"+trnno);		
					  //stmt1.executeUpdate("update GenParam set sh_trn_no="+trnno);
					   stmt1.executeUpdate("update Modules set last_trf_scroll_no="+trnno+" where modulecode='"+share_type+"'");
					   System.out.println("final trn no isss,,,,,"+trnno);*/
					ResultSet rs_trn=stmt1.executeQuery("select * from ShareTransaction where ac_type='"+actype+"' and ac_no="+acno+" order by trn_seq desc limit 1");
					rs_trn.next();
					ShareMasterObject ms=new ShareMasterObject();
					ms.setShareAccType(rs_trn.getString("ac_type"));
					ms.setShareNumber(rs_trn.getInt("ac_no"));
					ms.setShareStatus(rs_trn.getString("susp_ind"));
					int lst_trn_seq=rs_trn.getInt("trn_seq");
					lst_trn_seq++;
					System.out.println("lst_trn_seq"+lst_trn_seq);
					double lst_share_bal=rs_trn.getDouble("share_bal");
					System.out.println("lst_share_bal"+lst_share_bal);

					ResultSet rs_1=stmt1.executeQuery("select wrt_no,voucher_no,de_tml,de_user,de_date,ve_user,ve_tml,ve_date from ShareDividend where ac_no="+ac_no+" and div_ac_ty='"+actype+"' and wrt_no="+wrt_no);	
					rs_1.next();
					if(String.valueOf(rs_1.getInt("voucher_no"))!=null)
						trnno=rs_1.getInt("voucher_no");
					if(trnno!=0)
						ps1=conn.prepareStatement("insert into ShareTransaction values(?,"+trnno+",?,?,date_format(sysdate(),'%d/%m/%Y'),'I',?,?,?,?,?,'D',?,?,?,?,?,null,null,'F','F',?,?,date_format(sysdate(),'%d/%m/%Y %H:%i:%s'),?,date_format(sysdate(),'%d/%m/%Y %H:%i:%s'),?)");
					else
						ps1=conn.prepareStatement("insert into ShareTransaction values(?,null,?,?,date_format(sysdate(),'%d/%m/%Y'),'I',?,?,?,?,?,'D',?,?,?,?,?,null,null,'F','F',?,?,?,?,?,?)");

					ps1.setString(1,actype);
					ps1.setInt(2,acno);
					ps1.setInt(3,lst_trn_seq);
					//netamt=rs_trn.getFloat("div_amt")-rs_trn.getFloat("ddn_amt");
					System.out.println("netamt"+netamt);
					ps1.setDouble(4,netamt);
					ps1.setInt(5,rs_1.getInt("wrt_no"));
					StringTokenizer st=new StringTokenizer(div_warrant[j].getDivDate(),"/");
					st.nextToken();
					st.nextToken();
					String yr=st.nextToken();
					int pre_yr=Integer.parseInt(yr)-1;
					ps1.setString(6,String.valueOf(pre_yr).subSequence(2,4)+"-"+yr.subSequence(2,4)+" "+"Prnt.Wrnt "+rs_1.getInt("wrt_no"));
					//ps1.setString(6,null);
					ps1.setString(7,"W");
					ps1.setString(8,utml);
					ps1.setString(9,"D");
					ps1.setString(10,ms.getShareStatus());
					ps1.setDouble(11,lst_share_bal);
					ps1.setInt(12,0);
					ps1.setInt(13,0);
					ps1.setString(14,rs_1.getString("de_user"));
					ps1.setString(15,rs_1.getString("de_tml"));
					ps1.setString(16,rs_1.getString("de_date"));
					ps1.setString(17,rs_1.getString("ve_user"));
					ps1.setString(18,rs_1.getString("ve_date"));
					ps1.setString(19,rs_1.getString("ve_tml"));
					ps1.executeUpdate();

					ResultSet rs_master=stmt1.executeQuery("select mem_cat from ShareMaster where ac_type='"+actype+"' and ac_no="+acno+" ");
					rs_master.next();
					ms.setMemberCategory(rs_master.getInt("mem_cat"));
					Statement stmt_gl=conn.createStatement();
					ResultSet rs_gl=stmt_gl.executeQuery("select prm_gl_code,prm_gl_type from ShareParam where ac_type='"+actype+"' and mem_cat="+ms.getMemberCategory()+" and prm_ty='I'");
					rs_gl.next();
					String gl_code=rs_gl.getString("prm_gl_code");
					System.out.println("gl_code"+gl_code);
					String gl_type=rs_gl.getString("prm_gl_type");
					System.out.println("gl_type"+gl_type);
					rs_gl.close();
					stmt1.executeUpdate("update ShareMaster set lst_trn_seq="+lst_trn_seq+" where ac_type='"+actype+"' and ac_no="+acno+" ");

					//GLPOsting

					GLTransObject trnobj=new GLTransObject();

					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(gl_type);
					/*if(sm.getShareStatus().equals("T"))
					 trnobj.setGLCode(sm.getSuspGLCode());				
					 else
					 trnobj.setGLCode(sm.getGLCode());
					 */

					trnobj.setGLCode(gl_code);
					trnobj.setTrnMode("W");
					trnobj.setAmount(netamt);
					trnobj.setCdind("D");
					//trnobj.setAccType(actype+" "+ms.getShareStatus());
					trnobj.setAccType(actype);				
					trnobj.setAccNo(String.valueOf(acno));
					trnobj.setTrnType("I");
					trnobj.setTrnSeq(lst_trn_seq);
					trnobj.setRefNo(wrt_no);
					trnobj.setVtml(utml);
					trnobj.setVid(uid);
					trnobj.setVDate(getSysDate()+""+getSysTime());
					storeGLTransaction(trnobj);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}	
		return div_warrant;

	}
	
	public DividendObject[] print_warrant(DividendObject[] update_div_warrant,String utml,String uid,int type)throws RecordsNotFoundException
	{
		ResultSet rs=null,rs1=null,rs2=null;
		int c=0,wrt_no=0;
		double divamt,ddnamt,netamt=0;
		int j=0;
		DividendObject[] div_warrant = null;
		Connection conn=null;
		System.out.println("Type...:"+type);
		
		try{
			System.out.println("getting connection");
			conn=getConnection();
			Statement stmt=conn.createStatement();
			Statement cstmt=conn.createStatement();
			Statement vstmt=conn.createStatement();
			Statement fstmt=conn.createStatement();
			Statement stmt1=conn.createStatement();
			System.out.println("gottt connection");
			System.out.println("to execute query 1");
			
			//rs=vstmt.executeQuery("select lst_warrant_no from GenParam");
			rs=vstmt.executeQuery("select lst_voucher_no from Modules where modulecode='"+update_div_warrant[0].getSHType()+"'");
			rs.next();
			c=rs.getInt("lst_voucher_no");
			wrt_no=c+1;
			//vstmt.executeUpdate("update Modules set lst_voucher_no="+wrt_no+"where modulecode='"+update_div_warrant[0].getSHType());
			
			System.out.println("last_warrant_noccc"+c);
			rs.close();
			System.out.println("rs1 clossssed");
			
			rs.close();
			
			div_warrant = new DividendObject[update_div_warrant.length];
			for(int i=0;i<update_div_warrant.length;i++)
			{
				System.out.println("_ac_no...tranfser"+update_div_warrant[i].getSHNumber());
				System.out.println("_ac_type......tranfser"+update_div_warrant[i].getSHType());
				System.out.println("div_date.....tranfser"+update_div_warrant[i].getDivDate());
				rs2=fstmt.executeQuery("select div_amt,ddn_amt from ShareDividend where ac_no="+update_div_warrant[i].getSHNumber()+" and ac_type='"+update_div_warrant[i].getSHType()+"' and div_dt='"+update_div_warrant[i].getDivDate()+"' and cv_paid='F' and pay_mode='C' and wrt_prtd='F'");
				rs2.last();
				if(rs2.getRow()==0)
				{	
					System.out.println("result set empty");
					throw new RecordsNotFoundException();
				}
				rs2.beforeFirst();
				while(rs2.next())	
				{	
					divamt=rs2.getFloat("div_amt");
					ddnamt=rs2.getFloat("ddn_amt");
					netamt=divamt-ddnamt;
				}	
				//cstmt.executeUpdate("update ShareDividend set pay_mode='W' where div_ac_no="+update_div_warrant[i].getDivAccNo()+" and ac_no="+update_div_warrant[i].getSHNumber()+" and ac_type='"+update_div_warrant[i].getSHType()+"' and div_ac_ty='"+update_div_warrant[i].getDivAccType()+"' and div_dt='"+update_div_warrant[i].getDivDate()+"'");
				cstmt.executeUpdate("update ShareDividend set wrt_prtd='T',pay_mode='W',wrt_dt=date_format(sysdate(),'%d/%m/%Y'),paydiv_dt=date_format(sysdate(),'%d/%m/%Y'),wrt_no="+wrt_no+",wrt_amt="+netamt+" where ac_no="+update_div_warrant[i].getSHNumber()+" and ac_type='"+update_div_warrant[i].getSHType()+"' and div_dt='"+update_div_warrant[i].getDivDate()+"'");
				//cstmt.executeUpdate("update GenParam set lst_warrant_no="+wrt_no+" ");
				cstmt.executeUpdate("update Modules set lst_voucher_no="+wrt_no+" where modulecode='"+update_div_warrant[i].getSHType()+"'");//lst_voucher_no==>print warrant no. generation
				rs2.close();
				System.out.println("length....."+update_div_warrant.length);
				rs1=stmt.executeQuery("select sd.ac_type,sd.ac_no,sd.wrt_no,sd.voucher_no,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,ca.address,ca.city,ca.pin,sd.div_amt,sd.ddn_amt,sd.div_dt,sd.cv_paid,sd.pay_mode,sd.wrt_dt,sm.share_val from ShareDividend sd,ShareMaster sm, CustomerMaster cm, CustomerAddr ca where sd.ac_no="+update_div_warrant[i].getSHNumber()+" and sd.ac_type='"+update_div_warrant[i].getSHType()+"' and sd.div_dt='"+update_div_warrant[i].getDivDate()+"' and sd.ac_no = sm.ac_no and sm.cid = cm.cid and cm.cid = ca.cid ");
				rs1.last();
				if(rs1.getRow()==0)
				{	
					System.out.println("result set empty");
					throw new RecordsNotFoundException();
					
				}
				rs1.beforeFirst();
				if(rs1.next())
				{
					wrt_no=rs1.getInt("sd.wrt_no");
					div_warrant[j] = new DividendObject();	
					div_warrant[j].setSHType(rs1.getString("sd.ac_type"));
					div_warrant[j].setSHNumber(rs1.getInt("sd.ac_no"));
					div_warrant[j].setVoucherNo(rs1.getInt("sd.voucher_no"));
					div_warrant[j].setWarrantNo(rs1.getInt("sd.wrt_no"));
					div_warrant[j].setName(rs1.getString("name"));
					System.out.println("name"+div_warrant[i].getName());
					div_warrant[j].addr=new Address();
					div_warrant[j].addr.setAddress(rs1.getString("ca.address"));
					div_warrant[j].addr.setCity(rs1.getString("ca.city"));
					div_warrant[j].addr.setPin(rs1.getString("ca.pin"));
					div_warrant[j].setDivAmount(rs1.getFloat("sd.div_amt"));
					div_warrant[j].setDrfAmount(rs1.getFloat("sd.ddn_amt"));
					div_warrant[j].setWarrantDate(rs1.getString("sd.wrt_dt"));
					div_warrant[j].setShareValue(rs1.getDouble("sm.share_val"));
					System.out.println("jjjjjjjjjjjjjinnnnn"+j);
					j++;
				}		
				
				//norows++;
				System.out.println("jjjjjjjjjjjjjouttt"+j);
				if(type==0)
				{
					/*int trnno=0;
					 //ResultSet rs_1=stmt1.executeQuery("select sh_trn_no from GenParam");
					  ResultSet rs_1=stmt1.executeQuery("select last_trf_scroll_no from Modules where modulecode='"+update_div_warrant[i].getSHType()+"'");
					  if(rs_1.next())
					  //trnno=rs_1.getInt("sh_trn_no");
					   trnno=rs_1.getInt("last_trf_scroll_no");
					   
					   System.out.println("trn no"+trnno);
					   trnno++;
					   System.out.println("trn no++"+trnno);		
					   //stmt1.executeUpdate("update GenParam set sh_trn_no="+trnno);
					    stmt1.executeUpdate("update Modules set last_trf_scroll_no="+trnno+" where modulecode='"+update_div_warrant[i].getSHType()+"'");
					    System.out.println("final trn no isss,,,,,"+trnno);*/
					
					/* rs_mult_by=stmt_mult_by.executeQuery("select mult_by from GLPost where gl_code="+gl_code+" and ac_type='"+update_div_warrant[i].getSHType()+"' and trn_type='I' and cr_dr='D'");
					 rs_mult_by.next();
					 double mult_by=rs_mult_by.getInt("mult_by");
					 
					 System.out.println("mult_by"+mult_by);
					 rs_mult_by.close();
					 double current_share_balance=(netamt*mult_by);*/
					
					
				}
				else
					stmt1.executeUpdate("update ShareTransaction set ref_no="+wrt_no+",trn_narr=null,trn_mode='W',trn_source='"+utml+"' where ac_type='"+update_div_warrant[i].getSHType()+"' and ac_no="+update_div_warrant[i].getSHNumber()+" and ref_no="+update_div_warrant[i].getVoucherNo()+" and trn_type='I' ");
				
				
				if(type==0)
				{
					Statement stmt_mult_by=conn.createStatement();
					ResultSet rs_mult_by=null;
					
					ResultSet rs_master=stmt1.executeQuery("select mem_cat from ShareMaster where ac_type='"+update_div_warrant[i].getSHType()+"' and ac_no="+update_div_warrant[i].getSHNumber()+" ");
					rs_master.next();
					//ms.setMemberCategory(rs_master.getInt("mem_cat"));
					int mem_cat=rs_master.getInt("mem_cat");
					
					ResultSet rs_gl=stmt.executeQuery("select prm_gl_code,prm_gl_type from ShareParam where ac_type='"+update_div_warrant[i].getSHType()+"' and mem_cat="+mem_cat+" and prm_ty='I'");
					rs_gl.next();
					
					String gl_code=rs_gl.getString("prm_gl_code");
					System.out.println("gl_code"+gl_code);
					String gl_type=rs_gl.getString("prm_gl_type");
					System.out.println("gl_type"+gl_type);
					rs_gl.close();
					
					
					//GLPOsting
					GLTransObject trnobj=new GLTransObject();
					
					trnobj.setTrnDate(getSysDate());
					trnobj.setGLType(gl_type);
					/*if(sm.getShareStatus().equals("T"))
					 trnobj.setGLCode(sm.getSuspGLCode());				
					 else
					 trnobj.setGLCode(sm.getGLCode());*/
					trnobj.setGLCode(gl_code);
					trnobj.setTrnMode("W");
					trnobj.setAmount(netamt);
					trnobj.setCdind("D");
					//trnobj.setAccType(update_div_warrant[i].getSHType()+" "+ms.getShareStatus());
					trnobj.setAccType(update_div_warrant[i].getSHType());
					trnobj.setAccNo(String.valueOf(update_div_warrant[i].getSHNumber()));
					trnobj.setTrnType("I");
					trnobj.setRefNo(wrt_no);
					trnobj.setVtml(utml);
					trnobj.setVid(uid);
					trnobj.setVDate(getSysDate()+""+getSysTime());
					storeGLTransaction(trnobj);
				}
			}
			
			System.out.println("jjjjjjjjjjjjjfinallllll"+j);
			for(int i=0;i<div_warrant.length;i++)
			{
				System.out.println("name"+div_warrant[i].getName());
			}
			rs1.close();	
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		finally{
			try{
				System.out.println("conn.to close");
				conn.close();
				System.out.println("conn.closed");
			}catch(Exception e){e.printStackTrace();}
		}	
		return div_warrant;
	}	
	/*public int transfer(int shno,String sbty,int sbno)throws RemoteException
	 {
	 
	 ResultSet rs,rs1;
	 int ret=0,norows=0;
	 double divamt,ddnamt,bal;
	 int acno,seqno;
	 Connection conn=null;
	 
	 try{
	 conn=getConnection();
	 Statement stmt=conn.createStatement();
	 Statement cpstmt=conn.createStatement();
	 if(sbno==-1)
	 rs = stmt.executeQuery("select div_amt,ddn_amt,div_ac_ty,div_ac_no,ac_no from ShareDividend where pay_mode='T' and paydiv_dt is null");
	 else
	 rs = stmt.executeQuery("select div_amt,ddn_amt,div_ac_ty,div_ac_no,ac_no from ShareDividend where ac_no="+shno+" and paydiv_dt is null");		
	 
	 while(rs.next())
	 {
	 
	 divamt=rs.getDouble(1);
	 ddnamt=rs.getDouble(2);
	 acno=sbno;
	 if(sbno==-1)
	 {
	 sbty=rs.getString(3);
	 acno=rs.getInt(4);
	 shno=rs.getInt(5);
	 }
	 
	 rs1 = cpstmt.executeQuery("select cl_bal,trn_seq from AccountTransaction where ac_no="+acno+" and ac_type='"+sbty+"' order by trn_seq desc limit 1");
	 rs1.next();
	 bal = rs1.getDouble(1);
	 //using different trn_seq get trn_seq from GenParam,de_tml,de_user,ve_tml,ve_user and update them..---
	  seqno = rs1.getInt(2)+1;
	  bal=bal+divamt-ddnamt;
	  rs1.close();
	  if(divamt>ddnamt)
	  {					
	  ret=stmt.executeUpdate("insert into AccountTransaction(ac_type,ac_no,trn_date,trn_type,trn_amt,trn_mode,trn_source,cd_ind,trn_narr,cl_bal,trn_seq,de_date)values('"+sbty+"',"+acno+",date_format(sysdate(),'%d/%m/%Y'),'R',"+(divamt-ddnamt)+",'Dvd','linz','C','Dvdnd&DDN',"+bal+","+seqno+",date_format(sysdate(),'%d/%m/%Y %H:%i:%s'))");
	  stmt.executeUpdate("Update ShareDividend set paydiv_dt=date_format(sysdate(),'%d/%m/%Y') where ac_no="+shno);
	  
	  ResultSet rs2=cpstmt.executeQuery("select gl_code from GLKeyParam where ac_type=1999005 and code=1");
	  String glcode="";
	  if(rs1.next())
	  glcode=rs1.getString("gl_code");
	  rs1.close();
	  
	  rs1=cpstmt.executeQuery("select mult_by from GLPost where ac_type=1999005 and gl_code="+glcode+" and trn_type='R' and cr_dr='D'");
	  rs1.next();
	  GLTransObject trnobj=new GLTransObject();
	  //debiting amt in unclaimed gl
	   trnobj.setTrnDate(getSysDate());
	   trnobj.setGLType("GL");
	   trnobj.setGLCode(glcode);
	   trnobj.setTrnMode("T");
	   trnobj.setAmount(divamt*rs1.getInt(1));
	   trnobj.setCdind("D");
	   trnobj.setAccType(sbty);
	   trnobj.setAccNo(String.valueOf(acno));
	   trnobj.setTrnSeq(seqno);
	   trnobj.setTrnType("P");
	   trnobj.setVtml("EDP1");
	   trnobj.setVid("Ver");
	   storeGLTransaction(trnobj);
	   
	   String code="1002003";
	   rs1=cpstmt.executeQuery("select gl_code from GLKeyParam,AccountMaster where ac_type='"+sbty+"' and ac_no="+acno+" and GLKeyParam.ac_type="+code+" and (AccountMaster.ac_type+1)=code");
	   if(rs1.next())
	   glcode=rs1.getString("gl_code");
	   rs1.close();
	   
	   rs1=stmt.executeQuery("select mult_by from GLPost where ac_type="+code+" and gl_code="+glcode+" and trn_type='R' and cr_dr='C'");
	   //credit to that personl account
	    trnobj.setGLCode(glcode);
	    trnobj.setTrnMode("T");
	    trnobj.setCdind("C");
	    trnobj.setAmount(ddnamt);
	    trnobj.setTrnType("D");
	    trnobj.setVtml("EDP1");
	    trnobj.setVid("Ver");
	    storeGLTransaction(trnobj);
	    
	    rs2=cpstmt.executeQuery("select gl_code from GLKeyParam where ac_type=1999006 and code=1");
	    if(rs1.next())
	    glcode=rs1.getString("gl_code");
	    rs1.close();
	    
	    rs1=cpstmt.executeQuery("select mult_by from GLPost where ac_type=1999006 and gl_code="+glcode+" and trn_type='R' and cr_dr='D'");
	    rs1.next();
	    //credit to the DRF
	     trnobj.setGLCode(glcode);
	     trnobj.setTrnMode("T");
	     trnobj.setCdind("C");
	     trnobj.setTrnType("D");
	     trnobj.setVtml("EDP1");
	     trnobj.setVid("Ver");
	     storeGLTransaction(trnobj);	
	     }
	     }
	     rs.close();
	     
	     }catch(Exception e){e.printStackTrace();}
	     finally{
	     try{
	     conn.close();
	     }catch(Exception e){e.printStackTrace();}
	     }		
	     
	     return ret;
	     }*/
	
	public DividendRateObject retrieve(int acno)
	{
		DividendRateObject dvrate=new DividendRateObject();
		ResultSet rs;
		Connection conn=null;
		
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			
			rs = stmt.executeQuery("select concat(cm.fname,' ',cm.mname,' ',cm.lname)as name,(sd.div_amt-sd.ddn_amt) as amt from CustomerMaster cm,ShareDividend sd,ShareMaster sm where sm.ac_no="+acno+" and sd.ac_no="+acno+" and sm.cid=cm.cid and sd.paydiv_dt is null");
			rs.next();
			dvrate.setFlag(1);
			dvrate.setName(rs.getString(1));
			dvrate.setAmount(rs.getFloat(2));
			rs.close();
		}catch(Exception e){e.printStackTrace();dvrate.setFlag(2);}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}		
		return dvrate;
	}
	
	public int cash(DividendObject[] update_div_share,String utml,String uid,String udate,String date)throws RecordsNotFoundException
	{
		System.out.println("*******DIV Payment BEan");
		System.out.println(update_div_share[0].getSHNumber());
		System.out.println(update_div_share[0].getSHType());
		System.out.println(update_div_share[0].getDivDate());
		System.out.println(update_div_share[0].getDivAmount());
		System.out.println(utml);
		System.out.println(uid);
		System.out.println(udate);
		System.out.println(date);
		System.out.println(update_div_share.length);
		System.out.println("*******End");
		int lst_voucher_no=0;
		Connection conn=null;
		ResultSet rs=null;
		double double_total_amount=0;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			lst_voucher_no = common_local.getModulesColumn("lst_rct_no",update_div_share[0].getSHType());
			if(lst_voucher_no==0)
			{
				lst_voucher_no = common_local.getModulesColumn("lst_rct_no",update_div_share[0].getSHType());
			}
			
			for(int i=0;i<update_div_share.length;i++)
			{
				System.out.println("The lenght is "+update_div_share.length+"Sh no"+update_div_share[i].getSHNumber()+" Sh type"+update_div_share[i].getSHType());
				rs=stmt.executeQuery("select (div_amt-ddn_amt) as amt from ShareDividend where ac_no="+update_div_share[i].getSHNumber()+" and ac_type='"+update_div_share[i].getSHType()+"' and cv_paid='F' and pay_mode='C' and cv_dt is null");
				//c=15;
				rs.last();
				if(rs.getRow()==0)
				{
					
					throw new RecordsNotFoundException();
				}
				rs.beforeFirst();
				while(rs.next())
				{
					double_total_amount=rs.getDouble("amt");
					//String date=rs.getString("cv_dt");
					System.out.println("amt "+double_total_amount);
				}
				//stmt.executeUpdate("update ShareDividend set cv_dt=date_format(sysdate(),'%d/%m/%Y'),cv_no="+lst_voucher_no+" where ac_no="+update_div_share[i].getSHNumber()+" and ac_type='"+update_div_share[i].getSHType()+"' and div_dt='"+update_div_share[i].getDivDate()+"'");
				stmt.executeUpdate("update ShareDividend set cv_ty='I',cv_dt='"+date+"',paydiv_dt='"+date+"',cv_no="+lst_voucher_no+" where ac_no="+update_div_share[i].getSHNumber()+" and ac_type='"+update_div_share[i].getSHType()+"' and div_dt='"+update_div_share[i].getDivDate()+"'");
				
				/*Statement stmt1=conn.createStatement();
				 int trnno=0;
				 //ResultSet rs_1=stmt1.executeQuery("select sh_trn_no from GenParam");
				  ResultSet rs_1=stmt1.executeQuery("select last_trf_scroll_no from Modules where modulecode='"+update_div_share[i].getSHType()+"'");
				  if(rs_1.next())
				  //trnno=rs_1.getInt("sh_trn_no");
				   trnno=rs_1.getInt("last_trf_scroll_no");
				   
				   System.out.println("trn no"+trnno);
				   trnno++;
				   System.out.println("trn no++"+trnno);		
				   //stmt1.executeUpdate("update GenParam set sh_trn_no="+trnno);
				    stmt1.executeUpdate("update Modules set last_trf_scroll_no="+trnno+" where modulecode='"+update_div_share[i].getSHType()+"");
				    System.out.println("final trn no isss,,,,,"+trnno);*/
				
				
				//Karthi-->These are addedat the time of verification
				/* ResultSet rs_master=stmt1.executeQuery("select mem_cat from ShareMaster where ac_type='"+update_div_share[i].getSHType()+"' and ac_no="+update_div_share[i].getSHNumber()+" ");
				 rs_master.next();
				 ms.setMemberCategory(rs_master.getInt("mem_cat"));
				 
				 ResultSet rs_gl=stmt.executeQuery("select prm_gl_code,prm_gl_type from ShareParam where ac_type='"+update_div_share[i].getSHType()+"' and mem_cat="+ms.getMemberCategory()+" and prm_ty='I'");
				 rs_gl.next();
				 
				 String gl_code=rs_gl.getString("prm_gl_code");
				 String gl_type=rs_gl.getString("prm_gl_type");
				 System.out.println("gl_code"+gl_code);
				 rs_gl.close();*/
				
				/*rs_mult_by=stmt_mult_by.executeQuery("select mult_by from GLPost where gl_code="+gl_code+" and ac_type='"+update_div_share[i].getSHType()+"' and trn_type='I' and cr_dr='C'");
				 
				 
				 rs_mult_by.next();
				 double mult_by=rs_mult_by.getInt("mult_by");
				 
				 System.out.println("mult_by"+mult_by);
				 rs_mult_by.close();*///Karthi-->For Dividend Payment,this one is not necessary
				//double current_share_balance=(double_total_amount*mult_by);
				
				
				//GLPOsting
//				Karthi-->These are addedat the time of verification
				/*GLTransObject trnobj=new GLTransObject();
				 
				 trnobj.setTrnDate(getSysDate());
				 trnobj.setGLType(gl_type);
				 if(sm.getShareStatus().equals("T"))
				 trnobj.setGLCode(sm.getSuspGLCode());				
				 else
				 trnobj.setGLCode(sm.getGLCode());
				 
				 
				 trnobj.setGLCode(gl_code);
				 
				 trnobj.setTrnMode("C");
				 
				 trnobj.setAmount(double_total_amount);
				 
				 
				 trnobj.setCdind("D");
				 //trnobj.setAccType(update_div_share[i].getSHType()+" "+ms.getShareStatus());
				  trnobj.setAccType(update_div_share[i].getSHType());
				  
				  trnobj.setAccNo(String.valueOf(update_div_share[i].getSHNumber()));
				  
				  trnobj.setTrnType("I");
				  trnobj.setTrnSeq(lst_trn_seq);
				  trnobj.setRefNo(lst_voucher_no);
				  trnobj.setVtml(utml);
				  trnobj.setVid(uid);
				  trnobj.setVDate(common_remote.getSysDateTime());
				  System.out.println("Before writing into Share GL");
				  storeGLTransaction(trnobj);
				  System.out.println("after writing into Share GL");*/
				
				
				
				//After Verification only we can enter into Daycash Table
				/*Statement stmt_name=conn.createStatement();
				 ResultSet rs_name=null;
				 rs_name=stmt_name.executeQuery("select distinct sm.cid,sm.ac_no,sm.ac_type,concat_ws('',cm.fname,cm.mname,cm.lname) as name from ShareMaster sm,CustomerMaster cm where sm.ac_no="+update_div_share[i].getSHNumber()+" and cm.cid=sm.cid");
				 rs_name.next();
				 System.out.println("Name : "+rs_name.getString("name"));
				 PreparedStatement pstmt_insert= conn.prepareStatement("insert into DayCash (name,scroll_no,trn_type,trn_date,ac_type,ac_no,csh_amt,cd_ind,vch_type,trn_seq,attached,vch_no,de_tml,de_user,de_date,ve_user,ve_tml,ve_date) values (?,0,'P',?,?,?,?,'C','I',?,'F',?,?,?,?,?,?,?)");
				 pstmt_insert.setString(1,rs_name.getString("name"));
				 System.out.println("Name : "+rs_name.getString("name"));
				 pstmt_insert.setString(2,common_local.getSysDate());
				 pstmt_insert.setString(3,update_div_share[i].getSHType());
				 pstmt_insert.setInt(4,update_div_share[i].getSHNumber());
				 pstmt_insert.setDouble(5,double_total_amount);
				 pstmt_insert.setInt(6,lst_trn_seq);
				 System.out.println("Tran.Seq : "+lst_trn_seq);
				 pstmt_insert.setInt(7,lst_voucher_no);
				 pstmt_insert.setString(8,utml);
				 pstmt_insert.setString(9,uid);
				 pstmt_insert.setString(10,common_local.getSysDateTime());
				 pstmt_insert.setString(11,uid);
				 pstmt_insert.setString(12,utml);
				 pstmt_insert.setString(13,common_remote.getSysDateTime());
				 //pstmt_insert.setString(9,update_div_share[0].getVoucherType());
				  pstmt_insert.executeUpdate();*/
				
				//rs_mult_by=stmt_mult_by.executeQuery("select mult_by,gp.gl_code,gk.gl_type from GLPost gp,GLKeyParam gk where gp.ac_type='1019001' and gk.ac_type='1019001' and trn_type='R' and cr_dr='C'");
//				Karthi-->These are addedat the time of verification
				/*rs_mult_by=stmt_mult_by.executeQuery("select gl_type,gl_code from GLKeyParam where ac_type='1019001'");
				 
				 rs_mult_by.next();
				 gl_type=rs_mult_by.getString("gl_type");
				 gl_code=rs_mult_by.getString("gl_code");
				 rs_mult_by.close();
				 //Store in GLTransaction.
				  
				  
				  
				  trnobj.setAmount(double_total_amount);
				  
				  trnobj.setCdind("C");
				  trnobj.setAccNo("0");
				  trnobj.setAccType("1019001");
				  trnobj.setTrnSeq(lst_trn_seq);
				  trnobj.setGLCode(gl_code);
				  trnobj.setGLType(gl_type);
				  trnobj.setTrnSeq(0);
				  storeGLTransaction(trnobj);*/
			}
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}		
		
		return lst_voucher_no;
	}
	
	public int getYearEnd()
	{
		Connection conn=null;
		
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("select month from QtrDefinition where yr_defn='T'");
			rs.next();
			return rs.getInt(1);
		}catch(Exception e){e.printStackTrace();}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}		
		
		return 0;
	}
	
	public int storeDividendData(String shr_type,int shno,float damount,String date,String paymode,int accno,String acctype,String uid,String utml)
	{
		int n=-1;
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			
			ResultSet rs=null;
			rs=stmt.executeQuery("select ac_no from ShareDividend where ac_no="+shno+" and ac_type='"+shr_type+"' and div_dt='"+date+"'");
			if(rs.next())
				n=0;
			else
			{
				//n=stmt.executeUpdate("insert into ShareDividend(ac_type,ac_no,div_amt,div_dt,pay_mode)values("+1001001+","+mno+","+damount+",'"+date+"','"+paymode+"')");//Karthi
				pstmt=conn.prepareStatement("insert into ShareDividend(ac_type,ac_no,div_dt,div_amt,ddn_amt,cd_ind,wrt_prtd,pay_mode,cv_paid,de_tml,de_user,de_date,ve_tml,ve_user,ve_date) values(?,?,?,?,0,'C','F',?,'F',?,?,?,?,?,?)");
				pstmt.setString(1,shr_type);
				pstmt.setInt(2,shno);
				pstmt.setString(3,date);
				pstmt.setFloat(4,damount);
				if(paymode.equalsIgnoreCase("Cash"))
					pstmt.setString(5,"C");
				else if(paymode.equalsIgnoreCase("Transfer"))
					pstmt.setString(5,"T");
				else if(paymode.equalsIgnoreCase("PayOrder"))	
					pstmt.setString(5,"PO");
				
				pstmt.setString(6,utml);
				pstmt.setString(7,uid);
				pstmt.setString(8,getSysDate()+""+getSysTime());
				pstmt.setString(9,utml);
				pstmt.setString(10,uid);
				pstmt.setString(11,getSysDate()+""+getSysTime());
				n=pstmt.executeUpdate();
			}
			
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}		
		
		return n;
	}
	
	public DividendObject[] RetrieveUnPaid(int branchcode,String date,int startno,int endno,String string_query) throws DateFormatException
	{
		int norows=0,divlmt=0;
		DividendObject[] div = null;
		StringTokenizer token = new StringTokenizer(date,"-");
		int year = Integer.parseInt(token.nextToken());
		String month = token.nextToken();
		String day = token.nextToken();
		String fromdate;
		
		Connection conn=null;
		
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			
			ResultSet rs = null;
			//rs= stmt.executeQuery("select div_lmt from GenParam");
			rs= stmt.executeQuery("select inspection_period from Modules where modulecode='1001001'");
			rs.next();
			divlmt=rs.getInt(1);
			fromdate =String.valueOf(year-divlmt)+"-"+month+"-"+day;
			String str ="concat(right(sd.div_dt,4),'-',mid(sd.div_dt,locate('/',sd.div_dt)+1,(locate('/',sd.div_dt,4)-locate('/',sd.div_dt)-1)),'-',left(sd.div_dt,locate('/',sd.div_dt)-1))";
			
			if(string_query.equals(""))
				//rs = stmt.executeQuery("select sd.ac_no,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name ,sd.div_dt,sd.div_amt from ShareDividend sd,ShareMaster sm, CustomerMaster cm where sd.cv_paid = 'F' and sm.pay_mode = 'C' and sm.br_code = "+branchcode+" and sd.ac_no BETWEEN "+startno+" and "+endno+" and "+str+" BETWEEN '"+fromdate+"' and '"+date+"' and sd.ac_no = sm.ac_no and sm.cid = cm.cid order by sd.ac_no ");
				rs = stmt.executeQuery("select sd.ac_no,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name ,sd.div_dt,sd.div_amt from ShareDividend sd,ShareMaster sm, CustomerMaster cm where sd.cv_paid = 'F' and sm.pay_mode = 'C' and sm.br_code = "+branchcode+" and sd.ac_no BETWEEN "+startno+" and "+endno+" and "+str+" BETWEEN '"+fromdate+"' and '"+date+"' and sd.ac_no = sm.ac_no and sm.cid = cm.cid order by sd.ac_no ");
			else
				rs = stmt.executeQuery("select sd.ac_no,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name ,sd.div_dt,sd.div_amt from ShareDividend sd,ShareMaster sm, CustomerMaster cm where sd.cv_paid = 'F' and sm.pay_mode = 'C' and sm.br_code = "+branchcode+" and sd.ac_no BETWEEN "+startno+" and "+endno+" and "+str+" BETWEEN '"+fromdate+"' and '"+date+"' and sd.ac_no = sm.ac_no and sm.cid = cm.cid and "+string_query+" order by sd.ac_no ");
			//rs = stmt.executeQuery("select sd.ac_no,cm.fname,cm.mname,cm.lname ,sd.div_dt,sd.div_amt from ShareDividend sd,ShareMaster sm, CustomerMaster cm where sd.cv_paid = 'F' and sm.pay_mode = 'C' and sm.br_code = "+branchcode+" and sd.ac_no = sm.ac_no and sm.cid = cm.cid order by sd.ac_no;");
			//rs=stmt.executeQuery("select sd.ac_no,cm.fname,cm.mname,cm.lname ,sd.div_dt,sd.div_amt from ShareDividend sd,ShareMaster sm, CustomerMaster cm where sd.cv_paid = 'F' and sm.pay_mode = 'C' and sd.ac_no = sm.ac_no order by sd.ac_no;");
			if(rs.last())
			{
				norows=rs.getRow();
				div = new DividendObject[norows];
				rs.beforeFirst();
			}
			int i=0;
			while(rs.next())
			{
				
				div[i] = new DividendObject();	
				div[i].setSHNumber(rs.getInt(1));
				div[i].setName(rs.getString("name"));
				div[i].setDivDate(rs.getString(3));
				div[i].setDivAmount(rs.getFloat(4));
				i++;
			}
			rs.close();
			
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}		
		return div;     		
	}
	
	public DividendObject[] RetrieveUnPaidNotice(String acc_type,String date)
	{
		int norows=0,divlmt=0;
		DividendObject[] div = null;
		Connection conn=null;
		
		System.out.println("The date in retrive un paid dividend list is "+date);
		
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			
			ResultSet rs = null;
			
			//rs= stmt.executeQuery("select div_lmt from GenParam");
			rs=stmt.executeQuery("select inspection_period from Modules where modulecode='"+acc_type+"'");
			rs.next();
			divlmt=rs.getInt(1);
			rs.close();	
			rs = stmt.executeQuery("select distinct sd.ac_type,sd.ac_no,sd.div_ac_no,cm.cid,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,concat_ws(' ',ca.address,ca.city,ca.pin) as address,ca.state,ca.country,ca.mobile,ca.phno,ca.email,cm.br_code,sd.div_amt,sd.ddn_amt,sd.div_dt,sm.br_code from ShareDividend sd,ShareMaster sm, CustomerMaster cm, CustomerAddr ca where sd.cv_paid = 'F' and sd.pay_mode = 'C' and sd.ac_type='"+acc_type+"' and sd.ac_type=sm.ac_type and concat(right(sd.div_dt,4),'-',mid(sd.div_dt,locate('/',sd.div_dt)+1,(locate('/',sd.div_dt,4)-locate('/',sd.div_dt)-1)),'-',left(sd.div_dt,locate('/',sd.div_dt)-1)) < '"+date+"' and sd.div_amt >"+divlmt+" and sd.ac_no = sm.ac_no and sm.cid = cm.cid and cm.cid = ca.cid and sm.addr_type=ca.addr_type order by sd.ac_no");
			if(rs.next())
			{	
				rs.last();
				norows=rs.getRow();
				div = new DividendObject[norows];	
				rs.beforeFirst();
			}
			int i=0;	
			while(rs.next())
			{
				div[i] = new DividendObject();
				div[i].setSHType(rs.getString("sd.ac_type"));
				div[i].setShnum((rs.getInt("sd.ac_no")));
				div[i].setDivAccNo(rs.getInt("sd.div_ac_no"));
				div[i].setCustomerID(rs.getInt("cm.cid"));
				div[i].setName(rs.getString("name"));
				div[i].addr=new Address();
				div[i].addr.setState(rs.getString("ca.state"));
				div[i].addr.setCountry(rs.getString("ca.country"));
				div[i].addr.setAddress(rs.getString("address"));
				//div[i].addr.setAddress(rs.getString("address")+"\n"+div[i].addr.getState()+"\n"+div[i].addr.getCountry());
				div[i].addr.setMobile(rs.getString("ca.mobile"));
				div[i].addr.setPhno(rs.getString("ca.phno"));
				div[i].addr.setEmail(rs.getString("ca.email"));
				div[i].setBranchCode(rs.getInt("sm.br_code"));
				div[i].setDivAmount(rs.getFloat("sd.div_amt"));
				div[i].setDrfAmount(rs.getFloat("sd.ddn_amt"));
				div[i].setDivDate(rs.getString("sd.div_dt"));
				i++;
			}		
			rs.close();
		}catch(Exception e){e.printStackTrace();}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}		
		return div;        		
	}
	
	public String[] getTemplate(int stage,String code) 
	{
		System.out.println("***inside getTemplate****");
		
		String str[]=null;
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("select * from Template where ac_type='"+code+"' and stage_no="+stage+"");
			if(rs.next())
			{
				rs.last();
				str=new String[rs.getRow()];
				rs.beforeFirst();
			}
			int i=0;
			while(rs.next())
			{
				str[i]=rs.getString("text")+"%%%%%"+rs.getInt("temp_no");
				i++;
			}
		}catch(Exception exception){
			exception.printStackTrace();
			}
		finally{
			try{                
				conn.close();
			}catch(Exception exception){
				exception.printStackTrace();
				}
		}
		return str;
		
	}      
	
	
	public boolean storeTemplate(String text,String acty,int stage,String user,String tml,int temp_no) 
	{
		System.out.println("***inside storeTemplate****");
		
		Connection conn=null;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			if(temp_no==0)
			{
				PreparedStatement pstmt=conn.prepareStatement("insert into Template values(?,?,?,?,?,?,date_format(sysdate(),'%d/%m/%Y %r'))");
				ResultSet rs=stmt.executeQuery("select max(temp_no) from Template where ac_type='"+acty+"'");
				if(rs.next())
				{
					pstmt.setString(1,acty);
					pstmt.setInt(2,rs.getInt(1)+1);
					pstmt.setInt(3,stage);
					pstmt.setString(4,text);
					pstmt.setString(5,user);
					pstmt.setString(6,tml);
					if(pstmt.executeUpdate()==1)
						return true;
					return false;
				}
			}
			else if(stmt.executeUpdate("update Template set text='"+text+"' where ac_type='"+acty+"' and temp_no="+temp_no+"")==1)
				return true;
			
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		finally{
			try{                
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		return false;
	}
	
	public int getDeleteTemplate(String ac_type,int int_template_no)
	{
		Connection conn=null;
		try
		{         
			conn=getConnection();
			
			Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			if(stmt.executeUpdate("delete from Template where temp_no="+int_template_no+" and ac_type='"+ac_type+"' ")==0)
				throw new SQLException();
			
		}catch(SQLException ex){
			ex.printStackTrace();
			sessionContext.setRollbackOnly();
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		finally{
			try{
				conn.close();
			}catch(SQLException e){e.printStackTrace();}
		}
		
		return 0;
		
	}
	
	public DividendObject[] Retrieve_cash_table(String from,String to,int accno,String acctype) throws RecordsNotFoundException
	{
		int norows=0;
		DividendObject[] div = null;
		Connection conn=null;
		System.out.println("in Retrieve_cash_table fromdate"+from);
		System.out.println("in Retrieve_cash_table fromdate"+to);
		System.out.println("in Retrieve_cash_table fromdate"+accno);
		System.out.println("in Retrieve_cash_table fromdate"+acctype);
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs = null;
			
			
			// rs=stmt.executeQuery("select sd.ac_type,sd.ac_no,sd.div_ac_no,cm.cid,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,cm.br_code,sd.pay_mode,sd.div_amt,sd.ddn_amt,sd.div_dt,sm.br_code from ShareDividend sd,ShareMaster sm, CustomerMaster cm, CustomerAddr ca where sd.cv_paid = 'F' and concat(right(sd.div_dt,4),'-',mid(sd.div_dt,locate('/',sd.div_dt)+1,(locate('/',sd.div_dt,4)-locate('/',sd.div_dt)-1)),'-',left(sd.div_dt,locate('/',sd.div_dt)-1)) BETWEEN '"+from+"' and '"+to+"' and sd.ac_type='"+acctype+"' and sd.ac_no="+accno+" and sd.ac_no = sm.ac_no and sm.cid = cm.cid and cm.cid = ca.cid and sm.br_code=cm.br_code order by sd.div_dt");
			if(from.length()>0 && to.length()>0)
				rs=stmt.executeQuery("select ac_type,ac_no,pay_mode,div_dt,div_amt,ddn_amt,cv_no,voucher_no from ShareDividend where cv_paid = 'F' and pay_mode='C' and ac_type='"+acctype+"' and ac_no="+accno+" and concat(right(div_dt,4),'-',mid(div_dt,locate('/',div_dt)+1,(locate('/',div_dt,4)-locate('/',div_dt)-1)),'-',left(div_dt,locate('/',div_dt)-1)) BETWEEN '"+from+"' and '"+to+"'and cv_dt is null order by div_dt");
			else
				rs=stmt.executeQuery("select ac_type,ac_no,pay_mode,div_dt,div_amt,ddn_amt,voucher_no from ShareDividend where cv_paid = 'F' and pay_mode='C' and ac_type='"+acctype+"' and ac_no="+accno+" ");
			rs.last();
			if(rs.getRow()==0)
			{	System.out.println("result set for cash table empty");
			
			
			}
			System.out.println("result set for cash table notttttt empty");
			rs.beforeFirst();
			if(rs.next())
			{	
				rs.last();
				norows=rs.getRow();
				div = new DividendObject[norows];	
				rs.beforeFirst();
			}
			
			
			int i=0;	
			while(rs.next())
			{
				div[i] = new DividendObject();
				System.out.println("cv_no........"+rs.getInt("cv_no"));
				div[i].setSHType(rs.getString("ac_type"));
				div[i].setShnum(rs.getInt("ac_no"));
				div[i].setPayMode(rs.getString("pay_mode"));
				div[i].setDivDate(rs.getString("div_dt"));
				div[i].setDivAmount(rs.getFloat("div_amt"));
				div[i].setDrfAmount(rs.getFloat("ddn_amt"));
				div[i].setCvNumber(rs.getInt("cv_no"));
				div[i].setVoucherNo(rs.getInt("voucher_no"));
				//System.out.println("div[i].getvoucherno"+div[i].getCvNumber());
				
				i++;
			}		
			
			
			rs.close();
		}catch(Exception e){e.printStackTrace();}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}		
		
		return div;        		
	}
	
	/*public DividendObject[] RetrieveUnPaidNotice(String date) throws RemoteException
	 {
	 int norows=0,divlmt=0;
	 DividendObject[] div = null;
	 StringTokenizer token = new StringTokenizer(date,"-");
	 int year = Integer.parseInt(token.nextToken());
	 String month = token.nextToken();
	 String day = token.nextToken();
	 String fromdate;
	 Connection conn=null;
	 
	 try{
	 conn=getConnection();
	 Statement stmt=conn.createStatement();
	 
	 ResultSet rs = null;
	 
	 rs= stmt.executeQuery("select div_lmt from GenParam");
	 rs.next();
	 divlmt=rs.getInt(1);
	 
	 fromdate =String.valueOf(year-divlmt)+"-"+month+"-"+day;
	 //String str =  "concat(right(sd.div_dt,4),'-',mid(sd.div_dt,locate('/',sd.div_dt)+1,(locate('/',sd.div_dt,4)-locate('/',sd.div_dt)-1)),'-',left(sd.div_dt,locate('/',sd.div_dt)-1))" ;
	  
	  rs.close();	 	
	  rs = stmt.executeQuery("select cm.fname,cm.mname,cm.lname,ca.address,ca.city,ca.pin,sd.ac_no,sd.div_dt,sd.div_amt from ShareDividend sd,ShareMaster sm, CustomerMaster cm, CustomerAddr ca where concat(right(sd.div_dt,4),'-',mid(sd.div_dt,locate('/',sd.div_dt)+1,(locate('/',sd.div_dt,4)-locate('/',sd.div_dt)-1)),'-',left(sd.div_dt,locate('/',sd.div_dt)-1)) BETWEEN '"+fromdate+"' and '"+date+"' and sd.cv_paid = 'F' and sd.pay_mode = 'C' and sd.ac_no = sm.ac_no and sm.cid = cm.cid and cm.cid = ca.cid order by sd.ac_no ");
	  //("select fname from CustomerMaster where cid='1'");
	   rs.last();
	   norows=rs.getRow();
	   div = new DividendObject[norows];	
	   rs.beforeFirst();
	   
	   for(int i=0;i<norows;i++)
	   {
	   
	   if(!rs.next())
	   break;
	   div[i] = new DividendObject();
	   String str2 = rs.getString(1);
	   
	   div[i].setName(str2+" "+rs.getString(2)+" "+rs.getString(3));
	   div[i].addr=new Address();
	   div[i].addr.setAddress(rs.getString(4));
	   div[i].addr.setCity(rs.getString(5));
	   div[i].addr.setPin(rs.getString(6));
	   div[i].setSHNumber(rs.getInt(7));
	   div[i].setDivDate(rs.getString(8));
	   div[i].setDivAmount(rs.getFloat(9));
	   
	   }
	   rs.close();
	   }catch(Exception e){e.printStackTrace();}
	   finally{
	   try{
	   conn.close();
	   }catch(Exception e){e.printStackTrace();}
	   }		
	   
	   
	   return div;        		
	   }*/
	
	public DividendObject[] RetrievePayment(String from,String to,int accno,String acctype,int type)
	{
		int norows=0;
		DividendObject[] div = null;
		Connection conn=null;
		System.out.println("from date is"+from);
		System.out.println("to"+to);
		System.out.println("acccno"+accno);
		System.out.println("actype"+acctype);
		System.out.println("type"+type);
		
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs = null;
			
			if(type==0)//Transfer
				rs=stmt.executeQuery("select distinct sd.ac_type,sd.ac_no,sd.div_ac_no,cm.cid,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,cm.br_code,sd.pay_mode,sd.div_amt,sd.ddn_amt,sd.div_dt,sm.br_code,sd.div_ac_no from ShareDividend sd,ShareMaster sm, CustomerMaster cm, CustomerAddr ca where sd.pay_mode='T' and concat(right(sd.div_dt,4),'-',mid(sd.div_dt,locate('/',sd.div_dt)+1,(locate('/',sd.div_dt,4)-locate('/',sd.div_dt)-1)),'-',left(sd.div_dt,locate('/',sd.div_dt)-1)) BETWEEN '"+from+"' and '"+to+"' and sd.ac_type='"+acctype+"' and sd.ac_no="+accno+" and sd.ac_no = sm.ac_no and sm.cid = cm.cid and cm.cid = ca.cid  and sd.div_ac_no is not null order by sd.div_dt limit 1");
			else if(type==1)//PrintWarrant
				rs=stmt.executeQuery("select distinct sd.ac_type,sd.ac_no,sd.div_ac_no,cm.cid,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,cm.br_code,sd.pay_mode,sd.div_amt,sd.ddn_amt,sd.div_dt,sm.br_code,sd.wrt_no from ShareDividend sd,ShareMaster sm, CustomerMaster cm, CustomerAddr ca where sd.cv_paid = 'T' and sd.pay_mode='W' and concat(right(sd.div_dt,4),'-',mid(sd.div_dt,locate('/',sd.div_dt)+1,(locate('/',sd.div_dt,4)-locate('/',sd.div_dt)-1)),'-',left(sd.div_dt,locate('/',sd.div_dt)-1)) BETWEEN '"+from+"' and '"+to+"' and sd.ac_type='"+acctype+"' and sd.ac_no="+accno+" and sd.ac_no = sm.ac_no and sm.cid = cm.cid and cm.cid = ca.cid  and sd.wrt_no is not null order by sd.div_dt limit 1");
			else if(type==2)//Cash
				rs=stmt.executeQuery("select distinct sd.ac_type,sd.ac_no,sd.div_ac_no,cm.cid,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,cm.br_code,sd.pay_mode,sd.div_amt,sd.ddn_amt,sd.div_dt,sm.br_code,sd.cv_no from ShareDividend sd,ShareMaster sm, CustomerMaster cm, CustomerAddr ca where sd.cv_paid = 'T' and sd.pay_mode='C' and concat(right(sd.div_dt,4),'-',mid(sd.div_dt,locate('/',sd.div_dt)+1,(locate('/',sd.div_dt,4)-locate('/',sd.div_dt)-1)),'-',left(sd.div_dt,locate('/',sd.div_dt)-1)) BETWEEN '"+from+"' and '"+to+"' and sd.ac_type='"+acctype+"' and sd.ac_no="+accno+" and sd.ac_no = sm.ac_no and sm.cid = cm.cid and cm.cid = ca.cid  and sd.cv_no is not null order by sd.div_dt");
			else if(type==3)//PayOrder
				rs=stmt.executeQuery("select distinct sd.ac_type,sd.ac_no,sd.div_ac_no,cm.cid,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,cm.br_code,sd.pay_mode,sd.div_amt,sd.ddn_amt,sd.div_dt,sm.br_code,sd.cv_no from ShareDividend sd,ShareMaster sm, CustomerMaster cm, CustomerAddr ca where sd.cv_paid = 'T' and sd.pay_mode='P' and concat(right(sd.div_dt,4),'-',mid(sd.div_dt,locate('/',sd.div_dt)+1,(locate('/',sd.div_dt,4)-locate('/',sd.div_dt)-1)),'-',left(sd.div_dt,locate('/',sd.div_dt)-1)) BETWEEN '"+from+"' and '"+to+"' and sd.ac_type='"+acctype+"' and sd.ac_no="+accno+" and sd.ac_no = sm.ac_no and sm.cid = cm.cid and cm.cid = ca.cid  and sd.cv_no is not null order by sd.div_dt");
			
			if(rs.next())
			{	
				rs.last();
				norows=rs.getRow();
				div = new DividendObject[norows];	
				rs.beforeFirst();
			}
			int i=0;	
			while(rs.next())
			{
				div[i] = new DividendObject();
				
				div[i].setSHType(rs.getString("sd.ac_type"));
				div[i].setShnum(rs.getInt("sd.ac_no"));
				div[i].setDivAccNo(rs.getInt("sd.div_ac_no"));
				div[i].setCustomerID(rs.getInt("cm.cid"));
				div[i].setName(rs.getString("name"));
				div[i].setBranchCode(rs.getInt("sm.br_code"));
				div[i].setPayMode(rs.getString("sd.pay_mode"));
				div[i].setDivAmount(rs.getFloat("sd.div_amt"));
				div[i].setDrfAmount(rs.getFloat("sd.ddn_amt"));
				div[i].setDivDate(rs.getString("sd.div_dt"));
				
				if(type==0)
					div[i].setDivAccNo(rs.getInt("sd.div_ac_no"));
				else if(type==1)
					div[i].setWarrantNo(rs.getInt("sd.wrt_no"));
				else if(type==2 || type==3)	
					div[i].setVoucherNo(rs.getInt("sd.cv_no"));
				i++;
			}		
			rs.close();
		}catch(Exception e){e.printStackTrace();}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}		
		return div;        		
	}
	
	public ShareMasterObject[] RetrieveRegister(String date,int brno,int type,String string_query) throws DateFormatException,RecordsNotFoundException
	{
		int norows=0;
		ShareMasterObject[] sm = null;
		/*StringTokenizer token = new StringTokenizer(date,"-");
		 int year = Integer.parseInt(token.nextToken());
		 String month = token.nextToken();
		 String day = token.nextToken();*/
		Connection conn=null;
		int num_shares=0;
		double value=0;
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs = null;
			
			if(string_query.equals(""))
			{	
				if(type==0)
					rs=stmt.executeQuery("select distinct sd.ac_no,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,sm.mem_cat,sm.mem_issuedate,sm.share_val,sm.pay_mode,sm.pay_ac_type,sm.pay_ac_no,bm.br_name,sd.div_amt,sd.ddn_amt ,sm.br_code,st.share_bal,sv.shareval from ShareMaster sm,ShareDividend sd, CustomerMaster cm, BranchMaster bm,ShareTransaction st,ShareType sv where sd.div_dt = '"+date+"' and sm.br_code = "+brno+" and sd.ac_no = sm.ac_no and sd.ac_type=sm.ac_type and sm.cid = cm.cid and sm.br_code = bm.br_code and st.ac_no=sm.ac_no and st.ac_type=sm.ac_type and sm.mem_cat=sv.mem_cat  and st.trn_date=(select trn_date from ShareTransaction st1 where st1.ac_no=st.ac_no and st1.ac_type=st.ac_type order by concat(right(st1.trn_date,4),'-',mid(st1.trn_date,locate('/',st1.trn_date)+1, (locate('/',st1.trn_date,4)-locate('/',st1.trn_date)-1)),'-',left(st1.trn_date,locate('/',st1.trn_date)-1)) desc limit 1) order by sd.ac_no");
				//rs = stmt.executeQuery("select sd.ac_no,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,sm.mem_issuedate,sm.share_val,sm.pay_mode,sm.pay_ac_type,sm.pay_ac_no,b.bname,sd.div_amt,sd.ddn_amt ,sm.br_code from ShareMaster sm,ShareDividend sd, CustomerMaster cm, Branches b where sd.div_dt = '"+date+"' and sm.br_code = "+brno+" and sd.ac_no = sm.ac_no and sm.cid = cm.cid and sm.br_code = b.bcode order by sd.ac_no ");
				else if(type==1)
					rs = stmt.executeQuery("select distinct sd.ac_no,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,sm.mem_cat,sm.mem_issuedate,sm.share_val,sm.pay_mode,sm.pay_ac_type,sm.pay_ac_no,bm.br_name,sd.div_amt,sd.ddn_amt ,sm.br_code,st.share_bal,sv.shareval from ShareMaster sm,ShareDividend sd, CustomerMaster cm, BranchMaster bm,ShareTransaction st,ShareType sv where sd.div_dt = '"+date+"' and sd.ac_no = sm.ac_no and sd.ac_type=sm.ac_type and sm.cid = cm.cid and sm.br_code = bm.br_code and st.ac_no=sm.ac_no and st.ac_type=sm.ac_type and sm.mem_cat=sv.mem_cat and st.trn_date=(select trn_date from ShareTransaction st1 where st1.ac_no=st.ac_no and st1.ac_type=st.ac_type order by concat(right(st1.trn_date,4),'-',mid(st1.trn_date,locate('/',st1.trn_date)+1, (locate('/',st1.trn_date,4)-locate('/',st1.trn_date)-1)),'-',left(st1.trn_date,locate('/',st1.trn_date)-1)) desc limit 1) order by bm.br_code,sd.ac_no");
				
				//rs = stmt.executeQuery("select sd.ac_no,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,sm.mem_issuedate,sm.share_val,sm.pay_mode,sm.pay_ac_type,sm.pay_ac_no,b.bname,sd.div_amt,sd.ddn_amt,sm.br_code from ShareMaster sm,ShareDividend sd, CustomerMaster cm, Branches b where sd.div_dt = '"+date+"' and sd.ac_no = sm.ac_no and sm.cid = cm.cid and sm.br_code = b.bcode order by b.bcode , sd.ac_no ");	 			
				else if(type==2)
					rs=stmt.executeQuery("select distinct sd.ac_no,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,sm.mem_cat,sm.mem_issuedate,sm.share_val,sm.pay_mode,sm.pay_ac_type,sm.pay_ac_no,bm.br_name,sd.div_amt,sd.ddn_amt ,sm.br_code,st.share_bal,sv.shareval from ShareMaster sm,ShareDividend sd, CustomerMaster cm, BranchMaster bm,ShareTransaction st,ShareType sv where sd.div_dt = '"+date+"' and sd.ac_no = sm.ac_no and sd.ac_type=sm.ac_type and sm.cid = cm.cid and sm.br_code = bm.br_code and st.ac_no=sm.ac_no and st.ac_type=sm.ac_type and sm.mem_cat=sv.mem_cat and st.trn_date=(select trn_date from ShareTransaction st1 where st1.ac_no=st.ac_no and st1.ac_type=st.ac_type order by concat(right(st1.trn_date,4),'-',mid(st1.trn_date,locate('/',st1.trn_date)+1, (locate('/',st1.trn_date,4)-locate('/',st1.trn_date)-1)),'-',left(st1.trn_date,locate('/',st1.trn_date)-1)) desc limit 1) order by sd.ac_no");
				
				//rs = stmt.executeQuery("select sd.ac_no,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,sm.mem_issuedate,sm.share_val,sm.pay_mode,sm.pay_ac_type,sm.pay_ac_no,b.bname,sd.div_amt,sd.ddn_amt,sm.br_code from ShareMaster sm,ShareDividend sd, CustomerMaster cm, Branches b where sd.div_dt = '"+date+"' and sd.ac_no = sm.ac_no and sm.cid = cm.cid and sm.br_code = b.bcode order by sd.ac_no ");
			}
			else
			{
				if(type==0)
					rs=stmt.executeQuery("select distinct sd.ac_no,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,sm.mem_cat,sm.mem_cat,sm.mem_issuedate,sm.share_val,sm.pay_mode,sm.pay_ac_type,sm.pay_ac_no,bm.br_name,sd.div_amt,sd.ddn_amt ,sm.br_code,st.share_bal,sv.shareval from ShareMaster sm,ShareDividend sd, CustomerMaster cm, BranchMaster bm,ShareTransaction st,ShareType sv where sd.div_dt = '"+date+"' and sm.br_code = "+brno+" and sd.ac_no = sm.ac_no and sd.ac_type=sm.ac_type and sm.cid = cm.cid and sm.br_code = bm.br_code and st.ac_no=sm.ac_no and st.ac_type=sm.ac_type and sm.mem_cat=sv.mem_cat and st.trn_date=(select trn_date from ShareTransaction st1 where st1.ac_no=st.ac_no and st1.ac_type=st.ac_type order by concat(right(st1.trn_date,4),'-',mid(st1.trn_date,locate('/',st1.trn_date)+1, (locate('/',st1.trn_date,4)-locate('/',st1.trn_date)-1)),'-',left(st1.trn_date,locate('/',st1.trn_date)-1)) desc limit 1) and "+string_query+" order by sd.ac_no");
				//rs = stmt.executeQuery("select sd.ac_no,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,sm.mem_issuedate,sm.share_val,sm.pay_mode,sm.pay_ac_type,sm.pay_ac_no,b.bname,sd.div_amt,sd.ddn_amt ,sm.br_code from ShareMaster sm,ShareDividend sd, CustomerMaster cm, Branches b where sd.div_dt = '"+date+"' and sm.br_code = "+brno+" and sd.ac_no = sm.ac_no and sm.cid = cm.cid and sm.br_code = b.bcode order by sd.ac_no ");
				else if(type==1)
					rs = stmt.executeQuery("select distinct sd.ac_no,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,sm.mem_cat,sm.mem_issuedate,sm.share_val,sm.pay_mode,sm.pay_ac_type,sm.pay_ac_no,bm.br_name,sd.div_amt,sd.ddn_amt ,sm.br_code,st.share_bal,sv.shareval from ShareMaster sm,ShareDividend sd, CustomerMaster cm, BranchMaster bm,ShareTransaction st,ShareType sv where sd.div_dt = '"+date+"' and sd.ac_no = sm.ac_no and sd.ac_type=sm.ac_type and sm.cid = cm.cid and sm.br_code = bm.br_code and st.ac_no=sm.ac_no and st.ac_type=sm.ac_type and sm.mem_cat=sv.mem_cat and st.trn_date=(select trn_date from ShareTransaction st1 where st1.ac_no=st.ac_no and st1.ac_type=st.ac_type order by concat(right(st1.trn_date,4),'-',mid(st1.trn_date,locate('/',st1.trn_date)+1, (locate('/',st1.trn_date,4)-locate('/',st1.trn_date)-1)),'-',left(st1.trn_date,locate('/',st1.trn_date)-1)) desc limit 1) and "+string_query+" order by bm.br_code,sd.ac_no");
				
				//rs = stmt.executeQuery("select sd.ac_no,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,sm.mem_issuedate,sm.share_val,sm.pay_mode,sm.pay_ac_type,sm.pay_ac_no,b.bname,sd.div_amt,sd.ddn_amt,sm.br_code from ShareMaster sm,ShareDividend sd, CustomerMaster cm, Branches b where sd.div_dt = '"+date+"' and sd.ac_no = sm.ac_no and sm.cid = cm.cid and sm.br_code = b.bcode order by b.bcode , sd.ac_no ");	 			
				else if(type==2)
					rs=stmt.executeQuery("select distinct sd.ac_no,concat_ws(' ',cm.fname,cm.mname,cm.lname) as name,sm.mem_cat,sm.mem_issuedate,sm.share_val,sm.pay_mode,sm.pay_ac_type,sm.pay_ac_no,bm.br_name,sd.div_amt,sd.ddn_amt ,sm.br_code,st.share_bal,sv.shareval from ShareMaster sm,ShareDividend sd, CustomerMaster cm, BranchMaster bm,ShareTransaction st,ShareType sv where sd.div_dt = '"+date+"' and sd.ac_no = sm.ac_no and sd.ac_type=sm.ac_type and sm.cid = cm.cid and sm.br_code = bm.br_code and st.ac_no=sm.ac_no and st.ac_type=sm.ac_type and sm.mem_cat=sv.mem_cat and st.trn_date=(select trn_date from ShareTransaction st1 where st1.ac_no=st.ac_no and st1.ac_type=st.ac_type order by concat(right(st1.trn_date,4),'-',mid(st1.trn_date,locate('/',st1.trn_date)+1, (locate('/',st1.trn_date,4)-locate('/',st1.trn_date)-1)),'-',left(st1.trn_date,locate('/',st1.trn_date)-1)) desc limit 1) and "+string_query+" order by sd.ac_no");
				
			}
			
			rs.last();
			if(rs.getRow()==0)
			{
				System.out.println("rs empty");
				throw new RecordsNotFoundException();
			}
			rs.beforeFirst();
			
			if(rs.next())
			{    
				rs.last();
				norows=rs.getRow();
				rs.beforeFirst();
				sm = new ShareMasterObject[norows];
			}
			System.out.println("sm.length"+sm.length);
			int i=0;
			
			while(rs.next())
			{
				/*if(rs.getInt("sm.share_val")!=0)
				 num_shares=rs.getInt("st.share_bal")/rs.getInt("sm.share_val");*/
				if(rs.getInt("sm.mem_cat")==1)
				{
					num_shares=(int)(rs.getInt("st.share_bal")/rs.getFloat("sv.shareval"));
				}
				else if(rs.getInt("sm.mem_cat")==2)
				{
					num_shares=(int)(rs.getInt("st.share_bal")/rs.getFloat("sv.shareval"));
				}
				
				//break;
				sm[i] = new ShareMasterObject();	
				sm[i].setShareNumber(rs.getInt("sd.ac_no"));
				sm[i].setName(rs.getString("name"));
				sm[i].setIssueDate(rs.getString("sm.mem_issuedate"));
				sm[i].setNumberofShares(num_shares);
				//sm[i].setShareVal(rs.getFloat("sm.share_val"));
				sm[i].setShareVal(rs.getDouble("sv.shareval"));
				sm[i].setPayMode(rs.getString("sm.pay_mode"));
				//sm[i].setPaymentAcctype(rs.getString(6));
				sm[i].setPaymentAccno(rs.getInt("sm.pay_ac_no"));
				System.out.println("value"+rs.getInt("sm.pay_ac_no"));
				sm[i].setBranchName(rs.getString("bm.br_name"));
				sm[i].setDivAmount(rs.getDouble("sd.div_amt"));
				sm[i].setDrfAmount(rs.getDouble("sd.ddn_amt"));
				//sm[i].setBranchCode(rs.getInt(netamt));
				i++;
			}
			
			rs.close();
			
		}catch(Exception e){e.printStackTrace();}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}		
		
		
		return sm;    	
	}  
	
	
	public int setBalance(String acctype,int accno,double amount,int type) 
	{
		int s=-1;
		Connection conn=null;
		
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			
			if(acctype.equals("B"))
			{
				if(type==1)

								s=stmt.executeUpdate("update Branches set balance=balance+"+amount+" where bcode="+accno);
				else if(type==0)
					s=stmt.executeUpdate("update Branches set balance=balance-"+amount+" where bcode="+accno);			
			}
			if(s>=1)
				return (1);
			
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}		
		
		return(s);	
	}
	
	
	
	public ShareMasterObject[] getShare(String from,String to,int type)
	{
		//type 3 for sharedispatch
		ShareMasterObject ms[]=null;
		Connection conn=null;
		
		try{
			conn=getConnection();
			
			Statement stmt1=conn.createStatement();
			ResultSet rs=null;
			
			if(type==1)
			{
				System.out.println("Coming inside");
				if(from.length()>0 && to.length()>0)
					//rs=stmt1.executeQuery("select ShareMaster.*,concat_ws(' ',fname,mname,lname) as name ,catname,shareval,sh_cap,sus_sh_cap,modulecode from ShareMaster,ShareType,Modules,CustomerMaster  where  CustomerMaster.cid=ShareMaster.cid and ShareMaster.mem_cat=ShareType.mem_cat and Modules.modulecode=ShareMaster.ac_type and modulecode like '1001%' and ShareMaster.ac_type=ShareType.ac_type and sh_ind='T' and concat(right(mem_issuedate,4),'-',mid(mem_issuedate,locate('/',mem_issuedate)+1, (locate('/',mem_issuedate,4)-locate('/',mem_issuedate)-1)),'-',left(mem_issuedate,locate('/',mem_issuedate)-1)) between '"+Validations.convertYMD(from)+"'  and  '"+Validations.convertYMD(to)+"' and ShareMaster.ve_user is not null");//Karthi
					//rs=stmt1.executeQuery("select distinct st.ac_type, st.ac_no,st.trn_amt,sm.mem_cat,sm.br_code,sm.cid,sm.mem_issuedate,sm.pay_mode,concat_ws(' ',fname,mname,lname) as name,sh.shareval,sh.catname,st.susp_ind,sh.sh_cap,sh.sus_sh_cap from ShareMaster sm,ShareTransaction st,ShareType sh,CustomerMaster cm where st.trn_seq<2 and sm.br_code=1 and sm.ac_no=st.ac_no and sm.ac_type=st.ac_type and sm.lst_trn_seq=st.trn_seq and sm.cid=cm.cid  and sm.mem_cat=sh.mem_cat and st.susp_ind='T' and concat(right(mem_issuedate,4),'-',mid(mem_issuedate,locate('/',mem_issuedate)+1, (locate('/',mem_issuedate,4)-locate('/',mem_issuedate)-1)),'-',left(mem_issuedate,locate('/',mem_issuedate)-1)) between '"+Validations.convertYMD(from)+"' and '"+Validations.convertYMD(to)+"'and st.ve_user is not null order by ac_no");
					rs=stmt1.executeQuery("select distinct st.ac_type, st.ac_no,st.trn_amt,sm.mem_cat,sm.br_code,sm.cid,sm.mem_issuedate,sm.pay_mode,concat_ws(' ',fname,mname,lname) as name,sh.shareval,sh.catname,st.susp_ind from ShareMaster sm,ShareTransaction st,ShareType sh,CustomerMaster cm where st.trn_seq<2 and sm.br_code=1 and sm.ac_no=st.ac_no and sm.ac_type=st.ac_type and sm.lst_trn_seq=st.trn_seq and sm.cid=cm.cid  and sm.mem_cat=sh.mem_cat and st.susp_ind='T' and concat(right(mem_issuedate,4),'-',mid(mem_issuedate,locate('/',mem_issuedate)+1, (locate('/',mem_issuedate,4)-locate('/',mem_issuedate)-1)),'-',left(mem_issuedate,locate('/',mem_issuedate)-1)) between '"+Validations.convertYMD(from)+"' and '"+Validations.convertYMD(to)+"'and st.ve_user is not null order by ac_no");
				else
					//rs=stmt1.executeQuery("select ShareMaster.*,concat_ws(' ',fname,mname,lname) as name ,catname,shareval,sh_cap,sus_sh_cap,modulecode from ShareMaster,ShareType,Modules,CustomerMaster  where  CustomerMaster.cid=ShareMaster.cid and ShareMaster.mem_cat=ShareType.mem_cat and Modules.modulecode=ShareMaster.ac_type and modulecode like '1001%' and ShareMaster.ac_type=ShareType.ac_type and sh_ind='T' and ShareMaster.ve_user is not null");
					//rs=stmt1.executeQuery("select distinct st.ac_type, st.ac_no,st.trn_amt,sm.mem_cat,sm.br_code,sm.mem_cat,sm.cid,sm.mem_issuedate,sm.pay_mode,concat_ws(' ',fname,mname,lname) as name,sh.shareval,sh.catname,st.susp_ind,sh.sh_cap,sh.sus_sh_cap from ShareMaster sm,ShareTransaction st,ShareType sh,CustomerMaster cm where st.trn_seq<2 and sm.br_code=1 and sm.ac_no=st.ac_no and sm.ac_type=st.ac_type and sm.lst_trn_seq=st.trn_seq and sm.cid=cm.cid  and sm.mem_cat=sh.mem_cat and st.susp_ind='T' and st.ve_user is not null order by ac_no");
					rs=stmt1.executeQuery("select distinct st.ac_type, st.ac_no,st.trn_amt,sm.mem_cat,sm.br_code,sm.mem_cat,sm.cid,sm.mem_issuedate,sm.pay_mode,concat_ws(' ',fname,mname,lname) as name,sh.shareval,sh.catname,st.susp_ind from ShareMaster sm,ShareTransaction st,ShareType sh,CustomerMaster cm where st.trn_seq<2  and sm.br_code=1 and sm.ac_no=st.ac_no and sm.ac_type=st.ac_type and sm.lst_trn_seq=st.trn_seq and sm.cid=cm.cid  and sm.mem_cat=sh.mem_cat and st.susp_ind='T' and st.ve_user is not null order by ac_no");
				System.out.println("If type 1.............");
			}
			
			
			else if(type==2)
			{
				if(from.length()>0 && to.length()>0)
					//rs=stmt1.executeQuery("select trn_no,catname,ShareTransaction.ac_type,ShareTransaction.ac_no,trn_amt,share_bal,trn_date,ShareMaster.br_code,ShareMaster.cid,concat_ws(' ',fname,mname,lname) as name,sh_cap,sus_sh_cap,modulecode,ShareType.mem_cat,shareval from ShareTransaction,ShareMaster,ShareType,Modules,CustomerMaster  where CustomerMaster.cid=ShareMaster.cid and ShareMaster.mem_cat=ShareType.mem_cat and Modules.modulecode=ShareMaster.ac_type and modulecode like '1001%' and ShareMaster.ac_type=ShareType.ac_type and susp_ind='T' and ShareMaster.ac_no=ShareTransaction.ac_no and ShareMaster.ac_type=ShareTransaction.ac_type and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))  between '"+Validations.convertYMD(from)+"' and '"+Validations.convertYMD(to)+"' and ShareTransaction.ve_user is not null");//Karthi
					//rs=stmt1.executeQuery("select distinct st.ac_type,st.trn_no,st.ac_no,st.trn_amt,st.trn_date,st.trn_mode,sm.br_code,sm.mem_cat,sm.cid,sm.pay_mode,concat_ws(' ',fname,mname,lname) as name,sh.shareval,sh.catname,st.susp_ind,sh.sh_cap,sh.sus_sh_cap from ShareMaster sm,ShareTransaction st,ShareType sh,CustomerMaster cm,BranchMaster bm where st.trn_seq>1 and st.trn_no is not null  and sm.ac_type=st.ac_type and sm.ac_no=st.ac_no and sm.cid=cm.cid and sm.mem_cat=sh.mem_cat and sm.br_code=bm.br_code and st.susp_ind='T' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))  between '"+Validations.convertYMD(from)+"' and '"+Validations.convertYMD(to)+"' and st.trn_seq is not null and st.ac_no!=0 and st.ve_user is not null order by trn_no");
					rs=stmt1.executeQuery("select distinct st.ac_type,st.trn_no,st.ac_no,st.trn_amt,st.trn_date,st.trn_mode,sm.br_code,sm.mem_cat,sm.cid,sm.pay_mode,concat_ws(' ',fname,mname,lname) as name,sh.shareval,sh.catname,st.susp_ind from ShareMaster sm,ShareTransaction st,ShareType sh,CustomerMaster cm,BranchMaster bm where st.trn_seq>1 and st.trn_no is not null  and sm.ac_type=st.ac_type and sm.ac_no=st.ac_no and sm.cid=cm.cid and sm.mem_cat=sh.mem_cat and sm.br_code=bm.br_code and st.susp_ind='T' and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))  between '"+Validations.convertYMD(from)+"' and '"+Validations.convertYMD(to)+"' and st.trn_seq is not null and st.ac_no!=0 and st.ve_user is not null order by trn_no");
				else
					//rs=stmt1.executeQuery("select trn_no,catname,ShareTransaction.ac_type,ShareTransaction.ac_no,trn_amt,share_bal,trn_date,ShareMaster.br_code,ShareMaster.cid,concat_ws(' ',fname,mname,lname) as name,sh_cap,sus_sh_cap,modulecode,ShareType.mem_cat,shareval from ShareTransaction,ShareMaster,ShareType,Modules,CustomerMaster  where CustomerMaster.cid=ShareMaster.cid and ShareMaster.mem_cat=ShareType.mem_cat and Modules.modulecode=ShareMaster.ac_type and modulecode like '1001%' and ShareMaster.ac_type=ShareType.ac_type and susp_ind='T' and ShareMaster.ac_no=ShareTransaction.ac_no and ShareMaster.ac_type=ShareTransaction.ac_type and ShareTransaction.ve_user is not null");//Karthi
					//rs=stmt1.executeQuery("select distinct st.ac_type,st.trn_no,st.ac_no,st.trn_amt,st.trn_date,st.trn_mode,sm.br_code,sm.mem_cat,sm.cid,sm.pay_mode,concat_ws(' ',fname,mname,lname) as name,sh.shareval,sh.catname,st.susp_ind,sh.sh_cap,sh.sus_sh_cap from ShareMaster sm,ShareTransaction st,ShareType sh,CustomerMaster cm,BranchMaster bm where st.trn_seq>1 and st.trn_no is not null  and sm.ac_type=st.ac_type and sm.ac_no=st.ac_no and sm.cid=cm.cid and sm.mem_cat=sh.mem_cat and sm.br_code=bm.br_code and st.susp_ind='T' and st.trn_seq is not null and st.ac_no!=0 and st.ve_user is not null order by trn_no");
					rs=stmt1.executeQuery("select distinct st.ac_type,st.trn_no,st.ac_no,st.trn_amt,st.trn_date,st.trn_mode,sm.br_code,sm.mem_cat,sm.cid,sm.pay_mode,concat_ws(' ',fname,mname,lname) as name,sh.shareval,sh.catname,st.susp_ind from ShareMaster sm,ShareTransaction st,ShareType sh,CustomerMaster cm,BranchMaster bm where st.trn_seq>1 and st.trn_no is not null  and sm.ac_type=st.ac_type and sm.ac_no=st.ac_no and sm.cid=cm.cid and sm.mem_cat=sh.mem_cat and sm.br_code=bm.br_code and st.susp_ind='T' and st.trn_seq is not null and st.ac_no!=0 and st.ve_user is not null order by trn_no");
				System.out.println("If type 2.............");
			}
			//Added by Karthi--->16/02/2006
			else if(type==3)
			{
				if(from.length()>0 && to.length()>0)
					rs=stmt1.executeQuery("select sm.ac_type,sm.temp_no,sm.cid,sm.mem_issuedate,sm.br_code,sm.share_val,bm.br_name,sm.mem_cat,concat_ws(' ',fname,mname,lname) as name ,st.catname,st.shareval from ShareMaster sm,ShareType st,CustomerMaster cm,BranchMaster bm  where sm.br_code!=1 and sm.sh_ind='T' and cm.cid=sm.cid and sm.ac_type=st.ac_type and concat(right(mem_issuedate,4),'-',mid(mem_issuedate,locate('/',mem_issuedate)+1, (locate('/',mem_issuedate,4)-locate('/',mem_issuedate)-1)),'-',left(mem_issuedate,locate('/',mem_issuedate)-1)) between '"+Validations.convertYMD(from)+"' and '"+Validations.convertYMD(to)+"' and sm.mem_cat=st.mem_cat and sm.br_code=bm.br_code and sm.ac_type=st.ac_type and  sm.ve_user is not null order by temp_no");
				else
					rs=stmt1.executeQuery("select sm.ac_type,sm.temp_no,sm.cid,sm.mem_issuedate,sm.br_code,sm.share_val,bm.br_name,sm.mem_cat,concat_ws(' ',fname,mname,lname) as name ,st.catname,st.shareval from ShareMaster sm,ShareType st,CustomerMaster cm,BranchMaster bm  where sm.br_code!=1 and sm.sh_ind='T' and cm.cid=sm.cid and sm.mem_cat=st.mem_cat and sm.ac_type=st.ac_type and sm.br_code=bm.br_code and sm.ac_type=st.ac_type and  sm.ve_user is not null order by temp_no");
				System.out.println("If type 3.............");
			}
			//System.out.println("If type 1.............");
			rs.last();
			ms=new ShareMasterObject[rs.getRow()];
			rs.beforeFirst();
			int i=0;
			//System.out.println("If type 1.............");
			while(rs.next())
			{
				
				ms[i]=new ShareMasterObject();
				//System.out.println("If type 1.............");
				if(type==1)
				{
					//System.out.println("If type 1.............");
					ms[i].setId(String.valueOf(i));
					ms[i].setShareAccType(rs.getString("ac_type"));
					//ms[i].setTempShareNumber(rs.getInt("temp_no")); //not yet verified
					ms[i].setTempShareNumber(rs.getInt("ac_no"));
					ms[i].setBranchCode(rs.getInt("br_code"));
					ms[i].setCustomerId(rs.getInt("cid"));
					ms[i].setName(rs.getString("name"));				
					ms[i].setRecievedMode(rs.getString("pay_mode"));				
					ms[i].setMemberCategory(rs.getInt("mem_cat"));
					ms[i].setShareStatus(rs.getString("catname"));
					/*Statement stmt2=conn.createStatement();
					 ResultSet rs3=stmt2.executeQuery("select shareval,sh_cap,sus_sh_cap from ShareType where mem_cat="+ms[i].getMemberCategory() +" and ac_type="+ms[i].getShareAccType());
					 rs3.next();
					 ms[i].setShareVal(rs3.getFloat("shareval"));*/
					ms[i].setShareVal(rs.getFloat("shareval"));
					int no_of_shares=(int)(rs.getFloat("trn_amt")/rs.getFloat("shareval"));
					ms[i].setNumberofShares(no_of_shares);
					//ms[i].setShareVal(rs.getFloat("share_val"));
					ms[i].setIssueDate(rs.getString("mem_issuedate"));			
					//ms[i].setGLCode(rs.getString("sh_cap"));
					//ms[i].setSuspGLCode(rs.getString("sus_sh_cap"));
					//ms[i].setShareVal(rs.getDouble("shareval"));
					
				}
				else if(type==2)
				{
					//System.out.println("If type 2.............");
					ms[i].setTempShareNumber(rs.getInt("trn_no")); //not yet verified
					ms[i].setShareNumber(rs.getInt("ac_no"));			
					//ms[i].setGLCode(rs.getString("sh_cap"));
					//ms[i].setSuspGLCode(rs.getString("sus_sh_cap"));
					ms[i].setName(rs.getString("name"));					
					ms[i].setBranchCode(rs.getInt("br_code"));
					ms[i].setCustomerId(rs.getInt("cid"));
					ms[i].setShareType(rs.getInt("mem_cat"));
					ms[i].setMemberCategory(rs.getInt("mem_cat"));
					ms[i].setShareVal(rs.getDouble("shareval"));
					ms[i].setNumberofShares((rs.getFloat("trn_amt")/rs.getDouble("shareval")));//Added by Karthi
					ms[i].setRecievedMode(rs.getString("trn_mode"));
					ms[i].setShareStatus(rs.getString("catname"));//Added by Karthi
					ms[i].setIssueDate(rs.getString("trn_date"));
					ms[i].setAmount(rs.getDouble("trn_amt"));
					ms[i].setShareAccType(rs.getString("ac_type"));
					
				}
				else
				{
					ms[i].setShareAccType(rs.getString("ac_type"));
					ms[i].setTempShareNumber(rs.getInt("temp_no"));
					ms[i].setCustomerId(rs.getInt("cid"));
					ms[i].setIssueDate(rs.getString("mem_issuedate"));
					ms[i].setBranchCode(rs.getInt("br_code"));
					ms[i].setBranchName(rs.getString("br_name"));
					ms[i].setShareBalance(rs.getDouble("share_val"));
					ms[i].setMemberCategory(rs.getInt("mem_cat"));
					ms[i].setShareVal(rs.getDouble("shareval"));
					ms[i].setName(rs.getString("name"));
					ms[i].setShareStatus(rs.getString("catname"));
					ms[i].setNumberofShares(rs.getDouble("share_val")/rs.getDouble("shareval"));
				}
				i++;
				
			}
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}		
		
		return ms;
	}
	
	
	//Modifyed by Karthi==>05/04/2006
	public int storeShareParam(Object[][] obj)
	{
		Connection conn=null;
		try
		{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			System.out.println("inside.....");
			PreparedStatement ps=conn.prepareStatement("insert into ShareParam values(?,?,?,?,?,?,?,?,?)");
			for(int i=0;i<obj.length;i++)
			{
				System.out.println("1.obj[i][j] :"+obj[i][0]);
				System.out.println("2.obj[i][j] :"+obj[i][1]);
				System.out.println("3.obj[i][j] :"+obj[i][2]);
				stmt.executeUpdate("delete from ShareParam where ac_type='"+obj[i][0]+"' and mem_cat="+obj[i][1]+" and prm_code="+obj[i][2]+" ");
				for(int j=0;j<obj[0].length;j++)
				{
					if(obj[i][j]!=null)
						ps.setString((j+1),obj[i][j].toString());
					else 
						ps.setString((j+1),String.valueOf(obj[i][j]));
				}
				ps.addBatch();
			}
			int a[]=ps.executeBatch();
			return (a[0]);
		}catch(SQLException exception){
			sessionContext.setRollbackOnly();
			exception.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		finally
		{
			try{conn.close();}catch(Exception e){e.printStackTrace();}
		}
		return 0;
	}
	
	//Commented by Karthi==>05/04/2006
	//its not working properly
	/*	public int storeShareParam(Vector vec) throws RemoteException
	 {
	 Connection conn=null;
	 System.out.println("Vector size.....:"+vec.size());
	 try{
	 conn=getConnection();
	 Statement stmt=conn.createStatement();
	 System.out.println("inside.....");
	 PreparedStatement ps=conn.prepareStatement("insert into ShareParam values(?,?,?,?,?,?,?,?,?)");
	 for(int i=0;i<vec.size();i++)
	 {
	 Object obj[]=(Object[])vec.get(i);
	 stmt.executeUpdate("delete from ShareParam where ac_type="+obj[0].toString()+" and mem_cat="+obj[1].toString() +" and prm_code="+obj[2].toString());
	 ps.setString(1,obj[0].toString());
	 ps.setString(2,obj[1].toString());
	 ps.setString(3,obj[2].toString());
	 ps.setString(4,obj[3].toString());
	 ps.setString(5,obj[4].toString());
	 if(obj[5]==null)
	 ps.setString(6,String.valueOf(obj[5]));
	 else
	 ps.setString(6,obj[5].toString());
	 
	 ps.setString(7,obj[6].toString());
	 ps.setString(8,obj[7].toString())
	 ps.setString(9,obj[8].toString());
	 ps.addBatch();
	 }
	 int a[]=ps.executeBatch();
	 return (a[0]);		
	 }catch(SQLException exception){
	 sessionContext.setRollbackOnly();
	 exception.printStackTrace();
	 }catch(Exception exception){
	 sessionContext.setRollbackOnly();
	 exception.printStackTrace();}
	 finally{
	 try{
	 conn.close();
	 }catch(Exception e){e.printStackTrace();}
	 }		
	 
	 
	 return 1;
	 }*/
	
	//added by suraj on 6.3.08
	
	public int verifyShare(ShareMasterObject sm,int trnno,int type,ShareParamObject shparam[],String date)
	{
		Connection conn=null;
		double misc_amt=0;
		System.out.println("THE VALUES ARE ________________________"+date);
		System.out.println(sm.getShareBalance());
		System.out.println(sm.getShareVal());
		System.out.println(sm.getAmount());
		System.out.println(sm.getShareStatus());
		System.out.println(sm.getRecievedMode());
		System.out.println(sm.getRecievedAcctype());
		System.out.println(sm.getRecievedAccno());
		System.out.println(sm.getBranchCode());
		System.out.println(sm.getNumberofShares());
		System.out.println(sm.getTrnamt());
		System.out.println("The trno is"+trnno);
		//System.out.println("share param length"+shparam.length);
		System.out.println("THE VALUES ARE ________________________");
		//Changed all queries wherever trn_amt was being updated in share master---trn_amt
		//is not updated during verification---------Swaran
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			Statement cpstmt=conn.createStatement();
			Statement stmt_trnseq=conn.createStatement();
			Context ctx=getInitialContext();
			Object obj=ctx.lookup("COMMONWEB");
			commonServer.CommonHome home=(commonServer.CommonHome)obj;
			CommonRemote cremote=home.create();
			System.out.println("data b4");
			System.out.println("sm.getShareBalance()="+sm.getShareBalance());
			System.out.println("sm.getShareVal()="+sm.getShareVal());
			System.out.println("sm.getShareNumber()="+sm.getShareNumber());
			System.out.println("trn_amt="+sm.getShareBalance()*sm.getShareVal());
			
			System.out.println("trn_number="+trnno);
			//type 0--new,1--additional,2--Withdrawal
			int shnumber=0;
			if(sm.getShareStatus().equals("T")&& sm.getBranchCode()!=1)
				shnumber=sm.getTempShareNumber();
			else
				shnumber=sm.getShareNumber();
			
			int cfrom=0,shno=0;
			System.out.println("verification..............");
			/*Used for Certificate Printing
			 *max_renewal_count-->Distinctive Number
			 *std_inst-->Sharecertificate Number
			 */
			if((type==0 || type==1)&& sm.getShareStatus().equals("P"))//certificate number generation for new and additional share allotment
			{
				System.out.println("verification..............certificate number generation for new and additional share allotment");
				Statement stmt2=conn.createStatement();
				//ResultSet rs1=stmt2.executeQuery("select lst_certallot_no from GenParam");
				
				ResultSet rs1=stmt2.executeQuery("select max_renewal_count,std_inst from Modules where modulecode='"+sm.getShareAccType()+"'");
				rs1.next();
				cfrom=rs1.getInt(1)+1;
				shno=rs1.getInt(2)+1;
				rs1.close();
				for(int i=cfrom;i<(cfrom+sm.getNumberofShares());i++)
					stmt2.executeUpdate("insert into CertificateMaster values("+i+","+sm.getShareNumber()+",'"+sm.getShareAccType()+"','"+date+"',null)");
				//stmt2.executeUpdate("update GenParam set lst_certallot_no="+(cfrom+sm.getNumberofShares()-1));
				stmt2.executeUpdate("update Modules set max_renewal_count="+(cfrom+sm.getNumberofShares()-1)+",std_inst="+shno+" where modulecode='"+sm.getShareAccType()+"'");
			}
			/**
			 * we will enter Consecutive entries in ‘Certificate Master’ for Permanent Shares with allotment date and refund date (refund date is null for Allotment time).This is for Permanent shares only.
			 * consecutive entries are depends upon the number of shares.
			 * we will update the refund date field in ‘Certificate Master’ table for Withdrawal Shares. Suppose if person having some 50 shares. He withdraw only 20 shares means we update first 20 shares refund date(refunddt field) in ‘Certificate Master’ table.
			 * For temporary shares at the time of convertion(temp. to perm.)we enter the entry to 'CertificateMaster' table. 
			 */
			if(type==0)//New Share verification
			{
				if(sm.getShareStatus().equals("T"))
				{
					if(sm.getBranchCode()!=1)
						stmt.executeUpdate("update ShareMaster set share_val="+(sm.getShareVal()*sm.getNumberofShares())+",ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date='"+sm.uv.getVerDate()+"' where ac_type='"+sm.getShareAccType()+"' and temp_no="+shnumber);
					else
						stmt.executeUpdate("update ShareMaster set share_val="+(sm.getShareVal()*sm.getNumberofShares())+",ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date='"+sm.uv.getVerDate()+"' where ac_type='"+sm.getShareAccType()+"' and  ac_no="+shnumber);
					//stmt.executeUpdate("update ShareTransaction set trn_amt="+(sm.getShareVal()*sm.getNumberofShares())+", ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date=date_format(sysdate(),'%d/%m/%Y %H:%i:%s') where ac_no="+shnumber+" and susp_ind='T'");
					stmt.executeUpdate("update ShareTransaction set trn_amt="+(sm.getShareVal()*sm.getNumberofShares())+",ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date='"+sm.uv.getVerDate()+"' where ac_type='"+sm.getShareAccType()+"' and ac_no="+shnumber+" and susp_ind='T'");
					System.out.println("new share verification.)T(.............");
					ResultSet rs_name=stmt.executeQuery("select concat_ws('',fname,mname,lname) as name from CustomerMaster cm,ShareMaster sm where sm.cid=cm.cid and sm.cid="+sm.getCustomerId());
					rs_name.next();
					//stmt.executeUpdate("insert into SignatureInstruction values('"+sm.getShareAccType()+"',"+sm.getTempShareNumber()+","+sm.getCustomerId()+",'"+rs_name.getString("name")+"',null,0,0,'Single')");
				}
				else
				{
					System.out.println("@@@@@@@@@cfrom :"+cfrom);
					System.out.println("@@@@@@@@@shno :"+shno);
					stmt.executeUpdate("update ShareMaster set share_val="+(sm.getShareVal()*sm.getNumberofShares())+",ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date='"+sm.uv.getVerDate()+"' where ac_type='"+sm.getShareAccType()+"' and ac_no="+shnumber);
					//stmt.executeUpdate("update ShareTransaction set trn_amt="+(sm.getShareVal()*sm.getNumberofShares())+",ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date=date_format(sysdate(),'%d/%m/%Y %H:%i:%s'),dist_no_from="+cfrom+",dist_no_to=share_bal+"+(cfrom-1)+" where ac_no="+shnumber+" and susp_ind='P'");
					stmt.executeUpdate("update ShareTransaction set trn_amt="+(sm.getShareVal()*sm.getNumberofShares())+",ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date='"+sm.uv.getVerDate()+"',dist_no_from="+cfrom+",dist_no_to="+((cfrom-1)+sm.getNumberofShares())+",sh_cert_no="+shno+" where ac_type='"+sm.getShareAccType()+"' and ac_no="+shnumber+" and susp_ind='P'");
					//stmt.executeUpdate("update ShareTransaction set ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date=date_format(sysdate(),'%d/%m/%Y %H:%i:%s'),dist_no_from="+cfrom+",dist_no_to=share_bal+"+(cfrom-1)+" where ac_no="+shnumber+" and susp_ind='P'");
					System.out.println("new share verification.)P(.............");
					ResultSet rs_name=stmt.executeQuery("select concat_ws('',fname,mname,lname) as name from CustomerMaster cm,ShareMaster sm where sm.cid=cm.cid and sm.cid="+sm.getCustomerId());
					rs_name.next();
					stmt.executeUpdate("insert into SignatureInstruction values('"+sm.getShareAccType()+"',"+sm.getShareNumber()+","+sm.getCustomerId()+",'"+rs_name.getString("name")+"',null,0,0,'"+sm.getOperation()+"')");//suraj removed the comment on 31/07/2008
				}
				
			}
			if(type==2 && sm.getShareStatus().equals("P"))
			{
				Statement stmt_mstr=conn.createStatement();
				ResultSet rs_mstr1=null,rs_mstr2=null,rs_mstr3=null,rs_mstr4=null,rs_mstr5=null;
				int start=0,end=0;
				System.out.println("verification...type=2 &&sh_ind=P for cert");
				ResultSet rs2=stmt.executeQuery("select dist_no_from,dist_no_to  from ShareTransaction where ac_no="+sm.getShareNumber()+" and ac_type='"+sm.getShareAccType()+"' and ve_user is not null  order by trn_no");
				//double numofshares=sm.getNumberofShares();
				int numofshares=(int)(sm.getShareBalance());
				System.out.println("@@@@@@@@@The no.of Shares :"+numofshares);
				rs2.last();
				int count=rs2.getRow();
				rs2.beforeFirst();
				aa:if(count==1)
				{
					while(rs2.next() && numofshares>0)
					{
						start=rs2.getInt(1);
						end=rs2.getInt(2);
						if(start==0 && end==0)
							break aa;
						
						System.out.println("@@@@start:"+start);
						System.out.println("@@@@end:"+end);
						Statement stmt2=conn.createStatement();
						if(((end-start)+1)<numofshares)
						{
							stmt2.executeUpdate("update CertificateMaster set refunddt=date_format(sysdate(),'%d/%m/%Y') where sh_dist_no between "+ start +" and "+end);
							numofshares=numofshares-(end-start+1);
							stmt.executeUpdate("update ShareTransaction set dist_no_from="+start+",dist_no_to="+end+" where ac_no="+sm.getShareNumber()+" and trn_no="+trnno+"");
						}
						else if(((end-start)+1)>=numofshares)
						{
							System.out.println("@@@@trnno:"+trnno);
							stmt2.executeUpdate("update CertificateMaster set refunddt=date_format(sysdate(),'%d/%m/%Y') where sh_dist_no between "+ start +" and "+(start+numofshares-1));
							stmt.executeUpdate("update ShareTransaction set dist_no_from="+start+",dist_no_to="+(start+numofshares-1)+" where ac_no="+sm.getShareNumber()+" and trn_no="+trnno+"");
						}
					}
				}
				//Added by Karhi
				else if(count>1)
				{
					
					System.out.println("Counnt>1.........");
					rs_mstr1=stmt_mstr.executeQuery("select * from CertificateMaster where sh_ac_no="+sm.getShareNumber()+" and sh_ac_type='"+sm.getShareAccType()+"' and refunddt is null");
					rs_mstr1.last();
					int noof_records=rs_mstr1.getRow();
					System.out.println("No.of_records---->"+noof_records);
					if(noof_records!=0)
					{
						rs_mstr1.beforeFirst();
						if(noof_records>=numofshares && numofshares>1)
						{
							rs_mstr2=stmt_mstr.executeQuery("select * from CertificateMaster where sh_ac_no="+sm.getShareNumber()+" and sh_ac_type='"+sm.getShareAccType()+"' and refunddt is null limit "+numofshares+" ");
							rs_mstr2.next();
							int c=rs_mstr2.getInt("sh_dist_no");
							System.out.println("C---->"+c);
							/*rs_mstr2.last();
							 rs_mstr2.beforeFirst();*/
							int i=1;
							boolean t=true;
							while(t)
							{
								int v=rs_mstr2.getInt("sh_dist_no");
								System.out.println("V---->"+v);
								rs_mstr2.next();
								int s=v+1;
								System.out.println("S---->"+s);
								if(s==rs_mstr2.getInt("sh_dist_no"))
								{
									stmt.executeUpdate("update ShareTransaction set dist_no_from="+c+",dist_no_to="+s+" where ac_no="+sm.getShareNumber()+" and ac_type='"+sm.getShareAccType()+"' and trn_no="+trnno+" ");
									stmt.executeUpdate("update CertificateMaster set refunddt=date_format(sysdate(),'%d/%m/%Y') where sh_ac_no="+sm.getShareNumber()+" and sh_dist_no between "+c+" and "+s+" ");
									i++;
									System.out.println("i---->"+i);
									if(i==numofshares)
										t=false;
									
								}
								else
								{
									System.out.println("i--->"+i);
									PreparedStatement ps1=null;
									rs_mstr3=stmt_mstr.executeQuery("select * from CertificateMaster where sh_ac_no="+sm.getShareNumber()+" and sh_ac_type='"+sm.getShareAccType()+"' and refunddt is null limit "+(numofshares-i)+" ");
									rs_mstr3.next();
									int d=rs_mstr3.getInt("sh_dist_no");
									rs_mstr3.last();
									rs_mstr3.beforeFirst();
									int e=0;
									while(rs_mstr3.next())
									{
										stmt.executeUpdate("update CertificateMaster set refunddt='"+date+"' where sh_ac_no="+sm.getShareNumber()+"  and sh_ac_type='"+sm.getShareAccType()+"' and sh_dist_no="+rs_mstr3.getInt("sh_dist_no")+" ");
										e=rs_mstr3.getInt("sh_dist_no");
										
									}
									rs_mstr4=stmt_mstr.executeQuery("select * from ShareTransaction where ac_no="+sm.getShareNumber()+" and ac_type='"+sm.getShareAccType()+"' and trn_no="+trnno+" ");
									rs_mstr4.next();
									ps1=conn.prepareStatement("insert into ShareTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
									ps1.setString(1,rs_mstr4.getString("ac_type"));
									ps1.setInt(2,rs_mstr4.getInt("trn_no"));
									ps1.setInt(3,rs_mstr4.getInt("ac_no"));
									ps1.setInt(4,rs_mstr4.getInt("trn_seq"));
									ps1.setString(5,rs_mstr4.getString("trn_date"));
									ps1.setString(6,rs_mstr4.getString("trn_type"));
									ps1.setFloat(7,rs_mstr4.getFloat("trn_amt"));
									ps1.setInt(8,rs_mstr4.getInt("ref_no"));
									ps1.setString(9,rs_mstr4.getString("trn_narr"));
									ps1.setString(10,rs_mstr4.getString("trn_mode"));
									ps1.setString(11,rs_mstr4.getString("trn_source"));
									ps1.setString(12,rs_mstr4.getString("cd_ind"));
									ps1.setString(13,rs_mstr4.getString("alt_ind"));
									ps1.setString(14,rs_mstr4.getString("susp_ind"));
									ps1.setFloat(15,rs_mstr4.getFloat("share_bal"));
									ps1.setInt(16,d);
									ps1.setInt(17,e);
									ps1.setString(18,rs_mstr4.getString("sh_cert_no"));
									ps1.setString(19,rs_mstr4.getString("sh_cert_dt"));
									ps1.setString(20,rs_mstr4.getString("cert_prtd"));
									ps1.setString(21,rs_mstr4.getString("markdel"));
									ps1.setString(22,rs_mstr4.getString("de_user"));
									ps1.setString(23,rs_mstr4.getString("de_tml"));
									ps1.setString(24,rs_mstr4.getString("de_date"));
									ps1.setString(25,rs_mstr4.getString("ve_user"));
									ps1.setString(26,rs_mstr4.getString("ve_date"));
									ps1.setString(27,rs_mstr4.getString("ve_tml"));
									ps1.executeUpdate();
									t=false;
								}
							}
						}
						else if(noof_records<numofshares)
						{
							rs_mstr5=stmt_mstr.executeQuery("select trn_no,dist_no_from,dist_no_to from ShareTransaction st, ShareMaster sm where st.ac_no="+sm.getShareNumber()+" and st.ac_type='"+sm.getShareAccType()+"' and  sm.ac_no=st.ac_no and sm.ac_type=st.ac_type and st.trn_type='W' and st.ve_user is null order by trn_no desc limit 1");
							rs_mstr5.next();
							start=rs_mstr5.getInt(3)+1;
							end=start-1+(int)numofshares;
							stmt.executeUpdate("update CertificateMaster set refunddt='"+date+"' where sh_dist_no between "+ start +" and "+end);
							numofshares=numofshares-(end-start+1);
							stmt.executeUpdate("update ShareTransaction set dist_no_from="+start+",dist_no_to="+end+" where ac_no="+sm.getShareNumber()+" and trn_no="+trnno+"");
						}
						else if(numofshares==1)
						{
							rs_mstr2=stmt_mstr.executeQuery("select * from CertificateMaster where sh_ac_no="+sm.getShareNumber()+" and sh_ac_type='"+sm.getShareAccType()+"' and refunddt is null limit "+numofshares+" ");
							rs_mstr2.next();
							int c=rs_mstr2.getInt("sh_dist_no");
							stmt.executeUpdate("update ShareTransaction set dist_no_from="+c+",dist_no_to="+c+" where ac_no="+sm.getShareNumber()+" and ac_type='"+sm.getShareAccType()+"' and trn_no="+trnno+" ");
							stmt.executeUpdate("update CertificateMaster set refunddt='"+date+"' where sh_ac_no="+sm.getShareNumber()+" and sh_dist_no="+c+" ");
						}
					}
				}
			}
			
			if(type==1 || type==2)
			{
				Statement stmt_mstr=conn.createStatement();
				ResultSet rs_mstr=null;
				System.out.println("The transaction number"+trnno);
				System.out.println("The share acc type"+sm.getShareAccType());
				System.out.println("The acount no"+sm.getShareNumber());
				
				rs_mstr=stmt_mstr.executeQuery("select trn_seq,share_bal from ShareTransaction where trn_no="+trnno+" and ac_type='"+sm.getShareAccType()+"' and ac_no="+sm.getShareNumber()+"");
				rs_mstr.next();
				int trn_seq=rs_mstr.getInt("trn_seq");
				double share_val=rs_mstr.getInt("share_bal");
				System.out.println("sm.getShareNumber()}}}}}}"+sm.getShareNumber());
				System.out.println("trn_seq}}}}}}}}}"+trn_seq);
				System.out.println("==============UPDATE LST_TRN_NO=========");
				if(sm.getShareStatus().equals("P"))
					stmt_mstr.executeUpdate("update ShareMaster set lst_trn_seq="+trn_seq+" where ac_type='"+sm.getShareAccType()+"' and ac_no="+sm.getShareNumber()+" ");
				else if(sm.getShareStatus().equals("T"))
					stmt_mstr.executeUpdate("update ShareMaster set lst_trn_seq="+trn_seq+" where ac_type='"+sm.getShareAccType()+"' and ac_no="+sm.getTempShareNumber()+" ");
				System.out.println("=============UPDATED LST_TRN_NO IN SHAREMASTER============");
				rs_mstr.close();
			}
			
			if(type==2 || (type==1 && sm.getShareStatus().equals("T")))
			{
				System.out.println("verification...type=2||type==1&&sh_ind=T");
				if(type==1)
					//stmt.executeUpdate("update ShareTransaction set trn_amt="+(sm.getShareBalance()*sm.getShareVal())+",ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date=date_format(sysdate(),'%d/%m/%Y %H:%i:%s') where trn_no="+trnno);
					stmt.executeUpdate("update ShareTransaction set trn_amt="+(sm.getNumberofShares()*sm.getShareVal())+",ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date='"+sm.uv.getVerDate()+"' where trn_no="+trnno);
				else
					stmt.executeUpdate("update ShareTransaction set ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date='"+sm.uv.getVerDate()+"' where trn_no="+trnno);
				//stmt.executeUpdate("update ShareTransaction set trn_amt="+(sm.getNumberofShares()*sm.getShareVal())+",ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date='"+sm.uv.getVerDate()+"' where trn_no="+trnno);
			}
			
			if(type==1 && sm.getShareStatus().equals("P"))
			{
				
				System.out.println("entering sharestautus==P");
				//stmt.executeUpdate("update ShareTransaction set share_bal=share_bal+"+sm.getShareBalance()+",trn_amt="+(sm.getShareBalance()*sm.getShareVal())+",ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date=date_format(sysdate(),'%d/%m/%Y %H:%i:%s'),dist_no_from="+cfrom+",dist_no_to=share_bal+"+(cfrom-1)+" where trn_no="+trnno);
				//stmt.executeUpdate("update ShareTransaction set trn_amt="+(sm.getShareBalance()*sm.getShareVal())+",ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date=date_format(sysdate(),'%d/%m/%Y %H:%i:%s'),dist_no_from="+cfrom+",dist_no_to=share_bal+"+(cfrom-1)+" where trn_no="+trnno);
				stmt.executeUpdate("update ShareTransaction set trn_amt="+(sm.getNumberofShares()*sm.getShareVal())+",ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date='"+sm.uv.getVerDate()+"',dist_no_from="+cfrom+",dist_no_to="+((cfrom-1)+sm.getNumberofShares())+",sh_cert_no="+shno+" where trn_no="+trnno);
				//stmt.executeUpdate("update ShareTransaction set ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date=date_format(sysdate(),'%d/%m/%Y %H:%i:%s'),dist_no_from="+cfrom+",dist_no_to=share_bal+"+(cfrom-1)+" where trn_no="+trnno);
				stmt.executeUpdate("update ShareMaster set share_val=share_val+"+(sm.getNumberofShares()*sm.getShareVal()) +"where ac_type='"+sm.getShareAccType()+"' and ac_no="+sm.getShareNumber());//(sm.getNumberofShares()*sm.getShareVal())  Amzad
				System.out.println("verification...type=1 &&sh_ind=P");
			}
			
			if(type==1 && sm.getShareStatus().equals("T"))
			{
				
				System.out.println("entering sharestautus==T");
				//stmt.executeUpdate("update ShareTransaction set share_bal=share_bal+"+sm.getShareBalance()+",trn_amt="+(sm.getShareBalance()*sm.getShareVal())+",ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date=date_format(sysdate(),'%d/%m/%Y %H:%i:%s'),dist_no_from="+cfrom+",dist_no_to=share_bal+"+(cfrom-1)+" where trn_no="+trnno);
				//stmt.executeUpdate("update ShareTransaction set trn_amt="+(sm.getShareBalance()*sm.getShareVal())+",ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date=date_format(sysdate(),'%d/%m/%Y %H:%i:%s'),dist_no_from="+cfrom+",dist_no_to=share_bal+"+(cfrom-1)+" where trn_no="+trnno);
				//stmt.executeUpdate("update ShareTransaction set ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date=date_format(sysdate(),'%d/%m/%Y %H:%i:%s'),dist_no_from="+cfrom+",dist_no_to=share_bal+"+(cfrom-1)+" where trn_no="+trnno);
				stmt.executeUpdate("update ShareTransaction set ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date='"+sm.uv.getVerDate()+"' where trn_no="+trnno);
				stmt.executeUpdate("update ShareMaster set share_val=share_val+"+(sm.getNumberofShares()*sm.getShareVal()) +"where ac_type='"+sm.getShareAccType()+"' and temp_no="+sm.getTempShareNumber());
				System.out.println("verification...type=1 &&sh_ind=T");
			}
			
			if(type==2 && sm.getShareStatus().equals("T"))
			{	stmt.executeUpdate("update ShareMaster set mem_cl_date='"+date+"' where ac_type='"+sm.getShareAccType()+"' and temp_no="+sm.getShareNumber()+" and sh_ind='T'");
			System.out.println("verification...type=2 &&sh_ind=T");
			}
			
			if(type==2 && sm.getShareStatus().equals("P"))
			{
				System.out.println("verification...type=1 &&sh_ind=P");
				System.out.println("Share value"+(sm.getShareBalance()*sm.getShareVal()));
				stmt.executeUpdate("update ShareMaster set share_val=share_val-"+(sm.getShareBalance()*sm.getShareVal())+" where ac_no="+sm.getShareNumber()+" and ac_type='"+sm.getShareAccType()+"'");
				ResultSet rs_w=stmt.executeQuery("select share_val from ShareMaster where ac_no="+sm.getShareNumber()+" and ac_type='"+sm.getShareAccType()+"'");
				rs_w.next();
				double shr_val=rs_w.getDouble("share_val");
				if(shr_val==0)//For Account Closure
					stmt.executeUpdate("update ShareMaster set mem_cl_date='"+date+"' where ac_no="+sm.getShareNumber()+" and ac_type='"+sm.getShareAccType()+"'");
			}
			System.out.println("data after");
			System.out.println("sm.getShareBalance()="+sm.getShareBalance());
			System.out.println("sm.getShareVal()="+sm.getShareVal());
			System.out.println("sm.getShareNumber()="+sm.getShareNumber());
			System.out.println("trn_amt="+sm.getShareBalance()*sm.getShareVal());
			System.out.println("trn_number="+trnno);
			
			GLTransObject trnobj=new GLTransObject();
			trnobj.setTrnDate(date);
			String trntype,cdind;
			if(type==0 || type==1)
			{
				trntype="A";//A-->Allotment
				cdind="C";
			}
			else
			{
				trntype="W";//W-->Withdrawal
				cdind="D";
			}
			
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			System.out.println("type........."+type);
			System.out.println("gl code"+sm.getGLCode());
			System.out.println("trntype"+trntype);
			System.out.println("cdind"+cdind);
			System.out.println("type"+type);
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			
			System.out.println("acctype"+sm.getShareAccType());
			System.out.println("mem cat"+sm.getMemberCategory());
			System.out.println("sh ind"+sm.getShareStatus());
			
			String prm_type=null;
			if(sm.getShareStatus().equals("P"))
				prm_type="D";
			if(sm.getShareStatus().equals("T"))
				prm_type="S";
			
			
			System.out.println("prm_type"+prm_type+" "+sm.getShareAccType()+" "+sm.getMemberCategory()+" "+prm_type);
			
			ResultSet rs_gl=stmt.executeQuery("select prm_gl_code,prm_gl_type from ShareParam where ac_type='"+sm.getShareAccType()+"' and mem_cat="+sm.getMemberCategory()+" and prm_ty='"+prm_type+"'");
			rs_gl.next();
			
			String gl_code=rs_gl.getString("prm_gl_code");
			String gl_type=rs_gl.getString("prm_gl_type");
			//String gl_name=rs_gl.getString("");
			System.out.println("gl_code:"+gl_code);
			System.out.println("gl_type:"+gl_type);
			//System.out.println("gl_name"+gl_name);
			rs_gl.close();
			
			double shtot_amt=0;
			
			ResultSet rs=stmt.executeQuery("select mult_by from GLPost where ac_type='"+sm.getShareAccType()+"' and trn_type='"+trntype+"' and cr_dr='"+cdind+"' and gl_code='"+gl_code+"'");
			
			System.out.println("if Before writing into Share GL");
			if(rs.next())
			{
				int mult_by=rs.getInt(1);
				trnobj.setTrnDate(date);
				trnobj.setGLType(gl_type);
				/*if(sm.getShareStatus().equals("T"))
				 trnobj.setGLCode(sm.getSuspGLCode());				
				 else
				 trnobj.setGLCode(sm.getGLCode());
				 */
				
				if(sm.getShareStatus().equals("T"))
					trnobj.setGLCode(gl_code);				
				else
					trnobj.setGLCode(gl_code);
				
				//trnobj.setGLName(gl_name);
				if(sm.getRecievedMode().equalsIgnoreCase("PO"))
					trnobj.setTrnMode("T");
				else
					trnobj.setTrnMode(sm.getRecievedMode());
				if(type==0 ||type==1)
				{
					if(mult_by>0){
						trnobj.setAmount(sm.getShareVal()*sm.getNumberofShares()*mult_by);
						System.out.println(sm.getShareVal());
						System.out.println(sm.getNumberofShares());
						System.out.println("The setamount when mult by is "+sm.getShareVal()*sm.getNumberofShares()*mult_by);
					}
					else{
						trnobj.setAmount(sm.getShareVal()*sm.getNumberofShares()*mult_by*(-1));
					}
				}
				else if(type==2)
				{
					/*if(type==1)
					 trnobj.setAmount(sm.getShareBalance()*sm.getShareVal()*rs.getInt(1));
					 else
					 trnobj.setAmount(sm.getShareBalance()*sm.getShareVal()*rs.getInt(1)*(-1));*/
					if(mult_by>0)
					{
						trnobj.setAmount(sm.getShareBalance()*sm.getShareVal()*mult_by);//suraj commented on 31/03/2008
						//trnobj.setAmount(sm.getShareBalance()*mult_by);
						System.out.println("share balanceeee"+sm.getShareBalance());
						System.out.println("SHre value"+sm.getShareVal());
						//cdind="C";
					}
					else
						trnobj.setAmount(sm.getShareBalance()*sm.getShareVal()*mult_by*(-1));
				}
				
				
				trnobj.setCdind(cdind);
				//trnobj.setAccType(sm.getShareAccType()+" "+sm.getShareStatus());
				trnobj.setAccType(sm.getShareAccType());
				
				if(sm.getShareStatus().equals("P"))
					trnobj.setAccNo(String.valueOf(sm.getShareNumber()));
				else
				{
					if(type==1)
						trnobj.setAccNo(String.valueOf(sm.getShareNumber()));
					else
						trnobj.setAccNo(String.valueOf(sm.getTempShareNumber()));
				}
				//Added by Karthi-->09/01/2006
				//commented by suraj
				ResultSet rs_trnseq;
				if(sm.getRecievedMode().equals("C"))
				{
					 rs_trnseq=stmt_trnseq.executeQuery("select max(trn_seq),max(ref_no) from ShareTransaction  where ac_no="+trnobj.getAccNo()+" and ac_type='"+trnobj.getAccType()+"' and trn_type='"+trntype+"'");
				}
				else //if(sm.getRecievedMode()=="T")
				{
			    rs_trnseq=stmt_trnseq.executeQuery("select max(trn_seq),max(trn_no) from ShareTransaction  where ac_no="+trnobj.getAccNo()+" and ac_type='"+trnobj.getAccType()+"' and trn_type='"+trntype+"'");
				}
				rs_trnseq.next();
				System.out.println("Accounttttttttt no"+trnobj.getAccNo());
				trnobj.setTrnSeq(rs_trnseq.getInt(1));
				trnobj.setRefNo(rs_trnseq.getInt(2));
				trnobj.setTrnType(trntype);
				trnobj.setVtml(sm.uv.getVerTml());
				trnobj.setVid(sm.uv.getVerId());
				trnobj.setVDate(sm.uv.getVerDate());
				System.out.println("Before writing into Share GL1");
                common_local.storeGLTransaction(trnobj);
				System.out.println("after writing into Share GL1");
				
				/**
				 * While Share Allotment time the bank collect the application fees. 
				 * so we have to enter GL entry for that also.
				 */
				if(shparam!=null)
				{
					System.out.println("Enter ing share param ");
					for(int i=0;i<shparam.length;i++)
					{
						if(shparam[i].isPaid())
						{
							System.out.println("check here for paid"+shparam[i].isPaid());
							System.out.println("The prm gl key is"+shparam[i].getPrm_gl_key());
							ResultSet rs1=cpstmt.executeQuery("select mult_by from GLPost where gl_code='"+shparam[i].getPrm_gl_key()+"' and ac_type='"+sm.getShareAccType()+"' and trn_type='R' and cr_dr='C'");
							rs1.next();
							trnobj.setGLCode(shparam[i].getPrm_gl_key());
							trnobj.setCdind("C");
							if(rs.getInt(1)>0)
								trnobj.setAmount(shparam[i].getPrm_amt()*rs.getInt(1));
							else
								trnobj.setAmount(shparam[i].getPrm_amt()*rs.getInt(1)*(-1));
							
							shtot_amt=shtot_amt+trnobj.getAmount();
                            common_local.storeGLTransaction(trnobj);
						}
					}
				}
				
				if(type==0)
				{
				misc_amt=sm.getAmount()-(sm.getNumberofShares()*sm.getShareVal()+shtot_amt);
				System.out.println(".....getAmount%....."+sm.getAmount());
				System.out.println(".....getAmount1%....."+sm.getNumberofShares());
				System.out.println(".....getAmount2%....."+sm.getShareVal());
				System.out.println(".....getAmount3%....."+shtot_amt);
				
				System.out.println(".....misc....%.."+misc_amt);
				
				}
				else if(type==1)
				{
					misc_amt=sm.getAmount_paid()-(sm.getNumberofShares()*sm.getShareVal()+shtot_amt);//suraj
					System.out.println(".....getAmount%....."+sm.getAmount_paid());
					System.out.println(".....getAmount1%....."+sm.getNumberofShares());
					System.out.println(".....getAmount2%....."+sm.getShareVal());
					System.out.println(".....getAmount3%....."+shtot_amt);
					
					System.out.println(".....misc....%.."+misc_amt);
					
					
				}
				else
				{
				System.out.println("..2...getAmount%....."+sm.getAmount());
				System.out.println("...2..getAmount1%....."+sm.getNumberofShares());
				System.out.println("...2..getAmount2%....."+sm.getShareVal());
				System.out.println("..2...getAmount3%....."+shtot_amt);
				
				System.out.println(".2....misc....%.."+misc_amt);
				}
				if(misc_amt>=0)
				{
					System.out.println(" @@** The Extra Amount : "+sm.getMiscAmount());
					ResultSet rs_misc=cpstmt.executeQuery("select gl_type,gl_code from GLKeyParam where ac_type='1012000' and code=1");
					rs_misc.next();
					trnobj.setGLCode(rs_misc.getString("gl_code"));
					trnobj.setGLType(rs_misc.getString("gl_type"));
					trnobj.setCdind("C");
					trnobj.setAmount(misc_amt);
					System.out.println("SURAJ");
					common_local.storeGLTransaction(trnobj);
				}
			}
			
			if(sm.getRecievedMode().equals("PO") &&(type==2))
			{
				System.out.println("Inside po");
				PayOrderObject po = new PayOrderObject();
				po.setPOType("X");
				try
				{//Added By Karthi-->20/01/2006
					Statement stmt_cust=conn.createStatement();
					ResultSet rs_cust=null,rs_glname=null;
					if(sm.getShareStatus().equals("P"))
						rs_cust=stmt_cust.executeQuery("select distinct sm.ac_no,sm.cid,concat_ws('',cm.fname,cm.mname,cm.lname)as name, cm.custtype from ShareMaster sm,CustomerMaster cm where sm.ac_type='"+sm.getShareAccType()+"'and sm.ac_no="+sm.getShareNumber()+" and cm.cid=sm.cid");
					else
						rs_cust=stmt_cust.executeQuery("select distinct sm.temp_no,sm.cid,concat_ws('',cm.fname,cm.mname,cm.lname)as name, cm.custtype from ShareMaster sm,CustomerMaster cm where sm.ac_type='"+sm.getShareAccType()+"'and sm.temp_no="+sm.getTempShareNumber()+" and cm.cid=sm.cid");
					rs_cust.next();
					po.setPOPayee(rs_cust.getString("name"));
					po.setCustType(rs_cust.getInt("custtype"));
				}catch(SQLException se){se.printStackTrace();}
				
				Statement stmt_glname=conn.createStatement();
				Statement stmt_shtran=conn.createStatement();
				ResultSet rs_glname=null;
				po.setPOAccType(sm.getShareAccType());
				if(sm.getShareStatus().equals("P"))
					po.setPOAccNo(sm.getShareNumber());
				else
					po.setPOAccNo(sm.getTempShareNumber());
				
				po.setPOGlCode(Integer.parseInt(trnobj.getGLCode()));
				po.setPOGlType(trnobj.getGLType());
				rs_glname=stmt_glname.executeQuery("select gl_name from GLMaster where gl_type='"+trnobj.getGLType()+"' and gl_code="+trnobj.getGLCode()+" and ((from_date<='"+Validations.convertYMD(date)+"' and to_date is null) or (from_date<='"+Validations.convertYMD(date)+"' and to_date>='"+Validations.convertYMD(date)+"'))");
				rs_glname.next();
				po.setPOGlName(rs_glname.getString("gl_name"));
				po.setCommissionAmount(0);
				po.uv.setUserTml(sm.uv.getVerTml());
				po.uv.setUserId(sm.uv.getVerId());
				po.uv.setUserDate(sm.uv.getUserDate());
				po.uv.setVerId(sm.uv.getVerId());
				po.uv.setVerTml(sm.uv.getVerTml());
				po.uv.setVerDate(sm.uv.getVerDate());
				po.setPOAmount(trnobj.getAmount());
				int value=cremote.storePayOrder(po);
				System.out.println("Inside po"+value);
				Statement newstmt=conn.createStatement();
				ResultSet rs2 = newstmt.executeQuery("select gl_code,gl_type from GLKeyParam where ac_type= 1016001 and code=1");
				if(rs2.next())
				{
					GLTransObject trnobj1=new GLTransObject();
					trnobj1.setTrnDate(date);
					trnobj1.setGLType(rs2.getString(2));
					trnobj1.setGLCode(rs2.getString(1));
					trnobj1.setTrnMode("T");
					trnobj1.setAmount(trnobj.getAmount());
					trnobj1.setCdind("C");
					trnobj1.setAccType("1016001");
					
					/*if(sm.getShareStatus().equals("P"))
					 trnobj1.setAccNo(String.valueOf(sm.getShareNumber()));
					 else
					 trnobj1.setAccNo(String.valueOf(sm.getTempShareNumber()));*/
					
					trnobj1.setAccNo(String.valueOf(value));
					trnobj1.setTrnType("R");
					trnobj1.setRefNo(value);						
					trnobj1.setVtml(sm.uv.getVerTml());
					trnobj1.setVid(sm.uv.getVerId());
					trnobj1.setVDate(sm.uv.getVerDate());
					System.out.println("suraj 1");
                    common_local.storeGLTransaction(trnobj1);
					newstmt.executeUpdate("update GLTransaction set ref_no="+value+" where gl_type='"+trnobj.getGLType()+"'and gl_code="+trnobj.getGLCode()+" and trn_date='"+getSysDate()+"'and ref_ac_no="+trnobj.getAccNo()+" ");
				}
				if(sm.getShareStatus().equalsIgnoreCase("P"))
					stmt_shtran.executeUpdate("update ShareTransaction set ref_no="+value+",trn_narr='PayOrdr. "+value+"' where ac_no="+sm.getShareNumber()+" and ac_type='"+sm.getShareAccType()+"' and trn_date='"+getSysDate()+"'and trn_mode='PO' and trn_no="+trnno+" ");
				else
					stmt_shtran.executeUpdate("update ShareTransaction set ref_no="+value+",trn_narr='PayOrdr. "+value+"' where ac_no="+sm.getTempShareNumber()+" and ac_type='"+sm.getShareAccType()+"' and trn_date='"+getSysDate()+"'and trn_mode='PO' and trn_no="+trnno+" ");
				System.out.println("Inside po");		
			}
			if(sm.getRecievedMode().equals("C") && (type==2))
			{
				//stmt.executeUpdate("update DayCash set ac_no="+shnumber+",attached='T' where scroll_no="+sm.getRecievedAccno());
				//Changed by Karthi
				int no=0;
				if(sm.getShareStatus().equals("P"))
					no=sm.getShareNumber();
				else
					no=sm.getTempShareNumber();
				
				PreparedStatement pstmt_update=null;
				pstmt_update=conn.prepareStatement("update DayCash set ve_user=?,ve_tml=?,ve_date=? where ac_type='"+sm.getShareAccType()+"' and ac_no="+no+" ");
				pstmt_update.setString(1,sm.uv.getVerId());
				pstmt_update.setString(2,sm.uv.getVerTml());
				pstmt_update.setString(3,sm.uv.getVerDate());
				pstmt_update.executeUpdate();
				
				//PreparedStatement pstmt1=conn.prepareStatement("insert into DayCash (scroll_no,trn_date,ac_type,ac_no,csh_amt,cd_ind,vch_type,attached,vch_no,de_user,de_tml,de_date,ve_user,ve_tml,ve_date) values (0,?,?,?,?,'C','W','F',?,?,?,?,?,?,date_format(sysdate(),'%d/%m/%Y'))");
				/*PreparedStatement pstmt1=conn.prepareStatement("insert into DayCash (scroll_no,trn_date,ac_type,ac_no,trn_type,csh_amt,cd_ind,name,vch_no,vch_type,trn_seq,attached,de_user,de_tml,de_date,ve_user,ve_tml,ve_date,share_category) values (0,?,?,?,'P',?,'C',?,?,?,?,'F',?,?,?,?,?,?,?)");//Karthi
				 System.out.println("System date"+getSysDate());
				 
				 pstmt1.setString(1,getSysDate());
				 System.out.println("System date"+getSysDate());
				 pstmt1.setString(2,sm.getShareAccType());
				 pstmt1.setInt(3,no);
				 pstmt1.setDouble(4,sm.getAmount());
				 ResultSet rs_name=stmt_name.executeQuery("select concat_ws(' ',fname,mname,lname)as name from CustomerMaster where cid='"+sm.getCustomerId()+"'");
				 rs_name.next();
				 pstmt1.setString(5,rs_name.getString("name"));
				 pstmt1.setInt(6,trnno);
				 
				 if(sm.getWithdrawalType()==0)
				 pstmt1.setString(7,"W");
				 else
				 pstmt1.setString(7,"C");
				 
				 ResultSet rs_trnseq=stmt_trnseq.executeQuery("select  lst_trn_seq from ShareMaster where ac_type='"+sm.getShareAccType()+"' and ac_no="+no+" ");
				 rs_trnseq.next();
				 pstmt1.setInt(8,rs_trnseq.getInt("lst_trn_seq"));
				 
				 pstmt1.setString(9,sm.uv.getUserId());
				 pstmt1.setString(10,sm.uv.getUserTml());
				 pstmt1.setString(11,common_remote.getSysDateTime());
				 pstmt1.setString(12,sm.uv.getVerId());
				 pstmt1.setString(13,sm.uv.getVerTml());					
				 pstmt1.setString(14,sm.uv.getVerTml());
				 pstmt1.setString(14,common_remote.getSysDateTime());
				 
				 if(sm.getMemberCategory()==1)
				 pstmt1.setString(15,"Regular");
				 else if(sm.getMemberCategory()==2)
				 pstmt1.setString(15,"Associate");
				 else if(sm.getMemberCategory()==3)
				 pstmt1.setString(15,"Nominal");
				 else
				 pstmt1.setString(15,null);
				 
				 pstmt1.executeUpdate();			*/
				
				//rs=stmt.executeQuery("select mult_by,gp.gl_code,gk.gl_type from GLPost gp,GLKeyParam gk where gp.ac_type='1019001' and gk.ac_type='1019001' and trn_type='R' and cr_dr='C'");
				rs=stmt.executeQuery("select gl_code,gl_type from GLKeyParam where ac_type=1019001");
				rs.next();
				trnobj.setGLCode(rs.getString("gl_code"));
				trnobj.setGLType(rs.getString("gl_type"));
				//trnobj.setAccType("1019001");//
				//trnobj.setAccNo(String.valueOf(sm.getRecievedAccno()));
				//trnobj.setAccNo("0");//
				trnobj.setCdind("C");
				//trnobj.setTrnType("");//
				//trnobj.setTrnSeq(0);//
				//trnobj.setAmount(sm.getAmount()*rs.getInt("mult_by"));
				trnobj.setAmount(sm.getShareVal()*sm.getShareBalance());//credit could be done here suraj
				System.out.println("credit................"+sm.getShareVal()*sm.getShareBalance()+"cdind"+trnobj.getCdind());
				trnobj.setRefNo(trnno);
				System.out.println("suraj 2");
                common_local.storeGLTransaction(trnobj);	
				
			}
			if((sm.getRecievedMode().equals("C")|| sm.getRecievedMode().equals("T")) && (type==0 || type==1))
			{
				if(type==0)
					stmt.executeUpdate("update DayCash set ac_no="+shnumber+",attached='T',trn_seq=1,ve_user='"+sm.uv.getVerId()+"',ve_tml='"+sm.uv.getVerTml()+"',ve_date='"+sm.uv.getVerDate()+"' where scroll_no="+sm.getRecievedAccno()+" ");
				else
					stmt.executeUpdate("update DayCash set ac_no="+shnumber+",attached='T',ve_user='"+sm.uv.getVerId()+"',ve_tml='"+sm.uv.getVerTml()+"',ve_date='"+sm.uv.getVerDate()+"' where scroll_no="+sm.getRecievedAccno());
				
				//rs=stmt.executeQuery("select mult_by,GLPost.gl_code from GLPost,GLKeyParam  where GLKeyParam.ac_type='1019001' and GLKeyParam.ac_type=GLPost.ac_type  and trn_type='P'  and cr_dr='D'");
				if(sm.getRecievedMode().equals("C"))
					rs=stmt.executeQuery("select gl_code,gl_type from GLKeyParam where ac_type=1019001");
				else
					rs=stmt.executeQuery("select gl_code,gl_type from GLKeyParam where ac_type="+sm.getRecievedAcctype());
				rs.next();
				trnobj.setGLCode(rs.getString("gl_code"));
				trnobj.setGLType(rs.getString("gl_type"));
				System.out.println("The gl code for recived mode is"+rs.getString("gl_code"));
				System.out.println("The gl_type fro recieved mode is"+rs.getString("gl_type"));
				//trnobj.setAccType("1019001");//
				//trnobj.setAccNo(String.valueOf(sm.getRecievedAccno()));
				//trnobj.setAccNo("0");//
				
				trnobj.setCdind("D");
				if(type==0){
					trnobj.setAmount(sm.getAmount());
					System.out.println("The set amount in set cdind D is"+sm.getAmount());
				}
				else if(type==1){
					trnobj.setAmount(sm.getAmount());
					System.out.println("the set amont in set cdind  is"+sm.getAmount_paid());
				}
				//trnobj.setAmount(sm.getAmount()*rs.getInt("mult_by"));
				
				/*if(type==0 || type==1)
					//trnobj.setAmount(sm.getShareVal()*sm.getNumberofShares()+shtot_amt+misc_amt);
					//trnobj.setAmount(sm.getAmount());//suraj
					trnobj.setAmount(sm.getAmount_paid());*/
				/*else if(type==2)
				{
					trnobj.setCdind("C");
					//trnobj.setAmount(sm.getShareBalance()*sm.getShareVal());
					System.out.println("suraj..................."+sm.getShareBalance()*sm.getShareVal());
				}*/
					//trnobj.setAmount(sm.getAmount());//suraj
				
					//trnobj.setAmount(sm.getAmount_paid()); suraj commented here
					
				//trnobj.setTrnType("");//
				//trnobj.setTrnSeq(0);//
				System.out.println("suraj 3");
                common_local.storeGLTransaction(trnobj);
				shtot_amt=0;
				
				Statement stmt_tml=conn.createStatement();
				Statement stmt_tml1=conn.createStatement();
				ResultSet rs_tml=null;
				try
				{
					System.out.println("Select de_tml from DayCash........");
					rs_tml=stmt_tml.executeQuery("select de_tml from DayCash where ac_type='"+sm.getShareAccType()+"' and ac_no="+shnumber+" and scroll_no="+sm.getRecievedAccno()+" and ve_date='"+sm.uv.getVerDate()+"'");
					if(rs_tml.next())
						stmt_tml1.executeUpdate("update ShareTransaction set trn_source='"+rs_tml.getString("de_tml")+"' where ac_type='"+sm.getShareAccType()+"' and ac_no="+shnumber+" and ref_no="+sm.getRecievedAccno()+" ");
					else
						System.out.println("The rs_tml is empty........");
				}catch(SQLException ex){
					ex.printStackTrace();
					sessionContext.setRollbackOnly();
				}
				//code changed by amzad 
				if(sm.getRecievedMode().equals("T") )
				{
				try{
					
					AccountTransObject accounttransobject=new AccountTransObject();
					System.out.println("sm.getRecievedAcctype()=="+sm.getRecievedAcctype());
					System.out.println("sm.getRecievedAccno()=="+sm.getRecievedAccno());
					System.out.println("sm.setamount"+sm.getShareBalance());
					accounttransobject.setAccType(sm.getRecievedAcctype());
					accounttransobject.setAccNo(sm.getRecievedAccno());
					//accounttransobject.setName(sm.getName());//Karthi
					//accounttransobject.setTransAmount(sm.getAmount()+shtot_amt);
					
					accounttransobject.setTransDate(date);
					if(type==1)
						accounttransobject.setTransAmount(sm.getAmount());//changed from getAmount to getAmountpaid
					else if(type==0)
						accounttransobject.setTransAmount(sm.getAmount());
					else
						accounttransobject.setTransAmount(sm.getShareBalance()*sm.getShareVal());// suraj commented on 31/03/2008
						//accounttransobject.setTransAmount(sm.getShareBalance());
					
					accounttransobject.setTransMode("T");
					accounttransobject.setTransSource(sm.uv.getVerTml());
					
					if(type==2)
					{
						accounttransobject.setTransType("R");
						accounttransobject.setCdInd("C");
					}
					else if(type==1|| type==0)
					{
						accounttransobject.setTransType("P");	
						accounttransobject.setCdInd("D");
					}
					if(sm.getShareStatus().equals("P"))
						accounttransobject.setTransNarr(sm.getShareAccType() +" "+sm.getShareNumber());
					else
					{
						if(type!=1)
							//accounttransobject.setTransNarr(sm.getShareAccType()+" T "+sm.getTempShareNumber());
							accounttransobject.setTransNarr(sm.getShareAccType()+"  "+sm.getTempShareNumber());
						else
							//accounttransobject.setTransNarr(sm.getShareAccType()+" T "+sm.getShareNumber());
							accounttransobject.setTransNarr(sm.getShareAccType()+"  "+sm.getShareNumber());
					}
					accounttransobject.uv.setUserTml(sm.uv.getUserTml());
					accounttransobject.uv.setUserId(sm.uv.getUserId());
					accounttransobject.uv.setUserDate(sm.uv.getUserDate());
					accounttransobject.uv.setVerTml(sm.uv.getVerTml());
					accounttransobject.uv.setVerId(sm.uv.getVerId());
					accounttransobject.uv.setVerDate(sm.uv.getVerDate());
					cremote.storeAccountTransaction(accounttransobject);
				}catch(Exception e){e.printStackTrace();}
				}
				
				
				
				
			}
			else if(sm.getRecievedMode().equals("T") || sm.getRecievedMode().equals("B"))// Amzad
			{
				AccountTransObject accounttransobject=new AccountTransObject();
				System.out.println("sm.getRecievedAcctype()=="+sm.getRecievedAcctype());
				System.out.println("sm.getRecievedAccno()=="+sm.getRecievedAccno());
				accounttransobject.setAccType(sm.getRecievedAcctype());
				accounttransobject.setAccNo(sm.getRecievedAccno());
				//accounttransobject.setName(sm.getName());//Karthi
				//accounttransobject.setTransAmount(sm.getAmount()+shtot_amt);
				
				accounttransobject.setTransDate(date);
				if(type==1)
					accounttransobject.setTransAmount(sm.getAmount());
				else if(type==0)
					accounttransobject.setTransAmount(sm.getAmount());
				else
					accounttransobject.setTransAmount(sm.getShareBalance()*sm.getShareVal());// suraj commented on 31/03/2008
				//accounttransobject.setTransAmount(sm.getShareBalance());
				
				accounttransobject.setTransMode("T");
				accounttransobject.setTransSource(sm.uv.getVerTml());
				
				if(type==2)
				{
					accounttransobject.setTransType("R");
					accounttransobject.setCdInd("C");
				}
				else if(type==1|| type==0)
				{
					accounttransobject.setTransType("P");	
					accounttransobject.setCdInd("D");
				}
				if(sm.getShareStatus().equals("P"))
					accounttransobject.setTransNarr(sm.getShareAccType() +" "+sm.getShareNumber());
				else
				{
					if(type!=1)
						//accounttransobject.setTransNarr(sm.getShareAccType()+" T "+sm.getTempShareNumber());
						accounttransobject.setTransNarr(sm.getShareAccType()+"  "+sm.getTempShareNumber());
					else
						//accounttransobject.setTransNarr(sm.getShareAccType()+" T "+sm.getShareNumber());
						accounttransobject.setTransNarr(sm.getShareAccType()+"  "+sm.getShareNumber());
				}
				accounttransobject.uv.setUserTml(sm.uv.getUserTml());
				accounttransobject.uv.setUserId(sm.uv.getUserId());
				accounttransobject.uv.setUserDate(sm.uv.getUserDate());
				accounttransobject.uv.setVerTml(sm.uv.getVerTml());
				accounttransobject.uv.setVerId(sm.uv.getVerId());
				accounttransobject.uv.setVerDate(sm.uv.getVerDate());
				cremote.storeAccountTransaction(accounttransobject);
			}
			else if(sm.getRecievedMode().equalsIgnoreCase("G") && type==1)
			{
				ResultSet rs_clg=stmt.executeQuery("select gl_code,gl_type from GLKeyParam where ac_type='1011001' and code=1");
				rs_clg.next();
				trnobj.setGLCode(rs_clg.getString("gl_code"));
				trnobj.setGLType(rs_clg.getString("gl_type"));
				trnobj.setAccType("1011001");
				//trnobj.setAccNo(String.valueOf(sm.getRecievedAccno()));
				trnobj.setAccNo("0");
				
				trnobj.setCdind("D");
				
				//trnobj.setAmount(sm.getAmount()*rs.getInt("mult_by"));
				
				//trnobj.setAmount(sm.getShareBalance()*sm.getShareVal()+shtot_amt);
				trnobj.setAmount(sm.getAmount());
				trnobj.setTrnType("");
				trnobj.setTrnSeq(0);
				System.out.println("suraj 4");
                common_local.storeGLTransaction(trnobj);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}		
		return(1);
	}
	
	
	
	
	
	/*public int verifyShare(ShareMasterObject sm,int trnno,int type,ShareParamObject shparam[],String date)
	{
		Connection conn=null;
		double misc_amt=0;
		//Changed all queries wherever trn_amt was being updated in share master---trn_amt
		//is not updated during verification---------Swaran
		try{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			Statement cpstmt=conn.createStatement();
			Statement stmt_trnseq=conn.createStatement();
			Context ctx=getInitialContext();
			Object obj=ctx.lookup("COMMONWEB");
			commonServer.CommonHome home=(commonServer.CommonHome)obj;
			CommonRemote cremote=home.create();
			System.out.println("data b4");
			System.out.println("sm.getShareBalance()="+sm.getShareBalance());
			System.out.println("sm.getShareVal()="+sm.getShareVal());
			System.out.println("sm.getShareNumber()="+sm.getShareNumber());
			System.out.println("trn_amt="+sm.getShareBalance()*sm.getShareVal());
			System.out.println("trn_number="+trnno);
			System.out.println("No of shares "+sm.getNumberofShares());
			//type 0--new,1--additional,2--Withdrawal
			int shnumber=0;
			if(sm.getShareStatus().equals("T")&& sm.getBranchCode()!=1)
				shnumber=sm.getTempShareNumber();
			else
				shnumber=sm.getShareNumber();
			
			int cfrom=0,shno=0;
			System.out.println("verification..............");
			Used for Certificate Printing
			 *max_renewal_count-->Distinctive Number
			 *std_inst-->Sharecertificate Number
			 
			if((type==0 || type==1)&& sm.getShareStatus().equals("P"))//certificate number generation for new and additional share allotment
			{
				System.out.println("verification..............certificate number generation for new and additional share allotment");
				Statement stmt2=conn.createStatement();
				//ResultSet rs1=stmt2.executeQuery("select lst_certallot_no from GenParam");
				
				ResultSet rs1=stmt2.executeQuery("select max_renewal_count,std_inst from Modules where modulecode='"+sm.getShareAccType()+"'");
				rs1.next();
				cfrom=rs1.getInt(1)+1;
				shno=rs1.getInt(2)+1;
				rs1.close();
				for(int i=cfrom;i<(cfrom+sm.getNumberofShares());i++)
					stmt2.executeUpdate("insert into CertificateMaster values("+i+","+sm.getShareNumber()+",'"+sm.getShareAccType()+"','"+date+"',null)");
				//stmt2.executeUpdate("update GenParam set lst_certallot_no="+(cfrom+sm.getNumberofShares()-1));
				stmt2.executeUpdate("update Modules set max_renewal_count="+(cfrom+sm.getNumberofShares()-1)+",std_inst="+shno+" where modulecode='"+sm.getShareAccType()+"'");
			}
			*//**
			 * we will enter Consecutive entries in ‘Certificate Master’ for Permanent Shares with allotment date and refund date (refund date is null for Allotment time).This is for Permanent shares only.
			 * consecutive entries are depends upon the number of shares.
			 * we will update the refund date field in ‘Certificate Master’ table for Withdrawal Shares. Suppose if person having some 50 shares. He withdraw only 20 shares means we update first 20 shares refund date(refunddt field) in ‘Certificate Master’ table.
			 * For temporary shares at the time of convertion(temp. to perm.)we enter the entry to 'CertificateMaster' table. 
			 *//*
			if(type==0)//New Share verification
			{ 
				System.out.println("*************VERFICATION");
				  System.out.println(sm.getShareVal());
				  System.out.println(sm.getNumberofShares());
				System.out.println("*************VERFICATION");
				
				if(sm.getShareStatus().equals("T"))
				{
					if(sm.getBranchCode()!=1)
						stmt.executeUpdate("update ShareMaster set share_val="+(sm.getShareVal()*sm.getNumberofShares())+",ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date='"+sm.uv.getVerDate()+"' where ac_type='"+sm.getShareAccType()+"' and temp_no="+shnumber);
					else
						stmt.executeUpdate("update ShareMaster set share_val="+(sm.getShareVal()*sm.getNumberofShares())+",ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date='"+sm.uv.getVerDate()+"' where ac_type='"+sm.getShareAccType()+"' and  ac_no="+shnumber);
					//stmt.executeUpdate("update ShareTransaction set trn_amt="+(sm.getShareVal()*sm.getNumberofShares())+", ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date=date_format(sysdate(),'%d/%m/%Y %H:%i:%s') where ac_no="+shnumber+" and susp_ind='T'");
					stmt.executeUpdate("update ShareTransaction set trn_amt="+(sm.getShareVal()*sm.getNumberofShares())+",ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date='"+sm.uv.getVerDate()+"' where ac_type='"+sm.getShareAccType()+"' and ac_no="+shnumber+" and susp_ind='T'");
					System.out.println("new share verification.)T(.............");
					ResultSet rs_name=stmt.executeQuery("select concat_ws('',fname,mname,lname) as name from CustomerMaster cm,ShareMaster sm where sm.cid=cm.cid and sm.cid="+sm.getCustomerId());
					rs_name.next();
					//stmt.executeUpdate("insert into SignatureInstruction values('"+sm.getShareAccType()+"',"+sm.getTempShareNumber()+","+sm.getCustomerId()+",'"+rs_name.getString("name")+"',null,0,0,'Single')");
				}
				else
				{
					System.out.println("@@@@@@@@@cfrom :"+cfrom);
					System.out.println("@@@@@@@@@shno :"+shno);
					stmt.executeUpdate("update ShareMaster set share_val="+(sm.getShareVal()*sm.getNumberofShares())+",ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date='"+sm.uv.getVerDate()+"' where ac_type='"+sm.getShareAccType()+"' and ac_no="+shnumber);
					//stmt.executeUpdate("update ShareTransaction set trn_amt="+(sm.getShareVal()*sm.getNumberofShares())+",ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date=date_format(sysdate(),'%d/%m/%Y %H:%i:%s'),dist_no_from="+cfrom+",dist_no_to=share_bal+"+(cfrom-1)+" where ac_no="+shnumber+" and susp_ind='P'");
					stmt.executeUpdate("update ShareTransaction set trn_amt="+(sm.getShareVal()*sm.getNumberofShares())+",ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date='"+sm.uv.getVerDate()+"',dist_no_from="+cfrom+",dist_no_to="+((cfrom-1)+sm.getNumberofShares())+",sh_cert_no="+shno+" where ac_type='"+sm.getShareAccType()+"' and ac_no="+shnumber+" and susp_ind='P'");
					//stmt.executeUpdate("update ShareTransaction set ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date=date_format(sysdate(),'%d/%m/%Y %H:%i:%s'),dist_no_from="+cfrom+",dist_no_to=share_bal+"+(cfrom-1)+" where ac_no="+shnumber+" and susp_ind='P'");
					System.out.println("new share verification.)P(.............");
					ResultSet rs_name=stmt.executeQuery("select concat_ws('',fname,mname,lname) as name from CustomerMaster cm,ShareMaster sm where sm.cid=cm.cid and sm.cid="+sm.getCustomerId());
					rs_name.next();
					//stmt.executeUpdate("insert into SignatureInstruction values('"+sm.getShareAccType()+"',"+sm.getShareNumber()+","+sm.getCustomerId()+",'"+rs_name.getString("name")+"',null,0,0,'Single')");
				}
				
			}
			if(type==2 && sm.getShareStatus().equals("P"))
			{
				Statement stmt_mstr=conn.createStatement();
				ResultSet rs_mstr1=null,rs_mstr2=null,rs_mstr3=null,rs_mstr4=null,rs_mstr5=null;
				int start=0,end=0;
				System.out.println("verification...type=2 &&sh_ind=P for cert");
				ResultSet rs2=stmt.executeQuery("select dist_no_from,dist_no_to  from ShareTransaction where ac_no="+sm.getShareNumber()+" and ac_type='"+sm.getShareAccType()+"' and ve_user is not null  order by trn_no");
				//double numofshares=sm.getNumberofShares();
				int numofshares=(int)(sm.getShareBalance());
				System.out.println("@@@@@@@@@The no.of Shares :"+numofshares);
				rs2.last();
				int count=rs2.getRow();
				rs2.beforeFirst();
				aa:if(count==1)
				{
					while(rs2.next() && numofshares>0)
					{
						start=rs2.getInt(1);
						end=rs2.getInt(2);
						if(start==0 && end==0)
							break aa;
						
						System.out.println("@@@@start:"+start);
						System.out.println("@@@@end:"+end);
						Statement stmt2=conn.createStatement();
						if(((end-start)+1)<numofshares)
						{
							stmt2.executeUpdate("update CertificateMaster set refunddt=date_format(sysdate(),'%d/%m/%Y') where sh_dist_no between "+ start +" and "+end);
							numofshares=numofshares-(end-start+1);
							stmt.executeUpdate("update ShareTransaction set dist_no_from="+start+",dist_no_to="+end+" where ac_no="+sm.getShareNumber()+" and trn_no="+trnno+"");
						}
						else if(((end-start)+1)>=numofshares)
						{
							System.out.println("@@@@trnno:"+trnno);
							stmt2.executeUpdate("update CertificateMaster set refunddt=date_format(sysdate(),'%d/%m/%Y') where sh_dist_no between "+ start +" and "+(start+numofshares-1));
							stmt.executeUpdate("update ShareTransaction set dist_no_from="+start+",dist_no_to="+(start+numofshares-1)+" where ac_no="+sm.getShareNumber()+" and trn_no="+trnno+"");
						}
					}
				}
				//Added by Karhi
				else if(count>1)
				{
					
					System.out.println("Counnt>1.........");
					rs_mstr1=stmt_mstr.executeQuery("select * from CertificateMaster where sh_ac_no="+sm.getShareNumber()+" and sh_ac_type='"+sm.getShareAccType()+"' and refunddt is null");
					rs_mstr1.last();
					int noof_records=rs_mstr1.getRow();
					System.out.println("No.of_records---->"+noof_records);
					if(noof_records!=0)
					{
						rs_mstr1.beforeFirst();
						if(noof_records>=numofshares && numofshares>1)
						{
							rs_mstr2=stmt_mstr.executeQuery("select * from CertificateMaster where sh_ac_no="+sm.getShareNumber()+" and sh_ac_type='"+sm.getShareAccType()+"' and refunddt is null limit "+numofshares+" ");
							rs_mstr2.next();
							int c=rs_mstr2.getInt("sh_dist_no");
							System.out.println("C---->"+c);
							rs_mstr2.last();
							 rs_mstr2.beforeFirst();
							int i=1;
							boolean t=true;
							while(t)
							{
								int v=rs_mstr2.getInt("sh_dist_no");
								System.out.println("V---->"+v);
								rs_mstr2.next();
								int s=v+1;
								System.out.println("S---->"+s);
								if(s==rs_mstr2.getInt("sh_dist_no"))
								{
									stmt.executeUpdate("update ShareTransaction set dist_no_from="+c+",dist_no_to="+s+" where ac_no="+sm.getShareNumber()+" and ac_type='"+sm.getShareAccType()+"' and trn_no="+trnno+" ");
									stmt.executeUpdate("update CertificateMaster set refunddt=date_format(sysdate(),'%d/%m/%Y') where sh_ac_no="+sm.getShareNumber()+" and sh_dist_no between "+c+" and "+s+" ");
									i++;
									System.out.println("i---->"+i);
									if(i==numofshares)
										t=false;
									
								}
								else
								{
									System.out.println("i--->"+i);
									PreparedStatement ps1=null;
									rs_mstr3=stmt_mstr.executeQuery("select * from CertificateMaster where sh_ac_no="+sm.getShareNumber()+" and sh_ac_type='"+sm.getShareAccType()+"' and refunddt is null limit "+(numofshares-i)+" ");
									rs_mstr3.next();
									int d=rs_mstr3.getInt("sh_dist_no");
									rs_mstr3.last();
									rs_mstr3.beforeFirst();
									int e=0;
									while(rs_mstr3.next())
									{
										stmt.executeUpdate("update CertificateMaster set refunddt='"+date+"' where sh_ac_no="+sm.getShareNumber()+"  and sh_ac_type='"+sm.getShareAccType()+"' and sh_dist_no="+rs_mstr3.getInt("sh_dist_no")+" ");
										e=rs_mstr3.getInt("sh_dist_no");
										
									}
									rs_mstr4=stmt_mstr.executeQuery("select * from ShareTransaction where ac_no="+sm.getShareNumber()+" and ac_type='"+sm.getShareAccType()+"' and trn_no="+trnno+" ");
									rs_mstr4.next();
									ps1=conn.prepareStatement("insert into ShareTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
									ps1.setString(1,rs_mstr4.getString("ac_type"));
									ps1.setInt(2,rs_mstr4.getInt("trn_no"));
									ps1.setInt(3,rs_mstr4.getInt("ac_no"));
									ps1.setInt(4,rs_mstr4.getInt("trn_seq"));
									ps1.setString(5,rs_mstr4.getString("trn_date"));
									ps1.setString(6,rs_mstr4.getString("trn_type"));
									ps1.setFloat(7,rs_mstr4.getFloat("trn_amt"));
									ps1.setInt(8,rs_mstr4.getInt("ref_no"));
									ps1.setString(9,rs_mstr4.getString("trn_narr"));
									ps1.setString(10,rs_mstr4.getString("trn_mode"));
									ps1.setString(11,rs_mstr4.getString("trn_source"));
									ps1.setString(12,rs_mstr4.getString("cd_ind"));
									ps1.setString(13,rs_mstr4.getString("alt_ind"));
									ps1.setString(14,rs_mstr4.getString("susp_ind"));
									ps1.setFloat(15,rs_mstr4.getFloat("share_bal"));
									ps1.setInt(16,d);
									ps1.setInt(17,e);
									ps1.setString(18,rs_mstr4.getString("sh_cert_no"));
									ps1.setString(19,rs_mstr4.getString("sh_cert_dt"));
									ps1.setString(20,rs_mstr4.getString("cert_prtd"));
									ps1.setString(21,rs_mstr4.getString("markdel"));
									ps1.setString(22,rs_mstr4.getString("de_user"));
									ps1.setString(23,rs_mstr4.getString("de_tml"));
									ps1.setString(24,rs_mstr4.getString("de_date"));
									ps1.setString(25,rs_mstr4.getString("ve_user"));
									ps1.setString(26,rs_mstr4.getString("ve_date"));
									ps1.setString(27,rs_mstr4.getString("ve_tml"));
									ps1.executeUpdate();
									t=false;
								}
							}
						}
						else if(noof_records<numofshares)
						{
							rs_mstr5=stmt_mstr.executeQuery("select trn_no,dist_no_from,dist_no_to from ShareTransaction st, ShareMaster sm where st.ac_no="+sm.getShareNumber()+" and st.ac_type='"+sm.getShareAccType()+"' and  sm.ac_no=st.ac_no and sm.ac_type=st.ac_type and st.trn_type='W' and st.ve_user is not null order by trn_no desc limit 1");
							rs_mstr5.next();
							start=rs_mstr5.getInt(3)+1;
							end=start-1+(int)numofshares;
							stmt.executeUpdate("update CertificateMaster set refunddt='"+date+"' where sh_dist_no between "+ start +" and "+end);
							numofshares=numofshares-(end-start+1);
							stmt.executeUpdate("update ShareTransaction set dist_no_from="+start+",dist_no_to="+end+" where ac_no="+sm.getShareNumber()+" and trn_no="+trnno+"");
						}
						else if(numofshares==1)
						{
							rs_mstr2=stmt_mstr.executeQuery("select * from CertificateMaster where sh_ac_no="+sm.getShareNumber()+" and sh_ac_type='"+sm.getShareAccType()+"' and refunddt is null limit "+numofshares+" ");
							rs_mstr2.next();
							int c=rs_mstr2.getInt("sh_dist_no");
							stmt.executeUpdate("update ShareTransaction set dist_no_from="+c+",dist_no_to="+c+" where ac_no="+sm.getShareNumber()+" and ac_type='"+sm.getShareAccType()+"' and trn_no="+trnno+" ");
							stmt.executeUpdate("update CertificateMaster set refunddt='"+date+"' where sh_ac_no="+sm.getShareNumber()+" and sh_dist_no="+c+" ");
						}
					}   
				}
			}
			
			if(type==1 || type==2)
			{
				Statement stmt_mstr=conn.createStatement();
				ResultSet rs_mstr=null;
				
				rs_mstr=stmt_mstr.executeQuery("select trn_seq from ShareTransaction where trn_no="+trnno+" and ac_type='"+sm.getShareAccType()+"' and ac_no="+sm.getShareNumber()+"");
				rs_mstr.next();
				int trn_seq=rs_mstr.getInt("trn_seq");
				System.out.println("sm.getShareNumber()}}}}}}"+sm.getShareNumber());
				System.out.println("trn_seq}}}}}}}}}"+trn_seq);
				System.out.println("==============UPDATE LST_TRN_NO=========");
				if(sm.getShareStatus().equals("P"))
					stmt_mstr.executeUpdate("update ShareMaster set lst_trn_seq="+trn_seq+" where ac_type='"+sm.getShareAccType()+"' and ac_no="+sm.getShareNumber()+" ");
				else if(sm.getShareStatus().equals("T"))
					stmt_mstr.executeUpdate("update ShareMaster set lst_trn_seq="+trn_seq+" where ac_type='"+sm.getShareAccType()+"' and ac_no="+sm.getTempShareNumber()+" ");
				System.out.println("=============UPDATED LST_TRN_NO IN SHAREMASTER============");
				rs_mstr.close();
			}
			
			if(type==2 || (type==1 && sm.getShareStatus().equals("T")))
			{
				System.out.println("verification...type=2||type==1&&sh_ind=T");
				if(type==1)
					//stmt.executeUpdate("update ShareTransaction set trn_amt="+(sm.getShareBalance()*sm.getShareVal())+",ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date=date_format(sysdate(),'%d/%m/%Y %H:%i:%s') where trn_no="+trnno);
					stmt.executeUpdate("update ShareTransaction set trn_amt="+(sm.getNumberofShares()*sm.getShareVal())+",ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date='"+sm.uv.getVerDate()+"' where trn_no="+trnno);
				else
					stmt.executeUpdate("update ShareTransaction set ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date='"+sm.uv.getVerDate()+"' where trn_no="+trnno);
				//stmt.executeUpdate("update ShareTransaction set trn_amt="+(sm.getNumberofShares()*sm.getShareVal())+",ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date='"+sm.uv.getVerDate()+"' where trn_no="+trnno);
			}
			
			if(type==1 && sm.getShareStatus().equals("P"))
			{
				
				System.out.println("entering sharestautus==P");
				//stmt.executeUpdate("update ShareTransaction set share_bal=share_bal+"+sm.getShareBalance()+",trn_amt="+(sm.getShareBalance()*sm.getShareVal())+",ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date=date_format(sysdate(),'%d/%m/%Y %H:%i:%s'),dist_no_from="+cfrom+",dist_no_to=share_bal+"+(cfrom-1)+" where trn_no="+trnno);
				//stmt.executeUpdate("update ShareTransaction set trn_amt="+(sm.getShareBalance()*sm.getShareVal())+",ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date=date_format(sysdate(),'%d/%m/%Y %H:%i:%s'),dist_no_from="+cfrom+",dist_no_to=share_bal+"+(cfrom-1)+" where trn_no="+trnno);
				stmt.executeUpdate("update ShareTransaction set trn_amt="+(sm.getNumberofShares()*sm.getShareVal())+",ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date='"+sm.uv.getVerDate()+"',dist_no_from="+cfrom+",dist_no_to="+((cfrom-1)+sm.getNumberofShares())+",sh_cert_no="+shno+" where trn_no="+trnno);
				//stmt.executeUpdate("update ShareTransaction set ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date=date_format(sysdate(),'%d/%m/%Y %H:%i:%s'),dist_no_from="+cfrom+",dist_no_to=share_bal+"+(cfrom-1)+" where trn_no="+trnno);
				stmt.executeUpdate("update ShareMaster set share_val=share_val+"+(sm.getNumberofShares()*sm.getShareVal()) +"where ac_type='"+sm.getShareAccType()+"' and ac_no="+sm.getShareNumber());
				System.out.println("verification...type=1 &&sh_ind=P");
			}
			
			if(type==1 && sm.getShareStatus().equals("T"))
			{
				
				System.out.println("entering sharestautus==T");
				//stmt.executeUpdate("update ShareTransaction set share_bal=share_bal+"+sm.getShareBalance()+",trn_amt="+(sm.getShareBalance()*sm.getShareVal())+",ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date=date_format(sysdate(),'%d/%m/%Y %H:%i:%s'),dist_no_from="+cfrom+",dist_no_to=share_bal+"+(cfrom-1)+" where trn_no="+trnno);
				//stmt.executeUpdate("update ShareTransaction set trn_amt="+(sm.getShareBalance()*sm.getShareVal())+",ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date=date_format(sysdate(),'%d/%m/%Y %H:%i:%s'),dist_no_from="+cfrom+",dist_no_to=share_bal+"+(cfrom-1)+" where trn_no="+trnno);
				//stmt.executeUpdate("update ShareTransaction set ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date=date_format(sysdate(),'%d/%m/%Y %H:%i:%s'),dist_no_from="+cfrom+",dist_no_to=share_bal+"+(cfrom-1)+" where trn_no="+trnno);
				stmt.executeUpdate("update ShareTransaction set ve_user='"+sm.uv.getVerId()+"', ve_tml='"+sm.uv.getVerTml()+"',ve_date='"+sm.uv.getVerDate()+"' where trn_no="+trnno);
				stmt.executeUpdate("update ShareMaster set share_val=share_val+"+(sm.getNumberofShares()*sm.getShareVal()) +"where ac_type='"+sm.getShareAccType()+"' and temp_no="+sm.getTempShareNumber());
				System.out.println("verification...type=1 &&sh_ind=T");
			}
			
			if(type==2 && sm.getShareStatus().equals("T"))
			{	stmt.executeUpdate("update ShareMaster set mem_cl_date='"+date+"' where ac_type='"+sm.getShareAccType()+"' and temp_no="+sm.getShareNumber()+" and sh_ind='T'");
			System.out.println("verification...type=2 &&sh_ind=T");
			}
			
			if(type==2 && sm.getShareStatus().equals("P"))
			{
				System.out.println("verification...type=1 &&sh_ind=P");
				System.out.println("Share value"+(sm.getShareBalance()*sm.getShareVal()));
				stmt.executeUpdate("update ShareMaster set share_val=share_val-"+(sm.getShareBalance()*sm.getShareVal())+" where ac_no="+sm.getShareNumber()+" and ac_type='"+sm.getShareAccType()+"'");
				ResultSet rs_w=stmt.executeQuery("select share_val from ShareMaster where ac_no="+sm.getShareNumber()+" and ac_type='"+sm.getShareAccType()+"'");
				rs_w.next();
				double shr_val=rs_w.getDouble("share_val");
				if(shr_val==0)//For Account Closure
					stmt.executeUpdate("update ShareMaster set mem_cl_date='"+date+"' where ac_no="+sm.getShareNumber()+" and ac_type='"+sm.getShareAccType()+"'");
			}
			System.out.println("data after");
			System.out.println("sm.getShareBalance()="+sm.getShareBalance());
			System.out.println("sm.getShareVal()="+sm.getShareVal());
			System.out.println("sm.getShareNumber()="+sm.getShareNumber());
			System.out.println("trn_amt="+sm.getShareBalance()*sm.getShareVal());
			System.out.println("trn_number="+trnno);
			
			GLTransObject trnobj=new GLTransObject();
			String trntype,cdind;
			if(type==0 || type==1)
			{
				trntype="A";//A-->Allotment
				cdind="C";
			}
			else
			{
				trntype="W";//W-->Withdrawal
				cdind="D";
			}
			
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			System.out.println("type........."+type);
			System.out.println("gl code"+sm.getGLCode());
			System.out.println("trntype"+trntype);
			System.out.println("cdind"+cdind);
			System.out.println("type"+type);
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			
			System.out.println("acctype"+sm.getShareAccType());
			System.out.println("mem cat"+sm.getMemberCategory());
			System.out.println("sh ind"+sm.getShareStatus());
			
			String prm_type=null;
			if(sm.getShareStatus().equals("P"))
				prm_type="D";
			if(sm.getShareStatus().equals("T"))
				prm_type="S";
			
			System.out.println("prm_type"+prm_type);
			
			ResultSet rs_gl=stmt.executeQuery("select prm_gl_code,prm_gl_type from ShareParam where ac_type='"+sm.getShareAccType()+"' and mem_cat="+sm.getMemberCategory()+" and prm_ty='"+prm_type+"'");
			rs_gl.next();
			
			String gl_code=rs_gl.getString("prm_gl_code");
			String gl_type=rs_gl.getString("prm_gl_type");
			//String gl_name=rs_gl.getString("");
			System.out.println("gl_code:"+gl_code);
			System.out.println("gl_type:"+gl_type);
			//System.out.println("gl_name"+gl_name);
			rs_gl.close();
			
			double shtot_amt=0;
			
			ResultSet rs=stmt.executeQuery("select mult_by from GLPost where ac_type='"+sm.getShareAccType()+"' and trn_type='"+trntype+"' and cr_dr='"+cdind+"' and gl_code='"+gl_code+"'");
			
			System.out.println("if Before writing into Share GL");
			if(rs.next())
			{
				int mult_by=rs.getInt(1);
				trnobj.setTrnDate(date);
				trnobj.setGLType(gl_type);
				if(sm.getShareStatus().equals("T"))
				 trnobj.setGLCode(sm.getSuspGLCode());				
				 else
				 trnobj.setGLCode(sm.getGLCode());
				 
				
				if(sm.getShareStatus().equals("T"))
					trnobj.setGLCode(gl_code);				
				else
					trnobj.setGLCode(gl_code);
				
				//trnobj.setGLName(gl_name);
				if(sm.getRecievedMode().equalsIgnoreCase("PO"))
					trnobj.setTrnMode("T");
				else
					trnobj.setTrnMode(sm.getRecievedMode());
				if(type==0 ||type==1)
				{
					if(mult_by>0)
						trnobj.setAmount(sm.getShareVal()*sm.getNumberofShares()*mult_by);
					else
						trnobj.setAmount(sm.getShareVal()*sm.getNumberofShares()*mult_by*(-1));
				}
				else if(type==2)
				{
					if(type==1)
					 trnobj.setAmount(sm.getShareBalance()*sm.getShareVal()*rs.getInt(1));
					 else
					 trnobj.setAmount(sm.getShareBalance()*sm.getShareVal()*rs.getInt(1)*(-1));
					if(mult_by>0){
						System.out.println("The share balance is "+sm.getShareBalance());
						System.out.println("The share val is  "+sm.getShareVal());
						System.out.println("The mult by is "+mult_by);
						//trnobj.setAmount(sm.getShareBalance()*sm.getShareVal()*mult_by); // commented by suraj on 6/3/2008
						trnobj.setAmount(sm.getShareBalance()*mult_by);
						System.out.println("trnobj....... is"+trnobj.getAmount());
					}
					else
						trnobj.setAmount(sm.getShareBalance()*sm.getShareVal()*mult_by*(-1));
				}
				
				
				trnobj.setCdind(cdind);
				//trnobj.setAccType(sm.getShareAccType()+" "+sm.getShareStatus());
				trnobj.setAccType(sm.getShareAccType());
				
				if(sm.getShareStatus().equals("P"))
					trnobj.setAccNo(String.valueOf(sm.getShareNumber()));
				else
				{
					if(type==1)
						trnobj.setAccNo(String.valueOf(sm.getShareNumber()));
					else
						trnobj.setAccNo(String.valueOf(sm.getTempShareNumber()));
				}
				//Added by Karthi-->09/01/2006
				
				ResultSet rs_trnseq=stmt_trnseq.executeQuery("select max(trn_seq),max(trn_no) from ShareTransaction  where ac_no="+trnobj.getAccNo()+" and ac_type='"+trnobj.getAccType()+"' and trn_type='"+trntype+"'");
				rs_trnseq.next();
				//added by suraj on 6.3.08
				ResultSet rs_trnseq;
				if(sm.getRecievedMode().equals("C"))
				{
					 rs_trnseq=stmt_trnseq.executeQuery("select max(trn_seq),max(ref_no) from ShareTransaction  where ac_no="+trnobj.getAccNo()+" and ac_type='"+trnobj.getAccType()+"' and trn_type='"+trntype+"'");
				}
				else //if(sm.getRecievedMode()=="T")
				{
			    rs_trnseq=stmt_trnseq.executeQuery("select max(trn_seq),max(trn_no) from ShareTransaction  where ac_no="+trnobj.getAccNo()+" and ac_type='"+trnobj.getAccType()+"' and trn_type='"+trntype+"'");
				}
				rs_trnseq.next();
				System.out.println("Accounttttttttt no"+trnobj.getAccNo());
				
				
				
				
				trnobj.setTrnSeq(rs_trnseq.getInt(1));
				trnobj.setRefNo(rs_trnseq.getInt(2));
				trnobj.setTrnType(trntype);
				trnobj.setVtml(sm.uv.getVerTml());
				trnobj.setVid(sm.uv.getVerId());
				trnobj.setVDate(sm.uv.getVerDate());
				System.out.println("Before writing into Share GL");
				storeGLTransaction(trnobj);
				System.out.println("after writing into Share GL");
				
				*//**
				 * While Share Allotment time the bank collect the application fees. 
				 * so we have to enter GL entry for that also.
				 *//*
				if(shparam!=null)
				{
					for(int i=0;i<shparam.length;i++)
					{
						if(shparam[i].isPaid())
						{
							ResultSet rs1=cpstmt.executeQuery("select mult_by from GLPost where gl_code='"+shparam[i].getPrm_gl_key()+"' and ac_type='"+sm.getShareAccType()+"' and trn_type='R' and cr_dr='C'");
							rs1.next();
							trnobj.setGLCode(shparam[i].getPrm_gl_key());
							trnobj.setCdind("C");
							if(rs.getInt(1)>0)
								trnobj.setAmount(shparam[i].getPrm_amt()*rs.getInt(1));
							else
								trnobj.setAmount(shparam[i].getPrm_amt()*rs.getInt(1)*(-1));
							
							shtot_amt=shtot_amt+trnobj.getAmount();
							storeGLTransaction(trnobj);
						}
					}
				}
				
				if(type==0)
				{
				misc_amt=sm.getAmount()-(sm.getNumberofShares()*sm.getShareVal()+shtot_amt);
				System.out.println(".....getAmount%....."+sm.getAmount());
				System.out.println(".....getAmount1%....."+sm.getNumberofShares());
				System.out.println(".....getAmount2%....."+sm.getShareVal());
				System.out.println(".....getAmount3%....."+shtot_amt);
				
				System.out.println(".....misc....%.."+misc_amt);
				
				}
				else if(type==1)
				{
					misc_amt=sm.getAmount_paid()-(sm.getNumberofShares()*sm.getShareVal()+shtot_amt);//suraj
					System.out.println(".....getAmount%....."+sm.getAmount_paid());
					System.out.println(".....getAmount1%....."+sm.getNumberofShares());
					System.out.println(".....getAmount2%....."+sm.getShareVal());
					System.out.println(".....getAmount3%....."+shtot_amt);
					
					System.out.println(".....misc....%.."+misc_amt);
					
					
				}
				else
				{
				System.out.println("..2...getAmount%....."+sm.getAmount());
				System.out.println("...2..getAmount1%....."+sm.getNumberofShares());
				System.out.println("...2..getAmount2%....."+sm.getShareVal());
				System.out.println("..2...getAmount3%....."+shtot_amt);
				
				System.out.println(".2....misc....%.."+misc_amt);
				}
				
				
				
				misc_amt=sm.getAmount()-(sm.getNumberofShares()*sm.getShareVal()+shtot_amt);
				if(misc_amt>0)
				{
					System.out.println(" @@** The Extra Amount : "+sm.getMiscAmount());
					ResultSet rs_misc=cpstmt.executeQuery("select gl_type,gl_code from GLKeyParam where ac_type='1012000' and code=1");
					rs_misc.next();
					trnobj.setGLCode(rs_misc.getString("gl_code"));
					trnobj.setGLType(rs_misc.getString("gl_type"));
					trnobj.setCdind("C");
					trnobj.setAmount(misc_amt);
					common_local.storeGLTransaction(trnobj);
				}
			}
			
			if(sm.getRecievedMode().equals("PO") &&(type==2))
			{
				System.out.println("Inside po");
				PayOrderObject po = new PayOrderObject();
				po.setPOType("X");
				try
				{//Added By Karthi-->20/01/2006
					Statement stmt_cust=conn.createStatement();
					ResultSet rs_cust=null,rs_glname=null;
					if(sm.getShareStatus().equals("P"))
						rs_cust=stmt_cust.executeQuery("select distinct sm.ac_no,sm.cid,concat_ws('',cm.fname,cm.mname,cm.lname)as name, cm.custtype from ShareMaster sm,CustomerMaster cm where sm.ac_type='"+sm.getShareAccType()+"'and sm.ac_no="+sm.getShareNumber()+" and cm.cid=sm.cid");
					else
						rs_cust=stmt_cust.executeQuery("select distinct sm.temp_no,sm.cid,concat_ws('',cm.fname,cm.mname,cm.lname)as name, cm.custtype from ShareMaster sm,CustomerMaster cm where sm.ac_type='"+sm.getShareAccType()+"'and sm.temp_no="+sm.getTempShareNumber()+" and cm.cid=sm.cid");
					rs_cust.next();
					po.setPOPayee(rs_cust.getString("name"));
					po.setCustType(rs_cust.getInt("custtype"));
				}catch(SQLException se){se.printStackTrace();}
				
				Statement stmt_glname=conn.createStatement();
				Statement stmt_shtran=conn.createStatement();
				ResultSet rs_glname=null;
				po.setPOAccType(sm.getShareAccType());
				if(sm.getShareStatus().equals("P"))
					po.setPOAccNo(sm.getShareNumber());
				else
					po.setPOAccNo(sm.getTempShareNumber());
				
				po.setPOGlCode(Integer.parseInt(trnobj.getGLCode()));
				po.setPOGlType(trnobj.getGLType());
				rs_glname=stmt_glname.executeQuery("select gl_name from GLMaster where gl_type='"+trnobj.getGLType()+"' and gl_code="+trnobj.getGLCode()+" and ((from_date<='"+Validations.convertYMD(date)+"' and to_date is null) or (from_date<='"+Validations.convertYMD(date)+"' and to_date>='"+Validations.convertYMD(date)+"'))");
				rs_glname.next();
				po.setPOGlName(rs_glname.getString("gl_name"));
				po.setCommissionAmount(0);
				po.uv.setUserTml(sm.uv.getVerTml());
				po.uv.setUserId(sm.uv.getVerId());
				po.uv.setUserDate(sm.uv.getUserDate());
				po.uv.setVerId(sm.uv.getVerId());
				po.uv.setVerTml(sm.uv.getVerTml());
				po.uv.setVerDate(sm.uv.getVerDate());
				po.setPOAmount(trnobj.getAmount());
				int value=cremote.storePayOrder(po);
				System.out.println("Inside po"+value);
				Statement newstmt=conn.createStatement();
				ResultSet rs2 = newstmt.executeQuery("select gl_code,gl_type from GLKeyParam where ac_type= 1016001 and code=1");
				if(rs2.next())
				{
					GLTransObject trnobj1=new GLTransObject();
					trnobj1.setTrnDate(date);
					trnobj1.setGLType(rs2.getString(2));
					trnobj1.setGLCode(rs2.getString(1));
					trnobj1.setTrnMode("T");
					trnobj1.setAmount(trnobj.getAmount());
					trnobj1.setCdind("C");
					trnobj1.setAccType("1016001");
					
					if(sm.getShareStatus().equals("P"))
					 trnobj1.setAccNo(String.valueOf(sm.getShareNumber()));
					 else
					 trnobj1.setAccNo(String.valueOf(sm.getTempShareNumber()));
					
					trnobj1.setAccNo(String.valueOf(value));
					trnobj1.setTrnType("R");
					trnobj1.setRefNo(value);						
					trnobj1.setVtml(sm.uv.getVerTml());
					trnobj1.setVid(sm.uv.getVerId());
					trnobj1.setVDate(sm.uv.getVerDate());
					storeGLTransaction(trnobj1);
					newstmt.executeUpdate("update GLTransaction set ref_no="+value+" where gl_type='"+trnobj.getGLType()+"'and gl_code="+trnobj.getGLCode()+" and trn_date='"+getSysDate()+"'and ref_ac_no="+trnobj.getAccNo()+" ");
				}
				if(sm.getShareStatus().equalsIgnoreCase("P"))
					stmt_shtran.executeUpdate("update ShareTransaction set ref_no="+value+",trn_narr='PayOrdr. "+value+"' where ac_no="+sm.getShareNumber()+" and ac_type='"+sm.getShareAccType()+"' and trn_date='"+getSysDate()+"'and trn_mode='PO' and trn_no="+trnno+" ");
				else
					stmt_shtran.executeUpdate("update ShareTransaction set ref_no="+value+",trn_narr='PayOrdr. "+value+"' where ac_no="+sm.getTempShareNumber()+" and ac_type='"+sm.getShareAccType()+"' and trn_date='"+getSysDate()+"'and trn_mode='PO' and trn_no="+trnno+" ");
				System.out.println("Inside po");		
			}
			if(sm.getRecievedMode().equals("C") && (type==2))
			{
				//stmt.executeUpdate("update DayCash set ac_no="+shnumber+",attached='T' where scroll_no="+sm.getRecievedAccno());
				//Changed by Karthi
				int no=0;
				if(sm.getShareStatus().equals("P"))
					no=sm.getShareNumber();
				else
					no=sm.getTempShareNumber();
				
				PreparedStatement pstmt_update=null;
				pstmt_update=conn.prepareStatement("update DayCash set ve_user=?,ve_tml=?,ve_date=? where ac_type='"+sm.getShareAccType()+"' and ac_no="+no+" ");
				pstmt_update.setString(1,sm.uv.getVerId());
				pstmt_update.setString(2,sm.uv.getVerTml());
				pstmt_update.setString(3,sm.uv.getVerDate());
				pstmt_update.executeUpdate();
				
				//PreparedStatement pstmt1=conn.prepareStatement("insert into DayCash (scroll_no,trn_date,ac_type,ac_no,csh_amt,cd_ind,vch_type,attached,vch_no,de_user,de_tml,de_date,ve_user,ve_tml,ve_date) values (0,?,?,?,?,'C','W','F',?,?,?,?,?,?,date_format(sysdate(),'%d/%m/%Y'))");
				PreparedStatement pstmt1=conn.prepareStatement("insert into DayCash (scroll_no,trn_date,ac_type,ac_no,trn_type,csh_amt,cd_ind,name,vch_no,vch_type,trn_seq,attached,de_user,de_tml,de_date,ve_user,ve_tml,ve_date,share_category) values (0,?,?,?,'P',?,'C',?,?,?,?,'F',?,?,?,?,?,?,?)");//Karthi
				 System.out.println("System date"+getSysDate());
				 
				 pstmt1.setString(1,getSysDate());
				 System.out.println("System date"+getSysDate());
				 pstmt1.setString(2,sm.getShareAccType());
				 pstmt1.setInt(3,no);
				 pstmt1.setDouble(4,sm.getAmount());
				 ResultSet rs_name=stmt_name.executeQuery("select concat_ws(' ',fname,mname,lname)as name from CustomerMaster where cid='"+sm.getCustomerId()+"'");
				 rs_name.next();
				 pstmt1.setString(5,rs_name.getString("name"));
				 pstmt1.setInt(6,trnno);
				 
				 if(sm.getWithdrawalType()==0)
				 pstmt1.setString(7,"W");
				 else
				 pstmt1.setString(7,"C");
				 
				 ResultSet rs_trnseq=stmt_trnseq.executeQuery("select  lst_trn_seq from ShareMaster where ac_type='"+sm.getShareAccType()+"' and ac_no="+no+" ");
				 rs_trnseq.next();
				 pstmt1.setInt(8,rs_trnseq.getInt("lst_trn_seq"));
				 
				 pstmt1.setString(9,sm.uv.getUserId());
				 pstmt1.setString(10,sm.uv.getUserTml());
				 pstmt1.setString(11,common_remote.getSysDateTime());
				 pstmt1.setString(12,sm.uv.getVerId());
				 pstmt1.setString(13,sm.uv.getVerTml());					
				 pstmt1.setString(14,sm.uv.getVerTml());
				 pstmt1.setString(14,common_remote.getSysDateTime());
				 
				 if(sm.getMemberCategory()==1)
				 pstmt1.setString(15,"Regular");
				 else if(sm.getMemberCategory()==2)
				 pstmt1.setString(15,"Associate");
				 else if(sm.getMemberCategory()==3)
				 pstmt1.setString(15,"Nominal");
				 else
				 pstmt1.setString(15,null);
				 
				 pstmt1.executeUpdate();			
				
				//rs=stmt.executeQuery("select mult_by,gp.gl_code,gk.gl_type from GLPost gp,GLKeyParam gk where gp.ac_type='1019001' and gk.ac_type='1019001' and trn_type='R' and cr_dr='C'");
				rs=stmt.executeQuery("select gl_code,gl_type from GLKeyParam where ac_type=1019001");
				rs.next();
				trnobj.setGLCode(rs.getString("gl_code"));
				trnobj.setGLType(rs.getString("gl_type"));
				//trnobj.setAccType("1019001");//
				//trnobj.setAccNo(String.valueOf(sm.getRecievedAccno()));
				//trnobj.setAccNo("0");//
				trnobj.setCdind("C");
				//trnobj.setTrnType("");//
				//trnobj.setTrnSeq(0);//
				//trnobj.setAmount(sm.getAmount()*rs.getInt("mult_by"));
				trnobj.setRefNo(trnno);
				storeGLTransaction(trnobj);	
				
			}
			if(sm.getRecievedMode().equals("C") && (type==0 || type==1))
			{
				if(type==0)
					stmt.executeUpdate("update DayCash set ac_no="+shnumber+",attached='T',trn_seq=1,ve_user='"+sm.uv.getVerId()+"',ve_tml='"+sm.uv.getVerTml()+"',ve_date='"+sm.uv.getVerDate()+"' where scroll_no="+sm.getRecievedAccno()+" ");
				else
					stmt.executeUpdate("update DayCash set ac_no="+shnumber+",attached='T',ve_user='"+sm.uv.getVerId()+"',ve_tml='"+sm.uv.getVerTml()+"',ve_date='"+sm.uv.getVerDate()+"' where scroll_no="+sm.getRecievedAccno());
				
				//rs=stmt.executeQuery("select mult_by,GLPost.gl_code from GLPost,GLKeyParam  where GLKeyParam.ac_type='1019001' and GLKeyParam.ac_type=GLPost.ac_type  and trn_type='P'  and cr_dr='D'");
				rs=stmt.executeQuery("select gl_code,gl_type from GLKeyParam where ac_type=1019001");
				rs.next();
				trnobj.setGLCode(rs.getString("gl_code"));
				trnobj.setGLType(rs.getString("gl_type"));
				//trnobj.setAccType("1019001");//
				//trnobj.setAccNo(String.valueOf(sm.getRecievedAccno()));
				//trnobj.setAccNo("0");//
				
				trnobj.setCdind("D");
				
				//trnobj.setAmount(sm.getAmount()*rs.getInt("mult_by"));
				
				if(type==0 || type==1)
					//trnobj.setAmount(sm.getShareVal()*sm.getNumberofShares()+shtot_amt+misc_amt);
					trnobj.setAmount(sm.getAmount());
				else
					//trnobj.setAmount(sm.getShareBalance()*sm.getShareVal()+shtot_amt+misc_amt);
					trnobj.setAmount(sm.getAmount());
				
				//trnobj.setTrnType("");//
				//trnobj.setTrnSeq(0);//
				storeGLTransaction(trnobj);
				shtot_amt=0;
				
				Statement stmt_tml=conn.createStatement();
				Statement stmt_tml1=conn.createStatement();
				ResultSet rs_tml=null;
				try
				{
					System.out.println("Select de_tml from DayCash........");
					rs_tml=stmt_tml.executeQuery("select de_tml from DayCash where ac_type='"+sm.getShareAccType()+"' and ac_no="+shnumber+" and scroll_no="+sm.getRecievedAccno()+" and ve_date='"+sm.uv.getVerDate()+"'");
					if(rs_tml.next())
						stmt_tml1.executeUpdate("update ShareTransaction set trn_source='"+rs_tml.getString("de_tml")+"' where ac_type='"+sm.getShareAccType()+"' and ac_no="+shnumber+" and ref_no="+sm.getRecievedAccno()+" ");
					else
						System.out.println("The rs_tml is empty........");
				}catch(SQLException ex){
					ex.printStackTrace();
					sessionContext.setRollbackOnly();
				}
				
			}
			else if(sm.getRecievedMode().equals("T") || sm.getRecievedMode().equals("B"))
			{
				AccountTransObject accounttransobject=new AccountTransObject();
				accounttransobject.setAccType(sm.getRecievedAcctype());
				accounttransobject.setAccNo(sm.getRecievedAccno());
				//accounttransobject.setName(sm.getName());//Karthi
				//accounttransobject.setTransAmount(sm.getAmount()+shtot_amt);
				
				accounttransobject.setTransDate(date);
				if(type==1)
					accounttransobject.setTransAmount(sm.getAmount());
				else if(type==0)
					accounttransobject.setTransAmount(sm.getAmount());
				else
					accounttransobject.setTransAmount(sm.getShareBalance()*sm.getShareVal());
				
				accounttransobject.setTransMode("T");
				accounttransobject.setTransSource(sm.uv.getVerTml());
				
				if(type==2)
				{
					accounttransobject.setTransType("R");
					accounttransobject.setCdInd("C");
				}
				else if(type==1|| type==0)
				{
					accounttransobject.setTransType("P");	
					accounttransobject.setCdInd("D");
				}
				if(sm.getShareStatus().equals("P"))
					accounttransobject.setTransNarr(sm.getShareAccType() +" "+sm.getShareNumber());
				else
				{
					if(type!=1)
						//accounttransobject.setTransNarr(sm.getShareAccType()+" T "+sm.getTempShareNumber());
						accounttransobject.setTransNarr(sm.getShareAccType()+"  "+sm.getTempShareNumber());
					else
						//accounttransobject.setTransNarr(sm.getShareAccType()+" T "+sm.getShareNumber());
						accounttransobject.setTransNarr(sm.getShareAccType()+"  "+sm.getShareNumber());
				}
				accounttransobject.uv.setUserTml(sm.uv.getUserTml());
				accounttransobject.uv.setUserId(sm.uv.getUserId());
				accounttransobject.uv.setUserDate(sm.uv.getUserDate());
				accounttransobject.uv.setVerTml(sm.uv.getVerTml());
				accounttransobject.uv.setVerId(sm.uv.getVerId());
				accounttransobject.uv.setVerDate(sm.uv.getVerDate());
				cremote.storeAccountTransaction(accounttransobject);
			}
			else if(sm.getRecievedMode().equalsIgnoreCase("G") && type==1)
			{
				ResultSet rs_clg=stmt.executeQuery("select gl_code,gl_type from GLKeyParam where ac_type='1011001' and code=1");
				rs_clg.next();
				trnobj.setGLCode(rs_clg.getString("gl_code"));
				trnobj.setGLType(rs_clg.getString("gl_type"));
				trnobj.setAccType("1011001");
				//trnobj.setAccNo(String.valueOf(sm.getRecievedAccno()));
				trnobj.setAccNo("0");
				
				trnobj.setCdind("D");
				
				//trnobj.setAmount(sm.getAmount()*rs.getInt("mult_by"));
				
				//trnobj.setAmount(sm.getShareBalance()*sm.getShareVal()+shtot_amt);
				trnobj.setAmount(sm.getAmount());
				trnobj.setTrnType("");
				trnobj.setTrnSeq(0);
				storeGLTransaction(trnobj);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}		
		return(1);
	}*/
	public Connection getConnection() 
	{
		try{			
			return ds.getConnection("root","");
		}catch(Exception e)	{e.printStackTrace();}
		return null;
	}
	public String getSysDate() throws DateFormatException 
	{
		Calendar c=Calendar.getInstance();
		return(Validations.checkDate(c.get(Calendar.DATE)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.YEAR)));
	}
	public String getSysTime()
	{
		Calendar c=Calendar.getInstance();
		
		String str=String.valueOf(c.get(Calendar.SECOND));
		if(str.length()==1)
			str="0"+str;
		String str1=String.valueOf(c.get(Calendar.MINUTE));
		if(str1.length()==1)
			str1="0"+str1;
		String str2=String.valueOf(c.get(Calendar.HOUR));
		if(str2.length()==1)
			str2="0"+str2;			
		return(str2+":"+str1+":"+str+"  ");
	}
	 public Context getInitialContext() throws Exception
	{
		java.util.Properties p = new java.util.Properties();
		p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");		
		p.put(Context.URL_PKG_PREFIXES, "jboss.naming:org.jnp.interfaces");
		p.put(Context.PROVIDER_URL, "localhost:1099");
		return new javax.naming.InitialContext(p);  
	}
	
	public int storeGLTransaction(GLTransObject trnobj)
	{
		Connection conn=null;
		System.out.println("Store gl transaction");
		try{
			conn=getConnection();
			
			PreparedStatement pstmt=conn.prepareStatement("insert into GLTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			System.out.println("%%%%%% GL ENTRIES %%%%%%%%%");
			System.out.println("trn date"+trnobj.getTrnDate());
			pstmt.setString(1,trnobj.getTrnDate());
			System.out.println("GL TYPE"+trnobj.getGLType());
			pstmt.setString(2,trnobj.getGLType());
			System.out.println("GL Code"+trnobj.getGLCode());
			pstmt.setString(3,trnobj.getGLCode());
			System.out.println("TRN MODE"+trnobj.getTrnMode());
			pstmt.setString(4,trnobj.getTrnMode());
			System.out.println("Amount"+trnobj.getAmount());
			pstmt.setDouble(5,trnobj.getAmount());
			System.out.println("CD IND"+trnobj.getCdind());
			pstmt.setString(6,trnobj.getCdind());
			System.out.println("ACCtype "+trnobj.getAccType());
			pstmt.setString(7,trnobj.getAccType());
			System.out.println("AccNo"+trnobj.getAccNo());
			pstmt.setString(8,trnobj.getAccNo());
			System.out.println("TrnSeq"+trnobj.getTrnSeq());
			pstmt.setInt(9,trnobj.getTrnSeq());
			System.out.println("Trn type"+trnobj.getTrnType());
			pstmt.setString(10,trnobj.getTrnType());
			System.out.println("Ref no"+trnobj.getRefNo());
			pstmt.setInt(11,trnobj.getRefNo());
			pstmt.setString(12,trnobj.getVtml());
			pstmt.setString(13,trnobj.getVid());
			pstmt.setString(14,trnobj.getVDate());
			System.out.println("%%%%%% GL ENTRIES END %%%%%%%%%");
			return(pstmt.executeUpdate());
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		finally{
			try{
				conn.close();
			}catch(Exception e){e.printStackTrace();}
		}		
		
		return 0;
	}
	
	
	//Added By Karthi
	public ShareMasterObject[] displayScrollNo(String ac_type,String date) throws SQLException,ScrollNotFoundException
	{
		Connection conn=null;
		ShareMasterObject smobj[]=null;
		Statement stmt_scrollno=null;
		try
		{
			conn=getConnection();
			stmt_scrollno=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rs_scrollno=null;
			if(ac_type.length()>0)
			{
				rs_scrollno=stmt_scrollno.executeQuery("select scroll_no,ac_type,name,csh_amt from DayCash where scroll_no!=0 and trn_date='"+date+"' and ac_type='"+ac_type+"' and attached='F' ");
				rs_scrollno.last();
				smobj=new ShareMasterObject[rs_scrollno.getRow()];
				if(rs_scrollno.getRow()==0)
					throw new ScrollNotFoundException();
				else
				{
					rs_scrollno.beforeFirst();
					
					System.out.println("rs_scrollno.getRow()----->"+rs_scrollno.getRow());
					int i=0;
					while(rs_scrollno.next())
					{
						System.out.println("goin inside.........");
						smobj[i]=new ShareMasterObject();	
						smobj[i].setScrollno(rs_scrollno.getInt("scroll_no"));
						smobj[i].setCashAcctype(rs_scrollno.getString("ac_type"));
						smobj[i].setCashAccountName(rs_scrollno.getString("name"));
						smobj[i].setCashAmount(rs_scrollno.getDouble("csh_amt"));
						System.out.println("i=="+i);
						i++;
						
					}
				}
			}
		} catch(SQLException ex){ex.printStackTrace();}
		finally
		{
			conn.close();
		}
		return smobj;
	}
	
	//Added By Karthi-->16/01/2006
	public DividendRateObject[] getDivRateDetails(int type) throws RecordsNotFoundException
	{
		System.out.println("Inside Share div.rat..........");
		Connection conn=null;
		DividendRateObject div_ro[]=null;
		System.out.println("type:"+type);
		try
		{
			conn=getConnection();
			Statement stmt_divrate=conn.createStatement();
			ResultSet rs_divrate=stmt_divrate.executeQuery("select fr_date,to_date,div_rate,drf_amt,cal_done,cal_opt from ShareDivRate ");
			rs_divrate.last();
			System.out.println("The No.of Rows is:"+rs_divrate.getRow());
			div_ro=new DividendRateObject[rs_divrate.getRow()];
			if(rs_divrate.getRow()==0)
				throw new RecordsNotFoundException();
			else
			{
				rs_divrate.beforeFirst();
				int i=0;
				while(rs_divrate.next())
				{
					div_ro[i]=new DividendRateObject();
					div_ro[i].setFromDate(rs_divrate.getString("fr_date"));
					div_ro[i].setToDate(rs_divrate.getString("to_date"));
					div_ro[i].setRate(rs_divrate.getDouble("div_rate"));
					div_ro[i].setAmount(rs_divrate.getDouble("drf_amt"));
					div_ro[i].setCalDone(rs_divrate.getString("cal_done"));
					div_ro[i].setCalopt(rs_divrate.getString("cal_opt"));
					i++;
				}
			}
		}catch(SQLException sqlex){sqlex.printStackTrace();}
		finally
		{
			try
			{
				conn.close();
			}catch(SQLException sqlex){sqlex.printStackTrace();}
		}
		return div_ro;
	}
	
//	Added By Karthi-->16/01/2006
	public DirectorMasterObject[] getDirectorMasterDetails(int type) throws RecordsNotFoundException
	{
		System.out.println("Inside Director detail..........");
		Connection conn=null;
		DirectorMasterObject dirmo[]=null;
		System.out.println("type:"+type);
		try
		{
			conn=getConnection();
			Statement stmt_master=conn.createStatement();
			ResultSet rs_master=stmt_master.executeQuery("select * from DirectorMaster");
			rs_master.last();
			System.out.println("The No.of Records:"+rs_master.getRow());
			dirmo=new DirectorMasterObject[rs_master.getRow()];
			if(rs_master.getRow()==0)
				throw new RecordsNotFoundException();
			else
			{
				rs_master.beforeFirst();
				int i=0;
				while(rs_master.next())
				{
					dirmo[i]=new DirectorMasterObject();
					dirmo[i].setDirectorId(rs_master.getInt("director_code"));
					dirmo[i].setDirectorCustomerId(rs_master.getInt("cid"));
					dirmo[i].setFromDate(rs_master.getString("from_date"));
					dirmo[i].setToDate(rs_master.getString("to_date"));
					i++;
				}
			}
			
			
		}catch(SQLException sqlex){sqlex.printStackTrace();}
		finally
		{
			try
			{
				conn.close();
			}catch(SQLException sqlex){sqlex.printStackTrace();}
		}
		return dirmo;
	}
//	Added By Karthi-->16/01/2006
	public DirectorMasterObject[] getDirectorRelationDetails(int type) throws RecordsNotFoundException
	{
		System.out.println("Inside Director detail..........");
		Connection conn=null;
		DirectorMasterObject dirmo[]=null;
		System.out.println("type:"+type);
		try
		{
			conn=getConnection();
			Statement stmt_rel=conn.createStatement();
			ResultSet rs_rel=stmt_rel.executeQuery("select * from DirectorRelation");
			rs_rel.last();
			System.out.println("The No.of Records:"+rs_rel.getRow());
			dirmo=new DirectorMasterObject[rs_rel.getRow()];
			if(rs_rel.getRow()==0)
				throw new RecordsNotFoundException();
			else
			{
				rs_rel.beforeFirst();
				int i=0;
				while(rs_rel.next())
				{
					dirmo[i]=new DirectorMasterObject();
					dirmo[i].setDirectorRelationCode(rs_rel.getInt("rel_code"));
					dirmo[i].setDirectorRelationType(rs_rel.getString("relation_type"));
					i++;
				}
			}
		}catch(SQLException sqlex){sqlex.printStackTrace();}
		finally
		{
			try
			{
				conn.close();
			}catch(SQLException sqlex){sqlex.printStackTrace();}
		}
		return dirmo;
	}
	
//	Added By Karthi-->17/01/2006
	public int deleteShareDivRate(String[] fd,String[] td,double[] dr,int type)
	{
		System.out.println("Inside delete..........");
		System.out.println("Type:"+type);
		Connection conn=getConnection();
		try
		{
			for(int i=0;i<fd.length;i++)
			{
				PreparedStatement stmt_del=conn.prepareStatement("delete from ShareDivRate where fr_date=? and  to_date=? and div_rate=? " );
				
				try
				{
					stmt_del.setString(1,fd[i]);
					stmt_del.setString(2,td[i]);
					stmt_del.setDouble(3,dr[i]);
					
					if(stmt_del.executeUpdate()!=0)
					{
						System.out.println("Deleted ..!");
						return 1;
					}
					else
					{
						System.out.println("Unable to Delete...");
						return 0;
					}
				}catch(SQLException se){se.printStackTrace();}
			}
			
		}catch(SQLException sqlex){
			sqlex.printStackTrace();
			sessionContext.setRollbackOnly();
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		finally
		{
			try
			{
				conn.close();
			}catch(SQLException sqlex){sqlex.printStackTrace();}
		}
		return 0;
	}
	
//	Added By Karthi-->17/01/2006
	public int deleteDirMasterRelation(int[] did,int[] dcid,int[] rcod,String[] rtyp,int type)
	{
		System.out.println("Inside delete..........");
		System.out.println("Type:"+type);
		Connection conn=getConnection();
		try
		{
			Statement stmt_del=conn.createStatement();
			if(type==1 && did!=null)
			{ 
				System.out.println("type==2--->"+type);
				for(int i=0;i<did.length;i++)
				{
					if(stmt_del.executeUpdate("delete from DirectorMaster where director_code="+did[i]+" and cid="+dcid[i]+" ")!=0)
					{
						System.out.println("Deleted.......");
						return 1;
					}
					else
					{
						System.out.println("Not Deleted.......");
						return 0;
					}
				}
			}
			else if(type==2 && rcod!=null)
			{
				System.out.println("type==3--->"+type);
				for(int i=0;i<rcod.length;i++)
				{
					/*PreparedStatement pstmt_director=conn.prepareStatement("delete from DirectorRelation where rel_code="+rcod[i]+" and relation_type='"+rtyp[i]+"' ");
					 pstmt_director.executeUpdate();*/
					if(stmt_del.executeUpdate("delete from DirectorRelation where rel_code="+rcod[i]+" and relation_type='"+rtyp[i]+"' ")!=0)
					{
						System.out.println("Deleted.......");
						return 1;
					}
					else
					{
						System.out.println("Not Deleted.......");
						return 0;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		finally
		{
			try
			{
				conn.close();
			}catch(SQLException sqlex){sqlex.printStackTrace();}
		}
		return 0;
	}
	
//	Added By Karthi-->18/01/2006
	public int storeShareDivRate(DividendRateObject[] dr_obj,int type,String uid,String tml,String udate)
	{
		System.out.println("Inside Store..........");
		System.out.println("Type:"+type);
		System.out.println("The Length:"+dr_obj.length);
		System.out.println("Tha data is "+dr_obj);
		for(int i=0;i<dr_obj.length;i++){
			System.out.println(dr_obj[i].getFromDate());
			System.out.println(dr_obj[i].getToDate());
			System.out.println(dr_obj[i].getAmount());
			System.out.println(dr_obj[i].getRate());
			System.out.println(dr_obj[i].getCalDone());
			System.out.println(dr_obj[i].getCalopt());
		}
		
		
		
		Connection conn=getConnection();
		try
		{
			Statement stmt_shdiv=conn.createStatement();
			stmt_shdiv.executeUpdate("delete from ShareDivRate");
			for(int i=0;i<dr_obj.length;i++)
			{
				System.out.println("Going to execute-----------------------");
				PreparedStatement pstmt_shdiv=conn.prepareStatement("insert into ShareDivRate values(?,?,?,?,?,?,?,?,?)");
				pstmt_shdiv.setString(1,dr_obj[i].getFromDate());
				// System.out.println("From date:"+dr_obj[i].getFromDate());
				pstmt_shdiv.setString(2,dr_obj[i].getToDate());
				//System.out.println("to date:"+dr_obj[i].getToDate());
				pstmt_shdiv.setDouble(3,dr_obj[i].getRate());
				pstmt_shdiv.setDouble(4,dr_obj[i].getAmount());
				pstmt_shdiv.setString(5,dr_obj[i].getCalDone());
				if(dr_obj[i].getCalopt().equalsIgnoreCase("null"))
					pstmt_shdiv.setString(6,"");
				else
					pstmt_shdiv.setString(6,dr_obj[i].getCalopt());
				pstmt_shdiv.setString(7,uid);
				pstmt_shdiv.setString(8,tml);
				pstmt_shdiv.setString(9,udate);
				System.out.println("Somewhat executed----------------------------");
				pstmt_shdiv.executeUpdate();
			}
		}catch(SQLException se){
			se.printStackTrace();
			sessionContext.setRollbackOnly();
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		finally
		{
			try{conn.close();}catch(SQLException se){se.printStackTrace();}
		}
		return 1;	
	}
	
//	Added By Karthi-->18/01/2006
	public int storeDirMasterRelation(DirectorMasterObject[] dmo,int type)
	{
		System.out.println("Inside Store..........");
		System.out.println("Type:"+type);
		System.out.println("The Length:"+dmo.length);
		Connection conn=null;
		int i=0;
		try
		{
			conn=getConnection();
			Statement stmt_dir=conn.createStatement();
			if(type==1)
			{
				stmt_dir.executeUpdate("delete from DirectorMaster");
				for(;i<dmo.length;i++)
				{
					PreparedStatement pstmt_dir=conn.prepareStatement("insert into DirectorMaster values(?,?,?,?,?)");
					pstmt_dir.setInt(1,i+1);
					pstmt_dir.setInt(2,dmo[i].getDirectorId());
					pstmt_dir.setInt(3,dmo[i].getDirectorCustomerId());
					pstmt_dir.setString(4,dmo[i].getFromDate());
					pstmt_dir.setString(5,dmo[i].getToDate());
					pstmt_dir.executeUpdate();
					
				}
			}
			else if(type==2)
			{
				stmt_dir.executeUpdate("delete from DirectorRelation");
				for(;i<dmo.length;i++)
				{
					PreparedStatement pstmt_dir=conn.prepareStatement("insert into DirectorRelation values(?,?)");
					pstmt_dir.setInt(1,dmo[i].getDirectorRelationCode());
					pstmt_dir.setString(2,dmo[i].getDirectorRelationType());
					pstmt_dir.executeUpdate();
					
				}
			}
			
		}catch(SQLException se){
			se.printStackTrace();
			sessionContext.setRollbackOnly();
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		finally
		{
			try
			{
				conn.close();
			}catch(SQLException sqlex){sqlex.printStackTrace();}
		}
		return 1;
	}
	
//	Added By Karthi--24/01/2006
	public int checkCustomer(int cid)
	{
		Connection conn=null;
		System.out.println("The Customer Id:"+cid);
		try
		{
			conn=getConnection();
			Statement stmt_cid=conn.createStatement();
			ResultSet rs_cid=stmt_cid.executeQuery("select * from ShareMaster where cid="+cid+"");
			//rs_cid.next();
			rs_cid.last();
			if(rs_cid.getRow()>0)
			{
				System.out.println("The Customer Already a ShareHolder.....");
				return 1;
			}
			
		}catch(SQLException sqlex){sqlex.printStackTrace();}
		finally
		{
			try{conn.close();}catch(SQLException sqlex){sqlex.printStackTrace();}
		}
		return 0;
	}
//	Added by karthi-->03/03/2006
	public DividendObject[] getDividend(int vouch_no,String acct_type) throws RecordsNotFoundException
	{
		System.out.println("VoucherNo:"+vouch_no);
		Statement stmt;
		Connection conn=null;
		ResultSet rs_dis=null;
		DividendObject div[]=null;
		try
		{
			conn=getConnection();
			stmt=conn.createStatement();
			//rs_dis=stmt.executeQuery("select ac_type,ac_no,pay_mode,div_dt,div_amt,ddn_amt,voucher_no,cv_no,ve_user,ve_tml,ve_date from ShareDividend where ac_type='"+acct_type+"' and pay_mode='C' and cv_paid = 'F' and  voucher_no="+vouch_no+" and ve_user is null");
			rs_dis=stmt.executeQuery("select ac_type,ac_no,pay_mode,div_dt,div_amt,ddn_amt,div_ac_ty,div_ac_no,voucher_no,cv_no,ve_user,ve_tml,ve_date from ShareDividend where ac_type='"+acct_type+"' and  cv_no="+vouch_no+" and ve_user is null order by div_dt");
			rs_dis.last();
			div=new DividendObject[rs_dis.getRow()];
			rs_dis.beforeFirst();
			int i=0;
			if(rs_dis!=null){
			while(rs_dis.next())
			{
				div[i]=new DividendObject();
				div[i].setSHType(rs_dis.getString("ac_type"));
				div[i].setSHNumber(rs_dis.getInt("ac_no"));
				div[i].setPayMode(rs_dis.getString("pay_mode"));
				div[i].setDivDate(rs_dis.getString("div_dt"));
				div[i].setDivAmount(rs_dis.getFloat("div_amt"));
				div[i].setDivAccType(rs_dis.getString("div_ac_ty"));
				div[i].setDivAccNo(rs_dis.getInt("div_ac_no"));
				div[i].setDrfAmount(rs_dis.getFloat("ddn_amt"));
				div[i].setCvNumber(rs_dis.getInt("cv_no"));
				div[i].setVoucherNo(rs_dis.getInt("voucher_no"));
				
				System.out.println("div.getCvNumber:"+div[i].getCvNumber());
				System.out.println("div.getDivAccType:"+div[i].getDivAccType());
				i++;
			}
			}
			else 
				throw new RecordsNotFoundException();
		}catch(SQLException sqlex){sqlex.printStackTrace();}
		finally
		{
			try{conn.close();}catch(SQLException sqlex){sqlex.printStackTrace();}
		}
		return div;
	}
//	Added by karthi-->04/03/2006
	public int verifyDividend(DividendObject[] divobj,int vouch_no,String uid,String utml,String udate,String date)
	{
		System.out.println("inside verification.......");
		PreparedStatement pstmt=null;
		Statement stmt=null,stmt1=null,stmt2=null;
		ResultSet rs_master=null,rs_gl=null;
		Connection conn=null;
		System.out.println("divobj.length....:"+divobj.length);
		int k=0;
		
		try
		{
			conn=getConnection();
			stmt=conn.createStatement();
			stmt1=conn.createStatement();
			stmt2=conn.createStatement();
			for(int i=0;i<divobj.length;i++)
			{
				if(divobj[i].getPaymentType().equalsIgnoreCase("C") || divobj[i].getPaymentType().equalsIgnoreCase("T"))
					pstmt=conn.prepareStatement("update ShareDividend set ve_user=?,ve_tml=?,ve_date=?,paydiv_dt='"+date+"' where ac_type=? and ac_no=? and cv_no=? and div_dt=? and div_amt=? and ddn_amt=?");
				else if(divobj[i].getPaymentType().equalsIgnoreCase("PW"))
					pstmt=conn.prepareStatement("update ShareDividend set ve_user=?,ve_tml=?,ve_date=?,paydiv_dt='"+getSysDate()+"' where ac_type=? and ac_no=? and wrt_no=? and div_dt=? and div_amt=? and ddn_amt=?");
				pstmt.setString(1,uid);
				pstmt.setString(2,utml);
				pstmt.setString(3,udate);
				pstmt.setString(4,divobj[i].getSHType());
				pstmt.setInt(5,divobj[i].getSHNumber());
				pstmt.setInt(6,vouch_no);
				pstmt.setString(7,divobj[i].getDivDate());
				pstmt.setDouble(8,divobj[i].getDivAmount());
				pstmt.setDouble(9,divobj[i].getDrfAmount());
				k=pstmt.executeUpdate();
				System.out.println("K...:"+k);
				if(divobj[i].getPaymentType().equalsIgnoreCase("C"))
				{
					Statement stmt3=conn.createStatement();
					PreparedStatement ps1=null;
					int trnno=0;
					ResultSet rs_1=stmt2.executeQuery("select cv_no,voucher_no,de_tml,de_user,de_date,ve_user,ve_tml,ve_date from ShareDividend where ac_no="+divobj[i].getSHNumber()+" and ac_type='"+divobj[i].getSHType()+"' and cv_no="+vouch_no);
					if(rs_1.next())
					{
						if(String.valueOf(rs_1.getInt("voucher_no"))!=null)
							trnno=rs_1.getInt("voucher_no");
					}
					ResultSet rs_trn=stmt3.executeQuery("select * from ShareTransaction where ac_type='"+divobj[i].getSHType()+"' and ac_no="+divobj[i].getSHNumber()+" order by trn_seq desc limit 1");
					rs_trn.next();
					ShareMasterObject ms=new ShareMasterObject();
					ms.setShareAccType(rs_trn.getString("ac_type"));
					ms.setShareNumber(rs_trn.getInt("ac_no"));
					ms.setShareStatus(rs_trn.getString("susp_ind"));
					int lst_trn_seq=rs_trn.getInt("trn_seq");
					lst_trn_seq++;
					System.out.println("lst_trn_seq"+lst_trn_seq);
					double lst_share_bal=rs_trn.getDouble("share_bal");
					System.out.println("lst_share_bal"+lst_share_bal);
					System.out.println("Insert into ShareTrans....0000000000");
					if(trnno!=0)
						ps1=conn.prepareStatement("insert into ShareTransaction values(?,"+trnno+",?,?,'"+date+"','I',?,?,?,?,?,'D',?,?,?,?,?,null,null,'F','F',?,?,?,?,?,?)");
					else
						ps1=conn.prepareStatement("insert into ShareTransaction values(?,null,?,?,'"+date+"','I',?,?,?,?,?,'D',?,?,?,?,?,null,null,'F','F',?,?,?,?,?,?)");
					ps1.setString(1,ms.getShareAccType());
					ps1.setInt(2,ms.getShareNumber());
					ps1.setInt(3,lst_trn_seq);
					ps1.setDouble(4,divobj[i].getDivAmount()-divobj[i].getDrfAmount());
					ps1.setInt(5,rs_1.getInt("cv_no"));
					StringTokenizer st=new StringTokenizer(divobj[i].getDivDate(),"/");
					st.nextToken();
					st.nextToken();
					String yr=st.nextToken();
					int pre_yr=Integer.parseInt(yr)-1;
					ps1.setString(6,(String.valueOf(pre_yr).subSequence(2,4)+"-"+yr.subSequence(2,4)+"Csh Vch.No "));
					ps1.setString(7,"C");
					//ps1.setString(8,utml);//
					ps1.setString(8,null);
					ps1.setString(9,"D");
					ps1.setString(10,ms.getShareStatus());
					ps1.setDouble(11,lst_share_bal);
					ps1.setInt(12,0);
					ps1.setInt(13,0);
					ps1.setString(14,rs_1.getString("de_user"));
					ps1.setString(15,rs_1.getString("de_tml"));
					ps1.setString(16,rs_1.getString("de_date"));
					ps1.setString(17,rs_1.getString("ve_user"));
					ps1.setString(18,rs_1.getString("ve_date"));
					ps1.setString(19,rs_1.getString("ve_tml"));
					System.out.println("Before Executing Share Transaction...........");
					ps1.executeUpdate();
					System.out.println("after Executing Share Transaction...........");	
					System.out.println("Insertedd into ShareTrans....0000000000");
					
					stmt1.executeUpdate("update ShareMaster set lst_trn_seq="+lst_trn_seq+" where ac_type='"+divobj[i].getSHType()+"' and ac_no="+divobj[i].getSHNumber()+" ");
					
					rs_master=stmt1.executeQuery("select mem_cat,lst_trn_seq from ShareMaster where ac_type='"+divobj[i].getSHType()+"' and ac_no="+divobj[i].getSHNumber()+" ");
					rs_master.next();
					int mem_cat=rs_master.getInt("mem_cat");
					int lst_trn=rs_master.getInt("lst_trn_seq");
					
					rs_gl=stmt.executeQuery("select prm_gl_code,prm_gl_type from ShareParam where ac_type='"+divobj[i].getSHType()+"' and mem_cat="+mem_cat+" and prm_ty='I'");
					rs_gl.next();
					String gl_code=rs_gl.getString("prm_gl_code");
					String gl_type=rs_gl.getString("prm_gl_type");
					System.out.println("gl_code"+gl_code);
					//rs_gl.close();
					
					GLTransObject trnobj=new GLTransObject();
					//Credit Entry
					trnobj.setTrnDate(date);
					trnobj.setGLType(gl_type);
					trnobj.setGLCode(gl_code);
					trnobj.setTrnMode("C");
					trnobj.setAmount(divobj[i].getDivAmount()-divobj[i].getDrfAmount());
					trnobj.setCdind("D");
					trnobj.setAccType(divobj[i].getSHType());
					trnobj.setAccNo(String.valueOf(divobj[i].getSHNumber()));
					trnobj.setTrnType("I");
					trnobj.setTrnSeq(lst_trn);
					trnobj.setRefNo(vouch_no);
					trnobj.setVtml(utml);
					trnobj.setVid(uid);
					trnobj.setVDate(udate);
					//trnobj.setVDate(common_remote.getSysDateTime());
					System.out.println("Before writing into Share GL");
					storeGLTransaction(trnobj);
					System.out.println("after writing into Share GL");
					
					Statement stmt_name=conn.createStatement();
					ResultSet rs_name=null;
					rs_name=stmt_name.executeQuery("select distinct sm.cid,sm.ac_no,sm.ac_type,concat_ws('',cm.fname,cm.mname,cm.lname) as name from ShareMaster sm,CustomerMaster cm where sm.ac_no="+divobj[i].getSHNumber()+" and cm.cid=sm.cid");
					rs_name.next();
					System.out.println("Name : "+rs_name.getString("name"));
					PreparedStatement pstmt_insert= conn.prepareStatement("insert into DayCash (name,scroll_no,trn_type,trn_date,ac_type,ac_no,csh_amt,cd_ind,vch_type,trn_seq,attached,vch_no,de_tml,de_user,de_date,ve_user,ve_tml,ve_date) values (?,0,'I',?,?,?,?,'C','I',?,'F',?,?,?,?,?,?,?)");
					pstmt_insert.setString(1,rs_name.getString("name"));
					System.out.println("Name : "+rs_name.getString("name"));
					pstmt_insert.setString(2,date);
					pstmt_insert.setString(3,divobj[i].getSHType());
					pstmt_insert.setInt(4,divobj[i].getSHNumber());
					pstmt_insert.setDouble(5,(divobj[i].getDivAmount()-divobj[i].getDrfAmount()));
					pstmt_insert.setInt(6,lst_trn_seq);
					System.out.println("Tran.Seq : "+lst_trn_seq);
					pstmt_insert.setInt(7,vouch_no);
					pstmt_insert.setString(8,utml);
					pstmt_insert.setString(9,uid);
					pstmt_insert.setString(10,udate);
					pstmt_insert.setString(11,uid);
					pstmt_insert.setString(12,utml);
					pstmt_insert.setString(13,udate);
					//pstmt_insert.setString(9,update_div_share[0].getVoucherType());
					pstmt_insert.executeUpdate();
					//Debit Entry
					rs_gl=stmt.executeQuery("select gl_type,gl_code from GLKeyParam where ac_type='1019001'");
					rs_gl.next();
					gl_type=rs_gl.getString("gl_type");
					gl_code=rs_gl.getString("gl_code");
					rs_gl.close();
					trnobj.setCdind("C");
					//trnobj.setAccNo("0");
					//trnobj.setAccType("1019001");
					trnobj.setGLCode(gl_code);
					trnobj.setGLType(gl_type);
					//trnobj.setTrnSeq(0);
					storeGLTransaction(trnobj);
				}
				else if(divobj[i].getPaymentType().equalsIgnoreCase("T"))
				{
					PreparedStatement ps1=null;
					if(divobj[i].getDivAmount()>divobj[i].getDrfAmount())
					{	
						
						int trnno=0;
						ResultSet rs_1=stmt2.executeQuery("select cv_no,voucher_no,de_tml,de_user,de_date,ve_user,ve_tml,ve_date from ShareDividend where ac_no="+divobj[i].getSHNumber()+" and ac_type='"+divobj[i].getSHType()+"' and cv_no="+vouch_no);
						if(rs_1.next())
						{
							if(String.valueOf(rs_1.getInt("voucher_no"))!=null)
								trnno=rs_1.getInt("voucher_no");
						}
						ResultSet rs_trn=stmt1.executeQuery("select * from ShareTransaction where ac_type='"+divobj[i].getSHType()+"' and ac_no="+divobj[i].getSHNumber()+" order by trn_seq desc limit 1");
						rs_trn.next();
						ShareMasterObject ms=new ShareMasterObject();
						ms.setShareAccType(rs_trn.getString("ac_type"));
						ms.setShareNumber(rs_trn.getInt("ac_no"));
						ms.setShareStatus(rs_trn.getString("susp_ind"));
						int lst_trn_seq=rs_trn.getInt("trn_seq");
						lst_trn_seq++;
						System.out.println("lst_trn_seq"+lst_trn_seq);
						double lst_share_bal=rs_trn.getDouble("share_bal");
						System.out.println("lst_share_bal"+lst_share_bal);
						
						System.out.println("Insert into ShareTrans....0000000000");
						if(trnno!=0)
							ps1=conn.prepareStatement("insert into ShareTransaction values(?,"+trnno+",?,?,'"+date+"','I',?,?,?,?,?,'D',?,?,?,?,?,null,null,'F','F',?,?,?,?,?,?)");
						else
							ps1=conn.prepareStatement("insert into ShareTransaction values(?,null,?,?,'"+date+"','I',?,?,?,?,?,'D',?,?,?,?,?,null,null,'F','F',?,?,?,?,?,?)");
						
						ps1.setString(1,ms.getShareAccType());
						ps1.setInt(2,ms.getShareNumber());
						ps1.setInt(3,lst_trn_seq);
						ps1.setDouble(4,divobj[i].getDivAmount()-divobj[i].getDrfAmount());
						//ps1.setInt(5,0);
						ps1.setInt(5,rs_1.getInt("cv_no"));
						StringTokenizer st=new StringTokenizer(divobj[i].getDivDate(),"/");
						st.nextToken();
						st.nextToken();
						String yr=st.nextToken();
						int pre_yr=Integer.parseInt(yr)-1;
						ps1.setString(6,String.valueOf(pre_yr).subSequence(2,4)+"-"+yr.subSequence(2,4)+" "+divobj[i].getDivAccType()+" "+divobj[i].getDivAccNo());
						ps1.setString(7,"T");
						ps1.setString(8,rs_1.getString("de_tml"));
						ps1.setString(9,"D");
						ps1.setString(10,ms.getShareStatus());
						ps1.setDouble(11,lst_share_bal);
						ps1.setInt(12,0);
						ps1.setInt(13,0);
						ps1.setString(14,rs_1.getString("de_user"));
						ps1.setString(15,rs_1.getString("de_tml"));
						ps1.setString(16,rs_1.getString("de_date"));
						ps1.setString(17,rs_1.getString("ve_user"));
						ps1.setString(18,rs_1.getString("ve_date"));
						ps1.setString(19,rs_1.getString("ve_tml"));
						System.out.println("Before Executing Share Transaction...........");
						ps1.executeUpdate();
						System.out.println("after Executing Share Transaction...........");	
						
						System.out.println("Insertedd into ShareTrans....0000000000");
						
						stmt1.executeUpdate("update ShareMaster set lst_trn_seq="+lst_trn_seq+" where ac_type='"+divobj[i].getSHType()+"' and ac_no="+divobj[i].getSHNumber()+" ");
						
						System.out.println("divant>ddnamt");
						AccountTransObject at=new AccountTransObject(); 
						//at=null;
						//System.out.println("object at created"+sbty);
						at.setAccType(divobj[i].getDivAccType());
						at.setAccNo(divobj[i].getDivAccNo());
						at.setTransType("R");
						at.setTransAmount(divobj[i].getDivAmount()-divobj[i].getDrfAmount());
						at.setTransMode("T");
						at.setTransSource(utml);
						at.setTransDate(date);
						at.setCdInd("C");
						at.setChqDDNo(0);
						at.setChqDDDate("");
						String dt=divobj[i].getDivDate();
						/*StringTokenizer st = new StringTokenizer(dt,"/");
						 String yr=null;
						 while(st.hasMoreTokens())
						 yr=st.nextToken();
						 System.out.println("Year is...:"+yr);
						 at.setTransNarr("Dvd. "+divobj[i].getSHNumber()+" yr."+yr);*/
						at.setTransNarr(divobj[i].getSHType()+" "+divobj[i].getSHNumber());
						at.setRef_No(vouch_no);
						at.setPayeeName("");
						at.setCloseBal(divobj[i].getDivAmount()-divobj[i].getDrfAmount());
						at.setLedgerPage(0);
						at.uv.setUserTml(utml);
						at.uv.setUserId(uid);
						at.uv.setUserDate(udate);
						at.uv.setVerTml(utml);
						at.uv.setVerId(uid);
						at.uv.setVerDate(udate);
						
						if(divobj[i].getDivAccType()!=null)
							at.setGLRefCode(Integer.parseInt(divobj[i].getDivAccType()));
						else
							at.setGLRefCode(0);
						
						System.out.println("commonremote b4");
						common_local.storeAccountTransaction(at);
						System.out.println("commonremote after function cal");
					}
					
					rs_master=stmt.executeQuery("select mem_cat,lst_trn_seq from ShareMaster where ac_type='"+divobj[i].getSHType()+"' and ac_no="+divobj[i].getSHNumber()+" ");
					rs_master.next();
					int mem_cat=rs_master.getInt("mem_cat");
					int lst_trn_seq=rs_master.getInt("lst_trn_seq");
					rs_gl=stmt.executeQuery("select prm_gl_code,prm_gl_type from ShareParam where ac_type='"+divobj[i].getSHType()+"' and mem_cat="+mem_cat+" and prm_ty='I'");
					rs_gl.next();
					String gl_code=rs_gl.getString("prm_gl_code");
					System.out.println("gl_code"+gl_code);
					String gl_type=rs_gl.getString("prm_gl_type");
					System.out.println("gl_code"+gl_type);
					//rs_gl.close();
					
					GLTransObject trnobj=new GLTransObject();
					trnobj.setTrnDate(date);
					trnobj.setGLType(gl_type);
					trnobj.setGLCode(gl_code);
					trnobj.setTrnMode("T");
					trnobj.setAmount(divobj[i].getDivAmount()-divobj[i].getDrfAmount());
					trnobj.setCdind("D");
					//trnobj.setAccType(update_div_transfer[i].getSHType()+" "+ms.getShareStatus());
					trnobj.setAccType(divobj[i].getSHType());
					trnobj.setAccNo(String.valueOf(divobj[i].getSHNumber()));
					trnobj.setTrnType("I");
					trnobj.setTrnSeq(lst_trn_seq);
					trnobj.setRefNo(vouch_no);
					trnobj.setVtml(utml);
					trnobj.setVid(uid);
					trnobj.setVDate(udate);
					System.out.println("Before writing into Share GL");
					storeGLTransaction(trnobj);
					System.out.println("after writing into Share GL");
				}
				else if(divobj[i].getPaymentType().equalsIgnoreCase("PW"))
				{
					int trnno=0;
					ResultSet rs_1=stmt2.executeQuery("select wrt_no,voucher_no,de_tml,de_user,de_date,ve_user,ve_tml,ve_date from ShareDividend where ac_no="+divobj[i].getSHNumber()+" and ac_type='"+divobj[i].getSHType()+"' and wrt_no="+vouch_no);
					if(rs_1.next())
					{
						if(String.valueOf(rs_1.getInt("voucher_no"))!=null)
							trnno=rs_1.getInt("voucher_no");
					}
					ResultSet rs_trn=stmt1.executeQuery("select * from ShareTransaction where ac_type='"+divobj[i].getSHType()+"' and ac_no="+divobj[i].getSHNumber()+" order by trn_seq desc limit 1");
					rs_trn.next();
					ShareMasterObject ms=new ShareMasterObject();
					ms.setShareAccType(rs_trn.getString("ac_type"));
					ms.setShareNumber(rs_trn.getInt("ac_no"));
					ms.setShareStatus(rs_trn.getString("susp_ind"));
					int lst_trn_seq=rs_trn.getInt("trn_seq");
					lst_trn_seq++;
					System.out.println("lst_trn_seq"+lst_trn_seq);
					double lst_share_bal=rs_trn.getDouble("share_bal");
					System.out.println("lst_share_bal"+lst_share_bal);
					
					System.out.println("Insert into ShareTrans....0000000000");
					PreparedStatement ps1=conn.prepareStatement("insert into ShareTransaction values(?,"+trnno+",?,?,'"+date+"','I',?,?,?,?,?,'D',?,?,?,?,?,null,null,'F','F',?,?,?,?,?,?)");
					ps1.setString(1,ms.getShareAccType());
					ps1.setInt(2,ms.getShareNumber());
					ps1.setInt(3,lst_trn_seq);
					ps1.setDouble(4,divobj[i].getDivAmount()-divobj[i].getDrfAmount());
					ps1.setInt(5,rs_1.getInt("wrt_no"));
					StringTokenizer st=new StringTokenizer(divobj[i].getDivDate(),"/");
					st.nextToken();
					st.nextToken();
					String yr=st.nextToken();
					int pre_yr=Integer.parseInt(yr)-1;
					ps1.setString(6,String.valueOf(pre_yr).subSequence(2,4)+"-"+yr.subSequence(2,4)+" "+"Prnt.Wrnt "+rs_1.getInt("wrt_no"));
					//ps1.setString(6,null);
					ps1.setString(7,"W");
					ps1.setString(8,utml);
					ps1.setString(9,"D");
					ps1.setString(10,ms.getShareStatus());
					ps1.setDouble(11,lst_share_bal);
					ps1.setInt(12,0);
					ps1.setInt(13,0);
					ps1.setString(14,rs_1.getString("de_user"));
					ps1.setString(15,rs_1.getString("de_tml"));
					ps1.setString(16,rs_1.getString("de_date"));
					ps1.setString(17,rs_1.getString("ve_user"));
					ps1.setString(18,rs_1.getString("ve_date"));
					ps1.setString(19,rs_1.getString("ve_tml"));
					System.out.println("Before Executing Share Transaction...........");
					ps1.executeUpdate();
					System.out.println("after Executing Share Transaction...........");	
					System.out.println("Insertedd into ShareTrans....0000000000");
					
					stmt1.executeUpdate("update ShareMaster set lst_trn_seq="+lst_trn_seq+" where ac_type='"+divobj[i].getSHType()+"' and ac_no="+divobj[i].getSHNumber()+" ");
					
					
					rs_master=stmt.executeQuery("select mem_cat,lst_trn_seq from ShareMaster where ac_type='"+divobj[i].getSHType()+"' and ac_no="+divobj[i].getSHNumber()+" ");
					rs_master.next();
					int mem_cat=rs_master.getInt("mem_cat");
					int lst_trn=rs_master.getInt("lst_trn_seq");
					rs_gl=stmt.executeQuery("select prm_gl_code,prm_gl_type from ShareParam where ac_type='"+divobj[i].getSHType()+"' and mem_cat="+mem_cat+" and prm_ty='I'");
					rs_gl.next();
					String gl_code=rs_gl.getString("prm_gl_code");
					System.out.println("gl_code"+gl_code);
					String gl_type=rs_gl.getString("prm_gl_type");
					System.out.println("gl_type"+gl_type);
					rs_gl.close();
					
					GLTransObject trnobj=new GLTransObject();
					trnobj.setTrnDate(date);
					trnobj.setGLType(gl_type);
					/*if(sm.getShareStatus().equals("T"))
					 trnobj.setGLCode(sm.getSuspGLCode());				
					 else
					 trnobj.setGLCode(sm.getGLCode());*/
					trnobj.setGLCode(gl_code);
					trnobj.setTrnMode("W");
					trnobj.setAmount(divobj[i].getDivAmount()-divobj[i].getDrfAmount());
					trnobj.setCdind("D");
					trnobj.setAccType(divobj[i].getSHType());
					trnobj.setAccNo(String.valueOf(divobj[i].getSHNumber()));
					trnobj.setTrnType("I");
					trnobj.setTrnSeq(lst_trn);
					trnobj.setRefNo(vouch_no);
					trnobj.setVtml(utml);
					trnobj.setVid(uid);
					trnobj.setVDate(udate);
					System.out.println("Before writing into Share GL");
					storeGLTransaction(trnobj);
					System.out.println("after writing into Share GL");
					
				}
				else if(divobj[i].getPaymentType().equalsIgnoreCase("PO"))
				{
					PreparedStatement ps1=null;
					if(divobj[i].getDivAmount()>divobj[i].getDrfAmount())
					{	
						int trnno=0;
						ResultSet rs_1=stmt2.executeQuery("select cv_no,voucher_no,de_tml,de_user,de_date,ve_user,ve_tml,ve_date from ShareDividend where ac_no="+divobj[i].getSHNumber()+" and ac_type='"+divobj[i].getSHType()+"' and cv_no="+vouch_no);
						if(rs_1.next())
						{
							if(String.valueOf(rs_1.getInt("voucher_no"))!=null)
								trnno=rs_1.getInt("voucher_no");
						}
						ResultSet rs_trn=stmt1.executeQuery("select * from ShareTransaction where ac_type='"+divobj[i].getSHType()+"' and ac_no="+divobj[i].getSHNumber()+" order by trn_seq desc limit 1");
						rs_trn.next();
						ShareMasterObject ms=new ShareMasterObject();
						ms.setShareAccType(rs_trn.getString("ac_type"));
						ms.setShareNumber(rs_trn.getInt("ac_no"));
						ms.setShareStatus(rs_trn.getString("susp_ind"));
						int lst_trn_seq=rs_trn.getInt("trn_seq");
						lst_trn_seq++;
						System.out.println("lst_trn_seq"+lst_trn_seq);
						double lst_share_bal=rs_trn.getDouble("share_bal");
						System.out.println("lst_share_bal"+lst_share_bal);
						System.out.println("Insert into ShareTrans table....");
						if(trnno!=0)
							ps1=conn.prepareStatement("insert into ShareTransaction values(?,"+trnno+",?,?,'"+date+"','%d/%m/%Y'),'I',?,?,?,?,?,'D',?,?,?,?,?,null,null,'F','F',?,?,?,?,?,?)");
						else
							ps1=conn.prepareStatement("insert into ShareTransaction values(?,"+trnno+",?,?,'"+date+"','I',?,?,?,?,?,'D',?,?,?,?,?,null,null,'F','F',?,?,?,?,?,?)");
						
						ps1.setString(1,ms.getShareAccType());
						ps1.setInt(2,ms.getShareNumber());
						ps1.setInt(3,lst_trn_seq);
						ps1.setDouble(4,divobj[i].getDivAmount()-divobj[i].getDrfAmount());
						//ps1.setInt(5,0);
						ps1.setInt(5,rs_1.getInt("cv_no"));
						StringTokenizer st=new StringTokenizer(divobj[i].getDivDate(),"/");
						st.nextToken();
						st.nextToken();
						String yr=st.nextToken();
						int pre_yr=Integer.parseInt(yr)-1;
						ps1.setString(6,String.valueOf(pre_yr).subSequence(2,4)+"-"+yr.subSequence(2,4)+" "+"Payodr. "+trnno+"");
						//ps1.setString(6,"Payodr. "+trnno+"");
						ps1.setString(7,"PO");
						ps1.setString(8,utml);
						ps1.setString(9,"D");
						ps1.setString(10,ms.getShareStatus());
						ps1.setDouble(11,lst_share_bal);
						ps1.setInt(12,0);
						ps1.setInt(13,0);
						ps1.setString(14,rs_1.getString("de_user"));
						ps1.setString(15,rs_1.getString("de_tml"));
						ps1.setString(16,rs_1.getString("de_date"));
						ps1.setString(17,rs_1.getString("ve_user"));
						ps1.setString(18,rs_1.getString("ve_date"));
						ps1.setString(19,rs_1.getString("ve_tml"));
						System.out.println("Before Executing Share Transaction...........");
						ps1.executeUpdate();
						System.out.println("after Executing Share Transaction...........");	
						System.out.println("Inserted into ShareTransaction table....");
						stmt1.executeUpdate("update ShareMaster set lst_trn_seq="+lst_trn_seq+" where ac_type='"+divobj[i].getSHType()+"' and ac_no="+divobj[i].getSHNumber()+" ");
						rs_master=stmt1.executeQuery("select mem_cat,lst_trn_seq from ShareMaster where ac_type='"+divobj[i].getSHType()+"' and ac_no="+divobj[i].getSHNumber()+" ");
						rs_master.next();
						int mem_cat=rs_master.getInt("mem_cat");
						int lst_trn=rs_master.getInt("lst_trn_seq");
						rs_gl=stmt.executeQuery("select prm_gl_code,prm_gl_type from ShareParam where ac_type='"+divobj[i].getSHType()+"' and mem_cat="+mem_cat+" and prm_ty='I'");
						rs_gl.next();
						String gl_code=rs_gl.getString("prm_gl_code");
						System.out.println("gl_code"+gl_code);
						String gl_type=rs_gl.getString("prm_gl_type");
						System.out.println("gl_code"+gl_type);
						rs_gl.close();
						
						PayOrderObject po = new PayOrderObject();
						po.setPOType("X");
						ResultSet rs_customer=stmt.executeQuery("select distinct sm.ac_no,sm.cid,concat_ws('',cm.fname,cm.mname,cm.lname)as name, cm.custtype from ShareMaster sm,CustomerMaster cm where sm.ac_type='"+divobj[i].getSHType()+"'and sm.ac_no="+divobj[i].getSHNumber()+" and cm.cid=sm.cid");
						rs_customer.next();
						po.setPOPayee(rs_customer.getString("name"));
						po.setCustType(rs_customer.getInt("custtype"));
						po.setPOAccType(divobj[i].getSHType());
						po.setPOAccNo(divobj[i].getSHNumber());
						po.setPOGlCode(Integer.parseInt(gl_code));
						po.setPOGlType(gl_type);
						ResultSet rs_glname=stmt.executeQuery("select gl_name from GLMaster where gl_type='"+gl_type+"' and gl_code="+gl_code+" and ((from_date<='"+Validations.convertYMD(date)+"' and to_date is null) or (from_date<='"+Validations.convertYMD(date)+"' and to_date>='"+Validations.convertYMD(date)+"'))");
						rs_glname.next();
						po.setPOGlName(rs_glname.getString("gl_name"));
						po.setCommissionAmount(0);
						po.uv.setUserTml(utml);
						po.uv.setUserId(uid);
						po.uv.setUserDate(udate);
						po.uv.setVerId(uid);
						po.uv.setVerTml(utml);
						po.uv.setVerDate(udate);
						po.setPOAmount(divobj[i].getDivAmount());
						int value=common_remote.storePayOrder(po);
						System.out.println("Inside po"+value);
						GLTransObject trnobj=new GLTransObject();
						trnobj.setTrnDate(date);
						trnobj.setGLType(gl_type);
						trnobj.setGLCode(gl_code);
						trnobj.setTrnMode("T");
						trnobj.setAmount(divobj[i].getDivAmount());
						trnobj.setCdind("D");
						trnobj.setAccType(divobj[i].getSHType());
						trnobj.setAccNo(String.valueOf(divobj[i].getSHNumber()));
						trnobj.setTrnType("I");
						trnobj.setTrnSeq(lst_trn);
						trnobj.setRefNo(value);
						trnobj.setVtml(utml);
						trnobj.setVid(uid);
						trnobj.setVDate(udate);
						System.out.println("Before writing into Share GL");
						storeGLTransaction(trnobj);
						System.out.println("after writing into Share GL");
						
						ResultSet rs1 = stmt.executeQuery("select gl_code,gl_type from GLKeyParam where ac_type= 1016001 and code=1");
						if(rs1.next())
						{
							GLTransObject trnobj1=new GLTransObject();
							trnobj1.setTrnDate(date);
							trnobj1.setGLType(rs1.getString(2));
							trnobj1.setGLCode(rs1.getString(1));
							trnobj1.setTrnMode("T");
							trnobj1.setAmount(trnobj.getAmount());
							trnobj1.setCdind("C");
							trnobj1.setAccType("1016001");
							trnobj1.setAccNo(String.valueOf(value));
							trnobj1.setTrnType("R");
							trnobj1.setRefNo(value);						
							trnobj1.setVtml(utml);
							trnobj1.setVid(uid);
							trnobj1.setVDate(udate);
							storeGLTransaction(trnobj1);
						}
						stmt.executeUpdate("update ShareTransaction set ref_no="+value+",trn_narr='"+String.valueOf(pre_yr).subSequence(2,4)+"-"+yr.subSequence(2,4)+" "+"PayOrdr. "+value+"' where ac_no="+divobj[i].getSHNumber()+" and ac_type='"+divobj[i].getSHType()+"' and trn_mode='PO' and ref_no="+vouch_no+" ");
					}
				}
			}
			if(k==1)
			{
				System.out.println("Updated Sucessfully.......");
				return 1;
			}
			else
			{
				System.out.println("Unable to Update.........");
				return 0;
			}
			
		}catch(SQLException sqlex){
			sqlex.printStackTrace();
			sessionContext.setRollbackOnly();
			return 0;
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
			return 0;
		}
		finally
		{
			try{conn.close();}catch(SQLException sqlex){sqlex.printStackTrace();}
		}
	}
	
	
//	Added by karthi-->06/03/2006
	public int deleteDividend(int vouch_no,String acc_type,int acc_no)
	{
		System.out.println("VoucherNo.:"+vouch_no);
		System.out.println("AccountType:"+acc_type);
		System.out.println("AccountNo.:"+acc_no);
		Connection conn=null;
		Statement stmt=null;
		int r=0;
		try
		{
			conn=getConnection();
			stmt=conn.createStatement();
			stmt.addBatch("update ShareDividend set pay_mode='C',cv_dt=null,div_ac_ty=null,div_ac_no=0,cv_paid='F',voucher_no=null where ac_type='"+acc_type+"' and ac_no="+acc_no+" and voucher_no="+vouch_no+"");
			stmt.addBatch("delete from ShareTransaction where ac_type='"+acc_type+"' and ac_no="+acc_no+" and ref_no="+vouch_no+" and trn_type='I'");
			stmt.addBatch("update ShareMaster set lst_trn_seq=lst_trn_seq-1 where ac_type='"+acc_type+"' and ac_no="+acc_no+"");
			int[] count=stmt.executeBatch();
			if(count.length==3)
			{
				System.out.println("The process is successfully executed....");
				return 1;
			}
			else 
			{
				System.out.println("The process is not executed properly....");
				return 0;
			}
		}catch(SQLException sqlex){
			sqlex.printStackTrace();
			sessionContext.setRollbackOnly();
			return 0;
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
			return 0;
		}
		finally
		{
			try{conn.close();}catch(SQLException sqlex){sqlex.printStackTrace();}
		}
	}
//	Added by karthi-->06/03/2006
	public int payOrder(DividendObject[] update_div_share,String utml,String uid,String udate,int type,String date)throws RecordsNotFoundException
	{
		System.out.println("Type...:"+type);
		Connection conn=null;
		Statement stmt=null;
		Statement cstmt=null;
		Statement stmt1=null;
		double divamt=0,ddnamt=0,double_total_amount=0;
		int accno=0,divaccno=0;
		String acctype=null,divacctype=null;
		int lst_voucher_no=0;
		
		try
		{
			System.out.println("getting connection");
			conn=getConnection();
			stmt=conn.createStatement();
			cstmt=conn.createStatement();
			stmt1=conn.createStatement();
			ResultSet rs=null;
			System.out.println("got connection");
			lst_voucher_no = common_local.getModulesColumn("lst_rct_no",update_div_share[0].getSHType());//Added by karthi-->08/03/2006
			if(lst_voucher_no==0)
				lst_voucher_no = common_local.getModulesColumn("lst_rct_no",update_div_share[0].getSHType());//Added by karthi-->08/03/2006
			for(int i=0;i<update_div_share.length;i++)
			{
				System.out.println("_ac_no...tranfser"+update_div_share[i].getSHNumber());
				System.out.println("_ac_type......tranfser"+update_div_share[i].getSHType());
				
				rs=stmt.executeQuery("select ac_type,ac_no,div_ac_no,div_ac_ty,div_amt,ddn_amt,cv_paid from ShareDividend where ac_no="+update_div_share[i].getSHNumber()+" and ac_type='"+update_div_share[i].getSHType()+"' and cv_paid='F' ");
				System.out.println("executed query 1");
				rs.last();
				if(rs.getRow()==0)
				{	
					System.out.println("result set empty");
					throw new RecordsNotFoundException();
					
				}
				rs.beforeFirst();
				while(rs.next())
				{
					accno=rs.getInt("ac_type");
					acctype=rs.getString("ac_no");
					divaccno=rs.getInt("div_ac_no");
					divacctype=rs.getString("div_ac_ty");
					divamt=rs.getDouble("div_amt");
					ddnamt=rs.getDouble("ddn_amt");
					double_total_amount=(divamt-ddnamt);
					System.out.println("inside while looooop");
					if(divamt>ddnamt)
					{	
						System.out.println("divant>ddnamt");
						if(type==0)
						{
							System.out.println("Type...:"+type);
							
							//ResultSet rs_1=stmt1.executeQuery("select sh_trn_no from GenParam");
							/*ResultSet rs_1=stmt1.executeQuery("select last_trf_scroll_no from Modules where modulecode='"+update_div_share[i].getSHType()+"'");
							 if(rs_1.next())
							 //trnno=rs_1.getInt("sh_trn_no");
							  trnno=rs_1.getInt("last_trf_scroll_no");
							  
							  System.out.println("trn no"+trnno);
							  trnno++;
							  System.out.println("trn no++"+trnno);		
							  //stmt1.executeUpdate("update GenParam set sh_trn_no="+trnno);
							   stmt1.executeUpdate("update Modules set last_trf_scroll_no="+trnno+" where modulecode='"+update_div_share[i].getSHType()+"'");
							   System.out.println("final trn no isss,,,,,"+trnno);*/
							
							//added at the time of verification-->karthi
							/*	ResultSet rs_master=stmt1.executeQuery("select mem_cat from ShareMaster where ac_type='"+update_div_share[i].getSHType()+"' and ac_no="+update_div_share[i].getSHNumber()+" ");
							 rs_master.next();
							 int mem_cat=rs_master.getInt("mem_cat");
							 ResultSet rs_gl=stmt.executeQuery("select prm_gl_code,prm_gl_type from ShareParam where ac_type='"+update_div_share[i].getSHType()+"' and mem_cat="+mem_cat+" and prm_ty='I'");
							 rs_gl.next();
							 String gl_code=rs_gl.getString("prm_gl_code");
							 System.out.println("gl_code"+gl_code);
							 String gl_type=rs_gl.getString("prm_gl_type");
							 System.out.println("gl_code"+gl_type);
							 rs_gl.close();
							 
							 PayOrderObject po = new PayOrderObject();
							 po.setPOType("X");
							 ResultSet rs_customer=stmt.executeQuery("select distinct sm.ac_no,sm.cid,concat_ws('',cm.fname,cm.mname,cm.lname)as name, cm.custtype from ShareMaster sm,CustomerMaster cm where sm.ac_type='"+update_div_share[i].getSHType()+"'and sm.ac_no="+update_div_share[i].getSHNumber()+" and cm.cid=sm.cid");
							 rs_customer.next();
							 po.setPOPayee(rs_customer.getString("name"));
							 po.setCustType(rs_customer.getInt("custtype"));
							 po.setPOAccType(update_div_share[i].getSHType());
							 po.setPOAccNo(update_div_share[i].getSHNumber());
							 po.setPOGlCode(Integer.parseInt(gl_code));
							 po.setPOGlType(gl_type);
							 ResultSet rs_glname=stmt.executeQuery("select gl_name from GLMaster where gl_type='"+gl_type+"' and gl_code="+gl_code+" ");
							 rs_glname.next();
							 po.setPOGlName(rs_glname.getString("gl_name"));
							 po.setCommissionAmount(0);
							 po.uv.setUserTml(utml);
							 po.uv.setUserId(uid);
							 po.uv.setUserDate(common_remote.getSysDateTime());
							 po.uv.setVerId(uid);
							 po.uv.setVerTml(utml);
							 po.uv.setVerDate(common_remote.getSysDateTime());
							 po.setPOAmount(double_total_amount);
							 int value=common_remote.storePayOrder(po);
							 System.out.println("Inside po"+value);
							 
							 GLTransObject trnobj=new GLTransObject();
							 
							 trnobj.setTrnDate(getSysDate());
							 trnobj.setGLType(gl_type);
							 trnobj.setGLCode(gl_code);
							 trnobj.setTrnMode("P");
							 trnobj.setTrnSeq(lst_trn_seq);
							 trnobj.setAmount(double_total_amount);
							 trnobj.setCdind("D");
							 trnobj.setAccType(update_div_share[i].getSHType());
							 trnobj.setAccNo(String.valueOf(update_div_share[i].getSHNumber()));
							 trnobj.setTrnType("I");
							 trnobj.setRefNo(value);
							 trnobj.setVtml(utml);
							 trnobj.setVid(uid);
							 trnobj.setVDate(common_remote.getSysDateTime());
							 System.out.println("Before writing into Share GL");
							 storeGLTransaction(trnobj);
							 System.out.println("after writing into Share GL");
							 
							 ResultSet rs1 = stmt.executeQuery("select gl_code,gl_type from GLKeyParam where ac_type= 1016001 and code=1");
							 if(rs1.next())
							 {
							 GLTransObject trnobj1=new GLTransObject();
							 trnobj1.setTrnDate(getSysDate());
							 trnobj1.setGLType(rs1.getString(2));
							 trnobj1.setGLCode(rs1.getString(1));
							 trnobj1.setTrnMode("P");
							 trnobj1.setAmount(trnobj.getAmount());
							 trnobj1.setCdind("C");
							 trnobj1.setAccType("1016001");
							 trnobj1.setAccNo(String.valueOf(value));
							 trnobj1.setTrnType(" ");
							 trnobj1.setRefNo(value);						
							 trnobj1.setVtml(utml);
							 trnobj1.setVid(uid);
							 trnobj1.setVDate(common_remote.getSysDateTime());
							 storeGLTransaction(trnobj1);
							 }*/
							//stmt.executeUpdate("update ShareTransaction set ref_no="+value+",trn_narr='PayOrdr. "+value+"' where ac_no="+update_div_share[i].getSHNumber()+" and ac_type='"+update_div_share[i].getSHType()+"' and trn_mode='PO' and trn_no="+trnno+" ");
							stmt.executeUpdate("update ShareDividend set pay_mode='P',cv_no="+lst_voucher_no+",cv_dt='"+date+"',paydiv_dt='"+date+"'  where ac_no="+update_div_share[i].getSHNumber()+" and ac_type='"+update_div_share[i].getSHType()+"' and div_dt='"+update_div_share[i].getDivDate()+"'");
							
						}
						else
						{
							stmt.executeUpdate("update ShareDividend set cv_paid='F',pay_mode='P',div_ac_no=0,div_ac_ty=null where ac_no="+update_div_share[i].getSHNumber()+" and ac_type='"+update_div_share[i].getSHType()+"' and div_dt='"+update_div_share[i].getDivDate()+"' and voucher_no="+update_div_share[i].getVoucherNo()+" ");
							stmt.executeUpdate("update ShareTransaction set trn_mode='PO',trn_narr='PayOrdr. "+update_div_share[i].getVoucherNo()+"',trn_source='"+utml+"' where ac_no="+update_div_share[i].getSHNumber()+" and ac_type='"+update_div_share[i].getSHType()+"' and ref_no="+update_div_share[i].getVoucherNo()+" and trn_type='I'");
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
			return 0;
		}
		finally{
			try{
				System.out.println("conn.to close");
				conn.close();
				System.out.println("conn.closed");
			}catch(Exception e){e.printStackTrace();}
		}	
		return 0;
	}
	
	public int RetriveReferenceNo(String modulecode)
	{
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		System.out.println("Modulecode....."+modulecode);
		int ref_no=0;
		try
		{
			conn=getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery("select top_margin from Modules where modulecode='"+modulecode+"'");
			rs.next();
			ref_no=rs.getInt("top_margin");
			if((stmt.executeUpdate("update Modules set top_margin="+(ref_no+1)+" where modulecode='"+modulecode+"'"))>0)
			{
				System.out.println("Updated Successfully.....");
				return ref_no;
			}
			else
			{
				System.out.println("Unable to update.....");
				return 0;
			}
			
		}catch(SQLException ex){
			ex.printStackTrace();
			sessionContext.setRollbackOnly();
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		finally
		{
			try{conn.close();}catch(SQLException ex){ex.printStackTrace();}
			
		}
		return 0;
	}
	
	public float InsertintoShareTran(String ac_type,int ac_no,float amnt,String type,int trn_no,String date,String de_user,String de_tml,String de_date)throws RecordsNotFoundException,IllegalAccessException
	{
		Connection conn=null;
		Statement stmt=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		float share_bal;
		try
		{
			conn=getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery("select sm.sh_ind,sm.share_val,sm.ln_availed,sm.lst_trn_seq,st.share_bal from ShareMaster sm,ShareTransaction st where sm.ac_no="+ac_no+" and sm.ac_type='"+ac_type+"' and sm.ac_type=st.ac_type and sm.ac_no=st.ac_no order by trn_seq desc limit 1");
			rs.last();
			if(rs.getRow()==0)
				throw new RecordsNotFoundException();
			else
			{
				rs.beforeFirst();
				rs.next();
				if(type.equalsIgnoreCase("C"))//type C ==>For CreditEntry
					pstmt=conn.prepareStatement("insert into ShareTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				else
				{  //type D ==>For DebitEntry
					share_bal=rs.getFloat("sm.share_val");
					if(rs.getString("ln_availed").equalsIgnoreCase("T") || (share_bal<amnt))
					{throw new IllegalAccessException(String.valueOf(share_bal));}
					else 
						pstmt=conn.prepareStatement("insert into ShareTransaction values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				}
				
				pstmt.setString(1,ac_type);
				pstmt.setInt(2,trn_no);
				pstmt.setInt(3,ac_no);
				pstmt.setInt(4,(rs.getInt(4)+1));
				pstmt.setString(5,date);
				float total_c=rs.getFloat("st.share_bal")+amnt;
				float total_d=rs.getFloat("st.share_bal")-amnt;
				
				if(type.equalsIgnoreCase("C"))
				{
					pstmt.setString(6,"A");
					pstmt.setString(12,"C");
					pstmt.setFloat(15,total_c);
					stmt.executeUpdate("insert into ShareMasterLog select * from ShareMaster where ac_type='"+ac_type+"' and ac_no="+ac_no);
					stmt.executeUpdate("update ShareMaster set share_val=(share_val+"+amnt+"),lst_trn_seq=lst_trn_seq+1 where ac_type='"+ac_type+"' and ac_no="+ac_no);
				}
				else
				{
					pstmt.setString(6,"W");
					pstmt.setString(12,"D");
					pstmt.setFloat(15,total_d);
					stmt.executeUpdate("insert into ShareMasterLog select * from ShareMaster where ac_type='"+ac_type+"' and ac_no="+ac_no);
					stmt.executeUpdate("update ShareMaster set share_val=(share_val-"+amnt+"),lst_trn_seq=lst_trn_seq+1 where ac_type='"+ac_type+"' and ac_no="+ac_no);
				}
				
				pstmt.setFloat(7,amnt);
				pstmt.setInt(8,0);
				pstmt.setString(9,null);
				pstmt.setString(10,"T");
				pstmt.setString(11,null);
				pstmt.setString(13,"D");
				pstmt.setString(14,rs.getString("sm.sh_ind"));
				pstmt.setInt(16,0);
				pstmt.setInt(17,0);
				pstmt.setInt(18,0);
				pstmt.setString(19,null);
				pstmt.setString(20,"F");
				pstmt.setString(21,"F");
				pstmt.setString(22,de_user);
				pstmt.setString(23,de_tml);
				pstmt.setString(24,de_date);
				pstmt.setString(25,null);
				pstmt.setString(26,null);
				pstmt.setString(27,null);
				
				float a=pstmt.executeUpdate();
				if(a==1.0)
				{System.out.println("Successfully Inserted....");return a;}
				else
				{System.out.println("Unable to  Inser....");return 0;}
				
			}
			
		}catch(SQLException sqlex){
			sqlex.printStackTrace();
			sessionContext.setRollbackOnly();
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		finally
		{
			try{conn.close();}catch(SQLException sqlex){sqlex.printStackTrace();}  
			
		}
		return 0;
	}
	
//	Added by Karthi-->19/04/2006
	public int updateShareMaster(ShareMasterObject smobj,String ac_type,int ac_no)throws RecordsNotFoundException
	{
		System.out.println("Ac type "+ac_type);
		System.out.println("Ac no"+ac_no);
		System.out.println("Master obj is"+smobj);
		Connection conn=null;
		Statement stmt=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try
		{
			conn=getConnection();
			stmt=conn.createStatement();
			System.out.println("in bean");
			
			System.out.println(smobj.getShareStatus());
			System.out.println(smobj.getBranchCode());
			System.out.println(smobj.getMailingAddress());
			System.out.println(smobj.getMemberCategory());
			System.out.println(smobj.getIssueDate());
			System.out.println(smobj.getIntroducerAcctype());
			System.out.println(smobj.getIntroducerAccno());
			System.out.println(smobj.getDivUptoDate());
			System.out.println(smobj.getCloseDate());
			System.out.println(smobj.getPayMode());
			System.out.println(smobj.getPaymentAcctype());
			System.out.println(smobj.getPaymentAccno());
			
			System.out.println(smobj.getRelationtoDirector());
			System.out.println(smobj.getRelationDesc());
			System.out.println("out of bean");
			
			stmt.executeUpdate("insert into ShareMasterLog select * from ShareMaster where ac_type='"+smobj.getShareAccType()+"' and ac_no="+smobj.getShareNumber());
			System.out.println("suraj 1");
			pstmt=conn.prepareStatement("update ShareMaster set sh_ind=?,br_code=?,addr_type=?,mem_cat=?,mem_issuedate=?,intr_ac_type=?,intr_ac_no=?,div_uptodt=?,mem_cl_date=?,pay_mode=?,pay_ac_type=?,pay_ac_no=?,rel_code=?,rel_director=?  where ac_type='"+smobj.getShareAccType()+"' and ac_no="+smobj.getShareNumber()+"");
			pstmt.setString(1,smobj.getShareStatus());
			pstmt.setInt(2,smobj.getBranchCode());
			pstmt.setInt(3,smobj.getMailingAddress());
			pstmt.setInt(4,smobj.getMemberCategory());
			pstmt.setString(5,smobj.getIssueDate());
			
			System.out.println("suraj 2");
			System.out.println("Ac type "+ac_type);
			System.out.println("Ac no"+ac_no);
			System.out.println("The ac type from master object is"+smobj.getShareAccType());
			System.out.println("The ac nom from master opbject is"+smobj.getShareNumber());
			rs=stmt.executeQuery("select allotmentdt from CertificateMaster where sh_ac_type='"+smobj.getShareAccType()+"'and sh_ac_no="+smobj.getShareNumber()+" limit 1");
			
			
			rs.last();
			System.out.println("ROW IS"+rs.getRow());
			if(rs.getRow()>0)
			{
				rs.beforeFirst();
				rs.next();
				System.out.println("suraj 3");
				if(smobj.getIssueDate()!=rs.getString(1))
					stmt.executeUpdate("update CertificateMaster set allotmentdt='"+smobj.getIssueDate()+"' where sh_ac_type='"+smobj.getShareAccType()+"'and sh_ac_no="+smobj.getShareNumber());
			}
					
			System.out.println("suraj 4");
			pstmt.setString(6,smobj.getIntroducerAcctype());
			pstmt.setInt(7,smobj.getIntroducerAccno());
			pstmt.setString(8,smobj.getDivUptoDate());
			pstmt.setString(9,smobj.getCloseDate());
			pstmt.setString(10,smobj.getPayMode());
			pstmt.setString(11,smobj.getPaymentAcctype());
			pstmt.setInt(12,smobj.getPaymentAccno());
			System.out.println("suraj 5");
			pstmt.setString(13,smobj.getRelationtoDirector());
			pstmt.setString(14,smobj.getRelationDesc());
			System.out.println("suraj 6");
			int a=pstmt.executeUpdate();
			System.out.println("ssssssssssssssss  "+a);
			
			if(pstmt.executeUpdate()>0)
			{
				System.out.println("Successfully Updated...");
				return 1;
			}
			else
			{
				System.out.println("Unable to Update...");
				return 0;
			}
			
		}catch(SQLException sqlex){
			sqlex.printStackTrace();
			sessionContext.setRollbackOnly();
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		finally
		{try{conn.close();}catch(SQLException sqlex){sqlex.printStackTrace();}}
		return 0;
	}
	
	
//	Added by Karthi==>21/04/2006
	public int updateNomineeMaster(NomineeObject[] nom_obj_old,NomineeObject[] nom_obj_new,String date)

    {
		Connection conn=null;
		Statement stmt=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		System.out.println("Date Time:"+date);
		String comm_date=date;
		int nom_id=0,update=0;
		int[] a=null;
		try
		{
			conn=getConnection();
			stmt=conn.createStatement();
			if(nom_obj_old!=null)
			{
			System.out.println("nom_obj_old.length==>"+nom_obj_old.length);
			System.out.println("nom_obj_old.getRegNo==>"+nom_obj_old[0].getRegNo());
			System.out.println("nom_obj_old.getAccType==>"+nom_obj_old[0].getAccType());
			System.out.println("nom_obj_old.getAccNo==>"+nom_obj_old[0].getAccNo());
			System.out.println("date="+date);
			update=stmt.executeUpdate("update NomineeMaster set to_date='"+date+"' where reg_no="+nom_obj_old[0].getRegNo()+" and ac_type='"+nom_obj_old[0].getAccType()+"'and ac_no="+nom_obj_old[0].getAccNo());
			System.out.println("Rows updated="+update);
			}
			
			
			
			if(nom_obj_new!=null)
			{
				pstmt=conn.prepareStatement("insert into NomineeMaster values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'"+comm_date+"',null)");
				for(int k=0;k<nom_obj_new.length;k++)
				{
					if(nom_obj_old!=null)
						pstmt.setInt(1,nom_obj_old[0].getRegNo());
					else
					{
						if(nom_obj_new[k].getAccType().startsWith("1002")||nom_obj_new[k].getAccType().startsWith("1007"))
							rs=stmt.executeQuery("select nom_no from AccountMaster where ac_type='"+nom_obj_new[k].getAccType()+"' and ac_no="+nom_obj_new[k].getAccNo()+" ");
						if(nom_obj_new[k].getAccType().startsWith("1014")||nom_obj_new[k].getAccType().startsWith("1015"))
							rs=stmt.executeQuery("select nom_no from ODCCMaster where ac_type='"+nom_obj_new[k].getAccType()+"' and ac_no="+nom_obj_new[k].getAccNo()+" ");
						if(nom_obj_new[k].getAccType().startsWith("1006"))
							rs=stmt.executeQuery("select nom_no from PygmyMaster where ac_type='"+nom_obj_new[k].getAccType()+"' and ac_no="+nom_obj_new[k].getAccNo()+" ");
						if(nom_obj_new[k].getAccType().startsWith("1009"))
							rs=stmt.executeQuery("select nom_no from LockerMaster where ac_type='"+nom_obj_new[k].getAccType()+"' and ac_no="+nom_obj_new[k].getAccNo()+" ");
						if(nom_obj_new[k].getAccType().startsWith("1010")||nom_obj_new[k].getAccType().startsWith("1008"))
							rs=stmt.executeQuery("select nom_no from LoanMaster where ac_type='"+nom_obj_new[k].getAccType()+"' and ac_no="+nom_obj_new[k].getAccNo()+" ");
						if(nom_obj_new[k].getAccType().startsWith("1004")||nom_obj_new[k].getAccType().startsWith("1003")||nom_obj_new[k].getAccType().startsWith("1005"))
							rs=stmt.executeQuery("select nom_no from Accoutmaster where ac_type='"+nom_obj_new[k].getAccType()+"' and ac_no="+nom_obj_new[k].getAccNo()+" ");
						
						if(rs.next())
						{
							pstmt.setInt(1,rs.getInt(1));
						}
						else
						{
							rs=stmt.executeQuery("selext max(reg_no) from NomineeMaster");
							if(rs.next() && rs.getString(1)!=null)
								pstmt.setInt(1,(rs.getInt(1)+1));
							else
								pstmt.setInt(1,1);
						}
				}
				pstmt.setInt(2,nom_obj_new[k].getCustomerId());
				pstmt.setString(3,nom_obj_new[k].getAccType());
				pstmt.setInt(4,nom_obj_new[k].getAccNo());
				pstmt.setInt(5,nom_obj_new[k].getSex());
				pstmt.setString(6,nom_obj_new[k].getNomineeName());
				pstmt.setString(7,nom_obj_new[k].getNomineeDOB());
				pstmt.setString(8,nom_obj_new[k].getNomineeAddress());
				pstmt.setString(9,nom_obj_new[k].getNomineeRelation());
				pstmt.setDouble(10,nom_obj_new[k].getPercentage());
				pstmt.setString(11,nom_obj_new[k].getGuardiantype());
				pstmt.setString(12,nom_obj_new[k].getGuardianName());
				pstmt.setString(13,nom_obj_new[k].getGuardianAddress());
				pstmt.setInt(14,nom_obj_new[k].getGuardSex());
				pstmt.setString(15,nom_obj_new[k].getGuardRelation());
				pstmt.setInt(16,nom_obj_new[k].getCourtOrderNo());
				pstmt.setString(17,nom_obj_new[k].getCourtOrderDate());
				pstmt.addBatch();
				}
				a=pstmt.executeBatch();
			}
			
			if((a!=null &&  a.length>0) || update>0)
			{
				System.out.println("Successfully Updated...");
				return 1;
			}
			else
			{
				System.out.println("Unable to Update...");
				return 0;
			}
			
			
		}catch(SQLException sqlex){
			sqlex.printStackTrace();
			sessionContext.setRollbackOnly();
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		
		finally {try{conn.close();}catch(SQLException sqlex){sqlex.printStackTrace();}}
		
		return 0;
	}
	
//	Added by Karthi==>26/04/2006
	public int updateSignatureInstruction(SignatureInstructionObject[] sig_inst,String acc_type,int acc_no)
	{
		System.out.println("SignatureInstructionObject.Length==>"+sig_inst.length);
		Connection conn=null;
		Statement stmt=null;
		try
		{
			conn=getConnection();
			stmt=conn.createStatement();
			stmt.executeUpdate("insert into SignatureInstructionLog select * from SignatureInstruction where ac_type='"+acc_type+"' and ac_no="+acc_no);
			stmt.executeUpdate("delete from SignatureInstruction where ac_type='"+acc_type+"' and ac_no="+acc_no);
			try
			{
				boolean var=common_remote.storeSignatureDetails(sig_inst,acc_no);//sotre the signature instruction details in 'SignatureInstruction' table
				if(var==true)
				{
					System.out.println("Updated successfully....");
					return 1;
				}
				else
				{
					System.out.println("Unable to Update....");
					return 0;
				}
			}catch(SignatureNotInsertedException sinex){throw new SignatureNotInsertedException();}
			
		}catch(SQLException sqlex){
			sqlex.printStackTrace();
			sessionContext.setRollbackOnly();
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
		}
		finally {try{conn.close();}catch(SQLException sqlex){sqlex.printStackTrace();}}
		
		return 0;
	}
	
	
//	Added By Karthi-->22/05/2006
	public ShareMasterObject[] displayControllNo(String ac_type,int ac_no,String date) throws SQLException,ScrollNotFoundException
	{
		Connection conn=null;
		Statement stmt=null,stmt1=null;
		ResultSet rs=null,rs1=null;
		ShareMasterObject smobj[]=null;
		System.out.println("Acc. Type==>"+ac_type);
		System.out.println("Acc. no==>"+ac_no);
		
		try
		{
			conn=getConnection();
			stmt=conn.createStatement();
			stmt1=conn.createStatement();
			//rs=stmt.executeQuery("select clg_date,ctrl_no,payee_name,trn_amt from Cheque where cr_ac_type='"+ac_type+"' and cr_ac_no="+ac_no+" and clg_date='"+date+"' and post_ind='T'");
			rs=stmt.executeQuery("select clg_date,ctrl_no,payee_name,trn_amt from Cheque where cr_ac_type='"+ac_type+"' and cr_ac_no="+ac_no+" and post_ind='T'");
			rs.last();
			if(rs.getRow()<=0)
				throw new ScrollNotFoundException();
			else
			{
				smobj=new ShareMasterObject[rs.getRow()];
				int i=0;
				rs.beforeFirst();
				aa:while(rs.next())
				{
					rs1=stmt1.executeQuery("select ref_no,trn_narr from ShareTransaction where ac_type='"+ac_type+"' and ac_no="+ac_no+" and ref_no="+rs.getInt("ctrl_no"));
					rs1.last();
					int s=rs1.getRow();
					if(s>0)
						continue aa;
					else
					{
						smobj[i]=new ShareMasterObject();
						smobj[i].setIssueDate(rs.getString("clg_date"));
						smobj[i].setScrollno(rs.getInt("ctrl_no"));
						smobj[i].setName(rs.getString("payee_name"));
						smobj[i].setAmount(rs.getDouble("trn_amt"));
						i++;
					}
				}
			}
			
		}catch(SQLException ex){ex.printStackTrace();}
		finally
		{
			try{conn.close();}catch(SQLException ex){ex.printStackTrace();}
		}
		return smobj;
	}
	
//	Added By Karthi-->26/06/2006
	
	public int checkLoanDetails(String ac_type,int ac_no)
	{
		Connection conn=null;
		Statement stmt=null,stmt1=null;
		ResultSet rs=null,rs1=null;
		
		try
		{
			conn=getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery("Select ac_no,ac_type,appn_date,close_date from LoanMaster where sh_type='"+ac_type+"' and sh_no="+ac_no+" and close_date is null");
			rs.last();
			if(rs.getRow()>0)
			{
				rs.beforeFirst();
				rs.next();
				System.out.println("Loan Type : "+rs.getString("ac_type"));
				System.out.println("Loan No : "+rs.getString("ac_no"));
				System.out.println("Appl. Date : "+rs.getString("appn_date"));
				return 1;
			}
			else
			{
				String ac_status=null;
				rs1=stmt.executeQuery("Select ac_no,ac_type,appn_date,ac_status from ODCCMaster where sh_type='"+ac_type+"' and sh_no="+ac_no);
				rs1.last();
				if(rs1.getRow()>0)
				{
					rs1.beforeFirst();
					rs1.next();
					System.out.println("Loan Type : "+rs1.getString("ac_type"));
					System.out.println("Loan No : "+rs1.getString("ac_no"));
					System.out.println("Appl. Date : "+rs1.getString("appn_date"));
					ac_status=rs1.getString("ac_status");
					System.out.println("Account Status : "+ac_status);
					if(ac_status.equalsIgnoreCase("C"))
						return 0;
					else if(ac_status.equalsIgnoreCase("O"))
						return 1;
					else if(ac_status.equalsIgnoreCase("I"))
						return 2;
				}
			}
		}catch(SQLException ex){ex.printStackTrace();}
		finally
		{
			try{conn.close();}catch(SQLException ex){ex.printStackTrace();}
		}    
		
		return 0;
	}
	
	
//	Added By Karthi-->01/07/2006
	
	public ShareMasterObject[] displayTransactionNo(String date) throws ScrollNotFoundException
	{
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		ShareMasterObject smobj[]=null;
		System.out.println("Date == "+date);
		try
		{
			conn=getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery("select trn_no,ac_type,ac_no from ShareTransaction where trn_date='"+date+"' and ve_user is null and de_user is not null");
			rs.last();
			if(rs.getRow()<=0)
				throw new ScrollNotFoundException();
			else
			{
				smobj=new ShareMasterObject[rs.getRow()];
				rs.beforeFirst();
				int i=0;
				while(rs.next())
				{
					smobj[i]=new ShareMasterObject();
					smobj[i].setTranNumber(rs.getInt("trn_no"));
					smobj[i].setShareAccType(rs.getString("ac_type"));
					smobj[i].setShareNumber(rs.getInt("ac_no"));
					i++;
				}
			}
		}catch(SQLException ex){ex.printStackTrace();}
		finally
		{
			try{conn.close();}catch(SQLException ex){ex.printStackTrace();}
		}
		
		return smobj;
	}
	
//	Added By Karthi-->10/07/2006
	
	public ShareMasterObject[] displayUnVerifiedShrNos(String ac_type,String date)throws ScrollNotFoundException
	{
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		ShareMasterObject smobj[]=null;
		System.out.println("Acc.Type == "+ac_type);
		System.out.println("Date == "+date);
		try
		{
			conn=getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery("select sm.ac_type,sm.ac_no,sm.mem_issuedate,concat_ws('',fname,mname,lname)as name from ShareMaster sm,CustomerMaster cm where sm.ac_type='"+ac_type+"' and sm.ve_user is null and sm.de_user is not null and sm.cid=cm.cid");
			
			rs.last();
			if(rs.getRow()<=0)
				throw new ScrollNotFoundException();
			else
			{
				smobj=new ShareMasterObject[rs.getRow()];
				rs.beforeFirst();
				int i=0;
				while(rs.next())
				{
					smobj[i]=new ShareMasterObject();
					smobj[i].setShareAccType(rs.getString("ac_type"));
					smobj[i].setShareNumber(rs.getInt("ac_no"));
					smobj[i].setIssueDate(rs.getString("mem_issuedate"));
					smobj[i].setName(rs.getString("name"));
					i++;
				}
			}
		}catch(SQLException ex){ex.printStackTrace();}
		finally
		{
			try{conn.close();}catch(SQLException ex){ex.printStackTrace();}
		}
		return smobj;
	}
	
//	Added By Karthi-->10/07/2006
	public int insertdeleteModules(ModuleObject[] mobj,int type)
	{
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Statement stmt=null,stmt1=null;
		System.out.println("mobj.length == > "+mobj.length);
		int r=0;
		int a[]=null;
		try
		{
			conn=getConnection();
			stmt=conn.createStatement();
			stmt1=conn.createStatement();
			if(type==0)
				pstmt=conn.prepareStatement("insert into Modules(modulecode,moduledesc,moduleabbr,lst_acc_no,max_renewal_count,std_inst,lst_voucher_scroll_no,top_margin,last_trf_scroll_no,lst_rct_no,lst_voucher_no,inspection_period,ln_modulecode) values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			for(int i=0;i<mobj.length;i++)
			{	
				if(type==0)//For Insertion
				{
					rs=stmt1.executeQuery("select * from Modules where modulecode="+mobj[i].getModuleCode());
					rs.last();
					if(rs.getRow()>0)
					{stmt.executeUpdate("delete from Modules where modulecode="+mobj[i].getModuleCode());rs.close();}
					
					pstmt.setString(1,mobj[i].getModuleCode());
					pstmt.setString(2,mobj[i].getModuleDesc());
					pstmt.setString(3,mobj[i].getModuleAbbrv());
					pstmt.setInt(4,mobj[i].getLastAccNo());
					pstmt.setInt(5,mobj[i].getMaxRenewalCount());
					pstmt.setInt(6,mobj[i].getStdInst());
					pstmt.setInt(7,mobj[i].getLastVoucherScrollno());
					pstmt.setInt(8,mobj[i].getTopmargin());
					pstmt.setInt(9,mobj[i].getLastTRFScrollno());
					pstmt.setInt(10,mobj[i].getRCTNo());
					pstmt.setInt(11,mobj[i].getLastVoucherNo());
					pstmt.setInt(12,mobj[i].getInspectionPeriod());
					pstmt.setInt(13,Integer.parseInt(mobj[i].getLoanModuleCode()));
					pstmt.addBatch();
				}
				else if(type==1)//For Deletion
				{
					if(stmt.executeUpdate("delete from Modules where modulecode="+mobj[i].getModuleCode())==1)
						r++;
					else
						r=0;
				}
			}
			if(type==0)
				a=pstmt.executeBatch();
			
			if((type==0&&a.length>0)||(type==1&&r>0))
				return 1;
			else 
				return 0;
		}catch(SQLException sqlex){
			sqlex.printStackTrace();
			sessionContext.setRollbackOnly();
			return 0;
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
			return 0;
		}
		finally {try{conn.close();}catch(SQLException sqlex){sqlex.printStackTrace();}}
	}
	
	public double getClosingBalance(String ac_type,int ac_no,String date) 
	{
		double cl_bal=0.0;
		Connection conn=null;
		ResultSet rs=null;
		try
		{
			conn=getConnection();
			Statement stmt=conn.createStatement();
			rs = stmt.executeQuery("select share_bal from ShareTransaction where ac_type='"+ac_type+"' and ac_no="+ac_no+" and (trn_type='A' or trn_type='W') and concat(right(trn_date,4),'-',mid(trn_date,locate('/',trn_date)+1, (locate('/',trn_date,4)-locate('/',trn_date)-1)),'-',left(trn_date,locate('/',trn_date)-1))<='"+Validations.convertYMD(date)+"' and ve_tml is not null and ve_user is not null and ve_date is not null order by trn_seq  desc limit 1");
			if(rs.next() && rs.getString("share_bal")!=null)
			{
				cl_bal=rs.getDouble("share_bal");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try{                
				conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return cl_bal;
	}
	
//	Added By Karthi-->29/09/2006
	public int storeToRemainder(DividendObject[] dvobj)
	{
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Statement stmt=null,stmt1=null;
		System.out.println("dvobj.length == > "+dvobj.length);
		int a[];
		
		try{
			conn=getConnection();
			stmt=conn.createStatement();
			stmt1=conn.createStatement();
			pstmt=conn.prepareStatement("insert into DividendReminderNotice(ac_type,ac_no,div_dt,templ_no,div_amt,notice_no,de_user,de_tml,de_date) values(?,?,?,?,?,?,?,?,?)");
			for(int i=0;i<dvobj.length;i++)
			{
				pstmt.setString(1,dvobj[i].getSHType());
				pstmt.setInt(2,dvobj[i].getSHNumber());
				pstmt.setString(3,dvobj[i].getDivDate());
				pstmt.setInt(4,dvobj[i].getCvNumber());
				pstmt.setDouble(5,dvobj[i].getDivAmount());
				pstmt.setInt(6,dvobj[i].getVoucherNo());
				pstmt.setString(7,dvobj[i].getVerified());
				pstmt.setString(8,dvobj[i].getVeTml());
				pstmt.setString(9,dvobj[i].getVeDate());
				pstmt.addBatch();
			}
			a=a=pstmt.executeBatch();
			if(a!=null && a.length>0)
				return 1;
			else
				return 0;
		}catch(SQLException sqlex){
			sqlex.printStackTrace();
			sessionContext.setRollbackOnly();
			return 0;
		}catch(Exception e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
			return 0;
		}
		finally {try{conn.close();}catch(SQLException sqlex){sqlex.printStackTrace();}}
		
	}
	
	
	public double getPrmAmt(int category){
		double prmamt=0.0;
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		System.out.println("The category is"+category);
		
		try{
			con=getConnection();
			stmt=con.createStatement();
			rs=stmt.executeQuery("select sum(prm_amt) as amount from ShareParam where mem_cat="+category+" and prm_ty!='Z' and prm_freq!='AD' and prm_freq!='AL'");
			if(rs.next()){
				prmamt=rs.getDouble("amount");
				System.out.println("the amount is"+rs.getDouble("amount"));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				con.close();
			}
			catch(Exception e1){
				e1.printStackTrace();
			}
		}
		return prmamt;
		
	}
	
	public int checkShareNumber(int type){
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		int maxshnum=0;
		try{
			con=getConnection();
			stmt=con.createStatement();
			 if(type==0){
			rs=stmt.executeQuery("select max(ac_no) as maxnum from ShareMaster");
			 }
			 if(type==1){
				 rs=stmt.executeQuery("select max(cid) as maxnum from CustomerMaster");
			 }
			if(rs.next()){
				maxshnum=rs.getInt("maxnum");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		finally{
			try{
				con.close();
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return maxshnum;
	}
	
	public ShareMasterObject getDetails(int ac_no){
		ShareMasterObject shobj=new ShareMasterObject();
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		try{
			con=getConnection();
			stmt=con.createStatement();
			rs=stmt.executeQuery("select * from CustomerMaster where cid=(select cid from ShareMaster where ac_no="+ac_no+")");
			if(rs.next()){
                     shobj.setName(rs.getString("fname"));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				con.close();
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return shobj;
	}
	
  public String getAcDetails(int ac_no,String ac_type){
	  String name=null;
	    Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		System.out.println("The ac type"+ac_type);
		System.out.println("The ac no is "+ac_no);
		try{
			con=getConnection();
			stmt=con.createStatement();
			rs=stmt.executeQuery("select * from CustomerMaster where cid=(select cid from AccountMaster where ac_type='"+ac_type+"' and ac_no="+ac_no+")");
			if(rs.next()){
                   name=rs.getString("fname");
                   System.out.println("THE NAME IN BEAN IS"+name);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				con.close();
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
	  return name;
  }
  
  public ShareMasterObject[] viewlog(String ac_type,int ac_no){
	  ShareMasterObject[] shobj=null;
	  Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		System.out.println("The ac_no is bean"+ac_no);
		System.out.println("the ac type is bean"+ac_type);
		try{
			con=getConnection();
			stmt=con.createStatement();
			rs=stmt.executeQuery("Select * from ShareMasterLog where ac_no="+ac_no+" and ac_type='"+ac_type+"'");
			rs.last();
			int size=0;
			size=rs.getRow();
			shobj=new ShareMasterObject[size];
			rs.beforeFirst();
			int i=0;
			while(rs.next()){
			     shobj[i]=new ShareMasterObject(); 
			     shobj[i].setShareNumber(Integer.parseInt(rs.getString("ac_no")));
			     shobj[i].setMailingAddress(Integer.parseInt(rs.getString("addr_type")));
			     shobj[i].setDivUptoDate(rs.getString("div_uptodt"));
			     shobj[i].setIssueDate(rs.getString("mem_issuedate"));
			     shobj[i].setCloseDate(rs.getString("mem_cl_date"));
			     System.out.println("The close date is"+rs.getString("mem_cl_date"));
			     shobj[i].setLoanAvailed(rs.getString("ln_availed"));
			     shobj[i].setTransferDate(rs.getString("trf_date"));
			     shobj[i].setPayMode(rs.getString("pay_mode"));
			     shobj[i].setPaymentAcctype(rs.getString("pay_ac_type"));
			     shobj[i].setPaymentAccno(Integer.parseInt(rs.getString("pay_ac_no")));
			     System.out.println("The ac_no in bean is"+rs.getString("ac_no"));
			     i++;
			     
			}
			
			return shobj;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		finally{
			try{
				con.close();
			}
			catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		return shobj;
  }
	
  
  public ShareCategoryObject[] getShareTypes(){
	  ShareCategoryObject[] sh_cat=null;
	  Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		
		try{
			con=getConnection();
			stmt=con.createStatement();
			rs=stmt.executeQuery("select * from ShareType");
			rs.last();
			int size=0;
			size=rs.getRow();
			sh_cat=new ShareCategoryObject[size];
			rs.beforeFirst();
			int i=0;
			while(rs.next()){
				sh_cat[i]=new ShareCategoryObject(); 
			    sh_cat[i].setCatName(rs.getString("catname")); 
			    sh_cat[i].setShareType(rs.getString("ac_type"));
			    sh_cat[i].setShCat(rs.getInt("mem_cat"));
			    sh_cat[i].setMinShare(rs.getInt("minshare"));
			    sh_cat[i].setMaxShare(rs.getInt("maxshare"));
			    sh_cat[i].setShareVal(rs.getDouble("shareval"));
			    sh_cat[i].setVote(rs.getString("vote_pow"));
			    
			     i++;
			     
			}
			
			return sh_cat; 
			}catch(Exception e){
			e.printStackTrace();
		}
		
		finally{
			try{
				con.close();
			}
			catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	  
	  return sh_cat;
  }
  
  public ModuleObject changeofmodules(String mod_code){
	  ModuleObject modobj=new ModuleObject();
	  Connection con=null;
	  ResultSet rs=null;
	  Statement st=null;
	  
	  System.out.println("The actype in bean is"+mod_code);
	  try{
		  con=getConnection();
		  st=con.createStatement();
		  rs=st.executeQuery("select * from Modules where modulecode='"+mod_code+"'");
		  rs.last();
		  int size=0;
	      size=rs.getRow();
	      //modobj=new ModuleObject[size];
	      System.out.println("The size is "+size);
	      rs.beforeFirst();
	      int i=0;
	      System.out.println("The actype in bean 111 is"+mod_code);
		  while(rs.next()){
			  	System.out.println(rs.getString("modulecode"));
		
			  	modobj.setModuleCode(rs.getString("modulecode"));
			    modobj.setModuleAbbrv(rs.getString("moduleabbr"));
			    modobj.setModuleDesc(rs.getString("moduledesc"));
			    modobj.setLastAccNo(rs.getInt("lst_acc_no"));
			    modobj.setMaxRenewalCount(rs.getInt("max_renewal_count"));
			    modobj.setMinBal(rs.getDouble("min_bal"));
			    modobj.setStdInst(rs.getInt("std_inst"));
			    modobj.setLastVoucherScrollno(rs.getInt("lst_voucher_scroll_no"));
			    modobj.setTopmargin(rs.getInt("top_margin"));
			    modobj.setLastTRFScrollno(rs.getInt("last_trf_scroll_no"));
			    modobj.setLastVoucherNo(rs.getInt("lst_voucher_no"));
			    modobj.setInspectionPeriod(rs.getInt("inspection_period"));
			    modobj.setLoanModuleCode(rs.getString("ln_modulecode"));
			     
			}
		  return modobj;
	  }
	  catch (Exception e) {
		e.printStackTrace();
	}
	  
	  finally{
		  try{
			  con.close();
		  }
		  catch(Exception e){
			  e.printStackTrace();
		  }
	  }
	  return modobj;
  }
  
  public ShareParamObject[] storeShareParams()throws RemoteException{
	  ShareParamObject[] sh_param=null;
	  Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		
		try{
			con=getConnection();
			stmt=con.createStatement();
			rs=stmt.executeQuery("Select * from ShareParam where ac_type='1001001' order by ac_type");
			rs.last();
			int size=0;
			size=rs.getRow();
			sh_param=new ShareParamObject[size];
			rs.beforeFirst();
			int i=0;
			while(rs.next()){
				sh_param[i]=new ShareParamObject();
				sh_param[i].setShareType(rs.getString("ac_type"));
				sh_param[i].setSh_cat(rs.getInt("mem_cat"));
				sh_param[i].setPrm_code(rs.getInt("prm_code"));
				sh_param[i].setPrm_desc(rs.getString("prm_desc"));
				sh_param[i].setPrm_amt(rs.getDouble("prm_amt"));
				sh_param[i].setPrm_freq(rs.getString("prm_freq"));
				sh_param[i].setPrm_ty(rs.getString("prm_ty"));
				sh_param[i].setPrm_gl_key(rs.getString("prm_gl_code"));
				sh_param[i].setPrm_gl_type(rs.getString("prm_gl_type"));
			    
			     i++;
			     
			}
			
			return sh_param; 
			}catch(Exception e){
			e.printStackTrace();
		}
		
		finally{
			try{
				con.close();
			}
			catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	    return sh_param;
	  
  }
  
  public int SetNomineeDetails(NomineeObject[] nom)throws RemoteException{
	    int i=0;
	    Connection con=null;
		Statement stmt=null;
		ResultSet rs,rs1=null;
		System.out.println("The length of nominee is "+nom.length);
		try{
			con=getConnection();
			stmt=con.createStatement();
			if(nom!=null)
			{
				Context ctx=getInitialContext();
				Object obj=ctx.lookup("COMMONWEB");
				commonServer.CommonHome home=(commonServer.CommonHome)obj;
				CommonRemote cremote=home.create();
				System.out.println("Executing insert query...............");
				int id=cremote.storeNominee(nom,nom[0].getAccType(),nom[0].getAccNo(),nom[0].getFromDate());
				System.out.println("iDDDDDDDDDDDDD is"+id);
				int done=stmt.executeUpdate("update ShareMaster set nom_no="+id+" where ac_no="+nom[0].getAccNo()+" and ac_type='"+nom[0].getAccType()+"'");
				System.out.println("The done value is "+done);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		finally{
			try{
			con.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	  return i;
  }
  
  
  public String getNomineeAddress(int cid)throws RemoteException{
	    String addr=null;
	    Connection con=null;
		Statement stmt=null;
		ResultSet rs,rs1=null;
		System.out.println("The length of nominee is "+cid);
		try{
			con=getConnection();
			stmt=con.createStatement();
			rs=stmt.executeQuery("select address from CustomerAddr where cid="+cid+" limit 1");
			while(rs.next()){
			  addr=rs.getString("address");
			}
			return addr;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		finally{
			try{
			con.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	  return addr;
}


  
  public int getNomineePercent(int accno,String ac_type)throws RemoteException{
	    int per=0;
	    Connection con=null;
		Statement stmt=null;
		ResultSet rs,rs1=null;
		System.out.println("The length of nominee is "+accno);
		try{
			con=getConnection();
			stmt=con.createStatement();
			System.out.println("Before query");
			rs=stmt.executeQuery("select sum(percentage) as per from NomineeMaster where ac_no="+accno+" and ac_type='"+ac_type+"'");
			System.out.println("afetr query");
			while(rs.next()){
			  per=rs.getInt("per");
			}
			return per;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		finally{
			try{
			con.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	  return per;
}
  public String getTemplatedata(int template_no)throws RemoteException{
	  String data=null;
	  Connection con=null;
	  Statement stmt=null;
	  ResultSet rs,rs1=null;
	  try{
			con=getConnection();
			stmt=con.createStatement();
			System.out.println("Before query");
			rs=stmt.executeQuery("select text from Template where temp_no="+template_no+" and ac_type='1001001'");
			while(rs.next()){
				data=rs.getString("text");
			}
			return data;
	  }
	  catch(Exception e){
		  
		  e.printStackTrace();
	  }
	  finally{
			try{
			con.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	  return data;
  }
  
  public boolean storeSignatureDetails(SignatureInstructionObject a[],int acno) throws SignatureNotInsertedException 
	{
		System.out.println("Storing SignatureDetails...");
		System.out.println("The length of signature is"+a.length);
		Connection conn=null;
		try{
			conn=getConnection();	
			System.out.println("Teh ac_no in sign is"+acno);
			PreparedStatement ps=conn.prepareStatement("insert into SignatureInstruction values(?,?,?,?,?,?,?,?)");
			//for(int i=0;i<a.length;i++)
		//	{
				System.out.println(a[0].getSAccType());
				ps.setString(1,a[0].getSAccType());
				ps.setInt(2,acno);
				System.out.println(a[1].getCid());
				ps.setInt(3,a[1].getCid());
				System.out.println("The name in sign is "+a[2].getName());
				ps.setString(4,a[2].getName());
				ps.setString(5,a[3].getDesignation());
				ps.setInt(6,a[4].getMinLimit());
				ps.setInt(7,a[5].getMaxLimit());
				System.out.println(a[6].getTypeOfOperation());
				ps.setString(8,a[6].getTypeOfOperation());
				if(ps.executeUpdate()!=1)
				{
					System.out.println("inside not inserted signature");
					throw new SignatureNotInsertedException();
				}
		//	}
		}catch(SQLException exception){
			exception.printStackTrace();
			sessionContext.setRollbackOnly();
		}catch(Exception e){
			e.printStackTrace();
		sessionContext.setRollbackOnly();
		}
		finally{
			try{
				if(conn!=null)
					conn.close();
			}catch(Exception exception){exception.printStackTrace();}
		}
		
		return true;
	}
  
}
