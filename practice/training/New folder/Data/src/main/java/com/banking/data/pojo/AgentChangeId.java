package com.banking.data.pojo;
// default package



/**
 * AgentChangeId entity. @author MyEclipse Persistence Tools
 */

public class AgentChangeId  implements java.io.Serializable {


    // Fields    

     private String frmAgtType;
     private Integer frmAgtNo;
     private String toAgtType;
     private Integer toAgtNo;
     private String acType;
     private String pdModulecode;
     private Integer acNo;
     private String deTml;
     private String deUser;
     private String deDate;
     private String veTml;
     private String veUser;
     private String veDate;
     private Integer refInd;


    // Constructors

    /** default constructor */
    public AgentChangeId() {
    }

    
    /** full constructor */
    public AgentChangeId(String frmAgtType, Integer frmAgtNo, String toAgtType, Integer toAgtNo, String acType, String pdModulecode, Integer acNo, String deTml, String deUser, String deDate, String veTml, String veUser, String veDate, Integer refInd) {
        this.frmAgtType = frmAgtType;
        this.frmAgtNo = frmAgtNo;
        this.toAgtType = toAgtType;
        this.toAgtNo = toAgtNo;
        this.acType = acType;
        this.pdModulecode = pdModulecode;
        this.acNo = acNo;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
        this.veTml = veTml;
        this.veUser = veUser;
        this.veDate = veDate;
        this.refInd = refInd;
    }

   
    // Property accessors

    public String getFrmAgtType() {
        return this.frmAgtType;
    }
    
    public void setFrmAgtType(String frmAgtType) {
        this.frmAgtType = frmAgtType;
    }

    public Integer getFrmAgtNo() {
        return this.frmAgtNo;
    }
    
    public void setFrmAgtNo(Integer frmAgtNo) {
        this.frmAgtNo = frmAgtNo;
    }

    public String getToAgtType() {
        return this.toAgtType;
    }
    
    public void setToAgtType(String toAgtType) {
        this.toAgtType = toAgtType;
    }

    public Integer getToAgtNo() {
        return this.toAgtNo;
    }
    
    public void setToAgtNo(Integer toAgtNo) {
        this.toAgtNo = toAgtNo;
    }

    public String getAcType() {
        return this.acType;
    }
    
    public void setAcType(String acType) {
        this.acType = acType;
    }

    public String getPdModulecode() {
        return this.pdModulecode;
    }
    
    public void setPdModulecode(String pdModulecode) {
        this.pdModulecode = pdModulecode;
    }

    public Integer getAcNo() {
        return this.acNo;
    }
    
    public void setAcNo(Integer acNo) {
        this.acNo = acNo;
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

    public String getVeTml() {
        return this.veTml;
    }
    
    public void setVeTml(String veTml) {
        this.veTml = veTml;
    }

    public String getVeUser() {
        return this.veUser;
    }
    
    public void setVeUser(String veUser) {
        this.veUser = veUser;
    }

    public String getVeDate() {
        return this.veDate;
    }
    
    public void setVeDate(String veDate) {
        this.veDate = veDate;
    }

    public Integer getRefInd() {
        return this.refInd;
    }
    
    public void setRefInd(Integer refInd) {
        this.refInd = refInd;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof AgentChangeId) ) return false;
		 AgentChangeId castOther = ( AgentChangeId ) other; 
         
		 return ( (this.getFrmAgtType()==castOther.getFrmAgtType()) || ( this.getFrmAgtType()!=null && castOther.getFrmAgtType()!=null && this.getFrmAgtType().equals(castOther.getFrmAgtType()) ) )
 && ( (this.getFrmAgtNo()==castOther.getFrmAgtNo()) || ( this.getFrmAgtNo()!=null && castOther.getFrmAgtNo()!=null && this.getFrmAgtNo().equals(castOther.getFrmAgtNo()) ) )
 && ( (this.getToAgtType()==castOther.getToAgtType()) || ( this.getToAgtType()!=null && castOther.getToAgtType()!=null && this.getToAgtType().equals(castOther.getToAgtType()) ) )
 && ( (this.getToAgtNo()==castOther.getToAgtNo()) || ( this.getToAgtNo()!=null && castOther.getToAgtNo()!=null && this.getToAgtNo().equals(castOther.getToAgtNo()) ) )
 && ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getPdModulecode()==castOther.getPdModulecode()) || ( this.getPdModulecode()!=null && castOther.getPdModulecode()!=null && this.getPdModulecode().equals(castOther.getPdModulecode()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) )
 && ( (this.getVeTml()==castOther.getVeTml()) || ( this.getVeTml()!=null && castOther.getVeTml()!=null && this.getVeTml().equals(castOther.getVeTml()) ) )
 && ( (this.getVeUser()==castOther.getVeUser()) || ( this.getVeUser()!=null && castOther.getVeUser()!=null && this.getVeUser().equals(castOther.getVeUser()) ) )
 && ( (this.getVeDate()==castOther.getVeDate()) || ( this.getVeDate()!=null && castOther.getVeDate()!=null && this.getVeDate().equals(castOther.getVeDate()) ) )
 && ( (this.getRefInd()==castOther.getRefInd()) || ( this.getRefInd()!=null && castOther.getRefInd()!=null && this.getRefInd().equals(castOther.getRefInd()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getFrmAgtType() == null ? 0 : this.getFrmAgtType().hashCode() );
         result = 37 * result + ( getFrmAgtNo() == null ? 0 : this.getFrmAgtNo().hashCode() );
         result = 37 * result + ( getToAgtType() == null ? 0 : this.getToAgtType().hashCode() );
         result = 37 * result + ( getToAgtNo() == null ? 0 : this.getToAgtNo().hashCode() );
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getPdModulecode() == null ? 0 : this.getPdModulecode().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         result = 37 * result + ( getVeTml() == null ? 0 : this.getVeTml().hashCode() );
         result = 37 * result + ( getVeUser() == null ? 0 : this.getVeUser().hashCode() );
         result = 37 * result + ( getVeDate() == null ? 0 : this.getVeDate().hashCode() );
         result = 37 * result + ( getRefInd() == null ? 0 : this.getRefInd().hashCode() );
         return result;
   }   





}