package com.banking.data.pojo;
// default package



/**
 * ReasonLink entity. @author MyEclipse Persistence Tools
 */

public class ReasonLink  implements java.io.Serializable {


    // Fields    

     private ReasonLinkId id;


    // Constructors

    /** default constructor */
    public ReasonLink() {
    }

    
    /** full constructor */
    public ReasonLink(ReasonLinkId id) {
        this.id = id;
    }

   
    // Property accessors

    public ReasonLinkId getId() {
        return this.id;
    }
    
    public void setId(ReasonLinkId id) {
        this.id = id;
    }
   








}