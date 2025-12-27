package com.banking.data.pojo;
// default package



/**
 * ReasonLinkId entity. @author MyEclipse Persistence Tools
 */

public class ReasonLinkId  implements java.io.Serializable {


    // Fields    

     private Integer linkCode;
     private String linkDesc;
     private Integer RCode;
     private String deTml;
     private String deUser;
     private String deDtTime;


    // Constructors

    /** default constructor */
    public ReasonLinkId() {
    }

    
    /** full constructor */
    public ReasonLinkId(Integer linkCode, String linkDesc, Integer RCode, String deTml, String deUser, String deDtTime) {
        this.linkCode = linkCode;
        this.linkDesc = linkDesc;
        this.RCode = RCode;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDtTime = deDtTime;
    }

   
    // Property accessors

    public Integer getLinkCode() {
        return this.linkCode;
    }
    
    public void setLinkCode(Integer linkCode) {
        this.linkCode = linkCode;
    }

    public String getLinkDesc() {
        return this.linkDesc;
    }
    
    public void setLinkDesc(String linkDesc) {
        this.linkDesc = linkDesc;
    }

    public Integer getRCode() {
        return this.RCode;
    }
    
    public void setRCode(Integer RCode) {
        this.RCode = RCode;
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

    public String getDeDtTime() {
        return this.deDtTime;
    }
    
    public void setDeDtTime(String deDtTime) {
        this.deDtTime = deDtTime;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ReasonLinkId) ) return false;
		 ReasonLinkId castOther = ( ReasonLinkId ) other; 
         
		 return ( (this.getLinkCode()==castOther.getLinkCode()) || ( this.getLinkCode()!=null && castOther.getLinkCode()!=null && this.getLinkCode().equals(castOther.getLinkCode()) ) )
 && ( (this.getLinkDesc()==castOther.getLinkDesc()) || ( this.getLinkDesc()!=null && castOther.getLinkDesc()!=null && this.getLinkDesc().equals(castOther.getLinkDesc()) ) )
 && ( (this.getRCode()==castOther.getRCode()) || ( this.getRCode()!=null && castOther.getRCode()!=null && this.getRCode().equals(castOther.getRCode()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDtTime()==castOther.getDeDtTime()) || ( this.getDeDtTime()!=null && castOther.getDeDtTime()!=null && this.getDeDtTime().equals(castOther.getDeDtTime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getLinkCode() == null ? 0 : this.getLinkCode().hashCode() );
         result = 37 * result + ( getLinkDesc() == null ? 0 : this.getLinkDesc().hashCode() );
         result = 37 * result + ( getRCode() == null ? 0 : this.getRCode().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDtTime() == null ? 0 : this.getDeDtTime().hashCode() );
         return result;
   }   





}