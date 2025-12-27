package masterObject.administrator;
import java.io.*;
import java.util.Vector;

public class AdministratorObject implements Serializable
{
	int int_user_no,pass_expiry_period,cust_id;
	String string_full_name,string_short_name,string_pwd,string_user_type,string_tml_no,string_uid,string_tml_desc,string_tml_type,de_date,de_user,de_tml;          
	String re_type_pass,pass_expiry_date,acc_operation_fromDate,acc_oper_toDate, tmpipm,terminal_no;
	boolean disable;
	Vector user_roles;
	String date,day,reason,br_name,de_date1,de_tml1,de_user1;
	//changed by Mohsin on 24/11/2009 for RoleAccess
	String rolecode,roledesc,formcode,formname,access,pageId;
	
	public void setdate(String date)
	{
		this.date=date;
	}
	
	public String getdate()
	{
		return date;
	}
	
	
	public void setday(String day)
	{
		this.day=day;
	}
	
	public String getday()
	{
		return day;
	}
	
	public void setreason(String reason)
	{
		this.reason=reason;
	}
	
	public String getreason()
	{
		return reason;
	}
	
	public void setbr_name(String br_name)
	{
		this.br_name=br_name;
	}
	
	public String getbr_name()
	{
		return br_name;
	}
	
	public void setdate1(String de_date1)
	{
		this.de_date1=de_date1;
	}
	
	public String getdate1()
	{
		return de_date1;
	}
	
	public void setde_tml(String de_tml1)
	{
		this.de_tml1=de_tml1;
	}
	
	public String getde_tml()
	{
		return de_tml1;
	}
	
	public void setdeuser(String de_user1)
	{
		this.de_user1=de_user1;
	}
	
	public String getdeuser()
	{
		return de_user1;
	}
	
	
	public void setpass_expiry_period(int pass_expiry_period)
	{
		this.pass_expiry_period=pass_expiry_period;
	}
	public int getpass_expiry_period()
	{
		return pass_expiry_period;
	}
	public String getAcc_oper_toDate() {
		return acc_oper_toDate;
	}
	public String getDe_date() {
		return de_date;
	}
	public void setDe_date(String de_date) {
		this.de_date = de_date;
	}
	public String getDe_tml() {
		return de_tml;
	}
	public void setDe_tml(String de_tml) {
		this.de_tml = de_tml;
	}
	public String getDe_user() {
		return de_user;
	}
	public void setDe_user(String de_user) {
		this.de_user = de_user;
	}
	public void setAcc_oper_toDate(String acc_oper_toDate) {
		this.acc_oper_toDate = acc_oper_toDate;
	}
	public String getAcc_operation_fromDate() {
		return acc_operation_fromDate;
	}
	public void setAcc_operation_fromDate(String acc_operation_fromDate) {
		this.acc_operation_fromDate = acc_operation_fromDate;
	}
	public int getCust_id() {
		return cust_id;
	}
	public void setCust_id(int cust_id) {
		this.cust_id = cust_id;
	}
	public boolean getDisable() {
		return disable;
	}
	public void setDisable(boolean disable) {
		this.disable = disable;
	}
	
	public String getPass_expiry_date() {
		return pass_expiry_date;
	}
	public void setPass_expiry_date(String pass_expiry_date) {
		this.pass_expiry_date = pass_expiry_date;
	}
	public int getPass_expiry_period() {
		return pass_expiry_period;
	}
	public void setPass_expiry_period(int pass_expiry_period) {
		this.pass_expiry_period = pass_expiry_period;
	}
	public String getRe_type_pass() {
		return re_type_pass;
	}
	public void setRe_type_pass(String re_type_pass) {
		this.re_type_pass = re_type_pass;
	}
	public void setUserNo(int int_user_no)
	{
		this.int_user_no=int_user_no;
	}
	public int getUserNo()
	{
		return int_user_no;
	}
	

	public void setShortName(String string_short_name)
	{
		this.string_short_name=string_short_name;
	}
	public String getShortName()
	{
		return string_short_name;
	}
	

	public void setFullName(String string_full_name)
	{
		this.string_full_name=string_full_name;
	}
	public String getFullName()
	{
		return string_full_name;
	}
	
	
	public void setPassword(String string_pwd)
	{
		this.string_pwd=string_pwd;
	}
	public String getPassword()
	{
		return string_pwd;
	}
	
	
	public void setUserType(String string_user_type)
	{
		this.string_user_type=string_user_type;
	}
	public String getUserType()
	{
		return string_user_type;
	}
	
	
	public void setTmlNo(String string_tml_no)
	{
		this.string_tml_no=string_tml_no;
	}
	public String getTmlNo()
	{
		return string_tml_no;
	}
	
	
	public void setUid(String string_uid)
	{
		this.string_uid=string_uid;
	}
	public String getUid()
	{
		return string_uid;
	}
	
	
	public void setTmlDesc(String string_tml_desc)
	{
		this.string_tml_desc=string_tml_desc;
	}
	public String getTmlDesc()
	{
		return string_tml_desc;
	}
	
	
	public void setTmlType(String string_tml_type)
	{
		this.string_tml_type=string_tml_type;
	}
	public String getTmlType()
	{
		return string_tml_type;
	}
	public String getThump_ipm()
	{
	    return tmpipm;
	}
	public void setThump_ipm(String tmpipm)
	{
	    this.tmpipm=tmpipm;
	}
	public String getTerminal_no() {
		return terminal_no;
	}
	public void setTerminal_no(String terminal_no) {
		this.terminal_no = terminal_no;
	}
	
	public void setUserRoles(Vector user_roles) {
		this.user_roles = user_roles;
	}
	
	public Vector getUserRoles() {
		return user_roles;
	}

	public String getRolecode() {
		return rolecode;
	}

	public void setRolecode(String rolecode) {
		this.rolecode = rolecode;
	}

	public String getRoledesc() {
		return roledesc;
	}

	public void setRoledesc(String roledesc) {
		this.roledesc = roledesc;
	}

	public String getFormcode() {
		return formcode;
	}

	public void setFormcode(String formcode) {
		this.formcode = formcode;
	}

	public String getFormname() {
		return formname;
	}

	public void setFormname(String formname) {
		this.formname = formname;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
}