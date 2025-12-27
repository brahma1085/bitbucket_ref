package com.banking.data.pojo;
// default package



/**
 * DirectorMaster entity. @author MyEclipse Persistence Tools
 */

public class DirectorMaster  implements java.io.Serializable {


    // Fields    

     private Integer srNo;
     private Integer directorCode;
     private Integer cid;
     private String fromDate;
     private String toDate;


    // Constructors

    /** default constructor */
    public DirectorMaster() {
    }

	/** minimal constructor */
    public DirectorMaster(Integer directorCode) {
        this.directorCode = directorCode;
    }
    
    /** full constructor */
    public DirectorMaster(Integer directorCode, Integer cid, String fromDate, String toDate) {
        this.directorCode = directorCode;
        this.cid = cid;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

   
    // Property accessors

    public Integer getSrNo() {
        return this.srNo;
    }
    
    public void setSrNo(Integer srNo) {
        this.srNo = srNo;
    }

    public Integer getDirectorCode() {
        return this.directorCode;
    }
    
    public void setDirectorCode(Integer directorCode) {
        this.directorCode = directorCode;
    }

    public Integer getCid() {
        return this.cid;
    }
    
    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getFromDate() {
        return this.fromDate;
    }
    
    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return this.toDate;
    }
    
    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
   








}