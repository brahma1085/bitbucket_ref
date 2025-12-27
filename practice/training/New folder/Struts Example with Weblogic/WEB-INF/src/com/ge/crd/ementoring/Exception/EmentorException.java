package com.ge.crd.ementoring.Exception;

public class EmentorException extends Exception  {

    private String messageKey;

    public EmentorException() {
        super();
    }

    public EmentorException(String message) {
        super();
        this.messageKey = message;
    }

    public String getMessageKey() {
        return this.messageKey;
    }

    public String toString() {
        return ("Ementor-EXCEPTION -> [" + this.messageKey + "]");
    }

}
