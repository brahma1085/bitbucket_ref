package com.scb.common.actions;

import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.scb.cm.actions.ParameterForm;

/*import com.scb.cm.actions.ParameterForm;*/

public class MenuAction extends Action {
        public ActionForward execute(ActionMapping map,ActionForm form, HttpServletRequest req, HttpServletResponse res)throws Exception{
            String done="Done";
            String done1="Done1";
            String notdone="Failure";
            boolean flag=false;
            System.out.println("Princess path="+map.getPath());
           if(map.getPath().equals("/MenuLink")){
           DynaActionForm menuform=(DynaActionForm)form;
           String id=(String)menuform.get("pageId");
            System.out.println("id="+id);
            req.setAttribute("pageId",id);
            flag=true;

           }
          else{
          ParameterForm paramform=(ParameterForm)form;
          String param=paramform.getAddresstype();
          System.out.println("value in action class"+param);  
          paramform.setTxt("swetha");
          req.setAttribute("swe1",paramform);
          String pageid1=paramform.getPageId();
          System.out.println("second pageid="+pageid1);
          req.setAttribute("pageId",pageid1);     
          flag=false;

        }
            
        if (flag) {
                return map.findForward(done1);
            } else {
            return map.findForward(done);
            }
}

}