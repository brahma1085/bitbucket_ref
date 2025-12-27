package com.banking.data.pojo;
// default package



/**
 * JointHoldersLog entity. @author MyEclipse Persistence Tools
 */

public class JointHoldersLog  implements java.io.Serializable {


    // Fields    

     private JointHoldersLogId id;


    // Constructors

    /** default constructor */
    public JointHoldersLog() {
    }

    
    /** full constructor */
    public JointHoldersLog(JointHoldersLogId id) {
        this.id = id;
    }

   
    // Property accessors

    public JointHoldersLogId getId() {
        return this.id;
    }
    
    public void setId(JointHoldersLogId id) {
        this.id = id;
    }
   








}