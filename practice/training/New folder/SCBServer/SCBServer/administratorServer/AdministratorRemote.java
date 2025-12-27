package administratorServer;

import java.net.ConnectException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import com.mysql.jdbc.Connection;

import exceptions.RecordNotInsertedException;
import exceptions.RecordsNotInsertedException;
import exceptions.LoginException;

import masterObject.administrator.AdministratorObject;
import masterObject.administrator.MenuObject;
import masterObject.administrator.TerminalObject;
import masterObject.administrator.TreeObject;
import masterObject.general.BranchObject;
import masterObject.general.ModuleAdminObject;

public interface AdministratorRemote extends javax.ejb.EJBObject
{
	//LOGIN
	public boolean verifyUser(String txt_user_id,String pwd,String txt_terminal_name) throws RemoteException;
	public int changePassword(String string_name,String string_old_password,String string_new_password) throws RemoteException;
	public Vector checkLogin(String user_id, String password, String login_date, String tml_id, String ip_address) throws LoginException,RemoteException;
	public boolean checkUserLogin(String tml_id,String user_id) throws RemoteException;
	//USER
	public int createUser(AdministratorObject obj_new_user) throws RemoteException;
	public AdministratorObject[] getTmlCodes() throws RemoteException;
	public java.sql.Connection getConn() throws RemoteException,ConnectException;
	public AdministratorObject[] getReqUsers(String string_tml_code) throws RemoteException;
	public AdministratorObject[] getAllUsers() throws RemoteException;
	public AdministratorObject[] getUserDetails() throws RemoteException;
	public AdministratorObject[] getTmlDetails() throws RemoteException;
	public AdministratorObject[] getUserAccess(int int_user_no) throws RemoteException;
	public int deletePermission(int user_no) throws RemoteException;
	public int allowAccess(AdministratorObject[] array_obj_permit) throws RemoteException;
	public AdministratorObject[] getViewUser(String userid,int type,String from,String to) throws RemoteException,SQLException;
	//public AdministratorObject[] getViewUser1(String from,String to) throws RemoteException,SQLException;
	
	
	//TERMINAL
	public int createTerminal(String string_tml_code,String string_tml_name,String string_tml_type) throws RemoteException;
	public AdministratorObject[] viewTerminals() throws RemoteException;
	//code added by amzad
	public Vector usrAccessRights(String string_name) throws RemoteException;
	//CREATE TERMINAL TYPE/MENU OBJECT
	public MenuObject[] getMenus() throws RemoteException;
	public TreeObject[] getTreeDetails() throws RemoteException, SQLException;
	
	public void setUserId(String uid) throws RemoteException;
	
	public Hashtable getAccessValues(String tml)throws RemoteException;
	
	public int insertAccessValues(Hashtable hash,String tml) throws RemoteException;
	
	public TerminalObject[] getTerminalDetails() throws RemoteException;
	
	public void setTerminalDetail(TerminalObject tml_object) throws  RemoteException;
	
	public String[] getUsers() throws RemoteException;
	
	public String[] getUserTerminals(String user_id) throws RemoteException;
	
	public void setUserTerminals(String user_id,String[] tml_code,String user,String tml,String date) throws RecordsNotInsertedException,RemoteException;
	
	public int creatingUsers(AdministratorObject adminObj,int i)throws RecordsNotInsertedException,RemoteException;
	
	public AdministratorObject retrieveCustDetail(int cust_id)throws RemoteException,RecordNotInsertedException;
	public AdministratorObject retrieveCustDetailNew(String cust_id)throws RemoteException,RecordNotInsertedException;
	
	public void updateCreate(AdministratorObject adObje,AdministratorObject admObj) throws RemoteException,RecordNotInsertedException;
	
	public int deleteCreateUser(String cid)throws RemoteException;
	
	public String[] getValidUser(String userid) throws RemoteException;
	
	public int createUserPassword(String string_name,String string_old_password,String string_new_password) throws RemoteException;
	
	public int deleteTerminalIpAddr(String str[])throws RecordNotInsertedException,RemoteException;
	
	public boolean deleteTerminal(String tml) throws RemoteException;//Amzad
	
	public void insertIntoTerminalIPAddr(AdministratorObject adminObjec,String[] str )throws RecordNotInsertedException,RemoteException;
	
	public ArrayList getDestinctTerminalAddr()throws RemoteException;
	
	public ArrayList getTerminalIPAddr(String str)throws RemoteException;
	//code added by Amzad on 16.02.2009
	public  AdministratorObject [] showholidays()throws RemoteException;
	//code added by Amzad on 17.02.2009
	public void insertHoliday(String day[],String date[],String reason[],String br_name[],String tml[],String uid[],String sysdate[]) throws RemoteException;
	//code added by Amzad on 17.02.2009
	public String deleteHoliday(String day[]) throws RemoteException;
	public ModuleAdminObject getGLData(String modulecode )throws RemoteException;
	public int UpdateGLModuleAdmin(ModuleAdminObject admin)throws RemoteException;
	//code added by amzad
	public void doLogout(String tml_id,String user_id) throws RemoteException;
	//added by Mohsin
	public  String [] getAccessRight(String user,String tml)throws RemoteException;
	//added by Mohsin
	public  String setAccessRight(String user,String tml,String[] arr,int ind)throws RemoteException;
	//added by Mohsin
	public  Object[][] getFormwiseAccessRights(String rolecode,String formcode)throws RemoteException;
	//added by Mohsin
	public AdministratorObject[] getRoleDefinition() throws RemoteException;
	//added by Mohsin
	public AdministratorObject[] getUserRoleAssignment(String user) throws RemoteException;
	//added by Mohsin
	public String setUserRoleAssignment(String user,AdministratorObject[] admObj) throws RemoteException;
	//Added by Mohsin
	public String setFormwiseRoleAccess(AdministratorObject[] admObj,String rolecode,String formcode) throws RemoteException;
	//Added by Mohsin
	public  Map getUserLoginAccessRights(String user,String formcode) throws RemoteException;
	//Added by Mohsin
	public String insertFormsDetails(AdministratorObject[] admObj,String formcode) throws RemoteException;
	
	//Added by Mohsin
	public AdministratorObject[] getFormsDetail(String formcode) throws RemoteException;
	
	public BranchObject[] getBranchMaster()throws RemoteException;
}