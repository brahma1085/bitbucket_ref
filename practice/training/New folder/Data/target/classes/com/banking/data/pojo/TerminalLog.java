package com.banking.data.pojo;
// default package



/**
 * TerminalLog entity. @author MyEclipse Persistence Tools
 */

public class TerminalLog  implements java.io.Serializable {


    // Fields    

     private TerminalLogId id;


    // Constructors

    /** default constructor */
    public TerminalLog() {
    }

    
    /** full constructor */
    public TerminalLog(TerminalLogId id) {
        this.id = id;
    }

   
    // Property accessors

    public TerminalLogId getId() {
        return this.id;
    }
    
    public void setId(TerminalLogId id) {
        this.id = id;
    }
   








}