package com.scb.cm.actions;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Nov 6, 2007
 * Time: 12:15:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class PhotoForm extends ActionForm {
    
    public FormFile getTheFile() {
        return theFile;
    }

    public void setTheFile(FormFile theFile) {
        this.theFile = theFile;
    }

    public FormFile theFile;
}
