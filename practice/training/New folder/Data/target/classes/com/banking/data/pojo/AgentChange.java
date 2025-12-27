package com.banking.data.pojo;
// default package



/**
 * AgentChange entity. @author MyEclipse Persistence Tools
 */

public class AgentChange  implements java.io.Serializable {


    // Fields    

     private AgentChangeId id;


    // Constructors

    /** default constructor */
    public AgentChange() {
    }

    
    /** full constructor */
    public AgentChange(AgentChangeId id) {
        this.id = id;
    }

   
    // Property accessors

    public AgentChangeId getId() {
        return this.id;
    }
    
    public void setId(AgentChangeId id) {
        this.id = id;
    }
   








}