package masterObject.administrator;



import java.io.Serializable;

public class UserActivityMasterObject implements Serializable {
	String tml_no,user_id,activity,activity_date,activity_time,page_visit,ip_address,ac_type,branch;
	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	int ac_no,cid;
	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getActivity_date() {
		return activity_date;
	}

	public void setActivity_date(String activity_date) {
		this.activity_date = activity_date;
	}

	public String getActivity_time() {
		return activity_time;
	}

	public void setActivity_time(String activity_time) {
		this.activity_time = activity_time;
	}

	public String getTml_no() {
		return tml_no;
	}

	public void setTml_no(String tml_no) {
		this.tml_no = tml_no;
	}

	
	public int getAc_no() {
		return ac_no;
	}

	public void setAc_no(int ac_no) {
		this.ac_no = ac_no;
	}

	public String getPage_visit() {
		return page_visit;
	}

	public void setPage_visit(String page_visit) {
		this.page_visit = page_visit;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getAc_type() {
		return ac_type;
	}

	public void setAc_type(String ac_type) {
		this.ac_type = ac_type;
	}

	public String getIp_address() {
		return ip_address;
	}

	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}
}
