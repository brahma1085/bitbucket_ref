package com.banking.data.pojo;
// default package



/**
 * TerminalLogId entity. @author MyEclipse Persistence Tools
 */

public class TerminalLogId  implements java.io.Serializable {


    // Fields    

     private Integer userNo;
     private String tmlCode;
     private String deUser;
     private String deTml;
     private String deDatetime;


    // Constructors

    /** default constructor */
    public TerminalLogId() {
    }

    
    /** full constructor */
    public TerminalLogId(Integer userNo, String tmlCode, String deUser, String deTml, String deDatetime) {
        this.userNo = userNo;
        this.tmlCode = tmlCode;
        this.deUser = deUser;
        this.deTml = deTml;
        this.deDatetime = deDatetime;
    }

   
    // Property accessors

    public Integer getUserNo() {
        return this.userNo;
    }
    
    public void setUserNo(Integer userNo) {
        this.userNo = userNo;
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

    public String getDeDatetime() {
        return this.deDatetime;
    }
    
    public void setDeDatetime(String deDatetime) {
        this.deDatetime = deDatetime;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof TerminalLogId) ) return false;
		 TerminalLogId castOther = ( TerminalLogId ) other; 
         
		 return ( (this.getUserNo()==castOther.getUserNo()) || ( this.getUserNo()!=null && castOther.getUserNo()!=null && this.getUserNo().equals(castOther.getUserNo()) ) )
 && ( (this.getTmlCode()==castOther.getTmlCode()) || ( this.getTmlCode()!=null && castOther.getTmlCode()!=null && this.getTmlCode().equals(castOther.getTmlCode()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeDatetime()==castOther.getDeDatetime()) || ( this.getDeDatetime()!=null && castOther.getDeDatetime()!=null && this.getDeDatetime().equals(castOther.getDeDatetime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getUserNo() == null ? 0 : this.getUserNo().hashCode() );
         result = 37 * result + ( getTmlCode() == null ? 0 : this.getTmlCode().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeDatetime() == null ? 0 : this.getDeDatetime().hashCode() );
         return result;
   }   





}