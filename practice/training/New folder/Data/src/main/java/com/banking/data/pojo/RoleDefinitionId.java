package com.banking.data.pojo;
// default package



/**
 * RoleDefinitionId entity. @author MyEclipse Persistence Tools
 */

public class RoleDefinitionId  implements java.io.Serializable {


    // Fields    

     private String roleCode;
     private String roleDef;


    // Constructors

    /** default constructor */
    public RoleDefinitionId() {
    }

    
    /** full constructor */
    public RoleDefinitionId(String roleCode, String roleDef) {
        this.roleCode = roleCode;
        this.roleDef = roleDef;
    }

   
    // Property accessors

    public String getRoleCode() {
        return this.roleCode;
    }
    
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleDef() {
        return this.roleDef;
    }
    
    public void setRoleDef(String roleDef) {
        this.roleDef = roleDef;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof RoleDefinitionId) ) return false;
		 RoleDefinitionId castOther = ( RoleDefinitionId ) other; 
         
		 return ( (this.getRoleCode()==castOther.getRoleCode()) || ( this.getRoleCode()!=null && castOther.getRoleCode()!=null && this.getRoleCode().equals(castOther.getRoleCode()) ) )
 && ( (this.getRoleDef()==castOther.getRoleDef()) || ( this.getRoleDef()!=null && castOther.getRoleDef()!=null && this.getRoleDef().equals(castOther.getRoleDef()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getRoleCode() == null ? 0 : this.getRoleCode().hashCode() );
         result = 37 * result + ( getRoleDef() == null ? 0 : this.getRoleDef().hashCode() );
         return result;
   }   





}