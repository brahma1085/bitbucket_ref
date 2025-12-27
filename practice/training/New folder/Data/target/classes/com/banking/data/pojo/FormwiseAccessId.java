package com.banking.data.pojo;
// default package



/**
 * FormwiseAccessId entity. @author MyEclipse Persistence Tools
 */

public class FormwiseAccessId  implements java.io.Serializable {


    // Fields    

     private String roleCode;
     private String formcode;
     private String access;
     private String deUser;
     private String deDate;


    // Constructors

    /** default constructor */
    public FormwiseAccessId() {
    }

    
    /** full constructor */
    public FormwiseAccessId(String roleCode, String formcode, String access, String deUser, String deDate) {
        this.roleCode = roleCode;
        this.formcode = formcode;
        this.access = access;
        this.deUser = deUser;
        this.deDate = deDate;
    }

   
    // Property accessors

    public String getRoleCode() {
        return this.roleCode;
    }
    
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getFormcode() {
        return this.formcode;
    }
    
    public void setFormcode(String formcode) {
        this.formcode = formcode;
    }

    public String getAccess() {
        return this.access;
    }
    
    public void setAccess(String access) {
        this.access = access;
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
		 if ( !(other instanceof FormwiseAccessId) ) return false;
		 FormwiseAccessId castOther = ( FormwiseAccessId ) other; 
         
		 return ( (this.getRoleCode()==castOther.getRoleCode()) || ( this.getRoleCode()!=null && castOther.getRoleCode()!=null && this.getRoleCode().equals(castOther.getRoleCode()) ) )
 && ( (this.getFormcode()==castOther.getFormcode()) || ( this.getFormcode()!=null && castOther.getFormcode()!=null && this.getFormcode().equals(castOther.getFormcode()) ) )
 && ( (this.getAccess()==castOther.getAccess()) || ( this.getAccess()!=null && castOther.getAccess()!=null && this.getAccess().equals(castOther.getAccess()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getRoleCode() == null ? 0 : this.getRoleCode().hashCode() );
         result = 37 * result + ( getFormcode() == null ? 0 : this.getFormcode().hashCode() );
         result = 37 * result + ( getAccess() == null ? 0 : this.getAccess().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         return result;
   }   





}