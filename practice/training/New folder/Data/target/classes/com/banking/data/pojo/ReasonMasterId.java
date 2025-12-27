package com.banking.data.pojo;
// default package



/**
 * ReasonMasterId entity. @author MyEclipse Persistence Tools
 */

public class ReasonMasterId  implements java.io.Serializable {


    // Fields    

     private Integer code;
     private String reason;
     private String deTml;
     private String deUser;
     private String deDate;


    // Constructors

    /** default constructor */
    public ReasonMasterId() {
    }

	/** minimal constructor */
    public ReasonMasterId(Integer code) {
        this.code = code;
    }
    
    /** full constructor */
    public ReasonMasterId(Integer code, String reason, String deTml, String deUser, String deDate) {
        this.code = code;
        this.reason = reason;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
    }

   
    // Property accessors

    public Integer getCode() {
        return this.code;
    }
    
    public void setCode(Integer code) {
        this.code = code;
    }

    public String getReason() {
        return this.reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
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
		 if ( !(other instanceof ReasonMasterId) ) return false;
		 ReasonMasterId castOther = ( ReasonMasterId ) other; 
         
		 return ( (this.getCode()==castOther.getCode()) || ( this.getCode()!=null && castOther.getCode()!=null && this.getCode().equals(castOther.getCode()) ) )
 && ( (this.getReason()==castOther.getReason()) || ( this.getReason()!=null && castOther.getReason()!=null && this.getReason().equals(castOther.getReason()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getCode() == null ? 0 : this.getCode().hashCode() );
         result = 37 * result + ( getReason() == null ? 0 : this.getReason().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         return result;
   }   





}