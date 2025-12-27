/**
 * Copyright 2005 Darren L. Spurgeon
 * Copyright 2007 Jens Kapitza
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ajaxtags.demo.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ajaxtags.demo.Car;
import org.ajaxtags.demo.CarService;
import org.ajaxtags.servlets.BaseAjaxServlet;
import org.ajaxtags.xml.AjaxXmlBuilder;

/**
 * An example servlet that responds to an ajax:select tag action. This servlet would be referenced
 * by the baseUrl attribute of the JSP tag.
 * 
 * @author Darren L. Spurgeon
 * @author Jens Kapitza
 * @version $Revision: 1.7 $ $Date: 2007/07/22 18:29:45 $
 */
public class DropdownServlet extends BaseAjaxServlet {

  /**
   * @see  BaseAjaxServlet#getXmlContent(javax.servlet.http.HttpServletRequest,
   *      javax.servlet.http.HttpServletResponse)
   */
  public String getXmlContent(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    String make = request.getParameter("make");

    // Get maker from your service bean
    CarService service = new CarService();
    List<Car> list = service.getModelsByMake(make);
    AjaxXmlBuilder xml = new AjaxXmlBuilder();
    int c =0;
    for (Car car:list) {
    	String param3 = "false";
    	//  i select each 4 value 
    	if (c==3) {
    		param3 = "true";
    	}
    	c++;
    	xml.addItem(car.getModel(),true,car.getModel(),param3);
    }
    System.out.println(xml.toString() + " -- ");
    return xml.toString();
  }

}
