package com.banking.data.pojo;
// default package



/**
 * ShareBranchData entity. @author MyEclipse Persistence Tools
 */

public class ShareBranchData  implements java.io.Serializable {


    // Fields    

     private ShareBranchDataId id;


    // Constructors

    /** default constructor */
    public ShareBranchData() {
    }

    
    /** full constructor */
    public ShareBranchData(ShareBranchDataId id) {
        this.id = id;
    }

   
    // Property accessors

    public ShareBranchDataId getId() {
        return this.id;
    }
    
    public void setId(ShareBranchDataId id) {
        this.id = id;
    }
   








}