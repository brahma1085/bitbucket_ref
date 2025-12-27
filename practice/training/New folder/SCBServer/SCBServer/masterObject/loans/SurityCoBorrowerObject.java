package masterObject.loans;

import java.io.Serializable;

public class SurityCoBorrowerObject implements Serializable {
	String ac_type,type,moduleabbr,ac_no,cid;

	public String getAc_type() {
		return ac_type;
	}

	public void setAc_type(String ac_type) {
		this.ac_type = ac_type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getModuleabbr() {
		return moduleabbr;
	}

	public void setModuleabbr(String moduleabbr) {
		this.moduleabbr = moduleabbr;
	}

	public String getAc_no() {
		return ac_no;
	}

	public void setAc_no(String ac_no) {
		this.ac_no = ac_no;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

}
