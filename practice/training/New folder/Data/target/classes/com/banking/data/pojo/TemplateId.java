package com.banking.data.pojo;
// default package



/**
 * TemplateId entity. @author MyEclipse Persistence Tools
 */

public class TemplateId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private Integer tempNo;
     private Integer stageNo;
     private String text;
     private String deUser;
     private String deTml;
     private String deDate;


    // Constructors

    /** default constructor */
    public TemplateId() {
    }

    
    /** full constructor */
    public TemplateId(String acType, Integer tempNo, Integer stageNo, String text, String deUser, String deTml, String deDate) {
        this.acType = acType;
        this.tempNo = tempNo;
        this.stageNo = stageNo;
        this.text = text;
        this.deUser = deUser;
        this.deTml = deTml;
        this.deDate = deDate;
    }

   
    // Property accessors

    public String getAcType() {
        return this.acType;
    }
    
    public void setAcType(String acType) {
        this.acType = acType;
    }

    public Integer getTempNo() {
        return this.tempNo;
    }
    
    public void setTempNo(Integer tempNo) {
        this.tempNo = tempNo;
    }

    public Integer getStageNo() {
        return this.stageNo;
    }
    
    public void setStageNo(Integer stageNo) {
        this.stageNo = stageNo;
    }

    public String getText() {
        return this.text;
    }
    
    public void setText(String text) {
        this.text = text;
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
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof TemplateId) ) return false;
		 TemplateId castOther = ( TemplateId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getTempNo()==castOther.getTempNo()) || ( this.getTempNo()!=null && castOther.getTempNo()!=null && this.getTempNo().equals(castOther.getTempNo()) ) )
 && ( (this.getStageNo()==castOther.getStageNo()) || ( this.getStageNo()!=null && castOther.getStageNo()!=null && this.getStageNo().equals(castOther.getStageNo()) ) )
 && ( (this.getText()==castOther.getText()) || ( this.getText()!=null && castOther.getText()!=null && this.getText().equals(castOther.getText()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getTempNo() == null ? 0 : this.getTempNo().hashCode() );
         result = 37 * result + ( getStageNo() == null ? 0 : this.getStageNo().hashCode() );
         result = 37 * result + ( getText() == null ? 0 : this.getText().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         return result;
   }   





}