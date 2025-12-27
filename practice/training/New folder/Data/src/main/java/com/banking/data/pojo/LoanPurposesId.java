package com.banking.data.pojo;
// default package



/**
 * LoanPurposesId entity. @author MyEclipse Persistence Tools
 */

public class LoanPurposesId  implements java.io.Serializable {


    // Fields    

     private Integer ppsNo;
     private String ppsDesc;
     private String deTml;
     private String deUser;
     private String deDatetime;


    // Constructors

    /** default constructor */
    public LoanPurposesId() {
    }

    
    /** full constructor */
    public LoanPurposesId(Integer ppsNo, String ppsDesc, String deTml, String deUser, String deDatetime) {
        this.ppsNo = ppsNo;
        this.ppsDesc = ppsDesc;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDatetime = deDatetime;
    }

   
    // Property accessors

    public Integer getPpsNo() {
        return this.ppsNo;
    }
    
    public void setPpsNo(Integer ppsNo) {
        this.ppsNo = ppsNo;
    }

    public String getPpsDesc() {
        return this.ppsDesc;
    }
    
    public void setPpsDesc(String ppsDesc) {
        this.ppsDesc = ppsDesc;
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

    public String getDeDatetime() {
        return this.deDatetime;
    }
    
    public void setDeDatetime(String deDatetime) {
        this.deDatetime = deDatetime;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof LoanPurposesId) ) return false;
		 LoanPurposesId castOther = ( LoanPurposesId ) other; 
         
		 return ( (this.getPpsNo()==castOther.getPpsNo()) || ( this.getPpsNo()!=null && castOther.getPpsNo()!=null && this.getPpsNo().equals(castOther.getPpsNo()) ) )
 && ( (this.getPpsDesc()==castOther.getPpsDesc()) || ( this.getPpsDesc()!=null && castOther.getPpsDesc()!=null && this.getPpsDesc().equals(castOther.getPpsDesc()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDatetime()==castOther.getDeDatetime()) || ( this.getDeDatetime()!=null && castOther.getDeDatetime()!=null && this.getDeDatetime().equals(castOther.getDeDatetime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getPpsNo() == null ? 0 : this.getPpsNo().hashCode() );
         result = 37 * result + ( getPpsDesc() == null ? 0 : this.getPpsDesc().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDatetime() == null ? 0 : this.getDeDatetime().hashCode() );
         return result;
   }   





}