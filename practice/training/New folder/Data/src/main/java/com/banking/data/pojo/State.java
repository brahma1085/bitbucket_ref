package com.banking.data.pojo;
// default package



/**
 * State entity. @author MyEclipse Persistence Tools
 */

public class State  implements java.io.Serializable {


    // Fields    

     private StateId id;


    // Constructors

    /** default constructor */
    public State() {
    }

    
    /** full constructor */
    public State(StateId id) {
        this.id = id;
    }

   
    // Property accessors

    public StateId getId() {
        return this.id;
    }
    
    public void setId(StateId id) {
        this.id = id;
    }
   








}