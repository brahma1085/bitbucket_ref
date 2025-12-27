package com.banking.data.pojo;
// default package



/**
 * UserMasterId entity. @author MyEclipse Persistence Tools
 */

public class UserMasterId  implements java.io.Serializable {


    // Fields    

     private String userId;
     private Integer cid;
     private String password;
     private String shortName;
     private Integer pwdExpiryPeriod;
     private String pwdExpiryDate;
     private String thumpIpm;
     private String operativeFromDate;
     private String operativeToDate;
     private Integer disable;
     private String deTml;
     private String deUser;
     private String deDate;


    // Constructors

    /** default constructor */
    public UserMasterId() {
    }

    
    /** full constructor */
    public UserMasterId(String userId, Integer cid, String password, String shortName, Integer pwdExpiryPeriod, String pwdExpiryDate, String thumpIpm, String operativeFromDate, String operativeToDate, Integer disable, String deTml, String deUser, String deDate) {
        this.userId = userId;
        this.cid = cid;
        this.password = password;
        this.shortName = shortName;
        this.pwdExpiryPeriod = pwdExpiryPeriod;
        this.pwdExpiryDate = pwdExpiryDate;
        this.thumpIpm = thumpIpm;
        this.operativeFromDate = operativeFromDate;
        this.operativeToDate = operativeToDate;
        this.disable = disable;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
    }

   
    // Property accessors

    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getCid() {
        return this.cid;
    }
    
    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public String getShortName() {
        return this.shortName;
    }
    
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Integer getPwdExpiryPeriod() {
        return this.pwdExpiryPeriod;
    }
    
    public void setPwdExpiryPeriod(Integer pwdExpiryPeriod) {
        this.pwdExpiryPeriod = pwdExpiryPeriod;
    }

    public String getPwdExpiryDate() {
        return this.pwdExpiryDate;
    }
    
    public void setPwdExpiryDate(String pwdExpiryDate) {
        this.pwdExpiryDate = pwdExpiryDate;
    }

    public String getThumpIpm() {
        return this.thumpIpm;
    }
    
    public void setThumpIpm(String thumpIpm) {
        this.thumpIpm = thumpIpm;
    }

    public String getOperativeFromDate() {
        return this.operativeFromDate;
    }
    
    public void setOperativeFromDate(String operativeFromDate) {
        this.operativeFromDate = operativeFromDate;
    }

    public String getOperativeToDate() {
        return this.operativeToDate;
    }
    
    public void setOperativeToDate(String operativeToDate) {
        this.operativeToDate = operativeToDate;
    }

    public Integer getDisable() {
        return this.disable;
    }
    
    public void setDisable(Integer disable) {
        this.disable = disable;
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
		 if ( !(other instanceof UserMasterId) ) return false;
		 UserMasterId castOther = ( UserMasterId ) other; 
         
		 return ( (this.getUserId()==castOther.getUserId()) || ( this.getUserId()!=null && castOther.getUserId()!=null && this.getUserId().equals(castOther.getUserId()) ) )
 && ( (this.getCid()==castOther.getCid()) || ( this.getCid()!=null && castOther.getCid()!=null && this.getCid().equals(castOther.getCid()) ) )
 && ( (this.getPassword()==castOther.getPassword()) || ( this.getPassword()!=null && castOther.getPassword()!=null && this.getPassword().equals(castOther.getPassword()) ) )
 && ( (this.getShortName()==castOther.getShortName()) || ( this.getShortName()!=null && castOther.getShortName()!=null && this.getShortName().equals(castOther.getShortName()) ) )
 && ( (this.getPwdExpiryPeriod()==castOther.getPwdExpiryPeriod()) || ( this.getPwdExpiryPeriod()!=null && castOther.getPwdExpiryPeriod()!=null && this.getPwdExpiryPeriod().equals(castOther.getPwdExpiryPeriod()) ) )
 && ( (this.getPwdExpiryDate()==castOther.getPwdExpiryDate()) || ( this.getPwdExpiryDate()!=null && castOther.getPwdExpiryDate()!=null && this.getPwdExpiryDate().equals(castOther.getPwdExpiryDate()) ) )
 && ( (this.getThumpIpm()==castOther.getThumpIpm()) || ( this.getThumpIpm()!=null && castOther.getThumpIpm()!=null && this.getThumpIpm().equals(castOther.getThumpIpm()) ) )
 && ( (this.getOperativeFromDate()==castOther.getOperativeFromDate()) || ( this.getOperativeFromDate()!=null && castOther.getOperativeFromDate()!=null && this.getOperativeFromDate().equals(castOther.getOperativeFromDate()) ) )
 && ( (this.getOperativeToDate()==castOther.getOperativeToDate()) || ( this.getOperativeToDate()!=null && castOther.getOperativeToDate()!=null && this.getOperativeToDate().equals(castOther.getOperativeToDate()) ) )
 && ( (this.getDisable()==castOther.getDisable()) || ( this.getDisable()!=null && castOther.getDisable()!=null && this.getDisable().equals(castOther.getDisable()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getUserId() == null ? 0 : this.getUserId().hashCode() );
         result = 37 * result + ( getCid() == null ? 0 : this.getCid().hashCode() );
         result = 37 * result + ( getPassword() == null ? 0 : this.getPassword().hashCode() );
         result = 37 * result + ( getShortName() == null ? 0 : this.getShortName().hashCode() );
         result = 37 * result + ( getPwdExpiryPeriod() == null ? 0 : this.getPwdExpiryPeriod().hashCode() );
         result = 37 * result + ( getPwdExpiryDate() == null ? 0 : this.getPwdExpiryDate().hashCode() );
         result = 37 * result + ( getThumpIpm() == null ? 0 : this.getThumpIpm().hashCode() );
         result = 37 * result + ( getOperativeFromDate() == null ? 0 : this.getOperativeFromDate().hashCode() );
         result = 37 * result + ( getOperativeToDate() == null ? 0 : this.getOperativeToDate().hashCode() );
         result = 37 * result + ( getDisable() == null ? 0 : this.getDisable().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         return result;
   }   





}