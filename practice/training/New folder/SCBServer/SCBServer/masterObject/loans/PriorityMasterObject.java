package masterObject.loans;

//import general.UserVerifier;

import java.io.Serializable;
//Vinay
public class PriorityMasterObject implements Serializable {
   private String prior_desc;
   private int prior_code;
   
   //public UserVerifier uv = new UserVerifier();

public int getPrior_code() {
	return prior_code;
}

public void setPrior_code(int prior_code) {
	this.prior_code = prior_code;
}

public String getPrior_desc() {
	return prior_desc;
}

public void setPrior_desc(String prior_desc) {
	this.prior_desc = prior_desc;
}
   
   
}
