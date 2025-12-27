package com.banking.data.pojo;
// default package



/**
 * StdInstParamId entity. @author MyEclipse Persistence Tools
 */

public class StdInstParamId  implements java.io.Serializable {


    // Fields    

     private Integer priorityNo;
     private String frType;
     private String toType;
     private String deTml;
     private String deUser;
     private String deDtTime;


    // Constructors

    /** default constructor */
    public StdInstParamId() {
    }

    
    /** full constructor */
    public StdInstParamId(Integer priorityNo, String frType, String toType, String deTml, String deUser, String deDtTime) {
        this.priorityNo = priorityNo;
        this.frType = frType;
        this.toType = toType;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDtTime = deDtTime;
    }

   
    // Property accessors

    public Integer getPriorityNo() {
        return this.priorityNo;
    }
    
    public void setPriorityNo(Integer priorityNo) {
        this.priorityNo = priorityNo;
    }

    public String getFrType() {
        return this.frType;
    }
    
    public void setFrType(String frType) {
        this.frType = frType;
    }

    public String getToType() {
        return this.toType;
    }
    
    public void setToType(String toType) {
        this.toType = toType;
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
		 if ( !(other instanceof StdInstParamId) ) return false;
		 StdInstParamId castOther = ( StdInstParamId ) other; 
         
		 return ( (this.getPriorityNo()==castOther.getPriorityNo()) || ( this.getPriorityNo()!=null && castOther.getPriorityNo()!=null && this.getPriorityNo().equals(castOther.getPriorityNo()) ) )
 && ( (this.getFrType()==castOther.getFrType()) || ( this.getFrType()!=null && castOther.getFrType()!=null && this.getFrType().equals(castOther.getFrType()) ) )
 && ( (this.getToType()==castOther.getToType()) || ( this.getToType()!=null && castOther.getToType()!=null && this.getToType().equals(castOther.getToType()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDtTime()==castOther.getDeDtTime()) || ( this.getDeDtTime()!=null && castOther.getDeDtTime()!=null && this.getDeDtTime().equals(castOther.getDeDtTime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getPriorityNo() == null ? 0 : this.getPriorityNo().hashCode() );
         result = 37 * result + ( getFrType() == null ? 0 : this.getFrType().hashCode() );
         result = 37 * result + ( getToType() == null ? 0 : this.getToType().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDtTime() == null ? 0 : this.getDeDtTime().hashCode() );
         return result;
   }   





}