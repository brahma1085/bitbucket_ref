package com.banking.data.pojo;
// default package



/**
 * ShareType entity. @author MyEclipse Persistence Tools
 */

public class ShareType  implements java.io.Serializable {


    // Fields    

     private ShareTypeId id;
     private String catname;
     private Integer minshare;
     private Integer maxshare;
     private Double shareval;
     private String votePow;


    // Constructors

    /** default constructor */
    public ShareType() {
    }

	/** minimal constructor */
    public ShareType(ShareTypeId id) {
        this.id = id;
    }
    
    /** full constructor */
    public ShareType(ShareTypeId id, String catname, Integer minshare, Integer maxshare, Double shareval, String votePow) {
        this.id = id;
        this.catname = catname;
        this.minshare = minshare;
        this.maxshare = maxshare;
        this.shareval = shareval;
        this.votePow = votePow;
    }

   
    // Property accessors

    public ShareTypeId getId() {
        return this.id;
    }
    
    public void setId(ShareTypeId id) {
        this.id = id;
    }

    public String getCatname() {
        return this.catname;
    }
    
    public void setCatname(String catname) {
        this.catname = catname;
    }

    public Integer getMinshare() {
        return this.minshare;
    }
    
    public void setMinshare(Integer minshare) {
        this.minshare = minshare;
    }

    public Integer getMaxshare() {
        return this.maxshare;
    }
    
    public void setMaxshare(Integer maxshare) {
        this.maxshare = maxshare;
    }

    public Double getShareval() {
        return this.shareval;
    }
    
    public void setShareval(Double shareval) {
        this.shareval = shareval;
    }

    public String getVotePow() {
        return this.votePow;
    }
    
    public void setVotePow(String votePow) {
        this.votePow = votePow;
    }
   








}