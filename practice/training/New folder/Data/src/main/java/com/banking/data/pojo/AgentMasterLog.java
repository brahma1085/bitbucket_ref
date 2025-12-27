package com.banking.data.pojo;
// default package



/**
 * AgentMasterLog entity. @author MyEclipse Persistence Tools
 */

public class AgentMasterLog  implements java.io.Serializable {


    // Fields    

     private AgentMasterLogId id;


    // Constructors

    /** default constructor */
    public AgentMasterLog() {
    }

    
    /** full constructor */
    public AgentMasterLog(AgentMasterLogId id) {
        this.id = id;
    }

   
    // Property accessors

    public AgentMasterLogId getId() {
        return this.id;
    }
    
    public void setId(AgentMasterLogId id) {
        this.id = id;
    }
   








}