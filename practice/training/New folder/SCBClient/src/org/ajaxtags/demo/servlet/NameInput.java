/**
 * Copyright (C) 2007 Jens Kapitza
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 **/
package org.ajaxtags.demo.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ajaxtags.servlets.BaseAjaxServlet;
import org.ajaxtags.xml.AjaxXmlBuilder;

public class NameInput extends BaseAjaxServlet {
	@Override
	public String getXmlContent(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		AjaxXmlBuilder xml = new AjaxXmlBuilder();
		xml.addItem("name", arg0.getParameter("name"));
		int age = -1;
		try {
			age = Integer.parseInt(arg0.getParameter("age"));
		} catch (Exception e) {
		}

		xml.addItem("age", age == -1 ? "$not a number ("
				+ arg0.getParameter("age") + ") ;)" : String.valueOf(age));
		return xml.toString();
	}
}
