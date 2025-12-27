package com.scb.common.actions;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Nov 14, 2007
 * Time: 2:52:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class MenuForm extends ActionForm {
    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    String pageId;
}
