package com.banking.data.pojo;
// default package



/**
 * UserRoleMappingId entity. @author MyEclipse Persistence Tools
 */

public class UserRoleMappingId  implements java.io.Serializable {


    // Fields    

     private String userid;
     private String roleCode;


    // Constructors

    /** default constructor */
    public UserRoleMappingId() {
    }

    
    /** full constructor */
    public UserRoleMappingId(String userid, String roleCode) {
        this.userid = userid;
        this.roleCode = roleCode;
    }

   
    // Property accessors

    public String getUserid() {
        return this.userid;
    }
    
    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRoleCode() {
        return this.roleCode;
    }
    
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof UserRoleMappingId) ) return false;
		 UserRoleMappingId castOther = ( UserRoleMappingId ) other; 
         
		 return ( (this.getUserid()==castOther.getUserid()) || ( this.getUserid()!=null && castOther.getUserid()!=null && this.getUserid().equals(castOther.getUserid()) ) )
 && ( (this.getRoleCode()==castOther.getRoleCode()) || ( this.getRoleCode()!=null && castOther.getRoleCode()!=null && this.getRoleCode().equals(castOther.getRoleCode()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getUserid() == null ? 0 : this.getUserid().hashCode() );
         result = 37 * result + ( getRoleCode() == null ? 0 : this.getRoleCode().hashCode() );
         return result;
   }   





}