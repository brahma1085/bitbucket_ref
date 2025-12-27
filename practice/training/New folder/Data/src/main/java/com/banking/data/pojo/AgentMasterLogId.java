package com.banking.data.pojo;
// default package



/**
 * AgentMasterLogId entity. @author MyEclipse Persistence Tools
 */

public class AgentMasterLogId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private Integer acNo;
     private Integer cid;
     private Integer noJtHldr;
     private String apptDate;
     private String PAcType;
     private Integer PAcNo;
     private String jtAcType;
     private Integer jtAcNo;
     private Integer secCid;
     private String closeDate;
     private Integer closeInd;
     private String deTml;
     private String deUser;
     private String deDate;
     private String veTml;
     private String veUser;
     private String veDate;
     private Integer refInd;


    // Constructors

    /** default constructor */
    public AgentMasterLogId() {
    }

	/** minimal constructor */
    public AgentMasterLogId(String acType, Integer acNo) {
        this.acType = acType;
        this.acNo = acNo;
    }
    
    /** full constructor */
    public AgentMasterLogId(String acType, Integer acNo, Integer cid, Integer noJtHldr, String apptDate, String PAcType, Integer PAcNo, String jtAcType, Integer jtAcNo, Integer secCid, String closeDate, Integer closeInd, String deTml, String deUser, String deDate, String veTml, String veUser, String veDate, Integer refInd) {
        this.acType = acType;
        this.acNo = acNo;
        this.cid = cid;
        this.noJtHldr = noJtHldr;
        this.apptDate = apptDate;
        this.PAcType = PAcType;
        this.PAcNo = PAcNo;
        this.jtAcType = jtAcType;
        this.jtAcNo = jtAcNo;
        this.secCid = secCid;
        this.closeDate = closeDate;
        this.closeInd = closeInd;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
        this.veTml = veTml;
        this.veUser = veUser;
        this.veDate = veDate;
        this.refInd = refInd;
    }

   
    // Property accessors

    public String getAcType() {
        return this.acType;
    }
    
    public void setAcType(String acType) {
        this.acType = acType;
    }

    public Integer getAcNo() {
        return this.acNo;
    }
    
    public void setAcNo(Integer acNo) {
        this.acNo = acNo;
    }

    public Integer getCid() {
        return this.cid;
    }
    
    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getNoJtHldr() {
        return this.noJtHldr;
    }
    
    public void setNoJtHldr(Integer noJtHldr) {
        this.noJtHldr = noJtHldr;
    }

    public String getApptDate() {
        return this.apptDate;
    }
    
    public void setApptDate(String apptDate) {
        this.apptDate = apptDate;
    }

    public String getPAcType() {
        return this.PAcType;
    }
    
    public void setPAcType(String PAcType) {
        this.PAcType = PAcType;
    }

    public Integer getPAcNo() {
        return this.PAcNo;
    }
    
    public void setPAcNo(Integer PAcNo) {
        this.PAcNo = PAcNo;
    }

    public String getJtAcType() {
        return this.jtAcType;
    }
    
    public void setJtAcType(String jtAcType) {
        this.jtAcType = jtAcType;
    }

    public Integer getJtAcNo() {
        return this.jtAcNo;
    }
    
    public void setJtAcNo(Integer jtAcNo) {
        this.jtAcNo = jtAcNo;
    }

    public Integer getSecCid() {
        return this.secCid;
    }
    
    public void setSecCid(Integer secCid) {
        this.secCid = secCid;
    }

    public String getCloseDate() {
        return this.closeDate;
    }
    
    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public Integer getCloseInd() {
        return this.closeInd;
    }
    
    public void setCloseInd(Integer closeInd) {
        this.closeInd = closeInd;
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
		 if ( !(other instanceof AgentMasterLogId) ) return false;
		 AgentMasterLogId castOther = ( AgentMasterLogId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getCid()==castOther.getCid()) || ( this.getCid()!=null && castOther.getCid()!=null && this.getCid().equals(castOther.getCid()) ) )
 && ( (this.getNoJtHldr()==castOther.getNoJtHldr()) || ( this.getNoJtHldr()!=null && castOther.getNoJtHldr()!=null && this.getNoJtHldr().equals(castOther.getNoJtHldr()) ) )
 && ( (this.getApptDate()==castOther.getApptDate()) || ( this.getApptDate()!=null && castOther.getApptDate()!=null && this.getApptDate().equals(castOther.getApptDate()) ) )
 && ( (this.getPAcType()==castOther.getPAcType()) || ( this.getPAcType()!=null && castOther.getPAcType()!=null && this.getPAcType().equals(castOther.getPAcType()) ) )
 && ( (this.getPAcNo()==castOther.getPAcNo()) || ( this.getPAcNo()!=null && castOther.getPAcNo()!=null && this.getPAcNo().equals(castOther.getPAcNo()) ) )
 && ( (this.getJtAcType()==castOther.getJtAcType()) || ( this.getJtAcType()!=null && castOther.getJtAcType()!=null && this.getJtAcType().equals(castOther.getJtAcType()) ) )
 && ( (this.getJtAcNo()==castOther.getJtAcNo()) || ( this.getJtAcNo()!=null && castOther.getJtAcNo()!=null && this.getJtAcNo().equals(castOther.getJtAcNo()) ) )
 && ( (this.getSecCid()==castOther.getSecCid()) || ( this.getSecCid()!=null && castOther.getSecCid()!=null && this.getSecCid().equals(castOther.getSecCid()) ) )
 && ( (this.getCloseDate()==castOther.getCloseDate()) || ( this.getCloseDate()!=null && castOther.getCloseDate()!=null && this.getCloseDate().equals(castOther.getCloseDate()) ) )
 && ( (this.getCloseInd()==castOther.getCloseInd()) || ( this.getCloseInd()!=null && castOther.getCloseInd()!=null && this.getCloseInd().equals(castOther.getCloseInd()) ) )
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
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getCid() == null ? 0 : this.getCid().hashCode() );
         result = 37 * result + ( getNoJtHldr() == null ? 0 : this.getNoJtHldr().hashCode() );
         result = 37 * result + ( getApptDate() == null ? 0 : this.getApptDate().hashCode() );
         result = 37 * result + ( getPAcType() == null ? 0 : this.getPAcType().hashCode() );
         result = 37 * result + ( getPAcNo() == null ? 0 : this.getPAcNo().hashCode() );
         result = 37 * result + ( getJtAcType() == null ? 0 : this.getJtAcType().hashCode() );
         result = 37 * result + ( getJtAcNo() == null ? 0 : this.getJtAcNo().hashCode() );
         result = 37 * result + ( getSecCid() == null ? 0 : this.getSecCid().hashCode() );
         result = 37 * result + ( getCloseDate() == null ? 0 : this.getCloseDate().hashCode() );
         result = 37 * result + ( getCloseInd() == null ? 0 : this.getCloseInd().hashCode() );
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