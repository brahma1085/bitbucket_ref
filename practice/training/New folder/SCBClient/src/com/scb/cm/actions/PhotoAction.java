package com.scb.cm.actions;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Created by IntelliJ IDEA.
 * User: shwetha
 * Date: Nov 23, 2007
 * Time: 4:45:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class PhotoAction extends Action{
        public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse res)throws Exception{
         String done="Done";
        PhotoForm myform=(PhotoForm)form;
        System.out.println("manu");

         FormFile myfile=myform.getTheFile();

         String contentype=myfile.getContentType();
         System.out.println("after get conttype");
         String fileName=myfile.getFileName();
         int filesize=myfile.getFileSize();
         byte[] filedata =myfile.getFileData();

         System.out.println("file name" +fileName);

         System.out.println("file size" +filesize);

         System.out.println("file data" +filedata);

          String content="goldfade.JPG";  
          req.setAttribute("swe",content);

         return map.findForward(done);
         }
      }


