package com.banking.data.pojo;
// default package



/**
 * JointHolders entity. @author MyEclipse Persistence Tools
 */

public class JointHolders  implements java.io.Serializable {


    // Fields    

     private JointHoldersId id;


    // Constructors

    /** default constructor */
    public JointHolders() {
    }

    
    /** full constructor */
    public JointHolders(JointHoldersId id) {
        this.id = id;
    }

   
    // Property accessors

    public JointHoldersId getId() {
        return this.id;
    }
    
    public void setId(JointHoldersId id) {
        this.id = id;
    }
   








}