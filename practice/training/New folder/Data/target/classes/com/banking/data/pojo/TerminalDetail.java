package com.banking.data.pojo;
// default package



/**
 * TerminalDetail entity. @author MyEclipse Persistence Tools
 */

public class TerminalDetail  implements java.io.Serializable {


    // Fields    

     private TerminalDetailId id;


    // Constructors

    /** default constructor */
    public TerminalDetail() {
    }

    
    /** full constructor */
    public TerminalDetail(TerminalDetailId id) {
        this.id = id;
    }

   
    // Property accessors

    public TerminalDetailId getId() {
        return this.id;
    }
    
    public void setId(TerminalDetailId id) {
        this.id = id;
    }
   








}