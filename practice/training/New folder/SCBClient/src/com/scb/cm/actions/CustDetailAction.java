package com.scb.cm.actions;

import org.apache.struts.action.*;
import org.apache.struts.upload.FormFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.scb.designPatterns.CustomerDelegate;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Nov 8, 2007
 * Time: 1:32:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class CustDetailAction extends Action {

    public ActionForward execute(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse httpServletResponse) throws Exception {

      String done="Done";

     
        /*CustomerDetailForm custform=(CustomerDetailForm)form;
         FormFile myfile=custform.getPhoto();
         String contentype=myfile.getContentType();
         System.out.println("after get conttype");
         String fileName=myfile.getFileName();
         int filesize=myfile.getFileSize();
         byte[] filedata =myfile.getFileData();

         System.out.println("file name" +fileName);

         System.out.println("file size" +filesize);

         System.out.println("file data" +filedata);

*/
        System.out.println("hi from customer detail form");

        System.out.println("path"+map.getPath());
        CustomerDetailForm custform=(CustomerDetailForm)form;

        System.out.println("************************************");

        String name=custform.getName();
        System.out.println("Name========="+name);


        System.out.println("middlename========="+custform.getMidname());

        
        System.out.println("lastname============"+custform.getLastname());

        String slu=custform.getSalutation();
        System.out.println("salutatioin================" +slu);

        String cat=custform.getCategories();
        System.out.println("categories==================" +cat);

        String fathername=custform.getFathername();
        System.out.println("father name=========" +fathername);

        String mothername= custform.getMothername();
        System.out.println("mothername========" +mothername);

        String maritial=custform.getMarital();
        System.out.println("maritial=============" +maritial);

        String spouse=custform.getSpousename();
        System.out.println("spouse============"+spouse);

        String nation=custform.getNation();
        System.out.println("nation=============" +nation);

        String religion=custform.getReligion();
        System.out.println("religion====================" +religion);


        String sex=custform.getSex();
        System.out.println("sex=============="+sex);

        String caste=custform.getCaste();
        System.out.println("caste================="+caste);

        String scst=custform.getScst();
        System.out.println("scst=================="+scst);

        String DOB=custform.getDob();
        System.out.println("DOB==============="+DOB);

       int introid=custform.getIntro();
        System.out.println("introid============"+introid);

        int pass=custform.getPassportno();
        System.out.println("pass==================="+pass);

        String issue=custform.getIssuedate();
        System.out.println("issue========="+issue);

        String expdate=custform.getExpirydate();
        System.out.println("expdate==========="+expdate);

        String occup=custform.getOccupation();
        System.out.println("occup=========="+occup);

        String pan=custform.getPan();
        System.out.println("pan==========="+pan);

        String addrtype=custform.getAddrestype();
        System.out.println("addrtype============="+addrtype);

        String addrproof=custform.getAddrproof();
        System.out.println("addrproof================="+addrproof);

        String country=custform.getCountry();
        System.out.println("country==============="+country);

        String state=custform.getState();
        System.out.println("state============" +state);

        String city=custform.getCity();
        System.out.println("city============="+city);

        int pin=custform.getPin();
        System.out.println("pin============="+pin);

        int mobile=custform.getMobile();
        System.out.println("mobile===========" +mobile);

        String mailid=custform.getMailid();
        System.out.println("mailid=========="+mailid);

        String nameproof=custform.getNameproof();
        System.out.println("nameproof============="+nameproof);

         System.out.println("************************************");

        
        return map.findForward(done);

    }


}
