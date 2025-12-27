package com.banking.data.pojo;
// default package



/**
 * AgentMaster entity. @author MyEclipse Persistence Tools
 */

public class AgentMaster  implements java.io.Serializable {


    // Fields    

     private AgentMasterId id;
     private Integer cid;
     private Integer noJtHldr;
     private String apptDate;
     private String PAcType;
     private Integer PAcNo;
     private String jtAcType;
     private Integer jtAcNo;
     private String intrAcType;
     private Integer intrAcNo;
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
    public AgentMaster() {
    }

	/** minimal constructor */
    public AgentMaster(AgentMasterId id) {
        this.id = id;
    }
    
    /** full constructor */
    public AgentMaster(AgentMasterId id, Integer cid, Integer noJtHldr, String apptDate, String PAcType, Integer PAcNo, String jtAcType, Integer jtAcNo, String intrAcType, Integer intrAcNo, String closeDate, Integer closeInd, String deTml, String deUser, String deDate, String veTml, String veUser, String veDate, Integer refInd) {
        this.id = id;
        this.cid = cid;
        this.noJtHldr = noJtHldr;
        this.apptDate = apptDate;
        this.PAcType = PAcType;
        this.PAcNo = PAcNo;
        this.jtAcType = jtAcType;
        this.jtAcNo = jtAcNo;
        this.intrAcType = intrAcType;
        this.intrAcNo = intrAcNo;
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

    public AgentMasterId getId() {
        return this.id;
    }
    
    public void setId(AgentMasterId id) {
        this.id = id;
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

    public String getIntrAcType() {
        return this.intrAcType;
    }
    
    public void setIntrAcType(String intrAcType) {
        this.intrAcType = intrAcType;
    }

    public Integer getIntrAcNo() {
        return this.intrAcNo;
    }
    
    public void setIntrAcNo(Integer intrAcNo) {
        this.intrAcNo = intrAcNo;
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
   








}