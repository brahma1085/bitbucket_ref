 package masterObject.clearing;

  public class Reason implements java.io.Serializable
  {		
	  	int controlNo,reasonCd,linkReasonCd;
	  	String deTml,deUser,deTime;
	  	String veTml,veUser,veTime,reasonDesc;
	  	double bounceFine;
		public int getControlNo() {
			return controlNo;
		}
		public void setControlNo(int controlNo) {
			this.controlNo = controlNo;
		}
		public int getReasonCd() {
			return reasonCd;
		}
		public void setReasonCd(int reasonCd) {
			this.reasonCd = reasonCd;
		}
		public int getLinkReasonCd() {
			return linkReasonCd;
		}
		public void setLinkReasonCd(int linkReasonCd) {
			this.linkReasonCd = linkReasonCd;
		}
		public String getDeTml() {
			return deTml;
		}
		public void setDeTml(String deTml) {
			this.deTml = deTml;
		}
		public String getDeUser() {
			return deUser;
		}
		public void setDeUser(String deUser) {
			this.deUser = deUser;
		}
		public String getDeTime() {
			return deTime;
		}
		public void setDeTime(String deTime) {
			this.deTime = deTime;
		}
		public String getVeTml() {
			return veTml;
		}
		public void setVeTml(String veTml) {
			this.veTml = veTml;
		}
		public String getVeUser() {
			return veUser;
		}
		public void setVeUser(String veUser) {
			this.veUser = veUser;
		}
		public String getVeTime() {
			return veTime;
		}
		public void setVeTime(String veTime) {
			this.veTime = veTime;
		}
		public String getReasonDesc() {
			return reasonDesc;
		}
		public void setReasonDesc(String reasonDesc) {
			this.reasonDesc = reasonDesc;
		}
		public double getBounceFine() {
			return bounceFine;
		}
		public void setBounceFine(double bounceFine) {
			this.bounceFine = bounceFine;
		}
	  
	  	

	  	
	 }