package com.banking.data.pojo;
// default package



/**
 * DocumentParamerId entity. @author MyEclipse Persistence Tools
 */

public class DocumentParamerId  implements java.io.Serializable {


    // Fields    

     private Integer docNo;
     private String docDesc;
     private String lnCat;
     private String deTml;
     private String deUser;
     private String deDate;


    // Constructors

    /** default constructor */
    public DocumentParamerId() {
    }

    
    /** full constructor */
    public DocumentParamerId(Integer docNo, String docDesc, String lnCat, String deTml, String deUser, String deDate) {
        this.docNo = docNo;
        this.docDesc = docDesc;
        this.lnCat = lnCat;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
    }

   
    // Property accessors

    public Integer getDocNo() {
        return this.docNo;
    }
    
    public void setDocNo(Integer docNo) {
        this.docNo = docNo;
    }

    public String getDocDesc() {
        return this.docDesc;
    }
    
    public void setDocDesc(String docDesc) {
        this.docDesc = docDesc;
    }

    public String getLnCat() {
        return this.lnCat;
    }
    
    public void setLnCat(String lnCat) {
        this.lnCat = lnCat;
    }

    public String getDeTml() {
        return this.deTml;
    }
    
    public void setDeTml(String deTml) {
        this.deTml = deTml;
    }

    public String getDeUser() {
        return this.deUser;
    }
    
    public void setDeUser(String deUser) {
        this.deUser = deUser;
    }

    public String getDeDate() {
        return this.deDate;
    }
    
    public void setDeDate(String deDate) {
        this.deDate = deDate;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof DocumentParamerId) ) return false;
		 DocumentParamerId castOther = ( DocumentParamerId ) other; 
         
		 return ( (this.getDocNo()==castOther.getDocNo()) || ( this.getDocNo()!=null && castOther.getDocNo()!=null && this.getDocNo().equals(castOther.getDocNo()) ) )
 && ( (this.getDocDesc()==castOther.getDocDesc()) || ( this.getDocDesc()!=null && castOther.getDocDesc()!=null && this.getDocDesc().equals(castOther.getDocDesc()) ) )
 && ( (this.getLnCat()==castOther.getLnCat()) || ( this.getLnCat()!=null && castOther.getLnCat()!=null && this.getLnCat().equals(castOther.getLnCat()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getDocNo() == null ? 0 : this.getDocNo().hashCode() );
         result = 37 * result + ( getDocDesc() == null ? 0 : this.getDocDesc().hashCode() );
         result = 37 * result + ( getLnCat() == null ? 0 : this.getLnCat().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         return result;
   }   





}