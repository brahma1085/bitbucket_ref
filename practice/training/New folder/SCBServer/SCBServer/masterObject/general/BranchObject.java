package masterObject.general;

public class BranchObject implements java.io.Serializable {
	static final long serialVersionUID = 1L;// ship.....20/01/2007

	int branchCode, branchACNo, glCode;
	String branchName, shortName, branchACType, glType, branchAddress,
			branchType, de_tml, de_user, de_date, br_abbrv;

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

	public String getDe_date() {
		return de_date;
	}

	public void setDe_date(String de_date) {
		this.de_date = de_date;
	}

	public int getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(int br_code) {
		this.branchCode = br_code;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String br_name) {
		this.branchName = br_name;
	}

	public int getBranchACNo() {
		return branchACNo;
	}

	public void setBranchACNo(int branchACNo) {
		this.branchACNo = branchACNo;
	}

	public int getGlCode() {
		return glCode;
	}

	public void setGlCode(int glCode) {
		this.glCode = glCode;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getBranchACType() {
		return branchACType;
	}

	public void setBranchACType(String branchACType) {
		this.branchACType = branchACType;
	}

	public String getGlType() {
		return glType;
	}

	public void setGlType(String glType) {
		this.glType = glType;
	}

	public String getBranchAddress() {
		return branchAddress;
	}

	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}

	public String getBranchType() {
		return branchType;
	}

	public void setBranchType(String branchType) {
		this.branchType = branchType;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getBr_abbrv() {
		return br_abbrv;
	}

	public void setBr_abbrv(String br_abbrv) {
		this.br_abbrv = br_abbrv;
	}
}