<tr>
	<td><bean:message key="label.dep_date"></bean:message></td>
	<%if(verify_values != null){
				
				System.out.println("Verify Not null--  dep date----------------");
				
				%>

	<td><html:text property="dep_date"
			value="<%= ""+verify_values.getDepDate() %>" size="10"
			styleClass="formTextFieldWithoutTransparent"></html:text> <%System.out.println("JSP VALUE ac_no verify value =="+verify_values.getDepDate()); %>

	</td>
	<%}else{
				   
				   System.out.println("Verify  null-------dep date-----------");
				   
				   %>

	<td><html:text property="dep_date"
			value="<%= ""+request.getAttribute("date") %>" size="10"
			styleClass="formTextFieldWithoutTransparent"></html:text></td>
	<%} %>
</tr>

<tr>

	<td><bean:message key="label.Period_in_days"></bean:message></td>
	<%if(verify_values != null){
				
				
				
				System.out.println("Verify Not null--cid----------------");
				
				%>

	<td><html:text property="period_of_days"
			value="<%=""+verify_values.getDepositDays() %>" onchange="submit()"
			onblur="checkblank(period_of_days.value,'Deposit Days >30')"
			size="10" styleClass="formTextFieldWithoutTransparent"></html:text> <%System.out.println("JSP VALUE period in days verify value =="+verify_values.getDepositDays()); %>

	</td>


	<%}else{
				   
				   System.out.println("Verify  -------no of days-----------");
				   
				   %>

	<td><html:text property="period_of_days" onchange="submit()"
			onblur="checkblank(period_of_days.value,'Deposit Days >30')"
			size="10" styleClass="formTextFieldWithoutTransparent"></html:text></td>
	<%} %>
</tr>

<tr>
	<td><bean:message key="label.mat_date"></bean:message></td>

	<%if(verify_values != null){
			    	
			    	System.out.println("Verify Not mat date---------------");
			    	%>

	<td><html:text property="mat_date"
			value="<%=""+verify_values.getMaturityDate() %>" size="10"></html:text>
	</td>

	<td><html:select property="combo_mat_cat"
			styleClass="formTextFieldWithoutTransparent">
			<% combo_mat_cat=(String[])request.getAttribute("combo_mat_cat");
			    			for(int i=0;i<combo_mat_cat.length;i++)
			    			{
			    			 	System.out.println("combo_mat_cat"+ combo_mat_cat);
			    	%>
			<html:option value="<%=""+combo_mat_cat[i]%>"><%=""+combo_mat_cat[i]%></html:option>
			<%
			    		
			    			}
			    	%>
		</html:select></td>


	<%}else{%>

	<%System.out.println("Verify  -------mat date-----------");%>

	<td><html:text property="mat_date"
			value="<%=""+request.getAttribute("matdate") %>" size="10"></html:text>
	</td>
	<td><html:select property="combo_mat_cat"
			styleClass="formTextFieldWithoutTransparent">
			<% combo_mat_cat=(String[])request.getAttribute("combo_mat_cat");
			    			for(int i=0;i<combo_mat_cat.length;i++)
			    			{
			    			 	System.out.println("combo_mat_cat"+ combo_mat_cat);
			    	%>
			<html:option value="<%=""+combo_mat_cat[i]%>"><%=""+combo_mat_cat[i]%></html:option>
			<%
			    		
			    			}
			    	%>
		</html:select></td>
	<%} %>
</tr>

<tr>
	<td><bean:message key="label.combo_autorenewal"></bean:message></td>


	<%if(verify_values != null){
			    	
			    	System.out.println("Verify Not null--autorenewal----------------");
				
				%>

	<td><html:select property="combo_autorenewal" size="1"
			styleClass="formTextFieldWithoutTransparent">
			<% auto=(String[])request.getAttribute("auto");
			    			for(int i=0;i<auto.length;i++)
			    			{
			    	
			        		 System.out.println("auto"+ auto);
			    		%>

			<html:option value="<%=""+verify_values.getAutoRenewal() %>"></html:option>
			<%
			    		
			    			}
			    		%>
		</html:select></td>

	<%}else{%>

	<%System.out.println("Verify-------autorenewal-----------");%>


	<td><html:select property="combo_autorenewal" size="1"
			styleClass="formTextFieldWithoutTransparent">
			<% auto=(String[])request.getAttribute("auto");
			    			for(int i=0;i<auto.length;i++)
			    			{
			    	
			        		 System.out.println("auto"+ auto);
			    		%>

			<html:option value="<%=""+auto[i]%>"><%=""+auto[i]%></html:option>
			<%
			    		
			    			}
			    		%>
		</html:select></td>

	<%}%>
</tr>


<tr>
	<td><bean:message key="label.introduceractypeno"></bean:message></td>

	<%if(verify_values!= null){%>
	<td><html:select property="intro_ac_type" size="1"
			styleClass="formTextFieldWithoutTransparent">
			<% module_object = (ModuleObject[])request.getAttribute("intro_type");
			    			for(int i=0;i<module_object.length;i++){
			    		%>
			<html:option value="<%=""+verify_values.getIntroAccType() %>"></html:option>
			<%
			    			}
			    		%>
		</html:select></td>
	<%}else{%>
	<td><html:select property="intro_ac_type" size="1"
			styleClass="formTextFieldWithoutTransparent">
			<% module_object = (ModuleObject[])request.getAttribute("intro_type");
			    			for(int i=0;i<module_object.length;i++){
			    		%>
			<html:option value="<%=""+combo_mat_cat[i]%>"><%=""+combo_mat_cat[i]%> ></html:option>
			<%}%>
		</html:select></td>

</tr>

