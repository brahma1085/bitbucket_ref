package masterObject.pygmyDeposit;

import java.io.Serializable;

public class SimpleMasterObject implements Serializable 
{
	
	String accType;
	int cid;
	public String getAccType() {
		return accType;
	}
	public void setAccType(String accType) {
		this.accType = accType;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
}
