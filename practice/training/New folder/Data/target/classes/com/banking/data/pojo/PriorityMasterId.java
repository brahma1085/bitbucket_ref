package com.banking.data.pojo;
// default package



/**
 * PriorityMasterId entity. @author MyEclipse Persistence Tools
 */

public class PriorityMasterId  implements java.io.Serializable {


    // Fields    

     private Integer prCode;
     private String prDesc;
     private String deTml;
     private String deUser;
     private String deDate;


    // Constructors

    /** default constructor */
    public PriorityMasterId() {
    }

	/** minimal constructor */
    public PriorityMasterId(Integer prCode) {
        this.prCode = prCode;
    }
    
    /** full constructor */
    public PriorityMasterId(Integer prCode, String prDesc, String deTml, String deUser, String deDate) {
        this.prCode = prCode;
        this.prDesc = prDesc;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
    }

   
    // Property accessors

    public Integer getPrCode() {
        return this.prCode;
    }
    
    public void setPrCode(Integer prCode) {
        this.prCode = prCode;
    }

    public String getPrDesc() {
        return this.prDesc;
    }
    
    public void setPrDesc(String prDesc) {
        this.prDesc = prDesc;
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
		 if ( !(other instanceof PriorityMasterId) ) return false;
		 PriorityMasterId castOther = ( PriorityMasterId ) other; 
         
		 return ( (this.getPrCode()==castOther.getPrCode()) || ( this.getPrCode()!=null && castOther.getPrCode()!=null && this.getPrCode().equals(castOther.getPrCode()) ) )
 && ( (this.getPrDesc()==castOther.getPrDesc()) || ( this.getPrDesc()!=null && castOther.getPrDesc()!=null && this.getPrDesc().equals(castOther.getPrDesc()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getPrCode() == null ? 0 : this.getPrCode().hashCode() );
         result = 37 * result + ( getPrDesc() == null ? 0 : this.getPrDesc().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         return result;
   }   





}