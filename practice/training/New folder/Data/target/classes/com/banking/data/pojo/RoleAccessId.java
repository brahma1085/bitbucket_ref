package com.banking.data.pojo;
// default package



/**
 * RoleAccessId entity. @author MyEclipse Persistence Tools
 */

public class RoleAccessId  implements java.io.Serializable {


    // Fields    

     private String role;
     private String screenId;
     private Integer access;


    // Constructors

    /** default constructor */
    public RoleAccessId() {
    }

	/** minimal constructor */
    public RoleAccessId(String role, String screenId) {
        this.role = role;
        this.screenId = screenId;
    }
    
    /** full constructor */
    public RoleAccessId(String role, String screenId, Integer access) {
        this.role = role;
        this.screenId = screenId;
        this.access = access;
    }

   
    // Property accessors

    public String getRole() {
        return this.role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }

    public String getScreenId() {
        return this.screenId;
    }
    
    public void setScreenId(String screenId) {
        this.screenId = screenId;
    }

    public Integer getAccess() {
        return this.access;
    }
    
    public void setAccess(Integer access) {
        this.access = access;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof RoleAccessId) ) return false;
		 RoleAccessId castOther = ( RoleAccessId ) other; 
         
		 return ( (this.getRole()==castOther.getRole()) || ( this.getRole()!=null && castOther.getRole()!=null && this.getRole().equals(castOther.getRole()) ) )
 && ( (this.getScreenId()==castOther.getScreenId()) || ( this.getScreenId()!=null && castOther.getScreenId()!=null && this.getScreenId().equals(castOther.getScreenId()) ) )
 && ( (this.getAccess()==castOther.getAccess()) || ( this.getAccess()!=null && castOther.getAccess()!=null && this.getAccess().equals(castOther.getAccess()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getRole() == null ? 0 : this.getRole().hashCode() );
         result = 37 * result + ( getScreenId() == null ? 0 : this.getScreenId().hashCode() );
         result = 37 * result + ( getAccess() == null ? 0 : this.getAccess().hashCode() );
         return result;
   }   





}