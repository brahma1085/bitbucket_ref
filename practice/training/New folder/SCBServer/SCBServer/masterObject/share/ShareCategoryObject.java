package masterObject.share;
import java.io.Serializable;

public class ShareCategoryObject implements Serializable
{

    	int shCat,minShare,maxShare;
    	double shareVal;
    	String catName,cap_gl_code,sus_gl_code,shareType;
    	String vote="<html><input type='text'></html>";
        String check="<html><input type='text'></html>";
    	public String getCheck() {
			return check;
		}

		public void setCheck(String check) {
			this.check = check;
		}

		public int getShCat(){return shCat;}

    	public int getMinShare(){return minShare;}

	public int getMaxShare(){return maxShare;}

	public double getShareVal(){return shareVal;}
 
	public String getCatName(){return catName;}



        public void setShCat(int ShCat){this.shCat=ShCat;}

    	public void setMinShare(int minshare){ this.minShare=minshare;}

	public void setMaxShare(int maxshare){ this.maxShare=maxshare;}

	public void setShareVal(double shareval){ this.shareVal=shareval;}

	public void setCatName(String CatName){this.catName=CatName;}

	public String getShareType(){return shareType;}
	public void setShareType(String catname){this.shareType=catname;}

	public String getCapitalGLCode(){return cap_gl_code;}
	public void setCapitalGLCode(String catname){this.cap_gl_code=catname;}

	public String getSuspenceGLCode(){return sus_gl_code;}
	public void setSuspenceGLCode(String sus_gl_code){this.sus_gl_code=sus_gl_code;}

	public String getVote() {
		return vote;
	}

	public void setVote(String vote) {
		this.vote = vote;
	}

}


