package com.banking.data.pojo;
// default package



/**
 * UserTerminalsId entity. @author MyEclipse Persistence Tools
 */

public class UserTerminalsId  implements java.io.Serializable {


    // Fields    

     private String userId;
     private String tmlCode;
     private String deUser;
     private String deTml;
     private String deDate;
     private Integer status;


    // Constructors

    /** default constructor */
    public UserTerminalsId() {
    }

    
    /** full constructor */
    public UserTerminalsId(String userId, String tmlCode, String deUser, String deTml, String deDate, Integer status) {
        this.userId = userId;
        this.tmlCode = tmlCode;
        this.deUser = deUser;
        this.deTml = deTml;
        this.deDate = deDate;
        this.status = status;
    }

   
    // Property accessors

    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTmlCode() {
        return this.tmlCode;
    }
    
    public void setTmlCode(String tmlCode) {
        this.tmlCode = tmlCode;
    }

    public String getDeUser() {
        return this.deUser;
    }
    
    public void setDeUser(String deUser) {
        this.deUser = deUser;
    }

    public String getDeTml() {
        return this.deTml;
    }
    
    public void setDeTml(String deTml) {
        this.deTml = deTml;
    }

    public String getDeDate() {
        return this.deDate;
    }
    
    public void setDeDate(String deDate) {
        this.deDate = deDate;
    }

    public Integer getStatus() {
        return this.status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof UserTerminalsId) ) return false;
		 UserTerminalsId castOther = ( UserTerminalsId ) other; 
         
		 return ( (this.getUserId()==castOther.getUserId()) || ( this.getUserId()!=null && castOther.getUserId()!=null && this.getUserId().equals(castOther.getUserId()) ) )
 && ( (this.getTmlCode()==castOther.getTmlCode()) || ( this.getTmlCode()!=null && castOther.getTmlCode()!=null && this.getTmlCode().equals(castOther.getTmlCode()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) )
 && ( (this.getStatus()==castOther.getStatus()) || ( this.getStatus()!=null && castOther.getStatus()!=null && this.getStatus().equals(castOther.getStatus()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getUserId() == null ? 0 : this.getUserId().hashCode() );
         result = 37 * result + ( getTmlCode() == null ? 0 : this.getTmlCode().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         result = 37 * result + ( getStatus() == null ? 0 : this.getStatus().hashCode() );
         return result;
   }   





}