/**
 * Copyright 2005 Darren L. Spurgeon
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ajaxtags.servlets.BaseAjaxServlet;
import org.ajaxtags.xml.AjaxXmlBuilder;

/**
 * An example servlet that responds to an ajax:callout tag action. This servlet would be referenced
 * by the baseUrl attribute of the JSP tag.
 * <p>
 * This servlet should generate XML in the following format:
 * </p>
 * <code><![CDATA[<?xml version="1.0"?>
 * <list>
 *   <item value="Item1">First Item</item>
 *   <item value="Item2">Second Item</item>
 *   <item value="Item3">Third Item</item>
 * </list>]]></code>
 * 
 * @author Darren L. Spurgeon
 * @version $Revision: 1.2 $ $Date: 2007/07/22 18:29:45 $
 */
public class CalloutServlet extends BaseAjaxServlet {

  /**
   * @see BaseAjaxServlet#getXmlContent(javax.servlet.http.HttpServletRequest,
   *      javax.servlet.http.HttpServletResponse)
   */
  public String getXmlContent(HttpServletRequest request, HttpServletResponse response) {
    String param = request.getParameter("q");

    return new AjaxXmlBuilder().addItemAsCData(
        "Callout Header",
        "<p>This is a test of the 'callout.view'</p><p>You asked about:<br/><b>"
            + param + "</b>.</p>").toString();
  }
}
