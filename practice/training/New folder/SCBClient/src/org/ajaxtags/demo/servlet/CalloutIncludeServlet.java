/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 * Copyright @2004 the original author or authors.
 */
package org.ajaxtags.demo.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ajaxtags.helpers.AjaxHtmlHelper;
import org.ajaxtags.servlets.BaseAjaxServlet;
import org.ajaxtags.xml.AjaxXmlBuilder;

/**
 * Example to demonstrate how to use an include with the DisplayTag.
 *
 * @author Jose E. Gonzalez
 * @version $Revision: 1.2 $ $Date: 2007/07/22 18:29:45 $
 */
public class CalloutIncludeServlet extends BaseAjaxServlet {

  /**
   * @see  BaseAjaxServlet#getXmlContent(javax.servlet.http.HttpServletRequest,
   *      javax.servlet.http.HttpServletResponse)
   */
  public String getXmlContent(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    String responseString = "";
    RequestDispatcher dispatcher = request.getRequestDispatcher("/calloutcontent.jsp");
    if (dispatcher != null) {
      StringWrapper wrapper = new StringWrapper((HttpServletResponse) response);
      dispatcher.include(request, wrapper);
      responseString = wrapper.toString();
    }
    responseString = AjaxHtmlHelper.getTagContentById(responseString, "sample");

    return new AjaxXmlBuilder().addItemAsCData("Callout Header", responseString).toString();
  }

}
